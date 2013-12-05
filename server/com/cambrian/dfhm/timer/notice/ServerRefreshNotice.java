package com.cambrian.dfhm.timer.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.dfhm.common.entity.PlayerInfo;
import com.cambrian.game.Session;

/**
 * ��˵����������ˢ������
 * @author��Zmk
 * 
 */
public class ServerRefreshNotice extends SendCommand
{

	/* static fields */
	private static final Logger log=Logger.getLogger(TokenSendNotice.class);

	/* static methods */

	/* fields */
	/** ��Ϣ֪ͨ�˿� */
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
		PlayerInfo playerInfo = (PlayerInfo)objs[0];
		playerInfo.bytesWrite(data);
		connect.send(noticePort,data);
	}
}
