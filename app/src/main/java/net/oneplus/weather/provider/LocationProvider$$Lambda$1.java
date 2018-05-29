package net.oneplus.weather.provider;

import android.location.Location;
import net.oneplus.weather.location.OneplusLocationListener;

final /* synthetic */ class LocationProvider$$Lambda$1 implements OneplusLocationListener {
    private final LocationProvider arg$1;

    LocationProvider$$Lambda$1(LocationProvider locationProvider) {
        this.arg$1 = locationProvider;
    }

    public void onLocationChanged(Location location) {
        this.arg$1.bridge$lambda$0$LocationProvider(location);
    }
}
