// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.actionbarsherlock.internal.nineoldandroids.animation;

import java.util.HashMap;

// Referenced classes of package com.actionbarsherlock.internal.nineoldandroids.animation:
//            ValueAnimator, PropertyValuesHolder, TypeEvaluator, Animator

public final class ObjectAnimator extends ValueAnimator
{

    public ObjectAnimator()
    {
    }

    private ObjectAnimator(Object obj, String s)
    {
        mTarget = obj;
        setPropertyName(s);
    }

    public static transient ObjectAnimator ofFloat(Object obj, String s, float af[])
    {
        obj = new ObjectAnimator(obj, s);
        ((ObjectAnimator) (obj)).setFloatValues(af);
        return ((ObjectAnimator) (obj));
    }

    public static transient ObjectAnimator ofInt(Object obj, String s, int ai[])
    {
        obj = new ObjectAnimator(obj, s);
        ((ObjectAnimator) (obj)).setIntValues(ai);
        return ((ObjectAnimator) (obj));
    }

    public static transient ObjectAnimator ofObject(Object obj, String s, TypeEvaluator typeevaluator, Object aobj[])
    {
        obj = new ObjectAnimator(obj, s);
        ((ObjectAnimator) (obj)).setObjectValues(aobj);
        ((ObjectAnimator) (obj)).setEvaluator(typeevaluator);
        return ((ObjectAnimator) (obj));
    }

    public static transient ObjectAnimator ofPropertyValuesHolder(Object obj, PropertyValuesHolder apropertyvaluesholder[])
    {
        ObjectAnimator objectanimator = new ObjectAnimator();
        objectanimator.mTarget = obj;
        objectanimator.setValues(apropertyvaluesholder);
        return objectanimator;
    }

    void animateValue(float f)
    {
        animateValue(f);
        int j = mValues.length;
        for(int i = 0; i < j; i++)
            mValues[i].setAnimatedValue(mTarget);

    }

    public volatile Animator clone()
    {
        return clone();
    }

    public ObjectAnimator clone()
    {
        return (ObjectAnimator)clone();
    }

    public volatile ValueAnimator clone()
    {
        return clone();
    }

    public volatile Object clone()
        throws CloneNotSupportedException
    {
        return clone();
    }

    public String getPropertyName()
    {
        return mPropertyName;
    }

    public Object getTarget()
    {
        return mTarget;
    }

    void initAnimation()
    {
        if(!mInitialized)
        {
            int j = mValues.length;
            for(int i = 0; i < j; i++)
                mValues[i].setupSetterAndGetter(mTarget);

            initAnimation();
        }
    }

    public volatile Animator setDuration(long l)
    {
        return setDuration(l);
    }

    public ObjectAnimator setDuration(long l)
    {
        setDuration(l);
        return this;
    }

    public volatile ValueAnimator setDuration(long l)
    {
        return setDuration(l);
    }

    public transient void setFloatValues(float af[])
    {
        if(mValues == null || mValues.length == 0)
        {
            setValues(new PropertyValuesHolder[] {
                PropertyValuesHolder.ofFloat(mPropertyName, af)
            });
            return;
        } else
        {
            setFloatValues(af);
            return;
        }
    }

    public transient void setIntValues(int ai[])
    {
        if(mValues == null || mValues.length == 0)
        {
            setValues(new PropertyValuesHolder[] {
                PropertyValuesHolder.ofInt(mPropertyName, ai)
            });
            return;
        } else
        {
            setIntValues(ai);
            return;
        }
    }

    public transient void setObjectValues(Object aobj[])
    {
        if(mValues == null || mValues.length == 0)
        {
            setValues(new PropertyValuesHolder[] {
                PropertyValuesHolder.ofObject(mPropertyName, (TypeEvaluator)null, aobj)
            });
            return;
        } else
        {
            setObjectValues(aobj);
            return;
        }
    }

    public void setPropertyName(String s)
    {
        if(mValues != null)
        {
            PropertyValuesHolder propertyvaluesholder = mValues[0];
            String s1 = propertyvaluesholder.getPropertyName();
            propertyvaluesholder.setPropertyName(s);
            mValuesMap.remove(s1);
            mValuesMap.put(s, propertyvaluesholder);
        }
        mPropertyName = s;
        mInitialized = false;
    }

    public void setTarget(Object obj)
    {
label0:
        {
            if(mTarget != obj)
            {
                Object obj1 = mTarget;
                mTarget = obj;
                if(obj1 == null || obj == null || obj1.getClass() != obj.getClass())
                    break label0;
            }
            return;
        }
        mInitialized = false;
    }

    public void setupEndValues()
    {
        initAnimation();
        int j = mValues.length;
        for(int i = 0; i < j; i++)
            mValues[i].setupEndValue(mTarget);

    }

    public void setupStartValues()
    {
        initAnimation();
        int j = mValues.length;
        for(int i = 0; i < j; i++)
            mValues[i].setupStartValue(mTarget);

    }

    public void start()
    {
        start();
    }

    public String toString()
    {
        String s = (new StringBuilder()).append("ObjectAnimator@").append(Integer.toHexString(hashCode())).append(", target ").append(mTarget).toString();
        String s1 = s;
        if(mValues != null)
        {
            int i = 0;
            do
            {
                s1 = s;
                if(i >= mValues.length)
                    break;
                s = (new StringBuilder()).append(s).append("\n    ").append(mValues[i].toString()).toString();
                i++;
            } while(true);
        }
        return s1;
    }

    private static final boolean DBG = false;
    private String mPropertyName;
    private Object mTarget;
}
