package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * ��˵����׷ɱ����
 * 
 * @author��Sebastian
 */
public class ChaseSkill extends Skill
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
	 * ��Ѫ��
	 * 
	 * @param aimCard
	 * @param attCard
	 * @return
	 */
	private int downHp(BattleCard aimCard,BattleCard attCard)
	{
		// ����ȡ��(���ƹ�����*����ϵ��/1000+���ƹ�����*��ȡ��(�������HP/ÿ���ٵİٷֱȣ�*�ӳɵĹ������ٷֱȣ�)
		System.err.println("׷ɱ����ʹ�� -----------------");
		int value=aimCard.getMaxHp()-aimCard.getCurHp();// ������Ѫ��
		System.err.println("��Ҽ���Ѫ�� ==="+value);
		int hp_=aimCard.getMaxHp()*hp/100;// ÿ�μӹ����������ٵ�Ѫ��
		System.err.println("ÿ�μӹ����������ٵ�Ѫ�� ==="+hp_);
		int num=value/hp_;// �ӹ��Ĵ���
		System.err.println("�ӹ��Ĵ��� ==="+num);
		double a=getSkillHurt(attCard.getAtt())/1000;
		double b=attCard.getAtt()*num*hurt/100;
		value=(int)(a+b);
		System.err.println("��ҹ����� ==="+attCard.getAtt());
		System.err.println("���Ӻ���ܹ����� ==="+value);
		return value;
	}

	@Override
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		int value;
		BattleCard aimCard;
		int index;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
		for(int i=0;i<aim.size();i++)
		{
			index=aim.get(i);
			aimCard=aimList[index];
			value=downHp(aimCard,attCard);
			value=super.buffValue(attCard,value,aimCard,record);

			if(BattleAct.isCrit(attCard))
			{
				crit=true;
				damageStatus=DamageEntity.DAMAGE_CRIT;
				value=BattleAct.countCritDamage(value,attCard);
			}
			else
			{
				if(BattleAct.isDodge(aimCard))
				{
					damageStatus=DamageEntity.DAMAGE_DODGE;
					value=0;
				}
			}
			if(value>0)
			{
				value=BattleAct.countFloatDamage(value);
				record.setAttMax(value);
			}
			DamageEntity damage=new DamageEntity(damageStatus,value);
			addHurt(damage);
			if(value>0)
				System.err.println("׷ɱ���ܣ��ճ��˺� ==="+value
					+(crit?"�����˺�":"��ͨ�˺�"));
			else
				System.err.println("�˺�������");
		}
		return getHurtList();
	}
}
