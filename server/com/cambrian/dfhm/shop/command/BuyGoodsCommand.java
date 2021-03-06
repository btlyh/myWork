package com.cambrian.dfhm.shop.command;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.shop.logic.ShopManager;
import com.cambrian.game.Session;

/**
 * 类说明：购买商品
 * @author：Zmk
 * 
 */
public class BuyGoodsCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
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
		int goodsId = data.readInt();
		int useGold = data.readInt();
		ArrayList<Integer> uidList = ShopManager.getInstance().buyGoods(player, goodsId, useGold);
		data.clear();
		data.writeInt(uidList.size());
		for (Integer integer : uidList)
		{
			data.writeInt(integer);
		}
	}
}
