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
 * 类说明：消息管理器
 * 
 * @version 1.0
 * @date 2013-6-5
 * @author maxw<woldits@qq.com>
 */
public class MessageManager
{

	/* static fields */
	/** 实例 */
	public static MessageManager instance=new MessageManager();
	
	/* static methods */

	/* fields */
	/** 游戏服务器 */
	DataServer dataServer;
	/** 存储器 */
	MessageSqlAccess access;

	/* constructors */

	/* properties */
	/** 设置游戏服务器 */
	public void setDataServer(DataServer dataServer)
	{
		this.dataServer=dataServer;
	}
	/** 设置数据存取器 */
	public void setAccess(MessageSqlAccess access)
	{
		this.access=access;
	}
	/* init start */

	/* methods */
	
	/** 获取指定服务器的连接 */
	public NioTcpConnect getConnect(int serverId)
	{
		// TODO 多服时添加

		return null;
	}
	/** 发送消息 */
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
	/** 解析 */
	public Message parse(ByteBuffer data)
	{
		Message message=new Message();
		message.bytesRead(data);
		return message;
	}
	
	/** 处理消息 */
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
	/** 保存消息 */
	public void save(Message message)
	{
		access.save(message);
	}
	/* common methods */

	/* inner class */
}
