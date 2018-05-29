package com.oneplus.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import com.oneplus.commonctrl.R;

public class OPBottomSheet {
    private Dialog mDialog;
    private View mView;

    public OPBottomSheet(Context context) {
        this.mDialog = new Dialog(context, R.style.Oneplus_bottom_fullscreen);
        this.mDialog.requestWindowFeature(1);
        Window window = this.mDialog.getWindow();
        window.setGravity(net.oneplus.weather.R.styleable.AppCompatTheme_panelMenuListTheme);
        window.setWindowAnimations(R.style.Oneplus_popup_bottom_animation);
    }

    public void show() {
        if (this.mDialog != null && this.mView != null) {
            this.mDialog.setContentView(this.mView);
            this.mDialog.show();
        }
    }

    public void dismiss() {
        if (isShowing()) {
            this.mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return this.mDialog != null ? this.mDialog.isShowing() : false;
    }

    public void setView(View view) {
        this.mView = view;
    }
}
