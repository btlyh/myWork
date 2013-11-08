package com.cambrian.dfhm.slaveAndWar.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：马仔类-，-
 * 
 * @author:LazyBear
 */
public class Slave
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 马仔名字 */
	private String name;
	/** 马仔状态 */
	private int status;
	/** 马仔战力 */
	private int fightPoint;
	/** 马仔开始办事时间 */
	private long startWorkTime;
	/** 被捕获时间 */
	private long takeTime;
	/** 是否被托管 */
	private boolean isManaged;

	/* constructors */

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
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeUTF(name);
		data.writeInt(status);
		data.writeInt(fightPoint);
		data.writeLong(startWorkTime);
		data.writeLong(takeTime);
		data.writeBoolean(isManaged);
	}
	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		name=data.readUTF();
		status=data.readInt();
		fightPoint=data.readInt();
		startWorkTime=data.readLong();
		takeTime=data.readLong();
		isManaged=data.readBoolean();
	}
}
