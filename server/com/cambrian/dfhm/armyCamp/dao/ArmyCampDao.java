package com.cambrian.dfhm.armyCamp.dao;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.StringField;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.dfhm.armyCamp.entity.ArmyCamp;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * 类说明：军帐数据访问类
 * @author：Zmk
 * 
 */
public class ArmyCampDao
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
	 * 获取军帐信息
	 * 
	 * @param userId 玩家UID
	 * @return
	 */
	public ArmyCamp getArmyCamp(int userId)
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("armyCamp",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		ArmyCamp armyCamp=new ArmyCamp();
		armyCamp.dbBytesRead(new ByteBuffer(bytes));
		return armyCamp;
	}

	/**
	 * 存储军帐信息
	 * 
	 * @param friendInfo 好友信息对象
	 * @param userId 用户id
	 */
	public void setArmyCamp(ArmyCamp armyCamp,int userId)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		armyCamp.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("armyCamp",data.toArray());
		int i=DBKit.set("player_info",cm,FieldKit.create("userId",userId),
			fields);
		System.out.println(i);
	}
	
	/**
	 * 获取背包信息
	 * 
	 * @param userId 玩家UID
	 * @return
	 */
	public CardBag getCardBag(int userId)
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("cardBag",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		CardBag cardBag=new CardBag();
		cardBag.dbBytesRead(new ByteBuffer(bytes));
		return cardBag;
	}
	/**
	 * 存储背包信息
	 * 
	 * @param friendInfo 好友信息对象
	 * @param userId 用户id
	 */
	public void setCardBag(CardBag cardBag,int userId)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		cardBag.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("cardBag",data.toArray());
		int i=DBKit.set("player_info",cm,FieldKit.create("userId",userId),
			fields);
		System.out.println(i);
	}
	/**获取玩家对象 */
	public Player getPlayer(String name, Session session)
	{
		if (session != null)
			return (Player)session.getSource();
		int userId = getUserIdByName(name, session);
		Field[] array=new Field[2];
		array[0]=FieldKit.create("nickname",(String)null);
		array[1]=FieldKit.create("vipLevel",0);
		Fields fields=new Fields(array);
		DBKit.get("player",cm,FieldKit.create("userId",userId),fields);

		Player player=new Player();
		player.setUserId(userId);
		player.setNickname(((StringField)array[0]).value);
		
		array = new Field[2];
		array[0]=FieldKit.create("formation", (byte[])null);
		array[1]=FieldKit.create("cardBag", (byte[])null);
		fields = new Fields(array);
		DBKit.get("player_info", cm, FieldKit.create("userId", userId), fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		Formation formation = new Formation();
		if (bytes != null)
			formation.dbBytesRead(new ByteBuffer(bytes));
		else return null;
		player.formation = formation;
		bytes=((ByteArrayField)array[1]).value;
		CardBag cardBag = new CardBag();
		if (bytes != null)
			cardBag.dbBytesRead(new ByteBuffer(bytes));
		else return null;
		player.setCardBag(cardBag);
		return player;
	}
}
