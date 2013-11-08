package com.cambrian.game.dc;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.game.dc.CheckNameFilter;

/**
 * 类说明：
 * @author：Sebastian
 *
 */
public class GetRandomNameCommand extends Command
{

	/** 名称过滤 */
	CheckNameFilter ckf;

	/** 设置名称过滤 */
	public void setCheckNameFilter(CheckNameFilter ckf)
	{
		this.ckf=ckf;
	}
	@Override
	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		data.clear();
		String name = ckf.getRandomName();
		data.writeUTF(name);
	}
}
