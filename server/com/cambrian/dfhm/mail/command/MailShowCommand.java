package com.cambrian.dfhm.mail.command;

import java.util.ArrayList;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.Command;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.net.NioTcpConnect;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.mail.logic.MailManager;
import com.cambrian.game.Session;

/**
 * 类说明：邮件查看
 * 
 * @author：LazyBear
 */
public class MailShowCommand extends Command
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
		int index=data.readInt();
		data.clear();
		ArrayList<Mail> mailList=MailManager.getInstance().showMail(index,player);
		if(mailList==null)
		{
			data.writeInt(0);
		}
		else
		{
			Mail mail=null;
			data.writeInt(mailList.size());
			for(int i=0;i<mailList.size();i++)
			{
				mail=mailList.get(i);
				mail.bytesWrite(data);
			}
		}
	}

	/* static fields */

	/* static methods */

	/* fields */

	/* constructors */

	/* properties */

	/* init start */

	/* methods */

}
