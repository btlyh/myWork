package com.cambrian.dfhm.dc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.LongField;
import com.cambrian.common.field.StringField;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.common.sql.SqlKit;
import com.cambrian.common.util.ArrayList;
import com.cambrian.common.util.ChangeAdapter;
import com.cambrian.common.util.TextKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.instancing.entity.AttRecord;
import com.cambrian.dfhm.mail.entity.Mail;
import com.cambrian.game.cc.SidEncoder;
import com.cambrian.game.dc.DBAccess;
import com.cambrian.game.dc.CheckNameFilter;
import com.cambrian.game.util.DBListManager;

/**
 * 类说明：玩家信息取存
 * 
 * @version 2013-5-13
 * @author HYZ (huangyz1988@qq.com)
 */
public class GameDCAccess extends ChangeAdapter implements DBAccess
{

	private static final Logger log=Logger.getLogger(GameDCAccess.class);
	private static final String EXIST_SQL="select p.userid from player p,user u where p.userid = u.userid and u.username = ";
	private static final String REGEX_CHECKNAME="^[\u4E00-\u9FA5A-Za-z0-9_]{2,10}";
	DBListManager dblist;
	/** Sid编码器 */
	SidEncoder sidEncoder=new SidEncoder();
	ConnectionManager cm;
	CheckNameFilter ckf;

	public void setDBListManager(DBListManager dblist)
	{
		this.dblist=dblist;
	}

	public void setConnectionManager(ConnectionManager cm)
	{
		this.cm=cm;
	}

	public void setCheckNameFilter(CheckNameFilter ckf)
	{
		this.ckf=ckf;
	}

	public boolean canAccess()
	{
		return true;
	}

	/** 检测用户是否有角色 */
	private boolean checkPlayerExist(String userName)
	{
		Fields fields=SqlKit.query(cm,EXIST_SQL+"'"+userName+"'");
		if(fields!=null)
		{
			return true;
		}
		return false;
	}

	/** 检查昵称的正确性 */
	private boolean checkNickName(String nickName) throws Exception
	{
		boolean result=true;
		if(!nickName.matches(REGEX_CHECKNAME))
		{
			result=false;
		}
		BufferedReader br=new BufferedReader(new InputStreamReader(
			new FileInputStream("./txt/mingzi_use.txt"),"UTF-8"));
		String name=br.readLine();
		while(name!=null)
		{
			if(nickName.equals(name))
			{
				result=false;
				break;
			}
			name=br.readLine();
		}
		for(int i=0;i<ckf.filterStr.size();i++)
		{
			if(nickName.contains((String)ckf.filterStr.get(i)))
			{
				result=false;
				break;
			}
		}
		return result;
	}

