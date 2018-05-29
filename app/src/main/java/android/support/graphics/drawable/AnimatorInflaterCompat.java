package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build.VERSION;
import android.support.annotation.AnimatorRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.PathParser.PathDataNode;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.IOException;
import java.util.ArrayList;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({Scope.LIBRARY_GROUP})
public class AnimatorInflaterCompat {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int MAX_NUM_POINTS = 100;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;

    private static class PathDataEvaluator implements TypeEvaluator<PathDataNode[]> {
        private PathDataNode[] mNodeArray;

        private PathDataEvaluator() {
        }

        PathDataEvaluator(PathDataNode[] nodeArray) {
            this.mNodeArray = nodeArray;
        }

        public PathDataNode[] evaluate(float fraction, PathDataNode[] startPathData, PathDataNode[] endPathData) {
            if (PathParser.canMorph(startPathData, endPathData)) {
                if (this.mNodeArray == null || !PathParser.canMorph(this.mNodeArray, startPathData)) {
                    this.mNodeArray = PathParser.deepCopyNodes(startPathData);
                }
                for (int i = VALUE_TYPE_FLOAT; i < startPathData.length; i++) {
                    this.mNodeArray[i].interpolatePathDataNode(startPathData[i], endPathData[i], fraction);
                }
                return this.mNodeArray;
            }
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }

    public static Animator loadAnimator(Context context, @AnimatorRes int id) throws NotFoundException {
        return VERSION.SDK_INT >= 24 ? AnimatorInflater.loadAnimator(context, id) : loadAnimator(context, context.getResources(), context.getTheme(), id);
    }

