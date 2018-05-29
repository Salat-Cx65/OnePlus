package net.oneplus.weather.widget.anim;

import android.os.SystemClock;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.Random;

public class FogAnimation extends BaseAnimation {
    private static int COLOR_PERCENT;
    private static float[][] mDuckGrayColor;
    private static float[][] mGrayColor;
    private float mSpeedX;
    private float mSpeedY;
    private float mSpeedZ;
    private float mXStep;
    private float mYStep;
    private float mZStep;

    static {
        COLOR_PERCENT = 20;
        mGrayColor = new float[][]{new float[]{0.4862745f, 0.56078434f, 0.6039216f, 1.0f}, new float[]{0.18039216f, 0.1764706f, 0.1764706f, 1.0f}};
        mDuckGrayColor = new float[][]{new float[]{0.3882353f, 0.46666667f, 0.5137255f, 1.0f}, new float[]{0.14901961f, 0.15294118f, 0.16078432f}};
    }

    public FogAnimation() {
        this.mXStep = 0.02f;
        this.mYStep = 0.06f;
        this.mZStep = 0.02f;
        Random random = new Random(System.currentTimeMillis() + ((long) INDEX));
        INDEX += 10;
        int rx = random.nextInt(ConnectionResult.RESTRICTED_PROFILE);
        int rz = random.nextInt(DetectedActivity.RUNNING);
        this.mSpeedX = (((float) (rx - 5)) * this.mXStep) * 0.1f;
        this.mSpeedY = -this.mYStep;
        this.mSpeedZ = (((float) (rz - 4)) * this.mZStep) * 0.1f;
    }

    public static float[][] randomColor() {
        return new Random(SystemClock.currentThreadTimeMillis() + ((long) INDEX)).nextInt(COLOR_PERCENT) < COLOR_PERCENT / 10 ? mDuckGrayColor : mGrayColor;
    }

    public float[] next() {
        return new float[]{this.mSpeedX, this.mSpeedY, this.mSpeedZ};
    }
}
