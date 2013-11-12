package com.cambrian.dfhm.slaveAndWar.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;

/**
 * ��˵����������-��-
 * 
 * @author:LazyBear
 */
public class Slave implements Comparable<Slave>
{

	/* static fields */

	/* static methods */

	/* fields */
	/** �������� */
	private String name;
	/** ����״̬ */
	private int status;
	/** ����ս�� */
	private int fightPoint;
	/** ���п�ʼ����ʱ�� */
	private long startWorkTime;
	/** ������ʱ�� */
	private long takeTime;
	/** �Ƿ��й� */
	private boolean isManaged;

	/* constructors */
	/**
	 * ���й��췽���������µ����У�
	 * 
	 * @param name ��������
	 * @param fightPoint ս����
	 */
	public Slave(String name,int fightPoint)
	{
		this.name=name;
		this.fightPoint=fightPoint;
		this.takeTime=TimeKit.nowTimeMills();
	}

	public Slave()
	{

	}
	/* properties */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status=status;
	}

	public int getFightPoint()
	{
		return fightPoint;
	}

	public void setFightPoint(int fightPoint)
	{
		this.fightPoint=fightPoint;
	}

	public long getStartWorkTime()
	{
		return startWorkTime;
	}

	public void setStartWorkTime(long lastWorkTime)
	{
		this.startWorkTime=lastWorkTime;
	}

	public long getTakeTime()
	{
		return takeTime;
	}

	public void setTakeTime(long takeTime)
	{
		this.takeTime=takeTime;
	}

	public boolean isManaged()
	{
		return isManaged;
	}

	public void setManaged(boolean isManaged)
	{
		this.isManaged=isManaged;
	}
	/* init start */

	/* methods */
	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeUTF(name);
		data.writeInt(status);
		data.writeInt(fightPoint);
		data.writeLong(startWorkTime);
		data.writeLong(takeTime);
		data.writeBoolean(isManaged);
	}
	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		name=data.readUTF();
		status=data.readInt();
		fightPoint=data.readInt();
		startWorkTime=data.readLong();
		takeTime=data.readLong();
		isManaged=data.readBoolean();
	}

	/**
	 * ��д�ȽϷ���(����ս������)
	 */
	public int compareTo(Slave slave)
	{
		int i=this.fightPoint;
		int j=slave.getFightPoint();
		return ((i==j)?0:(i>j)?-1:1);
	}
}
