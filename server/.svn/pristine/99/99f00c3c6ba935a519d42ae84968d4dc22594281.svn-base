package com.cambrian.dfhm.qualifying.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.qualifying.logic.QualifyingManager;
import com.cambrian.game.Session;

/**
 * 类说明：购买挑战次数
 * @author：Zmk
 * 
 */
public class BuyDuelTimesCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	public void handle(NioTcpConnect connect, ByteBuffer data)
	{
		Session session = connect.getSession();
		if (log.isDebugEnabled())
		{
			log.debug("session = " + session);
		}
		if (session == null)
		{
			throw new DataAccessException(601, Lang.F9000_SDE);
		}
		Player player = (Player) session.getSource();
		if (player == null)
		{
			throw new DataAccessException(601, Lang.F9000_SDE);
		}
		int useGold = data.readInt();
		QualifyingManager.getInstance().buyDuelTimes(player, useGold);
	}
}
