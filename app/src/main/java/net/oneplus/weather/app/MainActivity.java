package net.oneplus.weather.app;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.oneplus.lib.app.OPAlertDialog.Builder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import net.oneplus.weather.api.nodes.Alarm;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.app.ContentWrapper.OnUIChangedListener;
import net.oneplus.weather.app.citylist.CityListActivity;
import net.oneplus.weather.db.ChinaCityDB;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.db.CityWeatherDB.CityListDBListener;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.receiver.AlarmReceiver;
import net.oneplus.weather.util.AlertUtils;
import net.oneplus.weather.util.BitmapUtils;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.util.MediaUtil;
import net.oneplus.weather.util.NetUtil;
import net.oneplus.weather.util.OrientationSensorUtil;
import net.oneplus.weather.util.PermissionUtil;
import net.oneplus.weather.util.PreferenceUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.SystemSetting.OnDataChangeListener;
import net.oneplus.weather.util.TemperatureUtil;
import net.oneplus.weather.util.UIUtil;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherResHelper;
import net.oneplus.weather.util.WeatherViewCreator;
import net.oneplus.weather.widget.AbsWeather;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.widget.WidgetHelper;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";
    private static final String ACTION_TIME_CHANGED = "android.intent.action.TIME_SET";
    private static final String ACTION_TIME_TICK = "android.intent.action.TIME_TICK";
    public static boolean MOCK_TEST_FLAG = false;
    private static final String TAG;
    private static final int UPDATE_UNIT = 88;
    private int MOCK_BUTTON_ENALBE_CONDITION;
    public int currentPositon;
    private int currentWeatherId;
    private AbsWeather currentWeatherView;
    private float currentWeatherViewAlpha;
    private ViewGroup mBackground;
    private boolean mCityChanged;
    private final CityListDBListener mCityListDBListener;
    private CityWeatherDB mCityWeatherDB;
    private View mDecorView;
    private final Handler mHandler;
    private int mLastHour;
    private int mLastIndex;
    private boolean mLastIsDay;
    private MainPagerAdapter mMainPagerAdapter;
    private int mMockButtonClickCount;
    private boolean mNeedUpdateUnit;
    private BroadcastReceiver mReceiver;
    private BroadcastReceiver mTimeChangeReceiver;
    private Toolbar mToolbar;
    private ImageView mToolbar_gps;
    private TextView mToolbar_subtitle;
    private TextView mToolbar_title;
    private ViewPager mViewPager;
    private final CopyOnWriteArrayList<OnViewPagerScrollListener> mViewPagerListener;
    private int nextPositon;
    private AbsWeather nextWeatherView;
    private Dialog noConnectionDialog;
    private boolean sameWeatherView;

    class AnonymousClass_11 implements OnMenuItemClickListener {
        final /* synthetic */ ArrayList val$alarms;
        final /* synthetic */ CityData val$data;

        AnonymousClass_11(ArrayList arrayList, CityData cityData) {
            this.val$alarms = arrayList;
            this.val$data = cityData;
        }

        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(MainActivity.this, WeatherWarningActivity.class);
            intent.putParcelableArrayListExtra(WeatherWarningActivity.INTENT_PARA_WARNING, this.val$alarms);
            intent.putExtra(WeatherWarningActivity.INTENT_PARA_CITY, this.val$data.getLocalName());
            MainActivity.this.startActivity(intent);
            return true;
        }
    }

    public static interface OnViewPagerScrollListener {
        void onScrolled(float f, int i);

        void onSelected(int i);
    }

    private class SavePic extends AsyncTask<String, Void, String> {
        private SavePic() {
        }

        protected String doInBackground(String... params) {
            if (!TextUtils.isEmpty(params[0])) {
                BitmapUtils.savePic(BitmapUtils.compressImage(MainActivity.this.getShareImage()), params[0]);
            }
            return params[0];
        }

        protected void onPostExecute(String path) {
            if (TextUtils.isEmpty(path)) {
                Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.no_weather_data), 0).show();
                return;
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.SUBJECT", MainActivity.this.getString(R.string.share_subject));
            intent.setFlags(268435456);
            intent.putExtra("android.intent.extra.STREAM", MediaUtil.getInstace().getImageContentUri(MainActivity.this, new File(path)));
            MainActivity.this.startActivity(Intent.createChooser(intent, MainActivity.this.getString(R.string.share_title)));
        }
    }

    public MainActivity() {
        this.MOCK_BUTTON_ENALBE_CONDITION = 20;
        this.mMockButtonClickCount = 0;
        this.mViewPager = null;
        this.currentWeatherId = 0;
        this.currentPositon = -1;
        this.nextPositon = -1;
        this.mViewPagerListener = new CopyOnWriteArrayList();
        this.mLastIndex = 0;
        this.mNeedUpdateUnit = false;
        this.mCityChanged = false;
        this.mLastHour = -1;
        this.mLastIsDay = true;
        this.currentWeatherViewAlpha = 1.0f;
        this.mCityListDBListener = new CityListDBListener() {
            public void onCityAdded(long newId) {
                if (newId != 0) {
                    MainActivity.this.mCityChanged = true;
                }
            }

            public void onCityDeleted(long deletedId) {
                if (deletedId != 0) {
                    MainActivity.this.mCityChanged = true;
                }
            }

            public void onCityUpdated(long recordId) {
                if (recordId != 0) {
                    MainActivity.this.mCityChanged = true;
                }
            }
        };
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    NetworkInfo info = ((ConnectivityManager) MainActivity.this.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
                    if (info != null && info.isAvailable()) {
                        if (!(MainActivity.this.mMainPagerAdapter == null || MainActivity.this.mViewPager == null)) {
                            MainActivity.this.mMainPagerAdapter.loadWeather(MainActivity.this.mViewPager.getCurrentItem());
                        }
                        if (MainActivity.this.noConnectionDialog != null && MainActivity.this.noConnectionDialog.isShowing()) {
                            try {
                                MainActivity.this.noConnectionDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_UNIT:
                        MainActivity.this.refreshViewPagerChild();
                    default:
                        break;
                }
            }
        };
        this.mTimeChangeReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_DATE_CHANGED.equals(action)) {
                }
                if (ACTION_TIME_CHANGED.equals(action)) {
                    System.out.println("ACTION_TIME_CHANGED");
                }
                if (ACTION_TIME_TICK.equals(action)) {
                    int currentHour = DateTimeUtils.longTimeToHour(System.currentTimeMillis(), MainActivity.this.mMainPagerAdapter.getCityAtPosition(MainActivity.this.currentPositon).getWeathers().getCurrentWeather().getLocalTimeZone());
                    if (currentHour != MainActivity.this.mLastHour) {
                        MainActivity.this.refreshViewPagerChild();
                        MainActivity.this.mLastHour = currentHour;
                    }
                }
                if (MainActivity.this.currentPositon != -1) {
                    boolean isday = MainActivity.this.isDay(MainActivity.this.currentPositon);
                    if (MainActivity.this.mLastIsDay != isday) {
                        MainActivity.this.updateBackground(MainActivity.this.currentPositon, false, true);
                        if (MainActivity.this.mMainPagerAdapter != null) {
                            ContentWrapper cw = MainActivity.this.mMainPagerAdapter.getContentWrap(MainActivity.this.currentPositon);
                            if (cw != null) {
                                cw.updateCurrentWeatherUI();
                                MainActivity.this.mLastIsDay = isday;
                            }
                        }
                    }
                }
            }
        };
    }

    static /* synthetic */ int access$2208(MainActivity x0) {
        int i = x0.mMockButtonClickCount;
        x0.mMockButtonClickCount = i + 1;
        return i;
    }

    static {
        TAG = MainActivity.class.getSimpleName();
        MOCK_TEST_FLAG = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtil.setWindowStyle(this);
        setContentView(R.layout.main_activity);
        setupActionBar();
        this.mDecorView = getWindow().getDecorView();
        ChinaCityDB.openCityDB(this);
        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        registerReceiver();
        init3DView();
        getWindow().setBackgroundDrawable(null);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.mCityWeatherDB = CityWeatherDB.getInstance(MainActivity.this);
                MainActivity.this.initViewPager();
                MainActivity.this.addOnSettingChangeListener();
                MainActivity.this.addCityWeatherDBListener();
                MainActivity.this.mViewPager.setOffscreenPageLimit(DetectedActivity.RUNNING);
                if (!MainActivity.this.isNetworkConnected()) {
                    MainActivity.this.noConnectionDialog = AlertUtils.showNoConnectionDialog(MainActivity.this);
                }
            }
        }, 70);
        AlarmReceiver.setAlarmClock(getApplicationContext());
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                boolean isGranted = true;
                if (grantResults.length > 0) {
                    int length = grantResults.length;
                    for (int i = 0; i < length; i++) {
                        if (Integer.valueOf(grantResults[i]).intValue() != 0) {
                            isGranted = false;
                            if (isGranted) {
                                new Builder(this).setMessage((int) R.string.dialog_necessary_permissions).setPositiveButton((int) R.string.dialog_go_to_settings, new OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                                        intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                                        MainActivity.this.startActivity(intent);
                                    }
                                }).setNegativeButton((int) R.string.dialog_exit, new OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        MainActivity.this.finish();
                                    }
                                }).create().show();
                                return;
                            } else if (!permissions[0].equals("android.permission.ACCESS_FINE_LOCATION") && this.mMainPagerAdapter != null) {
                                this.mMainPagerAdapter.loadWeather(0, true);
                                return;
                            } else if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                                shareImageAndText();
                            }
                        }
                    }
                    if (isGranted) {
                        new Builder(this).setMessage((int) R.string.dialog_necessary_permissions).setPositiveButton((int) R.string.dialog_go_to_settings, new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                                MainActivity.this.startActivity(intent);
                            }
                        }).setNegativeButton((int) R.string.dialog_exit, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        }).create().show();
                        return;
                    }
                    if (!permissions[0].equals("android.permission.ACCESS_FINE_LOCATION")) {
                    }
                    if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        shareImageAndText();
                    }
                }
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    Intent intent = data;
                    if (intent != null && intent.getExtras() != null) {
                        int index;
                        Bundle d = intent.getExtras();
                        boolean callByCityListActivity = d.containsKey(GlobalConfig.INTENT_EXTRA_CITY_INDEX);
                        if (callByCityListActivity) {
                            index = d.getInt(GlobalConfig.INTENT_EXTRA_CITY_INDEX);
                        } else {
                            index = this.mLastIndex;
                        }
                        if (this.mCityChanged) {
                            this.mCityChanged = false;
                            this.mMainPagerAdapter.updateCityList(this);
                            this.mMainPagerAdapter.notifyDataSetChanged();
                            updateToolbar(index);
                        }
                        if (callByCityListActivity) {
                            this.nextWeatherView = null;
                            if (this.mViewPager != null) {
                                this.mViewPager.setCurrentItem(index, false);
                            }
                            this.nextPositon = -1;
                            updateBackground(index, true, false);
                            if (this.mMainPagerAdapter != null) {
                                ContentWrapper cw = this.mMainPagerAdapter.getContentWrap(index);
                                if (cw != null) {
                                    cw.resetScrollView();
                                }
                                if (this.currentWeatherView != null) {
                                    this.currentWeatherView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                                }
                            }
                            this.currentWeatherViewAlpha = 1.0f;
                        }
                    } else if (this.mCityChanged) {
                        this.mCityChanged = false;
                        this.mMainPagerAdapter.updateCityList(this);
                        this.mMainPagerAdapter.notifyDataSetChanged();
                    }
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    if (this.mMainPagerAdapter != null) {
                        this.mMainPagerAdapter.getContentWrap(0).updateWeatherInfo(CacheMode.LOAD_NO_CACHE);
                    }
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    if (this.mMainPagerAdapter != null) {
                        this.mMainPagerAdapter.getContentWrap(0).updateWeatherInfo(CacheMode.LOAD_NO_CACHE);
                    }
                default:
                    break;
            }
        }
    }

    private void init3DView() {
        if (this.mBackground == null) {
            this.mBackground = (ViewGroup) findViewById(R.id.current_opweather_background);
        }
        RainSurfaceView child = new RainSurfaceView(this, -1, isDay(this.currentPositon));
        child.stopAnimate();
        child.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mBackground.addView(child);
        child.onPause();
    }

    private void initViewPager() {
        int position = 0;
        int widgetId = getIntent().getIntExtra(WidgetHelper.WIDGET_ID, -1);
        String locationId = String.valueOf(PreferenceUtils.getInt(this, WidgetHelper.WIDGET_ID_PREFIX + widgetId, -1));
        if (widgetId != -1 && !"-1".equals(locationId)) {
            Cursor cursor = this.mCityWeatherDB.getAllCities();
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                while (!cursor.getString(RainSurfaceView.RAIN_LEVEL_RAINSTORM).equals(locationId)) {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                }
                position = cursor.getPosition();
                cursor.close();
            }
        } else if (widgetId != -1 && "-1".equals(locationId)) {
            WidgetHelper.getInstance(this).updateWidgetById(widgetId, false);
        }
        this.mLastIndex = position;
        this.mMainPagerAdapter = new MainPagerAdapter(this, this.mViewPagerListener, this.mToolbar_subtitle);
        this.mMainPagerAdapter.setOnUIChangedListener(new OnUIChangedListener() {
            public void onScrollViewChange(float alpha) {
                MainActivity.this.currentWeatherViewAlpha = alpha;
                MainActivity.this.setCurrentWeatherViewAlpha(alpha);
            }

            public void onChangedCurrentWeather() {
                MainActivity.this.updateBackground(MainActivity.this.mViewPager.getCurrentItem(), true, false);
            }

            public void ChangePathMenuResource(int index, boolean isBlack, boolean isLoading) {
                RootWeather weather = MainActivity.this.mMainPagerAdapter.getWeatherDataAtPosition(index);
                if (index != MainActivity.this.mViewPager.getCurrentItem()) {
                    return;
                }
                CityData cityData;
                if (MainActivity.this.mToolbar.getMenu().findItem(R.id.action_cities) == null || MainActivity.this.mToolbar.getMenu().findItem(R.id.action_warning) == null) {
                    Log.e(TAG, "findItem : is null");
                    cityData = MainActivity.this.mMainPagerAdapter.getCityAtPosition(index);
                    if (cityData != null) {
                        MainActivity.this.mToolbar_title.setText(cityData.getProvider() != -1 ? cityData.getLocalName() : MainActivity.this.getString(R.string.current_location));
                        MainActivity.this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(null, false, MainActivity.this));
                    } else {
                        MainActivity.this.mToolbar_title.setText(R.string.current_location);
                        MainActivity.this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(null, false, MainActivity.this));
                    }
                    MainActivity.this.mToolbar_title.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_primary_dark));
                    MainActivity.this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_secondary_dark));
                } else if (weather == null) {
                    Log.e(TAG, "weather is null");
                    Log.e(TAG, "isLoading is :" + isLoading);
                    if (!isLoading) {
                        boolean z;
                        cityData = MainActivity.this.mMainPagerAdapter.getCityAtPosition(index);
                        String access$1200 = TAG;
                        StringBuilder append = new StringBuilder().append("cityData is :");
                        if (cityData != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                        Log.e(access$1200, append.append(z).toString());
                        if (cityData != null) {
                            MainActivity.this.mToolbar_title.setText(cityData.getProvider() != -1 ? cityData.getLocalName() : MainActivity.this.getString(R.string.current_location));
                            MainActivity.this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(null, false, MainActivity.this));
                        } else {
                            MainActivity.this.mToolbar_title.setText(R.string.current_location);
                            MainActivity.this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(null, false, MainActivity.this));
                        }
                    }
                    MainActivity.this.mToolbar_title.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_primary_dark));
                    MainActivity.this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_secondary_dark));
                    MainActivity.this.showGPSIcon(index, ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_gps), ContextCompat.getDrawable(MainActivity.this, R.drawable.btn_home_enable));
                } else {
                    MainActivity.this.updateWeatherWarning(index);
                    Date date = weather.getDate();
                    if (isBlack) {
                        MainActivity.this.mToolbar.setOverflowIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.more_setting_black));
                        MainActivity.this.mToolbar.getMenu().findItem(R.id.action_cities).setIcon(R.drawable.ic_city_black);
                        MainActivity.this.mToolbar.getMenu().findItem(R.id.action_warning).setIcon(R.drawable.ic_warn_black);
                        if (!isLoading) {
                            MainActivity.this.mToolbar_title.setVisibility(0);
                            MainActivity.this.mToolbar_subtitle.setVisibility(0);
                            MainActivity.this.mToolbar_title.setText(MainActivity.this.mMainPagerAdapter.getCityAtPosition(index).getLocalName());
                            MainActivity.this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(date, weather.getRequestIsSuccess(), MainActivity.this));
                        }
                        MainActivity.this.mToolbar_title.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_primary_light));
                        MainActivity.this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_secondary_light));
                        MainActivity.this.showGPSIcon(index, ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_gps_black), ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_home_black));
                        MainActivity.this.mDecorView.setSystemUiVisibility(9472);
                        return;
                    }
                    MainActivity.this.mToolbar.setOverflowIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.more_setting));
                    MainActivity.this.mToolbar.getMenu().findItem(R.id.action_cities).setIcon(R.drawable.ic_city);
                    MainActivity.this.mToolbar.getMenu().findItem(R.id.action_warning).setIcon(R.drawable.ic_warn);
                    if (!isLoading) {
                        MainActivity.this.mToolbar_title.setText(MainActivity.this.mMainPagerAdapter.getCityAtPosition(index).getLocalName());
                        MainActivity.this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(date, weather.getRequestIsSuccess(), MainActivity.this));
                    }
                    MainActivity.this.mToolbar_title.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_primary_dark));
                    MainActivity.this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.oneplus_contorl_text_color_secondary_dark));
                    MainActivity.this.showGPSIcon(index, ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_gps), ContextCompat.getDrawable(MainActivity.this, R.drawable.btn_home_enable));
                    MainActivity.this.mDecorView.setSystemUiVisibility(1280);
                }
            }

            public void onWeatherDataUpdate(int index) {
                if (index == MainActivity.this.mViewPager.getCurrentItem()) {
                    MainActivity.this.updateBackground(index, true, true);
                }
            }

            public void onError() {
                MainActivity.this.updateBackground(MainActivity.this.mViewPager.getCurrentItem(), false, false);
            }
        });
        this.mViewPager.setAdapter(this.mMainPagerAdapter);
        this.mViewPager.setCurrentItem(position, false);
        this.mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            boolean dragging;

            {
                this.dragging = false;
            }

            public void onPageSelected(int position) {
                MainActivity.this.mMainPagerAdapter.loadWeather(position);
                Iterator it = MainActivity.this.mViewPagerListener.iterator();
                while (it.hasNext()) {
                    ((OnViewPagerScrollListener) it.next()).onSelected(position);
                }
                if (MainActivity.this.mViewPager.getChildCount() > MainActivity.this.mLastIndex) {
                    ContentWrapper cw = MainActivity.this.mMainPagerAdapter.getContentWrap(MainActivity.this.mLastIndex);
                    if (cw != null) {
                        cw.resetScrollView();
                    }
                }
                MainActivity.this.currentWeatherViewAlpha = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
                MainActivity.this.mLastIndex = position;
                MainActivity.this.updateWeatherWarning(position);
                MainActivity.this.updateToolbar(position);
                MainActivity.this.updateBackground(position, true, false);
            }

            public void onPageScrolled(int position, float present, int offset) {
                if (this.dragging) {
                    this.dragging = false;
                    if (position < MainActivity.this.currentPositon) {
                        MainActivity.this.addNextWeatherView(position);
                    } else {
                        MainActivity.this.addNextWeatherView(position + 1);
                    }
                }
                if (position == MainActivity.this.currentPositon) {
                    present = 1.0f - present;
                } else if (position < MainActivity.this.currentPositon) {
                    position++;
                } else if (position > MainActivity.this.currentPositon) {
                    return;
                }
                MainActivity.this.setWeatherViewAlpha(present, position, false);
            }

            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        MainActivity.this.updateBackground(MainActivity.this.mLastIndex, false, false);
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        this.dragging = true;
                    default:
                        break;
                }
            }
        });
    }

    private void addCityWeatherDBListener() {
        this.mCityWeatherDB.addDataChangeListener(this.mCityListDBListener);
    }

    private void removeCityWeatherDBListener() {
        if (this.mCityWeatherDB != null) {
            this.mCityWeatherDB.removeDataChangeListener(this.mCityListDBListener);
            this.mCityWeatherDB.close();
            this.mCityWeatherDB = null;
        }
    }

    private void addOnSettingChangeListener() {
        SystemSetting.addOnDataChangeListener(new OnDataChangeListener() {
            public void onWindChanged(boolean check) {
            }

            public void onTemperatureChanged(boolean check) {
            }

            public void onHumidityChanged(boolean check) {
            }

            public void onUnitChanged(boolean check) {
                MainActivity.this.mNeedUpdateUnit = true;
            }
        });
    }

    private void setupActionBar() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        this.mToolbar_gps = (ImageView) findViewById(R.id.toolbar_gps);
        this.mToolbar_gps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.this.mMockButtonClickCount > MainActivity.this.MOCK_BUTTON_ENALBE_CONDITION) {
                    MainActivity.this.gotoMocLocation();
                } else {
                    MainActivity.access$2208(MainActivity.this);
                }
            }
        });
        this.mToolbar_title = (TextView) findViewById(R.id.toolbar_title);
        this.mToolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
    }

    protected void onStart() {
        super.onStart();
        OrientationSensorUtil.requestSensor(this);
        if (this.currentWeatherView != null) {
            this.currentWeatherView.onViewStart();
        }
        if (this.nextWeatherView != null) {
            this.nextWeatherView.onViewStart();
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    public void onBackPressed() {
        finish();
    }

    protected void onRestart() {
        super.onRestart();
        if (this.mNeedUpdateUnit) {
            this.mHandler.removeMessages(UPDATE_UNIT);
            this.mHandler.sendEmptyMessage(UPDATE_UNIT);
        }
        if (this.mMainPagerAdapter != null) {
            if (PermissionUtil.hasGrantedPermissions(this, "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE")) {
                this.mMainPagerAdapter.loadWeather(this.mViewPager.getCurrentItem(), false);
                refreshViewPagerChild();
            }
        }
        this.mNeedUpdateUnit = false;
    }

    protected void onResume() {
        super.onResume();
        if (this.currentWeatherView != null && (this.currentWeatherView instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.currentWeatherView).onResume();
        }
        if (this.nextWeatherView != null && (this.nextWeatherView instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.nextWeatherView).onResume();
        }
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        stopCurrentWeatherView();
        stopNextWeatherView();
        OrientationSensorUtil.releaseSensor();
        super.onStop();
    }

    protected void onDestroy() {
        removeCityWeatherDBListener();
        SystemSetting.removeAllDataListener();
        unregisterReceiver();
        OrientationSensorUtil.releaseSensor();
        ChinaCityDB.openCityDB(this).close();
        this.mViewPager.clearOnPageChangeListeners();
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cities:
                gotoCityList();
                break;
            case R.id.popup_menu_settings:
                gotoSettings();
                break;
            case R.id.popup_menu_share:
                openShareList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateToolbar(int position) {
        RootWeather weather = this.mMainPagerAdapter.getWeatherDataAtPosition(position);
        if (weather == null) {
            this.mToolbar_title.setTextColor(ContextCompat.getColor(this, R.color.oneplus_contorl_text_color_primary_dark));
            this.mToolbar_title.setText(this.mMainPagerAdapter.getCityAtPosition(position).getLocalName());
            this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(this, R.color.oneplus_contorl_text_color_secondary_light));
            this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(null, false, this));
            showGPSIcon(position, ContextCompat.getDrawable(this, R.drawable.icon_gps_black), ContextCompat.getDrawable(this, R.drawable.ic_home_black));
            return;
        }
        Date date = weather.getDate();
        if (this.mMainPagerAdapter.getWeatherDescriptionId(position) == 1003) {
            this.mToolbar_title.setTextColor(ContextCompat.getColor(this, R.color.oneplus_contorl_text_color_primary_light));
            this.mToolbar_title.setText(this.mMainPagerAdapter.getCityAtPosition(position).getLocalName());
            this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(this, R.color.oneplus_contorl_text_color_secondary_light));
            this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(date, weather.getRequestIsSuccess(), this));
            this.mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.more_setting_black));
            this.mToolbar.getMenu().findItem(R.id.action_cities).setIcon(R.drawable.ic_city_black);
            this.mToolbar.getMenu().findItem(R.id.action_warning).setIcon(R.drawable.ic_warn_black);
            showGPSIcon(position, ContextCompat.getDrawable(this, R.drawable.icon_gps_black), ContextCompat.getDrawable(this, R.drawable.ic_home_black));
            this.mDecorView.setSystemUiVisibility(9472);
            return;
        }
        this.mToolbar_title.setTextColor(ContextCompat.getColor(this, R.color.oneplus_contorl_text_color_primary_dark));
        this.mToolbar_title.setText(this.mMainPagerAdapter.getCityAtPosition(position).getLocalName());
        this.mToolbar_subtitle.setTextColor(ContextCompat.getColor(this, R.color.oneplus_contorl_text_color_secondary_dark));
        this.mToolbar_subtitle.setText(DateTimeUtils.getTimeTitle(date, weather.getRequestIsSuccess(), this));
        this.mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.more_setting));
        this.mToolbar.getMenu().findItem(R.id.action_cities).setIcon(R.drawable.ic_city);
        showGPSIcon(position, ContextCompat.getDrawable(this, R.drawable.icon_gps), ContextCompat.getDrawable(this, R.drawable.btn_home_enable));
        this.mToolbar.getMenu().findItem(R.id.action_warning).setIcon(R.drawable.ic_warn);
        this.mDecorView.setSystemUiVisibility(1280);
    }

    private void updateWeatherWarning(int position) {
        ArrayList<Alarm> alarms;
        MenuItem menuItem = this.mToolbar.getMenu().findItem(R.id.action_warning);
        RootWeather weather = this.mMainPagerAdapter.getWeatherDataAtPosition(position);
        CityData data = this.mMainPagerAdapter.getCityAtPosition(position);
        if (weather == null) {
            alarms = null;
        } else {
            alarms = (ArrayList) weather.getWeatherAlarms();
        }
        if (alarms == null || alarms.size() <= 0 || ((Alarm) alarms.get(0)).getTypeName().equalsIgnoreCase("None") || ((Alarm) alarms.get(0)).getContentText().equalsIgnoreCase("None") || ((Alarm) alarms.get(0)).getTypeName().equalsIgnoreCase("null") || ((Alarm) alarms.get(0)).getContentText().equalsIgnoreCase("null")) {
            menuItem.setVisible(false);
            return;
        }
        menuItem.setVisible(true);
        menuItem.setOnMenuItemClickListener(new AnonymousClass_11(alarms, data));
    }

    private void showGPSIcon(int position, Drawable locationResId, Drawable homeResId) {
        if (this.mMainPagerAdapter.getCityAtPosition(position).isLocatedCity()) {
            this.mToolbar_gps.setVisibility(0);
            this.mToolbar_gps.setImageDrawable(locationResId);
        } else if (this.mMainPagerAdapter.getCityAtPosition(position).isDefault()) {
            this.mToolbar_gps.setVisibility(0);
            this.mToolbar_gps.setImageDrawable(homeResId);
        } else {
            this.mToolbar_gps.setVisibility(DetectedActivity.RUNNING);
        }
    }

    public void gotoMocLocation() {
        startActivityForResult(new Intent(this, MockLocation.class), RainSurfaceView.RAIN_LEVEL_SHOWER);
        overridePendingTransition(R.anim.citylist_translate_up, R.anim.alpha_out);
    }

    public void gotoSettings() {
        startActivity(new Intent(this, SettingPreferenceActivity.class));
    }

    public void gotoCityList() {
        startActivityForResult(new Intent(this, CityListActivity.class), 1);
        overridePendingTransition(R.anim.citylist_translate_up, R.anim.alpha_out);
    }

    public void openShareList() {
        this.mHandler.post(new Runnable() {
            public void run() {
                MainActivity.this.shareImageAndText();
            }
        });
    }

    private String getShareMsg() {
        float f;
        CityData cityDate = this.mMainPagerAdapter.getCityAtPosition(this.mViewPager.getCurrentItem());
        RootWeather weatherData = this.mMainPagerAdapter.getWeatherDataAtPosition(this.mViewPager.getCurrentItem());
        int highTemp = 0;
        int lowTemp = 0;
        String currentWeather = StringUtils.EMPTY_STRING;
        String cityName = cityDate.getLocalName();
        if (weatherData != null) {
            highTemp = weatherData.getTodayHighTemperature();
            lowTemp = weatherData.getTodayLowTemperature();
            currentWeather = weatherData.getCurrentWeatherText(this);
        }
        boolean cOrf = SystemSetting.getTemperature(this);
        String tempUnit = cOrf ? "\u2103" : "\u2109";
        if (cOrf) {
            f = (float) highTemp;
        } else {
            f = SystemSetting.celsiusToFahrenheit((float) highTemp);
        }
        int highUnitTemp = (int) f;
        if (cOrf) {
            f = (float) lowTemp;
        } else {
            f = SystemSetting.celsiusToFahrenheit((float) lowTemp);
        }
        return cityName + "    " + new SimpleDateFormat(getString(2131689709)).format(new Date(System.currentTimeMillis())) + "\n" + currentWeather + "    " + highUnitTemp + tempUnit + " / " + ((int) f) + tempUnit + getString(2131689834);
    }

    public String getShareMsmFirstLineCityName() {
        CityData cityDate = this.mMainPagerAdapter.getCityAtPosition(this.mViewPager.getCurrentItem());
        if (cityDate == null) {
            return StringUtils.EMPTY_STRING;
        }
        return "\u200e" + cityDate.getLocalName();
    }

    public String getShareMsmFirstLineDateAndWeekday() {
        long time = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return "\u200e" + DateTimeUtils.longTimeToMMddTwo(this, time, null) + " " + DateTimeUtils.getDayString(this, c.get(DetectedActivity.WALKING));
    }

    public String getShareMsmSecondCurrentTemp() {
        CityData cityDate = this.mMainPagerAdapter.getCityAtPosition(this.mViewPager.getCurrentItem());
        if (cityDate != null) {
            RootWeather data = cityDate.getWeathers();
            if (data != null) {
                int curTemp = data.getTodayCurrentTemp();
                int todayHighTemperature = data.getTodayHighTemperature();
                int todayLowTemperature = data.getTodayLowTemperature();
                return TemperatureUtil.getCurrentTemperature(this, curTemp);
            }
        }
        return StringUtils.EMPTY_STRING;
    }

    public String getShareMsmThirdWeatherTypeAndTemp() {
        CityData cityDate = this.mMainPagerAdapter.getCityAtPosition(this.mViewPager.getCurrentItem());
        if (cityDate != null) {
            RootWeather data = cityDate.getWeathers();
            if (data != null) {
                String currentTemp = data.getCurrentWeatherText(this);
                int highTemp = data.getTodayHighTemperature();
                int lowTemp = data.getTodayLowTemperature();
                String hTemp = TemperatureUtil.getHighTemperature(this, highTemp);
                return "\u200e" + currentTemp + "  " + hTemp + "/" + TemperatureUtil.getHighTemperature(this, lowTemp) + (SystemSetting.getTemperature(this) ? "C" : "F");
            }
        }
        return StringUtils.EMPTY_STRING;
    }

    private void shareImageAndText() {
        if (PermissionUtil.check(this, "android.permission.WRITE_EXTERNAL_STORAGE", getString(R.string.request_permission_storage), 1) && this.mMainPagerAdapter != null && this.mViewPager != null) {
            CityData cityDate = this.mMainPagerAdapter.getCityAtPosition(this.mViewPager.getCurrentItem());
            if (cityDate != null) {
                String shareIamgePath = BitmapUtils.getPicFileName(cityDate.getLocalName(), this);
                new SavePic().execute(new String[]{shareIamgePath});
                return;
            }
            Toast.makeText(this, getString(R.string.no_weather_data), 0).show();
        }
    }

    public Bitmap getShareImage() {
        RootWeather weatherData = this.mMainPagerAdapter.getWeatherDataAtPosition(this.mViewPager.getCurrentItem());
        int weatherId = GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES;
        if (weatherData != null) {
            weatherId = WeatherResHelper.weatherToResID(this, weatherData.getCurrentWeatherId());
        }
        String cityName = getShareMsmFirstLineCityName();
        String dateAndWeekday = getShareMsmFirstLineDateAndWeekday();
        String currentTemp = getShareMsmSecondCurrentTemp();
        String cWeatherTypeAndTemp = getShareMsmThirdWeatherTypeAndTemp();
        Bitmap canvasBmp = BitmapFactory.decodeResource(getResources(), WeatherResHelper.getWeatherListitemBkgResID(weatherId, isDay(this.mViewPager.getCurrentItem()))).copy(Config.RGB_565, true);
        Canvas cn = new Canvas(canvasBmp);
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setARGB(MotionEventCompat.ACTION_MASK, 0, 0, 0);
        paint.setTextSize(48.0f);
        if (needGrayColor(getWeatherId())) {
            paint.setColor(getResources().getColor(R.color.top_gray));
        } else {
            paint.setColor(-1);
        }
        StaticLayout layout = new StaticLayout(cityName, paint, 720, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        cn.save(R.styleable.OneplusTheme_onePlusTextColor);
        cn.translate(51.0f, 39.0f);
        layout.draw(cn);
        cn.restore();
        paint.setTextSize(36.0f);
        StaticLayout staticLayout = new StaticLayout(dateAndWeekday, paint, 264, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        cn.save(R.styleable.OneplusTheme_onePlusTextColor);
        paint.setTextSize(36.0f);
        cn.translate(768.0f, 45.9f);
        paint.setTextSize((float) UIUtil.dip2px(this, 11.0f));
        staticLayout.draw(cn);
        cn.restore();
        paint.setTextSize(104.0f);
        paint.setColor(-1);
        staticLayout = new StaticLayout(currentTemp, paint, 1032, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        cn.save(R.styleable.OneplusTheme_onePlusTextColor);
        cn.translate(41.1f, 243.90001f);
        staticLayout.draw(cn);
        cn.restore();
        paint.setTextSize(36.0f);
        paint.setColor(-1);
        staticLayout = new StaticLayout(cWeatherTypeAndTemp, paint, 645, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        cn.save(R.styleable.OneplusTheme_onePlusTextColor);
        cn.translate(51.0f, 372.0f);
        staticLayout.draw(cn);
        cn.restore();
        paint.setTextSize(36.0f);
        paint.setColor(-1);
        staticLayout = new StaticLayout(getString(R.string.share_from_oneplus_weather), paint, 336, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        cn.save(R.styleable.OneplusTheme_onePlusTextColor);
        cn.translate(696.9f, 368.09998f);
        staticLayout.draw(cn);
        cn.restore();
        return canvasBmp;
    }

    private boolean isNetworkConnected() {
        return NetUtil.isNetworkAvailable(this);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mReceiver, filter);
    }

    private void unregisterReceiver() {
        if (this.mReceiver != null) {
            unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    protected void setCurrentWeatherViewAlpha(float alpha) {
        if (this.currentWeatherView != null) {
            this.currentWeatherView.setAlpha(alpha);
        }
    }

    protected void setNextWeatherViewAlpha(float alpha) {
        if (this.nextWeatherView != null) {
            this.nextWeatherView.setAlpha(alpha);
        }
    }

    protected void setWeatherViewAlpha(float alpha, int position, boolean refeshAlpha) {
        Iterator it = this.mViewPagerListener.iterator();
        while (it.hasNext()) {
            ((OnViewPagerScrollListener) it.next()).onScrolled(alpha, position);
        }
        if (alpha < 1.0f && ((double) alpha) >= 0.5d) {
            alpha = (2.0f * alpha) - 1.0f;
        } else if (((double) alpha) < 0.5d) {
            alpha = 0.2f;
        }
        float currAlpha = this.currentWeatherViewAlpha * alpha;
        setCurrentWeatherViewAlpha(currAlpha);
        float nextAlpha = (1.0f - alpha) * 2.0f;
        setNextWeatherViewAlpha(nextAlpha);
        if (this.sameWeatherView) {
            setNextWeatherViewAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            if (this.currentWeatherViewAlpha == 1.0f) {
                setCurrentWeatherViewAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            } else if (currAlpha < nextAlpha) {
                setCurrentWeatherViewAlpha(nextAlpha);
            }
        }
        if (refeshAlpha) {
            setWeatherViewAlpha(alpha, position);
        }
    }

    protected void setWeatherViewAlpha(float alpha, int position) {
        RootWeather weatherData = this.mMainPagerAdapter != null ? this.mMainPagerAdapter.getWeatherDataAtPosition(position) : null;
        if (this.mBackground == null) {
            this.mBackground = (ViewGroup) findViewById(R.id.current_opweather_background);
        }
        int weatherId = GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES;
        if (weatherData != null) {
            weatherId = WeatherResHelper.weatherToResID(this, weatherData.getCurrentWeatherId());
        }
        this.mBackground.setBackgroundColor((16777215 & getWeatherColor(this, weatherId, this.currentPositon)) | (((int) (255.0f * alpha)) << 24));
    }

    private void addNextWeatherView(int position) {
        if (this.mMainPagerAdapter != null && this.mBackground != null && this.nextPositon != position && position >= 0 && position < this.mMainPagerAdapter.getCount()) {
            this.nextPositon = position;
            RootWeather weatherData = this.mMainPagerAdapter.getWeatherDataAtPosition(position);
            int weatherId = GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES;
            if (weatherData != null) {
                weatherId = WeatherResHelper.weatherToResID(this, weatherData.getCurrentWeatherId());
            }
            if (this.currentWeatherId == weatherId && this.mLastIsDay == isDay(this.nextPositon)) {
                this.sameWeatherView = true;
            } else {
                this.sameWeatherView = false;
            }
            if (this.nextWeatherView != null) {
                this.mBackground.removeView((View) this.nextWeatherView);
                this.nextWeatherView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                this.nextWeatherView.stopAnimate();
                destoryNextWeatherView();
            }
            this.nextWeatherView = WeatherViewCreator.getViewFromDescription(this, weatherId, isDay(this.nextPositon));
            this.nextWeatherView.startAnimate();
            this.mBackground.addView((View) this.nextWeatherView);
        }
    }

    public void updateBackground(int position, boolean force, boolean dayNightChange) {
        if (this.mMainPagerAdapter != null) {
            if (force) {
                this.currentPositon = -1;
                destoryNextWeatherView();
            }
            if (this.currentPositon != position || dayNightChange) {
                this.currentPositon = position;
                RootWeather weatherData = this.mMainPagerAdapter.getWeatherDataAtPosition(position);
                if (this.mBackground == null) {
                    this.mBackground = (ViewGroup) findViewById(R.id.current_opweather_background);
                }
                int weatherId = -1;
                if (weatherData != null) {
                    weatherId = WeatherResHelper.weatherToResID(this, weatherData.getCurrentWeatherId());
                }
                if (weatherId == -1) {
                    weatherId = WeatherDescription.WEATHER_DESCRIPTION_UNKNOWN;
                }
                if (this.currentWeatherId != weatherId || dayNightChange || this.mLastIsDay != isDay(this.currentPositon)) {
                    this.mLastIsDay = isDay(this.currentPositon);
                    if (this.nextWeatherView != null) {
                        this.mBackground.removeView((View) this.currentWeatherView);
                        if (this.currentWeatherView != null) {
                            this.currentWeatherView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                            this.currentWeatherView.stopAnimate();
                            destoryCurrentWeatherView();
                        }
                        this.currentWeatherView = this.nextWeatherView;
                        this.nextWeatherView = null;
                    } else {
                        if (this.currentWeatherView != null) {
                            this.mBackground.removeView((View) this.currentWeatherView);
                            this.currentWeatherView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                            this.currentWeatherView.stopAnimate();
                            destoryCurrentWeatherView();
                        }
                        this.currentWeatherView = WeatherViewCreator.getViewFromDescription(this, weatherId, isDay(this.currentPositon));
                        this.currentWeatherView.startAnimate();
                        this.currentWeatherView.setAlpha(this.currentWeatherViewAlpha);
                        this.mBackground.setBackgroundColor(getWeatherColor(this, weatherId, this.currentPositon));
                        this.mBackground.addView((View) this.currentWeatherView);
                    }
                    checkBackgroundChild();
                    this.mBackground.setBackgroundColor(getWeatherColor(this, weatherId, this.currentPositon));
                    getWindow().getDecorView().setBackgroundColor(getWeatherColor(this, weatherId, this.currentPositon));
                    this.currentWeatherId = weatherId;
                    return;
                }
                return;
            }
            checkBackgroundChild();
        }
    }

    private void stopNextWeatherView() {
        if (this.nextWeatherView != null) {
            this.nextWeatherView.onViewPause();
        }
        if (this.nextWeatherView != null && (this.nextWeatherView instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.nextWeatherView).onPause();
        }
    }

    private void stopCurrentWeatherView() {
        if (this.currentWeatherView != null) {
            this.currentWeatherView.onViewPause();
        }
        if (this.currentWeatherView != null && (this.currentWeatherView instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.currentWeatherView).onPause();
        }
    }

    private void destoryNextWeatherView() {
        stopNextWeatherView();
        this.nextWeatherView = null;
    }

    private void destoryCurrentWeatherView() {
        stopCurrentWeatherView();
        this.currentWeatherView = null;
    }

    private void checkBackgroundChild() {
        if (this.mBackground != null && this.mBackground.getChildCount() > 1) {
            this.mBackground.removeView((View) this.currentWeatherView);
            for (int i = 0; i < this.mBackground.getChildCount(); i++) {
                View view = this.mBackground.getChildAt(i);
                if (view instanceof GLSurfaceView) {
                    ((GLSurfaceView) view).onPause();
                }
            }
            this.mBackground.removeAllViews();
            if (this.currentWeatherView != null) {
                this.mBackground.addView((View) this.currentWeatherView);
            }
            destoryNextWeatherView();
            this.nextPositon = -1;
        }
    }

    public int getWeatherColor(Context context, int weatherId, int position) {
        return context.getResources().getColor(WeatherResHelper.getWeatherColorStringID(weatherId, isDay(position)));
    }

    public int getWeatherId() {
        RootWeather weatherData = this.mMainPagerAdapter.getWeatherDataAtPosition(this.currentPositon);
        return weatherData != null ? WeatherResHelper.weatherToResID(this, weatherData.getCurrentWeatherId()) : GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES;
    }

    public boolean isDay(int position) {
        boolean isDay = true;
        if (position == -1) {
            return DateTimeUtils.isTimeMillisDayTime(System.currentTimeMillis());
        }
        CityData cityData = this.mMainPagerAdapter.getCityAtPosition(position);
        if (cityData != null) {
            RootWeather weatherData = cityData.getWeathers();
            if (weatherData != null) {
                try {
                    isDay = cityData.isDay(weatherData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return isDay;
    }

    public boolean needGrayColor(int weatherId) {
        CityData cityData = this.mMainPagerAdapter.getCityAtPosition(this.currentPositon);
        RootWeather weatherData = cityData.getWeathers();
        boolean isDay = true;
        if (weatherData != null) {
            try {
                isDay = cityData.isDay(weatherData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return weatherId == 1003 && isDay;
    }

    private void registerTimeChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIME_TICK);
        filter.addAction(ACTION_TIME_CHANGED);
        registerReceiver(this.mTimeChangeReceiver, filter);
    }

    private void unRegisterTimeChangeReceiver() {
        unregisterReceiver(this.mTimeChangeReceiver);
    }

    private void refreshViewPagerChild() {
        int childCount = this.mViewPager.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mViewPager.getChildAt(i).invalidate();
        }
    }

    public void clikUrl(View view) {
        CityData data = this.mMainPagerAdapter.getCityAtPosition(this.currentPositon);
        if (data != null) {
            String idOrkey = data.getLocationId();
            String local = Locale.getDefault().getLanguage();
            boolean isChina = data.getProvider() == 4096;
            RootWeather weather = data.getWeathers();
            if (weather != null && weather.getCurrentWeather() != null) {
                String url;
                switch (view.getId()) {
                    case R.id.aqiView:
                        openBrower(StringUtils.getAqiMobileLink(idOrkey, local), view.getContext());
                    case R.id.click_url_text:
                        if (isChina) {
                            url = StringUtils.getFifteendaysMobileLink(idOrkey, local);
                        } else {
                            url = weather.getFutureLink();
                        }
                        openBrower(url, view.getContext());
                    case R.id.opweather_detail:
                        openBrower(StringUtils.getLifeMobileLink(idOrkey, local), view.getContext());
                    case R.id.opweather_info:
                        if (isChina) {
                            url = StringUtils.getChinaMainMobileLink(idOrkey, local);
                        } else {
                            url = weather.getCurrentWeather().getMainMoblieLink();
                        }
                        openBrower(url, view.getContext());
                    default:
                        break;
                }
            }
        }
    }

    private void openBrower(String url, Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.browser_not_found), 0).show();
            e.printStackTrace();
        }
    }
}
