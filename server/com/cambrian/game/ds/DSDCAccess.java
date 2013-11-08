package com.cambrian.game.ds;

import com.cambrian.common.net.ByteBuffer;

/**
 * 类说明：
 * 
 * @version 2013-4-23
 * @author HYZ (huangyz1988@qq.com)
 */
public interface DSDCAccess
{

	/** 是否能访问 */
	public abstract boolean canAccess();
	/** 通过uid和sid加载数据 */
	public abstract ByteBuffer load(String uid,String nickName,String userName);
	/** 通过uid和sid登录 */
	public abstract void login(String id,String sid,String address);
	/** 保存uid对应的数据 */
	public abstract void save(String id,boolean exit,ByteBuffer data);
	/** 访问随机名字*/
	public abstract ByteBuffer getRandomName();
	
	
}