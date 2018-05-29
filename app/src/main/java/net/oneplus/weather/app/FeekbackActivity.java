package net.oneplus.weather.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import net.oneplus.weather.util.AlertUtils;
import net.oneplus.weather.util.NetUtil;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class FeekbackActivity extends TopBarActivity {
    private EditText mContent;
    private ProgressDialog mProgressDialog;

    class AnonymousClass_3 implements Runnable {
        final /* synthetic */ boolean val$exist;
        final /* synthetic */ String val$retInfo;

        AnonymousClass_3(String str, boolean z) {
            this.val$retInfo = str;
            this.val$exist = z;
        }

        public void run() {
            AlertUtils.showSimpleAlertDialog(FeekbackActivity.this, this.val$retInfo, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (AnonymousClass_3.this.val$exist) {
                        FeekbackActivity.this.finish();
                        FeekbackActivity.this.overridePendingTransition(R.anim.alpha_in_listclick, R.anim.alpha_out_listclick);
                    }
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.feekback_activity);
        setRightButtonVisibility(0);
        setRightButtonText(R.string.send);
        setTopTitle(R.string.feedback);
        setRightButtonEnable(false);
        setRightButtonOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String content = FeekbackActivity.this.mContent.getText().toString();
                if (!NetUtil.isNetworkAvailable(FeekbackActivity.this)) {
                    AlertUtils.showNoConnectionDialog(FeekbackActivity.this);
                } else if (!TextUtils.isEmpty(content) && FeekbackActivity.this.mProgressDialog == null) {
                    FeekbackActivity.this.mProgressDialog = new ProgressDialog(FeekbackActivity.this);
                    FeekbackActivity.this.mProgressDialog.setProgressStyle(0);
                    FeekbackActivity.this.mProgressDialog.setCancelable(false);
                    FeekbackActivity.this.mProgressDialog.setMessage(FeekbackActivity.this.getString(R.string.sending));
                    FeekbackActivity.this.mProgressDialog.setCancelable(false);
                    FeekbackActivity.this.mProgressDialog.show();
                }
            }
        });
        this.mContent = (EditText) findViewById(R.id.feekback_content);
        this.mContent.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable edit) {
                if (TextUtils.isEmpty(edit.toString())) {
                    FeekbackActivity.this.setRightButtonEnable(false);
                } else {
                    FeekbackActivity.this.setRightButtonEnable(true);
                }
            }
        });
    }

    private void updateSendMsg(String retInfo, boolean exist) {
        runOnUiThread(new AnonymousClass_3(retInfo, exist));
    }

    public static void showKeyboard(View v) {
        ((InputMethodManager) v.getContext().getSystemService("input_method")).showSoftInput(v, RainSurfaceView.RAIN_LEVEL_SHOWER);
    }
}
