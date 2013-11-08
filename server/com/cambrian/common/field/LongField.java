package com.cambrian.common.field;

public final class LongField extends Field
{

	public long value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof LongField) ((LongField)dest).value=value;
	}

	public Object getValue()
	{
		return new Long(this.value);
	}
}
