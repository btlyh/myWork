package com.cambrian.dfhm.common.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.LuckBoxCfg;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.common.entity.TakeCardRecord;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.util.MailFactory;

/**
 * 类说明：卡牌抽奖逻辑类
 * 
 * @author：LazyBear
 */
public class LuckBoxManager
{

	/* static fields */
	private static LuckBoxManager instance=new LuckBoxManager();
	/** 免费刷新、武魂刷新 ,金币刷新*/
	private static final int FLUSH_FREE=0,FLUSH_SOUL=1,FLUSH_GOLD=2;

	/* static methods */
	public static LuckBoxManager getInstance()
	{
		return instance;
	}

	/* fields */
	MailFactory mf;

	/* constructors */

	/* properties */
	public void setMailFactory(MailFactory mf)
	{
		instance.mf=mf;
	}
	/* init start */

	/* methods */

	/**
	 * 直接购买
	 * 
	 * @param player 玩家对象
	 * @param needGold 所需RMB
	 * @return
	 */
	public Card buyCard(Player player,int needGold,int cardSid)
	{
		Map<String,Object> resultMap=checkBuyCard(player,needGold,cardSid);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(needGold);
		Card card=null;
		if(player.getCardBag().getSurplusCapacity()<=0)
		{
			ArrayList<Integer> cards=new ArrayList<Integer>();
			cards.add(cardSid);
			Mail mail=mf.createSystemMail(cards,0,0,0,0,0,
				(int)player.getUserId());
			player.addMail(mail);
		}
		else
		{
			card=player.getCardBag().add(cardSid,player.getAchievements());
		}
		player.getPlayerInfo().setBuyed(true);
		return card;
	}

	/**
	 * 检查直接购买
	 * 
	 * @param player 玩家对象
	 * @param needGold 所需金钱
	 * @return
	 */
	private Map<String,Object> checkBuyCard(Player player,int needGold,
		int cardSid)
	{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		LuckBoxCfg lbc=(LuckBoxCfg)Sample.getFactory().getSample(
			player.getPlayerInfo().getLuckBoxSid());
		if(lbc==null)
		{
			resultMap.put("error",Lang.F1603);
			return resultMap;
		}
		if(needGold>player.getGold())
		{
			resultMap.put("error",Lang.F1606);
			return resultMap;
		}
		if(needGold!=lbc.getBuyGold())
		{
			resultMap.put("error",Lang.F1607);
			return resultMap;
		}
		if(player.getPlayerInfo().isBuyed())
		{
			resultMap.put("error",Lang.F1608);
			return resultMap;
		}
		Card card=(Card)Sample.getFactory().getSample(cardSid);
		if(card==null)
		{
			resultMap.put("error",Lang.F1609);
			return resultMap;
		}
		resultMap.put("error",null);
		return resultMap;
	}

	/***
	 * 抽取卡牌
	 * 
	 * @param player 玩家对象
	 * @param needGold 所需RMB
	 * @param index 位置
	 * @return
	 */
	public Card takeCard(Player player,int needGold,int index,Boolean type)
	{
		Map<String,Object> resultMap=checkTakeCard(player,needGold,type);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		LuckBoxCfg lbc=(LuckBoxCfg)resultMap.get("lbc");		
		if (type)//武魂抽取
		{
			player.decrSoul(lbc.gettNeedSoul()*(player.getPlayerInfo().getExtractTimes()+1));
			player.getPlayerInfo().inSoulextractTimes(1);
		
		}else
		{
			player.decrGold(lbc.getNeedGold()*(player.getPlayerInfo().getExtractTimes()+1));
			player.getPlayerInfo().inSoulextractTimes(1);		
		}
		TakeCardRecord cardRecord=getRandomCard(player.getPlayerInfo()
			.getTakeCardRecords());
		Card card=null;
		if(player.getCardBag().getSurplusCapacity()<=0)
		{
			ArrayList<Integer> cards=new ArrayList<Integer>();
			cards.add(cardRecord.getSid());
			Mail mail=mf.createSystemMail(cards,0,0,0,0,0,
				(int)player.getUserId());
			player.addMail(mail);
		}
		else
		{
			card=player.getCardBag().add(cardRecord.getSid(),player.getAchievements());
		}
		if(card==null)
		{
			card=new Card();
			card.setSid(cardRecord.getSid());
		}
		player.getPlayerInfo().setIndex(cardRecord,index);
		return card;
	}

