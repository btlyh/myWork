package com.cambrian.dfhm.bag.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：背包-前台发送-获取道具列表
 * 
 * @version 1.0
 * @date 2013-5-30
 * @author maxw<woldits@qq.com>
 */
public class PropListCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		data.clear();
		player.getCardBag().bytesWrite(data);
	}
}
