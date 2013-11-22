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
 * 类说明：任务对象类
 * 
 * @author：Zmk
 * 
 */
public class Task extends Sample
{

	/* static fields */
	/** 任务状态，0：未领取，1：进行中，2：完成 ,3：已完成*/
	public static final int UNTAKE = 0, TAKED = 1, FINISH = 2, FINISHED=3;
	/** 是否是日常任务，0：不是日常，1：是日常 */
	public static final int NORMAL = 0, DAYLY = 1;
	/* static methods */

	/* fields */
	/** 名字 */
	String name;
	/** 描述 */
	String description;
	/** 图片 */
	String img;
	/** 类型 */
	int type;
	/** 任务类型 */
	int kind;
	/** 状态 */
	int status=0;
	/** 是否是日常 */
	int isDayly;

	/* 领取条件 */
	/** 前置任务Sid */
	int needBeforeSid;
	/** 通关指定副本 */
	int needRaidSid;
	/** 需要VIP等级 */
	int needVipLevel;

	/* 完成条件 */
	/** 获得指定卡牌 */
	int[] getCard;
	/** 获得指定数量武魂 */
	int getSoul;
	/** 获得指定数量银币 */
	int getMoney;
	/** 充值指定金币 */
	int payRmb;
	/** 副本次数 */
	int instancingCount;
	/** 排位赛次数 */
	int qualifyingCount;
	/** 排位赛胜利次数 */
	int qualifyingWin;
	/** 通关指定副本 */
	int instancingSid;

	/** 奖励表SID */
	public int awardSid;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** 检查任务是否完成 */
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
	/** 检查副本条件 */
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
	/** 完成任务 */
	public boolean finish(Player player)
	{
		if (this.status == FINISHED	) return false;
		if (!checkFinish(player)) return false;
		this.status = FINISHED;
		this.dispense(player);
		return true;
	}

	/** 检查是否可领取任务 */
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

	/** 是否达到领取条件 */
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

	/** 任务是否已经完成 */
	private boolean isFinish()
	{
		return status == FINISHED;
	}

	/** 领取任务 */
	public boolean take(Player player)
	{
		if (!checkTake(player)) return false;
		this.status = TAKED;
		return true;
	}

	/** 分配奖励 
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
	/** 分配卡牌 
	 * @return */
	private Card dispenseCard(Player player, TaskAward award)
	{
		if (award.card != null && award.card.length > 0)
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
	/** 设置完成条件 */

	@Override
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(getSid());
		data.writeInt(status);
		System.err.println("前台序列化任务---------SID===="+getSid()+"   satus===="+status);
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
