package com.loc;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

// compiled from: BinaryRequest.java
public abstract class bj extends bn {
    protected Context a;
    protected s b;

    public bj(Context context, s sVar) {
        if (context != null) {
            this.a = context.getApplicationContext();
        }
        this.b = sVar;
    }

    protected static byte[] a(byte[] bArr) {
        byte length = (byte) (bArr.length % 256);
        return new byte[]{(byte) (bArr.length / 256), length};
    }

    private static byte[] j() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(t.a("PANDORA$"));
            byteArrayOutputStream.write(new byte[]{(byte) 1});
            byteArrayOutputStream.write(new byte[]{(byte) 0});
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return toByteArray;
            } catch (Throwable th) {
                w.a(th, "BinaryRequest", "getBinaryHead");
                return toByteArray;
            }
        } catch (Throwable th2) {
            try {
                w.a(th2, "BinaryRequest", "getBinaryHead");
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th22) {
                    w.a(th22, "BinaryRequest", "getBinaryHead");
                }
                return null;
            } catch (Throwable th3) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th4) {
                    w.a(th4, "BinaryRequest", "getBinaryHead");
                }
            }
        }
    }

    private byte[] k() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] a;
            byteArrayOutputStream.write(new byte[]{(byte) 3});
            if (g()) {
                a = m.a(this.a, i());
                byteArrayOutputStream.write(a(a));
                byteArrayOutputStream.write(a);
            } else {
                byteArrayOutputStream.write(new byte[]{(byte) 0, (byte) 0});
            }
            a = t.a(f());
            if (a == null || a.length <= 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0, (byte) 0});
            } else {
                byteArrayOutputStream.write(a(a));
                byteArrayOutputStream.write(a);
            }
            a = t.a(h());
            if (a == null || a.length <= 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0, (byte) 0});
            } else {
                byteArrayOutputStream.write(a(a));
                byteArrayOutputStream.write(a);
            }
            a = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return a;
            } catch (Throwable th) {
                w.a(th, "BinaryRequest", "getRequestEncryptData");
                return a;
            }
        } catch (Throwable th2) {
            try {
                w.a(th2, "BinaryRequest", "getPublicData");
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th22) {
                    w.a(th22, "BinaryRequest", "getRequestEncryptData");
                }
                return new byte[]{(byte) 0};
            } catch (Throwable th3) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th4) {
                    w.a(th4, "BinaryRequest", "getRequestEncryptData");
                }
            }
        }
    }

    private byte[] l() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] a_ = a_();
            if (a_ == null || a_.length == 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0});
                a_ = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                    return a_;
                } catch (Throwable th) {
                    w.a(th, "BinaryRequest", "getRequestRawData");
                    return a_;
                }
            }
            byteArrayOutputStream.write(new byte[]{(byte) 1});
            byteArrayOutputStream.write(a(a_));
            byteArrayOutputStream.write(a_);
            a_ = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return a_;
            } catch (Throwable th2) {
                w.a(th2, "BinaryRequest", "getRequestRawData");
                return a_;
            }
        } catch (Throwable th3) {
            try {
                w.a(th3, "BinaryRequest", "getRequestRawData");
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th32) {
                    w.a(th32, "BinaryRequest", "getRequestRawData");
                }
                return new byte[]{(byte) 0};
            } catch (Throwable th4) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th22) {
                    w.a(th22, "BinaryRequest", "getRequestRawData");
                }
            }
        }
    }

    private byte[] m() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] e = e();
            if (e == null || e.length == 0) {
                byteArrayOutputStream.write(new byte[]{(byte) 0});
                e = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                    return e;
                } catch (Throwable th) {
                    w.a(th, "BinaryRequest", "getRequestEncryptData");
                    return e;
                }
            }
            byteArrayOutputStream.write(new byte[]{(byte) 1});
            Context context = this.a;
            e = m.a(e);
            byteArrayOutputStream.write(a(e));
            byteArrayOutputStream.write(e);
            e = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return e;
            } catch (Throwable th2) {
                w.a(th2, "BinaryRequest", "getRequestEncryptData");
                return e;
            }
        } catch (Throwable th3) {
            try {
                w.a(th3, "BinaryRequest", "getRequestEncryptData");
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th32) {
                    w.a(th32, "BinaryRequest", "getRequestEncryptData");
                }
                return new byte[]{(byte) 0};
            } catch (Throwable th4) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th22) {
                    w.a(th22, "BinaryRequest", "getRequestEncryptData");
                }
            }
        }
    }

    public abstract byte[] a_();

    public Map<String, String> b_() {
        String f = k.f(this.a);
        String a = m.a();
        String a2 = m.a(this.a, a, new StringBuilder("key=").append(f).toString());
        Map<String, String> hashMap = new HashMap();
        hashMap.put("ts", a);
        hashMap.put("key", f);
        hashMap.put("scode", a2);
        return hashMap;
    }

    public final byte[] d() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(j());
            byteArrayOutputStream.write(k());
            byteArrayOutputStream.write(l());
            byteArrayOutputStream.write(m());
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return toByteArray;
            } catch (Throwable th) {
                w.a(th, "BinaryRequest", "getEntityBytes");
                return toByteArray;
            }
        } catch (Throwable th2) {
            try {
                w.a(th2, "BinaryRequest", "getEntityBytes");
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th22) {
                    w.a(th22, "BinaryRequest", "getEntityBytes");
                }
                return null;
            } catch (Throwable th3) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th4) {
                    w.a(th4, "BinaryRequest", "getEntityBytes");
                }
            }
        }
    }

    public abstract byte[] e();

    protected String f() {
        return "2.1";
    }

    public boolean g() {
        return true;
    }

    public String h() {
        return String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{this.b.c(), this.b.a()});
    }

    protected boolean i() {
        return false;
    }
}
