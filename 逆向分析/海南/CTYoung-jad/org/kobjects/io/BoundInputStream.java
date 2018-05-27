// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.kobjects.io;

import java.io.IOException;
import java.io.InputStream;

public class BoundInputStream extends InputStream
{

    public BoundInputStream(InputStream inputstream, int i)
    {
        is = inputstream;
        remaining = i;
    }

    public int available()
        throws IOException
    {
        int i = is.available();
        if(i < remaining)
            return i;
        else
            return remaining;
    }

    public void close()
    {
        try
        {
            is.close();
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public int read()
        throws IOException
    {
        if(remaining <= 0)
        {
            return -1;
        } else
        {
            remaining = remaining - 1;
            return is.read();
        }
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = j;
        if(j > remaining)
            k = remaining;
        i = is.read(abyte0, i, k);
        if(i > 0)
            remaining = remaining - i;
        return i;
    }

    InputStream is;
    int remaining;
}
