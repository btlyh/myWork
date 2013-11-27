package com.cambrian.dfhm.qualifying.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：排行榜玩家信息
 * @author：Zmk
 * 
 */
public class QualifyingInfo implements Comparable<QualifyingInfo>
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 玩家排名 */
	private int playerRanking;
	/** 玩家名字 */
	private String playerName;
	/** 玩家战斗力 */
	private int playerPower;
	/** 玩家最强卡牌SID */
	private int playerCardSid;
	/** 玩家VIP等级 */
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
	/** 序列化 前台 写 */
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
