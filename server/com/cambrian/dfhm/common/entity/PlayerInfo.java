package com.cambrian.dfhm.common.entity;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.card.Card;

/**
 * ��˵�����û���Ϣ��
 * 
 * @author��zmk
 */
public class PlayerInfo
{

	/* static fields */

	/* static methods */

	/* fields */
	/** �������ˢ�´��� */
	private int skillFlushFreeTimes;
	/** �齱SID */
	private int luckBoxSid=39051;
	/** ��ѳ齱���� */
	private int luckBoxFreeTimes=3;
	/** �ϴγ齱ʱ�� */
	private long lastTime;
	/** ���Ƴ�ȡ��¼���� */
	private ArrayList<TakeCardRecord> takeCardRecords=new ArrayList<TakeCardRecord>();
	/** ���ѳ�ȡ�Ĵ��� */
	private int payTimes;
	/** ���Թ���Ŀ���SID */
	private int bestCardSid;
	/** ���� */
	private int normalPoint;
	/** �Ƿ��һ�γ�ȡ*/
	private boolean isFirst;
	/** �ܳ�ֵ��� */
	private int payRMB = 0;
	/** ����ģʽ����ʱ�� */
	private int normalNPCTime = 0;
	/** ��սģʽ����ʱ�� */
	private int hardNPCTime = 0;
	/* constructors */

	/* properties */
	public int getSkillFlushFreeTimes()
	{
		return skillFlushFreeTimes;
	}

	public void setSkillFlushFreeTimes(int skillFlushFreeTimes)
	{
		this.skillFlushFreeTimes=skillFlushFreeTimes;
	}

	public int getLuckBoxSid()
	{
		return luckBoxSid;
	}

	public void setLuckBoxSid(int luckBoxSid)
	{
		this.luckBoxSid=luckBoxSid;
	}

	public int getLuckBoxFreeTimes()
	{
		return luckBoxFreeTimes;
	}

	public void setLuckBoxFreeTimes(int luckBoxFreeTimes)
	{
		this.luckBoxFreeTimes=luckBoxFreeTimes;
	}

	public long getLastTime()
	{
		return lastTime;
	}

	public void setLastTime(long lastTime)
	{
		this.lastTime=lastTime;
	}

	public int getPayTimes()
	{
		return payTimes;
	}

	public void setPayTimes(int payTimes)
	{
		this.payTimes=payTimes;
	}
	public int getBestCardSid()
	{
		return bestCardSid;
	}

	public void setBestCardSid(int bestCardSid)
	{
		this.bestCardSid=bestCardSid;
	}

	public ArrayList<TakeCardRecord> getTakeCardRecords()
	{
		return takeCardRecords;
	}

	public void setTakeCardRecords(ArrayList<TakeCardRecord> takeCardRecords)
	{
		this.takeCardRecords=takeCardRecords;
	}
	public int getNormalPoint()
	{
		return normalPoint;
	}

	public void setNormalPoint(int normalPoint)
	{
		this.normalPoint=normalPoint;
	}
	public boolean isFirst()
	
	{
		return isFirst;
	}
	public void setFirst(boolean isFirst)
	{
		this.isFirst = isFirst;
	}
	public int getPayRMB()
	{
		return payRMB;
	}

	public void setPayRMB(int payRMB)
	{
		this.payRMB = payRMB;
	}

	public int getNormalNPCTime()
	{
		return normalNPCTime;
	}

	public void setNormalNPCTime(int normalNPCTime)
	{
		this.normalNPCTime = normalNPCTime;
	}

	public int getHardNPCTime()
	{
		return hardNPCTime;
	}

	public void setHardNPCTime(int hardNPCTime)
	{
		this.hardNPCTime = hardNPCTime;
	}
	/* init start */

	/* methods */

	/** ���л� ��ǰ̨ͨ�� */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(skillFlushFreeTimes);
		data.writeInt(normalPoint);
		data.writeInt(luckBoxFreeTimes);
		data.writeInt(payTimes);
	}

	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(skillFlushFreeTimes);
		data.writeInt(luckBoxSid);
		data.writeInt(luckBoxFreeTimes);
		data.writeLong(lastTime);
		data.writeInt(payTimes);
		data.writeInt(bestCardSid);
		data.writeInt(takeCardRecords.size());
		for(TakeCardRecord cardRecord:takeCardRecords)
		{
			cardRecord.dbBytesWrite(data);
		}
		data.writeInt(normalPoint);
		data.writeBoolean(isFirst);
		data.writeInt(payRMB);
		data.writeInt(normalNPCTime);
		data.writeInt(hardNPCTime);
	}
	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		skillFlushFreeTimes=data.readInt();
		luckBoxSid=data.readInt();
		luckBoxFreeTimes=data.readInt();
		lastTime=data.readLong();
		payTimes=data.readInt();
		bestCardSid=data.readInt();
		int len=data.readInt();
		for(int i=0;i<len;i++)
		{
			TakeCardRecord cardRecord=new TakeCardRecord();
			cardRecord.dbBytesRead(data);
			takeCardRecords.add(cardRecord);
		}
		normalPoint=data.readInt();
		isFirst=data.readBoolean();
		payRMB=data.readInt();
		normalNPCTime=data.readInt();
		hardNPCTime=data.readInt();
	}

	public void decrLuckBoxFreeTimes(int times)
	{
		luckBoxFreeTimes-=times;
	}

	public void inPayTimes(int times)
	{
		payTimes+=times;
	}

	public void incrNormalPoint(int normalPoint)
	{
		this.normalPoint+=normalPoint;

	}
	/**
	 * ���ÿ���λ��
	 * 
	 * @param cardSid
	 */
	public void setIndex(TakeCardRecord cardRecord_,int index)
	{
		for(TakeCardRecord cardRecord:takeCardRecords)
		{
			if(cardRecord.getIndex()==index)
			{
				boolean tempb=cardRecord.isTkae();
				int tempSid=cardRecord.getSid();
				cardRecord.setSid(cardRecord_.getSid());
				cardRecord.setTake(cardRecord_.isTkae());
				cardRecord_.setSid(tempSid);
				cardRecord_.setTake(tempb);
				break;
			}
		}
	}

	/**
	 * ��ȡ��õĿ���
	 * 
	 * @param cards ����
	 * @return ������ÿ������SID
	 */
	public void initBestCardSid()
	{
		int bestCard=0;
		int level=0;
		for(TakeCardRecord cardRecord:takeCardRecords)
		{
			Card card=(Card)Sample.factory.getSample(cardRecord.getSid());
			if(card.getType()>level)
			{
				level=card.getType();
				bestCard=cardRecord.getSid();
			}
		}
		bestCardSid=bestCard;
	}
}
