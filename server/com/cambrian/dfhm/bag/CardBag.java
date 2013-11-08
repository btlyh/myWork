package com.cambrian.dfhm.bag;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.DBSerializable;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.Serializable;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.UidFile;

/**
 * ��˵������Ƭ�б�
 * 
 * @version 1.0
 * @date 2013-5-30
 * @author maxw<woldits@qq.com>
 */
public class CardBag implements Serializable,DBSerializable
{

	/* static fields */
	/** ��ʼ���� */
	public static final int INITIAL_CAPACITY=20;

	/* static methods */

	/* fields */
	/** ���� */
	int capacity=INITIAL_CAPACITY;
	/** ��Ƭ�б� */
	ArrayList<Card> list=new ArrayList<Card>();

	/* constructors */

	/* properties */
	public int getCapacity()
	{
		return capacity;
	}

	public ArrayList<Card> getList()
	{
		return list;
	}

	/* init start */

	/* methods */
	public void inCapacity(int size)
	{
		capacity += size;
	}

	/** ���ʣ������ */
	public int getSurplusCapacity()
	{
		return capacity-list.size();
	}

	/** ��ӿ�Ƭ */
	public synchronized Card add(int sid)
	{
		Card card=(Card)Sample.factory.newSample(sid);
		card.init();
		card.uid = UidFile.getCardFile().getPlusUid();
		list.add(card);
		return card;
	}

	/** �Ƴ���Ƭ */
	public synchronized void remove(int uid)
	{
		for(Card card:list)
		{
			if(card.getId()==uid)
			{
				list.remove(card);
				return;
			}
		}
	}

	/** ���ָ���Ŀ�Ƭ */
	public Card getById(int uid)
	{
		for(Card card:list)
		{
			if(card.getId()==uid) return card;
		}
		return null;
	}

	/* common methods */

	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(capacity);
		int n=0;
		int top=data.top();
		data.writeInt(n);
		System.err.println("list.size() !!!!=="+list.size());
		for(int i=0;i<list.size();i++)
		{
			Card card=(Card)list.get(i);
			if(card==null) continue;
			card.bytesWrite(data);
			n++;
		}
		int top_=data.top();
		data.setTop(top);
		data.writeInt(n);
		data.setTop(top_);
	}

	public void bytesRead(ByteBuffer data)
	{
		this.capacity=data.readInt();
		int len=data.readInt();
		list=new ArrayList<Card>(len);
		for(int i=0;i<list.size();i++)
		{
			int sid=data.readUnsignedShort();
			Card card=(Card)Sample.factory.newSample(sid);
			card.bytesRead(data);
			list.add(card);
		}
	}

	/** ���л�(��dcͨ��) */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(capacity);
		int n=0;
		int top=data.top();
		data.writeInt(n);
		for(int i=0;i<list.size();i++)
		{
			Card card=(Card)list.get(i);
			if(card==null) continue;
			card.dbBytesWrite(data);
			n++;
			card.bytesWrite_db(data);
		}
		int top_=data.top();
		data.setTop(top);
		data.writeInt(n);
		data.setTop(top_);
	}

	/** �����л�(��dcͨ��) */
	public void dbBytesRead(ByteBuffer data)
	{
		this.capacity=data.readInt();
		int len=data.readInt();
		list=new ArrayList<Card>(capacity);
		for(int i=0;i<len;i++)
		{
			int sid=data.readUnsignedShort();
			Card card=(Card)Sample.factory.newSample(sid);
			card.dbBytesRead(data);
			card.bytesRead_db(data);
			list.add(card);
		}
	}
	/* inner class */
}
