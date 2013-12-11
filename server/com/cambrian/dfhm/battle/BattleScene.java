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
 * ��˵����ս���߼���
 * 
 * @version 1.0
 * @author
 */
public class BattleScene
{

	/* static field */
	/** ��־��¼ */
	public static final Logger log=Logger.getLogger(BattleScene.class);

	/** ����Ŀ�����ͣ�1=�Լ���2=���� */
	public static final int OWN=1,NENMY=2;
	/** ��ͨ���������ܹ��� */
	public static final int DEFAULTATT=1,SKILLATT=2;
	/** ����Ʋ�ս�� */
	public static final int MAXBATTLECOUNT=5;
	/** DEBUFF������0=������1=�ж���2=��ѣ,3=���� */
	public static final int NORMAL=0,POISON=1,DIZZY=2,DEHURT=3,NOHURT=4;
	/** ս�����ͣ� 1��ͨPVEս�� 2����BOSSս�� 3��ͨPVPս���� */
	public static final int FIGHT_NORMAL=1,FIGHT_GLOBALBOSS=2,FIGHT_PVP=3;

	/* static method */

	/* field */
	boolean isStart=false;// �Ƿ�ʼ
	/** ��ǰ�غ��� */
	private int curRound=1;
	/** ս�����غ��� */
	private int maxRound;

	/** ����ܷ����� */
	private final int MAXSKILLRATE=100000;
	/** ս���������������� */
	private int step;
	/** �����¼��ÿ��λ�ö��п��ܵ�����[���ͣ�����] */
	private ArrayList<Integer> dropAward=new ArrayList<Integer>(10);
	/** ս����¼ */
	private BattleRecord record=new BattleRecord();
	/** ս��������ʶ */
	private boolean die=false;
	private ArrayList<Skill> deSkill=new ArrayList<Skill>();
	/** ������ */
	private ChangeListenerList listener=new ChangeListenerList();
	/** ս���غ����ƣ�����BOSS�ã� */
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
	/** ��Ӽ����� */
	public void addListener(ChangeListener listener)
	{
		this.listener.addListener(listener);
	}
	/** �Ƴ�״̬�ı��¼������� */
	public void removeListener(ChangeListener listener)
	{
		this.listener.removeListener(listener);
	}
	/** �Ƴ����еļ����� */
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
	/** ˢ����һ���ؿ� */
	public void refreshNextStage()
	{

	}

	/**
	 * ������ʼ
	 */
	public void start(BattleCard[] attList,BattleCard[] defList,int type)
	{
		System.err.println("BattleScene.start ----------");
		System.err.println("------��ս��������Ա����-------"+attList.length);
		for(BattleCard battleCard:attList)
		{
			if(battleCard!=null)
				System.err.println(battleCard.getName()+", λ��="
					+battleCard.getIndex()+", Ѫ��="+battleCard.getCurHp()
					+", ����IP ==="+battleCard.getAwardSid());
		}
		System.err.println("------��ս���ط���Ա����-------"+defList.length);
		for(BattleCard battleCard:defList)
		{
			if(battleCard!=null)
				System.err.println(battleCard.getName()+", λ��="
					+battleCard.getIndex()+", Ѫ��="+battleCard.getCurHp()
					+", ����IP ==="+battleCard.getAwardSid());
		}
		System.err.println("------ս����ʼ-------");
		// TODO ����д��BattleRoom����ȥ��ÿ��ս����new BattleRoom
		step=0;// ��������ǰ̨Ҫ��
		record.addRecord(step);
		int result;
		while(curRound<=maxRound)
		{
			for(int i=0;i<MAXBATTLECOUNT;i++)
			{
				System.err.println("------��������-------");
				die=attackLogic(attList,defList,type);// ��������
				if(die)
				{
					result=getResult(attList,defList);
					record.addRecord(result);// win
					endBattle(attList,defList,type,true);
					return;
				}

				System.err.println("------�ط�����-------");
				die=attackLogic(defList,attList,type);// �ط�����
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
	 * ʤ���жϣ�1=win��-1=lose
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
	 * �����߼�
	 * 
	 * @param attList
	 * @param defList
	 * @param side ����or����
	 * @return ս������
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
			System.err.println("������Ա ==="+attCard.getName()+", ������λ�� ==="
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
			if(deSkillTemp>0)// ֪ͨǰ�ˣ�����������ʣdebuff
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
					System.err.println("------����Ŀ�����Լ���-------");
					aim=getAttRange(attCard,attList,attType);
					System.err.print("�������� ==="+aim.size());
					for(int i=0;i<aim.size();i++)
					{
						System.err.print(", ����λ�� ==="+aim.get(i));
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
					System.err.println("------����Ŀ���Ƕ���-------");
					aim=getAttRange(attCard,defList,attType);
					System.err.print("�������� ==="+aim.size());
					for(int i=0;i<aim.size();i++)
					{
						System.err.print(", ����λ�� ==="+aim.get(i));
					}
					System.err.println();
					die=attValue(attCard,aim,defList,attType,true);
				}
				die=addLog_end(defList);
				System.err.println("die ==="+die);
				if(die) return die;
				// TODO ��ʱ���������������Ȳ�������������
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
					System.err.println("ս������ ======"+die);
					return die;
				}
			}
			attCard.setAttack(true);
			record.addRecord(-1);
		}
		return die;
	}
	/** ��ù������ͣ�1=��ͨ��2=���� */
	private int getAttType(BattleCard attCard)
	{
		int rate=MathKit.randomValue(0,MAXSKILLRATE+1);
		int attType=DEFAULTATT;
		if(rate<=attCard.getSkillRate()+attCard.getSkill().getSkillRate())
			attType=SKILLATT;
		return attType;
	}

