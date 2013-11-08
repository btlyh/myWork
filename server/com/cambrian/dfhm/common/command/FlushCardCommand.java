package com.cambrian.dfhm.common.command;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.common.entity.TakeCardRecord;
import com.cambrian.dfhm.common.logic.LuckBoxManager;
import com.cambrian.game.Session;

/**
 * 类说明：刷新
 * 
 * @author：LazyBear
 */
public class FlushCardCommand extends Command
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
		int type=data.readInt();
		data.clear();
		long surplusTime=LuckBoxManager.getInstance(
			).flushCardList(player,
			type);
		data.writeLong(surplusTime);
		data.writeInt(player.getPlayerInfo().getBestCardSid());
		ArrayList<TakeCardRecord> cardRecords=player.getPlayerInfo()
			.getTakeCardRecords();
		data.writeInt(cardRecords.size());
		for(TakeCardRecord cardRecord:cardRecords)
		{
			cardRecord.BytesWrite(data);
		}
		data.writeInt(player.getPlayerInfo().getLuckBoxSid());
		data.writeBoolean(player.getPlayerInfo().isFirst());
	}
}
