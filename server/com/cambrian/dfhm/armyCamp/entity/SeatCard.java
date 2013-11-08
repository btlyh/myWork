package com.cambrian.dfhm.armyCamp.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵������λ�ϵĿ���
 * 
 * @author��Zmk
 * 
 */
public class SeatCard
{

	/* static fields */

	/* static methods */

	/* fields */
	/** λ�ñ�� */
	private int seatId;
	/** ����sid */
	private int cardSid;
	/** ����Uid */
	private int cardUid;
	/** ����������� */
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

	/** ���л� ��ǰ̨ͨ�� */
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
	
	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(seatId);
		data.writeInt(cardSid);
		data.writeInt(cardUid);
		data.writeUTF(ownerName);
	}
	
	/** ���л� ��DCͨ�� �� */
	public void dbBytesRead(ByteBuffer data)
	{
		this.seatId = data.readInt();
		this.cardSid = data.readInt();
		this.cardUid = data.readInt();
		this.ownerName = data.readUTF();
	}
}