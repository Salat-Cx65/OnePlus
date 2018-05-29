package net.oneplus.weather.widget;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.sdk.utils.OpBoostFramework;
import java.io.IOException;
import java.util.Map;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.shap.FogParticle;

public class BaseVideoView extends SurfaceView implements MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private String TAG;
    private OnAudioFocusChangeListener mAudioFocusListener;
    private int mAudioId;
    private AudioManager mAudioManager;
    private OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener;
    private Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private int mDuration;
    private OnErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    private boolean mIsPlaySound;
    private MediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayerAudio;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnPreparedListener mOnPreparedListener;
    OnPreparedListener mPreparedListener;
    Callback mSHCallback;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;

    public BaseVideoView(Context context) {
        super(context);
        this.TAG = "VideoView";
        this.mAudioId = -1;
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mMediaPlayerAudio = null;
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                BaseVideoView.this.mVideoWidth = mp.getVideoWidth();
                BaseVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BaseVideoView.this.mVideoWidth != 0 && BaseVideoView.this.mVideoHeight != 0) {
                    BaseVideoView.this.getHolder().setFixedSize(BaseVideoView.this.mVideoWidth, BaseVideoView.this.mVideoHeight);
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                BaseVideoView.this.mCurrentState = STATE_PREPARED;
                BaseVideoView.this.mCanPause = BaseVideoView.this.mCanSeekBack = BaseVideoView.this.mCanSeekForward = true;
                if (BaseVideoView.this.mOnPreparedListener != null) {
                    BaseVideoView.this.mOnPreparedListener.onPrepared(BaseVideoView.this.mMediaPlayer);
                }
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.setEnabled(true);
                }
                BaseVideoView.this.mVideoWidth = mp.getVideoWidth();
                BaseVideoView.this.mVideoHeight = mp.getVideoHeight();
                int seekToPosition = BaseVideoView.this.mSeekWhenPrepared;
                if (seekToPosition != 0) {
                    BaseVideoView.this.seekTo(seekToPosition);
                }
                if (BaseVideoView.this.mVideoWidth != 0 && BaseVideoView.this.mVideoHeight != 0) {
                    BaseVideoView.this.getHolder().setFixedSize(BaseVideoView.this.mVideoWidth, BaseVideoView.this.mVideoHeight);
                    if (BaseVideoView.this.mSurfaceWidth != BaseVideoView.this.mVideoWidth || BaseVideoView.this.mSurfaceHeight != BaseVideoView.this.mVideoHeight) {
                        return;
                    }
                    if (BaseVideoView.this.mTargetState == 3) {
                        BaseVideoView.this.start();
                        if (BaseVideoView.this.mMediaController != null) {
                            BaseVideoView.this.mMediaController.show();
                        }
                    } else if (!BaseVideoView.this.isPlaying()) {
                        if ((seekToPosition != 0 || BaseVideoView.this.getCurrentPosition() > 0) && BaseVideoView.this.mMediaController != null) {
                            BaseVideoView.this.mMediaController.show(STATE_IDLE);
                        }
                    }
                } else if (BaseVideoView.this.mTargetState == 3) {
                    BaseVideoView.this.start();
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                BaseVideoView.this.mCurrentState = STATE_PLAYBACK_COMPLETED;
                BaseVideoView.this.mTargetState = STATE_PLAYBACK_COMPLETED;
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.hide();
                }
                if (BaseVideoView.this.mOnCompletionListener != null) {
                    BaseVideoView.this.mOnCompletionListener.onCompletion(BaseVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                Log.d(BaseVideoView.this.TAG, "Error: " + framework_err + "," + impl_err);
                BaseVideoView.this.mCurrentState = STATE_ERROR;
                BaseVideoView.this.mTargetState = STATE_ERROR;
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.hide();
                }
                if ((BaseVideoView.this.mOnErrorListener == null || !BaseVideoView.this.mOnErrorListener.onError(BaseVideoView.this.mMediaPlayer, framework_err, impl_err)) && BaseVideoView.this.getWindowToken() != null) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                BaseVideoView.this.mCurrentBufferPercentage = percent;
            }
        };
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                BaseVideoView.this.mSurfaceWidth = w;
                BaseVideoView.this.mSurfaceHeight = h;
                boolean isValidState;
                if (BaseVideoView.this.mTargetState == 3) {
                    isValidState = true;
                } else {
                    isValidState = false;
                }
                boolean hasValidSize;
                if (BaseVideoView.this.mVideoWidth == w && BaseVideoView.this.mVideoHeight == h) {
                    hasValidSize = true;
                } else {
                    hasValidSize = false;
                }
                if (BaseVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                    if (BaseVideoView.this.mSeekWhenPrepared != 0) {
                        BaseVideoView.this.seekTo(BaseVideoView.this.mSeekWhenPrepared);
                    }
                    BaseVideoView.this.start();
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                BaseVideoView.this.mSurfaceHolder = holder;
                BaseVideoView.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                BaseVideoView.this.mSurfaceHolder = null;
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.hide();
                }
                BaseVideoView.this.release(true);
            }
        };
        this.mAudioFocusListener = new OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                Log.i(BaseVideoView.this.TAG, "onAudioFocusChange() focusChange: " + focusChange);
                switch (focusChange) {
                    case OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY:
                    case ListPopupWindow.WRAP_CONTENT:
                    case STATE_ERROR:
                        Log.d(BaseVideoView.this.TAG, "AudioFocus: received AUDIOFOCUS_LOSS ");
                        if (BaseVideoView.this.mMediaPlayerAudio.isPlaying()) {
                            Log.d(BaseVideoView.this.TAG, "mMediaPlayerAudio.stop();");
                            BaseVideoView.this.mMediaPlayerAudio.stop();
                        }
                    case STATE_PREPARING:
                        Log.d(BaseVideoView.this.TAG, "AudioFocus: received AUDIOFOCUS_GAIN ");
                    default:
                        Log.e(BaseVideoView.this.TAG, "Unknown audio focus change code");
                }
            }
        };
        initVideoView(context);
    }

    public BaseVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initVideoView(context);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.TAG = "VideoView";
        this.mAudioId = -1;
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mMediaPlayerAudio = null;
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                BaseVideoView.this.mVideoWidth = mp.getVideoWidth();
                BaseVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BaseVideoView.this.mVideoWidth != 0 && BaseVideoView.this.mVideoHeight != 0) {
                    BaseVideoView.this.getHolder().setFixedSize(BaseVideoView.this.mVideoWidth, BaseVideoView.this.mVideoHeight);
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                BaseVideoView.this.mCurrentState = STATE_PREPARED;
                BaseVideoView.this.mCanPause = BaseVideoView.this.mCanSeekBack = BaseVideoView.this.mCanSeekForward = true;
                if (BaseVideoView.this.mOnPreparedListener != null) {
                    BaseVideoView.this.mOnPreparedListener.onPrepared(BaseVideoView.this.mMediaPlayer);
                }
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.setEnabled(true);
                }
                BaseVideoView.this.mVideoWidth = mp.getVideoWidth();
                BaseVideoView.this.mVideoHeight = mp.getVideoHeight();
                int seekToPosition = BaseVideoView.this.mSeekWhenPrepared;
                if (seekToPosition != 0) {
                    BaseVideoView.this.seekTo(seekToPosition);
                }
                if (BaseVideoView.this.mVideoWidth != 0 && BaseVideoView.this.mVideoHeight != 0) {
                    BaseVideoView.this.getHolder().setFixedSize(BaseVideoView.this.mVideoWidth, BaseVideoView.this.mVideoHeight);
                    if (BaseVideoView.this.mSurfaceWidth != BaseVideoView.this.mVideoWidth || BaseVideoView.this.mSurfaceHeight != BaseVideoView.this.mVideoHeight) {
                        return;
                    }
                    if (BaseVideoView.this.mTargetState == 3) {
                        BaseVideoView.this.start();
                        if (BaseVideoView.this.mMediaController != null) {
                            BaseVideoView.this.mMediaController.show();
                        }
                    } else if (!BaseVideoView.this.isPlaying()) {
                        if ((seekToPosition != 0 || BaseVideoView.this.getCurrentPosition() > 0) && BaseVideoView.this.mMediaController != null) {
                            BaseVideoView.this.mMediaController.show(STATE_IDLE);
                        }
                    }
                } else if (BaseVideoView.this.mTargetState == 3) {
                    BaseVideoView.this.start();
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                BaseVideoView.this.mCurrentState = STATE_PLAYBACK_COMPLETED;
                BaseVideoView.this.mTargetState = STATE_PLAYBACK_COMPLETED;
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.hide();
                }
                if (BaseVideoView.this.mOnCompletionListener != null) {
                    BaseVideoView.this.mOnCompletionListener.onCompletion(BaseVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                Log.d(BaseVideoView.this.TAG, "Error: " + framework_err + "," + impl_err);
                BaseVideoView.this.mCurrentState = STATE_ERROR;
                BaseVideoView.this.mTargetState = STATE_ERROR;
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.hide();
                }
                if ((BaseVideoView.this.mOnErrorListener == null || !BaseVideoView.this.mOnErrorListener.onError(BaseVideoView.this.mMediaPlayer, framework_err, impl_err)) && BaseVideoView.this.getWindowToken() != null) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                BaseVideoView.this.mCurrentBufferPercentage = percent;
            }
        };
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                BaseVideoView.this.mSurfaceWidth = w;
                BaseVideoView.this.mSurfaceHeight = h;
                boolean isValidState;
                if (BaseVideoView.this.mTargetState == 3) {
                    isValidState = true;
                } else {
                    isValidState = false;
                }
                boolean hasValidSize;
                if (BaseVideoView.this.mVideoWidth == w && BaseVideoView.this.mVideoHeight == h) {
                    hasValidSize = true;
                } else {
                    hasValidSize = false;
                }
                if (BaseVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                    if (BaseVideoView.this.mSeekWhenPrepared != 0) {
                        BaseVideoView.this.seekTo(BaseVideoView.this.mSeekWhenPrepared);
                    }
                    BaseVideoView.this.start();
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                BaseVideoView.this.mSurfaceHolder = holder;
                BaseVideoView.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                BaseVideoView.this.mSurfaceHolder = null;
                if (BaseVideoView.this.mMediaController != null) {
                    BaseVideoView.this.mMediaController.hide();
                }
                BaseVideoView.this.release(true);
            }
        };
        this.mAudioFocusListener = new OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                Log.i(BaseVideoView.this.TAG, "onAudioFocusChange() focusChange: " + focusChange);
                switch (focusChange) {
                    case OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY:
                    case ListPopupWindow.WRAP_CONTENT:
                    case STATE_ERROR:
                        Log.d(BaseVideoView.this.TAG, "AudioFocus: received AUDIOFOCUS_LOSS ");
                        if (BaseVideoView.this.mMediaPlayerAudio.isPlaying()) {
                            Log.d(BaseVideoView.this.TAG, "mMediaPlayerAudio.stop();");
                            BaseVideoView.this.mMediaPlayerAudio.stop();
                        }
                    case STATE_PREPARING:
                        Log.d(BaseVideoView.this.TAG, "AudioFocus: received AUDIOFOCUS_GAIN ");
                    default:
                        Log.e(BaseVideoView.this.TAG, "Unknown audio focus change code");
                }
            }
        };
        initVideoView(context);
    }

    public int getAudioSessionId() {
        return STATE_ERROR;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(this.mVideoWidth, widthMeasureSpec), getDefaultSize(this.mVideoHeight, heightMeasureSpec));
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case CitySearchProvider.GET_SEARCH_RESULT_FAIL:
                return Math.min(desiredSize, specSize);
            case STATE_IDLE:
                return desiredSize;
            case CitySearchProvider.GET_SEARCH_RESULT_SUCC:
                return specSize;
            default:
                return result;
        }
    }

    private void initVideoView(Context context) {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(STATE_PLAYING);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    public void setAudioId(int audioId) {
        this.mAudioId = audioId;
    }

    private void setDataSourceFromResource(Resources resources, MediaPlayer player, int res) throws IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);
        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        }
    }

    public void setVideoURI(Uri uri, Map<String, String> headers) {
        this.mUri = uri;
        this.mHeaders = headers;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
            abandonAudioFocus();
            this.mMediaPlayerAudio.stop();
            this.mMediaPlayerAudio.release();
            this.mMediaPlayerAudio = null;
        }
    }

    private void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            release(false);
            try {
                this.mMediaPlayer = new MediaPlayer();
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mDuration = -1;
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                this.mCurrentBufferPercentage = 0;
                this.mMediaPlayer.setDataSource(getContext(), this.mUri, this.mHeaders);
                this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
                this.mMediaPlayer.setAudioStreamType(STATE_PREPARING);
                this.mMediaPlayer.setScreenOnWhilePlaying(false);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
                attachMediaController();
                this.mMediaPlayerAudio = new MediaPlayer();
                this.mMediaPlayerAudio.setAudioStreamType(STATE_PREPARING);
                if (this.mAudioId > 0) {
                    setDataSourceFromResource(this.mContext.getResources(), this.mMediaPlayerAudio, this.mAudioId);
                    this.mMediaPlayerAudio.prepare();
                }
            } catch (IOException ex) {
                Log.w(this.TAG, "Unable to open content: " + this.mUri, ex);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, STATE_PREPARING, STATE_IDLE);
            } catch (IllegalArgumentException ex2) {
                Log.w(this.TAG, "Unable to open content: " + this.mUri, ex2);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, STATE_PREPARING, STATE_IDLE);
            }
        }
    }

    public void setMediaController(MediaController controller) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            View anchorView;
            this.mMediaController.setMediaPlayer(this);
            if (getParent() instanceof View) {
                anchorView = (View) getParent();
            } else {
                anchorView = this;
            }
            this.mMediaController.setAnchorView(anchorView);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (cleartargetstate) {
                this.mTargetState = 0;
            }
            abandonAudioFocus();
            this.mMediaPlayerAudio.reset();
            this.mMediaPlayerAudio.release();
            this.mMediaPlayerAudio = null;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
        if (!isInPlaybackState() || !isKeyCodeSupported || this.mMediaController == null || (keyCode == 79 || keyCode == 85)) {
            if (this.mMediaPlayer.isPlaying()) {
                pause();
                this.mMediaController.show();
                return true;
            }
            start();
            this.mMediaController.hide();
            return true;
        } else if (keyCode == 126) {
            if (this.mMediaPlayer.isPlaying()) {
                return true;
            }
            start();
            this.mMediaController.hide();
            return true;
        } else if (keyCode != 86 && keyCode != 127) {
            toggleMediaControlsVisiblity();
            return super.onKeyDown(keyCode, event);
        } else if (!this.mMediaPlayer.isPlaying()) {
            return true;
        } else {
            pause();
            this.mMediaController.show();
            return true;
        }
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void playWeatherSound() {
        this.mIsPlaySound = false;
        boolean isMusicOn = this.mAudioManager.isMusicActive();
        Log.d(this.TAG, "playWeatherSound() --- mIsPlaySound = " + this.mIsPlaySound + ", isMusicOn = " + isMusicOn);
        KeyguardManager km = (KeyguardManager) this.mContext.getSystemService("keyguard");
        if (this.mIsPlaySound && !isMusicOn && this.mMediaPlayerAudio != null && !km.inKeyguardRestrictedInputMode()) {
            requestAudioFocus();
            this.mMediaPlayerAudio.setVolume(FogParticle.SPEED_UNIT, FogParticle.SPEED_UNIT);
            this.mMediaPlayerAudio.start();
        }
    }

    public void stopPlaySound() {
        if (this.mMediaPlayerAudio != null && this.mMediaPlayerAudio.isPlaying()) {
            this.mMediaPlayerAudio.stop();
        }
    }

    private void requestAudioFocus() {
        Log.i(this.TAG, "requestAudioFocus().");
        if (this.mAudioManager != null) {
            this.mAudioManager.requestAudioFocus(this.mAudioFocusListener, DetectedActivity.WALKING, STATE_PREPARED);
        }
    }

    public void abandonAudioFocus() {
        Log.i(this.TAG, "abandonAudioFocus().");
        if (this.mAudioManager != null) {
            this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
        }
    }

    public void start() {
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
            this.mMediaPlayerAudio.pause();
        }
        this.mTargetState = 4;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openVideo();
    }

    public int getDuration() {
        if (!isInPlaybackState()) {
            this.mDuration = -1;
            return this.mDuration;
        } else if (this.mDuration > 0) {
            return this.mDuration;
        } else {
            this.mDuration = this.mMediaPlayer.getDuration();
            return this.mDuration;
        }
    }

    public int getCurrentPosition() {
        return isInPlaybackState() ? this.mMediaPlayer.getCurrentPosition() : STATE_IDLE;
    }

    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = 0;
            return;
        }
        this.mSeekWhenPrepared = msec;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return this.mMediaPlayer != null ? this.mCurrentBufferPercentage : STATE_IDLE;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    public boolean canPause() {
        return this.mCanPause;
    }

    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }
}
