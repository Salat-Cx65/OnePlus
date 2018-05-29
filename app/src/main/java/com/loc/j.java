package com.loc;

import com.amap.api.services.core.AMapException;
import net.oneplus.weather.util.StringUtils;

// compiled from: AMapCoreException.java
public final class j extends Exception {
    private String a;
    private String b;
    private String c;
    private String d;
    private int e;

    public j(String str) {
        super(str);
        this.a = "\u672a\u77e5\u7684\u9519\u8bef";
        this.b = StringUtils.EMPTY_STRING;
        this.c = "1900";
        this.d = "UnknownError";
        this.e = -1;
        this.a = str;
        if (AMapException.AMAP_CLIENT_IO_EXCEPTION.equals(str)) {
            this.e = 21;
            this.c = "1902";
            this.d = "IOException";
        } else if ("socket \u8fde\u63a5\u5f02\u5e38 - SocketException".equals(str)) {
            this.e = 22;
        } else if (AMapException.AMAP_CLIENT_SOCKET_TIMEOUT_EXCEPTION.equals(str)) {
            this.e = 23;
            this.c = "1802";
            this.d = "SocketTimeoutException";
        } else if (AMapException.AMAP_CLIENT_INVALID_PARAMETER.equals(str)) {
            this.e = 24;
            this.c = "1901";
            this.d = "IllegalArgumentException";
        } else if (AMapException.AMAP_CLIENT_NULLPOINT_EXCEPTION.equals(str)) {
            this.e = 25;
            this.c = "1903";
            this.d = "NullPointException";
        } else if (AMapException.AMAP_CLIENT_URL_EXCEPTION.equals(str)) {
            this.e = 26;
            this.c = "1803";
            this.d = "MalformedURLException";
        } else if (AMapException.AMAP_CLIENT_UNKNOWHOST_EXCEPTION.equals(str)) {
            this.e = 27;
            this.c = "1804";
            this.d = "UnknownHostException";
        } else if ("\u670d\u52a1\u5668\u8fde\u63a5\u5931\u8d25 - UnknownServiceException".equals(str)) {
            this.e = 28;
            this.c = "1805";
            this.d = "CannotConnectToHostException";
        } else if (AMapException.AMAP_CLIENT_ERROR_PROTOCOL.equals(str)) {
            this.e = 29;
            this.c = "1801";
            this.d = "ProtocolException";
        } else if ("http\u8fde\u63a5\u5931\u8d25 - ConnectionException".equals(str)) {
            this.e = 30;
            this.c = "1806";
            this.d = "ConnectionException";
        } else if ("\u672a\u77e5\u7684\u9519\u8bef".equals(str)) {
            this.e = 31;
        } else if ("key\u9274\u6743\u5931\u8d25".equals(str)) {
            this.e = 32;
        } else if ("requeust is null".equals(str)) {
            this.e = 1;
        } else if ("request url is empty".equals(str)) {
            this.e = 2;
        } else if ("response is null".equals(str)) {
            this.e = 3;
        } else if ("thread pool has exception".equals(str)) {
            this.e = 4;
        } else if ("sdk name is invalid".equals(str)) {
            this.e = 5;
        } else if ("sdk info is null".equals(str)) {
            this.e = 6;
        } else if ("sdk packages is null".equals(str)) {
            this.e = 7;
        } else if ("\u7ebf\u7a0b\u6c60\u4e3a\u7a7a".equals(str)) {
            this.e = 8;
        } else if ("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef".equals(str)) {
            this.e = 101;
        } else {
            this.e = -1;
        }
    }

    public j(String str, String str2) {
        this(str);
        this.b = str2;
    }

    public final String a() {
        return this.a;
    }

    public final String b() {
        return this.c;
    }

    public final String c() {
        return this.d;
    }

    public final String d() {
        return this.b;
    }

    public final int e() {
        return this.e;
    }
}
