package com.cambrian.dfhm.reward.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.CardManager;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.reward.RewardManager;
import com.cambrian.game.Session;

public class OnlineAwardCommand extends Command
{
	@Override
	public void handle(NioTcpConnect connect, ByteBuffer data) {
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
	//	boolean isReward = data.readBoolean();		
		data.clear();
		RewardManager.getInstance().onlineReward(player,data);
	}		
}
