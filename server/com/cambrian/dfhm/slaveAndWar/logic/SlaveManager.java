package com.cambrian.dfhm.slaveAndWar.logic;

import java.util.List;

import sun.net.www.content.text.plain;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵�����������ܹ�����
 * 
 * @author��LazyBear
 */
public class SlaveManager
{

	/* static fields */

	/* static methods */

	/* fields */
	private DataServer ds;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	public List<Identity> enterMain(Player player)
	{
		String error=checkEnterMain(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}

		return null;
	}

	/**
	 * ����ȡ�����б�
	 * 
	 * @return
	 */
	private String checkEnterMain(Player player)
	{
		if(player.getIdentity().getGrade()!=Identity.OWNER
			&&player.getIdentity().getGrade()!=Identity.FREEMAN)
		{
			return Lang.F2001;
		}
		return null;
	}

	public List<Identity> getIdentitys(Player player,int errorValue)
	{
		return null;
	}
}