	/**
	 * 检查抽取卡牌
	 * 
	 * @param player 玩家对象
	 * @return
	 */
	private Map<String,Object> checkTakeCard(Player player,int needGold,boolean type)
	{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		LuckBoxCfg lbc=(LuckBoxCfg)Sample.getFactory().getSample(
			player.getPlayerInfo().getLuckBoxSid());
		int usergold =0;
		if(lbc==null)
		{
			resultMap.put("error",Lang.F1603);
			return resultMap;
		}
		if(type)//武魂获取
		{
			int useGold=(player.getPlayerInfo().getExtractTimes()+1)
					*lbc.gettNeedSoul();
			usergold = player.getSoul();
				
			
			if(needGold!=useGold)//武魂抽奖 客户端消耗和服务器不一样
			{
				resultMap.put("error",Lang.F1614);
				return resultMap;
			}else
			{
				if(usergold<useGold)
				{
					resultMap.put("error",Lang.F1613);
					return resultMap;
				}	
			}	
		}else//金币抽取
		{
			int useGold=(player.getPlayerInfo().getExtractTimes()+1)
					*lbc.getNeedGold();
			usergold = player.getGold();
			if(needGold!=useGold)//金币抽奖 客户端消耗和服务器不一样
			{
				resultMap.put("error",Lang.F1604);
				return resultMap;
			}else
			{
				if(needGold>usergold)//武魂抽奖   金币不足
				{
					resultMap.put("error",Lang.F1605);
					return resultMap;
				}	
			}	
		}	
		
		
		/*if(player.getPlayerInfo().isFirst()
			&&player.getSoul()<lbc.gettNeedSoul())
		{
			if(needGold>player.getGold())
			{
				resultMap.put("error",Lang.F1605);
				return resultMap;
			}
		}
		if(!player.getPlayerInfo().isFirst())
		{
			if(needGold>player.getGold())
			{
				resultMap.put("error",Lang.F1605);
				return resultMap;
			}
		}
		int useGold=(player.getPlayerInfo().getPayTimes()+1)
			*lbc.getNeedGold();
		if(player.getPlayerInfo().getLuckBoxFreeTimes()<1&&needGold!=useGold)
		{
			resultMap.put("error",Lang.F1604);
			return resultMap;
		}*/
		boolean isAllTake=true;
		for(TakeCardRecord cardRecord:player.getPlayerInfo()
			.getTakeCardRecords())
		{
			if(!cardRecord.isTkae())
			{
				isAllTake=false;
			}
		}
		if(isAllTake)
		{
			resultMap.put("error",Lang.F1611);
			return resultMap;
		}
		resultMap.put("lbc",lbc);
		resultMap.put("error",null);
		return resultMap;
	}
	/**
	 * 刷新获取卡牌列表
	 * 
	 * @param player 用户对象
	 * @param type 0自然刷新 1武魂刷新
	 * @return 返回剩余时间
	 */
	public Long flushCardList(Player player,int type)
	{
		Map<String,Object> resultMap=checkGetCardList(player,type);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		LuckBoxCfg lbc=(LuckBoxCfg)resultMap.get("lbc");
		long lastTime=player.getPlayerInfo().getLastTime();
		if(type==FLUSH_FREE)
		{
			if(TimeKit.nowTimeMills()-lastTime>=LuckBoxCfg.CDTime)
			{
				player.getPlayerInfo().setLastTime(TimeKit.nowTimeMills());
				player.getPlayerInfo().setTakeCardRecords(
					getCardRecord(getRandomCards(lbc)));
				player.getPlayerInfo().initBestCardSid();
				player.getPlayerInfo().setExtractTimes(0);
			//	player.getPlayerInfo().setSoulTimes(0);
			}
		}
		else if(type==FLUSH_SOUL)
		{
			player.decrSoul(lbc.getNeedSoul());
			player.getPlayerInfo().setLastTime(TimeKit.nowTimeMills());
			player.getPlayerInfo().setTakeCardRecords(
				getCardRecord(getRandomCards(lbc)));
			player.getPlayerInfo().initBestCardSid();
			player.getPlayerInfo().setExtractTimes(0);
		//	player.getPlayerInfo().setSoulTimes(0);
		}
		else if(type==FLUSH_GOLD)
		{
			player.decrGold(lbc.getFlushGold());
			player.getPlayerInfo().setLastTime(TimeKit.nowTimeMills());
			player.getPlayerInfo().setTakeCardRecords(
				getCardRecord(getRandomCards(lbc)));
			player.getPlayerInfo().initBestCardSid();
			player.getPlayerInfo().setExtractTimes(0);
		//	player.getPlayerInfo().setSoulTimes(0);
		}
		
		if(type==FLUSH_SOUL||type==FLUSH_GOLD)
		{
			return LuckBoxCfg.CDTime;
		}
		long surplusTime=LuckBoxCfg.CDTime-(TimeKit.nowTimeMills()-lastTime);
		return lastTime<1?LuckBoxCfg.CDTime:surplusTime;

	}

