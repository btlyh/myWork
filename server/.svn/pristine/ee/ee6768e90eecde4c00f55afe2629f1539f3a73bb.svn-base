package com.cambrian.dfhm.armyCamp.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.armyCamp.logic.ArmyCampManager;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：选中卡牌喝酒
 * @author：Zmk
 * 
 */
public class SetCardToDrinkCommand extends Command
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
		int seatId = data.readInt();
		System.err.println("<-------------------drink------------------>");
		System.err.println("seatId==="+seatId);
		int cardUid = data.readInt();
		System.err.println("cardUid==="+cardUid);
		String ownerName = data.readUTF();
		System.err.println("ownerName==="+ownerName);
		System.err.println("PlayerName==="+player.getNickname());
		ArmyCampManager.getInstance().setCardToDrink(player, ownerName, cardUid, seatId);
	}
}
