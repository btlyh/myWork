package com.cambrian.dfhm.battle;

import java.util.ArrayList;
import java.util.Random;

import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.battle.entity.DamageEntity;
import com.cambrian.dfhm.skill.DecrHurtSkill;
import com.cambrian.dfhm.skill.IncrHurtSkill;
import com.cambrian.dfhm.skill.Skill;

/**
 * 战斗计算类
 */
public class BattleAct
{

	/* static fields */
	/** Buffer状态: 1=治疗, 2=中毒, 3=眩晕, 4=连击, 5=狂怒, 6=追杀, 7=加攻, 8=减伤, 9=免伤 */
	public static final int TREAT=1,POISON=2,DIZZY=3,ATTACKS=4,ANGER=5,
					CHASE=6,INCRHURT=7,DECRHURT=8,NOHURT=9;
	/**
	 * 攻击范围: 0=默认, 1=随机， 2=纵向，3=横向, 4=敌全体, 5=己全体, 6=左斜, 7=右斜, 8=中间, 9=己方血少，
	 * 10=敌方血少，11=自己
	 */
	public static final int DEFAULT=0,RANDOM=1,ENDLONG=2,HORIZONTAL=3,
					ENEMYALL=4,OWNALL=5,LEFTINCLINED=6,RIGHTINCLINED=7,
					MITTLE=8,OWNHPMIN=9,ENEMYHPMIN=10,SELE=11;

	/* static methods */

