package com.cambrian.dfhm.globalboss.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：用于记录战斗的一些数据
 * 
 * @author：Boss战斗记录类
 */
public class BossFightRecord implements Comparable<BossFightRecord>
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 当前BOSS SID */
	private int curBossSid;
	/** 对BOSS造成的总伤害 */
	private int totalDamage;
	/** 当前排名 */
	private int rank;
	/** 攻击强化百分比 */
	private int attUp=1;
	/** 玩家ID */
	private int playerId;
	/** 玩家姓名 */
	private String nickName;
	/** 上次攻击BOSS时间 */
	private long lastAttTime;
	/** 是否终结BOSS */
	private boolean isFinished;
	/** 是否自动战斗 */
	private boolean isAuto;

	/* constructors */
	/** 初始化BOSS战斗记录 */
	public BossFightRecord(String nickName,int playerId,int bossSid)
	{
		this.nickName=nickName;
		this.playerId=playerId;
		this.curBossSid=bossSid;
	}

	/* properties */
	public int getTotalDamage()
	{
		return totalDamage;
	}

	public int getRank()
	{
		return rank;
	}

	public void setRank(int rank)
	{
		this.rank=rank;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName=nickName;
	}

	public int getAttUp()
	{
		return attUp;
	}

	public void setAttUp(int attUp)
	{
		this.attUp=attUp;
	}

	public long getLastAttTime()
	{
		return lastAttTime;
	}

	public void setLastAttTime(long lastAttTime)
	{
		this.lastAttTime=lastAttTime;
	}

	public boolean isFinished()
	{
		return isFinished;
	}

	public void setFinished(boolean isFinished)
	{
		this.isFinished=isFinished;
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(int playerId)
	{
		this.playerId=playerId;
	}
	public int getCurBossSid()
	{
		return curBossSid;
	}
	public void setCurBossSid(int curBossSid)
	{
		this.curBossSid=curBossSid;
	}
	public boolean isAuto()
	{
		return isAuto;
	}

	public void setAuto(boolean isAuto)
	{
		this.isAuto=isAuto;
	}
	/* init start */

	/* methods */
	/**
	 * 实现comparable接口重写这个类的排序方法
	 */
	public int compareTo(BossFightRecord bossFightRecord)
	{
		int i=this.totalDamage;
		int j=bossFightRecord.getTotalDamage();
		return ((i==j)?0:(i>j)?-1:1);
	}

	/** 序列化 前台接收邮件数据 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(rank);
		data.writeUTF(nickName);
	}

	/** 增加强化次数 */
	public void inAttUp()
	{
		attUp++;
	}

	/** 记录伤害 */
	public void recordDamage(int damage)
	{
		totalDamage+=damage;
	}
}
