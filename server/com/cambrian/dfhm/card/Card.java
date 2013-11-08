package com.cambrian.dfhm.card;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.skill.Skill;
import com.cambrian.common.util.TimeKit;

/**
 * 类说明：
 * 
 * @author：Sebastian
 */
public class Card extends Sample
{

	/* static fields */
	/** 状态：没状态=1，上阵=,2，恢复=3 */
	public static int NORMAL=1,ATTACK=2,RECOVER=3;
	/** 喝酒状态: 振奋=1，醉酒=2，正常=3 */
	public static int HYPER=1,DRUNK=2,AWAKE=3;

	/* static methods */

	/* fields */
	/** 编号 */
	public int uid;
	private String name;
	/** 介绍 */
	private String description;
	/** 卡片头像(前台用) */
	private String avatar;
	/** 小卡片头像(前台用) */
	private String tinyAvatar;
	/** 类型（白绿蓝紫橙，5种） */
	private int type;
	/** 出售价格 */
	private int money;
	/** 攻击力 */
	private int att;
	/** 技能发动率 */
	private int skillRate;
	/** 最大体力 */
	private int maxHp;
	/** 当前体力 */
	private int curHp;
	/** 普通攻击范围 (跟技能攻击范围一样) */
	private int attRange;
	/** 拥有技能ID */
	private int skillId;
	/** 刷新出的技能ID */
	private int flushSkillId=-1;
	/** 可拥有技能范围 */
	private int[] skillRange;
	/** 等级 */
	private int level=1;
	/** 培养点数 */
	private int forsterNumber=9999;
	/** 暴击率 */
	private int critRate;
	/** 闪避率 */
	private int dodgeRate;
	/** 金币培养攻击成长最大值 */
	private int moneyFosterAttMax;
	/** 金币培养攻击成长最小值 */
	private int moneyFosterAttMin;
	/** 土豪培养攻击成长最大值 */
	private int goldFosterAttMax;
	/** 土豪培养攻击成长最小值 */
	private int goldFosterAttMin;	
	/** 普通培养攻击成长最大值*/
	private int normalFosterAttMax;
	/** 普通培养攻击成长最小值*/
	private int normalFosterAttMin;
	/** 金币培养技能发动率成长最大值 */
	private int moneyFosterRangeMax;
	/** 金币培养技能发动率成长最小值 */
	private int moneyFosterRangeMin;
	/** 土豪培养技能发动率成长最大值 */
	private int goldFosterRangeMax;
	/** 土豪培养技能发动率成长最小值 */
	private int goldFosterRangeMin;
	/**普通培养技能发动率成长最大值*/
	private int normalFosterRangeMax;
	/**普通培养技能发动率成长最大值*/
	private int normalFosterRangeMin;
	/** 金币培养体力成长最大值 */
	private int moneyFosterHpMax;
	/** 金币培养体力成长最小值 */
	private int moneyFosterHpMin;
	/** 土豪培养体力成长最大值 */
	private int goldFosterHpMax;
	/** 土豪培养体力成长最小值 */
	private int goldFosterHpMin;
	/** 普通培养体力成长最大值 */
	private int normalFosterHpMax;
	/** 普通培养体力成长最小值 */
	private int normalFosterHpMin;
	/** 状态 */
	private int status;
	/** 培养攻总增加值 */
	private int forsterAtt;
	/** 培养技能发动率总增加值 */
	private int forsterRange;
	/** 培养体力总增加值 */
	private int forsterHp;
	/** 最近一次培养攻,增加值 */
	private int forsterAttLast;
	/** 最近一次培养技能发动率，增加值 */
	private int forsterRangeLast;
	/** 最近一次培养体力，增加值 */
	private int forsterHpLast;
	/** 攻击目标对象，1=自身，2=敌人 */
	private int aimType;
	/** 卡牌基础经验(被吞噬用) */
	private int swallowExp;
	/** 经验 */
	private int exp;
	/** 技能等级 */
	private int skillLevel;
	/** 境界突破限制 [等级，卡牌id,卡牌id,卡牌id,卡牌id,卡牌id,奖励培养次数] */
	private int[] realmLimit;
	/** 境界突破状态 */
	private boolean realm=false;
	/** 已经吃过的卡牌(突破瓶颈用) */
	private ArrayList<Integer> engulfCards=new ArrayList<Integer>();
	/** 卡牌是否锁定 */
	private boolean isLock=false;
	
