package com.cambrian.dfhm.battle;

import java.util.ArrayList;

import com.cambrian.common.log.Logger;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.ChangeListener;
import com.cambrian.common.util.ChangeListenerList;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.battle.entity.DamageEntity;
import com.cambrian.dfhm.skill.DecrHurtSkill;
import com.cambrian.dfhm.skill.DizzySkill;
import com.cambrian.dfhm.skill.NoHurtSkill;
import com.cambrian.dfhm.skill.PoisonSkill;
import com.cambrian.dfhm.skill.Skill;

/**
 * 类说明：战斗逻辑类
 * 
 * @version 1.0
 * @author
 */
public class BattleScene
{

	/* static field */
	/** 日志记录 */
	public static final Logger log=Logger.getLogger(BattleScene.class);

	/** 攻击目标类型，1=自己，2=敌人 */
	public static final int OWN=1,NENMY=2;
	/** 普通攻击，技能攻击 */
	public static final int DEFAULTATT=1,SKILLATT=2;
	/** 最大卡牌参战数 */
	public static final int MAXBATTLECOUNT=5;
	/** DEBUFF常量：0=正常，1=中毒，2=晕眩,3=减伤 */
	public static final int NORMAL=0,POISON=1,DIZZY=2,DEHURT=3,NOHURT=4;
	/** 战斗类型（ 1普通PVE战斗 2世界BOSS战斗 3普通PVP战斗） */
	public static final int FIGHT_NORMAL=1,FIGHT_GLOBALBOSS=2,FIGHT_PVP=3;

	/* static method */

	/* field */
	boolean isStart=false;// 是否开始
	/** 当前回合数 */
	private int curRound=1;
	/** 战斗最大回合数 */
	private int maxRound;

	/** 最大技能发动率 */
	private final int MAXSKILLRATE=100000;
	/** 战斗步骤数（攻击） */
	private int step;
	/** 掉落记录：每个位置都有可能掉东西[类型，数量] */
	private ArrayList<Integer> dropAward=new ArrayList<Integer>(10);
	/** 战斗记录 */
	private BattleRecord record=new BattleRecord();
	/** 战斗结束标识 */
	private boolean die=false;
	private ArrayList<Skill> deSkill=new ArrayList<Skill>();
	/** 监听器 */
	private ChangeListenerList listener=new ChangeListenerList();
	/** 战斗回合限制（世界BOSS用） */
	private int roundConfine;

	/* constructors */

	/* properties */
	public int getMaxRound()
	{
		return maxRound;
	}
	public void setMaxRound(int maxRound)
	{
		this.maxRound=maxRound;
	}
	/** 添加监听器 */
	public void addListener(ChangeListener listener)
	{
		this.listener.addListener(listener);
	}
	/** 移除状态改变事件监听器 */
	public void removeListener(ChangeListener listener)
	{
		this.listener.removeListener(listener);
	}
	/** 移除所有的监听器 */
	public void removeListeners()
	{
		this.listener.removeListeners();
	}
	public int getStep()
	{
		return step;
	}
	public ArrayList<Integer> getRecord()
	{
		return record.getRecord();
	}
	public void setCurRound(int curRound)
	{
		this.curRound=curRound;
	}
	public ArrayList<Integer> getDropAward()
	{
		return dropAward;
	}
	public BattleRecord getBattleRecord()
	{
		return record;
	}
	public int getCurRound()
	{
		return curRound;
	}
	public void setRoundConfine(int roundConfine)
	{
		this.roundConfine=roundConfine;
	}
	/* init start */

	/* methods */
	/** 刷新下一个关卡 */
	public void refreshNextStage()
	{

	}

