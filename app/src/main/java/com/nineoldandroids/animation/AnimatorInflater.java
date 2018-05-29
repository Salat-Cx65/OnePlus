package com.nineoldandroids.animation;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AnimationUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflater {
    private static final int[] Animator;
    private static final int[] AnimatorSet;
    private static final int AnimatorSet_ordering = 0;
    private static final int Animator_duration = 1;
    private static final int Animator_interpolator = 0;
    private static final int Animator_repeatCount = 3;
    private static final int Animator_repeatMode = 4;
    private static final int Animator_startOffset = 2;
    private static final int Animator_valueFrom = 5;
    private static final int Animator_valueTo = 6;
    private static final int Animator_valueType = 7;
    private static final int[] PropertyAnimator;
    private static final int PropertyAnimator_propertyName = 0;
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_FLOAT = 0;

    static {
        AnimatorSet = new int[]{16843490};
        PropertyAnimator = new int[]{16843489};
        Animator = new int[]{16843073, 16843160, 16843198, 16843199, 16843200, 16843486, 16843487, 16843488};
    }

    public static Animator loadAnimator(Context context, int id) throws NotFoundException {
        XmlResourceParser xmlResourceParser = null;
        try {
            xmlResourceParser = context.getResources().getAnimation(id);
            Animator createAnimatorFromXml = createAnimatorFromXml(context, xmlResourceParser);
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            return createAnimatorFromXml;
        } catch (XmlPullParserException ex) {
            NotFoundException rnf = new NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex2) {
            rnf = new NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex2);
            throw rnf;
        } catch (Throwable th) {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    private static Animator createAnimatorFromXml(Context c, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(c, parser, Xml.asAttributeSet(parser), null, Animator_interpolator);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.nineoldandroids.animation.Animator createAnimatorFromXml(android.content.Context r18_c, org.xmlpull.v1.XmlPullParser r19_parser, android.util.AttributeSet r20_attrs, com.nineoldandroids.animation.AnimatorSet r21_parent, int r22_sequenceOrdering) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: com.nineoldandroids.animation.AnimatorInflater.createAnimatorFromXml(android.content.Context, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, com.nineoldandroids.animation.AnimatorSet, int):com.nineoldandroids.animation.Animator");
        /*
        r4 = 0;
        r6 = 0;
        r7 = r19.getDepth();
    L_0x0006:
        r14 = r19.next();
        r15 = 3;
        if (r14 != r15) goto L_0x0013;
    L_0x000d:
        r15 = r19.getDepth();
        if (r15 <= r7) goto L_0x00a5;
    L_0x0013:
        r15 = 1;
        if (r14 == r15) goto L_0x00a5;
    L_0x0016:
        r15 = 2;
        if (r14 != r15) goto L_0x0006;
    L_0x0019:
        r11 = r19.getName();
        r15 = "objectAnimator";
        r15 = r11.equals(r15);
        if (r15 == 0) goto L_0x003a;
    L_0x0025:
        r0 = r18;
        r1 = r20;
        r4 = loadObjectAnimator(r0, r1);
    L_0x002d:
        if (r21 == 0) goto L_0x0006;
    L_0x002f:
        if (r6 != 0) goto L_0x0036;
    L_0x0031:
        r6 = new java.util.ArrayList;
        r6.<init>();
    L_0x0036:
        r6.add(r4);
        goto L_0x0006;
    L_0x003a:
        r15 = "animator";
        r15 = r11.equals(r15);
        if (r15 == 0) goto L_0x004c;
    L_0x0042:
        r15 = 0;
        r0 = r18;
        r1 = r20;
        r4 = loadAnimator(r0, r1, r15);
        goto L_0x002d;
    L_0x004c:
        r15 = "set";
        r15 = r11.equals(r15);
        if (r15 == 0) goto L_0x0088;
    L_0x0054:
        r4 = new com.nineoldandroids.animation.AnimatorSet;
        r4.<init>();
        r15 = AnimatorSet;
        r0 = r18;
        r1 = r20;
        r3 = r0.obtainStyledAttributes(r1, r15);
        r13 = new android.util.TypedValue;
        r13.<init>();
        r15 = 0;
        r3.getValue(r15, r13);
        r15 = r13.type;
        r16 = 16;
        r0 = r16;
        if (r15 != r0) goto L_0x0086;
    L_0x0074:
        r12 = r13.data;
    L_0x0076:
        r15 = r4;
        r15 = (com.nineoldandroids.animation.AnimatorSet) r15;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        createAnimatorFromXml(r0, r1, r2, r15, r12);
        r3.recycle();
        goto L_0x002d;
    L_0x0086:
        r12 = 0;
        goto L_0x0076;
    L_0x0088:
        r15 = new java.lang.RuntimeException;
        r16 = new java.lang.StringBuilder;
        r16.<init>();
        r17 = "Unknown animator name: ";
        r16 = r16.append(r17);
        r17 = r19.getName();
        r16 = r16.append(r17);
        r16 = r16.toString();
        r15.<init>(r16);
        throw r15;
    L_0x00a5:
        if (r21 == 0) goto L_0x00cd;
    L_0x00a7:
        if (r6 == 0) goto L_0x00cd;
    L_0x00a9:
        r15 = r6.size();
        r5 = new com.nineoldandroids.animation.Animator[r15];
        r9 = 0;
        r8 = r6.iterator();
    L_0x00b4:
        r15 = r8.hasNext();
        if (r15 == 0) goto L_0x00c6;
    L_0x00ba:
        r3 = r8.next();
        r3 = (com.nineoldandroids.animation.Animator) r3;
        r10 = r9 + 1;
        r5[r9] = r3;
        r9 = r10;
        goto L_0x00b4;
    L_0x00c6:
        if (r22 != 0) goto L_0x00ce;
    L_0x00c8:
        r0 = r21;
        r0.playTogether(r5);
    L_0x00cd:
        return r4;
    L_0x00ce:
        r0 = r21;
        r0.playSequentially(r5);
        goto L_0x00cd;
        */
    }

    private static ObjectAnimator loadObjectAnimator(Context context, AttributeSet attrs) throws NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(context, attrs, anim);
        TypedArray a = context.obtainStyledAttributes(attrs, PropertyAnimator);
        anim.setPropertyName(a.getString(Animator_interpolator));
        a.recycle();
        return anim;
    }

    private static ValueAnimator loadAnimator(Context context, AttributeSet attrs, ValueAnimator anim) throws NotFoundException {
        TypedArray a = context.obtainStyledAttributes(attrs, Animator);
        long duration = (long) a.getInt(1, 0);
        long startDelay = (long) a.getInt(2, 0);
        int valueType = a.getInt(7, 0);
        if (anim == null) {
            anim = new ValueAnimator();
        }
        boolean getFloats = valueType == 0;
        TypedValue tvFrom = a.peekValue(5);
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : Animator_interpolator;
        TypedValue tvTo = a.peekValue(6);
        boolean hasTo = tvTo != null;
        int toType = hasTo ? tvTo.type : Animator_interpolator;
        if ((hasFrom && fromType >= 28 && fromType <= 31) || (hasTo && toType >= 28 && toType <= 31)) {
            getFloats = false;
            anim.setEvaluator(new ArgbEvaluator());
        }
        if (getFloats) {
            float valueTo;
            float[] fArr;
            if (hasFrom) {
                float valueFrom;
                if (fromType == 5) {
                    valueFrom = a.getDimension(5, 0.0f);
                } else {
                    valueFrom = a.getFloat(5, 0.0f);
                }
                if (hasTo) {
                    if (toType == 5) {
                        valueTo = a.getDimension(6, 0.0f);
                    } else {
                        valueTo = a.getFloat(6, 0.0f);
                    }
                    fArr = new float[2];
                    fArr[0] = valueFrom;
                    fArr[1] = valueTo;
                    anim.setFloatValues(fArr);
                } else {
                    fArr = new float[1];
                    fArr[0] = valueFrom;
                    anim.setFloatValues(fArr);
                }
            } else {
                if (toType == 5) {
                    valueTo = a.getDimension(6, 0.0f);
                } else {
                    valueTo = a.getFloat(6, 0.0f);
                }
                fArr = new float[1];
                fArr[0] = valueTo;
                anim.setFloatValues(fArr);
            }
        } else if (hasFrom) {
            int valueFrom2;
            if (fromType == 5) {
                valueFrom2 = (int) a.getDimension(5, 0.0f);
            } else if (fromType < 28 || fromType > 31) {
                valueFrom2 = a.getInt(5, 0);
            } else {
                valueFrom2 = a.getColor(5, 0);
            }
            if (hasTo) {
                if (toType == 5) {
                    valueTo = (int) a.getDimension(6, 0.0f);
                } else if (toType < 28 || toType > 31) {
                    valueTo = a.getInt(6, 0);
                } else {
                    valueTo = a.getColor(6, 0);
                }
                r22 = new int[2];
                r22[0] = valueFrom2;
                r22[1] = valueTo;
                anim.setIntValues(r22);
            } else {
                r22 = new int[1];
                r22[0] = valueFrom2;
                anim.setIntValues(r22);
            }
        } else if (hasTo) {
            if (toType == 5) {
                valueTo = (int) a.getDimension(6, 0.0f);
            } else if (toType < 28 || toType > 31) {
                valueTo = a.getInt(6, 0);
            } else {
                valueTo = a.getColor(6, 0);
            }
            r22 = new int[1];
            r22[0] = valueTo;
            anim.setIntValues(r22);
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        if (a.hasValue(3)) {
            anim.setRepeatCount(a.getInt(3, 0));
        }
        if (a.hasValue(4)) {
            anim.setRepeatMode(a.getInt(4, 1));
        }
        int resID = a.getResourceId(0, 0);
        if (resID > 0) {
            anim.setInterpolator(AnimationUtils.loadInterpolator(context, resID));
        }
        a.recycle();
        return anim;
    }
}
