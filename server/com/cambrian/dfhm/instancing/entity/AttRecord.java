package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：攻击记录类
 * 
 * @author：LazyBear
 * 
 */
public class AttRecord
{

	/* static fields */

	/* static methods */

	/* fields */
	/** NPC SID */
	private int sidNPC;
	/** 攻击次数 (如果为-1 代表 此NPC已击杀不能再次击杀) */
	private int attacks;
	/** 记录类型 */
	private int type;

	/* constructors */
	public AttRecord(int sidNPC, int attacks, int type)
	{
		this.sidNPC = sidNPC;
		this.attacks = attacks;
		this.type = type;
	}

	public AttRecord()
	{

	}

	/* properties */

	public int getAttacks()
	{
		return attacks;
	}

	public void setAttacks(int attacks)
	{
		this.attacks = attacks;
	}

	public int getSidNPC()
	{
		return sidNPC;
	}

	public void setSidNPC(int sidNPC)
	{
		this.sidNPC = sidNPC;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public void inAttacks(int attacks)
	{
		this.attacks += attacks;
	}

	/* init start */

	/* methods */

	/** 前台序列化 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(sidNPC);
		data.writeInt(attacks);
		data.writeInt(type);
	}

	/** 与DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(sidNPC);
		data.writeInt(attacks);
		data.writeInt(type);
	}

	/** 与DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		this.sidNPC = data.readInt();
		this.attacks = data.readInt();
		this.type = data.readInt();
	}
}
