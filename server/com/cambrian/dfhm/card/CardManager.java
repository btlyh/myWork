package com.cambrian.dfhm.card;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：
 * 
 * @author：Sebastian
 */
public class CardManager
{

	/* static fields */
	private static CardManager instance=new CardManager();

	/** 最大卡牌上阵数 */
	public static final int MAXBATTLECOUNT=5;
	/** 游戏币培养消耗数，RMB培养消耗数 */
	public static final int FORSTERMONEY=10,FORSTERGOLD=100;
	/** 锁定技能的倍率  当前倍率为2  当前锁定一个 为 价格*2 2个为 价格*4*/
	public static final int FORSTERPERNUM = 2;
	

	/* static methods */
	public static CardManager getInstance()
	{
		return instance;
	}

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

	/** 卡牌锁定 */
	public void lockCard(Player player,int cardId,boolean state)
	{
		Card card=player.getCardBag().getById(cardId);
		String error=checkCard(card);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		card.setLock(state);
	}

	/** 卡牌吞噬 */
	public boolean[] engulfCard(Player player,int cardId,
		ArrayList<Integer> list)
	{
		Card card=player.getCardBag().getById(cardId);
		String error=checkCard(card);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		boolean result[];
		if(card.isRealm())
		{
			result=realmBeyong(player,cardId,list);
		}
		else
		{
			result=levelUp(player,cardId,list);
		}
		return result;
	}

	/** 卡牌检查 */
	private String checkCard(Card card)
	{
		if(card==null) return Lang.F1200;

		return null;
	}

	/** 批量出售卡牌 */
	public void sellCard(Player player,ArrayList<Integer> list,int money)
	{
		String error=checkSellCard(player,list,money);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		int id;
		for(int i=0;i<list.size();i++)
		{
			id=list.get(i);
			player.getCardBag().remove(id);
		}
		player.incrMoney(money);
	}

	/*计算卡牌的倍率**/
	
	/** 出售卡牌检查 */
	private String checkSellCard(Player player,ArrayList<Integer> list,
		int money)
	{
		int id;// 卡片id
		Card card;// 卡牌对象
		int cardMoney=0;
		for(int i=0;i<list.size();i++)
		{
			id=list.get(i);
			card=player.getCardBag().getById(id);
			if(card==null)
			{
				return Lang.F1200;
			}
			if(card.getStatus()!=Card.NORMAL&&card.isLock())
			{
				return Lang.F1201;
			}
			cardMoney+=card.getMoney();
		}
		if(cardMoney!=money)
		{
			return Lang.F1222;
		}
		return null;
	}

	/** 选择卡牌上阵 */
	private void selectBattle(Player player,Card card,int index,
		BattleCard[] array)
	{
		String error=checkSelectBattle(card,index,array);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		card.setStatus(Card.ATTACK);
		BattleCard bCard=new BattleCard(card.getId(),card.getName(),
			card.getAvatar(),card.getTinyAvatar(),card.getLevel(),
			card.getAtt(),card.getSkillRate(),card.getAttRange(),
			card.getSkillId(),card.getMaxHp(),card.getCurHp(),index,
			card.getAimType(),card.getCritRate(),card.getDodgeRate(),0,
			card.getType());
		array[index]=bCard;
	}

	/** 检查选择卡牌上阵 */
	private String checkSelectBattle(Card card,int index,BattleCard[] bcard2)
	{
		int num=0;
		for(BattleCard battleCard:bcard2)
		{
			if(battleCard!=null) num++;
		}
		if(num>=MAXBATTLECOUNT)
		{
			return Lang.F1221;
		}
		if(card==null)
		{
			return Lang.F1200;
		}
		if(card.getStatus()==Card.ATTACK)
		{
			return Lang.F1223;
		}
		// TODO 茶铺回血的时候还需要判断状态
		return null;
	}

	/** 选择卡牌下阵 */
	private void cancelBattle(Player player,Card card,int index,
		BattleCard[] array)
	{
		String error=checkCancelBattle(player,card,index,array);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		card.setStatus(Card.NORMAL);
		array[index]=null;
	}

	/** 选择卡牌下阵检查 */
	private String checkCancelBattle(Player player,Card card,int index,
		BattleCard[] array)
	{
		if(card==null)
		{
			return Lang.F1200;
		}
		BattleCard bCard=array[index];
		System.err.println("bCard ==="+bCard);
		if(bCard==null||bCard.getId()!=card.getId())
		{
			return Lang.F1214;
		}
		// if(player.getCardBag().getSurplusCapacity()<1)
		// {
		// return Lang.F1100;
		// }
		return null;
	}

