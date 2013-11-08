package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵��������
 * 
 * @author��Sebastian
 */
public class NoHurtSkill extends Skill
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ���˴��� */
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
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		clearHurt();
		addHurt(0);
		NoHurtSkill skill;
		BattleCard bCard;
		for(int i=0;i<aim.size();i++)
		{
			bCard=aimList[aim.get(i)];
			if(!bCard.hadDeSkill(this.getSid()))
			{
				skill=(NoHurtSkill)Sample.factory.newSample(this.getSid());
				bCard.addDeBuff(skill);
			}
		}
		return getHurtList();
	}

	@Override
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		number--;
		if(number==0) aimCard.delDeBuff(this.getSid());
		System.err.println("����״̬��ʣ����� ==="+number);
		return 0;
	}
}
