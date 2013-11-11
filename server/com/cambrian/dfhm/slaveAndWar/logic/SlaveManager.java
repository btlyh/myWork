package com.cambrian.dfhm.slaveAndWar.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.net.www.content.text.plain;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.sql.DBKit;
import com.cambrian.common.sql.SqlKit;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：当壕功能管理器
 * 
 * @author：LazyBear
 */
public class SlaveManager
{

	/* static fields */

	/* static methods */

	/* fields */
	private DataServer ds;
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * 获取敌人列表
	 * 
	 * @return
	 */
	public List<Identity> getEnemy(Player player)
	{
		String error=checkGetEnemy(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		return null;
	}

	/**
	 * 检测获取敌人列表
	 * 
	 * @return
	 */
	private String checkGetEnemy(Player player)
	{
		if(player.getIdentity().getGrade()!=Identity.OWNER
			&&player.getIdentity().getGrade()!=Identity.FREEMAN)
		{
			return Lang.F2101;
		}
		return null;
	}

	private List<Identity> getIdentity(Player player,int errorValue)
	{
		List<Identity> enemyList=new ArrayList<Identity>();
		Session[] sessions=ds.getSessionMap().getSessions().clone();
		List<Session> sessionList=Arrays.asList(sessions);
		return null;
	}
	
	/**
	 * 从内存中尽量获取最多的敌人
	 * @param sessionList
	 * @return
	 */
	private List<Identity> getIdentityForRandom(List<Session> sessionList,Identity myIdentity)
	{
		return null;
	}
}