	/**
	 * 检查获取卡牌列表
	 * 
	 * @param player
	 * @return
	 */
	private Map<String,Object> checkGetCardList(Player player,int type)
	{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(player.getPlayerInfo().getLuckBoxSid()==0)
		{
			resultMap.put("error",Lang.F1601);
			return resultMap;
		}
		LuckBoxCfg lbc=(LuckBoxCfg)Sample.getFactory().getSample(
			player.getPlayerInfo().getLuckBoxSid());
		if(lbc==null)
		{
			resultMap.put("error",Lang.F1603);
			return resultMap;
		}
		if(type==FLUSH_SOUL)
		{
			if(player.getSoul()<lbc.getNeedSoul())
			{
				resultMap.put("error",Lang.F1602);
				return resultMap;
			}
		}
		
		if(type==FLUSH_GOLD)
		{
			if(player.getGold()<lbc.getFlushGold())
			{
				resultMap.put("error", Lang.F1612);
				return resultMap;
			}	
		}	
		resultMap.put("error",null);
		resultMap.put("lbc",lbc);
		return resultMap;
	}

	/**
	 * 获取随机卡组
	 * 
	 * @param lbc 抽奖配置对象
	 * @return
	 */
	private ArrayList<Integer> getRandomCards(LuckBoxCfg lbc)
	{
		ArrayList<Integer> cards=new ArrayList<Integer>();
		boolean isSave=false;
		for(int i=0;i<lbc.getMaxTakeSize();i++)
		{
			int cardSid=MathKit.randomValue(lbc.getStartCardSid(),
				lbc.getEndCardSid()+1);
			Card card=(Card)Sample.getFactory().getSample(cardSid);
			if(card.getType()>6)// 是否是紫卡或者橙卡
			{
				if(isSave)
				{
					i--;
					continue;
				}
				else
				{
					isSave=true;
				}
			}
			cards.add(cardSid);
		}
		return cards;
	}
	/**
	 * 抽取卡牌
	 * 
	 * @param randomCards
	 * @return
	 */
	private TakeCardRecord getRandomCard(
		ArrayList<TakeCardRecord> randomCards)
	{
		int index=0;
		for(;;)
		{
			index=MathKit.randomValue(0,randomCards.size());
			if(!randomCards.get(index).isTkae())
			{
				System.err.println(randomCards.get(index));
				TakeCardRecord cardRecord=randomCards.get(index);
				cardRecord.setTake(true);
				return cardRecord;
			}
		}
	}
	/***
	 * 获取卡牌记录
	 * 
	 * @param cards
	 * @return
	 */
	private ArrayList<TakeCardRecord> getCardRecord(ArrayList<Integer> cards)
	{
		ArrayList<TakeCardRecord> takeCardRecords=new ArrayList<TakeCardRecord>();
		for(int i=0;i<cards.size();i++)
		{
			TakeCardRecord cardRecord=new TakeCardRecord();
			cardRecord.setSid(cards.get(i));
			cardRecord.setIndex(i);
			cardRecord.setTake(false);
			takeCardRecords.add(cardRecord);
		}
		return takeCardRecords;
	}
}
