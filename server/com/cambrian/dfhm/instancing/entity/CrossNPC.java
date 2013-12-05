package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.monster.Monster;

/**
 * ��˵����
 * 
 * @author��LazyBear
 */
public class CrossNPC extends NPC
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��NPC�Ĺ����������� */
	private int attConfine;

	
	/**ɨ���Ľ���ID**/
	private int sweepId;
	/* constructors */

	public int getSweepId() {
		return sweepId;
	}

	public void setSweepId(int sweepId) {
		this.sweepId = sweepId;
	}

	/* properties */
	public int getAttConfine()
	{
		return attConfine;
	}

	public void setAttConfine(int attConfine)
	{
		this.attConfine=attConfine;
	}
	/* init start */

	/* methods */
	@Override
	public String checkAttNpc(Player player,AttRecord checkRecord)
	{
		if(player.getCurIndexForCorssNPC()<getSid())
		{
			return Lang.F1402;
		}
		player.getPlayerInfo().incrInstancingCount(1);
//		if(checkRecord!=null)
//		{
//			if(checkRecord.getAttacks()>=attConfine)
//			{
//				return Lang.F1407;
//			}
//		}
		return null;
	}

	@Override
	public void handleForWin(Player player)
	{
		if(getIsBoss()!=BOSS&&getNextSid()>player.getCurIndexForCorssNPC())
		{
			player.setCurIndexForCorssNPC(getNextSid());
			player.getPlayerInfo().incrInstancingCount(1);
		//	CrossNPC crossNPC = (CrossNPC)Sample.getFactory().getSample(getSid());						
			player.getPlayerInfo().addCrossNpc(getSid());
		}

	}
	@Override
	public void handleForAtt(Player player)
	{
		boolean exist=false;
		for(AttRecord attRecord:player.getAttRecords())
		{
			if(attRecord.getSidNPC()==getSid())
			{
				attRecord.inAttacks(1);
				exist=true;
				break;
			}
		}
		if(!exist)
		{
			AttRecord attRecord=new AttRecord(getSid(),1,getType());
			player.addAttRecord(attRecord);
		}
	}

	@Override
	public void initMonsters(String str)
	{
		monsters=new BattleCard[5];
		for(int i=0;i<battleCardList.length;i++)
		{
			Monster card=(Monster)Sample.factory
				.getSample(battleCardList[i]);
			BattleCard battleCard=new BattleCard(card.getId(),
				card.getName(),card.getAvatar(),card.getTinyAvatar(),
				card.getLevel(),card.getAtt(),card.getSkillRate(),
				card.getAttRange(),card.getSkillId(),card.getMaxHp(),
				card.getCurHp(),i,card.getAimType(),card.getCritRate(),
				card.getDodgeRate(),card.getAwardSid(),i,card.getSid(),card.getCritFactor());
			monsters[i]=battleCard;
		}
	}

	@Override
	public void winCondition(BattleScene scene,BattleCard[] attList,Player player)
	{
		// TODO Auto-generated method stub

	}
}
