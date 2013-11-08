package com.cambrian.dfhm.shop;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：商城
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class Shop
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 商品列表 */
	Category[] categories;

	/* constructors */

	/* properties */

	/* init start */
	/** 初始化商城 */
	public void init()
	{
		// TODO Auto-generated method stub
		// TODO 初始化商品列表
		
	}
	/* methods */
	/** 获得指定商品 */
	public Goods getGoods(int sid)
	{
		for(int i=0;i<categories.length;i++)
		{
			for(int j=0;j<categories[i].goods.length;j++)
			{
				if(categories[i].goods[j].getSid()==sid)
					return categories[i].goods[j];
			}
		}
		return null;
	}
	/* common methods */
	/** 序列化 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(categories.length);
		for(int i=0;i<categories.length;i++)
		{
			categories[i].bytesWrite(data);
		}
	}
	
	/* inner class */
}