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
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.rankings.dao.RankingsDao;
import com.cambrian.dfhm.rankings.entity.CardRankInfo;
import com.cambrian.dfhm.rankings.entity.RankInfo;
import com.cambrian.dfhm.rankings.entity.Rankings;
import com.cambrian.dfhm.rankings.timer.RankingsTimer;
import com.cambrian.dfhm.rankings.util.RankingsComparator;

/**
 * ��˵�������а��߼�������
 * 
 * @author��Zmk
 */
public class RankingsManager
{

	/* static fields */
	private static RankingsManager instance=new RankingsManager();
	/** ���ظ���TYPE 1��������2����ս��3�����ѣ�4������ */
	private static final int STORY=0,CHELLENGE=1,PAY=2,CARD=3;
	/** ������Ϣ��������� */
	private static final int SIZE=200;
	/** ��ʱ��������������Ϣ */
	private static long TIME=TimeKit.MIN_MILLS;

	/* static methods */
	public static RankingsManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** ���а����ݷ��ʶ��� */
	RankingsDao dao;
	/** ���ݷ����� */
	DataServer ds;
	/** ���а� */
	Rankings rankings=new Rankings();
	/** ���а�汾 */
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

	/** ��ʼ��ʱ�� */
	public void timerStart()
	{
		Timer rankingsTimer=new Timer();
		rankingsTimer.schedule(new RankingsTimer(),0l,TIME); // ���ڷ������������ʱ��������а���Ϣ��������
	}

	/* init start */

	/* methods */

	/** ����������Ϣ */
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
	 * �ϲ����ݿ����к���������
	 * 
	 * @param onlineRankings ���ߵ�������Ϣ
	 * @param dbRankings ���ݿ��е�������Ϣ
	 * @return �ϲ���ɺ������
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
		// �������в�һ�� ��������
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
	 * �ϲ������е��б�
	 * 
	 * @param dbList ���ݿ������е��б�
	 * @param onlineList ���������е��б�
	 * @return �ϲ��ɹ�֮��������б�
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

	/** �õ�һ�����ݿ��е����� */
	private Rankings getDbRankings()
	{
		List<Player> dbPlayers=dao.getAllPlayer();
		Rankings dbRankings=handleRankings(dbPlayers);
		return dbRankings;
	}

	/** �õ�һ��������ҵ����� */
	private Rankings getOnlineRankings()
	{
		List<Player> onlinePlayers=getAllOnlinePlayer();
		Rankings onlineRankings=handleRankings(onlinePlayers);
		return onlineRankings;
	}

	/** ���������б� */
	private Rankings handleRankings(List<Player> players)
	{
		Rankings ranking=new Rankings();
		for(Player player:players)
		{
			RankInfo rankInfo=new RankInfo();
			rankInfo.setPlayerName(player.getNickname());
			rankInfo.setInfo(player.getCurIndexForNormalNPC()-1);
			rankInfo.setTime(player.getPlayerInfo().getNormalNPCTime());
			ranking.getStoryRankings().add(rankInfo);

			rankInfo=new RankInfo();
			rankInfo.setPlayerName(player.getNickname());
			rankInfo.setInfo(player.getPlayerInfo().getHardNPCIndex());
			rankInfo.setTime(player.getPlayerInfo().getHardNPCTime());
			ranking.getChallengeRankings().add(rankInfo);

			rankInfo=new RankInfo();
			rankInfo.setPlayerName(player.getNickname());
			rankInfo.setInfo(player.getPlayerInfo().getPayRMB());
			ranking.getPayRankings().add(rankInfo);

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

	/** ��ȡ����������Ҷ��� */
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
	 * �������а�
	 * 
	 * @param player ��ǰ���
	 * @return �������б�������а�
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
	 * ���ظ���
	 * 
	 * @param type �����ĸ�����
	 * @param index �Ӷ��ٿ�ʼ
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

	/** �����ظ��� */
	private String checkLoadMore(int type,int index)
	{
		if(type<STORY||type>CARD)
		{
			return Lang.F1901; // ���ʹ���
		}
		if(index<1||index>199)
		{
			return Lang.F1902; // �±곬��
		}
		return null;
	}
}
