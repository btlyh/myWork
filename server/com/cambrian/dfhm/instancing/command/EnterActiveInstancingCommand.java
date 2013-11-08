package com.cambrian.dfhm.instancing.command;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.logic.InstancingManager;
import com.cambrian.game.Session;

/**
 * ��˵���� ��������
 * 
 * @author��LazyBear
 * 
 */
public class EnterActiveInstancingCommand extends Command
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
		ArrayList<Integer> openNPCSids = InstancingManager.getInstance().enterInstancing();
		data.writeInt(openNPCSids.size());
		for (Integer sid : openNPCSids)
		{
			data.writeInt(sid);
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