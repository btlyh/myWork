package com.cambrian.dfhm.skill;

import java.util.ArrayList;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵��������
 * 
 * @author��Sebastian
 */
public class AttacksSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ������������ */
	private int count;
	/** ÿ�ι����˺��� */
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
			// ����ȡ��(���ƹ�����*����ϵ��/1000+���ƹ�����*�����˺�ϵ��/1000*��������)
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
			System.err.println("�������ܣ��ճ��˺� ==="+value);
		}
		return getHurtList();
	}
}
