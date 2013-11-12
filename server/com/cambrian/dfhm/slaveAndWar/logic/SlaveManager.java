package com.cambrian.dfhm.slaveAndWar.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵�����������ܹ�����
 * 
 * @author��LazyBear
 */
public class SlaveManager
{

	/* static fields */
	private static SlaveManager instance=new SlaveManager();

	/* static methods */
	public static SlaveManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** �ڴ����ݷ��ʶ��� */
	private DataServer ds;
	/** ����DB���ʶ��� */
	private SlaveAndWarDao dao;

	/* constructors */

	/* properties */
	public void setDs(DataServer ds)
	{
		instance.ds=ds;
	}
	public void setSawd(SlaveAndWarDao dao)
	{
		instance.dao=dao;
	}
	/* init start */

	/* methods */

	/**
	 * ��������
	 * 
	 * @param player ��Ҷ���
	 * @param tarPlayerId Ŀ�����ID
	 * @return ����ս������
	 */
	public List<Integer> attEnemy(Player player,int tarPlayerId)
	{
		Map<String,Object> mapResult=checkAttEnemy(player,tarPlayerId);
		String error=(String)mapResult.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Player tarPlayer=(Player)mapResult.get("tarPlayer");

		return null;
	}

	/**
	 * ս��ʤ�������ݸı�ʹ���
	 * 
	 * @param attPlayer �������
	 * @param defPlayer �������
	 */
	private void changeGradeForWin(Player attPlayer,Player defPlayer)
	{
		switch(attPlayer.getIdentity().getGrade())
		{
			case Identity.FREEMAN:

				break;
			case Identity.OWNER:

				break;
			case Identity.SLAVE:

				break;
			default:
				break;
		}
	}

	/**
	 * ����ʤ��
	 * 
	 * @param attPlayer
	 * @param defPlayer
	 */
	private void ownerWin(Player attPlayer,Player defPlayer)
	{
		switch(defPlayer.getIdentity().getGrade())
		{
			case Identity.FREEMAN:
				
				break;

			case Identity.OWNER:
				
				break;
			default:
				break;
		}
	}

	/**
	 * ��⹥������
	 * @param player ��Ҷ���
	 * @param tarPlayerId Ŀ�����ID
	 * @return ���ؼ������Ϣ
	 */
	private Map<String,Object> checkAttEnemy(Player player,int tarPlayerId)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(player.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2104);
			return mapInfo;
		}
		if(player.getIdentity().getGrade()!=Identity.FREEMAN
			&&player.getIdentity().getGrade()!=Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2101);
			return mapInfo;
		}
		if(player.formation.isEmpty())
		{
			mapInfo.put("error",Lang.F2103);
			return mapInfo;
		}
		Player tarPlayer=getTarPlayer(tarPlayerId);
		if(tarPlayer.getIdentity().getGrade()==Identity.SLAVE)
		{
			mapInfo.put("error",Lang.F2102);
			return mapInfo;
		}
		if(tarPlayer.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			mapInfo.put("error",Lang.F2105);
			return mapInfo;
		}
		mapInfo.put("tarPlayer",tarPlayer);
		mapInfo.put("error",null);
		return mapInfo;
	}
	/**
	 * ��ȡ�����б�
	 * 
	 * @return
	 */
	public List<Player> getEnemy(Player player)
	{
		String error=checkGetEnemy(player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		List<Player> enemyList=new ArrayList<Player>();
		getIdentity(player,GameCFG.getErrorValue(),enemyList);
		return enemyList;
	}

	/**
	 * ����ȡ�����б�
	 * 
	 * @return
	 */
	private String checkGetEnemy(Player player)
	{
		if(player.getIdentity().getGrade()<Identity.SLAVE
			||player.getIdentity().getGrade()>Identity.OWNER)
		{
			return Lang.F2104;
		}
		if(player.getIdentity().getGrade()!=Identity.OWNER
			&&player.getIdentity().getGrade()!=Identity.FREEMAN)
		{
			return Lang.F2101;
		}
		return null;
	}

	/**
	 * ����������ȡ�����б�
	 * 
	 * @param player ��Ҷ���
	 * @param errorValue ���ֵ
	 * @param enemyList �����б�
	 * @return
	 */
	private void getIdentity(Player player,int errorValue,
		List<Player> enemyList)
	{
		if(enemyList.size()==GameCFG.getEnemySize()
			||errorValue==GameCFG.getEnemySize()*GameCFG.getMatchTimes())
		{
			return;
		}
		Session[] sessions=ds.getSessionMap().getSessions().clone();
		List<Session> sessionList=Arrays.asList(sessions);
		List<Player> rmPlayers=new ArrayList<Player>();
		for(Session session:sessionList)
		{
			if(session!=null)
			{
				rmPlayers.add(((Player)session.getSource()));
			}
		}
		List<Player> players=dao.getAllPlayer();
		players.removeAll(rmPlayers);
		getIdentityForRAM(sessionList,player,enemyList,errorValue);
		getIdentityForDB(players,player,enemyList,errorValue);
		errorValue+=GameCFG.getErrorValue();
		getIdentity(player,errorValue,enemyList);
	}

	/**
	 * ���ڴ��о�����ȡ���ĵ���
	 * 
	 * @param sessionList �ڴ�Ự�б�
	 * @param player ��Ҷ���
	 * @param enemyList �����б�
	 * @param errorValue ���ֵ
	 * @return
	 */
	private void getIdentityForRAM(List<Session> sessionList,Player player,
		List<Player> enemyList,int errorValue)
	{
		while(sessionList.size()>0)
		{
			int index=MathKit.randomValue(0,sessionList.size());
			Session session=sessionList.get(index);
			if(session!=null)
			{
				sessionList.remove(index);
				Player tarPlayer=(Player)session.getSource();
				if(Math
					.abs(tarPlayer.getFightPoint()-player.getFightPoint())<=errorValue
					&&tarPlayer.getIdentity().getGrade()!=Identity.SLAVE)
				{
					enemyList.add(tarPlayer);
					if(enemyList.size()==GameCFG.getEnemySize())
					{
						return;
					}
				}
			}
		}

	}
	/**
	 * �����ݿ��о�����ȡ���ĵ���
	 * 
	 * @param players ���ݿ�����б�
	 * @param player ��Ҷ���
	 * @param enemyList �����б�
	 * @param errorValue ���ֵ �����ݿ��о�����ȡ���ĵ���
	 */
	private void getIdentityForDB(List<Player> players,Player player,
		List<Player> enemyList,int errorValue)
	{
		while(players.size()>0)
		{
			int index=MathKit.randomValue(0,players.size());
			Player tarPlayer=players.get(index);
			players.remove(index);
			if(Math.abs(tarPlayer.getFightPoint()-player.getFightPoint())<=errorValue
				&&tarPlayer.getIdentity().getGrade()!=Identity.SLAVE)
			{
				enemyList.add(tarPlayer);
				if(enemyList.size()==GameCFG.getEnemySize())
				{
					return;
				}
			}
		}
	}

	/**
	 * ��ȡĿ����Ҷ���
	 * 
	 * @param userId
	 * @return ����Ŀ����Ҷ���
	 */
	private Player getTarPlayer(int userId)
	{
		Session session=ds.getSession(userId);
		if(session!=null)
		{
			return (Player)session.getSource();
		}
		else
		{
			return dao.getPlayer(userId);
		}
	}
}
