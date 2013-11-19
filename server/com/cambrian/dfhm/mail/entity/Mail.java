package com.cambrian.dfhm.mail.entity;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;

/**
 * ��˵�����ʼ���
 * 
 * @author��LazyBear
 * 
 */
public class Mail
{
	/* static fields */
	/** �ʼ����ڵĺ����� */
	public static final long DELETETIME = TimeKit.DAY_MILLS * 7;
	/** �ʼ�״̬���� δ�� ,�Ѷ�δ�ո���,�Ѷ�����ȡ���� */
	public static final int MAILSTATE_UNREAD = 0, MAILSTATE_READ_UNGET = 1, MAILSTATE_READ_GET = 2;
	/** һ����ȡ�����ʼ��������� */
	public static final int MAIL_SIZE_CONFINE = 20;
	/** �ʼ�������С���� */
	/** �ʼ��������� */
	public static final int MAIL_ANNEX_MINNUM = 1;
	public static final int MAIL_CONTENT_CONFINE = 120;
	/* static methods */

	/* fields */
	/** �ʼ�ID (ΨһID) */
	private int mailId;
	/** �ʼ�״̬ */
	private int state;
	/** �ʼ�����ʱ�� ������ */
	private long sendTime;
	/** �ʼ����� */
	private String title;
	/** �ʼ����� */
	private String content;
	/** �ʼ�����-�����б� */
	private ArrayList<Integer> cardList = new ArrayList<Integer>();
	/** �ʼ�����-RMB */
	private int gold;
	/** �ʼ�����-���� */
	private int money;
	/** �ʼ�����-���� */
	private int token;
	/** �ʼ�����-��� */
	private int soulPoint;
	/** �ʼ�����-���� */
	private int normalPoint;
	/** �ʼ�-������ */
	private String sendPalyerName;

	/* constructors */
	
	public Mail()
	{
	}

	/**
	 * ���֮���ʼ� �Ĺ��췽��
	 * 
	 * @param state
	 *            �ʼ�״̬
	 * @param title
	 *            �ʼ�����
	 * @param content
	 *            �ʼ�����
	 */
	public Mail(int mailId, int state, String title, String content, String sendPlayerName)
	{
		this.mailId = mailId;
		this.state = state;
		this.title = title;
		this.content = content;
		this.gold = 0;
		this.money = 0;
		this.token = 0;
		this.soulPoint = 0;
		this.normalPoint = 0;
		this.sendPalyerName = sendPlayerName;
	}

	/**
	 * ϵͳ����ҷ��͵��ʼ�
	 * 
	 * @param state
	 *            ״̬
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * @param cards
	 *            �����б�
	 * @param gold
	 *            ���ñ�
	 * @param money
	 *            ��Ϸ��
	 * @param token
	 *            ����
	 * @param soulPoint
	 *            ���
	 * @param normalPoint
	 *            ����
	 */
	public Mail(int mailId, int state, String title, String content, ArrayList<Integer> cards, int gold, int money, int token, int soulPoint,
			int normalPoint, String sendPlayerName)
	{
		this.mailId = mailId;
		this.state = state;
		this.title = title;
		this.content = content;
		this.gold = gold;
		this.money = money;
		this.token = token;
		this.soulPoint = soulPoint;
		this.normalPoint = normalPoint;
		cardList.addAll(cards);
		this.sendPalyerName = sendPlayerName;
	}

	/* properties */

	public long getMailId()
	{
		return mailId;
	}

	public void setMailId(int mailId)
	{
		this.mailId = mailId;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public long getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(long sendTime)
	{
		this.sendTime = sendTime;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public ArrayList<Integer> getCardList()
	{
		return cardList;
	}

	public int getGold()
	{
		return gold;
	}

	public void setGold(int gold)
	{
		this.gold = gold;
	}

	public int getMoney()
	{
		return money;
	}

	public void setMoney(int money)
	{
		this.money = money;
	}

	public int getToken()
	{
		return token;
	}

	public void setToken(int token)
	{
		this.token = token;
	}

	public int getSoulPoint()
	{
		return soulPoint;
	}

	public void setSoulPoint(int soulPoint)
	{
		this.soulPoint = soulPoint;
	}

	public int getNormalPoint()
	{
		return normalPoint;
	}

	public void setNormalPoint(int normalPoint)
	{
		this.normalPoint = normalPoint;
	}

	public String getSendPalyerName()
	{
		return sendPalyerName;
	}

	public void setSendPalyerName(String sendPalyerName)
	{
		this.sendPalyerName = sendPalyerName;
	}

	/** ��ӿ��� */
	public void addCard(int cardId)
	{
		cardList.add(cardId);
	}

	/** ��ȡ�����б���ַ��棬���ڴ洢 */
	public String getCardListStr()
	{
		StringBuilder cardListStr = new StringBuilder("");
		for (Integer i : cardList)
		{
			cardListStr.append(i).append("/");
		}
		return cardListStr.toString();
	}

	/* init start */

	/* methods */
	/** ���л� ǰ̨�����ʼ����� */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(mailId);
		data.writeInt(state);
		data.writeLong(sendTime);
		data.writeUTF(title);
		data.writeUTF(content);
		data.writeInt(cardList.size());
		for (int i = 0; i < cardList.size(); i++)
		{
			data.writeInt(cardList.get(i));// ���Ƶ�Сͷ���ȷ�������ȺͲ߻���ȷ�Ϻ��ٽ��и���
		}
		data.writeInt(gold);
		data.writeInt(money);
		data.writeInt(token);
		data.writeInt(soulPoint);
		data.writeInt(normalPoint);
		data.writeUTF(sendPalyerName);
	}

	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(mailId);
		data.writeInt(state);
		data.writeLong(sendTime);
		data.writeUTF(title);
		data.writeUTF(content);
		data.writeInt(cardList.size());
		for (int i = 0; i < cardList.size(); i++)
		{
			data.writeInt(cardList.get(i));
		}
		data.writeInt(gold);
		data.writeInt(money);
		data.writeInt(token);
		data.writeInt(soulPoint);
		data.writeInt(normalPoint);
		data.writeUTF(sendPalyerName);
	}

	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		this.mailId = data.readInt();
		this.state = data.readInt();
		this.sendTime = data.readLong();
		this.title = data.readUTF();
		this.content = data.readUTF();
		int len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			this.cardList.add(data.readInt());
		}
		this.gold = data.readInt();
		this.money = data.readInt();
		this.token = data.readInt();
		this.soulPoint = data.readInt();
		this.normalPoint = data.readInt();
		this.sendPalyerName = data.readUTF();
	}

}
