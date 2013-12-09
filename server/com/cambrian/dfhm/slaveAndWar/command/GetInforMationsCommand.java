package com.cambrian.dfhm.slaveAndWar.command;

import java.util.Collections;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.entity.Information;
import com.cambrian.game.Session;

/**
 * 类说明：获取信息
 * 
 * @author：LazyBear
 */
public class GetInforMationsCommand extends Command
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
		List<Information> informations=player.getIdentity()
			.getInformations();
		Collections.sort(informations);
		data.writeInt(informations.size());
		for(Information information:informations)
		{
			data.writeUTF(TimeKit.dateToString(information.getCreateTime(),
				"yyyy年MM月dd日 HH:mm"));
			data.writeUTF(information.getContent());
		}
	}
}
