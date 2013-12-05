package com.cambrian.dfhm.instancing.entity;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：
 * 
 * @author：LazyBear
 */
public abstract class NPC extends Sample
{

	/* static fields */
	/** 副本类型 1主线 2挑战 3穿越（日常） 4活动 */
	public static final int NORMAL=19901,HARD=19902,CROSS=19903;
	/** NPC是BOSS */
	public static final int BOSS=1;
	/* static methods */

	/* fields */
	/** NPC中的怪物卡牌 */
	protected int[] battleCardList;
	/** 挑战此NPC所需军令 */
	private int needToken;
	/** 是否为BOSS战 1是 0否 */
	private int isBoss;
	/** 下一个NPC的位置 如果为0表示最高副本 */
	private int nextSid;
	/** 出战卡牌列表 */
	protected BattleCard[] monsters=new BattleCard[5];
	/** NPC类型(相当于副本类型) */
	private int type;
	/** 回合 */
	private int round;

	/* constructors */

	/* properties */
	public int[] getBattleCardList()
	{
		return battleCardList;
	}

	public void setBattleCardList(int[] battleCardList)
	{
		this.battleCardList=battleCardList;
	}

	public int getNeedToken()
	{
		return needToken;
	}

	public void setNeedToken(int needToken)
	{
		this.needToken=needToken;
	}

	public int getIsBoss()
	{
		return isBoss;
	}

	public void setIsBoss(int isBoss)
	{
		this.isBoss=isBoss;
	}

	public int getNextSid()
	{
		return nextSid;
	}

	public void setNextSid(int nextSid)
	{
		this.nextSid=nextSid;
	}

	public BattleCard[] getMonsters()
	{
		return monsters;
	}

	public void setMonsters(BattleCard[] monsters)
	{
		for(int i=0;i<this.monsters.length;i++)
		{
			this.monsters[i]=monsters[i];
		}
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type=type;
	}
	public int getRound()
	{
		return round;
	}
	public void setRound(int round)
	{
		this.round=round;
	}

	/* init start */

	/* methods */
	/** 怪物初始化 */
	public abstract void initMonsters(String str);
	/** 攻击NPC之前的检测 */
	public abstract String checkAttNpc(Player player,AttRecord checkRecord);
	/** 攻击胜利之后NPC的处理 */
	public abstract void handleForWin(Player player);
	/** 攻击之后的处理 */
	public abstract void handleForAtt(Player player);
	/** 胜利条件判定 */
	public abstract void winCondition(BattleScene scene,
		BattleCard[] attList,Player player);
	/** 派发奖励 */
	public ArrayList<Integer> addAward(BattleScene scene,Player player)
	{
		ArrayList<Integer> list=scene.getDropAward();
		int type,value;
		scene.getRecord().add(list.size()/2);
		ArrayList<Integer> rewardCards=new ArrayList<Integer>();
		for(int i=0;i<list.size();i+=2)
		{
			type=list.get(i);
			value=list.get(i+1);
			scene.getRecord().add(type);
			scene.getRecord().add(value);
			if(type==1)// 游戏币
			{
				player.incrMoney(value);
			}
			else if(type==2)// 武魂
			{
				player.incrSoul(value);
			}
			else if(type==3)// RMB
			{
				player.incrGold(value);
			}
			else
			// 卡牌
			{
				System.err.println("sid ==="+value);
				rewardCards.add(value);
			}
		}
		return rewardCards;
	}
}
