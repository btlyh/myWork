package com.cambrian.dfhm.armyCamp.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵��������ҵ���߼�������
 * 
 * @author��Zmk
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
	/** �������ݷ��ʶ��� */
	ArmyCampDao dao;
	/** ���ݷ����� */
	DataServer ds;
	/** �Զ��Ƴ��������� */
	RemoveMyCardNotice rmcn;
	/** �Ѿ������� */
	UseAwakeSoupNotice uasn;
	/** �ʼ����� */
	MailFactory mf;
	/** ʱ���ʽ */
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

	/**
	 * �õ���ǰ��ҵ���ʷ��¼����
	 * 
	 * @param player
	 *            ��ǰ���
	 * @return ��ǰ��ҵ���ʷ��¼����
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
	 * ���ƽ�����λ�Ⱦ�
	 * 
	 * @param player
	 *            ��ǰ���
	 * @param userName
	 *            �������������
	 * @param cardUid
	 *            Ҫ�ȾƵĿ���UID
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
		card.setArmyName(userName);
		if (player.getNickname().equals(userName))
			setArmyCamp(tarArmyCamp, userName);
		else
		{
			int userId = dao.getUserIdByName(userName, ds.getSession(userName));
			dao.setArmyCamp(tarArmyCamp, userId);
		}
	}

	/** ��鿨�ƽ�����λ�Ⱦ� */
	private Map<String, Object> checkSetCardToDrink(Player player,
			String userName, int cardUid, int sitNum)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (userName.length() < 1 || userName == null)
		{
			mapInfo.put("error", Lang.F1701); // ������Ʋ���ȷ
			return mapInfo;
		}
		Card card = player.getCardBag().getById(cardUid);
		System.err.println("status====" + card.getDrinkStatus());
		if (card.getDrinkStatus() != Card.AWAKE)
		{
			mapInfo.put("error", Lang.F1702);
			return mapInfo; // ��ǰ����״̬����ȷ
		}
		if (player.getCardBag().getById(cardUid) == null)
		{
			mapInfo.put("error", Lang.F1703);
			return mapInfo; // ������û�д˿���
		}
		ArmyCamp tarArmyCamp = null;
		if (player.getNickname().equals(userName))
		{
			tarArmyCamp = player.getArmyCamp();
			if (tarArmyCamp.getPrivateSeatSize()
					- tarArmyCamp.getPrivateList().size() < 1)
			{
				mapInfo.put("error", Lang.F1704);
				return mapInfo; // ˽����λ��Ա
			}
		} else
		{
			int tarPlayerId = dao.getUserIdByName(userName, ds.getSession(userName));
			if (!player.getFriendInfo().getFriendList().contains(tarPlayerId))
			{
				mapInfo.put("error", Lang.F1717);
				return mapInfo; // �Է������б����Ѿ�ɾ������
			}
			tarArmyCamp = getArmyCamp(userName);
			if (tarArmyCamp.getPublicSeatSize()
					- tarArmyCamp.getPublicList().size() < 1)
			{
				mapInfo.put("error", Lang.F1705);
				return mapInfo; // �Է�������λ����
			}
		}
		if (tarArmyCamp.getSeatCardById(cardUid) != null)
		{
			mapInfo.put("error", Lang.F1706);
			return mapInfo; // �����Ѿ�����λ��
		}
		if (sitNum > 7 || sitNum < 0)
		{
			mapInfo.put("error", Lang.F1716);
			return mapInfo; // ��λ�Ŵ���
		}
		if (tarArmyCamp.getSeatCardBySeatId(sitNum) != null)
		{
			mapInfo.put("error", Lang.F1707);
			return mapInfo; // ��ǰ��λ�Ѿ��п�����
		}
		mapInfo.put("error", null);
		mapInfo.put("armyCamp", tarArmyCamp);
		return mapInfo;
	}

	/**
	 * �й�
	 * 
	 * @param session
	 *            ��ǰ��һỰ
	 * @return
	 */
	public Player walkAround(Player player, Session session)
	{
		Session[] sessions = ds.getSessionMap().getSessions();
		List<Session> sessionList = Arrays.asList(sessions);
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
		List<Session> list = new ArrayList<Session>();
		while (list.size() < sessionList.size())
		{
			Session randomSession = sessionList.get(MathKit.randomValue(0,
					sessionList.size()));
			if (!kickSessionList.contains(randomSession)
					&& player.getArmyCamp().getWalkSession() != randomSession)
			{
				player.getArmyCamp().setWalkSession(randomSession);
				return (Player) randomSession.getSource();
			}
			if (!list.contains(randomSession))
				list.add(randomSession);
		}
		return null;
	}

	/**
	 * ��˽����λ�Ƴ�����
	 * 
	 * @param player
	 *            ��ǰ���
	 * @param cardUid
	 *            ����uid
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
		card.setInArmyCamp(0);
		privateList.remove(seatCard);
		player.getArmyCamp().setPrivateList(privateList);
	}

	/** ����Ƴ����� */
	private String checkRemoveMyCard(Player player, SeatCard seatCard)
	{
		Card card = player.getCardBag().getById(seatCard.getCardUid());
		if (card == null)
		{
			return Lang.F1200; // ����uid����
		}
		if (TimeKit.nowTimeMills() - card.getLastDrinkTime() < ArmyCamp.DRINK_CD)
		{
			return Lang.F1708; // ���ƻ�����ȴ��
		}
		return null;
	}

	/**
	 * �����Ѿ�
	 * 
	 * @param player
	 *            ��ǰ���
	 * @param userName
	 *            ���������������
	 * @param cardUid
	 *            ����uid
	 * @param useGold
	 *            ����Ҫ�Ľ�Ǯ
	 * @param b
	 *            �Ƿ����Զ��Ⱦƣ��������Ƹ����ѾƵ��ж�
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
		card.setInArmyCamp(0);
		ArmyCamp tarArmyCamp = (ArmyCamp) resultMap.get("armyCamp");
		SeatCard seatCard = (SeatCard) resultMap.get("seatCard");
		tarArmyCamp.removeSeatCard(seatCard);
		setArmyCamp(tarArmyCamp, userName);
		player.decrGold(useGold);
	}

	/**
	 * ��鸶���Ѿ�
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
			return mapInfo; // ���VIP�ȼ�����
		}
		if (card.getDrinkStatus() != Card.DRUNK && !b)
		{
			mapInfo.put("error", Lang.F1715);
			return mapInfo; // ����״̬����ȷ
		}
		if (!tarArmyCamp.getPrivateList().contains(seatCard)
				&& !tarArmyCamp.getPublicList().contains(seatCard))
		{
			mapInfo.put("error", Lang.F1709);
			return mapInfo; // ���Ʋ�����λ��
		}
		if (userName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // ��������
		}
		if (useGold != countUseGold(card.getLastDrinkTime()))
		{
			mapInfo.put("error", Lang.F1710);
			return mapInfo; // �����������˲���
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
	 * ʹ���Ѿ���
	 * 
	 * @param player
	 *            ��ǰ���
	 * @param userName
	 *            ��ǰ�����������
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
				+ ") ���㹲�����Ѿ�������������佫�ĺȾ���ȴʱ�������30%");
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

	/** ���ٿ�����ȴʱ�� */
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

	/** ���ʹ���Ѿ��� */
	private Map<String, Object> checkUseAwakeSoup(Player player, String userName)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (userName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // �����������
		}
		int userId = dao.getUserIdByName(userName, ds.getSession(userName));
		if (!player.getFriendInfo().getFriendList().contains(userId))
		{
			mapInfo.put("error", Lang.F1711);
			return mapInfo; // ����Ҳ�����ĺ���
		}
		ArmyCamp tarArmyCamp = getArmyCamp(userName);
		if (TimeKit.nowTimeMills() - tarArmyCamp.getLastUseAwakeSoupTime() < ArmyCamp.SOUP_CDTIME)
		{
			mapInfo.put("error", Lang.F1712);
			return mapInfo; // �þ����Ѿ�����ȴʱ��δ����
		}
		mapInfo.put("error", null);
		mapInfo.put("tarArmyCamp", tarArmyCamp);
		return mapInfo;
	}

	/**
	 * �������
	 * 
	 * @param player
	 *            ��ǰ���
	 * @param userName
	 *            ����ľ�������
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
				card.setInArmyCamp(0);
				setCardBag(cardBag, seatCard.getOwnerName());
				publicList.remove(seatCard);
				armyCamp.setPublicList(publicList);
				setArmyCamp(armyCamp, userName);
				String s = "�װ���������ã�����" + seatCard.getOwnerName() + "�����е�"
						+ card.getName() + "������ȴʱ���ѵ���ϵͳ���Զ��Ƴ���ף����Ϸ��죡";
				Mail mail = mf.createSystemMailNothing(s);
				tarPlayer.addMail(mail);
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
				card.setInArmyCamp(0);
				setCardBag(cardBag, seatCard.getOwnerName());
				privateList.remove(seatCard);
				armyCamp.setPrivateList(privateList);
				setArmyCamp(armyCamp, userName);
				String s = "�װ���������ã������Լ������е�" + card.getName()
						+ "������ȴʱ���ѵ���ϵͳ���Զ��Ƴ���ף����Ϸ��죡";
				Mail mail = mf.createSystemMailNothing(s);
				player.addMail(mail);
				// Session noticeSession =
				// ds.getSession(seatCard.getOwnerName());
				// if (noticeSession != null)
				// {
				// rmcn.send(noticeSession, new Object[] { card.uid });
				// }
			}
		}
		resultMap.put("player", armyPlayer);
		resultMap.put("cardList", cardList);
		resultMap.put("armyCamp", armyCamp);
		return resultMap;
	}

	/** ��������� */
	private Map<String, Object> checkEnterArmyCamp(Player player,
			String userName)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (userName == null || userName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // �����������
		}
		Session session = ds.getSession(userName);
		int userId = dao.getUserIdByName(userName, session);
		if (!userName.equals(player.getNickname())
				&& !player.getFriendInfo().getFriendList().contains(userId))
		{
			mapInfo.put("error", Lang.F1711);
			return mapInfo; // �޷��������Ҿ���
		}
		mapInfo.put("error", null);
		return mapInfo;
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param ownerName
	 *            ���������������
	 * @param cardUid
	 *            ����uid
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

	/** ����ѯ���� */
	private Map<String, Object> checkCardInfo(String ownerName, int cardUid)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (ownerName.length() < 1)
		{
			mapInfo.put("error", Lang.F1701);
			return mapInfo; // ��������
		}
		Card card = getCardBag(ownerName).getById(cardUid);
		if (card == null)
		{
			mapInfo.put("error", Lang.F1200);
			return mapInfo; // ���Ʋ�����
		}
		mapInfo.put("error", null);
		mapInfo.put("card", card);
		return mapInfo;
	}

	/**
	 * �Զ��Ⱦ�
	 * 
	 * @param player
	 *            ��ǰ���
	 * @param useGold
	 *            ��Ҫ���ѵĽ��
	 * @param b
	 *            �Ƿ����Զ��Ⱦƣ��������Ƹ����ѾƵ��ж�
	 * @param publicList
	 *            �Ⱦƿ���Uid�б�
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
			if (card.getDrinkStatus() == Card.HYPER) // ���б���ų�������ܿ���
			{
				cards.remove(i);
				--i;
				continue;
			}
			if (card.getDrinkStatus() == Card.DRUNK) // ��ƿ��ƴ���
			{
				if (TimeKit.nowTimeMills() - card.getLastDrinkTime() >= ArmyCamp.DRINK_CD) // CD���˵��Ƴ�
				{
					enterArmyCamp(player, card.getArmyName());
					--i;
					continue;
				} else
				{
					card.setDrinkStatus(Card.HYPER);
					player.decrGold(countUseGold(TimeKit.nowTimeMills()));
				}
			}
			if (card.getDrinkStatus() == Card.AWAKE)
			{
				card.setDrinkStatus(Card.HYPER);
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

	/** ����Զ��Ⱦ� */
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
				return Lang.F1200; // ���ƴ���
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
		System.err.println("��������Զ��Ⱦ�������=======" + gold);
		if (gold != useGold)
		{
			return Lang.F1710;
		}
		return null;
	}

	/** ���������� */
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

	/** ��ȡ������Ϣ */
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

	/** �洢������Ϣ */
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

	/** ��ȡ������Ϣ */
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

	/** �洢������Ϣ */
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
