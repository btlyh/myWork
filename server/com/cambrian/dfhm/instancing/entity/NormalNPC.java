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
public class NormalNPC extends NPC
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ������սNPC��λ�� ���Ϊ0������ */
	private int hardIndex;
	/** ������ԽNPC��λ�� ���Ϊ0������ */
	private int crossIndex;

	/* constructors */

	/* properties */
	public int getHardIndex()
	{
		return hardIndex;
	}

	public void setHardIndex(int hardIndex)
	{
		this.hardIndex=hardIndex;
	}

	public int getCrossIndex()
	{
		return crossIndex;
	}

	public void setCrossIndex(int crossIndex)
	{
		this.crossIndex=crossIndex;
	}
	/* init start */

	/* methods */
	@Override
	public String checkAttNpc(Player player,AttRecord checkRecord)
	{
		if(player.getCurIndexForNormalNPC()<getSid())
		{
			return Lang.F1402;
		}
		return null;
	}

	@Override
	public void handleForWin(Player player)
	{
		if(getNextSid()>player.getCurIndexForNormalNPC())
		{
			player.setCurIndexForNormalNPC(getNextSid());
			player.getPlayerInfo().setNormalNPCTime((int)TimeKit.nowTimeMills());
		}
		if(getIsBoss()==BOSS)
		{
			if(hardIndex>player.getCurIndexForHardNPC())
			{
				player.setCurIndexForHardNPC(hardIndex);
			}
			if(crossIndex>player.getCurIndexForCorssNPC())
			{
				player.setCurIndexForCorssNPC(crossIndex);
			}
		}
		
	}
	@Override
	public void handleForAtt(Player player)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initMonsters(String str)
	{
		monsters=new BattleCard[battleCardList.length];
		for(int i=0;i<monsters.length;i++)
		{
			Monster card=(Monster)Sample.factory.getSample(battleCardList[i]);
			BattleCard battleCard=new BattleCard(card.getId(),
				card.getName(),card.getAvatar(),card.getTinyAvatar(),
				card.getLevel(),card.getAtt(),card.getSkillRate(),
				card.getDoubleSkill(),card.getAttRange(),card.getSkillId(),
				card.getMaxHp(),card.getCurHp(),i,card.getAimType(),
				card.getCritRate(),card.getDodgeRate(),card.getAwardSid(),i);
			monsters[i]=battleCard;
		}
	}

	@Override
	public void winCondition(BattleScene scene,BattleCard[] attList)
	{
		// TODO Auto-generated method stub
		
	}
}