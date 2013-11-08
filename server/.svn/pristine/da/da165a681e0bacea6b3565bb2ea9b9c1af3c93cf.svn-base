package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * 类说明：治疗技能
 * 
 * @author：Sebastian
 */
public class TreatSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		int value;
		BattleCard aimCard;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=getSkillHurt(attCard.getAtt())/1000;
//			value=buffValue(attCard,value,aimCard,record);
			addHurt(value);
			System.err.println("治疗技能，血量增加 ==="+value);
		}
		return getHurtList();
	}
}
