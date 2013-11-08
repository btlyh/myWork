package com.cambrian.dfhm.prop;

/**
 * 类说明：可堆叠物品
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class NormalProp extends Prop
{
	/* static fields */
	/** 可堆叠数量 */
	public static final int PILENUM=99;
	
	/* static methods */

	/* fields */
	
	/* constructors */
	
	/* properties */
	
	/* init start */

	/* methods */
	/* (non-Javadoc)
	 * @see com.cambrian.pvz.prop.Prop#getPileCount()
	 */
	@Override
	public int getPileCount()
	{
		return PILENUM;
	}
	/* common methods */

	/* inner class */
}
