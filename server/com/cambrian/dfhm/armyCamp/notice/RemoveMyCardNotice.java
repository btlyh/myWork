package com.cambrian.dfhm.armyCamp.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.game.Session;

/**
 * 类说明：自动移除卡牌推送
 * @author：Zmk
 * 
 */
public class RemoveMyCardNotice extends SendCommand
{

	/* static fields */
	private static final Logger log=Logger.getLogger(RemoveMyCardNotice.class);
	/* static methods */

	/* fields */
	/** 消息通知端口 */
	short noticePort;
	/* constructors */

	/* properties */
	public void setPort(short noticePort)
	{
		this.noticePort=noticePort;
	}
	/* init start */

	/* methods */
	@Override
	public void send(Session session, Object[] objs)
	{
		NioTcpConnect connect=session.getConnect();
		if(connect==null)
		{
			if(log.isDebugEnabled())
				log.debug("notice error, connect=null");
			return;
		}
		ByteBuffer data=new ByteBuffer();
		data.writeInt(Integer.parseInt(objs[0].toString()));
		System.err.println("<------------------推送------------------>");
		System.err.println("uid====="+Integer.parseInt(objs[0].toString()));
		connect.send(noticePort,data);
	}
}
