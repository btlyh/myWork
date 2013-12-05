package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * 类说明：晕眩技能
 * 
 * @author：Sebastian
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
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		int value;
		DizzySkill skill;
		BattleCard aimCard;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=getSkillHurt(attCard.getAtt())/1000;
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
			DamageEntity damage = new DamageEntity(damageStatus,value);
			addHurt(damage);
			if(value>0)
				System.err.println("眩晕技能 照成伤害 ==="+value+(crit?"暴击伤害":"普通伤害"));
			else
				System.err.println("伤害被闪避");
			if(!aimCard.hadDeSkill(this.getSid()))
			{
				skill=(DizzySkill)Sample.factory.newSample(this.getSid());
				boolean isDizzy=false;
				for(Skill skill_:aimCard.getDeSkill())
				{
					if(skill_ instanceof DizzySkill)
					{
						isDizzy=true;
						break;
					}
				}
				if(!isDizzy) aimCard.addDeBuff(skill);
			}
		}
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		record.addRecord(this.getSid());
		record.addRecord(2);// 表示晕眩
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("晕眩状态，剩余回合数 ==="+round);
		return 0;
	}
}
