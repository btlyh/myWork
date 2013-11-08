package com.cambrian.dfhm.drop;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;

/**
 * ��˵����
 * @author��Sebastian
 * 
 */
public class Monster extends Sample{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��Ϸ�ҵ��� */
	private int moneyRate;
	/** ��Ϸ�� */
	private int money;
	/** ������ */
	private int soulRate;
	/** ��� */
	private int soul;
	/** RMB���� */
	private int goldRate;
	/** RMB */
	private int gold;
	/** ���ֵ��� */
	private int integralRate;
	/** ���� */
	private int integral;
	/** ���Ƶ��� */
	private int cardRate;
	/** ���俨Ƭ����[��Ƭ������|��Ƭ������|...] */
	private int[] card;

	/* constructors */
	public int getMoneyRate() {
		return moneyRate;
	}
	public void setMoneyRate(int moneyRate) {
		this.moneyRate = moneyRate;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getSoulRate() {
		return soulRate;
	}
	public void setSoulRate(int soulRate) {
		this.soulRate = soulRate;
	}
	public int getSoul() {
		return soul;
	}
	public void setSoul(int soul) {
		this.soul = soul;
	}
	public int getGoldRate() {
		return goldRate;
	}
	public void setGoldRate(int goldRate) {
		this.goldRate = goldRate;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getIntegralRate() {
		return integralRate;
	}
	public void setIntegralRate(int integralRate) {
		this.integralRate = integralRate;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getCardRate() {
		return cardRate;
	}
	public void setCardRate(int cardRate) {
		this.cardRate = cardRate;
	}
	public int[] getCard() {
		return card;
	}
	public void setCard(int[] card) {
		System.err.println("drop.monster ------------");
		this.card = card;
	}

	/* properties */

	/* init start */
	
	/** ���ŵ��佱�� */
	public int[] dispense()
	{
		int[] award = new int[2];
		int ran = MathKit.randomValue(1, cardRate + 1);
		if(ran <= moneyRate)
		{
			award[0] = 1;
			award[1] = money;
		}
		else if(ran <= soulRate)
		{
			award[0] = 2;
			award[1] = soul;

		}
		else if(ran <= goldRate)
		{
			award[0] = 3;
			award[1] = gold;
		}
		else if(ran <= cardRate)
		{
			award[0] = 4;
			ran = MathKit.randomValue(1, card[card.length - 2] + 1);
			for (int i = 0; i < card.length; i+=2) {
				if(ran <= card[i])
				{
					award[1] = card[i];
					break;
				}					
			}
		}
		return award;
	}

	/* methods */

}