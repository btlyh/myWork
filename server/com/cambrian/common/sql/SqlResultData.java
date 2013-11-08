package com.cambrian.common.sql;

import com.cambrian.common.field.Fields;
import com.cambrian.common.util.ArrayList;

/**
 * ��˵����sql��ѯ�������
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class SqlResultData
{

	/** sql��� */
	String sql;
	/** ����б� */
	ArrayList list=new ArrayList();

	public SqlResultData(String sql)
	{
		this.sql=sql;
	}

	/** ���sql */
	public String getSql()
	{
		return sql;
	}
	/** ��ý�� */
	public Fields[] getData()
	{
		Fields[] data=new Fields[list.size()];
		list.toArray(data);
		return data;
	}

	/** ������� */
	public boolean addData(Fields obj)
	{
		list.add(obj);
		return true;
	}

	/** ��Ϣ */
	public String toString()
	{
		return super.toString()+"[sql="+sql+"]";
	}
}