	/** 喝酒状态 */
	private int drinkStatus = Card.AWAKE;
	/** 是否在军帐中 0：不在，1：在*/
	private int isInArmyCamp = 0;
	/** 上次喝酒时间 */
	private long lastDrinkTime;
	/** 所在军帐玩家名字 */
	private String armyName;	
	
	
	/* constructors */

	

	/* properties */
	@Override
	public int getId()
	{
		return uid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type=type;
	}

	public int getMoney()
	{
		return money;
	}

	public void setMoney(int money)
	{
		this.money=money;
	}

	public int getAtt()
	{
		return att;
	}

	public void setAtt(int att)
	{
		this.att=att;
	}

	public int getSkillRate()
	{
		return skillRate;
	}

	public void setSkillRate(int skillRate)
	{
		this.skillRate=skillRate;
	}

	public int getMaxHp()
	{
		return maxHp;
	}

	public void setMaxHp(int maxHp)
	{
		this.maxHp=maxHp;
	}

	public int getCurHp()
	{
		return curHp;
	}

	public void setCurHp(int curHp)
	{
		this.curHp=curHp;
	}

	public int getSkillId()
	{
		return skillId;
	}

	public void setSkillId(int skillId)
	{
		this.skillId=skillId;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level=level;
	}

	public int getForsterNumber()
	{
		return forsterNumber;
	}

	public void setForsterNumber(int forsterNumber)
	{
		this.forsterNumber=forsterNumber;
	}

	public int[] getSkillRange()
	{
		return skillRange;
	}

	public void setSkillRange(int[] skillRange)
	{
		this.skillRange=skillRange;
	}

	public int getCritRate()
	{
		return critRate;
	}

	public void setCritRate(int critRate)
	{
		this.critRate=critRate;
	}

	public int getDodgeRate()
	{
		return dodgeRate;
	}

	public void setDodgeRate(int dodgeRate)
	{
		this.dodgeRate=dodgeRate;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status=status;
	}

	public int getAttRange()
	{
		return attRange;
	}

	public void setAttRange(int attRange)
	{
		this.attRange=attRange;
	}

	public int getForsterAtt()
	{
		return forsterAtt;
	}

	public void setForsterAtt(int forsterAtt)
	{
		this.forsterAtt=forsterAtt;
	}

	public int getForsterAttLast()
	{
		return forsterAttLast;
	}

	public void setForsterAttLast(int forsterAttLast)
	{
		this.forsterAttLast=forsterAttLast;
	}

	public int getMoneyFosterAttMax()
	{
		return moneyFosterAttMax;
	}

	public void setMoneyFosterAttMax(int moneyFosterAttMax)
	{
		this.moneyFosterAttMax=moneyFosterAttMax;
	}

	public int getMoneyFosterAttMin()
	{
		return moneyFosterAttMin;
	}

	public void setMoneyFosterAttMin(int moneyFosterAttMin)
	{
		this.moneyFosterAttMin=moneyFosterAttMin;
	}

	public int getGoldFosterAttMax()
	{
		return goldFosterAttMax;
	}

	public void setGoldFosterAttMax(int goldFosterAttMax)
	{
		this.goldFosterAttMax=goldFosterAttMax;
	}

	public int getGoldFosterAttMin()
	{
		return goldFosterAttMin;
	}

