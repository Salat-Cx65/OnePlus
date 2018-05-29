package net.oneplus.weather.api.helper;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import net.oneplus.weather.api.helper.NetworkHelper.ResponseListener;

public class ByteArrayRequest extends Request<byte[]> {
    private String contentCharset;
    private final boolean mIsOppo;
    private final ResponseListener mListener;

    public ByteArrayRequest(String url, boolean isOppo, ResponseListener listener, ErrorListener errorListener) {
        super(0, url, errorListener);
        this.mListener = listener;
        this.mIsOppo = isOppo;
    }

    protected void deliverResponse(byte[] response) {
        this.mListener.onResponse(response, this.contentCharset);
    }

    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        this.contentCharset = HttpHeaderParser.parseCharset(response.headers);
        return this.mIsOppo ? Response.success(response.data, OppoHttpHeaderParser.parseCacheHeaders(response, OppoHttpHeaderParser.CACHE_TIME)) : Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
