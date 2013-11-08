package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵�����ӹ�
 * 
 * @author��Sebastian
 */
public class IncrHurtSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ���ӹ����ٷֱ� */
	private int hurt;
	/** �غ��� */
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
		IncrHurtSkill skill;
		BattleCard bCard;
		for(int i=0;i<aim.size();i++)
		{
			bCard=aimList[aim.get(i)];
			if(!bCard.hadDeSkill(this.getSid()))
			{
				skill=(IncrHurtSkill)Sample.factory.newSample(this.getSid());
				bCard.addDeBuff(skill);
			}
		}
		addHurt(0);
		return getHurtList();
	}

	/**
	 * ӵ�мӹ�buff�����㱻�˺�ֵ��������
	 */
	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		att=att*(1+hurt);
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("�ӹ��˺����ܣ��ӳ��˺� ==="+att);
		return att;
	}
}
