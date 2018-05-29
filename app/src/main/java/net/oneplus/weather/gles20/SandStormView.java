package net.oneplus.weather.gles20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.sdk.utils.OpBoostFramework;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@SuppressLint({"ClickableViewAccessibility"})
public class SandStormView extends BaseGL2SurfaceView implements OnTouchListener, OrientationInfoListener {
    private static final String TAG = "SandStormView";
    private SandStormRenderer mSandStormRenderer;

    class AnonymousClass_1 implements Runnable {
        final /* synthetic */ boolean val$day;

        AnonymousClass_1(boolean z) {
            this.val$day = z;
        }

        public void run() {
            SandStormView.this.mSandStormRenderer.setIsDay(this.val$day);
        }
    }

    class AnonymousClass_2 implements Runnable {
        final /* synthetic */ float val$alpha;

        AnonymousClass_2(float f) {
            this.val$alpha = f;
        }

        public void run() {
            SandStormView.this.mSandStormRenderer.setAlpha(this.val$alpha);
        }
    }

    class AnonymousClass_3 implements Runnable {
        final /* synthetic */ float val$x;
        final /* synthetic */ float val$y;

        AnonymousClass_3(float f, float f2) {
            this.val$x = f;
            this.val$y = f2;
        }

        public void run() {
            SandStormView.this.mSandStormRenderer.handleTouch(this.val$x, this.val$y);
        }
    }

    class AnonymousClass_4 implements Runnable {
        final /* synthetic */ float val$localX;
        final /* synthetic */ float val$localY;
        final /* synthetic */ float val$localZ;

        AnonymousClass_4(float f, float f2, float f3) {
            this.val$localX = f;
            this.val$localY = f2;
            this.val$localZ = f3;
        }

        public void run() {
            SandStormView.this.mSandStormRenderer.setRotation(this.val$localX, this.val$localY, this.val$localZ);
        }
    }

    public SandStormView(Context context) {
        this(context, true);
    }

    public SandStormView(Context context, boolean isDay) {
        super(context);
        setEGLContextClientVersion(RainSurfaceView.RAIN_LEVEL_SHOWER);
        setEGLConfigChooser(DetectedActivity.RUNNING, 8, 8, 8, ConnectionResult.API_UNAVAILABLE, 0);
        setZOrderOnTop(true);
        this.mSandStormRenderer = new SandStormRenderer(context, isDay);
        setRenderer(this.mSandStormRenderer);
        getHolder().setFormat(OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY);
        setOnTouchListener(this);
    }

    public void startAnimate() {
        setRenderMode(1);
    }

    public void stopAnimate() {
        setRenderMode(0);
    }

    public void onPageSelected(boolean isCurrent) {
    }

    public void setDay(boolean day) {
        queueEvent(new AnonymousClass_1(day));
    }

    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        queueEvent(new AnonymousClass_2(alpha));
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event != null) {
            queueEvent(new AnonymousClass_3(event.getX(), event.getY()));
        }
        return false;
    }

    public void onOrientationInfoChange(float x, float y, float z) {
        queueEvent(new AnonymousClass_4(x, y, z));
    }

    protected void onCreateOrientationInfoListener() {
        this.mListener = this;
    }
}
