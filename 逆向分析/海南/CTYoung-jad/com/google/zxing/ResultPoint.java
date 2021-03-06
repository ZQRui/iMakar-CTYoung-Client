// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing;


public class ResultPoint
{

    public ResultPoint(float f, float f1)
    {
        x = f;
        y = f1;
    }

    private static float crossProductZ(ResultPoint resultpoint, ResultPoint resultpoint1, ResultPoint resultpoint2)
    {
        float f = resultpoint1.x;
        float f1 = resultpoint1.y;
        return (resultpoint2.x - f) * (resultpoint.y - f1) - (resultpoint2.y - f1) * (resultpoint.x - f);
    }

    public static float distance(ResultPoint resultpoint, ResultPoint resultpoint1)
    {
        float f = resultpoint.getX() - resultpoint1.getX();
        float f1 = resultpoint.getY() - resultpoint1.getY();
        return (float)Math.sqrt(f * f + f1 * f1);
    }

    public static void orderBestPatterns(ResultPoint aresultpoint[])
    {
        float f = distance(aresultpoint[0], aresultpoint[1]);
        float f1 = distance(aresultpoint[1], aresultpoint[2]);
        float f2 = distance(aresultpoint[0], aresultpoint[2]);
        ResultPoint resultpoint;
        ResultPoint resultpoint1;
        ResultPoint resultpoint2;
        ResultPoint resultpoint3;
        ResultPoint resultpoint4;
        if(f1 >= f && f1 >= f2)
        {
            resultpoint2 = aresultpoint[0];
            resultpoint = aresultpoint[1];
            resultpoint1 = aresultpoint[2];
        } else
        if(f2 >= f1 && f2 >= f)
        {
            resultpoint2 = aresultpoint[1];
            resultpoint = aresultpoint[0];
            resultpoint1 = aresultpoint[2];
        } else
        {
            resultpoint2 = aresultpoint[2];
            resultpoint = aresultpoint[0];
            resultpoint1 = aresultpoint[1];
        }
        resultpoint4 = resultpoint;
        resultpoint3 = resultpoint1;
        if(crossProductZ(resultpoint, resultpoint2, resultpoint1) < 0.0F)
        {
            resultpoint3 = resultpoint;
            resultpoint4 = resultpoint1;
        }
        aresultpoint[0] = resultpoint4;
        aresultpoint[1] = resultpoint2;
        aresultpoint[2] = resultpoint3;
    }

    public boolean equals(Object obj)
    {
        boolean flag1 = false;
        boolean flag = flag1;
        if(obj instanceof ResultPoint)
        {
            obj = (ResultPoint)obj;
            flag = flag1;
            if(x == ((ResultPoint) (obj)).x)
            {
                flag = flag1;
                if(y == ((ResultPoint) (obj)).y)
                    flag = true;
            }
        }
        return flag;
    }

    public final float getX()
    {
        return x;
    }

    public final float getY()
    {
        return y;
    }

    public int hashCode()
    {
        return Float.floatToIntBits(x) * 31 + Float.floatToIntBits(y);
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(25);
        stringbuffer.append('(');
        stringbuffer.append(x);
        stringbuffer.append(',');
        stringbuffer.append(y);
        stringbuffer.append(')');
        return stringbuffer.toString();
    }

    private final float x;
    private final float y;
}
