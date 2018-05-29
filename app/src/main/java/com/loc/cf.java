package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.CellIdentityCdma;
import android.telephony.CellInfoCdma;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@SuppressLint({"NewApi"})
// compiled from: CgiManager.java
public final class cf {
    static ArrayList<ce> b;
    static long d;
    static CellLocation e;
    private static ArrayList<ce> k;
    int a;
    TelephonyManager c;
    String f;
    boolean g;
    StringBuilder h;
    private Context i;
    private String j;
    private int l;
    private cd m;
    private Object n;
    private int o;

    static {
        b = new ArrayList();
        k = new ArrayList();
        d = 0;
    }

    public cf(Context context) {
        this.a = 0;
        this.j = null;
        this.l = -113;
        this.c = null;
        this.m = null;
        this.o = 0;
        this.f = null;
        this.g = false;
        this.h = null;
        this.i = context;
        if (this.c == null) {
            this.c = (TelephonyManager) de.a(this.i, "phone");
        }
        if (this.c != null) {
            try {
                CellLocation cellLocation = this.c.getCellLocation();
                Context context2 = this.i;
                this.a = b(cellLocation);
            } catch (SecurityException e) {
                this.f = e.getMessage();
            } catch (Throwable th) {
                this.f = null;
                cw.a(th, "CgiManager", "CgiManager");
                this.a = 0;
            }
            try {
                this.o = p();
                switch (this.o) {
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        this.n = de.a(this.i, "phone_msim");
                        break;
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        this.n = de.a(this.i, "phone2");
                        break;
                    default:
                        this.n = de.a(this.i, "phone2");
                        break;
                }
            } catch (Throwable th2) {
            }
        }
        this.m = new cd();
    }

