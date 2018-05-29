package android.support.v7.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.DialogFragment;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class AppCompatDialogFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getContext(), getTheme());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setupDialog(Dialog dialog, int style) {
        if (dialog instanceof AppCompatDialog) {
            AppCompatDialog acd = (AppCompatDialog) dialog;
            switch (style) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    acd.supportRequestWindowFeature(1);
                    return;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    dialog.getWindow().addFlags(R.styleable.Toolbar_titleMarginStart);
                    acd.supportRequestWindowFeature(1);
                    return;
                default:
                    return;
            }
        }
        super.setupDialog(dialog, style);
    }
}
