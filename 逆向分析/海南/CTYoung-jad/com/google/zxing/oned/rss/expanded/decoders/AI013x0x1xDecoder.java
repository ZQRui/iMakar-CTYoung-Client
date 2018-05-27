// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

// Referenced classes of package com.google.zxing.oned.rss.expanded.decoders:
//            AI01weightDecoder, GeneralAppIdDecoder

final class AI013x0x1xDecoder extends AI01weightDecoder
{

    AI013x0x1xDecoder(BitArray bitarray, String s, String s1)
    {
        super(bitarray);
        dateCode = s1;
        firstAIdigits = s;
    }

    private void encodeCompressedDate(StringBuffer stringbuffer, int i)
    {
        int j = generalDecoder.extractNumericValueFromBitArray(i, 16);
        if(j == 38400)
            return;
        stringbuffer.append('(');
        stringbuffer.append(dateCode);
        stringbuffer.append(')');
        i = j % 32;
        int k = j / 32;
        j = k % 12 + 1;
        k /= 12;
        if(k / 10 == 0)
            stringbuffer.append('0');
        stringbuffer.append(k);
        if(j / 10 == 0)
            stringbuffer.append('0');
        stringbuffer.append(j);
        if(i / 10 == 0)
            stringbuffer.append('0');
        stringbuffer.append(i);
    }

    protected void addWeightCode(StringBuffer stringbuffer, int i)
    {
        i /= 0x186a0;
        stringbuffer.append('(');
        stringbuffer.append(firstAIdigits);
        stringbuffer.append(i);
        stringbuffer.append(')');
    }

    protected int checkWeight(int i)
    {
        return i % 0x186a0;
    }

    public String parseInformation()
        throws NotFoundException
    {
        if(information.size != 84)
        {
            throw NotFoundException.getNotFoundInstance();
        } else
        {
            StringBuffer stringbuffer = new StringBuffer();
            encodeCompressedGtin(stringbuffer, 8);
            encodeCompressedWeight(stringbuffer, 48, 20);
            encodeCompressedDate(stringbuffer, 68);
            return stringbuffer.toString();
        }
    }

    private static final int dateSize = 16;
    private static final int headerSize = 8;
    private static final int weightSize = 20;
    private final String dateCode;
    private final String firstAIdigits;
}
