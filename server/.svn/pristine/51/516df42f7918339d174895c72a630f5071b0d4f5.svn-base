/**
 * 
 */
package com.cambrian.common.util;

public final class ByteKit
{

	public static final String toString=ByteKit.class.getName();

	public static boolean readBoolean(byte[] paramArrayOfByte,int paramInt)
	{
		return paramArrayOfByte[paramInt]!=0;
	}

	public static byte readByte(byte[] paramArrayOfByte,int paramInt)
	{
		return paramArrayOfByte[paramInt];
	}

	public static int readUnsignedByte(byte[] paramArrayOfByte,int paramInt)
	{
		return paramArrayOfByte[paramInt]&0xFF;
	}

	public static char readChar(byte[] paramArrayOfByte,int paramInt)
	{
		return (char)readUnsignedShort(paramArrayOfByte,paramInt);
	}

	public static char readChar_(byte[] paramArrayOfByte,int paramInt)
	{
		return (char)readUnsignedShort_(paramArrayOfByte,paramInt);
	}

	public static short readShort(byte[] paramArrayOfByte,int paramInt)
	{
		return (short)readUnsignedShort(paramArrayOfByte,paramInt);
	}

	public static short readShort_(byte[] paramArrayOfByte,int paramInt)
	{
		return (short)readUnsignedShort_(paramArrayOfByte,paramInt);
	}

	public static int readUnsignedShort(byte[] paramArrayOfByte,int paramInt)
	{
		return (paramArrayOfByte[(paramInt+1)]&0xFF)
			+((paramArrayOfByte[paramInt]&0xFF)<<8);
	}

	public static int readUnsignedShort_(byte[] paramArrayOfByte,int paramInt)
	{
		return ((paramArrayOfByte[(paramInt+1)]&0xFF)<<8)
			+(paramArrayOfByte[paramInt]&0xFF);
	}

	public static int readInt(byte[] paramArrayOfByte,int paramInt)
	{
		return (paramArrayOfByte[(paramInt+3)]&0xFF)
			+((paramArrayOfByte[(paramInt+2)]&0xFF)<<8)
			+((paramArrayOfByte[(paramInt+1)]&0xFF)<<16)
			+((paramArrayOfByte[paramInt]&0xFF)<<24);
	}

	public static int readInt_(byte[] paramArrayOfByte,int paramInt)
	{
		return ((paramArrayOfByte[(paramInt+3)]&0xFF)<<24)
			+((paramArrayOfByte[(paramInt+2)]&0xFF)<<16)
			+((paramArrayOfByte[(paramInt+1)]&0xFF)<<8)
			+(paramArrayOfByte[paramInt]&0xFF);
	}

	public static float readFloat(byte[] paramArrayOfByte,int paramInt)
	{
		return Float.intBitsToFloat(readInt(paramArrayOfByte,paramInt));
	}

	public static float readFloat_(byte[] paramArrayOfByte,int paramInt)
	{
		return Float.intBitsToFloat(readInt_(paramArrayOfByte,paramInt));
	}

	public static long readLong(byte[] paramArrayOfByte,int paramInt)
	{
		return (paramArrayOfByte[(paramInt+7)]&0xFF)
			+((paramArrayOfByte[(paramInt+6)]&0xFF)<<8)
			+((paramArrayOfByte[(paramInt+5)]&0xFF)<<16)
			+((paramArrayOfByte[(paramInt+4)]&0xFF)<<24)
			+((paramArrayOfByte[(paramInt+3)]&0xFF)<<32)
			+((paramArrayOfByte[(paramInt+2)]&0xFF)<<40)
			+((paramArrayOfByte[(paramInt+1)]&0xFF)<<48)
			+((paramArrayOfByte[paramInt]&0xFF)<<56);
	}

	public static long readLong_(byte[] paramArrayOfByte,int paramInt)
	{
		return ((paramArrayOfByte[(paramInt+7)]&0xFF)<<56)
			+((paramArrayOfByte[(paramInt+6)]&0xFF)<<48)
			+((paramArrayOfByte[(paramInt+5)]&0xFF)<<40)
			+((paramArrayOfByte[(paramInt+4)]&0xFF)<<32)
			+((paramArrayOfByte[(paramInt+3)]&0xFF)<<24)
			+((paramArrayOfByte[(paramInt+2)]&0xFF)<<16)
			+((paramArrayOfByte[(paramInt+1)]&0xFF)<<8)
			+(paramArrayOfByte[paramInt]&0xFF);
	}

	public static double readDouble(byte[] paramArrayOfByte,int paramInt)
	{
		return Double.longBitsToDouble(readLong(paramArrayOfByte,paramInt));
	}

