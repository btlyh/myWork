package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;

/**
 * ��˵�������Ƽ��ܻ���
 * 
 * @author��Sebastian
 */
public class Skill extends Sample
{

	/* static fields */
	/** �������� */
	public static SampleFactory factory=new SampleFactory();

	/* static methods */
	/** ���һ���Ĺؿ� */
	public static Skill getSkill(int sid)
	{
		Skill skill=(Skill)factory.getSample(sid);
		if(skill==null)
			throw new DataAccessException(
				DataAccessException.CLIENT_SDATA_ERROR,sid+"");// ��׼��Ϊ���
		return skill;
	}

	/* fields */
	private String name;
	/** ���� */
	private String description;
	/** ������Χ */
	private int attRange;
	/** �ȼ� */
	private int level;
	/** ����ϵ�� */
	private int att;
	/** ��������1=�Լ���2=���� */
	private int aim;
	/** �˺�ֵ��¼ */
	private ArrayList<Integer> hurtList=new ArrayList<Integer>();
	/** ���ܷ����� */
	private int skillRate;

	/* constructors */

	/* properties */
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public int getAttRange()
	{
		return attRange;
	}
	public void setAttRange(int attRange)
	{
		this.attRange=attRange;
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level=level;
	}
	public int getAtt()
	{
		return att;
	}
	public void setAtt(int att)
	{
		this.att=att;
	}
	public int getAim()
	{
		return aim;
	}
	public void setAim(int aim)
	{
		this.aim=aim;
	}
	public int getSkillRate()
	{
		return skillRate;
	}

	public void setSkillRate(int skillRate)
	{
		this.skillRate=skillRate;
	}

	public ArrayList<Integer> getHurtList()
	{
		return hurtList;
	}
	/* init start */

	/* methods */
	/**
	 * ���ܹ����˺�ֵ����
	 * 
	 * @param attCard ������
	 * @param aim ������Ŀ������
	 * @param aimList ��������������
	 * @return
	 */
	public ArrayList<Integer> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		System.err.println("���ܹ����˺�ֵ,��̬�Ļ���Ŷ--------------");
		return hurtList;
	}

	/**
	 * ����buff�˺�ֵ
	 * 
	 * @param attCard ���ֹ�����
	 * @param att ���ι����˺�ֵ
	 * @param aimCard �˺�Ŀ��
	 * @param record ��¼
	 * @return �˺�ֵ
	 */
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		// System.err.println("����buff�˺�ֵ,��̬�Ļ���Ŷ--------------");
		if(aimCard.hadNoHurtSkill())// �жϷ��ַ��Ƿ�������״̬
		{
			return 0;
		}
		// �жϹ������Ƿ��й�״̬
		ArrayList<Skill> deSkill=attCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof IncrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		// �жϷ��ط��Ƿ��м���״̬
		deSkill=aimCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof DecrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		return att;
	}

	/** ��ü��ܹ���ֵ */
	public int getSkillHurt(int attValue)
	{
		System.err.println("attValue ==="+attValue);
		System.err.println("����ϵ�� ==="+att);
		int hurt=attValue*att;
		return hurt;
	}

	/** ��������λ�� */
	public void addHurt(int hurt)
	{
		hurtList.add(hurt);
	}

	/** �������λ�� */
	public void clearHurt()
	{
		hurtList.clear();
	}
}