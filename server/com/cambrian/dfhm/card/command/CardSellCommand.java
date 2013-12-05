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
 * @author��Sebastian
 *
 */
public class CardSellCommand extends Command
{

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
		int len = data.readInt();
		System.err.println("len ==="+len);
		ArrayList<Integer> list = new ArrayList<Integer>();
		int cardId;
		for(int i=0;i<len;i++)
		{
			cardId = data.readInt();//���Ƶ�uid
			System.err.println("id ==="+cardId);
			list.add(cardId);
		}
		int money = data.readInt();//�ܹ���ö���Ǯ
		data.clear();
		CardManager.getInstance().sellCard(player,list,money);
		data.writeBoolean(true);
	}

}
