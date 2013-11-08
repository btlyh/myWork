/** */
package com.cambrian.game.dc;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.NioTcpConnect;

/**
 * ÀàËµÃ÷£º
 * 
 * @version 2013-5-9
 * @author HYZ (huangyz1988@qq.com)
 */
public class CloseCommand extends Command
{

	DataCenter dc;

	public void setDC(DataCenter dc)
	{
		this.dc=dc;
	}

	@Override
	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		dc.close();
	}

}
