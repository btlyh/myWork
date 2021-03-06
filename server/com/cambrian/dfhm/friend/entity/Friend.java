package com.cambrian.dfhm.friend.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：好友类
 * 
 * @author：LazyBear
 */
public class Friend
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 玩家ID*/
	private int userId;
	/** 玩家昵称 */
	private String name;
	/** 玩家VIP等级 */
	private int vipLevel;
	/** 玩家距离上次登录的时间差 */
	private long time;

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/** 序列化 和前台通信 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeInt(userId);
		data.writeUTF(name);
		data.writeInt(vipLevel);
		data.writeLong(time);
	}

	/**
	 * 设置好友对象属性
	 * 
	 * @param player
	 */
	public void setFieldFromPlayer(Player player,boolean isAlive)
	{
		this.userId=(int)player.getUserId();
		this.name=player.getNickname();
		this.vipLevel=player.getVipLevel();
		this.time=0;
		if(!isAlive)
		{
			this.time=TimeKit.getTimeFromAssgin(player.getLogoutTime());
		}
	}
}
