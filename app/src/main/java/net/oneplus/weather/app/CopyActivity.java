package net.oneplus.weather.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CopyActivity extends BaseBarActivity {
    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copy_activity);
        setBarTitle(R.string.user_right);
        this.mWebView = (WebView) findViewById(R.id.copy_webview);
        this.mWebView.getSettings().setBuiltInZoomControls(false);
        this.mWebView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CopyActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }
        });
        String lan = getResources().getConfiguration().locale.toString();
        String cotyright = "file:///android_asset/copyright_en.htm";
        if (lan.contains("Hans") || lan.equals("zh_CN")) {
            cotyright = "file:///android_asset/copyright.htm";
        } else if (lan.contains("Hant") || lan.equals("zh_TW")) {
            cotyright = "file:///android_asset/copyright_tw.htm";
        }
        this.mWebView.loadUrl(cotyright);
    }
}
