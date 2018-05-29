package com.loc;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.oneplus.weather.util.StringUtils;

// compiled from: BeaconManager.java
public final class cc {
    BluetoothAdapter a;
    boolean b;
    a c;
    Object d;
    private ArrayList<cb> e;
    private ArrayList<cb> f;
    private boolean g;
    private Map<String, String> h;

    @SuppressLint({"NewApi"})
    // compiled from: BeaconManager.java
    class a implements LeScanCallback {
        a() {
        }

        public final void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            try {
                cb a = cc.this.a(bluetoothDevice, i, bArr);
                synchronized (cc.this.d) {
                    int i2 = 0;
                    while (i2 < cc.this.e.size()) {
                        cb cbVar = (cb) cc.this.e.get(i2);
                        if (cbVar.h.equals(a.h)) {
                            cc.this.e.remove(cbVar);
                            cc.this.e.add(a);
                            return;
                        }
                        int i3;
                        if (de.b() - cbVar.i > 3000) {
                            cc.this.e.remove(cbVar);
                            i3 = i2 - 1;
                        } else {
                            i3 = i2;
                        }
                        i2 = i3 + 1;
                    }
                    cc.this.e.add(a);
                }
            } catch (Throwable th) {
                cw.a(th, "APS", "onLeScan");
            }
        }
    }

    public cc(Context context) {
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.a = null;
        this.b = false;
        this.c = null;
        this.g = false;
        this.d = new Object();
        this.h = new HashMap();
        try {
            if (context.checkCallingOrSelfPermission("android.permission.BLUETOOTH_ADMIN") == 0 && context.checkCallingOrSelfPermission("android.permission.BLUETOOTH") == 0) {
                this.g = true;
            }
            if (de.c() >= 18 && this.g) {
                this.a = BluetoothAdapter.getDefaultAdapter();
            }
        } catch (Throwable th) {
        }
    }

    private cb a(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        try {
            String str;
            int i2;
            int i3;
            long b = de.b();
            String a = a(bArr);
            if (a.length() == 16) {
                str = a + "0000000000000000";
                i2 = 0;
                i3 = 0;
            } else if (a.length() == 12) {
                str = a + "00000000000000000000";
                i2 = 0;
                i3 = 0;
            } else {
                byte[] bArr2 = new byte[16];
                System.arraycopy(bArr, ConnectionResult.SERVICE_INVALID, bArr2, 0, ConnectionResult.API_UNAVAILABLE);
                a = de.b(bArr2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(a.substring(0, ItemTouchHelper.END));
                str = stringBuilder.toString().toUpperCase(Locale.getDefault());
                i3 = ((bArr[25] & 255) * 256) + (bArr[26] & 255);
                i2 = ((bArr[27] & 255) * 256) + (bArr[28] & 255);
                if (i2 == 11669 || i3 == 2080 || i3 == 1796 || bluetoothDevice == null) {
                    return null;
                }
            }
            byte b2 = bArr[29];
            String address = bluetoothDevice.getAddress();
            if (!BluetoothAdapter.checkBluetoothAddress(address.toUpperCase(Locale.CHINA))) {
                return null;
            }
            byte[] bArr3 = new byte[6];
            int i4 = 0;
            for (String str2 : address.split(":")) {
                bArr3[i4] = (byte) Integer.parseInt(str2, ConnectionResult.API_UNAVAILABLE);
                i4++;
            }
            return new cb(str, bluetoothDevice.getName(), bArr3, address, i3, i2, b2, i, b);
        } catch (Throwable th) {
            cw.a(th, "APS", "createFromScanData");
            return null;
        }
    }

    private String a(byte[] bArr) {
        if (bArr == null || bArr.length <= 24) {
            return StringUtils.EMPTY_STRING;
        }
        int i;
        if (bArr[0] == (byte) 2 && bArr[1] == (byte) 1) {
            if ((bArr[2] == 5 || bArr[2] == (byte) 6) && bArr[3] == 23) {
                byte[] bArr2 = new byte[16];
                System.arraycopy(bArr, ConnectionResult.SERVICE_INVALID, bArr2, 0, ConnectionResult.API_UNAVAILABLE);
                StringBuffer stringBuffer = new StringBuffer();
                int length = bArr2.length;
                for (i = 0; i < length; i++) {
                    stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bArr2[i])}));
                }
                String toString = stringBuffer.toString();
                String str = (String) this.h.get(toString);
                if (str != null) {
                    return str;
                }
                bArr2 = cj.a(de.c(bArr2), new BigInteger("8021267762677846189778330391499"), new BigInteger("49549924105414102803086139689747"));
                if (bArr2 == null || bArr2.length < 8) {
                    return StringUtils.EMPTY_STRING;
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                for (i = 6; i > 0; i--) {
                    stringBuffer2.append(String.format("%02X", new Object[]{Byte.valueOf(bArr2[i])}));
                }
                str = stringBuffer2.toString();
                this.h.put(toString, str);
                return str;
            }
        }
        if (bArr[0] == (byte) 2 && bArr[1] == (byte) 1 && bArr[2] == (byte) 6 && bArr[3] == 22 && bArr[5] == -88 && bArr[6] == (byte) 1 && bArr[7] == 32) {
            try {
                if (cj.b(de.d(bArr), new byte[]{(byte) -1, (byte) -15, (byte) 55, (byte) 33, (byte) 4, (byte) 21, (byte) 16, (byte) 20, (byte) -85, (byte) 9, (byte) 0, (byte) 2, (byte) -91, (byte) -43, (byte) -59, (byte) -75}) != null) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    for (i = 0; i < 8; i++) {
                        stringBuffer3.append(String.format("%02X", new Object[]{Byte.valueOf(r2[i])}));
                    }
                    return stringBuffer3.toString();
                }
            } catch (Throwable th) {
            }
        }
        return StringUtils.EMPTY_STRING;
    }

    private synchronized void e() {
        try {
            List list = this.f;
            List list2 = this.e;
            list.clear();
            synchronized (this.d) {
                if (list2 != null) {
                    if (list2.size() > 0) {
                        if (list2.size() > 20) {
                            Collections.sort(list2);
                        }
                        for (int i = 0; i < list2.size(); i++) {
                            list.add(list2.get(i));
                            if (i >= 25) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    private boolean f() {
        try {
            return this.a != null && this.a.isEnabled() && cv.A() && de.c() >= 18;
        } catch (Throwable th) {
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public final void a() {
        try {
            if (de.c() >= 18 && this.a != null) {
                if (this.c == null) {
                    this.c = new a();
                }
                if (this.b) {
                    this.a.stopLeScan(this.c);
                }
                this.b = false;
                this.f.clear();
                this.e.clear();
            }
        } catch (Throwable th) {
        }
    }

    @SuppressLint({"NewApi"})
    public final void b() {
        if (!this.b && f()) {
            if (this.c == null) {
                this.c = new a();
            }
            this.a.startLeScan(this.c);
            this.b = true;
        }
    }

    public final ArrayList<cb> c() {
        if (f()) {
            e();
        } else {
            this.f.clear();
        }
        return this.f;
    }

    public final void d() {
        a();
        this.a = null;
    }
}
