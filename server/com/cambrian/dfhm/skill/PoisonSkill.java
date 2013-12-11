package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * 类说明：中毒技能
 * 
 * @author：Sebastian
 */
public class PoisonSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 每次中毒扣血数 */
	private int hurt;
	private int round;
	/** 中毒扣血数 */
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
			// 技能照成伤害：向下取整(卡牌攻击力*技能系数/1000)
			int value=super.getSkillHurt(attCard.getAtt());
			// 每回合中毒扣血数：向下取整(（卡牌攻击力*技能系数/1000）*毒效果伤害倍数/1000*回合数)
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
				System.err.println("中毒技能，照成伤害 ==="+value
					+(crit?"暴击伤害":"普通伤害"));
			else
				System.err.println("伤害被闪避");
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
		record.addRecord(1);// 中毒
		record.addRecord(attCard.getSide());
		record.addRecord(decrHp);
		aimCard.decrHp(decrHp);
		if(attCard.getCurHp()<=0)// 可能中毒死亡
			record.addRecord(1);// 死亡
		else
			record.addRecord(-1);// 没死
		round--;
		if(round==0) aimCard.delDeBuff(this.getSid());
		System.err.println("中毒Debuffer，照成伤害对象==="+aimCard.getName()
			+", 血量损失 ==="+decrHp+", 剩余血量 ==="+aimCard.getCurHp()
			+"， 剩余回合数 ==="+round);
		return decrHp;
	}
}
