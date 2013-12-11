package com.cambrian.dfhm.task.entity;

import java.util.List;
import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：任务容器
 * @author：Zmk
 * 
 */
public class TaskContainer
{

	/* static fields */
	
	/* static methods */
	
	/* fields */
	/** 任务列表 */
	List<Task> taskList = new ArrayList<Task>();
	/** 日常任务列表 */
	List<Task> daylyTaskList = new ArrayList<Task>();
	/** 已完成任务列表 */
	List<Task> finishedTaskList = new ArrayList<Task>();
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** 是否已经包涵任务 */
	public boolean isContain(int sid)
	{
		for (Task task : taskList)
		{
			if (task.getSid() == sid) return true;
		}
		for (Task task : daylyTaskList)
		{
			if (task.getSid() == sid) return true;
		}
		for (Task task : finishedTaskList)
		{
			if (task.getSid() == sid) return true;
		}
		return false;
	}
	
	/** 获得已完成的任务 */
	public Task getFinishTask(int sid)
	{
		for (Task task : finishedTaskList)
		{
			if (task.getSid() == sid) return task;
		}
		return null;
	}
	
	/** 获得列表中指定的任务 */
	public Task getTask(int sid)
	{
		for (Task task : taskList)
		{
			if (task.getSid() == sid) return task;
		}
		for (Task task : daylyTaskList)
		{
			if (task.getSid() == sid) return task;
		}
		return null;
	}
	
	/** 刷新普通任务容器 */
	public void refreshList(Player player)
	{
		Sample[] samples = Sample.getFactory().getSamples();
		for (Sample sample : samples)
		{
			if (sample != null && sample instanceof Task)
			{
				Task task = (Task)Sample.factory.newSample(sample.getSid());
				task.status=0;
				if (task.isDayly == Task.NORMAL)
				{
					boolean b = task.take(player);
					if (b)
					{
						taskList.add(task);
					}
				}
			}
		}
		for (int i = 0; i < taskList.size(); i++)
		{
			Task task = taskList.get(i);
			task.checkFinish(player);
		}
		for (int i = 0; i < daylyTaskList.size(); i++)
		{
			Task task = daylyTaskList.get(i);
			task.checkFinish(player);
		}
	}
	/** 刷新日常任务 */
	public void refreshDayly(Player player)
	{
		Sample[] samples = Sample.getFactory().getSamples();
		for (Sample sample : samples)
		{
			if (sample != null && sample instanceof Task)
			{
				Task task = (Task)Sample.factory.newSample(sample.getSid());
				task.status = 0;
				if (task.isDayly == Task.DAYLY)
				{
					boolean b = task.take(player);
					if (b)
						daylyTaskList.add(task);
				}
			}
		}
		for (int i = 0; i < daylyTaskList.size(); i++)
		{
			Task task = daylyTaskList.get(i);
//			if (task.status == Task.FINISHED)
//			{
//				finishedTaskList.add(task);
//				taskList.remove(i);
//				i--;
//				continue;
//			}
			task.checkFinish(player);
		}
	}
	
	/** 前台序列化 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(taskList.size());
		for (Task task : taskList)
		{
			task.bytesWrite(data);
		}
		data.writeInt(daylyTaskList.size());
		for (Task task : daylyTaskList)
		{
			task.bytesWrite(data);
		}
	}
	
	/** 序列化 DC 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(taskList.size());
		for (Task task : taskList)
		{
			task.dbBytesWrite(data);
		}
		data.writeInt(daylyTaskList.size());
		for (Task task : daylyTaskList)
		{
			task.dbBytesWrite(data);
		}
	}
	
	/** 序列化 DC 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		int len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			int sid = data.readInt();
			Task task = (Task)Sample.getFactory().getSample(sid);
			task.dbBytesRead(data);
			taskList.add(task);
		}
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			int sid = data.readInt();
			Task task = (Task)Sample.getFactory().getSample(sid);
			task.dbBytesRead(data);
			daylyTaskList.add(task);
		}
	}
}
