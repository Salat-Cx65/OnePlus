package net.oneplus.weather.app;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

final /* synthetic */ class MockLocation$$Lambda$0 implements OnCheckedChangeListener {
    private final MockLocation arg$1;

    MockLocation$$Lambda$0(MockLocation mockLocation) {
        this.arg$1 = mockLocation;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$onCreate$0$MockLocation(compoundButton, z);
    }
}
