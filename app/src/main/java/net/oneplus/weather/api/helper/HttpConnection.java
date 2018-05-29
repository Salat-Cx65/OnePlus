package net.oneplus.weather.api.helper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import net.oneplus.weather.api.Connection;
import net.oneplus.weather.api.WeatherException;

public class HttpConnection implements Connection {
    private static final int CONNECT_TIMEOUT = 15000;
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final int READ_TIMEOUT = 10000;
    private static final String TAG = "HttpConnection";
    private URL mUrl;

    public static HttpConnection connect(String url) {
        HttpConnection con = new HttpConnection();
        con.url(url);
        return con;
    }

    public static HttpConnection connect(URL url) {
        HttpConnection con = new HttpConnection();
        con.url(url);
        return con;
    }

    private static HttpURLConnection createConnection(URL url) throws WeatherException {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.addRequestProperty("Accept-Encoding", "gzip");
            return conn;
        } catch (IOException e) {
            throw new WeatherException(e.getMessage());
        }
    }

    private static byte[] execute(URL url) throws WeatherException {
        HttpURLConnection conn = createConnection(url);
        try {
            InputStream dataStream;
            conn.connect();
            int status = conn.getResponseCode();
            if (status < 200 || status >= 400) {
                throw new WeatherException("HTTP error when fetching URL, code: " + status);
            }
            byte[] resultData;
            if (conn.getContentLength() != 0) {
                bodyStream = null;
                dataStream = null;
                if (conn.getErrorStream() != null) {
                    dataStream = conn.getErrorStream();
                } else {
                    dataStream = conn.getInputStream();
                }
                if (conn.getHeaderField(CONTENT_ENCODING) == null || !conn.getHeaderField(CONTENT_ENCODING).equalsIgnoreCase("gzip")) {
                    bodyStream = new BufferedInputStream(dataStream);
                } else {
                    bodyStream = new BufferedInputStream(new GZIPInputStream(dataStream));
                }
                resultData = IOUtils.toByteArray(bodyStream);
                if (bodyStream != null) {
                    bodyStream.close();
                }
                if (dataStream != null) {
                    dataStream.close();
                }
            } else {
                resultData = IOUtils.emptyByteArray();
            }
            conn.disconnect();
            return resultData;
        } catch (IOException e) {
            throw new WeatherException(e.getMessage());
        } catch (Throwable th) {
        }
    }

    public Connection url(URL url) {
        this.mUrl = url;
        return this;
    }

    public Connection url(String url) {
        Validate.notEmpty(url, "Must supply a valid URL");
        try {
            this.mUrl = new URL(url);
            return this;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + url, e);
        }
    }

    public byte[] get() throws WeatherException {
        return execute(this.mUrl);
    }
}
