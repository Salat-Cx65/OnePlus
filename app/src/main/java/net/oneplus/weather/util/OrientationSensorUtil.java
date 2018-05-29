package net.oneplus.weather.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OrientationSensorUtil {
    private static final String TAG = "OrientationSensorUtil";
    private static List<WeakReference<OrientationInfoListener>> mOrientationInfoListener;
    private static Sensor mSensor;
    private static SensorEventListener mSensorEventListener;
    private static SensorManager mSensorManager;

    public static interface OrientationInfoListener {
        void onOrientationInfoChange(float f, float f2, float f3);
    }

    static {
        mSensorManager = null;
        mSensor = null;
        mOrientationInfoListener = new ArrayList();
        mSensorEventListener = new SensorEventListener() {
            public void onSensorChanged(SensorEvent event) {
                OrientationSensorUtil.notifySensor(event);
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    public static synchronized void requestSensor(Context context) {
        synchronized (OrientationSensorUtil.class) {
            Log.i(TAG, "requestSensor");
            if (context != null && mSensorManager == null) {
                mSensorManager = (SensorManager) context.getApplicationContext().getSystemService("sensor");
                mSensor = mSensorManager.getDefaultSensor(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                mSensorManager.registerListener(mSensorEventListener, mSensor, 1);
            }
        }
    }

    public static void addOrientationInfoListener(OrientationInfoListener l) {
        mOrientationInfoListener.add(new WeakReference(l));
    }

    public static void removeOrientationInfoListener(OrientationInfoListener l) {
        for (WeakReference<OrientationInfoListener> w : mOrientationInfoListener) {
            if (w.get() == l) {
                mOrientationInfoListener.remove(w);
                return;
            }
        }
    }

    public static synchronized void releaseSensor() {
        synchronized (OrientationSensorUtil.class) {
            Log.i(TAG, "releaseSensor");
            if (mSensorManager != null) {
                mSensorManager.unregisterListener(mSensorEventListener, mSensor);
            }
            mSensorManager = null;
            mSensor = null;
            mOrientationInfoListener.clear();
        }
    }

    private static void notifySensor(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        List<WeakReference<OrientationInfoListener>> list = new ArrayList();
        list.addAll(mOrientationInfoListener);
        for (WeakReference<OrientationInfoListener> w : list) {
            OrientationInfoListener l = (OrientationInfoListener) w.get();
            if (l == null) {
                mOrientationInfoListener.remove(w);
            } else {
                l.onOrientationInfoChange(x, y, z);
            }
        }
    }
}
