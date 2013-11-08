package com.cambrian.dfhm.rankings.command;

import java.util.List;
import java.util.Map;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.rankings.entity.CardRankInfo;
import com.cambrian.dfhm.rankings.entity.RankInfo;
import com.cambrian.dfhm.rankings.logic.RankingsManager;
import com.cambrian.game.Session;

/**
 * ��˵�������ظ���
 * @author��Zmk
 * 
 */
public class LoadMoreRankingCommand extends Command
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
	public void handle(NioTcpConnect connect, ByteBuffer data)
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
		int type = data.readInt(); // �ĸ�����
		int index = data.readInt(); // �Ӷ��ٿ�ʼ
		Map<String, Object> resultMap = RankingsManager.getInstance().loadMore(type, index);
		boolean flag = (Boolean)resultMap.get("result"); // ����û������
		data.writeBoolean(flag);
		if (flag)
		{
			if (type == 3) // ��������
			{
				List<CardRankInfo> list = (List<CardRankInfo>)resultMap.get("list");
				data.writeInt(list.size());
				for (CardRankInfo cardRankInfo : list)
				{
					cardRankInfo.bytesWrite(data);
				}
			}else
			{
				List<RankInfo> list = (List<RankInfo>)resultMap.get("list");
				data.writeInt(list.size());
				for (RankInfo rankInfo : list)
				{
					rankInfo.bytesWrite(data);
				}
			}
		}
	}
}
