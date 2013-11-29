package com.cambrian.dfhm.reward;

import com.cambrian.common.object.Sample;

public class Reward extends Sample {


	private long time; // 领奖的冷却时间（秒为单位）
	private int gold;// 金币数量
	private int[][] card;// 卡片奖励（格式card[sid][len]）
	private int soul;// 武魂数量
	private int tired; // 军令数量
	private int money;//银币

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}



	public int[][] getCard() {
		return card;
	}

	public void setCard(int[][] card) {
		this.card = card;
	}

	public int getSoul() {
		return soul;
	}

	public void setSoul(int soul) {
		this.soul = soul;
	}

	public int getTired() {
		return tired;
	}

	public void setTired(int tired) {
		this.tired = tired;
	}
}