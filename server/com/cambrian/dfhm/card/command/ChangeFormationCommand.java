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
 * ÀàËµÃ÷£º
 * @author£ºSebastian
 * 
 */
public class ChangeFormationCommand extends Command{


	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data) {
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
		int cardId = data.readInt();
		System.err.println("cardId ==="+cardId);
		int index = data.readInt();
		System.err.println("index ==="+index);
		int type = data.readInt();
		System.err.println("type ==="+type);
		data.clear();
		CardManager.getInstance().changeFormation(player, cardId, index, type);
		player.formation.bytesWrite(data);
	}

}
