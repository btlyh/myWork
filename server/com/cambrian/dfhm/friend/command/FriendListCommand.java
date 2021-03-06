package com.cambrian.dfhm.friend.command;

import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.friend.entity.Friend;
import com.cambrian.dfhm.friend.logic.FriendManager;
import com.cambrian.game.Session;

/**
 * 类说明：查看好友列表/审批列表
 * 
 * @author：LazyBear
 */
public class FriendListCommand extends Command
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
		int type=data.readInt();
  		data.clear();
		List<Friend> list=FriendManager.getInstance().getFriendInfoList(
			player,type);
		if(list!=null)
		{
			data.writeInt(list.size());
			for(Friend friend:list)
			{
				friend.bytesWrite(data);
			}
			data.writeInt(player.getFriendInfo().getApplyList().size());
		}

	}
}
