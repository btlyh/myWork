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
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.dfhm.slaveAndWar.entity.Information;
import com.cambrian.dfhm.slaveAndWar.entity.Slave;
import com.cambrian.dfhm.slaveAndWar.notice.EventMessageNotice;
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
	/** 事件消息推送对象 */
	private EventMessageNotice emn;

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
	public void setEMN(EventMessageNotice emn)
	{
		instance.emn=emn;
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
		List<Integer> resultList=new ArrayList<Integer>();
		boolean isWin=false;
		player.getIdentity().inAttTimes();
		if(tarPlayer.formation.isEmpty())
		{
			resultList.add(-1);// 对方无上阵卡牌。战斗数据第一位为-1表示战斗直接胜利
			isWin=true;
		}
		else
		{
			BattleScene scene=new BattleScene();
			BattleCard[] att=player.formation.getFormation();
			BattleCard[] def=tarPlayer.formation.getFormation();
			battleInit(att,def);
			scene.setMaxRound(30);
			scene.start(att,def,BattleScene.FIGHT_NORMAL);
			scene.getRecord().set(0,scene.getStep());
			int win=scene.getRecord().get(scene.getRecord().size()-1);
			if(win==1)
			{
				isWin=true;
			}
			resultList=scene.getRecord();
		}
		fightEndHandle(player,tarPlayer,isWin);
		return resultList;
	}
	/**
	 * 普通战斗结束后处理
	 * 
	 * @param attPlayer 攻击玩家
	 * @param defPlayer 防守玩家
	 */
	private void fightEndHandle(Player attPlayer,Player defPlayer,
		boolean isWin)
	{
		int eventType=0;
		Slave slave=null;
		Player attachPlayer=null;
		switch(defPlayer.getIdentity().getGrade())
		{
			case Identity.FREEMAN:
				if(isWin)
				{
					slave=defPlayer.becomeSlave((int)attPlayer.getUserId());
					attPlayer.getIdentity().addSlave(slave);
				}
				eventType=Information.EVENT_FIGHT_FREE;
				break;
			case Identity.OWNER:
				if(isWin)
				{
					slave=defPlayer.getIdentity().cutSlave();
					attPlayer.getIdentity().addSlave(slave);
					attachPlayer=getTarPlayer(slave.getUserId());
					attachPlayer.getIdentity().setOwnerId(
						(int)attPlayer.getUserId());
				}
				eventType=Information.EVENT_FIGHT_OWNER;
				break;
			default:
				break;
		}
		recordInformation(isWin,attPlayer,defPlayer,attachPlayer,0,eventType);
	}
	/**
	 * 检测攻击敌人
	 * 
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
		if(player.getIdentity().getAttTimes()>=GameCFG.getAttConfine())
		{
			mapInfo.put("error",Lang.F2106);
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
			int index=MathKit.randomValue(0,sessionList.size()-1);
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
			int index=MathKit.randomValue(0,players.size()-1);
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

	/**
	 * 战斗前初始化
	 * 
	 * @param att
	 * @param def
	 * @param isGlobalBoss 是否攻击世界BOSS
	 */
	private void battleInit(BattleCard[] att,BattleCard[] def)
	{
		for(BattleCard battleCard:att)
		{
			if(battleCard!=null)
			{
				battleCard.setSide(1);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
		for(BattleCard battleCard:def)
		{
			if(battleCard!=null)
			{
				battleCard.setSide(2);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
	}

	/**
	 * 记录消息并进行推送
	 * 
	 * @param isSuccess 事件是否成功
	 * @param myPlayer 我的玩家对象
	 * @param tarPlayer 目标玩家对象
	 * @param attachPlayer 附加玩家对象
	 * @param attachValue 附加值
	 * @param eventType 事件类型
	 */
	private void recordInformation(boolean isSuccess,Player myPlayer,
		Player tarPlayer,Player attachPlayer,int attachValue,int eventType)
	{
		Information information=null;
		switch(eventType)
		{
			case Information.EVENT_FIGHT_FREE:
				information=new Information(eventType,isSuccess,
					myPlayer.getNickname(),tarPlayer.getNickname(),null,
					attachValue);
				myPlayer.getIdentity().addInformation(information);
				sendInformation(tarPlayer,information);
				break;
			case Information.EVENT_FIGHT_OWNER:
				if(isSuccess)
				{
					information=new Information(eventType,isSuccess,
						myPlayer.getNickname(),tarPlayer.getNickname(),
						attachPlayer.getNickname(),attachValue);
					sendInformation(tarPlayer,attachPlayer,information);
				}
				else
				{
					information=new Information(eventType,isSuccess,
						myPlayer.getNickname(),tarPlayer.getNickname(),null,
						attachValue);
					sendInformation(tarPlayer,information);
				}
				myPlayer.getIdentity().addInformation(information);

			default:
				break;
		}
	}
	/**
	 * 给玩家发送推送并记录信息(只有主动玩家和被动玩家时)
	 * 
	 * @param information 信息对象
	 * @param tarPlayer 目标玩家对象
	 */
	private void sendInformation(Player tarPlayer,Information information)
	{
		tarPlayer.getIdentity().addInformation(information);
		Session session=ds.getSession((int)tarPlayer.getUserId());
		if(session!=null)
		{
			emn.send(session,new Object[]{information});
		}
		else
		{
			dao.savePlayerVar(tarPlayer);
		}
	}

	/**
	 * 给玩家发送推送并记录信息(当有主动玩家、被动玩家和附属玩家时)
	 * 
	 * @param tarPlayer 被动玩家对象
	 * @param attachPlayer 附属玩家
	 * @param information 信息对象
	 */
	private void sendInformation(Player tarPlayer,Player attachPlayer,
		Information information)
	{
		tarPlayer.getIdentity().addInformation(information);
		Session session=ds.getSession((int)tarPlayer.getUserId());
		if(session!=null)
		{
			emn.send(session,new Object[]{information});
		}
		else
		{
			dao.savePlayerVar(tarPlayer);
		}
		attachPlayer.getIdentity().addInformation(information);
		Session session_=ds.getSession((int)attachPlayer.getUserId());
		if(session_!=null)
		{
			emn.send(session_,new Object[]{information});
		}
		else
		{
			dao.savePlayerVar(attachPlayer);
		}
	}
}
