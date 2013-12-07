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
import com.cambrian.dfhm.slaveAndWar.timer.SlaveContractOverTimeTask;
import com.cambrian.dfhm.slaveAndWar.timer.SlaveWorkTimeTask;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵�����������ܹ�����
 * 
 * @author��LazyBear
 */
public class SlaveManager {

	/* static fields */
	private static SlaveManager instance = new SlaveManager();
	/** ū���� */
	public List<Slave> slavePool = new ArrayList<Slave>();

	/* static methods */
	public static SlaveManager getInstance() {
		return instance;
	}

	/* fields */
	/** �ڴ����ݷ��ʶ��� */
	private DataServer ds;
	/** ����DB���ʶ��� */
	private SlaveAndWarDao dao;
	/** �¼���Ϣ���Ͷ��� */
	private EventMessageNotice emn;

	/* constructors */

	/* properties */
	public void setDS(DataServer ds) {
		instance.ds = ds;
	}

	public void setSawd(SlaveAndWarDao dao) {
		instance.dao = dao;
	}

	public void setEMN(EventMessageNotice emn) {
		instance.emn = emn;
	}

	/* init start */

	/* methods */

	/**
	 * ����ū�������̡߳�
	 */
	public void timerStart() {
		Timer slaveTimer = new Timer();
		slaveTimer.schedule(new SlaveWorkTimeTask(), 0, TimeKit.SEC_MILLS * 10);

		slaveTimer.schedule(new SlaveContractOverTimeTask(dao, ds),
				TimeKit.HOUR_MILLS * 7, TimeKit.MIN_MILLS * 10);
	}

	/**
	 * �ͷ�ū��
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param slaveId
	 *            ū��ID
	 */
	public void releaseSlave(Player player, int slaveId) {
		Map<String, Object> resultMap = checkReleaseSlave(player, slaveId);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Slave slave = (Slave) resultMap.get("slave");
		player.getIdentity().getSlaveList().remove(slave);
		if (player.getIdentity().getSlaveList().size() < 1) {
			player.becomeFreeMan();
		}
	}

	/**
	 * ����ͷ�ū��
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param slaveId
	 *            ū��ID
	 * @return
	 */
	private Map<String, Object> checkReleaseSlave(Player player, int slaveId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() != Identity.OWNER) {
			mapInfo.put("error", Lang.F2204);
			return mapInfo;
		}
		Slave slave = player.getIdentity().getSlave(slaveId);
		if (slave == null) {
			mapInfo.put("error", Lang.F2213);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("slave", slave);
		return mapInfo;
	}

	/**
	 * ��ȥ�ٻ�
	 * 
	 * @param player
	 * @param slaveId
	 */
	public void fastWork(Player player, int slaveId) {
		Map<String, Object> resultMap = checkFastWork(player, slaveId);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		player.decrGold(GameCFG.getFastWorkGold());
		Slave slave = (Slave) resultMap.get("slvae");
		int money = slave.getFightPoint() * GameCFG.getWorkTime();
		player.incrMoney(money);
		Player slavePlayer = getTarPlayer(slave.getUserId());
		recordInformation(true, null, player, slavePlayer, money,
				Information.EVENT_WORK);
		if (slave.isManaged()) {
			if (player.getIdentity().getWorkTimes() < GameCFG.getWorkConfine()) {
				player.getIdentity().inWorkTimes();
				slave.setStartWorkTime(TimeKit.nowTimeMills());
			} else {
				slave.workEndHandle();
				slavePool.remove(slave);
			}
		} else {
			slave.workEndHandle();
			slavePool.remove(slave);
		}
	}

	/**
	 * �����ȥ�ٻ�
	 * 
	 * @param player
	 * @param slaveId
	 * @return
	 */
	private Map<String, Object> checkFastWork(Player player, int slaveId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() != Identity.OWNER) {
			mapInfo.put("error", Lang.F2204);
			return mapInfo;
		}
		if (player.getGold() < GameCFG.getFastWorkGold()) {
			mapInfo.put("error", Lang.F2217);
			return mapInfo;
		}
		Slave slave = player.getIdentity().getSlave(slaveId);
		if (slave == null) {
			mapInfo.put("error", Lang.F2213);
			return mapInfo;
		}
		if (slave.getStatus() != Slave.STATUS_WORK) {
			mapInfo.put("error", Lang.F2216);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("slave", slave);
		return mapInfo;
	}

