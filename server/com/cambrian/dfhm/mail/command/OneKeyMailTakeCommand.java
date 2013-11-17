package com.cambrian.dfhm.mail.command;

import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.logic.MailManager;
import com.cambrian.game.Session;

/**
 * 类说明:一键收取
 * 
 * @author：LazyBear
 */
public class OneKeyMailTakeCommand extends Command {

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
		List<Integer> dataList = MailManager.getInstance().oneKeyTakeMail(
				player);
		data.writeInt(dataList.size());
		for (Integer integer : dataList) {
			data.writeInt(integer);
		}
	}
}
