// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.DataCharacter;
import java.util.Vector;

// Referenced classes of package com.google.zxing.oned.rss.expanded:
//            ExpandedPair

final class BitArrayBuilder
{

    private BitArrayBuilder()
    {
    }

    static BitArray buildBitArray(Vector vector)
    {
        int j = (vector.size() << 1) - 1;
        int i = j;
        if(((ExpandedPair)vector.lastElement()).getRightChar() == null)
            i = j - 1;
        BitArray bitarray = new BitArray(i * 12);
        i = 0;
        int j1 = ((ExpandedPair)vector.elementAt(0)).getRightChar().getValue();
        for(int k = 11; k >= 0; k--)
        {
            if((1 << k & j1) != 0)
                bitarray.set(i);
            i++;
        }

        for(int k1 = 1; k1 < vector.size();)
        {
            ExpandedPair expandedpair = (ExpandedPair)vector.elementAt(k1);
            int l1 = expandedpair.getLeftChar().getValue();
            for(int l = 11; l >= 0; l--)
            {
                if((1 << l & l1) != 0)
                    bitarray.set(i);
                i++;
            }

            int i1 = i;
            if(expandedpair.getRightChar() != null)
            {
                int j2 = expandedpair.getRightChar().getValue();
                int i2 = 11;
                do
                {
                    i1 = i;
                    if(i2 < 0)
                        break;
                    if((1 << i2 & j2) != 0)
                        bitarray.set(i);
                    i++;
                    i2--;
                } while(true);
            }
            k1++;
            i = i1;
        }

        return bitarray;
    }
}
