package com.cambrian.dfhm.battle;

import com.cambrian.common.net.ByteBuffer;

/**
 * 阵型:
 * 
 * @author:Sebastian
 */
public class Formation implements Cloneable
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
	/** 改变阵型 */
	public void changeFormation(int index,BattleCard bCard)
	{
		battleCrad[index]=bCard;
	}

	public BattleCard[] getFormation()
	{
		return battleCrad;
	}

	/** 获取战斗卡牌 */
	public BattleCard getBattleCard(int index)
	{
		return battleCrad[index];
	}

	/** 前台序列化 */
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

	/** dc 序列化写 */
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

	/** dc 序列化读 */
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
	 * 判断阵上是否有卡牌
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
				isEmpty=false;
				return isEmpty;
			}
		}
		return isEmpty;
	}

	@Override
	public Object clone()
	{
		Formation formation=null;
		try
		{
			formation=(Formation)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		formation.battleCrad=new BattleCard[battleCrad.length];
		for(int i=0;i<battleCrad.length;i++)
		{
			if((BattleCard)battleCrad[i]!=null)
				formation.battleCrad[i]=(BattleCard)battleCrad[i].clone();
		}
		return formation;
	}
}
