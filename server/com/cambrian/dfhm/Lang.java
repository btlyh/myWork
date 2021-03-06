package com.cambrian.dfhm;

public final class Lang
{

	/** 程序或策划bug,配置的物品数据错误 */
	public static final String ERROR_1="101";
	/** 程序或策划bug,前台传参数错误 */
	public static final String ERROR_2="102";

	/* 应用服务器端 */
	/** 600；无效通讯 */
	public static final String F600_CNCM="600";
	/** 611；网络错误 */
	public static final String F611_DE="611";
	/** 613；该账号没有激活 */
	public static final String F613_NA="613";
	/** 615；获得连接失败 */
	public static final String F615_GCF="615";

	/* 玩家信息 */
	/** 700；玩家不存在 */
	public static final String F700="700";
	/** 701；游戏币不足 */
	public static final String F701="701";
	/** 702；RMB不足 */
	public static final String F702="702";
	/** 703；购买军令出错 */
	public static final String F703="703";
	/** 704；购买军令的金币和服务端价格不符合 */
	public static final String F704="704";
	/** 705；购买数量达到限制 */
	public static final String F705="705";
	/** 706；当前军令已经是最大,无法购买 */
	public static final String F706="706";
	/** 707；购买背包的金币和服务端价格不符合 */
	public static final String F707="707";
	/** 708；错误的商品id */
	public static final String F708="708";
	/** 709；积分不足 */
	public static final String F709="709";

	/* 战斗 */

	/* 背包 */
	/** 1100；卡牌背包容量已满 */
	public static final String F1100="1100";

	/* 卡牌 */
	/** 1200；卡牌不存在 */
	public static final String F1200="1200";
	/** 1201；卡牌处于特殊状态中，不能出售 */
	public static final String F1201="1201";
	/** 1202; 卡牌背包容量已满 */
	public static final String F1202="1202";
	/** 1203; 卡牌培养点数不足 */
	public static final String F1203="1203";
	/** 1204; 错误培养消费值 */
	public static final String F1204="1204";
	/** 1206; 已处于境界突破状态 */
	public static final String F1206="1206";
	/** 1207; 吞噬卡牌不存在 */
	public static final String F1207="1207";
	/** 1208; 错误的卡牌状态 */
	public static final String F1208="1208";
	/** 1209; 不是境界突破状态 */
	public static final String F1209="1209";
	/** 1210; 不是指定的吞噬卡牌 */
	public static final String F1210="1210";
	/** 1211; 没有选择卡牌吞噬 */
	public static final String F1211="1211";
	/** 1212; 吞噬的卡牌不一样 */
	public static final String F1212="1212";
	/** 1213; 吞噬卡牌，不是相同的技能 */
	public static final String F1213="1213";
	/** 1214; 指定下阵卡牌错误 */
	public static final String F1214="1214";
	/** 1215; 卡牌技能链表错误 */
	public static final String F1215="1215";
	/** 1216; 卡牌技能不存在 */
	public static final String F1216="1216";
	/** 1217; 卡牌技能等级错误 */
	public static final String F1217="1217";
	/** 1218; 卡牌刷新技能所需花费RMB错误 */
	public static final String F1218="1218";
	/** 1219; 卡牌刷新技能RMB不足 */
	public static final String F1219="1219";
	/** 1220; 卡牌保存技能ID不合法 */
	public static final String F1220="1220";
	/** 1221; 出战名额已满 */
	public static final String F1221="1221";
	/** 1222; 错误的出售金钱数 */
	public static final String F1222="1222";
	/** 1223; 卡牌已上阵 */
	public static final String F1223="1223";
	/** 1224; 培养类型不存在 */
	public static final String F1224="1224";
	/** 1225; 锁定个数错误最多为2 */
	public static final String F1225="1225";
	/** 1226; 培养已经保存过了 */
	public static final String F1226="1226";
	/** 1227; 增加值为0的时候不能锁定 */
	public static final String F1227="1227";
	/** 1228 卡牌已经满级不能升级 */
	public static final String F1228="1228";
	/** 1229 境界突破的时候 重复吞噬 */
	public static final String F1229="1229";
	/** 1230 不能出售所有的卡牌，至少保留一张 */
	public static final String F1230="1230";
	/***1231 卡牌满级***/
	public static final String F1231="1231";
	/* 邮件 */
	/** 1301; 发送邮件的玩家姓名不正确 */
	public static final String F1301="1301";
	/** 1302; 发送邮件内容长度不正确 */
	public static final String F1302="1302";
	/** 1303; 查看邮件的位置不正确 */
	public static final String F1303="1303";
	/** 1304; 当前没有邮件 */
	public static final String F1304="1304";
	/** 1306; 收取的邮件不存在 */
	public static final String F1306="1306";
	/** 1307; 当前邮件没有附件 */
	public static final String F1307="1307";
	/** 1308; 此邮件的附件已经收取过了 */
	public static final String F1308="1308";
	/** 1309; 背包的空间不足，无法收取附件 */
	public static final String F1309="1309";
	/** 1310; 没有此人 */
	public static final String F1310="1310";