	public void setGoldFosterAttMin(int goldFosterAttMin)
	{
		this.goldFosterAttMin=goldFosterAttMin;
	}

	public int getMoneyFosterRangeMax()
	{
		return moneyFosterRangeMax;
	}

	public void setMoneyFosterRangeMax(int moneyFosterRangeMax)
	{
		this.moneyFosterRangeMax=moneyFosterRangeMax;
	}

	public int getMoneyFosterRangeMin()
	{
		return moneyFosterRangeMin;
	}

	public void setMoneyFosterRangeMin(int moneyFosterRangeMin)
	{
		this.moneyFosterRangeMin=moneyFosterRangeMin;
	}

	public int getGoldFosterRangeMax()
	{
		return goldFosterRangeMax;
	}

	public void setGoldFosterRangeMax(int goldFosterRangeMax)
	{
		this.goldFosterRangeMax=goldFosterRangeMax;
	}

	public int getGoldFosterRangeMin()
	{
		return goldFosterRangeMin;
	}

	public void setGoldFosterRangeMin(int goldFosterRangeMin)
	{
		this.goldFosterRangeMin=goldFosterRangeMin;
	}

	public int getMoneyFosterHpMax()
	{
		return moneyFosterHpMax;
	}

	public void setMoneyFosterHpMax(int moneyFosterHpMax)
	{
		this.moneyFosterHpMax=moneyFosterHpMax;
	}

	public int getMoneyFosterHpMin()
	{
		return moneyFosterHpMin;
	}

	public void setMoneyFosterHpMin(int moneyFosterHpMin)
	{
		this.moneyFosterHpMin=moneyFosterHpMin;
	}

	public int getGoldFosterHpMax()
	{
		return goldFosterHpMax;
	}

	public void setGoldFosterHpMax(int goldFosterHpMax)
	{
		this.goldFosterHpMax=goldFosterHpMax;
	}

	public int getGoldFosterHpMin()
	{
		return goldFosterHpMin;
	}

	public void setGoldFosterHpMin(int goldFosterHpMin)
	{
		this.goldFosterHpMin=goldFosterHpMin;
	}

	
	public int getNormalFosterAttMax() {
		return normalFosterAttMax;
	}

	public void setNormalFosterAttMax(int normalFosterAttMax) {
		this.normalFosterAttMax = normalFosterAttMax;
	}

	public int getNormalFosterAttMin() {
		return normalFosterAttMin;
	}

	public void setNormalFosterAttMin(int normalFosterAttMin) {
		this.normalFosterAttMin = normalFosterAttMin;
	}

	public int getNormalFosterRangeMax() {
		return normalFosterRangeMax;
	}

	public void setNormalFosterRangeMax(int normalFosterRangeMax) {
		this.normalFosterRangeMax = normalFosterRangeMax;
	}

	public int getNormalFosterRangeMin() {
		return normalFosterRangeMin;
	}

	public void setNormalFosterRangeMin(int normalFosterRangeMin) {
		this.normalFosterRangeMin = normalFosterRangeMin;
	}

	public int getNormalFosterHpMax() {
		return normalFosterHpMax;
	}

	public void setNormalFosterHpMax(int normalFosterHpMax) {
		this.normalFosterHpMax = normalFosterHpMax;
	}

	public int getNormalFosterHpMin() {
		return normalFosterHpMin;
	}

	public void setNormalFosterHpMin(int normalFosterHpMin) {
		this.normalFosterHpMin = normalFosterHpMin;
	}
	public int getForsterRange()
	{
		return forsterRange;
	}

	public void setForsterRange(int forsterRange)
	{
		this.forsterRange=forsterRange;
	}

	public int getForsterHp()
	{
		return forsterHp;
	}

	public void setForsterHp(int forsterHp)
	{
		this.forsterHp=forsterHp;
	}

	public int getForsterRangeLast()
	{
		return forsterRangeLast;
	}