	/**
	 * 比赛开始
	 */
	public void start(BattleCard[] attList,BattleCard[] defList,int type)
	{
		System.err.println("BattleScene.start ----------");
		System.err.println("------出战，攻方人员人数-------"+attList.length);
		for(BattleCard battleCard:attList)
		{
			if(battleCard!=null)
				System.err.println(battleCard.getName()+", 位置="
					+battleCard.getIndex()+", 血量="+battleCard.getCurHp()
					+", 掉落IP ==="+battleCard.getAwardSid());
		}
		System.err.println("------出战，守方人员人数-------"+defList.length);
		for(BattleCard battleCard:defList)
		{
			if(battleCard!=null)
				System.err.println(battleCard.getName()+", 位置="
					+battleCard.getIndex()+", 血量="+battleCard.getCurHp()
					+", 掉落IP ==="+battleCard.getAwardSid());
		}
		System.err.println("------战斗开始-------");
		// TODO 考虑写到BattleRoom里面去，每次战斗就new BattleRoom
		step=0;// 步骤数，前台要用
		record.addRecord(step);
		int result;
		while(curRound<=maxRound)
		{
			for(int i=0;i<MAXBATTLECOUNT;i++)
			{
				System.err.println("------攻方攻击-------");
				die=attackLogic(attList,defList,type);// 攻方攻击
				if(die)
				{
					result=getResult(attList,defList);
					record.addRecord(result);// win
					endBattle(attList,defList,type,true);
					return;
				}

				System.err.println("------守方攻击-------");
				die=attackLogic(defList,attList,type);// 守方攻击
				if(die)
				{
					result=getResult(attList,defList);
					record.addRecord(result);// lose
					endBattle(attList,defList,type,false);
					return;
				}
			}
			endRound(attList,defList);
		}
		if(curRound>maxRound)
		{
			record.addRecord(0);
			endBattle(attList,defList,type,false);
		}
	}

