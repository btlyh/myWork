package com.cambrian.dfhm.battle;

import java.util.ArrayList;

import com.cambrian.common.log.Logger;
import com.cambrian.common.timer.TimerCenter;
import com.cambrian.common.timer.TimerEvent;
import com.cambrian.common.timer.TimerListener;
import com.cambrian.common.util.ChangeAdapter;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明： 战场管理器
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class BattleManager extends ChangeAdapter implements TimerListener
{

	/** 日志记录 */
	private static Logger log=Logger.getLogger(BattleManager.class);
	/** 获得实例 */
	private static final BattleManager instance = new BattleManager();
	/** DS */
	DataServer manager;
	/** 时间监听 */
	protected TimerEvent runTimerEvent=new TimerEvent(this,"run",80);
	
	public static BattleManager getInstance()
	{
		return instance;
	}
	
	/** 获得dsmananger */
	public DataServer getDSManager()
	{
		return manager;
	}
	/** 设置dsmananger */
	public void setDSManager(DataServer ds)
	{
		this.manager=ds;
		this.manager.addListener(this);
	}

	/** 开始定时器 */
	public void timerStart()
	{
		TimerCenter.getMillisTimer().add(runTimerEvent);
	}

	/** 停止定时器 */
	public void timerStop()
	{
		if(log.isDebugEnabled()) log.debug(" ,stop "+toString());
		TimerCenter.getMinuteTimer().remove(runTimerEvent);
	}

	public void onTimer(TimerEvent ev)
	{
		if(ev==runTimerEvent) run(ev.getCurrentTime());
	}
	public void run(long time)
	{
		
	}
	
	public void battleInit(Player player, BattleCard[] att, BattleCard[] def)
	{	
		for (BattleCard battleCard : att) {
			if(battleCard != null)
			{
				battleCard.setSide(1);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
		for (BattleCard battleCard : def) {
			if(battleCard != null)
			{
				battleCard.setSide(2);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
	}

	@Override
	public void change(Object source,int type)
	{
		
	}
	/** 对象改变方法 */
	@Override
	public void change(Object source,int type,Object value)
	{
		if((source instanceof DataServer)
			&&(type==DataServer.LOGIN_AGAIN_CHANGED))
		{
			if(value==null) return;
			// getReLoadBattle((Session)value);
		}
		else if((source instanceof DataServer)
			&&(type==DataServer.LOAD_CHANGED))
		{
			if(value==null) return;
			Session s=(Session)value;
			// getOffLineBattle(s);
		}
		else if((source instanceof DataServer)
			&&(type==DataServer.PRE_EXIT_CHANGED))
		{
			if(value==null) return;
			Session s=(Session)value;
			Player player=(Player)s.getSource();
			log.debug("PRE_EXIT_CHANGED node,"+player);
			// if(player!=null) saveOffLineBattle(s);
		}
		else if((source instanceof DataServer)&&type==DataServer.EXIT_CHANGED)
		{
			
		}
	}
	
	/** 发放战斗掉落奖励 */
	public void award(Player player, ArrayList<Integer> award)
	{
		int type , value;
		for (int i = 0; i < award.size(); i+=2) {
			type = award.get(i);
			value = award.get(i + 1);
			if(type == 1)//游戏币
				player.incrMoney(value);
			else if(type == 2)//武魂
				player.incrSoul(value);
			else if(type == 3)//金币
				player.incrGold(value);
			else if(type == 4)//卡牌
				player.getCardBag().add(value);
		}		
	}
}