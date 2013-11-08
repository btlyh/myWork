package com.cambrian.common.field;

public final class ShortField extends Field
{

	public short value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof ShortField) ((ShortField)dest).value=value;
	}

	public Object getValue()
	{
		return new Short(this.value);
	}
}
