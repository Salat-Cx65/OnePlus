package com.oneplus.lib.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import com.oneplus.lib.preference.GenericInflater.Factory;
import java.io.IOException;
import java.util.HashMap;
import net.oneplus.weather.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

abstract class GenericInflater<T, P extends Parent> {
    private static final Class[] mConstructorSignature;
    private static final HashMap sConstructorMap;
    private final boolean DEBUG;
    private final Object[] mConstructorArgs;
    protected final Context mContext;
    private String mDefaultPackage;
    private Factory<T> mFactory;
    private boolean mFactorySet;

    public static interface Factory<T> {
        T onCreateItem(String str, Context context, AttributeSet attributeSet);
    }

    public static interface Parent<T> {
        void addItemFromInflater(T t);
    }

    private static class FactoryMerger<T> implements Factory<T> {
        private final Factory<T> mF1;
        private final Factory<T> mF2;

        FactoryMerger(Factory<T> f1, Factory<T> f2) {
            this.mF1 = f1;
            this.mF2 = f2;
        }

        public T onCreateItem(String name, Context context, AttributeSet attrs) {
            T v = this.mF1.onCreateItem(name, context, attrs);
            return v != null ? v : this.mF2.onCreateItem(name, context, attrs);
        }
    }

    public abstract GenericInflater cloneInContext(Context context);

    static {
        mConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sConstructorMap = new HashMap();
    }

    protected GenericInflater(Context context) {
        this.DEBUG = false;
        this.mConstructorArgs = new Object[2];
        this.mContext = context;
    }

    protected GenericInflater(GenericInflater<T, P> original, Context newContext) {
        this.DEBUG = false;
        this.mConstructorArgs = new Object[2];
        this.mContext = newContext;
        this.mFactory = original.mFactory;
    }

    public void setDefaultPackage(String defaultPackage) {
        this.mDefaultPackage = defaultPackage;
    }

    public String getDefaultPackage() {
        return this.mDefaultPackage;
    }

    public Context getContext() {
        return this.mContext;
    }

    public final Factory<T> getFactory() {
        return this.mFactory;
    }

    public void setFactory(Factory<T> factory) {
        if (this.mFactorySet) {
            throw new IllegalStateException("A factory has already been set on this inflater");
        } else if (factory == null) {
            throw new NullPointerException("Given factory can not be null");
        } else {
            this.mFactorySet = true;
            if (this.mFactory == null) {
                this.mFactory = factory;
            } else {
                this.mFactory = new FactoryMerger(factory, this.mFactory);
            }
        }
    }

    public T inflate(int resource, P root) {
        return inflate(resource, (Parent) root, root != null);
    }

    public T inflate(XmlPullParser parser, P root) {
        return inflate(parser, (Parent) root, root != null);
    }

    public T inflate(int resource, P root, boolean attachToRoot) {
        XmlPullParser parser = getContext().getResources().getXml(resource);
        T inflate = inflate(parser, (Parent) root, attachToRoot);
        parser.close();
        return inflate;
    }