	public void setForsterRangeLast(int forsterRangeLast)
	{
		this.forsterRangeLast=forsterRangeLast;
	}

	public int getForsterHpLast()
	{
		return forsterHpLast;
	}

	public void setForsterHpLast(int forsterHpLast)
	{
		this.forsterHpLast=forsterHpLast;
	}

	public int getAimType()
	{
		return aimType;
	}

	public void setAimType(int aimType)
	{
		this.aimType=aimType;
	}

	public int getSwallowExp()
	{
		return swallowExp;
	}

	public void setSwallowExp(int swallowExp)
	{
		this.swallowExp=swallowExp;
	}

	public int getExp()
	{
		return exp;
	}

	public void setExp(int exp)
	{
		this.exp=exp;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar=avatar;
	}

	public String getTinyAvatar()
	{
		return tinyAvatar;
	}

	public void setTinyAvatar(String tinyAvatar)
	{
		this.tinyAvatar=tinyAvatar;
	}

	public int getSkillLevel()
	{
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel)
	{
		this.skillLevel=skillLevel;
	}

	public void setRealmLimit(int[] realmLimit)
	{
		this.realmLimit=realmLimit;
	}

	public int getFlushSkillId()
	{
		return flushSkillId;
	}

	public void setFlushSkillId(int flushSkillId)
	{
		this.flushSkillId=flushSkillId;
	}

	public ArrayList<Integer> getEngulfCards()
	{
		return engulfCards;
	}

	public void setEngulfCards(ArrayList<Integer> engulfCards)
	{
		this.engulfCards=engulfCards;
	}
	
	public boolean isRealm()
	{
		return realm;
	}

	public void setRealm(boolean realm)
	{
		this.realm=realm;
	}

	public boolean isLock()
	{
		return isLock;
	}

	public void setLock(boolean isLock)
	{
		this.isLock=isLock;
	}

	public int getDrinkStatus()
	{
		return drinkStatus;
	}

	public void setDrinkStatus(int drinkStatus)
	{
		this.drinkStatus = drinkStatus;
	}

	public int isInArmyCamp()
	{
		return isInArmyCamp;
	}

	public void setInArmyCamp(int isInArmyCamp)
	{
		this.isInArmyCamp = isInArmyCamp;
	}

	public long getLastDrinkTime()
	{
		return lastDrinkTime;
	}

	public void setLastDrinkTime(long lastDrinkTime)
	{
		this.lastDrinkTime = lastDrinkTime;
	}

	public String getArmyName()
	{
		return armyName;
	}

	public void setArmyName(String armyName)
	{
		this.armyName = armyName;
	}
	/* init start */
	public void init()
	{
		int index=MathKit.randomValue(0,skillRange.length);
		skillId=skillRange[index];
		curHp=maxHp;
		drinkStatus = Card.AWAKE;
	}

	/* methods */
	public void decrForsterNumber(int count)
	{
		forsterNumber-=count;
	}

	public void incrForsterNumber(int count)
	{
		forsterNumber+=count;
	}

	public void incrForsterAtt(int forsterAtt)
	{
		this.forsterAtt+=forsterAtt;
	}

	public void incrForsterRange(int forsterRange)
	{
		this.forsterRange+=forsterRange;
	}

	public void incrForsterHp(int forsterHp)
	{
		this.forsterHp+=forsterHp;
	}

	public void incrExp(int exp)
	{
		this.exp+=exp;
	}

	public void incrAtt(int att)
	{
		this.att+=att;
	}

	public void decrAtt(int att)
	{
		this.att-=att;
	}

	public void incrHp(int hp)
	{
		this.maxHp+=hp;
		this.curHp+=hp;
	}

	public void decrHp(int hp)
	{
		this.maxHp-=hp;
		if(maxHp<=curHp) curHp=maxHp;
	}

	public void incrSkillRate(int skillRate)
	{
		this.skillRate+=skillRate;
	}

