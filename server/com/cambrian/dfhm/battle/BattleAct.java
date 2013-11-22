package com.cambrian.dfhm.battle;

import java.util.ArrayList;
import java.util.Random;

import com.cambrian.common.util.MathKit;

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
		if(aimCard.getIndex()<2)
		{
			if(aimList[0]!=null&&aimList[0].getCurHp()>0) list.add(0);
			if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
		}
		else if(aimCard.getIndex()<3)
			if(aimList[2]!=null&&aimList[2].getCurHp()>0)
				list.add(2);
			else
			{
				if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
				if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
			}
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
			defaultAtt(aimList[0],aimList);
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
	public static int getAttValue(BattleCard attCard,int att,int attType)
	{
		double a=attCard.getDodgeRate()/100000;// 闪避率
		double a1=MathKit.randomValue(-1,1)+1;
		int sanbi=(int)Math.ceil(a-a1);
		double b=attCard.getCritRate()/100000;// 暴击率
		double b1=MathKit.randomValue(-1,1)+1;
		int baoji=(int)(Math.ceil(b-b1)*0.5+1);
		double c=((MathKit.randomInt()%6)/100);// 5%的上下浮动值
		int hurt=(int)c*sanbi*baoji;
		if(attType==1)// 普通攻击
			hurt*=att*attCard.getAtt();
		else
			hurt*=att;
		return hurt;
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
}