	/* 副本 */
	/** 1401; 找不到NPC，错误的SID */
	public static final String F1401="1401";
	/** 1402; 还不能攻击此NPC */
	public static final String F1402="1402";
	/** 1403; 已经没有军令了 */
	public static final String F1403="1403";
	/** 1404; 现有军令不够攻击此NPC */
	public static final String F1404="1404";
	/** 1405; 当前副本没有开放 */
	public static final String F1405="1405";
	/** 1406; 当前NPC为挑战NPC已经击杀过不能再次击杀 */
	public static final String F1406="1406";
	/** 1407; 当前NPC挑战次数已经使用殆尽 */
	public static final String F1407="1407";
	/** 1408; 没有此副本 */
	public static final String F1408="1408";
	/** 1409; 副本类型错误 */
	public static final String F1409="1409";
	/** 1410; 没有上阵卡牌 */
	public static final String F1410="1410";
	/** 1415; 背包格子不够*/
	public static final String F1415="1415";

	/** 1411:副本未通关 */
	public static final String F1411="1411";
	/** 1412:副本类型不存在 */
	public static final String F1412="1412";
	/** 1413:军令不足扫荡 */
	public static final String F1413="1413";	
	/**1414:穿越副本挑战次数不足*/
	public static final String F1414="1414";
	/* 好友 */
	/** 1501; 申请好友的名字不正确 */
	public static final String F1501="1501";
	/** 1502; 申请的好友不存在 */
	public static final String F1502="1502";
	/** 1503; 自己好友上限已满 */
	public static final String F1503="1503";
	/** 1504; 对方好友上限已满 */
	public static final String F1504="1504";
	/** 1505; 你已经是对方好友 */
	public static final String F1505="1505";
	/** 1506; 你已经在申请列表中 */
	public static final String F1506="1506";
	/** 1507; 你要审批的好友不在审批列表中 */
	public static final String F1507="1507";
	/** 1508; 你要删除的玩家不是你的好友 */
	public static final String F1508="1508";
	/** 1509; 你要查看的列表不存在 */
	public static final String F1509="1509";
	/** 1510; 不能添加自己为好友 */
	public static final String F1510="1510";
	/** 1511; 对方已申请你为好友 */
	public static final String F1511="1511";

