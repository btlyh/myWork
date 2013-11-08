package com.cambrian.dfhm.task.premise;

import com.cambrian.dfhm.common.entity.Player;


/**
 * 类说明：上交金钱
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
	/** 金钱数 */
	final int money;
	
	/* constructors */
	/** 上交金钱  */
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
