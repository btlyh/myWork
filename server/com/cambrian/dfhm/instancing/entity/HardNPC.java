package com.cambrian.dfhm.instancing.entity;

import java.util.ArrayList;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleRecord;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.monster.Monster;

/**
 * ��˵����
 * 
 * @author��LazyBear
 */
public class HardNPC extends NPC
{

	/* static fields */
	/** ��սNPC ��ɱ��׼ */
	private static final int KILLED=-1;
	/* static methods */

	/* fields */
	/** ָ���佫 */
	private int assignCardId;
	/** �Ƿ���Ҫ�佫ȫ���� 1�� 0�� */
	private int isAllLive;
	/** ָ���غ��� */
	private int assignRound;
	/** ָ���佫���� */
	private int cardCount;
	/** һ��������ɵ��˺� */
	private int assignDamage;
	/** �����齱��SID */
	private int luckBoxSid;

	/* constructors */

	/* properties */
	public int getAssignCardId()
	{
		return assignCardId;
	}

	public void setAssignCardId(int assignCardId)
	{
		this.assignCardId=assignCardId;
	}

	public int getIsAllLive()
	{
		return isAllLive;
	}

	public void setIsAllLive(int isAllLive)
	{
		this.isAllLive=isAllLive;
	}

	public int getAssignRound()
	{
		return assignRound;
	}

	public void setAssignRound(int assignRound)
	{
		this.assignRound=assignRound;
	}

	public int getCardCount()
	{
		return cardCount;
	}

	public void setCardCount(int cardCount)
	{
		this.cardCount=cardCount;
	}

	public int getAssignDamage()
	{
		return assignDamage;
	}

	public void setAssignDamage(int assignDamage)
	{
		this.assignDamage=assignDamage;
	}
	/* init start */

	/* methods */
	@Override
	public String checkAttNpc(Player player,AttRecord checkRecord)
	{
		if(player.getCurIndexForHardNPC()<getSid())
		{
			return Lang.F1402;
		}
		if(checkRecord!=null)
		{
			if(checkRecord.getAttacks()==KILLED)
			{
				return Lang.F1406;
			}
		}
		return null;
	}

	@Override
	public void handleForWin(Player player)
	{
		if(getIsBoss()!=BOSS&&getNextSid()>player.getCurIndexForHardNPC())
		{
			player.setCurIndexForHardNPC(getNextSid());
		}
		else
		{
			player.getPlayerInfo().setLuckBoxSid(luckBoxSid);
		}
		AttRecord attRecord=new AttRecord(getSid(),KILLED,getType());
		player.addAttRecord(attRecord);
		player.getPlayerInfo().incrInstancingCount(1);
		player.getPlayerInfo().addHardNpc(getSid());
		if (getSid() > player.getPlayerInfo().getHighestHardNPC())
		{
			player.getPlayerInfo().setHardNPCTime(
				(int)TimeKit.nowTimeMills());
		}
	}
	@Override
	public void handleForAtt(Player player)
	{
		// TODO Auto-generated method stub
	}

	@Override
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
				card.getCurHp(),i,card.getAimType(),card.getCritRate(),
				card.getDodgeRate(),card.getAwardSid(),i,card.getSid(),card.getCritFactor());
			monsters[i]=battleCard;
		}
	}

	/** ���ָ������ */
	public boolean checkAssignCard(BattleCard[] battleCards,Player player)
	{
		if(assignCardId>0)// ָ������
		{
			for(int i=0;i<battleCards.length;i++)
			{
				if(battleCards[i]!=null)
				{
					Card card = player.getCardBag().getById(battleCards[i].getId());
					if(card.getSid()==assignCardId)
					{
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}
	/** ����Ƿ���Ҫȫ���� */
	public boolean checkIsAllLive(BattleRecord record)
	{
		if(isAllLive==1) // ȫ����
		{
			if(record.isDeath())
			{
				return false;
			}
		}
		return true;
	}
	/** ����Ƿ񳬹���ָ���غ� */
	public boolean checkAssignRound(int Round)
	{
		if(assignRound>0&&assignRound<Round)// �غ�
		{
			return false;
		}
		return true;
	}
	/** ���ָ���佫���� */
	public boolean checkCardCount(int count)
	{
		if(cardCount>0&&cardCount<count)
		{
			return false;
		}
		return true;
	}
	/** ������ָ���˺� */
	public boolean checkAssignDamage(int damage)
	{
		if(damage<assignDamage) // ָ���˺�
		{
			return false;
		}
		return true;
	}

	@Override
	public void winCondition(BattleScene scene,BattleCard[] attList,Player player)
	{
		boolean results[]={true,true,true,true,true};
		results[0]=checkAssignCard(attList,player);
		BattleRecord record=scene.getBattleRecord();
		results[1]=checkIsAllLive(record);
		results[2]=checkAssignRound(scene.getCurRound());
		int count=0;
		for(int i=0;i<attList.length;i++)
		{
			if(attList[i]!=null)
			{
				count++;
			}
		}
		results[3]=checkCardCount(count);
		results[4]=checkAssignDamage(record.getAttMax());

		ArrayList<Integer> recordList=scene.getRecord();
		for(int i=0;i<results.length;i++)
		{
			if(!results[i])
			{
				recordList.set(recordList.size()-1,-1);
				break;
			}
		}
	}
}