	/**
	 * 更换阵型位置:上阵，下阵，更换阵上位置
	 * 
	 * @param s
	 * @param cardId
	 * @param Index -1=背包中，1-5=阵中位置
	 * @param type 1=上阵，2=下阵
	 */
	public void changeFormation(Player player,int cardId,int index,int type)
	{
		Card card=player.getCardBag().getById(cardId);
		System.err.println("cardId ===="+cardId);
		BattleCard[] array=player.formation.getFormation();
		if(type==1)// 上阵
			selectBattle(player,card,index,array);
		else
			// 下阵
			cancelBattle(player,card,index,array);
	}

	/** 卡牌培养 */
	public void forster(Player player,int cardId,int type,int money,
		int gold,ByteBuffer data,boolean skillRateLocked,boolean hpLocked,boolean attLocked)
	{		
		Card card=player.getCardBag().getById(cardId);
		String error=checkForster(player,card,type,money,gold,skillRateLocked,hpLocked,attLocked);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}		
		int att=0,range=0,hp=0;
		if(type==1)//土豪培养
		{		
			if(!skillRateLocked)
				range 	= 	MathKit.randomValue(card.getGoldFosterRangeMin(), card.getGoldFosterRangeMax()+1);
			if (!hpLocked) 
				hp	    =   MathKit.randomValue(card.getGoldFosterHpMin(), card.getGoldFosterHpMax()+1);	
			if (!attLocked)
				att 	=	MathKit.randomValue(card.getGoldFosterAttMin(),card.getGoldFosterAttMax()+1);	
		}else if(type==2)//金币培养
		{
			if(!skillRateLocked)
				range 	= 	MathKit.randomValue(card.getMoneyFosterRangeMin(), card.getMoneyFosterRangeMax()+1);
			if (!hpLocked) 
				hp	    =   MathKit.randomValue(card.getMoneyFosterHpMin(), card.getMoneyFosterHpMax()+1);	
			if (!attLocked)
				att 	=	MathKit.randomValue(card.getMoneyFosterAttMin(),card.getMoneyFosterAttMax()+1);
		}else if(type==3)//普通培养
		{
			if(!skillRateLocked)
				range 	= 	MathKit.randomValue(card.getNormalFosterRangeMin(), card.getNormalFosterRangeMax()+1);
				
			if (!hpLocked) 
				hp	    =   MathKit.randomValue(card.getNormalFosterHpMin(), card.getNormalFosterHpMax()+1);		
			if (!attLocked)
					att 	=	MathKit.randomValue(card.getNormalFosterAttMin(),card.getNormalFosterAttMax()+1);
		}
		
		
		
		if(!hpLocked)
			card.setForsterHpLast(hp);
		if(!skillRateLocked)
			card.setForsterRangeLast(range);	
		if(!attLocked)
			card.setForsterAttLast(att);
		player.decrMoney(money);
		
		if(!attLocked)
			data.writeInt(att);
		else
			data.writeInt(card.getForsterAttLast());
		
		if(!hpLocked)			
			data.writeInt(hp);
		else
			data.writeInt(card.getForsterHpLast());
		
		if(!skillRateLocked)	
		data.writeInt(range);
		else 
		data.writeInt(card.getForsterRangeLast());	
		
