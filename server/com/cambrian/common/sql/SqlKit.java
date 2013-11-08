/**
 * 
 */
package com.cambrian.common.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.Fields;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.CharBuffer;

/***
 * ��˵����sql������������
 * 
 * @version 2013-5-14
 * @author
 */
public final class SqlKit
{

	/** ��־ */
	private static final Logger log=Logger.getLogger(SqlKit.class);
	/** ���� */
	public static final String toString=SqlKit.class.getName();
	/** �ύģʽ��1=�ֶ�,2=�Զ� */
	public static final int HAND_COMMIT=1,AUTO_COMMIT=2;

	/** ִ��sql */
	public static void execute(ConnectionManager cm,String sql)
	{
		Connection connect=cm.getConnection();
		try
		{
			execute(connect,sql,true);
		}
		finally
		{
			DBKit.close(connect);
		}
	}

	/** ִ��sql���Ƿ��Զ��ύ */
	public static void execute(Connection connect,String sql,boolean commit)
	{
		if(log.isInfoEnabled()) log.info("execute, sql="+sql);
		Statement stmt=null;
		boolean isAutoCommit=false;
		try
		{
			isAutoCommit=connect.getAutoCommit();
			stmt=connect.createStatement();
			stmt.executeUpdate(sql);
			if((!isAutoCommit)&&commit) connect.commit();
		}
		catch(SQLException e)
		{
			if(!isAutoCommit) DBKit.rollback(connect);
			if(log.isWarnEnabled())
				log.warn("execute error, sql="+sql+" "+connect,e);
			throw new RuntimeException(" execute error, sql="+sql,e);
		}
		finally
		{
			DBKit.close(stmt,null);
		}
	}

	/** ��ѯ�������� */
	public static Fields query(ConnectionManager cm,String sql)
	{
		Connection connect=cm.getConnection();
		try
		{
			Fields fields=query(connect,sql);
			return fields;
		}
		finally
		{
			DBKit.close(connect);
		}
	}

	/** ��ѯ�������� */
	public static Fields query(Connection connect,String sql)
	{
		if(log.isInfoEnabled()) log.info("query, sql="+sql);
		Statement stmt=null;
		ResultSet result=null;
		try
		{
			stmt=connect.createStatement();
			result=stmt.executeQuery(sql);
			if(result.next())
			{
				ResultSetMetaData resultMD=result.getMetaData();
				Field[] fields=new Field[resultMD.getColumnCount()];
				for(int i=0;i<fields.length;i++)
					fields[i]=DBKit.getField(result,resultMD,i+1);
				Fields fieldArray=new Fields(fields);
				return fieldArray;
			}
			return null;
		}
		catch(SQLException e)
		{
			if(log.isWarnEnabled())
				log.warn("query error, sql="+sql+" "+connect,e);
			throw new RuntimeException(" query, sql="+sql,e);
		}
		finally
		{
			DBKit.close(stmt,result);
		}
	}

	/** ��ѯ���з������� */
	public static Fields[] querys(ConnectionManager cm,String sql)
	{
		Connection connect=cm.getConnection();
		SqlResultData data=null;
		try
		{
			data=querys(connect,sql,true);
		}
		finally
		{
			DBKit.close(connect);
		}
		return data.getData();
	}

	/** ��ѯ */
	public static SqlResultData querys(Connection conn,String sql,
		boolean commit)
	{
		if(log.isInfoEnabled()) log.info("querys, [sql="+sql+"]");
		SqlResultData sqlData=new SqlResultData(sql);
		Statement stmt=null;
		ResultSet result=null;
		int auto=0;
		try
		{
			auto=conn.getAutoCommit()?AUTO_COMMIT:HAND_COMMIT;
			stmt=conn.createStatement();
			result=stmt.executeQuery(sql);
			while(result.next())
			{
				ResultSetMetaData resultMD=result.getMetaData();
				Field[] array=new Field[resultMD.getColumnCount()];
				for(int j=array.length-1;j>=0;j--)
					array[j]=DBKit.getField(result,resultMD,j+1);
				sqlData.addData(new Fields(array));
				// TOOD ɾ��һЩ
			}
			if(auto==HAND_COMMIT&&commit) conn.commit();
		}
		catch(SQLException e)
		{
			if(auto==HAND_COMMIT) DBKit.rollback(conn);
			if(log.isWarnEnabled())
				log.warn("querys error, sql="+sql+" "+conn,e);
			throw new RuntimeException(" querys, sql="+sql,e);
		}
		catch(RuntimeException e)
		{
			if(auto==HAND_COMMIT) DBKit.rollback(conn);
			if(log.isWarnEnabled())
				log.warn("querys error, sql="+sql+" "+conn,e);
			throw e;
		}
		finally
		{
			DBKit.close(stmt,result);
		}
		return sqlData;
	}

	/** ��ò�ѯsql */
	public static String getSelectSqlString(String table,String condition)
	{
		return getSqlString("select *",table,condition,null,false,-1,0);
	}
	/** ��ò�ѯsql */
	public static String getSelectSqlString(String table,String condition,
		String order,boolean desc)
	{
		return getSqlString("select *",table,condition,order,desc,-1,0);
	}
	/** ��ò�ѯsql */
	public static String getSelectSqlString(String table,String condition,
		String order,boolean desc,int v1,int v2)
	{
		return getSqlString("select *",table,condition,order,desc,v1,v2);
	}
	/** ���sql */
	public static String getSqlString(String operate,String table,
		String condition,String order,boolean desc,int limitV1,int limitV2)
	{
		CharBuffer cb=new CharBuffer();
		cb.append(operate).append(" from ").append(table);
		if(condition!=null&&!condition.isEmpty())
			cb.append(" where ").append(condition);
		if(order!=null&&!order.isEmpty())
		{
			cb.append(" order by ").append(order);
			if(desc) cb.append(" desc");
		}
		if(limitV1>=0)
		{
			cb.append(" limit ").append(limitV1);
			if(limitV2>0) cb.append(',').append(limitV2);
		}
		return cb.getString();
	}
	/** ���SQL����ַ��� */
	public static void getSqlString(String sql,String table,Field key,
		CharBuffer cb)
	{
		cb.append(sql).append(" from ").append(table);
		cb.append(" where ").append(key.name).append('=');
		cb.append('\'').append(key.getValue()).append('\'');
	}
	/** ת��Ϊsql�ַ���(�����) */
	public static void getSqlString(String sql,String table,Fields keys,
		CharBuffer cb)
	{
		Field[] array=keys.getFields();
		cb.append(sql).append(" from ").append(table).append(" where ");
		for(int i=0;i<array.length;i++)
		{
			cb.append(array[i].name).append('=');
			cb.append('\'').append(array[i].getValue()).append('\'');
			if(i<array.length-1) cb.append(" and ");
		}
	}
}