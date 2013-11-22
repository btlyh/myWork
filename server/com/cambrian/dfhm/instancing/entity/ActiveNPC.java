package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
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
public class ActiveNPC extends NPC
{

	/* static fields */
	public static final int[] ACTIVE_NPC_SIDS={10008};
	/* static methods */

	/* fields */
	/** ��ǰNPC�ܼ����� */
	private int openDay;
	/** ��NPC�Ĺ����������� */
	private int attConfine;

	/* constructors */

	/* properties */
	public int getOpenDay()
	{
		return openDay;
	}

	public void setOpenDay(int openDay)
	{
		this.openDay=openDay;
	}

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
		if(openDay!=TimeKit.getDayOfWeek())
		{
			return Lang.F1405;
		}
		if(checkRecord!=null)
		{
			if(checkRecord.getAttacks()>=attConfine)
			{
				return Lang.F1407;
			}
		}
		return null;
	}

	@Override
	public void handleForWin(Player player)
	{

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
		monsters=new BattleCard[battleCardList.length];
		for(int i=0;i<monsters.length;i++)
		{
			Monster card=(Monster)Sample.factory
				.getSample(battleCardList[i]);
			BattleCard battleCard=new BattleCard(card.getId(),
				card.getName(),card.getAvatar(),card.getTinyAvatar(),
				card.getLevel(),card.getAtt(),card.getSkillRate(),
				card.getAttRange(),card.getSkillId(),card.getMaxHp(),
				card.getCurHp(),i,card.getAimType(),card.getCritRate(),
				card.getDodgeRate(),card.getAwardSid(),i);
			monsters[i]=battleCard;
		}
	}

	@Override
	public void winCondition(BattleScene scene,BattleCard[] attList,Player player)
	{
		// TODO Auto-generated method stub

	}
}
