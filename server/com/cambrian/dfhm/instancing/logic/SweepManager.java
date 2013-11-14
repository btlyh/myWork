package com.cambrian.dfhm.instancing.logic;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.Sweep;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.util.MailFactory;

public class SweepManager {

	/* static fields */
	private static SweepManager instance = new SweepManager();

	/* static methods */
	public static SweepManager getInstance() {
		return instance;
	}

	private MailFactory mf;

	public void setMailFactory(MailFactory mf) {
		instance.mf = mf;
	}

	public void sweepMap(Player player, int mapId, int sweepNum, int maptype,
			ByteBuffer data) {
		String error = checkSweep(player, mapId, sweepNum, maptype);
		if (error != null) {
			throw new DataAccessException(601, error);
		}

		Sweep sweep = new Sweep();
		//����MAP ID�� sweepID  �ͻ���һ�� ��������ʵ�ֳ�2������ ����  SWEEP ID��Ҫ  ��MAPID ��10000dedao
		sweep = (Sweep) Sample.getFactory().getSample(mapId+10000);
		for (int i = 0; i < sweepNum; i++) // ���ж��ٴ�ɨ��
		{
			int money = MathKit.randomValue(sweep.getMoneymin(),
					sweep.getMoneymax() + 1);
			int gold = MathKit.randomValue(sweep.getGoldmin(),
					sweep.getGoldmax() + 1);
			int soul = MathKit.randomValue(sweep.getSoulmin(),
					sweep.getSoulmax() + 1);
			player.incrMoney(money);
			player.incrGold(gold);
			player.incrSoul(soul);
			data.writeInt(gold);
			data.writeInt(money); 
			data.writeInt(soul);

			int[] awardCard = sweep.getCard();
			ArrayList<Integer> cards = new ArrayList<Integer>();//
		//	data.writeInt(awardCard.length);
			ArrayList<Integer>recard = new ArrayList<Integer>();
			for (int j = 0; j < awardCard.length / 3; j++) // ���ݿ��Ƹ������
			{
				int cardid = awardCard[j * 3];// ����ID
				int per = awardCard[j * 3 + 1];// ���伸��
				int maxNum = awardCard[j * 3 + 2];// ��������
				int cardnum = 0;// ���Ƶ��������

				for (int k = 0; k < maxNum; k++) {
					int randvalue = MathKit.randomValue(0, 100000);

					if (randvalue < per) {
						cardnum++;
					}
				}

				if (cardnum != 0)// ��������Ŀ����Լ�����ȡ����
				{
					recard.add(cardid);
					recard.add(cardnum);
				}

			}

			data.writeInt(recard.size()/2);

			if (recard.size() != 0) {
				for (int j = 0; j < recard.size()/2; j++)// ��ǰ̨���ػ�õĿ��Ƶ����������
				{

					if (player.getCardBag().getSurplusCapacity() < recard.get(j*2+1))// ������������
					{

						for (int j2 = 0; j2 <  recard.get(j*2+1); j2++)// ���ſ��Ƶ�ʱ��
																	// ��Ҫ���ʼ����
						{
							cards.add( recard.get(j*2));
						}

						Mail mail = mf.createSystemMail(cards, 0, 0, 0, 0, 0,
								(int) player.getUserId());
						player.addMail(mail);
					} else {
						for (int j2 = 0; j2 <  recard.get(j*2+1); j2++)// ���ſ��Ƶ�ʱ��
																	// �����Ҫ��λ�ȡ����
						{
							player.getCardBag().add( recard.get(j*2));
						}
					}
					data.writeInt( recard.get(j*2));
					data.writeInt( recard.get(j*2+1));
				}
			}
		}
	}

	private String checkSweep(Player player, int mapId, int sweepNum,
			int maptype) {

		if (maptype == 1) {
			if (player.getCurIndexForNormalNPC() <= mapId) {
				return Lang.F1411;
			}
		} else if (maptype == 3) {
			if (player.getCurIndexForCorssNPC() <= mapId) {
				return Lang.F1411;
			}
		} else {
			return Lang.F1412;
		}

		if (player.getCurToken() < sweepNum) {
			return Lang.F1413;
		}

		return null;
	}

}
