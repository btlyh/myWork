package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.monster.Monster;

/**
 * 类说明：
 * 
 * @author：LazyBear
 */
public class NormalNPC extends NPC
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 开启挑战NPC的位置 如果为0则不配置 */
	private int hardIndex;
	/** 开启穿越NPC的位置 如果为0则不配置 */
	private int crossIndex;
	/**扫荡的奖励ID**/
	private int sweepId;
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

	public int getSweepId() {
		return sweepId;
	}

	public void setSweepId(int sweepId) {
		this.sweepId = sweepId;
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
			player.getPlayerInfo().setNormalNPCTime(
				(int)TimeKit.nowTimeMills());
			player.getPlayerInfo().incrInstancingCount(1);
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
