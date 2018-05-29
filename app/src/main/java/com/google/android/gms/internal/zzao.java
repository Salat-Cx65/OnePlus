package com.google.android.gms.internal;

import com.android.volley.toolbox.HttpClientStack.HttpPatch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLSocketFactory;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public final class zzao implements zzan {
    private final zzap zzaC;
    private final SSLSocketFactory zzaD;

    public zzao() {
        this(null);
    }

    private zzao(zzap com_google_android_gms_internal_zzap) {
        this(null, null);
    }

    private zzao(zzap com_google_android_gms_internal_zzap, SSLSocketFactory sSLSocketFactory) {
        this.zzaC = null;
        this.zzaD = null;
    }

    private static HttpEntity zza(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        HttpEntity basicHttpEntity = new BasicHttpEntity();
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength((long) httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    private static void zza(HttpURLConnection httpURLConnection, zzp<?> com_google_android_gms_internal_zzp_) throws IOException, zza {
        byte[] zzg = com_google_android_gms_internal_zzp_.zzg();
        if (zzg != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", zzp.zzf());
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(zzg);
            dataOutputStream.close();
        }
    }

    public final HttpResponse zza(zzp<?> com_google_android_gms_internal_zzp_, Map<String, String> map) throws IOException, zza {
        String zzg;
        String url = com_google_android_gms_internal_zzp_.getUrl();
        HashMap hashMap = new HashMap();
        hashMap.putAll(com_google_android_gms_internal_zzp_.getHeaders());
        hashMap.putAll(map);
        if (this.zzaC != null) {
            zzg = this.zzaC.zzg(url);
            if (zzg == null) {
                String str = "URL blocked by rewriter: ";
                zzg = String.valueOf(url);
                throw new IOException(zzg.length() != 0 ? str.concat(zzg) : new String(str));
            }
        }
        zzg = url;
        URL url2 = new URL(zzg);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        int zzi = com_google_android_gms_internal_zzp_.zzi();
        httpURLConnection.setConnectTimeout(zzi);
        httpURLConnection.setReadTimeout(zzi);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        "https".equals(url2.getProtocol());
        for (String url3 : hashMap.keySet()) {
            httpURLConnection.addRequestProperty(url3, (String) hashMap.get(url3));
        }
        ProtocolVersion protocolVersion;
        StatusLine basicStatusLine;
        HttpResponse basicHttpResponse;
        int method;
        boolean z;
        switch (com_google_android_gms_internal_zzp_.getMethod()) {
            case RainSurfaceView.RAIN_LEVEL_DEFAULT:
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    throw new IOException("Could not retrieve response code from HttpUrlConnection.");
                }
                basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                method = com_google_android_gms_internal_zzp_.getMethod();
                zzi = basicStatusLine.getStatusCode();
                if (method != 4) {
                    if (!((100 <= zzi && zzi < 200) || zzi == 204 || zzi == 304)) {
                        z = true;
                        if (z) {
                            basicHttpResponse.setEntity(zza(httpURLConnection));
                        }
                        for (Entry entry : httpURLConnection.getHeaderFields().entrySet()) {
                            if (entry.getKey() != null) {
                                basicHttpResponse.addHeader(new BasicHeader((String) entry.getKey(), (String) ((List) entry.getValue()).get(0)));
                            }
                        }
                        return basicHttpResponse;
                    }
                }
                z = false;
                if (z) {
                    basicHttpResponse.setEntity(zza(httpURLConnection));
                }
                for (Entry entry2 : httpURLConnection.getHeaderFields().entrySet()) {
                    if (entry2.getKey() != null) {
                        basicHttpResponse.addHeader(new BasicHeader((String) entry2.getKey(), (String) ((List) entry2.getValue()).get(0)));
                    }
                }
                return basicHttpResponse;
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                httpURLConnection.setRequestMethod("GET");
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                    basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                    method = com_google_android_gms_internal_zzp_.getMethod();
                    zzi = basicStatusLine.getStatusCode();
                    if (method != 4) {
                        z = true;
                        if (z) {
                            basicHttpResponse.setEntity(zza(httpURLConnection));
                        }
                        for (Entry entry22 : httpURLConnection.getHeaderFields().entrySet()) {
                            if (entry22.getKey() != null) {
                                basicHttpResponse.addHeader(new BasicHeader((String) entry22.getKey(), (String) ((List) entry22.getValue()).get(0)));
                            }
                        }
                        return basicHttpResponse;
                    }
                    z = false;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry222.getKey(), (String) ((List) entry222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                httpURLConnection.setRequestMethod("POST");
                zza(httpURLConnection, (zzp) com_google_android_gms_internal_zzp_);
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    throw new IOException("Could not retrieve response code from HttpUrlConnection.");
                }
                basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                method = com_google_android_gms_internal_zzp_.getMethod();
                zzi = basicStatusLine.getStatusCode();
                if (method != 4) {
                    z = true;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry2222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry2222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry2222.getKey(), (String) ((List) entry2222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                z = false;
                if (z) {
                    basicHttpResponse.setEntity(zza(httpURLConnection));
                }
                for (Entry entry22222 : httpURLConnection.getHeaderFields().entrySet()) {
                    if (entry22222.getKey() != null) {
                        basicHttpResponse.addHeader(new BasicHeader((String) entry22222.getKey(), (String) ((List) entry22222.getValue()).get(0)));
                    }
                }
                return basicHttpResponse;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                httpURLConnection.setRequestMethod("PUT");
                zza(httpURLConnection, (zzp) com_google_android_gms_internal_zzp_);
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                    basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                    method = com_google_android_gms_internal_zzp_.getMethod();
                    zzi = basicStatusLine.getStatusCode();
                    if (method != 4) {
                        z = true;
                        if (z) {
                            basicHttpResponse.setEntity(zza(httpURLConnection));
                        }
                        for (Entry entry222222 : httpURLConnection.getHeaderFields().entrySet()) {
                            if (entry222222.getKey() != null) {
                                basicHttpResponse.addHeader(new BasicHeader((String) entry222222.getKey(), (String) ((List) entry222222.getValue()).get(0)));
                            }
                        }
                        return basicHttpResponse;
                    }
                    z = false;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry2222222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry2222222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry2222222.getKey(), (String) ((List) entry2222222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                httpURLConnection.setRequestMethod("DELETE");
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    throw new IOException("Could not retrieve response code from HttpUrlConnection.");
                }
                basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                method = com_google_android_gms_internal_zzp_.getMethod();
                zzi = basicStatusLine.getStatusCode();
                if (method != 4) {
                    z = true;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry22222222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry22222222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry22222222.getKey(), (String) ((List) entry22222222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                z = false;
                if (z) {
                    basicHttpResponse.setEntity(zza(httpURLConnection));
                }
                for (Entry entry222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                    if (entry222222222.getKey() != null) {
                        basicHttpResponse.addHeader(new BasicHeader((String) entry222222222.getKey(), (String) ((List) entry222222222.getValue()).get(0)));
                    }
                }
                return basicHttpResponse;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                httpURLConnection.setRequestMethod("HEAD");
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                    basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                    method = com_google_android_gms_internal_zzp_.getMethod();
                    zzi = basicStatusLine.getStatusCode();
                    if (method != 4) {
                        z = true;
                        if (z) {
                            basicHttpResponse.setEntity(zza(httpURLConnection));
                        }
                        for (Entry entry2222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                            if (entry2222222222.getKey() != null) {
                                basicHttpResponse.addHeader(new BasicHeader((String) entry2222222222.getKey(), (String) ((List) entry2222222222.getValue()).get(0)));
                            }
                        }
                        return basicHttpResponse;
                    }
                    z = false;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry22222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry22222222222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry22222222222.getKey(), (String) ((List) entry22222222222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                httpURLConnection.setRequestMethod("OPTIONS");
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    throw new IOException("Could not retrieve response code from HttpUrlConnection.");
                }
                basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                method = com_google_android_gms_internal_zzp_.getMethod();
                zzi = basicStatusLine.getStatusCode();
                if (method != 4) {
                    z = true;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry222222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry222222222222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry222222222222.getKey(), (String) ((List) entry222222222222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                z = false;
                if (z) {
                    basicHttpResponse.setEntity(zza(httpURLConnection));
                }
                for (Entry entry2222222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                    if (entry2222222222222.getKey() != null) {
                        basicHttpResponse.addHeader(new BasicHeader((String) entry2222222222222.getKey(), (String) ((List) entry2222222222222.getValue()).get(0)));
                    }
                }
                return basicHttpResponse;
            case ConnectionResult.RESOLUTION_REQUIRED:
                httpURLConnection.setRequestMethod("TRACE");
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                    basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                    method = com_google_android_gms_internal_zzp_.getMethod();
                    zzi = basicStatusLine.getStatusCode();
                    if (method != 4) {
                        z = true;
                        if (z) {
                            basicHttpResponse.setEntity(zza(httpURLConnection));
                        }
                        for (Entry entry22222222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                            if (entry22222222222222.getKey() != null) {
                                basicHttpResponse.addHeader(new BasicHeader((String) entry22222222222222.getKey(), (String) ((List) entry22222222222222.getValue()).get(0)));
                            }
                        }
                        return basicHttpResponse;
                    }
                    z = false;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry222222222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry222222222222222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry222222222222222.getKey(), (String) ((List) entry222222222222222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            case DetectedActivity.WALKING:
                httpURLConnection.setRequestMethod(HttpPatch.METHOD_NAME);
                zza(httpURLConnection, (zzp) com_google_android_gms_internal_zzp_);
                protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                if (httpURLConnection.getResponseCode() == -1) {
                    throw new IOException("Could not retrieve response code from HttpUrlConnection.");
                }
                basicStatusLine = new BasicStatusLine(protocolVersion, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                basicHttpResponse = new BasicHttpResponse(basicStatusLine);
                method = com_google_android_gms_internal_zzp_.getMethod();
                zzi = basicStatusLine.getStatusCode();
                if (method != 4) {
                    z = true;
                    if (z) {
                        basicHttpResponse.setEntity(zza(httpURLConnection));
                    }
                    for (Entry entry2222222222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                        if (entry2222222222222222.getKey() != null) {
                            basicHttpResponse.addHeader(new BasicHeader((String) entry2222222222222222.getKey(), (String) ((List) entry2222222222222222.getValue()).get(0)));
                        }
                    }
                    return basicHttpResponse;
                }
                z = false;
                if (z) {
                    basicHttpResponse.setEntity(zza(httpURLConnection));
                }
                for (Entry entry22222222222222222 : httpURLConnection.getHeaderFields().entrySet()) {
                    if (entry22222222222222222.getKey() != null) {
                        basicHttpResponse.addHeader(new BasicHeader((String) entry22222222222222222.getKey(), (String) ((List) entry22222222222222222.getValue()).get(0)));
                    }
                }
                return basicHttpResponse;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }
}