		data.writeInt(player.getMoney());
		if(type==2||type==1) data.writeInt(player.getGold());
	}
	
	
	/**卡牌培养保存*/
	public void  saveFoster(Player player,int cardId)
	{
		Card card=player.getCardBag().getById(cardId);
		String error=checkSaveFoster(card);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		
		
		card.decrForsterNumber(1);
		card.incrForsterAtt(card.getForsterAttLast());
		card.incrAtt(card.getForsterAttLast());
		card.incrForsterRange(card.getForsterRangeLast());
		card.incrSkillRate(card.getForsterRangeLast());
		card.incrForsterHp(card.getForsterHpLast());
		card.incrHp(card.getForsterHpLast());
		
		
		card.setForsterHpLast(0);
		card.setForsterAttLast(0);
		card.setForsterRangeLast(0);
		
	}
	
	
	
	/**检测卡牌培养保存*/
	private String checkSaveFoster(Card card)
	{
		if(card==null)
		{
			return Lang.F1200;
		}
		
		if(card.getForsterNumber()==0)
		{
			return Lang.F1203; 
		}	
		
		if(card.getForsterAttLast()==0&&card.getForsterHpLast()==0&&card.getForsterRangeLast()==0)
		{
			return Lang.F1226; 
		}	
		return null;
	}
	
	
	/**获取卡牌的倍率*/
	private int  computeCardPerNum(boolean skillRateLocked,boolean hpLocked,boolean attLocked )
	{
		int totalper = 1;
		if (skillRateLocked)
		{
			totalper *=FORSTERPERNUM;
		}	
		
		if (hpLocked)
		{
			totalper *=FORSTERPERNUM;
		}
		
		if (attLocked)
		{
			totalper *=FORSTERPERNUM;
		}
		
		return totalper;
	}
	

	/** 检查卡牌培养 */
	private String checkForster(Player player,Card card,int type,int money,
		int gold,boolean skillRateLocked,boolean hpLocked,boolean attLocked)
	{
		
		if(card==null)
		{
			return Lang.F1200;
		}
		if(card.getForsterNumber()<=0)		
			return Lang.F1203;
				
		int index =0;
		if(skillRateLocked)
		{
			index++;
		}		
		if (attLocked)
		{
			index++;
		}
		
		if (hpLocked)
		{
			index++;
		}				
		if (index>2) 
			return Lang.F1225;
		
		if(card.getForsterAttLast()==0&&attLocked||card.getForsterRangeLast()==0&&skillRateLocked||card.getForsterHpLast()==0&&hpLocked)
			return Lang.F1227;	
		
		int perNum = computeCardPerNum(skillRateLocked,hpLocked,attLocked);
		
		if(type==1)//土豪
		{
			if(gold!=GameCFG.getGoldFosterNum()*perNum) return Lang.F1204;
			if(player.getGold()<gold) return Lang.F702;
		}
		else
			if(type==2)//金币
		{
			if(gold!=GameCFG.getMoneyFosterNum()*perNum) return Lang.F1204;
			if(player.getGold()<gold) return Lang.F702;
		}else if(type==3)// 普通培养
		{
			if (money!=GameCFG.getNormalFosterNum()*perNum) return Lang.F1204;
			if(player.getMoney()<money) return Lang.F701;
		}else//未知培养类型
		{
			return Lang.F1224;
		}
		return null;
	}

	/** 重置培养 */
	public void resetForster(Player player,int cardId,ByteBuffer data)
	{
		Card card=player.getCardBag().getById(cardId);
		String error=checkResetForster(player,card);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		card.setForsterNumber(card.getLevel());
		card.decrAtt(card.getForsterAtt());
		card.setForsterAtt(0);
		card.setForsterRange(0);
		card.decrSkillRate(card.getForsterRange());
		card.setForsterHp(0);
		card.decrHp(card.getForsterHp());

		data.writeInt(card.getAtt());
		data.writeInt(card.getMaxHp());
		data.writeInt(card.getCurHp());
		data.writeInt(card.getSkillRate());
	}

	/** 重置培养检查 */
	private String checkResetForster(Player player,Card card)
	{
		if(card==null) return Lang.F1200;
		if(card.getForsterNumber()<=0) return Lang.F1203;
		return null;
	}

	/**
	 * 卡牌升级
	 * 
	 * @param player
	 * @param cardId 升级卡牌id
	 * @param list 吞噬卡牌id链表
	 */
	private boolean[] levelUp(Player player,int cardId,
		ArrayList<Integer> list)
	{
		Card card=player.getCardBag().getById(cardId);
		Card card_;
		ArrayList<Card> cardList=new ArrayList<Card>();
		for(Integer id:list)
		{
			card_=player.getCardBag().getById(id);
			cardList.add(card_);
		}
		String error=checkLevelUp(player,card,cardList);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		boolean skillResult=skillLevelUp(player,cardId,list);
		int exp=0;
		if(!card.isRealm())
		{
			exp=getIncrExp(cardList);
		}
		card.incrExp(exp);
		boolean levelResult=card.levelUp();
		if(card.getRealmByLevel()[0]>0)
		{
			card.setRealm(true);
		}
		for(Integer id:list)
		{
			player.getCardBag().remove(id);
		}
		return new boolean[]{skillResult,levelResult};
	}

	/** 检查卡牌升级 */
	private String checkLevelUp(Player player,Card card,
		ArrayList<Card> cardList)
	{
		Card card_;
		for(int i=0;i<cardList.size();i++)
		{
			card_=cardList.get(i);
			if(card_==null) return Lang.F1207;
			if(card_.getStatus()!=Card.NORMAL&&card_.isLock())
				return Lang.F1208;
		}
		return null;
	}

	/** 吞噬经验计算 */
	private int getIncrExp(ArrayList<Card> cardList)
	{
		int exp=0;
		for(Card card:cardList)
		{
			exp+=card.getSwallowExp();
		}
		return exp;
	}

	/**
	 * 境界突破
	 * 
	 * @param player
	 * @param cardId 需要突破的卡牌ID
	 * @param list 吞噬卡牌ID
	 */
	private boolean[] realmBeyong(Player player,int cardId,
		ArrayList<Integer> list)
	{
		Card card=player.getCardBag().getById(cardId);
		int[] limit=card.getRealmByLevel();
		String error=checkRealmBeyong(player,card,limit,list);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		boolean skillResult=skillLevelUp(player,cardId,list);
		for(Integer id:list)
		{
			player.getCardBag().remove(id);
		}
		boolean breakOutResult=false;
		if(card.getEngulfCards().size()==(limit.length-1))
		{
			card.incrForsterNumber(list.get(list.size()-1));
			// TODO 移除规则还要和策划讨论
			card.setRealm(false);
			card.getEngulfCards().clear();
			breakOutResult=true;
		}
		return new boolean[]{skillResult,breakOutResult};
	}

	/** 检查境界突破 */
	private String checkRealmBeyong(Player player,Card card,int[] limit,
		ArrayList<Integer> cardList)
	{
		Card card_;
		int id;
		for(int i=0;i<cardList.size();i++)
		{
			id=cardList.get(i);
			for(int j=0;j<limit.length-1;j++)
			{
				if(limit[j]==id) break;
				card.getEngulfCards().add(id);
			}
			card_=player.getCardBag().getById(id);
			if(card_==null) return Lang.F1207;
			if(card_.getStatus()!=Card.NORMAL&&card_.isLock())
				return Lang.F1208;
		}
		return null;
	}

	/**
	 * 技能升级,吞噬卡牌
	 * 
	 * @param player
	 * @param cardId 升级卡牌
	 * @param swallow 吞噬对象
	 * @return
	 */
	public boolean skillLevelUp(Player player,int cardId,
		ArrayList<Integer> swallow)
	{
		Card card=player.getCardBag().getById(cardId);
		int needCount=GameCFG.getSkillLevel(card.getSkillLevel());
		String error=checkskillLevelUp(player,card,swallow,needCount);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		int count=MathKit.randomValue(1,needCount+1);
		boolean result=swallow.size()>=count;
		if(result)
		{
			card.setSkillLevel(card.getSkillLevel()+1);
		}
		return result;
	}

	/** 检查技能升级吞噬卡牌 */
	private String checkskillLevelUp(Player player,Card card,
		ArrayList<Integer> swallow,int needCount)
	{
		if(swallow.size()<=0) return Lang.F1211;
		int id=swallow.get(0);
		Card card_;
		for(int i=0;i<swallow.size();i++)
		{
			id=swallow.get(i);
			card_=player.getCardBag().getById(id);
			if(card_==null) return Lang.F1207;
		}
		return null;
	}

	/**
	 * 刷新技能
	 * 
	 * @param player 玩家对象
	 * @param cardId 卡牌ID
	 * @param needGold 所需RMB
	 * @return
	 */
	public int flushCardSkill(Player player,int cardId,int needGold)
	{
		Card card=player.getCardBag().getById(cardId);
		int freeTimes=player.getPlayerInfo().getSkillFlushFreeTimes();
		String error=checkFlushCardSkill(card,player,needGold,freeTimes);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		} // 需要策划定免费次数 根据 vip 等级有关
		if(freeTimes>0)
		{
			player.getPlayerInfo().setSkillFlushFreeTimes(freeTimes-1); // 免费次数-1
		}
		else
		{
			player.setGold(player.getGold()-needGold);
		}
		int newSkillId=card.getSkillRange()[MathKit.randomValue(0,
			card.getSkillRange().length)];// 计算随机技能
		card.setFlushSkillId(newSkillId);
		return newSkillId;
	}

	/**
	 * 检查技能刷新
	 * 
	 * @param card 卡牌对象
	 * @param player 玩家对象
	 * @param needGold 所需RMB
	 * @return
	 */
	private String checkFlushCardSkill(Card card,Player player,int needGold,
		int freeTimes)
	{
		int useGold=0; // 需要策划定值
		if(card==null)
		{
			return Lang.F1200;
		}
		if(card.getSkillRange().length<0)
		{
			return Lang.F1215;
		}
		if(useGold!=needGold)
		{
			return Lang.F1218;
		}
		if(player.getGold()<needGold&&freeTimes<=0)
		{
			return Lang.F1219;
		}
		return null;
	}

	/**
	 * 保存技能
	 * 
	 * @param player 玩家
	 * @param cardId 卡牌ID
	 * @param skillId 技能ID
	 */
	public void saveCardSkill(Player player,int cardId,int skillId)
	{
		Card card=player.getCardBag().getById(cardId);
		String error=checkSaveCardSkill(card,skillId);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		card.setSkillId(skillId);
		card.setSkillLevel(0);
		card.setFlushSkillId(0);
	}

	/**
	 * 检查保存技能
	 * 
	 * @param card
	 * @param skillId
	 * @return
	 */
	private String checkSaveCardSkill(Card card,int skillId)
	{
		if(card==null)
		{
			return Lang.F1200;
		}
		if(card.getSkillId()<0)
		{
			return Lang.F1216;
		}
		if(card.getSkillLevel()<0)
		{
			return Lang.F1217;
		}
		if(card.getFlushSkillId()!=skillId)
		{
			return Lang.F1220;
		}
		return null;
	}
}
