package com.cambrian.dfhm.task.premise;


/**
 * 类说明：任务条件代理器
 * 
 * @version 1.0
 * @date 2013-5-31
 * @author maxw<woldits@qq.com>
 */
public class ProxyTaskPremise
{
	/* static fields */
	/** 类型(前置) */
	public static final int PRE=1;
	
	/** 类型(每日只能做一次,前置任务完成后第二天才能接 )  */
	public static final int DAILY=11,PRE_DAILY_SPACE=12;
	
	/** 类型(上交金钱, ) */
	public static final int GIVE_MONEY=101;
	
	/* static methods */
	/** 获取指定 */
	public static TaskPremise newTaskPremise(int[] values,int sid)
	{
		// TODO Auto-generated method stub
		switch(values[0])
		{
			case PRE:
				return new PreTaskPremise(values[1]);
				//break;
			case DAILY:
				return new DailyTaskPremise(sid);
				//break;
			case PRE_DAILY_SPACE:
				return new PreDailySpaceTaskPremise(values[1]);
				//break;
			case GIVE_MONEY:
				return new GiveMoneyTaskPremise(values[1]);
				//break;

			default:
				return null;
				//throw new DataAccessException(500,"");
				//break;
		}
		
		
		
		
	}
	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

	/* common methods */

	/* inner class */
}
