package com.cambrian.dfhm.prop;

import com.cambrian.common.net.DataAccessException;
import com.cambrian.dfhm.common.UidFile;


/**
 * 类说明：不可堆叠物品
 * 
 * @version 1.0
 * @date 2013-5-29
 * @author maxw<woldits@qq.com>
 */
public class UniqueProp extends Prop
{
	/* static fields */

	/* static methods */
	/** 生成并绑定唯一编号 */
	public static boolean bindUid(UniqueProp up)
	{
		if(up.uid!=0) return false;
		up.uid=UidFile.getPropFile().getPlusUid();
		return true;
	}


	/* fields */
	/** 编号 */
	int uid;
	/** 物品生成时间 */
	int creatTime;
	
	/* constructors */

	/* properties */

	/* init start */

	/* methods */
	/* (non-Javadoc)
	 * @see com.cambrian.pvz.prop.Prop#setCount(int)
	 */
	@Override
	public void setCount(int count)
	{
		throw new DataAccessException(500,"UniqueProp prohibit set count");
	}
	
	/* common methods */

	/* inner class */
}
