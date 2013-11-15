package com.cambrian.dfhm.slaveAndWar.command;

import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;
import com.cambrian.game.Session;

/**
 * 类说明：攻击敌人命令
 * 
 * @author：LazyBear
 */
public class AttEnemyCommand extends Command
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
		int userId=data.readInt();
		boolean isSave=data.readBoolean();// 是否拔刀相助
		Map<String,Object> result=SlaveManager.getInstance().attEnemy(
			player,userId,isSave);

		@SuppressWarnings("unchecked")
		List<Integer> record=(List<Integer>)result.get("resultList");
		Formation formation=(Formation)result.get("resultList");
		formation.bytesWrite(data);
		data.writeInt(record.size());
		for(Integer integer:record)
		{
			data.writeInt(integer);
		}
	}
}
