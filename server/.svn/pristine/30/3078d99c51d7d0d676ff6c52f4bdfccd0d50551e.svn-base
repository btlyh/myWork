package com.cambrian.dfhm.back;

import com.cambrian.common.codec.MD5;
import com.cambrian.common.util.TimeKit;

/**
 * ÀàËµÃ÷£º
 * 
 * @version 2013-5-14
 * @author HYZ (huangyz1988@qq.com)
 */
public class Flag
{

	public static void main(String[] args)
	{
		String time=""+TimeKit.nowTimeMills();
		String flag=MD5.getValue("user:pass:serverid"+time+"pvzkey");
		String url="http://192.168.1.202:20585/certify?up=user:pass:serverid&&time="
			+time+"&&flag="+flag+"&&jsoncallback=json"+time;
		System.out.println(url);
	}
}
