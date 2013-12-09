package com.cambrian.dfhm.qualifying.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：排位赛类
 * 
 * @author：Zmk
 * 
 */
public class Qualifying
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 排位赛排行 */
	private List<String> qualifyingList = new ArrayList<String>();
	/** 排行榜前三名 */
	private List<QualifyingInfo> topList = new ArrayList<QualifyingInfo>();

	/* constructors */

	public List<String> getQualifyingList()
	{
		return qualifyingList;
	}

	public void setQualifyingList(List<String> qualifyingList)
	{
		this.qualifyingList = qualifyingList;
	}

	/* properties */

	/* init start */

	/* methods */
	/** 添加玩家 */
	public void addPlayer(String nickName)
	{
		if (!qualifyingList.contains(nickName))
			qualifyingList.add(nickName);
	}
	
	/** 获得指定玩家的排名 */
	public int getPlayerRank(String playerName)
	{
		for (int i = 0; i < qualifyingList.size(); i++)
		{
			String name = qualifyingList.get(i);
			if (name.equals(playerName))
				return i;
		}
		return -1;
	}

	public List<QualifyingInfo> getTopList()
	{
		return topList;
	}

	public void setTopList(List<QualifyingInfo> topList)
	{
		this.topList = topList;
	}

	/** 序列化 DC 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		int len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			String name = data.readUTF();
			qualifyingList.add(name);
		}
	}

	/** 序列化 DC 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(qualifyingList.size());
		for (String name : qualifyingList)
		{
			data.writeUTF(name);
		}
	}
}
