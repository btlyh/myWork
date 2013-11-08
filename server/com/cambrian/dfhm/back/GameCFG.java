package com.cambrian.dfhm.back;

/**
 * 类说明：游戏全局配置(功能配置)
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class GameCFG
{
	/** 卡牌升级经验[每级经验] */
	private static int[] levelExp;
	/** 技能升级[每级经验，吞噬卡牌数量] */
	private static int[] skillLevel;
	/** VIP等级所需要充值的金币 */
	private static int[] vipLevelGold;
	/** VIP等级对应的配置表 */
	private static int[] vipCfgXml;
	/** 武魂抽奖 */
	private static int[] soulLottery;
	/** 付费醒酒分钟数 */
	private static int payForAwakeMinutes;
	/** 付费醒酒每x分钟消耗的金币 */
	private static int payForAwakeGold;
	/** 醒酒汤减少对方玩家CD百分比 */
	private static int awakeSoupTarCdTime;
	/** 醒酒汤减少自己玩家CD百分比 */
	private static int awakeSoupOwnCdTime;
	/** 喝酒CD分钟数 */
	private static int drinkCd;
	/** 醒酒汤CD分钟数 */
	private static int awakeSoupCd;
	/** 连携技 */
	private static int[] DoubleSkill;
	/** 土豪培养 消耗的金币数*/
	private static int goldFosterNum;
	/**金币培养凶耗的金币数*/
	private static int moneyFosterNum;
	/**普通培养消耗的银币数*/
	private static int normalFosterNum;
	//
	/** 世界BOSS集合*/
	private static int[] globalBossList;

	public static int getPayForAwakeMinutes()
	{
		return payForAwakeMinutes;
	}

	public static void setPayForAwakeMinutes(int payForAwakeMinutes)
	{
		GameCFG.payForAwakeMinutes = payForAwakeMinutes;
	}

	public static int getPayForAwakeGold()
	{
		return payForAwakeGold;
	}

	public static void setPayForAwakeGold(int payForAwakeGold)
	{
		GameCFG.payForAwakeGold = payForAwakeGold;
	}

	public void setLevelExp(int[] levelExp)
	{
		GameCFG.levelExp = levelExp;
	}

	public void setSkillLevel(int[] skillLevel)
	{
		GameCFG.skillLevel = skillLevel;
	}

	public void setSoulLottery(int[] soulLottery)
	{
		GameCFG.soulLottery = soulLottery;
	}

	public void setVipCfgXml(int[] vipCfgXml)
	{
		GameCFG.vipCfgXml = vipCfgXml;
 	}

	/** 获得指定等级，卡牌升级的经验 */
	public static int getExp(int level)
	{
		return levelExp[level - 1];
	}

	/** 获得指定等级，技能升级的吞噬卡片数量 */
	public static int getSkillLevel(int level)
	{
		return skillLevel[level * 2 + 1];
	}

	public static float getAwakeSoupTarCdTime()
	{
		return awakeSoupTarCdTime / 100f;
	}

	public static void setAwakeSoupTarCdTime(int awakeSoupTarCdTime)
	{
		GameCFG.awakeSoupTarCdTime = awakeSoupTarCdTime;
	}

	public static float getAwakeSoupOwnCdTime()
	{
		return awakeSoupOwnCdTime / 100f;
	}

	public static void setAwakeSoupOwnCdTime(int awakeSoupOwnCdTime)
	{
		GameCFG.awakeSoupOwnCdTime = awakeSoupOwnCdTime;
	}

	public static int getDrinkCd()
	{
		return drinkCd;
	}

	public static void setDrinkCd(int drinkCd)
	{
		GameCFG.drinkCd = drinkCd;
	}

	public static int getAwakeSoupCd()
	{
		return awakeSoupCd;
	}

	public static void setAwakeSoupCd(int awakeSoupCd)
	{
		GameCFG.awakeSoupCd = awakeSoupCd;
	}
	/** 获得指定等级升级VIP需要的金额 */
	public static int getVipLevelGold(int level)
	{
		return vipLevelGold[level - 1];
	}

	public void setVipLevelGold(int[] vipLevelGold)
	{
		GameCFG.vipLevelGold = vipLevelGold;
	}
	/** 获得指定VIP等级对应的配置表SID */
	public static int getVipCfg(int level)
	{
		return vipCfgXml[level];
	}
	public static void setDoubleSkill(int[] doubleSkill)
	{
		DoubleSkill=doubleSkill;
		System.err.println("length ===="+DoubleSkill.length);
	}
	
	/**
	 * 获得所有BOSS集合
	 * @return 
	 */
	public static int[] getGlobalBossList()
	{
		return globalBossList;
	}

	/**
	 * 通过技能ID，获得对应连携技
	 * @param skillId
	 * @return
	 */
	public static int[] getDSkillById(int skillId)
	{		
		int id;
		for(int i=0;i<DoubleSkill.length;i+=2)
		{
			id = DoubleSkill[i];
			if(skillId == id)
			{
				int[] dSkill = new int[2];
				dSkill[0] = id;
				dSkill[0] = DoubleSkill[i+1];
				return dSkill;
			}
		}
		return null;
	}

	
	public static int getGoldFosterNum() {
		return goldFosterNum;
	}

	public static void setGoldFosterNum(int goldFosterNum) {
		GameCFG.goldFosterNum = goldFosterNum;
	}

	public static int getMoneyFosterNum() {
		return moneyFosterNum;
	}

	public static void setMoneyFosterNum(int moneyFosterNum) {
		GameCFG.moneyFosterNum = moneyFosterNum;
	}

	public static int getNormalFosterNum() {
		return normalFosterNum;
	}

	public static void setNormalFosterNum(int normalFosterNum) {
		GameCFG.normalFosterNum = normalFosterNum;
	}


	
	/**
	 *  通过技能ID，获得对应连携技概率
	 * @param skillId
	 * @return
	 */
	public static int getDsSkillRateById(int skillId)
	{		
		int id;
		for(int i=0;i<DoubleSkill.length;i+=2)
		{
			id = DoubleSkill[i];
			if(skillId == id)	
				return DoubleSkill[i+1];
		}
		return -1;
	}
	/**
	 * 设置BOSS集合
	 * @param globalBossList bossSid集合列表
	 */
	public static void setGlobalBossList(int[] globalBossList)
	{
		GameCFG.globalBossList = globalBossList;
	}


}
