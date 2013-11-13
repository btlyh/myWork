package com.cambrian.dfhm.common.entity;

import java.util.ArrayList;
import java.util.List;

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
	/** 每日免费领取军令数量 */
	private int getToken;
	/** 每日可购买军令数量 */
	private int buyToken;
	/** 每日武魂抽奖免费次数 */
	private int luckBoxFreeTimes;
	/** 每日刷新技能免费次数 */
	private int skillFlushFreeTimes;
	/** 免费金币培养次数 */
	private int cardGoldForsterFreeTimes;
	/* 功能 */
	/** 卡牌金币培养功能 */
	private int cardGoldForster;
	/** 战斗中使用跳过功能 */
	private int battleSkip;
	/** 付费醒酒功能 */
	private int payForAwake;
	/** 自动喝酒功能 */
	private int autoDrink;
	/** 自动战斗功能 */
	private int autoBossFight;
	/** 自动报名 */
	private int autoBossSign;
	
	/** 抽奖SID */
	private int luckBoxSid=39051;
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

	public int getGetToken()
	{
		return getToken;
	}

	public void setGetToken(int getToken)
	{
		this.getToken = getToken;
	}

	public int getBuyToken()
	{
		return buyToken;
	}

	public void setBuyToken(int buyToken)
	{
		this.buyToken = buyToken;
	}

	public int getCardGoldForsterFreeTimes()
	{
		return cardGoldForsterFreeTimes;
	}

	public void setCardGoldForsterFreeTimes(int cardGoldForsterFreeTimes)
	{
		this.cardGoldForsterFreeTimes = cardGoldForsterFreeTimes;
	}

	public int getCardGoldForster()
	{
		return cardGoldForster;
	}

	public void setCardGoldForster(int cardGoldForster)
	{
		this.cardGoldForster = cardGoldForster;
	}

	public int getBattleSkip()
	{
		return battleSkip;
	}

	public void setBattleSkip(int battleSkip)
	{
		this.battleSkip = battleSkip;
	}

	public int getPayForAwake()
	{
		return payForAwake;
	}

	public void setPayForAwake(int payForAwake)
	{
		this.payForAwake = payForAwake;
	}

	public int getAutoDrink()
	{
		return autoDrink;
	}

	public void setAutoDrink(int autoDrink)
	{
		this.autoDrink = autoDrink;
	}
	public int getAutoBossFight()
	{
		return autoBossFight;
	}

	public void setAutoBossFight(int autoBossFight)
	{
		this.autoBossFight = autoBossFight;
	}

	public int getAutoBossSign()
	{
		return autoBossSign;
	}

	public void setAutoBossSign(int autoBossSign)
	{
		this.autoBossSign = autoBossSign;
	}
	/** 序列化 和前台通信 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(skillFlushFreeTimes);
		data.writeInt(normalPoint);
		data.writeInt(luckBoxFreeTimes);
		data.writeInt(payTimes);
		data.writeInt(getToken);
		data.writeInt(buyToken);
		data.writeInt(cardGoldForsterFreeTimes);
		data.writeInt(cardGoldForster);
		data.writeInt(battleSkip);
		data.writeInt(payForAwake);
		data.writeInt(autoDrink);
		data.writeInt(autoBossFight);
		data.writeInt(autoBossSign);
//		data.writeInt(duelFreeTimes);
//		data.writeInt(duelBuyTimes);
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
		data.writeInt(getToken);
		data.writeInt(buyToken);
		data.writeInt(cardGoldForsterFreeTimes);
		data.writeInt(cardGoldForster);
		data.writeInt(battleSkip);
		data.writeInt(payForAwake);
		data.writeInt(autoDrink);
		data.writeInt(autoBossFight);
		data.writeInt(autoBossSign);
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
		getToken=data.readInt();
		buyToken=data.readInt();
		cardGoldForsterFreeTimes=data.readInt();
		cardGoldForster=data.readInt();
		battleSkip=data.readInt();
		payForAwake=data.readInt();
		autoDrink=data.readInt();
		autoBossFight=data.readInt();
		autoBossSign=data.readInt();
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
