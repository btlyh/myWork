package com.cambrian.dfhm.task;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.DBSerializable;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.Serializable;
import com.cambrian.common.util.ArrayList;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：任务容器
 * 
 * @version 1.0
 * @date 2013-5-30
 * @author maxw<woldits@qq.com>
 */
public class TaskContainer implements Serializable,DBSerializable
{
	/* static fields */
	/** 所有任务 */
	private static Task[] tasks;
	
	/* static methods */
	/** 列出所有任务 */
	public static void init()
	{
		ArrayList list=new ArrayList();
		Sample[] samples=Sample.factory.getSamples();
		for(int i=0;i<samples.length;i++)
		{
			if(samples[i]==null) continue;
			if(samples[i] instanceof Task)
			{
				list.add(samples[i]);
			}
		}
		tasks=new Task[list.size()];
		list.toArray(tasks);
	}
	/* fields */
	/** 任务列表  */
	ArrayList list=new ArrayList();
	
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** 是否包含该任务 */
	public boolean isContain(int sid)
	{
		for(int i=0;i<list.size();i++)
		{
			Task task=(Task)list.get(i);
			if(task.getSid()==sid)
				return true;
		}
		return false;
	}
	/** 获取指定sid任务 */
	public Task getTask(int sid)
	{
		for(int i=0;i<list.size();i++)
		{
			Task task=(Task)list.get(i);
			if(task.getSid()==sid)
				return task;
		}
		return null;
	}
	/** 刷新玩家任务 */
	public void refresh(Player player)
	{
		for(int i=0;i<tasks.length;i++)
		{
			tasks[i].take(player);
		}
	}
	/* common methods */
	
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(list.size());
		for(int i=0;i<list.size();i++)
		{
			((Task)list.get(i)).bytesWrite(data);
		}
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.Serializable#bytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void bytesRead(ByteBuffer data)
	{
		int len=data.readInt();
		list=new ArrayList(len);
		for(int i=0;i<len;i++)
		{
			int sid=data.readUnsignedShort();
			Task task=(Task)Sample.factory.getSample(sid);
			task.bytesRead(data);
			list.add(task);
		}
	}
	/* inner class */
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.DBSerializable#dbBytesWrite(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(list.size());
		for(int i=0;i<list.size();i++)
		{
			((Task)list.get(i)).bytesWrite(data);
		}
	}
	/* (non-Javadoc)
	 * @see com.cambrian.common.object.DBSerializable#dbBytesRead(com.cambrian.common.net.ByteBuffer)
	 */
	
	public void dbBytesRead(ByteBuffer data)
	{
		int len=data.readInt();
		list=new ArrayList(len);
		for(int i=0;i<len;i++)
		{
			int sid=data.readUnsignedShort();
			Task task=(Task)Sample.factory.getSample(sid);
			task.bytesRead(data);
			list.add(task);
		}
	}
}
