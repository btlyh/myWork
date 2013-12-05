package com.cambrian.dfhm.timer;
import com.cambrian.common.timer.TimerCenter;
import com.cambrian.common.timer.TimerEvent;
import com.cambrian.common.timer.TimerListener;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.timer.notice.TokenSendNotice;
import com.cambrian.game.Session;
import com.cambrian.game.SessionMap;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：军令定时器，用于恢复军令
 * 
 * @author：LazyBear
 * 
 */
public class TokenTimer implements TimerListener
{

	/* static fields */
	/** 恢复时间 默认现在30分钟 */
	public static long TOKENADDTIME = TimeKit.MIN_MILLS * 30;
	/** 恢复数量 */
	public static int TOKENADDNUM = 1;
	/* static methods */

	/* fields */
	/** 数据服务器 */
	DataServer dataServer;
	/** 恢复军令定时器事件 */
	TimerEvent tokenTimeEvent;
	/***/
	TokenSendNotice tsn;                                             
	

	/* constructors */
	public TokenTimer()
	{
		this.tokenTimeEvent = new TimerEvent(this, "tokenTimeEvent", (int) TOKENADDTIME);
	}

	/* properties */
	public void setDS(DataServer dataServer)
	{
		this.dataServer = dataServer;
	}
	public void setTsn(TokenSendNotice tsn) {
		this.tsn = tsn;
	}
	/* init start */

	/* methods */
	public void onTimer(TimerEvent e)
	{
		if (!e.getParameter().equals("tokenTimeEvent"))
			return;
		addTokenOnTime();
	}

	/**
	 * 开启定时器
	 */
	public void timerStart()
	{
		TimerCenter.getInstance();
		TimerCenter.getMillisTimer().add(tokenTimeEvent);
	}
	/**
	 * 开始恢复军令
	 */
	private void addTokenOnTime()
	{
		SessionMap sm = this.dataServer.getSessionMap();
		Session[] sessions = sm.getSessions();
		Player player = null;
		Session session = null;
		for (int i = 0; i < sessions.length; i++)
		{
			session = sessions[i];
			if (session != null)
			{
				if (session.getConnect().isActive())
				{
					player = (Player) session.getSource();
				}
			}
			if (player != null)
			{
				if (player.getCurToken() < player.getMaxToken())
				{
					player.incrToken(TOKENADDNUM);
					tsn.send(session,new Object[]{player.getCurToken()});
				}
			}
		}
	}
}
