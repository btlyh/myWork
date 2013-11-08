package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵������ѣ����
 * 
 * @author��Sebastian
 */
public class DizzySkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
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
		int value;
		DizzySkill skill;
		BattleCard aimCard;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=getSkillHurt(attCard.getAtt())/1000;
			value=super.buffValue(attCard,value,aimCard,record);
			record.setAttMax(value);
			addHurt(value);
			if(!aimCard.hadDeSkill(this.getSid()))
			{
				skill=(DizzySkill)Sample.factory.newSample(this.getSid());
				aimCard.addDeBuff(skill);
			}
			System.err.println("��ѣ���ܣ��ճ��˺� ==="+value);
		}
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		record.addRecord(this.getSid());
		record.addRecord(2);//��ʾ��ѣ
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("��ѣ״̬��ʣ��غ��� ==="+round);
		return 0;
	}
}