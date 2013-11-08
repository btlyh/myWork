package com.cambrian.dfhm.instancing.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.ActiveNPC;
import com.cambrian.dfhm.instancing.entity.AttRecord;
import com.cambrian.dfhm.instancing.entity.CrossNPC;
import com.cambrian.dfhm.instancing.entity.HardNPC;
import com.cambrian.dfhm.instancing.entity.NPC;
import com.cambrian.dfhm.instancing.entity.NormalNPC;

/**
 * ��˵��������������
 * 
 * @author��LazyBear
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

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

	/** �������� */
	public ArrayList<Integer> enterInstancing()
	{
		ArrayList<Integer> openActiveNPC=new ArrayList<Integer>();

		ActiveNPC npc=null;
		for(int i=0;i<ActiveNPC.ACTIVE_NPC_SIDS.length;i++)
		{
			npc=(ActiveNPC)Sample.factory
				.getSample(ActiveNPC.ACTIVE_NPC_SIDS[i]);

			if(npc.getOpenDay()==TimeKit.getDayOfWeek())
			{
				openActiveNPC.add(npc.getSid());
			}
		}
		return openActiveNPC;
	}
	/** ����NPC */
	public synchronized ArrayList<Integer> attackNPC(int sid,Player player,
		int npcType,int attType)
	{
		Map<String,Object> resultMap=checkAttackNPC(sid,player,npcType);
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
			case NPC.ACTIVITY:
				npc=(ActiveNPC)resultMap.get("npc");
				break;
			default:
				break;
		}
		BattleScene scene=new BattleScene();
		BattleCard[] att=player.formation.getFormation();
		BattleCard[] def=npc.getMonsters();
		for(int i=0;i<def.length;i++)
		{
			System.err.println("����ID==="+def[i].getSkill().getId());
		}
		battleInit(att,def);
		if(attType==1)
		{
			scene.setMaxRound(npc.getRound());
			scene.start(att,def,BattleScene.FIGHT_NORMAL);
			scene.getRecord().set(0,scene.getStep());
		}
		else if(attType==2)
		{
			// ɨ��
		}
		player.decrToken(npc.getNeedToken());
		npc.winCondition(scene,att);
		int win=scene.getRecord().get(scene.getRecord().size()-1);
		if(win==1)// ʤ��
		{
			npc.handleForWin(player);
			npc.addAward(scene,player);
		}
		npc.handleForAtt(player);
		return scene.getRecord();
	}

	/** ��鹥��NPC */
	private Map<String,Object> checkAttackNPC(int sid,Player player,
		int npcType)
	{
		System.err.println("npcType ==="+npcType);
		if(npcType>NPC.ACTIVITY||npcType<NPC.NORMAL)
		{
			throw new DataAccessException(601,Lang.F1409);
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
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
			case NPC.ACTIVITY:
				npc=(ActiveNPC)Sample.getFactory().getSample(sid);
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
	 * ս��ǰ��ʼ��
	 * 
	 * @param att
	 * @param def
	 * @param isGlobalBoss �Ƿ񹥻�����BOSS
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

}
