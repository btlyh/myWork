package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵����һ�㼼��
 * @author��Sebastian
 * 
 */
public class NormalSkill extends Skill{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public ArrayList<Integer> skillValue(BattleCard attCard, ArrayList<Integer> aim, BattleCard[] aimList, BattleRecord record)
	{
		clearHurt();
		BattleCard aimCard;
		int value;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=getSkillHurt(attCard.getAtt())/1000;
			value=super.buffValue(attCard,value,aimCard,record);
			record.setAttMax(value);
			addHurt(value);
			System.err.println("��ͨ���ܣ��ճ��˺� ==="+value);
		}			
		return getHurtList();
	}
}