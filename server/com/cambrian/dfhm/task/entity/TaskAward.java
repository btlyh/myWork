package com.cambrian.dfhm.task.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;

/**
 * ��˵����������
 * @author��Zmk
 * 
 */
public class TaskAward extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��� */
	public int gold;
	/** ���� */
	public int money;
	/** ��� */
	public int soul;
	/** ���� */
	public int token;
	/** ���� */
	public int point;
	/** ���� */
	public int[] card;
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** ���л� ǰ̨ д */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(gold);
		data.writeInt(money);
		data.writeInt(soul);
		data.writeInt(token);
		data.writeInt(point);
	}
}
