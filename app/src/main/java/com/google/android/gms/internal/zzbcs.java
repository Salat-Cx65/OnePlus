package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbcs<R extends Result> extends Handler {
    public zzbcs() {
        this(Looper.getMainLooper());
    }

    public zzbcs(Looper looper) {
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                Pair pair = (Pair) message.obj;
                ResultCallback resultCallback = (ResultCallback) pair.first;
                Result result = (Result) pair.second;
                try {
                    resultCallback.onResult(result);
                } catch (RuntimeException e) {
                    zzbcq.zzc(result);
                    throw e;
                }
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                ((zzbcq) message.obj).zzs(Status.zzaBr);
            default:
                Log.wtf("BasePendingResult", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
        }
    }

    public final void zza(ResultCallback<? super R> resultCallback, R r) {
        sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
    }
}
