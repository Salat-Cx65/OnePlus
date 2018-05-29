package android.support.transition;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import java.io.IOException;
import java.lang.reflect.Constructor;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final ArrayMap<String, Constructor> CONSTRUCTORS;
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE;
    private final Context mContext;

    static {
        CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
        CONSTRUCTORS = new ArrayMap();
    }

    private TransitionInflater(@NonNull Context context) {
        this.mContext = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    public Transition inflateTransition(int resource) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            Transition createTransitionFromXml = createTransitionFromXml(parser, Xml.asAttributeSet(parser), null);
            parser.close();
            return createTransitionFromXml;
        } catch (XmlPullParserException e) {
            throw new InflateException(e.getMessage(), e);
        } catch (IOException e2) {
            throw new InflateException(parser.getPositionDescription() + ": " + e2.getMessage(), e2);
        } catch (Throwable th) {
        }
    }

    public TransitionManager inflateTransitionManager(int resource, ViewGroup sceneRoot) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            TransitionManager createTransitionManagerFromXml = createTransitionManagerFromXml(parser, Xml.asAttributeSet(parser), sceneRoot);
            parser.close();
            return createTransitionManagerFromXml;
        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException(e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (IOException e2) {
            ex = new InflateException(parser.getPositionDescription() + ": " + e2.getMessage());
            ex.initCause(e2);
            throw ex;
        } catch (Throwable th) {
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.transition.Transition createTransitionFromXml(org.xmlpull.v1.XmlPullParser r9_parser, android.util.AttributeSet r10_attrs, android.support.transition.Transition r11_parent) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.createTransitionFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.support.transition.Transition):android.support.transition.Transition");
        /*
        this = this;
        r2 = 0;
        r0 = r9.getDepth();
        r5 = r11 instanceof android.support.transition.TransitionSet;
        if (r5 == 0) goto L_0x0045;
    L_0x0009:
        r5 = r11;
        r5 = (android.support.transition.TransitionSet) r5;
        r3 = r5;
    L_0x000d:
        r4 = r9.next();
        r5 = 3;
        if (r4 != r5) goto L_0x001a;
    L_0x0014:
        r5 = r9.getDepth();
        if (r5 <= r0) goto L_0x017f;
    L_0x001a:
        r5 = 1;
        if (r4 == r5) goto L_0x017f;
    L_0x001d:
        r5 = 2;
        if (r4 != r5) goto L_0x000d;
    L_0x0020:
        r1 = r9.getName();
        r5 = "fade";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0047;
    L_0x002c:
        r2 = new android.support.transition.Fade;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
    L_0x0033:
        if (r2 == 0) goto L_0x000d;
    L_0x0035:
        r5 = r9.isEmptyElementTag();
        if (r5 != 0) goto L_0x003e;
    L_0x003b:
        r8.createTransitionFromXml(r9, r10, r2);
    L_0x003e:
        if (r3 == 0) goto L_0x0175;
    L_0x0040:
        r3.addTransition(r2);
        r2 = 0;
        goto L_0x000d;
    L_0x0045:
        r3 = 0;
        goto L_0x000d;
    L_0x0047:
        r5 = "changeBounds";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0057;
    L_0x004f:
        r2 = new android.support.transition.ChangeBounds;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x0057:
        r5 = "slide";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0067;
    L_0x005f:
        r2 = new android.support.transition.Slide;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x0067:
        r5 = "explode";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0077;
    L_0x006f:
        r2 = new android.support.transition.Explode;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x0077:
        r5 = "changeImageTransform";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0087;
    L_0x007f:
        r2 = new android.support.transition.ChangeImageTransform;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x0087:
        r5 = "changeTransform";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0097;
    L_0x008f:
        r2 = new android.support.transition.ChangeTransform;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x0097:
        r5 = "changeClipBounds";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x00a7;
    L_0x009f:
        r2 = new android.support.transition.ChangeClipBounds;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x00a7:
        r5 = "autoTransition";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x00b8;
    L_0x00af:
        r2 = new android.support.transition.AutoTransition;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x00b8:
        r5 = "changeScroll";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x00c9;
    L_0x00c0:
        r2 = new android.support.transition.ChangeScroll;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x00c9:
        r5 = "transitionSet";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x00da;
    L_0x00d1:
        r2 = new android.support.transition.TransitionSet;
        r5 = r8.mContext;
        r2.<init>(r5, r10);
        goto L_0x0033;
    L_0x00da:
        r5 = "transition";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x00ee;
    L_0x00e2:
        r5 = android.support.transition.Transition.class;
        r6 = "transition";
        r2 = r8.createCustom(r10, r5, r6);
        r2 = (android.support.transition.Transition) r2;
        goto L_0x0033;
    L_0x00ee:
        r5 = "targets";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x00fb;
    L_0x00f6:
        r8.getTargetIds(r9, r10, r11);
        goto L_0x0033;
    L_0x00fb:
        r5 = "arcMotion";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0119;
    L_0x0103:
        if (r11 != 0) goto L_0x010d;
    L_0x0105:
        r5 = new java.lang.RuntimeException;
        r6 = "Invalid use of arcMotion element";
        r5.<init>(r6);
        throw r5;
    L_0x010d:
        r5 = new android.support.transition.ArcMotion;
        r6 = r8.mContext;
        r5.<init>(r6, r10);
        r11.setPathMotion(r5);
        goto L_0x0033;
    L_0x0119:
        r5 = "pathMotion";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x013a;
    L_0x0121:
        if (r11 != 0) goto L_0x012b;
    L_0x0123:
        r5 = new java.lang.RuntimeException;
        r6 = "Invalid use of pathMotion element";
        r5.<init>(r6);
        throw r5;
    L_0x012b:
        r5 = android.support.transition.PathMotion.class;
        r6 = "pathMotion";
        r5 = r8.createCustom(r10, r5, r6);
        r5 = (android.support.transition.PathMotion) r5;
        r11.setPathMotion(r5);
        goto L_0x0033;
    L_0x013a:
        r5 = "patternPathMotion";
        r5 = r5.equals(r1);
        if (r5 == 0) goto L_0x0158;
    L_0x0142:
        if (r11 != 0) goto L_0x014c;
    L_0x0144:
        r5 = new java.lang.RuntimeException;
        r6 = "Invalid use of patternPathMotion element";
        r5.<init>(r6);
        throw r5;
    L_0x014c:
        r5 = new android.support.transition.PatternPathMotion;
        r6 = r8.mContext;
        r5.<init>(r6, r10);
        r11.setPathMotion(r5);
        goto L_0x0033;
    L_0x0158:
        r5 = new java.lang.RuntimeException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Unknown scene name: ";
        r6 = r6.append(r7);
        r7 = r9.getName();
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5.<init>(r6);
        throw r5;
    L_0x0175:
        if (r11 == 0) goto L_0x000d;
    L_0x0177:
        r5 = new android.view.InflateException;
        r6 = "Could not add transition to another transition.";
        r5.<init>(r6);
        throw r5;
    L_0x017f:
        return r2;
        */
    }

    private Object createCustom(AttributeSet attrs, Class expectedType, String tag) {
        String className = attrs.getAttributeValue(null, "class");
        if (className == null) {
            throw new InflateException(tag + " tag must have a 'class' attribute");
        }
        try {
            Object newInstance;
            synchronized (CONSTRUCTORS) {
                Constructor constructor = (Constructor) CONSTRUCTORS.get(className);
                if (constructor == null) {
                    Class<?> c = this.mContext.getClassLoader().loadClass(className).asSubclass(expectedType);
                    if (c != null) {
                        constructor = c.getConstructor(CONSTRUCTOR_SIGNATURE);
                        constructor.setAccessible(true);
                        CONSTRUCTORS.put(className, constructor);
                    }
                }
                newInstance = constructor.newInstance(new Object[]{this.mContext, attrs});
            }
            return newInstance;
        } catch (Exception e) {
            throw new InflateException("Could not instantiate " + expectedType + " class " + className, e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getTargetIds(org.xmlpull.v1.XmlPullParser r13_parser, android.util.AttributeSet r14_attrs, android.support.transition.Transition r15_transition) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.getTargetIds(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.support.transition.Transition):void");
        /*
        this = this;
        r3 = r13.getDepth();
    L_0x0004:
        r8 = r13.next();
        r9 = 3;
        if (r8 != r9) goto L_0x0011;
    L_0x000b:
        r9 = r13.getDepth();
        if (r9 <= r3) goto L_0x00c3;
    L_0x0011:
        r9 = 1;
        if (r8 == r9) goto L_0x00c3;
    L_0x0014:
        r9 = 2;
        if (r8 != r9) goto L_0x0004;
    L_0x0017:
        r6 = r13.getName();
        r9 = "target";
        r9 = r6.equals(r9);
        if (r9 == 0) goto L_0x00a6;
    L_0x0023:
        r9 = r12.mContext;
        r10 = android.support.transition.Styleable.TRANSITION_TARGET;
        r0 = r9.obtainStyledAttributes(r14, r10);
        r9 = "targetId";
        r10 = 1;
        r11 = 0;
        r5 = android.support.v4.content.res.TypedArrayUtils.getNamedResourceId(r0, r13, r9, r10, r11);
        if (r5 == 0) goto L_0x003c;
    L_0x0035:
        r15.addTarget(r5);
    L_0x0038:
        r0.recycle();
        goto L_0x0004;
    L_0x003c:
        r9 = "excludeId";
        r10 = 2;
        r11 = 0;
        r5 = android.support.v4.content.res.TypedArrayUtils.getNamedResourceId(r0, r13, r9, r10, r11);
        if (r5 == 0) goto L_0x004b;
    L_0x0046:
        r9 = 1;
        r15.excludeTarget(r5, r9);
        goto L_0x0038;
    L_0x004b:
        r9 = "targetName";
        r10 = 4;
        r7 = android.support.v4.content.res.TypedArrayUtils.getNamedString(r0, r13, r9, r10);
        if (r7 == 0) goto L_0x0058;
    L_0x0054:
        r15.addTarget(r7);
        goto L_0x0038;
    L_0x0058:
        r9 = "excludeName";
        r10 = 5;
        r7 = android.support.v4.content.res.TypedArrayUtils.getNamedString(r0, r13, r9, r10);
        if (r7 == 0) goto L_0x0066;
    L_0x0061:
        r9 = 1;
        r15.excludeTarget(r7, r9);
        goto L_0x0038;
    L_0x0066:
        r9 = "excludeClass";
        r10 = 3;
        r1 = android.support.v4.content.res.TypedArrayUtils.getNamedString(r0, r13, r9, r10);
        if (r1 == 0) goto L_0x0095;
    L_0x006f:
        r2 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x0078 }
        r9 = 1;
        r15.excludeTarget(r2, r9);	 Catch:{ ClassNotFoundException -> 0x0078 }
        goto L_0x0038;
    L_0x0078:
        r4 = move-exception;
        r0.recycle();
        r9 = new java.lang.RuntimeException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Could not create ";
        r10 = r10.append(r11);
        r10 = r10.append(r1);
        r10 = r10.toString();
        r9.<init>(r10, r4);
        throw r9;
    L_0x0095:
        r9 = "targetClass";
        r10 = 0;
        r1 = android.support.v4.content.res.TypedArrayUtils.getNamedString(r0, r13, r9, r10);	 Catch:{ ClassNotFoundException -> 0x0078 }
        if (r1 == 0) goto L_0x0038;
    L_0x009e:
        r2 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x0078 }
        r15.addTarget(r2);	 Catch:{ ClassNotFoundException -> 0x0078 }
        goto L_0x0038;
    L_0x00a6:
        r9 = new java.lang.RuntimeException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Unknown scene name: ";
        r10 = r10.append(r11);
        r11 = r13.getName();
        r10 = r10.append(r11);
        r10 = r10.toString();
        r9.<init>(r10);
        throw r9;
    L_0x00c3:
        return;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.transition.TransitionManager createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser r8_parser, android.util.AttributeSet r9_attrs, android.view.ViewGroup r10_sceneRoot) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):android.support.transition.TransitionManager");
        /*
        this = this;
        r0 = r8.getDepth();
        r2 = 0;
    L_0x0005:
        r3 = r8.next();
        r4 = 3;
        if (r3 != r4) goto L_0x0012;
    L_0x000c:
        r4 = r8.getDepth();
        if (r4 <= r0) goto L_0x0055;
    L_0x0012:
        r4 = 1;
        if (r3 == r4) goto L_0x0055;
    L_0x0015:
        r4 = 2;
        if (r3 != r4) goto L_0x0005;
    L_0x0018:
        r1 = r8.getName();
        r4 = "transitionManager";
        r4 = r1.equals(r4);
        if (r4 == 0) goto L_0x002a;
    L_0x0024:
        r2 = new android.support.transition.TransitionManager;
        r2.<init>();
        goto L_0x0005;
    L_0x002a:
        r4 = "transition";
        r4 = r1.equals(r4);
        if (r4 == 0) goto L_0x0038;
    L_0x0032:
        if (r2 == 0) goto L_0x0038;
    L_0x0034:
        r7.loadTransition(r9, r8, r10, r2);
        goto L_0x0005;
    L_0x0038:
        r4 = new java.lang.RuntimeException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Unknown scene name: ";
        r5 = r5.append(r6);
        r6 = r8.getName();
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0055:
        return r2;
        */
    }

    private void loadTransition(AttributeSet attrs, XmlPullParser parser, ViewGroup sceneRoot, TransitionManager transitionManager) throws NotFoundException {
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, Styleable.TRANSITION_MANAGER);
        int transitionId = TypedArrayUtils.getNamedResourceId(a, parser, "transition", RainSurfaceView.RAIN_LEVEL_SHOWER, -1);
        int fromId = TypedArrayUtils.getNamedResourceId(a, parser, "fromScene", 0, -1);
        Scene fromScene = fromId < 0 ? null : Scene.getSceneForLayout(sceneRoot, fromId, this.mContext);
        int toId = TypedArrayUtils.getNamedResourceId(a, parser, "toScene", 1, -1);
        Scene toScene = toId < 0 ? null : Scene.getSceneForLayout(sceneRoot, toId, this.mContext);
        if (transitionId >= 0) {
            Transition transition = inflateTransition(transitionId);
            if (transition != null) {
                if (toScene == null) {
                    throw new RuntimeException("No toScene for transition ID " + transitionId);
                } else if (fromScene == null) {
                    transitionManager.setTransition(toScene, transition);
                } else {
                    transitionManager.setTransition(fromScene, toScene, transition);
                }
            }
        }
        a.recycle();
    }
}
