package android.support.graphics.drawable;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
public class AnimationUtilsCompat {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.view.animation.Interpolator loadInterpolator(android.content.Context r5_context, int r6_id) throws android.content.res.Resources.NotFoundException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.AnimationUtilsCompat.loadInterpolator(android.content.Context, int):android.view.animation.Interpolator");
        /*
        r3 = android.os.Build.VERSION.SDK_INT;
        r4 = 21;
        if (r3 < r4) goto L_0x000b;
    L_0x0006:
        r3 = android.view.animation.AnimationUtils.loadInterpolator(r5, r6);
    L_0x000a:
        return r3;
    L_0x000b:
        r1 = 0;
        r3 = 17563663; // 0x10c000f float:2.571398E-38 double:8.6776025E-317;
        if (r6 != r3) goto L_0x001c;
    L_0x0011:
        r3 = new android.support.v4.view.animation.FastOutLinearInInterpolator;	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r3.<init>();	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        if (r1 == 0) goto L_0x000a;
    L_0x0018:
        r1.close();
        goto L_0x000a;
    L_0x001c:
        r3 = 17563661; // 0x10c000d float:2.5713975E-38 double:8.6776015E-317;
        if (r6 != r3) goto L_0x002c;
    L_0x0021:
        r3 = new android.support.v4.view.animation.FastOutSlowInInterpolator;	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r3.<init>();	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        if (r1 == 0) goto L_0x000a;
    L_0x0028:
        r1.close();
        goto L_0x000a;
    L_0x002c:
        r3 = 17563662; // 0x10c000e float:2.5713978E-38 double:8.677602E-317;
        if (r6 != r3) goto L_0x003c;
    L_0x0031:
        r3 = new android.support.v4.view.animation.LinearOutSlowInInterpolator;	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r3.<init>();	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        if (r1 == 0) goto L_0x000a;
    L_0x0038:
        r1.close();
        goto L_0x000a;
    L_0x003c:
        r3 = r5.getResources();	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r1 = r3.getAnimation(r6);	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r3 = r5.getResources();	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r4 = r5.getTheme();	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        r3 = createInterpolatorFromXml(r5, r3, r4, r1);	 Catch:{ XmlPullParserException -> 0x0056, IOException -> 0x007e }
        if (r1 == 0) goto L_0x000a;
    L_0x0052:
        r1.close();
        goto L_0x000a;
    L_0x0056:
        r0 = move-exception;
        r2 = new android.content.res.Resources$NotFoundException;	 Catch:{ all -> 0x0077 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0077 }
        r3.<init>();	 Catch:{ all -> 0x0077 }
        r4 = "Can't load animation resource ID #0x";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0077 }
        r4 = java.lang.Integer.toHexString(r6);	 Catch:{ all -> 0x0077 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0077 }
        r3 = r3.toString();	 Catch:{ all -> 0x0077 }
        r2.<init>(r3);	 Catch:{ all -> 0x0077 }
        r2.initCause(r0);	 Catch:{ all -> 0x0077 }
        throw r2;	 Catch:{ all -> 0x0077 }
    L_0x0077:
        r3 = move-exception;
        if (r1 == 0) goto L_0x007d;
    L_0x007a:
        r1.close();
    L_0x007d:
        throw r3;
    L_0x007e:
        r0 = move-exception;
        r2 = new android.content.res.Resources$NotFoundException;	 Catch:{ all -> 0x0077 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0077 }
        r3.<init>();	 Catch:{ all -> 0x0077 }
        r4 = "Can't load animation resource ID #0x";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0077 }
        r4 = java.lang.Integer.toHexString(r6);	 Catch:{ all -> 0x0077 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0077 }
        r3 = r3.toString();	 Catch:{ all -> 0x0077 }
        r2.<init>(r3);	 Catch:{ all -> 0x0077 }
        r2.initCause(r0);	 Catch:{ all -> 0x0077 }
        throw r2;	 Catch:{ all -> 0x0077 }
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.view.animation.Interpolator createInterpolatorFromXml(android.content.Context r8_context, android.content.res.Resources r9_res, android.content.res.Resources.Theme r10_theme, org.xmlpull.v1.XmlPullParser r11_parser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.AnimationUtilsCompat.createInterpolatorFromXml(android.content.Context, android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser):android.view.animation.Interpolator");
        /*
        r2 = 0;
        r1 = r11.getDepth();
    L_0x0005:
        r4 = r11.next();
        r5 = 3;
        if (r4 != r5) goto L_0x0012;
    L_0x000c:
        r5 = r11.getDepth();
        if (r5 <= r1) goto L_0x00cc;
    L_0x0012:
        r5 = 1;
        if (r4 == r5) goto L_0x00cc;
    L_0x0015:
        r5 = 2;
        if (r4 != r5) goto L_0x0005;
    L_0x0018:
        r0 = android.util.Xml.asAttributeSet(r11);
        r3 = r11.getName();
        r5 = "linearInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x002e;
    L_0x0028:
        r2 = new android.view.animation.LinearInterpolator;
        r2.<init>();
        goto L_0x0005;
    L_0x002e:
        r5 = "accelerateInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x003c;
    L_0x0036:
        r2 = new android.view.animation.AccelerateInterpolator;
        r2.<init>(r8, r0);
        goto L_0x0005;
    L_0x003c:
        r5 = "decelerateInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x004a;
    L_0x0044:
        r2 = new android.view.animation.DecelerateInterpolator;
        r2.<init>(r8, r0);
        goto L_0x0005;
    L_0x004a:
        r5 = "accelerateDecelerateInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x0058;
    L_0x0052:
        r2 = new android.view.animation.AccelerateDecelerateInterpolator;
        r2.<init>();
        goto L_0x0005;
    L_0x0058:
        r5 = "cycleInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x0066;
    L_0x0060:
        r2 = new android.view.animation.CycleInterpolator;
        r2.<init>(r8, r0);
        goto L_0x0005;
    L_0x0066:
        r5 = "anticipateInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x0074;
    L_0x006e:
        r2 = new android.view.animation.AnticipateInterpolator;
        r2.<init>(r8, r0);
        goto L_0x0005;
    L_0x0074:
        r5 = "overshootInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x0082;
    L_0x007c:
        r2 = new android.view.animation.OvershootInterpolator;
        r2.<init>(r8, r0);
        goto L_0x0005;
    L_0x0082:
        r5 = "anticipateOvershootInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x0091;
    L_0x008a:
        r2 = new android.view.animation.AnticipateOvershootInterpolator;
        r2.<init>(r8, r0);
        goto L_0x0005;
    L_0x0091:
        r5 = "bounceInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x00a0;
    L_0x0099:
        r2 = new android.view.animation.BounceInterpolator;
        r2.<init>();
        goto L_0x0005;
    L_0x00a0:
        r5 = "pathInterpolator";
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x00af;
    L_0x00a8:
        r2 = new android.support.graphics.drawable.PathInterpolatorCompat;
        r2.<init>(r8, r0, r11);
        goto L_0x0005;
    L_0x00af:
        r5 = new java.lang.RuntimeException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Unknown interpolator name: ";
        r6 = r6.append(r7);
        r7 = r11.getName();
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5.<init>(r6);
        throw r5;
    L_0x00cc:
        return r2;
        */
    }
}
