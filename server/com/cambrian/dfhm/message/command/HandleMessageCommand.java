package com.cambrian.dfhm.message.command;

import com.cambrian.common.actor.ProxyActorProcess;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.message.Message;
import com.cambrian.game.Session;

/**
 * 类说明：消息-前台发送-处理消息
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class HandleMessageCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		long uuid=data.readLong();
		data.clear();
		Message message=player.message.getMessage(uuid);
		ProxyActorProcess.handle(message.pid,player,message);
	}
}
