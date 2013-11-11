package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵�����ж�����
 * 
 * @author��Sebastian
 */
public class PoisonSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ÿ���ж���Ѫ�� */
	private int hurt;
	private int round;
	/** �ж���Ѫ�� */
	private int decrHp;

	/* constructors */

	/* properties */
	public int getDecrHp()
	{
		return decrHp;
	}

	/* init start */

	/* methods */
	@Override
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();

		PoisonSkill skill;
		BattleCard aimCard;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			// �����ճ��˺�������ȡ��(���ƹ�����*����ϵ��/1000)
			int value=super.getSkillHurt(attCard.getAtt());
			// ÿ�غ��ж���Ѫ��������ȡ��(�����ƹ�����*����ϵ��/1000��*��Ч���˺�����/1000*�غ���)
			double a=(double)value/1000;
			double b=(double)hurt/1000;
			value/=1000;
			decrHp=(int)((double)a*(double)b*round);
			value=super.buffValue(attCard,value,aimCard,record);
			record.setAttMax(value);
			addHurt(value);
			if(!aimCard.hadDeSkill(attCard.getSkill().getSid()))
			{
				skill=(PoisonSkill)Sample.factory.newSample(attCard
					.getSkill().getSid());
				skill.decrHp=decrHp;
				aimCard.addDeBuff(skill);
			}
			System.err.println("�ж����ܣ��ճ��˺� ==="+value);
		}
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		// decrHp=10000;
		record.addRecord(this.getSid());
		record.addRecord(1);//�ж�
		record.addRecord(attCard.getSide());
		record.addRecord(hurt);
		aimCard.decrHp(decrHp);
		if(attCard.getCurHp()<=0)// �����ж�����
			record.addRecord(1);//����
		else
			record.addRecord(-1);// û��
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("�ж�Debuffer���ճ��˺�����==="+aimCard.getName()
			+", Ѫ����ʧ ==="+decrHp+", ʣ��Ѫ�� ==="+aimCard.getCurHp()
			+"�� ʣ��غ��� ==="+round);
		return decrHp;
	}
}
