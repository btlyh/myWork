package com.cambrian.dfhm.battle;

import java.util.ArrayList;
import java.util.Random;

import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.battle.entity.DamageEntity;
import com.cambrian.dfhm.skill.DecrHurtSkill;
import com.cambrian.dfhm.skill.IncrHurtSkill;
import com.cambrian.dfhm.skill.Skill;

/**
 * ս��������
 */
public class BattleAct
{

	/* static fields */
	/** Buffer״̬: 1=����, 2=�ж�, 3=ѣ��, 4=����, 5=��ŭ, 6=׷ɱ, 7=�ӹ�, 8=����, 9=���� */
	public static final int TREAT=1,POISON=2,DIZZY=3,ATTACKS=4,ANGER=5,
					CHASE=6,INCRHURT=7,DECRHURT=8,NOHURT=9;
	/**
	 * ������Χ: 0=Ĭ��, 1=����� 2=����3=����, 4=��ȫ��, 5=��ȫ��, 6=��б, 7=��б, 8=�м�, 9=����Ѫ�٣�
	 * 10=�з�Ѫ�٣�11=�Լ�
	 */
	public static final int DEFAULT=0,RANDOM=1,ENDLONG=2,HORIZONTAL=3,
					ENEMYALL=4,OWNALL=5,LEFTINCLINED=6,RIGHTINCLINED=7,
					MITTLE=8,OWNHPMIN=9,ENEMYHPMIN=10,SELE=11;

	/* static methods */

	/* fields */
	public static Random ran=new Random();

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/**
	 * Ĭ�ϡ���������Χ
	 * 
	 * @param aimCard Ŀ��λ��
	 * @param aimList ��������
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> defaultAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		ArrayList<Integer> aim=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					if(aimCard!=null&&aimCard.getIndex()==1
						&&battleCard.getIndex()==3)
						aim.add(4);
					else
						aim.add(battleCard.getIndex());
					return aim;
				}
			}
		}
		else
			aim.add(aimCard.getIndex());
		return aim;
	}

	/**
	 * �������������Χ
	 * 
	 * @param aimList
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> randomAtt(BattleCard[] aimList)
	{
		System.err.println("������� -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		for(BattleCard battleCard:aimList)
		{
			if(battleCard!=null&&battleCard.getCurHp()>0)
				list.add(battleCard.getIndex());
		}
		int index=MathKit.randomValue(0,list.size());
		index=list.get(index);
		list.clear();
		list.add(index);
		return list;
	}

	/**
	 * ���򡪡�������Χ
	 * 
	 * @param aimCard Ŀ�����
	 * @param aimList ��������
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> endLongAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("���򹥻� -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()==0||aimCard.getIndex()==3)
		{
			if(aimList[0]!=null&&aimList[0].getCurHp()>0) list.add(0);
			if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
		}
		// else if(aimCard.getIndex()==2)
		// if(aimList[2]!=null&&aimList[2].getCurHp()>0)
		// list.add(2);
		// else
		// {
		// if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
		// if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		// }// ����������ǰ���Ա�
		else if(aimCard.getIndex()==2)
		{
			if(aimList[2]!=null&&aimList[2].getCurHp()>0)
			{
				list.add(2);
			}
		}
		else if(aimCard.getIndex()==1||aimCard.getIndex()==4)
		{
			if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
			if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		}
		return list;
	}
	/**
	 * ���򡪡�������Χ
	 * 
	 * @param aimCard Ŀ�����
	 * @param aimList ��������
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> horizontalAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("���򹥻� -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()==0||aimCard.getIndex()==1)
		{
			if(aimList[0]!=null&&aimList[0].getCurHp()>0) list.add(0);
			if(aimList[1]!=null&&aimList[1].getCurHp()>0) list.add(1);
		}
		else if(aimCard.getIndex()==2)
		{
			if(aimList[2]!=null&&aimList[2].getCurHp()>0)
			{
				list.add(2);
			}
		}
		else if(aimCard.getIndex()==3||aimCard.getIndex()==4)
		{
			if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
			if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		}
		// else if(aimCard.getIndex()<3)
		// if(aimList[2]!=null&&aimList[2].getCurHp()>0)
		// list.add(2);
		// else
		// {
		// if(aimList[3]!=null&&aimList[3].getCurHp()>0) list.add(3);
		// if(aimList[4]!=null&&aimList[4].getCurHp()>0) list.add(4);
		// }
		return list;
	}

	/**
	 * ��б����������Χ
	 * 
	 * @param aimCard Ŀ�����
	 * @param aimList ��������
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> leftInclinedleAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("��б���� -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()%2==0)
		{
			for(int i=0;i<aimList.length;i+=2)
				if(aimCard!=null&&aimCard.getCurHp()>0) list.add(i);
		}
		else
			list.add(aimCard.getIndex());
		return list;
	}

	/**
	 * ��б����������Χ
	 * 
	 * @param aimCard Ŀ�����
	 * @param aimList ��������
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> rightInclinedleAtt(BattleCard aimCard,
		BattleCard[] aimList)
	{
		System.err.println("��б���� -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(aimCard==null||aimCard.getCurHp()<=0)
		{
			for(BattleCard battleCard:aimList)
			{
				if(battleCard!=null&&battleCard.getCurHp()>0)
				{
					aimCard=battleCard;
					break;
				}
			}
		}
		if(aimCard.getIndex()==1||aimCard.getIndex()==2
			||aimCard.getIndex()==3)
		{
			for(int i=1;i<4;i++)
				if(aimCard!=null&&aimCard.getCurHp()>0) list.add(i);
		}
		else
			list.add(aimCard.getIndex());
		return list;
	}

	/**
	 * �м䡪��������Χ
	 * 
	 * @param aimList ��������
	 * @return ����Ŀ��
	 */
	public static ArrayList<Integer> mittleAtt(BattleCard[] aimList)
	{
		System.err.println("�м乥�� -------------");
		ArrayList<Integer> list=new ArrayList<Integer>();
		BattleCard aimCard=aimList[2];
		if(aimCard==null||aimCard.getCurHp()<=0)
			list=defaultAtt(aimList[0],aimList);
		else
			list.add(2);
		return list;
	}

