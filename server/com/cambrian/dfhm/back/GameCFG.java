package com.cambrian.dfhm.back;

import java.util.ArrayList;

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
	/** 技能刷新所需金币 */
	private static int skillFlushNeedGold;
	/** VIP等级所需要充值的金币 */
	private static int[] vipLevelGold;
	/** VIP等级对应的配置表 */
	private static int[] vipCfgXml;
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
	/** 连携技[ID，发动概率] */
	private static String DoubleSkill;
	/** 土豪培养 消耗的金币数 */
	private static int goldFosterNum;
	/** 金币培养凶耗的金币数 */
	private static int moneyFosterNum;
	/** 普通培养消耗的银币数 */
	private static int normalFosterNum;

	/** 领奖的间隔时间 秒 */
	private static int[] rewardIntervalTime;

	/** 每天领奖的次数 */
	private static int everyDayRewardNum;

	/** 每天领奖的实例的ID */
	private static int[] rewardSampleId;

	/** 卡牌突破奖励的属性提升次数 */
	private static int[] cardBeyongNum;
	//

	/** 排位赛胜利获得积分 */
	private static int duelWinPoint;
	/** 排位赛失败获得积分 */
	private static int duelLosePoint;
	/** 排位赛每日领取积分 */
	private static int[] dayPoint;

	/** 当壕功能随机抽取战力误差值 */
	private static int errorValue;
	/** 当壕功能获取敌人列表上限 */
	private static int enemySize;
	/** 当壕功能递归匹配次数 */
	private static int matchTimes;
	/** 当壕功能马仔上限 */
	private static int slaveConfine;
	/** 当壕功能信息数量上限 */
	private static int informationSize;
	/** 当壕功能攻击次数限制 */
	private static int attConfine;
	/** 当壕功能反抗次数限制 */
	private static int reactConfine;
	/** 当壕功能赎身金币数量 */
	private static int getFreeGold;
	/** 当壕功能救人次数限制 */
	private static int saveConfine;
	/** 当壕功能救陌生人次数限制 */
	private static int saveNorConfine;
	/** 当壕功能求救次数限制 */
	private static int helpConfine;
	/** 当壕功能办事时间设置(分钟) */
	private static int workTime;
	/** 当壕功能办事次数限制 */
	private static int workConfine;
	/** 当壕功能托管所需金币数量 */
	private static int managedGold;
	/** 当壕功能速去速回所需金币数量 */
	private static int fastWorkGold;
	/** 当壕功能身份维持时间(小时) */
	private static int slaveKeepTime;
	/** 当壕功能信息SID集合 */
	private static int[] slaveInformations;
	/** 世界BOSS集合 */
	private static int[] globalBossList;
	/** 世界BOSS自动报名所需金币 */
	private static int bossAutoSignGold;
	/** 玩家初始化可挑战副本SID */
	private static int indexForNormalNPC;

	/** 卡牌吞噬消耗的基础金币 ***/
	private static int engulfCardMony;
	/** 服务器重置玩家每日数据时间 */
	private static int serverTime;

	/** 新手引导 奖励的卡牌ID **/
	private static int[] leadAwardCard;

	/** 卡牌被吞噬所能提供的经验值 ***/
	private static int[] engulfLevelExp;
	/** 卡牌吞噬经验回收比例 **/
	private static float engulfExpPer;
	/** 当壕赎身次数限制*/
	private static int ransomConfine;

	/**角色最大等级**/
	private static int maxLevel;
	public static int getPayForAwakeMinutes()
	{
		return payForAwakeMinutes;
	}

	public static void setPayForAwakeMinutes(int payForAwakeMinutes)
	{
		GameCFG.payForAwakeMinutes=payForAwakeMinutes;
	}

	public static int getPayForAwakeGold()
	{
		return payForAwakeGold;
	}

	public static void setPayForAwakeGold(int payForAwakeGold)
	{
		GameCFG.payForAwakeGold=payForAwakeGold;
	}

	public void setLevelExp(int[] levelExp)
	{
		GameCFG.levelExp=levelExp;
	}

	public static int[] getLevelExp()
	{
		return levelExp;
	}

	public void setSkillLevel(int[] skillLevel)
	{
		GameCFG.skillLevel=skillLevel;
	}

	public void setVipCfgXml(int[] vipCfgXml)
	{
		GameCFG.vipCfgXml=vipCfgXml;
	}

	/** 获得指定等级，卡牌升级的经验 */
	public static int getExp(int level)
	{
		return levelExp[level-1];
	}

	/** 获得指定等级，技能升级的吞噬卡片数量 */
	public static int getSkillLevel(int level)
	{
		return skillLevel[level*2+1];
	}
	/** 获得使用醒酒汤时对方军帐中卡牌CD时间减少百分比 */
	public static float getAwakeSoupTarCdTime()
	{
		return awakeSoupTarCdTime/100f;
	}

	public static void setAwakeSoupTarCdTime(int awakeSoupTarCdTime)
	{
		GameCFG.awakeSoupTarCdTime=awakeSoupTarCdTime;
	}
	/** 获得使用醒酒汤时自己军帐中卡牌CD时间减少百分比 */
	public static float getAwakeSoupOwnCdTime()
	{
		return awakeSoupOwnCdTime/100f;
	}

	public static void setAwakeSoupOwnCdTime(int awakeSoupOwnCdTime)
	{
		GameCFG.awakeSoupOwnCdTime=awakeSoupOwnCdTime;
	}

	public static int getDrinkCd()
	{
		return drinkCd;
	}

	public static void setDrinkCd(int drinkCd)
	{
		GameCFG.drinkCd=drinkCd;
	}

	public static int getAwakeSoupCd()
	{
		return awakeSoupCd;
	}

	public static void setAwakeSoupCd(int awakeSoupCd)
	{
		GameCFG.awakeSoupCd=awakeSoupCd;
	}

	/** 获得指定等级升级VIP需要的金额 */
	public static int getVipLevelGold(int level)
	{
		return vipLevelGold[level-1];
	}

	public void setVipLevelGold(int[] vipLevelGold)
	{
		GameCFG.vipLevelGold=vipLevelGold;
	}

	/** 获得指定VIP等级对应的配置表SID */
	public static int getVipCfg(int level)
	{
		return vipCfgXml[level];
	}
	public static void setRewardSampleId(int[] rewardSampleId)
	{
		GameCFG.rewardSampleId=rewardSampleId;
	}

	public static void setDoubleSkill(String doubleSkill)
	{
		DoubleSkill=doubleSkill;
		System.err.println("length ===="+DoubleSkill);
	}

	public static float getEngulfExpPer()
	{
		return engulfExpPer;
	}

	public static void setEngulfExpPer(float engulfExpPer)
	{
		GameCFG.engulfExpPer=engulfExpPer;
	}

	/**
	 * 获得所有BOSS集合
	 * 
	 * @return
	 */
	public static int[] getGlobalBossList()
	{
		return globalBossList;
	}

	/**
	 * 通过技能ID，获得对应连携技ID
	 * 
	 * @param skillId
	 * @return
	 */
	public static int[] getDSkillById(int skillId)
	{
		int id;
		String[] doubleStrings=DoubleSkill.split(";");
		for(int i=0;i<doubleStrings.length;i++)
		{
			String[] oneSkill=doubleStrings[i].split(",");
			id=Integer.parseInt(oneSkill[0]);
			if(skillId==id)
			{
				int[] ds=new int[oneSkill.length];
				for(int j=0;j<ds.length;j++)
				{
					ds[j]=Integer.parseInt(oneSkill[j]);
				}
				return ds;
			}
		}
		// for(int i=0;i<DoubleSkill.length;i++)
		// {
		// id=DoubleSkill[i][0];
		// if(skillId==id)
		// {
		// int[] ds=new int[DoubleSkill[i].length-1];
		// for(int j=0;j<ds.length;j++)
		// ds[j]=DoubleSkill[i][j+1];
		// return ds;
		// }
		// }
		return null;
	}

	public static int getGoldFosterNum()
	{
		return goldFosterNum;
	}

	public static void setGoldFosterNum(int goldFosterNum)
	{
		GameCFG.goldFosterNum=goldFosterNum;
	}

	public static int getMoneyFosterNum()
	{
		return moneyFosterNum;
	}

	public static void setMoneyFosterNum(int moneyFosterNum)
	{
		GameCFG.moneyFosterNum=moneyFosterNum;
	}

	public static int getNormalFosterNum()
	{
		return normalFosterNum;
	}

	public static void setNormalFosterNum(int normalFosterNum)
	{
		GameCFG.normalFosterNum=normalFosterNum;
	}

	public static void setRewardIntervalTime(int[] rewardIntervalTime)
	{
		GameCFG.rewardIntervalTime=rewardIntervalTime;
	}

	public static int getEveryDayRewardNum()
	{
		return everyDayRewardNum;
	}

	public static void setEveryDayRewardNum(int everyDayRewardNum)
	{
		GameCFG.everyDayRewardNum=everyDayRewardNum;
	}

	public static int[] getCardBeyongNum()
	{
		return cardBeyongNum;
	}

	public static void setCardBeyongNum(int[] cardBeyongNum)
	{
		GameCFG.cardBeyongNum=cardBeyongNum;
	}

	public static int[] getRewardSampleId()
	{
		return rewardSampleId;
	}

	public static int[] getVipLevelGold()
	{
		return vipLevelGold;
	}

	public static int getEngulfCardMony()
	{
		return engulfCardMony;
	}

	public static void setEngulfCardMony(int engulfCardMony)
	{
		GameCFG.engulfCardMony=engulfCardMony;
	}

	public static int[] getLeadAwardCard()
	{
		return leadAwardCard;
	}

	public static void setLeadAwardCard(int[] leadAwardCard)
	{
		GameCFG.leadAwardCard=leadAwardCard;
	}

	/***
	 * @param rewardNum 当前领奖次数
	 * @return 下一次的领奖间隔时间
	 */
	public static int getRewardIntervalTimeByIndex(int rewardNum)
	{
		for(int i=0;i<rewardIntervalTime.length;i++)
		{
			if(i==rewardNum)
			{
				return rewardIntervalTime[i];
			}
		}
		return -1;
	}

	/***
	 * @param rewardNum 当前领奖次数
	 * @return 下一次的领奖的UID
	 */
	public static int getRewardSampleIdByNum(int rewardNum)
	{
		for(int i=0;i<rewardSampleId.length;i++)
		{
			if(i==rewardNum)
			{
				return rewardSampleId[i];
			}
		}
		return -1;
	}

	/***
	 * 根据卡牌等级 返回突破奖励的属性提升比率
	 */

	public static int getAwardByLevel(int level)
	{
		for(int i=0;i<cardBeyongNum.length;i++)
		{
			if(cardBeyongNum[i]==level)
			{
				return cardBeyongNum[i+1];
			}
		}
		return -1;
	}

	public static int getDuelWinPoint()
	{
		return duelWinPoint;
	}

	public static void setDuelWinPoint(int duelWinPoint)
	{
		GameCFG.duelWinPoint=duelWinPoint;
	}

	public static int getDuelLosePoint()
	{
		return duelLosePoint;
	}

	public static void setDuelLosePoint(int duelLosePoint)
	{
		GameCFG.duelLosePoint=duelLosePoint;
	}

	public static int getDayPoint(int rank)
	{
		return dayPoint[rank];
	}

	public static void setDayPoint(int[] dayPoint)
	{
		GameCFG.dayPoint=dayPoint;
	}

	public static int[] getDayPoint()
	{
		return dayPoint;
	}
	/**
	 * 设置BOSS集合
	 * 
	 * @param globalBossList bossSid集合列表
	 */
	public static void setGlobalBossList(int[] globalBossList)
	{
		GameCFG.globalBossList=globalBossList;
	}

	/**
	 * 获取随机抽取战力误差值(当壕)
	 * 
	 * @return
	 */
	public static int getErrorValue()
	{
		return errorValue;
	}

	public static void setErrorValue(int errorValue)
	{
		GameCFG.errorValue=errorValue;
	}

	/**
	 * 获取敌人列表上限(当壕)
	 * 
	 * @return
	 */
	public static int getEnemySize()
	{
		return enemySize;
	}

	public static void setEnemySize(int enemySize)
	{
		GameCFG.enemySize=enemySize;
	}

	/**
	 * 获取递归匹配次数(当壕)
	 * 
	 * @return
	 */
	public static int getMatchTimes()
	{
		return matchTimes;
	}

	public static void setMatchTimes(int matchTimes)
	{
		GameCFG.matchTimes=matchTimes;
	}

	/**
	 * 获取马仔上限
	 * 
	 * @return
	 */
	public static int getSlaveConfine()
	{
		return slaveConfine;
	}

	public static void setSlaveConfine(int slaveConfine)
	{
		GameCFG.slaveConfine=slaveConfine;
	}

	/**
	 * 获取信息数量是上限
	 * 
	 * @return
	 */
	public static int getInformationSize()
	{
		return informationSize;
	}

	public static void setInformationSize(int informationSize)
	{
		GameCFG.informationSize=informationSize;
	}

	/**
	 * 获取攻击次数限制
	 * 
	 * @return
	 */
	public static int getAttConfine()
	{
		return attConfine;
	}

	public static void setAttConfine(int attConfine)
	{
		GameCFG.attConfine=attConfine;
	}
	/**
	 * 获取反抗次数限制
	 * 
	 * @return
	 */
	public static int getReactConfine()
	{
		return reactConfine;
	}

	public static void setReactConfine(int reactConfine)
	{
		GameCFG.reactConfine=reactConfine;
	}

	/**
	 * 获取赎身的价格
	 * 
	 * @return
	 */
	public static int getGetFreeGold()
	{
		return getFreeGold;
	}

	public static void setGetFreeGold(int getFreeGold)
	{
		GameCFG.getFreeGold=getFreeGold;
	}

	/**
	 * 获取救人次数限制
	 * 
	 * @return
	 */
	public static int getSaveConfine()
	{
		return saveConfine;
	}

	public static void setSaveConfine(int saveConfine)
	{
		GameCFG.saveConfine=saveConfine;
	}

	/**
	 * 获取拯救陌生人次数限制
	 * 
	 * @return
	 */
	public static int getSaveNorConfine()
	{
		return saveNorConfine;
	}

	public static void setSaveNorConfine(int saveNorConfine)
	{
		GameCFG.saveNorConfine=saveNorConfine;
	}
	/**
	 * 获取办事所需时间
	 * 
	 * @return
	 */
	public static int getWorkTime()
	{
		return workTime;
	}

	public static void setWorkTime(int workTime)
	{
		GameCFG.workTime=workTime;
	}

	/**
	 * 获取办事次数上限
	 * 
	 * @return
	 */
	public static int getWorkConfine()
	{
		return workConfine;
	}

	public static void setWorkConfine(int workConfine)
	{
		GameCFG.workConfine=workConfine;
	}

	/**
	 * 托管所需金币数量
	 * 
	 * @return
	 */
	public static int getManagedGold()
	{
		return managedGold;
	}

	public static void setManagedGold(int managedGold)
	{
		GameCFG.managedGold=managedGold;
	}

	/**
	 * 获取速去速回所需金币
	 * 
	 * @return
	 */
	public static int getFastWorkGold()
	{
		return fastWorkGold;
	}

	public static void setFastWorkGold(int fastWorkGold)
	{
		GameCFG.fastWorkGold=fastWorkGold;
	}

	/**
	 * 获取自动报名所需费用
	 * 
	 * @return
	 */
	public static int getBossAutoSignGold()
	{
		return bossAutoSignGold;
	}

	public static void setBossAutoSignGold(int bossAutoSignGold)
	{
		GameCFG.bossAutoSignGold=bossAutoSignGold;
	}

	/**
	 * 获取技能刷新所需要的金币
	 * 
	 * @return
	 */
	public static int getSkillFlushNeedGold()
	{
		return skillFlushNeedGold;
	}

	public static void setSkillFlushNeedGold(int skillFlushNeedGold)
	{
		GameCFG.skillFlushNeedGold=skillFlushNeedGold;
	}

	/***
	 * 求救次数
	 * 
	 * @return
	 */
	public static int getHelpConfine()
	{
		return helpConfine;
	}

	public static void setHelpConfine(int helpConfine)
	{
		GameCFG.helpConfine=helpConfine;
	}

	public static int[] getEngulfLevelExp()
	{
		return engulfLevelExp;
	}

	public static void setEngulfLevelExp(int[] engulfLevelExp)
	{
		GameCFG.engulfLevelExp=engulfLevelExp;
	}

	
	
	
	public static int getMaxLevel() {
		return maxLevel;
	}

	public static void setMaxlevel(int maxLevel) {
		GameCFG.maxLevel = maxLevel;
	}

	/***
	 * 获取玩家初始化可挑战副本SID
	 * 
	 * @return
	 */
	public static int getIndexForNormalNPC()
	{
		return indexForNormalNPC;
	}

	public static void setIndexForNormalNPC(int indexForNormalNPC)
	{
		GameCFG.indexForNormalNPC=indexForNormalNPC;
	}

	public static ArrayList<Integer> getAwardCardByStep(int step)
	{
		ArrayList<Integer> rtList=new ArrayList<Integer>();

		for(int i=0;i<leadAwardCard.length;i++)
		{
			if(leadAwardCard[i]==step)
			{
				rtList.add(leadAwardCard[i+1]);
			}
		}

		return rtList;

	}

	public static int getServerTime()
	{
		return serverTime;
	}

	public static void setServerTime(int serverTime)
	{
		GameCFG.serverTime=serverTime;
	}

	public static int getEngulfExpByLevel(int level)
	{
		return engulfLevelExp[level-1];
	}

	/**
	 * 获取身份维持时间
	 * 
	 * @return
	 */
	public static int getSlaveKeepTime()
	{
		return slaveKeepTime;
	}

	public static void setSlaveKeepTime(int slaveKeepTime)
	{
		GameCFG.slaveKeepTime=slaveKeepTime;
	}

	/***
	 * 获取当壕信息SID集合
	 * 
	 * @return
	 */
	public static int[] getSlaveInformations()
	{
		return slaveInformations;
	}

	public static void setSlaveInformations(int[] slaveInformations)
	{
		GameCFG.slaveInformations=slaveInformations;
	}
	
	/**
	 * 获取赎身次数限制
	 * */
	public static int getRansomConfine()
	{
		return ransomConfine;
	}

	public static void setRansomConfine(int ransomConfine)
	{
		GameCFG.ransomConfine = ransomConfine;
	}
}
