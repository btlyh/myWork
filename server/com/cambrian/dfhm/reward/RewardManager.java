package com.cambrian.dfhm.reward;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.card.Card;
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

	public void onlineReward(Player player,ByteBuffer data) {
		String error = checkReward(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}	
		int rewardNum = player.getPlayerInfo().getRewardNum();
/*		if(!isReward)//��һ�ν��� �����Ƿ�����콱 �����ص�һ�ο��콱��ID
		{
			data.writeInt(GameCFG.getRewardSampleIdByNum(rewardNum)); 
			data.writeInt(0);
			return;
		}*/
		
	/*	if (error == Lang.F2103) {
			data.writeInt(-1);
			return;
		}*/

		/*if (error == Lang.F2104) {
			if (rewardNum == 0) {
				data.writeInt(GameCFG.getRewardSampleIdByNum(rewardNum)); // ��һ���콱
				data.writeInt(0);
				return;
			} else {
				return;
			}

			 throw new DataAccessException(601,error);
		}*/

	//	if (isReward) {
			Reward reward = new Reward();

			reward = (Reward) Sample.getFactory().getSample(
					GameCFG.getRewardSampleIdByNum(rewardNum));
			// ������һ�ε��콱ʱ��
			player.getPlayerInfo().setNextRewardTime(
					TimeKit.nowTimeMills()
							+ GameCFG.getRewardIntervalTimeByIndex(rewardNum)
							* 1000);
			// �����������
			player.getPlayerInfo().setRewardNum(rewardNum + 1);
			// todo ���ӽ�����Ʒ

			player.setGold(player.getGold() + reward.getMoney());// ��ý�Ǯ
			player.setSoul(player.getSoul() + reward.getSoul());// ������
			player.setCurToken(player.getCurToken() + reward.getTired());// ��ý����ľ�����

			int[][] card = reward.getCard();
			int totalnum = 0;
			for (int i = 0; i < card.length; i++) {
				totalnum += card[i][1];
			}
			boolean isemail = false;
			int num = player.getCardBag().getSurplusCapacity();
			ArrayList<Card> templist = new ArrayList<Card>();
			if (num < totalnum)// ������Ʊ����������������ʼ�
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
				isemail = true;
			} else 
			{
				
				for (int i = 0; i < card.length; i++)
				{
					for (int j = 0; j < card[i][1]; j++)
					{
						templist.add(player.getCardBag().add(card[i][0]));
					}
				}
				
			}
			if(rewardNum==GameCFG.getRewardSampleId().length-1)
			{
				data.writeInt(-1); // ��һ���콱
				player.getPlayerInfo().setNextRewardId(-1);
			}else
			{
				
				Reward reward1 = new Reward();

				reward1 = (Reward) Sample.getFactory().getSample(
						GameCFG.getRewardSampleIdByNum(rewardNum+1));
				player.getPlayerInfo().setNextRewardId(reward1.getSid());
				data.writeInt(GameCFG.getRewardSampleIdByNum(rewardNum + 1)); // ��һ���콱
				if(isemail)
				{
					data.writeInt(0);
				}else {
					data.writeInt(totalnum);
				}	
				
				for (int i = 0; i < templist.size(); i++)
				{
					data.writeInt(templist.get(i).getId());
					data.writeInt(templist.get(i).getSid());
					data.writeInt(templist.get(i).getSkillId());
				}		
				
			}	
			
	//	}

	}

	private String checkReward(Player player) {

		if (player.getPlayerInfo().getRewardNum() >= GameCFG
				.getEveryDayRewardNum())// �콱��������
		{
			return Lang.F2103;
		}
		// ��δ���콱ʱ��
		if (TimeKit.nowTimeMills() - player.getPlayerInfo().getNextRewardTime() < 0) {
			return Lang.F2104;
		}

		return null;

	}

	public void nextRewardTime(Player player, ByteBuffer data) {
		int rewardNum = player.getPlayerInfo().getRewardNum();
		if(rewardNum==GameCFG.getRewardSampleId().length)//����콱��������
		{
			//throw new DataAccessException(601,Lang.F2103);
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
