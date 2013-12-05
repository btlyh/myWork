package com.cambrian.dfhm.monster;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;

/**
 * 类说明：怪物工厂类
 * @author：Sebastian
 * 
 */
public class Monster extends Sample{

	/* static fields */
	/** 样本工厂 */
	public static SampleFactory factory=new SampleFactory();

	/* static methods */
	
	/* fields */
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
	/** 普通攻击范围 (跟技能攻击范围一样)*/
	private int attRange;
	/** 最大血量 */
	private int maxHp;
	/** 当前血量 */
	private int curHp;
	/** 拥有技能ID */
	private int skillId;
	/** 攻击目标对象，1=自身，2=敌人 */
	private int aimType;
	/** 攻击位置 */
	private int attIndex;
	/** 暴击率 */
	private int critRate;
	/** 暴击伤害系数 */
	private int critFactor;
	/** 闪避率 */
	private int dodgeRate;
	/** 奖励掉落sid */
	private int awardSid;
	/** 类型（白绿蓝紫橙，5种） */
	private int type;

	/* constructors */

	/* properties */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getAtt() {
		return att;
	}
	public void setAtt(int att) {
		this.att = att;
	}
	public int getSkillRate() {
		return skillRate;
	}
	public void setSkillRate(int skillRate) {
		this.skillRate = skillRate;
	}
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public int getCritRate() {
		return critRate;
	}
	public void setCritRate(int critRate) {
		this.critRate = critRate;
	}
	public int getDodgeRate() {
		return dodgeRate;
	}
	public void setDodgeRate(int dodgeRate) {
		this.dodgeRate = dodgeRate;
	}
	public int getAwardSid() {
		return awardSid;
	}
	public void setAwardSid(int awardSid) {
		this.awardSid = awardSid;
	}
	public int getAttRange() {
		return attRange;
	}
	public void setAttRange(int attRange) {
		this.attRange = attRange;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getTinyAvatar() {
		return tinyAvatar;
	}
	public void setTinyAvatar(String tinyAvatar) {
		this.tinyAvatar = tinyAvatar;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public int getCurHp() {
		return curHp;
	}
	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}
	public int getAimType() {
		return aimType;
	}
	public void setAimType(int aimType) {
		this.aimType = aimType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAttIndex() {
		return attIndex;
	}
	public void setAttIndex(int attIndex) {
		this.attIndex = attIndex;
	}
	public int getCritFactor()
	{
		return critFactor;
	}
	public void setCritFactor(int critFactor)
	{
		this.critFactor = critFactor;
	}

	/* init start */

	/* methods */
	
}
