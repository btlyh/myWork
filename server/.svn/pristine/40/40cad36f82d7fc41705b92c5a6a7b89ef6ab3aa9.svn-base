package com.cambrian.dfhm.common.entity;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.card.Card;

/**
 * 类说明：用户信息类
 * 
 * @author：zmk
 */
public class PlayerInfo
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 技能免费刷新次数 */
	private int skillFlushFreeTimes;
	/** 抽奖SID */
	private int luckBoxSid=39051;
	/** 免费抽奖次数 */
	private int luckBoxFreeTimes=3;
	/** 上次抽奖时间 */
	private long lastTime;
	/** 卡牌抽取记录集合 */
	private ArrayList<TakeCardRecord> takeCardRecords=new ArrayList<TakeCardRecord>();
	/** 付费抽取的次数 */
	private int payTimes;
	/** 可以购买的卡牌SID */
	private int bestCardSid;
	/** 积分 */
	private int normalPoint;
	/** 是否第一次抽取*/
	private boolean isFirst;
	/** 总充值金额 */
	private int payRMB = 0;
	/** 故事模式副本时间 */
	private int normalNPCTime = 0;
	/** 挑战模式副本时间 */
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

	/** 序列化 和前台通信 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(skillFlushFreeTimes);
		data.writeInt(normalPoint);
		data.writeInt(luckBoxFreeTimes);
		data.writeInt(payTimes);
	}

	/** 序列化 和DC通信 存 */
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
	/** 序列化 和DC通信 取 */
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
	 * 设置卡牌位置
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
	 * 获取最好的卡牌
	 * 
	 * @param cards 卡组
	 * @return 返回最好卡牌最好SID
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
