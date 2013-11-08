package com.cambrian.dfhm.task.premise;

import com.cambrian.dfhm.back.BackKit;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.task.Task;


/**
 * 类说明：指定sid任务完成后第二天才可以接
 * 
 * @version 1.0
 * @date 2013-5-31
 * @author maxw<woldits@qq.com>
 */
public class PreDailySpaceTaskPremise extends TaskPremise
{
	/* static fields */

	/* static methods */

	/* fields */
	/** 任务id */
	final int sid;
	
	/* constructors */
	/** 前置任务完成后第二天才能接 */
	public PreDailySpaceTaskPremise(int sid)
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
//		if(task==null) return false;
//		if(task.isFinish()&&BackKit.isYesterday(task.finishtime))
//			return true;
		return false;
	}
	/* common methods */

	/* inner class */
}
