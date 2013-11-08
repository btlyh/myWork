package com.cambrian.dfhm.globalboss.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.globalboss.entity.BossFightRecord;
import com.cambrian.dfhm.globalboss.entity.GlobalBossCFG;
import com.cambrian.dfhm.globalboss.notice.BossEndNotice;
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：世界BOSS逻辑处理类
 * 
 * @author:LazyBear
 */
public class BossManager
{

	/* static fields */
	private static BossManager instance=new BossManager();

	/* static methods */
	public static BossManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** BOSS集合，以BOSS的SID为键 */
	public Map<Integer,GlobalBossCFG> bossMap=new HashMap<Integer,GlobalBossCFG>();
	/** BOSS结束推送消息 */
	private BossEndNotice ben;
	/** 数据服务器 */
	private DataServer ds;
	/** 邮件工厂类 */
	MailFactory mf;

	/* constructors */

	/* properties */
	public void setBen(BossEndNotice ben)
	{
		instance.ben=ben;
	}

	public void setDS(DataServer ds)
	{
		instance.ds=ds;
	}

	public void setMailFactory(MailFactory mf)
	{
		instance.mf=mf;
	}

	/* init start */

	/* methods */
	/**
	 * 关闭自动战斗
	 * 
	 * @param player
	 * @param bossSid
	 */
	public void turnOffAuto(Player player,int bossSid)
	{
		Map<String,Object> resultMap=checkturnOffAuto(player,bossSid);
		String error=resultMap.get("error").toString();
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		GlobalBossCFG gbc=(GlobalBossCFG)resultMap.get("gbc");
		gbc.autoList.remove(player);
	}

	/**
	 * 检查关闭自动战斗
	 * 
	 * @param player
	 * @param bossSid
	 * @return
	 */
	private Map<String,Object> checkturnOffAuto(Player player,int bossSid)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		GlobalBossCFG gbc=bossMap.get(bossSid);
		if(gbc==null)
		{
			mapInfo.put("error",Lang.F1805);
			return mapInfo;
		}
		if(player.getBfr()==null)
		{
			mapInfo.put("error",Lang.F1803);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("gbc",gbc);
		return mapInfo;
	}
	/**
	 * 开启自动战斗
	 * 
	 * @param player 玩家对象
	 * @param bossSid
	 */
	public void turnOnAuto(Player player,int bossSid)
	{
		Map<String,Object> resultMap=checkturnOnAuto(player,bossSid);
		String error=resultMap.get("error").toString();
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		GlobalBossCFG gbc=(GlobalBossCFG)resultMap.get("gbc");
		gbc.autoList.add(player);
	}
	/**
	 * 检查开启自动战斗
	 * 
	 * @param player
	 * @param bossSid
	 * @return
	 */
	private Map<String,Object> checkturnOnAuto(Player player,int bossSid)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		GlobalBossCFG gbc=bossMap.get(bossSid);
		if(gbc==null)
		{
			mapInfo.put("error",Lang.F1805);
			return mapInfo;
		}
		if(player.getBfr()==null)
		{
			mapInfo.put("error",Lang.F1803);
			return mapInfo;
		}
		if(!gbc.isOpen())
		{
			mapInfo.put("error",Lang.F1807);
			return mapInfo;
		}
		if(TimeKit.nowTimeMills()<TimeKit.timeOf(gbc.getActiveTime()))
		{
			mapInfo.put("error",Lang.F1810);
			return mapInfo;
		}
		if(player.formation.isEmpty())
		{
			mapInfo.put("error",Lang.F1410);
			return mapInfo;
		}
		if(player.getVipLevel()<gbc.getVipConfine())
		{
			mapInfo.put("error",Lang.F1809);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("gbc",gbc);
		return mapInfo;
	}

	/**
	 * 攻击BOSS
	 * 
	 * @param player
	 * @param bossSid
	 */
	public ArrayList<Integer> attBoss(Player player,int bossSid)
	{
		Map<String,Object> resultMap=checkAttBoss(player,bossSid);
		String error=resultMap.get("error").toString();
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		BattleCard[] att=player.formation.getFormation().clone();
		if(player.getBfr().getAttUp()>1)
		{
			for(BattleCard battleCard:att)
			{
				battleCard.setAtt(battleCard.getAtt()
					*player.getBfr().getAttUp());
			}
		}
		GlobalBossCFG gbc=(GlobalBossCFG)resultMap.get("gbc");
		synchronized(gbc)
		{
			BattleCard[] def=gbc.getMonsters();
			BattleScene scene=new BattleScene();
			battleInit(att,def);
			scene.setMaxRound(30);
			scene.setRoundConfine(gbc.getRoundConfine());
			scene.start(att,def,BattleScene.FIGHT_GLOBALBOSS);
			scene.getRecord().set(0,scene.getStep());

			player.getBfr().setLastAttTime(TimeKit.nowTimeMills());
			player.getBfr().recordDamage(
				scene.getBattleRecord().getTotalDamage());

			gbc.rankMap.put((int)player.getUserId(),player.getBfr());
			// 战斗胜利后的处理
			int win=scene.getRecord().get(scene.getRecord().size()-1);
			if(win==1)// 胜利
			{
				player.getBfr().setFinished(true);
				gbc.countRank();
				gbc.setOpen(false);
				gbc.reset();
				gbc.autoList.clear();
				sendReward(gbc,true);
			}
			return scene.getRecord();
		}

	}