	/** 保存名字 */
	private synchronized void saveName(String name) throws IOException
	{
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream("./txt/mingzi_use.txt",true),"UTF-8"));
		name+="\r\n";
		bw.write(name,0,name.length());
		bw.flush();
		bw.close();
		ckf.nameList.remove(name);
	}

	public ByteBuffer load(String uid,String sid,String nickName,
		String userName)
	{
		if(log.isInfoEnabled()) log.info("load, uid="+uid+", sid="+sid);
		if(uid==null)
			throw new DataAccessException(
				DataAccessException.SERVER_ACCESS_REFUSED,Lang.F611_DE);
		if(sid==null)
			throw new DataAccessException(
				DataAccessException.SERVER_ACCESS_REFUSED,Lang.F611_DE);
		String str=sidEncoder.parseSid(sid);
		String[] strs=TextKit.split(str,':');
		return load(TextKit.parseLong(uid),strs,nickName,userName);
	}

	public ByteBuffer load(long uid,String[] strs,String nickName,
		String userName)
	{
		Player p=new Player();
		boolean result=checkPlayerExist(userName);
		ByteBuffer data=new ByteBuffer();
		if(result)
		{
			p.setUserId(uid);
			loadPlayer(p,nickName);
		}
		else
		{
			try
			{
				if(!nickName.equals(""))
				{
					if(checkNickName(nickName))
					{
						p.setUserId(uid);
						loadPlayer(p,nickName);
					}
					else
					{
						p.setUserId(-2);
					}
				}
				else
				{
					p.setUserId(-1);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(log.isDebugEnabled()) log.debug("load ok, player="+p);
		p.dbBytesWrite(data);
		return data;
	}

	/** 加载玩家信息 */
	public boolean loadPlayer(Player p,String nickName)
	{
		Field[] array=new Field[13];

		/************ 测试 **********/
		// p.setCurIndexForCorssNPC(99999);
		// p.setCurIndexForNormalNPC(99999);
		/************* end **************/

		array[0]=FieldKit.create("userid",(int)p.getUserId());
		array[1]=FieldKit.create("nickname",p.getNickname());
		array[2]=FieldKit.create("money",p.getMoney());
		array[3]=FieldKit.create("gold",p.getGold());
		array[4]=FieldKit.create("curToken",p.getCurToken());
		array[5]=FieldKit.create("maxToken",p.getCurToken());
		array[6]=FieldKit.create("soul",p.getSoul());
		array[7]=FieldKit.create("logoutTime",p.getLogoutTime());
		array[8]=FieldKit.create("buyTokenNum",p.getBuyTokenNum());
		array[9]=FieldKit.create("vipLevel",p.getVipLevel());
		array[10]=FieldKit.create("curIndexForNormalNPC",
			p.getCurIndexForNormalNPC());
		array[11]=FieldKit.create("curIndexForHardNPC",
			p.getCurIndexForHardNPC());
		array[12]=FieldKit.create("curIndexForCorssNPC",
			p.getCurIndexForCorssNPC());

		Field key=FieldKit.create("userid",(int)p.getUserId());
		Fields fields=new Fields(array);
		int i=DBKit.get("player",cm,key,fields);
		if(i==DBKit.EXCEPTION)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,Lang.F611_DE);
		System.err.println("i ==="+i);
		if(i==DBKit.RESULTLESS)
		{
			initNewPlayer(p,nickName);
			System.out.println("init player   p="+p);
			return true;
		}
		p.setNickname(((StringField)array[1]).value);
		p.setMoney(((IntField)array[2]).value);
		p.setGold(((IntField)array[3]).value);
		p.setCurToken(((IntField)array[4]).value);
		p.setMaxToken(((IntField)array[5]).value);
		p.setSoul(((IntField)array[6]).value);
		p.setLogoutTime(((LongField)array[7]).value);
		p.setBuyTokenNum(((IntField)array[8]).value);
		p.setVipLevel(((IntField)array[9]).value);
		p.setCurIndexForNormalNPC(((IntField)array[10]).value);
		p.setCurIndexForHardNPC(((IntField)array[11]).value);
		p.setCurIndexForCorssNPC(((IntField)array[12]).value);

		loadPlayerVar(p);
		loadPlayerLog(p);
		return false;
	}

	public void initNewPlayer(Player p,String nickName)
	{
		long uid=p.getUserId();
		int serverId=(int)(uid>>32);
		int userid=(int)uid;
		if(log.isInfoEnabled())
			log.info("load ok, initNewPlayer, serverId="+serverId
				+", userid="+userid+", nickName="+p.getNickname());
		// p.setNickname(nickName);// TODO 昵称默认设置
		p.setNickname(nickName);
		p.setMoney(100000);
		p.setGold(100000);
		p.setMaxToken(50);
		p.setCurToken(50);
		p.setSoul(100000);
		p.setCurIndexForNormalNPC(GameCFG.getIndexForNormalNPC());
		p.setVipLevel(5);
		p.init();
		try
		{
			saveName(nickName);
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadPlayerVar(Player p)
	{
		// Field[] array = new Field[2];
		// array[0] = FieldKit.create("userid", (int)p.getUserId());
		// array[1] = FieldKit.create("cardBag", (byte[])null);
		// array[2]=FieldKit.create("task",(byte[])null);
		// array[3]=FieldKit.create("friend",(byte[])null);
		// array[4]=FieldKit.create("plantgun",(byte[])null);
		// array[5]=FieldKit.create("card",(byte[])null);
		// Field key = FieldKit.create("userid", (int)p.getUserId());
		// Fields fields = new Fields(array);
		// int i = DBKit.get("player_varfields",cm,key,fields);
		// if(i == DBKit.EXCEPTION) // ||i==DBKit.RESULTLESS
		// throw new DataAccessException(
		// DataAccessException.SERVER_INTERNAL_ERROR,Lang.F611_DE);
		// array = fields.getFields();
		// // -----背包----
		// byte[] bytes = ((ByteArrayField)array[1]).value;
		// CardBag bag = new CardBag();
		// if(bytes != null && bytes.length > 0)
		// bag.dbBytesRead(new ByteBuffer(bytes));
		// p.setCardBag(bag);
		// // -----任务----
		// bytes=((ByteArrayField)array[2]).value;
		// TaskContainer task=new TaskContainer();
		// if(bytes!=null&&bytes.length>0)
		// task.dbBytesRead(new ByteBuffer(bytes));
		// p.setTask(task);
		// // -----好友列表----
		// bytes=((ByteArrayField)array[3]).value;
		// FriendList friendList=new FriendList();
		// if(bytes!=null&&bytes.length>0)
		// friendList.dbBytesRead(new ByteBuffer(bytes));
		// p.setFriendList(friendList);
		// // -----植物炮----
		// bytes=((ByteArrayField)array[4]).value;
		// Bag plantGunList=new Bag();
		// if(bytes!=null&&bytes.length>0)
		// plantGunList.dbBytesRead(new ByteBuffer(bytes));
		// p.setPlantGunList(plantGunList);
		// // -----卡片图鉴----
		// bytes=((ByteArrayField)array[5]).value;
		// CardContainer card=new CardContainer();
		// if(bytes!=null&&bytes.length>0)
		// card.dbBytesRead(new ByteBuffer(bytes));
		// p.setCard(card);

		// // -----加载玩家邮件----
		ArrayList list=new ArrayList();
		Field[] array=new Field[14];
		array[0]=FieldKit.create("mailId",0L);
		array[1]=FieldKit.create("state",0);
		array[2]=FieldKit.create("sendTime",(String)null);
		array[3]=FieldKit.create("title",(String)null);
		array[4]=FieldKit.create("content",(String)null);
		array[5]=FieldKit.create("cardList",(String)null);
		array[6]=FieldKit.create("gold",0);
		array[7]=FieldKit.create("money",0);
		array[8]=FieldKit.create("token",0);
		array[9]=FieldKit.create("soulPoint",0);
		array[10]=FieldKit.create("normalPoint",0);
		array[11]=FieldKit.create("sendPlayerName",(String)null);
		array[12]=FieldKit.create("isFight",(Boolean)null);
		array[13]=FieldKit.create("isSystem",(Boolean)null);
		Fields fields=new Fields(array);
		DBKit.getAll("t_mail",cm,
			FieldKit.create("userId",(int)p.getUserId()),fields,list);
		for(int i=0;i<list.size();i++)
		{
			Mail mail=new Mail();
			fields=(Fields)list.get(i);
			mail.setMailId(Integer.parseInt(fields.get("mailId").getValue()
				.toString()));
			mail.setState(Integer.parseInt(fields.get("state").getValue()
				.toString()));
			mail.setSendTime(fields.get("sendTime").getValue().toString());
			mail.setTitle(fields.get("title").getValue().toString());
			mail.setContent(fields.get("content").getValue().toString());
			String cardList=fields.get("cardList").getValue().toString();
			if(!cardList.equals("")&&!cardList.equals("0"))
			{
				String[] cardLists=cardList.split("/");
				for(int j=0;j<cardLists.length;j++)
				{
					mail.addCard(Integer.parseInt(cardLists[j]));
				}
			}
			mail.setGold(Integer.parseInt(fields.get("gold").getValue()
				.toString()));
			mail.setMoney(Integer.parseInt(fields.get("money").getValue()
				.toString()));
			mail.setToken(Integer.parseInt(fields.get("token").getValue()
				.toString()));
			mail.setSoulPoint(Integer.parseInt(fields.get("soulPoint")
				.getValue().toString()));
			mail.setNormalPoint(Integer.parseInt(fields.get("normalPoint")
				.getValue().toString()));
			mail.setSendPalyerName(fields.get("sendPlayerName").getValue()
				.toString());
			mail.setFight(Boolean.parseBoolean(fields.get("isFight")
				.getValue().toString()));
			mail.setSystem(Boolean.parseBoolean(fields.get("isSystem")
				.getValue().toString()));
			p.addMail(mail);
		}

		// // -----加载玩家攻击记录----
		list=new ArrayList();
		array=new Field[3];
		array[0]=FieldKit.create("sidNPC",0);
		array[1]=FieldKit.create("attacks",0);
		array[2]=FieldKit.create("type",0);
		fields=new Fields(array);
		DBKit.getAll("attRecord",cm,
			FieldKit.create("userId",(int)p.getUserId()),fields,list);
		for(int i=0;i<list.size();i++)
		{
			AttRecord attRecord=new AttRecord();
			fields=(Fields)list.get(i);
			attRecord.setSidNPC(Integer.parseInt(fields.get("sidNPC")
				.getValue().toString()));
			attRecord.setAttacks(Integer.parseInt(fields.get("attacks")
				.getValue().toString()));
			attRecord.setType(Integer.parseInt(fields.get("type").getValue()
				.toString()));
			p.addAttRecord(attRecord);
		}

		// // -----加载玩家信息----
		array=new Field[7];
		array[0]=FieldKit.create("cardBag",(byte[])null);
		array[1]=FieldKit.create("friendInfo",(byte[])null);
		array[2]=FieldKit.create("armyCamp",(byte[])null);
		array[3]=FieldKit.create("playerInfo",(byte[])null);
		array[4]=FieldKit.create("formation",(byte[])null);
		array[5]=FieldKit.create("tasks",(byte[])null);
		array[6]=FieldKit.create("identity",(byte[])null);
		fields=new Fields(array);
		int i=DBKit.get("player_info",cm,
			FieldKit.create("userId",(int)p.getUserId()),fields);
		if(i==DBKit.EXCEPTION)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,Lang.F611_DE);
		byte[] bytes=((ByteArrayField)array[0]).value;
		p.getCardBag().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[1]).value;
		p.getFriendInfo().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[2]).value;
		p.getArmyCamp().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[3]).value;
		p.getPlayerInfo().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[4]).value;
		p.formation.dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[5]).value;
		p.getTasks().dbBytesRead(new ByteBuffer(bytes));
		bytes=((ByteArrayField)array[6]).value;
		p.getIdentity().dbBytesRead(new ByteBuffer(bytes));
	}

	private void loadPlayerLog(Player p)
	{
		// Field[] array=new Field[10];
		// array[0]=FieldKit.create("userid",(int)p.getUserId());
		// array[1]=FieldKit.create("money",0);
		// array[2]=FieldKit.create("login",0);
		// array[3]=FieldKit.create("bullet",0);
		// array[4]=FieldKit.create("prop",0);
		// array[5]=FieldKit.create("hitting",0);
		// array[6]=FieldKit.create("boss",0);
		// array[7]=FieldKit.create("interacts",0);
		// array[8]=FieldKit.create("zombles",(byte[])null);
		// array[9]=FieldKit.create("seeds",(byte[])null);
		// Field key=FieldKit.create("userid",(int)p.getUserId());
		// Fields fields=new Fields(array);
		// int i=DBKit.get("player_log",cm,key,fields);
		// if(i==DBKit.EXCEPTION)
		// throw new DataAccessException(
		// DataAccessException.SERVER_INTERNAL_ERROR,Lang.F611_DE);
		// array=fields.getFields();
		// p.playerLog.money=((IntField)array[1]).value;
		// p.playerLog.login=((IntField)array[2]).value;
		// p.playerLog.bullet=((IntField)array[3]).value;
		// p.playerLog.prop=((IntField)array[4]).value;
		// p.playerLog.hitting=((IntField)array[5]).value;
		// p.playerLog.boss=((IntField)array[6]).value;
		// p.playerLog.interacts=((IntField)array[7]).value;
		//
		// byte[] bytes=((ByteArrayField)array[8]).value;
		// if(bytes!=null&&bytes.length>0)
		// p.playerLog.dbBytesReadZombles(new ByteBuffer(bytes));
		// bytes=((ByteArrayField)array[9]).value;
		// if(bytes!=null&&bytes.length>0)
		// p.playerLog.dbBytesReadSeeds(new ByteBuffer(bytes));
	}

	/** 玩家信息字段映射 */
	private Fields playerInfoMapping(Player p)
	{
		Field[] array=new Field[8];
		ByteBuffer data=new ByteBuffer();
		p.getCardBag().dbBytesWrite(data);
		array[0]=FieldKit.create("userid",(int)p.getUserId());
		array[1]=FieldKit.create("cardBag",data.toArray());
		data=new ByteBuffer();
		p.getFriendInfo().dbBytesWrite(data);
		array[2]=FieldKit.create("friendInfo",data.toArray());
		data=new ByteBuffer();
		p.getArmyCamp().dbBytesWrite(data);
		array[3]=FieldKit.create("armyCamp",data.toArray());
		data=new ByteBuffer();
		p.getPlayerInfo().dbBytesWrite(data);
		array[4]=FieldKit.create("playerInfo",data.toArray());
		data=new ByteBuffer();
		p.formation.dbBytesWrite(data);
		array[5]=FieldKit.create("formation",data.toArray());
		data=new ByteBuffer();
		p.getTasks().dbBytesWrite(data);
		array[6]=FieldKit.create("tasks",data.toArray());
		data=new ByteBuffer();
		p.getIdentity().dbBytesWrite(data);
		array[7]=FieldKit.create("identity",data.toArray());
		return new Fields(array);

	}

	/** 玩家字段映射 */
	private Fields playerMapping(Player p)
	{
		Field[] array=new Field[13];
		array[0]=FieldKit.create("userid",(int)p.getUserId());
		array[1]=FieldKit.create("nickname",p.getNickname());
		array[2]=FieldKit.create("money",p.getMoney());
		array[3]=FieldKit.create("gold",p.getGold());
		array[4]=FieldKit.create("curToken",p.getCurToken());
		array[5]=FieldKit.create("maxToken",p.getCurToken());
		array[6]=FieldKit.create("soul",p.getSoul());
		array[7]=FieldKit.create("logoutTime",p.getLogoutTime());
		array[8]=FieldKit.create("buyTokenNum",p.getBuyTokenNum());
		array[9]=FieldKit.create("vipLevel",p.getVipLevel());
		array[10]=FieldKit.create("curIndexForNormalNPC",
			p.getCurIndexForNormalNPC());
		array[11]=FieldKit.create("curIndexForHardNPC",
			p.getCurIndexForHardNPC());
		array[12]=FieldKit.create("curIndexForCorssNPC",
			p.getCurIndexForCorssNPC());
		return new Fields(array);
	}

	/** 邮件字段映射 */
	private Fields mailMapping(int userId,Mail mail)
	{
		Field[] array=new Field[15];
		array[0]=FieldKit.create("mailId",mail.getMailId());
		array[1]=FieldKit.create("state",mail.getState());
		array[2]=FieldKit.create("sendTime",mail.getSendTime());
		array[3]=FieldKit.create("title",mail.getTitle());
		array[5]=FieldKit.create("cardList",mail.getCardListStr());
		array[4]=FieldKit.create("content",mail.getContent());
		array[6]=FieldKit.create("gold",mail.getGold());
		array[7]=FieldKit.create("money",mail.getMoney());
		array[8]=FieldKit.create("token",mail.getToken());
		array[9]=FieldKit.create("soulPoint",mail.getSoulPoint());
		array[10]=FieldKit.create("normalPoint",mail.getNormalPoint());
		array[11]=FieldKit.create("userId",userId);
		array[12]=FieldKit.create("sendPlayerName",mail.getSendPalyerName());
		array[13]=FieldKit.create("isFight",mail.isFight());
		array[14]=FieldKit.create("isSystem",mail.isSystem());
		return new Fields(array);
	}

	/** 攻击记录字段映射 */
	private Fields attRecordMapping(long userId,AttRecord attRecord)
	{
		Field[] array=new Field[4];
		array[0]=FieldKit.create("sidNPC",attRecord.getSidNPC());
		array[1]=FieldKit.create("attacks",attRecord.getAttacks());
		array[2]=FieldKit.create("userId",(int)userId);
		array[3]=FieldKit.create("type",attRecord.getType());
		return new Fields(array);
	}

	public boolean save(String id,ByteBuffer data)
	{
		int offset=data.offset();
		// TODO 序列化Player
		Player p=new Player();
		p.dbBytesRead(data);
		data.setOffset(offset);
		return save(p);
	}

	/** 玩家对象存储 */
	public boolean save(Player p)
	{
		Field key=FieldKit.create("userid",(int)p.getUserId());
		Fields playerFields=playerMapping(p);
		DBKit.set("player",cm,key,playerFields);
		saveVar(p,key);
		return true;
	}

	/** 玩家相关信息存储 */
	private void saveVar(Player p,Field key)
	{
		long userId=p.getUserId();
		List<Mail> mailList=p.getMailList();
		for(Mail mail:mailList)
		{
			Fields mailFields=mailMapping((int)userId,mail);
			DBKit.set("t_mail",cm,key,mailFields);
		}

		List<AttRecord> attRecords=p.getAttRecords();
		for(AttRecord attRecord:attRecords)
		{
			Fields attRecordFields=attRecordMapping(userId,attRecord);
			DBKit.set("attrecord",cm,key,attRecordFields);
		}

		Fields playerInfo=playerInfoMapping(p);
		DBKit.set("player_info",cm,key,playerInfo);
	}
}
