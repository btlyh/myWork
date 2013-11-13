package com.cambrian.dfhm.back;

/**
 * ��˵������Ϸȫ������(��������)
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class GameCFG {

	/** ������������[ÿ������] */
	private static int[] levelExp;
	/** ��������[ÿ�����飬���ɿ�������] */
	private static int[] skillLevel;
	/** VIP�ȼ�����Ҫ��ֵ�Ľ�� */
	private static int[] vipLevelGold;
	/** VIP�ȼ���Ӧ�����ñ� */
	private static int[] vipCfgXml;
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
	/** ��Я��[ID����������] */
	private static int[] DoubleSkill;
	/** �������� ���ĵĽ���� */
	private static int goldFosterNum;
	/** ��������׺ĵĽ���� */
	private static int moneyFosterNum;
	/** ��ͨ�������ĵ������� */
	private static int normalFosterNum;
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
	/** ��������������������*/
	private static int getFreeGold;
	/** ����BOSS���� */
	private static int[] globalBossList;

	public static int getPayForAwakeMinutes() {
		return payForAwakeMinutes;
	}

	public static void setPayForAwakeMinutes(int payForAwakeMinutes) {
		GameCFG.payForAwakeMinutes = payForAwakeMinutes;
	}

	public static int getPayForAwakeGold() {
		return payForAwakeGold;
	}

	public static void setPayForAwakeGold(int payForAwakeGold) {
		GameCFG.payForAwakeGold = payForAwakeGold;
	}

	public void setLevelExp(int[] levelExp) {
		GameCFG.levelExp = levelExp;
	}

	public void setSkillLevel(int[] skillLevel) {
		GameCFG.skillLevel = skillLevel;
	}

	public void setSoulLottery(int[] soulLottery) {
		GameCFG.soulLottery = soulLottery;
	}

	public void setVipCfgXml(int[] vipCfgXml) {
		GameCFG.vipCfgXml = vipCfgXml;
	}

	/** ���ָ���ȼ������������ľ��� */
	public static int getExp(int level) {
		return levelExp[level - 1];
	}

	/** ���ָ���ȼ����������������ɿ�Ƭ���� */
	public static int getSkillLevel(int level) {
		return skillLevel[level * 2 + 1];
	}

	public static float getAwakeSoupTarCdTime() {
		return awakeSoupTarCdTime / 100f;
	}

	public static void setAwakeSoupTarCdTime(int awakeSoupTarCdTime) {
		GameCFG.awakeSoupTarCdTime = awakeSoupTarCdTime;
	}

	public static float getAwakeSoupOwnCdTime() {
		return awakeSoupOwnCdTime / 100f;
	}

	public static void setAwakeSoupOwnCdTime(int awakeSoupOwnCdTime) {
		GameCFG.awakeSoupOwnCdTime = awakeSoupOwnCdTime;
	}

	public static int getDrinkCd() {
		return drinkCd;
	}

	public static void setDrinkCd(int drinkCd) {
		GameCFG.drinkCd = drinkCd;
	}

	public static int getAwakeSoupCd() {
		return awakeSoupCd;
	}

	public static void setAwakeSoupCd(int awakeSoupCd) {
		GameCFG.awakeSoupCd = awakeSoupCd;
	}

	/** ���ָ���ȼ�����VIP��Ҫ�Ľ�� */
	public static int getVipLevelGold(int level) {
		return vipLevelGold[level - 1];
	}

	public void setVipLevelGold(int[] vipLevelGold) {
		GameCFG.vipLevelGold = vipLevelGold;
	}

	/** ���ָ��VIP�ȼ���Ӧ�����ñ�SID */
	public static int getVipCfg(int level) {
		return vipCfgXml[level];
	}

	public static void setDoubleSkill(int[] doubleSkill) {
		DoubleSkill = doubleSkill;
		System.err.println("length ====" + DoubleSkill.length);
	}

	/**
	 * �������BOSS����
	 * 
	 * @return
	 */
	public static int[] getGlobalBossList() {
		return globalBossList;
	}

	/**
	 * ͨ������ID����ö�Ӧ��Я��ID
	 * 
	 * @param skillId
	 * @return
	 */
	public static int[] getDSkillById(int skillId) {
		int id;
		for (int i = 0; i < DoubleSkill.length; i += 3) {
			id = DoubleSkill[i];
			if (skillId == id) {
				int[] dSkill = new int[3];
				dSkill[0] = id;
				dSkill[1] = DoubleSkill[i + 1];
				dSkill[2] = DoubleSkill[i + 2];
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
	 * ͨ������ID����ö�Ӧ��Я������
	 * 
	 * @param skillId
	 * @return
	 */
	public static int getDsSkillRateById(int skillId) {
		for (int i = 0; i < DoubleSkill.length; i += 2) {
			if (skillId == DoubleSkill[i])
				return DoubleSkill[i + 2];
		}
		return -1;
	}

	/**
	 * ����BOSS����
	 * 
	 * @param globalBossList
	 *            bossSid�����б�
	 */
	public static void setGlobalBossList(int[] globalBossList) {
		GameCFG.globalBossList = globalBossList;
	}

	/**
	 * ��ȡ�����ȡս�����ֵ(����)
	 * 
	 * @return
	 */
	public static int getErrorValue() {
		return errorValue;
	}

	public static void setErrorValue(int errorValue) {
		GameCFG.errorValue = errorValue;
	}

	/**
	 * ��ȡ�����б�����(����)
	 * 
	 * @return
	 */
	public static int getEnemySize() {
		return enemySize;
	}

	public static void setEnemySize(int enemySize) {
		GameCFG.enemySize = enemySize;
	}

	/**
	 * ��ȡ�ݹ�ƥ�����(����)
	 * 
	 * @return
	 */
	public static int getMatchTimes() {
		return matchTimes;
	}

	public static void setMatchTimes(int matchTimes) {
		GameCFG.matchTimes = matchTimes;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public static int getSlaveConfine() {
		return slaveConfine;
	}

	public static void setSlaveConfine(int slaveConfine) {
		GameCFG.slaveConfine = slaveConfine;
	}

	/**
	 * ��ȡ��Ϣ����������
	 * 
	 * @return
	 */
	public static int getInformationSize() {
		return informationSize;
	}

	public static void setInformationSize(int informationSize) {
		GameCFG.informationSize = informationSize;
	}

	/**
	 * ��ȡ������������
	 * 
	 * @return
	 */
	public static int getAttConfine() {
		return attConfine;
	}

	public static void setAttConfine(int attConfine) {
		GameCFG.attConfine = attConfine;
	}
	/**
	 * ��ȡ������������
	 * @return
	 */
	public static int getReactConfine() {
		return reactConfine;
	}

	public static void setReactConfine(int reactConfine) {
		GameCFG.reactConfine = reactConfine;
	}
	
	/**
	 * ��ȡ����ļ۸�
	 * @return
	 */
	public static int getGetFreeGold() {
		return getFreeGold;
	}

	public static void setGetFreeGold(int getFreeGold) {
		GameCFG.getFreeGold = getFreeGold;
	}
}
