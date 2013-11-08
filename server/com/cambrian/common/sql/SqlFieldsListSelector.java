/**
 * 
 */
package com.cambrian.common.sql;

import com.cambrian.common.util.ArrayList;

public class SqlFieldsListSelector implements SqlSelector
{

	String sql;
	ArrayList list=new ArrayList();

	public SqlFieldsListSelector(String paramString)
	{
		this.sql=paramString;
	}

	public String getSql()
	{
		return this.sql;
	}

	public ArrayList getList()
	{
		return this.list;
	}

	public int select(Object paramObject)
	{
		this.list.add(paramObject);
		return 0;
	}

	public String toString()
	{
		return super.toString()+"[sql="+this.sql+"]";
	}
}