	/**
	 * 检查攻击BOSS
	 * 
	 * @param player
	 * @param bossSid
	 * @return
	 */
	private Map<String,Object> checkAttBoss(Player player,int bossSid)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		GlobalBossCFG gbc=bossMap.get(bossSid);
		if(gbc==null)
		{
			mapInfo.put("error",Lang.F1805);
			return mapInfo;
		}
		if(player.getBfr()==null)
		{
			mapInfo.put("error",Lang.F1803);
			return mapInfo;
		}
		if(TimeKit.nowTimeMills()<TimeKit.timeOf(gbc.getActiveTime()))
		{
			mapInfo.put("error",Lang.F1810);
			return mapInfo;
		}
		if(!gbc.isOpen())
		{
			mapInfo.put("error",Lang.F1807);
			return mapInfo;
		}
		if(player.formation.isEmpty())
		{
			mapInfo.put("error",Lang.F1410);
			return mapInfo;
		}
		if(player.getBfr().getLastAttTime()>0)
		{
			if(player.getBfr().getLastAttTime()
				+TimeKit.timeOf(0,gbc.getAttCD())>TimeKit.nowTime())
			{
				mapInfo.put("error",Lang.F1806);
				return mapInfo;
			}
		}
		mapInfo.put("error",null);
		mapInfo.put("gbc",gbc);
		return mapInfo;
	}

	/**
	 * 立即复活功能
	 */
	public void relive(Player player,int bossSid)
	{
		Map<String,Object> resultMap=checkRelive(player,bossSid);
		String error=resultMap.get("error").toString();
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		int needGold=Integer.parseInt(resultMap.get("needGold").toString());
		player.decrGold(needGold);
		player.getBfr().setLastAttTime(0);
	}

	/**
	 * 检查复活功能
	 * 
	 * @param player
	 * @param bossSid
	 * @return
	 */
	private Map<String,Object> checkRelive(Player player,int bossSid)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		GlobalBossCFG gbc=bossMap.get(bossSid);
		if(gbc==null)
		{
			mapInfo.put("error",Lang.F1805);
			return mapInfo;
		}
		if(player.getBfr()==null)
		{
			mapInfo.put("error",Lang.F1803);
			return mapInfo;
		}
		int needGold=gbc.getAttUpGold()*player.getBfr().getAttUp();
		if(player.getGold()<needGold)
		{
			mapInfo.put("error",Lang.F1804);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("needGold",needGold);
		return mapInfo;
	}

	/**
	 * 攻击强化功能
	 * 
	 * @param player
	 * @param bossSid
	 */
	public void attUp(Player player,int bossSid)
	{
		Map<String,Object> resultMap=checkAttUp(player,bossSid);
		String error=resultMap.get("error").toString();
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		int needGold=Integer.parseInt(resultMap.get("needGold").toString());
		player.decrGold(needGold);
		player.getBfr().inAttUp();
	}

	/**
	 * 检查攻击强化
	 * 
	 * @param player
	 * @param bossSid
	 * @return
	 */
	private Map<String,Object> checkAttUp(Player player,int bossSid)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		GlobalBossCFG gbc=bossMap.get(bossSid);
		if(gbc==null)
		{
			mapInfo.put("error",Lang.F1805);
			return mapInfo;
		}
		if(player.getBfr()==null)
		{
			mapInfo.put("error",Lang.F1803);
			return mapInfo;
		}
		int needGold=gbc.getAttUpGold()*player.getBfr().getAttUp();
		if(player.getGold()<needGold)
		{
			mapInfo.put("error",Lang.F1804);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("needGold",needGold);
		return mapInfo;
	}

	/**
	 * 进入BOSS主界面
	 * 
	 * @param player 玩家对象
	 * @param bossSid boss对象ID
	 */
	public Map<String,Object> bossMain(Player player,int bossSid)
	{
		Map<String,Object> resultMap=checkBossMain(player,bossSid);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		if(player.getBfr()==null)
		{
			player.setBfr(new BossFightRecord(player.getNickname(),
				(int)player.getUserId(),bossSid));
		}
		GlobalBossCFG gbc=(GlobalBossCFG)resultMap.get("gbc");
		List<BossFightRecord> recordList=gbc.countRank();
		long lastAttTime=player.getBfr().getLastAttTime();
		long surplusTime=0;
		if(lastAttTime>0)
		{
			long nextAttTime=lastAttTime+TimeKit.timeOf(0,gbc.getAttCD());
			surplusTime=nextAttTime-TimeKit.nowTimeMills();
		}

		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("recordList",recordList);
		dataMap.put("maxHp",gbc.getMaxHp());
		dataMap.put("curHp",gbc.getCurHp());
		dataMap.put("surplusTime",surplusTime);
		return dataMap;
	}

	/**
	 * 检查进入BOSS界面
	 * 
	 * @param player 玩家对象
	 * @param bossSid BOSS对象ID
	 * @return
	 */
	private Map<String,Object> checkBossMain(Player player,int bossSid)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		GlobalBossCFG gbc=bossMap.get(bossSid);
		if(gbc==null)
		{
			mapInfo.put("error",Lang.F1805);
			return mapInfo;
		}
		if(!gbc.isOpen())
		{
			mapInfo.put("error",Lang.F1801);
			return mapInfo;
		}
		if(player.getCurIndexForNormalNPC()<gbc.getNormalNPCIndex())
		{
			mapInfo.put("error",Lang.F1802);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("gbc",gbc);
		return mapInfo;
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
				battleCard.setAttack(false);
			}
		}
	}

	/**
	 * BOSS活动结束后发放奖励
	 */
	@SuppressWarnings("unchecked")
	public void sendReward(GlobalBossCFG gbc,boolean isDeath)
	{
		Iterator<Integer> iterator=gbc.rankMap.keySet().iterator();
		BossFightRecord bfr;
		while(iterator.hasNext())
		{
			int key=iterator.next();
			bfr=gbc.rankMap.get(key);
			Map<String,ArrayList<Object>> rewardMap=gbc.countReward(bfr);
			ArrayList<Object> finishReward=rewardMap.get("finishReward");
			ArrayList<Object> rankReward=rewardMap.get("rankReward");
			ArrayList<Object> damageReward=rewardMap.get("damageReward");
			if(finishReward!=null)
			{
				mf.createSystemMail((ArrayList<Integer>)finishReward.get(0),
					0,Integer.parseInt(finishReward.get(2).toString()),0,
					Integer.parseInt(finishReward.get(1).toString()),0,
					bfr.getPlayerId());
			}
			if(rankReward!=null)
			{
				mf.createSystemMail((ArrayList<Integer>)rankReward.get(0),0,
					Integer.parseInt(rankReward.get(2).toString()),0,
					Integer.parseInt(rankReward.get(1).toString()),0,
					bfr.getPlayerId());
			}
			if(damageReward!=null)
			{
				mf.createSystemMail((ArrayList<Integer>)damageReward.get(0),
					0,Integer.parseInt(damageReward.get(2).toString()),0,
					Integer.parseInt(damageReward.get(1).toString()),0,
					bfr.getPlayerId());
			}
			Session session=ds.getSession(bfr.getNickName());
			if(session!=null)
			{
				Player player=(Player)session.getSource();
				player.setBfr(null);
			}
		}
		Session[] sessions=ds.getSessionMap().getSessions();
		for(Session session:sessions)
		{
			if(session!=null)
			{
				if(isDeath)
					ben.send(session,new Object[]{Lang.F1807});
				else
					ben.send(session,new Object[]{Lang.F1808});
			}
		}
	}

	/**
	 * 获取活动BOSS的SID
	 * 
	 * @return
	 */
	public int getActiveBossSid()
	{
		Iterator<Integer> iterator=bossMap.keySet().iterator();
		int bossSid=0;
		for(int i=0;i<bossMap.keySet().size();i++)
		{
			if(iterator.hasNext())
			{
				bossSid=iterator.next();
				if(bossMap.get(bossSid).isOpen())
				{
					break;
				}
				bossSid=0;
			}
		}
		return bossSid;
	}

	/**
	 * 获取BOSS活动的剩余时间
	 * 
	 * @return
	 */
	public long getActiveBossTime(int bossSid)
	{
		long surplusTime=0;
		if(bossSid!=0)
		{
			GlobalBossCFG gbc=bossMap.get(bossSid);
			if(gbc.isOpen())
			{
				long time=TimeKit.timeOf(gbc.getActiveTime());
				surplusTime=time-TimeKit.nowTimeMills();
				if(surplusTime<0)
				{
					surplusTime=0;
				}
			}
		}
		return surplusTime;
	}
}
