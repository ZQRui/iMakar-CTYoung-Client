// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.oned:
//            UPCEANWriter, Code39Reader

public final class Code39Writer extends UPCEANWriter
{

    public Code39Writer()
    {
    }

    private static void toIntArray(int i, int ai[])
    {
        int j = 0;
        while(j < 9) 
        {
            int k;
            if((i & 1 << j) == 0)
                k = 1;
            else
                k = 2;
            ai[j] = k;
            j++;
        }
    }

    public BitMatrix encode(String s, BarcodeFormat barcodeformat, int i, int j, Hashtable hashtable)
        throws WriterException
    {
        if(barcodeformat != BarcodeFormat.CODE_39)
            throw new IllegalArgumentException("Can only encode CODE_39, but got " + barcodeformat);
        else
            return super.encode(s, barcodeformat, i, j, hashtable);
    }

    public byte[] encode(String s)
    {
        int l1 = s.length();
        if(l1 > 80)
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + l1);
        int ai[] = new int[9];
        int l = l1 + 25;
        for(int i = 0; i < l1; i++)
        {
            int i1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(s.charAt(i));
            toIntArray(Code39Reader.CHARACTER_ENCODINGS[i1], ai);
            for(int j1 = 0; j1 < ai.length; j1++)
                l += ai[j1];

        }

        byte abyte0[] = new byte[l];
        toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], ai);
        int j = appendPattern(abyte0, 0, ai, 1);
        int ai1[] = new int[1];
        ai1[0] = 1;
        l = j + appendPattern(abyte0, j, ai1, 0);
        for(int k = l1 - 1; k >= 0; k--)
        {
            int k1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(s.charAt(k));
            toIntArray(Code39Reader.CHARACTER_ENCODINGS[k1], ai);
            l += appendPattern(abyte0, l, ai, 1);
            l += appendPattern(abyte0, l, ai1, 0);
        }

        toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], ai);
        appendPattern(abyte0, l, ai, 1);
        return abyte0;
    }
}
