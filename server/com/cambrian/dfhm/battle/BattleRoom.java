package com.cambrian.dfhm.battle;

import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.util.ChangeAdapter;
import com.cambrian.common.util.ChangeListener;
import com.cambrian.common.util.ChangeListenerList;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * ��˵����ս������
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class BattleRoom extends ChangeAdapter
{
	/** ��־��¼ */
	public static final Logger log=Logger.getLogger(BattleRoom.class);
	/** ״̬��־�������ȴ��У��������أ�������ʼ������ر� */
	public static final int WAIT=10,MATCH=11,MATCH_LOAD=12,CLOSE=0;
	/** ����Ψһid�� */
	protected int id;
	/** ״̬ */
	protected int state=0;
	/** ��һỰ */
	protected Session session;
	/** �������� */
	protected BattleScene battleScene;
	/** ������ */
	protected ChangeListenerList listenerList=new ChangeListenerList();

	/** ���췽�� ,��ǰ̨UI�ı�maxCount��ʱ���� */
	public BattleRoom(Session session)
	{
		String err=newRoomCheck(session);
		if(err!=null) throw new DataAccessException(601,err);
		state=WAIT;
		newBattleScene();
		state(session);
	}
	/** ����Ψһid�� */
	public int getId()
	{
		return this.id;
	}
	/** ����Ψһid�� */
	public void setId(int id)
	{
		this.id=id;
	}
	/** ���״̬ */
	public int getState()
	{
		return state;
	}
	/** ����״̬ */
	public void setState(int state)
	{
		this.state=state;
	}
	/** �Ƿ�Ϊ������ */
	public boolean isMatching()
	{
		return state>=MATCH;
	}
	/** ��ñ������� */
	public BattleScene getBattleScene()
	{
		return battleScene;
	}
	/** ���ñ������� */
	public void setBattleScene(BattleScene scene)
	{
		this.battleScene=scene;
	}
	/** ���һ���µĳ��� */
	public void newBattleScene()
	{
		BattleScene battleScene=new BattleScene();
		battleScene.addListener(this);
		setBattleScene(battleScene);
	}
	/** ���������� */
	protected String newRoomCheck(Session s)
	{
		return null;
	}
	/** �ı�״̬ */
	public void state(Session session)
	{
		if(state>=MATCH) return;
		synchronized(this)
		{
			Player player=(Player)session.getSource();
//			player.setStatus(Player.START);
		}
	}
	/** ��Ϸ��ʼǰ��һЩ���ӳ�ʼ�� */
	public void initStart()
	{
		if(state>WAIT) return;
		// TODO ���ر���
		state=MATCH_LOAD;
	}
	/** ��ʼ�����ļ�� */
	public String startCheck(Session session)
	{
		return startCheck();
	}
	/** ��ʼ�����ļ�� */
	protected String startCheck()
	{
		return null;
	}
	/** ��ʼ���� */
	public synchronized void startMatch()
	{
		if(isMatching()) return;
		state=MATCH;
		// TODO ��ʼ����
//		battleScene.start(true);
	}

	/** �˳� */
	public synchronized void exit(Session session,String action)
	{
		if(log.isDebugEnabled())
			log.debug("Room exit, action=["+action+"]");
		session.setAttribute("room",null);
		Player p=(Player)session.getSource();
//		p.setStatus(Player.RUN_AWAY);
		listenerList.change(this,CLOSE);
	}
	public void destory()
	{
		listenerList.removeListeners();
	}
	/** ������� */
	public void clear()
	{
		// battleScene.clear();
		session.setAttribute("room",null);
		session=null;
	}
	/** ������ */
	public void totle(int winner)
	{
		
	}
	/** ������,���� */
	protected void totleAward(Session session,int winner,ByteBuffer data)
	{
		
	}
	/** ������,��ɫ */
	protected void totlePlayer(Session session,int winner,ByteBuffer data)
	{
		
	}
	public void addListener(ChangeListener list)
	{
		this.listenerList.addListener(list);
	}
	public void removeListener(ChangeListener list)
	{
		this.listenerList.removeListener(list);
	}
	public void change(Object sr,int type,Object zb,Object bl)
	{
		ByteBuffer data=new ByteBuffer();
		BattleScene source=(BattleScene)sr;
		if((BattleScene)source==battleScene&&type==1)
		{
			data.writeInt(type);		
		}
		System.err.println("��ǰ̨�㲥��Ϣ��");
//		session.getConnect().send(GlobalConst.COMMAND_BATTLE_ZOMBIE,data);
		data.clear();
		
	}
}