	/* fields */
	public static Random ran=new Random();

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * 默认——攻击范围
	 * 
	 * @param aimCard 目标位置
	 * @param aimList 对象链表
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> defaultAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		ArrayList<Integer> aim=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					if(aimCard!=null&&aimCard.getIndex()==1
						&&battleCard.getIndex()==3)
						aim.add(4);
					else
						aim.add(battleCard.getIndex());
					return aim;
				}
			}
		}
		else
			aim.add(aimCard.getIndex());
		return aim;
	}

	/**
	 * 随机——攻击范围
	 * 
	 * @param aimList
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> randomAtt(BattleCard[] aimList)
	{
		System.err.println("随机攻击 -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		for(BattleCard battleCard:aimList)
		{
			if(battleCard!=null&&battleCard.getCurHp()>0)
				list.add(battleCard.getIndex());
		}
		int index=MathKit.randomValue(0,list.size());
		index=list.get(index);
		list.clear();
		list.add(index);
		return list;
	}

	/**
	 * 纵向——攻击范围
	 * 
	 * @param aimCard 目标对象
	 * @param aimList 对象链表
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> endLongAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("纵向攻击 -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()==0||aimCard.getIndex()==3)
		{
			if(aimList[0]!=null&&aimList[0].getCurHp()>0) list.add(0);
			if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
		}
		// else if(aimCard.getIndex()==2)
		// if(aimList[2]!=null&&aimList[2].getCurHp()>0)
		// list.add(2);
		// else
		// {
		// if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
		// if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		// }// 错误代码和以前做对比
		else if(aimCard.getIndex()==2)
		{
			if(aimList[2]!=null&&aimList[2].getCurHp()>0)
			{
				list.add(2);
			}
		}
		else if(aimCard.getIndex()==1||aimCard.getIndex()==4)
		{
			if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
			if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		}
		return list;
	}
	/**
	 * 横向——攻击范围
	 * 
	 * @param aimCard 目标对象
	 * @param aimList 对象链表
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> horizontalAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("横向攻击 -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()==0||aimCard.getIndex()==1)
		{
			if(aimList[0]!=null&&aimList[0].getCurHp()>0) list.add(0);
			if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
		}
		else if(aimCard.getIndex()==2)
		{
			if(aimList[2]!=null&&aimList[2].getCurHp()>0)
			{
				list.add(2);
			}
		}
		else if(aimCard.getIndex()==3||aimCard.getIndex()==4)
		{
			if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
			if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		}
		// else if(aimCard.getIndex()<3)
		// if(aimList[2]!=null&&aimList[2].getCurHp()>0)
		// list.add(2);
		// else
		// {
		// if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
		// if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		// }
		return list;
	}

	/**
	 * 左斜——攻击范围
	 * 
	 * @param aimCard 目标对象
	 * @param aimList 对象链表
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> leftInclinedleAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("左斜攻击 -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()%2==0)
		{
			for(int i=0;i<aimList.length;i+=2)
				if(aimCard!=null&&aimCard.getCurHp()>0) list.add(i);
		}
		else
			list.add(aimCard.getIndex());
		return list;
	}

	/**
	 * 右斜——攻击范围
	 * 
	 * @param aimCard 目标对象
	 * @param aimList 对象链表
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> rightInclinedleAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("右斜攻击 -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()==1||aimCard.getIndex()==2
			||aimCard.getIndex()==3)
		{
			for(int i=1;i<4;i++)
				if(aimCard!=null&&aimCard.getCurHp()>0) list.add(i);
		}
		else
			list.add(aimCard.getIndex());
		return list;
	}

	/**
	 * 中间——攻击范围
	 * 
	 * @param aimList 对象链表
	 * @return 攻击目标
	 */
	public static ArrayList<Integer> mittleAtt(BattleCard[] aimList)
	{
		System.err.println("中间攻击 -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		BattleCard aimCard=aimList[2];
		if(aimCard==null||aimCard.getCurHp()<=0)
			list=defaultAtt(aimList[0],aimList);
		else
			list.add(2);
		return list;
	}

	/**
	 * 血最少——攻击范围
	 * 
	 * @param aimList
	 * @return
	 */
	public static ArrayList<Integer> HpMin(BattleCard[] aimList)
	{
		System.err.println("血最少攻击 -------------");
		BattleCard aimCard,aimCard_;
		ArrayList<Integer> list=new ArrayList<Integer>();
		int index=0;
		for(int i=0;i<aimList.length;i++)
		{
			aimCard=aimList[i];
			if(aimCard!=null&&aimCard.getCurHp()>0)
			{
				index=aimCard.getIndex();
				for(int j=i+1;j<aimList.length;j++)
				{
					aimCard_=aimList[j];
					if(aimCard_!=null&&aimCard_.getCurHp()>0
						&&aimCard.getCurHp()>aimCard_.getCurHp())
						aimCard=aimCard_;
				}
				index=aimCard.getIndex();
				break;
			}
		}
		list.add(index);
		return list;
	}

	/**
	 * 获取伤害值
	 * 
	 * @param battleCard
	 * @param att 伤害系数
	 * @param attType 攻击类型
	 * @return
	 */
	public static ArrayList<DamageEntity> getAttValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record,
		ArrayList<DamageEntity> hurtList)
	{
		hurtList.clear();
		BattleCard aimCard=null;
		int value=0;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=buffValue(attCard,attCard.getAtt(),aimCard,record);
			if(isCrit(attCard))
			{
				crit=true;
				damageStatus=DamageEntity.DAMAGE_CRIT;
				value=countCritDamage(value,attCard);
			}
			else
			{
				if(isDodge(aimCard))
				{
					damageStatus=DamageEntity.DAMAGE_DODGE;
					value=0;
				}
			}
			if(value > 0){
				value=countFloatDamage(value);
				record.setAttMax(value);
			}
			DamageEntity damage = new DamageEntity(damageStatus,value);
			hurtList.add(damage);
			if(value>0)
				System.err.println("照成伤害 ==="+value+(crit?"暴击伤害":"普通伤害"));
			else
				System.err.println("伤害被闪避");
		}
		return hurtList;
	}
	/**
	 * 获得攻击对象
	 * 
	 * @param attCard 攻击卡牌
	 * @param aimList 被攻击目标链表
	 * @return
	 */
	public static BattleCard getAim(BattleCard attCard,BattleCard[] aimList)
	{
		int index=attCard.getIndex();
		BattleCard aimCard=null,aimCard_1=null;
		for(int i=0;i<aimList.length;i++)
		{
			aimCard=aimList[i];
			if(aimCard!=null&&aimCard.getCurHp()>0)
			{
				if((index==1||index==4)&&aimCard.getIndex()==0)
				{
					aimCard_1=aimList[1];
					if(aimCard_1!=null&&aimCard_1.getCurHp()>0)
						return aimCard_1;
					else
						return aimCard;
				}
				else if((index==1||index==4)&&aimCard.getIndex()==3)
				{
					aimCard_1=aimList[4];
					if(aimCard_1!=null&&aimCard_1.getCurHp()>0)
						return aimCard_1;
					else
						return aimCard;
				}
				break;
			}
		}
		return aimCard;
	}

	/**
	 * 技能buff伤害值
	 * 
	 * @param attCard 出手攻击者
	 * @param att 本次攻击伤害值
	 * @param aimCard 伤害目标
	 * @param record 记录
	 * @return 伤害值
	 */
	private static int buffValue(BattleCard attCard,int att,
		BattleCard aimCard,BattleRecord record)
	{
		// System.err.println("技能buff伤害值,多态的基类哦--------------");
		if(aimCard.hadNoHurtSkill())// 判断放手方是否有免伤状态
		{
			return 0;
		}
		// 判断攻击方是否有攻状态
		ArrayList<Skill> deSkill=attCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof IncrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		// 判断防守方是否有减伤状态
		deSkill=aimCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof DecrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		return att;
	}

	/**
	 * 判断是否暴击
	 * 
	 * @param attCard 进攻卡牌
	 * @return 是否暴击
	 */
	public static boolean isCrit(BattleCard attCard)
	{
		boolean isCrit=false;
		int critRate=attCard.getCritRate()/1000;// 暴击率
		int randomValue=MathKit.randomValue(1,101);
		if(randomValue<critRate)
		{
			isCrit=true;
		}
		return isCrit;
	}

	/**
	 * 判断是否闪避
	 * 
	 * @param defCard 防守卡牌
	 * @return 是否闪避
	 */
	public static boolean isDodge(BattleCard defCard)
	{
		boolean isDodge=false;
		int dodgeRate=defCard.getDodgeRate()/1000;// 闪避率
		int randomValue=MathKit.randomValue(1,101);
		if(randomValue<dodgeRate)
		{
			isDodge=true;
		}
		return isDodge;
	}
	/**
	 * 计算暴击伤害
	 * 
	 * @param value 实际值
	 * @param card 卡牌对象
	 * @return 暴击伤害值
	 */
	public static int countCritDamage(int value,BattleCard card)
	{
		return value*card.getCritFactor()/1000;
	}
	/**
	 * 计算伤害浮动值(95%-105%)
	 * 
	 * @param value 实际值
	 * @return 浮动伤害值s
	 */
	public static int countFloatDamage(int value)
	{
		int randomValue=MathKit.randomValue(95,106);
		return value*randomValue/100;
	}
}
