package com.cambrian.dfhm.common.entity;

import com.cambrian.common.object.Sample;

/**
 * 类说明：VIP功能配置类
 * @author：Zmk
 * 
 */
public class VipCfg extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 每日免费领取军令数量 */
	public int getToken;
	/** 每日可购买军令数量 */
	public int buyToken;
	/** 每日刷新技能免费次数 */
	public int skillFlushFreeTimes;
	/** 军帐中私人座位个数 */
	public int privateSeat;
	/** 军帐中公共座位个数 */
	public int publicSeat;
	/** 免费金币培养次数 */
	public int cardGoldForsterFreeTimes;
	/* 功能 0：不具备，1：具备*/
	/** 卡牌金币培养功能 */
	public int cardGoldForster;
	/** 战斗中使用跳过功能 */
	public int battleSkip;
	/** 付费醒酒功能 */
	public int payForAwake;
	/** 自动喝酒功能 */
	public int autoDrink;
	/** 世界BOSS自动战斗 */
	public int autoBossFight;
	/** 世界BOSS自动报名 */
	public int autoBossSign;
	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
