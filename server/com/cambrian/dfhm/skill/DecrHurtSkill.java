package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * 类说明：减少伤害技能
 * 
 * @author：Sebastian
 */
public class DecrHurtSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 减少伤害百分比 */
	private int hurt;
	/** 回合数 */
	private int round;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		DecrHurtSkill skill;
		BattleCard bCard;
		for(int i=0;i<aim.size();i++)
		{
			bCard=aimList[aim.get(i)];
			if(!bCard.hadDeSkill(this.getSid()))
			{
				skill=(DecrHurtSkill)Sample.factory.newSample(attCard
					.getSkill().getSid());
				bCard.addDeBuff(skill);
			}
		}
		addHurt(0);
		System.err.println("减伤技能!!!");
		return getHurtList();
	}

	/**
	 * 拥有减伤buff，计算被伤害值，并返回
	 */
	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		clearHurt();
		double value=att*(1-hurt/100d);
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("减少伤害技能，减免伤害 ==="+value);
		return (int)value;
	}
}
