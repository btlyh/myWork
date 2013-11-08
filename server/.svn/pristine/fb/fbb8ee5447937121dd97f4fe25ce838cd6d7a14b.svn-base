package com.cambrian.dfhm.armyCamp.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.armyCamp.logic.ArmyCampManager;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：查询卡牌信息
 * @author：Zmk
 * 
 */
public class CardInfoCommand extends Command
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
		String ownerName = data.readUTF();
		int cardUid = data.readInt();
		System.err.println("CardInfo:-----------ownerName==="+ownerName);
		System.err.println("CardInfo:-----------cardUid==="+cardUid);
		Card card = ArmyCampManager.getInstance().cardInfo(ownerName, cardUid);
		card.bytesWrite(data);
	}
}
