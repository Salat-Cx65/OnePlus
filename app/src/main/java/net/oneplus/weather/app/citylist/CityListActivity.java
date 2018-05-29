package net.oneplus.weather.app.citylist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.google.android.gms.location.DetectedActivity;

import net.oneplus.weather.app.BaseBarActivity;
import net.oneplus.weather.app.MainActivity;
import net.oneplus.weather.app.citylist.CityListAdapter.OnDefaulatChangeListener;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.db.CityWeatherDBHelper.WeatherEntry;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.util.AlertUtils;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.util.NumberUtils;
import net.oneplus.weather.util.PermissionUtil;
import net.oneplus.weather.util.PreferenceUtils;
import net.oneplus.weather.widget.swipelistview.BaseSwipeListViewListener;
import net.oneplus.weather.widget.swipelistview.SwipeListView;
import net.oneplus.weather.widget.widget.WidgetHelper;

public class CityListActivity extends BaseBarActivity implements OnDefaulatChangeListener {
    public static final String DEFAULT_CITY = "default_city";
    public static final String INTENT_SEARCH_CITY = "search_city";
    private static final String TAG;
    private SwipeListView cityListView;
    private int mAppWidgetId;
    private CityListAdapter mCityListAdapter;
    private CityListHandler mCityListHandler;
    private CityWeatherDB mCityWeatherDB;
    private Cursor mCursor;

    class AnonymousClass_4 extends AsyncTask<Void, Void, Void> {
        final /* synthetic */ int val$postion;

        AnonymousClass_4(int i) {
            this.val$postion = i;
        }

        protected Void doInBackground(Void... voids) {
            CityListActivity.this.mCityWeatherDB.changeDefaultCity(this.val$postion);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent();
            intent.putExtra(GlobalConfig.INTENT_EXTRA_CITY_INDEX, 0);
            CityListActivity.this.setResult(-1, intent);
            Toast.makeText(CityListActivity.this, R.string.default_city_changed, 0).show();
            CityListActivity.this.finish();
        }
    }

    public CityListActivity() {
        this.mAppWidgetId = -1;
    }

