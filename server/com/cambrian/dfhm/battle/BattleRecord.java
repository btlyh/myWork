package com.cambrian.dfhm.battle;

import java.util.ArrayList;

/**
 * 类说明：
 * 
 * @author：Sebastian
 */
public class BattleRecord
{

	/* static fields */
	/** 是否有成员战斗死亡 */
	private boolean death=false;
	/** 最高攻击力记录 */
	private int attMax=0;
	/** 战斗记录 */
	private ArrayList<Integer> record=new ArrayList<Integer>();
	/** 玩家此次战斗的总伤害（用于世界BOSS） */
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
	 * 添加记录
	 * 
	 * @param rec
	 */
	public void addRecord(int rec)
	{
		record.add(rec);
	}

	/**
	 * 统计伤害
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
