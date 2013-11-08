package com.cambrian.dfhm.task.premise;

import com.cambrian.dfhm.back.BackKit;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.task.Task;


/**
 * 类说明：每日任务
 * 
 * @version 1.0
 * @date 2013-5-31
 * @author maxw<woldits@qq.com>
 */
public class DailyTaskPremise extends TaskPremise
{
	/* static fields */

	/* static methods */

	/* fields */
	/** 任务id */
	final int sid;
	
	/* constructors */
	/** 每日任务 */
	public DailyTaskPremise(int sid)
	{
		this.sid=sid;
	}
	/* properties */

	/* init start */

	/* methods */
	@Override
	public boolean isAchieve(Player player)
	{
//		Task task=player.task.getTask(sid);
//		if(task==null) return true;
//		if(task.isFinish()&&BackKit.isBeforeToday(task.finishtime))
//			return true;
		return false;
	}
	/* common methods */

	/* inner class */
}
