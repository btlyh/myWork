package com.cambrian.dfhm.skill;

import java.util.ArrayList;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵������ŭ
 * 
 * @author��Sebastian
 */
public class AngerSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ÿ�����ӵĹ������ٷֱ� */
	private int hurt;
	/** �½�Ѫ���Ȱٷֱ� */
	private int hp;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * ÿ�½���%5��Ѫ�������ӹ���
	 * 
	 * @param attCard
	 * @return ���ض������ӵĹ�����
	 */
	private int downHp(BattleCard attCard)
	{
		// ����ȡ��(���ƹ�����*����ϵ��/1000+���ƹ�����*��ȡ��(��������HP/ÿ���ٵİٷֱȣ�*�ӳɵĹ������ٷֱȣ�)
		System.err.println("��ŭ����ʹ�� -----------------");
		int value=attCard.getMaxHp()-attCard.getCurHp();// �ܹ�����Ѫ��
		System.err.println("��Ҽ���Ѫ�� ==="+value);
		int hp_=attCard.getMaxHp()*hp/100;// ÿ�μӹ����������ٵ�Ѫ��
		System.err.println("ÿ�μӹ����������ٵ�Ѫ�� ==="+hp_);
		int num=value/hp_;// �ӹ��Ĵ���
		System.err.println("�ӹ��Ĵ��� ==="+num);
		double a=getSkillHurt(attCard.getAtt())/1000;
		double b=attCard.getAtt()*num*hurt;
		value=(int)(a+b);// ���Ӻ���ܹ�����
		System.err.println("��ҹ����� ==="+attCard.getAtt());
		System.err.println("���Ӻ���ܹ����� ==="+value);
		return value;
	}

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
			value=downHp(attCard);
			value=super.buffValue(attCard,value,aimCard,record);
			addHurt(value);
			record.setAttMax(value);
			System.err.println("��ŭ���ܣ��ճ��˺� ==="+value);
		}
		return getHurtList();
	}
}