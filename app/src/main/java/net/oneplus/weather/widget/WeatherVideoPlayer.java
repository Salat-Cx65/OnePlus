package net.oneplus.weather.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;

public class WeatherVideoPlayer extends FrameLayout implements OnCompletionListener {
    private ImageView mImageView;
    private Uri mUri;
    private BaseVideoView mVideoView;

    public WeatherVideoPlayer(Context context) {
        this(context, null);
    }

    public WeatherVideoPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherVideoPlayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.weather_video_layout, this, true);
        initViews();
    }

    private void initViews() {
        this.mImageView = (ImageView) findViewById(R.id.imageViewBlank);
        this.mVideoView = (BaseVideoView) findViewById(R.id.videoViewWeather);
        this.mVideoView.setOnCompletionListener(this);
    }

    public void start() {
        this.mVideoView.setVideoURI(this.mUri);
        this.mVideoView.setVisibility(0);
        this.mVideoView.start();
    }

    public void stop() {
    }

    public void stopPlaySound() {
    }

    public void pause() {
    }

    public void setVideoWeatherType() {
    }

    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        this.mUri = uri;
    }

    public void setAudioId(int audioId) {
    }

    public void onCompletion(MediaPlayer mp) {
        this.mVideoView.start();
        this.mImageView.setVisibility(DetectedActivity.RUNNING);
    }
}