    private CellLocation a(Object obj, String str, Object... objArr) {
        if (obj == null) {
            return null;
        }
        try {
            Object a = cz.a(obj, str, objArr);
            CellLocation cellLocation = a != null ? (CellLocation) a : null;
            if (a(cellLocation)) {
                return cellLocation;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    private android.telephony.CellLocation a(java.util.List<android.telephony.CellInfo> r17) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cf.a(java.util.List):android.telephony.CellLocation");
        /*
        this = this;
        if (r17 == 0) goto L_0x0008;
    L_0x0002:
        r2 = r17.isEmpty();
        if (r2 == 0) goto L_0x000a;
    L_0x0008:
        r2 = 0;
    L_0x0009:
        return r2;
    L_0x000a:
        r11 = 0;
        r10 = 0;
        r13 = k;
        r0 = r16;
        r14 = r0.m;
        r9 = 0;
        r15 = r17.size();
        if (r15 == 0) goto L_0x0186;
    L_0x0019:
        if (r13 == 0) goto L_0x001e;
    L_0x001b:
        r13.clear();
    L_0x001e:
        r2 = 0;
        r12 = r2;
    L_0x0020:
        if (r12 >= r15) goto L_0x0186;
    L_0x0022:
        r0 = r17;
        r2 = r0.get(r12);
        r2 = (android.telephony.CellInfo) r2;
        if (r2 == 0) goto L_0x0051;
    L_0x002c:
        r3 = r2.isRegistered();	 Catch:{ Throwable -> 0x0070 }
        r4 = r2 instanceof android.telephony.CellInfoCdma;	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x0072;
    L_0x0034:
        r2 = (android.telephony.CellInfoCdma) r2;	 Catch:{ Throwable -> 0x0070 }
        r4 = r2.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x0055;
    L_0x003c:
        r5 = r4.getSystemId();	 Catch:{ Throwable -> 0x0070 }
        if (r5 <= 0) goto L_0x0055;
    L_0x0042:
        r5 = r4.getNetworkId();	 Catch:{ Throwable -> 0x0070 }
        if (r5 < 0) goto L_0x0055;
    L_0x0048:
        r4 = r4.getBasestationId();	 Catch:{ Throwable -> 0x0070 }
        if (r4 < 0) goto L_0x0055;
    L_0x004e:
        r4 = 1;
    L_0x004f:
        if (r4 != 0) goto L_0x0057;
    L_0x0051:
        r2 = r12 + 1;
        r12 = r2;
        goto L_0x0020;
    L_0x0055:
        r4 = 0;
        goto L_0x004f;
    L_0x0057:
        r0 = r16;
        r9 = r0.a(r2, r3);	 Catch:{ Throwable -> 0x0070 }
        r2 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r4 = r14.a(r9);	 Catch:{ Throwable -> 0x0070 }
        r2 = java.lang.Math.min(r2, r4);	 Catch:{ Throwable -> 0x0070 }
        r2 = (int) r2;	 Catch:{ Throwable -> 0x0070 }
        r2 = (short) r2;	 Catch:{ Throwable -> 0x0070 }
        r9.l = r2;	 Catch:{ Throwable -> 0x0070 }
        r13.add(r9);	 Catch:{ Throwable -> 0x0070 }
        goto L_0x0051;
    L_0x0070:
        r2 = move-exception;
        goto L_0x0051;
    L_0x0072:
        r4 = r2 instanceof android.telephony.CellInfoGsm;	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x00cd;
    L_0x0076:
        r0 = r2;
        r0 = (android.telephony.CellInfoGsm) r0;	 Catch:{ Throwable -> 0x0070 }
        r8 = r0;
        r2 = r8.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x00cb;
    L_0x0080:
        r4 = r2.getLac();	 Catch:{ Throwable -> 0x0070 }
        r4 = b(r4);	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x00cb;
    L_0x008a:
        r2 = r2.getCid();	 Catch:{ Throwable -> 0x0070 }
        r2 = c(r2);	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x00cb;
    L_0x0094:
        r2 = 1;
    L_0x0095:
        if (r2 == 0) goto L_0x0051;
    L_0x0097:
        r7 = r8.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        r2 = 1;
        r4 = r7.getMcc();	 Catch:{ Throwable -> 0x0070 }
        r5 = r7.getMnc();	 Catch:{ Throwable -> 0x0070 }
        r6 = r7.getLac();	 Catch:{ Throwable -> 0x0070 }
        r7 = r7.getCid();	 Catch:{ Throwable -> 0x0070 }
        r8 = r8.getCellSignalStrength();	 Catch:{ Throwable -> 0x0070 }
        r8 = r8.getDbm();	 Catch:{ Throwable -> 0x0070 }
        r9 = a(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x0070 }
        r2 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r4 = r14.a(r9);	 Catch:{ Throwable -> 0x0070 }
        r2 = java.lang.Math.min(r2, r4);	 Catch:{ Throwable -> 0x0070 }
        r2 = (int) r2;	 Catch:{ Throwable -> 0x0070 }
        r2 = (short) r2;	 Catch:{ Throwable -> 0x0070 }
        r9.l = r2;	 Catch:{ Throwable -> 0x0070 }
        r13.add(r9);	 Catch:{ Throwable -> 0x0070 }
        goto L_0x0051;
    L_0x00cb:
        r2 = 0;
        goto L_0x0095;
    L_0x00cd:
        r4 = r2 instanceof android.telephony.CellInfoWcdma;	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x0129;
    L_0x00d1:
        r0 = r2;
        r0 = (android.telephony.CellInfoWcdma) r0;	 Catch:{ Throwable -> 0x0070 }
        r8 = r0;
        r2 = r8.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x0127;
    L_0x00db:
        r4 = r2.getLac();	 Catch:{ Throwable -> 0x0070 }
        r4 = b(r4);	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x0127;
    L_0x00e5:
        r2 = r2.getCid();	 Catch:{ Throwable -> 0x0070 }
        r2 = c(r2);	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x0127;
    L_0x00ef:
        r2 = 1;
    L_0x00f0:
        if (r2 == 0) goto L_0x0051;
    L_0x00f2:
        r7 = r8.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        r2 = 4;
        r4 = r7.getMcc();	 Catch:{ Throwable -> 0x0070 }
        r5 = r7.getMnc();	 Catch:{ Throwable -> 0x0070 }
        r6 = r7.getLac();	 Catch:{ Throwable -> 0x0070 }
        r7 = r7.getCid();	 Catch:{ Throwable -> 0x0070 }
        r8 = r8.getCellSignalStrength();	 Catch:{ Throwable -> 0x0070 }
        r8 = r8.getDbm();	 Catch:{ Throwable -> 0x0070 }
        r9 = a(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x0070 }
        r2 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r4 = r14.a(r9);	 Catch:{ Throwable -> 0x0070 }
        r2 = java.lang.Math.min(r2, r4);	 Catch:{ Throwable -> 0x0070 }
        r2 = (int) r2;	 Catch:{ Throwable -> 0x0070 }
        r2 = (short) r2;	 Catch:{ Throwable -> 0x0070 }
        r9.l = r2;	 Catch:{ Throwable -> 0x0070 }
        r13.add(r9);	 Catch:{ Throwable -> 0x0070 }
        goto L_0x0051;
    L_0x0127:
        r2 = 0;
        goto L_0x00f0;
    L_0x0129:
        r4 = r2 instanceof android.telephony.CellInfoLte;	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x01dd;
    L_0x012d:
        r0 = r2;
        r0 = (android.telephony.CellInfoLte) r0;	 Catch:{ Throwable -> 0x0070 }
        r8 = r0;
        r2 = r8.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x0184;
    L_0x0137:
        r4 = r2.getTac();	 Catch:{ Throwable -> 0x0070 }
        r4 = b(r4);	 Catch:{ Throwable -> 0x0070 }
        if (r4 == 0) goto L_0x0184;
    L_0x0141:
        r2 = r2.getCi();	 Catch:{ Throwable -> 0x0070 }
        r2 = c(r2);	 Catch:{ Throwable -> 0x0070 }
        if (r2 == 0) goto L_0x0184;
    L_0x014b:
        r2 = 1;
    L_0x014c:
        if (r2 == 0) goto L_0x0051;
    L_0x014e:
        r7 = r8.getCellIdentity();	 Catch:{ Throwable -> 0x0070 }
        r2 = 3;
        r4 = r7.getMcc();	 Catch:{ Throwable -> 0x0070 }
        r5 = r7.getMnc();	 Catch:{ Throwable -> 0x0070 }
        r6 = r7.getTac();	 Catch:{ Throwable -> 0x0070 }
        r7 = r7.getCi();	 Catch:{ Throwable -> 0x0070 }
        r8 = r8.getCellSignalStrength();	 Catch:{ Throwable -> 0x0070 }
        r8 = r8.getDbm();	 Catch:{ Throwable -> 0x0070 }
        r2 = a(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x0070 }
        r4 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r6 = r14.a(r2);	 Catch:{ Throwable -> 0x01d6 }
        r4 = java.lang.Math.min(r4, r6);	 Catch:{ Throwable -> 0x01d6 }
        r3 = (int) r4;	 Catch:{ Throwable -> 0x01d6 }
        r3 = (short) r3;	 Catch:{ Throwable -> 0x01d6 }
        r2.l = r3;	 Catch:{ Throwable -> 0x01d6 }
        r13.add(r2);	 Catch:{ Throwable -> 0x01d6 }
    L_0x0181:
        r9 = r2;
        goto L_0x0051;
    L_0x0184:
        r2 = 0;
        goto L_0x014c;
    L_0x0186:
        if (r13 == 0) goto L_0x01da;
    L_0x0188:
        r2 = r13.size();
        if (r2 <= 0) goto L_0x01da;
    L_0x018e:
        r0 = r16;
        r2 = r0.a;
        r2 = r2 | 4;
        r0 = r16;
        r0.a = r2;
        r14.a(r13);
        r2 = r13.size();
        r2 = r2 + -1;
        r2 = r13.get(r2);
        r7 = r2;
        r7 = (com.loc.ce) r7;
        if (r7 == 0) goto L_0x01c8;
    L_0x01aa:
        r2 = r7.k;
        r3 = 2;
        if (r2 != r3) goto L_0x01c8;
    L_0x01af:
        r2 = new android.telephony.cdma.CdmaCellLocation;
        r2.<init>();
        r3 = r7.i;
        r4 = r7.e;
        r5 = r7.f;
        r6 = r7.g;
        r7 = r7.h;
        r2.setCellLocationData(r3, r4, r5, r6, r7);
        r3 = r2;
        r2 = r11;
    L_0x01c3:
        if (r3 == 0) goto L_0x0009;
    L_0x01c5:
        r2 = r3;
        goto L_0x0009;
    L_0x01c8:
        r2 = new android.telephony.gsm.GsmCellLocation;
        r2.<init>();
        r3 = r9.c;
        r4 = r9.d;
        r2.setLacAndCid(r3, r4);
        r3 = r10;
        goto L_0x01c3;
    L_0x01d6:
        r3 = move-exception;
        r9 = r2;
        goto L_0x0051;
    L_0x01da:
        r3 = r10;
        r2 = r11;
        goto L_0x01c3;
    L_0x01dd:
        r2 = r9;
        goto L_0x0181;
        */
    }

    private static ce a(int i, boolean z, int i2, int i3, int i4, int i5, int i6) {
        ce ceVar = new ce(i, z);
        ceVar.a = i2;
        ceVar.b = i3;
        ceVar.c = i4;
        ceVar.d = i5;
        ceVar.j = i6;
        return ceVar;
    }

    @SuppressLint({"NewApi"})
    private ce a(CellInfoCdma cellInfoCdma, boolean z) {
        int parseInt;
        int parseInt2;
        ce a;
        CellIdentityCdma cellIdentity = cellInfoCdma.getCellIdentity();
        String[] a2 = de.a(this.c);
        try {
            parseInt = Integer.parseInt(a2[0]);
            try {
                parseInt2 = Integer.parseInt(a2[1]);
            } catch (Throwable th) {
                parseInt2 = 0;
                a = a(RainSurfaceView.RAIN_LEVEL_SHOWER, z, parseInt, parseInt2, 0, 0, cellInfoCdma.getCellSignalStrength().getCdmaDbm());
                a.g = cellIdentity.getSystemId();
                a.h = cellIdentity.getNetworkId();
                a.i = cellIdentity.getBasestationId();
                a.e = cellIdentity.getLatitude();
                a.f = cellIdentity.getLongitude();
                return a;
            }
        } catch (Throwable th2) {
            parseInt = 0;
            parseInt2 = 0;
            a = a(RainSurfaceView.RAIN_LEVEL_SHOWER, z, parseInt, parseInt2, 0, 0, cellInfoCdma.getCellSignalStrength().getCdmaDbm());
            a.g = cellIdentity.getSystemId();
            a.h = cellIdentity.getNetworkId();
            a.i = cellIdentity.getBasestationId();
            a.e = cellIdentity.getLatitude();
            a.f = cellIdentity.getLongitude();
            return a;
        }
        a = a(RainSurfaceView.RAIN_LEVEL_SHOWER, z, parseInt, parseInt2, 0, 0, cellInfoCdma.getCellSignalStrength().getCdmaDbm());
        a.g = cellIdentity.getSystemId();
        a.h = cellIdentity.getNetworkId();
        a.i = cellIdentity.getBasestationId();
        a.e = cellIdentity.getLatitude();
        a.f = cellIdentity.getLongitude();
        return a;
    }

    private static ce a(NeighboringCellInfo neighboringCellInfo, String[] strArr) {
        try {
            ce ceVar = new ce(1, false);
            ceVar.a = Integer.parseInt(strArr[0]);
            ceVar.b = Integer.parseInt(strArr[1]);
            ceVar.c = cz.b(neighboringCellInfo, "getLac", new Object[0]);
            ceVar.d = neighboringCellInfo.getCid();
            ceVar.j = de.a(neighboringCellInfo.getRssi());
            return ceVar;
        } catch (Throwable th) {
            cw.a(th, "CgiManager", "getGsm");
            return null;
        }
    }

    public static ArrayList<ce> a() {
        return b;
    }

    private void a(CellLocation cellLocation, String[] strArr, boolean z) {
        if (cellLocation != null && this.c != null) {
            b.clear();
            if (a(cellLocation)) {
                this.a = 1;
                ArrayList arrayList = b;
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                ce ceVar = new ce(1, true);
                ceVar.a = Integer.parseInt(strArr[0]);
                ceVar.b = Integer.parseInt(strArr[1]);
                ceVar.c = gsmCellLocation.getLac();
                ceVar.d = gsmCellLocation.getCid();
                ceVar.j = this.l;
                arrayList.add(ceVar);
                if (!z) {
                    List<NeighboringCellInfo> neighboringCellInfo = this.c.getNeighboringCellInfo();
                    if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                        for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                            if (neighboringCellInfo2 != null && a(neighboringCellInfo2.getLac(), neighboringCellInfo2.getCid())) {
                                ce a = a(neighboringCellInfo2, strArr);
                                if (a != null && !b.contains(a)) {
                                    b.add(a);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean a(int i) {
        return i > 0 && i <= 15;
    }

    private static boolean a(int i, int i2) {
        return (i == -1 || i == 0 || i > 65535 || i2 == -1 || i2 == 0 || i2 == 65535 || i2 >= 268435455) ? false : true;
    }

    private boolean a(CellLocation cellLocation) {
        if (cellLocation == null) {
            return false;
        }
        boolean z = true;
        Context context = this.i;
        switch (b(cellLocation)) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                try {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    z = a(gsmCellLocation.getLac(), gsmCellLocation.getCid());
                } catch (Throwable th) {
                    cw.a(th, "CgiManager", "cgiUseful Cgi.iGsmT");
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                try {
                    if (cz.b(cellLocation, "getSystemId", new Object[0]) <= 0 || cz.b(cellLocation, "getNetworkId", new Object[0]) < 0 || cz.b(cellLocation, "getBaseStationId", new Object[0]) < 0) {
                        z = false;
                    }
                } catch (Throwable th2) {
                    cw.a(th2, "CgiManager", "cgiUseful Cgi.iCdmaT");
                }
                break;
        }
        if (!z) {
            this.a = 0;
        }
        return z;
    }

    private int b(CellLocation cellLocation) {
        int i = 0;
        if (this.g || cellLocation == null) {
            return 0;
        }
        if (cellLocation instanceof GsmCellLocation) {
            return 1;
        }
        try {
            Class.forName("android.telephony.cdma.CdmaCellLocation");
            i = RainSurfaceView.RAIN_LEVEL_SHOWER;
            return RainSurfaceView.RAIN_LEVEL_SHOWER;
        } catch (Throwable th) {
            cw.a(th, "Utils", "getCellLocT");
            return i;
        }
    }

    public static ArrayList<ce> b() {
        return k;
    }

    private static boolean b(int i) {
        return (i == -1 || i == 0 || i > 65535) ? false : true;
    }

    private static boolean c(int i) {
        return (i == -1 || i == 0 || i == 65535 || i >= 268435455) ? false : true;
    }

    private CellLocation k() {
        if (this.c != null) {
            try {
                CellLocation cellLocation = this.c.getCellLocation();
                this.f = null;
                if (a(cellLocation)) {
                    e = cellLocation;
                    return cellLocation;
                }
            } catch (SecurityException e) {
                this.f = e.getMessage();
            } catch (Throwable th) {
                this.f = null;
                cw.a(th, "CgiManager", "getCellLocation");
            }
        }
        return null;
    }

    private void l() {
        this.f = null;
        e = null;
        this.a = 0;
        b.clear();
    }

    @SuppressLint({"NewApi"})
    private CellLocation m() {
        CellLocation cellLocation = null;
        Object obj = this.c;
        if (obj == null) {
            return null;
        }
        CellLocation k = k();
        if (a(k)) {
            return k;
        }
        if (de.c() >= 18) {
            try {
                cellLocation = a(obj.getAllCellInfo());
            } catch (SecurityException e) {
                this.f = e.getMessage();
            }
        }
        if (cellLocation != null) {
            return cellLocation;
        }
        cellLocation = a(obj, "getCellLocationExt", Integer.valueOf(1));
        if (cellLocation != null) {
            return cellLocation;
        }
        cellLocation = a(obj, "getCellLocationGemini", Integer.valueOf(1));
        return cellLocation != null ? cellLocation : cellLocation;
    }

    private CellLocation n() {
        CellLocation cellLocation = null;
        Object obj = this.n;
        if (obj != null) {
            try {
                Class o = o();
                if (o.isInstance(obj)) {
                    obj = o.cast(obj);
                    String str = "getCellLocation";
                    cellLocation = a(obj, str, new Object[0]);
                    if (cellLocation == null) {
                        cellLocation = a(obj, str, Integer.valueOf(1));
                        if (cellLocation == null) {
                            cellLocation = a(obj, "getCellLocationGemini", Integer.valueOf(1));
                            if (cellLocation == null) {
                                cellLocation = a(obj, "getAllCellInfo", Integer.valueOf(1));
                                if (cellLocation != null) {
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                cw.a(th, "CgiManager", "getSim2Cgi");
            }
        }
        return cellLocation;
    }

    private Class<?> o() {
        String str;
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        switch (this.o) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                str = "android.telephony.TelephonyManager";
                break;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                str = "android.telephony.MSimTelephonyManager";
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                str = "android.telephony.TelephonyManager2";
                break;
            default:
                str = null;
                break;
        }
        try {
            return systemClassLoader.loadClass(str);
        } catch (Throwable th) {
            cw.a(th, "CgiManager", "getSim2TmClass");
            return null;
        }
    }

    private int p() {
        try {
            Class.forName("android.telephony.MSimTelephonyManager");
            this.o = 1;
        } catch (Throwable th) {
        }
        if (this.o == 0) {
            try {
                Class.forName("android.telephony.TelephonyManager2");
                this.o = 2;
            } catch (Throwable th2) {
            }
        }
        return this.o;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(boolean r10) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cf.a(boolean):void");
        /*
        this = this;
        r8 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r1 = 1;
        r2 = 0;
        r0 = r9.i;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = com.loc.de.a(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r9.g = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r9.g;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 == 0) goto L_0x006c;
    L_0x0011:
        r0 = r2;
    L_0x0012:
        if (r0 == 0) goto L_0x0064;
    L_0x0014:
        r0 = r9.g;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 != 0) goto L_0x0032;
    L_0x0018:
        r0 = r9.c;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 == 0) goto L_0x0032;
    L_0x001c:
        r0 = r9.m();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r3 = r9.a(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r3 != 0) goto L_0x002a;
    L_0x0026:
        r0 = r9.n();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x002a:
        r3 = r9.a(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r3 == 0) goto L_0x007b;
    L_0x0030:
        e = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x0032:
        r0 = e;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r9.a(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 != 0) goto L_0x0087;
    L_0x003a:
        r0 = b;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0.clear();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = k;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0.clear();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x0044:
        r0 = r9.c;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 == 0) goto L_0x005e;
    L_0x0048:
        r0 = r9.c;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r0.getNetworkOperator();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r9.j = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r9.j;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 != 0) goto L_0x005e;
    L_0x0058:
        r0 = r9.a;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r0 | 8;
        r9.a = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x005e:
        r0 = com.loc.de.b();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        d = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x0064:
        r0 = r9.g;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 == 0) goto L_0x017e;
    L_0x0068:
        r9.l();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x006b:
        return;
    L_0x006c:
        r4 = com.loc.de.b();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r6 = d;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r4 = r4 - r6;
        r6 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x019f;
    L_0x0079:
        r0 = r2;
        goto L_0x0012;
    L_0x007b:
        r0 = 0;
        e = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        goto L_0x0032;
    L_0x007f:
        r0 = move-exception;
        r0 = r0.getMessage();
        r9.f = r0;
        goto L_0x006b;
    L_0x0087:
        r0 = r9.c;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r3 = com.loc.de.a(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = e;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r4 = r9.i;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r9.b(r0);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        switch(r0) {
            case 1: goto L_0x0099;
            case 2: goto L_0x00a8;
            default: goto L_0x0098;
        };	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x0098:
        goto L_0x0044;
    L_0x0099:
        r0 = e;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r9.a(r0, r3, r10);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        goto L_0x0044;
    L_0x009f:
        r0 = move-exception;
        r1 = "CgiManager";
        r2 = "refresh";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x006b;
    L_0x00a8:
        r4 = e;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r4 == 0) goto L_0x0044;
    L_0x00ac:
        r0 = b;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0.clear();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = com.loc.de.c();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r5 = 5;
        if (r0 < r5) goto L_0x0044;
    L_0x00b8:
        r0 = r9.n;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r0 == 0) goto L_0x00e4;
    L_0x00bc:
        r0 = r4.getClass();	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        r5 = "mGsmCellLoc";
        r0 = r0.getDeclaredField(r5);	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        r5 = r0.isAccessible();	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        if (r5 != 0) goto L_0x00d0;
    L_0x00cc:
        r5 = 1;
        r0.setAccessible(r5);	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
    L_0x00d0:
        r0 = r0.get(r4);	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        r0 = (android.telephony.gsm.GsmCellLocation) r0;	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        if (r0 == 0) goto L_0x017b;
    L_0x00d8:
        r5 = r9.a(r0);	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        if (r5 == 0) goto L_0x017b;
    L_0x00de:
        r9.a(r0, r3, r10);	 Catch:{ Throwable -> 0x017a, SecurityException -> 0x007f }
        r0 = r1;
    L_0x00e2:
        if (r0 != 0) goto L_0x0044;
    L_0x00e4:
        r0 = r9.a(r4);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r0 == 0) goto L_0x0044;
    L_0x00ea:
        r0 = 2;
        r9.a = r0;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0 = new com.loc.ce;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = 2;
        r2 = 1;
        r0.<init>(r1, r2);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = 0;
        r1 = r3[r1];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = java.lang.Integer.parseInt(r1);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.a = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = 1;
        r1 = r3[r1];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = java.lang.Integer.parseInt(r1);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.b = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = "getSystemId";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = com.loc.cz.b(r4, r1, r2);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.g = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = "getNetworkId";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = com.loc.cz.b(r4, r1, r2);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.h = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = "getBaseStationId";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = com.loc.cz.b(r4, r1, r2);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.i = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = r9.l;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.j = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = "getBaseStationLatitude";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = com.loc.cz.b(r4, r1, r2);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.e = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = "getBaseStationLongitude";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = com.loc.cz.b(r4, r1, r2);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r0.f = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = r0.e;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 < 0) goto L_0x015b;
    L_0x0145:
        r1 = r0.f;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 < 0) goto L_0x015b;
    L_0x0149:
        r1 = r0.e;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 == r8) goto L_0x015b;
    L_0x014d:
        r1 = r0.f;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 == r8) goto L_0x015b;
    L_0x0151:
        r1 = r0.e;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r2 = r0.f;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 != r2) goto L_0x0161;
    L_0x0157:
        r1 = r0.e;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 <= 0) goto L_0x0161;
    L_0x015b:
        r1 = 0;
        r0.e = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = 0;
        r0.f = r1;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
    L_0x0161:
        r1 = b;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1 = r1.contains(r0);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        if (r1 != 0) goto L_0x0044;
    L_0x0169:
        r1 = b;	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        r1.add(r0);	 Catch:{ Throwable -> 0x0170, SecurityException -> 0x007f }
        goto L_0x0044;
    L_0x0170:
        r0 = move-exception;
        r1 = "CgiManager";
        r2 = "hdlCdmaLocChange";
        com.loc.cw.a(r0, r1, r2);	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        goto L_0x0044;
    L_0x017a:
        r0 = move-exception;
    L_0x017b:
        r0 = r2;
        goto L_0x00e2;
    L_0x017e:
        r0 = r9.a;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        switch(r0) {
            case 1: goto L_0x0185;
            case 2: goto L_0x0192;
            default: goto L_0x0183;
        };	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
    L_0x0183:
        goto L_0x006b;
    L_0x0185:
        r0 = b;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r0.isEmpty();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 == 0) goto L_0x006b;
    L_0x018d:
        r0 = 0;
        r9.a = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        goto L_0x006b;
    L_0x0192:
        r0 = b;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        r0 = r0.isEmpty();	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        if (r0 == 0) goto L_0x006b;
    L_0x019a:
        r0 = 0;
        r9.a = r0;	 Catch:{ SecurityException -> 0x007f, Throwable -> 0x009f }
        goto L_0x006b;
    L_0x019f:
        r0 = r1;
        goto L_0x0012;
        */
    }

    public final ce c() {
        if (this.g) {
            return null;
        }
        ArrayList arrayList = b;
        return arrayList.size() > 0 ? (ce) arrayList.get(0) : null;
    }

    public final int d() {
        return this.a;
    }

    public final int e() {
        return this.a & 3;
    }

    public final TelephonyManager f() {
        return this.c;
    }

    public final void g() {
        this.m.a();
        this.l = -113;
        this.c = null;
        this.n = null;
    }

    public final String h() {
        return this.f;
    }

    public final String i() {
        return this.j;
    }

    public final String j() {
        if (this.g) {
            l();
        }
        if (this.h == null) {
            this.h = new StringBuilder();
        } else {
            this.h.delete(0, this.h.length());
        }
        switch (this.a & 3) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                for (int i = 1; i < b.size(); i++) {
                    this.h.append("#").append(((ce) b.get(i)).b);
                    this.h.append("|").append(((ce) b.get(i)).c);
                    this.h.append("|").append(((ce) b.get(i)).d);
                }
                break;
        }
        if (this.h.length() > 0) {
            this.h.deleteCharAt(0);
        }
        return this.h.toString();
    }
}
