package net.oneplus.weather.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import net.oneplus.weather.R;

public class CenterAlertDialog extends Dialog {
    private TextView mCancel;
    private OnClickListener mCancelListener;
    private TextView mMsg;
    private TextView mOK;
    private OnClickListener mOKListener;
    private TextView mTitle;

    public CenterAlertDialog(Context context) {
        super(context);
        requestWindowFeature(1);
        setContentView(LayoutInflater.from(context).inflate(R.layout.center_alert_dialog, null));
        this.mMsg = (TextView) findViewById(R.id.center_alert_dialog_msg);
        this.mMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.mOK = (TextView) findViewById(R.id.center_alert_dialog_ok);
        this.mTitle = (TextView) findViewById(R.id.center_alert_dialog_title);
        this.mCancel = (TextView) findViewById(R.id.center_alert_dialog_cancel);
        this.mOK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CenterAlertDialog.this.mOKListener != null) {
                    CenterAlertDialog.this.mOKListener.onClick(v);
                }
                CenterAlertDialog.this.dismiss();
            }
        });
        this.mCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CenterAlertDialog.this.mCancelListener != null) {
                    CenterAlertDialog.this.mCancelListener.onClick(v);
                }
                CenterAlertDialog.this.dismiss();
            }
        });
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    public void setMessage(String text) {
        this.mMsg.setText(text);
    }

    public void setConfirmVisibility(int visibility) {
        if (this.mOK != null) {
            this.mOK.setVisibility(visibility);
        }
    }

    public void setCancelVisibility(int visibility) {
        if (this.mCancel != null) {
            this.mCancel.setVisibility(visibility);
        }
    }

    public void setConfirmOnClickListener(OnClickListener l) {
        this.mOKListener = l;
    }

    public void setCancelOnClickListener(OnClickListener listener) {
        this.mCancelListener = listener;
    }

    public void setConfirmText(String text) {
        this.mOK.setText(text);
    }
}
