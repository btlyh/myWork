package com.cambrian.dfhm.message;

import com.cambrian.common.actor.ProxyActorProcess;
import com.cambrian.common.net.ByteBuffer;


/**
 * ��˵������Ϣ 
 * 
 * @version 1.0
 * @date 2013-5-29
 * @author maxw<woldits@qq.com>
 */
public class Message
{
	/* static fields */
	/** ��Ϣ״̬ (����,��,)*/
	public static final int HANDLE = 1,READ=2;
	
	/* static methods */

	/* fields */
	/** id */
	public long uuid;
	/** ����  */
	public int type;
	/** ״̬ */
	public int status;
	/** Դ */
	public long scr;
	/** Ŀ�� */
	public long dest;
	/** ������ */
	public int pid;
	/** ���� */
	public Object[] objs;
	
	/* constructors */

	/* properties */
	/** ���״̬ */
	public int getStatus()
	{
		return status;
	}
	/** ����״̬ */
	public void setStatus(int status)
	{
		this.status=status;
	}
	/** ���ָ��״̬ */
	public void addStatus(int status)
	{
		this.status|=status;
	}
	/** ɾ��ָ��״̬ */
	public void delStatus(int status)
	{
		this.status&=(~status);
	}
	/** �Ƿ���ָ����״̬ */
	public boolean hasStatus(int status)
	{
		return (this.status&status)==status;
	}
	public byte[] getDBObjs() 
	{
		ByteBuffer data=new ByteBuffer();
		MessageProcess process=(MessageProcess)ProxyActorProcess.proxy.getProcess(pid);
		process.bytesWrite(data,objs);
		return data.toArray();
	}
	public void setDBObjs(byte[] bb) 
	{
		MessageProcess process=(MessageProcess)ProxyActorProcess.proxy.getProcess(pid);
		objs=process.bytesRead(new ByteBuffer(bb));
	}
	/* init start */

	/* methods */
//	/** ִ��  */
//	public boolean execute(Actor actor)
//	{
//		// TODO Auto-generated method stub
//		// TODO ���ж������Ƿ����ִ��,�Ƿ�ֻ����ʾ��
//		//if()
//		//boolean b=
//		return ProxyActorProcess.handle(process,actor,data);
//	}
	/* common methods */
	/** ���л� */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeLong(uuid);
		data.writeInt(type);
		data.writeInt(status);
		data.writeLong(scr);
		data.writeLong(dest);
		data.writeInt(pid);
		MessageProcess process=(MessageProcess)ProxyActorProcess.proxy.getProcess(pid);
		process.bytesWrite(data,objs);
	}
	/** �����л� */
	public Object bytesRead(ByteBuffer data)
	{
		this.uuid=data.readLong();
		this.type=data.readInt();
		this.status=data.readInt();
		this.scr=data.readLong();
		this.dest=data.readLong();
		this.pid=data.readInt();
		MessageProcess process=(MessageProcess)ProxyActorProcess.proxy.getProcess(pid);
		objs=process.bytesRead(data);
		return this;
	}
	/* inner class */
}
