package com.cambrian.dfhm.rankings.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.cambrian.game.Session;
import com.cambrian.game.SessionMap;
import com.cambrian.game.ds.DataServer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.NPC;
import com.cambrian.dfhm.instancing.entity.NormalNPC;
import com.cambrian.dfhm.rankings.dao.RankingsDao;
import com.cambrian.dfhm.rankings.entity.CardRankInfo;
import com.cambrian.dfhm.rankings.entity.RankInfo;
import com.cambrian.dfhm.rankings.entity.Rankings;
import com.cambrian.dfhm.rankings.timer.RankingsTimer;
import com.cambrian.dfhm.rankings.util.RankingsComparator;

/**
 * 类说明：排行榜逻辑处理类
 * 
 * @author：Zmk
 */
public class RankingsManager
{

	/* static fields */
	private static RankingsManager instance=new RankingsManager();
	/** 加载更多TYPE 1：副本，2：挑战，3：付费，4：卡牌 */
	private static final int STORY=0,CHELLENGE=1,PAY=2,CARD=3;
	/** 排行信息保存的上限 */
	private static final int SIZE=200;
	/** 计时器：更新排行信息 */
	private static long TIME=TimeKit.MIN_MILLS*30;

	/* static methods */
	public static RankingsManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** 排行榜数据访问对象 */
	RankingsDao dao;
	/** 数据服务器 */
	DataServer ds;
	/** 排行榜 */
	Rankings rankings=new Rankings();
	/** 排行榜版本 */
	private int version=0;

	/* constructors */

	/* properties */
	public void setRankingsDao(RankingsDao dao)
	{
		instance.dao=dao;
	}

	public void setDS(DataServer ds)
	{
		instance.ds=ds;
	}

	public int getVersion()
	{
		return version;
	}

	/** 开始定时器 */
	public void timerStart()
	{
		Timer rankingsTimer=new Timer();
		rankingsTimer.schedule(new RankingsTimer(),0l,TIME); // 会在服务器起起的来时候加载排行榜信息到缓存中
	}

	/* init start */

	/* methods */

	/** 更新排行信息 */
	public void flushRankings()
	{
		Rankings onlineRankings=getOnlineRankings();
		Rankings dbRankings=getDbRankings();
		rankings=mergeRankings(onlineRankings,dbRankings);
		dao.setRankings(rankings);
		version++;
		// for (RankInfo rankInfo : rankings.getStoryRankings())
		// {
		// System.err.println(rankInfo.toString());
		// }
		// for (CardRankInfo cardRankInfo : rankings.getCardRankings())
		// {
		// System.err.println(cardRankInfo.toString());
		// }
	}

	/**
	 * 合并数据库排行和在线排行
	 * 
	 * @param onlineRankings 在线的排行信息
	 * @param dbRankings 数据库中的排行信息
	 * @return 合并完成后的排行
	 */
	private Rankings mergeRankings(Rankings onlineRankings,
		Rankings dbRankings)
	{
		Rankings finalRankings=new Rankings();
		List<RankInfo> story=mergeList(dbRankings.getStoryRankings(),
			onlineRankings.getStoryRankings());
		finalRankings.setStoryRankings(story);

		List<RankInfo> challenge=mergeList(
			dbRankings.getChallengeRankings(),
			onlineRankings.getChallengeRankings());
		finalRankings.setChallengeRankings(challenge);

		List<RankInfo> pay=mergeList(dbRankings.getPayRankings(),
			onlineRankings.getPayRankings());
		finalRankings.setPayRankings(pay);
		// 卡牌排行不一样 单独处理
		List<CardRankInfo> cards=new ArrayList<CardRankInfo>();
		cards.addAll(dbRankings.getCardRankings());
		cards.removeAll(onlineRankings.getCardRankings());
		cards.addAll(onlineRankings.getCardRankings());

		Comparator<Object> listComarator=new RankingsComparator();
		Collections.sort(cards,listComarator);

		for(int i=0;i<cards.size();i++)
		{
			if(i>=SIZE) cards.remove(i);
		}
		finalRankings.setCardRankings(cards);
		return finalRankings;
	}

	/**
	 * 合并排行中的列表
	 * 
	 * @param dbList 数据库排行中的列表
	 * @param onlineList 在线排行中的列表
	 * @return 合并成功之后并排序的列表
	 */
	private List<RankInfo> mergeList(List<RankInfo> dbList,
		List<RankInfo> onlineList)
	{
		List<RankInfo> resultList=new ArrayList<RankInfo>();
		resultList.addAll(dbList);
		resultList.removeAll(onlineList);
		resultList.addAll(onlineList);

		Comparator<Object> listComarator=new RankingsComparator();
		Collections.sort(resultList,listComarator);

		for(int i=0;i<resultList.size();i++)
		{
			if(i>=SIZE) resultList.remove(i);
		}
		return resultList;
	}

	/** 得到一份数据库中的排行 */
	private Rankings getDbRankings()
	{
		List<Player> dbPlayers=dao.getAllPlayer();
		Rankings dbRankings=handleRankings(dbPlayers);
		return dbRankings;
	}

	/** 得到一份在线玩家的排行 */
	private Rankings getOnlineRankings()
	{
		List<Player> onlinePlayers=getAllOnlinePlayer();
		Rankings onlineRankings=handleRankings(onlinePlayers);
		return onlineRankings;
	}

