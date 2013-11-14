package com.cambrian.dfhm.task.command;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.common.object.Sample;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.task.entity.Task;
import com.cambrian.dfhm.task.entity.TaskAward;
import com.cambrian.game.Session;

/**
 * ��˵����������
 * @author��Zmk
 * 
 */
public class FinishTaskCommand extends Command
{

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */
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
		int sid = data.readInt();
		Task task = player.getTasks().getTask(sid);
		boolean b = task.finish(player);
		data.writeBoolean(b);
		if (b)
		{
			TaskAward award = (TaskAward)Sample.getFactory().getSample(task.awardSid);
			award.bytesWrite(data);
			Card card = task.dispense(player);
			if (card != null)
			{
				data.writeBoolean(true);
				data.writeInt(card.getSid());
				data.writeInt(card.getId());
				data.writeInt(card.getSkillId());
			}
			else
				data.writeBoolean(false);
		}
	}
}
