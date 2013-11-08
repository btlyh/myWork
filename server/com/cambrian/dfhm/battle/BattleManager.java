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
 * ��˵���� ս��������
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class BattleManager extends ChangeAdapter implements TimerListener
{

	/** ��־��¼ */
	private static Logger log=Logger.getLogger(BattleManager.class);
	/** ���ʵ�� */
	private static final BattleManager instance = new BattleManager();
	/** DS */
	DataServer manager;
	/** ʱ����� */
	protected TimerEvent runTimerEvent=new TimerEvent(this,"run",80);
	
	public static BattleManager getInstance()
	{
		return instance;
	}
	
	/** ���dsmananger */
	public DataServer getDSManager()
	{
		return manager;
	}
	/** ����dsmananger */
	public void setDSManager(DataServer ds)
	{
		this.manager=ds;
		this.manager.addListener(this);
	}

	/** ��ʼ��ʱ�� */
	public void timerStart()
	{
		TimerCenter.getMillisTimer().add(runTimerEvent);
	}

	/** ֹͣ��ʱ�� */
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
	/** ����ı䷽�� */
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
	
	/** ����ս�����佱�� */
	public void award(Player player, ArrayList<Integer> award)
	{
		int type , value;
		for (int i = 0; i < award.size(); i+=2) {
			type = award.get(i);
			value = award.get(i + 1);
			if(type == 1)//��Ϸ��
				player.incrMoney(value);
			else if(type == 2)//���
				player.incrSoul(value);
			else if(type == 3)//���
				player.incrGold(value);
			else if(type == 4)//����
				player.getCardBag().add(value);
		}		
	}
}