/** */
package com.cambrian.game.dc;


import com.cambrian.common.codec.CRC32;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.timer.TimerCenter;
import com.cambrian.common.timer.TimerEvent;
import com.cambrian.common.timer.TimerListener;
import com.cambrian.common.util.ArrayList;
import com.cambrian.common.util.AttributeList;
import com.cambrian.common.util.ChangeListenerList;
import com.cambrian.common.util.Selector;
import com.cambrian.common.util.TimeKit;
import com.cambrian.game.Session;
import com.cambrian.game.SessionMap;
import com.cambrian.game.close.CloseAble;

/**
 * 类说明：数据中心(数据管理)
 * 
 * @version 2013-4-25
 * @author HYZ (huangyz1988@qq.com)
 */
public class DataCenter extends ChangeListenerList implements CloseAble,
	TimerListener
{

	public static final int TIMEOUT=1800000;
	public static final int SAVE_TIMEOUT=3600000;
	public static final int CRC_STATE=100;
	public static final int SAVE_TIME_STATE=200;
	public static final int PRE_LOGIN_CHANGED=1;
	public static final int LOGIN_CHANGED=2;
	public static final int LOGIN_BUFFER_CHANGED=3;
	public static final int LOGIN_AGAIN_CHANGED=4;
	public static final int PRE_LOAD_CHANGED=11;
	public static final int LOAD_CHANGED=12;
	public static final int LOAD_RENEW_CHANGED=13;
	public static final int PRE_SAVE_CHANGED=21;
	public static final int SAVE_CHANGED=22;
	public static final int PRE_EXIT_CHANGED=31;
	public static final int EXIT_CHANGED=32;
	public static final int PRE_ACCESS_SAVE_CHANGED=41;
	public static final int ACCESS_SAVE_CHANGED=42;
	public static final int PRE_ACCESS_EXIT_CHANGED=51;
	public static final int ACCESS_EXIT_CHANGED=52;
	public static final int PRE_UPDATE_CHANGED=61;
	public static final int UPDATE_CHANGED=62;
	public static final int SAVE_FAIL_EXCEPTION=800;
	public static final String SAVE_FAIL_EXCEPTION_MESSAGE="access save fail";
	public static final char SEPARATOR=':';
	public static final String SID="SID";
	public static final String SESSION_AMOUNT="sessionAmount";
	public static final String err21=DataCenter.class.getName()
		+" load, invalid id";
	public static final String err22=DataCenter.class.getName()
		+" load, session is in process of save";
	public static final String err23=DataCenter.class.getName()
		+" load, invalid address";
	public static final String err24=DataCenter.class.getName()
		+" load, data can not be accessed";
	/** 保存，错误的标识 */
	public static final String err31=DataCenter.class.getName()
		+" save, invalid id";
	/** 保存，错误的通讯地址 */
	public static final String err32=DataCenter.class.getName()
		+" save, invalid address";
	public static final String err41=DataCenter.class.getName()
		+" update, invalid id";
	public static final String err42=DataCenter.class.getName()
		+" update, invalid address";

	private static final Logger log=Logger.getLogger(DataCenter.class);
	SessionMap sm;
	DBAccess access;
	int timeout=1800000;
	int saveTimeout=360000;
	TimerEvent collateTimerEvent=new TimerEvent(this,"collate",60000);
	AttributeList attributes=new AttributeList();
	CollateSelector selecter=new CollateSelector();
	ArrayList loginList=new ArrayList();
	ArrayList saveList=new ArrayList();

	public DataCenter()
	{
		this(new SessionMap());
	}

	public DataCenter(SessionMap sm)
	{
		this.sm=sm;
	}

	public SessionMap getSessionMap()
	{
		return sm;
	}

	public DBAccess getDBAccess()
	{
		return access;
	}

	public void setDBAccess(DBAccess access)
	{
		this.access=access;
	}

	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(int t)
	{
		timeout=t;
	}

	public int getSaveTimeout()
	{
		return saveTimeout;
	}

	public void setSaveTimeout(int t)
	{
		saveTimeout=t;
	}

	public TimerEvent getCollateTimerEvent()
	{
		return collateTimerEvent;
	}

	public AttributeList getAttributes()
	{
		return attributes;
	}

	public Session login(String uid,String sid,String address,Session session)
	{
		if(log.isDebugEnabled())
			log.debug("login, id="+uid+", sid="+sid+", address="+address
				+", "+session);
		synchronized(loginList)
		{
			if(loginList.contain(uid))
				throw new DataAccessException(400,err21);
			loginList.add(uid);
		}
		try
		{
			change(session,PRE_LOGIN_CHANGED,uid,sid,address);
			Session s=sm.get(uid);
			if(s!=null)
			{
				Session old=(Session)s.getSource();
				if(old==null)
				{
					s.setSource(session);
					s.setActiveTime(TimeKit.nowTimeMills());
					s.setAttribute("SID",sid);
					change(session,LOGIN_BUFFER_CHANGED,s,old,address);
					if(log.isInfoEnabled())
						log.info("login ok buffer, id="+uid+", sid="+sid
							+", address="+address+", "+session);
				}
				else
				{
					if((session!=null)&&(session!=old))
						throw new DataAccessException(300,old.getId());
					s.setActiveTime(TimeKit.nowTimeMills());
					s.setAttribute("SID",sid);
					change(session,LOGIN_AGAIN_CHANGED,s,sid,address);
					if(log.isInfoEnabled())
						log.info("login ok, again, id="+uid+", sid="+sid
							+", address="+address+", "+session);
				}
			}
			else
			{
				s=new Session(uid);
				s.setSource(session);
				s.setActiveTime(TimeKit.nowTimeMills());
				s.setAttribute("SID",sid);
				sm.add(s);
				change(session,LOGIN_CHANGED,s,sid,address);
				if(log.isInfoEnabled())
					log.info("login ok, id="+uid+", "+session);
			}
			return s;
		}
		finally
		{
			synchronized(loginList)
			{
				loginList.remove(uid);
			}
		}
	}

	public ByteBuffer load(String uid,Session session,String nickName,
		String userName)
	{
		if(log.isDebugEnabled()) log.debug("load, id="+uid+", "+session);
		Session s=sm.get(uid);
		if(s==null) throw new DataAccessException(560,err21);
		s.setActiveTime(TimeKit.nowTimeMills());
		synchronized(this.saveList)
		{
			if(this.saveList.contain(uid))
				throw new DataAccessException(500,err22);
		}
		change(this,PRE_LOAD_CHANGED,s,session);
		if((session!=null)&&(session!=s.getSource()))
		{
			if(log.isWarnEnabled())
				log.warn("load error, id="+uid+", current="+s.getSource()
					+", invalid="+session,null);
			throw new DataAccessException(560,err23);
		}
		ByteBuffer bb=(ByteBuffer)s.getReference();
		if(bb==null)
		{
			if(!this.access.canAccess())
				throw new DataAccessException(500,err24);
			bb=this.access.load(uid,(String)s.getAttribute("SID"),nickName,
				userName);
			if(bb.getArray()[0]!=-1)
			{
				s.setReference(bb);
				change(this,LOAD_CHANGED,s,bb);
			}
			if(log.isInfoEnabled())
				log.info("load ok, id="+uid+", "+session);
		}
		else
		{
			change(this,LOAD_RENEW_CHANGED,s,bb);
			if(log.isInfoEnabled())
				log.info("load ok, buffer, id="+uid+", "+session);
		}
		return bb;
	}

	public void save(String uid,ByteBuffer data,boolean save,boolean logout,
		Session session)
	{
		if(log.isDebugEnabled())
			log.debug("save, id="+uid+", save="+save+", logout="+logout+", "
				+session);
		Session s=sm.get(uid);
		if(s==null)
		{
			if(log.isWarnEnabled()) log.warn("save error, invalid id="+uid);
			throw new DataAccessException(560,err31);
		}
		if(session!=s.getSource())
		{
			if(log.isWarnEnabled())
				log.warn("save error, id="+uid+", current session="
					+s.getSource()+", invalid session="+session);
			throw new DataAccessException(560,err32);
		}
		s.setActiveTime(TimeKit.nowTimeMills());
		if(save)
		{
			change(this,PRE_SAVE_CHANGED,s,data,session);
			s.setReference(data);
			change(this,SAVE_CHANGED,s,data,session);
			if(log.isInfoEnabled())
				log.info("save ok, id="+uid+", "+session);
		}
		if(!logout) return;
		change(this,PRE_EXIT_CHANGED,s,session);
		s.setSource(null);
		change(this,EXIT_CHANGED,s,session);
		if(log.isInfoEnabled())
			log.info("save ok, logout, id="+uid+", "+session);
	}

	public void timerStart()
	{
		TimerCenter.getMinuteTimer().add(collateTimerEvent);
	}

	public void onTimer(TimerEvent ev)
	{
		if(ev==collateTimerEvent) collate(ev.getCurrentTime());
	}

	void collate(long time)
	{
		boolean b=access.canAccess();
		if(log.isDebugEnabled()) log.debug(runtimeLog());
		selecter.remove=b;
		selecter.list1.clear();
		selecter.list2.clear();
		selecter.time=(time-timeout);
		try
		{
			synchronized(saveList)
			{
				sm.select(selecter);
			}
			if(b)
			{
				exitSessions(selecter.list1);
				saveSessions(selecter.list2,time);
				attributes.set("sessionAmount",String.valueOf(sm.size()));
			}
			if(log.isInfoEnabled()) log.info(runtimeLog());
		}
		finally
		{
			synchronized(saveList)
			{
				saveList.clear();
			}
		}
	}

	public void exitSessions(ArrayList list)
	{
		int i=0;
		for(int n=list.size();i<n;i++)
		{
			Session s=(Session)list.get(i);
			change(this,PRE_ACCESS_EXIT_CHANGED,s);
			DataAccessException e=saveSession(s);
			change(this,ACCESS_EXIT_CHANGED,s,e);
		}
	}

	public void saveSessions(ArrayList list,long time)
	{
		int t=TimeKit.nowTime();
		int ct=TimeKit.timeSecond(time-saveTimeout);
		int i=0;
		for(int n=list.size();i<n;i++)
		{
			Session s=(Session)list.get(i);
			int ctime=s.getState(SAVE_TIME_STATE);
			if(ct>=ctime)
			{
				change(this,PRE_ACCESS_SAVE_CHANGED,s);
				DataAccessException e=saveSession(s);
				if(e==null) s.setState(SAVE_TIME_STATE,t);
				change(this,ACCESS_SAVE_CHANGED,s,e);
			}
		}
	}
	
	public DataAccessException saveSession(Session s)
	{
		String id=s.getId();
		ByteBuffer data=(ByteBuffer)s.getReference();
		if(data==null)
		{
			if(log.isInfoEnabled())
				log.info("saveSession, null data, id="+id);
			return null;
		}
		boolean save=false;
		try
		{
			int crc=CRC32.getValue(data.toArray());
			save=crc!=s.getState(CRC_STATE);
			boolean b=true;
			if(save)
			{
				b=access.save(s.getId(),data);
				if(b) s.setState(CRC_STATE,crc);
			}
			if(log.isInfoEnabled())
				log.info("saveSession, id="+id+", data length="
					+data.length()+", save="+save+", result="+b);
			if(b) return null;
		}
		catch(Throwable e)
		{
			if(log.isWarnEnabled())
				log.warn("saveSession error, save="+save+", id="+id,e);
			return new DataAccessException(500,e.toString());
		}
		return new DataAccessException(SAVE_FAIL_EXCEPTION,
			"access save fail");
	}

	public void close(ByteBuffer data)
	{
		boolean b=access.canAccess();
		if(log.isInfoEnabled())
			log.info("close, access="+b+", size="+sm.size());
		TimerCenter.getMinuteTimer().remove(collateTimerEvent);

		ArrayList list;
		synchronized(saveList)
		{
			list=new ArrayList(sm.getSessions());
			sm.clear();
		}
		if(b) exitSessions(list);
		attributes.set("sessionAmount",String.valueOf(sm.size()));
		if(log.isInfoEnabled()) log.info("close ok, size="+sm.size());
	}

	/** 运行日志 */
	private String runtimeLog()
	{
		Runtime r=Runtime.getRuntime();
		long memory=r.totalMemory();
		long used=memory-r.freeMemory();
		return "collate ok, size="+sm.size()+", memory="+used+"/"+memory
			+", maxMemory="+r.maxMemory();
	}

	/* @see com.cambrian.game.CloseAble#close() */
	public void close()
	{

	}

	class CollateSelector implements Selector
	{

		boolean remove;
		ArrayList list1=new ArrayList();
		ArrayList list2=new ArrayList();
		long time;

		CollateSelector()
		{
		}

		public int select(Object obj)
		{
			Session s=(Session)obj;
			synchronized(loginList)
			{
				if(loginList.contain(s.getId())) return 0;
			}
			if(this.time<s.getActiveTime())
			{
				list2.add(s);
				return 0;
			}
			list1.add(s);
			if(!remove) return 0;
			saveList.add(s.getId());
			return 1;
		}
	}
}
