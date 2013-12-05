package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * 类说明：免伤
 * 
 * @author：Sebastian
 */
public class NoHurtSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 免伤次数 */
	private int number;

	/* constructors */

	/* properties */
	public int getNumber()
	{
		return number;
	}

	/* init start */

	/* methods */

	@Override
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		NoHurtSkill skill;
		BattleCard bCard;
		for(int i=0;i<aim.size();i++)
		{
			bCard=aimList[aim.get(i)];
			if(!bCard.hadDeSkill(this.getSid()))
			{
				skill=(NoHurtSkill)Sample.factory.newSample(this.getSid());
				boolean isNoHurt=false;
				for(Skill skill_:bCard.getDeSkill())
				{
					if(skill_ instanceof NoHurtSkill)
					{
						isNoHurt=true;
						break;
					}
				}
				if(!isNoHurt) bCard.addDeBuff(skill);
			}
			System.err.println("免伤技能！！！！！！");
		}
		DamageEntity damage=new DamageEntity(DamageEntity.DAMAGE_NORMAL,0);
		addHurt(damage);
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		number--;
		if(number==0) aimCard.delDeBuff(this.getSid());
		System.err.println("免伤状态，剩余次数 ==="+number);
		return 0;
	}
}
