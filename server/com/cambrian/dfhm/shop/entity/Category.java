package com.cambrian.dfhm.shop.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.StringKit;

/**
 * 商城列表类
 * 
 * @author Zmk
 */
public class Category extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 商品列表 */
	Goods[] goods;
	/** 前台用页卡标识 */
	int shopType;
	/* constructors */

	/* properties */

	/* init start */
	
	/* methods */
	/**  设置商品列表 */
	public void setGoods(String str)
	{
		int[] values=StringKit.parseInts(str);
		goods=new Goods[values.length];
		for(int i=0;i<goods.length;i++)
		{
			goods[i]=(Goods)Sample.factory.getSample(values[i]);
			if(goods[i]==null)
				throw new DataAccessException(500,"goods["+values[i]+"]=null");
		}
	}
	
	/* common methods */
	/** 序列化 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(goods.length);
		for(int i=0;i<goods.length;i++)
		{
			goods[i].bytesWrite(data);
		}
	}
	/* inner class */
}