package net.oneplus.weather.app.citylist;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.oneplus.weather.R;
import net.oneplus.weather.app.BaseActivity;
import net.oneplus.weather.app.ShowWeatherActivity;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.model.CommonCandidateCity;
import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.starwar.StarWarUtils;
import net.oneplus.weather.starwar.VidePlayActivity;
import net.oneplus.weather.util.AlertUtils;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.EncodeUtil;
import net.oneplus.weather.util.GpsUtils;
import net.oneplus.weather.util.NetUtil;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.ClearableEditText;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class CitySearchActivity extends BaseActivity {
    public static final String INTENT_RESULT_SEARCH_CITY = "city_search";
    private static final int SEARCH_PROVIDER_CHINA = 4096;
    private static final int SEARCH_PROVIDER_FOREIGN = 2048;
    private static final String TAG;
    private CityListSearchAdapter adapter;
    private List<CommonCandidateCity> citySearchResult;
    private CitySearchProvider mChinaCitySearProvider;
    private int mCityCount;
    private ClearableEditText mCityKeyword;
    private CityWeatherDB mCityWeatherDB;
    private CitySearchProvider mForeignCitySearProvider;
    private Handler mHandler;
    private TextView mNoSearchView;
    private BroadcastReceiver mReceiver;
    private ListView mSearchListView;
    private Dialog noConnectionDialog;

    public CitySearchActivity() {
        this.citySearchResult = new ArrayList();
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    NetworkInfo info = ((ConnectivityManager) CitySearchActivity.this.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
                    if (info == null || !info.isAvailable()) {
                        CitySearchActivity.this.mNoSearchView.setText(R.string.no_network_statu);
                        CitySearchActivity.this.mNoSearchView.setCompoundDrawablesWithIntrinsicBounds(null, context.getDrawable(R.drawable.no_network), null, null);
                        return;
                    }
                    if (CitySearchActivity.this.noConnectionDialog != null && CitySearchActivity.this.noConnectionDialog.isShowing()) {
                        try {
                            CitySearchActivity.this.noConnectionDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    CitySearchActivity.this.mNoSearchView.setText(R.string.no_search_data);
                    CitySearchActivity.this.mNoSearchView.setCompoundDrawablesWithIntrinsicBounds(null, context.getDrawable(R.drawable.no_search_icon), null, null);
                }
            }
        };
    }

    static {
        TAG = CityListSearchAdapter.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_citylist_activity);
        registerReceiver();
        if (!isNetworkConnected()) {
            this.noConnectionDialog = AlertUtils.showNoConnectionDialog(this);
        }
        init();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mReceiver, filter);
    }

    private void unRegisterReceiver() {
        if (this.mReceiver != null) {
            unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    private boolean isNetworkConnected() {
        return NetUtil.isNetworkAvailable(this);
    }

    private void init() {
        initData();
        initUIView();
    }

    private void initData() {
        this.mCityCount = getIntent().getIntExtra(CityListActivity.INTENT_SEARCH_CITY, 0);
        this.mCityWeatherDB = CityWeatherDB.getInstance(getApplicationContext());
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                String country;
                if (GpsUtils.isH2OS() && msg.what == 4096) {
                    List<CommonCandidateCity> chinaCitySearchResult = CitySearchActivity.this.mChinaCitySearProvider.getCandidateCityList();
                    if (chinaCitySearchResult != null) {
                        Iterator<CommonCandidateCity> chinaCityIterator = chinaCitySearchResult.iterator();
                        while (chinaCityIterator.hasNext()) {
                            CommonCandidateCity city = (CommonCandidateCity) chinaCityIterator.next();
                            country = city.getCityCountryID();
                            String provinceEn = city.getCityProvinceEn();
                            if (!country.equals("China") || provinceEn.equals("Taiwan Province")) {
                                chinaCityIterator.remove();
                            }
                        }
                        if (chinaCitySearchResult.size() == 0) {
                            CitySearchActivity.this.mNoSearchView.setVisibility(0);
                        }
                        CitySearchActivity.this.citySearchResult.addAll(chinaCitySearchResult);
                    }
                }
                if (msg.what == 2048) {
                    List<CommonCandidateCity> foreignCitySearchResult = CitySearchActivity.this.mForeignCitySearProvider.getCandidateCityList();
                    if (foreignCitySearchResult != null) {
                        Iterator<CommonCandidateCity> foreignIterator = foreignCitySearchResult.iterator();
                        while (GpsUtils.isH2OS() && foreignIterator.hasNext()) {
                            country = ((CommonCandidateCity) foreignIterator.next()).getCityCountryID();
                            if (country.equals("CN") || country.equals("HK") || country.equals("MO")) {
                                foreignIterator.remove();
                            }
                        }
                        if (foreignCitySearchResult.size() == 0) {
                            CitySearchActivity.this.mNoSearchView.setVisibility(0);
                        }
                        CitySearchActivity.this.citySearchResult.addAll(foreignCitySearchResult);
                    }
                }
                if (StarWarUtils.isStarWar() && StarWarUtils.isShow(CitySearchActivity.this.mCityKeyword.getText().toString())) {
                    CitySearchActivity.this.citySearchResult.add(0, new CommonCandidateCity(StarWarUtils.STATWAR_NAME, StarWarUtils.STATWAR_KEY_WORD, null, null, null, 0));
                }
                if (CitySearchActivity.this.citySearchResult != null && CitySearchActivity.this.citySearchResult.size() > 0) {
                    Set<CommonCandidateCity> primesWithoutDuplicates = new LinkedHashSet(CitySearchActivity.this.citySearchResult);
                    CitySearchActivity.this.citySearchResult.clear();
                    CitySearchActivity.this.citySearchResult.addAll(primesWithoutDuplicates);
                    CitySearchActivity.this.mNoSearchView.setVisibility(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
                    CitySearchActivity.this.adapter = new CityListSearchAdapter(CitySearchActivity.this, CitySearchActivity.this.citySearchResult);
                    CitySearchActivity.this.mSearchListView.setAdapter(CitySearchActivity.this.adapter);
                }
            }
        };
        this.mChinaCitySearProvider = new CitySearchProvider(getApplicationContext(), 4096, this.mHandler);
        this.mForeignCitySearProvider = new CitySearchProvider(getApplicationContext(), 2048, this.mHandler);
    }

    private void initUIView() {
        ActionBar bar = getActionBar();
        if (bar != null) {
            View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.citylist_search_bar, null);
            bar.setDisplayShowCustomEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setCustomView(actionbarLayout);
            this.mCityKeyword = (ClearableEditText) actionbarLayout.findViewById(R.id.search_bar_input_field);
        }
        this.mNoSearchView = (TextView) findViewById(R.id.no_search_view);
        this.mCityKeyword.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                String searchText = s.toString();
                if (searchText.equals("\u4e00\u52a0\u96fe")) {
                    Intent intent = new Intent(CitySearchActivity.this, ShowWeatherActivity.class);
                    intent.putExtra("type", WeatherDescription.WEATHER_DESCRIPTION_FOG);
                    CitySearchActivity.this.startActivity(intent);
                } else if (searchText.length() < 1) {
                } else {
                    if (CitySearchActivity.this.isNetworkConnected()) {
                        if (CitySearchActivity.this.citySearchResult.size() > 0 && CitySearchActivity.this.adapter != null) {
                            CitySearchActivity.this.citySearchResult.clear();
                            CitySearchActivity.this.adapter.notifyDataSetInvalidated();
                        }
                        CitySearchActivity.this.mChinaCitySearProvider.searchCitiesByKeyword(searchText, EncodeUtil.androidLocaleToAccuFormat(CitySearchActivity.this.mCityKeyword.getTextLocale()));
                        CitySearchActivity.this.mForeignCitySearProvider.searchCitiesByKeyword(searchText, EncodeUtil.androidLocaleToAccuFormat(CitySearchActivity.this.mCityKeyword.getTextLocale()));
                        return;
                    }
                    Toast.makeText(CitySearchActivity.this, CitySearchActivity.this.getString(R.string.no_network), 0).show();
                }
            }
        });
        this.mSearchListView = (ListView) findViewById(R.id.search_list);
        this.mSearchListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CitySearchActivity.this.hideSoftKeyboard(CitySearchActivity.this.mCityKeyword);
                if (CitySearchActivity.this.mCityCount == 8) {
                    Toast.makeText(CitySearchActivity.this.getApplicationContext(), R.string.city_count_limit, 0).show();
                    return;
                }
                CommonCandidateCity city = ((CityListSearchAdapter) parent.getAdapter()).getItem(position);
                if (city == null || !city.getCityCode().equals(StarWarUtils.STATWAR_NAME)) {
                    if (CitySearchActivity.this.mCityWeatherDB.addCity(city.getCityProvider() == 1 ? SEARCH_PROVIDER_CHINA : SEARCH_PROVIDER_FOREIGN, city.getCityName(CitySearchActivity.this), city.getCityName(CitySearchActivity.this), city.getCityCode(), DateTimeUtils.longTimeToRefreshTime(CitySearchActivity.this, System.currentTimeMillis()), true) < 0) {
                        Toast.makeText(CitySearchActivity.this.getApplicationContext(), R.string.city_exit, 0).show();
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_RESULT_SEARCH_CITY, true);
                    CitySearchActivity.this.setResult(-1, intent);
                    CitySearchActivity.this.finish();
                    return;
                }
                CitySearchActivity.this.startActivity(new Intent(CitySearchActivity.this, VidePlayActivity.class));
                CitySearchActivity.this.finish();
            }
        });
        this.mCityKeyword.setText(StringUtils.EMPTY_STRING);
        if (this.mCityKeyword.requestFocus()) {
            ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.mCityKeyword, 1);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        hideSoftKeyboard(this.mCityKeyword);
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.citylist_translate_down);
        return true;
    }

    private void hideSoftKeyboard(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.citylist_translate_down);
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }

    private void startActivityByTransition(View view, Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.alpha_in_listclick, R.anim.alpha_out_listclick);
    }
}
