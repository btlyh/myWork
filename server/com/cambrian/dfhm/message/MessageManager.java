package com.cambrian.dfhm.message;

import com.cambrian.common.actor.ProxyActorProcess;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.GlobalConst;
import com.cambrian.dfhm.back.BackKit;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;


/**
 * ��˵������Ϣ������
 * 
 * @version 1.0
 * @date 2013-6-5
 * @author maxw<woldits@qq.com>
 */
public class MessageManager
{

	/* static fields */
	/** ʵ�� */
	public static MessageManager instance=new MessageManager();
	
	/* static methods */

	/* fields */
	/** ��Ϸ������ */
	DataServer dataServer;
	/** �洢�� */
	MessageSqlAccess access;

	/* constructors */

	/* properties */
	/** ������Ϸ������ */
	public void setDataServer(DataServer dataServer)
	{
		this.dataServer=dataServer;
	}
	/** �������ݴ�ȡ�� */
	public void setAccess(MessageSqlAccess access)
	{
		this.access=access;
	}
	/* init start */

	/* methods */
	
	/** ��ȡָ�������������� */
	public NioTcpConnect getConnect(int serverId)
	{
		// TODO ���ʱ����

		return null;
	}
	/** ������Ϣ */
	public void send(Message message)
	{
		if(dataServer.isThis(message.dest))
		{
			handle(message);
		}
		else
		{
			ByteBuffer data=new ByteBuffer();
			message.bytesWrite(data);
			int serverId=BackKit.getDBId(message.dest);
			getConnect(serverId).send(GlobalConst.COMMAND_MESSAGE_MESSAGE,data);
		}
	}
	/** ���� */
	public Message parse(ByteBuffer data)
	{
		Message message=new Message();
		message.bytesRead(data);
		return message;
	}
	
	/** ������Ϣ */
	public void handle(Message message)
	{
		// TODO Auto-generated method stub
		save(message);
		Session session=dataServer.getSessionMap().get(message.dest+"");
		if(session!=null&&session.getSource()!=null)
		{
			Player player=(Player)session.getSource();
			player.message.addMessage(message);
			//boolean b=
			if(message.pid!=GlobalConst.PROCESS_NULL)
				ProxyActorProcess.handle(message.pid,player,message);
		}
	}
	/** ������Ϣ */
	public void save(Message message)
	{
		access.save(message);
	}
	/* common methods */

	/* inner class */
}