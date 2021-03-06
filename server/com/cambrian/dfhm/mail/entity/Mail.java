package com.cambrian.dfhm.mail.entity;

import java.util.ArrayList;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;

/**
 * 类说明：邮件类
 * 
 * @author：LazyBear
 */
public class Mail
{

	/* static fields */
	/** 邮件到期的毫秒数 */
	public static final long DELETETIME=TimeKit.DAY_MILLS*7;
	/** 邮件状态类型 0未读,1已读未收附件,2已读已收取附件,3剩余邮件,4打回去邮件 */
	public static final int MAILSTATE_UNREAD=0,MAILSTATE_READ_UNGET=1,
					MAILSTATE_READ_GET=2;
	/** 一次性取出的邮件数量上限 */
	public static final int MAIL_SIZE_CONFINE=20;
	/** 邮件附件最小数量 */
	/** 邮件内容上限 */
	public static final int MAIL_ANNEX_MINNUM=1;
	public static final int MAIL_CONTENT_CONFINE=120;
	/* static methods */

	/* fields */
	/** 邮件ID (唯一ID) */
	private int mailId;
	/** 邮件状态 */
	private int state;
	/** 邮件发送时间 */
	private String sendTime;
	/** 邮件标题 */
	private String title;
	/** 邮件内容 */
	private String content;
	/** 邮件附件-卡牌列表 */
	private ArrayList<Integer> cardList=new ArrayList<Integer>();
	/** 邮件附件-RMB */
	private int gold;
	/** 邮件附件-银币 */
	private int money;
	/** 邮件附件-军令 */
	private int token;
	/** 邮件附件-武魂 */
	private int soulPoint;
	/** 邮件附件-积分 */
	private int normalPoint;
	/** 邮件-发件人 */
	private String sendPalyerName;
	/** 邮件是否是战斗邮件 */
	private boolean isFight=false;
	/** 是否是系统邮件*/
	private boolean isSystem;

	/* constructors */

	public Mail()
	{
	}

	/**
	 * 玩家之间邮件 的构造方法
	 * 
	 * @param state 邮件状态
	 * @param title 邮件标题
	 * @param content 邮件内容
	 */
	public Mail(int mailId,int state,String title,String content,
		String sendPlayerName)
	{
		this.mailId=mailId;
		this.state=state;
		this.title=title;
		this.content=content;
		this.gold=0;
		this.money=0;
		this.token=0;
		this.soulPoint=0;
		this.normalPoint=0;
		this.sendPalyerName=sendPlayerName;
		this.sendTime=TimeKit.dateToString(TimeKit.nowTimeMills(),
			"yyyy年MM月dd日 HH:mm");
		isSystem=false;

	}

	/**
	 * 系统给玩家发送的邮件
	 * 
	 * @param state 状态
	 * @param title 标题
	 * @param content 内容
	 * @param cards 卡牌列表
	 * @param gold 软妹币
	 * @param money 游戏币
	 * @param token 军令
	 * @param soulPoint 武魂
	 * @param normalPoint 积分
	 */
	public Mail(int mailId,int state,String title,String content,
		ArrayList<Integer> cards,int gold,int money,int token,int soulPoint,
		int normalPoint,String sendPlayerName)
	{
		this.mailId=mailId;
		this.state=state;
		this.title=title;
		this.content=content;
		this.gold=gold;
		this.money=money;
		this.token=token;
		this.soulPoint=soulPoint;
		this.normalPoint=normalPoint;
		cardList.addAll(cards);
		this.sendPalyerName=sendPlayerName;
		this.sendTime=TimeKit.dateToString(TimeKit.nowTimeMills(),"yyyy年MM月dd日 HH:mm");;
		isSystem=true;
	}

	/* properties */

	public long getMailId()
	{
		return mailId;
	}

	public void setMailId(int mailId)
	{
		this.mailId=mailId;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state=state;
	}

