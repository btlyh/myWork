package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * ��˵���������˺�����
 * 
 * @author��Sebastian
 */
public class DecrHurtSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** �����˺��ٷֱ� */
	private int hurt;
	/** �غ��� */
	private int round;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		DecrHurtSkill skill;
		BattleCard bCard;
		for(int i=0;i<aim.size();i++)
		{
			bCard=aimList[aim.get(i)];
			if(!bCard.hadDeSkill(this.getSid()))
			{
				skill=(DecrHurtSkill)Sample.factory.newSample(attCard
					.getSkill().getSid());
				bCard.addDeBuff(skill);
			}
			System.err.println("���˼���!!!");
		}
		DamageEntity damage=new DamageEntity(DamageEntity.DAMAGE_NORMAL,0);
		addHurt(damage);
		return getHurtList();
	}

	/**
	 * ӵ�м���buff�����㱻�˺�ֵ��������
	 */
	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		clearHurt();
		double value=att*(1-hurt/100d);
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("�����˺����ܣ������˺� ==="+value);
		return (int)value;
	}
}
