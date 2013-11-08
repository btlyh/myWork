package com.cambrian.dfhm.battle.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.game.Session;

/**
 * 类说明：战斗消息通知
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class BattleNotice
{

	private static final Logger log=Logger.getLogger(BattleNotice.class);

	private static BattleNotice instence;

	public static BattleNotice getInstence()
	{
		if(instence==null) instence=new BattleNotice();
		return instence;
	}

	public static void setPort(short port)
	{
		if(instence==null) instence=new BattleNotice();
		instence.noticePort=port;
	}

	/** 消息通知端口 */
	short noticePort;

	private BattleNotice()
	{

	}

	public void setNoticePort(short port)
	{
		noticePort=port;
	}

	public void notice(Session session,int type,String msg)
	{
		NioTcpConnect c=session.getConnect();
		if(c==null)
		{
			if(log.isDebugEnabled())
				log.debug("notice error, connect=null, msg="+msg);
			return;
		}
		ByteBuffer data=new ByteBuffer();
		data.writeShort(noticePort);
		data.writeInt(type);
		data.writeUTF(msg);
		// c.send(data);
	}
}
