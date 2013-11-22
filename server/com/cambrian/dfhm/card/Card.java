package com.cambrian.dfhm.card;

import java.util.ArrayList;

import org.w3c.dom.Attr;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.skill.Skill;
import com.cambrian.common.util.TimeKit;

/**
 * ��˵����
 * 
 * @author��Sebastian
 */
public class Card extends Sample
{

	/* static fields */
	/** ״̬��û״̬=1������=,2���ָ�=3 */
	public static int NORMAL=1,ATTACK=2,RECOVER=3;
	/** �Ⱦ�״̬: ���=1�����=2������=3 */
	public static int HYPER=1,DRUNK=2,AWAKE=3;

	/* static methods */

	/* fields */
	/** ��� */
	public int uid;
	private String name;
	/** ���� */
	private String description;
	/** ��Ƭͷ��(ǰ̨��) */
	private String avatar;
	/** С��Ƭͷ��(ǰ̨��) */
	private String tinyAvatar;
	/** ���ͣ��������ϳȣ�5�֣� */
	private int type;
	/** ���ۼ۸� */
	private int money;
	/** ������ */
	private int att;
	/** ���ܷ����� */
	private int skillRate;
	/** �������� */
	private int maxHp;
	/** ��ǰ���� */
	private int curHp;
	/** ��ͨ������Χ (�����ܹ�����Χһ��) */
	private int attRange;
	/** ӵ�м���ID */
	private int skillId;
	/** ˢ�³��ļ���ID */
	private int flushSkillId=-1;
	/** ��ӵ�м��ܷ�Χ */
	private int[] skillRange;
	/** �ȼ� */
	private int level=1;
	/** �������� */
	private int forsterNumber=9999;
	/** ������ */
	private int critRate;
	/** ������ */
	private int dodgeRate;
	/** ������������ɳ����ֵ */
	private int moneyFosterAttMax;
	/** ������������ɳ���Сֵ */
	private int moneyFosterAttMin;
	/** �������������ɳ����ֵ */
	private int goldFosterAttMax;
	/** �������������ɳ���Сֵ */
	private int goldFosterAttMin;
	/** ��ͨ���������ɳ����ֵ */
	private int normalFosterAttMax;
	/** ��ͨ���������ɳ���Сֵ */
	private int normalFosterAttMin;
	/** ����������ܷ����ʳɳ����ֵ */
	private int moneyFosterRangeMax;
	/** ����������ܷ����ʳɳ���Сֵ */
	private int moneyFosterRangeMin;
	/** �����������ܷ����ʳɳ����ֵ */
	private int goldFosterRangeMax;
	/** �����������ܷ����ʳɳ���Сֵ */
	private int goldFosterRangeMin;
	/** ��ͨ�������ܷ����ʳɳ����ֵ */
	private int normalFosterRangeMax;
	/** ��ͨ�������ܷ����ʳɳ����ֵ */
	private int normalFosterRangeMin;
	/** ������������ɳ����ֵ */
	private int moneyFosterHpMax;
	/** ������������ɳ���Сֵ */
	private int moneyFosterHpMin;
	/** �������������ɳ����ֵ */
	private int goldFosterHpMax;
	/** �������������ɳ���Сֵ */
	private int goldFosterHpMin;
	/** ��ͨ���������ɳ����ֵ */
	private int normalFosterHpMax;
	/** ��ͨ���������ɳ���Сֵ */
	private int normalFosterHpMin;
	/** ״̬ */
	private int status;
	/** ������������ֵ */
	private int forsterAtt;
	/** �������ܷ�����������ֵ */
	private int forsterRange;
	/** ��������������ֵ */
	private int forsterHp;
	/** ���һ��������,����ֵ */
	private int forsterAttLast;
	/** ���һ���������ܷ����ʣ�����ֵ */
	private int forsterRangeLast;
	/** ���һ����������������ֵ */
	private int forsterHpLast;
	/** ����Ŀ�����1=����2=���� */
	private int aimType;
	/** ���ƻ�������(��������) */
	private int swallowExp;
	/** ���� */
	private int exp;
	/** ���ܵȼ� */
	private int skillLevel;
	/** ����ͻ������ [�ȼ�������id,����id,����id,����id,����id,������������] */
	public int[] realmLimit;
	/** ����ͻ��״̬ */
	private boolean realm=false;
	/** �Ѿ��Թ��Ŀ���(ͻ��ƿ����) */
	private ArrayList<Integer> engulfCards=new ArrayList<Integer>();
	/** �����Ƿ����� */
	private boolean isLock=false;

