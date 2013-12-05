package com.cambrian.dfhm.back;

/**
 * ��˵������Ϸȫ������(��������)
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class GameCFG
{

	/** ������������[ÿ������] */
	private static int[] levelExp;
	/** ��������[ÿ�����飬���ɿ�������] */
	private static int[] skillLevel;
	/** ����ˢ�������� */
	private static int skillFlushNeedGold;
	/** VIP�ȼ�����Ҫ��ֵ�Ľ�� */
	private static int[] vipLevelGold;
	/** VIP�ȼ���Ӧ�����ñ� */
	private static int[] vipCfgXml;
	/** �����ѾƷ����� */
	private static int payForAwakeMinutes;
	/** �����Ѿ�ÿx�������ĵĽ�� */
	private static int payForAwakeGold;
	/** �Ѿ������ٶԷ����CD�ٷֱ� */
	private static int awakeSoupTarCdTime;
	/** �Ѿ��������Լ����CD�ٷֱ� */
	private static int awakeSoupOwnCdTime;
	/** �Ⱦ�CD������ */
	private static int drinkCd;
	/** �Ѿ���CD������ */
	private static int awakeSoupCd;
	/** ��Я��[ID����������] */
	private static String DoubleSkill;
	/** �������� ���ĵĽ���� */
	private static int goldFosterNum;
	/** ��������׺ĵĽ���� */
	private static int moneyFosterNum;
	/** ��ͨ�������ĵ������� */
	private static int normalFosterNum;

	/** �콱�ļ��ʱ�� �� */
	private static int[] rewardIntervalTime;

	/** ÿ���콱�Ĵ��� */
	private static int everyDayRewardNum;

	/** ÿ���콱��ʵ����ID */
	private static int[] rewardSampleId;

	/** ����ͻ�ƽ����������������� */
	private static int[] cardBeyongNum;
	//

	/** ��λ��ʤ����û��� */
	private static int[] duelWinPoint;
	/** ��λ��ʧ�ܻ�û��� */
	private static int[] duelLosePoint;
	/** ��λ��ÿ����ȡ���� */
	private static int[] dayPoint;

	/** �������������ȡս�����ֵ */
	private static int errorValue;
	/** �������ܻ�ȡ�����б����� */
	private static int enemySize;
	/** �������ܵݹ�ƥ����� */
	private static int matchTimes;
	/** ���������������� */
	private static int slaveConfine;
	/** ����������Ϣ�������� */
	private static int informationSize;
	/** �������ܹ����������� */
	private static int attConfine;
	/** �������ܷ����������� */
	private static int reactConfine;
	/** ������������������ */
	private static int getFreeGold;
	/** �������ܾ��˴������� */
	private static int saveConfine;
	/** �������ܾ�İ���˴������� */
	private static int saveNorConfine;
	/** ����������ȴ������� */
	private static int helpConfine;
	/** �������ܰ���ʱ������(����) */
	private static int workTime;
	/** �������ܰ��´������� */
	private static int workConfine;
	/** ���������й����������� */
	private static int managedGold;
	/** ����������ȥ�ٻ����������� */
	private static int fastWorkGold;
	/** ����BOSS���� */
	private static int[] globalBossList;
	/** ����BOSS�Զ����������� */
	private static int bossAutoSignGold;
	/** ��ҳ�ʼ������ս����SID*/
	private static int indexForNormalNPC;

	/** �����������ĵĻ������***/
	private static int engulfCardMony;
	/** �������������ÿ������ʱ�� */
	private static int serverTime;
	
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

	/** ���ָ���ȼ������������ľ��� */
	public static int getExp(int level)
	{
		return levelExp[level-1];
	}

	/** ���ָ���ȼ����������������ɿ�Ƭ���� */
	public static int getSkillLevel(int level)
	{
		return skillLevel[level*2+1];
	}
	/** ���ʹ���Ѿ���ʱ�Է������п���CDʱ����ٰٷֱ� */
	public static float getAwakeSoupTarCdTime()
	{
		return awakeSoupTarCdTime/100f;
	}

	public static void setAwakeSoupTarCdTime(int awakeSoupTarCdTime)
	{
		GameCFG.awakeSoupTarCdTime=awakeSoupTarCdTime;
	}
	/** ���ʹ���Ѿ���ʱ�Լ������п���CDʱ����ٰٷֱ� */
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

	/** ���ָ���ȼ�����VIP��Ҫ�Ľ�� */
	public static int getVipLevelGold(int level)
	{
		return vipLevelGold[level-1];
	}

	public void setVipLevelGold(int[] vipLevelGold)
	{
		GameCFG.vipLevelGold=vipLevelGold;
	}

	/** ���ָ��VIP�ȼ���Ӧ�����ñ�SID */
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

	/**
	 * �������BOSS����
	 * 
	 * @return
	 */
	public static int[] getGlobalBossList()
	{
		return globalBossList;
	}

	/**
	 * ͨ������ID����ö�Ӧ��Я��ID
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
	
	

	public static int getEngulfCardMony() {
		return engulfCardMony;
	} 

	public static void setEngulfCardMony(int engulfCardMony) {
		GameCFG.engulfCardMony = engulfCardMony;
	}

	/***
	 * @param rewardNum ��ǰ�콱����
	 * @return ��һ�ε��콱���ʱ��
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
	 * @param rewardNum ��ǰ�콱����
	 * @return ��һ�ε��콱��UID
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
	 * ���ݿ��Ƶȼ� ����ͻ�ƽ�����������������
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

	public static int getDuelWinPoint(int rank)
	{
		if(rank>=duelWinPoint.length-1) rank=duelWinPoint.length-1;
		return duelWinPoint[rank];
	}

	public static void setDuelWinPoint(int[] duelWinPoint)
	{
		GameCFG.duelWinPoint=duelWinPoint;
	}

	public static int getDuelLosePoint(int rank)
	{
		if(rank>=duelLosePoint.length-1) rank=duelLosePoint.length-1;
		return duelLosePoint[rank];
	}

	public static void setDuelLosePoint(int[] duelLosePoint)
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

	public static int[] getDuelWinPoint()
	{
		return duelWinPoint;
	}

	public static int[] getDuelLosePoint()
	{
		return duelLosePoint;
	}

	public static int[] getDayPoint()
	{
		return dayPoint;
	}
	/**
	 * ����BOSS����
	 * 
	 * @param globalBossList bossSid�����б�
	 */
	public static void setGlobalBossList(int[] globalBossList)
	{
		GameCFG.globalBossList=globalBossList;
	}

	/**
	 * ��ȡ�����ȡս�����ֵ(����)
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
	 * ��ȡ�����б�����(����)
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
	 * ��ȡ�ݹ�ƥ�����(����)
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
	 * ��ȡ��������
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
	 * ��ȡ��Ϣ����������
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
	 * ��ȡ������������
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
	 * ��ȡ������������
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
	 * ��ȡ����ļ۸�
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
	 * ��ȡ���˴�������
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
	 * ��ȡ����İ���˴�������
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
	 * ��ȡ��������ʱ��
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
	 * ��ȡ���´�������
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
	 * �й�����������
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
	 * ��ȡ��ȥ�ٻ�������
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
	 * ��ȡ�Զ������������
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
	 * ��ȡ����ˢ������Ҫ�Ľ��
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
	 * ��ȴ���
	 * @return
	 */
	public static int getHelpConfine()
	{
		return helpConfine;
	}

	public static void setHelpConfine(int helpConfine)
	{
		GameCFG.helpConfine = helpConfine;
	}
	
	/***
	 * ��ȡ��ҳ�ʼ������ս����SID
	 * @return
	 */
	public static int getIndexForNormalNPC()
	{
		return indexForNormalNPC;
	}

	public static void setIndexForNormalNPC(int indexForNormalNPC)
	{
		GameCFG.indexForNormalNPC = indexForNormalNPC;
	}

	public static int getServerTime()
	{
		return serverTime;
	}

	public static void setServerTime(int serverTime)
	{
		GameCFG.serverTime = serverTime;
	}
}
