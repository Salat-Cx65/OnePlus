package net.oneplus.weather.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.anim.WeatherPathAnim;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WeatherPathMenu extends RelativeLayout {
    private int DURATION;
    private boolean isOpen;
    private ImageView mCityImage;
    private ViewGroup mContainer;
    private Context mContext;
    private ImageView mMenuImage;
    private View mOutsideArea;
    private WeatherPathAnim mPathAnim;
    private ImageView mSettingImage;
    private ImageView mShareImage;
    private View mView;
    private int mViewHeight;

    class AnonymousClass_3 implements OnClickListener {
        final /* synthetic */ View val$childView;
        final /* synthetic */ OnClickListener val$l;

        AnonymousClass_3(OnClickListener onClickListener, View view) {
            this.val$l = onClickListener;
            this.val$childView = view;
        }

        public void onClick(View view) {
            WeatherPathMenu.this.close(new onClickAnimationListener(this.val$l, this.val$childView));
        }
    }

    static class onClickAnimationListener implements AnimatorListener {
        private static int COUNT;
        private OnClickListener mListener;
        private View mView;

        static {
            COUNT = 0;
        }

        public onClickAnimationListener(OnClickListener l, View view) {
            this.mListener = l;
            this.mView = view;
            COUNT = 0;
        }

        public void onAnimationCancel(Animator arg0) {
        }

        public void onAnimationEnd(Animator arg0) {
            COUNT++;
            if (COUNT == 3 && this.mListener != null) {
                this.mListener.onClick(this.mView);
            }
        }

        public void onAnimationRepeat(Animator arg0) {
        }

        public void onAnimationStart(Animator arg0) {
        }
    }

    public WeatherPathMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.DURATION = 300;
        this.isOpen = false;
        this.mContext = context;
        init(context, attrs);
    }

    public WeatherPathMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.DURATION = 300;
        this.isOpen = false;
        this.mContext = context;
        init(context, attrs);
    }

    public WeatherPathMenu(Context context) {
        super(context);
        this.DURATION = 300;
        this.isOpen = false;
        this.mContext = context;
        init(context, null);
    }

    public void init(Context context, AttributeSet attrs) {
        this.mView = LayoutInflater.from(context).inflate(R.layout.weather_menu_layout, this);
        this.mOutsideArea = this.mView.findViewById(R.id.menu_outside_area);
        this.mContainer = (ViewGroup) this.mView.findViewById(R.id.menu_container);
        this.mMenuImage = (ImageView) this.mView.findViewById(R.id.menu);
        this.mShareImage = (ImageView) this.mView.findViewById(R.id.menu_share);
        this.mCityImage = (ImageView) this.mView.findViewById(R.id.menu_city);
        this.mSettingImage = (ImageView) this.mView.findViewById(R.id.menu_setting);
        for (int i = 1; i < this.mContainer.getChildCount(); i++) {
            View inoutimagebutton = this.mContainer.getChildAt(i);
            this.mViewHeight += inoutimagebutton.getHeight();
            inoutimagebutton.setVisibility(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }
        this.mPathAnim = new WeatherPathAnim(this.mContainer, 4);
        this.mPathAnim.startAnimationsClose(this.DURATION, null);
        this.mOutsideArea.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (WeatherPathMenu.this.isOpen) {
                    WeatherPathMenu.this.close(null);
                }
                return false;
            }
        });
        this.mMenuImage.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (WeatherPathMenu.this.isOpen) {
                    WeatherPathMenu.this.close(null);
                } else {
                    WeatherPathMenu.this.open();
                }
            }
        });
    }

    public void setMenuIcon(int resId) {
        this.mMenuImage.setImageResource(resId);
    }

    public void setButtonsOnClickListener(OnClickListener l) {
        if (this.mContainer != null) {
            for (int i = 1; i < this.mContainer.getChildCount(); i++) {
                View childView = this.mContainer.getChildAt(i);
                if (childView != null) {
                    childView.setOnClickListener(new AnonymousClass_3(l, childView));
                }
            }
        }
    }

    public void close(AnimatorListener l) {
        this.mPathAnim.startAnimationsClose(this.DURATION, l);
        this.isOpen = false;
    }

    public void open() {
        this.mPathAnim.startAnimationsOpen(this.DURATION);
        this.isOpen = true;
    }

    public boolean isOpened() {
        return this.isOpen;
    }
}
