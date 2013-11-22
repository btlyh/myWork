package com.cambrian.dfhm.armyCamp.notice;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.SendCommand;
import com.cambrian.dfhm.armyCamp.entity.SeatCard;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：使用醒酒汤推送
 * @author：Zmk
 * 
 */
public class UseAwakeSoupNotice extends SendCommand
{

	/* static fields */
	private static final Logger log=Logger.getLogger(RemoveMyCardNotice.class);
	/* static methods */

	/* fields */
	/** 消息通知端口 */
	short noticePort;
	/* constructors */

	/* properties */
	public void setPort(short noticePort)
	{
		this.noticePort=noticePort;
	}
	/* init start */

	/* methods */
	@Override
	public void send(Session session, Object[] objs)
	{
		NioTcpConnect connect=session.getConnect();
		if(connect==null)
		{
			if(log.isDebugEnabled())
				log.debug("notice error, connect=null");
			return;
		}
		ByteBuffer data=new ByteBuffer();
		Player tarPlayer = (Player)objs[0];
		data.writeInt(tarPlayer.getArmyCamp().getPrivateList().size());
		System.err.println("醒酒汤推送================"+tarPlayer.getArmyCamp().getPrivateList().size());
		for (SeatCard seatCard : tarPlayer.getArmyCamp().getPrivateList())
		{
			Card card = tarPlayer.getCardBag().getById(seatCard.getCardUid());
			data.writeInt(card.getId());
			System.err.println("醒酒汤推送================"+card.getId());
			data.writeLong(TimeKit.nowTimeMills() - card.getLastDrinkTime());
		}
		connect.send(noticePort,data);
	}
}
