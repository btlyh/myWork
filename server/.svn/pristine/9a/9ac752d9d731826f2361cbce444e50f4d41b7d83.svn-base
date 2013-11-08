package com.cambrian.dfhm.rankings.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.rankings.util.RankingsComparator;

/**
 * 类说明：排行榜
 * 
 * @author：Zmk
 * 
 */
public class Rankings
{

	/* static fields */
	/** 一次发送排行的数量 */
	public static final int LENTH=100;
	
	/* static methods */

	/* fields */
	/** 剧情副本排名 */
	private List<RankInfo> storyRankings = new ArrayList<RankInfo>();
	/** 挑战副本排名 */
	private List<RankInfo> challengeRankings = new ArrayList<RankInfo>();
	/** 充值排名 */
	private List<RankInfo> payRankings = new ArrayList<RankInfo>();
	/** 卡牌排名 */
	private List<CardRankInfo> cardRankings = new ArrayList<CardRankInfo>();

	/* constructors */

	/* properties */
	public List<RankInfo> getStoryRankings()
	{
		return storyRankings;
	}

	public void setStoryRankings(List<RankInfo> storyRankings)
	{
		this.storyRankings = storyRankings;
	}

	public List<RankInfo> getChallengeRankings()
	{
		return challengeRankings;
	}

	public void setChallengeRankings(List<RankInfo> challengeRankings)
	{
		this.challengeRankings = challengeRankings;
	}

	public List<RankInfo> getPayRankings()
	{
		return payRankings;
	}

	public void setPayRankings(List<RankInfo> payRankings)
	{
		this.payRankings = payRankings;
	}

	public List<CardRankInfo> getCardRankings()
	{
		return cardRankings;
	}

	public void setCardRankings(List<CardRankInfo> cardRankings)
	{
		this.cardRankings = cardRankings;
	}

	/* init start */

	/* methods */
	/** 从列表中找到某玩家的排名 */
	public int getPlayerRankInfo(Player player, List<RankInfo> list)
	{
		for (RankInfo rankInfo : list)
		{
			if (rankInfo.getPlayerName().equals(player.getNickname()))
			{
				return list.indexOf(rankInfo) + 1;
			}
		}
		return 0;
	}
	/** 获取卡牌排名 */
	public int getCardRankInfo(Card card)
	{
		for (CardRankInfo rankInfo : cardRankings)
		{
			if (rankInfo.getCardUid() == card.getId())
			{
				return cardRankings.indexOf(rankInfo) + 1;
			}
		}
		return 0;
	}

	/** 排序 */
	private void sortList(List<?> list)
	{
		Comparator<Object> listComarator = new RankingsComparator();
		Collections.sort(list, listComarator);
	}

	/** 序列化 前台 写 */
	public void bytesWrite(ByteBuffer data)
	{
		int len = storyRankings.size() > LENTH ? LENTH : storyRankings.size();
		data.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			RankInfo rankInfo = storyRankings.get(i);
			rankInfo.bytesWrite(data);
		}
		len = challengeRankings.size() > LENTH ? LENTH : challengeRankings.size();
		data.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			RankInfo rankInfo = challengeRankings.get(i);
			rankInfo.bytesWrite(data);
		}
		len = payRankings.size() > LENTH ? LENTH : payRankings.size();
		data.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			RankInfo rankInfo = payRankings.get(i);
			rankInfo.bytesWrite(data);
		}
		len = cardRankings.size() > LENTH ? LENTH : cardRankings.size();
		data.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			CardRankInfo rankInfo = cardRankings.get(i);
			rankInfo.bytesWrite(data);
		}
	}

	/** 序列化 DC 写 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(storyRankings.size());
		for (RankInfo rankInfo : storyRankings)
		{
			rankInfo.bytesWrite(data);
		}
		data.writeInt(challengeRankings.size());
		for (RankInfo rankInfo : challengeRankings)
		{
			rankInfo.bytesWrite(data);
		}
		data.writeInt(payRankings.size());
		for (RankInfo rankInfo : payRankings)
		{
			rankInfo.bytesWrite(data);
		}
		data.writeInt(cardRankings.size());
		for (CardRankInfo rankInfo : cardRankings)
		{
			rankInfo.bytesWrite(data);
		}
	}

	/** 序列化 DC 读 */
	public void dbBytesRead(ByteBuffer data)
	{
		int len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			RankInfo rankInfo = new RankInfo();
			rankInfo.dbBytesRead(data);
			storyRankings.add(rankInfo);
		}
		sortList(storyRankings);
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			RankInfo rankInfo = new RankInfo();
			rankInfo.dbBytesRead(data);
			challengeRankings.add(rankInfo);
		}
		sortList(challengeRankings);
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			RankInfo rankInfo = new RankInfo();
			rankInfo.dbBytesRead(data);
			payRankings.add(rankInfo);
		}
		sortList(payRankings);
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			CardRankInfo rankInfo = new CardRankInfo();
			rankInfo.dbBytesRead(data);
			cardRankings.add(rankInfo);
		}
		sortList(cardRankings);
	}
}
