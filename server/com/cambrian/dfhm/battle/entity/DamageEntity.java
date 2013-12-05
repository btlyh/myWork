package com.cambrian.dfhm.battle.entity;

/**
 * ÀàËµÃ÷£ºÉËº¦¶ÔÏó
 * 
 * @author£ºLazyBear
 */
public class DamageEntity
{

	/* static fields */
	/** ÉËº¦×´Ì¬ 0ÆÕÍ¨¡¢1±©»÷¡¢2ÉÁ±Ü */
	public static final int DAMAGE_NORMAL=0,DAMAGE_CRIT=1,DAMAGE_DODGE=2;
	/* static methods */

	/* fields */
	/** ÉËº¦×´Ì¬ */
	private int status;
	/** ÉËº¦Öµ */
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
