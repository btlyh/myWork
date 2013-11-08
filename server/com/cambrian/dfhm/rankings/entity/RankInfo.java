package com.cambrian.dfhm.rankings.entity;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵����������Ϣ
 * @author��Zmk
 * 
 */
public class RankInfo
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ������� */
	private String playerName;
	/** ������Ϣ */
	private int info;
	/** ͨ�ظ�����ʱ�䣬�������� ������ǰ̨*/
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
	/** ��д�ıȽϷ��������ںϲ��б�ʱ��Ĵ��� */
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
	
	/** ���л� ǰ̨ д */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeInt(info);
	}
	
	/** ���л�DC д */
	public void dbByteswrite(ByteBuffer data)
	{
		data.writeUTF(playerName);
		data.writeInt(info);
		data.writeInt(time);
	}
	/** ���л� DC �� */
	public void dbBytesRead(ByteBuffer data)
	{
		this.playerName = data.readUTF();
		this.info = data.readInt();
		this.time = data.readInt();
	}
}
