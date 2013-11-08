package com.cambrian.dfhm.armyCamp.dao;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.dfhm.armyCamp.entity.ArmyCamp;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.game.Session;

/**
 * ��˵�����������ݷ�����
 * @author��Zmk
 * 
 */
public class ArmyCampDao
{

	/* static fields */

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
	 * ��������ǳƲ�ѯuserid
	 * 
	 * @param name ����ǳ�
	 * @return
	 */
	public int getUserIdByName(String name,Session session)
	{
		int userId=0;
		if(session!=null)
		{
			Player player=(Player)session.getSource();
			userId=(int)player.getUserId();
		}
		else
		{
			Field[] array=new Field[1];
			array[0]=FieldKit.create("userId",0);
			Fields fields=new Fields(array);
			int i=DBKit.get("player",cm,FieldKit.create("nickname",name),
				fields);
			if(i==DBKit.OK)
			{
				userId=((IntField)array[0]).value;
			}
		}
		return userId;
	}
	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param userId ���UID
	 * @return
	 */
	public ArmyCamp getArmyCamp(int userId)
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("armyCamp",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		ArmyCamp armyCamp=new ArmyCamp();
		armyCamp.dbBytesRead(new ByteBuffer(bytes));
		return armyCamp;
	}

	/**
	 * �洢������Ϣ
	 * 
	 * @param friendInfo ������Ϣ����
	 * @param userId �û�id
	 */
	public void setArmyCamp(ArmyCamp armyCamp,int userId)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		armyCamp.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("armyCamp",data.toArray());
		int i=DBKit.set("player_info",cm,FieldKit.create("userId",userId),
			fields);
		System.out.println(i);
	}
	
	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param userId ���UID
	 * @return
	 */
	public CardBag getCardBag(int userId)
	{
		Field[] array=new Field[1];
		array[0]=FieldKit.create("cardBag",(byte[])null);
		Fields fields=new Fields(array);
		DBKit.get("player_info",cm,FieldKit.create("userId",userId),fields);
		byte[] bytes=((ByteArrayField)array[0]).value;
		CardBag cardBag=new CardBag();
		cardBag.dbBytesRead(new ByteBuffer(bytes));
		return cardBag;
	}
	/**
	 * �洢������Ϣ
	 * 
	 * @param friendInfo ������Ϣ����
	 * @param userId �û�id
	 */
	public void setCardBag(CardBag cardBag,int userId)
	{
		Field[] array=new Field[1];
		ByteBuffer data=new ByteBuffer();
		cardBag.dbBytesWrite(data);
		Fields fields=new Fields(array);
		array[0]=FieldKit.create("cardBag",data.toArray());
		int i=DBKit.set("player_info",cm,FieldKit.create("userId",userId),
			fields);
		System.out.println(i);
	}
}