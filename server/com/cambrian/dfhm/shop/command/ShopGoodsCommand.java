package com.cambrian.dfhm.shop.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.shop.Shop;
import com.cambrian.game.Session;

/**
 * ��˵�����̳�-ǰ̨����-��ȡ��Ʒ�б�
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class ShopGoodsCommand extends PlayerCommand
{
	/** �̳� */
	Shop shop;
	
	/** �����̳�  */
	public void setShop(Shop shop)
	{
		this.shop=shop;
	}
	
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		data.clear();
		shop.bytesWrite(data);
	}
}