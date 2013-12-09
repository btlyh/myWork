package com.cambrian.dfhm.slaveAndWar.timer;

import java.util.TimerTask;

import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
import com.cambrian.dfhm.slaveAndWar.entity.Information;
import com.cambrian.dfhm.slaveAndWar.entity.Slave;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;
import com.cambrian.dfhm.slaveAndWar.notice.EventMessageNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：奴隶办事任务
 * 
 * @author:LazyBear
 */
public class SlaveWorkTimeTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	SlaveAndWarDao dao;
	DataServer ds;
	EventMessageNotice emn;

	/* constructors */
	public SlaveWorkTimeTask(SlaveAndWarDao dao,DataServer ds,
		EventMessageNotice emn)
	{
		this.dao=dao;
		this.ds=ds;
		this.emn=emn;
	}

	/* properties */

	/* init start */

	/* methods */
	/**
	 * 奴隶自动办事流程(小复杂)
	 */
	@Override
	public void run()
	{
		for(int i=0;i<SlaveManager.getInstance().slavePool.size();i++)
		{
			Slave slave=SlaveManager.getInstance().slavePool.get(i);
			synchronized(slave)
			{
				if(slave.getHowLongForWork()==0)
				{
					int money=slave.getFightPoint()*GameCFG.getWorkTime();
					Player ownerPlayer=SlaveManager.getInstance()
						.getTarPlayer(slave.getBossId());
					ownerPlayer.incrMoney(money);

					Player slavePlayer=SlaveManager.getInstance()
						.getTarPlayer(slave.getUserId());

					Information.CreatandSave(ownerPlayer.getIdentity(),
						slavePlayer.getNickname(),"",
						Information.TYPE_WORKDONE,Information.EVENT_SUCCESS,
						money);
					Information.CreatandSave(slavePlayer.getIdentity(),
						ownerPlayer.getNickname(),"",
						Information.TYPE_WORKDONE,Information.EVENT_SUCCESS,
						money);
					Session session=ds.getSession((int)ownerPlayer
						.getUserId());

					if(session==null)
					{
						dao.savePlayerVar(ownerPlayer);
					}
					else
					{
						emn.send(session,new Object[]{money});
					}
					Session session_=ds.getSession((int)slavePlayer
						.getUserId());
					if(session_==null)
					{
						dao.savePlayerVar(slavePlayer);
					}
					else
					{
						emn.send(session_,new Object[]{0});
					}
					if(slave.isManaged())
					{
						if(ownerPlayer.getIdentity().getWorkTimes()<GameCFG
							.getWorkConfine())
						{
							ownerPlayer.getIdentity().inWorkTimes();
							slave.workStartHandle();
						}
						else
						{
							slave.workEndHandle();
							SlaveManager.getInstance().slavePool.remove(i);
						}
					}
					else
					{
						slave.workEndHandle();
						SlaveManager.getInstance().slavePool.remove(i);
					}
				}
			}

		}
	}
}
