package com.cambrian.dfhm.message.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.message.Message;
import com.cambrian.dfhm.message.MessageManager;

/**
 * 类说明：消息-后台间发送-消息
 * 
 * @version 1.0
 * @date 2013-6-6
 * @author maxw<woldits@qq.com>
 */
public class MessageCommand extends Command
{
	@Override
	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		Message message=MessageManager.instance.parse(data);
		MessageManager.instance.handle(message);
	}
}
