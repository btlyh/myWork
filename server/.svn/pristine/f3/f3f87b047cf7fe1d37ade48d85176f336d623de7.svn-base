package com.cambrian.dfhm.globalboss.timer;

import java.util.Timer;
import java.util.TimerTask;

import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.globalboss.entity.GlobalBossCFG;
import com.cambrian.dfhm.globalboss.logic.BossManager;
import com.cambrian.dfhm.globalboss.notice.BossAutoAttNotice;
import com.cambrian.dfhm.globalboss.notice.BossStartNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：世界BOSS开始定时任务类 用于BOSS活动开始给客户端进行推送
 * 
 * @author：LazyBear
 */
public class BossStartTimeTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS配置信息 */
	private GlobalBossCFG gbc;
	/** BOSS开始推送消息对象 */
	private BossStartNotice bsn;
	/** 数据服务器 */
	private DataServer ds;
	/** BOSS自动战斗消息推送对象 */
	private BossAutoAttNotice baan;

	/* constructors */
	public BossStartTimeTask(GlobalBossCFG gbc,DataServer ds,
		BossStartNotice bsn,BossAutoAttNotice baan)
	{
		this.gbc=gbc;
		this.ds=ds;
		this.bsn=bsn;
		this.baan=baan;
	}
	/* properties */

	/* init start */

	/* methods */
	/** 任务所执行的方法 ，开启BOSS */
	@Override
	public void run()
	{
		BossManager.getInstance().bossMap.get(gbc.getSid()).setOpen(true);// 开启BOSS
		Session[] sessions=ds.getSessionMap().getSessions();
		
		Timer bossEndTimer=new Timer();
		bossEndTimer.schedule(new BossEndTimeTask(gbc),
			TimeKit.timeOf(0,gbc.getTimeConfine()));

		Timer autoAttBossTimer=new Timer();
		autoAttBossTimer.schedule(new AutoAttTimeTask(gbc,ds,baan),
			TimeKit.SEC_MILLS,TimeKit.SEC_MILLS);
		
		for(Session session:sessions)
		{
			if(session!=null)
			{
				long time=TimeKit.timeOf(gbc.getActiveTime());
				long surplusTime=time-TimeKit.nowTimeMills();
				bsn.send(session,new Object[]{surplusTime,gbc.getSid()});
			}
		}
	}
}