	/** ս�������жϣ�����¼��־ */
	private boolean addLog_end(BattleCard[] defList)
	{
		if(die)
			die=isEnd(defList);
		else
			record.addRecord(-1);
		return die;
	}

	/**
	 * �������ܹ����ж�
	 * 
	 * @param skillId ��������ID
	 * @param attList
	 * @param attList
	 * @param index ���ܷ���������λ��
	 * @return ս������
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
					System.err.println("���������� -----------------");
					addLog(attCard);
					aim=getAttRange(attCard,defList,SKILLATT);
					System.err.print("�������� ==="+aim.size());
					for(int j=0;j<aim.size();j++)
					{
						System.err.print(", ����λ�� ==="+aim.get(j));
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
	 * ����������¼���
	 * 
	 * @param attCard
	 */
	private void addLog(BattleCard attCard)
	{
		step++;
		record.addRecord(1);// ��������
		record.addRecord(attCard.getIndex());// �����������˵�λ�ã�ǰ̨��Ҫ
		record.addRecord(-99999);
		record.addRecord(curRound);
		record.addRecord(attCard.getSide());
		record.addRecord(attCard.getIndex());
		record.addRecord(0);// ����������debuff״̬Ӱ��
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
		record.addRecord(2);// ���ܹ���
	}

	/**
	 * �˺�Ч������
	 * 
	 * @param attCard ������
	 * @param aim ����Ŀ������
	 * @param aimList ������������
	 * @param attType ������ʽ����
	 * @param isDamage �Ƿ�����˺�
	 * @return �Ƿ�����
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
			// �����˺����ڼ����˺�ϵ��������ҹ�����
			// ʵ���˺����ڹ������Ͻ�������5%����ĸ���
			System.err.println("------���ܹ���-------");
			record.addRecord(attCard.getSkill().getId());
			hurtList=attCard.getSkill().skillValue(attCard,aim,aimList,
				record);
		}
		else
		{
			System.err.println("------��ͨ����-------");
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
				record.addRecord(1);// ��
				drop(bCard);
			}
			else
			{
				record.addRecord(-1);// û��
				record.addRecord(-1);// ��������
			}
			System.err.println(bCard.getName()+",���ճ��˺���Ѫ����ʧ ==="
				+damageEntity.getValue()+", ʣ��Ѫ�� ==="+bCard.getCurHp());
			if(isDamage&&attCard.getSide()==1)
				record.countDamage(damageEntity.getValue());
			// ���㱻�����ߣ����ϵ�debuff
			deSkill=bCard.getDeSkill();
			record.addRecord(deSkill.size());
			for(Skill skill:deSkill)
				record.addRecord(skill.getSid());
		}
		return die;
	}

	/** ��ó��ֹ������� */
	private BattleCard getAttackCard(BattleCard[] list)
	{
		for(BattleCard bcard:list)
		{
			if(bcard!=null&&!bcard.isAttack()&&bcard.getCurHp()>0) // ����״̬�ж�
				return bcard;
		}
		return null;
	}

