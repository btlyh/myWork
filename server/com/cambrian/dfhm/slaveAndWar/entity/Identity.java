package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.Lang;

/**
 * 类说明：身份对象-用于当壕功能
 * 
 * @author：LazyBear
 */
public class Identity
{

	/* static fields */
	public static final int SLAVE=1,FREEMAN=2,OWNER=3;
	/* static methods */

	/* fields */
	/** 级别 */
	private int grade;
	/** 级别名称 */
	private String gradeName;
	/** 马仔列表 */
	private List<Slave> slaveList=new ArrayList<Slave>();
	/** 主人ID(此属性为0表示不是奴隶) */
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
	 * 根据级别变更级别名称
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

	/** 序列化 和DC通信 存 */
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
	/** 序列化 和DC通信 取 */
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
