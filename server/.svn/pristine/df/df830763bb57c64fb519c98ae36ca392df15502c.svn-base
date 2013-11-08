package com.cambrian.dfhm;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;
import com.cambrian.game.SessionCommand;


/**
 * 类说明：
 * 
 * @version 1.0
 * @date 2013-6-8
 * @author maxw<woldits@qq.com>
 */
public abstract class PlayerCommand extends SessionCommand
{

	/* (non-Javadoc)
	 * @see com.cambrian.game.SessionCommand#handle(com.cambrian.game.Session, com.cambrian.common.net.ByteBuffer)
	 */
	@Override
	public void handle(Session session,ByteBuffer data)
	{
		Player player=(Player)session.getSource();
		if(player==null) throw new DataAccessException(601,Lang.F9000_SDE);
		handle(session,player,data);
	}
	/** 处理 */
	public abstract void handle(Session session,Player player,ByteBuffer data);

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

	/* common methods */

	/* inner class */
}
