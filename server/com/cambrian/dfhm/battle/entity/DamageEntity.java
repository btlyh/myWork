package com.cambrian.dfhm.battle.entity;

/**
 * ��˵�����˺�����
 * 
 * @author��LazyBear
 */
public class DamageEntity
{

	/* static fields */
	/** �˺�״̬ 0��ͨ��1������2���� */
	public static final int DAMAGE_NORMAL=0,DAMAGE_CRIT=1,DAMAGE_DODGE=2;
	/* static methods */

	/* fields */
	/** �˺�״̬ */
	private int status;
	/** �˺�ֵ */
	private int value;

	/* constructors */
	public DamageEntity(int status,int value)
	{
		this.status=status;
		this.value=value;
	}
	/* properties */
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status=status;
	}
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value=value;
	}
	/* init start */

	/* methods */

}