	/**
	 * Ѫ���١���������Χ
	 * 
	 * @param aimList
	 * @return
	 */
	public static ArrayList<Integer> HpMin(BattleCard[] aimList)
	{
		System.err.println("Ѫ���ٹ��� -------------");
		BattleCard aimCard,aimCard_;
		ArrayList<Integer> list=new ArrayList<Integer>();
		int index=0;
		for(int i=0;i<aimList.length;i++)
		{
			aimCard=aimList[i];
			if(aimCard!=null&&aimCard.getCurHp()>0)
			{
				index=aimCard.getIndex();
				for(int j=i+1;j<aimList.length;j++)
				{
					aimCard_=aimList[j];
					if(aimCard_!=null&&aimCard_.getCurHp()>0
						&&aimCard.getCurHp()>aimCard_.getCurHp())
						aimCard=aimCard_;
				}
				index=aimCard.getIndex();
				break;
			}
		}
		list.add(index);
		return list;
	}

	/**
	 * ��ȡ�˺�ֵ
	 * 
	 * @param battleCard
	 * @param att �˺�ϵ��
	 * @param attType ��������
	 * @return
	 */
	public static ArrayList<DamageEntity> getAttValue(BattleCard attCard,
		ArrayList<Integer> aim,BattleCard[] aimList,BattleRecord record,
		ArrayList<DamageEntity> hurtList)
	{
		hurtList.clear();
		BattleCard aimCard=null;
		int value=0;
		boolean crit=false;
		int damageStatus=DamageEntity.DAMAGE_NORMAL;
		for(int i=0;i<aim.size();i++)
		{
			aimCard=aimList[aim.get(i)];
			value=buffValue(attCard,attCard.getAtt(),aimCard,record);
			if(isCrit(attCard))
			{
				crit=true;
				damageStatus=DamageEntity.DAMAGE_CRIT;
				value=countCritDamage(value,attCard);
			}
			else
			{
				if(isDodge(aimCard))
				{
					damageStatus=DamageEntity.DAMAGE_DODGE;
					value=0;
				}
			}
			if(value > 0){
				value=countFloatDamage(value);
				record.setAttMax(value);
			}
			DamageEntity damage = new DamageEntity(damageStatus,value);
			hurtList.add(damage);
			if(value>0)
				System.err.println("�ճ��˺� ==="+value+(crit?"�����˺�":"��ͨ�˺�"));
			else
				System.err.println("�˺�������");
		}
		return hurtList;
	}
	/**
	 * ��ù�������
	 * 
	 * @param attCard ��������
	 * @param aimList ������Ŀ������
	 * @return
	 */
	public static BattleCard getAim(BattleCard attCard,BattleCard[] aimList)
	{
		int index=attCard.getIndex();
		BattleCard aimCard=null,aimCard_1=null;
		for(int i=0;i<aimList.length;i++)
		{
			aimCard=aimList[i];
			if(aimCard!=null&&aimCard.getCurHp()>0)
			{
				if((index==1||index==4)&&aimCard.getIndex()==0)
				{
					aimCard_1=aimList[1];
					if(aimCard_1!=null&&aimCard_1.getCurHp()>0)
						return aimCard_1;
					else
						return aimCard;
				}
				else if((index==1||index==4)&&aimCard.getIndex()==3)
				{
					aimCard_1=aimList[4];
					if(aimCard_1!=null&&aimCard_1.getCurHp()>0)
						return aimCard_1;
					else
						return aimCard;
				}
				break;
			}
		}
		return aimCard;
	}