	public static double readDouble_(byte[] paramArrayOfByte,int paramInt)
	{
		return Double.longBitsToDouble(readLong_(paramArrayOfByte,paramInt));
	}

	public static int getReadLength(byte paramByte)
	{
		int i=paramByte&0xFF;
		if(i>=128) return 1;
		if(i>=64) return 2;
		if(i>=32) return 4;
		throw new IllegalArgumentException(toString
			+" getReadLength, invalid number:"+i);
	}

	public static int readLength(byte[] paramArrayOfByte,int paramInt)
	{
		int i=paramArrayOfByte[paramInt]&0xFF;
		if(i>=128) return i-128;
		if(i>=64) return (i<<8)+(paramArrayOfByte[(paramInt+1)]&0xFF)-16384;
		if(i>=32)
		{
			return (i<<24)+((paramArrayOfByte[(paramInt+1)]&0xFF)<<16)
				+((paramArrayOfByte[(paramInt+2)]&0xFF)<<8)
				+(paramArrayOfByte[(paramInt+3)]&0xFF)-536870912;
		}
		throw new IllegalArgumentException(toString
			+" readLength, invalid number:"+i);
	}

	public static void writeBoolean(boolean paramBoolean,
		byte[] paramArrayOfByte,int paramInt)
	{
		paramArrayOfByte[paramInt]=(byte)(paramBoolean?1:0);
	}

	public static void writeByte(byte paramByte,byte[] paramArrayOfByte,
		int paramInt)
	{
		paramArrayOfByte[paramInt]=paramByte;
	}

	public static void writeChar(char paramChar,byte[] paramArrayOfByte,
		int paramInt)
	{
		writeShort((short)paramChar,paramArrayOfByte,paramInt);
	}

	public static void writeChar_(char paramChar,byte[] paramArrayOfByte,
		int paramInt)
	{
		writeShort_((short)paramChar,paramArrayOfByte,paramInt);
	}

	public static void writeShort(short paramShort,byte[] paramArrayOfByte,
		int paramInt)
	{
		paramArrayOfByte[paramInt]=(byte)(paramShort>>>8);
		paramArrayOfByte[(paramInt+1)]=(byte)paramShort;
	}

	public static void writeShort_(short paramShort,byte[] paramArrayOfByte,
		int paramInt)
	{
		paramArrayOfByte[paramInt]=(byte)paramShort;
		paramArrayOfByte[(paramInt+1)]=(byte)(paramShort>>>8);
	}

	public static void writeInt(int paramInt1,byte[] paramArrayOfByte,
		int paramInt2)
	{
		paramArrayOfByte[paramInt2]=(byte)(paramInt1>>>24);
		paramArrayOfByte[(paramInt2+1)]=(byte)(paramInt1>>>16);
		paramArrayOfByte[(paramInt2+2)]=(byte)(paramInt1>>>8);
		paramArrayOfByte[(paramInt2+3)]=(byte)paramInt1;
	}

	public static void writeInt_(int paramInt1,byte[] paramArrayOfByte,
		int paramInt2)
	{
		paramArrayOfByte[paramInt2]=(byte)paramInt1;
		paramArrayOfByte[(paramInt2+1)]=(byte)(paramInt1>>>8);
		paramArrayOfByte[(paramInt2+2)]=(byte)(paramInt1>>>16);
		paramArrayOfByte[(paramInt2+3)]=(byte)(paramInt1>>>24);
	}

	public static void writeFloat(float paramFloat,byte[] paramArrayOfByte,
		int paramInt)
	{
		writeInt(Float.floatToIntBits(paramFloat),paramArrayOfByte,paramInt);
	}

	public static void writeFloat_(float paramFloat,byte[] paramArrayOfByte,
		int paramInt)
	{
		writeInt_(Float.floatToIntBits(paramFloat),paramArrayOfByte,paramInt);
	}

	public static void writeLong(long paramLong,byte[] paramArrayOfByte,
		int paramInt)
	{
		paramArrayOfByte[paramInt]=(byte)(int)(paramLong>>>56);
		paramArrayOfByte[(paramInt+1)]=(byte)(int)(paramLong>>>48);
		paramArrayOfByte[(paramInt+2)]=(byte)(int)(paramLong>>>40);
		paramArrayOfByte[(paramInt+3)]=(byte)(int)(paramLong>>>32);
		paramArrayOfByte[(paramInt+4)]=(byte)(int)(paramLong>>>24);
		paramArrayOfByte[(paramInt+5)]=(byte)(int)(paramLong>>>16);
		paramArrayOfByte[(paramInt+6)]=(byte)(int)(paramLong>>>8);
		paramArrayOfByte[(paramInt+7)]=(byte)(int)paramLong;
	}

