package com.cambrian.dfhm.theater.back;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

public class GetPlayerSatge extends Command
{

	@Override
	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		Session session=connect.getSession();
		if(log.isDebugEnabled())
			log.debug("session="+session);
		if(session==null) throw new DataAccessException(601,Lang.F9000_SDE);
		Player player=(Player)session.getSource();
		if(player==null) throw new DataAccessException(601,Lang.F9000_SDE);
		int plantId=data.readInt();
	}

}
