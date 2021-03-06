/** */
package com.cambrian.game.dc;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.game.Session;

/**
 * 类说明：加载命令
 * 
 * @version 2013-5-6
 * @author HYZ (huangyz1988@qq.com)
 */
public class LoadCommand extends Command
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
		String uid=data.readUTF();
		String nickName=data.readUTF();
		String userName=data.readUTF();
		Session session=connect.getSession();
		ByteBuffer bb=dc.load(uid,session,nickName,userName);
		data.clear();
		data.write(bb.getArray(),bb.offset(),bb.length());
	}

}
