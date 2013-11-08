package com.cambrian.dfhm.bag.command.send;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.game.Session;


/**
 * 类说明：背包-后台发送-移除道具
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class RemovePropCommand extends SendCommand
{
	@Override
	public void send(Session session,Object[] objs)
	{
		ByteBuffer data=new ByteBuffer();
		int propid=(Integer)objs[0];
		int num=(Integer)objs[1];
		data.writeInt(propid);
		data.writeInt(num);
		session.getConnect().send(id,data);
	}
}
