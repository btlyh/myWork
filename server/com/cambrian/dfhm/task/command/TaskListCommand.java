package com.cambrian.dfhm.task.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：任务-前台发送-获取任务列表
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class TaskListCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		data.clear();
//		player.task.bytesWrite(data);
	}
}
