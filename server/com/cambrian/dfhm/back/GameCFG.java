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
	/** ���齱 */
	private static int[] soulLottery;
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
	/** ��Я�� */
	private static int[] DoubleSkill;
	/** �������� ���ĵĽ����*/
	private static int goldFosterNum;
	/**��������׺ĵĽ����*/
	private static int moneyFosterNum;
	/**��ͨ�������ĵ�������*/
	private static int normalFosterNum;
	//
	/** ����BOSS����*/
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

	/** ���ָ���ȼ������������ľ��� */
	public static int getExp(int level)
	{
		return levelExp[level - 1];
	}

	/** ���ָ���ȼ����������������ɿ�Ƭ���� */
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
	
	public static void setDoubleSkill(int[] doubleSkill)
	{
		DoubleSkill=doubleSkill;
		System.err.println("length ===="+DoubleSkill.length);
	}
	
	/**
	 * �������BOSS����
	 * @return 
	 */
	public static int[] getGlobalBossList()
	{
		return globalBossList;
	}

	/**
	 * ͨ������ID����ö�Ӧ��Я��
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
	 *  ͨ������ID����ö�Ӧ��Я������
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
	 * ����BOSS����
	 * @param globalBossList bossSid�����б�
	 */
	public static void setGlobalBossList(int[] globalBossList)
	{
		GameCFG.globalBossList = globalBossList;
	}


}