    static {
        TAG = CityListSearchAdapter.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citylist_activity);
        init();
    }

    private void init() {
        initData();
        initUIView();
        initWidgetData();
    }

    private void initWidgetData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && this.mCursor != null) {
            this.mCityListAdapter.setWidgeMode(true);
            this.cityListView.setSwipeMode(0);
            this.mAppWidgetId = extras.getInt("appWidgetId", 0);
        }
    }

    private void initData() {
        PermissionUtil.requestPermission((Activity) this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION"}, (int) PermissionUtil.ALL_PERMISSION_REQUEST);
        this.mCityWeatherDB = CityWeatherDB.getInstance(getApplicationContext());
        this.mCursor = this.mCityWeatherDB.getAllCities();
        this.mCityListAdapter = new CityListAdapter(getApplicationContext(), this.mCursor, false);
        this.mCityListAdapter.setOnDefaulatChangeListener(this);
        HandlerThread handlerThread = new HandlerThread("handler_hread");
        handlerThread.start();
        this.mCityListHandler = new CityListHandler(handlerThread.getLooper(), this);
        if (this.mCursor.getCount() == 0) {
            findViewById(R.id.no_city_view).setVisibility(0);
        }
    }

    private void initUIView() {
        setBarTitle(R.string.city_list_select_city);
        this.cityListView = (SwipeListView) findViewById(R.id.cityListView);
        this.cityListView.setAdapter(this.mCityListAdapter);
        this.cityListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            public void onClickFrontView(int position) {
                super.onClickFrontView(position);
                Cursor cursor = CityListActivity.this.mCityWeatherDB.getAllCities();
                Intent intent = new Intent(CityListActivity.this.getApplicationContext(), MainActivity.class);
                if (!(CityListActivity.this.mAppWidgetId == -1 || cursor == null || cursor.getCount() <= position)) {
                    if (cursor.moveToPosition(position)) {
                        int locationId = cursor.getInt(cursor.getColumnIndex(WeatherEntry.COLUMN_1_LOCATION_ID));
                        PreferenceUtils.commitInt(CityListActivity.this, WidgetHelper.WIDGET_ID_PREFIX + CityListActivity.this.mAppWidgetId, locationId);
                        PreferenceUtils.commitInt(CityListActivity.this, WidgetHelper.WIDGET_ID_PREFIX + String.valueOf(locationId), CityListActivity.this.mAppWidgetId);
                        CityData data = CityListActivity.this.mCityWeatherDB.getCityFromLocationId(locationId);
                        if (data != null) {
                            WidgetHelper.getInstance(CityListActivity.this).setCityByID(CityListActivity.this, data);
                            WidgetHelper.getInstance(CityListActivity.this).updateWidgetById(CityListActivity.this.mAppWidgetId, true);
                            intent.putExtra("appWidgetId", CityListActivity.this.mAppWidgetId);
                        } else {
                            return;
                        }
                    }
                    return;
                }
                intent.putExtra(GlobalConfig.INTENT_EXTRA_CITY_INDEX, position);
                intent.setAction("android.intent.action.MAIN");
                CityListActivity.this.setResult(-1, intent);
                CityListActivity.this.finish();
                CityListActivity.this.overridePendingTransition(R.anim.alpha_in_listclick, R.anim.alpha_out_listclick);
            }

            public void onDismiss(int[] reverseSortedPositions) {
                super.onDismiss(reverseSortedPositions);
                long[] preDelete = new long[reverseSortedPositions.length];
                for (int i = 0; i < reverseSortedPositions.length; i++) {
                    Cursor cursor = (Cursor) CityListActivity.this.mCityListAdapter.getItem(reverseSortedPositions[i]);
                    if (cursor != null) {
                        preDelete[i] = cursor.getLong(cursor.getColumnIndex("_id"));
                    }
                }
                for (long delId : preDelete) {
                    if (0 != delId) {
                        CityListActivity.this.mCityListAdapter.delete(delId);
                        Message msg = new Message();
                        msg.what = NumberUtils.parseInt(delId);
                        msg.obj = Long.valueOf(delId);
                        CityListActivity.this.mCityListHandler.sendMessage(msg);
                    }
                }
            }
        });
        this.cityListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(GlobalConfig.INTENT_EXTRA_CITY_INDEX, position);
                CityListActivity.this.setResult(-1, intent);
                CityListActivity.this.finish();
                CityListActivity.this.overridePendingTransition(R.anim.alpha_in_listclick, R.anim.alpha_out_listclick);
            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CityListActivity.this, CitySearchActivity.class);
                intent.putExtra(INTENT_SEARCH_CITY, CityListActivity.this.cityListView.getCount());
                CityListActivity.this.startActivityForResult(intent, 1);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        if (this.mAppWidgetId == -1) {
            setResult(-1);
        }
        finish();
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data.getBooleanExtra(CitySearchActivity.INTENT_RESULT_SEARCH_CITY, false)) {
            findViewById(R.id.no_city_view).setVisibility(DetectedActivity.RUNNING);
            this.mCityListAdapter.setCanScroll(true);
            this.cityListView.smoothScrollToPosition(this.cityListView.getCount());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 202 && !PermissionUtil.hasGrantedPermissions(this, permissions)) {
            AlertUtils.showNonePermissionDialog(this);
        }
    }

    public void onBackPressed() {
        if (this.mAppWidgetId == -1) {
            setResult(-1);
        }
        finish();
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        if (this.mCityListAdapter != null) {
            this.mCityListAdapter.onDestroy();
        }
        if (this.mCursor != null) {
            this.mCursor.close();
            this.mCursor = null;
        }
        super.onDestroy();
    }

    private void startActivityByTransition(View view, Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.alpha_in_listclick, R.anim.alpha_out_listclick);
    }

    public void onChanged(int postion) {
        new AnonymousClass_4(postion).execute(new Void[0]);
    }
}
