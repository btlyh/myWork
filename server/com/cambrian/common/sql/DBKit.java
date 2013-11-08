package com.cambrian.common.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.log4j.PropertyConfigurator;

import com.cambrian.common.field.BooleanField;
import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.ByteField;
import com.cambrian.common.field.CharField;
import com.cambrian.common.field.DoubleField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.FloatField;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.LongField;
import com.cambrian.common.field.ShortField;
import com.cambrian.common.field.StringField;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.CharBuffer;
import com.cambrian.common.util.ArrayList;
import com.mysql.jdbc.PreparedStatement;

/**
 * 类说明：数据库工具集
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class DBKit
{

	/** 日志 */
	public static final Logger log=Logger.getLogger(DBKit.class);
	/** 数据库操作状态：数据增加，操作成功，没有数据，操作异常 */
	public static final int ADD=2,OK=1,RESULTLESS=0,EXCEPTION=-1;

	/** 更新指定结果集中指定字段 */
	public static void update(ResultSet rs,Field field) throws SQLException
	{
		if(field instanceof StringField)
			rs.updateString(field.name,((StringField)field).value);
		else if(field instanceof ByteArrayField)
			rs.updateBytes(field.name,((ByteArrayField)field).value);
		else if(field instanceof BooleanField)
			rs.updateBoolean(field.name,((BooleanField)field).value);
		else if(field instanceof ByteField)
			rs.updateByte(field.name,((ByteField)field).value);
		else if(field instanceof ShortField)
			rs.updateShort(field.name,((ShortField)field).value);
		else if(field instanceof CharField)
			rs.updateShort(field.name,(short)((CharField)field).value);
		else if(field instanceof IntField)
			rs.updateInt(field.name,((IntField)field).value);
		else if(field instanceof LongField)
			rs.updateLong(field.name,((LongField)field).value);
		else if(field instanceof FloatField)
			rs.updateFloat(field.name,((FloatField)field).value);
		else if(field instanceof DoubleField)
			rs.updateDouble(field.name,((DoubleField)field).value);
	}

	/** 查询结果集中指定字段 */
	public static void query(ResultSet rs,Field field) throws SQLException
	{
		if(field instanceof StringField)
			((StringField)field).value=rs.getString(field.name);
		else if(field instanceof ByteArrayField)
			((ByteArrayField)field).value=rs.getBytes(field.name);
		else if(field instanceof BooleanField)
			((BooleanField)field).value=rs.getBoolean(field.name);
		else if(field instanceof ByteField)
			((ByteField)field).value=rs.getByte(field.name);
		else if(field instanceof ShortField)
			((ShortField)field).value=rs.getShort(field.name);
		else if(field instanceof CharField)
			((CharField)field).value=(char)rs.getShort(field.name);
		else if(field instanceof IntField)
			((IntField)field).value=rs.getInt(field.name);
		else if(field instanceof LongField)
			((LongField)field).value=rs.getLong(field.name);
		else if(field instanceof FloatField)
			((FloatField)field).value=rs.getFloat(field.name);
		else if(field instanceof DoubleField)
			((DoubleField)field).value=rs.getDouble(field.name);
	}

	/** 获取指定字段 */
	public static Field getField(ResultSet result,
		ResultSetMetaData resultMD,int column) throws SQLException
	{
		int type=resultMD.getColumnType(column);
		String name=resultMD.getColumnName(column);
		if(type==Types.INTEGER)
			return FieldKit.create(name,result.getInt(column));
		if(type==Types.CHAR||type==Types.VARCHAR||type==Types.LONGVARCHAR
			||type==Types.CLOB)
			return FieldKit.create(name,result.getString(column));
		if(type==Types.BINARY||type==Types.VARBINARY
			||type==Types.LONGVARBINARY||type==Types.BLOB)
			return FieldKit.create(name,result.getBytes(column));
		if(type==Types.BIGINT)
			return FieldKit.create(name,result.getLong(column));
		if(type==Types.DOUBLE||type==Types.DECIMAL)
			return FieldKit.create(name,result.getDouble(column));
		if(type==Types.FLOAT)
			return FieldKit.create(name,result.getFloat(column));
		if(type==Types.SMALLINT)
			return FieldKit.create(name,(short)result.getInt(column));
		if(type==Types.BIT||type==Types.TINYINT)
			return FieldKit.create(name,(byte)result.getInt(column));
		if(type==Types.BOOLEAN)
			return FieldKit.create(name,result.getBoolean(column));
		return FieldKit.create(name,result.getString(column));
	}
	/** 设置数据（新增或更新数据库） */
	public static int set(String table,ConnectionManager cm,Field key,
		Fields fields)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,key,cb);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rest=null;
		int ac=0;
		try
		{
			conn=cm.getConnection();
			ac=cm.isAutoCommit()?Connection.TRANSACTION_READ_COMMITTED
				:Connection.TRANSACTION_READ_UNCOMMITTED;
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			rest=stmt.executeQuery(cb.getString());
			int t=OK;
			if(rest.next())
			{
				Field[] array=fields.toArray();
				if(log.isInfoEnabled())
				{
					cb.append("; OK");
					for(int i=0;i<array.length;++i)
					{
						cb.append(' ');
						cb.append(array[i].name).append('=');
						cb.append(array[i].getValue());
						update(rest,array[i]);
					}
				}
				else
				{
					for(int i=0;i<array.length;++i)
						update(rest,array[i]);
				}
				rest.updateRow();
			}
			else
			{
				t=ADD;
				rest.moveToInsertRow();
				Field[] array=fields.toArray();
				update(rest,key);
				if(log.isInfoEnabled())
					cb.append("; ADD ").append(key.name).append('=')
						.append(key.getValue());
				for(int i=0;i<array.length;++i)
				{
					if(log.isInfoEnabled())
						cb.append(' ').append(array[i].name).append('=')
							.append(array[i].getValue());
					update(rest,array[i]);
				}
				rest.insertRow();
			}
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) conn.commit();
			if(log.isDebugEnabled()) log.debug("set, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) rollback(conn);
			if(log.isWarnEnabled())
				log.warn("set error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(stmt,rest);
			close(conn);
		}
	}
	/** 获取数据（查询数据库） */
	public static int get(String table,ConnectionManager cm,Field key,
		Fields fields)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,key,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			c=cm.getConnection();
			st=c.createStatement();
			System.err.println("sql ==="+cb.getString());
			rs=st.executeQuery(cb.getString());
			int t=OK;
			if(rs.next())
			{
				Field[] array=fields.toArray();
				if(array.length==0)
				{
					ResultSetMetaData rsmd=rs.getMetaData();
					array=new Field[rsmd.getColumnCount()];
					for(int i=0;i<array.length;++i)
						array[i]=getField(rs,rsmd,i);
					fields.add(array);
				}
				else
				{
					if(log.isInfoEnabled()) cb.append("; OK");
					for(int i=0;i<array.length;++i)
					{
						query(rs,array[i]);
						if(log.isInfoEnabled())
							cb.append(' ').append(array[i].name).append('=')
								.append(array[i].getValue());
					}
				}
			}
			else
			{
				t=RESULTLESS;
				if(log.isInfoEnabled()) cb.append("; RESULTLESS");
			}
			if(log.isDebugEnabled()) log.debug("get, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			if(log.isWarnEnabled())
				log.warn("get error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}
	/** 更新数据（更新数据库） */
	public static int update(String table,ConnectionManager cm,Field key,
		Fields setFields,Fields getFields)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,key,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		int ac=0;
		try
		{
			c=cm.getConnection();
			ac=(cm.isAutoCommit())?Connection.TRANSACTION_READ_COMMITTED
				:Connection.TRANSACTION_READ_UNCOMMITTED;
			st=c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery(cb.getString());
			int t=OK;
			if(rs.next())
			{
				Field[] array=getFields.toArray();
				if(log.isInfoEnabled())
				{
					cb.append("; OK");
					for(int i=0;i<array.length;++i)
					{
						cb.append(' ');
						cb.append(array[i].name).append('=');
						query(rs,array[i]);
						cb.append(array[i].getValue());
					}
				}
				else
				{
					for(int i=0;i<array.length;++i)
						query(rs,array[i]);
				}
				array=setFields.toArray();
				if(log.isInfoEnabled())
				{
					cb.append("; SET");
					for(int i=0;i<array.length;++i)
					{
						cb.append(' ');
						cb.append(array[i].name).append('=');
						cb.append(array[i].getValue());
						update(rs,array[i]);
					}
				}
				else
				{
					for(int i=0;i<array.length;++i)
						update(rs,array[i]);
				}
				rs.updateRow();
				if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) c.commit();

			}
			else
			{
				t=ADD;
				rs.moveToInsertRow();
				Field[] array=setFields.toArray();
				update(rs,key);
				if(log.isInfoEnabled())
					cb.append("; ADD ").append(key.name).append('=')
						.append(key.getValue());
				for(int i=0;i<array.length;++i)
				{
					if(log.isInfoEnabled())
						cb.append(' ').append(array[i].name).append('=')
							.append(array[i].getValue());
					update(rs,array[i]);
				}
				rs.insertRow();
				if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) c.commit();
				array=getFields.toArray();
				if(log.isInfoEnabled()) cb.append("; GET");
				for(int i=0;i<array.length;++i)
				{
					query(rs,array[i]);
					if(log.isInfoEnabled())
						cb.append(' ').append(array[i].name).append('=')
							.append(array[i].getValue());
				}
			}
			if(log.isDebugEnabled()) log.debug("update, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) rollback(c);
			if(log.isWarnEnabled())
				log.warn("update error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}
	/** 删除数据（从数据库中删除） */
	public static int delete(String table,ConnectionManager cm,Field key)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("delete",table,key,cb);
		Connection c=null;
		Statement st=null;
		int ac=0;
		try
		{
			c=cm.getConnection();
			ac=(cm.isAutoCommit())?Connection.TRANSACTION_READ_COMMITTED
				:Connection.TRANSACTION_READ_UNCOMMITTED;
			st=c.createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);
			int t=st.executeUpdate(cb.getString());
			t=t>OK?OK:RESULTLESS;
			if(log.isInfoEnabled()) cb.append(t==OK?"; OK":"; RESULTLESS");
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) c.commit();
			if(log.isDebugEnabled()) log.debug("delete, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) rollback(c);
			if(log.isWarnEnabled())
				log.warn("delete error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,null);
			close(c);
		}
	}
	/** 移除数据 */
	public static int remove(String table,ConnectionManager cm,Field key,
		Fields fields)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,key,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			c=cm.getConnection();
			st=c.createStatement(1003,1008);
			rs=st.executeQuery(cb.getString());
			int t=OK;
			if(rs.next())
			{
				Field[] array=fields.toArray();
				if(log.isInfoEnabled()) cb.append("; OK");
				for(int i=0;i<array.length;++i)
				{
					query(rs,array[i]);
					if(log.isInfoEnabled())
						cb.append(' ').append(array[i].name).append('=')
							.append(array[i].getValue());
				}
				rs.deleteRow();
			}
			else
			{
				t=RESULTLESS;
				if(log.isInfoEnabled()) cb.append("; RESULTLESS");
			}
			if(log.isDebugEnabled()) log.debug("remove, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			if(log.isWarnEnabled())
				log.warn("remove error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}

	/** 根据指定的键获取一条记录(多个键) */
	public static int get(String table,ConnectionManager cm,Fields keys,
		Fields fields)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,keys,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			c=cm.getConnection();
			st=c.createStatement();
			rs=st.executeQuery(cb.getString());
			int t=OK;
			if(rs.next())
			{
				Field[] array=fields.getFields();
				if(array.length==0)
				{
					ResultSetMetaData rsmd=rs.getMetaData();
					array=new Field[rsmd.getColumnCount()];
					for(int i=0;i<array.length;++i)
						array[i]=getField(rs,rsmd,i);
					fields.add(array);
				}
				else
				{
					int i;
					if(log.isInfoEnabled()) cb.append("; OK");
					for(i=0;i<array.length;++i)
					{
						query(rs,array[i]);
						if(log.isInfoEnabled())
							cb.append(' ').append(array[i].name).append('=')
								.append(array[i].getValue());
					}
				}
			}
			else
			{
				t=RESULTLESS;
				if(log.isInfoEnabled()) cb.append("; RESULTLESS");
			}
			if(log.isDebugEnabled()) log.debug("get, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(log.isWarnEnabled())
				log.warn("get error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}

	/** 根据指定的键设置指定的字段值(多个键) */
	public static int set(String table,ConnectionManager cm,Fields keys,
		Fields fields)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,keys,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		int ac=0;
		try
		{
			c=cm.getConnection();
			ac=(cm.isAutoCommit())?Connection.TRANSACTION_READ_COMMITTED
				:Connection.TRANSACTION_READ_UNCOMMITTED;
			st=c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery(cb.getString());
			Field[] array=fields.getFields();
			Field[] keysArray=keys.getFields();
			int i;
			int t=OK;
			if(rs.next())
			{
				if(log.isInfoEnabled()) cb.append("; OK");
				for(i=0;i<array.length;++i)
				{
					if(log.isInfoEnabled())
						cb.append(' ').append(array[i].name).append('=')
							.append(array[i].getValue());
					update(rs,array[i]);
				}
				rs.updateRow();
			}
			else
			{
				t=ADD;
				rs.moveToInsertRow();
				if(log.isInfoEnabled()) cb.append("; ADD");
				for(int j=0;j<keysArray.length;j++)
				{
					if(log.isInfoEnabled())
						cb.append(' ').append(keysArray[j]).append('=')
							.append(keysArray[j].getValue());
					update(rs,keysArray[j]);
				}
				for(i=0;i<array.length;++i)
				{
					if(log.isInfoEnabled())
						cb.append(' ').append(array[i].name).append('=')
							.append(array[i].getValue());
					update(rs,array[i]);
				}
				rs.insertRow();
			}
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) c.commit();
			if(log.isDebugEnabled()) log.debug("set, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) rollback(c);
			if(log.isWarnEnabled())
				log.warn("set error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}

	/** 根据指定的键获得获取多条数据 (单个键) */
	public static int getAll(String table,ConnectionManager cm,Field key,
		Fields fields,ArrayList list)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,key,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			c=cm.getConnection();
			st=c.createStatement();
			rs=st.executeQuery(cb.getString());
			int t=RESULTLESS;
			while(rs.next())
			{
				t++;
				Fields temp=FieldKit.coppy(fields);
				Field[] array=temp.getFields();
				int i;
				for(i=0;i<array.length;++i)
				{
					query(rs,array[i]);
				}
				list.add(temp);
			}
			if(log.isInfoEnabled())
			{
				String s=t>=OK?(t>=RESULTLESS?"RESULTLESS":"EXCEPTION"):"OK";
				log.info("get "+s+", "+cb.getString());
			}
			return t>=OK?OK:t;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(log.isWarnEnabled())
				log.warn("get error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}

	/** 根据key查询到的数据，若一条数据的decrkey字段的值不在usedArray中则删除该条数据 */
	public static int delete(String table,ConnectionManager cm,Field key,
		String decrkey,int[] usedArray)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("select *",table,key,cb);
		Connection c=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			c=cm.getConnection();
			st=c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery(cb.getString());
			int t=RESULTLESS;
			while(rs.next())
			{
				boolean b=false;
				int id=rs.getInt(decrkey);
				for(int i=0;i<usedArray.length;i++)
				{
					b=(id==usedArray[i]);
					if(b) break;
				}
				if(!b)
				{
					rs.deleteRow();
					t++;
					if(log.isInfoEnabled()) cb.append(", ").append(id);
				}
			}
			if(t==RESULTLESS)
				if(log.isInfoEnabled()) cb.append(", RESULTLESS");
			if(log.isDebugEnabled()) log.debug("delete, "+cb.getString());
			return t>=OK?OK:t;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(log.isWarnEnabled())
				log.warn("delete error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,rs);
			close(c);
		}
	}

	/** 根据指定的键删除数据(多个键) */
	public static int delete(String table,ConnectionManager cm,Fields keys)
	{
		CharBuffer cb=new CharBuffer();
		SqlKit.getSqlString("delete",table,keys,cb);
		Connection c=null;
		Statement st=null;
		int ac=0;
		try
		{
			c=cm.getConnection();
			ac=cm.isAutoCommit()?Connection.TRANSACTION_READ_COMMITTED
				:Connection.TRANSACTION_READ_UNCOMMITTED;
			st=c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			int t=st.executeUpdate(cb.getString());
			t=t>OK?OK:RESULTLESS;
			if(log.isInfoEnabled()) cb.append(t==OK?"; OK":"; RESULTLESS");
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) c.commit();
			if(log.isDebugEnabled()) log.debug("delete, "+cb.getString());
			return t;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(ac==Connection.TRANSACTION_READ_UNCOMMITTED) rollback(c);
			if(log.isWarnEnabled())
				log.warn("delete error, "+cb.getString(),e);
			return EXCEPTION;
		}
		finally
		{
			close(st,null);
			close(c);
		}
	}
	/** 回滚 */
	public static void rollback(Connection connect)
	{
		try
		{
			connect.rollback();
		}
		catch(Exception e)
		{
			if(log.isWarnEnabled()) log.warn("rollback error, "+connect,e);
		}
	}
	/** 关闭连接 */
	public static void close(Connection conn)
	{
		try
		{
			if(conn!=null) conn.close();
		}
		catch(Exception e)
		{
		}
	}
	/** 关闭语句对象 */
	public static void close(Statement stmt)
	{
		try
		{
			if(stmt!=null) stmt.close();
		}
		catch(Exception e)
		{
		}
	}
	/** 关闭预编译语句对象 */
	public static void close(PreparedStatement pstmt)
	{
		try
		{
			if(pstmt!=null) pstmt.close();
		}
		catch(Exception e)
		{
		}
	}
	/** 关闭结果集对象 */
	public static void close(ResultSet result)
	{
		try
		{
			if(result!=null) result.close();
		}
		catch(Exception e)
		{
		}
	}
	/** 关闭语句对象和结果集对象 */
	public static void close(Statement stmt,ResultSet result)
	{
		close(result);
		close(stmt);
	}

	public static void main(String[] args)
	{
		PropertyConfigurator.configure("pvz.log4j.cfg");
		String table="user";
		ConnectionManager cm=init();
		ArrayList list=new ArrayList();
		Field[] array=new Field[3];
		array[0]=FieldKit.create("userid",0);
		array[1]=FieldKit.create("username",(String)null);
		array[2]=FieldKit.create("passwd",(String)null);
		Fields fields=new Fields(array);
		getAll(table,cm,FieldKit.create("passwd",1),fields,list);
		for(int i=0;i<list.size();i++)
		{
			fields=(Fields)list.get(i);
			System.out.print(fields.get("userid").getValue()+"\t");
			System.out.print(fields.get("username").getValue()+"\t");
			System.out.println(fields.get("passwd").getValue());
		}
	}
	public static ConnectionManager init()
	{
		ConnectionManager cm=new ConnectionManager();
		cm.setDriver("com.mysql.jdbc.Driver");
		cm.setURL("jdbc:mysql:///pvz");
		cm.getProperties().put("user","root");
		cm.getProperties().put("password","dong2feng");
		cm.init();
		return cm;
	}

}
