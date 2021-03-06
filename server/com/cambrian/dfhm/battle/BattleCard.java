package com.cambrian.dfhm.battle;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.skill.NoHurtSkill;
import com.cambrian.dfhm.skill.Skill;

/**
 * 类说明：战斗对象
 * 
 * @author：Sebastian
 */
public class BattleCard implements Cloneable
{

	/* static fields */

	/* static methods */

	@Override
	public Object clone()
	{
		BattleCard battleCard=null;
		try
		{
			battleCard=(BattleCard)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return battleCard;
	}

	/* fields */
	/** 卡牌UID */
	private int id;
	/** 卡牌SID */
	private int sid;
	/** 卡牌名称 */
	private String name;
	/** 卡片头像(前台用) */
	private String avatar;
	/** 小卡片头像(前台用) */
	private String tinyAvatar;
	/** 等级 */
	private int level;
	/** 攻击力 */
	private int att;
	/** 技能发动率 */
	private int skillRate;
	/** 普通攻击范围 (跟技能攻击范围一样) */
	private int attRange;
	/** 拥有技能ID */
	private Skill skill;
	/** 最大血量 */
	private int maxHp;
	/** 当前血量 */
	private int curHp;
	/** 是否已经攻击过 */
	private boolean isAttack;
	/** 上阵位置 */
	private int index;
	/** 攻击目标对象，1=自身，2=敌人 */
	private int aimType;
	/** 暴击率 */
	private int critRate;
	/** 暴击伤害系数 */
	private int critFactor;
	/** 闪避率 */
	private int dodgeRate;
	/** 被作用技能 */
	public ArrayList<Skill> deSkill;
	/** 奖励掉落sid */
	private int awardSid;
	/** 战斗方位：1=攻击，2=防守 */
	private int side;
	/** 类型（白绿蓝紫橙，5种） */
	private int type;
	/** 喝酒状态 */
	private int drinkStatus=Card.AWAKE;

	/* constructors */
	public BattleCard(int id,String name,String avatar,String tinyAvatar,
		int level,int att,int skillRate,int attRange,int skillId,int maxHp,
		int curHp,int index,int aimType,int critRate,int dodgeRate,
		int awardSid,int type,int sid,int critFactor,int drinkStatus)
	{
		this.id=id;
		this.name=name;
		this.avatar=avatar;
		this.tinyAvatar=tinyAvatar;
		this.att=att;
		this.skillRate=skillRate;
		this.attRange=attRange;
		this.skill=(Skill)Sample.factory.getSample(skillId);
		System.err.println("id ===="+id);
		System.err.println("skillId ===="+skillId);
		System.err.println("skill ===="+skill);
		this.maxHp=maxHp;
		this.curHp=curHp;
		isAttack=false;
		this.index=index;
		this.aimType=aimType;
		this.critRate=critRate;
		this.dodgeRate=dodgeRate;
		deSkill=new ArrayList<Skill>();
		this.awardSid=awardSid;
		this.type=type;
		this.sid=sid;
		this.critFactor=critFactor;
		this.level=level;
		this.drinkStatus=drinkStatus;
	}

	public BattleCard()
	{
		// TODO Auto-generated constructor stub
	}

	/* properties */
	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getAtt()
	{
		return att;
	}

	public int getSkillRate()
	{
		return skillRate;
	}

	public int getAttRange()
	{
		return attRange;
	}

	public boolean isAttack()
	{
		return isAttack;
	}

	public void setAttack(boolean isAttack)
	{
		this.isAttack=isAttack;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index=index;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public void setAtt(int att)
	{
		this.att=att;
	}

	public void setSkillRate(int skillRate)
	{
		this.skillRate=skillRate;
	}

	public void setAttRange(int attRange)
	{
		this.attRange=attRange;
	}

	public int getAimType()
	{
		return aimType;
	}

	public void setAimType(int aimType)
	{
		this.aimType=aimType;
	}

	public Skill getSkill()
	{
		return skill;
	}

	public void setSkill(Skill skill)
	{
		this.skill=skill;
	}

	public int getMaxHp()
	{
		return maxHp;
	}

	public void setMaxHp(int maxHp)
	{
		this.maxHp=maxHp;
	}

	public int getCurHp()
	{
		return curHp;
	}

	public void setCurHp(int curHp)
	{
		this.curHp=curHp;
	}
	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar=avatar;
	}

	public String getTinyAvatar()
	{
		return tinyAvatar;
	}

	public void setTinyAvatar(String tinyAvatar)
	{
		this.tinyAvatar=tinyAvatar;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level=level;
	}

	public int getCritRate()
	{
		return critRate;
	}

	public void setCritRate(int critRate)
	{
		this.critRate=critRate;
	}

	public int getDodgeRate()
	{
		return dodgeRate;
	}

	public void setDodgeRate(int dodgeRate)
	{
		this.dodgeRate=dodgeRate;
	}

	public int getAwardSid()
	{
		return awardSid;
	}
	public int getSide()
	{
		return side;
	}

	public void setSide(int side)
	{
		this.side=side;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type=type;
	}

	public ArrayList<Skill> getDeSkill()
	{
		return deSkill;
	}

	public int getCritFactor()
	{
		return critFactor;
	}

	public void setCritFactor(int critFactor)
	{
		this.critFactor=critFactor;
	}
	/* init start */

	/* methods */
	public void attUp(int ratio)
	{
		att+=(int)(att*ratio*0.01f);
	}

	public int getDrinkStatus()
	{
		return drinkStatus;
	}

	public void setDrinkStatus(int drinkStatus)
	{
		this.drinkStatus=drinkStatus;
	}
	/**
	 * 扣血
	 * 
	 * @param hp
	 * @return 返回是否死亡
	 */
	public boolean decrHp(int hp)
	{
		this.curHp-=hp;
		return curHp<=0;
	}

	public void incrHp(int hp)
	{
		this.curHp+=hp;
		this.curHp=(curHp>maxHp)?maxHp:curHp;
	}

	/**
	 * 判断是否拥有指定的debuff技能
	 * 
	 * @param skillId
	 * @return
	 */
	public boolean hadDeSkill(int skillId)
	{
		for(Skill skill:deSkill)
		{
			if(skill.getSid()==skillId) return true;
		}
		return false;
	}

	/**
	 * 是否拥有免伤技能
	 * 
	 * @return
	 */
	public boolean hadNoHurtSkill()
	{
		for(Skill skill:deSkill)
		{
			if(skill instanceof NoHurtSkill) return true;
		}
		return false;
	}

	/**
	 * 增加指定debuff
	 * 
	 * @param skill
	 */
	public void addDeBuff(Skill skill)
	{
		deSkill.add(skill);
	}

	/**
	 * 删除指定debuff
	 * 
	 * @param skillId
	 */
	public void delDeBuff(int skillId)
	{
		for(int i=0;i<deSkill.size();i++)
		{
			if(deSkill.get(i).getSid()==skillId)
			{
				deSkill.remove(i);
				return;
			}
		}
	}
	/** 前台序列化读取 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(id);
		data.writeInt(sid);
		data.writeUTF(name);
		data.writeUTF(avatar);
		data.writeUTF(tinyAvatar);
		data.writeInt(level);
		data.writeInt(att);
		data.writeInt(skillRate);
		data.writeInt(attRange);
		data.writeInt(skill.getSid());
		data.writeInt(maxHp);
		data.writeInt(curHp);
		data.writeBoolean(isAttack);
		data.writeInt(index);
		data.writeInt(aimType);
		data.writeInt(critRate);
		data.writeInt(dodgeRate);
		data.writeInt(type);
	}

	/** 序列化(和dc通信) */
	public void dbBytesWrite(ByteBuffer data)
	{
		System.err.println("------BattleCard.dbBytesWrite--------");
		data.writeInt(id);
		data.writeUTF(name);
		data.writeUTF(avatar);
		data.writeUTF(tinyAvatar);
		data.writeInt(level);
		data.writeInt(att);
		data.writeInt(skillRate);
		data.writeInt(attRange);
		System.err.println("id ===="+id);
		System.err.println("skill ===="+skill);
		data.writeInt(skill.getSid());
		data.writeInt(maxHp);
		data.writeInt(curHp);
		data.writeBoolean(isAttack);
		data.writeInt(index);
		data.writeInt(aimType);
		data.writeInt(critRate);
		data.writeInt(dodgeRate);
		// System.err.println("att ==="+ att);
		// System.err.println("skillRate ==="+ skillRate);
		// System.err.println("doubleSkill[0] ==="+ doubleSkill[0]);
		// System.err.println("doubleSkill[1] ==="+ doubleSkill[1]);
		// System.err.println("attRange ==="+ attRange);
		// System.err.println("skill.getSid() ==="+ skill.getSid());
		// System.err.println("maxHp ==="+ maxHp);
		// System.err.println("curHp ==="+ curHp);
		// System.err.println("isAttack ==="+ isAttack);
		// System.err.println("index ==="+ index);
		// System.err.println("aimType ==="+ aimType);
		if(deSkill==null)
		{
			data.writeInt(-1);
			System.err.println("-1");
		}
		else
		{
			data.writeInt(deSkill.size());
			for(Skill skill:deSkill)
			{
				data.writeInt(skill.getSid());
				System.err.println("skill.getSid() ==="+skill.getSid());
			}
		}
		data.writeInt(awardSid);
		data.writeInt(type);
		data.writeInt(sid);
		data.writeInt(critFactor);
		data.writeInt(drinkStatus);
	}

	/** 反序列化(和dc通信) */
	public void dbBytesRead(ByteBuffer data)
	{
		// System.err.println("------BattleCard.dbBytesRead--------");
		id=data.readInt();
		name=data.readUTF();
		avatar=data.readUTF();
		tinyAvatar=data.readUTF();
		level=data.readInt();
		att=data.readInt();
		skillRate=data.readInt();
		attRange=data.readInt();
		int skillId=data.readInt();
		skill=(Skill)Sample.factory.getSample(skillId);
		maxHp=data.readInt();
		curHp=data.readInt();
		isAttack=data.readBoolean();
		index=data.readInt();
		aimType=data.readInt();
		critRate=data.readInt();
		dodgeRate=data.readInt();
		int len=data.readInt();
		// System.err.println("att ==="+ att);
		// System.err.println("skillRate ==="+ skillRate);
		// System.err.println("doubleSkill[0] ==="+ doubleSkill[0]);
		// System.err.println("doubleSkill[1] ==="+ doubleSkill[1]);
		// System.err.println("attRange ==="+ attRange);
		// System.err.println("skill.getSid() ==="+ skill.getSid());
		// System.err.println("maxHp ==="+ maxHp);
		// System.err.println("curHp ==="+ curHp);
		// System.err.println("isAttack ==="+ isAttack);
		// System.err.println("index ==="+ index);
		// System.err.println("aimType ==="+ aimType);
		// System.err.println("deSkillId ==="+ skillId);
		if(len>0)
		{
			Skill skill;
			for(int i=0;i<len;i++)
			{
				skill=(Skill)Sample.factory.getSample(skillId);
				deSkill.add(skill);
			}
		}
		else
		{
			deSkill=new ArrayList<Skill>();
		}
		awardSid=data.readInt();
		type=data.readInt();
		sid=data.readInt();
		critFactor=data.readInt();
		drinkStatus=data.readInt();
	}
}
