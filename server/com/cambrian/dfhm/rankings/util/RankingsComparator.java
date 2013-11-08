package com.cambrian.dfhm.rankings.util;

import java.util.Comparator;

import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.rankings.entity.CardRankInfo;
import com.cambrian.dfhm.rankings.entity.RankInfo;

/**
 * 类说明：排序规则
 * 
 * @author：Zmk
 * 
 */
public class RankingsComparator implements Comparator<Object>
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	public int compare(Object obj1, Object obj2)
	{
		if (obj1 instanceof RankInfo && obj2 instanceof RankInfo) // 副本排行，挑战排行，付费排行
		{
			RankInfo o1 = (RankInfo) obj1;
			RankInfo o2 = (RankInfo) obj2;
			if (o1.getInfo() < o2.getInfo())
			{
				return 1;
			} else if (o1.getInfo() > o2.getInfo())
			{
				return -1;
			} else
			{
				if (o1.getTime() > 0 && o1.getTime() > o2.getTime()) // 如果进度一样根据时间来排序，付费排行没有时间所以付费排行
					return 1;										 // 会直接跳到else用名字首写字母排序	
				else if (o2.getTime() > 0 && o1.getTime() < o2.getTime())
					return -1;
				else
					return ((java.text.RuleBasedCollator) java.text.Collator
							.getInstance(java.util.Locale.CHINA)).compare(
							o1.getPlayerName(), o2.getPlayerName());
			}
		} else if (obj1 instanceof CardRankInfo && obj2 instanceof CardRankInfo) 
		{															// 卡牌排行，根据战斗力排序，战斗力相等根据卡牌名字排序
			CardRankInfo o1 = (CardRankInfo) obj1;
			CardRankInfo o2 = (CardRankInfo) obj2;
			if (o1.getInfo() < o2.getInfo())
			{
				return 1;
			} else if (o1.getInfo() > o2.getInfo())
			{
				return -1;
			}else
			{
				return ((java.text.RuleBasedCollator) java.text.Collator
						.getInstance(java.util.Locale.CHINA)).compare(
						o1.getCardName(), o2.getCardName());
			}
		}else
		{
			Card o1 = (Card)obj1;
			Card o2 = (Card)obj2;
			if (o1.getZhandouli() < o2.getZhandouli())
			{
				return 1;
			} else if (o1.getZhandouli() > o2.getZhandouli())
			{
				return -1;
			}else
			{
				return ((java.text.RuleBasedCollator) java.text.Collator
						.getInstance(java.util.Locale.CHINA)).compare(
						o1.getName(), o2.getName());
			}
		}
	}
}
