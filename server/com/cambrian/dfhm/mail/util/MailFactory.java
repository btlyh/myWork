package com.cambrian.dfhm.mail.util;

import java.util.ArrayList;

import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.mail.dao.MailDao;
import com.cambrian.dfhm.mail.entity.Mail;

/**
 * 类说明：邮件工厂类
 * 
 * @author：LazyBear
 */
public class MailFactory
{

	/* static fields */

	/* static methods */

	/* fields */
	/** 邮件数据访问对象 */
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
	 * 创建玩家邮件
	 * @param mailContent 邮件内容
	 * @param sendName 发件人昵称
	 * @return 返回邮件对象
	 */
	public Mail createPlayerMail(String mailContent,
		String sendName)
	{
		Mail mail=new Mail((int)dao.getMailId(),Mail.MAILSTATE_UNREAD,
			sendName+Lang.TITLE,mailContent,sendName);
		return mail;
	}
	
	/**
	 * 创建系统邮件 
	 * @param cards 卡组SID集合
	 * @param gold  RMB
	 * @param money 游戏币
	 * @param token 军令
	 * @param soulPoint 武魂
	 * @param normalPoint 积分
	 * @return 返回邮件对象
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
