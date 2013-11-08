package com.cambrian.dfhm.battle;

import java.util.ArrayList;

/**
 * ��˵����
 * 
 * @author��Sebastian
 */
public class BattleRecord
{

	/* static fields */
	/** �Ƿ��г�Աս������ */
	private boolean death=false;
	/** ��߹�������¼ */
	private int attMax=0;
	/** ս����¼ */
	private ArrayList<Integer> record=new ArrayList<Integer>();
	/** ��Ҵ˴�ս�������˺�����������BOSS�� */
	private int totalDamage;

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */
	public boolean isDeath()
	{
		return death;
	}
	public void setDeath(boolean death)
	{
		this.death=death;
	}
	public int getAttMax()
	{
		return attMax;
	}
	public void setAttMax(int attMax)
	{
		if(this.attMax<attMax) this.attMax=attMax;
	}
	public ArrayList<Integer> getRecord()
	{
		return record;
	}
	public int getTotalDamage()
	{
		return totalDamage;
	}
	public void setTotalDamage(int totalDamage)
	{
		this.totalDamage=totalDamage;
	}
	/* init start */

	/* methods */
	/**
	 * ��Ӽ�¼
	 * 
	 * @param rec
	 */
	public void addRecord(int rec)
	{
		record.add(rec);
	}

	/**
	 * ͳ���˺�
	 * 
	 * @param damage
	 */
	public void countDamage(int damage)
	{
		totalDamage+=damage;
	}

	/* common methods */

	/* inner class */

}
