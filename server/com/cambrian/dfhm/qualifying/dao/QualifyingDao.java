package com.cambrian.dfhm.qualifying.dao;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.StringField;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.common.entity.PlayerInfo;
import com.cambrian.dfhm.qualifying.entity.Qualifying;
import com.cambrian.game.Session;

/**
 * 类说明：排位赛数据处理类
 * @author：Zmk
 * 
 */
public class QualifyingDao
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
	
	/**获取玩家对象 */
	public Player getPlayer(String name, Session session)
	{
		int userId = getUserIdByName(name, session);
		Field[] array=new Field[2];
		array[0]=FieldKit.create("nickname",(String)null);
		array[1]=FieldKit.create("vipLevel",0);
		Fields fields=new Fields(array);
		DBKit.get("player",cm,FieldKit.create("userId",userId),fields);

		Player player=new Player();
		player.setUserId(userId);
		player.setNickname(((StringField)array[0]).value);
		player.setVipLevel(((IntField)array[1]).value);
		
		array = new Field[3];
		array[0]=FieldKit.create("playerInfo", (byte[])null);
		array[1]=FieldKit.create("cardBag", (byte[])null);
		array[2]=FieldKit.create("formation", (byte[])null);
		fields = new Fields(array);
		DBKit.get("player_info", cm, FieldKit.create("userId", userId), fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		PlayerInfo playerInfo = new PlayerInfo();
		if (bytes != null)
			playerInfo.dbBytesRead(new ByteBuffer(bytes));
		player.setPlayerInfo(playerInfo);
		bytes = ((ByteArrayField)array[1]).value;
		CardBag cardBag = new CardBag();
		if (bytes != null)
			cardBag.dbBytesRead(new ByteBuffer(bytes));
		player.setCardBag(cardBag);
		bytes = ((ByteArrayField)array[2]).value;
		Formation formation = new Formation();
		if (bytes != null)
			formation.dbBytesRead(new ByteBuffer(bytes));
		player.formation = formation;
		return player;
	}
	/** 获取排位赛 */
	public Qualifying getQualifying()
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("qualifying",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("rankings",cm,FieldKit.create("rankingsId",1),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		if (bytes != null)
		{
			Qualifying qualifying = new Qualifying();
			qualifying.dbBytesRead(new ByteBuffer(bytes));
			return qualifying;
		}
		return null;
	}
	
	/** 存储排位赛 */
	public void setQualifying(Qualifying qualifying)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		qualifying.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("qualifying",data.toArray());
		int i=DBKit.set("rankings",cm,FieldKit.create("rankingsID",1),
			fields);
		System.out.println("排位赛存储-------"+i);
	}
}
