package net.oneplus.weather.location;

import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnSuccessListener;

final /* synthetic */ class GMSLocationHelper$$Lambda$0 implements OnSuccessListener {
    private final GMSLocationHelper arg$1;

    GMSLocationHelper$$Lambda$0(GMSLocationHelper gMSLocationHelper) {
        this.arg$1 = gMSLocationHelper;
    }

    public void onSuccess(Object obj) {
        this.arg$1.lambda$startLocation$1$GMSLocationHelper((LocationSettingsResponse) obj);
    }
}
