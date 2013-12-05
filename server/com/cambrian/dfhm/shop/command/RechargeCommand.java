package com.cambrian.dfhm.shop.command;

import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.shop.logic.RechargeManager;
import com.cambrian.game.Session;

public class RechargeCommand  extends Command
{

	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data) {
		Session session=connect.getSession();
		if(log.isDebugEnabled())
		{
			log.debug("session = "+session);
		}
		if(session==null)
		{
			throw new DataAccessException(601,Lang.F9000_SDE);
		}
		Player player=(Player)session.getSource();
		if(player==null)
		{
			throw new DataAccessException(601,Lang.F9000_SDE);
		}
		int gold = data.readInt();		
		Map<String,Object>rtmap = RechargeManager.getInstance().rechargeGold(player, gold);		
		data.clear();
		data.writeInt(Integer.parseInt(rtmap.get("level").toString()));
		data.writeInt(Integer.parseInt(rtmap.get("exp").toString()));
		
	}

}
