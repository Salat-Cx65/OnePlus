package net.oneplus.weather.starwar;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.VideoView;
import com.android.volley.DefaultRetryPolicy;
import net.oneplus.weather.BuildConfig;
import net.oneplus.weather.R;

public class VidePlayActivity extends Activity implements OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private VideoView mVideoView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starwar_activity);
        this.mVideoView = (VideoView) findViewById(R.id.starwar_view);
        Uri uri = getVideoUri();
        if (uri != null) {
            this.mVideoView.setVideoURI(uri);
            this.mVideoView.setOnCompletionListener(this);
            setWindowBrightness(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mVideoView.start();
        }
    }

    protected void onPause() {
        super.onPause();
        this.mVideoView.stopPlayback();
        finish();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(4610);
    }

    private void setWindowBrightness(float brightness) {
        Window window = getWindow();
        LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness;
        window.setAttributes(lp);
    }

    public Uri getVideoUri() {
        int rawId = getResources().getIdentifier("starwar", "raw", BuildConfig.APPLICATION_ID);
        return rawId != 0 ? Uri.parse("android.resource://" + getPackageName() + "/" + rawId) : null;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        this.mVideoView.stopPlayback();
        finish();
    }
}
