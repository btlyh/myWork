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
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.notice.MailSendNotice;
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.dfhm.qualifying.dao.QualifyingDao;
import com.cambrian.dfhm.qualifying.entity.Qualifying;
import com.cambrian.dfhm.qualifying.entity.QualifyingInfo;
import com.cambrian.dfhm.qualifying.timer.QualifyingTimer;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：排位赛逻辑处理类
 * 
 * @author：Zmk
 * 
 */
public class QualifyingManager
{

	/* static fields */
	private static QualifyingManager instance = new QualifyingManager();
	/** 计时器：更新排行信息 */
	private static long TIME = TimeKit.MIN_MILLS * 30;

	/* static methods */
	public static QualifyingManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** 排行榜数据访问对象 */
	QualifyingDao dao;
	/** 数据服务器 */
	DataServer ds;
	/** 排位赛对象 */
	Qualifying qualifying;
	/** 时间格式 */
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/** 邮件工厂 */
	MailFactory mf;
	/** 邮件推送 */
	MailSendNotice msn;

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
	
	public void setMf(MailFactory mf)
	{
		instance.mf = mf;
	}
	public void setMailSendNotice(MailSendNotice msn)
	{
		instance.msn = msn;
	}

	/** 开始定时器 */
	public void timerStart()
	{
		Timer rankingsTimer = new Timer();
		rankingsTimer.schedule(new QualifyingTimer(), 0l, TIME);
	}

	/* init start */

	/* methods */
	/** 进入排位赛 */
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

