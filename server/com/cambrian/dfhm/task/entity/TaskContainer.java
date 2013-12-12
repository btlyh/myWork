package com.cambrian.dfhm.task.entity;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;

/**
 * ��˵������������
 * 
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
	/** �����ճ����� */
	ArrayList<Task> allDaylyTaskList;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** �Ƿ��Ѿ��������� */
	public boolean isContain(int sid)
	{
		for (Task task : taskList)
		{
			if (task.getSid() == sid)
				return true;
		}
		for (Task task : daylyTaskList)
		{
			if (task.getSid() == sid)
				return true;
		}
		for (Task task : finishedTaskList)
		{
			if (task.getSid() == sid)
				return true;
		}
		return false;
	}

	/** �������ɵ����� */
	public Task getFinishTask(int sid)
	{
		for (Task task : finishedTaskList)
		{
			if (task.getSid() == sid)
				return task;
		}
		return null;
	}

	/** ����б���ָ�������� */
	public Task getTask(int sid)
	{
		for (Task task : taskList)
		{
			if (task.getSid() == sid)
				return task;
		}
		for (Task task : daylyTaskList)
		{
			if (task.getSid() == sid)
				return task;
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
				Task task = (Task) Sample.factory.newSample(sample.getSid());
				task.status = 0;
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

	/** ˢ���ճ����� */
	public void refreshDayly(Player player)
	{
		initDaylyTasks();
		daylyTaskList.clear();
		List<Task> canTakeDaylyTasks = getCanTakeDayly(player);
		takeDaylyTask(canTakeDaylyTasks, player);
		for (int i = 0; i < daylyTaskList.size(); i++)
		{
			Task task = daylyTaskList.get(i);
			task.checkFinish(player);
		}
	}
	/** ��ȡ�ճ����� 
	 * @param player */
	private void takeDaylyTask(List<Task> canTakeDaylyTasks, Player player)
	{
		for (int i = 0; i < GameCFG.getCanTakeDaylyCount(); i++)
		{
			if(canTakeDaylyTasks.size()==0)
				break;
			int index = MathKit.randomValue(0, canTakeDaylyTasks.size()-1);
			Task task = canTakeDaylyTasks.get(index);
			if(task.take(player))
			{
				daylyTaskList.add(canTakeDaylyTasks.get(index));
				canTakeDaylyTasks.remove(index);
			}
			else
			{
				i--;
				continue;
			}
		}
	}

	/** �����ҿ�����ȡ������ */
	private List<Task> getCanTakeDayly(Player player)
	{
		List<Task> canTakeList = new ArrayList<Task>();
		int topSid = player.getCurIndexForNormalNPC() - 1;
		int index = getTopIndex(topSid);
		for (int i = index; i >= 0; i--)
		{
			Task task = allDaylyTaskList.get(i);
			canTakeList.add(task);
			if(canTakeList.size() == GameCFG.getRandomDaylyCount())
				break;
		}
		return canTakeList;
	}

	/** �õ��ɽ��ܵ����INDEX */
	private int getTopIndex(int topSid)
	{
		for (int i = 0; i < allDaylyTaskList.size(); i++)
		{
			Task task = allDaylyTaskList.get(i);
			if (task.instancingSid == topSid)
				return i;
		}
		return -1;
	}

	/** ��ʼ��ÿ������ */
	private void initDaylyTasks()
	{
		allDaylyTaskList = new ArrayList<Task>();
		Sample[] samples = Sample.getFactory().getSamples();
		for (Sample sample : samples)
		{
			if (sample != null && sample instanceof Task)
			{
				Task task = (Task) Sample.factory.newSample(sample.getSid());
				task.status = 0;
				if (task.isDayly == Task.DAYLY)
				{
					allDaylyTaskList.add(task);
				}
			}
		}
		Collections.sort(daylyTaskList);
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
			Task task = (Task) Sample.getFactory().getSample(sid);
			task.dbBytesRead(data);
			taskList.add(task);
		}
		len = data.readInt();
		for (int i = 0; i < len; i++)
		{
			int sid = data.readInt();
			Task task = (Task) Sample.getFactory().getSample(sid);
			task.dbBytesRead(data);
			daylyTaskList.add(task);
		}
	}
}
