package com.cambrian.dfhm.shop;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;

/**
 * 类说明：商品
 * 
 * @version 1.1
 * @author maxw<woldits@qq.com>
 */
public class Goods extends Sample
{

	/* static fields */
	/** 商品类型 (道具,功能处理) */
	public static final int PROP=1,PROCESS=2;
	/** 货币类型常量 (金条,礼卷,游戏币,功勋) */
	public static final int GOLD=1,MONEY=2,GIFT=4,EXPLOIT=8;

	/* static methods */

	/* fields */
	/** 商品类型 */
	public int type;
	/** 包含商品id */
	public int pid;
	/** 货币类型 */
	public int moneytype;
	/** 价格 */
	public int price;
	/** 价格折率 */
	public int rate;

	/* constructors */
	
	/* properties */

	/* init start */

	/* methods */
	/** 获取打折后的价格 */
	public int getPrice()
	{
		return price*rate/100;
	}
	/** 序列化 */
	public void bytesWrite(ByteBuffer data)
	{
		super.bytesWrite(data);
		data.writeInt(type);
		data.writeInt(pid);
		data.writeInt(moneytype);
		data.writeInt(price);
		data.writeInt(rate);
	}
	
	/* common methods */

	/* inner class */

}