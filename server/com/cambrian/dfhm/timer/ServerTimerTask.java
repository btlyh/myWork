package com.cambrian.dfhm.timer;

import java.util.TimerTask;

import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.timer.notice.ServerRefreshNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵������������ʱ��
 * @author��Zmk
 * 
 */
public class ServerTimerTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	/** ���ݷ����� */
	private DataServer ds;
	/** ������ˢ������ */
	private ServerRefreshNotice srn;
	/* constructors */

	/* properties */

	/* init start */

	public ServerTimerTask(DataServer ds, ServerRefreshNotice srn)
	{
		this.ds=ds;
		this.srn=srn;
	}

	/* methods */
	@Override
	public void run()
	{
		refreshOnlinePlayer();
	}
	
	/** ˢ������������� */
	private void refreshOnlinePlayer()
	{
		Session[] sessions = ds.getSessionMap().getSessions();
		for (Session session : sessions)
		{
			if (session != null)
			{
				Player player = (Player)session.getSource();
				if (player != null)
				{
					player.refreshDayly();
					srn.send(session, new Object[] {player.getPlayerInfo()});
				}
			}
		}
	}
}
