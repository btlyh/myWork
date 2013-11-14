package com.cambrian.dfhm;

/**
 * 类说明：常量
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public final class GlobalConst
{

	/** 命令处理器改变 */
	public static final int HANDLER_CHANGED=0;
	/** 命令处理器接收端口改变 */
	public static final int PORT_CHANGED=1;

	/* 通信命令号 */
	/** 一般返回消息接收端口 */
	public static final int ACCESS_RETURN_PORT=4;
	/** 获取服务器时间 */
	public static final int TIME_PORT=6;
	/**  */
	public static final int ATTRIBUTE_PORT=11;
	/**  */
	public static final int FILE_PORT=21;
	/**  */
	public static final int AUTHORIZED_FILE_PORT=22;
	/** 认证-获取sid */
	public static final int CC_CERTIFY_PORT=101;
	/** 认证-加载用户数据 */
	public static final int CC_LOAD_PORT=102;
	/** 认证-保持用户活跃 */
	public static final int CC_ACTIVE_PORT=103;
	/** 认证-用户退出 */
	public static final int CC_EXIT_PORT=104;
	/** 数据中心-玩家登陆 */
	public static final int DC_LOGIN_PORT=111;
	/** 数据中心-加载玩家数据 */
	public static final int DC_LOAD_PORT=112;
	/** 数据中心-保持玩家数据 */
	public static final int DC_SAVE_PORT=113;
	/** 数据中心-检查昵称 */
	public static final int DC_CHECKNICKNAME_PORT=114;
	/** 数据中心-获取随机名字 */
	public static final int DC_RANDOMNAME_PORT=115;
	/** 数据中心-更新玩家数据 */
	public static final int DC_UPDATE_PORT=121;
	/**  */
	public static final int CERTIFY_CODE_PORT=201;
	/**  */
	public static final int CERTIFY_PROXY_PORT=202;
	/** 数据服务端-玩家登陆 */
	public static final int LOGIN_PORT=211;
	/** 数据服务端-加载玩家数据 */
	public static final int LOAD_PORT=212;
	/** 数据服务端-玩家退出 */
	public static final int EXIT_PORT=213;
	/** 数据服务端-登陆并获取玩家数据 */
	public static final int COMMAND_CLL_PORT=214;
	/** 获取随机名字 */
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

	/* 战斗相关,前台发送 */
	/** 卡牌-前台发送-修改阵型 */
	public static final int COMMAND_BATTLE_CHANGEFORMATION=902;

	/* 通信命令号 */
	/** 反射,回应ping消息 */
	public static final int ECHO_PORT=1;
	/** 发送ping消息 */
	public static final int PING_PORT=2;

	/* 通信命令号-背包 */
	/** 背包-前台发送-获取道具列表 */
	public static final int COMMAND_BAG_PROPLIST=1101;
	/** 背包-前台发送-出售道具 */
	public static final int COMMAND_BAG_SELLPROP=1102;
	/** 背包-后台发送-添加道具 */
	public static final int COMMAND_BAG_ADDPROP=1103;
	/** 背包-后台发送-移除道具 */
	public static final int COMMAND_BAG_REMOVEPROP=1104;
	/* 通信命令号-好友 */
	/** 好友-前台发送-获取好友列表 */
	public static final int COMMAND_FRIEND_FRIENDLIST=1201;
	/** 好友-前台发送-申请添加 */
	public static final int COMMAND_FRIEND_APPLYFRIEND=1202;
	/** 好友-后台发送-申请推送 */
	public static final int COMMAND_FRIEND_APPLYFRIEND_BACK=1203;
	/** 好友-前台发送-审批好友 */
	public static final int COMMAND_FRIEND_CHECK=1204;
	/** 好友-后台发送-审批推送 */
	public static final int COMMAND_FRIEND_CHECK_BACK=1205;
	/** 好友-前台发送-删除好友 */
	public static final int COMMAND_FRIEND_REMOVE=1206;
	/* 通信命令号-消息 */
	/** 消息-后台间发送-消息 */
	public static final int COMMAND_MESSAGE_MESSAGE=1301;
	/** 消息-前台发送-获取消息列表 */
	public static final int COMMAND_MESSAGE_MESSAGELIST=1302;
	/** 消息-前台发送-读消息 */
	public static final int COMMAND_MESSAGE_READMESSAGE=1303;
	/** 消息-前台发送-处理消息 */
	public static final int COMMAND_MESSAGE_HANDLEMESSAGE=1304;
	/** 消息-前台发送-移除消息 */
	public static final int COMMAND_MESSAGE_REMOVEMESSAGE=1305;
	/** 消息-后台发送-新增消息 */
	public static final int COMMAND_MESSAGE_ADDMESSAGE=1306;
	/* 通信命令号-商城 */
	/** 商城-前台发送-获取商品列表 */
	public static final int COMMAND_SHOP_GOODSLIST=1401;
	/** 商城-前台发送-购买商品 */
	public static final int COMMAND_SHOP_BUYGOODS=1402;
	/* 通信命令号-任务 */
	/** 任务-前台发送-获取任务列表 */
	public static final int COMMAND_TASK_TASKLIST=1501;
	/** 任务-前台发送-完成任务 */
	public static final int COMMAND_TASK_FINISHTASK=1502;
	/* 通信命令号-卡片图鉴 */
	/** 卡片图鉴-前台发送-获取卡片列表 */
	public static final int COMMAND_CARD_CARDLIST=1601;
	/** 卡牌-前台发送-刷新卡牌技能 */
	public static final int COMMAND_CARD_FLUSHSKILL=1602;
	/** 卡牌-前台发送-保存卡牌技能 */
	public static final int COMMAND_CARD_SAVESKILL=1603;
	/** 卡牌-前台发送-卡牌吞噬 */
	public static final int COMMAND_CARD_ENGULF=1604;
	/** 卡牌-前台发送-卡牌锁定 */
	public static final int COMMAND_CARD_LOCK=1605;
	/** 卡牌-前台发送-卡牌培养 */
	public static final int COMMAND_CARD_FORSTER=1606;
	/** 卡牌-前台发送-重置卡牌培养 */
	public static final int COMMAND_CARD_RESETFORSTER=1607;
	/** 卡牌-前台发送-出售 */
	public static final int COMMAND_CARD_SELL=1608;
	/** 卡牌 培养保存 */
	public static final int COMMAND_CARD_FORSTER_SAVE=1609;

	/* 通信命令号-金币消耗 */
	/** 金币消耗-前台发送-购买军令 */
	public static final int COMMAND_USEGOLD_TOKEN=1701;
	/** 金币消耗-前台发送-购买背包 */
	public static final int COMMAND_USEGOLD_BAGSIZE=1702;
	/* 通信命令号-积分消耗 */
	/** 积分消耗-前台发送-兑换积分 */
	public static final int COMMAND_USEPOINTS_GIFT=1703;
	/* 通信命令号-邮件 */
	/** 邮件-前台发送-发送邮件 */
	public static final int COMMAND_MAIL_SEND=1801;
	/** 邮件-后台发送-发送邮件 */
	public static final int COMMAND_MAIL_SEND_BACK=1802;
	/** 邮件-前台发送-查看邮件 */
	public static final int COMMAND_MAIL_SHOW=1803;
	/** 邮件-前台发送-浏览邮件 */
	public static final int COMMAND_MAIL_BROWSE=1804;
	/** 邮件-前台发送-删除邮件 */
	public static final int COMMAND_MAIL_DELETE=1805;
	/** 邮件-前台发送-收取邮件（附件） */
	public static final int COMMAND_MAIL_TAKE=1806;

	/* 通信命令号-副本 */
	/** 副本-前台发送-攻击NPC */
	public static final int COMMAND_INSTANCING_ATT=1901;
	/** 副本-前台发送-进入活动副本 */
	public static final int COMMAND_ACTIVEINSTANCING_ENTER=1902;
	/** 副本-前台发送-进入副本 */
	public static final int COMMAND_INSTANCING_ENTER=1903;
	/** 副本-前台发送-获取布阵 */
	public static int COMMAND_COPY_GETEMBATTLE=1905;

	/* 卡牌抽奖 */
	/** 抽奖-刷新 */
	public static final int COMMAND_LUCKBOX_FLUSH=2001;
	/** 抽奖-抽取卡牌 */
	public static final int COMMAND_LUCKBOX_TAKE=2002;
	/** 抽奖-卡牌购买 */
	public static final int COMMAND_LUCKBOX_BUY=2003;

	/* 军帐 */
	/** 军帐-前台发送-醒酒汤 */
	public static final int COMMAND_ARMYCAMP_SOUP=2101;
	/** 军帐-前台发送-进入军帐 */
	public static final int COMMAND_ARMYCAMP_ENTER=2102;
	/** 军帐-前台发送-信息记录 */
	public static final int COMMAND_ARMYCAMP_LOG=2103;
	/** 军帐-前台发送-付费醒酒 */
	public static final int COMMAND_ARMYCAMP_PAY=2104;
	/** 军帐-前台发送-移除卡牌 */
	public static final int COMMAND_ARMYCAMP_REMOVE=2105;
	/** 军帐-前台发送-喝酒 */
	public static final int COMMAND_ARMYCAMP_DRINK=2106;
	/** 军帐-前台发送-闲逛 */
	public static final int COMMAND_ARMYCAMP_WALK=2107;
	/** 军帐-前台发送-查询卡牌信息 */
	public static final int COMMAND_ARMYCAMP_INFO=2108;
	/** 军帐-后台发送-自动移除卡牌 */
	public static final int COMMAND_ARMYCAMP_REMOVE_BACK=2109;
	/** 军帐-前台发送-自动喝酒 */
	public static final int COMMAND_ARMYCAMP_AUTO=2110;
	/* 排行榜 */
	/** 排行榜-前台发送-进入排行榜 */
	public static final int COMMAND_RANKINGS_ENTER=2201;
	/** 排行榜-前台发送-加载更多 */
	public static final int COMMAND_RANKINGS_LOAD=2202;

	/* 世界BOSS */
	/** 世界BOSS-前台发送-攻击BOSS */
	public static final int COMMAND_GLOBALBOSS_ATT=2301;
	/** 世界BOSS-前台发送-攻击强化 */
	public static final int COMMAND_GLOBALBOSS_ATTUP=2302;
	/** 世界BOSS-前台发送-开启自动战斗 */
	public static final int COMMAND_GLOBALBOSS_AUTOON=2303;
	/** 世界BOSS-前台发送-取消自动战斗 */
	public static final int COMMAND_GLOBALBOSS_AUTOOFF=2304;
	/** 世界BOSS-前台发送-进入世界BOSS活动 */
	public static final int COMMAND_GLOBALBOSS_MAIN=2305;
	/** 世界BOSS-前台发送-春哥附体 */
	public static final int COMMAND_GLOBALBOSS_RELIVE=2306;
	/** 世界BOSS-后台发送-自动战斗数据推送 */
	public static final int COMMAND_GLOBALBOSS_BACK_AUTOATT=2307;
	/** 世界BOSS-后台发送-活动开始推送 */
	public static final int COMMAND_GLOBALBOSS_BACK_START=2308;
	/** 世界BOSS-后台发送-活动结束推送 */
	public static final int COMMAND_GLOBALBOSS_BACK_END=2309;

	/* 排位赛 */
	/** 排位赛-前台发送-进入排位赛 */
	public static final int COMMAND_QUALIFYING_ENTER=2401;
	/** 排位赛-前台发送-挑战对手 */
	public static final int COMMAND_QUALIFYING_DUEL=2402;
	/** 排位赛-前台发送-购买挑战次数 */
	public static final int COMMAND_QUALIFYING_BUY=2403;
	/** 排位赛-前台发送-领取奖励 */
	public static final int COMMAND_QUALIFYING_GIFT=2404;
	/** 排位赛-前台发送-获取记录 */
	public static final int COMMAND_QUALIFYING_LOG=2405;

	/* 商城 */
	/** 商城-前台发送-购买商品 */
	public static final int COMMAND_SHOP_BUY=2501;

	/* 当壕 */
	/** 当壕-前台发送-攻击敌人命令 */
	public static final int COMMAND_SLAVE_ATTENE=2601;
	/** 当壕-前台发送-速去速回 */
	public static final int COMMAND_SLAVE_FASTWORK=2602;
	/** 当壕-前台发送-获取敌人列表 */
	public static final int COMMAND_SLAVE_GETENE=2603;
	/** 当壕-前台发送-赎身 */
	public static final int COMMAND_SLAVE_GETFREE=2604;
	/** 当壕-前台发送-获取好友列表 */
	public static final int COMMAND_SLAVE_GETFRI=2605;
	/** 当壕-前台发送-获取求助列表 */
	public static final int COMMAND_SLAVE_GETHELP=2606;
	/** 当壕-前台发送-获取奴隶列表 */
	public static final int COMMAND_SLAVE_GETSLAVE=2607;
	/** 当壕-前台发送-求助好友 */
	public static final int COMMAND_SLAVE_FORHELP=2608;
	/** 当壕-前台发送-托管 */
	public static final int COMMAND_SLAVE_MANAGED=2609;
	/** 当壕-前台发送-反抗 */
	public static final int COMMAND_SLAVE_REACT=2610;
	/** 当壕-前台发送-救朋友 */
	public static final int COMMAND_SLAVE_SAVEFRI=2611;
	/** 当壕-前台发送-办事 */
	public static final int COMMAND_SLAVE_WORK=2612;
	/** 当壕-后台发送-信息推送 */
	public static final int COMMAND_SLAVE_BACK_MESSAGE=2613;
	/** 当壕-前台发送-获取身份信息 */
	public static final int COMMAND_SLAVE_GETID=2614;
	/**/
	/* 功能处理器 */
	/** 空功能处理器 */
	public static final int PROCESS_NULL=0;
	/* 功能处理器-背包 */
	/** 背包-功能处理器-出售道具 */
	public static final int PROCESS_BAG_SELLPROP=3101;
	/* 功能处理器-好友 */
	/** 好友-功能处理器-添加好友请求结果消息 */
	public static final int PROCESS_FRIEND_HANDLEAPPLYFRIEND=3201;

	/** 军令 功能处理器 离线军令补发 */
	public static final int OUTLINE_TOKEN_REISSUE_PORT=3301;

	/* 功能处理器-消息 */

	/* 功能处理器-商城 */
	/* 功能处理器-任务 */
	/* 功能处理器-卡片图鉴 */

	/* 外部WEB数据修改端口 */
	public static final int UPDATE_BASE=10001;
	public static final int UPDATE_PRO=10002;
	public static final int UPDATE_CARD=10003;
}
