package com.cambrian.dfhm.bag.command;

import com.cambrian.common.actor.ProxyActorProcess;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.GlobalConst;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.ProxySendCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：背包-前台发送-出售道具
 * 
 * @version 1.0
 * @date 2013-5-30
 * @author maxw<woldits@qq.com>
 */
public class SellPropCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		int sid=data.readInt();
		int value=data.readInt();
		data.clear();
		ProxyActorProcess.handle(GlobalConst.PROCESS_BAG_SELLPROP,player,new int[]{sid,value});
		
		ProxySendCommand.proxy.handle(GlobalConst.COMMAND_BAG_REMOVEPROP,session,new Object[]{sid,value});
	}
}
