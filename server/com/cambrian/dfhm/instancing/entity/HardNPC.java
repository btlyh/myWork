package com.cambrian.dfhm.instancing.entity;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.monster.Monster;

/**
 * 类说明：
 * 
 * @author：LazyBear
 */
public class HardNPC extends NPC
{

	/* static fields */
	/** 挑战NPC 击杀标准 */
	private static final int KILLED=-1;
	/* static methods */

	/* fields */
	/** 指定武将 */
	private int assignCardId;
	/** 是否需要武将全体存活 1是 0否 */
	private int isAllLive;
	/** 指定回合数 */
	private int assignRound;
	/** 指定武将个数 */
	private int cardCount;
	/** 一击所需造成的伤害 */
	private int assignDamage;
	/** 开启抽奖的SID */
	private int luckBoxSid;

	/* constructors */

	/* properties */
	public int getAssignCardId()
	{
		return assignCardId;
	}

	public void setAssignCardId(int assignCardId)
	{
		this.assignCardId=assignCardId;
	}

	public int getIsAllLive()
	{
		return isAllLive;
	}

	public void setIsAllLive(int isAllLive)
	{
		this.isAllLive=isAllLive;
	}

	public int getAssignRound()
	{
		return assignRound;
	}

	public void setAssignRound(int assignRound)
	{
		this.assignRound=assignRound;
	}

	public int getCardCount()
	{
		return cardCount;
	}

	public void setCardCount(int cardCount)
	{
		this.cardCount=cardCount;
	}

	public int getAssignDamage()
	{
		return assignDamage;
	}

	public void setAssignDamage(int assignDamage)
	{
		this.assignDamage=assignDamage;
	}
	/* init start */

	/* methods */
	@Override
	public String checkAttNpc(Player player,AttRecord checkRecord)
	{
		if(player.getCurIndexForHardNPC()<getSid())
		{
			return Lang.F1402;
		}
		if(checkRecord!=null)
		{
			if(checkRecord.getAttacks()==KILLED)
			{
				return Lang.F1406;
			}
		}
		return null;
	}

	@Override
	public void handleForWin(Player player)
	{
		player.getPlayerInfo().addInstancingCount(this.getSid(), 1);
		if(getIsBoss()!=BOSS&&getNextSid()>player.getCurIndexForHardNPC())
		{
			player.setCurIndexForHardNPC(getNextSid());
		}
		else
		{
			player.getPlayerInfo().setLuckBoxSid(luckBoxSid);
		}
		AttRecord attRecord=new AttRecord(getSid(),KILLED,getType());
		player.addAttRecord(attRecord);
		player.getPlayerInfo().addHardNpc(getSid());
		if (getSid() > player.getPlayerInfo().getHighestHardNPC())
		{
			player.getPlayerInfo().setHardNPCTime(
				(int)TimeKit.nowTimeMills());
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
				card.getDodgeRate(),card.getAwardSid(),i,card.getSid(),card.getCritFactor(),Card.AWAKE);
			monsters[i]=battleCard;
		}
	}

	/** 检测指定卡牌 */
	public boolean checkAssignCard(BattleCard[] battleCards,Player player)
	{
		if(assignCardId>0)// 指定卡牌
		{
			for(int i=0;i<battleCards.length;i++)
			{
				if(battleCards[i]!=null)
				{
					Card card = player.getCardBag().getById(battleCards[i].getId());
					if(card.getSid()==assignCardId)
					{
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}
	/** 检查是否需要全体存活 */
	public boolean checkIsAllLive(BattleRecord record)
	{
		if(isAllLive==1) // 全体存活
		{
			if(record.isDeath())
			{
				return false;
			}
		}
		return true;
	}
	/** 检查是否超过是指定回合 */
	public boolean checkAssignRound(int Round)
	{
		if(assignRound>0&&assignRound<Round)// 回合
		{
			return false;
		}
		return true;
	}
	/** 检查指定武将个数 */
	public boolean checkCardCount(int count)
	{
		if(cardCount>0&&cardCount<count)
		{
			return false;
		}
		return true;
	}
	/** 检查造成指定伤害 */
	public boolean checkAssignDamage(int damage)
	{
		if(damage<assignDamage) // 指定伤害
		{
			return false;
		}
		return true;
	}

	@Override
	public void winCondition(BattleScene scene,BattleCard[] attList,Player player)
	{
		boolean results[]={true,true,true,true,true};
		results[0]=checkAssignCard(attList,player);
		BattleRecord record=scene.getBattleRecord();
		results[1]=checkIsAllLive(record);
		results[2]=checkAssignRound(scene.getCurRound());
		int count=0;
		for(int i=0;i<attList.length;i++)
		{
			if(attList[i]!=null)
			{
				count++;
			}
		}
		results[3]=checkCardCount(count);
		results[4]=checkAssignDamage(record.getAttMax());

		ArrayList<Integer> recordList=scene.getRecord();
		for(int i=0;i<results.length;i++)
		{
			if(!results[i])
			{
				recordList.set(recordList.size()-1,-1);
				break;
			}
		}
	}
}
