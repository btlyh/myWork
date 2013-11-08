/** */
package com.cambrian.game.dc;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.game.Session;

/**
 * 类说明：退出命令
 * 
 * @version 2013-5-6
 * @author HYZ (huangyz1988@qq.com)
 */
public class SaveCommand extends Command
{

	/** 数据服务 */
	DataCenter dc;

	/** 设置数据服务 */
	public void setDC(DataCenter dc)
	{
		this.dc=dc;
	}

	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		String id=data.readUTF();
		boolean logout=data.readBoolean();
		boolean save=data.readBoolean();
		Session session=connect.getSession();
		dc.save(id,data,save,logout,session);
	}
}
