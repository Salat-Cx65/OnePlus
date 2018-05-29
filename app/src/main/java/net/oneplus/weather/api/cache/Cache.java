package net.oneplus.weather.api.cache;

import net.oneplus.weather.api.nodes.RootWeather;

public interface Cache {
    void clear();

    void close();

    void flush();

    byte[] getFromDiskCache(String str);

    RootWeather getFromMemCache(String str);

    void putToDisk(String str, byte[] bArr);

    void putToMemory(String str, RootWeather rootWeather);
}
