package com.cambrian.dfhm.mail.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.dao.MailDao;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.notice.MailSendNotice;
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵����
 * 
 * @author��LazyBear
 */
public class MailManager {

	/* static fields */
	private static MailManager instance = new MailManager();

	/* static methods */
	public static MailManager getInstance() {
		return instance;
	}

	/* fields */
	/** ���ݷ����� */
	DataServer dataServer;
	/** ���ͷ����ʼ���Ϣ */
	MailSendNotice msn;
	/** �ʼ����ݷ��ʶ��� */
	MailDao dao;
	/** �ʼ������� */
	MailFactory mf;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	public void setDS(DataServer dataServer) {
		instance.dataServer = dataServer;
	}

	public void setMailSendNotice(MailSendNotice msn) {
		instance.msn = msn;
	}

	public void setMailDao(MailDao dao) {
		instance.dao = dao;
	}

	public void setMailFactory(MailFactory mf) {
		instance.mf = mf;
	}

	/**
	 * һ��ɾ���ʼ�
	 * 
	 * @param player
	 */
	public void oneKeyDeleteMail(Player player) {
		String error = checkOneKeyDeleteMail(player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		for (Mail mail : player.getMailList()) {
			player.getMailList().remove(mail);
			dao.deleteMail((int) mail.getMailId());
		}
	}

	/**
	 * ���һ��ɾ��
	 * 
	 * @param player
	 * @return
	 */
	private String checkOneKeyDeleteMail(Player player) {
		ArrayList<Mail> mailList = player.getMailList();
		if (mailList.size() < 1) {
			return Lang.F1304;
		}
		return null;
	}

	/**
	 * һ����ȡ�ʼ�
	 * 
	 * @param player
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> oneKeyTakeMail(Player player) {
		Map<String, Object> resultMap = checkOneKeyTakeMail(player);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		ArrayList<Integer> dataList = new ArrayList<Integer>();
		ArrayList<Mail> canTakeMails = (ArrayList<Mail>) resultMap
				.get("canTakeMails");
		for (Mail mail : canTakeMails) {
			dataList.addAll(takeAnnex(mail, player));
		}
		return dataList;
	}

	/**
	 * ���һ����ȡ
	 * 
	 * @param player
	 * @return
	 */
	private Map<String, Object> checkOneKeyTakeMail(Player player) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		ArrayList<Mail> mailList = player.getMailList();
		if (mailList.size() < 1) {
			mapInfo.put("error", Lang.F1304);
			return mapInfo;
		}
		ArrayList<Mail> canTakeMails = new ArrayList<Mail>();
		for (Mail mail : mailList) {
			if (mail.getState() == Mail.MAILSTATE_READ_UNGET
					|| mail.getState() == Mail.MAILSTATE_UNREAD) {
				if (mail.getCardList().size() >= Mail.MAIL_ANNEX_MINNUM
						|| mail.getGold() >= Mail.MAIL_ANNEX_MINNUM
						|| mail.getMoney() >= Mail.MAIL_ANNEX_MINNUM
						|| mail.getToken() >= Mail.MAIL_ANNEX_MINNUM
						|| mail.getSoulPoint() >= Mail.MAIL_ANNEX_MINNUM
						|| mail.getNormalPoint() >= Mail.MAIL_ANNEX_MINNUM) {
					canTakeMails.add(mail);
				}
			}
		}
		if (canTakeMails.size() < 1) {
			mapInfo.put("error", Lang.F1307);
			return mapInfo;
		}
		int cardListSize = 0;
		for (Mail mail : canTakeMails) {
			cardListSize += mail.getCardList().size();
		}
		if (cardListSize >= Mail.MAIL_ANNEX_MINNUM) {
			if (cardListSize > player.getCardBag().getSurplusCapacity()) {
				mapInfo.put("error", Lang.F1309);
				return mapInfo;
			}
		}
		mapInfo.put("error", null);
		mapInfo.put("canTakeMails", canTakeMails);
		return mapInfo;
	}

	/**
	 * ɾ���ʼ�
	 * 
	 * @param uid
	 *            Ψһ��ʶ
	 * @param player
	 *            ��Ҷ���
	 */
	public void deleteMail(int uid, Player player) {
		Map<String, Object> resultMap = checkMail(uid, player);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Mail mail = (Mail) resultMap.get(String.valueOf(uid));
		player.getMailList().remove(mail);
		dao.deleteMail(uid);
	}