	public static void writeLong_(long paramLong,byte[] paramArrayOfByte,
		int paramInt)
	{
		paramArrayOfByte[paramInt]=(byte)(int)paramLong;
		paramArrayOfByte[(paramInt+1)]=(byte)(int)(paramLong>>>8);
		paramArrayOfByte[(paramInt+2)]=(byte)(int)(paramLong>>>16);
		paramArrayOfByte[(paramInt+3)]=(byte)(int)(paramLong>>>24);
		paramArrayOfByte[(paramInt+4)]=(byte)(int)(paramLong>>>32);
		paramArrayOfByte[(paramInt+5)]=(byte)(int)(paramLong>>>40);
		paramArrayOfByte[(paramInt+6)]=(byte)(int)(paramLong>>>48);
		paramArrayOfByte[(paramInt+7)]=(byte)(int)(paramLong>>>56);
	}

	public static void writeDouble(double paramDouble,
		byte[] paramArrayOfByte,int paramInt)
	{
		writeLong(Double.doubleToLongBits(paramDouble),paramArrayOfByte,
			paramInt);
	}

	public static void writeDouble_(double paramDouble,
		byte[] paramArrayOfByte,int paramInt)
	{
		writeLong_(Double.doubleToLongBits(paramDouble),paramArrayOfByte,
			paramInt);
	}

	public static int writeLength(int paramInt1,byte[] paramArrayOfByte,
		int paramInt2)
	{
		if((paramInt1>=536870912)||(paramInt1<0))
			throw new IllegalArgumentException(toString
				+" writeLength, invalid len:"+paramInt1);
		if(paramInt1>=16384)
		{
			writeInt(paramInt1+536870912,paramArrayOfByte,paramInt2);
			return 4;
		}
		if(paramInt1>=128)
		{
			writeShort((short)(paramInt1+16384),paramArrayOfByte,paramInt2);
			return 2;
		}

		writeByte((byte)(paramInt1+128),paramArrayOfByte,paramInt2);
		return 1;
	}

	public static String readISO8859_1(byte[] paramArrayOfByte)
	{
		return readISO8859_1(paramArrayOfByte,0,paramArrayOfByte.length);
	}

	public static String readISO8859_1(byte[] paramArrayOfByte,
		int paramInt1,int paramInt2)
	{
		char[] arrayOfChar=new char[paramInt2];
		int i=paramInt1+paramInt2-1;
		for(int j=arrayOfChar.length-1;i>=paramInt1;j--)
		{
			arrayOfChar[j]=(char)paramArrayOfByte[i];

			i--;
		}
		return new String(arrayOfChar);
	}

	public static byte[] writeISO8859_1(String paramString)
	{
		return writeISO8859_1(paramString,0,paramString.length());
	}

	public static byte[] writeISO8859_1(String paramString,int paramInt1,
		int paramInt2)
	{
		byte[] arrayOfByte=new byte[paramInt2];
		writeISO8859_1(paramString,paramInt1,paramInt2,arrayOfByte,0);
		return arrayOfByte;
	}

	public static void writeISO8859_1(String paramString,int paramInt1,
		int paramInt2,byte[] paramArrayOfByte,int paramInt3)
	{
		int j=paramInt1+paramInt2-1;
		for(int k=paramInt3+paramInt2-1;j>=paramInt1;k--)
		{
			int i=paramString.charAt(j);
			paramArrayOfByte[k]=(i>256?63:(byte)i);

			j--;
		}
	}

	public static void writeISO8859_1(char[] paramArrayOfChar,int paramInt1,
		int paramInt2,byte[] paramArrayOfByte,int paramInt3)
	{
		int j=paramInt1+paramInt2-1;
		for(int k=paramInt3+paramInt2-1;j>=paramInt1;k--)
		{
			int i=paramArrayOfChar[j];
			paramArrayOfByte[k]=(i>256?63:(byte)i);

			j--;
		}
	}

	public static String readUTF(byte[] paramArrayOfByte)
	{
		char[] arrayOfChar=new char[paramArrayOfByte.length];
		int i=readUTF(paramArrayOfByte,0,paramArrayOfByte.length,arrayOfChar);
		return i>=0?new String(arrayOfChar,0,i):null;
	}

	public static String readUTF(byte[] paramArrayOfByte,int paramInt1,
		int paramInt2)
	{
		char[] arrayOfChar=new char[paramInt2];
		int i=readUTF(paramArrayOfByte,paramInt1,paramInt2,arrayOfChar);
		return i>=0?new String(arrayOfChar,0,i):null;
	}

