package com.cambrian.dfhm.task.command;

import java.util.ArrayList;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.task.entity.TaskAward;
import com.cambrian.dfhm.task.logic.TaskManager;
import com.cambrian.game.Session;

/**
 * 类说明：张梦恺
 * @author：Zmk
 * 
 */
public class FinishTaskCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@SuppressWarnings("unchecked")
	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data)
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
		int sid = data.readInt();
		Map<String, Object> resultMap = TaskManager.getInstance().finishTask(player, sid);
		TaskAward award = (TaskAward)resultMap.get("award");
		award.bytesWrite(data);
		boolean b = resultMap.get("flag")==null?false:(Boolean)resultMap.get("flag");
		ArrayList<Integer> removeCards = (ArrayList<Integer>)resultMap.get("removeCards");
		data.writeInt(removeCards.size());
		for (Integer integer : removeCards)
		{
			data.writeInt(integer);
		}
		data.writeBoolean(b);
		if (b)
		{
			ArrayList<Card> cardList = (ArrayList<Card>)resultMap.get("list");
			data.writeInt(cardList.size());
			for (Card card : cardList)
			{
				data.writeInt(card.getSid());
				data.writeInt(card.getId());
				data.writeInt(card.getSkillId());
			}
		}else
		{
			ArrayList<Integer> cardSidList = (ArrayList<Integer>)resultMap.get("list");
			data.writeInt(cardSidList.size());
			for (Integer integer : cardSidList)
			{
				data.writeInt(integer);
			}
		}
	}
}
