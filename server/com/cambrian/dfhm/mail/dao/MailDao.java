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
 * ��˵�����ʼ����ݷ�����
 * 
 * @author��LazyBear
 */
public class MailDao
{

	/* static fields */
	private static final String TABLE="t_mail";
	/* static methods */

	/* fields */
	/** ���ӹ����� */
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
	 * ɾ���ʼ�
	 * 
	 * @param uid ΨһID
	 */
	public void deleteMail(int uid)
	{
		DBKit.delete(TABLE,cm,FieldKit.create("mailId",uid));
	}

	/**
	 * �����ʼ�״̬
	 * 
	 * @param uid ΨһID
	 * @param mail �ʼ�����
	 */
	public void setMailState(int uid,Mail mail)
	{
		Field[] fields=new Field[1];
		fields[0]=FieldKit.create("state",mail.getState());
		DBKit.set(TABLE,cm,FieldKit.create("mailId",uid),new Fields(fields));
	}

	/**
	 * ��ѯ���UID
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
	 * ����ʼ����UID
	 * 
	 * @return
	 */
	private int getMaxMailId()
	{
		String sql="select max(mailId) num from t_mail";
		Fields fields=SqlKit.query(cm,sql);
		return ((IntField)fields.get("num")).value;
	}

	/** ����ʼ�ID */
	public synchronized long getMailId()
	{
		long i=getMaxMailId();
		i++;
		return i;
	}
	
	/**
	 * ����ʼ�
	 * @param mail �ʼ�����
	 * @param userId �û�ID
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
