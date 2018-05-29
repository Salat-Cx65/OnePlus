package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import com.amap.api.location.APSServiceBase;
import com.google.android.gms.common.ConnectionResult;
import com.loc.e.a;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: APSServiceCore.java
public class f implements APSServiceBase {
    e a;
    Context b;
    Messenger c;

    public f(Context context) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.b = context.getApplicationContext();
        this.a = new e(this.b);
    }

    public IBinder onBind(Intent intent) {
        e eVar = this.a;
        Object stringExtra = intent.getStringExtra("a");
        if (!TextUtils.isEmpty(stringExtra)) {
            l.a(stringExtra);
        }
        eVar.a = intent.getStringExtra("b");
        k.a(eVar.a);
        String stringExtra2 = intent.getStringExtra("d");
        if (!TextUtils.isEmpty(stringExtra2)) {
            n.a(stringExtra2);
        }
        eVar = this.a;
        if ("true".equals(intent.getStringExtra("as")) && eVar.d != null) {
            eVar.d.sendEmptyMessageDelayed(ConnectionResult.SERVICE_INVALID, 100);
        }
        this.c = new Messenger(this.a.d);
        return this.c.getBinder();
    }

    public void onCreate() {
        try {
            this.a.j = de.b();
            this.a.k = de.a();
            e eVar = this.a;
            try {
                eVar.i = new db();
                eVar.b = new b("amapLocCoreThread");
                eVar.b.setPriority(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
                eVar.b.start();
                eVar.d = new a(eVar.b.getLooper());
            } catch (Throwable th) {
                cw.a(th, "APSServiceCore", "onCreate");
            }
        } catch (Throwable th2) {
            cw.a(th2, "APSServiceCore", "onCreate");
        }
    }

    public void onDestroy() {
        try {
            if (this.a != null) {
                this.a.d.sendEmptyMessage(ConnectionResult.LICENSE_CHECK_FAILED);
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "onDestroy");
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 0;
    }
}
