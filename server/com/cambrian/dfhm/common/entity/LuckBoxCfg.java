package com.cambrian.dfhm.common.entity;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;

/**
 * 类说明： 抽奖配置类
 * 
 * @author：LazyBear
 */
public class LuckBoxCfg extends Sample
{

	/* static fields */
	public static final long CDTime=TimeKit.HOUR_MILLS;
	/* static methods */

	/* fields */
	/** 起始卡牌SID */
	private int startCardSid;
	/** 结束卡牌SID */
	private int endCardSid;
	/** 武魂刷新所需武魂 */
	private int needSoul;
	/** 武魂抽卡所需武魂 */
	private int tNeedSoul;
	/** 刷新时获取卡牌的上限 */
	private int maxTakeSize;
	/** 付费抽取所需RMB */
	private int needGold;
	/** 卡牌直接购买所需RMB */
	private int buyGold;

	/**金币刷新的消耗值*/
	private int flushGold;
	
	/* constructors */

	/* properties */
	public int getStartCardSid()
	{
		return startCardSid;
	}
	public void setStartCardSid(int startCardSid)
	{
		this.startCardSid=startCardSid;
	}
	public int getEndCardSid()
	{
		return endCardSid;
	}
	public void setEndCardSid(int endCardSid)
	{
		this.endCardSid=endCardSid;
	}
	public int getNeedSoul()
	{
		return needSoul;
	}
	public void setNeedSoul(int needSoul)
	{
		this.needSoul=needSoul;
	}
	public int getMaxTakeSize()
	{
		return maxTakeSize;
	}
	public void setMaxTakeSize(int maxTakeSize)
	{
		this.maxTakeSize=maxTakeSize;
	}
	public int getNeedGold()
	{
		return needGold;
	}
	public void setNeedGold(int needGold)
	{
		this.needGold=needGold;
	}
	public int getBuyGold()
	{
		return buyGold;
	}
	public void setBuyGold(int buyGold)
	{
		this.buyGold=buyGold;
	}
	public int gettNeedSoul()
	{
		return tNeedSoul;
	}
	public void settNeedSoul(int tNeedSoul)
	{
		this.tNeedSoul=tNeedSoul;
	}
	public int getFlushGold() {
		return flushGold;
	}
	public void setFlushGold(int flushGold) {
		this.flushGold = flushGold;
	}
	
	
	
	/* init start */

	/* methods */

}
