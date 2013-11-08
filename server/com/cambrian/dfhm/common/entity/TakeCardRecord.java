package com.cambrian.dfhm.common.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：卡牌抽取记录类
 * 
 * @author:LazyBear
 */
public class TakeCardRecord
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 卡牌SID */
	private int sid;
	/** 卡牌位置 */
	private int index;
	/** 是否已被抽取 */
	private boolean isTkae;

	/* constructors */

	/* properties */
	public int getSid()
	{
		return sid;
	}

	public void setSid(int sid)
	{
		this.sid=sid;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index=index;
	}

	public boolean isTkae()
	{
		return isTkae;
	}

	public void setTake(boolean isTkae)
	{
		this.isTkae=isTkae;
	}
	/* init start */

	/* methods */
	/** 前台序列化通信*/
	public void BytesWrite(ByteBuffer data)
	{
		data.writeInt(sid);
		data.writeInt(index);
		data.writeBoolean(isTkae);
	}
	
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(sid);
		data.writeInt(index);
		data.writeBoolean(isTkae);
	}
	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		sid=data.readInt();
		index=data.readInt();
		isTkae=data.readBoolean();
	}

	@Override
	public String toString()
	{
		return sid+"";
	}
	
	
}
