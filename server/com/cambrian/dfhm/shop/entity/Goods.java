package com.cambrian.dfhm.shop.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;

/**
 * 商品
 * 
 * @author Zmk
 */
public class Goods extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 商品名字 */
	public String name;
	/** 商品描述 */
	public String description;
	/** 商品图片路径 */
	public String img;
	/** 商品动画路径 */
	public String animation;
	/** 消费的金币类型 */
	public int moneyType;
	/** 价格 */
	public int price;
	/** 价格折率 */
	public int rate;
	/** 金币 */
	public int gold;
	/** 银币 */
	public int money;
	/** 武魂 */
	public int soul;
	/** 军令 */
	public int token;
	/** 卡牌(sid) */
	public int[] cardSid;

	/* constructors */
	
	/* properties */

	/* init start */

	/* methods */
	
	/** 序列化 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(price);
		data.writeInt(rate);
	}
	
	/* common methods */

	/* inner class */

}