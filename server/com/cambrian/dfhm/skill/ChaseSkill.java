package com.cambrian.dfhm.skill;

import java.util.ArrayList;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * 类说明：追杀技能
 * 
 * @author：Sebastian
 */
public class ChaseSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 每次增加的攻击力百分比 */
	private int hurt;
	/** 下降血量度百分比 */
	private int hp;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * 扣血量
	 * 
	 * @param aimCard
	 * @param attCard
	 * @return
	 */
	private int downHp(BattleCard aimCard,BattleCard attCard)
	{
		// 向下取整(卡牌攻击力*技能系数/1000+卡牌攻击力*（取整(自身减少HP/每减少的百分比）*加成的攻击力百分比）)
		System.err.println("追杀技能使用 -----------------");
		int value=aimCard.getMaxHp()-aimCard.getCurHp();// 减少总血量
		System.err.println("玩家减少血量 ==="+value);
		int hp_=aimCard.getMaxHp()*hp/100;// 每次加攻，需具体减少的血量
		System.err.println("每次加攻，需具体减少的血量 ==="+hp_);
		int num=value/hp_;// 加攻的次数
		System.err.println("加攻的次数 ==="+num);
		double a=getSkillHurt(attCard.getAtt())/1000;
		double b=attCard.getAtt()*num*hurt/100;
		value=(int)(a+b);
		System.err.println("玩家攻击力 ==="+attCard.getAtt());
		System.err.println("增加后的总攻击力 ==="+value);
		return value;
	}

	@Override
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		int value;
		BattleCard aimCard;
		int index;
		for(int i=0;i<aim.size();i++)
		{
			index=aim.get(i);
			aimCard=aimList[index];
			value=downHp(aimCard,attCard);
			value=super.buffValue(attCard,value,aimCard,record);
			record.setAttMax(value);
			addHurt(value);
			System.err.println("追杀技能，照成伤害 ==="+value);
		}
		return getHurtList();
	}
}
