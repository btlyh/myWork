package com.cambrian.dfhm.rankings.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：排名信息
 * @author：Zmk
 * 
 */
public class RankInfo
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 玩家姓名 */
	private String playerName;
	/** 排名信息 */
	private int info;
	/** 通关副本的时间，用于排序 ，不发前台*/
	private int time;
	/* constructors */

	/* properties */
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	public int getInfo()
	{
		return info;
	}
	public void setInfo(int info)
	{
		this.info = info;
	}
	public int getTime()
	{
		return time;
	}
	public void setTime(int time)
	{
		this.time = time;
	}
	/* init start */

	/* methods */
	@Override
	/** 重写的比较方法，用于合并列表时候的处理 */
	public boolean equals(Object obj)
	{
		if (obj instanceof RankInfo)
		{
			RankInfo rankInfo = (RankInfo)obj;
			return playerName.equals(rankInfo.getPlayerName());
		}
		return false;
	}
	
	public String toString()
	{
		return "playerName="+playerName+"info="+info;
	}
	
	/** 序列化 前台 写 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeInt(info);
	}
	
	/** 序列化DC 写 */
	public void dbByteswrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeInt(info);
		data.writeInt(time);
	}
	/** 序列化 DC 读 */
	public void dbBytesRead(ByteBuffer data)
	{
		this.playerName = data.readUTF();
		this.info = data.readInt();
		this.time = data.readInt();
	}
}
