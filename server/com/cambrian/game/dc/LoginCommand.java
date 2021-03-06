/** */
package com.cambrian.game.dc;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.game.Session;

/**
 * ��˵����
 * 
 * @version 2013-4-27
 * @author HYZ (huangyz1988@qq.com)
 */
public class LoginCommand extends Command
{

	DataCenter dc;

	public void setDC(DataCenter dc)
	{
		this.dc=dc;
	}
	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		System.err.println("dc.LoginCommand ----------");
		String uid=data.readUTF();
		String sid=data.readUTF();
		String address=data.readUTF();
		Session session=connect.getSession();
		dc.login(uid,sid,address,session);
	}
}
