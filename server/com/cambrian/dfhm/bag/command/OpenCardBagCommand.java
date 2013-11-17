package com.cambrian.dfhm.bag.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明： 打开背包命令
 * 
 * @author：LazyBear
 * 
 */
public class OpenCardBagCommand extends Command {

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data) {
		Session session = connect.getSession();
		if (log.isDebugEnabled()) {
			log.debug("session = " + session);
		}
		if (session == null) {
			throw new DataAccessException(601, Lang.F9000_SDE);
		}
		Player player = (Player) session.getSource();
		if (player == null) {
			throw new DataAccessException(601, Lang.F9000_SDE);
		}
		for (Card card : player.getCardBag().getList()) {
			if (card.isNew()) {
				card.setNew(false);
			}
		}
	}
}
