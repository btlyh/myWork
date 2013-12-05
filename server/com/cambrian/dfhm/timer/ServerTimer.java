package com.cambrian.dfhm.timer;

import java.util.Timer;

import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.timer.notice.ServerRefreshNotice;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明： 服务器定时器
 * 
 * @author：Zmk
 * 
 */
public class ServerTimer
{

	

	/* static fields */

	/* static methods */

	/* fields */
	/** 数据服务器 */
	private DataServer ds;
	/** 服务器刷新推送 */
	private ServerRefreshNotice srn;
	/* constructors */

	/* properties */
	public void setDS(DataServer ds)
	{
		this.ds=ds;
	}
	public void setServerRefreshNotice(ServerRefreshNotice srn)
	{
		this.srn=srn;
	}
	/* init start */

	/* methods */
	public void timerStart()
	{
		long activeTime=0;
		long startTime=0;
		activeTime=TimeKit.timeOf(GameCFG.getServerTime());
		if(activeTime<TimeKit.nowTimeMills())
		{
			activeTime+=TimeKit.DAY_MILLS;
		}
		startTime = activeTime - TimeKit.nowTimeMills();
		Timer serverTimer = new Timer();
		serverTimer.scheduleAtFixedRate(new ServerTimerTask(ds,srn), startTime, TimeKit.DAY_MILLS);
	}
}
