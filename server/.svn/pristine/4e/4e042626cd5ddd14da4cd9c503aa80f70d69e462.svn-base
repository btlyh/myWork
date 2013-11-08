package com.cambrian.common.field;

public final class CharField extends Field
{

	public char value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof CharField) ((CharField)dest).value=value;
	}

	public Object getValue()
	{
		return new Character(this.value);
	}
}
