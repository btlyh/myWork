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
 * ��˵�����������ܹ�����
 * 
 * @author��LazyBear
 */
public class SlaveManager {

	/* static fields */
	private static SlaveManager instance = new SlaveManager();

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
	public void setDs(DataServer ds) {
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
			return Lang.F2107;
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
			return Lang.F2107;
		}
		if (player.getGold() < GameCFG.getGetFreeGold()) {
			return Lang.F2109;
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
	public List<Integer> react(Player player) {
		String error = checkReact(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Player tarPlayer = getTarPlayer(player.getIdentity().getOwnerId());
		List<Integer> resultList = new ArrayList<Integer>();
		boolean isWin = false;
		player.getIdentity().inAttTimes();
		if (tarPlayer.formation.isEmpty()) {
			resultList.add(-1);// �Է��������ơ�ս�����ݵ�һλΪ-1��ʾս��ֱ��ʤ��
			isWin = true;
		} else {
			resultList = pvpFight(player.formation.getFormation(),
					tarPlayer.formation.getFormation());
			int win = resultList.get(resultList.size() - 1);
			if (win == 1) {
				isWin = true;
			}
		}
		fighEndHandleReact(player, tarPlayer, isWin);
		return resultList;
	}

	/**
	 * �����ҷ���
	 * 
	 * @param player
	 * @return ���ش�����Ϣ
	 */
	private String checkReact(Player player) {
		if (player.getIdentity().getGrade() != Identity.SLAVE) {
			return Lang.F2107;
		}
		if (player.getIdentity().getReactTimes() >= GameCFG.getReactConfine()) {
			return Lang.F2108;
		}
		if (player.formation.isEmpty()) {
			return Lang.F2103;
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
	 * @param �Ƿ����
	 * @return ����ս������
	 */
	public List<Integer> attEnemy(Player player, int tarPlayerId) {
		Map<String, Object> mapResult = checkAttEnemy(player, tarPlayerId);
		String error = (String) mapResult.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Player tarPlayer = (Player) mapResult.get("tarPlayer");
		List<Integer> resultList = new ArrayList<Integer>();
		boolean isWin = false;
		player.getIdentity().inAttTimes();
		if (tarPlayer.formation.isEmpty()) {
			resultList.add(-1);// �Է��������ơ�ս�����ݵ�һλΪ-1��ʾս��ֱ��ʤ��
			isWin = true;
		} else {
			resultList = pvpFight(player.formation.getFormation(),
					tarPlayer.formation.getFormation());
			int win = resultList.get(resultList.size() - 1);
			if (win == 1) {
				isWin = true;
			}
		}
		fightEndHandleNormal(player, tarPlayer,isWin);
		return resultList;
	}

	/**
	 * ���������
	 * 
	 * @param attPlayer
	 * @param defPlayer
	 */
	private void getFreeHandle(Player attPlayer, Player defPlayer) {
		attPlayer.becomeFreeMan();
		defPlayer.getIdentity().cutSlave((int) attPlayer.getUserId());
		recordInformation(true, attPlayer, defPlayer, null, 0,
				Information.EVENT_GETFREE);
	}

	/**
	 * ����ս����������
	 * 
	 * @param attPlayer
	 * @param defPlayer
	 * @param isWin
	 */
	private void fighEndHandleReact(Player attPlayer, Player defPlayer,
			boolean isWin) {
		if (isWin) {
			attPlayer.becomeFreeMan();
			defPlayer.getIdentity().cutSlave((int) attPlayer.getUserId());
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
	 */
	private void fightEndHandleNormal(Player attPlayer, Player defPlayer,
			boolean isWin) {
		int eventType = 0;
		Slave slave = null;
		Player attachPlayer = null;
		switch (defPlayer.getIdentity().getGrade()) {
		case Identity.FREEMAN:
			if (isWin) {
				slave = defPlayer.becomeSlave((int) attPlayer.getUserId());
				attPlayer.getIdentity().addSlave(slave);
			}
			eventType = Information.EVENT_FIGHT_FREE;
			break;
		case Identity.OWNER:
			if (isWin) {
				slave = defPlayer.getIdentity().cutSlave();
				attPlayer.getIdentity().addSlave(slave);
				attachPlayer = getTarPlayer(slave.getUserId());
				attachPlayer.getIdentity().setOwnerId(
						(int) attPlayer.getUserId());
			}
			eventType = Information.EVENT_FIGHT_OWNER;
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
	private Map<String, Object> checkAttEnemy(Player player, int tarPlayerId) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (player.getIdentity().getGrade() < Identity.SLAVE
				|| player.getIdentity().getGrade() > Identity.OWNER) {
			mapInfo.put("error", Lang.F2104);
			return mapInfo;
		}
		if (player.getIdentity().getGrade() != Identity.FREEMAN
				&& player.getIdentity().getGrade() != Identity.OWNER) {
			mapInfo.put("error", Lang.F2101);
			return mapInfo;
		}
		if (player.formation.isEmpty()) {
			mapInfo.put("error", Lang.F2103);
			return mapInfo;
		}
		Player tarPlayer = getTarPlayer(tarPlayerId);
		if (tarPlayer.getIdentity().getGrade() == Identity.SLAVE) {
			mapInfo.put("error", Lang.F2102);
			return mapInfo;
		}
		if (tarPlayer.getIdentity().getGrade() < Identity.SLAVE
				|| player.getIdentity().getGrade() > Identity.OWNER) {
			mapInfo.put("error", Lang.F2105);
			return mapInfo;
		}
		if (player.getIdentity().getAttTimes() >= GameCFG.getAttConfine()) {
			mapInfo.put("error", Lang.F2106);
			return mapInfo;
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
			return Lang.F2104;
		}
		if (player.getIdentity().getGrade() != Identity.OWNER
				&& player.getIdentity().getGrade() != Identity.FREEMAN) {
			return Lang.F2101;
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
				|| errorValue == GameCFG.getEnemySize()
						* GameCFG.getMatchTimes()) {
			return;
		}
		Session[] sessions = ds.getSessionMap().getSessions().clone();
		List<Session> sessionList = Arrays.asList(sessions);
		List<Player> rmPlayers = new ArrayList<Player>();
		for (Session session : sessionList) {
			if (session != null) {
				rmPlayers.add(((Player) session.getSource()));
			}
		}
		List<Player> players = dao.getAllPlayer();
		players.removeAll(rmPlayers);
		getIdentityForRAM(sessionList, player, enemyList, errorValue, isSave);
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
						&& tarPlayer.getIdentity().getGrade() == Identity.OWNER) {
					enemyList.add(tarPlayer);
					if (enemyList.size() == GameCFG.getEnemySize()) {
						return;
					}
				}
			} else {
				if (Math
						.abs(tarPlayer.getFightPoint() - player.getFightPoint()) <= errorValue
						&& tarPlayer.getIdentity().getGrade() != Identity.SLAVE) {
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
	private Player getTarPlayer(int userId) {
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

	/**
	 * ��¼��Ϣ����������
	 * 
	 * @param isSuccess
	 *            �¼��Ƿ�ɹ�
	 * @param myPlayer
	 *            �ҵ���Ҷ���
	 * @param tarPlayer
	 *            Ŀ����Ҷ���
	 * @param attachPlayer
	 *            ������Ҷ���
	 * @param attachValue
	 *            ����ֵ
	 * @param eventType
	 *            �¼�����
	 */
	private void recordInformation(boolean isSuccess, Player myPlayer,
			Player tarPlayer, Player attachPlayer, int attachValue,
			int eventType) {
		Information information = null;
		if (eventType == Information.EVENT_FIGHT_FREE
				|| eventType == Information.EVENT_FIGHT_SAVENOR
				|| eventType == Information.EVENT_GETFREE) {
			information = new Information(eventType, isSuccess, myPlayer
					.getNickname(), tarPlayer.getNickname(), null, attachValue);
			myPlayer.getIdentity().addInformation(information);
			sendInformation(tarPlayer, information);
		} else if (eventType == Information.EVENT_FIGHT_OWNER) {
			if (isSuccess) {
				information = new Information(eventType, isSuccess, myPlayer
						.getNickname(), tarPlayer.getNickname(), attachPlayer
						.getNickname(), attachValue);
				sendInformation(tarPlayer, attachPlayer, information);
			} else {
				information = new Information(eventType, isSuccess, myPlayer
						.getNickname(), tarPlayer.getNickname(), null,
						attachValue);
				sendInformation(tarPlayer, information);
			}
			myPlayer.getIdentity().addInformation(information);
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
