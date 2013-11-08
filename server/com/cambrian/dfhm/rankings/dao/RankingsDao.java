package com.cambrian.dfhm.rankings.dao;

import java.util.ArrayList;
import java.util.List;

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
import com.cambrian.common.sql.SqlKit;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.rankings.entity.Rankings;

/**
 * 类说明：排行榜数据访问类
 * @author：Zmk
 * 
 */
public class RankingsDao
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
	/** 获取排行信息 */
	public Rankings readRankings()
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("rankings",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("rankings",cm,FieldKit.create("rankingsId",1),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		if (bytes != null)
		{
			Rankings rankings = new Rankings();
			rankings.dbBytesRead(new ByteBuffer(bytes));
			return rankings;
		}
		return null;
	}
	/** 存储排行信息 */
	public void setRankings(Rankings rankings)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		rankings.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("rankings",data.toArray());
		int i=DBKit.set("rankings",cm,FieldKit.create("rankingsID",1),
			fields);
		System.out.println("排行信息存储-------"+i);
	}
	
	/** 获取数据库中所有玩家uid */
	public List<Integer> getAllPlayerId()
	{
		String sql = "select userid from player";
		Fields[] resultFields = SqlKit.querys(cm, sql);
		List<Integer> userIdList = new ArrayList<Integer>();
		for (Fields fields : resultFields)
		{
			userIdList.add(((IntField)(fields.get("userid"))).value);
		}
		return userIdList;
	}
	
	/** 获取玩家对象 */
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
		CardBag cardBag = getCardBag(userId);
		player.setCardBag(cardBag);
		return player;
	}
	/** 获取背包信息 */
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
	
	/** 获取数据库所有玩家对象 */
	public List<Player> getAllPlayer()
	{
		List<Integer> userIdList = getAllPlayerId();
		List<Player> playerList = new ArrayList<Player>();
		for (Integer integer : userIdList)
		{
			Player player = getPlayer(integer);
			playerList.add(player);
		}
		return playerList;
	}
}
