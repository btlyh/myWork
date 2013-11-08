package com.cambrian.dfhm.prop;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;

public class Prop extends Sample
{

	/* static field */

	/* field */
	/** 名字 */
	String name;
	/** 介绍 */
	String description;
	/** 图片路径 */
	String img;
	/** 类型 */
	int type;
	/** 出售价格 */
	int money;
	/** 数量 */
	protected int count=1;
	
	/* commond method */
	/** 获得类型 */
	public int getType()
	{
		return type;
	}
	/** 设置类型 */
	public void setType(int type)
	{
		this.type=type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img=img;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int count)
	{
		this.count=count;
	}
	
	/** 最大堆叠数量 */
	public int getPileCount()
	{
		return 1;
	}
	
	/** 序列化(前台登陆时获取的数据) */
	@Override
	public void bytesWrite(ByteBuffer data)
	{
		
	}
	
	/** 序列化(和dc通信) */
	public void dbBytesWrite(ByteBuffer data)
	{
		
	}
	
	/** 反序列化(和dc通信) */
	public void dbBytesRead(ByteBuffer data)
	{
		
	}
}
