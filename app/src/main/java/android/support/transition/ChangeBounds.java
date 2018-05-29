package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

public class ChangeBounds extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY;
    private static final Property<View, PointF> POSITION_PROPERTY;
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
    private static RectEvaluator sRectEvaluator;
    private static final String[] sTransitionProperties;
    private boolean mReparent;
    private boolean mResizeClip;
    private int[] mTempLocation;

    class AnonymousClass_10 extends AnimatorListenerAdapter {
        final /* synthetic */ BitmapDrawable val$drawable;
        final /* synthetic */ ViewGroup val$sceneRoot;
        final /* synthetic */ float val$transitionAlpha;
        final /* synthetic */ View val$view;

        AnonymousClass_10(ViewGroup viewGroup, BitmapDrawable bitmapDrawable, View view, float f) {
            this.val$sceneRoot = viewGroup;
            this.val$drawable = bitmapDrawable;
            this.val$view = view;
            this.val$transitionAlpha = f;
        }

        public void onAnimationEnd(Animator animation) {
            ViewUtils.getOverlay(this.val$sceneRoot).remove(this.val$drawable);
            ViewUtils.setTransitionAlpha(this.val$view, this.val$transitionAlpha);
        }
    }

    static class AnonymousClass_1 extends Property<Drawable, PointF> {
        private Rect mBounds;

        AnonymousClass_1(Class x0, String x1) {
            super(x0, x1);
            this.mBounds = new Rect();
        }

        public void set(Drawable object, PointF value) {
            object.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(value.x), Math.round(value.y));
            object.setBounds(this.mBounds);
        }

        public PointF get(Drawable object) {
            object.copyBounds(this.mBounds);
            return new PointF((float) this.mBounds.left, (float) this.mBounds.top);
        }
    }

    static class AnonymousClass_2 extends Property<ViewBounds, PointF> {
        AnonymousClass_2(Class x0, String x1) {
            super(x0, x1);
        }

        public void set(ViewBounds viewBounds, PointF topLeft) {
            viewBounds.setTopLeft(topLeft);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    }

    static class AnonymousClass_3 extends Property<ViewBounds, PointF> {
        AnonymousClass_3(Class x0, String x1) {
            super(x0, x1);
        }

        public void set(ViewBounds viewBounds, PointF bottomRight) {
            viewBounds.setBottomRight(bottomRight);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    }

    static class AnonymousClass_4 extends Property<View, PointF> {
        AnonymousClass_4(Class x0, String x1) {
            super(x0, x1);
        }

        public void set(View view, PointF bottomRight) {
            ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(bottomRight.x), Math.round(bottomRight.y));
        }

        public PointF get(View view) {
            return null;
        }
    }

    static class AnonymousClass_5 extends Property<View, PointF> {
        AnonymousClass_5(Class x0, String x1) {
            super(x0, x1);
        }

        public void set(View view, PointF topLeft) {
            ViewUtils.setLeftTopRightBottom(view, Math.round(topLeft.x), Math.round(topLeft.y), view.getRight(), view.getBottom());
        }

        public PointF get(View view) {
            return null;
        }
    }

    static class AnonymousClass_6 extends Property<View, PointF> {
        AnonymousClass_6(Class x0, String x1) {
            super(x0, x1);
        }

        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            ViewUtils.setLeftTopRightBottom(view, left, top, left + view.getWidth(), top + view.getHeight());
        }

        public PointF get(View view) {
            return null;
        }
    }

    class AnonymousClass_7 extends AnimatorListenerAdapter {
        private ViewBounds mViewBounds;
        final /* synthetic */ ViewBounds val$viewBounds;

        AnonymousClass_7(ViewBounds viewBounds) {
            this.val$viewBounds = viewBounds;
            this.mViewBounds = this.val$viewBounds;
        }
    }

    class AnonymousClass_8 extends AnimatorListenerAdapter {
        private boolean mIsCanceled;
        final /* synthetic */ int val$endBottom;
        final /* synthetic */ int val$endLeft;
        final /* synthetic */ int val$endRight;
        final /* synthetic */ int val$endTop;
        final /* synthetic */ Rect val$finalClip;
        final /* synthetic */ View val$view;

        AnonymousClass_8(View view, Rect rect, int i, int i2, int i3, int i4) {
            this.val$view = view;
            this.val$finalClip = rect;
            this.val$endLeft = i;
            this.val$endTop = i2;
            this.val$endRight = i3;
            this.val$endBottom = i4;
        }

        public void onAnimationCancel(Animator animation) {
            this.mIsCanceled = true;
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.mIsCanceled) {
                ViewCompat.setClipBounds(this.val$view, this.val$finalClip);
                ViewUtils.setLeftTopRightBottom(this.val$view, this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
            }
        }
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            this.mTopLeftCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            this.mBottomRightCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }

    class AnonymousClass_9 extends TransitionListenerAdapter {
        boolean mCanceled;
        final /* synthetic */ ViewGroup val$parent;

        AnonymousClass_9(ViewGroup viewGroup) {
            this.val$parent = viewGroup;
            this.mCanceled = false;
        }

        public void onTransitionCancel(@NonNull Transition transition) {
            ViewGroupUtils.suppressLayout(this.val$parent, false);
            this.mCanceled = true;
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            if (!this.mCanceled) {
                ViewGroupUtils.suppressLayout(this.val$parent, false);
            }
            transition.removeListener(this);
        }

        public void onTransitionPause(@NonNull Transition transition) {
            ViewGroupUtils.suppressLayout(this.val$parent, false);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            ViewGroupUtils.suppressLayout(this.val$parent, true);
        }
    }

    static {
        sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
        DRAWABLE_ORIGIN_PROPERTY = new AnonymousClass_1(PointF.class, "boundsOrigin");
        TOP_LEFT_PROPERTY = new AnonymousClass_2(PointF.class, "topLeft");
        BOTTOM_RIGHT_PROPERTY = new AnonymousClass_3(PointF.class, "bottomRight");
        BOTTOM_RIGHT_ONLY_PROPERTY = new AnonymousClass_4(PointF.class, "bottomRight");
        TOP_LEFT_ONLY_PROPERTY = new AnonymousClass_5(PointF.class, "topLeft");
        POSITION_PROPERTY = new AnonymousClass_6(PointF.class, "position");
        sRectEvaluator = new RectEvaluator();
    }

    public ChangeBounds() {
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
    }

    public ChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_BOUNDS);
        boolean resizeClip = TypedArrayUtils.getNamedBoolean(a, (XmlResourceParser) attrs, "resizeClip", 0, false);
        a.recycle();
        setResizeClip(resizeClip);
    }

    @Nullable
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            values.values.put(PROPNAME_PARENT, values.view.getParent());
            if (this.mReparent) {
                values.view.getLocationInWindow(this.mTempLocation);
                values.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.mTempLocation[0]));
                values.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.mTempLocation[1]));
            }
            if (this.mResizeClip) {
                values.values.put(PROPNAME_CLIP, ViewCompat.getClipBounds(view));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private boolean parentMatches(View startParent, View endParent) {
        if (!this.mReparent) {
            return true;
        }
        TransitionValues endValues = getMatchedTransitionValues(startParent, true);
        if (endValues == null) {
            return startParent == endParent;
        } else {
            return endParent == endValues.view;
        }
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        View startParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        View endParent = (ViewGroup) endValues.values.get(PROPNAME_PARENT);
        if (startParent == null || endParent == null) {
            return null;
        }
        View view = endValues.view;
        Animator mergeAnimators;
        if (parentMatches(startParent, endParent)) {
            Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
            Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
            int startLeft = startBounds.left;
            int endLeft = endBounds.left;
            int startTop = startBounds.top;
            int endTop = endBounds.top;
            int startRight = startBounds.right;
            int endRight = endBounds.right;
            int startBottom = startBounds.bottom;
            int endBottom = endBounds.bottom;
            int startWidth = startRight - startLeft;
            int startHeight = startBottom - startTop;
            int endWidth = endRight - endLeft;
            int endHeight = endBottom - endTop;
            Rect startClip = (Rect) startValues.values.get(PROPNAME_CLIP);
            Rect endClip = (Rect) endValues.values.get(PROPNAME_CLIP);
            int numChanges = 0;
            if (!((startWidth == 0 || startHeight == 0) && (endWidth == 0 || endHeight == 0))) {
                if (!(startLeft == endLeft && startTop == endTop)) {
                    numChanges = 0 + 1;
                }
                if (!(startRight == endRight && startBottom == endBottom)) {
                    numChanges++;
                }
            }
            if (!(startClip == null || startClip.equals(endClip)) || (startClip == null && endClip != null)) {
                numChanges++;
            }
            if (numChanges > 0) {
                if (this.mResizeClip) {
                    Rect rect;
                    int i = startLeft;
                    int i2 = startTop;
                    ViewUtils.setLeftTopRightBottom(view, i, i2, startLeft + Math.max(startWidth, endWidth), startTop + Math.max(startHeight, endHeight));
                    ObjectAnimator positionAnimator = null;
                    if (!(startLeft == endLeft && startTop == endTop)) {
                        positionAnimator = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                    }
                    Rect finalClip = endClip;
                    if (startClip == null) {
                        rect = new Rect(0, 0, startWidth, startHeight);
                    }
                    if (endClip == null) {
                        rect = new Rect(0, 0, endWidth, endHeight);
                    }
                    ObjectAnimator clipAnimator = null;
                    if (!startClip.equals(endClip)) {
                        ViewCompat.setClipBounds(view, startClip);
                        clipAnimator = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, new Object[]{startClip, endClip});
                        clipAnimator.addListener(new AnonymousClass_8(view, finalClip, endLeft, endTop, endRight, endBottom));
                    }
                    mergeAnimators = TransitionUtils.mergeAnimators(positionAnimator, clipAnimator);
                } else {
                    ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startRight, startBottom);
                    if (numChanges == 2) {
                        if (startWidth == endWidth && startHeight == endHeight) {
                            mergeAnimators = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                        } else {
                            ViewBounds viewBounds = new ViewBounds(view);
                            viewBounds = viewBounds;
                            ObjectAnimator topLeftAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, TOP_LEFT_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                            viewBounds = viewBounds;
                            ObjectAnimator bottomRightAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, BOTTOM_RIGHT_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom, (float) endRight, (float) endBottom));
                            Animator set = new AnimatorSet();
                            set.playTogether(new Animator[]{topLeftAnimator, bottomRightAnimator});
                            mergeAnimators = set;
                            set.addListener(new AnonymousClass_7(viewBounds));
                        }
                    } else if (startLeft == endLeft && startTop == endTop) {
                        mergeAnimators = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom, (float) endRight, (float) endBottom));
                    } else {
                        mergeAnimators = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                    }
                }
                if (!(view.getParent() instanceof ViewGroup)) {
                    return mergeAnimators;
                }
                ViewGroup parent = (ViewGroup) view.getParent();
                ViewGroupUtils.suppressLayout(parent, true);
                addListener(new AnonymousClass_9(parent));
                return mergeAnimators;
            }
        }
        int startX = ((Integer) startValues.values.get(PROPNAME_WINDOW_X)).intValue();
        int startY = ((Integer) startValues.values.get(PROPNAME_WINDOW_Y)).intValue();
        int endX = ((Integer) endValues.values.get(PROPNAME_WINDOW_X)).intValue();
        int endY = ((Integer) endValues.values.get(PROPNAME_WINDOW_Y)).intValue();
        if (!(startX == endX && startY == endY)) {
            sceneRoot.getLocationInWindow(this.mTempLocation);
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(bitmap));
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            float transitionAlpha = ViewUtils.getTransitionAlpha(view);
            ViewUtils.setTransitionAlpha(view, AutoScrollHelper.RELATIVE_UNSPECIFIED);
            ViewUtils.getOverlay(sceneRoot).add(drawable);
            PropertyValuesHolder origin = PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, getPathMotion().getPath((float) (startX - this.mTempLocation[0]), (float) (startY - this.mTempLocation[1]), (float) (endX - this.mTempLocation[0]), (float) (endY - this.mTempLocation[1])));
            mergeAnimators = ObjectAnimator.ofPropertyValuesHolder(drawable, new PropertyValuesHolder[]{origin});
            mergeAnimators.addListener(new AnonymousClass_10(sceneRoot, drawable, view, transitionAlpha));
            return mergeAnimators;
        }
        return null;
    }
}
