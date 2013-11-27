package com.cambrian.dfhm.rankings.command;

import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.rankings.entity.Rankings;
import com.cambrian.dfhm.rankings.logic.RankingsManager;
import com.cambrian.game.Session;

/**
 * 类说明：进入排行榜
 * 
 * @author：Zmk
 * 
 */
public class EnterRankingsCommand extends Command
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
		int version = data.readInt();
		int rankingsVersion = RankingsManager.getInstance().getVersion();
		Map<String, Object> resultMap = RankingsManager.getInstance()
				.enterRankings(player);
		Rankings rankings = (Rankings) resultMap.get("rankings");
		data.writeInt(rankingsVersion);
		if (version != rankingsVersion)
		{
			rankings.bytesWrite(data);
		}
		data.writeInt(rankings.getPlayerRankInfo(player,rankings.getStoryRankings()));
		data.writeInt(player.getCurIndexForNormalNPC()-1);
		data.writeInt(rankings.getPlayerRankInfo(player,rankings.getChallengeRankings()));
		data.writeInt(player.getPlayerInfo().getHardNPCIndex());
		data.writeInt(rankings.getPlayerRankInfo(player,rankings.getPayRankings()));
		data.writeInt(player.getPlayerInfo().getPayRMB());
		Card card = (Card) resultMap.get("card");
		data.writeInt(rankings.getCardRankInfo(card));
		data.writeInt(card.getId());
		data.writeInt(card.getZhandouli());
	}
}
