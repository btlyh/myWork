package com.cambrian.common.field;

public final class StringField extends Field
{

	public String value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof StringField) ((StringField)dest).value=value;
	}

	public Object getValue()
	{
		return this.value;
	}
}
