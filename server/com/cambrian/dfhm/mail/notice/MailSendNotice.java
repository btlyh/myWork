package com.cambrian.dfhm.mail.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.game.Session;

/**
 * ��˵��: �ʼ�֪ͨ
 * 
 * @author��LazyBear
 * 
 */
public class MailSendNotice extends SendCommand
{

	/* static fields */
	private static final Logger log = Logger.getLogger(MailSendNotice.class);

	private static MailSendNotice instance = new MailSendNotice();

	/* static methods */
	public static MailSendNotice getInstance()
	{
		return instance;
	}
	
	/* fields */
	/** ��Ϣ֪ͨ�˿�*/
	short noticePort;
	/* constructors */

	/* properties */
	public void setPort(short noticePort)
	{
		this.noticePort = noticePort;
	}
	/* init start */

	/* methods */
	/**
	 * ����δ���ʼ�����
	 * @param session �Ự
	 * @param num ����
	 */
	@Override
	public void send(Session session, Object[] objs)
	{
		NioTcpConnect connect = session.getConnect();
		if(connect==null)
		{
			if(log.isDebugEnabled())
				log.debug("notice error, connect=null");
			return;
		}
		ByteBuffer data = new ByteBuffer();
		data.writeInt(Integer.parseInt(objs[0].toString()));
		connect.send(noticePort,data);
	}
}