package com.cambrian.dfhm.task.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.card.Card;
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
public class Task extends Sample
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
	int status=0;
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
		if (status == UNTAKE || status == FINISHED) return false;
		if (!finishIsAchieve(player)) return false;
		this.status = FINISH;
		return true;
	}
	/**  */
	private boolean finishIsAchieve(Player player)
	{
		for (Integer integer : getCard)
		{
			if (integer != 0)
			{
				if (!player.getCardBag().isContain(integer))
					return false;
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
					&& player.getCurIndexForHardNPC() <= sid)
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
		if (!checkFinish(player)) return false;
		this.status = FINISHED;
		this.dispense(player);
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

	/** ���佱�� 
	 * @return */
	public Card dispense(Player player)
	{
		TaskAward award = (TaskAward)Sample.getFactory().getSample(awardSid);
		player.incrGold(award.gold);
		player.incrMoney(award.money);
		player.incrSoul(award.soul);
		player.incrToken(award.token);
		player.getPlayerInfo().incrNormalPoint(award.point);
		return dispenseCard(player, award);
	}
	/** ���俨�� 
	 * @return */
	private Card dispenseCard(Player player, TaskAward award)
	{
		if (award.card.length > 0)
		{
			int random = MathKit.randomValue(0, award.card[award.card.length-1]);
			for (int i = 0; i < award.card.length; i+=2)
			{
				if (random < award.card[i+1])
				{
					Card card = player.getCardBag().add(award.card[i]);
					return card;
				}	
			}
		}
		return null;
	}
	/** ����������� */

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
		super.dbBytesWrite(data);
		data.writeInt(status);
	}

	@Override
	public void dbBytesRead(ByteBuffer data)
	{
		super.dbBytesRead(data);
		this.status = data.readInt();
	}

}