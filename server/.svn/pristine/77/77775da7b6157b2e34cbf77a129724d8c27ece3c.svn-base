package com.cambrian.dfhm.shop.entity;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;

/**
 * 商城
 * 
 * @author Zmk
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
		ArrayList<Sample> arrayList=new ArrayList<Sample>();
		Sample[] samples=Sample.factory.getSamples();
		for(int i=0;i<samples.length;i++)
		{
			if(samples[i]==null) continue;
			if(samples[i] instanceof Category)
			{
				arrayList.add(samples[i]);
			}
		}
		categories=new Category[arrayList.size()];
		arrayList.toArray(categories);
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
//	public void bytesWrite(ByteBuffer data)
//	{
//		data.writeInt(categories.length);
//		for(int i=0;i<categories.length;i++)
//		{
//			categories[i].bytesWrite(data);
//		}
//	}
	
	/* inner class */
}