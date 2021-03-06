package com.cambrian.dfhm.armyCamp.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.MathKit;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.armyCamp.dao.ArmyCampDao;
import com.cambrian.dfhm.armyCamp.entity.ArmyCamp;
import com.cambrian.dfhm.armyCamp.entity.SeatCard;
import com.cambrian.dfhm.armyCamp.notice.RemoveMyCardNotice;
import com.cambrian.dfhm.armyCamp.notice.UseAwakeSoupNotice;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.notice.MailSendNotice;
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：军帐业务逻辑处理类
 * 
 * @author：Zmk
 * 
 */
public class ArmyCampManager
{

	/* static fields */
	private static ArmyCampManager instance = new ArmyCampManager();

	/* static methods */
	public static ArmyCampManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** 军帐数据访问对象 */
	ArmyCampDao dao;
	/** 数据服务器 */
	DataServer ds;
	/** 自动移除卡牌推送 */
	RemoveMyCardNotice rmcn;
	/** 醒酒汤推送 */
	UseAwakeSoupNotice uasn;
	/** 邮件工厂 */
	MailFactory mf;
	/** 邮件推送 */
	MailSendNotice msn;
	/** 时间格式 */
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	public void setArmyCampDao(ArmyCampDao dao)
	{
		instance.dao = dao;
	}

	public void setDS(DataServer ds)
	{
		instance.ds = ds;
	}

	public void setRemoveMyCardNotice(RemoveMyCardNotice rmcn)
	{
		instance.rmcn = rmcn;
	}

	public void setUseAwakeSoupNotice(UseAwakeSoupNotice uasn)
	{
		instance.uasn = uasn;
	}
	
	public void setMf(MailFactory mf)
	{
		instance.mf = mf;
	}
	public void setMailSendNotice(MailSendNotice msn)
	{
		instance.msn = msn;
	}

	/**
	 * 得到当前玩家的历史记录集合
	 * 
	 * @param player
	 *            当前玩家
	 * @return 当前玩家的历史记录集合
	 */
	public ArrayList<String> getHitoryLog(Player player)
	{
		ArrayList<String> log = player.getArmyCamp().getHistoryLogList();
		if (log == null)
		{
			log = new ArrayList<String>();
		}
		return log;
	}

	/**
	 * 卡牌进入座位喝酒
	 * 
	 * @param player
	 *            当前玩家
	 * @param userName
	 *            军帐所属玩家名
	 * @param cardUid
	 *            要喝酒的卡牌UID
	 * @param sitNum
	 */
	public void setCardToDrink(Player player, String userName, int cardUid,
			int sitNum)
	{

		Map<String, Object> resultMap = checkSetCardToDrink(player, userName,
				cardUid, sitNum);
		String error = (String) resultMap.get("error");
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		Card card = player.getCardBag().getById(cardUid);
		ArmyCamp tarArmyCamp = (ArmyCamp) resultMap.get("armyCamp");
		SeatCard seatCard = new SeatCard();
		if (player.getNickname().equals(userName))
		{
			List<SeatCard> privateList = tarArmyCamp.getPrivateList();
			seatCard.setSeatId(sitNum);
			seatCard.setCardSid(card.getSid());
			seatCard.setCardUid(cardUid);
			seatCard.setOwnerName(player.getNickname());
			privateList.add(seatCard);
			tarArmyCamp.setPrivateList(privateList);
		} else
		{
			List<SeatCard> publicList = tarArmyCamp.getPublicList();
			seatCard.setSeatId(sitNum);
			seatCard.setCardSid(card.getSid());
			seatCard.setCardUid(cardUid);
			seatCard.setOwnerName(player.getNickname());
			publicList.add(seatCard);
			System.err.println("publicList.size()====" + publicList.size());
			tarArmyCamp.setPublicList(publicList);
		}
		card.setLastDrinkTime(TimeKit.nowTimeMills());
		card.setInArmyCamp(1);
		card.setDrinkStatus(Card.HYPER);
		player.formation.setBattleCardDrink(card.getId(), Card.HYPER);
		card.setArmyName(userName);
		if (player.getNickname().equals(userName))
			setArmyCamp(tarArmyCamp, userName);
		else
		{
			int userId = dao.getUserIdByName(userName, ds.getSession(userName));
			dao.setArmyCamp(tarArmyCamp, userId);
		}
		
	
		
	}

