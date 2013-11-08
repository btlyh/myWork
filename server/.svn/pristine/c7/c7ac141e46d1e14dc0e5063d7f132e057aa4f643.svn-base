package com.cambrian.dfhm.message;

import com.cambrian.common.actor.ProxyActorProcess;
import com.cambrian.common.net.ByteBuffer;


/**
 * 类说明：消息 
 * 
 * @version 1.0
 * @date 2013-5-29
 * @author maxw<woldits@qq.com>
 */
public class Message
{
	/* static fields */
	/** 消息状态 (处理,读,)*/
	public static final int HANDLE = 1,READ=2;
	
	/* static methods */

	/* fields */
	/** id */
	public long uuid;
	/** 类型  */
	public int type;
	/** 状态 */
	public int status;
	/** 源 */
	public long scr;
	/** 目标 */
	public long dest;
	/** 处理器 */
	public int pid;
	/** 参数 */
	public Object[] objs;
	
	/* constructors */

	/* properties */
	/** 获得状态 */
	public int getStatus()
	{
		return status;
	}
	/** 设置状态 */
	public void setStatus(int status)
	{
		this.status=status;
	}
	/** 添加指定状态 */
	public void addStatus(int status)
	{
		this.status|=status;
	}
	/** 删除指定状态 */
	public void delStatus(int status)
	{
		this.status&=(~status);
	}
	/** 是否有指定的状态 */
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
//	/** 执行  */
//	public boolean execute(Actor actor)
//	{
//		// TODO Auto-generated method stub
//		// TODO 加判读条件是否可以执行,是否只是显示等
//		//if()
//		//boolean b=
//		return ProxyActorProcess.handle(process,actor,data);
//	}
	/* common methods */
	/** 序列化 */
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
	/** 反序列化 */
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
