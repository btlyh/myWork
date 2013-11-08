package com.cambrian.dfhm.instancing.entity;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;

/**
 * ��˵����
 * 
 * @author��LazyBear
 */
public abstract class NPC extends Sample
{

	/* static fields */
	/** �������� 1���� 2��ս 3��Խ���ճ��� 4� */
	public static final int NORMAL=39001,HARD=39002,CROSS=39003,ACTIVITY=39004;
	/** NPC��BOSS */
	public static final int BOSS=1;
	/* static methods */

	/* fields */
	/** NPC�еĹ��￨�� */
	protected int[] battleCardList;
	/** ��ս��NPC������� */
	private int needToken;
	/** �Ƿ�ΪBOSSս 1�� 0�� */
	private int isBoss;
	/** ��һ��NPC��λ�� ���Ϊ0��ʾ��߸��� */
	private int nextSid;
	/** ��ս�����б� */
	protected BattleCard[] monsters=new BattleCard[5];
	/** NPC����(�൱�ڸ�������) */
	private int type;
	/** �غ� */
	private int round;

	/* constructors */

	/* properties */
	public int[] getBattleCardList()
	{
		return battleCardList;
	}

	public void setBattleCardList(int[] battleCardList)
	{
		this.battleCardList=battleCardList;
	}

	public int getNeedToken()
	{
		return needToken;
	}

	public void setNeedToken(int needToken)
	{
		this.needToken=needToken;
	}

	public int getIsBoss()
	{
		return isBoss;
	}

	public void setIsBoss(int isBoss)
	{
		this.isBoss=isBoss;
	}

	public int getNextSid()
	{
		return nextSid;
	}

	public void setNextSid(int nextSid)
	{
		this.nextSid=nextSid;
	}

	public BattleCard[] getMonsters()
	{
		return monsters;
	}

	public void setMonsters(BattleCard[] monsters)
	{
		for(int i=0;i<this.monsters.length;i++)
		{
			this.monsters[i]=monsters[i];
		}
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type=type;
	}
	public int getRound()
	{
		return round;
	}
	public void setRound(int round)
	{
		this.round=round;
	}

	/* init start */

	/* methods */
	/** �����ʼ�� */
	public abstract void initMonsters(String str);
	/** ����NPC֮ǰ�ļ�� */
	public abstract String checkAttNpc(Player player,AttRecord checkRecord);
	/** ����ʤ��֮��NPC�Ĵ��� */
	public abstract void handleForWin(Player player);
	/** ����֮��Ĵ��� */
	public abstract void handleForAtt(Player player);
	/** ʤ�������ж� */
	public abstract void winCondition(BattleScene scene,BattleCard[] attList);
	/** �ɷ����� */
	public void addAward(BattleScene scene,Player player)
	{
		ArrayList<Integer> list=scene.getDropAward();
		int type,value;
		scene.getRecord().add(list.size()/2);
		for(int i=0;i<list.size();i+=2)
		{
			type=list.get(i);
			value=list.get(i+1);
			scene.getRecord().add(type);
			scene.getRecord().add(value);
			if(type==1)// ��Ϸ��
			{
				player.incrMoney(value);
			}
			else if(type==2)// ���
			{
				player.incrSoul(value);
			}
			else if(type==3)// RMB
			{
				player.incrGold(value);
			}
			else
			// ����
			{
				System.err.println("sid ==="+value);
				player.getCardBag().add(value);
			}
		}
	}
}
