package net.oneplus.weather.location;

import com.google.android.gms.tasks.OnFailureListener;

final /* synthetic */ class GMSLocationHelper$$Lambda$1 implements OnFailureListener {
    private final GMSLocationHelper arg$1;

    GMSLocationHelper$$Lambda$1(GMSLocationHelper gMSLocationHelper) {
        this.arg$1 = gMSLocationHelper;
    }

    public void onFailure(Exception exception) {
        this.arg$1.lambda$startLocation$2$GMSLocationHelper(exception);
    }
}
