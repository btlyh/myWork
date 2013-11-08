package com.cambrian.dfhm.globalboss.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.game.Session;

/**
 * 类说明：世界BOSS活动开始推送消息
 * 
 * @author：LazyBear
 */
public class BossStartNotice extends SendCommand
{

	/* static fields */
	private static final Logger log=Logger.getLogger(BossStartNotice.class);
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
	public void send(Session session,Object[] objs)
	{
		NioTcpConnect connect=session.getConnect();
		if(connect==null)
		{
			if(log.isDebugEnabled())
				log.debug("notice error, connect=null");
			return;
		}
		ByteBuffer data=new ByteBuffer();
		data.writeLong(Long.parseLong(objs[0].toString()));// 剩余时间
		data.writeInt(Integer.parseInt(objs[1].toString()));// BOSS sid;
		connect.send(noticePort,data);
	}
}
