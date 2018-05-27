// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.detector.Detector;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.google.zxing.multi.qrcode.detector:
//            MultiFinderPatternFinder

public final class MultiDetector extends Detector
{

    public MultiDetector(BitMatrix bitmatrix)
    {
        super(bitmatrix);
    }

    public DetectorResult[] detectMulti(Hashtable hashtable)
        throws NotFoundException
    {
        hashtable = (new MultiFinderPatternFinder(getImage())).findMulti(hashtable);
        if(hashtable == null || hashtable.length == 0)
            throw NotFoundException.getNotFoundInstance();
        Vector vector = new Vector();
        int i = 0;
        while(i < hashtable.length) 
        {
            DetectorResult adetectorresult[];
            try
            {
                vector.addElement(processFinderPatternInfo(hashtable[i]));
            }
            catch(ReaderException readerexception) { }
            i++;
        }
        if(!vector.isEmpty()) goto _L2; else goto _L1
_L1:
        hashtable = EMPTY_DETECTOR_RESULTS;
_L4:
        return hashtable;
_L2:
        adetectorresult = new DetectorResult[vector.size()];
        i = 0;
        do
        {
            hashtable = adetectorresult;
            if(i >= vector.size())
                continue;
            adetectorresult[i] = (DetectorResult)vector.elementAt(i);
            i++;
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final DetectorResult EMPTY_DETECTOR_RESULTS[] = new DetectorResult[0];

}
