package com.cambrian.common.field;

public final class ByteArrayField extends Field
{

	public byte[] value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof ByteArrayField)
			((ByteArrayField)dest).value=value;
	}

	public Object getValue()
	{
		return this.value;
	}
}