	public static int readUTF(byte[] paramArrayOfByte,int paramInt1,
		int paramInt2,char[] paramArrayOfChar)
	{
		int n=0;
		int i1=paramInt1+paramInt2;
		while(paramInt1<i1)
		{
			int j=paramArrayOfByte[paramInt1]&0xFF;
			int i=j>>4;
			if(i<8)
			{
				paramInt1++;
				paramArrayOfChar[(n++)]=(char)j;
			}
			else
			{
				int k;
				if((i==12)||(i==13))
				{
					paramInt1+=2;
					if(paramInt1>i1) return -1;
					k=paramArrayOfByte[(paramInt1-1)];
					if((k&0xC0)!=128) return -1;
					paramArrayOfChar[(n++)]=(char)((j&0x1F)<<6|k&0x3F);
				}
				else if(i==14)
				{
					paramInt1+=3;
					if(paramInt1>i1) return -1;
					k=paramArrayOfByte[(paramInt1-2)];
					if((k&0xC0)!=128) return -1;
					int m=paramArrayOfByte[(paramInt1-1)];
					if((m&0xC0)!=128) return -1;
					paramArrayOfChar[(n++)]=(char)((j&0xF)<<12|(k&0x3F)<<6|m&0x3F);
				}
				else
				{
					return -1;
				}
			}
		}
		return n;
	}

	public static int getUTFLength(String paramString,int paramInt1,
		int paramInt2)
	{
		int i=0;

		for(int k=paramInt1;k<paramInt2;k++)
		{
			int j=paramString.charAt(k);
			if((j>=1)&&(j<=127))
				i++;
			else if(j>2047)
				i+=3;
			else
				i+=2;
		}
		return i;
	}

	public static int getUTFLength(char[] paramArrayOfChar,int paramInt1,
		int paramInt2)
	{
		int i=0;

		for(int k=paramInt1;k<paramInt2;k++)
		{
			int j=paramArrayOfChar[k];
			if((j>=1)&&(j<=127))
				i++;
			else if(j>2047)
				i+=3;
			else
				i+=2;
		}
		return i;
	}

	public static byte[] writeUTF(String paramString)
	{
		return writeUTF(paramString,0,paramString.length());
	}

	public static byte[] writeUTF(String paramString,int paramInt1,
		int paramInt2)
	{
		byte[] arrayOfByte=new byte[getUTFLength(paramString,paramInt1,
			paramInt2)];
		writeUTF(paramString,paramInt1,paramInt2,arrayOfByte,0);
		return arrayOfByte;
	}

	public static void writeUTF(String paramString,int paramInt1,
		int paramInt2,byte[] paramArrayOfByte,int paramInt3)
	{
		for(int j=paramInt1;j<paramInt2;j++)
		{
			int i=paramString.charAt(j);
			if((i>=1)&&(i<=127))
			{
				paramArrayOfByte[(paramInt3++)]=(byte)i;
			}
			else if(i>2047)
			{
				paramArrayOfByte[(paramInt3++)]=(byte)(0xE0|i>>12&0xF);
				paramArrayOfByte[(paramInt3++)]=(byte)(0x80|i>>6&0x3F);
				paramArrayOfByte[(paramInt3++)]=(byte)(0x80|i&0x3F);
			}
			else
			{
				paramArrayOfByte[(paramInt3++)]=(byte)(0xC0|i>>6&0x1F);
				paramArrayOfByte[(paramInt3++)]=(byte)(0x80|i&0x3F);
			}
		}
	}

	public static void writeUTF(char[] paramArrayOfChar,int paramInt1,
		int paramInt2,byte[] paramArrayOfByte,int paramInt3)
	{
		for(int j=paramInt1;j<paramInt2;j++)
		{
			int i=paramArrayOfChar[j];
			if((i>=1)&&(i<=127))
			{
				paramArrayOfByte[(paramInt3++)]=(byte)i;
			}
			else if(i>2047)
			{
				paramArrayOfByte[(paramInt3++)]=(byte)(0xE0|i>>12&0xF);
				paramArrayOfByte[(paramInt3++)]=(byte)(0x80|i>>6&0x3F);
				paramArrayOfByte[(paramInt3++)]=(byte)(0x80|i&0x3F);
			}
			else
			{
				paramArrayOfByte[(paramInt3++)]=(byte)(0xC0|i>>6&0x1F);
				paramArrayOfByte[(paramInt3++)]=(byte)(0x80|i&0x3F);
			}
		}
	}
}