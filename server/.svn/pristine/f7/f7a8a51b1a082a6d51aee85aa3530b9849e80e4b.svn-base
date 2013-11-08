package com.cambrian.common.field;

public final class DoubleField extends Field
{

	public double value;

	public void coppy(Field dest)
	{
		super.coppy(dest);
		if(dest instanceof DoubleField) ((DoubleField)dest).value=value;
	}

	public Object getValue()
	{
		return new Double(this.value);
	}
}
