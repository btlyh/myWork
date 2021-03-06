package com.cambrian.dfhm.friend.dao;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.LongField;
import com.cambrian.common.field.StringField;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.friend.entity.FriendInfo;
import com.cambrian.game.Session;

/**
 * 类说明：好友数据访问类
 * 
 * @author：LazyBear
 */
public class FriendDao
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 连接管理器 */
	ConnectionManager cm;

	/* constructors */

	/* properties */
	public void setConnectionManager(ConnectionManager cm)
	{
		this.cm=cm;
	}
	/* init start */

	/* methods */

	/**
	 * 根据玩家昵称查询userid
	 * 
	 * @param name 玩家昵称
	 * @return
	 */
	public int getUserIdByName(String name,Session session)
	{
		int userId=0;
		if(session!=null)
		{
			Player player=(Player)session.getSource();
			userId=(int)player.getUserId();
		}
		else
		{
			Field[] array=new Field[1];
			array[0]=FieldKit.create("userId",0);
			Fields fields=new Fields(array);
			int i=DBKit.get("player",cm,FieldKit.create("nickname",name),
				fields);
			if(i==DBKit.OK)
			{
				userId=((IntField)array[0]).value;
			}
		}
		return userId;
	}
	/**
	 * 获取好友信息
	 * 
	 * @param userId 玩家UID
	 * @return
	 */
	public FriendInfo getFriendInfo(int userId)
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("friendInfo",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		FriendInfo friendInfo=new FriendInfo();
		friendInfo.dbBytesRead(new ByteBuffer(bytes));
		return friendInfo;
	}

	/**
	 * 存储好友信息
	 * 
	 * @param friendInfo 好友信息对象
	 * @param userId 用户id
	 */
	public void setFriendInfo(FriendInfo friendInfo,int userId)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		friendInfo.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("friendInfo",data.toArray());
		int i=DBKit.set("player_info",cm,FieldKit.create("userId",userId),
			fields);
		System.out.println(i);
	}

	/**
	 * 获取玩家对象
	 * 
	 * @param userId
	 * @return
	 */
	public Player getPlayer(int userId)
	{
		Field[] array=new Field[3];
		array[0]=FieldKit.create("nickname",(String)null);
		array[1]=FieldKit.create("vipLevel",0);
		array[2]=FieldKit.create("logoutTime",0L);
		Fields fields=new Fields(array);
		DBKit.get("player",cm,FieldKit.create("userId",userId),fields);

		Player player=new Player();
		player.setUserId(userId);
		player.setNickname(((StringField)array[0]).value);
		player.setVipLevel(((IntField)array[1]).value);
		player.setLogoutTime(((LongField)array[2]).value);
		return player;
	}
}
