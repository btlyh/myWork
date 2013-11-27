package com.cambrian.dfhm.slaveAndWar.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.MathKit;
import com.cambrian.common.util.TimeKit;
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
import com.cambrian.dfhm.slaveAndWar.timer.SlaveWorkTimeTask;
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
	/** 奴隶池 */
	public List<Slave> slavePool=new ArrayList<Slave>();

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
	public void setDS(DataServer ds)
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
	 * 开启奴隶监听线程。
	 */
	public void timerStart()
	{
		Timer slaveTimer=new Timer();
		slaveTimer.schedule(new SlaveWorkTimeTask(),0,TimeKit.SEC_MILLS*10);
	}

	/**
	 * 释放奴隶
	 * 
	 * @param player 玩家对象
	 * @param slaveId 奴隶ID
	 */
	public void releaseSlave(Player player,int slaveId)
	{
		Map<String,Object> resultMap=checkReleaseSlave(player,slaveId);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Slave slave=(Slave)resultMap.get("slave");
		player.getIdentity().getSlaveList().remove(slave);
	}
	/**
	 * 检查释放奴隶
	 * 
	 * @param player 玩家对象
	 * @param slaveId 奴隶ID
	 * @return
	 */
	private Map<String,Object> checkReleaseSlave(Player player,int slaveId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2204);
			return mapInfo;
		}
		Slave slave=player.getIdentity().getSlave(slaveId);
		if(slave==null)
		{
			mapInfo.put("error",Lang.F2213);
			return mapInfo;
		}
		if(slave.getStatus()==Slave.STATUS_WORK)
		{
			mapInfo.put("error",Lang.F2218);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("slave",slave);
		return mapInfo;
	}

	/**
	 * 速去速回
	 * 
	 * @param player
	 * @param slaveId
	 */
	public void fastWork(Player player,int slaveId)
	{
		Map<String,Object> resultMap=checkFastWork(player,slaveId);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(GameCFG.getFastWorkGold());
		Slave slave=(Slave)resultMap.get("slvae");
		int money=slave.getFightPoint()*GameCFG.getWorkTime();
		player.incrMoney(money);
		Player slavePlayer=getTarPlayer(slave.getUserId());
		recordInformation(true,null,player,slavePlayer,money,
			Information.EVENT_WORK);
		if(slave.isManaged())
		{
			if(player.getIdentity().getWorkTimes()<GameCFG.getWorkConfine())
			{
				player.getIdentity().inWorkTimes();
				slave.setStartWorkTime(TimeKit.nowTimeMills());
			}
			else
			{
				slave.workEndHandle();
				slavePool.remove(slave);
			}
		}
		else
		{
			slave.workEndHandle();
			slavePool.remove(slave);
		}
	}

	/**
	 * 检查速去速回
	 * 
	 * @param player
	 * @param slaveId
	 * @return
	 */
	private Map<String,Object> checkFastWork(Player player,int slaveId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2204);
			return mapInfo;
		}
		if(player.getGold()<GameCFG.getFastWorkGold())
		{
			mapInfo.put("error",Lang.F2217);
			return mapInfo;
		}
		Slave slave=player.getIdentity().getSlave(slaveId);
		if(slave==null)
		{
			mapInfo.put("error",Lang.F2213);
			return mapInfo;
		}
		if(slave.getStatus()!=Slave.STATUS_WORK)
		{
			mapInfo.put("error",Lang.F2216);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("slave",slave);
		return mapInfo;
	}
	/**
	 * 开始托管
	 * 
	 * @param player
	 * @param slaveId
	 */
	public void managed(Player player,int slaveId)
	{
		Map<String,Object> resultMap=checkManaged(player,slaveId);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(GameCFG.getManagedGold());
		Slave slave=(Slave)resultMap.get("slvae");
		slave.setManaged(true);
	}
	/**
	 * 检查开始托管
	 * 
	 * @param player 玩家对象
	 * @param slaveId 奴隶ID
	 * @return
	 */
	private Map<String,Object> checkManaged(Player player,int slaveId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2204);
			return mapInfo;
		}
		if(player.getGold()<GameCFG.getManagedGold())
		{
			mapInfo.put("error",Lang.F2215);
			return mapInfo;
		}
		Slave slave=player.getIdentity().getSlave(slaveId);
		if(slave==null)
		{
			mapInfo.put("error",Lang.F2213);
			return mapInfo;
		}
		if(slave.getStatus()!=Slave.STATUS_WORK)
		{
			mapInfo.put("error",Lang.F2216);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("slave",slave);
		return mapInfo;
	}
	/**
	 * 开始办事
	 * 
	 * @param player 玩家对象
	 * @param slaveId 奴隶ID
	 */
	public void work(Player player,int slaveId)
	{
		Map<String,Object> resultMap=checkWork(player,slaveId);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.getIdentity().inWorkTimes();
		Slave slave=(Slave)resultMap.get("slvae");
		slave.workStartHandle();
		slavePool.add(slave);
	}

	/**
	 * 检查开始办事
	 * 
	 * @param player 玩家对象
	 * @param slaveId 奴隶ID
	 * @return
	 */
	private Map<String,Object> checkWork(Player player,int slaveId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2204);
			return mapInfo;
		}
		if(player.getIdentity().getWorkTimes()>=GameCFG.getWorkConfine())
		{
			mapInfo.put("error",Lang.F2214);
			return mapInfo;
		}
		Slave slave=player.getIdentity().getSlave(slaveId);
		if(slave==null)
		{
			mapInfo.put("error",Lang.F2213);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("slave",slave);
		return mapInfo;
	}

	/**
	 * 获取奴隶列表
	 * 
	 * @param player 玩家对象
	 * @return 返回奴隶列表
	 */
	public List<Slave> getSlaveList(Player player)
	{
		String error=checkGetSlaveList(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		return player.getIdentity().getSlaveList();
	}

	/**
	 * 检查获取奴隶列表
	 * 
	 * @param player
	 * @return
	 */
	private String checkGetSlaveList(Player player)
	{
		if(player.getIdentity().getGrade()!=Identity.OWNER)
		{
			return Lang.F2204;
		}
		return null;
	}
	/**
	 * 拯救好友
	 * 
	 * @param player 玩家对象
	 * @param friendId 好友ID
	 * @return 返回战斗数据
	 */
	public Map<String,Object> saveFriend(Player player,int friendId)
	{
		Map<String,Object> resultMap=checkSaveFriend(player,friendId);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Player friendPlayer=(Player)resultMap.get("tarPlayer");// 好友对象
		Player tarPlayer=getTarPlayer(friendPlayer.getIdentity()// 好友主人对象
			.getOwnerId());
		List<Integer> resultList=new ArrayList<Integer>();
		boolean isWin=false;
		player.getIdentity().inSaveTimes();
		resultList=pvpFight(player.formation.getFormation(),
			tarPlayer.formation.getFormation());
		int win=resultList.get(resultList.size()-1);
		if(win==1)
		{
			isWin=true;
		}
		fightEndSaveFriend(player,tarPlayer,friendPlayer,isWin);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("resultList",resultList);
		result.put("formation",tarPlayer.formation.getFormation());
		return result;
	}

	/**
	 * 检测拯救好友
	 * 
	 * @param player 玩家对象
	 * @param friendId 好友ID
	 * @return
	 */
	private Map<String,Object> checkSaveFriend(Player player,int friendId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()==Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2204);
			return mapInfo;
		}
		if(player.getIdentity().getSaveTimes()>=GameCFG.getSaveConfine())
		{
			mapInfo.put("error",Lang.F2211);
			return mapInfo;
		}
		Player tarPlayer=getTarPlayer(friendId);
		if(tarPlayer.getIdentity().getGrade()!=Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2205);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("tarPlayer",tarPlayer);
		return mapInfo;
	}
	/**
	 * 向好友求助
	 * 
	 * @param player 玩家对象
	 * @param friendId 好友ID
	 */
	public void forHelp(Player player,int friendId)
	{
		Map<String,Object> resultMap=checkForHelp(player,friendId);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.getIdentity().inHelpTimes();
		Player tarPlayer=(Player)resultMap.get("tarPlayer");
		tarPlayer.getIdentity().getHelpList().add((int)player.getUserId());
		saveTarPlayer(tarPlayer);
	}

	/**
	 * 检查好友求助
	 * 
	 * @param player
	 * @param friendId
	 * @return
	 */
	private Map<String,Object> checkForHelp(Player player,int friendId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()!=Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2207);
			return mapInfo;
		}
		Player tarPlayer=getTarPlayer(friendId);
		if(tarPlayer.getIdentity().getGrade()==Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2210);
			return mapInfo;
		}
		mapInfo.put("error",null);
		mapInfo.put("tarPlayer",tarPlayer);
		return mapInfo;
	}
	/**
	 * 获取好友求助列表
	 * 
	 * @param player
	 * @return
	 */
	public List<Player> getHelpList(Player player)
	{
		String error=checkGetEnemy(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		List<Player> helpList=new ArrayList<Player>();
		for(Integer integer:player.getIdentity().getHelpList())
		{
			Player tarPlayer=getTarPlayer(integer);
			helpList.add(tarPlayer);
		}
		return helpList;
	}
	/**
	 * 获取好友列表
	 * 
	 * @param player 玩家对象
	 * @return 返回玩家对象列表
	 */
	public List<Player> getFriendList(Player player)
	{
		String error=checkGetFriendList(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		List<Integer> friendIdList=player.getFriendInfo().getFriendList();

		List<Player> friends=new ArrayList<Player>();

		for(Integer integer:friendIdList)
		{
			Player tarPlayer=getTarPlayer(integer);
			if((int)tarPlayer.getUserId()==player.getIdentity().getOwnerId())
				continue;
			if(tarPlayer.getIdentity().getGrade()!=Identity.SLAVE)
				friends.add(tarPlayer);
		}
		return friends;
	}

	/**
	 * 检查获取好友列表
	 * 
	 * @param player
	 * @return
	 */
	private String checkGetFriendList(Player player)
	{
		if(player.getIdentity().getGrade()!=Identity.SLAVE)
		{
			return Lang.F2207;
		}
		return null;
	}

	/**
	 * 赎身
	 * 
	 * @param player 玩家对象
	 */
	public void getFree(Player player)
	{
		String error=checkGetFree(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(GameCFG.getGetFreeGold());
		Player tarPlayer=getTarPlayer(player.getIdentity().getOwnerId());
		getFreeHandle(player,tarPlayer);
	}

	/**
	 * 检查赎身
	 * 
	 * @param player 玩家对象
	 * @return 返回错误信息
	 */
	private String checkGetFree(Player player)
	{
		if(player.getIdentity().getGrade()!=Identity.SLAVE)
		{
			return Lang.F2207;
		}
		if(player.getGold()<GameCFG.getGetFreeGold())
		{
			return Lang.F2209;
		}
		return null;
	}

	/**
	 * 反抗
	 * 
	 * @param player 玩家对象
	 * @return 返回战斗数据
	 */
	public Map<String,Object> react(Player player)
	{
		String error=checkReact(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Player tarPlayer=getTarPlayer(player.getIdentity().getOwnerId());
		List<Integer> resultList=new ArrayList<Integer>();
		boolean isWin=false;
		player.getIdentity().inAttTimes();
		resultList=pvpFight(player.formation.getFormation(),
			tarPlayer.formation.getFormation());
		int win=resultList.get(resultList.size()-1);
		if(win==1)
		{
			isWin=true;
		}
		fighEndHandleReact(player,tarPlayer,isWin);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("resultList",resultList);
		result.put("formation",tarPlayer.formation.getFormation());
		return result;
	}

	/**
	 * 检查玩家反抗
	 * 
	 * @param player
	 * @return 返回错误信息
	 */
	private String checkReact(Player player)
	{
		if(player.getIdentity().getGrade()!=Identity.SLAVE)
		{
			return Lang.F2207;
		}
		if(player.getIdentity().getReactTimes()>=GameCFG.getReactConfine())
		{
			return Lang.F2208;
		}
		if(player.formation.isEmpty())
		{
			return Lang.F2203;
		}
		return null;
	}

	/**
	 * PVP战斗
	 * 
	 * @param att
	 * @param def
	 * @return 返回战斗数据
	 */
	private List<Integer> pvpFight(BattleCard[] att,BattleCard[] def)
	{
		BattleScene scene=new BattleScene();
		battleInit(att,def);
		scene.setMaxRound(30);
		scene.start(att,def,BattleScene.FIGHT_NORMAL);
		scene.getRecord().set(0,scene.getStep());
		return scene.getRecord();
	}

	/**
	 * 攻击敌人
	 * 
	 * @param player 玩家对象
	 * @param tarPlayerId 目标玩家ID
	 * @param isSave 是否救人
	 * @return 返回战斗数据
	 */
	public Map<String,Object> attEnemy(Player player,int tarPlayerId,
		boolean isSave)
	{
		Map<String,Object> mapResult=checkAttEnemy(player,tarPlayerId,isSave);
		String error=(String)mapResult.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Player tarPlayer=(Player)mapResult.get("tarPlayer");
		List<Integer> resultList=new ArrayList<Integer>();
		boolean isWin=false;
		player.getIdentity().inAttTimes();
		if(isSave)
		{
			player.getIdentity().inSaveNorTimes();
		}
		resultList=pvpFight(player.formation.getFormation(),
			tarPlayer.formation.getFormation());
		int win=resultList.get(resultList.size()-1);
		if(win==1)
		{
			isWin=true;
		}
		fightEndHandleNormal(player,tarPlayer,isWin,isSave);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("resultList",resultList);
		result.put("formation",tarPlayer.formation);
		return result;
	}
	/**
	 * 玩家赎身处理
	 * 
	 * @param attPlayer
	 * @param defPlayer
	 */
	private void getFreeHandle(Player attPlayer,Player defPlayer)
	{
		attPlayer.becomeFreeMan();
		Slave slave=defPlayer.getIdentity().cutSlave(
			(int)attPlayer.getUserId());
		slaveGetFreeHandle(defPlayer,slave);
		recordInformation(true,attPlayer,defPlayer,null,0,
			Information.EVENT_GETFREE);
	}

	/**
	 * 拯救朋友战斗结束后处理
	 * 
	 * @param myPlayer 我的玩家对象
	 * @param tarPlayer 目标玩家对象
	 * @param friendPlayer 好友玩家对象
	 * @param isWin 是否胜利
	 */
	private void fightEndSaveFriend(Player myPlayer,Player tarPlayer,
		Player friendPlayer,boolean isWin)
	{
		if(isWin)
		{
			friendPlayer.becomeFreeMan();
			Slave slave=tarPlayer.getIdentity().cutSlave(
				(int)friendPlayer.getUserId());
			slaveGetFreeHandle(tarPlayer,slave);
		}
		recordInformation(isWin,myPlayer,tarPlayer,friendPlayer,0,
			Information.EVENT_FIGHT_SAVEFRI);
	}

	/**
	 * @param ownerPlayer 奴隶主玩家对象
	 * @param slave 奴隶对象
	 */
	private void slaveGetFreeHandle(Player ownerPlayer,Slave slave)
	{
		if(slave.getStatus()==Slave.STATUS_WORK)
		{
			long alreadysWorkTime=TimeKit.nowTimeMills()
				-slave.getStartWorkTime();
			int money=(int)(alreadysWorkTime/1000/60*slave.getFightPoint());
			ownerPlayer.incrMoney(money);
			slave.workEndHandle();
			slavePool.remove(slave);
		}
	}
	/**
	 * 反抗战斗结束后处理
	 * 
	 * @param attPlayer 反抗玩家对象
	 * @param defPlayer 被反抗玩家对象
	 * @param isWin 是否胜利
	 */
	private void fighEndHandleReact(Player attPlayer,Player defPlayer,
		boolean isWin)
	{
		if(isWin)
		{
			attPlayer.becomeFreeMan();
			Slave slave=defPlayer.getIdentity().cutSlave(
				(int)attPlayer.getUserId());
			slaveGetFreeHandle(defPlayer,slave);
		}
		recordInformation(isWin,attPlayer,defPlayer,null,0,
			Information.EVENT_FIGHT_SAVENOR);
	}

	/**
	 * 普通战斗结束后处理
	 * 
	 * @param attPlayer 攻击玩家
	 * @param defPlayer 防守玩家
	 * @param isWin 是否胜利
	 * @param isSave 是否救人
	 */
	private void fightEndHandleNormal(Player attPlayer,Player defPlayer,
		boolean isWin,boolean isSave)
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
					attPlayer.getIdentity().addSlave(slave,
						(int)attPlayer.getUserId());
				}
				eventType=Information.EVENT_FIGHT_FREE;
				break;
			case Identity.OWNER:
				if(isWin)
				{
					if(isSave)
					{
						slave=defPlayer.getIdentity().cutSlave();
						slaveGetFreeHandle(defPlayer,slave);
						attachPlayer=getTarPlayer(slave.getUserId());
						attachPlayer.becomeFreeMan();
					}
					else
					{
						slave=defPlayer.getIdentity().cutSlave();
						slaveGetFreeHandle(defPlayer,slave);
						attPlayer.getIdentity().addSlave(slave,
							(int)attPlayer.getUserId());
						attachPlayer=getTarPlayer(slave.getUserId());
						attachPlayer.getIdentity().setOwnerId(
							(int)attPlayer.getUserId());
					}
				}
				if(isSave)
				{
					eventType=Information.EVENT_FIGHT_SAVENOR;
				}
				else
				{
					eventType=Information.EVENT_FIGHT_OWNER;
				}
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
	private Map<String,Object> checkAttEnemy(Player player,int tarPlayerId,
		boolean isSave)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2204);
			return mapInfo;
		}
		if(player.getIdentity().getGrade()!=Identity.FREEMAN
			&&player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2201);
			return mapInfo;
		}
		if(player.formation.isEmpty())
		{
			mapInfo.put("error",Lang.F2203);
			return mapInfo;
		}
		Player tarPlayer=getTarPlayer(tarPlayerId);
		if(tarPlayer.getIdentity().getGrade()==Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2202);
			return mapInfo;
		}
		if(tarPlayer.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2205);
			return mapInfo;
		}
		if(player.getIdentity().getAttTimes()>=GameCFG.getAttConfine())
		{
			mapInfo.put("error",Lang.F2206);
			return mapInfo;
		}
		if(isSave)
		{
			if(player.getIdentity().getSaveNorTimes()>=GameCFG
				.getSaveNorConfine())
			{
				mapInfo.put("error",Lang.F2212);
				return mapInfo;
			}
		}
		mapInfo.put("tarPlayer",tarPlayer);
		mapInfo.put("error",null);
		return mapInfo;
	}

	/**
	 * 获取敌人列表
	 * 
	 * @param player 玩家对象
	 * @param getType 获取类型
	 * @param isSave 是否是拔刀相助
	 * @return
	 */
	public List<Player> getEnemy(Player player,boolean isSave)
	{
		String error=checkGetEnemy(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		List<Player> enemyList=new ArrayList<Player>();
		getIdentity(player,GameCFG.getErrorValue(),enemyList,isSave);
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
			return Lang.F2204;
		}
		if(player.getIdentity().getGrade()!=Identity.OWNER
			&&player.getIdentity().getGrade()!=Identity.FREEMAN)
		{
			return Lang.F2201;
		}
		return null;
	}

	/**
	 * 根据条件获取敌人列表
	 * 
	 * @param player 玩家对象
	 * @param errorValue 误差值
	 * @param enemyList 敌人列表
	 * @param isSave 是否拔刀相助
	 * @return
	 */
	private void getIdentity(Player player,int errorValue,
		List<Player> enemyList,boolean isSave)
	{
		if(enemyList.size()==GameCFG.getEnemySize()
			||errorValue==GameCFG.getErrorValue()*GameCFG.getMatchTimes())
		{
			return;
		}
		Session[] sessions=ds.getSessionMap().getSessions().clone();
		List<Session> sessionList=Arrays.asList(sessions);
		List<Session> sessionList_=new ArrayList<Session>(sessionList.size());
		for(Session session:sessionList)
		{
			sessionList_.add(session);
		}

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
		getIdentityForRAM(sessionList_,player,enemyList,errorValue,isSave);
		getIdentityForDB(players,player,enemyList,errorValue,isSave);
		errorValue+=GameCFG.getErrorValue();
		getIdentity(player,errorValue,enemyList,isSave);
	}

	/**
	 * 从内存中尽量获取最多的敌人
	 * 
	 * @param sessionList 内存会话列表
	 * @param player 玩家对象
	 * @param enemyList 敌人列表
	 * @param errorValue 误差值
	 * @param isSave 是否拔刀相助
	 * @return
	 */
	private void getIdentityForRAM(List<Session> sessionList,Player player,
		List<Player> enemyList,int errorValue,boolean isSave)
	{
		while(sessionList.size()>0)
		{
			int index=MathKit.randomValue(0,sessionList.size()-1);
			Session session=sessionList.get(index);
			if(session!=null)
			{
				sessionList.remove(index);
				Player tarPlayer=(Player)session.getSource();
				if(isSave)
				{
					if(Math.abs(tarPlayer.getFightPoint()
						-player.getFightPoint())<=errorValue
						&&tarPlayer.getIdentity().getGrade()==Identity.OWNER)
					{
						enemyList.add(tarPlayer);
						if(enemyList.size()==GameCFG.getEnemySize())
						{
							return;
						}
					}
				}
				else
				{
					if(Math.abs(tarPlayer.getFightPoint()
						-player.getFightPoint())<=errorValue
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

	}

	/**
	 * 从数据库中尽量获取最多的敌人
	 * 
	 * @param players 数据库玩家列表
	 * @param player 玩家对象
	 * @param enemyList 敌人列表
	 * @param errorValue 误差值 从数据库中尽量获取最多的敌人
	 * @param 是否拔刀相助
	 */
	private void getIdentityForDB(List<Player> players,Player player,
		List<Player> enemyList,int errorValue,boolean isSave)
	{
		while(players.size()>0)
		{
			int index=MathKit.randomValue(0,players.size()-1);
			Player tarPlayer=players.get(index);
			players.remove(index);
			if(isSave)
			{
				if(Math
					.abs(tarPlayer.getFightPoint()-player.getFightPoint())<=errorValue
					&&tarPlayer.getIdentity().getGrade()==Identity.OWNER)
				{
					enemyList.add(tarPlayer);
					if(enemyList.size()==GameCFG.getEnemySize())
					{
						return;
					}
				}
			}
			else
			{
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
	 * 获取目标玩家对象
	 * 
	 * @param userId
	 * @return 返回目标玩家对象
	 */
	public Player getTarPlayer(int userId)
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
	public void recordInformation(boolean isSuccess,Player myPlayer,
		Player tarPlayer,Player attachPlayer,int attachValue,int eventType)
	{
		Information information=null;
		if(eventType==Information.EVENT_FIGHT_FREE
			||eventType==Information.EVENT_FIGHT_SAVENOR
			||eventType==Information.EVENT_GETFREE)
		{
			information=new Information(eventType,isSuccess,
				myPlayer.getNickname(),tarPlayer.getNickname(),null,
				attachValue);
			sendInformation(tarPlayer,information);
		}
		else if(eventType==Information.EVENT_FIGHT_OWNER)
		{
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
		}
		else if(eventType==Information.EVENT_FIGHT_SAVEFRI)
		{
			information=new Information(eventType,isSuccess,
				myPlayer.getNickname(),tarPlayer.getNickname(),
				attachPlayer.getNickname(),attachValue);
			sendInformation(tarPlayer,attachPlayer,information);
		}
		else if(eventType==Information.EVENT_WORK)
		{
			information=new Information(eventType,isSuccess,null,
				tarPlayer.getNickname(),attachPlayer.getNickname(),
				attachValue);
			sendInformation(tarPlayer,attachPlayer,information);
			return;
		}
		myPlayer.getIdentity().addInformation(information);
	}

	/**
	 * 保存目标对象
	 * 
	 * @param tarPlayer 目标对象
	 */
	private void saveTarPlayer(Player tarPlayer)
	{
		Session session=ds.getSession((int)tarPlayer.getUserId());
		if(session==null)
		{
			dao.savePlayerVar(tarPlayer);
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
