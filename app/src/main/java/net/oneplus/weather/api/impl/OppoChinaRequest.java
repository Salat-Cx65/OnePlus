package net.oneplus.weather.api.impl;

import net.oneplus.weather.api.CommonConfig;
import net.oneplus.weather.api.WeatherRequest;
import net.oneplus.weather.api.WeatherResponse.CacheListener;
import net.oneplus.weather.api.WeatherResponse.NetworkListener;
import net.oneplus.weather.api.parser.OppoChinaResponseParserV3;
import net.oneplus.weather.api.parser.ResponseParser;

public class OppoChinaRequest extends WeatherRequest {
    public static final String DATA_SOURCE_NAME = "Oppo.China";

    public OppoChinaRequest(String key) {
        super(key);
    }

    public OppoChinaRequest(int type, String key) {
        super(type, key);
    }

    public OppoChinaRequest(String key, NetworkListener networkListener, CacheListener cacheListener) {
        super(key, networkListener, cacheListener);
    }

    public OppoChinaRequest(int type, String key, NetworkListener networkListener, CacheListener cacheListener) {
        super(type, key, networkListener, cacheListener);
    }

    public String getRequestUrl(int type) {
        return type == 16 ? null : CommonConfig.getOppoChinaUrl(getRequestKey());
    }

    public ResponseParser getResponseParser() {
        return new OppoChinaResponseParserV3(this);
    }

    public String getMemCacheKey() {
        return "Oppo.China#" + getRequestKey();
    }

    public String getDiskCacheKey(int type) {
        return "Oppo.China#" + getRequestKey();
    }
}
