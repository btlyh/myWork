package com.cambrian.dfhm.common.entity;

import java.util.ArrayList;
import java.util.List;

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
	/** ÿ�������ȡ�������� */
	private int getToken;
	/** ÿ�տɹ���������� */
	private int buyToken;
	/** ÿ�����齱��Ѵ��� */
	private int luckBoxFreeTimes;
	/** ÿ��ˢ�¼�����Ѵ��� */
	private int skillFlushFreeTimes;
	/** ��ѽ���������� */
	private int cardGoldForsterFreeTimes;
	/* ���� */
	/** ���ƽ���������� */
	private int cardGoldForster;
	/** ս����ʹ���������� */
	private int battleSkip;
	/** �� ���Ѿƹ��� */
	private int payForAwake;
	/** �Զ��Ⱦƹ��� */
	private int autoDrink;
	/** �Զ�ս������ */
	private int autoBossFight;
	/** �Զ����� */
	private int autoBossSign;

	/** �Ƿ��Ѿ��Զ����� */
	private boolean isAutoSignBoss;

	/** �齱SID */
	private int luckBoxSid=19921;
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
	/** �Ƿ��һ�γ�ȡ */
	private boolean isFirst;
	/** �ܳ�ֵ��� */
	private int payRMB=0;
	/** ����ģʽ����ʱ�� */
	private int normalNPCTime=0;
	/** ��ս�˵�NPC */
	private int hardNPCIndex=-1;
	/** ��սģʽ����ʱ�� */
	private int hardNPCTime=0;

	/** ��һ�ο��콱��ID */
	private int rewardId;

	/** ��һ���콱ʱ�� */
	private long nextRewardTime;

	/** �����Ѿ��콱���� ��ʼ��Ϊ0 */
	private int rewardNum=0;

	/** �����ս���� */
	private int duelFreeTimes=10;
	/** �Ѿ��������ս���� */
	private int duelBuyTimes=0;
	/** �Ƿ������ȡ��λ������ */
	private int canTakePoint=0;
	/** ��λ����ս��ʷ��¼ */
	private List<String> qualifyingLog=new ArrayList<String>();
	/** �����б� */
	private List<String> enemyList=new ArrayList<String>();

	/** �������� */
	private int instancingCount;
	/** ��λ������ */
	private int qualifyingCount;
	/** ��λ��ʤ������ */
	private int qualifyingWin;
	/** �Ƿ��Ѿ��������ÿ��� */
	private boolean isBuyed;

	private int nextRewardId=21657;// ��һ�ε��콱ID ����ǰ̨��ʼ���콱��Ϣ

	private int crossMapNum = 10;//���մ�Խ������ս����
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
		this.isFirst=isFirst;
	}
	public int getPayRMB()
	{
		return payRMB;
	}

	public void setPayRMB(int payRMB)
	{
		this.payRMB=payRMB;
	}

	public int getNormalNPCTime()
	{
		return normalNPCTime;
	}

	public void setNormalNPCTime(int normalNPCTime)
	{
		this.normalNPCTime=normalNPCTime;
	}

	public int getHardNPCTime()
	{
		return hardNPCTime;
	}

	public void setHardNPCTime(int hardNPCTime)
	{
		this.hardNPCTime=hardNPCTime;
	}
	/* init start */

	/* methods */

	public int getGetToken()
	{
		return getToken;
	}

	public void setGetToken(int getToken)
	{
		this.getToken=getToken;
	}

	public int getBuyToken()
	{
		return buyToken;
	}

	public void setBuyToken(int buyToken)
	{
		this.buyToken=buyToken;
	}

	public int getCardGoldForsterFreeTimes()
	{
		return cardGoldForsterFreeTimes;
	}

	public void setCardGoldForsterFreeTimes(int cardGoldForsterFreeTimes)
	{
		this.cardGoldForsterFreeTimes=cardGoldForsterFreeTimes;
	}

	public int getCardGoldForster()
	{
		return cardGoldForster;
	}

	public void setCardGoldForster(int cardGoldForster)
	{
		this.cardGoldForster=cardGoldForster;
	}

	public int getBattleSkip()
	{
		return battleSkip;
	}

	public void setBattleSkip(int battleSkip)
	{
		this.battleSkip=battleSkip;
	}

	public int getPayForAwake()
	{
		return payForAwake;
	}

	public void setPayForAwake(int payForAwake)
	{
		this.payForAwake=payForAwake;
	}

	public int getAutoDrink()
	{
		return autoDrink;
	}

	public void setAutoDrink(int autoDrink)
	{
		this.autoDrink=autoDrink;
	}

	public int getDuelFreeTimes()
	{
		return duelFreeTimes;
	}

	public void setDuelFreeTimes(int duelFreeTimes)
	{
		this.duelFreeTimes=duelFreeTimes;
	}

	public int getDuelBuyTimes()
	{
		return duelBuyTimes;
	}

	public void setDuelBuyTimes(int duelBuyTimes)
	{
		this.duelBuyTimes=duelBuyTimes;
	}

	public int getCanTakePoint()
	{
		return canTakePoint;
	}

	public void setCanTakePoint(int canTakePoint)
	{
		this.canTakePoint=canTakePoint;
	}

	public List<String> getQualifyingLog()
	{
		return qualifyingLog;
	}

	public void setQualifyingLog(List<String> qualifyingLog)
	{
		this.qualifyingLog=qualifyingLog;
	}

	public List<String> getEnemyList()
	{
		return enemyList;
	}

	public void setEnemyList(List<String> enemyList)
	{
		this.enemyList=enemyList;
	}

	public int getAutoBossFight()
	{
		return autoBossFight;
	}

	public void setAutoBossFight(int autoBossFight)
	{
		this.autoBossFight=autoBossFight;
	}

	public int getAutoBossSign()
	{
		return autoBossSign;
	}

	public void setAutoBossSign(int autoBossSign)
	{
		this.autoBossSign=autoBossSign;
	}

	public int getRewardId()
	{
		return rewardId;
	}

	public void setRewardId(int rewardId)
	{
		this.rewardId=rewardId;
	}

	public long getNextRewardTime()
	{
		return nextRewardTime;
	}

	public void setNextRewardTime(long nextRewardTime)
	{
		this.nextRewardTime=nextRewardTime;
	}

	public int getRewardNum()
	{
		return rewardNum;
	}

	public void setRewardNum(int rewardNum)
	{
		this.rewardNum=rewardNum;
	}

	public int getInstancingCount()
	{
		return instancingCount;
	}

	public void setInstancingCount(int instancingCount)
	{
		this.instancingCount=instancingCount;
	}

	public int getQualifyingCount()
	{
		return qualifyingCount;
	}

	public void setQualifyingCount(int qualifyingCount)
	{
		this.qualifyingCount=qualifyingCount;
	}

	public int getQualifyingWin()
	{
		return qualifyingWin;
	}

	public void setQualifyingWin(int qualifyingWin)
	{
		this.qualifyingWin=qualifyingWin;
	}

	public boolean isAutoSignBoss()
	{
		return isAutoSignBoss;
	}

	public int getNextRewardId()
	{
		return nextRewardId;
	}

	public void setNextRewardId(int nextRewardId)
	{
		this.nextRewardId=nextRewardId;
	}

	public int getHardNPCIndex()
	{
		return hardNPCIndex;
	}

	public void setHardNPCIndex(int hardNPCIndex)
	{
		if (hardNPCIndex > this.hardNPCIndex)
			this.hardNPCIndex = hardNPCIndex;
	}

	public void setAutoSignBoss(boolean isAutoSignBoss)
	{
		this.isAutoSignBoss=isAutoSignBoss;
	}
	public boolean isBuyed()
	{
		return isBuyed;
	}

	public void setBuyed(boolean isBuyed)
	{
		this.isBuyed=isBuyed;
	}
	
	
	public int getCrossMapNum() {
		return crossMapNum;
	}

	public void setCrossMapNum(int crossMapNum) {
		this.crossMapNum = crossMapNum;
	}

	/** ���л� ��ǰ̨ͨ�� */
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
		data.writeInt(duelFreeTimes);
		data.writeInt(duelBuyTimes);
		data.writeInt(nextRewardId);
		data.writeInt(crossMapNum);
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
		data.writeInt(getToken);
		data.writeInt(buyToken);
		data.writeInt(cardGoldForsterFreeTimes);
		data.writeInt(cardGoldForster);
		data.writeInt(battleSkip);
		data.writeInt(payForAwake);
		data.writeInt(autoDrink);
		data.writeInt(autoBossFight);
		data.writeInt(autoBossSign);

		data.writeInt(rewardId);
		data.writeLong(nextRewardTime);
		data.writeInt(rewardNum);
		data.writeInt(duelFreeTimes);
		data.writeInt(duelBuyTimes);
		data.writeInt(canTakePoint);
		data.writeInt(qualifyingLog.size());
		for(String str:qualifyingLog)
		{
			data.writeUTF(str);
		}
		data.writeInt(enemyList.size());
		for(String str:enemyList)
		{
			data.writeUTF(str);
		}
		data.writeInt(instancingCount);
		data.writeInt(qualifyingCount);
		data.writeInt(qualifyingWin);
		data.writeInt(nextRewardId);
		data.writeInt(hardNPCIndex);
		data.writeInt(crossMapNum);
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
		getToken=data.readInt();
		buyToken=data.readInt();
		cardGoldForsterFreeTimes=data.readInt();
		cardGoldForster=data.readInt();
		battleSkip=data.readInt();
		payForAwake=data.readInt();
		autoDrink=data.readInt();
		autoBossFight=data.readInt();
		autoBossSign=data.readInt();

		rewardId=data.readInt();
		nextRewardTime=data.readLong();
		rewardNum=data.readInt();

		duelFreeTimes=data.readInt();
		duelBuyTimes=data.readInt();
		canTakePoint=data.readInt();
		len=data.readInt();
		for(int i=0;i<len;i++)
		{
			String str=data.readUTF();
			qualifyingLog.add(str);
		}
		len=data.readInt();
		for(int i=0;i<len;i++)
		{
			String str=data.readUTF();
			enemyList.add(str);
		}
		instancingCount=data.readInt();
		qualifyingCount=data.readInt();
		qualifyingWin=data.readInt();
		nextRewardId=data.readInt();
		hardNPCIndex = data.readInt();
		crossMapNum = data.readInt();
	
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

	public void decrDuelFreeTimes(int times)
	{
		duelFreeTimes-=times;
	}

	public void incrDuelFreeTimes(int times)
	{
		duelFreeTimes+=times;
	}

	public void incrDuelBuyTimes(int times)
	{
		duelBuyTimes+=times;
	}

	public void incrInstancingCount(int times)
	{
		instancingCount+=times;
	}

	public void incrQualifyingCount(int times)
	{
		qualifyingCount+=times;
	}

	public void incrQualifyingWin(int times)
	{
		qualifyingWin+=times;
	}

	/** �����λ����¼ */
	public void addQualifyingLog(String log)
	{
		if(qualifyingLog.size()>5) qualifyingLog.remove(0);
		qualifyingLog.add(log);
	}

	/** ��ӳ��� */
	public void addEnemy(String playerName)
	{
		if(enemyList.size()>5)
		{
			enemyList.remove(0);
		}
		enemyList.add(playerName);
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
		isBuyed=false;
	}
	/** ���ٻ��� */
	public void decrNormalPoint(int price)
	{
		normalPoint -= price;
	}
}
