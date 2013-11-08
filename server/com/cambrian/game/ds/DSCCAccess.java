package com.cambrian.game.ds;

/**
 * 类说明：
 * 
 * @version 2013-4-23
 * @author HYZ (huangyz1988@qq.com)
 */
public interface DSCCAccess
{

	/** 能否访问 */
	public abstract boolean canAccess();

	/** 加载信息 */
	public abstract String[] load(String sid,String address);

	/** 活跃 */
	public abstract void active(String sid);

	/** 退出 */
	public abstract void exit(String sid);
}