	/**
	 * ����buff�˺�ֵ
	 * 
	 * @param attCard ���ֹ�����
	 * @param att ���ι����˺�ֵ
	 * @param aimCard �˺�Ŀ��
	 * @param record ��¼
	 * @return �˺�ֵ
	 */
	private static int buffValue(BattleCard attCard,int att,
		BattleCard aimCard,BattleRecord record)
	{
		// System.err.println("����buff�˺�ֵ,��̬�Ļ���Ŷ--------------");
		if(aimCard.hadNoHurtSkill())// �жϷ��ַ��Ƿ�������״̬
		{
			return 0;
		}
		// �жϹ������Ƿ��й�״̬
		ArrayList<Skill> deSkill=attCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof IncrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		// �жϷ��ط��Ƿ��м���״̬
		deSkill=aimCard.getDeSkill();
		for(Skill skill:deSkill)
		{
			if(skill instanceof DecrHurtSkill)
			{
				att=skill.buffValue(attCard,att,aimCard,record);
				break;
			}
		}
		return att;
	}

	/**
	 * �ж��Ƿ񱩻�
	 * 
	 * @param attCard ��������
	 * @return �Ƿ񱩻�
	 */
	public static boolean isCrit(BattleCard attCard)
	{
		boolean isCrit=false;
		int critRate=attCard.getCritRate()/1000;// ������
		int randomValue=MathKit.randomValue(1,101);
		if(randomValue<critRate)
		{
			isCrit=true;
		}
		return isCrit;
	}

	/**
	 * �ж��Ƿ�����
	 * 
	 * @param defCard ���ؿ���
	 * @return �Ƿ�����
	 */
	public static boolean isDodge(BattleCard defCard)
	{
		boolean isDodge=false;
		int dodgeRate=defCard.getDodgeRate()/1000;// ������
		int randomValue=MathKit.randomValue(1,101);
		if(randomValue<dodgeRate)
		{
			isDodge=true;
		}
		return isDodge;
	}
	/**
	 * ���㱩���˺�
	 * 
	 * @param value ʵ��ֵ
	 * @param card ���ƶ���
	 * @return �����˺�ֵ
	 */
	public static int countCritDamage(int value,BattleCard card)
	{
		return value*card.getCritFactor()/1000;
	}
	/**
	 * �����˺�����ֵ(95%-105%)
	 * 
	 * @param value ʵ��ֵ
	 * @return �����˺�ֵs
	 */
	public static int countFloatDamage(int value)
	{
		int randomValue=MathKit.randomValue(95,106);
		return value*randomValue/100;
	}
}
