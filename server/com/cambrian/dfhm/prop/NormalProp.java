package com.cambrian.dfhm.prop;

/**
 * ��˵�����ɶѵ���Ʒ
 * 
 * @version 1.0
 * @author maxw<woldits@qq.com>
 */
public class NormalProp extends Prop
{
	/* static fields */
	/** �ɶѵ����� */
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