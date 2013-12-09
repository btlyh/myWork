package com.cambrian.dfhm.slaveAndWar.command;

import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;
import com.cambrian.game.Session;

/**
 * ��˵������ȡ���������б�
 * 
 * @author��LazyBear
 */
public class GetHelpListCommand extends Command
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
		List<Player> friends=SlaveManager.getInstance().getHelpList(player);
		data.writeInt(friends.size());
		for(Player tarPlayer:friends)
		{
			data.writeInt(tarPlayer.getFightPoint());// Ŀ��ս����
			data.writeInt((int)tarPlayer.getUserId());// Ŀ��userID
			data.writeUTF(tarPlayer.getNickname());// Ŀ������
			data.writeUTF(tarPlayer.getIdentity().getGradeName());// Ŀ���������
		}
	}
}
