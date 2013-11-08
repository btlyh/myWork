package com.cambrian.dfhm.shop.command;

import com.cambrian.common.actor.ProxyActorProcess;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.shop.Goods;
import com.cambrian.dfhm.shop.Shop;
import com.cambrian.game.Session;

/**
 * ��˵�����̳�-ǰ̨����-������Ʒ,����ƷΪ"������Ʒ"��һ��ֻ�ܹ���һ��
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class BuyGoodsCommand extends PlayerCommand
{

	/* static fields */
	/** ��־��¼ */
	private static final Logger log=Logger.getLogger(BuyGoodsCommand.class);
	
	/* static methods */

	/* fields */
	/** �̳� */
	Shop shop;
	
	/* constructors */

	/* properties */
	/** �����̳�  */
	public void setShop(Shop shop)
	{
		this.shop=shop;
	}
	
	/* init start */

	/* methods */

	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		int sid=data.readInt();
		Goods goods=shop.getGoods(sid);
		int num=1;
		if(goods.type==Goods.PROP)
			num=data.readInt();
		if(log.isInfoEnabled())
			log.info(",sid="+sid+",num="+num+",vlen="+data.length());
		boolean b=decrPlayerMoney(player,goods,num);
		if(!b) return;
		b=handleGoods(player,goods,num,data);
		if(!b)
			incrPlayerMoney(player,goods,num);
		
		data.clear();
		data.writeBoolean(b);
		if(log.isInfoEnabled())
			log.info(",ok="+b+",sid="+sid+",num="+num);
	}
	/** �۳���ҽ�Ǯ */
	private boolean decrPlayerMoney(Player player,Goods goods,int num)
	{
		// TODO Auto-generated method stub
		
		
		
		return true;
	}
	/** ������ҽ�Ǯ */
	private boolean incrPlayerMoney(Player player,Goods goods,int num)
	{
		// TODO Auto-generated method stub
		
		
		
		return true;
	}
	/** ������Ʒ */
	private boolean handleGoods(Player player,Goods goods,int num,ByteBuffer data)
	{
		switch(goods.type)
		{
			case Goods.PROP:
				return handleProp(player,goods,num);
				//break;
			case Goods.PROCESS:
				return handleProcess(player,goods,data);
				//break;
			default:
				return false;
				//break;
		}
	}
	/** ������� */
	private boolean handleProp(Player player,Goods goods,int num)
	{
		// TODO Auto-generated method stub
		
		
		
		return true;
	}
	/** ������� */
	private boolean handleProcess(Player player,Goods goods,ByteBuffer data)
	{
		return ProxyActorProcess.handle(goods.pid,player,data);
	}
	/* common methods */

	/* inner class */
}