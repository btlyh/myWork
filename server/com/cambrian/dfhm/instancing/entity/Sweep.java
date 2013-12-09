package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.object.Sample;
/**
 * 
 * 扫荡奖励类
 * @author Administrator
 *
 */
public class Sweep extends Sample {
	private int gold; // 最大金币数
	private int money; // 最小金币数
	private int soul; // 最大银币
	private int[] card;

	
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getSoul() {
		return soul;
	}

	public void setSoul(int soul) {
		this.soul = soul;
	}

	public int[] getCard() {
		return card;
	}

	public void setCard(int[] card) {
		this.card = card;
	}
	
	

}
