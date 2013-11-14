package com.cambrian.dfhm.reward;

import java.util.ArrayList;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.util.MailFactory;

public class RewardManager {
	private static RewardManager instance = new RewardManager();

	MailFactory mf;

	public static RewardManager getInstance() {
		return instance;
	}

	public void setMailFactory(MailFactory mf) {
		instance.mf = mf;
	}

	public void onlineReward(Player player, boolean isReward, ByteBuffer data) {
		String error = checkReward(player);
		int rewardNum = player.getPlayerInfo().getRewardNum();
		if(!isReward)//第一次进入 无论是否可以领奖 都返回第一次可领奖的ID
		{
			data.writeInt(GameCFG.getRewardSampleIdByNum(rewardNum)); 
			return;
		}
		
		if (error == Lang.F2103) {
			data.writeInt(-1);
			return;
		}

		if (error == Lang.F2104) {
			if (rewardNum == 0) {
				data.writeInt(GameCFG.getRewardSampleIdByNum(rewardNum)); // 下一次领奖
				return;
			} else {
				return;
			}

			// throw new DataAccessException(601,error);
		}

		if (isReward) {
			Reward reward = new Reward();

			reward = (Reward) Sample.getFactory().getSample(
					GameCFG.getRewardSampleIdByNum(rewardNum));
			// 设置下一次的领奖时间
			player.getPlayerInfo().setNextRewardTime(
					TimeKit.nowTimeMills()
							+ GameCFG.getRewardIntervalTimeByIndex(rewardNum)
							* 1000);
			// 设置已领次数
			player.getPlayerInfo().setRewardNum(rewardNum + 1);
			// todo 增加奖励物品

			player.setGold(player.getGold() + reward.getMoney());// 获得金钱
			player.setSoul(player.getSoul() + reward.getSoul());// 获得武魂
			player.setCurToken(player.getCurToken() + reward.getTired());// 获得奖励的军令数

			int[][] card = reward.getCard();
			int totalnum = 0;
			for (int i = 0; i < card.length; i++) {
				totalnum += card[i][1];
			}
			int num = player.getCardBag().getSurplusCapacity();
			if (num < totalnum)// 如果卡牌背包容量不够发发邮件
			{
				ArrayList<Integer> cards = new ArrayList<Integer>();
				for (int i = 0; i < card.length; i++)
				{
					for (int j = 0; j < card[i][1]; j++)
					{
						cards.add(card[i][0]);
					}
				}				
				Mail mail = mf.createSystemMail(cards, 0, 0, 0, 0, 0,
						(int) player.getUserId());
				player.addMail(mail);
			} else 
			{
				for (int i = 0; i < card.length; i++)
				{
					for (int j = 0; j < card[i][1]; j++)
					{
						player.getCardBag().add(card[i][0]);
					}
				}
				
			}
			if(rewardNum==GameCFG.getRewardSampleId().length-1)
			{
				data.writeInt(-1); // 下一次领奖
			}else
			{
				data.writeInt(GameCFG.getRewardSampleIdByNum(rewardNum + 1)); // 下一次领奖
			}	
			
		}

	}

	private String checkReward(Player player) {

		if (player.getPlayerInfo().getRewardNum() >= GameCFG
				.getEveryDayRewardNum())// 领奖次数不足
		{
			return Lang.F2103;
		}
		// 还未到领奖时间
		if (TimeKit.nowTimeMills() - player.getPlayerInfo().getNextRewardTime() < 0) {
			return Lang.F2104;
		}

		return null;

	}

	public void nextRewardTime(Player player, ByteBuffer data) {
		int rewardNum = player.getPlayerInfo().getRewardNum();
		if(rewardNum==GameCFG.getRewardSampleId().length)//如果领奖次数满了
		{
			data.writeInt(0);
			data.writeInt(0);
			return;
		}	
		
		if (player.getPlayerInfo().getNextRewardTime() == 0) {
			player.getPlayerInfo().setNextRewardTime(
					TimeKit.nowTimeMills()
							+ GameCFG.getRewardIntervalTimeByIndex(rewardNum)
							* 1000);
			data.writeInt(GameCFG.getRewardIntervalTimeByIndex(rewardNum));
			data.writeInt(GameCFG.getRewardSampleId().length-rewardNum);
		} else {
			if (player.getPlayerInfo().getNextRewardTime()
					- TimeKit.nowTimeMills() <= 0) {
				data.writeInt(0);
				data.writeInt(GameCFG.getRewardSampleId().length-rewardNum);
			} else {
				data.writeInt((int) (player.getPlayerInfo().getNextRewardTime() - TimeKit
						.nowTimeMills()) / 1000);
				data.writeInt(GameCFG.getRewardSampleId().length-rewardNum);
			}
		}
	}

}
