/** */
package com.cambrian.game.cc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cambrian.common.field.ByteField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.LongField;
import com.cambrian.common.field.StringField;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.common.sql.SqlKit;
import com.cambrian.common.util.TextKit;
import com.cambrian.game.util.DBListManager;

/***
 * 类说明：认证中心数据库访问器
 * 
 * @version 2013-5-6
 * @author HYZ (huangyz1988@qq.com)
 */
public class DBAccess
{

	/* static fields */
	/** 日志记录 */
	private static final Logger log=Logger.getLogger(DBAccess.class);

	/* fields */
	/** 表名 */
	String table="user";
	/** id的列名 */
	String idColumn="username";
	/** 密码的列名 */
	String pwColumn="passwd";
	/** uid的列名 */
	String uidColumn="userid";
	/** 数据库连接管理器列表（合服） */
	DBListManager dbList;
	/** 数据库连接管理器 */
	ConnectionManager cm;

	/* properties */
	/** 设置连接管理器 */
	public void setDBListManager(DBListManager dbList)
	{
		this.dbList=dbList;
	}
	/** 设置数据库连接管理器 */
	public void setConnectionManager(ConnectionManager cm)
	{
		this.cm=cm;
	}

	/** 获得表名 */
	public String getTable()
	{
		return table;
	}

	/** 设置表名 */
	public void setTable(String table)
	{
		this.table=table;
	}

	/** 获得id的列名 */
	public String getIdColumn()
	{
		return idColumn;
	}

	/** 设置id的列名 */
	public void setIdColumn(String c)
	{
		idColumn=c;
	}

	/** 获得密码的列名 */
	public String getPWColumn()
	{
		return pwColumn;
	}

	/** 设置密码的列名 */
	public void setPWColumn(String c)
	{
		pwColumn=c;
	}

	/** 获得uid的列名 */
	public String getUidColumn()
	{
		return uidColumn;
	}

	/** 设置uid的列名 */
	public void setUidColumn(String c)
	{
		uidColumn=c;
	}

