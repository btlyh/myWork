package com.cambrian.dfhm.common.entity;

import java.util.ArrayList;

import com.cambrian.common.actor.Actor;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.timer.TimerCenter;
import com.cambrian.common.timer.TimerEvent;
import com.cambrian.common.util.ChangeListener;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.armyCamp.entity.ArmyCamp;
import com.cambrian.dfhm.armyCamp.logic.ArmyCampManager;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.friend.entity.FriendInfo;
import com.cambrian.dfhm.globalboss.entity.BossFightRecord;
import com.cambrian.dfhm.globalboss.logic.BossManager;
import com.cambrian.dfhm.instancing.entity.AttRecord;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.qualifying.logic.QualifyingManager;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.dfhm.slaveAndWar.entity.Slave;
import com.cambrian.dfhm.task.entity.TaskContainer;
import com.cambrian.dfhm.timer.TokenTimer;

/**
 * 类说明：角色类
 * 
 * @version 1.0
 * @author
 */
public class Player extends Sample implements Actor
{

	private static final int UPDATE_PLAYER=0;


	private long userid;
	private String nickname;
	/** 游戏币 */
	private int money;
	/** RMB */
	private int gold;
	/** 当前军令 */
	private int curToken;
	/** 最大军令 */
	private int maxtToken;
	/** 武魂 */
	private int soul;
	/** 监听者 */
	public ChangeListener listener;
	/** 卡牌背包 */
	private CardBag cardBag=new CardBag();
	// /** 卡片图鉴 */
	// public CardContainer card;
	/** 角色基础信息 */
	private PlayerInfo playerInfo=new PlayerInfo();
	/** 好友 */
	private FriendInfo friendInfo=new FriendInfo();
	/** 军帐 */
	private ArmyCamp armyCamp=new ArmyCamp();
	/** 消息,在加载时执行操作 */
	// /** 任务 */
	public TaskContainer task;
	public Formation formation=new Formation();
	/** 玩家今日已购买军令数量 */
	private int buyTokenNum;
	/** 玩家当前VIP等级 */
	private int vipLevel;
	/** 玩家下线时间记录 */
	private long logoutTime;
	/** 玩家邮件列表 */
	private ArrayList<Mail> mailList=new ArrayList<Mail>();
	/** 玩家任务容器 */
	private TaskContainer tasks=new TaskContainer();

	/** 获得玩家id */
	public long getUserId()
	{
		return userid;
	}
 
	/** 当前可以挑战普通副本NPC的位置 */
	private int curIndexForNormalNPC;
	/** 当前可以挑战挑战副本NPC的位置 */
	private int curIndexForHardNPC;
	/** 当前可以挑战穿越副本NPC的位置 */
	private int curIndexForCorssNPC;
	/** 玩家攻击记录列表 */
	private ArrayList<AttRecord> attRecords=new ArrayList<AttRecord>();
	/** 玩家BOSS战记录 */
	private BossFightRecord bfr;
	/** 身份对象(当土豪功能用) */
	private Identity identity=new Identity();
	
	/**玩家成就系统**/
	private Achievements achievements = new Achievements();
	
	
	
	
	boolean isRefresh = false;

	/** 设置玩家id */
	public void setUserId(long uid)
	{
		this.userid=uid;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname=nickname;
	}

	public int getMoney()
	{
		return money;
	}

	public void setMoney(int money)
	{
		this.money=money;
	}

	public int getGold()
	{
		return gold;
	}

	public void setGold(int gold)
	{
		this.gold=gold;
	}

	public int getCurToken()
	{
		return curToken;
	}

	public void setCurToken(int curToken)
	{
		this.curToken=curToken;
	}

	public int getMaxToken()
	{
		return maxtToken;
	}

	public void setMaxToken(int maxtToken)
	{
		this.maxtToken=maxtToken;
	}

	public int getSoul()
	{
		return soul;
	}

	public void setSoul(int soul)
	{
		this.soul=soul;
	}

	public CardBag getCardBag()
	{
		return cardBag;
	}

	public void setCardBag(CardBag cardBag)
	{
		this.cardBag=cardBag;
	}

	public PlayerInfo getPlayerInfo()
	{
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo)
	{
		this.playerInfo=playerInfo;
	}

