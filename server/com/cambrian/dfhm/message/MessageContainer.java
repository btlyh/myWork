package com.cambrian.dfhm.message;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.DBSerializable;
import com.cambrian.common.object.Serializable;
import com.cambrian.common.util.ArrayList;

/**
 * ��˵������Ϣ����
 * 
 * @version 1.0
 * @date 2013-6-6
 * @author maxw<woldits@qq.com>
 */
public class MessageContainer implements Serializable,DBSerializable
{
	/* static fields */

	/* static methods */

	/* fields */
	/** �����б�  */
	ArrayList list=new ArrayList();
	
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** ��ʼ�� */
	public void init(Message[] messages) 
	{
		list=new ArrayList(messages);
	}
	/** ��ȡָ����Ϣ */
	public Message getMessage(long uuid)
	{
		for(int i=0;i<list.size();i++)
		{
			Message message=(Message)list.get(i);
			if(message==null) continue;
			if(message.uuid==uuid)
				return message;
		}
		return null;
	}
	
	/** ��� */
	public void addMessage(Message message)
	{
		// TODO Auto-generated method stub

	}
	/** �Ƴ� */
	public Message removeMessage(long uuid)
	{
		for(int i=0;i<list.size();i++)
		{
			Message message=(Message)list.get(i);
			if(message==null) continue;
			if(message.uuid==uuid)
				return (Message)list.remove(i);
		}
		return null;
	}
	/** �Ƴ� */
	public void removeMessages(long[] values)
	{
		for(int i=0;i<values.length;i++)
		{
			removeMessage(values[i]);
		}
	}
	/* common methods */
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Serializable#bytesWrite(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void bytesWrite(ByteBuffer data)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Serializable#bytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void bytesRead(ByteBuffer data)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.DBSerializable#dbBytesWrite(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void dbBytesWrite(ByteBuffer data)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.DBSerializable#dbBytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void dbBytesRead(ByteBuffer data)
	{
		// TODO Auto-generated method stub
		
	}
	
	/* inner class */
}