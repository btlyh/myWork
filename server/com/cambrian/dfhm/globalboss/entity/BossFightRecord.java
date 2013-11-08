package com.cambrian.dfhm.globalboss.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵�������ڼ�¼ս����һЩ����
 * 
 * @author��Bossս����¼��
 */
public class BossFightRecord implements Comparable<BossFightRecord>
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��ǰBOSS SID */
	private int curBossSid;
	/** ��BOSS��ɵ����˺� */
	private int totalDamage;
	/** ��ǰ���� */
	private int rank;
	/** ����ǿ���ٷֱ� */
	private int attUp=1;
	/** ���ID */
	private int playerId;
	/** ������� */
	private String nickName;
	/** �ϴι���BOSSʱ�� */
	private long lastAttTime;
	/** �Ƿ��ս�BOSS */
	private boolean isFinished;

	/* constructors */
	/** ��ʼ��BOSSս����¼ */
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
	/* init start */

	/* methods */
	/**
	 * ʵ��comparable�ӿ���д���������򷽷�
	 */
	public int compareTo(BossFightRecord bossFightRecord)
	{
		int i=this.totalDamage;
		int j=bossFightRecord.getTotalDamage();
		return ((i==j)?0:(i>j)?-1:1);
	}

	/** ���л� ǰ̨�����ʼ����� */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(rank);
		data.writeUTF(nickName);
	}

	/** ����ǿ������ */
	public void inAttUp()
	{
		attUp++;
	}

	/** ��¼�˺� */
	public void recordDamage(int damage)
	{
		totalDamage+=damage;
	}
}
