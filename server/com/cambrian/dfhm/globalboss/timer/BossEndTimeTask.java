package com.cambrian.dfhm.globalboss.timer;

import java.util.TimerTask;

import com.cambrian.dfhm.globalboss.entity.GlobalBossCFG;
import com.cambrian.dfhm.globalboss.logic.BossManager;

/**
 * 类说明：
 * 
 * @author：LazyBear
 */
public class BossEndTimeTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS配置信息 */
	private GlobalBossCFG gbc;

	/* constructors */
	public BossEndTimeTask(GlobalBossCFG gbc)
	{
		this.gbc=gbc;
	}
	/* properties */

	/* init start */

	/* methods */

	// BOSS活动结束时推送消息
	@Override
	public void run()
	{
		GlobalBossCFG gbc=BossManager.getInstance().bossMap.get(this.gbc
			.getSid());
		if(gbc.isOpen())
		{
			gbc.countRank();
			gbc.setOpen(false);
			gbc.reset();
			gbc.autoList.clear();
			BossManager.getInstance().sendReward(gbc,false);
		}
	}

}
