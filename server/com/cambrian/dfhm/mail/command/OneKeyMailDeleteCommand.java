package com.cambrian.dfhm.mail.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.logic.MailManager;
import com.cambrian.game.Session;

/**
 * 类说明：邮件一键删除
 * 
 * @author：LazyBear
 */
public class OneKeyMailDeleteCommand extends Command {

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
		MailManager.getInstance().oneKeyDeleteMail(player);
	}
}
