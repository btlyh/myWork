package com.cambrian.dfhm.instancing.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.AttRecord;
import com.cambrian.dfhm.instancing.entity.CrossNPC;
import com.cambrian.dfhm.instancing.entity.HardNPC;
import com.cambrian.dfhm.instancing.entity.NPC;
import com.cambrian.dfhm.instancing.entity.NormalNPC;
import com.cambrian.dfhm.mail.dao.MailDao;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.notice.MailSendNotice;
import com.cambrian.dfhm.mail.util.MailFactory;

/**
 * 类说明：副本管理器
 * 
 * @author：LazyBear
 */
public class InstancingManager
{

	/* static fields */
	private static InstancingManager instance=new InstancingManager();

	/* static methods */
	public static InstancingManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** 推送发送邮件消息 */
	MailSendNotice msn;
	/** 邮件数据访问对象 */
	MailDao dao;
	/** 邮件工厂类 */
	MailFactory mf;

	/* constructors */

	/* properties */
	public void setMailSendNotice(MailSendNotice msn)
	{
		instance.msn=msn;
	}

	public void setMailDao(MailDao dao)
	{
		instance.dao=dao;
	}

	public void setMailFactory(MailFactory mf)
	{
		instance.mf=mf;
	}
	/* init start */

	/* methods */

	/** 攻击NPC */
	public synchronized Map<String,ArrayList<Integer>> attackNPC(int sid,
		Player player,int npcType,int attType,List<Integer> cardList)
	{
		Map<String,Object> resultMap=checkAttackNPC(sid,player,npcType);
		Map<String,ArrayList<Integer>> resultData=new HashMap<String,ArrayList<Integer>>();
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		NPC npc=null;
		switch(npcType)
		{
			case NPC.NORMAL:
				npc=(NormalNPC)resultMap.get("npc");
				break;
			case NPC.HARD:
				npc=(HardNPC)resultMap.get("npc");
				break;
			case NPC.CROSS:
				npc=(CrossNPC)resultMap.get("npc");
				break;
			default:
				break;
		}

		BattleScene scene=new BattleScene();
		BattleCard[] att=player.formation.getFormation().clone();
		for(BattleCard battleCard:att)
		{
			if(battleCard!=null)
			{
				if(battleCard.getDrinkStatus()==Card.HYPER)
				{
					battleCard.setAtt(battleCard.getAtt()
						+battleCard.getAtt()*GameCFG.getDrinkAttUp()/100);
				}
			}
		}
		BattleCard[] def=npc.getMonsters();
		battleInit(att,def);
		if(attType==1)
		{
			scene.setMaxRound(npc.getRound());
			scene.start(att,def,BattleScene.FIGHT_NORMAL);
			scene.getRecord().set(0,scene.getStep());
		}
		else if(attType==2)
		{
			// 扫荡
		}
		player.decrToken(npc.getNeedToken());
		npc.winCondition(scene,att,player);
		int win=scene.getRecord().get(scene.getRecord().size()-1);
		ArrayList<Integer> rewardCardList=new ArrayList<Integer>();
		if(win>0)// 胜利
		{
			if(npcType==NPC.CROSS)
			{
				if(player.getPlayerInfo().getCrossMapNum()>0)
				{
					player.getPlayerInfo().setCrossMapNum(
						player.getPlayerInfo().getCrossMapNum()-1);
				}

			}
			npc.handleForWin(player);
			ArrayList<Integer> rewardCards=npc.addAward(scene,player);
			if(rewardCards.size()>0)
			{
				if(rewardCards.size()>player.getCardBag()
					.getSurplusCapacity())
				{
					Mail mail=mf.createSystemMail(rewardCards,0,0,0,0,0,
						(int)player.getUserId());
					player.addMail(mail);
				}
				else
				{
					for(Integer integer:rewardCards)
					{
						Card card=player.getCardBag().add(integer,
							player.getAchievements());
						rewardCardList.add(card.getSid());
						rewardCardList.add(card.uid);
						rewardCardList.add(card.getSkillId());
					}
				}
			}

			// setAttAchvments(player,sid);
		}
		npc.handleForAtt(player);
		/* 战斗完后改变卡牌的喝酒状态 */
		for (BattleCard battleCard : player.formation.getFormation())
		{
			if (battleCard != null)
			{
				if (battleCard.getDrinkStatus() == Card.HYPER)
				{
					player.getCardBag().getById(battleCard.getId()).setDrinkStatus(Card.DRUNK);
					battleCard.setDrinkStatus(Card.DRUNK);
				}
			}
		}
		for(int i=0;i<cardList.size();i++)
		{
			Card card=player.getCardBag().getById(cardList.get(i));
			if(card.isInArmyCamp()==1)
			{
				card.setDrinkStatus(Card.DRUNK);
				player.formation.setBattleCardDrink(card.getId(), Card.DRUNK);
			}
			else
			{
				card.setDrinkStatus(Card.AWAKE);
				player.formation.setBattleCardDrink(card.getId(), Card.AWAKE);
			}
		}
		resultData.put("record",scene.getRecord());
		resultData.put("reward",rewardCardList);
		return resultData;
	}
	/** 检查攻击NPC */
	private Map<String,Object> checkAttackNPC(int sid,Player player,
		int npcType)
	{

		Map<String,Object> resultMap=new HashMap<String,Object>();
//		if(player.getPlayerInfo().getLeadStep()!=-1)
//		{
//			for(BattleCard card:player.formation.getFormation())
//			{
//				if(card==null)
//				{
//					throw new DataAccessException(601,Lang.F2501);
//				}
//			}
//		}

		System.err.println("npcType ==="+npcType);
		if(npcType>NPC.CROSS||npcType<NPC.NORMAL)
		{
			resultMap.put("error",Lang.F1409);
			return resultMap;
		}
		if(player.getCurToken()<1)
		{
			resultMap.put("error",Lang.F1403);
			return resultMap;
		}
		if(player.formation.isEmpty())
		{
			resultMap.put("error",Lang.F1410);
			return resultMap;
		}

		if(npcType==NPC.CROSS)
		{
			if(player.getPlayerInfo().getCrossMapNum()==0)
			{
				resultMap.put("error",Lang.F1414);
				return resultMap;
			}
		}

		if(player.getCardBag().getSurplusCapacity()<1)
		{
			resultMap.put("error",Lang.F1415);
			return resultMap;
		}

		NPC npc=null;
		switch(npcType)
		{
			case NPC.NORMAL:
				npc=(NormalNPC)Sample.getFactory().getSample(sid);
				break;
			case NPC.HARD:
				npc=(HardNPC)Sample.getFactory().getSample(sid);
				break;
			case NPC.CROSS:
				npc=(CrossNPC)Sample.getFactory().getSample(sid);
				break;
			default:
				break;
		}
		if(npc==null)
		{
			resultMap.put("error",Lang.F1401);
			return resultMap;
		}
		if(player.getCurToken()<npc.getNeedToken())
		{
			resultMap.put("error",Lang.F1404);
			return resultMap;
		}
		ArrayList<AttRecord> attRecords=player.getAttRecords();
		AttRecord checkRecord=null;
		if(attRecords!=null&&attRecords.size()>0)
		{
			for(AttRecord attRecord:attRecords)
			{
				if(attRecord.getSidNPC()==npc.getSid())
				{
					checkRecord=attRecord;
				}
			}
		}
		String error=npc.checkAttNpc(player,checkRecord);
		resultMap.put("error",error);
		resultMap.put("npc",npc);
		return resultMap;
	}

