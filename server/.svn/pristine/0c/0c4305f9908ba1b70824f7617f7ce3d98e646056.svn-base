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
 * @author£ºLazyBear
 * 
 */
public class CardSkillFlushCommand extends Command
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
		int cardId = data.readInt();
		int needGold = data.readInt();
		data.clear();
		int newSkillId = CardManager.getInstance().flushCardSkill(player,cardId,needGold);
		data.writeInt(newSkillId);
	}
	
}
