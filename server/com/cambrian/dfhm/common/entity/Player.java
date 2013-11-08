package com.cambrian.dfhm.common.entity;

import java.util.ArrayList;
import java.util.Iterator;

import sun.awt.GlobalCursorManager;

import com.cambrian.common.actor.Actor;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.util.ChangeListener;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.armyCamp.entity.ArmyCamp;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.bag.CardBag;
import com.cambrian.dfhm.battle.BattleCard;
import com.cambrian.dfhm.battle.Formation;
import com.cambrian.dfhm.card.Card;
import com.cambrian.dfhm.common.UidFile;
import com.cambrian.dfhm.friend.entity.FriendInfo;
import com.cambrian.dfhm.globalboss.entity.BossFightRecord;
import com.cambrian.dfhm.globalboss.logic.BossManager;
import com.cambrian.dfhm.instancing.entity.AttRecord;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.dfhm.message.MessageContainer;
import com.cambrian.dfhm.slaveAndWar.entity.Identity;
import com.cambrian.dfhm.timer.TokenTimer;

/**
 * ��˵������ɫ��
 * 
 * @version 1.0
 * @author
 */
public class Player extends Sample implements Actor
{

	private static final int UPDATE_PLAYER=0;

	private long userid;
	private String nickname;
	/** ��Ϸ�� */
	private int money;
	/** RMB */
	private int gold;
	/** ��ǰ���� */
	private int curToken;
	/** ������ */
	private int maxtToken;
	/** ��� */
	private int soul;
	/** ������ */
	public ChangeListener listener;
	/** ���Ʊ��� */
	private CardBag cardBag=new CardBag();
	// /** ��Ƭͼ�� */
	// public CardContainer card;
	/** ��ɫ������Ϣ */
	private PlayerInfo playerInfo=new PlayerInfo();
	/** ���� */
	private FriendInfo friendInfo=new FriendInfo();
	/** ���� */
	private ArmyCamp armyCamp=new ArmyCamp();
	/** ��Ϣ,�ڼ���ʱִ�в��� */
	public MessageContainer message;
	// /** ���� */
	// public TaskContainer task;
	public Formation formation=new Formation();
	/** ��ҽ����ѹ���������� */
	private int buyTokenNum;
	/** ��ҵ�ǰVIP�ȼ� */
	private int vipLevel;
	/** �������ʱ���¼ */
	private long logoutTime;
	/** ����ʼ��б� */
	private ArrayList<Mail> mailList=new ArrayList<Mail>();

	/** ������id */
	public long getUserId()
	{
		return userid;
	}

	/** ��ǰ������ս��ͨ����NPC��λ�� */
	private int curIndexForNormalNPC;
	/** ��ǰ������ս��ս����NPC��λ�� */
	private int curIndexForHardNPC;
	/** ��ǰ������ս��Խ����NPC��λ�� */
	private int curIndexForCorssNPC;
	/** ��ҹ�����¼�б� */
	private ArrayList<AttRecord> attRecords=new ArrayList<AttRecord>();
	/** ���BOSSս��¼ */
	private BossFightRecord bfr;
	/** ���ݶ���(������������) */
	private Identity identity;

	/** �������id */
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
	public Identity getIdentity()
	{
		return identity;
	}

	public void setIdentity(Identity identity)
	{
		this.identity=identity;
	}
	/** ������Ϣ */
	public void update()
	{
		if(listener!=null) listener.change(this,UPDATE_PLAYER);
	}

	/** player�ĳ�ʼ�� */
	public void init()
	{
		Card card;
		BattleCard bCard;
		// for (int i = 0; i < 1; i++) {
		card=cardBag.add(10011);
		card.setStatus(Card.ATTACK);
		System.err.println("id !!!======="+card.getId());
		// card.setForsterNumber(100);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),GameCFG.getDSkillById(card.getSkillId()),
			card.getAttRange(),34801,card.getMaxHp(),card.getCurHp(),0,
			card.getAimType(),card.getCritRate(),card.getDodgeRate(),0,
			card.getType());
		formation.changeFormation(0,bCard);

