package com.cambrian.dfhm.card.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.CardManager;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * ��˵����
 * @author��Sebastian
 *
 */
public class CardFosterCommand extends Command
{

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
		int cardId = data.readInt();
		int type = data.readInt();
		int money = data.readInt();
		int gold = data.readInt();
		boolean attLocked =data.readBoolean();
		boolean skillRateLocked = data.readBoolean();
		boolean hpLocked = data.readBoolean();
		System.err.println("CardFosterCommand -----------------");
		System.err.println("cardId ==="+cardId);
		System.err.println("type ==="+type);
		System.err.println("money ==="+money);
		System.err.println("gold ==="+gold);
		data.clear();
		CardManager.getInstance().forster(player,cardId,type,money,gold,data,skillRateLocked,hpLocked,attLocked);
	}

}