	/* methods */
	/** 注册帐号 */
	public void register(String str,int gameserver)
	{
		String[] strs=TextKit.split(str,":");
		if(strs.length==0||strs.length%2!=0)
			throw new DataAccessException(
				DataAccessException.SERVER_CMSG_ERROR,
				"invalid register message");
//		addInviteTimes(gameserver,strs);
		StringBuffer sql=new StringBuffer();
		sql.append("insert into ").append(getTable());
		sql.append(" set ");
		sql.append("created=")
			.append((int)(System.currentTimeMillis()/1000)).append(",");
//		String username=null;
//		boolean b=true;
//		for(int i=0;i<strs.length;i+=2)
//		{
//			if(strs[i].equals("nickname"))
//			{
//				b=false;
//			}
//			else if(strs[i].equals("username"))
//			{
//				username=strs[i+1];
//			}
//		}
//		if(b) sql.append("nickname='").append(username).append("',");
		for(int i=0;i<strs.length;i+=2)
		{
			sql.append(strs[i]);
			if(strs[i+1]==null)
				sql.append("=").append(strs[i+1]).append("");
			else
				sql.append("='").append(strs[i+1]).append("'");
			if(i+2<strs.length) sql.append(",");
		}
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			SqlKit.execute(cm,sql.toString());
			if(log.isDebugEnabled()) log.debug("register ok,"+sql);
		}
		catch(Exception e)
		{
			if(log.isDebugEnabled()) log.debug("register error,"+sql);
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"regedit, db error");
		}
	}

	/** 校验帐号密码，返回uid */
	public String valid(String id,String pw,int serverId,String address)
	{
		Field key=FieldKit.create(idColumn,id);
		Field[] array=new Field[3];
		array[0]=FieldKit.create(uidColumn,0);
		array[1]=FieldKit.create(idColumn,(String)null);
		array[2]=FieldKit.create(pwColumn,(String)null);
		Fields fields=new Fields(array);
		int t=DBKit.get(table,cm,key,fields);
		if(t==DBKit.EXCEPTION||t==DBKit.RESULTLESS)
		{
			if(log.isDebugEnabled())
				log.debug(" valid fail, id="+id+", pw="+pw);
			return null;
		}
		String pwd=((StringField)array[2]).value;
		if((pwd==null&&pw!=null)||(pwd!=null&&pw==null)
			||(pwd!=null&&(!pwd.equalsIgnoreCase(pw))))
		{
			if(log.isDebugEnabled())
				log.debug(" valid fail, id="+id+", pw="+pw);
			return null;
		}
		long uid=((IntField)array[0]).value;
		uid|=(long)serverId<<32;
		return uid+":"+serverId;
	}

	/** 通过youkia账号获得玩家主账号 */
	public String getIdValue(int gameserver,String usernameyk)
	{
		String sql="select "+getIdColumn()+" from "+getTable()
			+" where usernameyk='"+usernameyk+"'";
		Fields fields;
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			fields=SqlKit.query(cm,sql);
		}
		catch(Exception e)
		{
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"valid, db error");
		}
		if(fields==null) return null;
		return ((StringField)fields.getFields()[0]).value;
	}

	/** 校验帐号密码，返回uid */
	public String valid37Wan(String id,int gameserver,int cid,
		String usernameyk)
	{
		String sql="select "+getUidColumn()+",gameserver"
			+",forbidtime,usernameyk,member from "+getTable()+" where "
			+getIdColumn()+"='"+id+"'";
		Fields fields;
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			fields=SqlKit.query(cm,sql);
		}
		catch(Exception e)
		{
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"valid, db error");
		}
		if(fields==null)
		{
			String str="username:"+id
				+":password:C4CA4238A0B923820DCC509A6F75849B:gameserver:"+0
				+":member:"+cid;
			if(usernameyk!=null) str+=(":usernameyk:"+usernameyk);
			register(str,gameserver);
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			fields=SqlKit.query(cm,sql);
			if(fields==null) return null;
		}
		Field array[]=fields.getFields();
		int uid=((IntField)array[0]).value;
		int gameserverid=((IntField)array[1]).value;
		long forbidtime=((LongField)array[2]).value;
		usernameyk=((StringField)array[3]).value;
		int member=((ByteField)array[4]).value;
		if(uid==0)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"valid, db uidColumn must be no null");
		if(System.currentTimeMillis()<forbidtime)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"valid, forbidtime");
		else if(gameserverid!=gameserver&&(gameserverid!=0))
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"valid, db gameserver error");
		else if(member!=0&&cid!=1&member!=cid)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"valid, db cid error");
		else
			return uid+((long)gameserver<<32)+":"+gameserverid+":"
				+usernameyk+":"+member;
	}

	/** 校验帐号密码，只用于游戏服务器加载不在线的玩家数据 */
	public String valid(String id,int gameserver,String address)
	{
		String sql="select "+getPWColumn()+","+getUidColumn()
			+",gameserver from "+getTable()+" where "+getIdColumn()+"='"+id
			+"'";
		Fields fields;
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			fields=SqlKit.query(cm,sql);
		}
		catch(Exception e)
		{
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"valid, db error");
		}
		if(fields==null) return null;
		Field array[]=fields.getFields();
		String pw=(String)array[0].getValue();
		int uid=((IntField)array[1]).value;
		int gameserverid=((IntField)array[2]).value;
		if(uid==0)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"valid, db uidColumn must be no null");
		else if(gameserverid!=gameserver&&(gameserverid!=0))
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,
				"valid, db gameserver error");
		else
			return uid+((long)gameserver<<32)+":"+gameserverid+":"+pw;
	}

	/** 更新用户信息 */
	public boolean update(int gameserver,String id,String str)
	{
		String[] strs=TextKit.split(str,":");
		id=TextKit.split(id,":")[0];
		if(strs.length==0||strs.length%2!=0)
			throw new DataAccessException(
				DataAccessException.SERVER_CMSG_ERROR,
				"invalid update message");

		StringBuffer sql=new StringBuffer();
		sql.append("update ").append(getTable());
		sql.append(" set ");
		for(int i=0;i<strs.length;i+=2)
		{
			sql.append(strs[i]).append("='").append(strs[i+1]).append("'");
			if(i+2<strs.length) sql.append(",");
		}
		sql.append(" where ");
		sql.append(getIdColumn()).append("='").append(id).append("'");

		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			SqlKit.execute(cm,sql.toString());
			if(log.isDebugEnabled()) log.debug("update ok, sql:="+sql);
		}
		catch(Exception e)
		{
			if(log.isWarnEnabled())
				log.warn("update error, id="+id+", sqlstr="+sql,e);
			return false;
		}
		return true;
	}

	/** 查看封号状态 */
	public long getForbidStatus(String username,int gameserver)
	{
		String sql="select forbidtime from "+getTable()+" where "
			+getIdColumn()+"='"+username+"'";
		Fields fields;
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			fields=SqlKit.query(cm,sql);
		}
		catch(Exception e)
		{
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"valid, db error");
		}
		if(fields==null) return 0;
		return ((LongField)fields.get("forbidtime")).value;
	}

	/** (合服修改) 查看服务器是否存在该玩家 */
	public int getUserServerId(int gameserver,String username)
	{
		String sql="select gameserver from "+getTable()+" where "
			+getIdColumn()+"='"+username+"'";
		Fields fields;
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			fields=SqlKit.query(cm,sql);
		}
		catch(Exception e)
		{
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"valid, db error");
		}
		if(fields==null) return 0;
		return ((IntField)fields.get("gameserver")).value;
	}

	/** 查看玩家信息 1账号,2昵称,3userId */
	public ByteBuffer getUserInfo(int type,int gameserver,String[] strs)
	{
		ByteBuffer data=new ByteBuffer();
		if(strs.length==0) return data;
		StringBuffer strb=new StringBuffer();
		strb.append("select ").append(getIdColumn()).append(",nickname,")
			.append(getUidColumn()).append(",gameserver from ")
			.append(getTable()).append(" where ");
		if(type==1)
			strb.append(getIdColumn());
		else if(type==2)
			strb.append("nickname");
		else
		{
			strb.append(getUidColumn());
			// TODO 合服修改
			for(int i=0;i<strs.length;i++)
			{
				long num=TextKit.parseLong(strs[i]);
				strs[i]=(int)num+"";
			}
		}
		strb.append(" in(");

		for(int i=0;i<strs.length;i++)
		{
			if(i>0) strb.append(",");
			if(type==1||type==2) strb.append("'");
			strb.append(strs[i]);
			if(type==1||type==2) strb.append("'");
		}
		// TODO-----------------------
		strb.append(") and gameserver=").append(gameserver);
		try
		{
			// TODO---------------如何确定cm--------------------
			ConnectionManager cm=(ConnectionManager)dbList
				.getConnectionManager(gameserver);
			cm=this.cm;
			Fields[] fields=SqlKit.querys(cm,strb.toString());
			if(fields==null) return data;
			data.writeInt(fields.length);
			for(int i=0;i<fields.length;i++)
			{
				String id=((StringField)fields[i].get(getIdColumn())).value;
				String nickname=((StringField)fields[i].get("nickname")).value;
				int uid=((IntField)fields[i].get(getUidColumn())).value;
				int gameserver_=((IntField)fields[i].get("gameserver")).value;
				data.writeUTF(id);
				data.writeUTF(nickname);
				data.writeUTF(uid+((long)gameserver<<32)+"");
				data.writeUTF(""+gameserver_);
			}
		}
		catch(Exception e)
		{
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"valid, db error");
		}
		return data;
	}

	/** 增加成功邀请次数 */
	public void addInviteTimes(int gameserver,String[] strs)
	{
		String inviteuser=null;
		int i=0;
		for(;i<strs.length;i+=2)
		{
			if(strs[i].equals("inviter"))
			{
				inviteuser=strs[i+1];
				break;
			}
		}
		if(inviteuser==null) return;

		String sql="select * from "+getTable()+" where nickname='"
			+inviteuser+"'";
		// TODO---------------如何确定cm--------------------
		ConnectionManager cm=(ConnectionManager)dbList
			.getConnectionManager(gameserver);
		cm=this.cm;
		Connection c=cm.getConnection();
		Statement st=null;
		ResultSet rs=null;
		try
		{
			st=c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			rs=st.executeQuery(sql);

			if(rs.next())
			{
				rs.updateInt("invitetimes",rs.getInt("invitetimes")+1);
				rs.updateRow();
			}
			else
			{
				strs[i+1]=null;
			}
		}
		catch(SQLException e)
		{
			strs[i+1]=null;
			e.printStackTrace();
		}
		finally
		{
			DBKit.close(st,rs);
			DBKit.close(c);
		}
	}
}
