package com.cambrian.dfhm.slaveAndWar.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;

/**
 * 类说明：信息对象(用于当壕功购能)
 * 
 * @author：LazyBear
 */
public class Information implements Comparable<Information>
{

	/* static fields */
	/** 事件类型：1 战斗打自由人 2 战斗打土豪 3战斗救陌生人 4战斗救朋友 5办事 6 赎身*/
	public static final int EVENT_FIGHT_FREE=1,EVENT_FIGHT_OWNER=2,
					EVENT_FIGHT_SAVENOR=3,EVENT_FIGHT_SAVEFRI=4,
					EVENT_FIGHT_WORK=5,EVENT_GETFREE=6;
	/* static methods */

	/* fields */
	/** 事件类型 */
	private int eventType;
	/** 是否成功 */
	private boolean success;
	/** 主动角色 */
	private String activeName;
	/** 被动角色 */
	private String unactiveName;
	/** 附加角色 */
	private String attachName;
	/** 附加值（用于办事获利显示） */
	private int attachValue;
	/** 获取信息时间 */
	private long createTime;

	/* constructors */
	public Information(int eventType,boolean success,String activeName,
		String unactivName,String attachName,int attachValue)
	{
		this.eventType=eventType;
		this.success=success;
		this.activeName=activeName;
		this.unactiveName=unactivName;
		this.attachName=attachName;
		this.attachValue=attachValue;
		this.createTime=TimeKit.nowTimeMills();
	}

	public Information()
	{

	}
	/* properties */
	public int getEventType()
	{
		return eventType;
	}

	public void setEventType(int eventType)
	{
		this.eventType=eventType;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success=success;
	}

	public String getActiveName()
	{
		return activeName;
	}

	public void setActiveName(String activeName)
	{
		this.activeName=activeName;
	}

	public String getUnacitveName()
	{
		return unactiveName;
	}

	public void setUnacitveName(String unacitveName)
	{
		this.unactiveName=unacitveName;
	}

	public String getAttachName()
	{
		return attachName;
	}

	public void setAttachName(String attachName)
	{
		this.attachName=attachName;
	}

	public int getAttachValue()
	{
		return attachValue;
	}

	public void setAttachValue(int attachValue)
	{
		this.attachValue=attachValue;
	}
	public long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(long takeTime)
	{
		this.createTime=takeTime;
	}
	/* init start */

	/* methods */

	/** 序列化 和前端通信 写 */
	public void BytesWrite(ByteBuffer data)
	{
		data.writeInt(eventType);
		data.writeBoolean(success);
		data.writeUTF(activeName);
		data.writeUTF(unactiveName);
		data.writeUTF(attachName);
		data.writeInt(attachValue);
		data.writeLong(createTime);
	}
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(eventType);
		data.writeBoolean(success);
		data.writeUTF(activeName);
		data.writeUTF(unactiveName);
		data.writeUTF(attachName);
		data.writeInt(attachValue);
		data.writeLong(createTime);
	}
	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		eventType=data.readInt();
		success=data.readBoolean();
		activeName=data.readUTF();
		unactiveName=data.readUTF();
		attachName=data.readUTF();
		createTime=data.readLong();
	}

	/**
	 * 重写排序
	 */
	public int compareTo(Information information)
	{
		long i=this.createTime;
		long j=information.getCreateTime();
		return ((i==j)?0:(i>j)?-1:1);
	}

}
