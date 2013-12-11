package com.cambrian.dfhm.bag;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.DBSerializable;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.Serializable;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.UidFile;
import com.cambrian.dfhm.common.entity.Achievements;

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
	public synchronized Card add(int sid,Achievements achievements)
	{
		Card card=(Card)Sample.factory.newSample(sid);
		Card card_ = new Card();
		card_.init(card);
		card_.uid = UidFile.getCardFile().getPlusUid();
		list.add(card_);
		
		if (!achievements.isBuleCard())
		{
			if (card_.getType()==5||card_.getType()==6) {
				achievements.setBuleCard(true);
			}
			
		}
		
		return card_;
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
	
	/** �Ƿ�������� */
	public boolean isContain(int sid)
	{
		for (Card card : list)
		{
			if (card.getSid() == sid)
				return true;
		}
		return false;
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
	/** �����ǿ���� */
	public int getBestCardSid()
	{
		Card bestCard = null;
		for (Card card : list)
		{
			if (card == null) continue;
			if (bestCard == null)
			{
				bestCard = card;
				continue;
			}
			if (card.getZhandouli() > bestCard.getZhandouli())
				bestCard = card;
		}
		if (bestCard != null)
			return bestCard.getSid();
		else
			return 10001;
	}
	/** �����ã���ñ��ռ��Ŀ��� */
	public Card getBySid(int sid)
	{
		for (Card reCard : list)
		{
			if (reCard.getSid() == sid)
				return reCard;
		}
		return null;
	}
}
