package com.cambrian.dfhm.task.entity;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.qualifying.logic.QualifyingManager;

/**
 * ��˵�������������
 * 
 * @author��Zmk
 * 
 */
public class Task extends Sample implements Comparable<Task>
{

	/* static fields */
	/** ����״̬��0��δ��ȡ��1�������У�2����� ,3�������*/
	public static final int UNTAKE = 0, TAKED = 1, FINISH = 2, FINISHED=3;
	/** �Ƿ����ճ�����0�������ճ���1�����ճ� */
	public static final int NORMAL = 0, DAYLY = 1;
	/* static methods */

	/* fields */
	/** ���� */
	String name;
	/** ���� */
	String description;
	/** ͼƬ */
	String img;
	/** ���� */
	int type;
	/** �������� */
	int kind;
	/** ״̬ */
	public int status=0;
	/** �Ƿ����ճ� */
	int isDayly;

	/* ��ȡ���� */
	/** ǰ������Sid */
	int needBeforeSid;
	/** ͨ��ָ������ */
	int needRaidSid;
	/** ��ҪVIP�ȼ� */
	int needVipLevel;

	/* ������� */
	/** ���ָ������ */
	int[] getCard;
	/** ���ָ��������� */
	int getSoul;
	/** ���ָ���������� */
	int getMoney;
	/** ��ֵָ����� */
	int payRmb;
	/** �������� */
	int instancingCount;
	/** ��λ������ */
	int qualifyingCount;
	/** ��λ��ʤ������ */
	int qualifyingWin;
	/** ��λ������ */
	int qualifyingRank;
	/** ͨ��ָ������ */
	int instancingSid;

	/** ������SID */
	public int awardSid;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** ��������Ƿ���� */
	public boolean checkFinish(Player player)
	{
		if (status != TAKED) return false;
		if (!finishIsAchieve(player)) return false;
		this.status = FINISH;
		return true;
	}
	/** �Ƿ���������������� */
	private boolean finishIsAchieve(Player player)
	{
		if (getCard != null)
		{
			for (Integer integer : getCard)
			{
				if (integer != 0)
				{
					if (!player.getCardBag().isContain(integer))
						return false;
					else
						break;
				}
			}
		}
		if (player.getSoul() < getSoul)
			return false;
		if (player.getMoney() < getMoney)
			return false;
		if (player.getPlayerInfo().getPayRMB() < payRmb)
			return false;
		if (instancingSid != 0 && instancingCount != 0)
		{
			if (!player.getPlayerInfo().checkInstancingCount(instancingSid,instancingCount))
				return false;
		}
		if (player.getPlayerInfo().getQualifyingCount() < qualifyingCount)
			return false;
		if (player.getPlayerInfo().getQualifyingWin() < qualifyingWin)
			return false;
		if (qualifyingRank != 0)
		{
			if (QualifyingManager.getInstance().getPlayerRank(player.getNickname()) > qualifyingRank)
				return false;
		}
		return true;
	}
	/** ������� */
	public ArrayList<Integer> finish(Player player)
	{
		this.status = FINISHED;
		player.getTasks().taskList.remove(this);
		player.getTasks().daylyTaskList.remove(this);
		player.getTasks().finishedTaskList.add(this);
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		if (getCard!=null && getCard[0] != 0)
		{
			removeList = getRemoveCard(getCard, player);
		}
		return removeList;
	}
	/** �����Ҫ�Ƴ��Ŀ��� 
	 * @param player */
	private ArrayList<Integer> getRemoveCard(int[] getCard, Player player)
	{
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		for (int i = 0; i < getCard.length; i++)
		{
			Card card = player.getCardBag().getBySid(getCard[i]);
			if (card != null)
				removeList.add(card.getId());
		}
		return removeList;
	}
	/** ����Ƿ����ȡ���� */
	public boolean checkTake(Player player)
	{
		if (this.status != UNTAKE)
			return false;
		if (player.getTasks().isContain(getSid()))
			return false;
		if (!takeIsAchieve(player))
			return false;
		return true;
	}

	/** �Ƿ�ﵽ��ȡ���� */
	private boolean takeIsAchieve(Player player)
	{
		if (needBeforeSid != 0)
		{
			Task task = player.getTasks().getFinishTask(needBeforeSid);
			if (task == null)
				return false;
			if (!task.isFinish())
				return false;
		}
		if (needRaidSid != 0)
		{
			if (!player.getPlayerInfo().checkInstancingCount(needRaidSid, 1))
				return false;
		}
		if (player.getVipLevel() < needVipLevel)
			return false;
		return true;
	}

	/** �����Ƿ��Ѿ���� */
	private boolean isFinish()
	{
		return status == FINISHED;
	}

	/** ��ȡ���� */
	public boolean take(Player player)
	{
		if (!checkTake(player)) return false;
		this.status = TAKED;
		if(this.instancingCount>1)
			this.instancingCount += player.getPlayerInfo().getInsCount(this.instancingSid);
		return true;
	}

	@Override
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(getSid());
		data.writeInt(status);
		System.err.println("ǰ̨���л�����---------SID===="+getSid()+"   satus===="+status +"     isdayli====="+isDayly);
	}

	@Override
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(getSid());
		data.writeInt(status);
	}

	@Override
	public void dbBytesRead(ByteBuffer data)
	{
		this.status = data.readInt();
	}
	
	public int compareTo(Task o)
	{
		return this.instancingSid - o.instancingSid;
	}
}