	/** �Ⱦ�״̬ */
	private int drinkStatus=Card.AWAKE;
	/** �Ƿ��ھ����� 0�����ڣ�1���� */
	private int isInArmyCamp=0;
	/** �ϴκȾ�ʱ�� */
	private long lastDrinkTime;
	/** ���ھ���������� */
	private String armyName;

	/** �����������ӵ��ܹ����� */
	private int engulfAddAct;
	/** �����������ӵ�����Ѫ */
	private int engulfAddHp;
	/** �����������ӵ������� */
	private int engulfAddDodge;
	/** ͻ�����ӵ��ܹ����� */
	private int beyongAddAct;
	/** ͻ�����ӵ�����Ѫ */
	private int beyongAddHp;
	/** ͻ�����ӵ������� */
	private int beyongAddDodge;
	/** �Ƿ����¿��� */
	private boolean isNew;
	/** ���һ�����Ըı��ʱ�� */
	private long attLastChangeTime;

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

	public long getAttLastChangeTime()
	{
		return attLastChangeTime;
	}

	public void setAttLastChangeTime(long attLastChangeTime)
	{
		this.attLastChangeTime=attLastChangeTime;
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

	public int getNormalFosterAttMax()
	{
		return normalFosterAttMax;
	}

	public void setNormalFosterAttMax(int normalFosterAttMax)
	{
		this.normalFosterAttMax=normalFosterAttMax;
	}

	public int getNormalFosterAttMin()
	{
		return normalFosterAttMin;
	}

	public void setNormalFosterAttMin(int normalFosterAttMin)
	{
		this.normalFosterAttMin=normalFosterAttMin;
	}

	public int getNormalFosterRangeMax()
	{
		return normalFosterRangeMax;
	}

	public void setNormalFosterRangeMax(int normalFosterRangeMax)
	{
		this.normalFosterRangeMax=normalFosterRangeMax;
	}

	public int getNormalFosterRangeMin()
	{
		return normalFosterRangeMin;
	}

	public void setNormalFosterRangeMin(int normalFosterRangeMin)
	{
		this.normalFosterRangeMin=normalFosterRangeMin;
	}

	public int getNormalFosterHpMax()
	{
		return normalFosterHpMax;
	}

	public void setNormalFosterHpMax(int normalFosterHpMax)
	{
		this.normalFosterHpMax=normalFosterHpMax;
	}

	public int getNormalFosterHpMin()
	{
		return normalFosterHpMin;
	}

	public void setNormalFosterHpMin(int normalFosterHpMin)
	{
		this.normalFosterHpMin=normalFosterHpMin;
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
		this.drinkStatus=drinkStatus;
	}

	public int isInArmyCamp()
	{
		return isInArmyCamp;
	}

	public void setInArmyCamp(int isInArmyCamp)
	{
		this.isInArmyCamp=isInArmyCamp;
	}

	public long getLastDrinkTime()
	{
		return lastDrinkTime;
	}

	public void setLastDrinkTime(long lastDrinkTime)
	{
		this.lastDrinkTime=lastDrinkTime;
	}

	public String getArmyName()
	{
		return armyName;
	}

	public void setArmyName(String armyName)
	{
		this.armyName=armyName;
	}

	public int getEngulfAddAct()
	{
		return engulfAddAct;
	}

	public void setEngulfAddAct(int engulfAddAct)
	{
		this.engulfAddAct=engulfAddAct;
	}

	public int getEngulfAddHp()
	{
		return engulfAddHp;
	}

	public void setEngulfAddHp(int engulfAddHp)
	{
		this.engulfAddHp=engulfAddHp;
	}

	public int getEngulfAddDodge()
	{
		return engulfAddDodge;
	}

	public void setEngulfAddDodge(int engulfAddDodge)
	{
		this.engulfAddDodge=engulfAddDodge;
	}

	public int getBeyongAddAct()
	{
		return beyongAddAct;
	}

	public void setBeyongAddAct(int beyongAddAct)
	{
		this.beyongAddAct=beyongAddAct;
	}

	public int getBeyongAddHp()
	{
		return beyongAddHp;
	}

	public void setBeyongAddHp(int beyongAddHp)
	{
		this.beyongAddHp=beyongAddHp;
	}

	public int getBeyongAddDodge()
	{
		return beyongAddDodge;
	}

	public void setBeyongAddDodge(int beyongAddDodge)
	{
		this.beyongAddDodge=beyongAddDodge;
	}

	/* init start */
	public void init(Card card)
	{
		this.setSid(card.getSid());
		skillRange=card.skillRange;
		name=card.getName();
		description=card.getDescription();
		avatar=card.getAvatar();
		tinyAvatar=card.getTinyAvatar();
		type=card.getType();
		money=card.getMoney();
		att=card.getAtt();
		skillRate=card.getSkillRate();
		maxHp=card.getMaxHp();
		curHp=card.getCurHp();
		attRange=card.getAttRange();
		critRate=card.getCritRate();
		dodgeRate=card.getDodgeRate();

		moneyFosterAttMax=card.getMoneyFosterAttMax();
		moneyFosterAttMin=card.getMoneyFosterAttMin();
		moneyFosterHpMax=card.getMoneyFosterHpMax();
		moneyFosterHpMin=card.getMoneyFosterHpMin();
		moneyFosterRangeMax=card.getMoneyFosterRangeMax();
		moneyFosterRangeMin=card.getMoneyFosterRangeMin();

		goldFosterAttMax=card.getGoldFosterAttMax();
		goldFosterAttMin=card.getGoldFosterAttMin();
		goldFosterHpMax=card.getGoldFosterHpMax();
		goldFosterHpMin=card.getGoldFosterHpMin();
		goldFosterRangeMax=card.getGoldFosterRangeMax();
		goldFosterRangeMin=card.getGoldFosterRangeMin();

		normalFosterAttMax=card.getNormalFosterAttMax();
		normalFosterAttMin=card.getNormalFosterAttMin();
		normalFosterHpMax=card.getNormalFosterHpMax();
		normalFosterHpMin=card.getNormalFosterHpMin();
		normalFosterRangeMax=card.getNormalFosterRangeMax();
		normalFosterRangeMin=card.getNormalFosterRangeMin();

		status=card.getStatus();
		forsterAtt=card.getForsterAtt();
		forsterRange=card.getForsterRange();
		forsterHp=card.getForsterHp();

		forsterAttLast=card.getForsterAttLast();
		forsterRangeLast=card.getForsterRangeLast();
		forsterHpLast=card.getForsterHpLast();
		aimType=card.getAimType();
		swallowExp=card.getSwallowExp();
		exp=card.getExp();
		skillLevel=card.getSkillLevel();
		realmLimit=card.realmLimit;
		lastDrinkTime=card.getLastDrinkTime();
		armyName=card.getArmyName();

		engulfAddAct=card.getEngulfAddAct();
		engulfAddDodge=card.getEngulfAddDodge();
		engulfAddHp=card.getEngulfAddHp();

		beyongAddAct=card.getBeyongAddAct();
		beyongAddDodge=card.getBeyongAddDodge();
		beyongAddHp=card.getBeyongAddHp();
		
		isNew=card.isNew();
		attLastChangeTime=card.getAttLastChangeTime();

		int index=MathKit.randomValue(0,skillRange.length);
		skillId=skillRange[index];
		curHp=maxHp;
		drinkStatus=Card.AWAKE;
		isNew=true;
		attLastChangeTime=TimeKit.nowTimeMills();

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

	public int getBaseAtt()
	{
		return att-beyongAddAct-engulfAddAct;
	}

	public int getBaseHp()
	{
		return maxHp-beyongAddHp-engulfAddHp;
	}

	public int getBaseDodge()
	{
		return dodgeRate-beyongAddDodge-engulfAddDodge;
	}

	public boolean isNew()
	{
		return isNew;
	}

	public void setNew(boolean isNew)
	{
		this.isNew=isNew;
	}

	/**
	 * ��þ���ͻ������
	 * 
	 * @param level ����ȼ�
	 * @return ����[����id,����id,����id,����id,����id,������������]
	 */
	public int[] getRealmByLevel()
	{
		int[] limit=new int[5];
		for(int i=0;i<realmLimit.length;i+=6)
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

	/** ����������� */
	public boolean levelUp()
	{
		boolean result=false;
		boolean islimit=false;
		if(level>=90)
		{
			return false;
		}
		// TODO һ����99����ÿ���ľ���������gameconfig����
		while(exp>GameCFG.getExp(level))
		{
			if(level<90)
			{
				level++;
				attLastChangeTime=TimeKit.nowTimeMills();
				result=true;
				if(getRealmByLevel()[0]>0)
				{
					realm=true;
					break;
				}
			}

		}

		if(level<90)
		{
			if(result)
			{
				int temp=level;
				forsterNumber+=--temp;
			}
			return result;
		}
		else
		{
			realm=false;
			return false;
		}
	}

	/** ��ȡ����ս���� */

	public int getZhandouli()
	{
		return (int)((maxHp+(double)att*3+(double)att*1.5*(double)critRate*3+(double)maxHp
			*(double)dodgeRate)*0.00005d);
	}

	/** ǰ̨���л���ȡ */
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
		if(uid==28)
		{
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
		data.writeLong(TimeKit.nowTimeMills()-lastDrinkTime);
		data.writeBoolean(isNew);
	}

	/** д��db���� */
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
		data.writeInt(engulfAddAct);
		data.writeInt(engulfAddHp);
		data.writeInt(engulfAddDodge);
		data.writeInt(beyongAddAct);
		data.writeInt(beyongAddHp);
		data.writeInt(beyongAddDodge);
		data.writeBoolean(isNew);
	}

	/** ��db��ȡ���� */
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
		this.drinkStatus=data.readInt();
		this.isInArmyCamp=data.readInt();
		this.lastDrinkTime=data.readLong();
		this.armyName=data.readUTF();
		this.engulfAddAct=data.readInt();
		this.engulfAddHp=data.readInt();
		this.engulfAddDodge=data.readInt();
		this.beyongAddAct=data.readInt();
		this.beyongAddHp=data.readInt();
		this.beyongAddDodge=data.readInt();
		this.isNew=data.readBoolean();
	}
	/*
	 * ��ȡ���Ƶ�ʵ�ʹ����� ��������+���������Ĺ���+���������Ĺ���
	 */
	/*
	 * public int getCardRealAct() { return this.getAtt() +
	 * this.getBeyongAddAct() + this.getEngulfAddAct(); }
	 */

	/*
	 * ��ȡ���Ƶ�ʵ����Ѫ ������Ѫ+������������Ѫ+������������Ѫ
	 */
	/*
	 * public int getCardRealHp() { return this.getMaxHp() +
	 * this.getBeyongAddHp() + this.getEngulfAddHp(); }
	 */

	/*
	 * ��ȡ���Ƶ�ʵ������ ��������+��������������+��������������
	 */
	/*
	 * public int getCardRealDodge() { return this.getDodgeRate() +
	 * this.getBeyongAddDodge() + this.getEngulfAddDodge(); }
	 */
}