		card=cardBag.add(10012);
		card.setStatus(Card.ATTACK);
		// card.setForsterNumber(100);
		System.err.println("id !!!======="+card.getId());
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),GameCFG.getDSkillById(card.getSkillId()),
			card.getAttRange(),34201,card.getMaxHp(),card.getCurHp(),1,
			card.getAimType(),card.getCritRate(),card.getDodgeRate(),0,
			card.getType());
		formation.changeFormation(1,bCard);

		card=cardBag.add(10013);
		card.setStatus(Card.ATTACK);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),GameCFG.getDSkillById(card.getSkillId()),
			card.getAttRange(),card.getSkillId(),card.getMaxHp(),
			card.getCurHp(),2,card.getAimType(),card.getCritRate(),
			card.getDodgeRate(),0,card.getType());
		formation.changeFormation(2,bCard);

		card=cardBag.add(10014);
		card.setStatus(Card.ATTACK);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),GameCFG.getDSkillById(card.getSkillId()),
			card.getAttRange(),card.getSkillId(),card.getMaxHp(),
			card.getCurHp(),3,card.getAimType(),card.getCritRate(),
			card.getDodgeRate(),0,card.getType());
		formation.changeFormation(3,bCard);

		card=cardBag.add(10015);
		card.setStatus(Card.ATTACK);
		bCard=new BattleCard(card.getId(),card.getName(),card.getAvatar(),
			card.getTinyAvatar(),card.getLevel(),card.getAtt(),
			card.getSkillRate(),GameCFG.getDSkillById(card.getSkillId()),
			card.getAttRange(),card.getSkillId(),card.getMaxHp(),
			card.getCurHp(),4,card.getAimType(),card.getCritRate(),
			card.getDodgeRate(),0,card.getType());
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
		// System.err.println(battleCard.getName()+", index ==="+battleCard.getIndex());
		// }
		// }
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
	public BossFightRecord getBfr()
	{
		return bfr;
	}

	public void setBfr(BossFightRecord bfr)
	{
		this.bfr=bfr;
	}
	/** ������δ���ʼ����� */
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

	/** ���л�(ǰ̨��½ʱ��ȡ������) */
	@Override
	public void bytesWrite(ByteBuffer data)
	{
		System.err.println("-------���л�(ǰ̨��½ʱ��ȡ������)---------");
		data.writeLong(userid);
		data.writeUTF(nickname);
		data.writeInt(money);
		data.writeInt(gold);
		data.writeInt(curToken);
		data.writeInt(maxtToken);
		data.writeInt(soul);
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
	}

	/** ���л�(��dcͨ��) */
	public void dbBytesWrite(ByteBuffer data)
	{
		System.err.println("-------���л�(��dcͨ��)---------");
		data.writeLong(userid);
		data.writeUTF(nickname);
		data.writeInt(money);
		data.writeInt(gold);
		data.writeInt(curToken);
		data.writeInt(maxtToken);
		data.writeInt(soul);
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
//		identity.dbBytesWrite(data);
	}

	/** �����л�(��dcͨ��) */
	public void dbBytesRead(ByteBuffer data)
	{
		System.err.println("-------�����л�(��dcͨ��)---------");
		userid=data.readLong();
		nickname=data.readUTF();
		money=data.readInt();
		gold=data.readInt();
		curToken=data.readInt();
		maxtToken=data.readInt();
		soul=data.readInt();
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
		if(identity==null) identity=new Identity();
//		identity.dbBytesRead(data);
	}

	@Override
	public String toString()
	{
		return "Player [userid="+userid+", nickname="+nickname+"]";
	}

	/**
	 * ��½ʱ��ʼ������
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
	 * ��ʼ����ɫ�� ���е�һЩ������Ϣ
	 * @param data
	 */
	public void initLogin(ByteBuffer data)
	{
		/** ����Ƿ�������BOSS� */
		int bossSid=BossManager.getInstance().getActiveBossSid();
		data.writeInt(bossSid);//BOSSSID
		long readyTime=BossManager.getInstance().getActiveBossTime(bossSid);
		data.writeLong(readyTime);//ʣ��׼��ʱ��
	}
	/* inner class */
}