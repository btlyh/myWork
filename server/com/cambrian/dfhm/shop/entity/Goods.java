package com.cambrian.dfhm.shop.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;

/**
 * ��Ʒ
 * 
 * @author Zmk
 */
public class Goods extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��Ʒ���� */
	public String name;
	/** ��Ʒ���� */
	public String description;
	/** ��ƷͼƬ·�� */
	public String img;
	/** ��Ʒ����·�� */
	public String animation;
	/** ���ѵĽ������ */
	public int moneyType;
	/** �۸� */
	public int price;
	/** �۸����� */
	public int rate;
	/** ��� */
	public int gold;
	/** ���� */
	public int money;
	/** ��� */
	public int soul;
	/** ���� */
	public int token;
	/** ����(sid) */
	public int[] cardSid;

	/* constructors */
	
	/* properties */

	/* init start */

	/* methods */
	
	/** ���л� */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(price);
		data.writeInt(rate);
	}
	
	/* common methods */

	/* inner class */

}