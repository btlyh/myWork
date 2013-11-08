package com.cambrian.dfhm.globalboss.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.game.Session;

/**
 * 类说明：世界BOSS活动结束 推送消息
 * 
 * @author：Sebastian
 */
public class BossEndNotice extends SendCommand
{

	/* static fields */
	private static final Logger log=Logger.getLogger(BossEndNotice.class);
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
		data.writeUTF(objs[0].toString());
		connect.send(noticePort,data);
	}
}
