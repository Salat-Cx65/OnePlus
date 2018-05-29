package com.oneplus.lib.widget;

import android.util.Log;

public class FakeWidget {
    private static final String Tag;

    static {
        Tag = FakeWidget.class.getSimpleName();
    }

    public FakeWidget() {
        Log.e(Tag, "FakeWidget created");
    }
}
