/** */
package com.cambrian.game.ds;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.ConnectProducer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.DataAccessHandler;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.common.util.ArrayList;
import com.cambrian.common.util.ByteBufferThreadLocal;
import com.cambrian.dfhm.GlobalConst;

/**
 * 类说明：
 * 
 * @version 2013-5-9
 * @author HYZ (huangyz1988@qq.com)
 */
public class DirectDSDCAccess implements DSDCAccess
{

	public static final String err=DirectDSDCAccess.class.getName()
		+" getConnect, connect fail";

	private static final Logger log=Logger.getLogger(DirectDSDCAccess.class);
	ConnectProducer connectProducer;

	int loginPort=GlobalConst.DC_LOGIN_PORT;
	int loadPort=GlobalConst.DC_LOAD_PORT;
	int savePort=GlobalConst.DC_SAVE_PORT;
	int checkNicknamePort=GlobalConst.DC_CHECKNICKNAME_PORT;
	int getRandomPort=GlobalConst.DC_RANDOMNAME_PORT;

	ArrayList list=new ArrayList();

	public ConnectProducer getConnectProducer()
	{
		return this.connectProducer;
	}

	public void setConnectProducer(ConnectProducer cp)
	{
		this.connectProducer=cp;
	}

	public int getLoginPort()
	{
		return this.loginPort;
	}

	public void setLoginPort(int port)
	{
		this.loginPort=port;
	}

	public int getLoadPort()
	{
		return this.loadPort;
	}

	public void setLoadPort(int port)
	{
		this.loadPort=port;
	}

	public int getSavePort()
	{
		return this.savePort;
	}

	public void setSavePort(int port)
	{
		this.savePort=port;
	}

	public NioTcpConnect getConnect()
	{
		ConnectProducer cp=this.connectProducer;
		NioTcpConnect c=(cp!=null)?cp.getConnect():null;
		if((c==null)||(!(c.isActive())))
			throw new DataAccessException(420,err);
		return c;
	}

	public boolean canAccess()
	{
		ConnectProducer cp=this.connectProducer;
		NioTcpConnect c=(cp!=null)?cp.getConnect():null;
		if((c==null)||(!(c.isActive())))
		{
			if(log.isWarnEnabled())
				log.warn("canAccess error, connect="+c,null);
			return false;
		}
		try
		{
			// DataAccessHandler.getInstance().access(c,11,null);
			ByteBuffer bb=new ByteBuffer();
			bb.writeInt(222);
			c.send((short)11,bb);
			return true;
		}
		catch(Exception e)
		{
			if(log.isWarnEnabled())
				log.warn("canAccess error, connect="+c,e);
		}
		return false;
	}

	public void login(String uid,String sid,String address)
	{
		NioTcpConnect c=getConnect();
		ByteBuffer bb=ByteBufferThreadLocal.getByteBuffer();
		bb.clear();
		bb.writeUTF(uid);
		bb.writeUTF(sid);
		bb.writeUTF(address);
		c.send((short)loginPort,bb);
	}

	public void save(String id,boolean exit,ByteBuffer data)
	{
		NioTcpConnect c=getConnect();
		ByteBuffer bb=new ByteBuffer();
		bb.clear();
		bb.writeUTF(id);
		bb.writeBoolean(exit);
		if(data!=null)
		{
			bb.writeBoolean(true);
			bb.write(data.getArray(),data.offset(),data.length());
		}
		else
		{
			bb.writeBoolean(false);
		}
		c.send((short)savePort,bb);
	}
	
	
	public ByteBuffer load(String uid,String nickName,String userName)
	{
		NioTcpConnect c=getConnect();
		ByteBuffer bb=ByteBufferThreadLocal.getByteBuffer();
		bb.clear();
		bb.writeUTF(uid);
		bb.writeUTF(nickName);
		bb.writeUTF(userName);
		return DataAccessHandler.getInstance().access(c,loadPort,bb);
	}
	
	/**
	 * 获取随机昵称
	 */
	public ByteBuffer getRandomName()
	{
		NioTcpConnect c=getConnect();
		ByteBuffer bb=ByteBufferThreadLocal.getByteBuffer();
		bb.clear();
		return DataAccessHandler.getInstance().access(c,getRandomPort,bb);
	}
}