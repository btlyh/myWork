package com.cambrian.dfhm.globalboss.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.monster.Monster;

/**
 * 类说明：BOSS配置类
 * 
 * @author：LazyBear
 */
public class GlobalBossCFG extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS名称 -XML */
	private String bossName;
	/** 活动开始时间(小时) -XML */
	private int activeTime;
	/** 准备时间 (分钟) -XML */
	private int readyTime;
	/** 活动时间限制 -XML */
	private int timeConfine;
	/** Boss中的怪物卡牌 -XML */
	private int[] battleCardList;
	/** 自动报名费用(RMB) -XML */
	private int autoRegGold;
	/** 自动报名VIP等级限制 -XML */
	private int vipConfine;
	/** 攻击强化费用 -XML */
	private int attUpGold;
	/** 攻击冷却时间(分钟) -XML */
	private int attCD;
	/** 立即复活费用 -XML */
	private int reliveGold;
	/** 排名奖励银币 -XML */
	private int orderMoney;
	/** 排名武魂奖励 -XML */
	private int orderSoul;
	/** 排名卡牌奖励 -XML */
	private int[] orderCardList;
	/** 普通奖励银币 -XML */
	private int normalMoney;
	/** 普通奖励武魂 -XML */
	private int normalSoul;
	/** 普通奖励卡牌集合 -XML */
	private int[] normalCardList;
	/** 特殊奖励银币 -XML */
	private int specialMoney;
	/** 特殊奖励武魂 -XML */
	private int specialSoul;
	/** 特殊奖励卡牌集合 -XML */
	private int[] specialCardList;
	/** 自动报名奖励银币 -XML */
	private int autoMoney;
	/** 自动报名奖励武魂 -XML */
	private int autoSoul;
	/** 自动报名奖励卡牌集合 -XML */
	private int[] autoCardList;
	/** BOSS是否开启 */
	private boolean isOpen;
	/** 挑战BOSS所需的主线关卡 -XML */
	private int normalNPCIndex;
	/** BOSS伤害排行榜 键为排名，值为玩家记录 */
	public Map<Integer,BossFightRecord> rankMap=new HashMap<Integer,BossFightRecord>();
	/** 出战卡牌列表 */
	protected BattleCard[] monsters=new BattleCard[5];
	/** 排名限制(用于限制排名前的获奖人员) -XML */
	private int orderConfine;
	/** 回合限制 -XML */
	private int roundConfine;
	/** 自动战斗玩家列表 */
	public List<Player> autoList=new ArrayList<Player>();

	/* constructors */

	/* properties */
	public String getBossName()
	{
		return bossName;
	}

	public void setBossName(String bossName)
	{
		this.bossName=bossName;
	}
	public int getActiveTime()
	{
		return activeTime;
	}

	public void setActiveTime(int activeTime)
	{
		this.activeTime=activeTime;
	}

	public int getReadyTime()
	{
		return readyTime;
	}

	public void setReadyTime(int readyTime)
	{
		this.readyTime=readyTime;
	}

	public int[] getBattleCardList()
	{
		return battleCardList;
	}

	public void setBattleCardList(int[] battleCardList)
	{
		this.battleCardList=battleCardList;
	}

	public int getAutoRegGold()
	{
		return autoRegGold;
	}

	public void setAutoRegGold(int autoRegGold)
	{
		this.autoRegGold=autoRegGold;
	}

	public int getVipConfine()
	{
		return vipConfine;
	}

	public void setVipConfine(int vipConfine)
	{
		this.vipConfine=vipConfine;
	}

	public int getAttUpGold()
	{
		return attUpGold;
	}

	public void setAttUpGold(int attGrow)
	{
		this.attUpGold=attGrow;
	}

	public int getAttCD()
	{
		return attCD;
	}

	public void setAttCD(int attCD)
	{
		this.attCD=attCD;
	}

	public int getReliveGold()
	{
		return reliveGold;
	}

	public void setReliveGold(int reliveGold)
	{
		this.reliveGold=reliveGold;
	}

	public int getNormalMoney()
	{
		return normalMoney;
	}

	public void setNormalMoney(int normalMoney)
	{
		this.normalMoney=normalMoney;
	}

	public int getNormalSoul()
	{
		return normalSoul;
	}

	public void setNormalSoul(int normalSoul)
	{
		this.normalSoul=normalSoul;
	}

	public int[] getNormalCardList()
	{
		return normalCardList;
	}

	public void setNormalCardList(int[] normalCardList)
	{
		this.normalCardList=normalCardList;
	}

	public int getSpecialMoney()
	{
		return specialMoney;
	}

	public void setSpecialMoney(int specialGold)
	{
		this.specialMoney=specialGold;
	}

	public int getSpecialSoul()
	{
		return specialSoul;
	}

	public void setSpecialSoul(int specialSoul)
	{
		this.specialSoul=specialSoul;
	}

	public int[] getSpecialCardList()
	{
		return specialCardList;
	}

	public void setSpecialCardList(int[] specialCardList)
	{
		this.specialCardList=specialCardList;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen=isOpen;
	}
	public int getNormalNPCIndex()
	{
		return normalNPCIndex;
	}

	public void setNormalNPCIndex(int normalNPCIndex)
	{
		this.normalNPCIndex=normalNPCIndex;
	}

	public int getOrderMoney()
	{
		return orderMoney;
	}

	public void setOrderMoney(int orderMoney)
	{
		this.orderMoney=orderMoney;
	}

	public int getOrderSoul()
	{
		return orderSoul;
	}

	public void setOrderSoul(int orderSoul)
	{
		this.orderSoul=orderSoul;
	}

	public int[] getOrderCardList()
	{
		return orderCardList;
	}

	public void setOrderCardList(int[] orderCardList)
	{
		this.orderCardList=orderCardList;
	}
	public int getOrderConfine()
	{
		return orderConfine;
	}

	public void setOrderConfine(int orderConfine)
	{
		this.orderConfine=orderConfine;
	}
	public BattleCard[] getMonsters()
	{
		return monsters;
	}
	public int getRoundConfine()
	{
		return roundConfine;
	}
	public void setRoundConfine(int roundConfine)
	{
		this.roundConfine=roundConfine;
	}
	public int getTimeConfine()
	{
		return timeConfine;
	}
	public void setTimeConfine(int timeConfine)
	{
		this.timeConfine=timeConfine;
	}
	public int getAutoMoney()
	{
		return autoMoney;
	}

	public void setAutoMoney(int autoMoney)
	{
		this.autoMoney=autoMoney;
	}

	public int getAutoSoul()
	{
		return autoSoul;
	}

	public void setAutoSoul(int autoSoul)
	{
		this.autoSoul=autoSoul;
	}

	public int[] getAutoCardList()
	{
		return autoCardList;
	}

	public void setAutoCardList(int[] autoCardList)
	{
		this.autoCardList=autoCardList;
	}
	/* init start */

	/* methods */
	/**
	 * 初始化怪物
	 */
	public void initMonsters(String str)
	{
		monsters=new BattleCard[5];
		for(int i=0;i<battleCardList.length;i++)
		{
			Monster card=(Monster)Sample.factory
				.getSample(battleCardList[i]);
			BattleCard battleCard=new BattleCard(card.getId(),
				card.getName(),card.getAvatar(),card.getTinyAvatar(),
				card.getLevel(),card.getAtt(),card.getSkillRate(),
				card.getAttRange(),card.getSkillId(),card.getMaxHp(),
				card.getMaxHp(),i,card.getAimType(),card.getCritRate(),
				card.getDodgeRate(),card.getAwardSid(),i);
			monsters[i]=battleCard;
		}
	}

	/**
	 * 获取BOSS最大血量
	 * 
	 * @return
	 */
	public int getMaxHp()
	{
		int temp=0;
		for(BattleCard bCard:monsters)
		{
			if(bCard!=null) temp+=bCard.getMaxHp();
		}
		return temp;
	}

	/**
	 * 获取BOSS当前血量
	 * 
	 * @return
	 */
	public int getCurHp()
	{
		int temp=0;
		for(BattleCard bCard:monsters)
		{
			if(bCard!=null) temp+=bCard.getCurHp();
		}
		return temp;
	}

	/**
	 * 根据玩家排名、伤害、击杀计算奖励
	 * 
	 * @param bfr
	 */
	public Map<String,ArrayList<Object>> countReward(BossFightRecord bfr)
	{
		Map<String,ArrayList<Object>> rewardMap=new HashMap<String,ArrayList<Object>>();
		ArrayList<Integer> cardList;
		if(bfr.isFinished())
		{
			ArrayList<Object> finishReward=new ArrayList<Object>();
			cardList=new ArrayList<Integer>(specialCardList.length);
			for(int i=0;i<specialCardList.length;i++)
			{
				cardList.add(specialCardList[i]);
			}
			finishReward.add(cardList);
			finishReward.add(specialSoul);
			finishReward.add(specialMoney);
			rewardMap.put("finishReward",finishReward);
		}
		if(bfr.getRank()<orderConfine)
		{
			ArrayList<Object> rankReward=new ArrayList<Object>();
			cardList=new ArrayList<Integer>(orderCardList.length);
			for(int i=0;i<orderCardList.length;i++)
			{
				cardList.add(orderCardList[i]);
			}
			rankReward.add(cardList);
			rankReward.add(orderSoul*(orderConfine-bfr.getRank()));
			rankReward.add(orderMoney*(orderConfine-bfr.getRank()));
			rewardMap.put("rankReward",rankReward);
		}
		if(bfr.getTotalDamage()>0)
		{
			ArrayList<Object> damageReward=new ArrayList<Object>();
			cardList=new ArrayList<Integer>(normalCardList.length);
			for(int i=0;i<normalCardList.length;i++)
			{
				cardList.add(normalCardList[i]);
			}
			damageReward.add(cardList);
			damageReward.add(normalSoul*bfr.getTotalDamage());
			damageReward.add(normalMoney*bfr.getTotalDamage());
			rewardMap.put("rankReward",damageReward);
		}
		return rewardMap;
	}

	/**
	 * 获取自动报名奖励
	 * 
	 * @return
	 */
	public ArrayList<Object> countReward()
	{
		ArrayList<Object> autoReward=new ArrayList<Object>();
		ArrayList<Integer> cardList=new ArrayList<Integer>(autoCardList.length);
		for(int i=0;i<autoCardList.length;i++)
		{
			cardList.add(autoCardList[i]);
		}
		autoReward.add(cardList);
		autoReward.add(autoSoul);
		autoReward.add(autoMoney);
		return autoReward;
	}
	/***
	 * 计算排名
	 */
	public List<BossFightRecord> countRank()
	{
		List<BossFightRecord> recordList=new ArrayList<BossFightRecord>();
		if(rankMap.size()>0)
		{
			Iterator<Integer> iterator=rankMap.keySet().iterator();
			while(iterator.hasNext())
			{
				int key=iterator.next();
				recordList.add(rankMap.get(key));
			}
		}
		if(recordList.size()>0)
		{
			Collections.sort(recordList);
			for(int i=0;i<recordList.size();i++)
			{
				recordList.get(i).setRank(i+1);
			}
		}
		return recordList;
	}

	/**
	 * 怪物重置
	 */
	public void reset()
	{
		for(BattleCard battleCard:monsters)
		{
			if(battleCard!=null)
			{
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
	}
}
