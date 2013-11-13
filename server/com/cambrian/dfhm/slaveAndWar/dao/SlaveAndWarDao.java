package com.cambrian.dfhm.slaveAndWar.dao;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.common.sql.SqlKit;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明:当壕数据访问类
 * 
 * @author：LazyBear
 */
public class SlaveAndWarDao
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
	/** 获取数据库中所有玩家uid */
	private List<Integer> getAllPlayerId()
	{
		String sql="select userid from player";
		Fields[] resultFields=SqlKit.querys(cm,sql);
		List<Integer> userIdList=new ArrayList<Integer>();
		for(Fields fields:resultFields)
		{
			userIdList.add(((IntField)(fields.get("userid"))).value);
		}
		return userIdList;
	}

	/** 获取数据库所有玩家对象 */
	public List<Player> getAllPlayer()
	{
		List<Integer> userIdList=getAllPlayerId();
		List<Player> playerList=new ArrayList<Player>();
		for(Integer integer:userIdList)
		{
			Player player=getPlayer(integer);
			playerList.add(player);
		}
		return playerList;
	}

	/** 获取玩家对象 */
	public Player getPlayer(int userId)
	{
		Field[] array=new Field[3];
		array[0]=FieldKit.create("identity",(byte[])null);
		array[1]=FieldKit.create("cardBag",(byte[])null);
		array[2]=FieldKit.create("formation",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		Player player=new Player();
		player.setUserId(userId);
		byte[] bytes=((ByteArrayField)array[0]).value;
		player.getIdentity().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[1]).value;
		player.getCardBag().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[2]).value;
		player.formation.dbBytesRead(new ByteBuffer(bytes));

		return player;
	}

	/** 存储玩家信息 */
	public void savePlayerVar(Player player)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		player.getIdentity().dbBytesWrite(data);
		array[0]=FieldKit.create("identity",data.toArray());
		DBKit.set("player_info",cm,FieldKit.create("userid",
			(int)player.getUserId()),new Fields(array));
	}
}
