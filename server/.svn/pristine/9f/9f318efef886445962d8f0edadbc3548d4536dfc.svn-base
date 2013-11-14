package com.cambrian.dfhm.slaveAndWar.timer;

import java.util.TimerTask;

import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.entity.Information;
import com.cambrian.dfhm.slaveAndWar.entity.Slave;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;

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

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * 奴隶自动办事流程(小复杂)
	 */
	@Override
	public void run()
	{
		synchronized(SlaveManager.getInstance().slavePool)
		{
			for(int i=0;i<SlaveManager.getInstance().slavePool.size();i++)
			{
				Slave slave=SlaveManager.getInstance().slavePool.get(i);

				if(slave.getHowLongForWork()==0)
				{
					int money=slave.getFightPoint()*GameCFG.getWorkTime();
					Player ownerPlayer=SlaveManager.getInstance()
						.getTarPlayer(slave.getBossId());
					ownerPlayer.incrMoney(money);
					Player slavePlayer=SlaveManager.getInstance()
						.getTarPlayer(slave.getUserId());
					SlaveManager.getInstance()
						.recordInformation(true,null,ownerPlayer,
							slavePlayer,money,Information.EVENT_WORK);
					if(slave.isManaged())
					{
						if(ownerPlayer.getIdentity().getWorkTimes()<GameCFG
							.getWorkConfine())
						{
							ownerPlayer.getIdentity().inWorkTimes();
							slave.setStartWorkTime(TimeKit.nowTimeMills());
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
