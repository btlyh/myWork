package com.cambrian.dfhm.common.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.common.logic.LuckBoxManager;
import com.cambrian.game.Session;

/**
 * ��˵�������Ƴ齱-����
 * 
 * @author��LazyBear
 */
public class BuyCardCommand extends Command
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
		int needGold=data.readInt();
		int cardSid=data.readInt();
		data.clear();
		Card card=LuckBoxManager.getInstance().buyCard(player,needGold,
			cardSid);
		if(card==null)
		{
			card=new Card();
			card.setSid(0);
			card.uid=0;
			card.setSkillId(0);
		}
		data.writeInt(card.getSid());
		data.writeInt(card.uid);
		data.writeInt(card.getSkillId());
	}
}
