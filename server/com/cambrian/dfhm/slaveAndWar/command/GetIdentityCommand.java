package com.cambrian.dfhm.slaveAndWar.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;
import com.cambrian.game.Session;

/**
 * 类说明：身份信息获取
 * 
 * @author：LazyBear
 */
public class GetIdentityCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public void handle(NioTcpConnect connect,ByteBuffer data)
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
		Player bossPlayer=SlaveManager.getInstance().getBossPlayer(player);
		player.getIdentity().BytesWrite(data);
		data.writeInt(player.getFightPoint());
		if(bossPlayer==null)
		{
			data.writeBoolean(false);
		}
		else
		{
			data.writeBoolean(true);
			data.writeUTF(bossPlayer.getNickname());
			data.writeInt(player.getFightPoint());
		}
	}
}