	/* 抽奖 */
	/** 1601; 没有达到抽奖资格 */
	public static final String F1601="1601";
	/** 1602; 武魂刷新锁需要武魂不够 */
	public static final String F1602="1602";
	/** 1603; 抽奖配置不存在 */
	public static final String F1603="1603";
	/** 1604; 付费抽卡的RMB不正确 */
	public static final String F1604="1604";
	/** 1605; 付费抽卡RMB不够 */
	public static final String F1605="1605";
	/** 1606; 直接购买卡牌RMB不够 */
	public static final String F1606="1606";
	/** 1607; 直接购买卡牌的RMB不正确 */
	public static final String F1607="1607";
	/** 1608; 已经购买过了 */
	public static final String F1608="1608";
	/** 1609; 直接购买的卡牌不存在 */
	public static final String F1609="1609";
	/** 1610; 直接购买-VIP等级不足 */
	public static final String F1610="1610";
	/** 1611; 所有卡牌已经抽取完毕 */
	public static final String F1611="1611";
	/** 抽奖刷新的RMB不足*/
	public static final String F1612="1612";
	/** 武魂抽奖 武魂不足*/
	public static final String F1613="1613";
	
	/** 武魂抽奖  武魂和服务器配置不同*/
	public static final String F1614="1614";
	
	

	/* 军帐 */
	/** 1701：姓名错误 */
	public static final String F1701="1701";
	/** 1702：当前卡牌状态不正确 */
	public static final String F1702="1702";
	/** 1703：背包中没有此卡牌 */
	public static final String F1703="1703";
	/** 1704：私人座位满 */
	public static final String F1704="1704";
	/** 1705：公共座位满 */
	public static final String F1705="1705";
	/** 1706：卡牌已经在座位中 */
	public static final String F1706="1706";
	/** 1707：当前座位已有卡牌 */
	public static final String F1707="1707";
	/** 1708：卡牌还在冷却中 */
	public static final String F1708="1708";
	/** 1709：卡牌不再座位中 */
	public static final String F1709="1709";
	/** 1710：所需金币与服务端不服 */
	public static final String F1710="1710";
	/** 1711：该玩家不是你的好友 */
	public static final String F1711="1711";
	/** 1712：该军帐醒酒汤冷却时间未结束 */
	public static final String F1712="1712";
	/** 1713：玩家金币不足以付费醒酒 */
	public static final String F1713="1713";
	/** 1714：玩家VIP等级不足 */
	public static final String F1714="1714";
	/** 1715：卡牌状态错误 */
	public static final String F1715="1715";
	/** 1716：座位号错误 */
	public static final String F1716="1716";
	/** 1717：对方好友列表中已经删除了你 */
	public static final String F1717="1717";

	/* 世界BOSS */
	/** 1801; 世界BOSS尚未开启 */
	public static final String F1801="1801";
	/** 1802; 挑战资格未达标 */
	public static final String F1802="1802";
	/** 1803; 非法操作，现在没有世界BOSS活动 */
	public static final String F1803="1803";
	/** 1804; 强化攻击所需RMB不足 */
	public static final String F1804="1804";
	/** 1805; BOSS不存在 */
	public static final String F1805="1805";
	/** 1806; 复活冷却时间未到 */
	public static final String F1806="1806";
	/** 1807; 世界BOSS已被击杀 */
	public static final String F1807="1807";
	/** 1808; 世界BOSS活动结束，BOSS未被击杀 */
	public static final String F1808="1808";
	/** 1809; 世界BOSS自动战斗VIP等级不足 */
	public static final String F1809="1809";
	/** 1810; 世界BOSS活动时间未到 */
	public static final String F1810="1810";
	/** 1811; 你已经自动报名，不能再次报名 */
	public static final String F1811="1811";
	/** 1812; 自动报名金币不足 */
	public static final String F1812="1812";
	/** 1813; 活动已经开始不能自动报名了 */
	public static final String F1813="1813";
	/** 1814; 复活所需RMB不足 */
	public static final String F1814="1814";
	/** 1815; 你已经复活了，不需要继续复活 */
	public static final String F1815="1815";
	/** 1816; 玩家VIP等级不足以开起自动报名 */
	public static final String F1816="1816";

	/* 排行榜 */
	/** 1901： 排行榜类型错误 */
	public static final String F1901="1901";
	/** 1902： 下标超出范围 */
	public static final String F1902="1902";

