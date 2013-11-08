package com.cambrian.dfhm.rankings.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：卡牌排名信息
 * @author：Zmk
 * 
 */
public class CardRankInfo
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 玩家姓名 */
	private String playerName;
	/** 卡牌名字 */
	private String cardName;
	/** 卡牌UID */
	private int cardUid;
	/** 战斗力 */
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
	/** 重写的比较方法，用于合并列表时候的处理 */
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
	
	/** 序列化 前台 写 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeUTF(cardName);
		data.writeInt(info);
	}
	
	/** 序列化DC 写 */
	public void dbByteswrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeUTF(cardName);
		data.writeInt(cardUid);
		data.writeInt(info);
	}
	/** 序列化 DC 读 */
	public void dbBytesRead(ByteBuffer data)
	{
		this.playerName = data.readUTF();
		this.cardName = data.readUTF();
		this.cardUid = data.readInt();
		this.info = data.readInt();
	}
}
