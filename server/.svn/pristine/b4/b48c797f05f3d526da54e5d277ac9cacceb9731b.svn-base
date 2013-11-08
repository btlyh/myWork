/** */
package com.cambrian.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 类说明：
 * 
 * @version 2013-4-22
 * @author HYZ (huangyz1988@qq.com)
 */
public class TimeKit
{

	/** 一秒钟时间 */
	public static final long SEC_MILLS=1000L;
	/** 一分钟时间 */
	public static final long MIN_MILLS=60*SEC_MILLS;
	/** 一小时时间 */
	public static final long HOUR_MILLS=60*MIN_MILLS;
	/** 半天的时间 */
	public static final long HALF_DAY_MILLS=12*HOUR_MILLS;
	/** 一天时间 */
	public static final long DAY_MILLS=24*HOUR_MILLS;
	/** 一周时间 */
	public static final long WEEK_MILLS=7*DAY_MILLS;
	/** JAVA里这个星期的第一天 */
	public static final int WEEK_FIRST_DAY=1;

	/** 默认时间格式 */
	public static final String FORMAT="yyyyMMdd";

	/** 获得当前时间（毫秒） */
	public static long nowTimeMills()
	{
		return System.currentTimeMillis();
	}

	/** 获得当前时间（秒） */
	public static int nowTime()
	{
		return (int)(System.currentTimeMillis()/1000L);
	}

	/** 毫秒转换为秒 */
	public static int timeSecond(long timeMillis)
	{
		return (int)(timeMillis/1000L);
	}

	/** 秒转换为毫秒 */
	public static long timeMillis(long timeSecond)
	{
		return (timeSecond*1000L);
	}

	/**
	 * 拿到当前时间距离指定天数的凌晨时间(秒) (正数为将来，负数为过去,0则是当天) 例如：-3 表示过去第3天，0 表示当天，3
	 * 表示将来第3天，
	 */
	public static int getDayTime(int dayNum)
	{
		Calendar c=Calendar.getInstance();
		int day=c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH,day+dayNum);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		int time=(int)(c.getTimeInMillis()/1000);
		return time;
	}

	/** 拿到当前时间距离下一次凌晨的时间 */
	public static int fromNextDayTime()
	{
		return getDayTime(1)-(int)(System.currentTimeMillis()/1000);
	}

	/** 获取当天剩余时间 */
	public static int remainOfDay()
	{
		int time=nowTime();
		int lastDayTime=getDayTime(1);
		return time-lastDayTime;
	}

	/** 判断是在同一半天(上午或下午) */
	public static boolean isHalfDay(long time1,long time2)
	{
		if((time2-time1)>=HALF_DAY_MILLS||(time2-time1)<=-HALF_DAY_MILLS)
			return false;
		Calendar cal1=Calendar.getInstance();
		cal1.setTimeInMillis(time1);
		Calendar cal2=Calendar.getInstance();
		cal2.setTimeInMillis(time2);
		if(cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)
			&&cal1.get(Calendar.DAY_OF_YEAR)==cal2.get(Calendar.DAY_OF_YEAR)
			&&cal1.get(Calendar.AM_PM)==cal2.get(Calendar.AM_PM))
			return true;
		return false;
	}

	/** 判断是否为同一天 */
	public static boolean isOneDay(long time1,long time2)
	{
		Calendar cal1=Calendar.getInstance();
		cal1.setTimeInMillis(time1);
		Calendar cal2=Calendar.getInstance();
		cal2.setTimeInMillis(time2);
		if(cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)
			&&cal1.get(Calendar.DAY_OF_YEAR)==cal2.get(Calendar.DAY_OF_YEAR))
			return true;
		return false;
	}

	/** 判断是否为同一周 */
	public static boolean isOneWeek(long time1,long time2)
	{
		Calendar cal1=Calendar.getInstance();
		cal1.setTimeInMillis(time1);
		Calendar cal2=Calendar.getInstance();
		cal2.setTimeInMillis(time2);
		if(cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)
			&&cal1.get(Calendar.WEEK_OF_YEAR)==cal2
				.get(Calendar.WEEK_OF_YEAR)) return true;
		return false;
	}

	/** 判断是否为同一月 */
	public static boolean isOneMonth(long time1,long time2)
	{
		Calendar cal1=Calendar.getInstance();
		cal1.setTimeInMillis(time1);
		Calendar cal2=Calendar.getInstance();
		cal2.setTimeInMillis(time2);
		if(cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)
			&&cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH))
			return true;
		return false;
	}

	/** 获取当天指定小时的时间值（毫秒） */
	public static long timeOf(int hour)
	{
		return timeOf(hour,0);
	}

	/** 获取当天指定小时和分钟的时间值（毫秒） */
	public static long timeOf(int hour,int minute)
	{
		return timeOf(hour,minute,0,0);
	}

	/** 获取当天指定时分秒毫秒的时间值（毫秒） */
	public static long timeOf(int hour,int min,int sec,int mill)
	{
		return timeOf(nowTimeMills(),hour,min,sec,mill);
	}

	/** 获取指定时间当天指定时分秒毫秒的时间值（毫秒） */
	public static long timeOf(long time,int hour,int min,int sec,int mill)
	{
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.setLenient(true);
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,min);
		cal.set(Calendar.SECOND,sec);
		cal.set(Calendar.MILLISECOND,mill);
		return cal.getTimeInMillis();
	}

	/** 获取当前时区指定时间的毫秒为单位的时间值 */
	public static long timeOf(int year,int month,int day,int hour,int min,
		int sec,int mill)
	{
		Calendar cal=Calendar.getInstance();
		cal.setLenient(true);
		cal.set(year,month-1,day,hour,min,sec);
		cal.set(Calendar.MILLISECOND,mill);
		return cal.getTimeInMillis();
	}

	/** 当前时间距离下一个每天固定时间的时间。 */
	public static int getIntervalTime(int time)
	{
		return remainOfDay()+time;
	}

	/** 指定时间转换为字符串表现形式 */
	public static String dateToString(long time,String format)
	{
		try
		{
			SimpleDateFormat sdf=null;
			if(format==null)
				sdf=new SimpleDateFormat(FORMAT);
			else
				sdf=new SimpleDateFormat(format);
			String res=sdf.format(time);
			return res;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/** 获取今天是周几 */
	public static int getDayOfWeek()
	{
		int day=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if(day==WEEK_FIRST_DAY)
			day=7;
		else
			day--;
		return day;
	}

	/** 获取指定时间距离现在的时间差 */
	public static long getTimeFromAssgin(long time)
	{
		return nowTimeMills()-time;
	}
}
