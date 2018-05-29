package net.oneplus.weather.api;

import java.net.URL;

public interface Connection {
    byte[] get() throws WeatherException;

    Connection url(String str);

    Connection url(URL url);
}
