package com.cambrian.dfhm.task.premise;

import com.cambrian.dfhm.common.entity.Player;


/**
 * 类说明：任务条件
 * 
 * @version 1.0
 * @date 2013-5-30
 * @author maxw<woldits@qq.com>
 */
public class TaskPremise
{
	/* static fields */

	/* static methods */

	/* fields */
	/** 类型 */
	int type;
	
	/* constructors */
	
	public TaskPremise()
	{
		// TODO Auto-generated constructor stub
	}
	/* properties */
	/** 获取条件类型 */
	public int getType()
	{
		return type;
	}
	
	/* init start */

	/* methods */
	/** 是否达到条件 */
	public boolean isAchieve(Player player)
	{
		// TODO Auto-generated method stub
		
		
		
		return false;
	}
	/** 执行 */
	public boolean execute(Player player)
	{
		// TODO Auto-generated method stub
		boolean b=isAchieve(player);
		if(!b) return b;

		
		
		
		
		return true;
	}
	
	/* common methods */

	/* inner class */
}
