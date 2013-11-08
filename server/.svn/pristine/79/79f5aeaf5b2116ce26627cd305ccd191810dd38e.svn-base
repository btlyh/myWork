package com.cambrian.dfhm.friend.entity;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：好友列表
 * 
 * @version 1.0
 * @date 2013-5-29
 * @author maxw<woldits@qq.com>
 */
public class FriendInfo
{

	/* static fields */
	/** 好友上限 */
	public static final int MAX_FRIENDLIST_SIZE=100;
	/** 类型 1,好友列表 0,申请列表 */
	public static final int TYPE_FRIEND=1,TYPE_APPLY=0;
	/* static methods */

	/* fields */
	/** 好友列表 */
	private ArrayList<Integer> friendList = new ArrayList<Integer>();
	/** 申请列表 */
	private ArrayList<Integer> applyList = new ArrayList<Integer>();

	/* constructors */

	/* properties */
	public synchronized void addFriendList(int id)
	{
		friendList.add(id);
	}

	public synchronized void removeFriendList(int id)
	{
		friendList.remove(new Integer(id));
	}

	public synchronized void addApplyList(int id)
	{
		applyList.add(id);
	}

	public synchronized void removeApplyList(int id)
	{
		applyList.remove(new Integer(id));
	}

	public ArrayList<Integer> getFriendList()
	{
		return friendList;
	}

	public void setFriendList(ArrayList<Integer> friendList)
	{
		this.friendList=friendList;
	}

	public ArrayList<Integer> getApplyList()
	{
		return applyList;
	}

	public void setApplyList(ArrayList<Integer> applyList)
	{
		this.applyList=applyList;
	}
	/* init start */

	/* methods */
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(friendList.size());
		for(Integer integer:friendList)
		{
			data.writeInt(integer);
		}
		data.writeInt(applyList.size());
		for(Integer integer:applyList)
		{
			data.writeInt(integer);
		}

	}
	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		int len=data.readInt();
		for(int i=0;i<len;i++)
		{
			friendList.add(data.readInt());
		}
		len=data.readInt();
		for(int i=0;i<len;i++)
		{
			applyList.add(data.readInt());
		}
	}
	/* common methods */

	/* inner class */

}
