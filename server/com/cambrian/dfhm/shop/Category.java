package com.cambrian.dfhm.shop;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.StringKit;

/**
 * ��˵�����̳��б���
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class Category extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��Ʒ�б� */
	Goods[] goods;

	/* constructors */

	/* properties */

	/* init start */
	/**  ��ʼ�� */
	public void init()
	{
		
	}
	/** ������� */
	public void check()
	{

	}
	/* methods */
	/**  ������Ʒ�б� */
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
	/** ���л� */
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