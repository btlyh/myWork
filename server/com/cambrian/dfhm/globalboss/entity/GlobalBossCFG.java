package com.cambrian.dfhm.globalboss.entity;

import java.util.ArrayList;
import java.util.Arrays;
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
 * ��˵����BOSS������
 * 
 * @author��LazyBear
 */
public class GlobalBossCFG extends Sample
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS���� -XML */
	private String bossName;
	/** ���ʼʱ��(Сʱ) -XML */
	private int activeTime;
	/** ׼��ʱ�� (����) -XML */
	private int readyTime;
	/** �ʱ������ -XML */
	private int timeConfine;
	/** Boss�еĹ��￨�� -XML */
	private int[] battleCardList;
	/** �Զ���������(RMB) -XML */
	private int autoRegGold;
	/** �Զ�����VIP�ȼ����� -XML */
	private int vipConfine;
	/** ����ǿ������ -XML */
	private int attUpGold;
	/** ������ȴʱ��(����) -XML */
	private int attCD;
	/** ����������� -XML */
	private int reliveGold;
	/** ������������ -XML */
	private int orderMoney;
	/** ������꽱�� -XML */
	private int orderSoul;
	/** �������ƽ��� -XML */
	private int[] orderCardList;
	/** ��ͨ�������� -XML */
	private int normalMoney;
	/** ��ͨ������� -XML */
	private int normalSoul;
	/** ��ͨ�������Ƽ��� -XML */
	private int[] normalCardList;
	/** ���⽱������ -XML */
	private int specialMoney;
	/** ���⽱����� -XML */
	private int specialSoul;
	/** ���⽱�����Ƽ��� -XML */
	private int[] specialCardList;
	/** �Զ������������� -XML */
	private int autoMoney;
	/** �Զ������������ -XML */
	private int autoSoul;
	/** �Զ������������Ƽ��� -XML */
	private int[] autoCardList;
	/** BOSS�Ƿ��� */
	private boolean isOpen;
	/** ��սBOSS��������߹ؿ� -XML */
	private int normalNPCIndex;
	/** BOSS�˺����а� ��Ϊ������ֵΪ��Ҽ�¼ */
	public Map<Integer,BossFightRecord> rankMap=new HashMap<Integer,BossFightRecord>();
	/** ��ս�����б� */
	protected BattleCard[] monsters=new BattleCard[5];
	/** ��������(������������ǰ�Ļ���Ա) -XML */
	private int orderConfine;
	/** �غ����� -XML */
	private int roundConfine;
	/** �Զ�ս������б� */
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
	 * ��ʼ������
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
	 * ��ȡBOSS���Ѫ��
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
	 * ��ȡBOSS��ǰѪ��
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
	 * ��������������˺�����ɱ���㽱��
	 * 
	 * @param bfr
	 */
	public Map<String,ArrayList<Object>> countReward(BossFightRecord bfr)
	{
		Map<String,ArrayList<Object>> rewardMap=new HashMap<String,ArrayList<Object>>();
		if(bfr.isFinished())
		{
			ArrayList<Object> finishReward=new ArrayList<Object>();
			finishReward.add(Arrays.asList(specialCardList));
			finishReward.add(specialSoul);
			finishReward.add(specialMoney);
			rewardMap.put("finishReward",finishReward);
		}
		if(bfr.getRank()<orderConfine)
		{
			ArrayList<Object> rankReward=new ArrayList<Object>();
			rankReward.add(Arrays.asList(orderCardList));
			rankReward.add(orderSoul*(orderConfine-bfr.getRank()));
			rankReward.add(orderMoney*(orderConfine-bfr.getRank()));
			rewardMap.put("rankReward",rankReward);
		}
		if(bfr.getTotalDamage()>0)
		{
			ArrayList<Object> damageReward=new ArrayList<Object>();
			damageReward.add(Arrays.asList(normalCardList));
			damageReward.add(normalSoul*bfr.getTotalDamage());
			damageReward.add(normalMoney*bfr.getTotalDamage());
			rewardMap.put("rankReward",damageReward);
		}
		return rewardMap;
	}

	/**
	 * ��ȡ�Զ���������
	 * 
	 * @return
	 */
	public ArrayList<Object> countReward()
	{
		ArrayList<Object> autoReward=new ArrayList<Object>();
		autoReward.add(Arrays.asList(autoCardList));
		autoReward.add(autoSoul);
		autoReward.add(autoMoney);
		return autoReward;
	}
	/***
	 * ��������
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
	 * ��������
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
