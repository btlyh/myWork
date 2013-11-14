package com.cambrian.dfhm.globalboss.notice;

import java.util.ArrayList;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.game.Session;

/**
 * 类说明：世界BOSS自动战斗数据推送
 * 
 * @author：LazyBear
 */
public class BossAutoAttNotice extends SendCommand
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
		if(objs[0] instanceof ArrayList)
		{
			@SuppressWarnings("unchecked")
			ArrayList<Integer> record=(ArrayList<Integer>)objs[0];
			System.err.println(record);
			for(Integer integer:record)
			{
				data.writeInt(integer);
			}
		}
		connect.send(noticePort,data);
	}
}
