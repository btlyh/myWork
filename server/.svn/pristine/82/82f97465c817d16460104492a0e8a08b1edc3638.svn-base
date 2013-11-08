/**
 * 
 */
package com.cambrian.common.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.Properties;

import com.cambrian.common.log.Logger;
import com.cambrian.common.timer.TimerCenter;
import com.cambrian.common.timer.TimerEvent;
import com.cambrian.common.timer.TimerListener;
import com.cambrian.common.util.ObjectArray;

public class ConnectionManager implements TimerListener
{

	public static final int INIT_SIZE=2;
	public static final int MAX_SIZE=20;
	public static final int TIMEOUT=180000;
	private static Logger log=Logger.getLogger(ConnectionManager.class);

	private Properties properties=new Properties();
	private String driver;
	private String url;
	private int initSize=2;

	private int maxSize=20;

	private int timeout=180000;
	private int maxUsedCount;
	private boolean autoCommit=true;

	private boolean check=true;

	private ObjectArray connections=new ObjectArray();

	TimerEvent collateTimerEvent=new TimerEvent(this,"collate",60000);

	public int size()
	{
		return this.connections.size();
	}

	public Properties getProperties()
	{
		return this.properties;
	}

	public String getDriver()
	{
		return this.driver;
	}

	public void setDriver(String paramString)
	{
		this.driver=paramString;
	}

	public String getURL()
	{
		return this.url;
	}

	public void setURL(String paramString)
	{
		this.url=paramString;
	}

	public String getCharacterEncoding()
	{
		return this.properties.getProperty("characterEncoding");
	}

	public void setCharacterEncoding(String paramString)
	{
		if(paramString==null)
			paramString=System.getProperty("file.encoding");
		this.properties.setProperty("useUnicode",
			"ISO-8859-1".equalsIgnoreCase(paramString)?"FALSE":"TRUE");
		this.properties.setProperty("characterEncoding",paramString);
	}

	public int getInitSize()
	{
		return this.initSize;
	}

	public void setInitSize(int paramInt)
	{
		this.initSize=paramInt;
	}

	public int getMaxSize()
	{
		return this.maxSize;
	}

	public void setMaxSize(int paramInt)
	{
		this.maxSize=paramInt;
	}

	public int getTimeout()
	{
		return this.timeout;
	}

	public void setTimeout(int paramInt)
	{
		this.timeout=paramInt;
	}

	public int getMaxUsedCount()
	{
		return this.maxUsedCount;
	}

	public void setMaxUsedCount(int paramInt)
	{
		this.maxUsedCount=paramInt;
	}

	public boolean isAutoCommit()
	{
		return this.autoCommit;
	}

	public void setAutoCommit(boolean paramBoolean)
	{
		this.autoCommit=paramBoolean;
	}

	public boolean isCheck()
	{
		return this.check;
	}

	public void setCheck(boolean paramBoolean)
	{
		this.check=paramBoolean;
	}

	public TimerEvent getCollateTimerEvent()
	{
		return this.collateTimerEvent;
	}

	public void init()
	{
		if((this.driver==null)||(this.driver.length()==0))
			throw new IllegalArgumentException(super.toString()
				+" init, null driver");
		if((this.url==null)||(this.url.length()==0))
			throw new IllegalArgumentException(super.toString()
				+" init, null url");
		Connection localConnection1=null;

		label287:try
		{
			Class.forName(this.driver).newInstance();
			localConnection1=DriverManager.getConnection(this.url,
				this.properties);
			DatabaseMetaData localDatabaseMetaData=localConnection1
				.getMetaData();

			int i=localDatabaseMetaData.getMaxConnections();
			if((i>0)&&(i<this.initSize)) this.initSize=i;
			if(i>0)
			{
				if(i>=this.maxSize) break label287;
				this.maxSize=i;
			}
		}
		catch(Exception localException1)
		{
			if(log.isWarnEnabled())
				log.warn("init, driver="+this.driver+", url="+this.url,
					localException1);
			throw new RuntimeException(super.toString()+" init, driver="
				+this.driver+", url="+this.url,localException1);
		}
		finally
		{
			try
			{
				if(localConnection1!=null) localConnection1.close();
			}
			catch(Exception localException2)
			{
			}

		}

		Object[] arrayOfObject=new Object[this.initSize];
		int j=0;
		for(int k=arrayOfObject.length-1;k>=0;k--)
		{
			Connection localConnection2=createConnection();
			if(localConnection2!=null)
				arrayOfObject[(j++)]=new ConnectionProxy(localConnection2);
		}
		this.connections.add(arrayOfObject,0,j);
		TimerCenter.getMinuteTimer().add(this.collateTimerEvent);
		if(log.isInfoEnabled()) log.info("init, "+this);
	}

