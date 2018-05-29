package net.oneplus.weather.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import net.oneplus.weather.api.WeatherType;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.util.UIUtil.DragOnTouchListener;
import net.oneplus.weather.util.UIUtil.OnDragListener;
import net.oneplus.weather.util.UIUtil.OnDragListener.DragEvent;

public class UIUtil {

    public static class DragOnTouchListener implements OnTouchListener {
        private int currentSlop;
        private boolean dispatchTouchEvent;
        private boolean enable;
        private PointF mLastPoint;
        private boolean mScrolling;
        private int mTouchSlop;
        private OnDragListener onDragListener;
        private View view;

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ MotionEvent val$ev;

            AnonymousClass_1(MotionEvent motionEvent) {
                this.val$ev = motionEvent;
            }

            public void run() {
                net.oneplus.weather.util.UIUtil.DragOnTouchListener.this.onDragListener.onDrag(net.oneplus.weather.util.UIUtil.DragOnTouchListener.this.view, this.val$ev, DragEvent.ACTION_DRAG, net.oneplus.weather.util.UIUtil.DragOnTouchListener.this.currentSlop);
            }
        }

        public DragOnTouchListener(View view, OnDragListener onDragListener, boolean dispatchTouchEvent) {
            this.mTouchSlop = 5;
            this.mScrolling = false;
            this.mLastPoint = null;
            this.currentSlop = 0;
            this.enable = false;
            this.onDragListener = onDragListener;
            this.view = view;
            this.dispatchTouchEvent = dispatchTouchEvent;
        }

        public void setDispatchTouchEvent(boolean dispatchTouchEvent) {
            this.dispatchTouchEvent = dispatchTouchEvent;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public boolean onTouch(View v, MotionEvent ev) {
            if (!this.enable) {
                return false;
            }
            float eventFloatY = ev.getY();
            if (ev.getAction() == 0) {
                this.mLastPoint = new PointF(ev.getX(), ev.getY());
            }
            if (ev.getAction() == 2) {
                if (this.mLastPoint == null) {
                    this.mLastPoint = new PointF(ev.getX(), ev.getY());
                }
                if (this.mScrolling) {
                    this.view.post(new AnonymousClass_1(ev));
                } else {
                    float slop = eventFloatY - this.mLastPoint.y;
                    this.currentSlop = (int) slop;
                    if (Math.abs(slop) >= ((float) this.mTouchSlop) && Math.abs((int) (this.mLastPoint.x - ev.getX())) < Math.abs((int) (this.mLastPoint.y - ev.getY()))) {
                        this.mScrolling = true;
                        this.onDragListener.onDrag(this.view, ev, DragEvent.ACTION_START, this.currentSlop);
                    }
                }
            } else if (ev.getAction() == 3 || ev.getAction() == 1) {
                if (this.mScrolling) {
                    this.mScrolling = false;
                    this.onDragListener.onDrag(this.view, ev, DragEvent.ACTION_END, this.currentSlop);
                    return true;
                }
                this.mLastPoint = null;
            }
            return !this.dispatchTouchEvent;
        }
    }

    public static class DragOnTouchListenerHolder {
        public DragOnTouchListener dragOnTouchListener;
    }

    public static interface OnDragListener {

        public enum DragEvent {
            ACTION_START,
            ACTION_DRAG,
            ACTION_END
        }

        void onDrag(View view, MotionEvent motionEvent, DragEvent dragEvent, int i);
    }

    public static Point getPos(View view) {
        int[] pos = new int[2];
        view.getLocationOnScreen(pos);
        return new Point(pos[0], pos[1]);
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metric);
        return metric.widthPixels;
    }

    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metric);
        return metric.widthPixels;
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static DragOnTouchListenerHolder setOnDragListener(View view, OnDragListener onDragListener, boolean dispatchTouchEvent, boolean enable) {
        DragOnTouchListenerHolder dragOnTouchListenerHolder = new DragOnTouchListenerHolder();
        DragOnTouchListener d = new DragOnTouchListener(view, onDragListener, dispatchTouchEvent);
        d.setEnable(enable);
        view.setOnTouchListener(d);
        dragOnTouchListenerHolder.dragOnTouchListener = d;
        if (!(view instanceof ListView)) {
            view.setOnClickListener(null);
        }
        return dragOnTouchListenerHolder;
    }

    public static View getViewByPosition(int position, ListView listView) {
        int firstListItemPosition = listView.getFirstVisiblePosition();
        return (position < firstListItemPosition || position > (listView.getChildCount() + firstListItemPosition) - 1) ? listView.getAdapter().getView(position, listView.getChildAt(position), listView) : listView.getChildAt(position - firstListItemPosition);
    }

    public static void setWindowStyle(Activity activity) {
        Window window = activity.getWindow();
        if (VERSION.SDK_INT >= 21) {
            window.clearFlags(201326592);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(CitySearchProvider.GET_SEARCH_RESULT_FAIL);
            window.setStatusBarColor(0);
            return;
        }
        activity.requestWindowFeature(1);
        window.setFlags(WeatherType.ACCU_WEATHER_RAIN_AND_SNOW, WeatherType.ACCU_WEATHER_RAIN_AND_SNOW);
    }

    public static void setSystemBar(Activity activity, int color) {
        if (VERSION.SDK_INT >= 19) {
            Window win = activity.getWindow();
            LayoutParams winParams = win.getAttributes();
            winParams.flags |= 67108864;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }
}