	public FriendInfo getFriendInfo()
	{
		return friendInfo;
	}

	public void setFriendInfo(FriendInfo friendInfo)
	{
		this.friendInfo=friendInfo;
	}

	public ArmyCamp getArmyCamp()
	{
		return armyCamp;
	}

	public void setArmyCamp(ArmyCamp armyCamp)
	{
		this.armyCamp=armyCamp;
	}

	public int getBuyTokenNum()
	{
		return buyTokenNum;
	}

	public void setBuyTokenNum(int buyTokenNum)
	{
		this.buyTokenNum=buyTokenNum;
	}

	public int getVipLevel()
	{
		return vipLevel;
	}

	public void setVipLevel(int vipLevel)
	{
		this.vipLevel=vipLevel;
	}

	public long getLogoutTime()
	{
		return logoutTime;
	}

	public void setLogoutTime(long logoutTime)
	{
		this.logoutTime=logoutTime;
	}

	public int getCurIndexForNormalNPC()
	{
		return curIndexForNormalNPC;
	}

	public void setCurIndexForNormalNPC(int curIndexForNormalNPC)
	{
		this.curIndexForNormalNPC=curIndexForNormalNPC;
	}

	public int getCurIndexForHardNPC()
	{
		return curIndexForHardNPC;
	}

	public void setCurIndexForHardNPC(int curIndexForHardNPC)
	{
		this.curIndexForHardNPC=curIndexForHardNPC;
	}

	public int getCurIndexForCorssNPC()
	{
		return curIndexForCorssNPC;
	}

	public void setCurIndexForCorssNPC(int curIndexForCorssNPC)
	{
		this.curIndexForCorssNPC=curIndexForCorssNPC;
	}

	public ArrayList<AttRecord> getAttRecords()
	{
		return attRecords;
	}

	public void setAttRecords(ArrayList<AttRecord> attRecords)
	{
		this.attRecords=attRecords;
	}

	public TaskContainer getTasks()
	{
		return tasks;
	}

	public void setTasks(TaskContainer tasks)
	{
		this.tasks=tasks;
	}

	public Identity getIdentity()
	{
		return identity;
	}

	public void setIdentity(Identity identity)
	{
		this.identity=identity;
	}

	public BossFightRecord getBfr()
	{
		return bfr;
	}

	public void setBfr(BossFightRecord bfr)
	{
		this.bfr=bfr;
	}

	/** 更新消息 */
	public void update()
	{
		if(listener!=null) listener.change(this,UPDATE_PLAYER);
	}

	
	public Achievements getAchievements() {
		return achievements;
	}

	public void setAchievements(Achievements achievements) {
		this.achievements = achievements;
	}

