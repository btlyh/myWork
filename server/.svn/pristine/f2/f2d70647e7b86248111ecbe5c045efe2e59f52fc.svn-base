package com.cambrian.dfhm.common.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.common.logic.UsePointsManager;
import com.cambrian.game.Session;

/**
 * 类说明：竞技场积分兑换
 * @author：Zmk
 * 
 */
public class ArenaGiftCommand extends Command
{
	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data)
	{
		Session session = connect.getSession();
		if (log.isDebugEnabled())
		{
			log.debug("session = "+session);
		}
		if (session == null) 
		{
			throw new DataAccessException(601, Lang.F9000_SDE);
		}
		Player player = (Player)session.getSource();
		if (player == null) 
		{
			throw new DataAccessException(601, Lang.F9000_SDE);
		}
		int needPoint = data.readInt();
		data.clear();
		int[] gift = UsePointsManager.getInstance().arenaGift(player, needPoint);
		data.writeInt(gift[0]); //奖品类型或卡牌SID
		System.err.println(gift[0]);
		data.writeInt(gift[1]); //奖品数量或卡牌UID
		System.err.println(gift[1]);
	}

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