    public static Animator loadAnimator(Context context, Resources resources, Theme theme, @AnimatorRes int id) throws NotFoundException {
        return loadAnimator(context, resources, theme, id, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static Animator loadAnimator(Context context, Resources resources, Theme theme, @AnimatorRes int id, float pathErrorScale) throws NotFoundException {
        XmlResourceParser xmlResourceParser = null;
        try {
            xmlResourceParser = resources.getAnimation(id);
            Animator animator = createAnimatorFromXml(context, resources, theme, xmlResourceParser, pathErrorScale);
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            return animator;
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

    private static PropertyValuesHolder getPVH(TypedArray styledAttributes, int valueType, int valueFromId, int valueToId, String propertyName) {
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        boolean hasFrom = tvFrom != null ? true : DBG_ANIMATOR_INFLATER;
        int fromType = hasFrom ? tvFrom.type : VALUE_TYPE_FLOAT;
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        boolean hasTo = tvTo != null ? true : DBG_ANIMATOR_INFLATER;
        int toType = hasTo ? tvTo.type : VALUE_TYPE_FLOAT;
        if (valueType == 4) {
            valueType = ((hasFrom && isColorType(fromType)) || (hasTo && isColorType(toType))) ? VALUE_TYPE_COLOR : VALUE_TYPE_FLOAT;
        }
        boolean getFloats = valueType == 0 ? true : DBG_ANIMATOR_INFLATER;
        PropertyValuesHolder returnValue = null;
        TypeEvaluator evaluator;
        if (valueType == 2) {
            String fromString = styledAttributes.getString(valueFromId);
            String toString = styledAttributes.getString(valueToId);
            PathDataNode[] nodesFrom = PathParser.createNodesFromPathData(fromString);
            PathDataNode[] nodesTo = PathParser.createNodesFromPathData(toString);
            if (nodesFrom == null && nodesTo == null) {
                return null;
            }
            Object[] objArr;
            if (nodesFrom != null) {
                evaluator = new PathDataEvaluator();
                if (nodesTo == null) {
                    objArr = new Object[1];
                    objArr[0] = nodesFrom;
                    return PropertyValuesHolder.ofObject(propertyName, evaluator, objArr);
                } else if (PathParser.canMorph(nodesFrom, nodesTo)) {
                    objArr = new Object[2];
                    objArr[0] = nodesFrom;
                    objArr[1] = nodesTo;
                    return PropertyValuesHolder.ofObject(propertyName, evaluator, objArr);
                } else {
                    throw new InflateException(" Can't morph from " + fromString + " to " + toString);
                }
            } else if (nodesTo == null) {
                return null;
            } else {
                evaluator = new PathDataEvaluator();
                objArr = new Object[1];
                objArr[0] = nodesTo;
                return PropertyValuesHolder.ofObject(propertyName, evaluator, objArr);
            }
        }
        evaluator = null;
        if (valueType == 3) {
            evaluator = ArgbEvaluator.getInstance();
        }
        if (getFloats) {
            float valueTo;
            float[] fArr;
            if (hasFrom) {
                float valueFrom;
                if (fromType == 5) {
                    valueFrom = styledAttributes.getDimension(valueFromId, 0.0f);
                } else {
                    valueFrom = styledAttributes.getFloat(valueFromId, 0.0f);
                }
                if (hasTo) {
                    if (toType == 5) {
                        valueTo = styledAttributes.getDimension(valueToId, 0.0f);
                    } else {
                        valueTo = styledAttributes.getFloat(valueToId, 0.0f);
                    }
                    fArr = new float[2];
                    fArr[0] = valueFrom;
                    fArr[1] = valueTo;
                    returnValue = PropertyValuesHolder.ofFloat(propertyName, fArr);
                } else {
                    fArr = new float[1];
                    fArr[0] = valueFrom;
                    returnValue = PropertyValuesHolder.ofFloat(propertyName, fArr);
                }
            } else {
                if (toType == 5) {
                    valueTo = styledAttributes.getDimension(valueToId, 0.0f);
                } else {
                    valueTo = styledAttributes.getFloat(valueToId, 0.0f);
                }
                fArr = new float[1];
                fArr[0] = valueTo;
                returnValue = PropertyValuesHolder.ofFloat(propertyName, fArr);
            }
        } else if (hasFrom) {
            int valueFrom2;
            if (fromType == 5) {
                valueFrom2 = (int) styledAttributes.getDimension(valueFromId, 0.0f);
            } else if (isColorType(fromType)) {
                valueFrom2 = styledAttributes.getColor(valueFromId, 0);
            } else {
                valueFrom2 = styledAttributes.getInt(valueFromId, 0);
            }
            if (hasTo) {
                if (toType == 5) {
                    valueTo = (int) styledAttributes.getDimension(valueToId, 0.0f);
                } else if (isColorType(toType)) {
                    valueTo = styledAttributes.getColor(valueToId, 0);
                } else {
                    valueTo = styledAttributes.getInt(valueToId, 0);
                }
                r18 = new int[2];
                r18[0] = valueFrom2;
                r18[1] = valueTo;
                returnValue = PropertyValuesHolder.ofInt(propertyName, r18);
            } else {
                r18 = new int[1];
                r18[0] = valueFrom2;
                returnValue = PropertyValuesHolder.ofInt(propertyName, r18);
            }
        } else if (hasTo) {
            if (toType == 5) {
                valueTo = (int) styledAttributes.getDimension(valueToId, 0.0f);
            } else if (isColorType(toType)) {
                valueTo = styledAttributes.getColor(valueToId, 0);
            } else {
                valueTo = styledAttributes.getInt(valueToId, 0);
            }
            r18 = new int[1];
            r18[0] = valueTo;
            returnValue = PropertyValuesHolder.ofInt(propertyName, r18);
        }
        if (returnValue == null || evaluator == null) {
            return returnValue;
        }
        returnValue.setEvaluator(evaluator);
        return returnValue;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator anim, TypedArray arrayAnimator, TypedArray arrayObjectAnimator, float pixelSize, XmlPullParser parser) {
        long duration = (long) TypedArrayUtils.getNamedInt(arrayAnimator, parser, "duration", VALUE_TYPE_INT, 300);
        long startDelay = (long) TypedArrayUtils.getNamedInt(arrayAnimator, parser, "startOffset", VALUE_TYPE_PATH, VALUE_TYPE_FLOAT);
        int valueType = TypedArrayUtils.getNamedInt(arrayAnimator, parser, "valueType", DetectedActivity.WALKING, VALUE_TYPE_UNDEFINED);
        if (TypedArrayUtils.hasAttribute(parser, "valueFrom") && TypedArrayUtils.hasAttribute(parser, "valueTo")) {
            if (valueType == 4) {
                valueType = inferValueTypeFromValues(arrayAnimator, RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, ConnectionResult.RESOLUTION_REQUIRED);
            }
            if (getPVH(arrayAnimator, valueType, RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, ConnectionResult.RESOLUTION_REQUIRED, StringUtils.EMPTY_STRING) != null) {
                anim.setValues(new PropertyValuesHolder[]{getPVH(arrayAnimator, valueType, RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, ConnectionResult.RESOLUTION_REQUIRED, StringUtils.EMPTY_STRING)});
            }
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        anim.setRepeatCount(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatCount", VALUE_TYPE_COLOR, VALUE_TYPE_FLOAT));
        anim.setRepeatMode(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatMode", VALUE_TYPE_UNDEFINED, VALUE_TYPE_INT));
        if (arrayObjectAnimator != null) {
            setupObjectAnimator(anim, arrayObjectAnimator, valueType, pixelSize, parser);
        }
    }

    private static void setupObjectAnimator(ValueAnimator anim, TypedArray arrayObjectAnimator, int valueType, float pixelSize, XmlPullParser parser) {
        ObjectAnimator oa = (ObjectAnimator) anim;
        String pathData = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "pathData", VALUE_TYPE_INT);
        if (pathData != null) {
            String propertyXName = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyXName", VALUE_TYPE_PATH);
            String propertyYName = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyYName", VALUE_TYPE_COLOR);
            if (valueType == 2 || valueType == 4) {
            }
            if (propertyXName == null && propertyYName == null) {
                throw new InflateException(arrayObjectAnimator.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            setupPathMotion(PathParser.createPathFromPathData(pathData), oa, 0.5f * pixelSize, propertyXName, propertyYName);
            return;
        }
        oa.setPropertyName(TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyName", VALUE_TYPE_FLOAT));
    }

    private static void setupPathMotion(Path path, ObjectAnimator oa, float precision, String propertyXName, String propertyYName) {
        PathMeasure measureForTotalLength = new PathMeasure(path, false);
        float totalLength = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        ArrayList<Float> contourLengths = new ArrayList();
        contourLengths.add(Float.valueOf(AutoScrollHelper.RELATIVE_UNSPECIFIED));
        do {
            totalLength += measureForTotalLength.getLength();
            contourLengths.add(Float.valueOf(totalLength));
        } while (measureForTotalLength.nextContour());
        PathMeasure pathMeasure = new PathMeasure(path, false);
        int numPoints = Math.min(MAX_NUM_POINTS, ((int) (totalLength / precision)) + 1);
        float[] mX = new float[numPoints];
        float[] mY = new float[numPoints];
        float[] position = new float[2];
        int contourIndex = VALUE_TYPE_FLOAT;
        float step = totalLength / ((float) (numPoints - 1));
        float currentDistance = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        for (int i = VALUE_TYPE_FLOAT; i < numPoints; i++) {
            pathMeasure.getPosTan(currentDistance, position, null);
            pathMeasure.getPosTan(currentDistance, position, null);
            mX[i] = position[0];
            mY[i] = position[1];
            currentDistance += step;
            if (contourIndex + 1 < contourLengths.size() && currentDistance > ((Float) contourLengths.get(contourIndex + 1)).floatValue()) {
                currentDistance -= ((Float) contourLengths.get(contourIndex + 1)).floatValue();
                contourIndex++;
                pathMeasure.nextContour();
            }
        }
        PropertyValuesHolder x = null;
        PropertyValuesHolder y = null;
        if (propertyXName != null) {
            x = PropertyValuesHolder.ofFloat(propertyXName, mX);
        }
        if (propertyYName != null) {
            y = PropertyValuesHolder.ofFloat(propertyYName, mY);
        }
        PropertyValuesHolder[] propertyValuesHolderArr;
        if (x == null) {
            propertyValuesHolderArr = new PropertyValuesHolder[1];
            propertyValuesHolderArr[0] = y;
            oa.setValues(propertyValuesHolderArr);
        } else if (y == null) {
            propertyValuesHolderArr = new PropertyValuesHolder[1];
            propertyValuesHolderArr[0] = x;
            oa.setValues(propertyValuesHolderArr);
        } else {
            propertyValuesHolderArr = new PropertyValuesHolder[2];
            propertyValuesHolderArr[0] = x;
            propertyValuesHolderArr[1] = y;
            oa.setValues(propertyValuesHolderArr);
        }
    }

    private static Animator createAnimatorFromXml(Context context, Resources res, Theme theme, XmlPullParser parser, float pixelSize) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(context, res, theme, parser, Xml.asAttributeSet(parser), null, VALUE_TYPE_FLOAT, pixelSize);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.animation.Animator createAnimatorFromXml(android.content.Context r23_context, android.content.res.Resources r24_res, android.content.res.Resources.Theme r25_theme, org.xmlpull.v1.XmlPullParser r26_parser, android.util.AttributeSet r27_attrs, android.animation.AnimatorSet r28_parent, int r29_sequenceOrdering, float r30_pixelSize) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.AnimatorInflaterCompat.createAnimatorFromXml(android.content.Context, android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.animation.AnimatorSet, int, float):android.animation.Animator");
        /*
        r13 = 0;
        r15 = 0;
        r16 = r26.getDepth();
    L_0x0006:
        r21 = r26.next();
        r4 = 3;
        r0 = r21;
        if (r0 != r4) goto L_0x0017;
    L_0x000f:
        r4 = r26.getDepth();
        r0 = r16;
        if (r4 <= r0) goto L_0x00f2;
    L_0x0017:
        r4 = 1;
        r0 = r21;
        if (r0 == r4) goto L_0x00f2;
    L_0x001c:
        r4 = 2;
        r0 = r21;
        if (r0 != r4) goto L_0x0006;
    L_0x0021:
        r20 = r26.getName();
        r17 = 0;
        r4 = "objectAnimator";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x0050;
    L_0x0031:
        r4 = r23;
        r5 = r24;
        r6 = r25;
        r7 = r27;
        r8 = r30;
        r9 = r26;
        r13 = loadObjectAnimator(r4, r5, r6, r7, r8, r9);
    L_0x0041:
        if (r28 == 0) goto L_0x0006;
    L_0x0043:
        if (r17 != 0) goto L_0x0006;
    L_0x0045:
        if (r15 != 0) goto L_0x004c;
    L_0x0047:
        r15 = new java.util.ArrayList;
        r15.<init>();
    L_0x004c:
        r15.add(r13);
        goto L_0x0006;
    L_0x0050:
        r4 = "animator";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x006c;
    L_0x005a:
        r8 = 0;
        r4 = r23;
        r5 = r24;
        r6 = r25;
        r7 = r27;
        r9 = r30;
        r10 = r26;
        r13 = loadAnimator(r4, r5, r6, r7, r8, r9, r10);
        goto L_0x0041;
    L_0x006c:
        r4 = "set";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x00a7;
    L_0x0076:
        r13 = new android.animation.AnimatorSet;
        r13.<init>();
        r4 = android.support.graphics.drawable.AndroidResources.STYLEABLE_ANIMATOR_SET;
        r0 = r24;
        r1 = r25;
        r2 = r27;
        r12 = android.support.v4.content.res.TypedArrayUtils.obtainAttributes(r0, r1, r2, r4);
        r4 = "ordering";
        r5 = 0;
        r6 = 0;
        r0 = r26;
        r10 = android.support.v4.content.res.TypedArrayUtils.getNamedInt(r12, r0, r4, r5, r6);
        r9 = r13;
        r9 = (android.animation.AnimatorSet) r9;
        r4 = r23;
        r5 = r24;
        r6 = r25;
        r7 = r26;
        r8 = r27;
        r11 = r30;
        createAnimatorFromXml(r4, r5, r6, r7, r8, r9, r10, r11);
        r12.recycle();
        goto L_0x0041;
    L_0x00a7:
        r4 = "propertyValuesHolder";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x00d5;
    L_0x00b1:
        r4 = android.util.Xml.asAttributeSet(r26);
        r0 = r23;
        r1 = r24;
        r2 = r25;
        r3 = r26;
        r22 = loadValues(r0, r1, r2, r3, r4);
        if (r22 == 0) goto L_0x00d1;
    L_0x00c3:
        if (r13 == 0) goto L_0x00d1;
    L_0x00c5:
        r4 = r13 instanceof android.animation.ValueAnimator;
        if (r4 == 0) goto L_0x00d1;
    L_0x00c9:
        r4 = r13;
        r4 = (android.animation.ValueAnimator) r4;
        r0 = r22;
        r4.setValues(r0);
    L_0x00d1:
        r17 = 1;
        goto L_0x0041;
    L_0x00d5:
        r4 = new java.lang.RuntimeException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Unknown animator name: ";
        r5 = r5.append(r6);
        r6 = r26.getName();
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x00f2:
        if (r28 == 0) goto L_0x011c;
    L_0x00f4:
        if (r15 == 0) goto L_0x011c;
    L_0x00f6:
        r4 = r15.size();
        r14 = new android.animation.Animator[r4];
        r18 = 0;
        r4 = r15.iterator();
    L_0x0102:
        r5 = r4.hasNext();
        if (r5 == 0) goto L_0x0115;
    L_0x0108:
        r12 = r4.next();
        r12 = (android.animation.Animator) r12;
        r19 = r18 + 1;
        r14[r18] = r12;
        r18 = r19;
        goto L_0x0102;
    L_0x0115:
        if (r29 != 0) goto L_0x011d;
    L_0x0117:
        r0 = r28;
        r0.playTogether(r14);
    L_0x011c:
        return r13;
    L_0x011d:
        r0 = r28;
        r0.playSequentially(r14);
        goto L_0x011c;
        */
    }

    private static PropertyValuesHolder[] loadValues(Context context, Resources res, Theme theme, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        ArrayList<PropertyValuesHolder> values = null;
        while (true) {
            int type = parser.getEventType();
            if (type == 3 || type == 1) {
                break;
            } else if (type != 2) {
                parser.next();
            } else {
                if (parser.getName().equals("propertyValuesHolder")) {
                    TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                    String propertyName = TypedArrayUtils.getNamedString(a, parser, "propertyName", VALUE_TYPE_COLOR);
                    int valueType = TypedArrayUtils.getNamedInt(a, parser, "valueType", VALUE_TYPE_PATH, VALUE_TYPE_UNDEFINED);
                    PropertyValuesHolder pvh = loadPvh(context, res, theme, parser, propertyName, valueType);
                    if (pvh == null) {
                        pvh = getPVH(a, valueType, VALUE_TYPE_FLOAT, VALUE_TYPE_INT, propertyName);
                    }
                    if (pvh != null) {
                        if (values == null) {
                            values = new ArrayList();
                        }
                        values.add(pvh);
                    }
                    a.recycle();
                }
                parser.next();
            }
        }
        PropertyValuesHolder[] valuesArray = null;
        if (values != null) {
            int count = values.size();
            valuesArray = new PropertyValuesHolder[count];
            for (int i = VALUE_TYPE_FLOAT; i < count; i++) {
                valuesArray[i] = (PropertyValuesHolder) values.get(i);
            }
        }
        return valuesArray;
    }

    private static int inferValueTypeOfKeyframe(Resources res, Theme theme, AttributeSet attrs, XmlPullParser parser) {
        boolean hasValue = DBG_ANIMATOR_INFLATER;
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        TypedValue keyframeValue = TypedArrayUtils.peekNamedValue(a, parser, "value", VALUE_TYPE_FLOAT);
        if (keyframeValue != null) {
            hasValue = true;
        }
        int valueType = (hasValue && isColorType(keyframeValue.type)) ? VALUE_TYPE_COLOR : VALUE_TYPE_FLOAT;
        a.recycle();
        return valueType;
    }

    private static int inferValueTypeFromValues(TypedArray styledAttributes, int valueFromId, int valueToId) {
        boolean hasFrom;
        int fromType;
        int toType;
        boolean hasTo = true;
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        if (tvFrom != null) {
            hasFrom = true;
        } else {
            hasFrom = false;
        }
        if (hasFrom) {
            fromType = tvFrom.type;
        } else {
            fromType = 0;
        }
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        if (tvTo == null) {
            hasTo = false;
        }
        if (hasTo) {
            toType = tvTo.type;
        } else {
            toType = 0;
        }
        return ((hasFrom && isColorType(fromType)) || (hasTo && isColorType(toType))) ? VALUE_TYPE_COLOR : VALUE_TYPE_FLOAT;
    }

    private static void dumpKeyframes(Object[] keyframes, String header) {
        if (keyframes != null && keyframes.length != 0) {
            Log.d(TAG, header);
            int count = keyframes.length;
            for (int i = VALUE_TYPE_FLOAT; i < count; i++) {
                Keyframe keyframe = (Keyframe) keyframes[i];
                Log.d(TAG, "Keyframe " + i + ": fraction " + (keyframe.getFraction() < 0.0f ? "null" : Float.valueOf(keyframe.getFraction())) + ", " + ", value : " + (keyframe.hasValue() ? keyframe.getValue() : "null"));
            }
        }
    }

    private static PropertyValuesHolder loadPvh(Context context, Resources res, Theme theme, XmlPullParser parser, String propertyName, int valueType) throws XmlPullParserException, IOException {
        Keyframe keyframe;
        PropertyValuesHolder value = null;
        ArrayList<Keyframe> keyframes = null;
        while (true) {
            int type = parser.next();
            if (type == 3 || type == 1) {
                break;
            }
            if (parser.getName().equals("keyframe")) {
                if (valueType == 4) {
                    valueType = inferValueTypeOfKeyframe(res, theme, Xml.asAttributeSet(parser), parser);
                }
                keyframe = loadKeyframe(context, res, theme, Xml.asAttributeSet(parser), valueType, parser);
                if (keyframe != null) {
                    if (keyframes == null) {
                        keyframes = new ArrayList();
                    }
                    keyframes.add(keyframe);
                }
                parser.next();
            }
        }
        if (keyframes != null) {
            int count = keyframes.size();
            if (count > 0) {
                Keyframe firstKeyframe = (Keyframe) keyframes.get(VALUE_TYPE_FLOAT);
                Keyframe lastKeyframe = (Keyframe) keyframes.get(count - 1);
                float endFraction = lastKeyframe.getFraction();
                if (endFraction < 1.0f) {
                    if (endFraction < 0.0f) {
                        lastKeyframe.setFraction(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    } else {
                        keyframes.add(keyframes.size(), createNewKeyframe(lastKeyframe, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        count++;
                    }
                }
                float startFraction = firstKeyframe.getFraction();
                if (startFraction != 0.0f) {
                    if (startFraction < 0.0f) {
                        firstKeyframe.setFraction(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    } else {
                        keyframes.add(VALUE_TYPE_FLOAT, createNewKeyframe(firstKeyframe, AutoScrollHelper.RELATIVE_UNSPECIFIED));
                        count++;
                    }
                }
                Keyframe[] keyframeArray = new Keyframe[count];
                keyframes.toArray(keyframeArray);
                for (int i = VALUE_TYPE_FLOAT; i < count; i++) {
                    keyframe = keyframeArray[i];
                    if (keyframe.getFraction() < 0.0f) {
                        if (i == 0) {
                            keyframe.setFraction(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                        } else if (i == count - 1) {
                            keyframe.setFraction(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        } else {
                            int startIndex = i;
                            int endIndex = i;
                            int j = startIndex + 1;
                            while (j < count - 1 && keyframeArray[j].getFraction() < 0.0f) {
                                endIndex = j;
                                j++;
                            }
                            distributeKeyframes(keyframeArray, keyframeArray[endIndex + 1].getFraction() - keyframeArray[startIndex - 1].getFraction(), startIndex, endIndex);
                        }
                    }
                }
                value = PropertyValuesHolder.ofKeyframe(propertyName, keyframeArray);
                if (valueType == 3) {
                    value.setEvaluator(ArgbEvaluator.getInstance());
                }
            }
        }
        return value;
    }

    private static Keyframe createNewKeyframe(Keyframe sampleKeyframe, float fraction) {
        if (sampleKeyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(fraction);
        }
        return sampleKeyframe.getType() == Integer.TYPE ? Keyframe.ofInt(fraction) : Keyframe.ofObject(fraction);
    }

    private static void distributeKeyframes(Keyframe[] keyframes, float gap, int startIndex, int endIndex) {
        float increment = gap / ((float) ((endIndex - startIndex) + 2));
        for (int i = startIndex; i <= endIndex; i++) {
            keyframes[i].setFraction(keyframes[i - 1].getFraction() + increment);
        }
    }

    private static Keyframe loadKeyframe(Context context, Resources res, Theme theme, AttributeSet attrs, int valueType, XmlPullParser parser) throws XmlPullParserException, IOException {
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        Keyframe keyframe = null;
        float fraction = TypedArrayUtils.getNamedFloat(a, parser, "fraction", VALUE_TYPE_COLOR, -1.0f);
        TypedValue keyframeValue = TypedArrayUtils.peekNamedValue(a, parser, "value", VALUE_TYPE_FLOAT);
        boolean hasValue = keyframeValue != null ? true : DBG_ANIMATOR_INFLATER;
        if (valueType == 4) {
            valueType = (hasValue && isColorType(keyframeValue.type)) ? VALUE_TYPE_COLOR : VALUE_TYPE_FLOAT;
        }
        if (hasValue) {
            switch (valueType) {
                case VALUE_TYPE_FLOAT:
                    keyframe = Keyframe.ofFloat(fraction, TypedArrayUtils.getNamedFloat(a, parser, "value", VALUE_TYPE_FLOAT, AutoScrollHelper.RELATIVE_UNSPECIFIED));
                    break;
                case VALUE_TYPE_INT:
                case VALUE_TYPE_COLOR:
                    keyframe = Keyframe.ofInt(fraction, TypedArrayUtils.getNamedInt(a, parser, "value", VALUE_TYPE_FLOAT, VALUE_TYPE_FLOAT));
                    break;
            }
        } else if (valueType == 0) {
            keyframe = Keyframe.ofFloat(fraction);
        } else {
            keyframe = Keyframe.ofInt(fraction);
        }
        int resID = TypedArrayUtils.getNamedResourceId(a, parser, "interpolator", VALUE_TYPE_INT, VALUE_TYPE_FLOAT);
        if (resID > 0) {
            keyframe.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, resID));
        }
        a.recycle();
        return keyframe;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources res, Theme theme, AttributeSet attrs, float pathErrorScale, XmlPullParser parser) throws NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(context, res, theme, attrs, anim, pathErrorScale, parser);
        return anim;
    }

    private static ValueAnimator loadAnimator(Context context, Resources res, Theme theme, AttributeSet attrs, ValueAnimator anim, float pathErrorScale, XmlPullParser parser) throws NotFoundException {
        TypedArray arrayAnimator = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_ANIMATOR);
        TypedArray arrayObjectAnimator = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        if (anim == null) {
            anim = new ValueAnimator();
        }
        parseAnimatorFromTypeArray(anim, arrayAnimator, arrayObjectAnimator, pathErrorScale, parser);
        int resID = TypedArrayUtils.getNamedResourceId(arrayAnimator, parser, "interpolator", VALUE_TYPE_FLOAT, VALUE_TYPE_FLOAT);
        if (resID > 0) {
            anim.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, resID));
        }
        arrayAnimator.recycle();
        if (arrayObjectAnimator != null) {
            arrayObjectAnimator.recycle();
        }
        return anim;
    }

    private static boolean isColorType(int type) {
        return (type < 28 || type > 31) ? DBG_ANIMATOR_INFLATER : true;
    }
}
