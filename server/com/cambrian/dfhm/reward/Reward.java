package com.cambrian.dfhm.reward;

import com.cambrian.common.object.Sample;

public class Reward extends Sample {


	private long time; // �콱����ȴʱ�䣨��Ϊ��λ��
	private int gold;// �������
	private int[][] card;// ��Ƭ��������ʽcard[sid][len]��
	private int soul;// �������
	private int tired; // ��������
	private int money;//����

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