    public T inflate(XmlPullParser parser, P root, boolean attachToRoot) {
        T result;
        synchronized (this.mConstructorArgs) {
            try {
                AttributeSet attrs = Xml.asAttributeSet(parser);
                this.mConstructorArgs[0] = this.mContext;
                P p = root;
                int type;
                do {
                    type = parser.next();
                    if (type == 2) {
                        break;
                    }
                } while (type != 1);
                if (type != 2) {
                    throw new InflateException(parser.getPositionDescription() + ": No start tag found!");
                }
                result = onMergeRoots(root, attachToRoot, (Parent) createItemFromTag(parser, parser.getName(), attrs));
                rInflate(parser, result, attrs);
            } catch (InflateException e) {
                throw e;
            } catch (XmlPullParserException e2) {
                InflateException ex = new InflateException(e2.getMessage());
                ex.initCause(e2);
                throw ex;
            } catch (IOException e3) {
                ex = new InflateException(parser.getPositionDescription() + ": " + e3.getMessage());
                ex.initCause(e3);
                throw ex;
            } catch (Throwable th) {
            }
        }
        return result;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final T createItem(java.lang.String r8_name, java.lang.String r9_prefix, android.util.AttributeSet r10_attrs) throws java.lang.ClassNotFoundException, android.view.InflateException {
        throw new UnsupportedOperationException("Method not decompiled: com.oneplus.lib.preference.GenericInflater.createItem(java.lang.String, java.lang.String, android.util.AttributeSet):T");
        /*
        this = this;
        r5 = sConstructorMap;
        r2 = r5.get(r8);
        r2 = (java.lang.reflect.Constructor) r2;
        if (r2 != 0) goto L_0x0036;
    L_0x000a:
        r5 = r7.mContext;	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r6 = r5.getClassLoader();	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        if (r9 == 0) goto L_0x0040;
    L_0x0012:
        r5 = new java.lang.StringBuilder;	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5.<init>();	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = r5.append(r9);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = r5.append(r8);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = r5.toString();	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
    L_0x0023:
        r1 = r6.loadClass(r5);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = mConstructorSignature;	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r2 = r1.getConstructor(r5);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = 1;
        r2.setAccessible(r5);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = sConstructorMap;	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5.put(r8, r2);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
    L_0x0036:
        r0 = r7.mConstructorArgs;	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = 1;
        r0[r5] = r10;	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        r5 = r2.newInstance(r0);	 Catch:{ NoSuchMethodException -> 0x0042, ClassNotFoundException -> 0x007a, Exception -> 0x007c }
        return r5;
    L_0x0040:
        r5 = r8;
        goto L_0x0023;
    L_0x0042:
        r3 = move-exception;
        r4 = new android.view.InflateException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = r10.getPositionDescription();
        r5 = r5.append(r6);
        r6 = ": Error inflating class ";
        r5 = r5.append(r6);
        if (r9 == 0) goto L_0x006b;
    L_0x005a:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r6 = r6.append(r9);
        r6 = r6.append(r8);
        r8 = r6.toString();
    L_0x006b:
        r5 = r5.append(r8);
        r5 = r5.toString();
        r4.<init>(r5);
        r4.initCause(r3);
        throw r4;
    L_0x007a:
        r3 = move-exception;
        throw r3;
    L_0x007c:
        r3 = move-exception;
        r4 = new android.view.InflateException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = r10.getPositionDescription();
        r5 = r5.append(r6);
        r6 = ": Error inflating class ";
        r5 = r5.append(r6);
        r6 = r2.getClass();
        r6 = r6.getName();
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        r4.initCause(r3);
        throw r4;
        */
    }

    protected T onCreateItem(String name, AttributeSet attrs) throws ClassNotFoundException {
        return createItem(name, this.mDefaultPackage, attrs);
    }

    private final T createItemFromTag(XmlPullParser parser, String name, AttributeSet attrs) {
        T item = null;
        try {
            if (this.mFactory != null) {
                item = this.mFactory.onCreateItem(name, this.mContext, attrs);
            }
            if (item == null) {
                return -1 == name.indexOf(R.styleable.AppCompatTheme_checkboxStyle) ? onCreateItem(name, attrs) : createItem(name, null, attrs);
            } else {
                return item;
            }
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException ie = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + name);
            ie.initCause(e2);
            throw ie;
        } catch (Exception e3) {
            ie = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + name);
            ie.initCause(e3);
            throw ie;
        }
    }

    private void rInflate(XmlPullParser parser, T parent, AttributeSet attrs) throws XmlPullParserException, IOException {
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1 && type == 2 && !onCreateCustomFromTag(parser, parent, attrs)) {
                T item = createItemFromTag(parser, parser.getName(), attrs);
                ((Parent) parent).addItemFromInflater(item);
                rInflate(parser, item, attrs);
            }
        }
    }

    protected boolean onCreateCustomFromTag(XmlPullParser parser, T t, AttributeSet attrs) throws XmlPullParserException {
        return false;
    }

    protected P onMergeRoots(P p, boolean attachToGivenRoot, P xmlRoot) {
        return xmlRoot;
    }
}
