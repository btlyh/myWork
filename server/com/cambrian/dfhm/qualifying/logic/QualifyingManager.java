package com.cambrian.dfhm.qualifying.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.qualifying.dao.QualifyingDao;
import com.cambrian.dfhm.qualifying.entity.Qualifying;
import com.cambrian.dfhm.qualifying.entity.QualifyingInfo;
import com.cambrian.dfhm.qualifying.timer.QualifyingTimer;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵������λ���߼�������
 * 
 * @author��Zmk
 * 
 */
public class QualifyingManager
{

	/* static fields */
	private static QualifyingManager instance = new QualifyingManager();
	/** ��ʱ��������������Ϣ */
	private static long TIME = TimeKit.MIN_MILLS * 5;

	/* static methods */
	public static QualifyingManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** ���а����ݷ��ʶ��� */
	QualifyingDao dao;
	/** ���ݷ����� */
	DataServer ds;
	/** ��λ������ */
	Qualifying qualifying = new Qualifying();
	/** ʱ���ʽ */
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/* constructors */

	/* properties */
	public void setQualifyingDao(QualifyingDao dao)
	{
		instance.dao = dao;
	}

	public void setDS(DataServer ds)
	{
		instance.ds = ds;
	}

	/** ��ʼ��ʱ�� */
	public void timerStart()
	{
		Timer rankingsTimer = new Timer();
		rankingsTimer.schedule(new QualifyingTimer(), 0l, TIME);
	}

	/* init start */

