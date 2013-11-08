package com.cambrian.dfhm.rankings.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵��������������Ϣ
 * @author��Zmk
 * 
 */
public class CardRankInfo
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ������� */
	private String playerName;
	/** �������� */
	private String cardName;
	/** ����UID */
	private int cardUid;
	/** ս���� */
	private int info;
	/* constructors */
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	public String getCardName()
	{
		return cardName;
	}
	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}
	public int getCardUid()
	{
		return cardUid;
	}
	public void setCardUid(int cardUid)
	{
		this.cardUid = cardUid;
	}
	public int getInfo()
	{
		return info;
	}
	public void setInfo(int info)
	{
		this.info = info;
	}
	/* properties */

	/* init start */
	
	/* methods */
	@Override
	/** ��д�ıȽϷ��������ںϲ��б�ʱ��Ĵ��� */
	public boolean equals(Object obj)
	{
		if (obj instanceof CardRankInfo)
		{
			CardRankInfo cardRankInfo = (CardRankInfo)obj;
			return playerName.equals(cardRankInfo.getPlayerName());
		}
		return false;
	}
	
	public String toString()
	{
		return "playerName="+playerName+"  cardName="+cardName+"  cardUid="+cardUid+"  info="+info;
	}
	
	/** ���л� ǰ̨ д */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeUTF(cardName);
		data.writeInt(info);
	}
	
	/** ���л�DC д */
	public void dbByteswrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeUTF(cardName);
		data.writeInt(cardUid);
		data.writeInt(info);
	}
	/** ���л� DC �� */
	public void dbBytesRead(ByteBuffer data)
	{
		this.playerName = data.readUTF();
		this.cardName = data.readUTF();
		this.cardUid = data.readInt();
		this.info = data.readInt();
	}
}
