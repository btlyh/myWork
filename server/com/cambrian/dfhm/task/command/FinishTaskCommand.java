package com.cambrian.dfhm.task.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.task.Task;
import com.cambrian.game.Session;

/**
 * 类说明：任务-前台发送-完成任务
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class FinishTaskCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		int taskid=data.readInt();
		data.clear();
//		Task task=player.task.getTask(taskid);
//		task.finish(player);
//		player.task.refresh(player);
	}
}
