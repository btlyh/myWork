package com.cambrian.dfhm.card.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.dfhm.PlayerCommand;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * ��˵������Ƭͼ��-ǰ̨����-��ȡ��Ƭ�б� 
 * 
 * @version 1.0
 * @date 2013-6-7
 * @author maxw<woldits@qq.com>
 */
public class CardListCommand extends PlayerCommand
{
	@Override
	public void handle(Session session,Player player,ByteBuffer data)
	{
		// TODO Auto-generated method stub
		data.clear();
		player.getCardBag().bytesWrite(data);
	}
}
