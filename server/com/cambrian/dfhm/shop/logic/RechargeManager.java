package com.cambrian.dfhm.shop.logic;

import java.util.HashMap;
import java.util.Map;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;



public class RechargeManager
{

	/* static fields */
	private static RechargeManager instance = new RechargeManager();

	/* static methods */
	public static RechargeManager getInstance()
	{
		return instance;
	}

	/*
	 *用户充值 
	 * 返回 当前的VIP等级
	 * **/
	public Map<String, Object>rechargeGold(Player player ,int gold)
	{
	
		Map<String, Object>rtMap = new HashMap<String, Object>();
		String error = checkRecharge(gold);
		if (error != null)
		{
			throw new DataAccessException(601, error);
		}
		//todo 这里需要一个人民币和金币的换算值 需要策划来定 per值 
		int per =10;
		int  curGold = gold*per;
		
		player.getPlayerInfo().setPayRMB(player.getPlayerInfo().getPayRMB()+curGold);
		player.setGold(player.getGold()+curGold);
		
		int[]viplevel = GameCFG.getVipLevelGold();
		if(player.getVipLevel()==viplevel.length)
		{
			rtMap.put("level",viplevel.length);
			rtMap.put("exp", 0);
			return rtMap;
			//return viplevel.length;
		}	
		
		for (int i = 0; i < viplevel.length; i++)
		{
			if(player.getPlayerInfo().getPayRMB()>viplevel[i]&&player.getPlayerInfo().getPayRMB()<viplevel[i+1])
			{
				//return i ;
				rtMap.put("level", i);
				rtMap.put("exp",viplevel[i+1]-player.getPlayerInfo().getPayRMB());
				return rtMap;
				
			}	
		}
		
		return null;
	}
	
	
	private String checkRecharge(int gold)
	{
		 if(gold<0)
			 return Lang.F2305;
		 
		 return null;
	}
	
	
	
}
