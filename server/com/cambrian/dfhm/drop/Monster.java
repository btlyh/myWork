package com.cambrian.dfhm.drop;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;

/**
 * 类说明：
 * 
 * @author：Sebastian
 */
public class Monster extends Sample
{

	/* static fields */
	public static final int MONEY=1,SOUL=2,CARD=4;
	public static final int RANDOM_BASE_VALUE=1;
	/* static methods */
	/* fields */
	/** 游戏币掉率 */
	private int[] moneyRate;
	/** 游戏币 */
	private int money;
	/** 武魂掉率 */
	private int[] soulRate;
	/** 武魂 */
	private int soul;
	/** 卡牌掉率 */
	private int cardRate[];
	/** 掉落卡片概率[卡片，概率|卡片，概率|...] */
	private int[][] card;
	/** 随机最大值 */
	private int randomValue;
	/** 卡牌随机最大值 */
	private int cardRandomVlaue;
	/* constructors */
	public int[] getMoneyRate()
	{
		return moneyRate;
	}
	public void setMoneyRate(int[] moneyRate)
	{
		this.moneyRate=moneyRate;
	}
	public int getMoney()
	{
		return money;
	}
	public void setMoney(int money)
	{
		this.money=money;
	}
	public int[] getSoulRate()
	{
		return soulRate;
	}
	public void setSoulRate(int[] soulRate)
	{
		this.soulRate=soulRate;
	}
	public int getSoul()
	{
		return soul;
	}
	public void setSoul(int soul)
	{
		this.soul=soul;
	}
	public int[] getCardRate()
	{
		return cardRate;
	}
	public void setCardRate(int[] cardRate)
	{
		this.cardRate=cardRate;
	}
	public int[][] getCard()
	{
		return card;
	}
	public void setCard(int[][] card)
	{
		System.err.println("drop.monster ------------");
		this.card=card;
	}
	public int getRandomValue()
	{
		return randomValue;
	}
	public void setRandomValue(int randomValue)
	{
		this.randomValue=randomValue;
	}
	public int getCardRandomVlaue()
	{
		return cardRandomVlaue;
	}
	public void setCardRandomVlaue(int cardRandomVlaue)
	{
		this.cardRandomVlaue=cardRandomVlaue;
	}
	/* properties */

	/* init start */

	/** 发放掉落奖励 */
	public int[] dispense(ArrayList<Integer> dropAward)
	{
		boolean allCard=false;
		int[] award=new int[2];
		int type=0;
		if(dropAward.size()>=8)// 当有4种掉落的情况
		{
			allCard=true;// 是否全部掉落了卡牌
			for(int i=0;i<dropAward.size();i+=2)
			{
				type=dropAward.get(i);
				if(!(type==CARD))
				{
					allCard=false;
					break;
				}
			}
		}
		if(allCard)
		{
			award[0]=MONEY;
			award[1]=money;
			return award;
		}
		else
		{
			int ran=MathKit.randomValue(RANDOM_BASE_VALUE,randomValue);

			if(ran>moneyRate[0]&&ran<=moneyRate[1])
			{
				award[0]=MONEY;
				award[1]=money;
			}
			if(ran>soulRate[0]&&ran<=soulRate[1])
			{
				award[0]=SOUL;
				award[1]=soul;
			}
			if(ran>cardRate[0]&&ran<=cardRate[1])
			{
				award[0]=CARD;
				ran=MathKit.randomValue(RANDOM_BASE_VALUE,cardRandomVlaue);
				for(int i=0;i<card.length;i++)
				{
					if(ran>card[i][1]&&ran<=card[i][2])
					{
						award[1]=card[i][0];
						break;
					}
				}
			}
			return award;
		}
	}
	/* methods */
}
