package com.cambrian.dfhm.monster;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;

/**
 * ��˵�������﹤����
 * @author��Sebastian
 * 
 */
public class Monster extends Sample{

	/* static fields */
	/** �������� */
	public static SampleFactory factory=new SampleFactory();

	/* static methods */
	
	/* fields */
	private String name;
	/** ��Ƭͷ��(ǰ̨��) */
	private String avatar;
	/** С��Ƭͷ��(ǰ̨��) */
	private String tinyAvatar;
	/** �ȼ� */
	private int level;
	/** ������ */
	private int att;
	/** ���ܷ����� */
	private int skillRate;
	/** ��ͨ������Χ (�����ܹ�����Χһ��)*/
	private int attRange;
	/** ���Ѫ�� */
	private int maxHp;
	/** ��ǰѪ�� */
	private int curHp;
	/** ӵ�м���ID */
	private int skillId;
	/** ����Ŀ�����1=����2=���� */
	private int aimType;
	/** ����λ�� */
	private int attIndex;
	/** ������ */
	private int critRate;
	/** �����˺�ϵ�� */
	private int critFactor;
	/** ������ */
	private int dodgeRate;
	/** ��������sid */
	private int awardSid;
	/** ���ͣ��������ϳȣ�5�֣� */
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
