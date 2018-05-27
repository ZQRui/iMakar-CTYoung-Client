// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

public final class ByteMatrix
{

    public ByteMatrix(int i, int j)
    {
        bytes = (byte[][])Array.newInstance(Byte.TYPE, new int[] {
            j, i
        });
        width = i;
        height = j;
    }

    public void clear(byte byte0)
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
                bytes[i][j] = byte0;

        }

    }

    public byte get(int i, int j)
    {
        return bytes[j][i];
    }

    public byte[][] getArray()
    {
        return bytes;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public void set(int i, int j, byte byte0)
    {
        bytes[j][i] = byte0;
    }

    public void set(int i, int j, int k)
    {
        bytes[j][i] = (byte)k;
    }

    public void set(int i, int j, boolean flag)
    {
        byte abyte0[] = bytes[j];
        if(flag)
            j = 1;
        else
            j = 0;
        abyte0[i] = (byte)j;
    }

    public String toString()
    {
        int i;
        StringBuffer stringbuffer;
        stringbuffer = new StringBuffer(width * 2 * height + 2);
        i = 0;
_L5:
        int j;
        if(i >= height)
            break; /* Loop/switch isn't completed */
        j = 0;
_L2:
        if(j >= width)
            break MISSING_BLOCK_LABEL_106;
        switch(bytes[i][j])
        {
        default:
            stringbuffer.append("  ");
            break;

        case 0: // '\0'
            break; /* Loop/switch isn't completed */

        case 1: // '\001'
            break MISSING_BLOCK_LABEL_96;
        }
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        stringbuffer.append(" 0");
          goto _L3
        stringbuffer.append(" 1");
          goto _L3
        stringbuffer.append('\n');
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        return stringbuffer.toString();
    }

    private final byte bytes[][];
    private final int height;
    private final int width;
}
