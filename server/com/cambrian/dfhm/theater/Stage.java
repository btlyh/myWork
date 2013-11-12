package com.cambrian.dfhm.theater;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;
import com.cambrian.common.util.TextKit;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.monster.Monster;

/**
 * 类说明：
 * 
 * @author：Sebastian
 */
public class Stage extends Sample
{

	/* static fields */
	/** 样本工厂 */
	public static SampleFactory factory=new SampleFactory();

	/* static methods */

	/* field */
	/** 名字 */
	private String name;
	/** 介绍 */
	private String description;
	/** 所属战区 */
	private int theaterId;
	/** 上个关卡 */
	private int parentStage;
	/** 下个关卡 */
	private int nextStage;
	/** 军令消耗 */
	private int token;
	/** 回合数 */
	private int round;
	/** 出战怪物列表 */
	private BattleCard[] monster;

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
	public int getTheaterId()
	{
		return theaterId;
	}
	public void setTheaterId(int theaterId)
	{
		this.theaterId=theaterId;
	}
	public int getParentStage()
	{
		return parentStage;
	}
	public void setParentStage(int parentStage)
	{
		this.parentStage=parentStage;
	}
	public int getNextStage()
	{
		return nextStage;
	}
	public void setNextStage(int nextStage)
	{
		this.nextStage=nextStage;
	}
	public int getToken()
	{
		return token;
	}
	public void setToken(int token)
	{
		this.token=token;
	}
	public int getRound()
	{
		return round;
	}
	public void setRound(int round)
	{
		this.round=round;
	}

	public BattleCard[] getMonster()
	{
		return monster;
	}
	/* init start */
	/** 初始化敌人 */
	public void initEnemy(String str)
	{
		String[] temp=TextKit.split(str,",");
		monster=new BattleCard[5];
		int sid;
		Monster monster_;
		BattleCard bcard;
		for(int i=0;i<temp.length;i++)
		{
			sid=TextKit.parseInt(temp[i]);
			monster_=(Monster)Sample.factory.getSample(sid);
			monster_.setCurHp(monster_.getMaxHp());
			bcard=new BattleCard(monster_.getSid(),monster_.getName(),
				monster_.getAvatar(),monster_.getTinyAvatar(),
				monster_.getLevel(),monster_.getAtt(),
				monster_.getSkillRate(),monster_.getAttRange(),
				monster_.getSkillId(),monster_.getMaxHp(),
				monster_.getCurHp(),i,monster_.getAimType(),
				monster_.getCritRate(),monster_.getDodgeRate(),
				monster_.getAwardSid(),monster_.getType());
			monster[i]=bcard;
		}
	}

	/* methods */
	public void bytesWrite(ByteBuffer data)
	{
		// data.writeUTF(name);
		// data.writeUTF(description);
		// data.writeInt(theaterId);
		// data.writeInt(id);
		// data.writeInt(enemy.length);
		// for (int i = 0; i < enemy.length; i++) {
		// data.writeInt(enemy[i]);
		// }
	}
}