	/**
	 * ��ʼ�й�
	 * 
	 * @param player
	 * @param slaveId
	 */
	public void managed(Player player, int slaveId) {
		Map<String, Object> resultMap = checkManaged(player, slaveId);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		player.decrGold(GameCFG.getManagedGold());
		Slave slave = (Slave) resultMap.get("slvae");
		slave.setManaged(true);
	}

	/**
	 * ��鿪ʼ�й�
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param slaveId
	 *            ū��ID
	 * @return
	 */
	private Map<String, Object> checkManaged(Player player, int slaveId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() != Identity.OWNER) {
			mapInfo.put("error", Lang.F2204);
			return mapInfo;
		}
		if (player.getGold() < GameCFG.getManagedGold()) {
			mapInfo.put("error", Lang.F2215);
			return mapInfo;
		}
		Slave slave = player.getIdentity().getSlave(slaveId);
		if (slave == null) {
			mapInfo.put("error", Lang.F2213);
			return mapInfo;
		}
		if (slave.getStatus() != Slave.STATUS_WORK) {
			mapInfo.put("error", Lang.F2216);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("slave", slave);
		return mapInfo;
	}

	/**
	 * ��ʼ����
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param slaveId
	 *            ū��ID
	 */
	public void work(Player player, int slaveId) {
		Map<String, Object> resultMap = checkWork(player, slaveId);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		player.getIdentity().inWorkTimes();
		Slave slave = (Slave) resultMap.get("slave");
		slave.workStartHandle();
		slavePool.add(slave);
	}

	/**
	 * ��鿪ʼ����
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param slaveId
	 *            ū��ID
	 * @return
	 */
	private Map<String, Object> checkWork(Player player, int slaveId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() != Identity.OWNER) {
			mapInfo.put("error", Lang.F2204);
			return mapInfo;
		}
		if (player.getIdentity().getWorkTimes() >= GameCFG.getWorkConfine()) {
			mapInfo.put("error", Lang.F2214);
			return mapInfo;
		}
		Slave slave = player.getIdentity().getSlave(slaveId);
		if (slave == null) {
			mapInfo.put("error", Lang.F2213);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("slave", slave);
		return mapInfo;
	}