	public void decrSkillRate(int skillRate)
	{
		this.skillRate-=skillRate;
	}

	/**
	 * 获得境界突破限制
	 * 
	 * @param level 需求等级
	 * @return 限制[卡牌id,卡牌id,卡牌id,卡牌id,卡牌id,奖励培养次数]
	 */
	public int[] getRealmByLevel()
	{
		int[] limit=new int[6];
		for(int i=0;i<realmLimit.length;i+=7)
		{
			if(realmLimit[i]==level)
			{
				for(int j=0;j<limit.length;j++)
				{
					limit[j]=realmLimit[i+j+1];
				}
				break;
			}
		}
		return limit;
	}

	/** 升级经验计算 */
	public boolean levelUp()
	{
		boolean result=false;
		// TODO 一共有99级，每级的经验配置在gameconfig里面
		while(exp>GameCFG.getExp(level))
		{
			level++;
			result=true;
		}
		if(result)
		{
			int temp=level;
			forsterNumber+=--temp;
		}
		return result;
	}
	/** 获取卡牌战斗力 */
	public int getZhandouli()
	{
		Skill skill = (Skill)Sample.getFactory().getSample(skillId);
		//return curHp*(att*((100000-skillRate)/1000)+ (att*skill.getAtt()/1000)*(skill.getSkillRate()/1000))/10000;
		return (maxHp+att*3+(int)(att*1.5)*(critRate/1000)*3+maxHp*(dodgeRate/1000)+att*skill.getAtt()*(skill.getSkillRate()/1000)*3)/10000;
		//return 4950*(1130*((100000-50535)/1000)+ (1130*skill.getAtt()/1000)*(50535/1000))/100000;
		//战斗力=(血量+普通攻击*标准回合数+攻击力*暴击伤害*暴击概率*标准回合数+血量*闪避概率+攻击力*技能系数*技能概率*标准回合数)*系数

	}

