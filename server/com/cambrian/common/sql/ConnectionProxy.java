/**
 * 
 */
package com.cambrian.common.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import com.cambrian.common.log.Logger;

class ConnectionProxy implements Connection
{

	private static Logger log=Logger.getLogger(ConnectionProxy.class);
	Connection connection;
	boolean used;
	int usedCount;
	long usedTime;

	public ConnectionProxy(Connection paramConnection)
	{
		if(paramConnection==null)
			throw new IllegalArgumentException(getClass().getName()
				+" <init>, null Connection");
		this.connection=paramConnection;
		this.usedTime=System.currentTimeMillis();
		if(log.isInfoEnabled())
			log.info("init, "+super.toString()+"["+this.connection+"]");
	}

	public synchronized boolean isUsed()
	{
		return this.used;
	}

	public int getUsedCount()
	{
		return this.usedCount;
	}

	public long getUsedTime()
	{
		return this.usedTime;
	}

	public boolean isActive()
	{
		try
		{
			return !this.connection.isClosed();
		}
		catch(Exception localException)
		{
		}
		return false;
	}

	public boolean isClosed() throws SQLException
	{
		return this.connection.isClosed();
	}

	public boolean isReadOnly() throws SQLException
	{
		return this.connection.isReadOnly();
	}

	public void setReadOnly(boolean paramBoolean) throws SQLException
	{
		this.connection.setReadOnly(paramBoolean);
	}

	public boolean getAutoCommit() throws SQLException
	{
		return this.connection.getAutoCommit();
	}

	public void setAutoCommit(boolean paramBoolean) throws SQLException
	{
		this.connection.setAutoCommit(paramBoolean);
	}

	public String getCatalog() throws SQLException
	{
		return this.connection.getCatalog();
	}

	public void setCatalog(String paramString) throws SQLException
	{
		this.connection.setCatalog(paramString);
	}

	public int getHoldability() throws SQLException
	{
		return this.connection.getHoldability();
	}

	public void setHoldability(int paramInt) throws SQLException
	{
		this.connection.setHoldability(paramInt);
	}

	public int getTransactionIsolation() throws SQLException
	{
		return this.connection.getTransactionIsolation();
	}

