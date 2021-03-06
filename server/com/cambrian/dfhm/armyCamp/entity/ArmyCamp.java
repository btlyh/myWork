package com.cambrian.dfhm.armyCamp.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.game.Session;

/**
 * 类说明：军帐
 * 
 * @author：LazyBear
 */
public class ArmyCamp
{

	/* static fields */

	/** 醉酒冷却时间 */
	public static final long DRINK_CD = TimeKit.MIN_MILLS * GameCFG.getDrinkCd();
	
	public static int INIT_PUBLICSEATSIZE = 1, INIT_PRIVATESEATSIZE = 3;
	
	public static long SOUP_CDTIME = TimeKit.MIN_MILLS * GameCFG.getAwakeSoupCd();
	/* static methods */

	/* fields */
	/** 公共座位大小 */
	private int publicSeatSize = INIT_PUBLICSEATSIZE;
	/** 私人座位大小 */
	private int privateSeatSize = INIT_PRIVATESEATSIZE;
	/** 公共座位集合 */
	private List<SeatCard> publicList = new ArrayList<SeatCard>();
	/** 私人座位集合 */
	private List<SeatCard> privateList = new ArrayList<SeatCard>();
	/** 历史信息记录集合 */
	private ArrayList<String> historyLogList = new ArrayList<String>();
	/** 上次使用醒酒汤的时间 */
	private long lastUseAwakeSoupTime = 0;
	/** 上次闲逛 */
	private Session walkSession;

	/* constructors */

	/* properties */
	public int getPublicSeatSize()
	{
		return publicSeatSize;
	}

	public void setPublicSeatSize(int publicSeatSize)
	{
		this.publicSeatSize = publicSeatSize;
	}

	public int getPrivateSeatSize()
	{
		return privateSeatSize;
	}

	public void setPrivateSeatSize(int privateSeatSize)
	{
		this.privateSeatSize = privateSeatSize;
	}

	public List<SeatCard> getPublicList()
	{
		return publicList;
	}

	public void setPublicList(List<SeatCard> publicList)
	{
		this.publicList = publicList;
	}

	public List<SeatCard> getPrivateList()
	{
		return privateList;
	}

	public void setPrivateList(List<SeatCard> privateList)
	{
		this.privateList = privateList;
	}

	public ArrayList<String> getHistoryLogList()
	{
		return historyLogList;
	}

	public void setHistoryLogList(ArrayList<String> historyLogList)
	{
		this.historyLogList = historyLogList;
	}

	public long getLastUseAwakeSoupTime()
	{
		return lastUseAwakeSoupTime;
	}

	public void setLastUseAwakeSoupTime(long lastUseAwakeSoupTime)
	{
		this.lastUseAwakeSoupTime = lastUseAwakeSoupTime;
	}

	/* init start */

	/* methods */

	public Session getWalkSession()
	{
		return walkSession;
	}

	public void setWalkSession(Session walkSession)
	{
		this.walkSession = walkSession;
	}

	/** 添加历史信息记录 */
	public void addHistoryLog(String log)
	{
		if (historyLogList == null)
		{
			historyLogList = new ArrayList<String>();
		}
		if (historyLogList.size() > GameCFG.getInformationSize())
		{
			historyLogList.remove(0);
		}
		historyLogList.add(log);
	}

	/** 通过uid获取SeatCard */
	public SeatCard getSeatCardById(int cardUid)
	{
		for (SeatCard seatCard : privateList)
		{
			if (seatCard.getCardUid() == cardUid)
				return seatCard;
		}
		for (SeatCard seatCard : publicList)
		{
			if (seatCard.getCardUid() == cardUid)
				return seatCard;
		}
		return null;
	}

	/** 通过座位号获取SeatCard */
	public SeatCard getSeatCardBySeatId(int seatId)
	{
		for (SeatCard seatCard : privateList)
		{
			if (seatCard.getSeatId() == seatId)
				return seatCard;
		}
		for (SeatCard seatCard : publicList)
		{
			if (seatCard.getSeatId() == seatId)
				return seatCard;
		}
		return null;
	}

	/** 从座位上移除卡牌 */
	public void removeSeatCard(SeatCard seatCard)
	{
		if (privateList.contains(seatCard))
			privateList.remove(seatCard);
		else if (publicList.contains(seatCard))
			publicList.remove(seatCard);
	}
	
	/** 序列化 和前台通信 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(publicSeatSize);
		data.writeInt(privateSeatSize);
		data.writeInt(publicList.size());
		for (SeatCard seatCard : publicList)
		{
			seatCard.bytesWrite(data);
		}
		data.writeInt(privateList.size());
		for (SeatCard seatCard : privateList)
		{
			seatCard.bytesWrite(data);
		}
		data.writeLong(TimeKit.nowTimeMills() - lastUseAwakeSoupTime);
	}

	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(publicSeatSize);
		data.writeInt(privateSeatSize);
		data.writeInt(publicList.size());
		for (SeatCard seatCard : publicList)
		{
			seatCard.dbBytesWrite(data);
		}
		data.writeInt(privateList.size());
		for (SeatCard seatCard : privateList)
		{
			seatCard.dbBytesWrite(data);
		}
		data.writeInt(historyLogList.size());
		for (String string : historyLogList)
		{
			data.writeUTF(string);
		}
		data.writeLong(lastUseAwakeSoupTime);
	}

	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		this.publicSeatSize = data.readInt();
		this.privateSeatSize = data.readInt();
		int len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			SeatCard seatCard = new SeatCard();
			seatCard.dbBytesRead(data);
			publicList.add(seatCard);
		}
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			SeatCard seatCard = new SeatCard();
			seatCard.dbBytesRead(data);
			privateList.add(seatCard);
		}
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			historyLogList.add(data.readUTF());
		}
		lastUseAwakeSoupTime = data.readLong();
	}
}
