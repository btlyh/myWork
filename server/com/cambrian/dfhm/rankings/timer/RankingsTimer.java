package com.cambrian.dfhm.rankings.timer;

import java.util.TimerTask;

import com.cambrian.dfhm.rankings.logic.RankingsManager;

/**
 * ��˵�������а�ʱ
 * @author��Zmk
 * 
 */
public class RankingsTimer extends TimerTask
{
	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@Override
	public void run()
	{
		RankingsManager.getInstance().flushRankings();
	}
}