	/** 获得指定排名可挑战的玩家姓名列表 */
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
				if (getInfo(i) == null)
				{
					qualifying.getQualifyingList().remove(i);
					i--;
					continue;
				}
				QualifyingInfo qualifyingInfo = getInfo(i);
				duelList.add(qualifyingInfo);
				if (duelList.size() >= 6)
					break;
				i++;
				continue;
			}
			if (getInfo(i) == null)
			{
				qualifying.getQualifyingList().remove(i);
				i--;
				continue;
			}
			QualifyingInfo qualifyingInfo = getInfo(i);
			duelList.add(qualifyingInfo);
			if (duelList.size() >= 6)
				break;
		}
		return duelList;
	}

	/** 生成玩家排位赛信息 */
	private QualifyingInfo getInfo(int i)
	{
		QualifyingInfo qualifyingInfo = new QualifyingInfo();
		String name = qualifying.getQualifyingList().get(i);
		Player player = getPlayer(name);
		if (player == null) return null;
		qualifyingInfo.setPlayerName(name);
		qualifyingInfo.setPlayerRanking(i);
		qualifyingInfo.setPlayerVipLevel(player.getVipLevel());
		qualifyingInfo.setPlayerPower(player.getFightPoint());
		qualifyingInfo.setPlayerCardSid(getBestCardSid(player));
		return qualifyingInfo;
	}

	/** 获得玩家 */
	private Player getPlayer(String name)
	{
		Session session = ds.getSession(name);
		if (session != null)
			return (Player) session.getSource();
		Player player = dao.getPlayer(name, session);
		return player;
	}

	/** 获得在阵卡牌中最强的卡牌 */
	private int getBestCardSid(Player player)
	{
		if (!player.formation.isEmpty())
		{
			Card bestCard = null;
			for (BattleCard bCard : player.formation.getFormation())
			{
				if (bCard == null)
					continue;
				if (bestCard == null)
				{
					bestCard = player.getCardBag().getById(bCard.getId());
					continue;
				}
				Card c = player.getCardBag().getById(bCard.getId());
				if (c.getZhandouli() > bestCard.getZhandouli())
				{
					bestCard = c;
				}
			}
			return bestCard.getSid();
		}else
		{
			return player.getCardBag().getBestCardSid();
		}
		
		
//		int uid = player.formation.getBestCardUid();
//		Card card = player.getCardBag().getById(uid);
//		if (card == null)
//			return 10001;
//		return card.getSid();
	}

	/** 挑战对手 */
	public Map<String, Object> duel(Player player, String tarName, int useGold)
	{
		String error = checkDuel(player, useGold);
		if (error != null)
			throw new DataAccessException(601, error);
		if (useGold != 0)
		{
			player.decrGold(useGold);
			player.getPlayerInfo().incrDuelFreeTimes(1);
			player.getPlayerInfo().incrDuelBuyTimes(1);
		}
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
			player.getPlayerInfo().incrQualifyingWin(1);
			changeRanking(player.getNickname(), tarName);
			log = "失败";
			Mail mail = mf.createFightBackMail(player.getNickname()+"挑战并战胜了你", Mail.MAILSTATE_UNREAD);
			mail.setFight(true);
			tarPlayer.addMail(mail);
			Session session = ds.getSession(tarName);
			if (session != null)
				msn.send(ds.getSession(tarName), new Object[]{tarPlayer.getUnreadMailCount()});
		} else
		{
			BattleScene scene = new BattleScene();
			battleInit(playerBattleCards, tarBattleCards);
			scene.setMaxRound(30);
			scene.start(playerBattleCards, tarBattleCards,
					BattleScene.FIGHT_NORMAL);
			scene.getRecord().set(0, scene.getStep());
			int win = scene.getRecord().get(scene.getRecord().size() - 1);
			System.err.println("win====="+win);
			if (win > 0)
			{
				int rank = qualifying.getPlayerRank(tarName);
				if (rank > GameCFG.getDuelWinPoint().length)
					rank = GameCFG.getDuelWinPoint().length -1;
				point = GameCFG.getDuelWinPoint(rank);
				player.getPlayerInfo().incrNormalPoint(point);
				player.getPlayerInfo().incrQualifyingWin(1);
				changeRanking(player.getNickname(), tarName);
				log = "失败";
				Mail mail = mf.createFightBackMail(player.getNickname()+"挑战并战胜了你", Mail.MAILSTATE_UNREAD);
				mail.setFight(true);
				tarPlayer.addMail(mail);
				Session session = ds.getSession(tarName);
				if (session != null)
					msn.send(ds.getSession(tarName), new Object[]{tarPlayer.getUnreadMailCount()});
			} else
			{
				int rank = qualifying.getPlayerRank(tarName);
				if (rank >= GameCFG.getDuelLosePoint().length)
					rank = GameCFG.getDuelLosePoint().length - 1;
				point = GameCFG.getDuelLosePoint(rank);
				player.getPlayerInfo().incrNormalPoint(point);
				log = "胜利";
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
	/** 检查挑战 */
	private String checkDuel(Player player, int useGold)
	{
		if (player.getPlayerInfo().getDuelFreeTimes() < 1)
		{
			if (useGold != 10 * (player.getPlayerInfo().getDuelBuyTimes()+1))
			{
				return Lang.F2101; // 金钱错误
			}
			if (player.getGold() < useGold)
			{
				return Lang.F2003; // 金钱不足
			}
		}
		BattleCard[] playerBattleCards = player.formation.getFormation();
		int i = 0;
		for (BattleCard battleCard : playerBattleCards)
		{
			if (battleCard == null)
				i++;
		}
		if (i >= 5)
			return Lang.F2102; //没有卡牌在阵上
		return null;
	}

	/** 获得一条记录字符串 */
	private String getLog(String log, String name)
	{
		Date nowTime = new Date();
		String date = dateFormat.format(nowTime);
		String historyLog = date + " (" + name + ")挑战了你，你战斗" + log;
		return historyLog;
	}

	/** 交换排名 */
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
	 * 战斗前初始化
	 * 
	 * @param att
	 * @param def
	 * @param isGlobalBoss
	 *            是否攻击世界BOSS
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

	/** 购买挑战次数 */
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

	/** 检查购买挑战次数 */
	private String checkBuyDuelTimes(Player player, int useGold)
	{
		// if (player.getPlayerInfo().getDuelFreeTimes() == 10)
		// {
		// return Lang.F1100; // 次数已满，不能购买
		// }
		
//		if (useGold != 5 * (int) Math.pow(2, player.getPlayerInfo()
//				.getDuelBuyTimes()))
		if (useGold != 10 * (player.getPlayerInfo().getDuelBuyTimes()+1))
		{
			return Lang.F2101; // 金钱错误
		}
		if (player.getGold() < useGold)
		{
			return Lang.F2003; // 金钱不足
		}
		return null;
	}

	/** 领取每日积分奖励 */
	public int getPointGift(Player player)
	{
		String error = checkGetPointGift(player);
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		int rank = qualifying.getPlayerRank(player.getNickname());
		if (rank > GameCFG.getDayPoint().length - 1)
			rank = GameCFG.getDayPoint().length - 1;
		int point = GameCFG.getDayPoint(rank);
		player.getPlayerInfo().incrNormalPoint(point);
		player.getPlayerInfo().setCanTakePoint(1);
		return point;
	}
	/** 检查领取积分 */
	private String checkGetPointGift(Player player)
	{
		if (player.getPlayerInfo().getCanTakePoint() == 1)
		{
			return Lang.F2406;//已经领取过了
		}
		return null;
	}

	/** 添加玩家 */
	public void addPlayer(String nickname)
	{
		qualifying.addPlayer(nickname);
	}

	/** 刷新前三名信息 */
	public void flushTopList()
	{
		if (qualifying == null)
		{
			qualifying = new Qualifying();
			if (dao.getQualifying() != null)
				qualifying=dao.getQualifying();
		}
		List<QualifyingInfo> topList = qualifying.getTopList();
		topList.clear();
		for (int i = 0; i < qualifying.getQualifyingList().size(); i++)
		{
			if (topList.size() > 2)
				break;
			if (getInfo(i) == null)
			{
				qualifying.getQualifyingList().remove(i);
				i--;
				continue;
			}
			topList.add(getInfo(i));
		}
		saveQualifying();
	}

	/** 保存排位赛信息 */
	private void saveQualifying()
	{
		dao.setQualifying(qualifying);
	}
}
