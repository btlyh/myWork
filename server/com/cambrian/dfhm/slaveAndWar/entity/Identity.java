package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.Lang;

/**
 * ��˵������ݶ���-���ڵ�������
 * 
 * @author��LazyBear
 */
public class Identity
{

	/* static fields */
	/** ���״̬ */
	public static final int SLAVE=1,FREEMAN=2,OWNER=3;
	/** �����ȡս�����ֵ */
	public static final int ERRORVLAUE=5;
	/* static methods */

	/* fields */
	/** ���� */
	private int grade;
	/** �������� */
	private String gradeName;
	/** �����б� */
	private List<Slave> slaveList=new ArrayList<Slave>();
	/** ����ID(������Ϊ0��ʾ����ū��) */
	private int ownerId;

	/* constructors */

	/* properties */
	public int getGrade()
	{
		return grade;
	}

	public void setGrade(int grade)
	{
		this.grade=grade;
		gradeNameChange();
	}

	public String getGradeName()
	{
		return gradeName;
	}

	public List<Slave> getSlaveList()
	{
		return slaveList;
	}

	public void setSlaveList(List<Slave> slaveList)
	{
		this.slaveList=slaveList;
	}

	public int getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(int ownerId)
	{
		this.ownerId=ownerId;
	}
	/* init start */

	/* methods */
	/**
	 * ���ݼ�������������
	 */
	private void gradeNameChange()
	{
		switch(grade)
		{
			case SLAVE:
				gradeName=Lang.SLAVE;
				break;
			case FREEMAN:
				gradeName=Lang.FREEMAN;
			case OWNER:
				gradeName=Lang.OWNER;
			default:
				gradeName="error";
				break;
		}
	}

	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(grade);
		data.writeUTF(gradeName);
		data.writeInt(slaveList.size());
		for(Slave slave:slaveList)
		{
			slave.dbBytesWrite(data);
		}
		data.writeInt(ownerId);
	}
	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		grade=data.readInt();
		gradeName=data.readUTF();
		int len=data.readInt();
		for(int i=0;i<len;i++)
		{
			Slave slave=new Slave();
			slave.dbBytesRead(data);
			slaveList.add(slave);
		}
		ownerId=data.readInt();
	}
}