	public void setTransactionIsolation(int paramInt) throws SQLException
	{
		this.connection.setTransactionIsolation(paramInt);
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public Map getTypeMap() throws SQLException
	{
		return this.connection.getTypeMap();
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	public void setTypeMap(Map paramMap) throws SQLException
	{
		this.connection.setTypeMap(paramMap);
	}

	public DatabaseMetaData getMetaData() throws SQLException
	{
		return this.connection.getMetaData();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return this.connection.getWarnings();
	}

	public Savepoint setSavepoint() throws SQLException
	{
		return this.connection.setSavepoint();
	}

	public Savepoint setSavepoint(String paramString) throws SQLException
	{
		return this.connection.setSavepoint(paramString);
	}

	public synchronized boolean use()
	{
		if(this.used) return false;
		this.used=true;
		this.usedCount+=1;
		this.usedTime=System.currentTimeMillis();
		if(log.isDebugEnabled())
			log.debug("use, "+super.toString()+"[usedCount="+this.usedCount
				+", usedTime="+this.usedTime+", "+this.connection+"]");
		return true;
	}

	public String nativeSQL(String paramString) throws SQLException
	{
		return this.connection.nativeSQL(paramString);
	}

	public void commit() throws SQLException
	{
		this.connection.commit();
	}

	public void rollback() throws SQLException
	{
		this.connection.rollback();
	}

	public void clearWarnings() throws SQLException
	{
		this.connection.clearWarnings();
	}

	public Statement createStatement() throws SQLException
	{
		return this.connection.createStatement();
	}

	public Statement createStatement(int paramInt1,int paramInt2)
		throws SQLException
	{
		return this.connection.createStatement(paramInt1,paramInt2);
	}

	public Statement createStatement(int paramInt1,int paramInt2,
		int paramInt3) throws SQLException
	{
		return this.connection
			.createStatement(paramInt1,paramInt2,paramInt3);
	}

	public PreparedStatement prepareStatement(String paramString)
		throws SQLException
	{
		return this.connection.prepareStatement(paramString);
	}

	public PreparedStatement prepareStatement(String paramString,int paramInt)
		throws SQLException
	{
		return this.connection.prepareStatement(paramString,paramInt);
	}

	public PreparedStatement prepareStatement(String paramString,
		int paramInt1,int paramInt2) throws SQLException
	{
		return this.connection.prepareStatement(paramString,paramInt1,
			paramInt2);
	}

	public PreparedStatement prepareStatement(String paramString,
		int paramInt1,int paramInt2,int paramInt3) throws SQLException
	{
		return this.connection.prepareStatement(paramString,paramInt1,
			paramInt2,paramInt3);
	}

	public PreparedStatement prepareStatement(String paramString,
		int[] paramArrayOfInt) throws SQLException
	{
		return this.connection.prepareStatement(paramString,paramArrayOfInt);
	}

	public PreparedStatement prepareStatement(String paramString,
		String[] paramArrayOfString) throws SQLException
	{
		return this.connection.prepareStatement(paramString,
			paramArrayOfString);
	}

	public CallableStatement prepareCall(String paramString)
		throws SQLException
	{
		return this.connection.prepareCall(paramString);
	}

	public CallableStatement prepareCall(String paramString,int paramInt1,
		int paramInt2) throws SQLException
	{
		return this.connection.prepareCall(paramString,paramInt1,paramInt2);
	}

	public CallableStatement prepareCall(String paramString,int paramInt1,
		int paramInt2,int paramInt3) throws SQLException
	{
		return this.connection.prepareCall(paramString,paramInt1,paramInt2,
			paramInt3);
	}

	public void rollback(Savepoint paramSavepoint) throws SQLException
	{
		this.connection.rollback(paramSavepoint);
	}

	public void releaseSavepoint(Savepoint paramSavepoint)
		throws SQLException
	{
		this.connection.releaseSavepoint(paramSavepoint);
	}

	public synchronized void close() throws SQLException
	{
		this.used=false;
		if(log.isDebugEnabled()) log.debug("close, "+super.toString());
	}

	public synchronized void destroy()
	{
		boolean bool=this.used;
		this.used=true;
		try
		{
			this.connection.close();
		}
		catch(SQLException localSQLException)
		{
		}
		if(log.isInfoEnabled())
			log.info("destroy, "+super.toString()+"[used="+bool
				+", usedCount="+this.usedCount+", usedTime="+this.usedTime
				+", "+this.connection+"]");
	}

	public String toString()
	{
		return super.toString()+"[used="+this.used+", usedCount="
			+this.usedCount+", usedTime="+this.usedTime+", "+this.connection
			+"]";
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	public <T>T unwrap(Class<T> iface) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	// /* (non-Javadoc)
	// * @see java.sql.Connection#setTypeMap(java.util.Map)
	// */
	// public void setTypeMap(Map<String,Class<?>> map) throws SQLException
	// {
	// // TODO Auto-generated method stub
	//
	// }

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#createClob()
	 */
	public Clob createClob() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#createBlob()
	 */
	public Blob createBlob() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#createNClob()
	 */
	public NClob createNClob() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#createSQLXML()
	 */
	public SQLXML createSQLXML() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#isValid(int)
	 */
	public boolean isValid(int timeout) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#setClientInfo(java.lang.String,
	 * java.lang.String)
	 */
	public void setClientInfo(String name,String value)
		throws SQLClientInfoException
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#setClientInfo(java.util.Properties)
	 */
	public void setClientInfo(Properties properties)
		throws SQLClientInfoException
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#getClientInfo(java.lang.String)
	 */
	public String getClientInfo(String name) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#getClientInfo()
	 */
	public Properties getClientInfo() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#createArrayOf(java.lang.String,
	 * java.lang.Object[])
	 */
	public Array createArrayOf(String typeName,Object[] elements)
		throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.Connection#createStruct(java.lang.String,
	 * java.lang.Object[])
	 */
	public Struct createStruct(String typeName,Object[] attributes)
		throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}
}