package com.cambrian.dfhm.instancing.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.armyCamp.logic.ArmyCampManager;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.logic.InstancingManager;
import com.cambrian.game.Session;

/**
 * 类说明：攻击NPC
 * 
 * @author：LazyBear
 */
public class AttackNPCCommand extends Command
{

	@Override
	public void handle(NioTcpConnect connect,ByteBuffer data)
	{
		Session session=connect.getSession();
		if(log.isDebugEnabled())
		{
			log.debug("session = "+session);
		}
		if(session==null)
		{
			throw new DataAccessException(601,Lang.F9000_SDE);
		}
		Player player=(Player)session.getSource();
		if(player==null)
		{
			throw new DataAccessException(601,Lang.F9000_SDE);
		}
		/* 进入战斗 */
		int sid=data.readInt();
		int npcType=data.readInt();
		int attType=data.readInt();// 攻击类型 是击杀还是扫荡
		/* 自动喝酒 */
		int auto=data.readInt();
		List<Integer> cardList=new ArrayList<Integer>();
		if(auto!=0)
		{
			int useGold=data.readInt();
			int len=data.readInt();
			for(int i=0;i<len;i++)
			{
				cardList.add(data.readInt());
			}
			/* 自动喝酒 */
			ArmyCampManager.getInstance().autoDrink(player,useGold,cardList,
				true);
		}
		/* 进入战斗 */
		Map<String,ArrayList<Integer>> resultMap=InstancingManager
			.getInstance().attackNPC(sid,player,npcType,attType,cardList);
		ArrayList<Integer> record=resultMap.get("record");
		ArrayList<Integer> reward=resultMap.get("reward");
		for(Integer integer:record)
		{
			data.writeInt(integer);
		}
		data.writeInt(reward.size());
		for(Integer integer:reward)
		{
			data.writeInt(integer);
		}
		System.err.println(record);

	}

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
