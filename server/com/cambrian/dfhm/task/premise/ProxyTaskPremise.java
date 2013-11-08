package com.cambrian.dfhm.task.premise;


/**
 * ��˵������������������
 * 
 * @version 1.0
 * @date 2013-5-31
 * @author maxw<woldits@qq.com>
 */
public class ProxyTaskPremise
{
	/* static fields */
	/** ����(ǰ��) */
	public static final int PRE=1;
	
	/** ����(ÿ��ֻ����һ��,ǰ��������ɺ�ڶ�����ܽ� )  */
	public static final int DAILY=11,PRE_DAILY_SPACE=12;
	
	/** ����(�Ͻ���Ǯ, ) */
	public static final int GIVE_MONEY=101;
	
	/* static methods */
	/** ��ȡָ�� */
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
