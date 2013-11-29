package com.cambrian.dfhm.common.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.common.logic.UsePointsManager;
import com.cambrian.game.Session;

/**
 * 类说明：竞技场积分兑换
 * @author：Zmk
 * 
 */
public class UsePointCommand extends Command
{
	@SuppressWarnings("unchecked")
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
		int sid = data.readInt();
		Map<String, Object> resultMap = UsePointsManager.getInstance().buyGift(player, sid);
		List<Integer> cardList = (ArrayList<Integer>)resultMap.get("cardList");
		boolean haveCard = (Boolean)resultMap.get("haveCard");
		data.writeBoolean(haveCard);
		data.writeInt(cardList.size());
		System.err.println("卡牌列表==============" + cardList);
		for (Integer integer : cardList)
		{
			data.writeInt(integer);
		}
	}

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
