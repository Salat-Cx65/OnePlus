package android.support.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.VectorDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.PathParser.PathDataNode;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.sdk.utils.OpBoostFramework;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat extends VectorDrawableCommon {
    private static final boolean DBG_VECTOR_DRAWABLE = false;
    static final Mode DEFAULT_TINT_MODE;
    private static final int LINECAP_BUTT = 0;
    private static final int LINECAP_ROUND = 1;
    private static final int LINECAP_SQUARE = 2;
    private static final int LINEJOIN_BEVEL = 2;
    private static final int LINEJOIN_MITER = 0;
    private static final int LINEJOIN_ROUND = 1;
    static final String LOGTAG = "VectorDrawableCompat";
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    private boolean mAllowCaching;
    private ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private final float[] mTmpFloats;
    private final Matrix mTmpMatrix;
    private VectorDrawableCompatState mVectorState;

    private static class VGroup {
        int mChangingConfigurations;
        final ArrayList<Object> mChildren;
        private String mGroupName;
        private final Matrix mLocalMatrix;
        private float mPivotX;
        private float mPivotY;
        float mRotate;
        private float mScaleX;
        private float mScaleY;
        private final Matrix mStackedMatrix;
        private int[] mThemeAttrs;
        private float mTranslateX;
        private float mTranslateY;

        public VGroup(VGroup copy, ArrayMap<String, Object> targetsMap) {
            this.mStackedMatrix = new Matrix();
            this.mChildren = new ArrayList();
            this.mRotate = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            this.mLocalMatrix = new Matrix();
            this.mGroupName = null;
            this.mRotate = copy.mRotate;
            this.mPivotX = copy.mPivotX;
            this.mPivotY = copy.mPivotY;
            this.mScaleX = copy.mScaleX;
            this.mScaleY = copy.mScaleY;
            this.mTranslateX = copy.mTranslateX;
            this.mTranslateY = copy.mTranslateY;
            this.mThemeAttrs = copy.mThemeAttrs;
            this.mGroupName = copy.mGroupName;
            this.mChangingConfigurations = copy.mChangingConfigurations;
            if (this.mGroupName != null) {
                targetsMap.put(this.mGroupName, this);
            }
            this.mLocalMatrix.set(copy.mLocalMatrix);
            ArrayList<Object> children = copy.mChildren;
            for (int i = LINEJOIN_MITER; i < children.size(); i++) {
                Object copyChild = children.get(i);
                if (copyChild instanceof VGroup) {
                    this.mChildren.add(new VGroup((VGroup) copyChild, targetsMap));
                } else {
                    VPath newPath;
                    if (copyChild instanceof VFullPath) {
                        newPath = new VFullPath((VFullPath) copyChild);
                    } else if (copyChild instanceof VClipPath) {
                        newPath = new VClipPath((VClipPath) copyChild);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.mChildren.add(newPath);
                    if (newPath.mPathName != null) {
                        targetsMap.put(newPath.mPathName, newPath);
                    }
                }
            }
        }

        public VGroup() {
            this.mStackedMatrix = new Matrix();
            this.mChildren = new ArrayList();
            this.mRotate = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            this.mLocalMatrix = new Matrix();
            this.mGroupName = null;
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.mLocalMatrix;
        }

        public void inflate(Resources res, AttributeSet attrs, Theme theme, XmlPullParser parser) {
            TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
            updateStateFromTypedArray(a, parser);
            a.recycle();
        }

        private void updateStateFromTypedArray(TypedArray a, XmlPullParser parser) {
            this.mThemeAttrs = null;
            this.mRotate = TypedArrayUtils.getNamedFloat(a, parser, "rotation", RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.mRotate);
            this.mPivotX = a.getFloat(LINEJOIN_ROUND, this.mPivotX);
            this.mPivotY = a.getFloat(LINEJOIN_BEVEL, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat(a, parser, "scaleX", RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat(a, parser, "scaleY", RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat(a, parser, "translateX", ConnectionResult.RESOLUTION_REQUIRED, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat(a, parser, "translateY", DetectedActivity.WALKING, this.mTranslateY);
            String groupName = a.getString(LINEJOIN_MITER);
            if (groupName != null) {
                this.mGroupName = groupName;
            }
            updateLocalMatrix();
        }

        private void updateLocalMatrix() {
            this.mLocalMatrix.reset();
            this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
            this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
            this.mLocalMatrix.postRotate(this.mRotate, AutoScrollHelper.RELATIVE_UNSPECIFIED, AutoScrollHelper.RELATIVE_UNSPECIFIED);
            this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        public float getRotation() {
            return this.mRotate;
        }

        public void setRotation(float rotation) {
            if (rotation != this.mRotate) {
                this.mRotate = rotation;
                updateLocalMatrix();
            }
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public void setPivotX(float pivotX) {
            if (pivotX != this.mPivotX) {
                this.mPivotX = pivotX;
                updateLocalMatrix();
            }
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public void setPivotY(float pivotY) {
            if (pivotY != this.mPivotY) {
                this.mPivotY = pivotY;
                updateLocalMatrix();
            }
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public void setScaleX(float scaleX) {
            if (scaleX != this.mScaleX) {
                this.mScaleX = scaleX;
                updateLocalMatrix();
            }
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public void setScaleY(float scaleY) {
            if (scaleY != this.mScaleY) {
                this.mScaleY = scaleY;
                updateLocalMatrix();
            }
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public void setTranslateX(float translateX) {
            if (translateX != this.mTranslateX) {
                this.mTranslateX = translateX;
                updateLocalMatrix();
            }
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        public void setTranslateY(float translateY) {
            if (translateY != this.mTranslateY) {
                this.mTranslateY = translateY;
                updateLocalMatrix();
            }
        }
    }

    private static class VPath {
        int mChangingConfigurations;
        protected PathDataNode[] mNodes;
        String mPathName;

        public VPath() {
            this.mNodes = null;
        }

        public void printVPath(int level) {
            String indent = StringUtils.EMPTY_STRING;
            for (int i = LINEJOIN_MITER; i < level; i++) {
                indent = indent + "    ";
            }
            Log.v(LOGTAG, indent + "current path is :" + this.mPathName + " pathData is " + nodesToString(this.mNodes));
        }

        public String nodesToString(PathDataNode[] nodes) {
            String result = " ";
            for (int i = LINEJOIN_MITER; i < nodes.length; i++) {
                result = result + nodes[i].mType + ":";
                float[] params = nodes[i].mParams;
                for (int j = LINEJOIN_MITER; j < params.length; j++) {
                    result = result + params[j] + ",";
                }
            }
            return result;
        }

        public VPath(VPath copy) {
            this.mNodes = null;
            this.mPathName = copy.mPathName;
            this.mChangingConfigurations = copy.mChangingConfigurations;
            this.mNodes = PathParser.deepCopyNodes(copy.mNodes);
        }

        public void toPath(Path path) {
            path.reset();
            if (this.mNodes != null) {
                PathDataNode.nodesToPath(this.mNodes, path);
            }
        }

        public String getPathName() {
            return this.mPathName;
        }

        public boolean canApplyTheme() {
            return DBG_VECTOR_DRAWABLE;
        }

        public void applyTheme(Theme t) {
        }

        public boolean isClipPath() {
            return DBG_VECTOR_DRAWABLE;
        }

        public PathDataNode[] getPathData() {
            return this.mNodes;
        }

        public void setPathData(PathDataNode[] nodes) {
            if (PathParser.canMorph(this.mNodes, nodes)) {
                PathParser.updateNodes(this.mNodes, nodes);
            } else {
                this.mNodes = PathParser.deepCopyNodes(nodes);
            }
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX;
        float mBaseHeight;
        float mBaseWidth;
        private int mChangingConfigurations;
        private Paint mFillPaint;
        private final Matrix mFinalPathMatrix;
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;
        int mRootAlpha;
        final VGroup mRootGroup;
        String mRootName;
        private Paint mStrokePaint;
        final ArrayMap<String, Object> mVGTargetsMap;
        float mViewportHeight;
        float mViewportWidth;

        static {
            IDENTITY_MATRIX = new Matrix();
        }

        public VPathRenderer() {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mVGTargetsMap = new ArrayMap();
            this.mRootGroup = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public void setRootAlpha(int alpha) {
            this.mRootAlpha = alpha;
        }

        public int getRootAlpha() {
            return this.mRootAlpha;
        }

        public void setAlpha(float alpha) {
            setRootAlpha((int) (255.0f * alpha));
        }

        public float getAlpha() {
            return ((float) getRootAlpha()) / 255.0f;
        }

        public VPathRenderer(VPathRenderer copy) {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mVGTargetsMap = new ArrayMap();
            this.mRootGroup = new VGroup(copy.mRootGroup, this.mVGTargetsMap);
            this.mPath = new Path(copy.mPath);
            this.mRenderPath = new Path(copy.mRenderPath);
            this.mBaseWidth = copy.mBaseWidth;
            this.mBaseHeight = copy.mBaseHeight;
            this.mViewportWidth = copy.mViewportWidth;
            this.mViewportHeight = copy.mViewportHeight;
            this.mChangingConfigurations = copy.mChangingConfigurations;
            this.mRootAlpha = copy.mRootAlpha;
            this.mRootName = copy.mRootName;
            if (copy.mRootName != null) {
                this.mVGTargetsMap.put(copy.mRootName, this);
            }
        }

        private void drawGroupTree(VGroup currentGroup, Matrix currentMatrix, Canvas canvas, int w, int h, ColorFilter filter) {
            currentGroup.mStackedMatrix.set(currentMatrix);
            currentGroup.mStackedMatrix.preConcat(currentGroup.mLocalMatrix);
            canvas.save();
            for (int i = LINEJOIN_MITER; i < currentGroup.mChildren.size(); i++) {
                Object child = currentGroup.mChildren.get(i);
                if (child instanceof VGroup) {
                    drawGroupTree((VGroup) child, currentGroup.mStackedMatrix, canvas, w, h, filter);
                } else if (child instanceof VPath) {
                    drawPath(currentGroup, (VPath) child, canvas, w, h, filter);
                }
            }
            canvas.restore();
        }

        public void draw(Canvas canvas, int w, int h, ColorFilter filter) {
            drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, canvas, w, h, filter);
        }

        private void drawPath(VGroup vGroup, VPath vPath, Canvas canvas, int w, int h, ColorFilter filter) {
            float scaleX = ((float) w) / this.mViewportWidth;
            float scaleY = ((float) h) / this.mViewportHeight;
            float minScale = Math.min(scaleX, scaleY);
            Matrix groupStackedMatrix = vGroup.mStackedMatrix;
            this.mFinalPathMatrix.set(groupStackedMatrix);
            this.mFinalPathMatrix.postScale(scaleX, scaleY);
            float matrixScale = getMatrixScale(groupStackedMatrix);
            if (matrixScale != 0.0f) {
                vPath.toPath(this.mPath);
                Path path = this.mPath;
                this.mRenderPath.reset();
                if (vPath.isClipPath()) {
                    this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                    canvas.clipPath(this.mRenderPath);
                    return;
                }
                VFullPath fullPath = (VFullPath) vPath;
                if (!(fullPath.mTrimPathStart == 0.0f && fullPath.mTrimPathEnd == 1.0f)) {
                    float start = (fullPath.mTrimPathStart + fullPath.mTrimPathOffset) % 1.0f;
                    float end = (fullPath.mTrimPathEnd + fullPath.mTrimPathOffset) % 1.0f;
                    if (this.mPathMeasure == null) {
                        this.mPathMeasure = new PathMeasure();
                    }
                    this.mPathMeasure.setPath(this.mPath, DBG_VECTOR_DRAWABLE);
                    float len = this.mPathMeasure.getLength();
                    start *= len;
                    end *= len;
                    path.reset();
                    if (start > end) {
                        this.mPathMeasure.getSegment(start, len, path, true);
                        this.mPathMeasure.getSegment(AutoScrollHelper.RELATIVE_UNSPECIFIED, end, path, true);
                    } else {
                        this.mPathMeasure.getSegment(start, end, path, true);
                    }
                    path.rLineTo(AutoScrollHelper.RELATIVE_UNSPECIFIED, AutoScrollHelper.RELATIVE_UNSPECIFIED);
                }
                this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                if (fullPath.mFillColor != 0) {
                    if (this.mFillPaint == null) {
                        this.mFillPaint = new Paint();
                        this.mFillPaint.setStyle(Style.FILL);
                        this.mFillPaint.setAntiAlias(true);
                    }
                    Paint fillPaint = this.mFillPaint;
                    fillPaint.setColor(VectorDrawableCompat.applyAlpha(fullPath.mFillColor, fullPath.mFillAlpha));
                    fillPaint.setColorFilter(filter);
                    this.mRenderPath.setFillType(fullPath.mFillRule == 0 ? FillType.WINDING : FillType.EVEN_ODD);
                    canvas.drawPath(this.mRenderPath, fillPaint);
                }
                if (fullPath.mStrokeColor != 0) {
                    if (this.mStrokePaint == null) {
                        this.mStrokePaint = new Paint();
                        this.mStrokePaint.setStyle(Style.STROKE);
                        this.mStrokePaint.setAntiAlias(true);
                    }
                    Paint strokePaint = this.mStrokePaint;
                    if (fullPath.mStrokeLineJoin != null) {
                        strokePaint.setStrokeJoin(fullPath.mStrokeLineJoin);
                    }
                    if (fullPath.mStrokeLineCap != null) {
                        strokePaint.setStrokeCap(fullPath.mStrokeLineCap);
                    }
                    strokePaint.setStrokeMiter(fullPath.mStrokeMiterlimit);
                    strokePaint.setColor(VectorDrawableCompat.applyAlpha(fullPath.mStrokeColor, fullPath.mStrokeAlpha));
                    strokePaint.setColorFilter(filter);
                    strokePaint.setStrokeWidth(fullPath.mStrokeWidth * (minScale * matrixScale));
                    canvas.drawPath(this.mRenderPath, strokePaint);
                }
            }
        }

        private static float cross(float v1x, float v1y, float v2x, float v2y) {
            return (v1x * v2y) - (v1y * v2x);
        }

        private float getMatrixScale(Matrix groupStackedMatrix) {
            float[] unitVectors = new float[]{0.0f, 1.0f, 1.0f, 0.0f};
            groupStackedMatrix.mapVectors(unitVectors);
            float scaleX = (float) Math.hypot((double) unitVectors[0], (double) unitVectors[1]);
            float scaleY = (float) Math.hypot((double) unitVectors[2], (double) unitVectors[3]);
            float crossProduct = cross(unitVectors[0], unitVectors[1], unitVectors[2], unitVectors[3]);
            float maxScale = Math.max(scaleX, scaleY);
            return maxScale > 0.0f ? Math.abs(crossProduct) / maxScale : AutoScrollHelper.RELATIVE_UNSPECIFIED;
        }
    }

    private static class VectorDrawableCompatState extends ConstantState {
        boolean mAutoMirrored;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        Bitmap mCachedBitmap;
        int mCachedRootAlpha;
        int[] mCachedThemeAttrs;
        ColorStateList mCachedTint;
        Mode mCachedTintMode;
        int mChangingConfigurations;
        Paint mTempPaint;
        ColorStateList mTint;
        Mode mTintMode;
        VPathRenderer mVPathRenderer;

        public VectorDrawableCompatState(VectorDrawableCompatState copy) {
            this.mTint = null;
            this.mTintMode = DEFAULT_TINT_MODE;
            if (copy != null) {
                this.mChangingConfigurations = copy.mChangingConfigurations;
                this.mVPathRenderer = new VPathRenderer(copy.mVPathRenderer);
                if (copy.mVPathRenderer.mFillPaint != null) {
                    this.mVPathRenderer.mFillPaint = new Paint(copy.mVPathRenderer.mFillPaint);
                }
                if (copy.mVPathRenderer.mStrokePaint != null) {
                    this.mVPathRenderer.mStrokePaint = new Paint(copy.mVPathRenderer.mStrokePaint);
                }
                this.mTint = copy.mTint;
                this.mTintMode = copy.mTintMode;
                this.mAutoMirrored = copy.mAutoMirrored;
            }
        }

        public void drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter filter, Rect originalBounds) {
            canvas.drawBitmap(this.mCachedBitmap, null, originalBounds, getPaint(filter));
        }

        public boolean hasTranslucentRoot() {
            return this.mVPathRenderer.getRootAlpha() < 255 ? true : DBG_VECTOR_DRAWABLE;
        }

        public Paint getPaint(ColorFilter filter) {
            if (!hasTranslucentRoot() && filter == null) {
                return null;
            }
            if (this.mTempPaint == null) {
                this.mTempPaint = new Paint();
                this.mTempPaint.setFilterBitmap(true);
            }
            this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha());
            this.mTempPaint.setColorFilter(filter);
            return this.mTempPaint;
        }

        public void updateCachedBitmap(int width, int height) {
            this.mCachedBitmap.eraseColor(LINEJOIN_MITER);
            this.mVPathRenderer.draw(new Canvas(this.mCachedBitmap), width, height, null);
        }

        public void createCachedBitmapIfNeeded(int width, int height) {
            if (this.mCachedBitmap == null || !canReuseBitmap(width, height)) {
                this.mCachedBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                this.mCacheDirty = true;
            }
        }

        public boolean canReuseBitmap(int width, int height) {
            return (width == this.mCachedBitmap.getWidth() && height == this.mCachedBitmap.getHeight()) ? true : DBG_VECTOR_DRAWABLE;
        }

        public boolean canReuseCache() {
            return (!this.mCacheDirty && this.mCachedTint == this.mTint && this.mCachedTintMode == this.mTintMode && this.mCachedAutoMirrored == this.mAutoMirrored && this.mCachedRootAlpha == this.mVPathRenderer.getRootAlpha()) ? true : DBG_VECTOR_DRAWABLE;
        }

        public void updateCacheStates() {
            this.mCachedTint = this.mTint;
            this.mCachedTintMode = this.mTintMode;
            this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha();
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public VectorDrawableCompatState() {
            this.mTint = null;
            this.mTintMode = DEFAULT_TINT_MODE;
            this.mVPathRenderer = new VPathRenderer();
        }

        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public Drawable newDrawable(Resources res) {
            return new VectorDrawableCompat(this);
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    @RequiresApi(24)
    private static class VectorDrawableDelegateState extends ConstantState {
        private final ConstantState mDelegateState;

        public VectorDrawableDelegateState(ConstantState state) {
            this.mDelegateState = state;
        }

        public Drawable newDrawable() {
            VectorDrawableCompat drawableCompat = new VectorDrawableCompat();
            drawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable();
            return drawableCompat;
        }

        public Drawable newDrawable(Resources res) {
            VectorDrawableCompat drawableCompat = new VectorDrawableCompat();
            drawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(res);
            return drawableCompat;
        }

        public Drawable newDrawable(Resources res, Theme theme) {
            VectorDrawableCompat drawableCompat = new VectorDrawableCompat();
            drawableCompat.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable(res, theme);
            return drawableCompat;
        }

        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }
    }

    private static class VClipPath extends VPath {
        public VClipPath(VClipPath copy) {
            super(copy);
        }

        public void inflate(Resources r, AttributeSet attrs, Theme theme, XmlPullParser parser) {
            if (TypedArrayUtils.hasAttribute(parser, "pathData")) {
                TypedArray a = TypedArrayUtils.obtainAttributes(r, theme, attrs, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
                updateStateFromTypedArray(a);
                a.recycle();
            }
        }

        private void updateStateFromTypedArray(TypedArray a) {
            String pathName = a.getString(LINEJOIN_MITER);
            if (pathName != null) {
                this.mPathName = pathName;
            }
            String pathData = a.getString(LINEJOIN_ROUND);
            if (pathData != null) {
                this.mNodes = PathParser.createNodesFromPathData(pathData);
            }
        }

        public boolean isClipPath() {
            return true;
        }
    }

    private static class VFullPath extends VPath {
        float mFillAlpha;
        int mFillColor;
        int mFillRule;
        float mStrokeAlpha;
        int mStrokeColor;
        Cap mStrokeLineCap;
        Join mStrokeLineJoin;
        float mStrokeMiterlimit;
        float mStrokeWidth;
        private int[] mThemeAttrs;
        float mTrimPathEnd;
        float mTrimPathOffset;
        float mTrimPathStart;

        public VFullPath() {
            this.mStrokeColor = 0;
            this.mStrokeWidth = 0.0f;
            this.mFillColor = 0;
            this.mStrokeAlpha = 1.0f;
            this.mFillRule = 0;
            this.mFillAlpha = 1.0f;
            this.mTrimPathStart = 0.0f;
            this.mTrimPathEnd = 1.0f;
            this.mTrimPathOffset = 0.0f;
            this.mStrokeLineCap = Cap.BUTT;
            this.mStrokeLineJoin = Join.MITER;
            this.mStrokeMiterlimit = 4.0f;
        }

        public VFullPath(VFullPath copy) {
            super(copy);
            this.mStrokeColor = 0;
            this.mStrokeWidth = 0.0f;
            this.mFillColor = 0;
            this.mStrokeAlpha = 1.0f;
            this.mFillRule = 0;
            this.mFillAlpha = 1.0f;
            this.mTrimPathStart = 0.0f;
            this.mTrimPathEnd = 1.0f;
            this.mTrimPathOffset = 0.0f;
            this.mStrokeLineCap = Cap.BUTT;
            this.mStrokeLineJoin = Join.MITER;
            this.mStrokeMiterlimit = 4.0f;
            this.mThemeAttrs = copy.mThemeAttrs;
            this.mStrokeColor = copy.mStrokeColor;
            this.mStrokeWidth = copy.mStrokeWidth;
            this.mStrokeAlpha = copy.mStrokeAlpha;
            this.mFillColor = copy.mFillColor;
            this.mFillRule = copy.mFillRule;
            this.mFillAlpha = copy.mFillAlpha;
            this.mTrimPathStart = copy.mTrimPathStart;
            this.mTrimPathEnd = copy.mTrimPathEnd;
            this.mTrimPathOffset = copy.mTrimPathOffset;
            this.mStrokeLineCap = copy.mStrokeLineCap;
            this.mStrokeLineJoin = copy.mStrokeLineJoin;
            this.mStrokeMiterlimit = copy.mStrokeMiterlimit;
        }

        private Cap getStrokeLineCap(int id, Cap defValue) {
            switch (id) {
                case LINEJOIN_MITER:
                    return Cap.BUTT;
                case LINEJOIN_ROUND:
                    return Cap.ROUND;
                case LINEJOIN_BEVEL:
                    return Cap.SQUARE;
                default:
                    return defValue;
            }
        }

        private Join getStrokeLineJoin(int id, Join defValue) {
            switch (id) {
                case LINEJOIN_MITER:
                    return Join.MITER;
                case LINEJOIN_ROUND:
                    return Join.ROUND;
                case LINEJOIN_BEVEL:
                    return Join.BEVEL;
                default:
                    return defValue;
            }
        }

        public boolean canApplyTheme() {
            return this.mThemeAttrs != null ? true : DBG_VECTOR_DRAWABLE;
        }

        public void inflate(Resources r, AttributeSet attrs, Theme theme, XmlPullParser parser) {
            TypedArray a = TypedArrayUtils.obtainAttributes(r, theme, attrs, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
            updateStateFromTypedArray(a, parser);
            a.recycle();
        }

        private void updateStateFromTypedArray(TypedArray a, XmlPullParser parser) {
            this.mThemeAttrs = null;
            if (TypedArrayUtils.hasAttribute(parser, "pathData")) {
                String pathName = a.getString(LINEJOIN_MITER);
                if (pathName != null) {
                    this.mPathName = pathName;
                }
                String pathData = a.getString(LINEJOIN_BEVEL);
                if (pathData != null) {
                    this.mNodes = PathParser.createNodesFromPathData(pathData);
                }
                this.mFillColor = TypedArrayUtils.getNamedColor(a, parser, "fillColor", LINEJOIN_ROUND, this.mFillColor);
                this.mFillAlpha = TypedArrayUtils.getNamedFloat(a, parser, "fillAlpha", WeatherCircleView.ARC_DIN, this.mFillAlpha);
                this.mStrokeLineCap = getStrokeLineCap(TypedArrayUtils.getNamedInt(a, parser, "strokeLineCap", DetectedActivity.RUNNING, -1), this.mStrokeLineCap);
                this.mStrokeLineJoin = getStrokeLineJoin(TypedArrayUtils.getNamedInt(a, parser, "strokeLineJoin", ConnectionResult.SERVICE_INVALID, -1), this.mStrokeLineJoin);
                this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(a, parser, "strokeMiterLimit", ConnectionResult.DEVELOPER_ERROR, this.mStrokeMiterlimit);
                this.mStrokeColor = TypedArrayUtils.getNamedColor(a, parser, "strokeColor", RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.mStrokeColor);
                this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(a, parser, "strokeAlpha", ConnectionResult.LICENSE_CHECK_FAILED, this.mStrokeAlpha);
                this.mStrokeWidth = TypedArrayUtils.getNamedFloat(a, parser, "strokeWidth", RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.mStrokeWidth);
                this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(a, parser, "trimPathEnd", ConnectionResult.RESOLUTION_REQUIRED, this.mTrimPathEnd);
                this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(a, parser, "trimPathOffset", DetectedActivity.WALKING, this.mTrimPathOffset);
                this.mTrimPathStart = TypedArrayUtils.getNamedFloat(a, parser, "trimPathStart", RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.mTrimPathStart);
                this.mFillRule = TypedArrayUtils.getNamedInt(a, parser, "fillType", ConnectionResult.CANCELED, this.mFillRule);
            }
        }

        public void applyTheme(Theme t) {
            if (this.mThemeAttrs != null) {
            }
        }

        int getStrokeColor() {
            return this.mStrokeColor;
        }

        void setStrokeColor(int strokeColor) {
            this.mStrokeColor = strokeColor;
        }

        float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        void setStrokeWidth(float strokeWidth) {
            this.mStrokeWidth = strokeWidth;
        }

        float getStrokeAlpha() {
            return this.mStrokeAlpha;
        }

        void setStrokeAlpha(float strokeAlpha) {
            this.mStrokeAlpha = strokeAlpha;
        }

        int getFillColor() {
            return this.mFillColor;
        }

        void setFillColor(int fillColor) {
            this.mFillColor = fillColor;
        }

        float getFillAlpha() {
            return this.mFillAlpha;
        }

        void setFillAlpha(float fillAlpha) {
            this.mFillAlpha = fillAlpha;
        }

        float getTrimPathStart() {
            return this.mTrimPathStart;
        }

        void setTrimPathStart(float trimPathStart) {
            this.mTrimPathStart = trimPathStart;
        }

        float getTrimPathEnd() {
            return this.mTrimPathEnd;
        }

        void setTrimPathEnd(float trimPathEnd) {
            this.mTrimPathEnd = trimPathEnd;
        }

        float getTrimPathOffset() {
            return this.mTrimPathOffset;
        }

        void setTrimPathOffset(float trimPathOffset) {
            this.mTrimPathOffset = trimPathOffset;
        }
    }

    public /* bridge */ /* synthetic */ void applyTheme(Theme theme) {
        super.applyTheme(theme);
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i, Mode mode) {
        super.setColorFilter(i, mode);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    static {
        DEFAULT_TINT_MODE = Mode.SRC_IN;
    }

    VectorDrawableCompat() {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    VectorDrawableCompat(@NonNull VectorDrawableCompatState state) {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = state;
        this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, state.mTintMode);
    }

    public Drawable mutate() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.mutate();
        } else if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    Object getTargetByName(String name) {
        return this.mVectorState.mVPathRenderer.mVGTargetsMap.get(name);
    }

    public ConstantState getConstantState() {
        if (this.mDelegateDrawable != null && VERSION.SDK_INT >= 24) {
            return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        this.mVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mVectorState;
    }

    public void draw(Canvas canvas) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.draw(canvas);
            return;
        }
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() > 0 && this.mTmpBounds.height() > 0) {
            ColorFilter colorFilter = this.mColorFilter == null ? this.mTintFilter : this.mColorFilter;
            canvas.getMatrix(this.mTmpMatrix);
            this.mTmpMatrix.getValues(this.mTmpFloats);
            float canvasScaleX = Math.abs(this.mTmpFloats[0]);
            float canvasScaleY = Math.abs(this.mTmpFloats[4]);
            float canvasSkewX = Math.abs(this.mTmpFloats[1]);
            float canvasSkewY = Math.abs(this.mTmpFloats[3]);
            if (!(canvasSkewX == 0.0f && canvasSkewY == 0.0f)) {
                canvasScaleX = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                canvasScaleY = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
            }
            int scaledHeight = (int) (((float) this.mTmpBounds.height()) * canvasScaleY);
            int scaledWidth = Math.min(MAX_CACHED_BITMAP_SIZE, (int) (((float) this.mTmpBounds.width()) * canvasScaleX));
            scaledHeight = Math.min(MAX_CACHED_BITMAP_SIZE, scaledHeight);
            if (scaledWidth > 0 && scaledHeight > 0) {
                int saveCount = canvas.save();
                canvas.translate((float) this.mTmpBounds.left, (float) this.mTmpBounds.top);
                if (needMirroring()) {
                    canvas.translate((float) this.mTmpBounds.width(), AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    canvas.scale(-1.0f, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                }
                this.mTmpBounds.offsetTo(LINEJOIN_MITER, LINEJOIN_MITER);
                this.mVectorState.createCachedBitmapIfNeeded(scaledWidth, scaledHeight);
                if (!this.mAllowCaching) {
                    this.mVectorState.updateCachedBitmap(scaledWidth, scaledHeight);
                } else if (!this.mVectorState.canReuseCache()) {
                    this.mVectorState.updateCachedBitmap(scaledWidth, scaledHeight);
                    this.mVectorState.updateCacheStates();
                }
                this.mVectorState.drawCachedBitmapWithRootAlpha(canvas, colorFilter, this.mTmpBounds);
                canvas.restoreToCount(saveCount);
            }
        }
    }

    public int getAlpha() {
        return this.mDelegateDrawable != null ? DrawableCompat.getAlpha(this.mDelegateDrawable) : this.mVectorState.mVPathRenderer.getRootAlpha();
    }

    public void setAlpha(int alpha) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setAlpha(alpha);
        } else if (this.mVectorState.mVPathRenderer.getRootAlpha() != alpha) {
            this.mVectorState.mVPathRenderer.setRootAlpha(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter(colorFilter);
            return;
        }
        this.mColorFilter = colorFilter;
        invalidateSelf();
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter tintFilter, ColorStateList tint, Mode tintMode) {
        return (tint == null || tintMode == null) ? null : new PorterDuffColorFilter(tint.getColorForState(getState(), LINEJOIN_MITER), tintMode);
    }

    public void setTint(int tint) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTint(this.mDelegateDrawable, tint);
        } else {
            setTintList(ColorStateList.valueOf(tint));
        }
    }

    public void setTintList(ColorStateList tint) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintList(this.mDelegateDrawable, tint);
            return;
        }
        VectorDrawableCompatState state = this.mVectorState;
        if (state.mTint != tint) {
            state.mTint = tint;
            this.mTintFilter = updateTintFilter(this.mTintFilter, tint, state.mTintMode);
            invalidateSelf();
        }
    }

    public void setTintMode(Mode tintMode) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintMode(this.mDelegateDrawable, tintMode);
            return;
        }
        VectorDrawableCompatState state = this.mVectorState;
        if (state.mTintMode != tintMode) {
            state.mTintMode = tintMode;
            this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, tintMode);
            invalidateSelf();
        }
    }

    public boolean isStateful() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.isStateful();
        }
        return (super.isStateful() || !(this.mVectorState == null || this.mVectorState.mTint == null || !this.mVectorState.mTint.isStateful())) ? true : DBG_VECTOR_DRAWABLE;
    }

    protected boolean onStateChange(int[] stateSet) {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState(stateSet);
        }
        VectorDrawableCompatState state = this.mVectorState;
        if (state.mTint == null || state.mTintMode == null) {
            return DBG_VECTOR_DRAWABLE;
        }
        this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, state.mTintMode);
        invalidateSelf();
        return true;
    }

    public int getOpacity() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getOpacity() : OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY;
    }

    public int getIntrinsicWidth() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getIntrinsicWidth() : (int) this.mVectorState.mVPathRenderer.mBaseWidth;
    }

    public int getIntrinsicHeight() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getIntrinsicHeight() : (int) this.mVectorState.mVPathRenderer.mBaseHeight;
    }

    public boolean canApplyTheme() {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.canApplyTheme(this.mDelegateDrawable);
        }
        return DBG_VECTOR_DRAWABLE;
    }

    public boolean isAutoMirrored() {
        return this.mDelegateDrawable != null ? DrawableCompat.isAutoMirrored(this.mDelegateDrawable) : this.mVectorState.mAutoMirrored;
    }

    public void setAutoMirrored(boolean mirrored) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setAutoMirrored(this.mDelegateDrawable, mirrored);
        } else {
            this.mVectorState.mAutoMirrored = mirrored;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public float getPixelSize() {
        if (this.mVectorState == null || this.mVectorState.mVPathRenderer == null || this.mVectorState.mVPathRenderer.mBaseWidth == 0.0f || this.mVectorState.mVPathRenderer.mBaseHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportWidth == 0.0f) {
            return DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        }
        float intrinsicWidth = this.mVectorState.mVPathRenderer.mBaseWidth;
        float intrinsicHeight = this.mVectorState.mVPathRenderer.mBaseHeight;
        return Math.min(this.mVectorState.mVPathRenderer.mViewportWidth / intrinsicWidth, this.mVectorState.mVPathRenderer.mViewportHeight / intrinsicHeight);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.Nullable
    public static android.support.graphics.drawable.VectorDrawableCompat create(@android.support.annotation.NonNull android.content.res.Resources r8_res, @android.support.annotation.DrawableRes int r9_resId, @android.support.annotation.Nullable android.content.res.Resources.Theme r10_theme) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.VectorDrawableCompat.create(android.content.res.Resources, int, android.content.res.Resources$Theme):android.support.graphics.drawable.VectorDrawableCompat");
        /*
        r7 = 2;
        r5 = android.os.Build.VERSION.SDK_INT;
        r6 = 24;
        if (r5 < r6) goto L_0x0020;
    L_0x0007:
        r1 = new android.support.graphics.drawable.VectorDrawableCompat;
        r1.<init>();
        r5 = android.support.v4.content.res.ResourcesCompat.getDrawable(r8, r9, r10);
        r1.mDelegateDrawable = r5;
        r5 = new android.support.graphics.drawable.VectorDrawableCompat$VectorDrawableDelegateState;
        r6 = r1.mDelegateDrawable;
        r6 = r6.getConstantState();
        r5.<init>(r6);
        r1.mCachedConstantStateDelegate = r5;
    L_0x001f:
        return r1;
    L_0x0020:
        r3 = r8.getXml(r9);	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
        r0 = android.util.Xml.asAttributeSet(r3);	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
    L_0x0028:
        r4 = r3.next();	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
        if (r4 == r7) goto L_0x0031;
    L_0x002e:
        r5 = 1;
        if (r4 != r5) goto L_0x0028;
    L_0x0031:
        if (r4 == r7) goto L_0x0045;
    L_0x0033:
        r5 = new org.xmlpull.v1.XmlPullParserException;	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
        r6 = "No start tag found";
        r5.<init>(r6);	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
        throw r5;	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
    L_0x003b:
        r2 = move-exception;
        r5 = "VectorDrawableCompat";
        r6 = "parser error";
        android.util.Log.e(r5, r6, r2);
    L_0x0043:
        r1 = 0;
        goto L_0x001f;
    L_0x0045:
        r1 = createFromXmlInner(r8, r3, r0, r10);	 Catch:{ XmlPullParserException -> 0x003b, IOException -> 0x004a }
        goto L_0x001f;
    L_0x004a:
        r2 = move-exception;
        r5 = "VectorDrawableCompat";
        r6 = "parser error";
        android.util.Log.e(r5, r6, r2);
        goto L_0x0043;
        */
    }

    public static VectorDrawableCompat createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat drawable = new VectorDrawableCompat();
        drawable.inflate(r, parser, attrs, theme);
        return drawable;
    }

    static int applyAlpha(int color, float alpha) {
        return (color & 16777215) | (((int) (((float) Color.alpha(color)) * alpha)) << 24);
    }

    public void inflate(Resources res, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.inflate(res, parser, attrs);
        } else {
            inflate(res, parser, attrs, null);
        }
    }

    public void inflate(Resources res, XmlPullParser parser, AttributeSet attrs, Theme theme) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.inflate(this.mDelegateDrawable, res, parser, attrs, theme);
            return;
        }
        VectorDrawableCompatState state = this.mVectorState;
        state.mVPathRenderer = new VPathRenderer();
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
        updateStateFromTypedArray(a, parser);
        a.recycle();
        state.mChangingConfigurations = getChangingConfigurations();
        state.mCacheDirty = true;
        inflateInternal(res, parser, attrs, theme);
        this.mTintFilter = updateTintFilter(this.mTintFilter, state.mTint, state.mTintMode);
    }

    private static Mode parseTintModeCompat(int value, Mode defaultMode) {
        switch (value) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Mode.SRC_OVER;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Mode.SRC_IN;
            case ConnectionResult.SERVICE_INVALID:
                return Mode.SRC_ATOP;
            case ConnectionResult.TIMEOUT:
                return Mode.MULTIPLY;
            case ConnectionResult.INTERRUPTED:
                return Mode.SCREEN;
            case ConnectionResult.API_UNAVAILABLE:
                return VERSION.SDK_INT >= 11 ? Mode.ADD : defaultMode;
            default:
                return defaultMode;
        }
    }

    private void updateStateFromTypedArray(TypedArray a, XmlPullParser parser) throws XmlPullParserException {
        VectorDrawableCompatState state = this.mVectorState;
        VPathRenderer pathRenderer = state.mVPathRenderer;
        state.mTintMode = parseTintModeCompat(TypedArrayUtils.getNamedInt(a, parser, "tintMode", ConnectionResult.RESOLUTION_REQUIRED, -1), Mode.SRC_IN);
        ColorStateList tint = a.getColorStateList(LINEJOIN_ROUND);
        if (tint != null) {
            state.mTint = tint;
        }
        state.mAutoMirrored = TypedArrayUtils.getNamedBoolean(a, parser, "autoMirrored", RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, state.mAutoMirrored);
        pathRenderer.mViewportWidth = TypedArrayUtils.getNamedFloat(a, parser, "viewportWidth", DetectedActivity.WALKING, pathRenderer.mViewportWidth);
        pathRenderer.mViewportHeight = TypedArrayUtils.getNamedFloat(a, parser, "viewportHeight", DetectedActivity.RUNNING, pathRenderer.mViewportHeight);
        if (pathRenderer.mViewportWidth <= 0.0f) {
            throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (pathRenderer.mViewportHeight <= 0.0f) {
            throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        } else {
            pathRenderer.mBaseWidth = a.getDimension(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, pathRenderer.mBaseWidth);
            pathRenderer.mBaseHeight = a.getDimension(LINEJOIN_BEVEL, pathRenderer.mBaseHeight);
            if (pathRenderer.mBaseWidth <= 0.0f) {
                throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (pathRenderer.mBaseHeight <= 0.0f) {
                throw new XmlPullParserException(a.getPositionDescription() + "<vector> tag requires height > 0");
            } else {
                pathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(a, parser, "alpha", RainSurfaceView.RAIN_LEVEL_RAINSTORM, pathRenderer.getAlpha()));
                String name = a.getString(LINEJOIN_MITER);
                if (name != null) {
                    pathRenderer.mRootName = name;
                    pathRenderer.mVGTargetsMap.put(name, pathRenderer);
                }
            }
        }
    }

    private void inflateInternal(Resources res, XmlPullParser parser, AttributeSet attrs, Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompatState state = this.mVectorState;
        VPathRenderer pathRenderer = state.mVPathRenderer;
        boolean noPathTag = true;
        Stack<VGroup> groupStack = new Stack();
        groupStack.push(pathRenderer.mRootGroup);
        int eventType = parser.getEventType();
        int innerDepth = parser.getDepth() + 1;
        while (eventType != 1) {
            if (parser.getDepth() < innerDepth && eventType == 3) {
                break;
            }
            if (eventType == 2) {
                String tagName = parser.getName();
                VGroup currentGroup = (VGroup) groupStack.peek();
                if (SHAPE_PATH.equals(tagName)) {
                    VFullPath path = new VFullPath();
                    path.inflate(res, attrs, theme, parser);
                    currentGroup.mChildren.add(path);
                    if (path.getPathName() != null) {
                        pathRenderer.mVGTargetsMap.put(path.getPathName(), path);
                    }
                    noPathTag = DBG_VECTOR_DRAWABLE;
                    state.mChangingConfigurations |= path.mChangingConfigurations;
                } else if (SHAPE_CLIP_PATH.equals(tagName)) {
                    VClipPath path2 = new VClipPath();
                    path2.inflate(res, attrs, theme, parser);
                    currentGroup.mChildren.add(path2);
                    if (path2.getPathName() != null) {
                        pathRenderer.mVGTargetsMap.put(path2.getPathName(), path2);
                    }
                    state.mChangingConfigurations |= path2.mChangingConfigurations;
                } else if (SHAPE_GROUP.equals(tagName)) {
                    VGroup newChildGroup = new VGroup();
                    newChildGroup.inflate(res, attrs, theme, parser);
                    currentGroup.mChildren.add(newChildGroup);
                    groupStack.push(newChildGroup);
                    if (newChildGroup.getGroupName() != null) {
                        pathRenderer.mVGTargetsMap.put(newChildGroup.getGroupName(), newChildGroup);
                    }
                    state.mChangingConfigurations |= newChildGroup.mChangingConfigurations;
                }
            } else if (eventType == 3) {
                if (SHAPE_GROUP.equals(parser.getName())) {
                    groupStack.pop();
                }
            }
            eventType = parser.next();
        }
        if (noPathTag) {
            StringBuffer tag = new StringBuffer();
            if (tag.length() > 0) {
                tag.append(" or ");
            }
            tag.append(SHAPE_PATH);
            throw new XmlPullParserException("no " + tag + " defined");
        }
    }

    private void printGroupTree(VGroup currentGroup, int level) {
        int i;
        String indent = StringUtils.EMPTY_STRING;
        for (i = LINEJOIN_MITER; i < level; i++) {
            indent = indent + "    ";
        }
        Log.v(LOGTAG, indent + "current group is :" + currentGroup.getGroupName() + " rotation is " + currentGroup.mRotate);
        Log.v(LOGTAG, indent + "matrix is :" + currentGroup.getLocalMatrix().toString());
        for (i = LINEJOIN_MITER; i < currentGroup.mChildren.size(); i++) {
            Object child = currentGroup.mChildren.get(i);
            if (child instanceof VGroup) {
                printGroupTree((VGroup) child, level + 1);
            } else {
                ((VPath) child).printVPath(level + 1);
            }
        }
    }

    void setAllowCaching(boolean allowCaching) {
        this.mAllowCaching = allowCaching;
    }

    private boolean needMirroring() {
        if (VERSION.SDK_INT >= 17) {
            return isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
        } else {
            return false;
        }
    }

    protected void onBoundsChange(Rect bounds) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds(bounds);
        }
    }

    public int getChangingConfigurations() {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.getChangingConfigurations() : super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
    }

    public void invalidateSelf() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public void scheduleSelf(Runnable what, long when) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.scheduleSelf(what, when);
        } else {
            super.scheduleSelf(what, when);
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return this.mDelegateDrawable != null ? this.mDelegateDrawable.setVisible(visible, restart) : super.setVisible(visible, restart);
    }

    public void unscheduleSelf(Runnable what) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.unscheduleSelf(what);
        } else {
            super.unscheduleSelf(what);
        }
    }
}