	public String getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(String sendTime)
	{
		this.sendTime=sendTime;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title=title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content=content;
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
		this.gold=gold;
	}

	public int getMoney()
	{
		return money;
	}

	public void setMoney(int money)
	{
		this.money=money;
	}

	public int getToken()
	{
		return token;
	}

	public void setToken(int token)
	{
		this.token=token;
	}

	public int getSoulPoint()
	{
		return soulPoint;
	}

	public void setSoulPoint(int soulPoint)
	{
		this.soulPoint=soulPoint;
	}

	public int getNormalPoint()
	{
		return normalPoint;
	}

	public void setNormalPoint(int normalPoint)
	{
		this.normalPoint=normalPoint;
	}

	public String getSendPalyerName()
	{
		return sendPalyerName;
	}

	public void setSendPalyerName(String sendPalyerName)
	{
		this.sendPalyerName=sendPalyerName;
	}

	public boolean isFight()
	{
		return isFight;
	}

	public void setFight(boolean isFight)
	{
		this.isFight=isFight;
	}

	public boolean isSystem()
	{
		return isSystem;
	}

	public void setSystem(boolean isSystem)
	{
		this.isSystem = isSystem;
	}

	/** 添加卡牌 */
	public void addCard(int cardId)
	{
		cardList.add(cardId);
	}

	/** 获取卡牌列表的字符存，用于存储 */
	public String getCardListStr()
	{
		StringBuilder cardListStr=new StringBuilder("");
		for(Integer i:cardList)
		{
			cardListStr.append(i).append("/");
		}
		return cardListStr.toString();
	}

	/* init start */

	/* methods */
	/** 序列化 前台接收邮件数据 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(mailId);
		data.writeInt(state);
		data.writeUTF(sendTime);
		data.writeUTF(title);
		data.writeUTF(content);
		data.writeInt(cardList.size());
		for(int i=0;i<cardList.size();i++)
		{
			data.writeInt(cardList.get(i));// 卡牌的小头像，先发这个，等和策划了确认后再进行更改
		}
		data.writeInt(gold);
		data.writeInt(money);
		data.writeInt(token);
		data.writeInt(soulPoint);
		data.writeInt(normalPoint);
		data.writeUTF(sendPalyerName);
		data.writeBoolean(isFight);
		data.writeBoolean(isSystem);
	}

	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(mailId);
		data.writeInt(state);
		data.writeUTF(sendTime);
		data.writeUTF(title);
		data.writeUTF(content);
		data.writeInt(cardList.size());
		for(int i=0;i<cardList.size();i++)
		{
			data.writeInt(cardList.get(i));
		}
		data.writeInt(gold);
		data.writeInt(money);
		data.writeInt(token);
		data.writeInt(soulPoint);
		data.writeInt(normalPoint);
		data.writeUTF(sendPalyerName);
		data.writeBoolean(isFight);
		data.writeBoolean(isSystem);
	}

	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		this.mailId=data.readInt();
		this.state=data.readInt();
		this.sendTime=data.readUTF();
		this.title=data.readUTF();
		this.content=data.readUTF();
		int len=data.readInt();
		for(int i=0;i<len;i++)
		{
			this.cardList.add(data.readInt());
		}
		this.gold=data.readInt();
		this.money=data.readInt();
		this.token=data.readInt();
		this.soulPoint=data.readInt();
		this.normalPoint=data.readInt();
		this.sendPalyerName=data.readUTF();
		this.isFight=data.readBoolean();
		this.isSystem=data.readBoolean();
	}

	/**
	 * 判断此邮件是否有附件
	 * 
	 * @return 真或假
	 */
	public boolean isHaveAnnex()
	{
		boolean isHave=false;
		if(cardList.size()>0||gold>0||money>0||token>0||soulPoint>0
						||normalPoint>0)
		{
			isHave=true;
		}
		return isHave;
	}
}
