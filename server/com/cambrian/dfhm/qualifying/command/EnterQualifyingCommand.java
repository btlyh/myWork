package com.cambrian.dfhm.qualifying.command;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.qualifying.entity.QualifyingInfo;
import com.cambrian.dfhm.qualifying.logic.QualifyingManager;
import com.cambrian.game.Session;

/**
 * 类说明：进入排行榜
 * @author：Zmk
 * 
 */
public class EnterQualifyingCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@SuppressWarnings("unchecked")
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
		Map<String, Object> resultMap = QualifyingManager.getInstance().enterQualifying(player);
		List<QualifyingInfo> topList = (List<QualifyingInfo>)resultMap.get("topList");
		List<QualifyingInfo> duelList = (List<QualifyingInfo>)resultMap.get("duelList");
		Collections.sort(duelList);
		int index = (Integer)resultMap.get("index");
		data.writeInt(player.getPlayerInfo().getCanTakePoint());
		if (player.getPlayerInfo().getCanTakePoint() != 1)
		{
			int point = QualifyingManager.getInstance().getPointGift(player);
			data.writeInt(point);
		}
		data.writeInt(player.getPlayerInfo().getDuelBuyTimes());
		data.writeInt(topList.size());
		for (QualifyingInfo qualifyingInfo : topList)
		{
			qualifyingInfo.bytesWrite(data);
		}
		data.writeInt(duelList.size());
		for (QualifyingInfo qualifyingInfo : duelList)
		{
			qualifyingInfo.bytesWrite(data);
		}
		data.writeInt(player.getPlayerInfo().getEnemyList().size());
		for (String name : player.getPlayerInfo().getEnemyList())
		{
			data.writeUTF(name);
		}
		data.writeInt(index);
		data.writeInt(player.getPlayerInfo().getNormalPoint());
		data.writeInt(player.getPlayerInfo().getDuelFreeTimes());
	}
}
