package com.cambrian.dfhm.slaveAndWar.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.dfhm.slaveAndWar.entity.Slave;
import com.cambrian.dfhm.slaveAndWar.notice.EventMessageNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：契约时间到,身份关系结束任务
 * 
 * @author：LazyBear
 */
public class SlaveContractOverTimeTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	SlaveAndWarDao dao;

	DataServer ds;
	
	EventMessageNotice emn;

	/* constructors */
	public SlaveContractOverTimeTask(SlaveAndWarDao dao,DataServer ds,EventMessageNotice emn)
	{
		this.dao=dao;
		this.ds=ds;
		this.emn=emn;
	}
	/* properties */

	/* init start */

	/* methods */
	@Override
	public void run()
	{
		List<Integer> playeIds=dao.getAllPlayerId();
		List<Integer> rmPlayerIds=new ArrayList<Integer>();
		Session[] sessions=ds.getSessionMap().getSessions();
		for(Session session:sessions)
		{
			if(session!=null)
			{
				Player player=(Player)session.getSource();
				if(player!=null)
				{
					rmPlayerIds.add((int)player.getUserId());
					releaseSlave(player);
				}
			}
		}
		playeIds.removeAll(rmPlayerIds);
		for(Integer integer:playeIds)
		{
			Player player=dao.getPlayer(integer);
			if(player!=null)
			{
				releaseSlave(player);
				dao.savePlayerVar(player);
			}
		}
	}

	/**
	 * 释放奴隶
	 * 
	 * @param player
	 */
	private void releaseSlave(Player player)
	{
		if(player.getIdentity().getGrade()==Identity.OWNER)
		{
			for(Slave slave:player.getIdentity().getSlaveList())
			{
				if((TimeKit.nowTimeMills()-slave.getTakeTime()>TimeKit.HOUR_MILLS
					*GameCFG.getSlaveKeepTime()))
				{
					player.getIdentity().cutSlave(slave.getUserId())
						.beFreeHandle(ds,dao,emn);
				}
			}
		}
	}
}
