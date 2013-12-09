package com.cambrian.dfhm.slaveAndWar.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.dfhm.globalboss.notice.BossEndNotice;
import com.cambrian.game.Session;

/**
 * ��˵�����¼���Ϣ����
 * 
 * @author��LazyBear
 */
public class EventMessageNotice extends SendCommand
{

	/* static fields */
	private static final Logger log=Logger.getLogger(BossEndNotice.class);
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
		data.writeInt(Integer.parseInt(objs[0].toString()));
		connect.send(noticePort,data);
	}
}
