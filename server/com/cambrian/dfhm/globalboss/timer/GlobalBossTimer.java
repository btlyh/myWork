package com.cambrian.dfhm.globalboss.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.cambrian.common.object.Sample;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.globalboss.entity.GlobalBossCFG;
import com.cambrian.dfhm.globalboss.logic.BossManager;
import com.cambrian.dfhm.globalboss.notice.BossAutoAttNotice;
import com.cambrian.dfhm.globalboss.notice.BossStartNotice;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：世界BOSS定时器 用于触发世界BOSS活动
 * 
 * @author：LazyBear
 */
public class GlobalBossTimer
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS集合 */
	private List<GlobalBossCFG> globalBossCFGs=new ArrayList<GlobalBossCFG>();
	/** 数据服务器 */
	private DataServer ds;
	/** Boss开始推送消息对象 */
	private BossStartNotice bsn;
	/** BOSS自动战斗推送消息对象 */
	private BossAutoAttNotice baan;

	/* constructors */

	/* properties */
	/** 设置世界BOSS */
	public void setGlobalBossCFGs()
	{
		int[] bossSids=GameCFG.getGlobalBossList();
		for(Integer integer:bossSids)
		{
			GlobalBossCFG gbc=(GlobalBossCFG)Sample.getFactory().getSample(
				integer);
			if(gbc!=null)
			{
				globalBossCFGs.add(gbc);
				BossManager.getInstance().bossMap.put(gbc.getSid(),gbc);
			}
		}
	}
	public void setDS(DataServer ds)
	{
		this.ds=ds;
	}
	public void setBsn(BossStartNotice bsn)
	{
		this.bsn=bsn;
	}
	public void setBaan(BossAutoAttNotice baan)
	{
		this.baan=baan;
	}
	/* init start */

	/* methods */
	/**
	 * 开启boss监听线程。一个BOSS一个线程。 24小时触发一次
	 */
	public void timerStart()
	{
		long activeTime=0;
		long startTime=0;
		setGlobalBossCFGs();
		for(GlobalBossCFG gbc:globalBossCFGs)
		{
			activeTime=TimeKit.timeOf(gbc.getActiveTime());
			if(activeTime<TimeKit.nowTimeMills())
			{
				activeTime+=TimeKit.DAY_MILLS;
			}
			startTime=activeTime-(gbc.getReadyTime()*60*1000)
				-TimeKit.nowTimeMills();
			Timer bossTimer=new Timer();
			bossTimer.schedule(new BossStartTimeTask(gbc,ds,bsn,baan),
				Math.abs(startTime),TimeKit.DAY_MILLS);
		}
	}
}