	/** 前台序列化读取 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(uid);
		data.writeUTF(name);
		data.writeUTF(description);
		data.writeUTF(avatar);
		data.writeUTF(tinyAvatar);
		data.writeInt(type);
		data.writeInt(money);
		data.writeInt(att);
		data.writeInt(skillRate);
		data.writeInt(maxHp);
		data.writeInt(curHp);
		data.writeInt(attRange);
		data.writeInt(skillId);
		data.writeInt(level);
		data.writeInt(forsterNumber);
		data.writeInt(skillRange.length);
		// System.err.println("name ==="+ name);
		// System.err.println("description ==="+ description);
		// System.err.println("type ==="+ type);
		// System.err.println("money ==="+ money);
		// System.err.println("att ==="+ att);
		// System.err.println("skillRate ==="+ skillRate);
		// System.err.println("maxHp ==="+ maxHp);
		// System.err.println("curHp ==="+ curHp);
		// System.err.println("attRange ==="+ attRange);
		// System.err.println("skillId ==="+ skillId);
		// System.err.println("level ==="+ level);
		// System.err.println("fosterNumber ==="+ fosterNumber);
		// System.err.println("skillRange.length ==="+ skillRange.length);
		for(int i=0;i<skillRange.length;i++)
		{
			data.writeInt(skillRange[i]);
			// System.err.println("skillRange[i] ==="+skillRange[i]);
		}
	
		data.writeInt(critRate);
		data.writeInt(dodgeRate);
		data.writeInt(status);
		data.writeInt(forsterAttLast);
		if(uid == 28){
			System.err.println(forsterAttLast);
		}
		data.writeInt(forsterRangeLast);
		data.writeInt(forsterHpLast);
		
		data.writeInt(aimType);
		data.writeInt(swallowExp);
		data.writeInt(exp);
		data.writeInt(flushSkillId);
		// data.writeInt(skillSwallowCount);

		// System.err.println("critRate ==="+ critRate);
		// System.err.println("dodgeRate ==="+ dodgeRate);
		// System.err.println("status ==="+ status);
		// System.err.println("forsterAtt ==="+ forsterAtt);
		// System.err.println("forsterRange ==="+ forsterRange);
		// System.err.println("forsterHp ==="+ forsterHp);
		// System.err.println("aimType ==="+ aimType);
		// System.err.println("swallowExp ==="+ swallowExp);
		// System.err.println("exp ==="+ exp);
		data.writeBoolean(realm);
		data.writeInt(engulfCards.size());
		for(Integer cardSId:engulfCards)
		{
			data.writeInt(cardSId);
		}
		data.writeBoolean(isLock);
		data.writeInt(getSid());
		data.writeInt(skillLevel);
		data.writeInt(drinkStatus);
		data.writeInt(isInArmyCamp);
		data.writeLong(TimeKit.nowTimeMills() - lastDrinkTime);
	}

	/** 写入db数据 */
	public void bytesWrite_db(ByteBuffer data)
	{
		System.err.println("------Card.bytesWrite_db--------");
		data.writeInt(uid);
		data.writeUTF(name);
		data.writeUTF(description);
		data.writeUTF(avatar);
		data.writeUTF(tinyAvatar);
		data.writeInt(type);
		data.writeInt(money);
		data.writeInt(att);
		data.writeInt(skillRate);
		data.writeInt(maxHp);
		data.writeInt(curHp);
		data.writeInt(attRange);
		data.writeInt(skillId);
		data.writeInt(flushSkillId);
		data.writeInt(level);
		data.writeInt(forsterNumber);
		data.writeInt(skillRange.length);
		// System.err.println("name ==="+ name);
		// System.err.println("description ==="+ description);
		// System.err.println("type ==="+ type);
		// System.err.println("money ==="+ money);
		// System.err.println("att ==="+ att);
		// System.err.println("skillRate ==="+ skillRate);
		// System.err.println("maxHp ==="+ maxHp);
		// System.err.println("curHp ==="+ curHp);
		// System.err.println("attRange ==="+ attRange);
		// System.err.println("skillId ==="+ skillId);
		// System.err.println("level ==="+ level);
		// System.err.println("fosterNumber ==="+ fosterNumber);
		// System.err.println("skillRange.length ==="+ skillRange.length);
		for(int i=0;i<skillRange.length;i++)
		{
			data.writeInt(skillRange[i]);
			// System.err.println("skillRange[i] ==="+ skillRange[i]);
		}
		data.writeInt(critRate);
		data.writeInt(dodgeRate);
		data.writeInt(moneyFosterAttMax);
		data.writeInt(moneyFosterAttMin);
		data.writeInt(goldFosterAttMax);
		data.writeInt(goldFosterAttMin);		
		data.writeInt(normalFosterAttMax);
		data.writeInt(normalFosterAttMin);	
		data.writeInt(moneyFosterRangeMax);
		data.writeInt(moneyFosterRangeMin);
		data.writeInt(goldFosterRangeMax);
		data.writeInt(goldFosterRangeMin);
		data.writeInt(normalFosterRangeMax);
		data.writeInt(normalFosterRangeMin);
		data.writeInt(moneyFosterHpMax);
		data.writeInt(moneyFosterHpMin);
		data.writeInt(goldFosterHpMax);
		data.writeInt(goldFosterHpMin);
		data.writeInt(normalFosterHpMax);
		data.writeInt(normalFosterHpMin);
		data.writeInt(status);
		data.writeInt(forsterAtt);
		data.writeInt(forsterRange);
		data.writeInt(forsterHp);
		data.writeInt(forsterAttLast);
		data.writeInt(forsterRangeLast);
		data.writeInt(forsterHpLast);
		data.writeInt(aimType);
		data.writeInt(swallowExp);
		data.writeInt(exp);
		data.writeInt(skillLevel);
		// data.writeInt(skillSwallowCount);
		// data.writeInt(attIndex);
		// System.err.println("critRate ==="+ critRate);
		// System.err.println("dodgeRate ==="+ dodgeRate);
		// System.err.println("moneyFosterAttMin ==="+ moneyFosterAttMin);
		// System.err.println("moneyFosterAttMin ==="+ moneyFosterAttMin);
		// System.err.println("goldFosterAttMax ==="+ goldFosterAttMax);
		// System.err.println("goldFosterAttMin ==="+ goldFosterAttMin);
		// System.err.println("moneyFosterRangeMax ==="+
		// moneyFosterRangeMax);
		// System.err.println("moneyFosterRangeMin ==="+
		// moneyFosterRangeMin);
		// System.err.println("goldFosterRangeMax ==="+ goldFosterRangeMax);
		// System.err.println("goldFosterRangeMin ==="+ goldFosterRangeMin);
		// System.err.println("moneyFosterHpMax ==="+ moneyFosterHpMax);
		// System.err.println("moneyFosterHpMin ==="+ moneyFosterHpMin);
		// System.err.println("goldFosterHpMax ==="+ goldFosterHpMax);
		// System.err.println("goldFosterHpMin ==="+ goldFosterHpMin);
		// System.err.println("doubleSkill[0] ==="+ doubleSkill[0]);
		// System.err.println("doubleSkill[1] ==="+ doubleSkill[1]);
		// System.err.println("status ==="+ status);
		// System.err.println("forsterAtt ==="+ forsterAtt);
		// System.err.println("forsterRange ==="+ forsterRange);
		// System.err.println("forsterHp ==="+ forsterHp);
		// System.err.println("forsterAttLast ==="+ forsterAttLast);
		// System.err.println("forsterRangeLast ==="+ forsterRangeLast);
		// System.err.println("forsterHpLast ==="+ forsterHpLast);
		// System.err.println("aimType ==="+ aimType);
		// System.err.println("swallowExp ==="+ swallowExp);
		// System.err.println("exp ==="+ exp);
		data.writeBoolean(realm);
		data.writeInt(engulfCards.size());
		for(Integer cardSId:engulfCards)
		{
			data.writeInt(cardSId);
		}
		data.writeBoolean(isLock);
		data.writeInt(drinkStatus);
		data.writeInt(isInArmyCamp);
		data.writeLong(lastDrinkTime);
		data.writeUTF(armyName);
	}

