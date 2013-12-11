package com.cambrian.dfhm.skill;

import java.util.ArrayList;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.entity.DamageEntity;

/**
 * 类说明：卡牌技能基类
 * 
 * @author：Sebastian
 */
public class Skill extends Sample
{

	/* static fields */
	/** 样本工厂 */
	public static SampleFactory factory=new SampleFactory();

	/* static methods */
	/** 获得一个的关卡 */
	public static Skill getSkill(int sid)
	{
		Skill skill=(Skill)factory.getSample(sid);
		if(skill==null)
			throw new DataAccessException(
				DataAccessException.CLIENT_SDATA_ERROR,sid+"");// 不准改为编号
		return skill;
	}

	/* fields */
	private String name;
	/** 介绍 */
	private String description;
	/** 攻击范围 */
	private int attRange;
	/** 等级 */
	private int level;
	/** 攻击系数 */
	private int att;
	/** 攻击对象，1=自己，2=敌人 */
	private int aim;
	/** 伤害对象记录 */
	private ArrayList<DamageEntity> hurtList=new ArrayList<DamageEntity>();
	/** 技能发动率 */
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

	public ArrayList<DamageEntity> getHurtList()
	{
		return hurtList;
	}
	/* init start */

	/* methods */
	/**
	 * 技能攻击伤害值计算
	 * 
	 * @param attCard 攻击者
	 * @param aim 被攻击目标索引
	 * @param aimList 被攻击对象数组
	 * @return
	 */
	public ArrayList<DamageEntity> skillValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record)
	{
		System.err.println("技能攻击伤害值,多态的基类哦--------------");
		return hurtList;
	}

	/**
	 * 技能buff伤害值
	 * 
	 * @param attCard 出手攻击者
	 * @param att 本次攻击伤害值
	 * @param aimCard 伤害目标
	 * @param record 记录
	 * @return 伤害值
	 */
	public int buffValue(BattleCard attCard,int att,BattleCard aimCard,
		BattleRecord record)
	{
		// System.err.println("技能buff伤害值,多态的基类哦--------------");
		// 判断攻击方是否有攻状态
		if(aimCard==null)
		{
			return 0;
		}
		ArrayList<Skill> deSkill=attCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof IncrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		deSkill=aimCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof NoHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
			if(skill instanceof DecrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		return att;
	}

	/** 获得技能攻击值 */
	public int getSkillHurt(int attValue)
	{
		System.err.println("attValue ==="+attValue);
		System.err.println("攻击系数 ==="+att);
		int hurt=attValue*att;
		return hurt;
	}

	/** 添加死亡位置 */
	public void addHurt(DamageEntity hurt)
	{
		hurtList.add(hurt);
	}

	/** 清除死亡位置 */
	public void clearHurt()
	{
		hurtList.clear();
	}
}
