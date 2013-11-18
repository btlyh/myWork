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
 * ��˵��������BOSS��ʼ��ʱ������ ����BOSS���ʼ���ͻ��˽�������
 * 
 * @author��LazyBear
 */
public class BossStartTimeTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS������Ϣ */
	private GlobalBossCFG gbc;
	/** BOSS��ʼ������Ϣ���� */
	private BossStartNotice bsn;
	/** ���ݷ����� */
	private DataServer ds;
	/** BOSS�Զ�ս����Ϣ���Ͷ��� */
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
	/** ������ִ�еķ��� ������BOSS */
	@Override
	public void run()
	{
		BossManager.getInstance().bossMap.get(gbc.getSid()).setOpen(true);// ����BOSS
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
				if(surplusTime<0)
				{
					surplusTime=0;
				}
				bsn.send(session,new Object[]{surplusTime,gbc.getSid()});
			}
		}
	}
}
