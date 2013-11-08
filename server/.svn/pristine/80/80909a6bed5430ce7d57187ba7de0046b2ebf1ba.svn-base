package com.cambrian.dfhm.shop.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.shop.Shop;
import com.cambrian.game.Session;

/**
 * 类说明：商城-前台发送-获取商品列表
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class ShopGoodsCommand extends PlayerCommand
{
	/** 商城 */
	Shop shop;
	
	/** 设置商城  */
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