	/* methods */
	/** ������λ�� */
	public Map<String, Object> enterQualifying(Player player)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<QualifyingInfo> topList = qualifying.getTopList();
		int index = qualifying.getPlayerRank(player.getNickname());
		List<QualifyingInfo> duelList = getDuelList(index);
		resultMap.put("topList", topList);
		resultMap.put("duelList", duelList);
		resultMap.put("index", index);
		return resultMap;
	}

	/** ���ָ����������ս����������б� */
	public List<QualifyingInfo> getDuelList(int index)
	{
		List<QualifyingInfo> duelList = new ArrayList<QualifyingInfo>();
		int i = index < 7 ? 0 : index;
		while (i < qualifying.getQualifyingList().size()
				&& duelList.size() < qualifying.getQualifyingList().size())
		{
			if (index > 1000)
			{
				i -= 10;
			} else if (index > 500)
			{
				i -= 5;
			} else if (index > 100)
			{
				i -= 3;
			} else if (index > 50)
			{
				i -= 2;
			} else if (index > 6)
			{
				i -= 1;
			} else
			{
				if (i == index)
				{
					i++;
					continue;
				}
				QualifyingInfo qualifyingInfo = getInfo(i);
				duelList.add(qualifyingInfo);
				if (duelList.size() >= 4)
					break;
				i++;
				continue;
			}
			QualifyingInfo qualifyingInfo = getInfo(i);
			duelList.add(qualifyingInfo);
			if (duelList.size() >= 4)
				break;
		}
		return duelList;
	}

	/** ���������λ����Ϣ */
	private QualifyingInfo getInfo(int i)
	{
		QualifyingInfo qualifyingInfo = new QualifyingInfo();
		String name = qualifying.getQualifyingList().get(i);
		Player player = getPlayer(name);
		qualifyingInfo.setPlayerName(name);
		qualifyingInfo.setPlayerRanking(i);
		qualifyingInfo.setPlayerVipLevel(player.getVipLevel());
		qualifyingInfo.setPlayerPower(player.getFightPoint());
		qualifyingInfo.setPlayerCardSid(getBestCardSid(player));
		return qualifyingInfo;
	}

	/** ������ */
	private Player getPlayer(String name)
	{
		Session session = ds.getSession(name);
		if (session != null)
			return (Player) session.getSource();
		Player player = dao.getPlayer(name, session);
		return player;
	}

	/** �������������ǿ�Ŀ��� */
	private int getBestCardSid(Player player)
	{
		int uid = player.formation.getBestCardUid();
		Card card = player.getCardBag().getById(uid);
		if (card == null)
			return 10001;
		return card.getSid();
	}

	/** ��ս���� */
	public Map<String, Object> duel(Player player, String tarName)
	{
		String error = checkDuel(player);
		if (error != null)
			throw new DataAccessException(601, error);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Player tarPlayer = getPlayer(tarName);
		BattleCard[] playerBattleCards = player.formation.getFormation();
		BattleCard[] tarBattleCards = tarPlayer.formation.getFormation();
		int tarBCardNull = 0;
		int point = 0;
		String log = "";
		for (BattleCard battleCard : tarBattleCards)
		{
			if (battleCard == null)
				tarBCardNull++;
		}
		if (tarBCardNull >= 5)
		{
			int rank = qualifying.getPlayerRank(tarName);
			if (rank > GameCFG.getDuelWinPoint().length)
				rank = GameCFG.getDuelWinPoint().length - 1;
			point = GameCFG.getDuelWinPoint(rank);
			player.getPlayerInfo().incrNormalPoint(point);
			changeRanking(player.getNickname(), tarName);
			log = "ʧ��";
		} else
		{
			BattleScene scene = new BattleScene();
			battleInit(playerBattleCards, tarBattleCards);
			scene.setMaxRound(5);
			scene.start(playerBattleCards, tarBattleCards,
					BattleScene.FIGHT_NORMAL);
			scene.getRecord().set(0, scene.getStep());
			int win = scene.getRecord().get(scene.getRecord().size() - 1);
			System.err.println("win====="+win);
			if (win == 1)
			{
				int rank = qualifying.getPlayerRank(tarName);
				if (rank > GameCFG.getDuelWinPoint().length)
					rank = GameCFG.getDuelWinPoint().length -1;
				point = GameCFG.getDuelWinPoint(rank);
				player.getPlayerInfo().incrNormalPoint(point);
				player.getPlayerInfo().incrQualifyingWin(1);
				changeRanking(player.getNickname(), tarName);
				log = "ʧ��";
			} else
			{
				int rank = qualifying.getPlayerRank(tarName);
				if (rank >= GameCFG.getDuelLosePoint().length)
					rank = GameCFG.getDuelLosePoint().length - 1;
				point = GameCFG.getDuelLosePoint(rank);
				player.getPlayerInfo().incrNormalPoint(point);
				log = "ʤ��";
			}
			tarPlayer.getPlayerInfo().addEnemy(player.getNickname());
			tarPlayer.getPlayerInfo().addQualifyingLog(
					getLog(log, player.getNickname()));
			resultMap.put("record", scene.getRecord());
			resultMap.put("battleCards", tarBattleCards);
		}
		player.getPlayerInfo().decrDuelFreeTimes(1);
		player.getPlayerInfo().incrQualifyingCount(1);
		resultMap.put("point", point);
		return resultMap;
	}
	/** �����ս */
	private String checkDuel(Player player)
	{
		if (player.getPlayerInfo().getDuelFreeTimes() < 1)
		{
			return Lang.F2105; //û����ս����
		}
		BattleCard[] playerBattleCards = player.formation.getFormation();
		int i = 0;
		for (BattleCard battleCard : playerBattleCards)
		{
			if (battleCard == null)
				i++;
		}
		if (i >= 5)
			return Lang.F2102; //û�п���������
		return null;
	}

	/** ���һ����¼�ַ��� */
	private String getLog(String log, String name)
	{
		Date nowTime = new Date();
		String date = dateFormat.format(nowTime);
		String historyLog = date + " (" + name + ")��ս���㣬��ս��" + log;
		return historyLog;
	}

	/** �������� */
	private void changeRanking(String nickname, String tarName)
	{
		int att = qualifying.getPlayerRank(nickname);
		int def = qualifying.getPlayerRank(tarName);
		if (def < att)
		{
			String temp = nickname;
			qualifying.getQualifyingList().set(att,
					qualifying.getQualifyingList().get(def));
			qualifying.getQualifyingList().set(def, temp);
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
	private void battleInit(BattleCard[] att, BattleCard[] def)
	{
		for (BattleCard battleCard : att)
		{
			if (battleCard != null)
			{
				battleCard.setSide(1);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
		for (BattleCard battleCard : def)
		{
			if (battleCard != null)
			{
				battleCard.setSide(2);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
	}

	/** ������ս���� */
	public void buyDuelTimes(Player player, int useGold)
	{
		String error = checkBuyDuelTimes(player, useGold);
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		player.decrGold(useGold);
		player.getPlayerInfo().incrDuelFreeTimes(1);
		player.getPlayerInfo().incrDuelBuyTimes(1);
	}

	/** ��鹺����ս���� */
	private String checkBuyDuelTimes(Player player, int useGold)
	{
		// if (player.getPlayerInfo().getDuelFreeTimes() == 10)
		// {
		// return Lang.F1100; // �������������ܹ���
		// }
		if (useGold != 5 * (int) Math.pow(2, player.getPlayerInfo()
				.getDuelBuyTimes()))
		{
			return Lang.F2101; // ��Ǯ����
		}
		return null;
	}

	/** ��ȡÿ�ջ��ֽ��� */
	public int getPointGift(Player player)
	{
		int rank = qualifying.getPlayerRank(player.getNickname());
		if (rank > GameCFG.getDayPoint().length - 1)
			rank = GameCFG.getDayPoint().length - 1;
		int point = GameCFG.getDayPoint(rank);
		player.getPlayerInfo().incrNormalPoint(point);
		player.getPlayerInfo().setCanTakePoint(0);
		return point;
	}

	/** ������ */
	public void addPlayer(String nickname)
	{
		qualifying.addPlayer(nickname);
	}

	/** ˢ��ǰ������Ϣ */
	public void flushTopList()
	{
//		 if (dao.getQualifying() != null)
//		 qualifying=dao.getQualifying();
		List<QualifyingInfo> topList = qualifying.getTopList();
		topList.clear();
		for (int i = 0; i < qualifying.getQualifyingList().size(); i++)
		{
			if (i > 2)
				break;
			topList.add(getInfo(i));
		}
		saveQualifying();
	}

	/** ������λ����Ϣ */
	private void saveQualifying()
	{
		dao.setQualifying(qualifying);
	}
}