	/* 当壕 */
	/** 2201; 马仔身份不能获取攻击对象列表 */
	public static final String F2201="2201";
	/** 2202; 不能直接攻击马仔 */
	public static final String F2202="2202";
	/** 2203; 你的阵上没有战斗卡牌 */
	public static final String F2203="2203";
	/** 2204; 无效的身份,非法操作 */
	public static final String F2204="2204";
	/** 2205; 对方无效身份,非法操作 */
	public static final String F2205="2205";
	/** 2206; 到达攻击次数限制,不能再次攻击 */
	public static final String F2206="2206";
	/** 2207; 现在不是奴隶了 */
	public static final String F2207="2207";
	/** 2208; 到达反抗次数限制,不能再次反抗 */
	public static final String F2208="2208";
	/** 2209; 金币不足,不能赎身 */
	public static final String F2209="2209";
	/** 2210; 求助的好友现在也是奴隶,不能求助 */
	public static final String F2210="2210";
	/** 2211; 救好友次数到达上限 */
	public static final String F2211="2211";
	/** 2212; 拯救陌生人次数到达上限 */
	public static final String F2212="2212";
	/** 2213; 你没有此奴隶了 */
	public static final String F2213="2213";
	/** 2214; 办事次数到达上限 */
	public static final String F2214="2214";
	/** 2215; 托管金币不够 */
	public static final String F2215="2215";
	/** 2216; 奴隶没有在工作中,不能托管 或 速去速回 */
	public static final String F2216="2216";
	/** 2217; 速去速回金币不够 */
	public static final String F2217="2217";
	/** 2218; 奴隶正在办事中，不能释放 */
	public static final String F2218="2218";
	/** 2219; 没有此人 */
	public static final String F2219="2219";
	/** 2220; 此奴隶没有在托管当中*/
	public static final String F2220="2220";
	/** 2221; 此奴隶已经在托管当中*/
	public static final String F2221="2221";
	/** 2222; 金币不足无法购买*/
	public static final String F2222="2222";
	/** 2223; 玩家VIP等级不足以开起托管功能 */
	public static final String F2223="2223";

	/* 商城 */
	/** 1901：商品序号不存在 */
	public static final String F2001="2001";
	/** 1902：所需金币与服务端不服 */
	public static final String F2002="2002";
	/** 1903：玩家金币不足 */
	public static final String F2003="2003";

	/** 2103： 玩家领奖次数不足 */
	public static final String F2103="2103";
	/** 2104:未到领奖时间 */
	public static final String F2104="2104";

	/* 任务（奖励） */
	/** 2401：任务sid错误 */
	public static final String F2401="2401";
	/** 2402：玩家没有这个任务 */
	public static final String F2402="2402";
	/** 2403: 任务未完成 */
	public static final String F2403="2403";

	/* 排位赛 */
	/** 金钱错误 */
	public static final String F2101="2101";
	/** 没有卡牌在阵上 */
	public static final String F2102="2102";
	/** 挑战次数不足 */
	public static final String F2105="2105";
	/** 当日已无法领取 */
	public static final String F2406="2406";

	/**充值金额错误*/
	public static final String F2305="2305";
	
	/*新手引导 卡牌上阵情况错误  没有全部上阵*/
	public static final String F2501="2501";
	/** 9000；网络错误,请重新登录 */
	public static final String F9000_SDE="9000";

//	/** 邮件国际惯例标题 */
//	public static final String TITLE="的邮件";
	/** 系统邮件 */
	public static final String SYSTEM_MAIL_TITLE="系统邮件";
	/** 系统邮件内容 */
	public static final String SYSTEM_MAIL_CONTENT="物品补发";

	/* 当壕功能身份名称 */
	/** 奴隶主名称 */
	public static final String OWNER="土豪";
	/** 自由人名称 */
	public static final String FREEMAN="侠客";
	/** 奴隶名称 */
	public static final String SLAVE="马仔";

}