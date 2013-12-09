package com.cambrian.dfhm.task.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.CrossNPC;
import com.cambrian.dfhm.instancing.entity.HardNPC;
import com.cambrian.dfhm.instancing.entity.NormalNPC;

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
				}
			}
		}
		if (player.getSoul() < getSoul)
			return false;
		if (player.getMoney() < getMoney)
			return false;
		if (player.getPlayerInfo().getPayRMB() < payRmb)
			return false;
		if (player.getPlayerInfo().getInstancingCount() < instancingCount)
			return false;
		if (player.getPlayerInfo().getQualifyingCount() < qualifyingCount)
			return false;
		if (player.getPlayerInfo().getQualifyingWin() < qualifyingWin)
			return false;
		if (!checkInstancing(instancingSid, player))
			return false;
		
		return true;
	}
	/** ��鸱������ */
	private boolean checkInstancing(int sid, Player player)
	{
		Sample sample = Sample.getFactory().getSample(sid);
		if (sample != null)
		{
			if (sample instanceof NormalNPC
					&& player.getCurIndexForNormalNPC() <= sid)
				return false;
			if (sample instanceof HardNPC
					&& !player.getPlayerInfo().getHardNPCList().contains(sid))
				return false;
			if (sample instanceof CrossNPC
					&& player.getCurIndexForCorssNPC() <= sid)
				return false;
		}
		return true;
	}
	/** ������� */
	public boolean finish(Player player)
	{
		if (this.status == FINISHED	) return false;
		//if (!checkFinish(player)) return false;
		this.status = FINISHED;
		player.getTasks().taskList.remove(this);
		player.getTasks().finishedTaskList.add(this);
		return true;
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
		if (!checkInstancing(needRaidSid, player))
			return false;
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
		return true;
	}

	@Override
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(getSid());
		data.writeInt(status);
		System.err.println("ǰ̨���л�����---------SID===="+getSid()+"   satus===="+status);
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
		return this.getSid() - o.getSid();
	}

}
