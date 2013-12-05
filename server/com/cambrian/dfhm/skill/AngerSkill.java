package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.dfhm.battle.BattleAct;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * 类说明：狂怒
 * 
 * @author：Sebastian
 */
public class AngerSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 每次增加的攻击力百分比 */
	private int hurt;
	/** 下降血量度百分比 */
	private int hp;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * 每下降了%5的血量，增加攻击
	 * 
	 * @param attCard
	 * @return 返回额外增加的攻击力
	 */
	private int downHp(BattleCard attCard)
	{
		// 向下取整(卡牌攻击力*技能系数/1000+卡牌攻击力*（取整(自身减少HP/每减少的百分比）*加成的攻击力百分比）)
		System.err.println("狂怒技能使用 -----------------");
		int value=attCard.getMaxHp()-attCard.getCurHp();// 总共减少血量
		System.err.println("玩家减少血量 ==="+value);
		int hp_=attCard.getMaxHp()*hp/100;// 每次加攻，需具体减少的血量
		System.err.println("每次加攻，需具体减少的血量 ==="+hp_);
		int num=value/hp_;// 加攻的次数
		System.err.println("加攻的次数 ==="+num);
		double a=getSkillHurt(attCard.getAtt())/1000;
		double b=attCard.getAtt()*num*hurt/100;
		value=(int)(a+b);// 增加后的总攻击力
		System.err.println("玩家攻击力 ==="+attCard.getAtt());
		System.err.println("增加后的总攻击力 ==="+value);
		return value;
	}

	@Override
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		int value;
		BattleCard aimCard;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=downHp(attCard);
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
				System.err.println("狂怒技能，照成伤害 ==="+value
					+(crit?"暴击伤害":"普通伤害"));
			else
				System.err.println("伤害被闪避");
		}
		return getHurtList();
	}
}
