package com.cambrian.dfhm.common.logic;

import java.util.ArrayList;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Gift;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.util.MailFactory;

/**
 * ��˵������������������ҵ���߼�������
 * 
 * @author��Zmk
 */
public class UsePointsManager
{

	/* static fields */
	private static UsePointsManager instance=new UsePointsManager();

	/* static methods */
	public static UsePointsManager getInstance()
	{
		return instance;
	}

	/* fields */
	MailFactory mf;

	/* constructors */
	public void setMf(MailFactory mf)
	{
		instance.mf=mf;
	}
	/* properties */

	/* init start */

	/* methods */
	/**
	 * ���������ֶһ�
	 * 
	 * @param player ��ǰ���
	 * @param needPoint ��Ҫ���ĵĻ���
	 * @return �һ��õ��Ľ�Ʒresult,result[0]Ϊ���ͣ�result[1]Ϊ������ID
	 */
	public int[] arenaGift(Player player,int needPoint)
	{
		String error=checkArenaGift(player,needPoint);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		Gift gift=(Gift)Sample.factory.getSample(Gift.SID);
		int[] result=gift.dispense();
		if(result[0]==Gift.TYPE_MONEY)
		{
			player.incrMoney(result[1]);
		}
		else if(result[0]==Gift.TYPE_SOUL)
		{
			player.incrSoul(result[1]);
		}
		else if(result[0]==Gift.TYPE_GOLD)
		{
			player.incrGold(result[1]);
		}
		else
		{
			if(player.getCardBag().getSurplusCapacity()<1)
			{
				ArrayList<Integer> cards=new ArrayList<Integer>();
				cards.add(result[0]);
				Mail mail=mf.createSystemMail(cards,0,0,0,0,0,
					(int)player.getUserId());
				player.addMail(mail);
			}
			else
			{
				Card card=player.getCardBag().add(result[0]);
				result[1]=card.getId();
			}

		}
		return result;
	}
	/** �����ֶһ� */
	private String checkArenaGift(Player player,int needPoint)
	{
		if(getNeedPoint()!=needPoint)
		{
			return Lang.F708;
		}
		if(player.getPlayerInfo().getNormalPoint()<needPoint)
		{
			return Lang.F709;
		}
		return null;
	}
	/** �����Ҫ���ĵĻ��� */
	private int getNeedPoint()
	{
		// TODO ��ֵû�У���ʱд0
		return 0;
	}
}