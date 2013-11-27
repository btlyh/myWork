package com.cambrian.dfhm.armyCamp.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.armyCamp.logic.ArmyCampManager;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * ÀàËµÃ÷£ºÏÐ¹ä
 * @author£ºZmk
 * 
 */
public class WalkAroundCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data)
	{
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
		int anyOneOnline = 0;
		Player tarPlayer = ArmyCampManager.getInstance().walkAround(player, session);
		if (tarPlayer != null)
		{
			anyOneOnline = 1;
		}
		System.err.println("WalkAround------------anyOneOnline===="+anyOneOnline);
		//System.err.println("tarPlayerName====="+tarPlayer.getNickname());
		data.writeInt(anyOneOnline);
		if (anyOneOnline != 0)
		{
			data.writeUTF(tarPlayer.getNickname());
			data.writeInt(tarPlayer.getVipLevel());
			tarPlayer.getArmyCamp().bytesWrite(data);
		}
	}
}
