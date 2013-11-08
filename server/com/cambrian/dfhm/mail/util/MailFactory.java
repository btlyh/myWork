package com.cambrian.dfhm.mail.util;

import java.util.ArrayList;

import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.mail.dao.MailDao;
import com.cambrian.dfhm.mail.entity.Mail;

/**
 * ��˵�����ʼ�������
 * 
 * @author��LazyBear
 */
public class MailFactory
{

	/* static fields */

	/* static methods */

	/* fields */
	/** �ʼ����ݷ��ʶ��� */
	MailDao dao;

	/* constructors */

	/* properties */
	public void setMailDao(MailDao dao)
	{
		this.dao=dao;
	}
	/* init start */

	/* methods */
	/**
	 * ��������ʼ�
	 * @param mailContent �ʼ�����
	 * @param sendName �������ǳ�
	 * @return �����ʼ�����
	 */
	public Mail createPlayerMail(String mailContent,
		String sendName)
	{
		Mail mail=new Mail((int)dao.getMailId(),Mail.MAILSTATE_UNREAD,
			sendName+Lang.TITLE,mailContent,sendName);
		return mail;
	}
	
	/**
	 * ����ϵͳ�ʼ� 
	 * @param cards ����SID����
	 * @param gold  RMB
	 * @param money ��Ϸ��
	 * @param token ����
	 * @param soulPoint ���
	 * @param normalPoint ����
	 * @return �����ʼ�����
	 */
	public Mail createSystemMail(ArrayList<Integer> cards,int gold,
		int money,int token,int soulPoint,int normalPoint,int userId)
	{
		Mail mail=new Mail((int)dao.getMailId(),Mail.MAILSTATE_UNREAD,
			Lang.SYSTEM_MAIL_TITLE,Lang.SYSTEM_MAIL_CONTENT,cards,gold,
			money,token,soulPoint,normalPoint,Lang.SYSTEM_MAIL_TITLE);
		
		dao.addMail(mail,userId);
		return mail;
	}
}