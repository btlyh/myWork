package com.cambrian.dfhm.qualifying.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵�������а������Ϣ
 * @author��Zmk
 * 
 */
public class QualifyingInfo implements Comparable<QualifyingInfo>
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ������� */
	private int playerRanking;
	/** ������� */
	private String playerName;
	/** ���ս���� */
	private int playerPower;
	/** �����ǿ����SID */
	private int playerCardSid;
	/** ���VIP�ȼ� */
	private int playerVipLevel;
	/* constructors */
	
	/* properties */
	public int getPlayerRanking()
	{
		return playerRanking;
	}
	public void setPlayerRanking(int playerRanking)
	{
		this.playerRanking = playerRanking;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	public int getPlayerPower()
	{
		return playerPower;
	}
	public void setPlayerPower(int playerPower)
	{
		this.playerPower = playerPower;
	}
	public int getPlayerCardSid()
	{
		return playerCardSid;
	}
	public void setPlayerCardSid(int playerCardSid)
	{
		this.playerCardSid = playerCardSid;
	}
	public int getPlayerVipLevel()
	{
		return playerVipLevel;
	}
	public void setPlayerVipLevel(int playerVipLevel)
	{
		this.playerVipLevel = playerVipLevel;
	}
	/* init start */

	/* methods */
	/** ���л� ǰ̨ д */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(playerRanking);
		data.writeUTF(playerName);
		data.writeInt(playerPower);
		data.writeInt(playerCardSid);
		data.writeInt(playerVipLevel);
	}
	public int compareTo(QualifyingInfo o)
	{
		return this.playerRanking-o.playerRanking;
	}
}
