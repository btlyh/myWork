package com.cambrian.dfhm.task.premise;

import com.cambrian.dfhm.common.entity.Player;


/**
 * ��˵�����Ͻ���Ǯ
 * 
 * @version 1.0
 * @date 2013-5-31
 * @author maxw<woldits@qq.com>
 */
public class GiveMoneyTaskPremise extends TaskPremise
{
	/* static fields */

	/* static methods */

	/* fields */
	/** ��Ǯ�� */
	final int money;
	
	/* constructors */
	/** �Ͻ���Ǯ  */
	public GiveMoneyTaskPremise(int money)
	{
		this.money = money;
	}
	
	/* properties */

	/* init start */

	/* methods */
	@Override
	public boolean isAchieve(Player player)
	{
		return player.getMoney() >= money;
	}
	@Override
	public boolean execute(Player player)
	{
		boolean b = super.execute(player);
		if(!b) return false;
		player.decrMoney(money);
		return true;
	}
	
	/* common methods */

	/* inner class */
}