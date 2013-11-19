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
		String userName = data.readUTF();
		Map<String, Object> resultMap = ArmyCampManager.getInstance().enterArmyCamp(player, userName);
		ArmyCamp armyCamp = (ArmyCamp)resultMap.get("armyCamp");
		List<Card> cardList = (List<Card>)resultMap.get("cardList");
		data.writeInt(cardList.size());
		for (int i = 0; i < cardList.size();i++)
		{
			Card card = cardList.get(i);
			SeatCard seatCard = armyCamp.getSeatCardById(card.getId());
			data.writeInt(seatCard.getSeatId());
			data.writeLong(TimeKit.nowTimeMills() - card.getLastDrinkTime());
		}
		armyCamp.bytesWrite(data);
	}
}
