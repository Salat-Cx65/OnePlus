package net.oneplus.weather.widget.anim;

import android.os.SystemClock;
import com.google.android.gms.location.DetectedActivity;
import java.util.Random;

public class SnowAnimation extends BaseAnimation {
    private static int COLOR_PERCENT;
    private static float[][] mColor;
    private static float[][] mGreenColor;
    private float mSpeedX;
    private float mSpeedY;
    private float mSpeedZ;
    private float mStep;

    static {
        COLOR_PERCENT = 8;
        mColor = new float[][]{new float[]{1.0f, 1.0f, 1.0f}, new float[]{0.50980395f, 0.5921569f, 0.654902f}};
        mGreenColor = new float[][]{new float[]{0.0f, 1.0f, 0.5882353f}, new float[]{0.08627451f, 0.654902f, 0.41960785f}};
    }

    public SnowAnimation() {
        this.mStep = 0.06f;
        Random random = new Random(System.currentTimeMillis() + ((long) INDEX));
        INDEX += 10;
        int r = random.nextInt(DetectedActivity.RUNNING);
        this.mSpeedX = (((float) (r - 4)) * this.mStep) * 0.1f;
        this.mSpeedY = -this.mStep;
        this.mSpeedZ = (((float) (r - 4)) * this.mStep) * 0.1f;
    }

    public static float[][] randomColor() {
        return new Random(SystemClock.currentThreadTimeMillis() + ((long) INDEX)).nextInt(COLOR_PERCENT) == COLOR_PERCENT + -1 ? mGreenColor : mColor;
    }

    public float[] next() {
        return new float[]{this.mSpeedX, this.mSpeedY, this.mSpeedZ};
    }
}
