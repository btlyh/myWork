package com.cambrian.dfhm.instancing.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵����������¼��
 * 
 * @author��LazyBear
 * 
 */
public class AttRecord
{

	/* static fields */

	/* static methods */

	/* fields */
	/** NPC SID */
	private int sidNPC;
	/** �������� (���Ϊ-1 ���� ��NPC�ѻ�ɱ�����ٴλ�ɱ) */
	private int attacks;
	/** ��¼���� */
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

	/** ǰ̨���л� */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(sidNPC);
		data.writeInt(attacks);
		data.writeInt(type);
	}

	/** ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(sidNPC);
		data.writeInt(attacks);
		data.writeInt(type);
	}

	/** ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		this.sidNPC = data.readInt();
		this.attacks = data.readInt();
		this.type = data.readInt();
	}
}