package com.cambrian.dfhm.globalboss.command;

import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.globalboss.entity.BossFightRecord;
import com.cambrian.dfhm.globalboss.logic.BossManager;
import com.cambrian.game.Session;

/**
 * 类说明：世界BOSS主界面命令
 * 
 * @author：LazyBear
 */
public class BossMainCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	@SuppressWarnings("unchecked")
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
		int bossSid=data.readInt();
		data.clear();
		Map<String,Object> dataMap=BossManager.getInstance().bossMain(
			player,bossSid);
		List<BossFightRecord> recordList=(List<BossFightRecord>)dataMap
			.get("recordList");
		int loop=recordList.size();
		if(loop>5)
		{
			loop=5;
		}
		data.writeInt(loop);
		for(int i=0;i<loop;i++)
		{
			recordList.get(i).bytesWrite(data);// 排行榜数据
		}
		data.writeInt(Integer.parseInt(dataMap.get("maxHp").toString()));// BOSS最大血量
		data.writeInt(Integer.parseInt(dataMap.get("curHp").toString()));// BOSS当前血量
		data.writeLong(Long.parseLong(dataMap.get("surplusTime").toString()));// 剩余的攻击时间
		data.writeInt(player.getBfr().getRank());// 玩家排名
		data.writeInt(player.getBfr().getTotalDamage()); // 玩家造成的伤害
		data.writeInt(player.getBfr().getAttUp());//强化层数

	}
}
