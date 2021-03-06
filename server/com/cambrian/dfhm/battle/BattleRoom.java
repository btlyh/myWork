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
 * 类说明：战斗房间
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class BattleRoom extends ChangeAdapter
{
	/** 日志记录 */
	public static final Logger log=Logger.getLogger(BattleRoom.class);
	/** 状态标志常量，等待中，比赛加载，比赛开始，房间关闭 */
	public static final int WAIT=10,MATCH=11,MATCH_LOAD=12,CLOSE=0;
	/** 房间唯一id号 */
	protected int id;
	/** 状态 */
	protected int state=0;
	/** 玩家会话 */
	protected Session session;
	/** 比赛场景 */
	protected BattleScene battleScene;
	/** 监听器 */
	protected ChangeListenerList listenerList=new ChangeListenerList();

	/** 构造方法 ,因前台UI改变maxCount暂时不用 */
	public BattleRoom(Session session)
	{
		String err=newRoomCheck(session);
		if(err!=null) throw new DataAccessException(601,err);
		state=WAIT;
		newBattleScene();
		state(session);
	}
	/** 房间唯一id号 */
	public int getId()
	{
		return this.id;
	}
	/** 房间唯一id号 */
	public void setId(int id)
	{
		this.id=id;
	}
	/** 获得状态 */
	public int getState()
	{
		return state;
	}
	/** 设置状态 */
	public void setState(int state)
	{
		this.state=state;
	}
	/** 是否为比赛中 */
	public boolean isMatching()
	{
		return state>=MATCH;
	}
	/** 获得比赛场景 */
	public BattleScene getBattleScene()
	{
		return battleScene;
	}
	/** 设置比赛场景 */
	public void setBattleScene(BattleScene scene)
	{
		this.battleScene=scene;
	}
	/** 获得一个新的场景 */
	public void newBattleScene()
	{
		BattleScene battleScene=new BattleScene();
		battleScene.addListener(this);
		setBattleScene(battleScene);
	}
	/** 创建房间检查 */
	protected String newRoomCheck(Session s)
	{
		return null;
	}
	/** 改变状态 */
	public void state(Session session)
	{
		if(state>=MATCH) return;
		synchronized(this)
		{
			Player player=(Player)session.getSource();
//			player.setStatus(Player.START);
		}
	}
	/** 游戏开始前的一些附加初始化 */
	public void initStart()
	{
		if(state>WAIT) return;
		// TODO 加载比赛
		state=MATCH_LOAD;
	}
	/** 开始比赛的检查 */
	public String startCheck(Session session)
	{
		return startCheck();
	}
	/** 开始比赛的检查 */
	protected String startCheck()
	{
		return null;
	}
	/** 开始比赛 */
	public synchronized void startMatch()
	{
		if(isMatching()) return;
		state=MATCH;
		// TODO 开始比赛
//		battleScene.start(true);
	}

	/** 退出 */
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
	/** 清除方法 */
	public void clear()
	{
		// battleScene.clear();
		session.setAttribute("room",null);
		session=null;
	}
	/** 计算结果 */
	public void totle(int winner)
	{
		
	}
	/** 计算结果,奖励 */
	protected void totleAward(Session session,int winner,ByteBuffer data)
	{
		
	}
	/** 计算结果,角色 */
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
		System.err.println("向前台广播消息！");
//		session.getConnect().send(GlobalConst.COMMAND_BATTLE_ZOMBIE,data);
		data.clear();
		
	}
}