package com.cambrian.dfhm.card;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.DBSerializable;
import com.cambrian.common.object.Serializable;
import com.cambrian.common.util.ArrayList;

/**
 * 类说明：卡片图鉴
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class CardContainer implements Serializable,DBSerializable
{

	/** 卡片列表  */
	ArrayList list=new ArrayList();	
	/**  */
	public void add(int id)
	{
		if(isContain(id)) return;
		list.add(id);
	}
	/** 是否包含该卡片 */
	public boolean isContain(int id)
	{
		for(int i=0;i<list.size();i++)
		{
			if(((Integer)list.get(i)).intValue()==id)
				return true;
		}
		return false;
	}
	/* common methods */
	
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(list.size());
		for(int i=0;i<list.size();i++)
		{
			data.writeInt((Integer)list.get(i));
		}
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Serializable#bytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void bytesRead(ByteBuffer data)
	{
		int len=data.readInt();
		list=new ArrayList(len);
		for(int i=0;i<len;i++)
		{
			list.add(data.readInt());
		}
		
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.DBSerializable#dbBytesWrite(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(list.size());
		for(int i=0;i<list.size();i++)
		{
			data.writeInt((Integer)list.get(i));
		}
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.DBSerializable#dbBytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void dbBytesRead(ByteBuffer data)
	{
		int len=data.readInt();
		list=new ArrayList(len);
		for(int i=0;i<len;i++)
		{
			list.add(data.readInt());
		}
	}
	/* inner class */
}
