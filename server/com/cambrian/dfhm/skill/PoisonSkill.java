package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

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
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();

		PoisonSkill skill;
		BattleCard aimCard;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			// 技能照成伤害：向下取整(卡牌攻击力*技能系数/1000)
			int value=super.getSkillHurt(attCard.getAtt());
			// 每回合中毒扣血数：向下取整(（卡牌攻击力*技能系数/1000）*毒效果伤害倍数/1000*回合数)
			double a=value/1000;
			double b=hurt/1000;
			decrHp=(int)a*(int)b*round;
			value/=1000;
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
			System.err.println("中毒技能，照成伤害 ==="+value);
		}
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		// decrHp=10000;
		record.addRecord(this.getSid());
		record.addRecord(1);//中毒
		record.addRecord(attCard.getSide());
		record.addRecord(hurt);
		aimCard.decrHp(decrHp);
		if(attCard.getCurHp()<=0)// 可能中毒死亡
			record.addRecord(1);//死亡
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
