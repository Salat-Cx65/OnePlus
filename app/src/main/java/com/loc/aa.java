package com.loc;

import android.content.Context;
import android.os.Looper;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.oneplus.weather.util.StringUtils;

// compiled from: AnrLogProcessor.java
public final class aa extends ad {
    private static boolean b;
    private String[] c;
    private int d;
    private boolean e;
    private int f;

    static {
        b = true;
    }

    protected aa(int i) {
        super(i);
        this.c = new String[10];
        this.d = 0;
        this.e = false;
        this.f = 0;
    }

    private String d() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int i = this.d;
            while (i < 10 && i <= 9) {
                stringBuilder.append(this.c[i]);
                i++;
            }
            for (i = 0; i < this.d; i++) {
                stringBuilder.append(this.c[i]);
            }
        } catch (Throwable th) {
            w.a(th, "ANRWriter", "getLogInfo");
        }
        return stringBuilder.toString();
    }

    protected final String a(List<s> list) {
        Throwable th;
        bg bgVar;
        InputStream inputStream;
        String str;
        String str2;
        InputStream inputStream2 = null;
        bg bgVar2 = null;
        try {
            InputStream fileInputStream;
            File file = new File("/data/anr/traces.txt");
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    int available = fileInputStream.available();
                    if (available > 1024000) {
                        fileInputStream.skip((long) (available - 1024000));
                    }
                    bgVar2 = new bg(fileInputStream, bh.a);
                    Object obj = null;
                    while (true) {
                        try {
                            String str3;
                            Object obj2;
                            String trim = bgVar2.a().trim();
                            if (trim.contains("pid")) {
                                while (!trim.startsWith("\"main\"")) {
                                    trim = bgVar2.a();
                                }
                                int i = 1;
                                str3 = trim;
                            } else {
                                obj2 = obj;
                                str3 = trim;
                            }
                            if (str3.equals(StringUtils.EMPTY_STRING) && obj2 != null) {
                                break;
                            }
                            if (obj2 != null) {
                                try {
                                    if (this.d > 9) {
                                        this.d = 0;
                                    }
                                    this.c[this.d] = str3;
                                    this.d++;
                                } catch (EOFException e) {
                                } catch (FileNotFoundException e2) {
                                    bgVar = bgVar2;
                                    inputStream = fileInputStream;
                                } catch (IOException e3) {
                                    th = e3;
                                } catch (Throwable th2) {
                                    w.a(th2, "ANRWriter", "addData");
                                }
                                if (this.f == 5) {
                                    break;
                                } else if (this.e) {
                                    this.f++;
                                } else {
                                    for (s sVar : list) {
                                        this.e = a(sVar.f(), str3);
                                        if (this.e) {
                                            a(sVar);
                                            obj = obj2;
                                            break;
                                        }
                                    }
                                    obj = obj2;
                                }
                            }
                            obj = obj2;
                        } catch (EOFException e4) {
                        } catch (FileNotFoundException e22) {
                            bgVar = bgVar2;
                            inputStream = fileInputStream;
                        } catch (IOException e32) {
                            th2 = e32;
                        } catch (Throwable th3) {
                            th2 = th3;
                        }
                    }
                    if (bgVar2 != null) {
                        try {
                            bgVar2.close();
                        } catch (Throwable th22) {
                            w.a(th22, "ANRWriter", "initLog1");
                        } catch (Throwable th222) {
                            w.a(th222, "ANRWriter", "initLog2");
                        }
                    }
                } catch (FileNotFoundException e5) {
                    bgVar = null;
                    inputStream = fileInputStream;
                    if (bgVar != null) {
                        bgVar.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    return this.e ? d() : null;
                } catch (IOException e6) {
                    th222 = e6;
                    bgVar2 = null;
                    w.a(th222, "ANRWriter", "initLog");
                    if (bgVar2 != null) {
                        bgVar2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (this.e) {
                    }
                } catch (Throwable th4) {
                    th222 = th4;
                    bgVar2 = null;
                    if (bgVar2 != null) {
                        bgVar2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th222;
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e7) {
                        th222 = e7;
                        str = "ANRWriter";
                        str2 = "initLog3";
                        w.a(th222, str, str2);
                        if (this.e) {
                        }
                    } catch (Throwable th5) {
                        th222 = th5;
                        str = "ANRWriter";
                        str2 = "initLog4";
                        w.a(th222, str, str2);
                        if (this.e) {
                        }
                    }
                }
                if (this.e) {
                }
            }
            if (null != null) {
                try {
                    bgVar2.close();
                } catch (Throwable e8) {
                    w.a(e8, "ANRWriter", "initLog1");
                } catch (Throwable e82) {
                    w.a(e82, "ANRWriter", "initLog2");
                }
            }
            if (null != null) {
                try {
                    inputStream2.close();
                } catch (Throwable th2222) {
                    w.a(th2222, "ANRWriter", "initLog3");
                } catch (Throwable th22222) {
                    w.a(th22222, "ANRWriter", "initLog4");
                }
            }
            return null;
        } catch (Throwable th6) {
            th22222 = th6;
            if (bgVar2 != null) {
                try {
                    bgVar2.close();
                } catch (Throwable e9) {
                    w.a(e9, "ANRWriter", "initLog1");
                } catch (Throwable e92) {
                    w.a(e92, "ANRWriter", "initLog2");
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Throwable e922) {
                    w.a(e922, "ANRWriter", "initLog3");
                } catch (Throwable e9222) {
                    w.a(e9222, "ANRWriter", "initLog4");
                }
            }
            throw th22222;
        }
    }

    protected final boolean a(Context context) {
        if (n.m(context) != 1 || !b) {
            return false;
        }
        b = false;
        synchronized (Looper.getMainLooper()) {
            aq aqVar = new aq(context);
            ar a = aqVar.a();
            if (a == null) {
                return true;
            } else if (a.c()) {
                a.c(false);
                aqVar.a(a);
                return true;
            } else {
                return false;
            }
        }
    }
}
