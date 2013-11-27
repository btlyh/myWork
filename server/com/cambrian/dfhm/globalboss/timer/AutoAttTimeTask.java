package com.cambrian.dfhm.globalboss.timer;

import java.util.TimerTask;

import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.BattleScene;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.globalboss.entity.GlobalBossCFG;
import com.cambrian.dfhm.globalboss.logic.BossManager;
import com.cambrian.dfhm.globalboss.notice.BossAutoAttNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：自动战斗定时器任务
 * 
 * @author：LazyBear
 */
public class AutoAttTimeTask extends TimerTask
{

	/* static fields */

	/* static methods */

	/* fields */
	/** BOSS配置信息 */
	private GlobalBossCFG gbc;
	/** 数据服务器 */
	private DataServer ds;
	/** BOSS自动战斗数据推送 */
	private BossAutoAttNotice baan;

	/* constructors */
	public AutoAttTimeTask(GlobalBossCFG gbc,DataServer ds,
		BossAutoAttNotice baan)
	{
		this.gbc=gbc;
		this.ds=ds;
		this.baan=baan;
	}

	/* properties */

	/* init start */

	/* methods */
	@Override
	public void run()
	{
		if(gbc.isOpen())
		{
			for(Player player:gbc.autoList)
			{
				if(player.getBfr().getLastAttTime()+TimeKit.MIN_MILLS
					*gbc.getAttCD()<TimeKit.nowTimeMills())
				{
					synchronized(gbc)
					{
						Formation formation_=(Formation)player.formation.clone();
						BattleCard[] att=formation_.getFormation();
						if(player.getBfr().getAttUp()>1)
						{
							for(BattleCard battleCard:att)
							{
								if(battleCard!=null)
									battleCard.attUp(player.getBfr().getAttUp());
							}
						}
						BattleCard[] def=gbc.getMonsters();
						BattleScene scene=new BattleScene();
						battleInit(att,def);
						scene.setMaxRound(30);
						scene.setRoundConfine(gbc.getRoundConfine());
						scene.start(att,def,BattleScene.FIGHT_GLOBALBOSS);
						scene.getRecord().set(0,scene.getStep());

						player.getBfr().setLastAttTime(
							TimeKit.nowTimeMills());
						player.getBfr().recordDamage(
							scene.getBattleRecord().getTotalDamage());

						gbc.rankMap.put((int)player.getUserId(),
							player.getBfr());
						// 战斗胜利后的处理
						int win=scene.getRecord().get(
							scene.getRecord().size()-1);
						if(win==1)// 胜利
						{
							player.getBfr().setFinished(true);
							gbc.countRank();
							gbc.setOpen(false);
							gbc.reset();
							BossManager.getInstance().sendReward(gbc,true);
							gbc.autoList.clear();
						}
						Session session=ds.getSession(player.getNickname());
						if(session!=null)
						{
							baan.send(session,
								new Object[]{scene.getRecord()});
						}
					}
				}
			}
		}
	}
	/**
	 * 战斗前初始化
	 * 
	 * @param att
	 * @param def
	 * @param isGlobalBoss 是否攻击世界BOSS
	 */
	private void battleInit(BattleCard[] att,BattleCard[] def)
	{
		for(BattleCard battleCard:att)
		{
			if(battleCard!=null)
			{
				battleCard.setSide(1);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.setAttack(false);
			}
		}
		for(BattleCard battleCard:def)
		{
			if(battleCard!=null)
			{
				battleCard.setSide(2);
				battleCard.setAttack(false);
			}
		}
	}
}
