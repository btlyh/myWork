package com.cambrian.dfhm.qualifying.timer;

import java.util.TimerTask;

import com.cambrian.dfhm.qualifying.logic.QualifyingManager;

/**
 * 类说明：排位赛计时器
 * @author：Zmk
 * 
 */
public class QualifyingTimer extends TimerTask
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
		QualifyingManager.getInstance().flushTopList();
	}
}
