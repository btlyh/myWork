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
 * 类说明：获取敌人列表命令
 * 
 * @author：LazyBear
 */
public class GetEnemyCommand extends Command
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
		boolean isSave=data.readBoolean();// 是否是拔刀相助
		List<Player> players=SlaveManager.getInstance().getEnemy(player,
			isSave);
		data.writeInt(players.size());
		for(Player tarPlayer:players)
		{
			data.writeInt(tarPlayer.getFightPoint());// 目标战斗力
			data.writeInt((int)tarPlayer.getUserId());// 目标userID
			data.writeUTF(tarPlayer.getNickname());// 目标名称
			data.writeUTF(tarPlayer.getIdentity().getGradeName());// 目标身份名称
			data.writeInt(tarPlayer.getIdentity().getSlaveList().size());// 马仔数量
		}
	}
}
