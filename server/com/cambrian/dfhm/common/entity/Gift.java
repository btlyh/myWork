package com.cambrian.dfhm.common.entity;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;

/**
 * ��˵����
 * @author��Zmk
 * 
 */
public class Gift extends Sample
{

	/* static fields */
	public static final int SID = 40005;
	/** ��Ʒ���� ��1--Ǯ�� 2-- ��꣬ 3--RMB�� 4-- ���� */
	public static final int TYPE_MONEY = 1,TYPE_SOUL = 2, TYPE_GOLD = 3, TYPE_CARD = 4;
	/* static methods */

	/* fields */
	/** ��Ϸ�Ҹ��� */
	private int moneyRate;
	/** ��Ϸ�� */
	private int[] money;
	/** ������ */
	private int soulRate;
	/** ��� */
	private int soul;
	/** RMB���� */
	private int goldRate;
	/** RMB */
	private int gold;
	/** ���ָ��� */
	private int integralRate;
	/** ���� */
	private int integral;
	/** ���Ƹ��� */
	private int cardRate;
	/** ���俨Ƭ����[��Ƭ������|��Ƭ������|...] */
	private int[] card;
	/* constructors */
	public int getMoneyRate()
	{
		return moneyRate;
	}
	public void setMoneyRate(int moneyRate)
	{
		this.moneyRate = moneyRate;
	}
	public int[] getMoney()
	{
		return money;
	}
	public void setMoney(int[] money)
	{
		this.money = money;
	}
	public int getSoulRate()
	{
		return soulRate;
	}
	public void setSoulRate(int soulRate)
	{
		this.soulRate = soulRate;
	}
	public int getSoul()
	{
		return soul;
	}
	public void setSoul(int soul)
	{
		this.soul = soul;
	}
	public int getGoldRate()
	{
		return goldRate;
	}
	public void setGoldRate(int goldRate)
	{
		this.goldRate = goldRate;
	}
	public int getGold()
	{
		return gold;
	}
	public void setGold(int gold)
	{
		this.gold = gold;
	}
	public int getIntegralRate()
	{
		return integralRate;
	}
	public void setIntegralRate(int integralRate)
	{
		this.integralRate = integralRate;
	}
	public int getIntegral()
	{
		return integral;
	}
	public void setIntegral(int integral)
	{
		this.integral = integral;
	}
	public int getCardRate()
	{
		return cardRate;
	}
	public void setCardRate(int cardRate)
	{
		this.cardRate = cardRate;
	}
	public int[] getCard()
	{
		return card;
	}
	public void setCard(int[] card)
	{
		this.card = card;
	}

	/* properties */

	/* init start */

	/* methods */
	/** ���Ž�Ʒ */
	public int[] dispense()
	{
		int[] award = new int[2];
		int ran = MathKit.randomValue(1, moneyRate + 1);
		if(ran <= goldRate)
		{
			award[0] = TYPE_GOLD;
			award[1] = gold;
		}
		else if(ran <= cardRate)
		{
			int cardRan = MathKit.randomValue(1, card[card.length - 1] + 1);
			for (int i = 1; i < card.length; i+=2) {
				if(cardRan <= card[i])
				{
					award[0] = card[i-1];
					award[1] = 0;
				}					
			}
		}
		else if(ran <= soulRate)
		{
			award[0] = TYPE_SOUL;
			award[1] = soul;
		}
		else if(ran <= moneyRate)
		{
			ran = MathKit.randomValue(1, money[1] + 1);
			for (int i = 1; i < money.length; i+=2)
			{
				if (ran <= money[i])
				{
					award[0] = TYPE_MONEY;
					award[1] = money[i-1];
				}
			}
		}
		return award;
	}
}
