package com.cambrian.dfhm.skill;

import java.util.ArrayList;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * 类说明：连击
 * 
 * @author：Sebastian
 */
public class AttacksSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 连续攻击次数 */
	private int count;
	/** 每次攻击伤害数 */
	private int hurt;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		BattleCard aimCard;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			// 向下取整(卡牌攻击力*技能系数/1000+卡牌攻击力*额外伤害系数/1000*连击次数)
			int value=getSkillHurt(attCard.getAtt())+attCard.getAtt()*hurt
				*count;
			double a=value/1000;
			double b=hurt/1000;
			double c=attCard.getAtt()*b;
			double d=c*count;
			value=(int)(a+d);
			value=super.buffValue(attCard,value,aimCard,record);
			addHurt(value);
			record.setAttMax(value);
			System.err.println("连击技能，照成伤害 ==="+value);
		}
		return getHurtList();
	}
}
