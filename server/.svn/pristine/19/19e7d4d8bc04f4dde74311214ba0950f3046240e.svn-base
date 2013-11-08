package com.cambrian.common.field;

public final class FloatField extends Field
{

	public float value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof FloatField) ((FloatField)dest).value=value;
	}

	public Object getValue()
	{
		return new Float(this.value);
	}
}
