package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

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
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();

		PoisonSkill skill;
		BattleCard aimCard;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			if(aimCard==null) continue;
			// �����ճ��˺�������ȡ��(���ƹ�����*����ϵ��/1000)
			int value=super.getSkillHurt(attCard.getAtt());
			// ÿ�غ��ж���Ѫ��������ȡ��(�����ƹ�����*����ϵ��/1000��*��Ч���˺�����/1000*�غ���)
			double a=(double)value/1000;
			double b=(double)hurt/1000;
			value/=1000;
			decrHp=(int)((double)a*(double)b);
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
				System.err.println("�ж����ܣ��ճ��˺� ==="+value
					+(crit?"�����˺�":"��ͨ�˺�"));
			else
				System.err.println("�˺�������");
			if(!aimCard.hadDeSkill(attCard.getSkill().getSid()))
			{
				skill=(PoisonSkill)Sample.factory.newSample(attCard
					.getSkill().getSid());
				skill.decrHp=decrHp;
				boolean isPoison=false;
				for(Skill skill_:aimCard.getDeSkill())
				{
					if(skill_ instanceof PoisonSkill)
					{
						isPoison=true;
						break;
					}
				}
				if(!isPoison) aimCard.addDeBuff(skill);
			}
		}
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		// decrHp=10000;
		record.addRecord(this.getSid());
		record.addRecord(1);// �ж�
		record.addRecord(attCard.getSide());
		record.addRecord(decrHp);
		aimCard.decrHp(decrHp);
		if(attCard.getCurHp()<=0)// �����ж�����
			record.addRecord(1);// ����
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
