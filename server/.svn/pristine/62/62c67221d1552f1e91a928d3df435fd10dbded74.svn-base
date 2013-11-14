package com.cambrian.dfhm.friend.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.friend.dao.FriendDao;
import com.cambrian.dfhm.friend.entity.Friend;
import com.cambrian.dfhm.friend.entity.FriendInfo;
import com.cambrian.dfhm.friend.notice.ApplyFriendNotice;
import com.cambrian.dfhm.friend.notice.CheckFriendNotice;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * 类说明：好友管理器
 * 
 * @author：LazyBear
 */
public class FriendManager
{

	/* static fields */
	private static  FriendManager instance=new FriendManager();

	/* static methods */
	public static FriendManager getInstance()
	{
		return instance;
	}

	/* fields */
	/** 好友数据访问对象 */
	FriendDao dao;
	/** 数据服务器 */
	DataServer ds;
	/** 审批推送消息 */
	CheckFriendNotice cfn;
	/** 申请推送消息 */
	ApplyFriendNotice afn;

	/* constructors */

	/* properties */
	public void setFriendDao(FriendDao dao)
	{
		instance.dao=dao;
	}
	public void setDS(DataServer ds)
	{
		instance.ds=ds;
	}
	public void setCheckFriendNotice(CheckFriendNotice cfn)
	{
		instance.cfn=cfn;
	}
	public void setApplyFriendNotice(ApplyFriendNotice afn)
	{
		instance.afn=afn;
	}
	/* init start */

	/* methods */

	/**
	 * 获取审批或者好友列表
	 * 
	 * @param type
	 */
	public List<Friend> getFriendInfoList(Player player,int type)
	{
		String error=checkGetFriendInfoList(type);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		List<Friend> friends=null;
		List<Integer> friendList=null;
		if(type==FriendInfo.TYPE_FRIEND)
		{
			friendList=player.getFriendInfo().getFriendList();
		}
		else if(type==FriendInfo.TYPE_APPLY)
		{
			friendList=player.getFriendInfo().getApplyList();
		}
		if(friendList!=null)
		{
			friends=getList(friendList);
		}
		return friends;
	}

	private String checkGetFriendInfoList(int type)
	{
		if(type!=FriendInfo.TYPE_APPLY&&type!=FriendInfo.TYPE_FRIEND)
		{
			return Lang.F1509;
		}
		return null;
	}

