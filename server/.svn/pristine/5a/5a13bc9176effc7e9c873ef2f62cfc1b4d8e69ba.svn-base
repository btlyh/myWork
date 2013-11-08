package com.cambrian.common.sql;

import com.cambrian.common.field.Fields;
import com.cambrian.common.util.ArrayList;

/**
 * 类说明：sql查询结果数据
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class SqlResultData
{

	/** sql语句 */
	String sql;
	/** 结果列表 */
	ArrayList list=new ArrayList();

	public SqlResultData(String sql)
	{
		this.sql=sql;
	}

	/** 获得sql */
	public String getSql()
	{
		return sql;
	}
	/** 获得结果 */
	public Fields[] getData()
	{
		Fields[] data=new Fields[list.size()];
		list.toArray(data);
		return data;
	}

	/** 添加数据 */
	public boolean addData(Fields obj)
	{
		list.add(obj);
		return true;
	}

	/** 信息 */
	public String toString()
	{
		return super.toString()+"[sql="+sql+"]";
	}
}