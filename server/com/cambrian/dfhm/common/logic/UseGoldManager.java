package com.cambrian.dfhm.common.logic;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：金币消耗业务逻辑处理类
 * 
 * @author：LazyBear
 */
public class UseGoldManager
{

	/* static fields */
	private static UseGoldManager instance=new UseGoldManager();

	/* static methods */
	public static UseGoldManager getInstance()
	{
		return instance;
	}
	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

	/** 购买背包空间 */
	public void buyBagSize(int needGold,int bagSize,Player player)
	{
		String error=checkBugBagSize(needGold,bagSize,player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(needGold);
		player.getCardBag().inCapacity(bagSize);
		
	/*	if(player.getPlayerInfo().getLeadStep() != -1)//引导第五步  背包购买 
		{
			player.getPlayerInfo().setLeadStep(player.getPlayerInfo().getLeadStep()+1);
		}*/	
	}

	/** 检查购买背包空间 */
	private String checkBugBagSize(int needGold,int bagSize,Player player)
	{
		if(getBagSizePrice()!=needGold/bagSize)
		{
			return Lang.F704;
		}
		if(player.getGold()<needGold)
		{
			return Lang.F707;
		}
		return null;
	}

	/**
	 * 购买军令
	 * 
	 * @param needGold 所需金币
	 * @param num 购买数量
	 * @param player 用户对象
	 */
	public void buyToken(int needGold,int num,Player player)
	{
		String error=checkBuyToken(needGold,num,player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(needGold);
		player.incrToken(num);
	}

	/**
	 * 检查购买军令
	 * 
	 * @param needGold 所需金币
	 * @param num 购买数量
	 * @param player 用户对象
	 * @return
	 */
	private String checkBuyToken(int needGold,int num,Player player)
	{
		if(player.getBuyTokenNum()<0||player.getCurToken()<0
			||player.getMaxToken()<0)
		{
			return Lang.F703;
		}
		if(player.getCurToken()==player.getMaxToken())
		{
			return Lang.F706;
		}
		int todayBuyNum=player.getBuyTokenNum();
		if(todayBuyNum>=getTimesForVip(player.getVipLevel()))
		{
			return Lang.F705;
		}
		if(getTokenSinglePrice(todayBuyNum)!=needGold/num)
		{
			return Lang.F704;
		}
		if(player.getGold()<needGold)
		{
			return Lang.F702;
		}
		return null;
	}

	/** 获得购买背包的价格 */
	private int getBagSizePrice()
	{
		// 这里获取购买背包的价格，等策划数值出来进行配置
		return 20;
	}

	/**
	 * 用于获取军令价格每个（现在没有现成数据，这个先拿着用，等策划出了数据，根据策划的实际数据配置）
	 * 
	 * @param alreadyBuyNum 已经购买数量
	 * @return 返回单个军令价格
	 */
	private int getTokenSinglePrice(int alreadyBuyNum)
	{
		switch(alreadyBuyNum>0?(alreadyBuyNum>10?(alreadyBuyNum>20
			?(alreadyBuyNum>40?4:3):2):1):1)
		{
			case 2:
				return 10;
			case 3:
				return 20;
			case 4:
				return 40;
			default:
				return 5;
		}
	}

	/**
	 * 根据VIP等级获得额外购买次数（现在没有现成数据，这个先拿着用，等策划出了数据，根据策划的实际数据配置）
	 * 
	 * @param vipLevel VIP等级
	 * @return 返回多的购买次数
	 */
	private int getTimesForVip(int vipLevel)
	{
		switch(vipLevel)
		{
			case 0:
			case 1:
				return 5;
			case 2:
				return 10;
			case 3:
			case 4:
			case 5:
				return 20;
			default:
				return 0;
		}
	}
}
