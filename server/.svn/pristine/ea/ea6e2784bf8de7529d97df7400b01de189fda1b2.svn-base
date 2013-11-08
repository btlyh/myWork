package com.cambrian.dfhm.armyCamp.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：座位上的卡牌
 * 
 * @author：Zmk
 * 
 */
public class SeatCard
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 位置编号 */
	private int seatId;
	/** 卡牌sid */
	private int cardSid;
	/** 卡牌Uid */
	private int cardUid;
	/** 所属玩家名字 */
	private String ownerName;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	public int getSeatId()
	{
		return seatId;
	}

	public void setSeatId(int seatId)
	{
		this.seatId = seatId;
	}

	public int getCardSid()
	{
		return cardSid;
	}

	public void setCardSid(int cardSid)
	{
		this.cardSid = cardSid;
	}

	public int getCardUid()
	{
		return cardUid;
	}

	public void setCardUid(int cardUid)
	{
		this.cardUid = cardUid;
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

	/** 序列化 和前台通信 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(seatId);
		data.writeInt(cardSid);
		data.writeInt(cardUid);
		data.writeUTF(ownerName);
		System.err.println("ENTER-seatId====="+seatId);
		System.err.println("ENTER_cardSid====="+cardSid);
		System.err.println("ENTER_cardUid====="+cardUid);
		System.err.println("ENTER_ownerName====="+ownerName);
	}
	
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(seatId);
		data.writeInt(cardSid);
		data.writeInt(cardUid);
		data.writeUTF(ownerName);
	}
	
	/** 序列化 和DC通信 存 */
	public void dbBytesRead(ByteBuffer data)
	{
		this.seatId = data.readInt();
		this.cardSid = data.readInt();
		this.cardUid = data.readInt();
		this.ownerName = data.readUTF();
	}
}
