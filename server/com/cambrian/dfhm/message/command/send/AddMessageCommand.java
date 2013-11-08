package com.cambrian.dfhm.message.command.send;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.dfhm.message.Message;
import com.cambrian.game.Session;


/**
 * 类说明：消息-后台发送-新增消息
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class AddMessageCommand extends SendCommand
{
	@Override
	public void send(Session session,Object[] objs)
	{
		// TODO Auto-generated method stub
		ByteBuffer data=new ByteBuffer();
		Message message=(Message)objs[0];
		message.bytesWrite(data);
		session.getConnect().send(id,data);
	}
}