	/**
	 * ��ȡū���б�
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @return ����ū���б�
	 */
	public List<Slave> getSlaveList(Player player) {
		String error = checkGetSlaveList(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		return player.getIdentity().getSlaveList();
	}

	/**
	 * ����ȡū���б�
	 * 
	 * @param player
	 * @return
	 */
	private String checkGetSlaveList(Player player) {
		if (player.getIdentity().getGrade() != Identity.OWNER) {
			return Lang.F2204;
		}
		return null;
	}

	/**
	 * ���Ⱥ���
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param friendId
	 *            ����ID
	 * @return ����ս������
	 */
	public Map<String, Object> saveFriend(Player player, int friendId) {
		Map<String, Object> resultMap = checkSaveFriend(player, friendId);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Player friendPlayer = (Player) resultMap.get("tarPlayer");// ���Ѷ���
		Player tarPlayer = getTarPlayer(friendPlayer.getIdentity()// �������˶���
				.getOwnerId());
		List<Integer> resultList = new ArrayList<Integer>();
		boolean isWin = false;
		player.getIdentity().inSaveTimes();
		resultList = pvpFight(player.formation.getFormation(),
				tarPlayer.formation.getFormation());
		int win = resultList.get(resultList.size() - 1);
		if (win == 1) {
			isWin = true;
		}
		fightEndSaveFriend(player, tarPlayer, friendPlayer, isWin);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resultList", resultList);
		result.put("formation", tarPlayer.formation.getFormation());
		return result;
	}

	/**
	 * ������Ⱥ���
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param friendId
	 *            ����ID
	 * @return
	 */
	private Map<String, Object> checkSaveFriend(Player player, int friendId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() == Identity.SLAVE) {
			mapInfo.put("error", Lang.F2204);
			return mapInfo;
		}
		if (player.getIdentity().getSaveTimes() >= GameCFG.getSaveConfine()) {
			mapInfo.put("error", Lang.F2211);
			return mapInfo;
		}
		Player tarPlayer = getTarPlayer(friendId);
		if (tarPlayer == null) {
			mapInfo.put("error", Lang.F2219);
			return mapInfo;
		}
		if (tarPlayer.getIdentity().getGrade() != Identity.SLAVE) {
			mapInfo.put("error", Lang.F2205);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("tarPlayer", tarPlayer);
		return mapInfo;
	}

	/**
	 * ���������
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param friendId
	 *            ����ID
	 */
	public void forHelp(Player player, int friendId) {
		Map<String, Object> resultMap = checkForHelp(player, friendId);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		player.getIdentity().inHelpTimes();
		Player tarPlayer = (Player) resultMap.get("tarPlayer");
		tarPlayer.getIdentity().getHelpList().add((int) player.getUserId());
		saveTarPlayer(tarPlayer);
	}

	/**
	 * ����������
	 * 
	 * @param player
	 * @param friendId
	 * @return
	 */
	private Map<String, Object> checkForHelp(Player player, int friendId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() != Identity.SLAVE) {
			mapInfo.put("error", Lang.F2207);
			return mapInfo;
		}
		Player tarPlayer = getTarPlayer(friendId);
		if (tarPlayer == null) {
			mapInfo.put("error", Lang.F2219);
			return mapInfo;
		}
		if (tarPlayer.getIdentity().getGrade() == Identity.SLAVE) {
			mapInfo.put("error", Lang.F2210);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("tarPlayer", tarPlayer);
		return mapInfo;
	}

	/**
	 * ��ȡ���������б�
	 * 
	 * @param player
	 * @return
	 */
	public List<Player> getHelpList(Player player) {
		String error = checkGetEnemy(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		List<Player> helpList = new ArrayList<Player>();
		for (Integer integer : player.getIdentity().getHelpList()) {
			Player tarPlayer = getTarPlayer(integer);
			helpList.add(tarPlayer);
		}
		return helpList;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @return ������Ҷ����б�
	 */
	public List<Player> getFriendList(Player player) {
		String error = checkGetFriendList(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		List<Integer> friendIdList = player.getFriendInfo().getFriendList();

		List<Player> friends = new ArrayList<Player>();

		for (Integer integer : friendIdList) {
			Player tarPlayer = getTarPlayer(integer);
			if ((int) tarPlayer.getUserId() == player.getIdentity()
					.getOwnerId())
				continue;
			if (tarPlayer.getIdentity().getGrade() != Identity.SLAVE)
				friends.add(tarPlayer);
		}
		return friends;
	}

	/**
	 * ����ȡ�����б�
	 * 
	 * @param player
	 * @return
	 */
	private String checkGetFriendList(Player player) {
		if (player.getIdentity().getGrade() != Identity.SLAVE) {
			return Lang.F2207;
		}
		return null;
	}

	/**
	 * ����
	 * 
	 * @param player
	 *            ��Ҷ���
	 */
	public void getFree(Player player) {
		String error = checkGetFree(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		player.decrGold(GameCFG.getGetFreeGold());
		Player tarPlayer = getTarPlayer(player.getIdentity().getOwnerId());
		getFreeHandle(player, tarPlayer);
	}

	/**
	 * �������
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @return ���ش�����Ϣ
	 */
	private String checkGetFree(Player player) {
		if (player.getIdentity().getGrade() != Identity.SLAVE) {
			return Lang.F2207;
		}
		if (player.getGold() < GameCFG.getGetFreeGold()) {
			return Lang.F2209;
		}
		return null;
	}

	/**
	 * ����
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @return ����ս������
	 */
	public Map<String, Object> react(Player player) {
		String error = checkReact(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Player tarPlayer = getTarPlayer(player.getIdentity().getOwnerId());
		List<Integer> resultList = new ArrayList<Integer>();
		boolean isWin = false;
		player.getIdentity().inAttTimes();
		resultList = pvpFight(player.formation.getFormation(),
				tarPlayer.formation.getFormation());
		int win = resultList.get(resultList.size() - 1);
		if (win == 1) {
			isWin = true;
		}
		fighEndHandleReact(player, tarPlayer, isWin);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resultList", resultList);
		result.put("formation", tarPlayer.formation);
		return result;
	}

	/**
	 * �����ҷ���
	 * 
	 * @param player
	 * @return ���ش�����Ϣ
	 */
	private String checkReact(Player player) {
		if (player.getIdentity().getGrade() != Identity.SLAVE) {
			return Lang.F2207;
		}
		if (player.getIdentity().getReactTimes() >= GameCFG.getReactConfine()) {
			return Lang.F2208;
		}
		if (player.formation.isEmpty()) {
			return Lang.F2203;
		}
		return null;
	}

	/**
	 * PVPս��
	 * 
	 * @param att
	 * @param def
	 * @return ����ս������
	 */
	private List<Integer> pvpFight(BattleCard[] att, BattleCard[] def) {
		BattleScene scene = new BattleScene();
		battleInit(att, def);
		scene.setMaxRound(30);
		scene.start(att, def, BattleScene.FIGHT_NORMAL);
		scene.getRecord().set(0, scene.getStep());
		return scene.getRecord();
	}

	/**
	 * ��������
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param tarPlayerId
	 *            Ŀ�����ID
	 * @param isSave
	 *            �Ƿ����
	 * @return ����ս������
	 */
	public Map<String, Object> attEnemy(Player player, int tarPlayerId,
			boolean isSave) {
		Map<String, Object> mapResult = checkAttEnemy(player, tarPlayerId,
				isSave);
		String error = (String) mapResult.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Player tarPlayer = (Player) mapResult.get("tarPlayer");
		List<Integer> resultList = new ArrayList<Integer>();
		boolean isWin = false;
		player.getIdentity().inAttTimes();
		if (isSave) {
			player.getIdentity().inSaveNorTimes();
		}
		resultList = pvpFight(player.formation.getFormation(),
				tarPlayer.formation.getFormation());
		int win = resultList.get(resultList.size() - 1);
		if (win > 0) {
			isWin = true;
		}
		fightEndHandleNormal(player, tarPlayer, isWin, isSave);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resultList", resultList);
		result.put("formation", tarPlayer.formation);
		return result;
	}

	/**
	 * ���������
	 * 
	 * @param attPlayer
	 * @param defPlayer
	 */
	private void getFreeHandle(Player attPlayer, Player defPlayer) {
		attPlayer.becomeFreeMan();
		Slave slave = defPlayer.getIdentity().cutSlave(
				(int) attPlayer.getUserId());
		slaveGetFreeHandle(defPlayer, slave);
		recordInformation(true, attPlayer, defPlayer, null, 0,
				Information.EVENT_GETFREE);
	}

	/**
	 * ��������ս����������
	 * 
	 * @param myPlayer
	 *            �ҵ���Ҷ���
	 * @param tarPlayer
	 *            Ŀ����Ҷ���
	 * @param friendPlayer
	 *            ������Ҷ���
	 * @param isWin
	 *            �Ƿ�ʤ��
	 */
	private void fightEndSaveFriend(Player myPlayer, Player tarPlayer,
			Player friendPlayer, boolean isWin) {
		if (isWin) {
			friendPlayer.becomeFreeMan();
			Slave slave = tarPlayer.getIdentity().cutSlave(
					(int) friendPlayer.getUserId());
			slaveGetFreeHandle(tarPlayer, slave);
		}
		recordInformation(isWin, myPlayer, tarPlayer, friendPlayer, 0,
				Information.EVENT_FIGHT_SAVEFRI);
	}

	// /**
	// * @param ownerPlayer
	// * ū������Ҷ���
	// * @param slave
	// * ū������
	// */
	// private void slaveGetFreeHandle(Player ownerPlayer, Slave slave) {
	// if (slave.getStatus() == Slave.STATUS_WORK) {
	// long alreadysWorkTime = TimeKit.nowTimeMills()
	// - slave.getStartWorkTime();
	// int money = (int) (alreadysWorkTime / 1000 / 60 * slave
	// .getFightPoint());
	// ownerPlayer.incrMoney(money);
	// slave.workEndHandle();
	// slavePool.remove(slave);
	// }
	// }

	/**
	 * ����ս����������
	 * 
	 * @param attPlayer
	 *            ������Ҷ���
	 * @param defPlayer
	 *            ��������Ҷ���
	 * @param isWin
	 *            �Ƿ�ʤ��
	 */
	private void fighEndHandleReact(Player attPlayer, Player defPlayer,
			boolean isWin) {
		if (isWin) {
			attPlayer.becomeFreeMan();
			Slave slave = defPlayer.getIdentity().cutSlave(
					(int) attPlayer.getUserId());
			slaveGetFreeHandle(defPlayer, slave);
		}
		recordInformation(isWin, attPlayer, defPlayer, null, 0,
				Information.EVENT_FIGHT_SAVENOR);
	}

	/**
	 * ��ͨս����������
	 * 
	 * @param attPlayer
	 *            �������
	 * @param defPlayer
	 *            �������
	 * @param isWin
	 *            �Ƿ�ʤ��
	 * @param isSave
	 *            �Ƿ����
	 */
	private void fightEndHandleNormal(Player attPlayer, Player defPlayer,
			boolean isWin, boolean isSave) {
		int eventType = 0;
		Slave slave = null;
		Player attachPlayer = null;
		switch (defPlayer.getIdentity().getGrade()) {
		case Identity.FREEMAN:
			if (isWin) {
				slave = defPlayer.becomeSlave((int) attPlayer.getUserId());
				Information.CreatandSave(defPlayer.getIdentity(), attPlayer
						.getNickname(), "", Information.TYPE_FIGHT,
						Information.EVENT_SUCCESS, 0);
				attPlayer.getIdentity().addSlave(slave,
						(int) attPlayer.getUserId());
				Information.CreatandSave(attPlayer.getIdentity(), defPlayer
						.getNickname(), "", Information.TYPE_FIGHT,
						Information.EVENT_SUCCESS, 0);
			} else {
				Information.CreatandSave(defPlayer.getIdentity(), attPlayer
						.getNickname(), "", Information.TYPE_FIGHT,
						Information.EVENT_FAIL, 0);
				Information.CreatandSave(attPlayer.getIdentity(), defPlayer
						.getNickname(), "", Information.TYPE_FIGHT,
						Information.EVENT_FAIL, 0);
			}
			Session session = ds.getSession((int) defPlayer.getUserId());
			if (session == null) {
				dao.savePlayerVar(defPlayer);
			} else {
				emn.send(session, new Object[] { 0 });
			}
			break;
		case Identity.OWNER:
			if (isWin) {
				slave = defPlayer.getIdentity().cutSlave();
				if (isSave) {
					slave.beFreeHandle(ds, dao);
					attachPlayer = getTarPlayer(slave.getUserId());

					Information
							.CreatandSave(attPlayer.getIdentity(), defPlayer
									.getNickname(), attachPlayer.getNickname(),
									Information.TYPE_SAVE,
									Information.EVENT_SUCCESS, 0);

					Information
							.CreatandSave(defPlayer.getIdentity(), attPlayer
									.getNickname(), attachPlayer.getNickname(),
									Information.TYPE_SAVE,
									Information.EVENT_SUCCESS, 0);

					Information
							.CreatandSave(attachPlayer.getIdentity(), attPlayer
									.getNickname(), defPlayer.getNickname(),
									Information.TYPE_SAVE,
									Information.EVENT_SUCCESS, 0);

				} else {
					attPlayer.getIdentity().addSlave(slave,
							(int) attPlayer.getUserId());
					attachPlayer = getTarPlayer(slave.getUserId());
					attachPlayer.getIdentity().setOwnerId(
							(int) attPlayer.getUserId());
					slave.competRemove(ds, dao, attachPlayer);

					Information.CreatandSave(attPlayer.getIdentity(), defPlayer
							.getNickname(), attachPlayer.getNickname(),
							Information.TYPE_FIGHT, Information.EVENT_SUCCESS,
							0);
					
					Information.CreatandSave(defPlayer.getIdentity(), playerA, playerB, type, result, value)
				}
			}
			if (isSave) {
				eventType = Information.EVENT_FIGHT_SAVENOR;
			} else {
				eventType = Information.EVENT_FIGHT_OWNER;
			}
			break;
		default:
			break;
		}
		recordInformation(isWin, attPlayer, defPlayer, attachPlayer, 0,
				eventType);
	}

	/**
	 * ��⹥������
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param tarPlayerId
	 *            Ŀ�����ID
	 * @return ���ؼ������Ϣ
	 */
	private Map<String, Object> checkAttEnemy(Player player, int tarPlayerId,
			boolean isSave) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() < Identity.SLAVE
				|| player.getIdentity().getGrade() > Identity.OWNER) {
			mapInfo.put("error", Lang.F2204);
			return mapInfo;
		}
		if (player.getIdentity().getGrade() != Identity.FREEMAN
				&& player.getIdentity().getGrade() != Identity.OWNER) {
			mapInfo.put("error", Lang.F2201);
			return mapInfo;
		}
		if (player.formation.isEmpty()) {
			mapInfo.put("error", Lang.F2203);
			return mapInfo;
		}
		Player tarPlayer = getTarPlayer(tarPlayerId);
		if (tarPlayer == null) {
			mapInfo.put("error", Lang.F2219);
			return mapInfo;
		}
		if (tarPlayer.getIdentity().getGrade() == Identity.SLAVE) {
			mapInfo.put("error", Lang.F2202);
			return mapInfo;
		}
		if (tarPlayer.getIdentity().getGrade() < Identity.SLAVE
				|| player.getIdentity().getGrade() > Identity.OWNER) {
			mapInfo.put("error", Lang.F2205);
			return mapInfo;
		}
		if (player.getIdentity().getAttTimes() >= GameCFG.getAttConfine()) {
			mapInfo.put("error", Lang.F2206);
			return mapInfo;
		}
		if (isSave) {
			if (player.getIdentity().getSaveNorTimes() >= GameCFG
					.getSaveNorConfine()) {
				mapInfo.put("error", Lang.F2212);
				return mapInfo;
			}
		}
		mapInfo.put("tarPlayer", tarPlayer);
		mapInfo.put("error", null);
		return mapInfo;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param getType
	 *            ��ȡ����
	 * @param isSave
	 *            �Ƿ��ǰε�����
	 * @return
	 */
	public List<Player> getEnemy(Player player, boolean isSave) {
		String error = checkGetEnemy(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		List<Player> enemyList = new ArrayList<Player>();
		getIdentity(player, GameCFG.getErrorValue(), enemyList, isSave);
		return enemyList;
	}

	/**
	 * ����ȡ�����б�
	 * 
	 * @return
	 */
	private String checkGetEnemy(Player player) {
		if (player.getIdentity().getGrade() < Identity.SLAVE
				|| player.getIdentity().getGrade() > Identity.OWNER) {
			return Lang.F2204;
		}
		if (player.getIdentity().getGrade() != Identity.OWNER
				&& player.getIdentity().getGrade() != Identity.FREEMAN) {
			return Lang.F2201;
		}
		return null;
	}

	/**
	 * ����������ȡ�����б�
	 * 
	 * @param player
	 *            ��Ҷ���
	 * @param errorValue
	 *            ���ֵ
	 * @param enemyList
	 *            �����б�
	 * @param isSave
	 *            �Ƿ�ε�����
	 * @return
	 */
	private void getIdentity(Player player, int errorValue,
			List<Player> enemyList, boolean isSave) {
		if (enemyList.size() == GameCFG.getEnemySize()
				|| errorValue == GameCFG.getErrorValue()
						* GameCFG.getMatchTimes()) {
			return;
		}
		Session[] sessions = ds.getSessionMap().getSessions().clone();
		List<Session> sessionList = Arrays.asList(sessions);
		List<Session> sessionList_ = new ArrayList<Session>(sessionList.size());
		for (Session session : sessionList) {
			sessionList_.add(session);
		}

		List<Player> rmPlayers = new ArrayList<Player>();
		for (Session session : sessionList) {
			if (session != null) {
				Player rmPlayer = (Player) session.getSource();
				if (rmPlayer != null)
					rmPlayers.add(rmPlayer);
			}
		}
		List<Player> players = dao.getAllPlayer();
		players.removeAll(rmPlayers);
		getIdentityForRAM(sessionList_, player, enemyList, errorValue, isSave);
		getIdentityForDB(players, player, enemyList, errorValue, isSave);
		errorValue += GameCFG.getErrorValue();
		getIdentity(player, errorValue, enemyList, isSave);
	}

	/**
	 * ���ڴ��о�����ȡ���ĵ���
	 * 
	 * @param sessionList
	 *            �ڴ�Ự�б�
	 * @param player
	 *            ��Ҷ���
	 * @param enemyList
	 *            �����б�
	 * @param errorValue
	 *            ���ֵ
	 * @param isSave
	 *            �Ƿ�ε�����
	 * @return
	 */
	private void getIdentityForRAM(List<Session> sessionList, Player player,
			List<Player> enemyList, int errorValue, boolean isSave) {
		while (sessionList.size() > 0) {
			int index = MathKit.randomValue(0, sessionList.size() - 1);
			Session session = sessionList.get(index);
			if (session != null) {
				sessionList.remove(index);
				Player tarPlayer = (Player) session.getSource();
				if (tarPlayer != null) {

					if (player.equals(tarPlayer))
						continue;
					if (enemyList.contains(tarPlayer))
						continue;
					if (isSave) {
						if (Math.abs(tarPlayer.getFightPoint()
								- player.getFightPoint()) <= errorValue
								&& tarPlayer.getIdentity().getGrade() == Identity.OWNER) {
							enemyList.add(tarPlayer);
							if (enemyList.size() == GameCFG.getEnemySize()) {
								return;
							}
						}
					} else {
						if (Math.abs(tarPlayer.getFightPoint()
								- player.getFightPoint()) <= errorValue
								&& tarPlayer.getIdentity().getGrade() != Identity.SLAVE) {
							enemyList.add(tarPlayer);
							if (enemyList.size() == GameCFG.getEnemySize()) {
								return;
							}
						}

					}
				}
			}
		}

	}

	/**
	 * �����ݿ��о�����ȡ���ĵ���
	 * 
	 * @param players
	 *            ���ݿ�����б�
	 * @param player
	 *            ��Ҷ���
	 * @param enemyList
	 *            �����б�
	 * @param errorValue
	 *            ���ֵ �����ݿ��о�����ȡ���ĵ���
	 * @param �Ƿ�ε�����
	 */
	private void getIdentityForDB(List<Player> players, Player player,
			List<Player> enemyList, int errorValue, boolean isSave) {
		while (players.size() > 0) {
			int index = MathKit.randomValue(0, players.size() - 1);
			Player tarPlayer = players.get(index);
			players.remove(index);
			if (isSave) {
				if (Math
						.abs(tarPlayer.getFightPoint() - player.getFightPoint()) <= errorValue
						&& tarPlayer.getIdentity().getGrade() == Identity.OWNER
						&& player != tarPlayer) {
					enemyList.add(tarPlayer);
					if (enemyList.size() == GameCFG.getEnemySize()) {
						return;
					}
				}
			} else {
				if (Math
						.abs(tarPlayer.getFightPoint() - player.getFightPoint()) <= errorValue
						&& tarPlayer.getIdentity().getGrade() != Identity.SLAVE
						&& player != tarPlayer) {
					enemyList.add(tarPlayer);
					if (enemyList.size() == GameCFG.getEnemySize()) {
						return;
					}
				}

			}
		}
	}

	/**
	 * ��ȡĿ����Ҷ���
	 * 
	 * @param userId
	 * @return ����Ŀ����Ҷ���
	 */
	public Player getTarPlayer(int userId) {
		if (userId < 1) {
			return null;
		}
		Session session = ds.getSession(userId);
		if (session != null) {
			return (Player) session.getSource();
		} else {
			return dao.getPlayer(userId);
		}
	}

	/**
	 * ս��ǰ��ʼ��
	 * 
	 * @param att
	 * @param def
	 * @param isGlobalBoss
	 *            �Ƿ񹥻�����BOSS
	 */
	private void battleInit(BattleCard[] att, BattleCard[] def) {
		for (BattleCard battleCard : att) {
			if (battleCard != null) {
				battleCard.setSide(1);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
		for (BattleCard battleCard : def) {
			if (battleCard != null) {
				battleCard.setSide(2);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
	}

	// /**
	// * ��¼��Ϣ����������
	// *
	// * @param isSuccess
	// * �¼��Ƿ�ɹ�
	// * @param myPlayer
	// * �ҵ���Ҷ���
	// * @param tarPlayer
	// * Ŀ����Ҷ���
	// * @param attachPlayer
	// * ������Ҷ���
	// * @param attachValue
	// * ����ֵ
	// * @param eventType
	// * �¼�����
	// */
	// public void recordInformation(boolean isSuccess, Player myPlayer,
	// Player tarPlayer, Player attachPlayer, int attachValue,
	// int eventType) {
	// Information information = null;
	// if (eventType == Information.EVENT_FIGHT_FREE
	// || eventType == Information.EVENT_FIGHT_SAVENOR
	// || eventType == Information.EVENT_GETFREE) {
	// information = new Information(eventType, isSuccess, myPlayer
	// .getNickname(), tarPlayer.getNickname(), null, attachValue);
	// sendInformation(tarPlayer, information);
	// } else if (eventType == Information.EVENT_FIGHT_OWNER) {
	// if (isSuccess) {
	// information = new Information(eventType, isSuccess, myPlayer
	// .getNickname(), tarPlayer.getNickname(), attachPlayer
	// .getNickname(), attachValue);
	// sendInformation(tarPlayer, attachPlayer, information);
	// } else {
	// information = new Information(eventType, isSuccess, myPlayer
	// .getNickname(), tarPlayer.getNickname(), null,
	// attachValue);
	// sendInformation(tarPlayer, information);
	// }
	// } else if (eventType == Information.EVENT_FIGHT_SAVEFRI) {
	// information = new Information(eventType, isSuccess, myPlayer
	// .getNickname(), tarPlayer.getNickname(), attachPlayer
	// .getNickname(), attachValue);
	// sendInformation(tarPlayer, attachPlayer, information);
	// } else if (eventType == Information.EVENT_WORK) {
	// information = new Information(eventType, isSuccess, null, tarPlayer
	// .getNickname(), attachPlayer.getNickname(), attachValue);
	// sendInformation(tarPlayer, attachPlayer, information);
	// return;
	// }
	// myPlayer.getIdentity().addInformation(information);
	// }

	/**
	 * ����Ŀ�����
	 * 
	 * @param tarPlayer
	 *            Ŀ�����
	 */
	private void saveTarPlayer(Player tarPlayer) {
		Session session = ds.getSession((int) tarPlayer.getUserId());
		if (session == null) {
			dao.savePlayerVar(tarPlayer);
		}
	}

	/**
	 * ����ҷ������Ͳ���¼��Ϣ(ֻ��������Һͱ������ʱ)
	 * 
	 * @param information
	 *            ��Ϣ����
	 * @param tarPlayer
	 *            Ŀ����Ҷ���
	 */
	private void sendInformation(Player tarPlayer, Information information) {
		tarPlayer.getIdentity().addInformation(information);
		Session session = ds.getSession((int) tarPlayer.getUserId());
		if (session != null) {
			emn.send(session, new Object[] { information });
		} else {
			dao.savePlayerVar(tarPlayer);
		}
	}

	/**
	 * ����ҷ������Ͳ���¼��Ϣ(����������ҡ�������Һ͸������ʱ)
	 * 
	 * @param tarPlayer
	 *            ������Ҷ���
	 * @param attachPlayer
	 *            �������
	 * @param information
	 *            ��Ϣ����
	 */
	private void sendInformation(Player tarPlayer, Player attachPlayer,
			Information information) {
		tarPlayer.getIdentity().addInformation(information);
		Session session = ds.getSession((int) tarPlayer.getUserId());
		if (session != null) {
			emn.send(session, new Object[] { information });
		} else {
			dao.savePlayerVar(tarPlayer);
		}
		attachPlayer.getIdentity().addInformation(information);
		Session session_ = ds.getSession((int) attachPlayer.getUserId());
		if (session_ != null) {
			emn.send(session_, new Object[] { information });
		} else {
			dao.savePlayerVar(attachPlayer);
		}
	}
}
