package com.cambrian.dfhm.task.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;

/**
 * 类说明：任务奖励
 * @author：Zmk
 * 
 */
public class TaskAward extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 金币 */
	public int gold;
	/** 银币 */
	public int money;
	/** 武魂 */
	public int soul;
	/** 军令 */
	public int token;
	/** 积分 */
	public int point;
	/** 卡牌 */
	public int[] card;
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** 序列化 前台 写 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(gold);
		data.writeInt(money);
		data.writeInt(soul);
		data.writeInt(token);
		data.writeInt(point);
	}
}
