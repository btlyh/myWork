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
 * ��˵�������Ƴ齱�߼���
 * 
 * @author��LazyBear
 */
public class LuckBoxManager
{

	/* static fields */
	private static LuckBoxManager instance=new LuckBoxManager();
	/** ���ˢ�¡����ˢ�� */
	private static final int FLUSH_FREE=0,FLUSH_SOUL=1;

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
	 * ֱ�ӹ���
	 * 
	 * @param player ��Ҷ���
	 * @param needGold ����RMB
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
			Mail mail=mf.createSystemMail(cards,0,0,0,0,0,(int)player.getUserId());
			player.addMail(mail);
		}
		else
		{
			card=player.getCardBag().add(cardSid);
		}
		player.getPlayerInfo().setBestCardSid(0);
		return card;
	}

	/**
	 * ���ֱ�ӹ���
	 * 
	 * @param player ��Ҷ���
	 * @param needGold �����Ǯ
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
		// if(player.getVipLevel()<3)
		// {
		// resultMap.put("error",Lang.F1610);
		// return resultMap;
		// }
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
		if(cardSid!=player.getPlayerInfo().getBestCardSid())
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
	 * ��ȡ����
	 * 
	 * @param player ��Ҷ���
	 * @param needGold ����RMB
	 * @param index λ��
	 * @return
	 */
	public Card takeCard(Player player,int needGold,int index)
	{
		Map<String,Object> resultMap=checkTakeCard(player,needGold);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		LuckBoxCfg lbc=(LuckBoxCfg)resultMap.get("lbc");
		if(player.getPlayerInfo().getLuckBoxFreeTimes()>0)
		{
			player.getPlayerInfo().decrLuckBoxFreeTimes(1);
		}
		else
		{
			if(player.getPlayerInfo().isFirst())
			{
				if(player.getSoul()>lbc.gettNeedSoul())
				{
					player.decrSoul(lbc.gettNeedSoul());
					player.getPlayerInfo().setFirst(false);
				}
				else
				{
					player.getPlayerInfo().inPayTimes(1);
					player.decrGold(needGold);
				}
			}
			else
			{
				player.getPlayerInfo().inPayTimes(1);
				player.decrGold(needGold);
			}

		}
		TakeCardRecord cardRecord=getRandomCard(player.getPlayerInfo()
			.getTakeCardRecords());
		Card card=null;
		if(player.getCardBag().getSurplusCapacity()<=0)
		{
			ArrayList<Integer> cards=new ArrayList<Integer>();
			cards.add(cardRecord.getSid());
			Mail mail=mf.createSystemMail(cards,0,0,0,0,0,(int)player.getUserId());
			player.addMail(mail);
		}
		else
		{
			card=player.getCardBag().add(cardRecord.getSid());
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
	 * ����ȡ����
	 * 
	 * @param player ��Ҷ���
	 * @return
	 */
	private Map<String,Object> checkTakeCard(Player player,int needGold)
	{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		LuckBoxCfg lbc=(LuckBoxCfg)Sample.getFactory().getSample(
			player.getPlayerInfo().getLuckBoxSid());
		if(lbc==null)
		{
			resultMap.put("error",Lang.F1603);
			return resultMap;
		}
		if(player.getPlayerInfo().isFirst()
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
		}
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
	 * ˢ�»�ȡ�����б�
	 * 
	 * @param player �û�����
	 * @param type 0��Ȼˢ�� 1���ˢ��
	 * @return ����ʣ��ʱ��
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
			if(TimeKit.nowTimeMills()-lastTime>LuckBoxCfg.CDTime)
			{
				player.getPlayerInfo().setLastTime(TimeKit.nowTimeMills());
				player.getPlayerInfo().setTakeCardRecords(
					getCardRecord(getRandomCards(lbc)));
				player.getPlayerInfo().initBestCardSid();
				player.getPlayerInfo().setFirst(true);
				player.getPlayerInfo().setPayTimes(0);
			}
		}
		else if(type==FLUSH_SOUL)
		{
			player.decrSoul(lbc.getNeedSoul());
			player.getPlayerInfo().setLastTime(TimeKit.nowTimeMills());
			player.getPlayerInfo().setTakeCardRecords(
				getCardRecord(getRandomCards(lbc)));
			player.getPlayerInfo().initBestCardSid();
			player.getPlayerInfo().setFirst(true);
			player.getPlayerInfo().setPayTimes(0);
		}
		return LuckBoxCfg.CDTime-(TimeKit.nowTimeMills()-lastTime);
	}

	/**
	 * ����ȡ�����б�
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
		resultMap.put("error",null);
		resultMap.put("lbc",lbc);
		return resultMap;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param lbc �齱���ö���
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
			if((card.getType()==4||card.getType()==5))// �Ƿ����Ͽ����߳ȿ�
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
	 * ��ȡ����
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
	 * ��ȡ���Ƽ�¼
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
