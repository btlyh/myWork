package com.cambrian.game.ds;

import com.cambrian.common.net.ByteBuffer;

/**
 * ��˵����
 * 
 * @version 2013-4-23
 * @author HYZ (huangyz1988@qq.com)
 */
public interface DSDCAccess
{

	/** �Ƿ��ܷ��� */
	public abstract boolean canAccess();
	/** ͨ��uid��sid�������� */
	public abstract ByteBuffer load(String uid,String nickName,String userName);
	/** ͨ��uid��sid��¼ */
	public abstract void login(String id,String sid,String address);
	/** ����uid��Ӧ������ */
	public abstract void save(String id,boolean exit,ByteBuffer data);
	/** �����������*/
	public abstract ByteBuffer getRandomName();
	
	
}