	/**
	 * 胜负判断，1=win，-1=lose
	 * 
	 * @param attList
	 * @return
	 */
	private int getResult(BattleCard[] attList,BattleCard[] defList)
	{
		double attTotalCurHp=0;
		double attTotalMaxHp=0;
		double defTotalCurHp=0;
		double defTotalMaxHp=0;
		double factor=0;
		for(BattleCard card:attList)
		{
			if(card!=null)
			{
				if(card.getCurHp()<0)
				{
					card.setCurHp(0);
				}
				attTotalCurHp+=card.getCurHp();
				attTotalMaxHp+=card.getMaxHp();
			}
		}
		if(attTotalCurHp>0)
		{
			factor=attTotalCurHp/attTotalMaxHp;
			if(factor<=0.2)
			{
				return 1;
			}
			if(factor<=0.8)
			{
				return 2;
			}
			if(factor<=1)
			{
				return 3;
			}
		}
		else
		{
			for(BattleCard card:defList)
			{
				if(card!=null)
				{
					if(card.getCurHp()<0)
					{
						card.setCurHp(0);
					}
					defTotalCurHp+=card.getCurHp();
					defTotalMaxHp+=card.getMaxHp();
				}
			}
			factor=defTotalCurHp/defTotalMaxHp;
			if(factor<=0.2)
			{
				return -1;
			}
			if(factor<=0.8)
			{
				return -2;
			}
			if(factor<=1)
			{
				return -3;
			}
		}
		// int result=1;
		// BattleCard bCard;
		// for(int i=0;i<attList.length;i++)
		// {
		// bCard=attList[i];
		// if(bCard!=null&&bCard.getCurHp()>0) return result;
		// }
		// result=-1;
		// return result;
		return 0;
	}
	/**
	 * 攻击逻辑
	 * 
	 * @param attList
	 * @param defList
	 * @param side 进攻or防守
	 * @return 战斗结束
	 */
	private boolean attackLogic(BattleCard[] attList,BattleCard[] defList,
		int type)
	{
		ArrayList<Integer> aim=new ArrayList<Integer>();
		BattleCard attCard=getAttackCard(attList);
		if(attCard!=null)
		{
			record.addRecord(-99999);
			record.addRecord(curRound);
			step++;
			record.addRecord(attCard.getSide());
			record.addRecord(attCard.getIndex());
			System.err.println("出手人员 ==="+attCard.getName()+", 出手者位置 ==="
				+attCard.getIndex());
			System.err.println("side ==="+attCard.getSide());
			int deSkillTemp=attCard.getDeSkill().size();
			int status=deBuffer(attCard,attList,type,deSkillTemp);
			int target;
			if(attCard.getCurHp()<0)
			{
				if(die)
					record.addRecord(1);
				else
					record.addRecord(-1);
				return die;
			}
			deSkill=attCard.getDeSkill();
			if(deSkillTemp>0)// 通知前端，攻击者自身还剩debuff
			{
				record.addRecord(deSkill.size());
				for(Skill skill:deSkill)
					record.addRecord(skill.getSid());
			}
			if(status==NORMAL&&!die)
			{
				int attType=getAttType(attCard);
				if(type==FIGHT_GLOBALBOSS&&attList[0].getSide()==2)
				{
					attType=DEFAULTATT;
					if(attList[0].getAwardSid()==2&&curRound>=roundConfine)
					{
						attType=2;
					}
				}
				if(attType==DEFAULTATT)
				{
					target=attCard.getAimType();
				}
				else
				{
					target=attCard.getSkill().getAim();
				}
				if(target==OWN)
				{
					record.addRecord(attCard.getSide());
					record.addRecord(attType);
					System.err.println("------攻击目标是自己人-------");
					aim=getAttRange(attCard,attList,attType);
					System.err.print("攻击人数 ==="+aim.size());
					for(int i=0;i<aim.size();i++)
					{
						System.err.print(", 攻击位置 ==="+aim.get(i));
					}
					System.err.println();
					die=attValue(attCard,aim,attList,attType,false);
				}
				else
				{
					if(target==1)
					{
						record.addRecord(attCard.getSide());
					}
					else
					{
						if(attCard.getSide()==1)
						{
							record.addRecord(2);
						}
						else
						{
							record.addRecord(1);
						}
					}
					record.addRecord(attType);
					System.err.println("------攻击目标是对手-------");
					aim=getAttRange(attCard,defList,attType);
					System.err.print("攻击人数 ==="+aim.size());
					for(int i=0;i<aim.size();i++)
					{
						System.err.print(", 攻击位置 ==="+aim.get(i));
					}
					System.err.println();
					die=attValue(attCard,aim,defList,attType,true);
				}
				die=addLog_end(defList);
				System.err.println("die ==="+die);
				if(die) return die;
				// TODO 暂时不考虑连续技，先不发连续技数据
				die=doubleSkillAtt(attCard.getSkill().getSid(),
					attCard.getIndex(),attList,defList);
				if(die) return die;
			}
			else
			{
				if(status==DIZZY)
				{
					die=false;
					attCard.setAttack(true);
					return die;
				}
				if(deSkill.size()==0) record.addRecord(deSkill.size());
				if(die)
				{
					drop(attCard);
					die=true;
					die=addLog_end(attList);
					System.err.println("战斗结束 ======"+die);
					return die;
				}
			}
			attCard.setAttack(true);
			record.addRecord(-1);
		}
		return die;
	}
	/** 获得攻击类型，1=普通，2=技能 */
	private int getAttType(BattleCard attCard)
	{
		int rate=MathKit.randomValue(0,MAXSKILLRATE+1);
		int attType=DEFAULTATT;
		if(rate<=attCard.getSkillRate()+attCard.getSkill().getSkillRate())
			attType=SKILLATT;
		return attType;
	}

	/** 战斗结束判断，并记录日志 */
	private boolean addLog_end(BattleCard[] defList)
	{
		if(die)
			die=isEnd(defList);
		else
			record.addRecord(-1);
		return die;
	}

