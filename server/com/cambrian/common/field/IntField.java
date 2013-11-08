package com.cambrian.common.field;

public final class IntField extends Field
{

	public int value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof IntField) ((IntField)dest).value=value;
	}

	public Object getValue()
	{
		return new Integer(this.value);
	}
}
