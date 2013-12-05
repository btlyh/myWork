package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * 类说明：连击
 * 
 * @author：Sebastian
 */
public class AttacksSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 连续攻击次数 */
	private int count;
	/** 每次攻击伤害数 */
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
			// 向下取整(卡牌攻击力*技能系数/1000+卡牌攻击力*额外伤害系数/1000*连击次数)
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
				System.err.println("连击技能，照成伤害 ==="+value
					+(crit?"暴击伤害":"普通伤害"));
			else
				System.err.println("伤害被闪避");
		}
		return getHurtList();
	}
}