	/** ��ù�����Χ */
	private ArrayList<Integer> getAttRange(BattleCard attCard,
		BattleCard[] aimList,int attType)
	{
		ArrayList<Integer> aim=new ArrayList<Integer>();
		if(attType==DEFAULTATT)
			attType=attCard.getAttRange();
		else
			attType=attCard.getSkill().getAttRange();
		System.err.println("������Χ���� ==="+attType);
		BattleCard aimCard=BattleAct.getAim(attCard,aimList);
		switch(attType)
		{
			case BattleAct.DEFAULT:// 0=Ĭ��
				aim=BattleAct.defaultAtt(aimCard,aimList);
				break;
			case BattleAct.RANDOM:// 1=���
				aim=BattleAct.randomAtt(aimList);
				break;
			case BattleAct.ENDLONG:// 2=����
				aim=BattleAct.endLongAtt(aimCard,aimList);
				break;
			case BattleAct.HORIZONTAL:// 3=����
				aim=BattleAct.horizontalAtt(aimCard,aimList);
				break;
			case BattleAct.ENEMYALL:// 4=��ȫ��
				for(int i=0;i<aimList.length;i++)
				{
					aimCard=aimList[i];
					if(aimCard!=null&&aimCard.getCurHp()>0)
						aim.add(aimCard.getIndex());
				}
				break;
			case BattleAct.OWNALL:// 5=��ȫ��
				for(int i=0;i<aimList.length;i++)
				{
					aimCard=aimList[i];
					if(aimCard!=null&&aimCard.getCurHp()>0)
						aim.add(aimCard.getIndex());
				}
				break;
			case BattleAct.LEFTINCLINED:// 6=��б
				aim=BattleAct.leftInclinedleAtt(aimCard,aimList);
				break;
			case BattleAct.RIGHTINCLINED:// 7=��б
				aim=BattleAct.rightInclinedleAtt(aimCard,aimList);
				break;
			case BattleAct.MITTLE:// 8=�м�
				aim=BattleAct.mittleAtt(aimList);
				break;
			case BattleAct.OWNHPMIN:// 9=����Ѫ��
				aim=BattleAct.HpMin(aimList);
				break;
			case BattleAct.ENEMYHPMIN:// 10=�з�Ѫ��
				aim=BattleAct.HpMin(aimList);
				break;
			case BattleAct.SELE:// 11=�Լ�
				aim.add(attCard.getIndex());
				break;

			default:
				break;
		}
		return aim;
	}

	/**
	 * ˥��buffer����
	 * 
	 * @param bCard ���ֿ���
	 * @param attList ��������
	 * @return 0=������1=�ж���2=��ѣ
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
				System.err.println("������Աӵ��debuffer״̬��debuff ==="
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

	/** �غϽ������� */
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
	 * @return �����Ƿ����
	 */
	private boolean isEnd(BattleCard[] aim)
	{
		BattleCard bCard;
		for(int i=0;i<aim.length;i++)
		{
			bCard=aim[i];
			if(bCard!=null)
			{
				System.err.println("�ж�ս���Ƿ������Ѫ��ʣ�����===="+bCard.getCurHp());
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
	 * �����Ƿ����
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
				System.err.println("�ж�ս���Ƿ������Ѫ��ʣ�����===="+bCard.getCurHp());
			}
			if(bCard!=null&&bCard.getCurHp()>0)
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * �������
	 * 
	 * @param dieCard
	 * @return �Ƿ����
	 */
	private boolean drop(BattleCard dieCard)
	{
		int type=0;
		System.err.println("���������ƷID ===="+dieCard.getAwardSid());
		if(dieCard.getAwardSid()>0)
		{
			com.cambrian.dfhm.drop.Monster drop=(com.cambrian.dfhm.drop.Monster)Sample.factory
				.getSample(dieCard.getAwardSid());
			int[] award=drop.dispense(dropAward);
			type=award[0];
			dropAward.add(award[0]);
			dropAward.add(award[1]);
			System.err.println("������Ʒ���� ==="+type+", ������Ʒ���� ==="+award[1]);
			record.addRecord(1);// ������
			record.addRecord(type);
			return true;
		}
		else
		{
			record.addRecord(-1);// ��������
			return false;
		}
	}

	/**
	 * ս���������ָ�����
	 * 
	 * @param attList
	 * @param defList
	 * @param type ս������
	 * @param side ����or����
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