	/**
	 * 连续技能攻击判断
	 * 
	 * @param skillId 触发技能ID
	 * @param attList
	 * @param attList
	 * @param index 技能发动者所在位置
	 * @return 战斗结束
	 */
	private boolean doubleSkillAtt(int skillId,int index,
		BattleCard[] attList,BattleCard[] defList)
	{
		int[] ds=GameCFG.getDSkillById(skillId);
		boolean fire=false;
		if(ds==null) return false;
		ArrayList<Integer> aim=new ArrayList<Integer>();
		BattleCard attCard;
		for(int i=index+1;i<attList.length;i++)
		{
			if(i==index) break;
			attCard=attList[i];
			if(attCard!=null&&attCard.getCurHp()>0)
			{
				for(int skillId_:ds)
				{
					if(skillId==skillId_) continue;
					if(skillId_==attCard.getSkill().getSid())
					{
						fire=true;
					}
				}
				if(fire)
				{
					System.err.println("连续技攻击 -----------------");
					addLog(attCard);
					aim=getAttRange(attCard,defList,SKILLATT);
					System.err.print("攻击人数 ==="+aim.size());
					for(int j=0;j<aim.size();j++)
					{
						System.err.print(", 攻击位置 ==="+aim.get(j));
					}
					System.err.println();
					die=attValue(attCard,aim,defList,SKILLATT,true);
					die=addLog_end(defList);
					if(die) return true;
				}
			}
			if(i==attList.length-1) i=-1;
		}
		return false;
	}

	/**
	 * 连续技，记录添加
	 * 
	 * @param attCard
	 */
	private void addLog(BattleCard attCard)
	{
		step++;
		record.addRecord(1);// 有连续技
		record.addRecord(attCard.getIndex());// 触发连续技人的位置，前台需要
		record.addRecord(-99999);
		record.addRecord(curRound);
		record.addRecord(attCard.getSide());
		record.addRecord(attCard.getIndex());
		record.addRecord(0);// 连续技不受debuff状态影响
		if(attCard.getSkill().getAim()==1)
		{
			record.addRecord(attCard.getSide());
		}
		else
		{
			if(attCard.getSide()==1)
			{
				record.addRecord(2);
			}
			else
			{
				record.addRecord(1);
			}
		}
		record.addRecord(2);// 技能攻击
	}

	/**
	 * 伤害效果计算
	 * 
	 * @param attCard 出手者
	 * @param aim 攻击目标数组
	 * @param aimList 攻击对象数组
	 * @param attType 攻击方式类型
	 * @param isDamage 是否造成伤害
	 * @return 是否死亡
	 */
	private boolean attValue(BattleCard attCard,ArrayList<Integer> aim,
		BattleCard[] aimList,int attType,boolean isDamage)
	{
		boolean die=false;
		ArrayList<DamageEntity> hurtList=new ArrayList<DamageEntity>();
		BattleCard bCard;
		DamageEntity damageEntity;
		if(attType==SKILLATT)
		{
			// 技能伤害等于技能伤害系数乘以玩家攻击力
			// 实际伤害会在攻击力上进行正负5%区间的浮动
			System.err.println("------技能攻击-------");
			record.addRecord(attCard.getSkill().getId());
			hurtList=attCard.getSkill().skillValue(attCard,aim,aimList,
				record);
		}
		else
		{
			System.err.println("------普通攻击-------");
			hurtList=BattleAct.getAttValue(attCard,aim,aimList,record,
				hurtList);
			System.out.println();
		}
		record.addRecord(aim.size());
		for(int i=0;i<aim.size();i++)
		{
			bCard=aimList[aim.get(i)];
			if(hurtList.size()>i)
				damageEntity=hurtList.get(i);
			else
				damageEntity=hurtList.get(0);
			if(attCard.getAimType()==OWN)
			{
				bCard.incrHp(damageEntity.getValue());
				die=false;
			}
			else
				die=bCard.decrHp(damageEntity.getValue());
			record.addRecord(bCard.getIndex());
			record.addRecord(damageEntity.getValue());
			record.addRecord(damageEntity.getStatus());
			if(die)
			{
				record.addRecord(1);// 死
				drop(bCard);
			}
			else
			{
				record.addRecord(-1);// 没死
				record.addRecord(-1);// 不掉东西
			}
			System.err.println(bCard.getName()+",被照成伤害，血量损失 ==="
				+damageEntity.getValue()+", 剩余血量 ==="+bCard.getCurHp());
			if(isDamage&&attCard.getSide()==1)
				record.countDamage(damageEntity.getValue());
			// 计算被攻击者，身上的debuff
			deSkill=bCard.getDeSkill();
			record.addRecord(deSkill.size());
			for(Skill skill:deSkill)
				record.addRecord(skill.getSid());
		}
		return die;
	}

