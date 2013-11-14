package com.cambrian.dfhm.qualifying.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.qualifying.logic.QualifyingManager;
import com.cambrian.game.Session;

/**
 * 类说明：排位赛决斗
 * 
 * @author：Zmk
 * 
 */
public class DuelCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
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
		String tarName = data.readUTF();
		Map<String, Object> resultMap = QualifyingManager.getInstance().duel(
				player, tarName);
		int point = (Integer) resultMap.get("point");
		BattleCard[] battleCards = (BattleCard[]) resultMap.get("battleCards");
		if (battleCards == null)
		{
			data.writeInt(0);
			data.writeInt(point);
		} else
		{
			data.writeInt(1);
			List<BattleCard> bCards = new ArrayList<BattleCard>();
			for (BattleCard battleCard : battleCards)
			{
				if (battleCard != null)
					bCards.add(battleCard);
			}
			data.writeInt(bCards.size());
			for (BattleCard battleCard : bCards)
			{
				battleCard.bytesWrite(data);
			}
			@SuppressWarnings("unchecked")
			ArrayList<Integer> record = (ArrayList<Integer>) resultMap.get("record");
			System.out.println(record);
			for (Integer integer : record)
			{
				data.writeInt(integer);
			}
			data.writeInt(point);
		}
	}
}
