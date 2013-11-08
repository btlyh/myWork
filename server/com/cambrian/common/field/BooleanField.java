package com.cambrian.common.field;

public final class BooleanField extends Field
{

	public boolean value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(!(dest instanceof BooleanField)) return;
		((BooleanField)dest).value=value;
	}

	public Object getValue()
	{
		return new Boolean(this.value);
	}
}
