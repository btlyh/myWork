package com.cambrian.dfhm.instancing.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.AttRecord;
import com.cambrian.game.Session;

/**
 * ÀàËµÃ÷£º
 * @author£ºLazyBear
 * 
 */
public class EnterInstancingCommand extends Command
{

	@Override
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
		data.writeInt(player.getCurIndexForNormalNPC());
		data.writeInt(player.getCurIndexForHardNPC());
		data.writeInt(player.getCurIndexForCorssNPC());
		data.writeInt(player.getAttRecords().size());
		for (AttRecord attRecord : player.getAttRecords())
		{
			attRecord.bytesWrite(data);
		}
	}

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