	/**
	 * 战斗前初始化
	 * 
	 * @param att
	 * @param def
	 * @param isGlobalBoss 是否攻击世界BOSS
	 */
	private void battleInit(BattleCard[] att,BattleCard[] def)
	{
		for(BattleCard battleCard:att)
		{
			if(battleCard!=null)
			{
				battleCard.setSide(1);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
		for(BattleCard battleCard:def)
		{
			if(battleCard!=null)
			{
				battleCard.setSide(2);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
	}

	private void setAttAchvments(Player player,int sid)
	{

		switch(sid)
		{
			case 12230:
				if(!player.getAchievements().isPassWoSong())
					player.getAchievements().setPassWoSong(true);
				break;
			case 12220:
				if(!player.getAchievements().isPassLuZhiShen())
					player.getAchievements().setPassLuZhiShen(true);
				break;
			case 12215:
				if(!player.getAchievements().isPassZhangJiao())
					player.getAchievements().setPassZhangJiao(true);
				break;
			case 12202:
				if(!player.getAchievements().isPassZhangManCheng())
					player.getAchievements().setPassZhangManCheng(true);
				break;
			case 12001:
				if(!player.getAchievements().isFristPassCross())
					player.getAchievements().setFristPassCross(true);
				break;
			case 12201:
				if(!player.getAchievements().isPassHuangJinQianShao())
					player.getAchievements().setPassHuangJinQianShao(true);
				break;
			default:
				break;
		}

	}

}