	/** 从db读取数据 */
	public void bytesRead_db(ByteBuffer data)
	{
		System.err.println("------Card.bytesRead_db--------");
		uid=data.readInt();
		name=data.readUTF();
		description=data.readUTF();
		avatar=data.readUTF();
		tinyAvatar=data.readUTF();
		type=data.readInt();
		money=data.readInt();
		att=data.readInt();
		skillRate=data.readInt();
		maxHp=data.readInt();
		curHp=data.readInt();
		attRange=data.readInt();
		skillId=data.readInt();
		flushSkillId=data.readInt();
		level=data.readInt();
		forsterNumber=data.readInt();
		int len=data.readInt();
		skillRange=new int[len];
		// System.err.println("name ==="+ name);
		// System.err.println("description ==="+ description);
		// System.err.println("type ==="+ type);
		// System.err.println("money ==="+ money);
		// System.err.println("att ==="+ att);
		// System.err.println("skillRate ==="+ skillRate);
		// System.err.println("maxHp ==="+ maxHp);
		// System.err.println("curHp ==="+ curHp);
		// System.err.println("attRange ==="+ attRange);
		// System.err.println("skillId ==="+ skillId);
		// System.err.println("level ==="+ level);
		// System.err.println("fosterNumber ==="+ fosterNumber);
		// System.err.println("skillRange.length ==="+ skillRange.length);
		for(int i=0;i<len;i++)
		{
			skillRange[i]=data.readInt();
			// System.err.println("skillRange[i] ==="+ skillRange[i]);
		}
		critRate=data.readInt();
		dodgeRate=data.readInt();
		moneyFosterAttMax=data.readInt();
		moneyFosterAttMin=data.readInt();
		goldFosterAttMax=data.readInt();
		goldFosterAttMin=data.readInt();
		normalFosterAttMax=data.readInt();
		normalFosterAttMin=data.readInt();		
		moneyFosterRangeMax=data.readInt();
		moneyFosterRangeMin=data.readInt();
		goldFosterRangeMax=data.readInt();
		goldFosterRangeMin=data.readInt();
		normalFosterRangeMax=data.readInt();
		normalFosterRangeMin=data.readInt();
		moneyFosterHpMax=data.readInt();
		moneyFosterHpMin=data.readInt();
		goldFosterHpMax=data.readInt();
		goldFosterHpMin=data.readInt();
		normalFosterHpMax=data.readInt();
		normalFosterHpMin=data.readInt();
		status=data.readInt();
		forsterAtt=data.readInt();
		forsterRange=data.readInt();
		forsterHp=data.readInt();
		forsterAttLast=data.readInt();
		forsterRangeLast=data.readInt();
		forsterHpLast=data.readInt();
		aimType=data.readInt();
		swallowExp=data.readInt();
		exp=data.readInt();
		skillLevel=data.readInt();
		// skillSwallowCount = data.readInt();
		// attIndex = data.readInt();
		// System.err.println("critRate ==="+ critRate);
		// System.err.println("dodgeRate ==="+ dodgeRate);
		// System.err.println("moneyFosterAttMin ==="+ moneyFosterAttMin);
		// System.err.println("moneyFosterAttMin ==="+ moneyFosterAttMin);
		// System.err.println("goldFosterAttMax ==="+ goldFosterAttMax);
		// System.err.println("goldFosterAttMin ==="+ goldFosterAttMin);
		// System.err.println("moneyFosterRangeMax ==="+
		// moneyFosterRangeMax);
		// System.err.println("moneyFosterRangeMin ==="+
		// moneyFosterRangeMin);
		// System.err.println("goldFosterRangeMax ==="+ goldFosterRangeMax);
		// System.err.println("goldFosterRangeMin ==="+ goldFosterRangeMin);
		// System.err.println("moneyFosterHpMax ==="+ moneyFosterHpMax);
		// System.err.println("moneyFosterHpMin ==="+ moneyFosterHpMin);
		// System.err.println("goldFosterHpMax ==="+ goldFosterHpMax);
		// System.err.println("goldFosterHpMin ==="+ goldFosterHpMin);
		// System.err.println("doubleSkill[0] ==="+ doubleSkill[0]);
		// System.err.println("doubleSkill[1] ==="+ doubleSkill[1]);
		// System.err.println("status ==="+ status);
		// System.err.println("forsterAtt ==="+ forsterAtt);
		// System.err.println("forsterRange ==="+ forsterRange);
		// System.err.println("forsterHp ==="+ forsterHp);
		// System.err.println("forsterAttLast ==="+ forsterAttLast);
		// System.err.println("forsterRangeLast ==="+ forsterRangeLast);
		// System.err.println("forsterHpLast ==="+ forsterHpLast);
		// System.err.println("aimType ==="+ aimType);
		// System.err.println("swallowExp ==="+ swallowExp);
		// System.err.println("exp ==="+ exp);
		realm=data.readBoolean();
		len=data.readInt();
		ArrayList<Integer> engulfCards=new ArrayList<Integer>();
		for(int i=0;i<len;i++)
		{
			engulfCards.add(data.readInt());
		}
		this.engulfCards=engulfCards;
		isLock=data.readBoolean();
		this.drinkStatus = data.readInt();
		this.isInArmyCamp = data.readInt();
		this.lastDrinkTime = data.readLong();
		this.armyName = data.readUTF();
	}
}
