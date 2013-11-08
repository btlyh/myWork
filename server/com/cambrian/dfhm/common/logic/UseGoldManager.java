package com.cambrian.dfhm.common.logic;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;

/**
 * ��˵�����������ҵ���߼�������
 * 
 * @author��LazyBear
 */
public class UseGoldManager
{

	/* static fields */
	private static UseGoldManager instance=new UseGoldManager();

	/* static methods */
	public static UseGoldManager getInstance()
	{
		return instance;
	}
	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

	/** ���򱳰��ռ� */
	public void buyBagSize(int needGold,int bagSize,Player player)
	{
		String error=checkBugBagSize(needGold,bagSize,player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(needGold);
		player.getCardBag().inCapacity(bagSize);
	}

	/** ��鹺�򱳰��ռ� */
	private String checkBugBagSize(int needGold,int bagSize,Player player)
	{
		if(getBagSizePrice()!=needGold/bagSize)
		{
			return Lang.F704;
		}
		if(player.getGold()<needGold)
		{
			return Lang.F707;
		}
		return null;
	}

	/**
	 * �������
	 * 
	 * @param needGold ������
	 * @param num ��������
	 * @param player �û�����
	 */
	public void buyToken(int needGold,int num,Player player)
	{
		String error=checkBuyToken(needGold,num,player);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.decrGold(needGold);
		player.incrToken(num);
	}

	/**
	 * ��鹺�����
	 * 
	 * @param needGold ������
	 * @param num ��������
	 * @param player �û�����
	 * @return
	 */
	private String checkBuyToken(int needGold,int num,Player player)
	{
		if(player.getBuyTokenNum()<0||player.getCurToken()<0
			||player.getMaxToken()<0)
		{
			return Lang.F703;
		}
		if(player.getCurToken()==player.getMaxToken())
		{
			return Lang.F706;
		}
		int todayBuyNum=player.getBuyTokenNum();
		if(todayBuyNum>=getTimesForVip(player.getVipLevel()))
		{
			return Lang.F705;
		}
		if(getTokenSinglePrice(todayBuyNum)!=needGold/num)
		{
			return Lang.F704;
		}
		if(player.getGold()<needGold)
		{
			return Lang.F702;
		}
		return null;
	}

	/** ��ù��򱳰��ļ۸� */
	private int getBagSizePrice()
	{
		// �����ȡ���򱳰��ļ۸񣬵Ȳ߻���ֵ������������
		return 20;
	}

	/**
	 * ���ڻ�ȡ����۸�ÿ��������û���ֳ����ݣ�����������ã��Ȳ߻��������ݣ����ݲ߻���ʵ���������ã�
	 * 
	 * @param alreadyBuyNum �Ѿ���������
	 * @return ���ص�������۸�
	 */
	private int getTokenSinglePrice(int alreadyBuyNum)
	{
		switch(alreadyBuyNum>0?(alreadyBuyNum>10?(alreadyBuyNum>20
			?(alreadyBuyNum>40?4:3):2):1):1)
		{
			case 2:
				return 10;
			case 3:
				return 20;
			case 4:
				return 40;
			default:
				return 5;
		}
	}

	/**
	 * ����VIP�ȼ���ö��⹺�����������û���ֳ����ݣ�����������ã��Ȳ߻��������ݣ����ݲ߻���ʵ���������ã�
	 * 
	 * @param vipLevel VIP�ȼ�
	 * @return ���ض�Ĺ������
	 */
	private int getTimesForVip(int vipLevel)
	{
		switch(vipLevel)
		{
			case 0:
			case 1:
				return 5;
			case 2:
				return 10;
			case 3:
			case 4:
			case 5:
				return 20;
			default:
				return 0;
		}
	}
}