	/** 处理排行列表 */
	private Rankings handleRankings(List<Player> players)
	{
		Rankings ranking=new Rankings();
		for(Player player:players)
		{
			RankInfo rankInfo=new RankInfo();
			NPC npc = null;
			if (player.getCurIndexForNormalNPC()-1 > 0)
				npc = (NPC)Sample.getFactory().getSample(player.getCurIndexForNormalNPC()-1);
			if (npc != null && npc instanceof NormalNPC)
			{
				rankInfo.setPlayerName(player.getNickname());
				rankInfo.setInfo(player.getCurIndexForNormalNPC()-1);
				rankInfo.setTime(player.getPlayerInfo().getNormalNPCTime());
				ranking.getStoryRankings().add(rankInfo);
			}

			if (player.getPlayerInfo().getHighestHardNPC() > 0)
			{
				rankInfo=new RankInfo();
				rankInfo.setPlayerName(player.getNickname());
				rankInfo.setInfo(player.getPlayerInfo().getHighestHardNPC());
				rankInfo.setTime(player.getPlayerInfo().getHardNPCTime());
				ranking.getChallengeRankings().add(rankInfo);
			}

			if (player.getPlayerInfo().getPayRMB() > 0)
			{
				rankInfo=new RankInfo();
				rankInfo.setPlayerName(player.getNickname());
				rankInfo.setInfo(player.getPlayerInfo().getPayRMB());
				ranking.getPayRankings().add(rankInfo);
			}

			for(Card card:player.getCardBag().getList())
			{
				CardRankInfo cardInfo=new CardRankInfo();
				cardInfo.setPlayerName(player.getNickname());
				cardInfo.setCardName(card.getName());
				cardInfo.setInfo(card.getZhandouli());
				cardInfo.setCardUid(card.getId());
				ranking.getCardRankings().add(cardInfo);
			}
		}
		return ranking;
	}

	/** 获取所有在线玩家对象 */
	private List<Player> getAllOnlinePlayer()
	{
		List<Player> onlineList=new ArrayList<Player>();
		SessionMap sm=ds.getSessionMap();
		Session[] sessions=sm.getSessions();
		for(Session session:sessions)
		{
			if(session!=null)
			{
				Player player=(Player)session.getSource();
				if(player!=null) onlineList.add(player);
			}
		}
		return onlineList;
	}

	/**
	 * 进入排行榜
	 * 
	 * @param player 当前玩家
	 * @return 服务器中保存的排行榜
	 */
	public Map<String,Object> enterRankings(Player player)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		mapInfo.put("rankings",rankings);
		List<Card> cardsList=player.getCardBag().getList();
		Comparator<Object> cardComarator=new RankingsComparator();
		Collections.sort(cardsList,cardComarator);
		Card card=cardsList.get(0);
		mapInfo.put("card",card);
		return mapInfo;
	}

	/**
	 * 加载更多
	 * 
	 * @param type 加载哪个排行
	 * @param index 从多少开始
	 * @return
	 */
	public Map<String,Object> loadMore(int type,int index)
	{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String error=checkLoadMore(type,index);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		if(type==STORY)
		{
			List<RankInfo> stroyList=new ArrayList<RankInfo>();
			if(index>=rankings.getStoryRankings().size())
			{
				resultMap.put("result",false);
			}
			else
			{
				for(int i=index;i<rankings.getStoryRankings().size();i++)
				{
					if(i>=index+Rankings.LENTH) break;
					RankInfo rankInfo=rankings.getStoryRankings().get(i);
					stroyList.add(rankInfo);
				}
				resultMap.put("list",stroyList);
				resultMap.put("result",true);
			}
		}
		if(type==CHELLENGE)
		{
			List<RankInfo> chellengeList=new ArrayList<RankInfo>();
			if(index>=rankings.getStoryRankings().size())
			{
				resultMap.put("result",false);
			}
			else
			{
				for(int i=index;i<rankings.getChallengeRankings().size();i++)
				{
					if(i>=index+Rankings.LENTH) break;
					RankInfo rankInfo=rankings.getChallengeRankings().get(i);
					chellengeList.add(rankInfo);
				}
				resultMap.put("list",chellengeList);
				resultMap.put("result",true);
			}
		}
		if(type==PAY)
		{
			List<RankInfo> payList=new ArrayList<RankInfo>();
			if(index>=rankings.getStoryRankings().size())
			{
				resultMap.put("result",false);
			}
			else
			{
				for(int i=index;i<rankings.getPayRankings().size();i++)
				{
					if(i>=index+Rankings.LENTH) break;
					RankInfo rankInfo=rankings.getPayRankings().get(i);
					payList.add(rankInfo);
				}
				resultMap.put("list",payList);
				resultMap.put("result",true);
			}
		}
		if(type==CARD)
		{
			List<CardRankInfo> cardList=new ArrayList<CardRankInfo>();
			if(index>=rankings.getCardRankings().size())
			{
				resultMap.put("result",false);
			}
			else
			{
				for(int i=index;i<rankings.getCardRankings().size();i++)
				{
					if(i>=index+Rankings.LENTH) break;
					CardRankInfo rankInfo=rankings.getCardRankings().get(i);
					cardList.add(rankInfo);
				}
				resultMap.put("list",cardList);
				resultMap.put("result",true);
			}
		}
		return resultMap;
	}

	/** 检查加载更多 */
	private String checkLoadMore(int type,int index)
	{
		if(type<STORY||type>CARD)
		{
			return Lang.F1901; // 类型错误
		}
		if(index<1||index>199)
		{
			return Lang.F1902; // 下标超出
		}
		return null;
	}
}
