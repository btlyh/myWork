/**
 * 
 */
package com.cambrian.common.util;

public class ChangeListenerList extends ChangeAdapter
{

	ObjectArray listenerArray=new ObjectArray();

	public int size()
	{
		return this.listenerArray.size();
	}

	public Object[] getListeners()
	{
		return this.listenerArray.getArray();
	}

	public void addListener(ChangeListener paramChangeListener)
	{
		if(paramChangeListener!=null)
			this.listenerArray.add(paramChangeListener);
	}

	public void removeListener(ChangeListener paramChangeListener)
	{
		this.listenerArray.remove(paramChangeListener);
	}

	public void removeListeners()
	{
		this.listenerArray.clear();
	}

	public void change(Object source,int type)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type);
	}

	public void change(Object source,int type,int paramInt2)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type,paramInt2);
	}

	public void change(Object source,int type,int paramInt2,int paramInt3)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type,paramInt2,
				paramInt3);
	}

	public void change(Object source,int type,int paramInt2,int paramInt3,
		int paramInt4)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type,paramInt2,
				paramInt3,paramInt4);
	}

	public void change(Object source,int type,Object paramObject2)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type,
				paramObject2);
	}

	public void change(Object source,int type,Object paramObject2,
		Object paramObject3)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type,
				paramObject2,paramObject3);
	}

	public void change(Object source,int type,Object paramObject2,
		Object paramObject3,Object paramObject4)
	{
		Object[] arrayOfObject=this.listenerArray.getArray();
		for(int i=arrayOfObject.length-1;i>=0;i--)
			((ChangeListener)arrayOfObject[i]).change(source,type,
				paramObject2,paramObject3,paramObject4);
	}
}