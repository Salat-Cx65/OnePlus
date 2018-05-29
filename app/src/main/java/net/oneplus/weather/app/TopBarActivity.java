package net.oneplus.weather.app;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import net.oneplus.weather.util.UIUtil;

public class TopBarActivity extends BaseActivity {
    private ImageView mBack;
    private TextView mButton;
    private ViewStub mContent;
    private TextView mTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtil.setWindowStyle(this);
        setContentView(R.layout.top_bar_layout);
        this.mContent = (ViewStub) findViewById(R.id.bottom_content);
        this.mBack = (ImageView) findViewById(R.id.top_back);
        this.mBack.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                TopBarActivity.this.finish();
                TopBarActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.citylist_translate_down);
            }
        });
        this.mTitle = (TextView) findViewById(R.id.top_title);
        this.mButton = (TextView) findViewById(R.id.top_button);
    }

    protected void setRightButtonEnable(boolean enable) {
        this.mButton.setEnabled(enable);
    }

    protected void setRightButtonVisibility(int visibility) {
        this.mButton.setVisibility(visibility);
    }

    protected void setRightButtonText(int resId) {
        this.mButton.setText(resId);
    }

    protected void setRightButtonText(String text) {
        this.mButton.setText(text);
    }

    protected void setRightButtonOnClickListener(OnClickListener l) {
        this.mButton.setOnClickListener(l);
    }

    protected void setTopTitle(int resId) {
        this.mTitle.setText(resId);
    }

    protected void setTopTitle(String title) {
        this.mTitle.setText(title);
    }

    protected void setContent(int resId) {
        this.mContent.setLayoutResource(resId);
        this.mContent.inflate();
    }
}
