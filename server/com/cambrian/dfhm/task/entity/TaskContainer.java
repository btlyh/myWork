package com.cambrian.dfhm.task.entity;

import java.util.List;
import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.common.entity.Player;

/**
 * ��˵������������
 * @author��Zmk
 * 
 */
public class TaskContainer
{

	/* static fields */
	
	/* static methods */
	
	/* fields */
	/** �����б� */
	List<Task> taskList = new ArrayList<Task>();
	/** �ճ������б� */
	List<Task> daylyTaskList = new ArrayList<Task>();
	/** ����������б� */
	List<Task> finishedTaskList = new ArrayList<Task>();
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** �Ƿ��Ѿ��������� */
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
	
	/** �������ɵ����� */
	public Task getFinishTask(int sid)
	{
		for (Task task : finishedTaskList)
		{
			if (task.getSid() == sid) return task;
		}
		return null;
	}
	
	/** ����б���ָ�������� */
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
	
	/** ˢ����ͨ�������� */
	public void refreshList(Player player)
	{
		Sample[] samples = Sample.getFactory().getSamples();
		for (Sample sample : samples)
		{
			if (sample != null && sample instanceof Task)
			{
				Task task = (Task)Sample.getFactory().newSample(sample.getSid());
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
	/** ˢ���ճ����� */
	public void refreshDaly(Player player)
	{
		Sample[] samples = Sample.getFactory().getSamples();
		for (Sample sample : samples)
		{
			if (sample != null && sample instanceof Task)
			{
				Task task = (Task)sample;
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
			Task task = taskList.get(i);
			if (task.status == Task.FINISHED)
			{
				finishedTaskList.add(task);
				taskList.remove(i);
				i--;
				continue;
			}
			task.checkFinish(player);
		}
	}
	
	/** ǰ̨���л� */
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
	
	/** ���л� DC �� */
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
	
	/** ���л� DC ȡ */
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
			taskList.add(task);
		}
	}
}
