package com.cambrian.dfhm.armyCamp.command;

import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.armyCamp.entity.ArmyCamp;
import com.cambrian.dfhm.armyCamp.entity.SeatCard;
import com.cambrian.dfhm.armyCamp.logic.ArmyCampManager;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：进入军帐
 * @author：Zmk
 * 
 */
public class EnterArmyCampCommand extends Command
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
		String userName = data.readUTF();
		System.err.println("<----------------------enter-------------------->");
		System.err.println("username===" + userName);
		System.err.println("playerName===="+player.getNickname());
		Map<String, Object> resultMap = ArmyCampManager.getInstance().enterArmyCamp(player, userName);
		ArmyCamp armyCamp = (ArmyCamp)resultMap.get("armyCamp");
		CardBag cardBag = (CardBag)resultMap.get("cardBag");
		List<SeatCard> publicList = armyCamp.getPublicList();
		data.writeInt(publicList.size());
		System.err.println("size====="+publicList.size());
		for (int i = 0; i < publicList.size();i++)
		{
			SeatCard seatCard = publicList.get(i);
			Card card = cardBag.getById(seatCard.getCardUid());
			data.writeInt(seatCard.getSeatId());
			data.writeLong(TimeKit.nowTimeMills() - card.getLastDrinkTime());
			System.err.println("seatId======"+seatCard.getSeatId());
			System.err.println("time======"+(TimeKit.nowTimeMills() - card.getLastDrinkTime()));
		}
		armyCamp.bytesWrite(data);
	}
}