	public int getRunningCount()
	{
		Object[] arrayOfObject=this.connections.getArray();
		ConnectionProxy localConnectionProxy=null;
		int i=0;
		for(int j=arrayOfObject.length-1;j>=0;j--)
		{
			localConnectionProxy=(ConnectionProxy)arrayOfObject[j];
			if(!localConnectionProxy.isUsed()) continue;
			i++;
		}
		return i;
	}

	public Connection createConnection()
	{
		Connection localConnection=null;
		try
		{
			localConnection=DriverManager.getConnection(this.url,
				this.properties);
			localConnection.setAutoCommit(this.autoCommit);
		}
		catch(Exception localException)
		{
			if(log.isWarnEnabled())
				log.warn("createConnection, url="+this.url,localException);
		}
		return localConnection;
	}

	public Connection getConnection()
	{
		Object[] arrayOfObject=this.connections.getArray();
		ConnectionProxy localConnectionProxy=null;
		int i=arrayOfObject.length-1;
		for(;i>=0;i--)
		{
			localConnectionProxy=(ConnectionProxy)arrayOfObject[i];
			if((localConnectionProxy.isUsed())
				||((this.check)&&(!localConnectionProxy.isActive())))
				continue;
			if(localConnectionProxy.use()) break;
		}
		if(i>=0) return localConnectionProxy;
		if(arrayOfObject.length>=this.maxSize)
			throw new IllegalStateException(super.toString()
				+" getConnection, overflow");
		Connection localConnection=createConnection();
		if(localConnection==null)
			throw new IllegalStateException(super.toString()
				+" getConnection, driver="+this.driver+", url="+this.url);
		localConnectionProxy=new ConnectionProxy(localConnection);
		localConnectionProxy.use();
		this.connections.add(localConnectionProxy);
		return localConnectionProxy;
	}

	public void onTimer(TimerEvent paramTimerEvent)
	{
		if(paramTimerEvent==this.collateTimerEvent)
			collate(paramTimerEvent.getCurrentTime());
	}

	public void collate(long paramLong)
	{
		paramLong-=this.timeout;
		Object[] arrayOfObject=this.connections.getArray();

		for(int i=arrayOfObject.length-1;i>=0;i--)
		{
			ConnectionProxy localConnectionProxy=(ConnectionProxy)arrayOfObject[i];

			if(localConnectionProxy.isActive())
			{
				if(paramLong<=localConnectionProxy.getUsedTime())
				{
					if(localConnectionProxy.isUsed())
					{
						continue;
					}

					if((this.maxUsedCount<=0)
						||(localConnectionProxy.getUsedCount()<this.maxUsedCount))
					{
						continue;
					}
				}
			}
			this.connections.remove(localConnectionProxy);
			localConnectionProxy.destroy();
		}
		if(log.isInfoEnabled()) log.info("collate, "+this);
	}

	public void close()
	{
		TimerCenter.getMinuteTimer().remove(this.collateTimerEvent);
		Object[] arrayOfObject=this.connections.getArray();
		this.connections.clear();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ConnectionProxy)arrayOfObject[i]).destroy();
		if(log.isInfoEnabled()) log.info("close, "+this);
	}

	public String toString()
	{
		return super.toString()+"[driver="+this.driver+", url="+this.url
			+", run="+getRunningCount()+", size="+size()+", maxSize="
			+this.maxSize+", timeout="+this.timeout+", maxUsedCount="
			+this.maxUsedCount+", autoCommit="+this.autoCommit+", check="
			+this.check+"]";
	}
}