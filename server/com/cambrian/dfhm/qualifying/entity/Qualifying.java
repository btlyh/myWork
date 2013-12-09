package com.cambrian.dfhm.qualifying.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵������λ����
 * 
 * @author��Zmk
 * 
 */
public class Qualifying
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ��λ������ */
	private List<String> qualifyingList = new ArrayList<String>();
	/** ���а�ǰ���� */
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
	/** ������ */
	public void addPlayer(String nickName)
	{
		if (!qualifyingList.contains(nickName))
			qualifyingList.add(nickName);
	}
	
	/** ���ָ����ҵ����� */
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

	/** ���л� DC ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		int len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			String name = data.readUTF();
			qualifyingList.add(name);
		}
	}

	/** ���л� DC �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(qualifyingList.size());
		for (String name : qualifyingList)
		{
			data.writeUTF(name);
		}
	}
}
