package com.cambrian.dfhm.task;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.StringKit;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.task.premise.ProxyTaskPremise;
import com.cambrian.dfhm.task.premise.TaskPremise;

/**
 * ��˵��������
 * 
 * @version 1.0
 * @date 2013-5-30
 * @author maxw<woldits@qq.com>
 */
public class Task extends Sample
{
	/* static fields */
	/** ����״̬ (δ��,������,���) */
	public static final int INIT=0,TAKED=1,FINISH=2;
	/** ��������(�Զ�������,���ظ�,���������Զ��Ƴ�) */
	public static final int AUTOTAKE=1,REPEAT=2,AUTODROP=4;

	/* static methods */

	/* fields */
	/** ����  */
	int type;
	/** ���� */
	int attribute;
	/** ����������  */
	TaskPremise[] takepremise;
	/** ���������Ҫ�ﵽ������ */
	TaskPremise[] achievepremise;
	/** ����{{����,ֵ,ֵ,...},{����,...},...}  */
	int[][] reward;
	
	/** ״̬  */
	int status;
	/** ������ʱ�� */
	public long begintime;
	/** ���ʱ��  */
	public long finishtime;
	/** ��ɴ��� */
	public int finishcount;
	
	/* constructors */

	/* properties */
	/** ����������{{����,ֵ,ֵ,...},{����,...},...}  */
	public void setTakePremise(String str)
	{
		int[][] values=StringKit.parseIntss(str);
		takepremise=new TaskPremise[values.length];
		for(int i=0;i<values.length;i++)
		{
			takepremise[i]=ProxyTaskPremise.newTaskPremise(values[i],getSid());
		}
	}
	/** ���������Ҫ�ﵽ������{{����,ֵ,ֵ,...},{����,...},...}  */
	public void setAchievePremise(String str)
	{
		int[][] values=StringKit.parseIntss(str);
		achievepremise=new TaskPremise[values.length];
		for(int i=0;i<values.length;i++)
		{
			achievepremise[i]=ProxyTaskPremise.newTaskPremise(values[i],getSid());
		}
	}
	
	/* init start */

	/* methods */
	/**  */
	private boolean executeTakepremise(Player player)
	{
		boolean b = true;
		for(int i=0;i<takepremise.length;i++)
		{
			b = takepremise[i].execute(player);
			if(!b)
				return b;
		}
		return b;
	}
	/**  */
	private boolean executeAchievepremise(Player player)
	{
		boolean b = true;
		for(int i=0;i<achievepremise.length;i++)
		{
			b = achievepremise[i].execute(player);
			if(!b)
				return b;
		}
		return b;
	}
	/** �Ƿ���ڴ����� */
	public boolean isAttribute(int attribute)
	{
		return (this.attribute&attribute)==attribute;
	}
	/** �Ƿ���� */
	public boolean isFinish()
	{
		return (status==FINISH);
	}
	/** ����Ƿ���Խ����� */
	public boolean checkTake(Player player)
	{
		boolean b = true;
		if(!isAttribute(REPEAT))
		{
//			b = player.task.isContain(getSid());
//			if(b)
//				return b;
		}
			
		for(int i=0;i<takepremise.length;i++)
		{
			b = takepremise[i].isAchieve(player);
			if(!b)
				return b;
		}
		return b;
	}
	/** ������ */
	public boolean take(Player player)
	{
		if(this.status!=INIT) return false;
		if(!checkTake(player)) return false;
		if(!executeTakepremise(player)) return false;
		this.begintime=System.currentTimeMillis();
		this.finishtime=0;
		this.status=TAKED;
		return true;
	}
	/** ����Ƿ����������� */
	public boolean checkFinish(Player player)
	{
		boolean b = true;
		for(int i=0;i<achievepremise.length;i++)
		{
			b=achievepremise[i].isAchieve(player);
			if(!b)
				return b;
		}
		return b;
	}
	/** ������� */
	public boolean finish(Player player)
	{
		if(this.status!=TAKED) return false;
		if(!checkFinish(player)) return false;
		if(!executeAchievepremise(player)) return false;
		this.finishtime=System.currentTimeMillis();
		this.finishcount++;
		this.status=FINISH;
		postReward(player);
		return true;
	}
	/**  ���Ž���  */
	private void postReward(Player player)
	{
		// TODO Auto-generated method stub

	}
	/* common methods */
	
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Sample#bytesWrite(com.cambrian.common.net.ByteBuffer)
	 */
	@Override
	public void bytesWrite(ByteBuffer data)
	{
		super.bytesWrite(data);
		data.writeInt(status);
		data.writeLong(begintime);
		data.writeLong(finishtime);
		data.writeInt(finishcount);
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Sample#bytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	@Override
	public void bytesRead(ByteBuffer data)
	{
		super.bytesRead(data);
		this.status=data.readInt();
		this.begintime=data.readLong();
		this.finishtime=data.readLong();
		this.finishcount=data.readInt();
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Sample#dbBytesWrite(com.cambrian.common.net.ByteBuffer)
	 */
	@Override
	public void dbBytesWrite(ByteBuffer data)
	{
		super.dbBytesWrite(data);
		data.writeInt(status);
		data.writeLong(begintime);
		data.writeLong(finishtime);
		data.writeInt(finishcount);
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Sample#dbBytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	@Override
	public void dbBytesRead(ByteBuffer data)
	{
		super.dbBytesRead(data);
		this.status=data.readInt();
		this.begintime=data.readLong();
		this.finishtime=data.readLong();
		this.finishcount=data.readInt();
	}
	
	/* inner class */
}