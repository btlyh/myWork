package com.cambrian.dfhm.slaveAndWar.timer;

import java.util.List;
import java.util.TimerTask;

import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
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

	/* constructors */
	public SlaveContractOverTimeTask(SlaveAndWarDao dao,DataServer ds)
	{
		this.dao=dao;
		this.ds=ds;
	}
	/* properties */

	/* init start */

	/* methods */
	@Override
	public void run()
	{
		List<Integer> playeIds = dao.getAllPlayerId();
		
	}

}
