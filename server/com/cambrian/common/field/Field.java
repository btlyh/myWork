package com.cambrian.common.field;

/***
 * ��˵�����ֶζ���
 * 
 * @version 2013-4-18
 * @author HYZ (huangyz1988@qq.com)
 */
public abstract class Field
{

	/** �ֶ����� */
	public String name;

	/** ��ȡ�ֶ�ֵ */
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
