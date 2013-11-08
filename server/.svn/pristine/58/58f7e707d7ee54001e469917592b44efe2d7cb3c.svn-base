package com.cambrian.common.field;

/***
 * 类说明：字段对象
 * 
 * @version 2013-4-18
 * @author HYZ (huangyz1988@qq.com)
 */
public abstract class Field
{

	/** 字段名字 */
	public String name;

	/** 获取字段值 */
	public abstract Object getValue();

	public void coppy(Field dest)
	{
		dest.name=name;
	}

	public String toString()
	{
		return "Field: name="+name+", value="+getValue();
	}
}
