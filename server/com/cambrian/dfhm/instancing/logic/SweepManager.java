package com.cambrian.dfhm.instancing.logic;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.CrossNPC;
import com.cambrian.dfhm.instancing.entity.NormalNPC;
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
		if(maptype==3)//穿越副本
		{
			CrossNPC crossNPC =  (CrossNPC) Sample.getFactory().getSample(mapId);
			sweep =  (Sweep) Sample.getFactory().getSample(crossNPC.getSweepId());
		}else if(maptype==1)//主线副本
		{
			NormalNPC normalNPC =  (NormalNPC) Sample.getFactory().getSample(mapId);
			sweep =  (Sweep) Sample.getFactory().getSample(normalNPC.getSweepId());
		}	
		ArrayList<Integer> cards = new ArrayList<Integer>();//
		
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
					
					for (int k = 0; k < cardnum; k++) {
						recard.add(cardid);
					}
				}
 
			}

			if (recard.size() != 0) {
				
				ArrayList<Card> tmepList = new ArrayList<Card>();	
				if (player.getCardBag().getSurplusCapacity() < recard.size())// 背包容量不足
				{	
					data.writeInt(recard.size());
				
					for (int j = 0; j < recard.size(); j++)
					{							
						cards.add( recard.get(j));	
						data.writeInt(0);
						data.writeInt(cards.get(j));
						data.writeInt(0);
					}					
				} else
				{		
					data.writeInt(recard.size());
					for (int j = 0; j < recard.size(); j++)// 往前台返回获得的卡牌的数量
					{
						tmepList.add(player.getCardBag().add(recard.get(j),player.getAchievements()));	
					}
						
					for (int k = 0; k <tmepList.size(); k++)
					{
						data.writeInt(tmepList.get(k).getId());
						data.writeInt(tmepList.get(k).getSid());
						data.writeInt(tmepList.get(k).getSkillId());
					}
				}																	
			}else//本次扫荡没得到卡牌
			{
				data.writeInt(0);
			}
		}
		if(cards.size()>0)
		{
			Mail mail = mf.createSystemMail(cards, 0, 0, 0, 0, 0,
					(int) player.getUserId());
			player.addMail(mail);
			System.out.println("card length ="+cards.size());
			
		}	
		
		
		
	/*	if(maptype==3)//穿越副本 消耗穿越次数
		{
			player.getPlayerInfo().setCrossMapNum(player.getPlayerInfo().getCrossMapNum()-sweepNum);
		}	*/

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
			
			if(sweepNum>player.getPlayerInfo().getCrossMapNum())
			{
				return Lang.F1414;
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
