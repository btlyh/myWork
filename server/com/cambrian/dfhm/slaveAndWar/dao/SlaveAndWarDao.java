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
 * ��˵��:�������ݷ�����
 * 
 * @author��LazyBear
 */
public class SlaveAndWarDao
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ���ӹ����� */
	ConnectionManager cm;

	/* constructors */

	/* properties */
	public void setConnectionManager(ConnectionManager cm)
	{
		this.cm=cm;
	}
	/* init start */

	/* methods */
	/** ��ȡ���ݿ����������uid */
	public List<Integer> getAllPlayerId()
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

	/** ��ȡ���ݿ�������Ҷ��� */
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

	/** ��ȡ��Ҷ��� */
	public Player getPlayer(int userId)
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("identity",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		Player player=new Player();
		player.setUserId(userId);
		byte[] bytes=((ByteArrayField)array[0]).value;
		player.getIdentity().dbBytesRead(new ByteBuffer(bytes));
		
		array=new Field[1];
		array[0]=FieldKit.create("money",player.getMoney());
		fields=new Fields(array);
		DBKit.get("player",cm,FieldKit.create("userId",userId),fields);
		player.setMoney(((IntField)array[0]).value);
		return player;
	}

	/** �洢�����Ϣ */
	public void savePlayerVar(Player player)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		player.getIdentity().dbBytesWrite(data);
		array[0]=FieldKit.create("identity",data.toArray());
		DBKit.set("player_info",cm,FieldKit.create("userid",
			(int)player.getUserId()),new Fields(array));
		
		array=new Field[1];
		array[0]=FieldKit.create("money",player.getMoney());
		DBKit.set("player",cm,FieldKit.create("userid",
			(int)player.getUserId()),new Fields(array));
	}
}
