package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
/**
 * 
 * ɨ��������
 * @author Administrator
 *
 */
public class Sweep extends Sample {
	private int goldmax; // �������
	private int goldmin; // ��С�����
	private int moneymax; // �������
	private int moneymin; // ��С����
	private int soulmax;// ������
	private int soulmin;// ��С���
	private int[] card;

	public int getGoldmax() {
		return goldmax;
	}

	public void setGoldmax(int goldmax) {
		this.goldmax = goldmax;
	}

	public int getGoldmin() {
		return goldmin;
	}

	public void setGoldmin(int goldmin) {
		this.goldmin = goldmin;
	}

	public int getMoneymax() {
		return moneymax;
	}

	public void setMoneymax(int moneymax) {
		this.moneymax = moneymax;
	}

	public int getMoneymin() {
		return moneymin;
	}

	public void setMoneymin(int moneymin) {
		this.moneymin = moneymin;
	}

	public int getSoulmax() {
		return soulmax;
	}

	public void setSoulmax(int soulmax) {
		this.soulmax = soulmax;
	}

	public int getSoulmin() {
		return soulmin;
	}

	public void setSoulmin(int soulmin) {
		this.soulmin = soulmin;
	}
 
	public int[] getCard() {
		return card;
	}

	public void setCard(int[] card) {
		this.card = card;
	}
	
	

}
