package net.oneplus.weather.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class UnderLineTextView extends TextView {
    private Paint mPaint;
    private Rect mRect;
    private float mStrokeWidth;

    public UnderLineTextView(Context context) {
        this(context, null, 0);
    }

    public UnderLineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        float density = context.getResources().getDisplayMetrics().density;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinedTextView, defStyleAttr, 0);
        int mColor = array.getColor(0, SupportMenu.CATEGORY_MASK);
        this.mStrokeWidth = array.getDimension(1, 2.0f * density);
        array.recycle();
        this.mRect = new Rect();
        this.mPaint = new Paint();
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setColor(mColor);
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
    }

    protected void onDraw(Canvas canvas) {
        int count = getLineCount();
        Layout layout = getLayout();
        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, this.mRect);
            int firstCharInLine = layout.getLineStart(i);
            int lastCharInLine = layout.getLineVisibleEnd(i);
            float f = (this.mStrokeWidth * 2.0f) + ((float) baseline);
            Canvas canvas2 = canvas;
            canvas2.drawLine(layout.getPrimaryHorizontal(firstCharInLine), f, layout.getPrimaryHorizontal(lastCharInLine), (this.mStrokeWidth * 2.0f) + ((float) baseline), this.mPaint);
        }
        super.onDraw(canvas);
    }
}
