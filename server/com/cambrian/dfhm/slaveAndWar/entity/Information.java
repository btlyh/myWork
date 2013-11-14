package com.cambrian.dfhm.slaveAndWar.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;

/**
 * ��˵������Ϣ����(���ڵ���������)
 * 
 * @author��LazyBear
 */
public class Information implements Comparable<Information>
{

	/* static fields */
	/** �¼����ͣ�1 ս���������� 2 ս�������� 3ս����İ���� 4ս�������� 5���� 6 ����*/
	public static final int EVENT_FIGHT_FREE=1,EVENT_FIGHT_OWNER=2,
					EVENT_FIGHT_SAVENOR=3,EVENT_FIGHT_SAVEFRI=4,
					EVENT_WORK=5,EVENT_GETFREE=6;
	/* static methods */

	/* fields */
	/** �¼����� */
	private int eventType;
	/** �Ƿ�ɹ� */
	private boolean success;
	/** ������ɫ */
	private String activeName;
	/** ������ɫ */
	private String unactiveName;
	/** ���ӽ�ɫ */
	private String attachName;
	/** ����ֵ�����ڰ��»�����ʾ�� */
	private int attachValue;
	/** ��ȡ��Ϣʱ�� */
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

	/** ���л� ��ǰ��ͨ�� д */
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
	/** ���л� ��DCͨ�� �� */
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
	/** ���л� ��DCͨ�� ȡ */
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
	 * ��д����
	 */
	public int compareTo(Information information)
	{
		long i=this.createTime;
		long j=information.getCreateTime();
		return ((i==j)?0:(i>j)?-1:1);
	}

}