	/**
	 * ����ʼ�
	 * 
	 * @param uid
	 *            Ψһ��ʶ
	 * @param player
	 *            ��Ҷ���
	 */
	public void browseMail(int uid, Player player) {
		Map<String, Object> resultMap = checkMail(uid, player);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Mail mail = (Mail) resultMap.get(String.valueOf(uid));
		if (mail.getState() == Mail.MAILSTATE_UNREAD) {
			mail.setState(Mail.MAILSTATE_READ_UNGET);
			dao.setMailState(uid, mail);
		}
	}

	/**
	 * ���Ҫ������ʼ�
	 * 
	 * @param uid
	 *            Ψһ��ʶ
	 * @param player
	 *            ��Ҷ���
	 * @return
	 */
	private Map<String, Object> checkMail(int uid, Player player) {
		ArrayList<Mail> mailList = player.getMailList();
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (mailList.size() < 1) {
			mapInfo.put("error", Lang.F1304);
			return mapInfo;
		}
		Mail mail = null;
		for (int i = 0; i < mailList.size(); i++) {
			mail = mailList.get(i);
			if (uid == mail.getMailId()) {
				break;
			}
		}
		if (mail == null) {
			mapInfo.put("error", Lang.F1306);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put(String.valueOf(uid), mail);
		return mapInfo;
	}

	/**
	 * ��ȡ�ʼ� (����)
	 * 
	 * @param uid
	 *            Ψһ��ʶ
	 * @param player
	 *            ��Ҷ���
	 */
	public List<Integer> takeMail(int uid, Player player) {
		Map<String, Object> resultMap = checkTakeMail(uid, player);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Mail mail = (Mail) resultMap.get(String.valueOf(uid));
		return takeAnnex(mail, player);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param mail
	 * @param player
	 * @return
	 */
	private ArrayList<Integer> takeAnnex(Mail mail, Player player) {
		ArrayList<Integer> dataList = new ArrayList<Integer>();
		if (mail.getCardList().size() >= Mail.MAIL_ANNEX_MINNUM) {
			for (int i = 0; i < mail.getCardList().size(); i++) {
				Card card = player.getCardBag().add(mail.getCardList().get(i));
				dataList.add(card.getSid());
				dataList.add(card.uid);
				dataList.add(card.getSkillId());
			}
		}
		// ���ñ�
		if (mail.getGold() >= Mail.MAIL_ANNEX_MINNUM) {
			player.incrGold(mail.getGold());
		}
		// ��Ϸ��
		if (mail.getMoney() >= Mail.MAIL_ANNEX_MINNUM) {
			player.incrMoney(mail.getMoney());
		}
		// ����
		if (mail.getToken() >= Mail.MAIL_ANNEX_MINNUM) {
			player.incrToken(mail.getToken());
		}
		// ���
		if (mail.getSoulPoint() >= Mail.MAIL_ANNEX_MINNUM) {
			player.incrSoul(mail.getSoulPoint());
		}
		// ����
		if (mail.getNormalPoint() >= Mail.MAIL_ANNEX_MINNUM) {
			player.getPlayerInfo().incrNormalPoint(mail.getNormalPoint());
		}
		mail.setState(Mail.MAILSTATE_READ_GET);
		dao.setMailState((int) mail.getMailId(), mail);
		return dataList;
	}

	/**
	 * �����ȡ�ʼ� (����)
	 * 
	 * @param uid
	 *            Ψһ��ʶ
	 * @param player
	 *            ��Ҷ���
	 * @return
	 */
	private Map<String, Object> checkTakeMail(int uid, Player player) {
		ArrayList<Mail> mailList = player.getMailList();
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		if (mailList.size() < 1) {
			mapInfo.put("error", Lang.F1304);
			return mapInfo;
		}
		Mail mail = null;
		for (int i = 0; i < mailList.size(); i++) {
			mail = mailList.get(i);
			if (uid == mail.getMailId()) {
				break;
			}
		}
		if (mail == null) {
			mapInfo.put("error", Lang.F1306);
			return mapInfo;
		}
		int cardListSize = mail.getCardList().size();
		if (mail.getCardList().size() < Mail.MAIL_ANNEX_MINNUM
				&& mail.getGold() < Mail.MAIL_ANNEX_MINNUM
				&& mail.getMoney() < Mail.MAIL_ANNEX_MINNUM
				&& mail.getToken() < Mail.MAIL_ANNEX_MINNUM
				&& mail.getSoulPoint() < Mail.MAIL_ANNEX_MINNUM
				&& mail.getNormalPoint() < Mail.MAIL_ANNEX_MINNUM) {
			mapInfo.put("error", Lang.F1307);
			return mapInfo;
		}
		if (mail.getState() == Mail.MAILSTATE_READ_GET) {
			mapInfo.put("error", Lang.F1308);
			return mapInfo;
		}
		if (cardListSize >= Mail.MAIL_ANNEX_MINNUM) {
			if (cardListSize > player.getCardBag().getSurplusCapacity()) {
				mapInfo.put("error", Lang.F1309);
				return mapInfo;
			}
		}
		mapInfo.put("error", null);
		mapInfo.put(String.valueOf(uid), mail);
		return mapInfo;
	}

	/**
	 * �����ʼ� (���֮��)
	 * 
	 * @param getPlayerName
	 *            �ռ���
	 * @param sendPlayerName
	 *            ������
	 * @param mailContent
	 *            �ʼ�����
	 */
	public void sendMail(String getPlayerName, String sendPlayerName,
			String mailContent) {
		Map<String, Object> resultMap = checkSendMail(getPlayerName,
				mailContent);
		String error = (String) resultMap.get("error");
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		Mail mail = mf.createPlayerMail(mailContent, sendPlayerName);
		Session session = null;
		session = dataServer.getSession(getPlayerName);
		Player player = null;
		if (session != null) {
			player = (Player) session.getSource();
		}
		if (player != null) {
			if (player.getNickname().equals(getPlayerName)) {
				mail.setSendTime(TimeKit.nowTimeMills());
				player.addMail(mail);
				msn.send(session, new Object[] { player.getUnreadMailCount() });
			}
		}
		int userId = (Integer) resultMap.get("userId");
		dao.addMail(mail, userId);
	}

	/**
	 * ��鷢���ʼ� (��ҷ����ʼ�ʱ)
	 * 
	 * @param getPlayerName
	 *            �ռ���
	 * @param mailContent
	 *            �ʼ�����
	 */
	private Map<String, Object> checkSendMail(String getPlayerName,
			String mailContent) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (getPlayerName.length() < 1) {
			resultMap.put("error", Lang.F1301);
			return resultMap;
		}
		if (mailContent.length() < 1
				|| mailContent.length() > Mail.MAIL_CONTENT_CONFINE) {
			resultMap.put("error", Lang.F1302);
			return resultMap;
		}
		Session session = dataServer.getSession(getPlayerName);
		Fields fields = dao.getPlayerUid(getPlayerName);
		if (fields == null && session == null) {
			resultMap.put("error", Lang.F1310);
			return resultMap;
		}
		resultMap.put("error", null);
		if (session != null) {
			resultMap.put("userId", (int) ((Player) session.getSource())
					.getUserId());
		} else {
			resultMap.put("userId", ((IntField) fields.get("userid")).value);
		}
		return resultMap;
	}

	/**
	 * �鿴�ʼ�
	 * 
	 * @param index
	 *            �鿴λ�ã�����λ��������������Щ�ʼ����ͻ��ˣ�
	 * @param player
	 *            ��Ҷ���
	 * @return �����ʼ��б�
	 */
	public ArrayList<Mail> showMail(int index, Player player) {
		String error = checkShowMail(index, player);
		if (error != null) {
			throw new DataAccessException(601, error);
		}
		return getListFromAnotherList(index, Mail.MAIL_SIZE_CONFINE, player
				.getMailList());
	}

	/**
	 * ���鿴�ʼ�
	 * 
	 * @param index
	 *            �鿴λ��
	 * @param player
	 *            ��Ҍ���
	 */
	private String checkShowMail(int index, Player player) {
		if (index < 0) {
			return Lang.F1303;
		}
		return null;
	}

	/**
	 * ��һ���ʼ�������ȡ����һ���ʼ�����
	 * 
	 * @param index
	 *            �±�λ��
	 * @param size
	 *            ��С
	 * @param list
	 *            ��һ������
	 * @return
	 */
	private ArrayList<Mail> getListFromAnotherList(int index, int size,
			ArrayList<Mail> list) {
		if (index < 0 || size < 1 || list.size() < 1 || list.size() < index + 1) {
			return null;
		}
		int temp = index + size;
		int loopTimes = 0;

		if (temp > list.size()) {
			loopTimes = list.size();
		} else {
			loopTimes = temp;
		}
		ArrayList<Mail> copyList = new ArrayList<Mail>();

		for (int i = index; i < loopTimes; i++) {
			copyList.add(list.get(i));
		}
		return copyList;
	}
}
