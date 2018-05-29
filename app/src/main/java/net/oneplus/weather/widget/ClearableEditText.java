package net.oneplus.weather.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.EditText;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class ClearableEditText extends EditText {
    private static final float DEFAULT_CLEAR_BUTTON_PADDING = 5.0f;
    private static final int DEFAULT_CLEAR_BUTTON_POSITION = 2;
    private static final float DEFAULT_CLEAR_BUTTON_SIZE = 16.0f;
    private static final int DEFAULT_CLEAR_DRAWABLE_ID = 2131230954;
    public static final int POSITION_END = 2;
    public static final int POSITION_START = 0;
    private static final String TAG = "ClearableEditText";
    private static final boolean localLOG = false;
    private boolean mClearButtonAlwaysVisible;
    private Drawable mClearButtonDrawable;
    private int mClearButtonHeight;
    private int mClearButtonPadding;
    private int mClearButtonPosition;
    private int mClearButtonWidth;
    private GestureDetector mGestureDetector;
    private OnContentClearListener mOnContentClearListener;

    private class ClearButtonGestureListener extends SimpleOnGestureListener {
        private ClearButtonGestureListener() {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (!(ClearableEditText.this.getCompoundDrawables()[0] == null && ClearableEditText.this.getCompoundDrawables()[2] == null)) {
                int left;
                int rectWidth = ClearableEditText.this.mClearButtonDrawable.getBounds().width();
                int rectHeight = ClearableEditText.this.mClearButtonDrawable.getBounds().height();
                if (ClearableEditText.this.mClearButtonPosition == 0) {
                    left = ClearableEditText.this.getPaddingLeft();
                } else {
                    left = (ClearableEditText.this.getWidth() - ClearableEditText.this.getPaddingRight()) - rectWidth;
                }
                int top = (((ClearableEditText.this.getHeight() + ClearableEditText.this.getPaddingTop()) - ClearableEditText.this.getPaddingBottom()) - rectHeight) / 2;
                int right = left + rectWidth;
                int bottom = top + rectHeight;
                if (e.getX() > ((float) left) && e.getX() < ((float) right) && e.getY() > ((float) top) && e.getY() < ((float) bottom)) {
                    ClearableEditText.this.clearContent();
                }
            }
            return false;
        }
    }

    private class ClearableTextWatcher implements TextWatcher {
        private ClearableTextWatcher() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0) {
                ClearableEditText.this.setClearButtonVisible(Boolean.valueOf(false));
            } else if (ClearableEditText.this.isFocused()) {
                ClearableEditText.this.setClearButtonVisible(Boolean.valueOf(true));
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public static interface OnContentClearListener {
        void onContentClear(ClearableEditText clearableEditText);
    }

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 2130968682);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.mClearButtonAlwaysVisible = false;
        float density = context.getResources().getDisplayMetrics().density;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ClearableEditText, defStyleAttr, R.style.ClearableEditText);
        this.mClearButtonPosition = a.getInt(RainSurfaceView.RAIN_LEVEL_RAINSTORM, POSITION_END);
        if (!(this.mClearButtonPosition == 0 || this.mClearButtonPosition == 2)) {
            this.mClearButtonPosition = 2;
        }
        this.mClearButtonAlwaysVisible = a.getBoolean(POSITION_START, false);
        this.mClearButtonPadding = a.getDimensionPixelSize(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, (int) (5.0f * density));
        this.mClearButtonDrawable = a.getDrawable(1);
        if (this.mClearButtonDrawable == null) {
            this.mClearButtonDrawable = getResources().getDrawable(DEFAULT_CLEAR_DRAWABLE_ID);
        }
        this.mClearButtonWidth = a.getDimensionPixelSize(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, -1);
        this.mClearButtonHeight = a.getDimensionPixelSize(POSITION_END, -1);
        if (this.mClearButtonWidth == -1 || this.mClearButtonHeight == -1) {
            int i = (int) (16.0f * density);
            this.mClearButtonHeight = i;
            this.mClearButtonWidth = i;
        }
        this.mClearButtonDrawable.setBounds(POSITION_START, POSITION_START, this.mClearButtonWidth, this.mClearButtonHeight);
        a.recycle();
        this.mGestureDetector = new GestureDetector(context, new ClearButtonGestureListener());
        addTextChangedListener(new ClearableTextWatcher());
        setClearButtonVisible(Boolean.valueOf(false));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        boolean superResult = super.onTouchEvent(event);
        if (getCompoundDrawables()[this.mClearButtonPosition == 0 ? POSITION_START : POSITION_END] != null) {
            this.mGestureDetector.onTouchEvent(event);
        }
        return superResult;
    }

    private void clearContent() {
        setText(StringUtils.EMPTY_STRING);
        setClearButtonVisible(Boolean.valueOf(false));
        if (this.mOnContentClearListener != null) {
            this.mOnContentClearListener.onContentClear(this);
        }
    }

    private void setClearButtonVisible(Boolean visible) {
        if (visible.booleanValue() || this.mClearButtonAlwaysVisible) {
            setCompoundDrawablePadding(this.mClearButtonPadding);
            setClearButton(this.mClearButtonPosition, this.mClearButtonDrawable);
            return;
        }
        setClearButton(this.mClearButtonPosition, null);
    }

    private void setClearButton(int position, Drawable drawable) {
        if (position == 0) {
            setCompoundDrawables(drawable, null, null, null);
        } else if (position == 2) {
            setCompoundDrawables(null, null, drawable, null);
        }
    }

    public Drawable getClearButtonDrawable() {
        return this.mClearButtonDrawable;
    }

    public void setClearButtonDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new NullPointerException("Drawable can not be null.");
        }
        this.mClearButtonDrawable = drawable;
        if (getCompoundDrawables()[this.mClearButtonPosition == 0 ? POSITION_START : POSITION_END] != null) {
            setClearButtonVisible(Boolean.valueOf(true));
        }
    }

    public void setClearButtonAlwaysVisible(boolean visible) {
        this.mClearButtonAlwaysVisible = visible;
        setClearButtonVisible(Boolean.valueOf(true));
    }

    public void setOnContentClearListener(OnContentClearListener l) {
        this.mOnContentClearListener = l;
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused || getText().toString().length() == 0) {
            setClearButtonVisible(Boolean.valueOf(false));
        } else {
            setClearButtonVisible(Boolean.valueOf(true));
        }
    }

    public void setClearButtonPosition(int position) {
        if (position == 2 || position == 0) {
            setClearButton(position, getClearButtonDrawable());
            this.mClearButtonPosition = position;
            return;
        }
        throw new IllegalArgumentException("Position can only be one of: POSITION_START or POSITION_END.");
    }
}
