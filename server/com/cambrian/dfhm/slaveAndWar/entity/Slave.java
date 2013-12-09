package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;
import com.cambrian.dfhm.slaveAndWar.notice.EventMessageNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：马仔类-，-
 * 
 * @author:LazyBear
 */
public class Slave implements Comparable<Slave>
{

	/* static fields */
	/** 奴隶状态 1空闲 2办事中 */
	public static final int STATUS_FREE=1,STATUS_WORK=2;
	/* static methods */

	/* fields */
	/** 马仔ID */
	private int userId;
	/** 马仔名字 */
	private String name;
	/** 马仔状态 */
	private int status;
	/** 马仔战力 */
	private int fightPoint;
	/** 马仔开始办事时间 */
	private long startWorkTime;
	/** 被捕获时间 */
	private long takeTime;
	/** 是否被托管 */
	private boolean isManaged;
	/** 土豪ID */
	private int bossId;

	/* constructors */
	/**
	 * 马仔构造方法（创建新的马仔）
	 * 
	 * @param name 马仔姓名
	 * @param fightPoint 战斗力
	 */
	public Slave(String name,int fightPoint,int bossId,int userId)
	{
		this.userId=userId;
		this.name=name;
		this.fightPoint=fightPoint;
		this.takeTime=TimeKit.nowTimeMills();
		this.bossId=bossId;
		this.status=STATUS_FREE;
	}

	public Slave()
	{

	}

	/* properties */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status=status;
	}

	public int getFightPoint()
	{
		return fightPoint;
	}

	public void setFightPoint(int fightPoint)
	{
		this.fightPoint=fightPoint;
	}

	public long getStartWorkTime()
	{
		return startWorkTime;
	}

	public void setStartWorkTime(long lastWorkTime)
	{
		this.startWorkTime=lastWorkTime;
	}

	public long getTakeTime()
	{
		return takeTime;
	}

	public void setTakeTime(long takeTime)
	{
		this.takeTime=takeTime;
	}

	public boolean isManaged()
	{
		return isManaged;
	}

	public void setManaged(boolean isManaged)
	{
		this.isManaged=isManaged;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId=userId;
	}

	public int getBossId()
	{
		return bossId;
	}

	public void setBossId(int bossId)
	{
		this.bossId=bossId;
	}

	/* init start */

	/* methods */
	/** 序列化 和前台通信 写 */
	public void BytesWrite(ByteBuffer data)
	{
		data.writeInt(userId);
		data.writeUTF(name);
		data.writeInt(status);
		data.writeInt(fightPoint);
		data.writeLong(getHowLongForWork());
		data.writeBoolean(isManaged);
		data.writeLong(GameCFG.getSlaveKeepTime()*TimeKit.HOUR_MILLS
			-(TimeKit.nowTimeMills()-takeTime));
	}
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(userId);
		data.writeUTF(name);
		data.writeInt(status);
		data.writeInt(fightPoint);
		data.writeLong(startWorkTime);
		data.writeLong(takeTime);
		data.writeBoolean(isManaged);
	}

	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		userId=data.readInt();
		name=data.readUTF();
		status=data.readInt();
		fightPoint=data.readInt();
		startWorkTime=data.readLong();
		takeTime=data.readLong();
		isManaged=data.readBoolean();
	}

	/**
	 * 重写比较方法(按照战力降序)
	 */
	public int compareTo(Slave slave)
	{
		int i=this.fightPoint;
		int j=slave.getFightPoint();
		return ((i==j)?0:(i>j)?-1:1);
	}

	/**
	 * 易主初始化
	 */
	public void changeHandInit(int bossId)
	{
		this.status=STATUS_FREE;
		this.startWorkTime=0;
		this.takeTime=TimeKit.nowTimeMills();
		this.isManaged=false;
		this.bossId=bossId;
	}

	/**
	 * 办事结束处理
	 */
	public void workEndHandle()
	{
		setStatus(STATUS_FREE);
		setStartWorkTime(0);
		setManaged(false);
	}

	/**
	 * 办事开始处理
	 */
	public void workStartHandle()
	{
		setStatus(STATUS_WORK);
		setStartWorkTime(TimeKit.nowTimeMills());
	}

	/**
	 * 获取还需办事时间
	 * 
	 * @return
	 */
	public long getHowLongForWork()
	{
		long surplusTime=0;
		if(startWorkTime>0)
		{
			surplusTime=(startWorkTime+TimeKit.MIN_MILLS
				*GameCFG.getWorkTime())
				-TimeKit.nowTimeMills();
			if(surplusTime<0)
			{
				surplusTime=0;
			}
		}
		return surplusTime;
	}

	/**
	 * 马仔自由事件处理
	 * 
	 * @param ds 内存数据对象
	 * @param dao 数据库数据对象
	 */
	public void beFreeHandle(DataServer ds,SlaveAndWarDao dao,
		EventMessageNotice emn)
	{
		Player slavePlayer;
		Player friendPlayer;
		Session session=ds.getSession(userId);
		if(session!=null)
		{
			slavePlayer=(Player)session.getSource();
		}
		else
		{
			slavePlayer=dao.getPlayer(userId);
		}
		slavePlayer.becomeFreeMan();
		if(status==STATUS_WORK) // 工作状态下
		{
			competRemove(ds,dao,slavePlayer,emn);
		}
		if(session==null) dao.savePlayerVar(slavePlayer);
		List<Integer> friendIds=slavePlayer.getFriendInfo().getFriendList();
		for(Integer integer:friendIds)
		{
			Session friendSession=ds.getSession(integer);
			if(friendSession!=null)
			{
				friendPlayer=(Player)friendSession.getSource();
			}
			else
			{
				friendPlayer=dao.getPlayer(integer);
			}
			friendPlayer.getIdentity().getHelpList()
				.remove(new Integer(userId));
			if(friendSession==null) dao.savePlayerVar(friendPlayer);
		}
	}

	/**
	 * 强制移除奴隶事件处理
	 * 
	 * @param ds 内存数据对象
	 * @param dao 数据库对象
	 * @param slavePlayer 奴隶玩家
	 */
	public void competRemove(DataServer ds,SlaveAndWarDao dao,
		Player slavePlayer,EventMessageNotice emn)
	{
		Player ownerPlayer;
		int money=(int)((TimeKit.nowTimeMills()-startWorkTime)/60000*fightPoint);
		Session session=ds.getSession(bossId);
		if(session!=null)
		{
			ownerPlayer=(Player)session.getSource();
		}
		else
		{
			ownerPlayer=dao.getPlayer(bossId);
		}
		ownerPlayer.incrMoney(money);
		Information.CreatandSave(ownerPlayer.getIdentity(),
			slavePlayer.getNickname(),"",Information.TYPE_WORKDONE,
			Information.EVENT_SUCCESS,money);
		Information.CreatandSave(slavePlayer.getIdentity(),
			ownerPlayer.getNickname(),"",Information.TYPE_WORKDONE,
			Information.EVENT_SUCCESS,money);
		SlaveManager.getInstance().slavePool.remove(this);
		this.workEndHandle();
		if(session==null)
			dao.savePlayerVar(ownerPlayer);
		else
			emn.send(session,new Object[]{money});
	}
}
