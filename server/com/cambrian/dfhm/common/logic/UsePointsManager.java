package com.cambrian.dfhm.common.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Gift;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.util.MailFactory;

/**
 * ��˵������������������ҵ���߼�������
 * 
 * @author��Zmk
 */
public class UsePointsManager
{

	/* static fields */
	private static UsePointsManager instance = new UsePointsManager();

	/* static methods */
	public static UsePointsManager getInstance()
	{
		return instance;
	}

	/* fields */
	MailFactory mf;

	/* constructors */
	public void setMf(MailFactory mf)
	{
		instance.mf = mf;
	}

	/* properties */

	/* init start */

	/* methods */
	/** ���ֶһ���Ʒ */
	public Map<String, Object> buyGift(Player player, int sid)
	{
		String error = checkBuyGift(player, sid);
		if (error != null)
			throw new DataAccessException(601, error);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Gift gift = (Gift) Sample.getFactory().getSample(sid);
		List<Integer> cardList = new ArrayList<Integer>();
		ArrayList<Integer> giftCards = new ArrayList<Integer>();
		boolean haveCard = false;
		if (gift.cardSid != null && gift.cardSid.length > 0)
		{
			haveCard = true;
			for (Integer integer : gift.cardSid)
			{
				giftCards.add(integer);
			}
		}
		player.getPlayerInfo().decrNormalPoint(gift.price);
		player.incrGold(gift.gold);
		player.incrMoney(gift.money);
		player.incrSoul(gift.soul);
		player.incrToken(gift.token);

		if (player.getCardBag().getSurplusCapacity() >= giftCards.size())
		{
			for (Integer integer : giftCards)
			{
				Card card = player.getCardBag().add(integer);
				cardList.add(integer);
				cardList.add(card.getId());
				cardList.add(card.getSkillId());
			}
		} else
		{
			Mail mail = mf.createSystemMail(giftCards, 0, 0, 0, 0, 0,
					(int) player.getUserId());
			player.addMail(mail);
		}
		resultMap.put("cardList", cardList);
		resultMap.put("haveCard", haveCard);
		return resultMap;
	}

	/** �����ֶһ� */
	private String checkBuyGift(Player player, int sid)
	{
		Gift gift = (Gift) Sample.getFactory().newSample(sid);
		if (gift == null)
			return Lang.F708; // SID����
		if (player.getPlayerInfo().getNormalPoint() < gift.price)
			return Lang.F709; // ���ֲ���
		return null;
	}
}