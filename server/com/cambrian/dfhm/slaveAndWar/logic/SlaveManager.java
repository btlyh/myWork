package com.cambrian.dfhm.slaveAndWar.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
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
	private static SlaveManager instance=new SlaveManager();

	/* static methods */
	public static SlaveManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** 内存数据访问对象 */
	private DataServer ds;
	/** 当壕DB访问对象 */
	private SlaveAndWarDao dao;

	/* constructors */

	/* properties */
	public void setDs(DataServer ds)
	{
		instance.ds=ds;
	}
	public void setSawd(SlaveAndWarDao dao)
	{
		instance.dao=dao;
	}
	/* init start */

	/* methods */

	/**
	 * 攻击敌人
	 * 
	 * @param player 玩家对象
	 * @param tarPlayerId 目标玩家ID
	 * @return 返回战斗数据
	 */
	public List<Integer> attEnemy(Player player,int tarPlayerId)
	{
		Map<String,Object> mapResult=checkAttEnemy(player,tarPlayerId);
		String error=(String)mapResult.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Player tarPlayer=(Player)mapResult.get("tarPlayer");

		return null;
	}

	/**
	 * 战斗胜利后的身份改变和处理
	 * 
	 * @param attPlayer 攻击玩家
	 * @param defPlayer 防守玩家
	 */
	private void changeGradeForWin(Player attPlayer,Player defPlayer)
	{
		switch(attPlayer.getIdentity().getGrade())
		{
			case Identity.FREEMAN:

				break;
			case Identity.OWNER:

				break;
			case Identity.SLAVE:

				break;
			default:
				break;
		}
	}

	/**
	 * 土豪胜利
	 * 
	 * @param attPlayer
	 * @param defPlayer
	 */
	private void ownerWin(Player attPlayer,Player defPlayer)
	{
		switch(defPlayer.getIdentity().getGrade())
		{
			case Identity.FREEMAN:
				
				break;

			case Identity.OWNER:
				
				break;
			default:
				break;
		}
	}

	/**
	 * 检测攻击敌人
	 * @param player 玩家对象
	 * @param tarPlayerId 目标玩家ID
	 * @return 返回检测结果信息
	 */
	private Map<String,Object> checkAttEnemy(Player player,int tarPlayerId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2104);
			return mapInfo;
		}
		if(player.getIdentity().getGrade()!=Identity.FREEMAN
			&&player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2101);
			return mapInfo;
		}
		if(player.formation.isEmpty())
		{
			mapInfo.put("error",Lang.F2103);
			return mapInfo;
		}
		Player tarPlayer=getTarPlayer(tarPlayerId);
		if(tarPlayer.getIdentity().getGrade()==Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2102);
			return mapInfo;
		}
		if(tarPlayer.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2105);
			return mapInfo;
		}
		mapInfo.put("tarPlayer",tarPlayer);
		mapInfo.put("error",null);
		return mapInfo;
	}
	/**
	 * 获取敌人列表
	 * 
	 * @return
	 */
	public List<Player> getEnemy(Player player)
	{
		String error=checkGetEnemy(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		List<Player> enemyList=new ArrayList<Player>();
		getIdentity(player,GameCFG.getErrorValue(),enemyList);
		return enemyList;
	}

	/**
	 * 检测获取敌人列表
	 * 
	 * @return
	 */
	private String checkGetEnemy(Player player)
	{
		if(player.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			return Lang.F2104;
		}
		if(player.getIdentity().getGrade()!=Identity.OWNER
			&&player.getIdentity().getGrade()!=Identity.FREEMAN)
		{
			return Lang.F2101;
		}
		return null;
	}

	/**
	 * 根据条件获取敌人列表
	 * 
	 * @param player 玩家对象
	 * @param errorValue 误差值
	 * @param enemyList 敌人列表
	 * @return
	 */
	private void getIdentity(Player player,int errorValue,
		List<Player> enemyList)
	{
		if(enemyList.size()==GameCFG.getEnemySize()
			||errorValue==GameCFG.getEnemySize()*GameCFG.getMatchTimes())
		{
			return;
		}
		Session[] sessions=ds.getSessionMap().getSessions().clone();
		List<Session> sessionList=Arrays.asList(sessions);
		List<Player> rmPlayers=new ArrayList<Player>();
		for(Session session:sessionList)
		{
			if(session!=null)
			{
				rmPlayers.add(((Player)session.getSource()));
			}
		}
		List<Player> players=dao.getAllPlayer();
		players.removeAll(rmPlayers);
		getIdentityForRAM(sessionList,player,enemyList,errorValue);
		getIdentityForDB(players,player,enemyList,errorValue);
		errorValue+=GameCFG.getErrorValue();
		getIdentity(player,errorValue,enemyList);
	}

	/**
	 * 从内存中尽量获取最多的敌人
	 * 
	 * @param sessionList 内存会话列表
	 * @param player 玩家对象
	 * @param enemyList 敌人列表
	 * @param errorValue 误差值
	 * @return
	 */
	private void getIdentityForRAM(List<Session> sessionList,Player player,
		List<Player> enemyList,int errorValue)
	{
		while(sessionList.size()>0)
		{
			int index=MathKit.randomValue(0,sessionList.size());
			Session session=sessionList.get(index);
			if(session!=null)
			{
				sessionList.remove(index);
				Player tarPlayer=(Player)session.getSource();
				if(Math
					.abs(tarPlayer.getFightPoint()-player.getFightPoint())<=errorValue
					&&tarPlayer.getIdentity().getGrade()!=Identity.SLAVE)
				{
					enemyList.add(tarPlayer);
					if(enemyList.size()==GameCFG.getEnemySize())
					{
						return;
					}
				}
			}
		}

	}
	/**
	 * 从数据库中尽量获取最多的敌人
	 * 
	 * @param players 数据库玩家列表
	 * @param player 玩家对象
	 * @param enemyList 敌人列表
	 * @param errorValue 误差值 从数据库中尽量获取最多的敌人
	 */
	private void getIdentityForDB(List<Player> players,Player player,
		List<Player> enemyList,int errorValue)
	{
		while(players.size()>0)
		{
			int index=MathKit.randomValue(0,players.size());
			Player tarPlayer=players.get(index);
			players.remove(index);
			if(Math.abs(tarPlayer.getFightPoint()-player.getFightPoint())<=errorValue
				&&tarPlayer.getIdentity().getGrade()!=Identity.SLAVE)
			{
				enemyList.add(tarPlayer);
				if(enemyList.size()==GameCFG.getEnemySize())
				{
					return;
				}
			}
		}
	}

	/**
	 * 获取目标玩家对象
	 * 
	 * @param userId
	 * @return 返回目标玩家对象
	 */
	private Player getTarPlayer(int userId)
	{
		Session session=ds.getSession(userId);
		if(session!=null)
		{
			return (Player)session.getSource();
		}
		else
		{
			return dao.getPlayer(userId);
		}
	}
}
