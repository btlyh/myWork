package com.cambrian.dfhm.shop.logic;

import java.util.ArrayList;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.notice.MailSendNotice;
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.dfhm.shop.entity.Goods;
import com.cambrian.dfhm.shop.entity.Shop;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：商城处理
 * 
 * @author：Zmk
 * 
 */
public class ShopManager
{

	/* static fields */
	private static ShopManager instance = new ShopManager();

	/* static methods */
	public static ShopManager getInstance()
	{
		return instance;
	}

	/* fields */
	Shop shop = new Shop();
	/** 邮件工厂 */
	MailFactory mf;
	/** 邮件推送 */
	MailSendNotice msn;
	/** 数据服务器 */
	DataServer ds;

	/* constructors */
	public void setMailFactory(MailFactory mf)
	{
		instance.mf = mf;
	}
	public void setMailSendNotice(MailSendNotice msn)
	{
		instance.msn = msn;
	}

	public void setDS(DataServer ds)
	{
		instance.ds = ds;
	}
	/* properties */

	/* init start */

	/* methods */
	/**
	 * 购买商品
	 * 
	 * @param player
	 *            当前玩家
	 * @param goodsId
	 *            商品sid
	 * @param useGold
	 *            所需金币
	 */
	public ArrayList<Integer> buyGoods(Player player, int goodsId, int useGold)
	{
		String error = checkBuyGoods(player, goodsId, useGold);
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		Goods goods = (Goods) Sample.factory.getSample(goodsId);
		player.incrGold(goods.gold);
		player.incrMoney(goods.money);
		player.incrSoul(goods.soul);
		player.incrToken(goods.token);
		ArrayList<Integer> cardsList = new ArrayList<Integer>();
		ArrayList<Integer> uidList = new ArrayList<Integer>();
		if (goods.cardSid != null)
		{
			for (int cardSid : goods.cardSid)
			{
				if (cardSid == 0)
					continue;
				cardsList.add(cardSid);
			}
		}
		if (player.getCardBag().getSurplusCapacity() < cardsList.size())
		{
			Mail mail = mf.createSystemMail(cardsList, 0, 0, 0, 0, 0,
					(int) player.getUserId());
			player.addMail(mail);
			Session session = ds.getSession(player.getNickname());
			if (session != null)
				msn.send(session, new Object[] {player.getUnreadMailCount()});
		}else
		{
			for (Integer integer : cardsList)
			{
				Card card = player.getCardBag().add(integer,player.getAchievements());
				uidList.add(card.getId());
				uidList.add(card.getSkillId());
			}
		}
		// for (int i = 0; i < cardsList.size(); i++)
		// {
		// int sid = cardsList.get(i);
		// if (player.getCardBag().getSurplusCapacity() > 0)
		// {
		// Card card = player.getCardBag().add(sid);
		// cardsList.remove(i);
		// --i;
		// uidList.add(card.getId());
		// uidList.add(card.getSkillId());
		// } else
		// break;
		// }
		// if (cardsList.size() > 0)
		// {
		// Mail mail = mf.createSystemMail(cardsList, 0, 0, 0, 0, 0,
		// (int) player.getUserId());
		// player.addMail(mail);
		// }
		player.decrGold(useGold);
		return uidList;
	}

	/** 检查购买物品 */
	private String checkBuyGoods(Player player, int goodsId, int useGold)
	{
		Goods goods = (Goods) Sample.factory.getSample(goodsId);
		if (goods == null)
		{
			return Lang.F2001; // 商品序号不存在
		}
		if (goods.price != useGold)
		{
			return Lang.F2002; // 所需金币与服务端不服
		}
		if (player.getGold() < useGold)
		{
			return Lang.F2003; // 玩家金币不足
		}
		return null;
	}
}