	/** 获得出手攻击卡牌 */
	private BattleCard getAttackCard(BattleCard[] list)
	{
		for(BattleCard bcard:list)
		{
			if(bcard!=null&&!bcard.isAttack()&&bcard.getCurHp()>0) // 出手状态判断
				return bcard;
		}
		return null;
	}

	/** 获得攻击范围 */
	private ArrayList<Integer> getAttRange(BattleCard attCard,
		BattleCard[] aimList,int attType)
	{
		ArrayList<Integer> aim=new ArrayList<Integer>();
		if(attType==DEFAULTATT)
			attType=attCard.getAttRange();
		else
			attType=attCard.getSkill().getAttRange();
		System.err.println("攻击范围类型 ==="+attType);
		BattleCard aimCard=BattleAct.getAim(attCard,aimList);
		switch(attType)
		{
			case BattleAct.DEFAULT:// 0=默认
				aim=BattleAct.defaultAtt(aimCard,aimList);
				break;
			case BattleAct.RANDOM:// 1=随机
				aim=BattleAct.randomAtt(aimList);
				break;
			case BattleAct.ENDLONG:// 2=纵向
				aim=BattleAct.endLongAtt(aimCard,aimList);
				break;
			case BattleAct.HORIZONTAL:// 3=横向
				aim=BattleAct.horizontalAtt(aimCard,aimList);
				break;
			case BattleAct.ENEMYALL:// 4=敌全体
				for(int i=0;i<aimList.length;i++)
				{
					aimCard=aimList[i];
					if(aimCard!=null&&aimCard.getCurHp()>0)
						aim.add(aimCard.getIndex());
				}
				break;
			case BattleAct.OWNALL:// 5=己全体
				for(int i=0;i<aimList.length;i++)
				{
					aimCard=aimList[i];
					if(aimCard!=null&&aimCard.getCurHp()>0)
						aim.add(aimCard.getIndex());
				}
				break;
			case BattleAct.LEFTINCLINED:// 6=左斜
				aim=BattleAct.leftInclinedleAtt(aimCard,aimList);
				break;
			case BattleAct.RIGHTINCLINED:// 7=右斜
				aim=BattleAct.rightInclinedleAtt(aimCard,aimList);
				break;
			case BattleAct.MITTLE:// 8=中间
				aim=BattleAct.mittleAtt(aimList);
				break;
			case BattleAct.OWNHPMIN:// 9=己方血少
				aim=BattleAct.HpMin(aimList);
				break;
			case BattleAct.ENEMYHPMIN:// 10=敌方血少
				aim=BattleAct.HpMin(aimList);
				break;
			case BattleAct.SELE:// 11=自己
				aim.add(attCard.getIndex());
				break;

			default:
				break;
		}
		return aim;
	}

	/**
	 * 衰弱buffer计算
	 * 
	 * @param bCard 出手卡牌
	 * @param attList 攻方链表
	 * @return 0=正常，1=中毒，2=晕眩
	 */
	private int deBuffer(BattleCard attCard,BattleCard[] attList,int type,
		int size)
	{
		deSkill=attCard.getDeSkill();
		if(type==FIGHT_GLOBALBOSS)
		{
			deSkill.clear();
		}
		int status=0;
		// ArrayList<Skill> skillList=new ArrayList<Skill>();
		// for(int i=0;i<deSkill.size();i++)
		// {
		// if(deSkill.get(i) instanceof DecrHurtSkill
		// ||deSkill.get(i) instanceof NoHurtSkill) continue;
		// skillList.add(deSkill.get(i));
		// }
		record.addRecord(deSkill.size());
		for(int i=0;i<size;i++)
		{
			if(deSkill.get(i) instanceof PoisonSkill)
			{
				System.err.println("出手人员拥有debuffer状态，debuff ==="
					+deSkill.get(i).getName());
				deSkill.get(i).buffValue(attCard,0,attCard,record);
				if(attCard.getCurHp()<0)
				{
					drop(attCard);
					die=isEndNoRecord(attList);
					attCard.setAttack(true);
					status=POISON;
				}
			}
			else if(deSkill.get(i) instanceof DizzySkill)
			{
				deSkill.get(i).buffValue(attCard,0,attCard,record);
				status=DIZZY;
			}
			else if(deSkill.get(i) instanceof DecrHurtSkill)
			{
				record.addRecord(deSkill.get(i).getSid());
				record.addRecord(DEHURT);
				status=0;
			}
			else if(deSkill.get(i) instanceof NoHurtSkill)
			{
				record.addRecord(deSkill.get(i).getSid());
				record.addRecord(NOHURT);
				status=0;
			}
		}
		return status;
	}

