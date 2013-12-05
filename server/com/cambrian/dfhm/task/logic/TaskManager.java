package com.cambrian.dfhm.task.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.notice.MailSendNotice;
import com.cambrian.dfhm.mail.util.MailFactory;
import com.cambrian.dfhm.task.entity.Task;
import com.cambrian.dfhm.task.entity.TaskAward;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：任务逻辑处理类
 * @author：Zmk
 * 
 */
public class TaskManager
{

	/* static fields */
	private static TaskManager instance = new TaskManager();
	/* static methods */
	public static TaskManager getInstance()
	{
		return instance;
	}
	/* fields */
	/** 邮件工厂 */
	MailFactory mf;
	/** 邮件推送 */
	MailSendNotice msn;
	/** 数据服务器 */
	DataServer ds;
	/* constructors */

	/* properties */
	public void setMf(MailFactory mf)
	{
		instance.mf = mf;
	}
	public void setMailSendNotice(MailSendNotice msn)
	{
		instance.msn = msn;
	}
	public void setDS(DataServer ds)
	{
		instance.ds = ds;
	}
	/* init start */

	/* methods */
	
	/** 完成任务 */
	public Map<String, Object> finishTask(Player player, int sid)
	{
		Map<String, Object> resultMap = checkFinishTask(player, sid);
		String error = (String)resultMap.get("error");
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		Task task = (Task)resultMap.get("task");
		task.finish(player);
		TaskAward award = (TaskAward)Sample.getFactory().getSample(task.awardSid);
		ArrayList<Card> cardList = new ArrayList<Card>();
		ArrayList<Integer> cardSidList = new ArrayList<Integer>(0);
		player.incrGold(award.gold);
		player.incrMoney(award.money);
		player.incrSoul(award.soul);
		player.incrToken(award.token);
		player.getPlayerInfo().incrNormalPoint(award.point);
		if (award.card != null && award.card.length > 0)
		{
			for (int i = 1; i < award.card.length-1; i+=2)
			{
				int random = MathKit.randomValue(0, 100000);
				if (random < award.card[i+1])
				{
					cardSidList.add(award.card[i]);
				}
				if (i+2==award.card.length-1&&cardSidList.size()<award.card[0])
				{
					i=-1;
				}
				if (cardSidList.size()==award.card[0])
					break;
			}
			if (player.getCardBag().getSurplusCapacity() < cardSidList.size())
			{
				Mail mail = mf.createSystemMail(cardSidList, 0, 0, 0, 0, 0,
						(int) player.getUserId());
				player.addMail(mail);
				Session session = ds.getSession(player.getNickname());
				if (session != null)
					msn.send(session, new Object[] {player.getUnreadMailCount()});
			}else
			{
				for (Integer integer : cardSidList)
				{
					Card card = player.getCardBag().add(integer);
					cardList.add(card);
					resultMap.put("flag", true);
				}
			}
		}
		if (cardSidList.size() > 0)
		{
			if (cardList.size() > 0)
			{
				resultMap.put("list", cardList);
			}else
			{
				resultMap.put("list", cardSidList);
			}
		}else
		{
			resultMap.put("list", cardSidList);
		}
		resultMap.put("award", award);
		return resultMap;
	}
	
	/** 检查完成任务 */
	private Map<String, Object> checkFinishTask(Player player, int sid)
	{
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		Task task = (Task)Sample.getFactory().getSample(sid);
		if (task == null)
		{
			mapInfo.put("error", Lang.F2401);
			return mapInfo;
		}
		task = player.getTasks().getTask(sid);
		if (task == null)
		{
			mapInfo.put("error", Lang.F2402);
			return mapInfo;
		}
		if (task.status != Task.FINISH)
		{
			mapInfo.put("error", Lang.F2403);
			return mapInfo;
		}
		mapInfo.put("error", null);
		mapInfo.put("task", task);
		return mapInfo;
	}
}