	/** player的初始化 */
	public void init()
	{
		Card card;
		BattleCard bCard;
		// for (int i = 0; i < 1; i++) {
		card=cardBag.add(10051,achievements);
		card.setStatus(Card.NORMAL);
		System.err.println("id !!!======="+card.getId());
		// card.setForsterNumber(100);
		/*bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),card.getAttRange(),card.getSkillId(),
			card.getMaxHp(),card.getCurHp(),0,card.getAimType(),
			card.getCritRate(),card.getDodgeRate(),0,card.getType(),
			card.getSid(),card.getCritFactor());
		formation.changeFormation(0,bCard);*/

		card=cardBag.add(10052,achievements);
		card.setStatus(Card.ATTACK);
		// card.setForsterNumber(100);
		System.err.println("id !!!======="+card.getId());
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),card.getAttRange(),card.getSkillId(),
			card.getMaxHp(),card.getCurHp(),1,card.getAimType(),
			card.getCritRate(),card.getDodgeRate(),0,card.getType(),
			card.getSid(),card.getCritFactor());
		formation.changeFormation(1,bCard);

		card=cardBag.add(10053,achievements);
		card.setStatus(Card.ATTACK);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),card.getAttRange(),card.getSkillId(),
			card.getMaxHp(),card.getCurHp(),2,card.getAimType(),
			card.getCritRate(),card.getDodgeRate(),0,card.getType(),
			card.getSid(),card.getCritFactor());
		formation.changeFormation(2,bCard);

		card=cardBag.add(10054,achievements);
		card.setStatus(Card.ATTACK);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),card.getAttRange(),card.getSkillId(),
			card.getMaxHp(),card.getCurHp(),3,card.getAimType(),
			card.getCritRate(),card.getDodgeRate(),0,card.getType(),
			card.getSid(),card.getCritFactor());
		formation.changeFormation(3,bCard);

		card=cardBag.add(10055,achievements);
		card.setStatus(Card.ATTACK);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),card.getAttRange(),card.getSkillId(),
			card.getMaxHp(),card.getCurHp(),4,card.getAimType(),
			card.getCritRate(),card.getDodgeRate(),0,card.getType(),
			card.getSid(),card.getCritFactor());
		formation.changeFormation(4,bCard);

		// card = cardBag.add(50002);
		// card.uid = 3;
		// card = cardBag.add(50003);
		// card.uid = 4;
		// card = cardBag.add(50004);
		// card.uid = 5;
		// card = cardBag.add(50003);
		// System.err.println("id !!!======="+card.getId());
		// card.init();
		// bCard = new BattleCard(card.getId(), card.getName(),
		// card.getAvatar(), card.getTinyAvatar(),
		// card.getLevel(), card.getAtt(), card.getSkillRate(),
		// card.getDoubleSkill(),
		// card.getAttRange(), card.getSkillId(), card.getMaxHp(),
		// card.getCurHp(), 2,
		// card.getAimType(), card.getCritRate(), card.getDodgeRate(), 0,
		// card.getType(), card.getAttIndex());
		// formation.changeFormation(0, 2, bCard);
		//
		// card = cardBag.add(50002);
		// System.err.println("id !!!======="+card.getId());
		// card.init();
		// bCard = new BattleCard(card.getId(), card.getName(),
		// card.getAvatar(), card.getTinyAvatar(),
		// card.getLevel(), card.getAtt(), card.getSkillRate(),
		// card.getDoubleSkill(),
		// card.getAttRange(), card.getSkillId(), card.getMaxHp(),
		// card.getCurHp(), 3,
		// card.getAimType(), card.getCritRate(), card.getDodgeRate(), 0,
		// card.getType(), card.getAttIndex());
		// formation.changeFormation(0, 3, bCard);
		//
		// card = cardBag.add(50001);
		// System.err.println("id !!!======="+card.getId());
		// card.init();
		// bCard = new BattleCard(card.getId(), card.getName(),
		// card.getAvatar(), card.getTinyAvatar(),
		// card.getLevel(), card.getAtt(), card.getSkillRate(),
		// card.getDoubleSkill(),
		// card.getAttRange(), card.getSkillId(), card.getMaxHp(),
		// card.getCurHp(), 4,
		// card.getAimType(), card.getCritRate(), card.getDodgeRate(), 0,
		// card.getType(), card.getAttIndex());
		// formation.changeFormation(0, 4, bCard);

		// BattleCard[] bCard_ = formation.getFormation(0);
		// for (BattleCard battleCard : bCard_) {
		// System.err.println(battleCard.getName()+", index
		// ==="+battleCard.getIndex());
		// }
		// }
		setVipCfg(); // 初始化VIP配置
		tasks.refreshDaly(this);
	}

	public void incrMoney(int money)
	{
		this.money+=money;
	}

	public void decrMoney(int money)
	{
		this.money-=money;
	}

	public void incrGold(int gold)
	{
		this.gold+=gold;
	}

	public void decrGold(int gold)
	{
		this.gold-=gold;
	}

	public synchronized void decrToken(int token)
	{
		this.curToken-=token;
	}

	public synchronized void incrToken(int token)
	{
		this.curToken+=token;
	}

	public void incrSoul(int soul)
	{
		this.soul+=soul;
	}

	public void decrSoul(int soul)
	{
		this.soul-=soul;
	}

	public ArrayList<Mail> getMailList()
	{
		return mailList;
	}

	/** 获得玩家未读邮件数量 */
	public int getUnreadMailCount()
	{
		int count=0;
		for(Mail mail:mailList)
		{
			if(mail.getState()==Mail.MAILSTATE_UNREAD)
			{
				count++;
			}
		}
		return count;
	}

	public void addMail(Mail mail)
	{
		mailList.add(mail);

	}

	public void addAttRecord(AttRecord attRecord)
	{
		attRecords.add(attRecord);
	}

	/** 提升VIP等级 */
	public void vipLevelUp()
	{
		if(playerInfo.getPayRMB()>GameCFG.getVipLevelGold(vipLevel))
			++vipLevel;
		setVipCfg();
	}

	/**
	 * 计算战斗力
	 */
	public int getFightPoint()
	{
		int fightPoint=0;
		BattleCard[] cards=formation.getFormation();
		for(BattleCard battleCard:cards)
		{
			if(battleCard!=null)
			{
				Card card=cardBag.getById(battleCard.getId());
				fightPoint+=card.getZhandouli();
			}
		}
		return fightPoint;
	}

	/** 设置VIP */
	public void setVipCfg()
	{
		int vipSid=GameCFG.getVipCfg(vipLevel);
		VipCfg vip=(VipCfg)Sample.factory.getSample(vipSid);
		if(vip==null) vip=(VipCfg)Sample.factory.newSample(vipSid);
		refreshVip(vip);
		armyCamp.setPrivateSeatSize(vip.privateSeat);
		armyCamp.setPublicSeatSize(vip.publicSeat);
	}
	/* 需要每日刷新的东西都写在这 */
	/** 服务器固定时间刷新数据 */
	public void refreshDayly()
	{
		int vipSid=GameCFG.getVipCfg(vipLevel);
		VipCfg vip=(VipCfg)Sample.factory.getSample(vipSid);
		if(vip==null) vip=(VipCfg)Sample.factory.newSample(vipSid);
		refreshVip(vip);
		playerInfo.setDuelFreeTimes(10);
		playerInfo.setDuelBuyTimes(0);
		playerInfo.setCanTakePoint(0);
	}
	
	/** 刷新playerInfo中Vip相关属性 */
	private void refreshVip(VipCfg vip)
	{
		playerInfo.setGetToken(vip.getToken);
		playerInfo.setBuyToken(vip.buyToken);
		playerInfo.setSkillFlushFreeTimes(vip.skillFlushFreeTimes);
		playerInfo.setCardGoldForsterFreeTimes(vip.cardGoldForsterFreeTimes);
		playerInfo.setCardGoldForster(vip.cardGoldForster);
		playerInfo.setBattleSkip(vip.battleSkip);
		playerInfo.setPayForAwake(vip.payForAwake);
		playerInfo.setAutoDrink(vip.autoDrink);
		playerInfo.setAutoBossFight(vip.autoBossFight);
		playerInfo.setAutoBossSign(vip.autoBossSign);
	}

	/** 序列化(前台登陆时获取的数据) */
	@Override
	public void bytesWrite(ByteBuffer data)
	{
		System.err.println("-------序列化(前台登陆时获取的数据)---------"); // TODO
																// VIP等级没存没序列化
		data.writeLong(userid);
		data.writeUTF(nickname);
		data.writeInt(money);
		data.writeInt(gold);
		data.writeInt(curToken);
		data.writeInt(maxtToken);
		data.writeInt(soul);
		data.writeInt(vipLevel);
		// System.err.println("userid ==="+userid);
		// System.err.println("nickname ==="+nickname);
		// System.err.println("money ==="+money);
		// System.err.println("gold ==="+gold);
		// System.err.println("curToken ==="+curToken);
		// System.err.println("maxtToken ==="+maxtToken);
		// System.err.println("soul ==="+soul);
		ArrayList<Card> cardList=cardBag.getList();
		data.writeInt(cardBag.getCapacity());
		data.writeInt(cardList.size());
		// System.err.println("cardList.size() ==="+cardList.size());
		for(Card card:cardList)
		{
			card.bytesWrite(data);
		}
		formation.bytesWrite(data);
		data.writeInt(buyTokenNum);
		data.writeInt(mailList.size());
		for(Mail mail:mailList)
		{
			mail.bytesWrite(data);
		}
		data.writeInt(curIndexForNormalNPC);
		data.writeInt(curIndexForHardNPC);
		data.writeInt(curIndexForCorssNPC);
		data.writeInt(attRecords.size());
		for(AttRecord attRecord:attRecords)
		{
			attRecord.bytesWrite(data);
		}
		playerInfo.bytesWrite(data);
		armyCamp.bytesWrite(data);
	//	achievements.bytesWrite(data);
	}

	/** 序列化(和dc通信) */
	public void dbBytesWrite(ByteBuffer data)
	{
		System.err.println("-------序列化(和dc通信)---------");
		data.writeLong(userid);
		data.writeUTF(nickname);
		data.writeInt(money);
		data.writeInt(gold);
		data.writeInt(curToken);
		data.writeInt(maxtToken);
		data.writeInt(soul);
		data.writeInt(vipLevel);
		// System.err.println("userid ==="+userid);
		// System.err.println("nickname ==="+nickname);
		// System.err.println("money ==="+money);
		// System.err.println("gold ==="+gold);
		// System.err.println("curToken ==="+curToken);
		// System.err.println("maxtToken ==="+maxtToken);
		// System.err.println("soul ==="+soul);
		if(cardBag==null) cardBag=new CardBag();
		cardBag.dbBytesWrite(data);
		if(formation==null) formation=new Formation();
		formation.dbBytesWrite(data);
		// data.writeBoolean(realm);
		data.writeInt(buyTokenNum);
		data.writeLong(logoutTime);
		data.writeInt(mailList.size());
		for(Mail mail:mailList)
		{
			mail.dbBytesWrite(data);
		}
		data.writeInt(curIndexForNormalNPC);
		data.writeInt(curIndexForHardNPC);
		data.writeInt(curIndexForCorssNPC);
		data.writeInt(attRecords.size());
		for(AttRecord attRecord:attRecords)
		{
			attRecord.bytesWrite(data);
		}
		if(playerInfo==null) playerInfo=new PlayerInfo();
		playerInfo.dbBytesWrite(data);
		if(friendInfo==null) friendInfo=new FriendInfo();
		friendInfo.dbBytesWrite(data);
		if(armyCamp==null) armyCamp=new ArmyCamp();
		armyCamp.dbBytesWrite(data);
		if(tasks==null) tasks=new TaskContainer();
		tasks.dbBytesWrite(data);
		if(identity==null) identity=new Identity();
		identity.dbBytesWrite(data);
	//	achievements.dbBytesWrite(data);
	}

	/** 反序列化(和dc通信) */
	public void dbBytesRead(ByteBuffer data)
	{
		System.err.println("-------反序列化(和dc通信)---------");
		userid=data.readLong();
		nickname=data.readUTF();
		money=data.readInt();
		gold=data.readInt();
		curToken=data.readInt();
		maxtToken=data.readInt();
		soul=data.readInt();
		vipLevel=data.readInt();
		System.err.println("userid ==="+userid);
		System.err.println("nickname ==="+nickname);
		System.err.println("money ==="+money);
		System.err.println("gold ==="+gold);
		System.err.println("curToken ==="+curToken);
		System.err.println("maxtToken ==="+maxtToken);
		System.err.println("soul ==="+soul);
		if(cardBag==null) cardBag=new CardBag();
		cardBag.dbBytesRead(data);
		if(formation==null) formation=new Formation();
		formation.dbBytesRead(data);
		// realm = data.readBoolean();
		buyTokenNum=data.readInt();
		logoutTime=data.readLong();
		int len=data.readInt();
		for(int i=0;i<len;i++)
		{
			Mail mail=new Mail();
			mail.dbBytesRead(data);
			mailList.add(mail);
		}
		curIndexForNormalNPC=data.readInt();
		curIndexForHardNPC=data.readInt();
		curIndexForCorssNPC=data.readInt();
		len=data.readInt();
		for(int i=0;i<len;i++)
		{
			AttRecord attRecord=new AttRecord();
			attRecord.dbBytesRead(data);
			attRecords.add(attRecord);
		}
		if(playerInfo==null) playerInfo=new PlayerInfo();
		playerInfo.dbBytesRead(data);
		if(friendInfo==null) friendInfo=new FriendInfo();
		friendInfo.dbBytesRead(data);
		if(armyCamp==null) armyCamp=new ArmyCamp();
		armyCamp.dbBytesRead(data);

		if(tasks==null) tasks=new TaskContainer();
		tasks.dbBytesRead(data);
		if(identity==null) identity=new Identity();
		identity.dbBytesRead(data);
	//	achievements.dbBytesRead(data);
	}

	@Override
	public String toString()
	{
		return "Player [userid="+userid+", nickname="+nickname+"]";
	}

	/**
	 * 登陆时初始化军令
	 */
	public void initToken()
	{
		if(logoutTime!=0)
		{
			long times;
			if((times=(TimeKit.nowTimeMills()-logoutTime)
				/TokenTimer.TOKENADDTIME)<1)
			{
				return;
			}
			incrToken((int)times*TokenTimer.TOKENADDNUM);

		}
	}

	/**
	 * 初始化角色后 下行的一些其他信息
	 * 
	 * @param data
	 */
	public void initLogin(ByteBuffer data,Player player)
	{
		/** 检测是否有世界BOSS活动 */

		int bossSid=BossManager.getInstance().getActiveBossSid();
		data.writeInt(bossSid);// BOSSSID
		long readyTime=BossManager.getInstance().getActiveBossTime(bossSid);
		data.writeLong(readyTime);// 剩余准备时间
		long surplusTime=BossManager.getInstance().getSurplusBossTime(
			bossSid);
		data.writeLong(surplusTime);// 活动剩余时间
		if(player.getBfr()!=null&&player.getBfr().isAuto())// 是否有BOSS战斗数据
		{
			data.writeBoolean(true);
			data.writeBoolean(player.getBfr().isAuto());// 是否有自动战斗,如果没有BOSS战斗数据是不接受此数据
		}
		else
		{
			data.writeBoolean(false);
		}
		long nextTokenFullTime=0;
		TimerEvent[] events=TimerCenter.getMillisTimer().getArray();
		for(TimerEvent event:events)
		{
			if(event.getParameter().equals("tokenTimeEvent"))
			{
				nextTokenFullTime=event.getNextTime();
				break;
			}

		}
		data.writeLong(nextTokenFullTime-TimeKit.nowTimeMills());
		/** 登录时遍历军帐 */
		if(nickname!=null)
		{
			ArmyCampManager.getInstance().enterArmyCamp(this,nickname);
			QualifyingManager.getInstance().addPlayer(nickname);
		}

		if(GameCFG.getRewardSampleId().length>player.getPlayerInfo()
			.getRewardNum())
		{
			player.getPlayerInfo().setNextRewardTime(
				TimeKit.nowTimeMills()
					+1000
					*GameCFG.getRewardIntervalTimeByIndex(player
						.getPlayerInfo().getRewardNum()));
		}
		else
		{
			player.getPlayerInfo().setNextRewardTime(0);
		}
		
		if(checkForRefreshDayly())
		{
			refreshDayly();
		}
	}
	/** 检查是否刷新每日数据 */
	private boolean checkForRefreshDayly()
	{
		if (isRefresh) return false;
		long refreshTime = TimeKit.timeOf(GameCFG.getServerTime());
		if(logoutTime < refreshTime-TimeKit.DAY_MILLS)
		{
			isRefresh = true;
			return true;
		}else if (logoutTime < refreshTime)
		{
			if (TimeKit.nowTimeMills() > refreshTime)
			{
				isRefresh = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * 变成奴隶
	 * 
	 * @param ownerId 主人ID
	 * @return
	 */
	public Slave becomeSlave(int ownerId)
	{
		Slave slave=new Slave(nickname,getFightPoint(),ownerId,
			(int)this.userid);
		this.identity.setGrade(Identity.SLAVE);
		this.identity.setOwnerId(ownerId);
		this.identity.getHelpList().clear();
		return slave;
	}

	/**
	 * 恢复自由身
	 */
	public void becomeFreeMan()
	{
		this.identity.setGrade(Identity.FREEMAN);
		this.identity.setOwnerId(0);
	}

	/**
	 * 重写equals方法用于合并比较内存和数据库的PLAYER集合
	 */
	@Override
	public boolean equals(Object paramObject)
	{
		return (int)this.userid==((int)((Player)paramObject).getUserId());
	}

	/* inner class */
}
