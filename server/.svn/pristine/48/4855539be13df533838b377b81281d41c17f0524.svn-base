package com.cambrian.dfhm.message.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：消息-前台发送-移除消息
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class RemoveMessageCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		int len=data.readInt();
		long[] values=new long[len];
		for(int i=0;i<values.length;i++)
		{
			values[i]=data.readLong();
		}
		data.clear();
		player.message.removeMessages(values);
	}
}
