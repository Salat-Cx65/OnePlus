package net.oneplus.weather.api.impl;

import android.content.Context;
import android.os.AsyncTask;
import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.WeatherRequest;
import net.oneplus.weather.api.WeatherResponse;
import net.oneplus.weather.api.helper.LogUtils;
import net.oneplus.weather.api.helper.NetworkHelper;
import net.oneplus.weather.api.helper.NetworkHelper.ResponseListener;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.nodes.RootWeather;

public class OnceRequestExecuter extends AbstractExecuter {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final String TAG = "OnceRequestExecuter";
    private final Context mContext;

    private class ParserWorkerTask extends AsyncTask<byte[], Void, Void> {
        private final WeatherRequest mRequest;
        private final WeatherResponse mResponse;

        public ParserWorkerTask(WeatherRequest mRequest, WeatherResponse mResponse) {
            this.mRequest = mRequest;
            this.mResponse = mResponse;
        }

        protected Void doInBackground(byte[]... params) {
            byte[] data = params[0];
            try {
                RootWeather rootWeather;
                if (this.mRequest.getRequestType() == 8) {
                    rootWeather = this.mRequest.getResponseParser().parseAqi(data);
                } else if (this.mRequest.getRequestType() == 1) {
                    rootWeather = this.mRequest.getResponseParser().parseCurrent(data);
                } else if (this.mRequest.getRequestType() == 16) {
                    rootWeather = this.mRequest.getResponseParser().parseLifeIndex(data);
                } else if (this.mRequest.getRequestType() == 2) {
                    rootWeather = this.mRequest.getResponseParser().parseHourForecasts(data);
                } else if (this.mRequest.getRequestType() == 4) {
                    rootWeather = this.mRequest.getResponseParser().parseDailyForecasts(data);
                } else if (this.mRequest.getRequestType() == 32) {
                    rootWeather = this.mRequest.getResponseParser().parseAlarm(data);
                } else {
                    throw new WeatherException("Unsupport request type!");
                }
                this.mResponse.addResponse(rootWeather, this.mRequest.getRequestType());
            } catch (WeatherException e) {
                this.mResponse.setError(new WeatherException(e.getMessage()));
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            WeatherResponse.deliverResponse(OnceRequestExecuter.this.mContext, this.mRequest, this.mResponse);
        }
    }

    class AnonymousClass_1 implements ResponseListener {
        final /* synthetic */ WeatherRequest val$request;
        final /* synthetic */ WeatherResponse val$response;
        final /* synthetic */ String val$url;

        AnonymousClass_1(String str, WeatherRequest weatherRequest, WeatherResponse weatherResponse) {
            this.val$url = str;
            this.val$request = weatherRequest;
            this.val$response = weatherResponse;
        }

        public void onResponse(byte[] data, String charset) {
            OnceRequestExecuter.this.addToDiskCache(OnceRequestExecuter.this.mContext, this.val$url, data);
            new ParserWorkerTask(this.val$request, this.val$response).execute(new Object[]{data});
        }

        public void onError(WeatherException e) {
            this.val$response.setError(e);
            WeatherResponse.deliverResponse(OnceRequestExecuter.this.mContext, this.val$request, this.val$response);
        }
    }

    static {
        $assertionsDisabled = !OnceRequestExecuter.class.desiredAssertionStatus() ? true : $assertionsDisabled;
    }

    public OnceRequestExecuter(Context context) {
        if ($assertionsDisabled || context != null) {
            this.mContext = context;
            return;
        }
        throw new AssertionError();
    }

    public void execute(WeatherRequest request) {
        String url = request.getRequestUrl(request.getRequestType());
        WeatherResponse response = new WeatherResponse();
        if (StringUtils.isBlank(url)) {
            response.setError(new WeatherException("Wrong request url or the data source don't support this type request!"));
            WeatherResponse.deliverResponse(this.mContext, request, response);
            return;
        }
        LogUtils.d(TAG, "Request url: " + url, new Object[0]);
        NetworkHelper.getInstance(this.mContext).get(url, new AnonymousClass_1(url, request, response));
    }
}