	/**
	 * 删除好友
	 * 
	 * @param player 玩家对象
	 * @param userId 用户ID
	 */
	public void removeFriend(Player player,int userId)
	{
		String error=checkRemoveFriend(player,userId);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		player.getFriendInfo().removeFriendList(userId);
		Session session=ds.getSession(userId);
		if(session!=null)
		{
			Player tarPlayer=(Player)session.getSource();
			tarPlayer.getFriendInfo().removeFriendList(
				(int)player.getUserId());
		}
		else
		{
			FriendInfo friendInfo=dao.getFriendInfo(userId);
			friendInfo.removeFriendList((int)player.getUserId());
			dao.setFriendInfo(friendInfo,userId);
		}
	}
	/**
	 * @param player 玩家对象
	 * @param userId 用户ID
	 * @return
	 */
	private String checkRemoveFriend(Player player,int userId)
	{
		for(Integer integer:player.getFriendInfo().getFriendList())
		{
			if(userId==integer)
			{
				return null;
			}
		}
		return Lang.F1508;
	}
	/**
	 * 审批好友申请
	 * 
	 * @param userId 用户ID
	 * @param isAccept 是否同意成为好友
	 */
	public void checkFriend(Player player,int userId,boolean isAccept)
	{
		String error=checkCheckFriend(player,userId);
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}
		if(isAccept)
		{
			player.getFriendInfo().addFriendList(userId);
			Session session=ds.getSession(userId);
			if(session!=null)
			{
				Player tarPlayer=(Player)session.getSource();
				tarPlayer.getFriendInfo().addFriendList(
					(int)player.getUserId());
				cfn.send(session,new Object[]{isAccept,player.getNickname()});
			}
			else
			{
				FriendInfo friendInfo=dao.getFriendInfo(userId);
				friendInfo.addFriendList((int)player.getUserId());
				dao.setFriendInfo(friendInfo,userId);
			}

		}
		player.getFriendInfo().removeApplyList(userId);

	}

	/**
	 * 检查审批好友申请
	 * 
	 * @param player 玩家对象
	 * @param userId 用户ID
	 * @return
	 */
	private String checkCheckFriend(Player player,int userId)
	{
		for(Integer integer:player.getFriendInfo().getApplyList())
		{
			if(integer==userId)
			{
				return null;
			}
		}
		return Lang.F1507;
	}
	/**
	 * 申请好友
	 * 
	 * @param player 玩家对象
	 * @param name 目标昵称
	 */
	public void applyFirend(Player player,String name)
	{
		Map<String,Object> resultMap=checkApplyFirend(name,player);
		String error=(String)resultMap.get("error");
		if(error!=null)
		{
			throw new DataAccessException(601,error);
		}

		FriendInfo friendInfo=(FriendInfo)resultMap.get("friendInfo");

		friendInfo.addApplyList((int)player.getUserId());

		Session session=ds.getSession(name);
		if(session!=null)
		{
			Player tarPlyer=(Player)session.getSource();
			tarPlyer.setFriendInfo(friendInfo);
			afn.send(session,new Object[]{tarPlyer.getFriendInfo()
				.getApplyList().size()});
		}
		else
		{
			int userId=(Integer)resultMap.get("userId");
			dao.setFriendInfo(friendInfo,userId);
		}
	}

	/**
	 * 检查申请好友
	 * 
	 * @param name 玩家昵称
	 * @return
	 */
	private Map<String,Object> checkApplyFirend(String name,Player player)
	{
		Map<String,Object> mapInfo=new HashMap<String,Object>();
		if(name.length()<1)
		{
			mapInfo.put("error",Lang.F1501);
			return mapInfo;
		}
		if(name.equals(player.getNickname()))
		{
			mapInfo.put("error",Lang.F1510);
			return mapInfo;
		}
		if(player.getFriendInfo().getFriendList().size()==FriendInfo.MAX_FRIENDLIST_SIZE)
		{
			mapInfo.put("error",Lang.F1503);
			return mapInfo;
		}
		Session session=ds.getSession(name);
		int userId=dao.getUserIdByName(name,session);
		if(userId==0)
		{
			mapInfo.put("error",Lang.F1502);
			return mapInfo;
		}
		FriendInfo friendInfo=getFriendInfo(userId,name);
		if(friendInfo.getFriendList().size()==FriendInfo.MAX_FRIENDLIST_SIZE)
		{
			mapInfo.put("error",Lang.F1504);
			return mapInfo;
		}
		for(Integer integer:friendInfo.getFriendList())
		{
			if((int)player.getUserId()==integer)
			{
				mapInfo.put("error",Lang.F1505);
				return mapInfo;
			}
		}
		for(Integer integer:friendInfo.getApplyList())
		{
			if((int)player.getUserId()==integer)
			{
				mapInfo.put("error",Lang.F1506);
				return mapInfo;
			}
		}
		for(Integer integer:player.getFriendInfo().getApplyList())
		{
			if(integer==userId)
			{
				mapInfo.put("error",Lang.F1511);
				return mapInfo;
			}
		}
		mapInfo.put("friendInfo",friendInfo);
		mapInfo.put("error",null);
		mapInfo.put("userId",userId);
		return mapInfo;
	}

	/**
	 * 获取好友信息
	 * 
	 * @param name 玩家昵称
	 * @return
	 */
	private FriendInfo getFriendInfo(int userId,String name)
	{
		FriendInfo friendInfo=null;
		Session session=ds.getSession(name);
		if(session!=null)
		{
			Player player=(Player)session.getSource();
			if(player!=null)
			{
				if((int)player.getUserId()==userId)
				{
					friendInfo=player.getFriendInfo();
				}
			}
		}
		if(friendInfo==null)
		{
			friendInfo=dao.getFriendInfo(userId);
		}
		return friendInfo;
	}

	/**
	 * 获取好友对象列表
	 * 
	 * @param list
	 * @return
	 */
	private List<Friend> getList(List<Integer> list)
	{
		List<Friend> friendList=new ArrayList<Friend>();
		Player player=null;
		for(Integer integer:list)
		{
			Friend friend=new Friend();
			Session session=ds.getSession(integer);
			if(session!=null)
			{
				player=(Player)session.getSource();
				friend.setFieldFromPlayer(player,true);
			}
			else
			{
				player=dao.getPlayer(integer);
				friend.setFieldFromPlayer(player,false);
			}
			friendList.add(friend);
		}
		return friendList;
	}
}
