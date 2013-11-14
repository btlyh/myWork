package com.cambrian.dfhm.instancing.logic;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.Sweep;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.util.MailFactory;

public class SweepManager {

	/* static fields */
	private static SweepManager instance = new SweepManager();

	/* static methods */
	public static SweepManager getInstance() {
		return instance;
	}

	private MailFactory mf;

	public void setMailFactory(MailFactory mf) {
		instance.mf = mf;
	}

	public void sweepMap(Player player, int mapId, int sweepNum, int maptype,
			ByteBuffer data) {
		String error = checkSweep(player, mapId, sweepNum, maptype);
		if (error != null) {
			throw new DataAccessException(601, error);
		}

		Sweep sweep = new Sweep();
		//由于MAP ID和 sweepID  客户端一样 而服务器实现成2个部分 所以  SWEEP ID需要  从MAPID 加10000dedao
		sweep = (Sweep) Sample.getFactory().getSample(mapId+10000);
		for (int i = 0; i < sweepNum; i++) // 进行多少次扫荡
		{
			int money = MathKit.randomValue(sweep.getMoneymin(),
					sweep.getMoneymax() + 1);
			int gold = MathKit.randomValue(sweep.getGoldmin(),
					sweep.getGoldmax() + 1);
			int soul = MathKit.randomValue(sweep.getSoulmin(),
					sweep.getSoulmax() + 1);
			player.incrMoney(money);
			player.incrGold(gold);
			player.incrSoul(soul);
			data.writeInt(gold);
			data.writeInt(money); 
			data.writeInt(soul);

			int[] awardCard = sweep.getCard();
			ArrayList<Integer> cards = new ArrayList<Integer>();//
		//	data.writeInt(awardCard.length);
			ArrayList<Integer>recard = new ArrayList<Integer>();
			for (int j = 0; j < awardCard.length / 3; j++) // 根据卡牌概率随机
			{
				int cardid = awardCard[j * 3];// 卡牌ID
				int per = awardCard[j * 3 + 1];// 掉落几率
				int maxNum = awardCard[j * 3 + 2];// 最大掉落数
				int cardnum = 0;// 卡牌掉落的张数

				for (int k = 0; k < maxNum; k++) {
					int randvalue = MathKit.randomValue(0, 100000);

					if (randvalue < per) {
						cardnum++;
					}
				}

				if (cardnum != 0)// 将随机到的卡牌以及张数取出来
				{
					recard.add(cardid);
					recard.add(cardnum);
				}

			}

			data.writeInt(recard.size()/2);

			if (recard.size() != 0) {
				for (int j = 0; j < recard.size()/2; j++)// 往前台返回获得的卡牌的种类和数量
				{

					if (player.getCardBag().getSurplusCapacity() < recard.get(j*2+1))// 背包容量不足
					{

						for (int j2 = 0; j2 <  recard.get(j*2+1); j2++)// 多张卡牌的时候
																	// 需要发邮件多次
						{
							cards.add( recard.get(j*2));
						}

						Mail mail = mf.createSystemMail(cards, 0, 0, 0, 0, 0,
								(int) player.getUserId());
						player.addMail(mail);
					} else {
						for (int j2 = 0; j2 <  recard.get(j*2+1); j2++)// 多张卡牌的时候
																	// 玩家需要多次获取卡牌
						{
							player.getCardBag().add( recard.get(j*2));
						}
					}
					data.writeInt( recard.get(j*2));
					data.writeInt( recard.get(j*2+1));
				}
			}
		}
	}

	private String checkSweep(Player player, int mapId, int sweepNum,
			int maptype) {

		if (maptype == 1) {
			if (player.getCurIndexForNormalNPC() <= mapId) {
				return Lang.F1411;
			}
		} else if (maptype == 3) {
			if (player.getCurIndexForCorssNPC() <= mapId) {
				return Lang.F1411;
			}
		} else {
			return Lang.F1412;
		}

		if (player.getCurToken() < sweepNum) {
			return Lang.F1413;
		}

		return null;
	}

}
