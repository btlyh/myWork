package com.cambrian.dfhm;

/**
 * ��˵��������
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public final class GlobalConst
{

	/** ��������ı� */
	public static final int HANDLER_CHANGED=0;
	/** ����������ն˿ڸı� */
	public static final int PORT_CHANGED=1;

	/* ͨ������� */
	/** һ�㷵����Ϣ���ն˿� */
	public static final int ACCESS_RETURN_PORT=4;
	/** ��ȡ������ʱ�� */
	public static final int TIME_PORT=6;
	/**  */
	public static final int ATTRIBUTE_PORT=11;
	/**  */
	public static final int FILE_PORT=21;
	/**  */
	public static final int AUTHORIZED_FILE_PORT=22;
	/** ��֤-��ȡsid */
	public static final int CC_CERTIFY_PORT=101;
	/** ��֤-�����û����� */
	public static final int CC_LOAD_PORT=102;
	/** ��֤-�����û���Ծ */
	public static final int CC_ACTIVE_PORT=103;
	/** ��֤-�û��˳� */
	public static final int CC_EXIT_PORT=104;
	/** ��������-��ҵ�½ */
	public static final int DC_LOGIN_PORT=111;
	/** ��������-����������� */
	public static final int DC_LOAD_PORT=112;
	/** ��������-����������� */
	public static final int DC_SAVE_PORT=113;
	/** ��������-����ǳ� */
	public static final int DC_CHECKNICKNAME_PORT=114;
	/** ��������-��ȡ������� */
	public static final int DC_RANDOMNAME_PORT=115;
	/** ��������-����������� */
	public static final int DC_UPDATE_PORT=121;
	/**  */
	public static final int CERTIFY_CODE_PORT=201;
	/**  */
	public static final int CERTIFY_PROXY_PORT=202;
	/** ���ݷ����-��ҵ�½ */
	public static final int LOGIN_PORT=211;
	/** ���ݷ����-����������� */
	public static final int LOAD_PORT=212;
	/** ���ݷ����-����˳� */
	public static final int EXIT_PORT=213;
	/** ���ݷ����-��½����ȡ������� */
	public static final int COMMAND_CLL_PORT=214;
	/** ��ȡ������� */
	public static final int RANDOMNAME_PORT=215;
	/**  */
	public static final int PROXY_ECHO_PORT=301;
	/**  */
	public static final int PROXY_PING_PORT=302;
	/**  */
	public static final int PROXY_TIME_PORT=306;
	/**  */
	public static final int PROXY_STATE_PORT=310;
	/**  */
	public static final int PROXY_LOGIN_PORT=402;
	/**  */
	public static final int PROXY_EXIT_PORT=404;
	/**  */
	public static final int CONNECT_REGISTER_PORT=601;
	/**  */
	public static final int ADVISE_OFFLINE_PORT=701;
	/**  */
	public static final int SERVER_LIST_PORT=801;

	/* ս�����,ǰ̨���� */
	/** ����-ǰ̨����-�޸����� */
	public static final int COMMAND_BATTLE_CHANGEFORMATION=902;

	/* ͨ������� */
	/** ����,��Ӧping��Ϣ */
	public static final int ECHO_PORT=1;
	/** ����ping��Ϣ */
	public static final int PING_PORT=2;

	/* ͨ�������-���� */
	/** ����-ǰ̨����-��ȡ�����б� */
	public static final int COMMAND_BAG_PROPLIST=1101;
	/** ����-ǰ̨����-���۵��� */
	public static final int COMMAND_BAG_SELLPROP=1102;
	/** ����-��̨����-���ӵ��� */
	public static final int COMMAND_BAG_ADDPROP=1103;
	/** ����-��̨����-�Ƴ����� */
	public static final int COMMAND_BAG_REMOVEPROP=1104;
	/* ͨ�������-���� */
	/** ����-ǰ̨����-��ȡ�����б� */
	public static final int COMMAND_FRIEND_FRIENDLIST=1201;
	/** ����-ǰ̨����-�������� */
	public static final int COMMAND_FRIEND_APPLYFRIEND=1202;
	/** ����-��̨����-�������� */
	public static final int COMMAND_FRIEND_APPLYFRIEND_BACK=1203;
	/** ����-ǰ̨����-�������� */
	public static final int COMMAND_FRIEND_CHECK=1204;
	/** ����-��̨����-�������� */
	public static final int COMMAND_FRIEND_CHECK_BACK=1205;
	/** ����-ǰ̨����-ɾ������ */
	public static final int COMMAND_FRIEND_REMOVE=1206;
	/* ͨ�������-��Ϣ */
	/** ��Ϣ-��̨�䷢��-��Ϣ */
	public static final int COMMAND_MESSAGE_MESSAGE=1301;
	/** ��Ϣ-ǰ̨����-��ȡ��Ϣ�б� */
	public static final int COMMAND_MESSAGE_MESSAGELIST=1302;
	/** ��Ϣ-ǰ̨����-����Ϣ */
	public static final int COMMAND_MESSAGE_READMESSAGE=1303;
	/** ��Ϣ-ǰ̨����-������Ϣ */
	public static final int COMMAND_MESSAGE_HANDLEMESSAGE=1304;
	/** ��Ϣ-ǰ̨����-�Ƴ���Ϣ */
	public static final int COMMAND_MESSAGE_REMOVEMESSAGE=1305;
	/** ��Ϣ-��̨����-������Ϣ */
	public static final int COMMAND_MESSAGE_ADDMESSAGE=1306;
	/* ͨ�������-�̳� */
	/** �̳�-ǰ̨����-��ȡ��Ʒ�б� */
	public static final int COMMAND_SHOP_GOODSLIST=1401;
	/** �̳�-ǰ̨����-������Ʒ */
	public static final int COMMAND_SHOP_BUYGOODS=1402;
	/* ͨ�������-���� */
	/** ����-ǰ̨����-��ȡ�����б� */
	public static final int COMMAND_TASK_TASKLIST=1501;
	/** ����-ǰ̨����-������� */
	public static final int COMMAND_TASK_FINISHTASK=1502;
	/* ͨ�������-��Ƭͼ�� */
	/** ��Ƭͼ��-ǰ̨����-��ȡ��Ƭ�б� */
	public static final int COMMAND_CARD_CARDLIST=1601;
	/** ����-ǰ̨����-ˢ�¿��Ƽ��� */
	public static final int COMMAND_CARD_FLUSHSKILL=1602;
	/** ����-ǰ̨����-���濨�Ƽ��� */
	public static final int COMMAND_CARD_SAVESKILL=1603;
	/** ����-ǰ̨����-�������� */
	public static final int COMMAND_CARD_ENGULF=1604;
	/** ����-ǰ̨����-�������� */
	public static final int COMMAND_CARD_LOCK=1605;
	/** ����-ǰ̨����-�������� */
	public static final int COMMAND_CARD_FORSTER=1606;
	/** ����-ǰ̨����-���ÿ������� */
	public static final int COMMAND_CARD_RESETFORSTER=1607;
	/** ����-ǰ̨����-���� */
	public static final int COMMAND_CARD_SELL=1608;
	/**���� ��������*/
	public static final int COMMAND_CARD_FORSTER_SAVE=1609;
	
	/* ͨ�������-������� */
	/** �������-ǰ̨����-������� */
	public static final int COMMAND_USEGOLD_TOKEN=1701;
	/** �������-ǰ̨����-���򱳰� */
	public static final int COMMAND_USEGOLD_BAGSIZE=1702;
	/* ͨ�������-�������� */
	/** ��������-ǰ̨����-�һ����� */
	public static final int COMMAND_USEPOINTS_GIFT=1703;
	/* ͨ�������-�ʼ� */
	/** �ʼ�-ǰ̨����-�����ʼ� */
	public static final int COMMAND_MAIL_SEND=1801;
	/** �ʼ�-��̨����-�����ʼ� */
	public static final int COMMAND_MAIL_SEND_BACK=1802;
	/** �ʼ�-ǰ̨����-�鿴�ʼ� */
	public static final int COMMAND_MAIL_SHOW=1803;
	/** �ʼ�-ǰ̨����-����ʼ� */
	public static final int COMMAND_MAIL_BROWSE=1804;
	/** �ʼ�-ǰ̨����-ɾ���ʼ� */
	public static final int COMMAND_MAIL_DELETE=1805;
	/** �ʼ�-ǰ̨����-��ȡ�ʼ��������� */
	public static final int COMMAND_MAIL_TAKE=1806;

	/* ͨ�������-���� */
	/** ����-ǰ̨����-����NPC */
	public static final int COMMAND_INSTANCING_ATT=1901;
	/** ����-ǰ̨����-�������� */
	public static final int COMMAND_ACTIVEINSTANCING_ENTER=1902;
	/** ����-ǰ̨����-���븱�� */
	public static final int COMMAND_INSTANCING_ENTER=1903;
	/** ����-ǰ̨����-��ȡ���� */
	public static int COMMAND_COPY_GETEMBATTLE=1905;

	/* ���Ƴ齱 */
	/** �齱-ˢ�� */
	public static final int COMMAND_LUCKBOX_FLUSH=2001;
	/** �齱-��ȡ���� */
	public static final int COMMAND_LUCKBOX_TAKE=2002;
	/** �齱-���ƹ��� */
	public static final int COMMAND_LUCKBOX_BUY=2003;

	/* ���� */
	/** ����-ǰ̨����-�Ѿ��� */
	public static final int COMMAND_ARMYCAMP_SOUP=2101;
	/** ����-ǰ̨����-������� */
	public static final int COMMAND_ARMYCAMP_ENTER=2102;
	/** ����-ǰ̨����-��Ϣ��¼ */
	public static final int COMMAND_ARMYCAMP_LOG=2103;
	/** ����-ǰ̨����-�����Ѿ� */
	public static final int COMMAND_ARMYCAMP_PAY=2104;
	/** ����-ǰ̨����-�Ƴ����� */
	public static final int COMMAND_ARMYCAMP_REMOVE=2105;
	/** ����-ǰ̨����-�Ⱦ� */
	public static final int COMMAND_ARMYCAMP_DRINK=2106;
	/** ����-ǰ̨����-�й� */
	public static final int COMMAND_ARMYCAMP_WALK=2107;
	/** ����-ǰ̨����-��ѯ������Ϣ */
	public static final int COMMAND_ARMYCAMP_INFO=2108;
	/** ����-��̨����-�Զ��Ƴ����� */
	public static final int COMMAND_ARMYCAMP_REMOVE_BACK=2109;
	/** ����-ǰ̨����-�Զ��Ⱦ� */
	public static final int COMMAND_ARMYCAMP_AUTO=2110;
	/* ���а� */
	/** ���а�-ǰ̨����-�������а� */
	public static final int COMMAND_RANKINGS_ENTER=2201;
	/** ���а�-ǰ̨����-���ظ��� */
	public static final int COMMAND_RANKINGS_LOAD=2202;

	/* ����BOSS */
	/** ����BOSS-ǰ̨����-����BOSS */
	public static final int COMMAND_GLOBALBOSS_ATT=2301;
	/** ����BOSS-ǰ̨����-����ǿ�� */
	public static final int COMMAND_GLOBALBOSS_ATTUP=2302;
	/** ����BOSS-ǰ̨����-�����Զ�ս�� */
	public static final int COMMAND_GLOBALBOSS_AUTOON=2303;
	/** ����BOSS-ǰ̨����-ȡ���Զ�ս�� */
	public static final int COMMAND_GLOBALBOSS_AUTOOFF=2304;
	/** ����BOSS-ǰ̨����-��������BOSS� */
	public static final int COMMAND_GLOBALBOSS_MAIN=2305;
	/** ����BOSS-ǰ̨����-���總�� */
	public static final int COMMAND_GLOBALBOSS_RELIVE=2306;
	/** ����BOSS-��̨����-�Զ�ս���������� */
	public static final int COMMAND_GLOBALBOSS_BACK_AUTOATT=2307;
	/** ����BOSS-��̨����-���ʼ���� */
	public static final int COMMAND_GLOBALBOSS_BACK_START=2308;
	/** ����BOSS-��̨����-��������� */
	public static final int COMMAND_GLOBALBOSS_BACK_END=2309;
	/**/
	/* ���ܴ����� */
	/** �չ��ܴ����� */
	public static final int PROCESS_NULL=0;
	/* ���ܴ�����-���� */
	/** ����-���ܴ�����-���۵��� */
	public static final int PROCESS_BAG_SELLPROP=3101;
	/* ���ܴ�����-���� */
	/** ����-���ܴ�����-���Ӻ�����������Ϣ */
	public static final int PROCESS_FRIEND_HANDLEAPPLYFRIEND=3201;
	
	/**����  ���ܴ����� ���߾����*/
	public static final int OUTLINE_TOKEN_REISSUE_PORT = 3301;
	
	/* ���ܴ�����-��Ϣ */
	
	/* ���ܴ�����-�̳� */
	/* ���ܴ�����-���� */
	/* ���ܴ�����-��Ƭͼ�� */

	/* �ⲿWEB�����޸Ķ˿� */
	public static final int UPDATE_BASE=10001;
	public static final int UPDATE_PRO=10002;
	public static final int UPDATE_CARD=10003;
}