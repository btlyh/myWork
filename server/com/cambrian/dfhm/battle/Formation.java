package com.cambrian.dfhm.battle;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵����
 * 
 * @author��Sebastian
 */
public class Formation
{

	/* static fields */

	/* static methods */

	/* fields */
	BattleCard[] battleCrad;

	/* constructors */
	public Formation()
	{
		battleCrad=new BattleCard[5];
	}

	/* properties */

	/* init start */

	/* methods */
	/** �ı�ָ������λ�Ķ��� */
	public void changeFormation(int index,BattleCard bCard)
	{
		battleCrad[index]=bCard;
	}

	public BattleCard[] getFormation()
	{
		return battleCrad;
	}

	/** ���ָ��λ�õ�������� */
	public BattleCard getBattleCard(int index)
	{
		return battleCrad[index];
	}

	/** ���л���ǰ̨ */
	public void bytesWrite(ByteBuffer data)
	{
		BattleCard bCard;
		for(int i=0;i<5;i++)
		{
			bCard=getBattleCard(i);
			if(bCard==null)
			{
				data.writeInt(-1);
			}
			else
			{
				data.writeInt(1);
				bCard.bytesWrite(data);
			}
		}
	}

	/** ���л�(��dcͨ��) */
	public void dbBytesWrite(ByteBuffer data)
	{
		System.err.println("------Formation.dbBytesWrite--------");
		System.err.println("-------battleCrad_1---------");
		BattleCard bCard;
		for(int i=0;i<5;i++)
		{
			bCard=battleCrad[i];
			if(bCard==null)
			{
				data.writeInt(-1);
			}
			else
			{
				data.writeInt(1);
				bCard.dbBytesWrite(data);
			}
		}
	}

	/** �����л�(��dcͨ��) */
	public void dbBytesRead(ByteBuffer data)
	{
		System.err.println("------Formation.dbBytesRead--------");
		if(battleCrad==null) battleCrad=new BattleCard[5];
		int temp;
		BattleCard bCard;
		for(int i=0;i<5;i++)
		{
			temp=data.readInt();
			if(temp==1)
			{
				bCard=new BattleCard();
				bCard.dbBytesRead(data);
				battleCrad[i]=bCard;
			}
		}
	}

	/**
	 * �ж��Ƿ�û�п�������
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		boolean isEmpty=true;
		for(BattleCard card:battleCrad)
		{
			if(card!=null)
			{
				isEmpty = false;
				return isEmpty;
			}
		}
		return isEmpty;
	}
}
