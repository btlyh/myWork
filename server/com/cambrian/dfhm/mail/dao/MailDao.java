package com.cambrian.dfhm.mail.dao;

import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.common.sql.SqlKit;
import com.cambrian.dfhm.mail.entity.Mail;

/**
 * 类说明：邮件数据访问类
 * 
 * @author：LazyBear
 */
public class MailDao
{

	/* static fields */
	private static final String TABLE="t_mail";
	/* static methods */

	/* fields */
	/** 连接管理器 */
	ConnectionManager cm;

	/* constructors */

	/* properties */
	public void setConnectionManager(ConnectionManager cm)
	{
		this.cm=cm;
	}
	/* init start */

	/* methods */
	/**
	 * 删除邮件
	 * 
	 * @param uid 唯一ID
	 */
	public void deleteMail(int uid)
	{
		DBKit.delete(TABLE,cm,FieldKit.create("mailId",uid));
	}

	/**
	 * 更新邮件状态
	 * 
	 * @param uid 唯一ID
	 * @param mail 邮件对象
	 */
	public void setMailState(int uid,Mail mail)
	{
		Field[] fields=new Field[1];
		fields[0]=FieldKit.create("state",mail.getState());
		DBKit.set(TABLE,cm,FieldKit.create("mailId",uid),new Fields(fields));
	}

	/**
	 * 查询玩家UID
	 * 
	 * @param name
	 * @return
	 */
	public Fields getPlayerUid(String name)
	{
		String sql="select userid from player where nickname = '"+name+"'";
		Fields fields=SqlKit.query(cm,sql);
		return fields;
	}

	/**
	 * 获得邮件最大UID
	 * 
	 * @return
	 */
	private int getMaxMailId()
	{
		String sql="select max(mailId) num from t_mail";
		Fields fields=SqlKit.query(cm,sql);
		return ((IntField)fields.get("num")).value;
	}

	/** 获得邮件ID */
	public synchronized long getMailId()
	{
		long i=getMaxMailId();
		i++;
		return i;
	}
	
	/**
	 * 添加邮件
	 * @param mail 邮件对象
	 * @param userId 用户ID
	 */
	public void addMail(Mail mail,int userId)
	{
		Field[] array=new Field[15];
		array[0]=FieldKit.create("mailId",mail.getMailId());
		array[1]=FieldKit.create("state",mail.getState());
		array[2]=FieldKit.create("sendTime",mail.getSendTime());
		array[3]=FieldKit.create("title",mail.getTitle());
		array[4]=FieldKit.create("content",mail.getContent());
		array[5]=FieldKit.create("cardList",0);
		array[6]=FieldKit.create("gold",mail.getGold());
		array[7]=FieldKit.create("money",mail.getMoney());
		array[8]=FieldKit.create("token",mail.getToken());
		array[9]=FieldKit.create("soulPoint",mail.getSoulPoint());
		array[10]=FieldKit.create("normalPoint",mail.getNormalPoint());
		array[11]=FieldKit.create("userId",userId);
		array[12]=FieldKit.create("sendPlayerName",mail.getSendPalyerName());
		array[13]=FieldKit.create("isFight", mail.isFight());
		array[14]=FieldKit.create("isSystem", mail.isSystem());

		DBKit.set(TABLE,cm,FieldKit.create("mailId",mail.getMailId()),
			new Fields(array));
	}

}