	/** 回合结束计算 */
	public void endRound(BattleCard[] attList,BattleCard[] defList)
	{
		curRound++;
		for(BattleCard attCard:attList)
		{
			if(attCard!=null&&attCard.getCurHp()>0)
				attCard.setAttack(false);
		}
		for(BattleCard defCard:defList)
		{
			if(defCard!=null&&defCard.getCurHp()>0)
				defCard.setAttack(false);
		}
		die=false;
	}

	/**
	 * @param aim
	 * @return 比赛是否结束
	 */
	private boolean isEnd(BattleCard[] aim)
	{
		BattleCard bCard;
		for(int i=0;i<aim.length;i++)
		{
			bCard=aim[i];
			if(bCard!=null)
			{
				System.err.println("判断战斗是否结束，血量剩余情况===="+bCard.getCurHp());
			}
			if(bCard!=null&&bCard.getCurHp()>0)
			{
				record.addRecord(-1);
				return false;
			}
		}
		record.addRecord(1);
		return true;
	}

	/**
	 * 比赛是否结束
	 * 
	 * @param aim
	 * @return
	 */
	private boolean isEndNoRecord(BattleCard[] aim)
	{
		BattleCard bCard;
		for(int i=0;i<aim.length;i++)
		{
			bCard=aim[i];
			if(bCard!=null)
			{
				System.err.println("判断战斗是否结束，血量剩余情况===="+bCard.getCurHp());
			}
			if(bCard!=null&&bCard.getCurHp()>0)
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * 掉落计算
	 * 
	 * @param dieCard
	 * @return 是否掉落
	 */
	private boolean drop(BattleCard dieCard)
	{
		int type=0;
		System.err.println("掉落计算物品ID ===="+dieCard.getAwardSid());
		if(dieCard.getAwardSid()>0)
		{
			com.cambrian.dfhm.drop.Monster drop=(com.cambrian.dfhm.drop.Monster)Sample.factory
				.getSample(dieCard.getAwardSid());
			int[] award=drop.dispense(dropAward);
			type=award[0];
			dropAward.add(award[0]);
			dropAward.add(award[1]);
			System.err.println("掉落物品类型 ==="+type+", 掉落物品数量 ==="+award[1]);
			record.addRecord(1);// 掉东西
			record.addRecord(type);
			return true;
		}
		else
		{
			record.addRecord(-1);// 不掉东西
			return false;
		}
	}

	/**
	 * 战斗结束，恢复数据
	 * 
	 * @param attList
	 * @param defList
	 * @param type 战斗类型
	 * @param side 攻击or防守
	 */
	private void endBattle(BattleCard[] attList,BattleCard[] defList,
		int type,boolean side)
	{
		for(BattleCard battleCard:attList)
		{
			if(battleCard!=null)
			{
				battleCard.setAttack(false);
				battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.getDeSkill().clear();
			}
		}
		for(BattleCard battleCard:defList)
		{
			if(battleCard!=null)
			{
				battleCard.setAttack(false);
				if(type==FIGHT_NORMAL||(type==FIGHT_GLOBALBOSS&&side))
					battleCard.setCurHp(battleCard.getMaxHp());
				battleCard.getDeSkill().clear();
			}
		}
	}

}