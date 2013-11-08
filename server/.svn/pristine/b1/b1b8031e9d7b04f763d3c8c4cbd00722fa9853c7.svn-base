package com.cambrian.common.field;

public final class ByteField extends Field
{

	public byte value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof ByteField) ((ByteField)dest).value=value;
	}

	public Object getValue()
	{
		return new Byte(this.value);
	}
}