	/** 检查卡牌进入座位喝酒 */
	private Map<String, Object> checkSetCardToDrink(Player player,
			String userName, int cardUid, int sitNum)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (userName.length() < 1 || userName == null)
		{
			mapInfo.put("error", Lang.F1701); // 玩家名称不正确
			return mapInfo;
		}
		Card card = player.getCardBag().getById(cardUid);
		System.err.println("status====" + card.getDrinkStatus());
		if (card.getDrinkStatus() != Card.AWAKE)
		{
			mapInfo.put("error", Lang.F1702);
			return mapInfo; // 当前卡牌状态不正确
		}
		if (player.getCardBag().getById(cardUid) == null)
		{
			mapInfo.put("error", Lang.F1703);
			return mapInfo; // 背包中没有此卡牌
		}
		ArmyCamp tarArmyCamp = null;
		if (player.getNickname().equals(userName))
		{
			tarArmyCamp = player.getArmyCamp();
			if (tarArmyCamp.getPrivateSeatSize()
					- tarArmyCamp.getPrivateList().size() < 1)
			{
				mapInfo.put("error", Lang.F1704);
				return mapInfo; // 私人座位满员
			}
		} else
		{
			int tarPlayerId = dao.getUserIdByName(userName, ds.getSession(userName));
			if (!player.getFriendInfo().getFriendList().contains(tarPlayerId))
			{
				mapInfo.put("error", Lang.F1717);
				return mapInfo; // 对方好友列表中已经删除了你
			}
			tarArmyCamp = getArmyCamp(userName);
			if (tarArmyCamp.getPublicSeatSize()
					- tarArmyCamp.getPublicList().size() < 1)
			{
				mapInfo.put("error", Lang.F1705);
				return mapInfo; // 对方公共座位已满
			}
		}
		if (tarArmyCamp.getSeatCardById(cardUid) != null)
		{
			mapInfo.put("error", Lang.F1706);
			return mapInfo; // 卡牌已经在座位中
		}
		if (sitNum > 7 || sitNum < 0)
		{
			mapInfo.put("error", Lang.F1716);
			return mapInfo; // 座位号错误
		}
		if (tarArmyCamp.getSeatCardBySeatId(sitNum) != null)
		{
			mapInfo.put("error", Lang.F1707);
			return mapInfo; // 当前座位已经有卡牌了
		}
		mapInfo.put("error", null);
		mapInfo.put("armyCamp", tarArmyCamp);
		return mapInfo;
	}

	/**
	 * 闲逛
	 * 
	 * @param session
	 *            当前玩家会话
	 * @return
	 */
	public Player walkAround(Player player, Session session)
	{
		Session[] sessions = ds.getSessionMap().getSessions();
		List<Session> sessionList = new ArrayList<Session>();
		for (Session session2 : sessions)
		{
			if (session2 != null)
				sessionList.add(session2);
		}
		List<Integer> firends = new ArrayList<Integer>();
		for (Integer integer : player.getFriendInfo().getFriendList())
		{
			firends.add(integer);
		}
		firends.add((int) player.getUserId());
		List<Session> kickSessionList = new ArrayList<Session>();
		for (int i = 0; i < firends.size(); i++)
		{
			Session firendSession = ds.getSession(firends.get(i));
			if (firendSession != null)
			{
				kickSessionList.add(firendSession);
			}
		}
		while (sessionList.size()>0)
		{
			Session randomSession = sessionList.get(MathKit.randomValue(0,
					sessionList.size()));
			if (!kickSessionList.contains(randomSession)
					&& player.getArmyCamp().getWalkSession() != randomSession)
			{
				player.getArmyCamp().setWalkSession(randomSession);
				return (Player) randomSession.getSource();
			}
			sessionList.remove(randomSession);
		}
		return null;
	}

	/**
	 * 从私人座位移除卡牌
	 * 
	 * @param player
	 *            当前玩家
	 * @param cardUid
	 *            卡牌uid
	 */
	public void removeMyCard(Player player, int cardUid)
	{
		SeatCard seatCard = player.getArmyCamp().getSeatCardById(cardUid);
		Card card = player.getCardBag().getById(cardUid);
		String error = checkRemoveMyCard(player, seatCard);
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		List<SeatCard> privateList = player.getArmyCamp().getPrivateList();
		card.setDrinkStatus(Card.AWAKE);
		player.formation.setBattleCardDrink(card.getId(), Card.AWAKE);
		card.setInArmyCamp(0);
		privateList.remove(seatCard);
		player.getArmyCamp().setPrivateList(privateList);
	}

	/** 检查移除卡牌 */
	private String checkRemoveMyCard(Player player, SeatCard seatCard)
	{
		Card card = player.getCardBag().getById(seatCard.getCardUid());
		if (card == null)
		{
			return Lang.F1200; // 卡牌uid错误
		}
		if (TimeKit.nowTimeMills() - card.getLastDrinkTime() < ArmyCamp.DRINK_CD)
		{
			return Lang.F1708; // 卡牌还在冷却中
		}
		return null;
	}

	/**
	 * 付费醒酒
	 * 
	 * @param player
	 *            当前玩家
	 * @param userName
	 *            所属军帐玩家名字
	 * @param cardUid
	 *            卡牌uid
	 * @param useGold
	 *            所需要的金钱
	 * @param b
	 *            是否是自动喝酒，用来控制付费醒酒的判断
	 */
	public void payForAwake(Player player, String userName, int cardUid,
			int useGold, boolean b)
	{
		Map<String, Object> resultMap = checkPayForAwake(player, userName,
				cardUid, useGold, b);
		String error = (String) resultMap.get("error");
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		Card card = (Card) resultMap.get("card");
		card.setDrinkStatus(Card.AWAKE);
		player.formation.setBattleCardDrink(card.getId(), Card.AWAKE);
		card.setInArmyCamp(0);
		ArmyCamp tarArmyCamp = (ArmyCamp) resultMap.get("armyCamp");
		SeatCard seatCard = (SeatCard) resultMap.get("seatCard");
		tarArmyCamp.removeSeatCard(seatCard);
		setArmyCamp(tarArmyCamp, userName);
		player.decrGold(useGold);
	}

	/**
	 * 检查付费醒酒
	 * 
	 * @param b
	 */
	private Map<String, Object> checkPayForAwake(Player player,
			String userName, int cardUid, int useGold, boolean b)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		Card card = player.getCardBag().getById(cardUid);
		ArmyCamp tarArmyCamp = getArmyCamp(userName);
		SeatCard seatCard = tarArmyCamp.getSeatCardById(cardUid);
		if (player.getPlayerInfo().getPayForAwake() != 1)
		{
			mapInfo.put("error", Lang.F1714);
			return mapInfo; // 玩家VIP等级不足
		}
		if (card.getDrinkStatus() != Card.DRUNK && !b)
		{
			mapInfo.put("error", Lang.F1715);
			return mapInfo; // 卡牌状态不正确
		}
		if (!tarArmyCamp.getPrivateList().contains(seatCard)
				&& !tarArmyCamp.getPublicList().contains(seatCard))
		{
			mapInfo.put("error", Lang.F1709);
			return mapInfo; // 卡牌不再座位中
		}
		if (userName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // 姓名错误
		}
		if (useGold != countUseGold(card.getLastDrinkTime()))
		{
			mapInfo.put("error", Lang.F1710);
			return mapInfo; // 所需金币与服务端不服
		}
		if (player.getGold() < useGold)
		{
			mapInfo.put("error", Lang.F1713);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("card", card);
		mapInfo.put("armyCamp", tarArmyCamp);
		mapInfo.put("seatCard", seatCard);
		return mapInfo;
	}

	/**
	 * 使用醒酒汤
	 * 
	 * @param player
	 *            当前玩家
	 * @param userName
	 *            当前军帐玩家名字
	 */
	public void useAwakeSoup(Player player, String userName)
	{
		Map<String, Object> resultMap = checkUseAwakeSoup(player, userName);
		String error = (String) resultMap.get("error");
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		ArmyCamp armyCamp = player.getArmyCamp();
		ArmyCamp tarArmyCamp = (ArmyCamp) resultMap.get("tarArmyCamp");
		decrDrinkCd(armyCamp, GameCFG.getAwakeSoupOwnCdTime());
		decrDrinkCd(tarArmyCamp, GameCFG.getAwakeSoupTarCdTime());
		Date nowTime = new Date();
		String date = dateFormat.format(nowTime);
		tarArmyCamp.addHistoryLog(date + " (" + player.getNickname()
				+ ") 和你共享了醒酒汤，你军帐中武将的喝酒冷却时间减少了30%");
		tarArmyCamp.setLastUseAwakeSoupTime(TimeKit.nowTimeMills());
		setArmyCamp(armyCamp, player.getNickname());
		setArmyCamp(tarArmyCamp, userName);
		Session noticeSession = ds.getSession(userName);
		if (noticeSession != null)
		{
			Player tarPlayer = (Player)noticeSession.getSource();
			uasn.send(noticeSession, new Object[] { tarPlayer });
		}
	}

	/** 减少卡牌冷却时间 */
	private void decrDrinkCd(ArmyCamp armyCamp, float per)
	{
		for (SeatCard seatCard : armyCamp.getPrivateList())
		{
			CardBag cardBag = getCardBag(seatCard.getOwnerName());
			Card card = cardBag.getById(seatCard.getCardUid());
			card.setLastDrinkTime(card.getLastDrinkTime()
					- (long) ((ArmyCamp.DRINK_CD - (TimeKit.nowTimeMills() - card
							.getLastDrinkTime())) * per));
		}
		for (SeatCard seatCard : armyCamp.getPublicList())
		{
			CardBag cardBag = getCardBag(seatCard.getOwnerName());
			Card card = cardBag.getById(seatCard.getCardUid());
			card.setLastDrinkTime(card.getLastDrinkTime()
					- (long) ((ArmyCamp.DRINK_CD - (TimeKit.nowTimeMills() - card
							.getLastDrinkTime())) * per));
		}
	}

	/** 检查使用醒酒汤 */
	private Map<String, Object> checkUseAwakeSoup(Player player, String userName)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (userName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // 玩家姓名错误
		}
		int userId = dao.getUserIdByName(userName, ds.getSession(userName));
		if (!player.getFriendInfo().getFriendList().contains(userId))
		{
			mapInfo.put("error", Lang.F1711);
			return mapInfo; // 该玩家不是你的好友
		}
		ArmyCamp tarArmyCamp = getArmyCamp(userName);
		if (TimeKit.nowTimeMills() - tarArmyCamp.getLastUseAwakeSoupTime() < ArmyCamp.SOUP_CDTIME)
		{
			mapInfo.put("error", Lang.F1712);
			return mapInfo; // 该军帐醒酒汤冷却时间未结束
		}
		mapInfo.put("error", null);
		mapInfo.put("tarArmyCamp", tarArmyCamp);
		return mapInfo;
	}

	/**
	 * 进入军帐
	 * 
	 * @param player
	 *            当前玩家
	 * @param userName
	 *            进入的军帐名字
	 * @return
	 */
	public Map<String, Object> enterArmyCamp(Player player, String userName)
	{
		Map<String, Object> resultMap = checkEnterArmyCamp(player, userName);
		String error = (String) resultMap.get("error");
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		List<Card> armyCardList = new ArrayList<Card>();
		for (Card card : player.getCardBag().getList())
		{
			if(card.isInArmyCamp()==1 && !card.getArmyName().equals(player.getNickname()))
			{
				armyCardList.add(card);
			}
		}
		for (Card card : armyCardList)
		{
			if (TimeKit.nowTimeMills() - card.getLastDrinkTime() >= ArmyCamp.DRINK_CD)
			{
				ArmyCamp otherArmyCamp = getArmyCamp(card.getArmyName());
				SeatCard seatCard = otherArmyCamp.getSeatCardById(card.getId());
				otherArmyCamp.removeSeatCard(seatCard);
				setArmyCamp(otherArmyCamp, card.getArmyName());
				card.setDrinkStatus(Card.AWAKE);
				player.formation.setBattleCardDrink(card.getId(), Card.AWAKE);
				card.setInArmyCamp(0);
				card.setArmyName("");
				String s = "亲爱的玩家您好，在您自己军帐中的" + card.getName()
						+ "卡牌冷却时间已到，系统已自动移除。祝您游戏愉快！";
				Mail mail = mf.createSystemMailNothing(s);
				player.addMail(mail);
				Session noticeSession = ds.getSession(player.getNickname());
				if (noticeSession != null)
				{
					msn.send(noticeSession, new Object[] {player.getUnreadMailCount()});
					rmcn.send(noticeSession, new Object[] {card.getId()});
				}
			}
		}
		ArmyCamp armyCamp = getArmyCamp(userName);
		Player armyPlayer = dao.getPlayer(userName, ds.getSession(userName));
		List<SeatCard> publicList = armyCamp.getPublicList();
		List<SeatCard> privateList = armyCamp.getPrivateList();
		List<Card> cardList = new ArrayList<Card>();
		for (int i = 0; i < publicList.size(); i++)
		{
			SeatCard seatCard = publicList.get(i);
			CardBag cardBag = getCardBag(seatCard.getOwnerName());
			Card card = cardBag.getById(seatCard.getCardUid());
			Player tarPlayer;
			Session session = ds.getSession(seatCard.getOwnerName());
			if (session != null)
				tarPlayer = (Player) session.getSource();
			else
				tarPlayer = dao.getPlayer(seatCard.getOwnerName(), session);
			cardList.add(card);
			if (TimeKit.nowTimeMills() - card.getLastDrinkTime() >= ArmyCamp.DRINK_CD)
			{
				card.setDrinkStatus(Card.AWAKE);
				tarPlayer.formation.setBattleCardDrink(card.getId(), Card.AWAKE);
				card.setInArmyCamp(0);
				card.setArmyName("");
				cardList.remove(card);
				setCardBag(cardBag, seatCard.getOwnerName());
				publicList.remove(seatCard);
				armyCamp.setPublicList(publicList);
				setArmyCamp(armyCamp, userName);
				String s = "亲爱的玩家您好，您在" + card.getArmyName() + "军帐中的"
						+ card.getName() + "卡牌冷却时间已到，系统已自动移除。祝您游戏愉快！";
				Mail mail = mf.createSystemMailNothing(s);
				tarPlayer.addMail(mail);
				if (session != null)
				{
					msn.send(session, new Object[] {tarPlayer.getUnreadMailCount()});
					rmcn.send(session, new Object[] {card.getId()});
				}
				// Session noticeSession =
				// ds.getSession(seatCard.getOwnerName());
				// if (noticeSession != null)
				// {
				// rmcn.send(noticeSession, new Object[] { card.uid });
				// }
			}
		}
		for (int i = 0; i < privateList.size(); i++)
		{
			SeatCard seatCard = privateList.get(i);
			CardBag cardBag = getCardBag(seatCard.getOwnerName());
			Card card = cardBag.getById(seatCard.getCardUid());
			if (TimeKit.nowTimeMills() - card.getLastDrinkTime() >= ArmyCamp.DRINK_CD)
			{
				card.setDrinkStatus(Card.AWAKE);
				player.formation.setBattleCardDrink(card.getId(), Card.AWAKE);
				card.setInArmyCamp(0);
				card.setArmyName("");
				setCardBag(cardBag, seatCard.getOwnerName());
				privateList.remove(seatCard);
				armyCamp.setPrivateList(privateList);
				setArmyCamp(armyCamp, userName);
				String s = "亲爱的玩家您好，在您自己军帐中的" + card.getName()
						+ "卡牌冷却时间已到，系统已自动移除。祝您游戏愉快！";
				Mail mail = mf.createSystemMailNothing(s);
				player.addMail(mail);
				Session noticeSession = ds.getSession(player.getNickname());
				if (noticeSession != null)
				{
					msn.send(noticeSession, new Object[] {player.getUnreadMailCount()});
					rmcn.send(noticeSession, new Object[] {card.getId()});
				}
			}
		}
		resultMap.put("player", armyPlayer);
		resultMap.put("cardList", cardList);
		resultMap.put("armyCamp", armyCamp);
		return resultMap;
	}

	/** 检查进入军帐 */
	private Map<String, Object> checkEnterArmyCamp(Player player,
			String userName)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (userName == null || userName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // 玩家姓名错误
		}
		Session session = ds.getSession(userName);
		int userId = dao.getUserIdByName(userName, session);
		if (!userName.equals(player.getNickname())
				&& !player.getFriendInfo().getFriendList().contains(userId))
		{
			mapInfo.put("error", Lang.F1711);
			return mapInfo; // 无法进入该玩家军帐
		}
		mapInfo.put("error", null);
		return mapInfo;
	}

	/**
	 * 查询卡牌信息
	 * 
	 * @param ownerName
	 *            卡牌所属玩家名字
	 * @param cardUid
	 *            卡牌uid
	 * @return
	 */
	public Card cardInfo(String ownerName, int cardUid)
	{
		Map<String, Object> resultMap = checkCardInfo(ownerName, cardUid);
		String error = (String) resultMap.get("error");
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		Card card = (Card) resultMap.get("card");
		return card;
	}

	/** 检查查询卡牌 */
	private Map<String, Object> checkCardInfo(String ownerName, int cardUid)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (ownerName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // 姓名错误
		}
		Card card = getCardBag(ownerName).getById(cardUid);
		if (card == null)
		{
			mapInfo.put("error", Lang.F1200);
			return mapInfo; // 卡牌不存在
		}
		mapInfo.put("error", null);
		mapInfo.put("card", card);
		return mapInfo;
	}

	/**
	 * 自动喝酒
	 * 
	 * @param player
	 *            当前玩家
	 * @param useGold
	 *            需要花费的金币
	 * @param b
	 *            是否是自动喝酒，用来控制付费醒酒的判断
	 * @param publicList
	 *            喝酒卡牌Uid列表
	 */
	public void autoDrink(Player player, int useGold, List<Integer> cardList,
			boolean b)
	{
		List<Integer> cards = new ArrayList<Integer>();
		List<Integer> checkList = new ArrayList<Integer>();
		for (Integer integer : cardList)
		{
			cards.add(integer);
			checkList.add(integer);
		}
		String error = checkAutoDrink(player, useGold, checkList);
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		for (int i = 0; i < cards.size(); i++)
		{
			Card card = player.getCardBag().getById(cards.get(i));
			if (card.getDrinkStatus() == Card.HYPER) // 从列表从排除已是振奋卡牌
			{
				cards.remove(i);
				--i;
				continue;
			}
			if (card.getDrinkStatus() == Card.DRUNK) // 醉酒卡牌处理
			{
				if (TimeKit.nowTimeMills() - card.getLastDrinkTime() >= ArmyCamp.DRINK_CD) // CD到了的移除
				{
					enterArmyCamp(player, card.getArmyName());
					--i;
					continue;
				} else
				{
					card.setDrinkStatus(Card.HYPER);
					player.formation.setBattleCardDrink(card.getId(), Card.HYPER);
					player.decrGold(countUseGold(TimeKit.nowTimeMills()));
				}
			}
			if (card.getDrinkStatus() == Card.AWAKE)
			{
				card.setDrinkStatus(Card.HYPER);
				player.formation.setBattleCardDrink(card.getId(), Card.HYPER);
				player.decrGold(countUseGold(TimeKit.nowTimeMills()));
			}
		}
//		ArmyCamp armyCamp = player.getArmyCamp();
//		int haveSeatNum = armyCamp.getPrivateSeatSize()
//				- armyCamp.getPrivateList().size();
//		if (haveSeatNum < cards.size())
//		{
//			for (int i = 0; i < armyCamp.getPrivateList().size(); i++)
//			{
//				SeatCard seatCard = armyCamp.getPrivateList().get(i);
//				Card card = player.getCardBag().getById(seatCard.getCardUid());
//				if ((TimeKit.nowTimeMills() - card.getLastDrinkTime()) >= ArmyCamp.DRINK_CD)
//				{
//					removeMyCard(player, card.uid);
//					++haveSeatNum;
//					if (haveSeatNum >= cards.size())
//						break;
//				}
//			}
//			for (int i = 0; i < armyCamp.getPrivateList().size(); i++)
//			{
//				SeatCard seatCard = armyCamp.getPrivateList().get(i);
//				Card card = player.getCardBag().getById(seatCard.getCardUid());
//				if ((TimeKit.nowTimeMills() - card.getLastDrinkTime()) < ArmyCamp.DRINK_CD)
//				{
//					payForAwake(player, player.getNickname(), card.uid,
//							countUseGold(card), b);
//					++haveSeatNum;
//					if (haveSeatNum >= cards.size())
//						break;
//				}
//			}
//		}
//		if (haveSeatNum >= cards.size())
//		{
//			for (int i = 0; i < cards.size(); i++)
//			{
//				int cardUid = cards.get(i);
//				for (int j = 0; j < 5; j++)
//				{
//					if (player.getArmyCamp().getSeatCardBySeatId(j) != null)
//						continue;
//					setCardToDrink(player, player.getNickname(), cardUid, j);
//					break;
//				}
//			}
//		}
	}

	/** 检查自动喝酒 */
	private String checkAutoDrink(Player player, int useGold,
			List<Integer> cards)
	{
		if (player.getPlayerInfo().getAutoDrink() != 1)
		{
			return Lang.F1714;
		}
		CardBag cardBag = player.getCardBag();
		for (Integer integer : cards)
		{
			Card card = cardBag.getById(integer);
			if (card == null)
			{
				return Lang.F1200; // 卡牌错误
			}
		}
		int gold = 0;
		List<SeatCard> cloneList = new ArrayList<SeatCard>();
		for (SeatCard seatCard : player.getArmyCamp().getPrivateList())
		{
			cloneList.add(seatCard);
		}
		for (int i = 0; i < cards.size(); i++)
		{
			Card card = player.getCardBag().getById(cards.get(i));
			SeatCard seatCard = player.getArmyCamp().getSeatCardById(
					cards.get(i));
			if (card.getDrinkStatus() == Card.HYPER)
			{
				cards.remove(i);
				--i;
				continue;
			}
			if (card.getDrinkStatus() == Card.DRUNK)
			{
				if (TimeKit.nowTimeMills() - card.getLastDrinkTime() < ArmyCamp.DRINK_CD)
				{
					gold = gold + countUseGold(TimeKit.nowTimeMills());
					cards.remove(i);
					--i;
					cloneList.remove(seatCard);
				}
			}
			if (card.getDrinkStatus() == Card.AWAKE)
			{
				gold = gold + countUseGold(TimeKit.nowTimeMills());
				cards.remove(i);
				--i;
			}
		}
		// ArmyCamp armyCamp = player.getArmyCamp();
		// int haveSeatNum = armyCamp.getPrivateSeatSize()
		// - armyCamp.getPrivateList().size();
		// if (haveSeatNum < cards.size())
		// {
		// for (int i = 0; i < cloneList.size(); i++)
		// {
		// SeatCard seatCard = cloneList.get(i);
		// Card card = player.getCardBag().getById(seatCard.getCardUid());
		// if ((TimeKit.nowTimeMills() - card.getLastDrinkTime()) <
		// ArmyCamp.DRINK_CD)
		// {
		// gold = gold + countUseGold(card);
		// ++haveSeatNum;
		// if (haveSeatNum >= cards.size())
		// break;
		// }
		// }
		// }
		System.err.println("算出来的自动喝酒所需金币=======" + gold);
		if (gold != useGold)
		{
			return Lang.F1710;
		}
		return null;
	}

	/** 计算所需金币 */
	private int countUseGold(long time)
	{
		int cdSeconds = TimeKit.timeSecond(ArmyCamp.DRINK_CD
				- (TimeKit.nowTimeMills() - time));
		int cdMinutes = cdSeconds % 60 > 0 ? cdSeconds / 60 + 1
				: cdSeconds / 60;
		int payForAwakeMinutes = GameCFG.getPayForAwakeMinutes();
		int payForAwakeGold = GameCFG.getPayForAwakeGold();
		int payMinutes = cdMinutes % payForAwakeMinutes > 0 ? cdMinutes
				/ payForAwakeMinutes + 1 : cdMinutes / payForAwakeMinutes;
		return payMinutes * payForAwakeGold;
	}

	/** 获取军帐信息 */
	private ArmyCamp getArmyCamp(String userName)
	{
		Session session = ds.getSession(userName);
		ArmyCamp armyCamp = null;
		if (session != null)
		{
			Player player = (Player) session.getSource();
			armyCamp = player.getArmyCamp();
		} else
		{
			int userId = dao.getUserIdByName(userName, session);
			armyCamp = dao.getArmyCamp(userId);
		}
		return armyCamp;
	}

	/** 存储军帐信息 */
	private void setArmyCamp(ArmyCamp armyCamp, String userName)
	{
		Session session = ds.getSession(userName);
		if (session != null)
		{
			Player player = (Player) session.getSource();
			player.setArmyCamp(armyCamp);
		} else
		{
			int userId = dao.getUserIdByName(userName, session);
			dao.setArmyCamp(armyCamp, userId);
		}
	}

	/** 获取背包信息 */
	private CardBag getCardBag(String userName)
	{
		Session session = ds.getSession(userName);
		CardBag cardBag = null;
		if (session != null)
		{
			Player player = (Player) session.getSource();
			cardBag = player.getCardBag();
		} else
		{
			int userId = dao.getUserIdByName(userName, session);
			cardBag = dao.getCardBag(userId);
		}
		return cardBag;
	}

	/** 存储背包信息 */
	private void setCardBag(CardBag cardBag, String userName)
	{
		Session session = ds.getSession(userName);
		if (session != null)
		{
			Player player = (Player) session.getSource();
			player.setCardBag(cardBag);
		} else
		{
			int userId = dao.getUserIdByName(userName, session);
			dao.setCardBag(cardBag, userId);
		}
	}
}
