/**
 * 
 */
package com.cambrian.common.util;

public abstract interface Selector
{

	/** ������ѡ�� */
	public static final int FALSE=0;
	/** ����ѡ�� */
	public static final int TRUE=1;
	/** ������ѡ������ */
	public static final int FALSE_BREAK=2;
	/** ����ѡ������ */
	public static final int TRUE_BREAK=3;

	/** ѡ��ָ�����󣬷���ѡ��״̬ */
	public abstract int select(Object paramObject);
}