package com.cambrian.dfhm.task.premise;

import com.cambrian.dfhm.common.entity.Player;


/**
 * ��˵������������
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
	/** ���� */
	int type;
	
	/* constructors */
	
	public TaskPremise()
	{
		// TODO Auto-generated constructor stub
	}
	/* properties */
	/** ��ȡ�������� */
	public int getType()
	{
		return type;
	}
	
	/* init start */

	/* methods */
	/** �Ƿ�ﵽ���� */
	public boolean isAchieve(Player player)
	{
		// TODO Auto-generated method stub
		
		
		
		return false;
	}
	/** ִ�� */
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
