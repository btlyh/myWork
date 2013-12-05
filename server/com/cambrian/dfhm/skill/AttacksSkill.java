package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

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
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		BattleCard aimCard;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
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
				System.err.println("�������ܣ��ճ��˺� ==="+value
					+(crit?"�����˺�":"��ͨ�˺�"));
			else
				System.err.println("�˺�������");
		}
		return getHurtList();
	}
}
