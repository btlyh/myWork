package com.cambrian.dfhm.card.command;

import java.util.ArrayList;

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
 * 
 * @author��LazyBear
 */
public class CardEngulfCommand extends Command
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
		int cardId=data.readInt();
		int len=data.readInt();
		ArrayList<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<len;i++)
		{
			list.add(data.readInt());
		}
		data.clear();
		CardManager.getInstance().engulfCard(player,cardId,
			list,data);
	/*	data.writeInt(result.length);
		for(int i=0;i<result.length;i++)
		{
			data.writeBoolean(result[i]);
		}*/
	}
	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
