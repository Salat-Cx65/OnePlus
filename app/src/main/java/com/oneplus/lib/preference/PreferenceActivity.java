package com.oneplus.lib.preference;

import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.commonctrl.R;
import com.oneplus.lib.preference.PreferenceActivity.Header;
import com.oneplus.lib.preference.PreferenceFragment.OnPreferenceStartFragmentCallback;
import com.oneplus.lib.preference.PreferenceManager.OnPreferenceTreeClickListener;
import com.oneplus.lib.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public abstract class PreferenceActivity extends ListActivity implements OnPreferenceTreeClickListener, OnPreferenceStartFragmentCallback {
    private static final String BACK_STACK_PREFS = ":android:prefs";
    private static final String CUR_HEADER_TAG = ":android:cur_header";
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final String PREFERENCES_TAG = ":android:preferences";
    private static final String TAG = "PreferenceActivity";
    private Header mCurHeader;
    private FragmentBreadCrumbs mFragmentBreadCrumbs;
    private Handler mHandler;
    private final ArrayList<Header> mHeaders;
    private FrameLayout mListFooter;
    private Button mNextButton;
    private int mPreferenceHeaderItemResId;
    private boolean mPreferenceHeaderRemoveEmptyIcon;
    private PreferenceManager mPreferenceManager;
    private ViewGroup mPrefsContainer;
    private Bundle mSavedInstanceState;
    private boolean mSinglePane;

    public static final class Header implements Parcelable {
        public static final Creator<com.oneplus.lib.preference.PreferenceActivity.Header> CREATOR;
        public CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id;
        public Intent intent;
        public CharSequence summary;
        public int summaryRes;
        public CharSequence title;
        public int titleRes;

        public Header() {
            this.id = -1;
        }

        public CharSequence getTitle(Resources res) {
            return this.titleRes != 0 ? res.getText(this.titleRes) : this.title;
        }

        public CharSequence getSummary(Resources res) {
            return this.summaryRes != 0 ? res.getText(this.summaryRes) : this.summary;
        }

        public CharSequence getBreadCrumbTitle(Resources res) {
            return this.breadCrumbTitleRes != 0 ? res.getText(this.breadCrumbTitleRes) : this.breadCrumbTitle;
        }

        public CharSequence getBreadCrumbShortTitle(Resources res) {
            return this.breadCrumbShortTitleRes != 0 ? res.getText(this.breadCrumbShortTitleRes) : this.breadCrumbShortTitle;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeInt(this.titleRes);
            TextUtils.writeToParcel(this.title, dest, flags);
            dest.writeInt(this.summaryRes);
            TextUtils.writeToParcel(this.summary, dest, flags);
            dest.writeInt(this.breadCrumbTitleRes);
            TextUtils.writeToParcel(this.breadCrumbTitle, dest, flags);
            dest.writeInt(this.breadCrumbShortTitleRes);
            TextUtils.writeToParcel(this.breadCrumbShortTitle, dest, flags);
            dest.writeInt(this.iconRes);
            dest.writeString(this.fragment);
            dest.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                dest.writeInt(MSG_BIND_PREFERENCES);
                this.intent.writeToParcel(dest, flags);
            } else {
                dest.writeInt(0);
            }
            dest.writeBundle(this.extras);
        }

        public void readFromParcel(Parcel in) {
            this.id = in.readLong();
            this.titleRes = in.readInt();
            this.title = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.summaryRes = in.readInt();
            this.summary = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.breadCrumbTitleRes = in.readInt();
            this.breadCrumbTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.breadCrumbShortTitleRes = in.readInt();
            this.breadCrumbShortTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.iconRes = in.readInt();
            this.fragment = in.readString();
            this.fragmentArguments = in.readBundle();
            if (in.readInt() != 0) {
                this.intent = (Intent) Intent.CREATOR.createFromParcel(in);
            }
            this.extras = in.readBundle();
        }

        Header(Parcel in) {
            this.id = -1;
            readFromParcel(in);
        }

        static {
            CREATOR = new Creator<com.oneplus.lib.preference.PreferenceActivity.Header>() {
                public com.oneplus.lib.preference.PreferenceActivity.Header createFromParcel(Parcel source) {
                    return new com.oneplus.lib.preference.PreferenceActivity.Header(source);
                }

                public com.oneplus.lib.preference.PreferenceActivity.Header[] newArray(int size) {
                    return new com.oneplus.lib.preference.PreferenceActivity.Header[size];
                }
            };
        }
    }

    private static class HeaderAdapter extends ArrayAdapter<Header> {
        private LayoutInflater mInflater;
        private int mLayoutResId;
        private boolean mRemoveIconIfEmpty;

        private static class HeaderViewHolder {
            ImageView icon;
            TextView summary;
            TextView title;

            private HeaderViewHolder() {
            }
        }

        public HeaderAdapter(Context context, List<Header> objects, int layoutResId, boolean removeIconBehavior) {
            super(context, 0, objects);
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mLayoutResId = layoutResId;
            this.mRemoveIconIfEmpty = removeIconBehavior;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            HeaderViewHolder holder;
            if (convertView == null) {
                view = this.mInflater.inflate(this.mLayoutResId, parent, false);
                holder = new HeaderViewHolder();
                holder.icon = (ImageView) view.findViewById(16908294);
                holder.title = (TextView) view.findViewById(16908310);
                holder.summary = (TextView) view.findViewById(16908304);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (HeaderViewHolder) view.getTag();
            }
            Header header = (Header) getItem(position);
            if (!this.mRemoveIconIfEmpty) {
                holder.icon.setImageResource(header.iconRes);
            } else if (header.iconRes == 0) {
                holder.icon.setVisibility(DetectedActivity.RUNNING);
            } else {
                holder.icon.setVisibility(0);
                holder.icon.setImageResource(header.iconRes);
            }
            holder.title.setText(header.getTitle(getContext().getResources()));
            CharSequence summary = header.getSummary(getContext().getResources());
            if (TextUtils.isEmpty(summary)) {
                holder.summary.setVisibility(DetectedActivity.RUNNING);
            } else {
                holder.summary.setVisibility(0);
                holder.summary.setText(summary);
            }
            return view;
        }
    }

    public PreferenceActivity() {
        this.mHeaders = new ArrayList();
        this.mPreferenceHeaderItemResId = 0;
        this.mPreferenceHeaderRemoveEmptyIcon = false;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_BIND_PREFERENCES:
                        PreferenceActivity.this.bindPreferences();
                    case MSG_BUILD_HEADERS:
                        ArrayList<Header> oldHeaders = new ArrayList(PreferenceActivity.this.mHeaders);
                        PreferenceActivity.this.mHeaders.clear();
                        PreferenceActivity.this.onBuildHeaders(PreferenceActivity.this.mHeaders);
                        if (PreferenceActivity.this.getListAdapter() instanceof BaseAdapter) {
                            ((BaseAdapter) PreferenceActivity.this.getListAdapter()).notifyDataSetChanged();
                        }
                        Header header = PreferenceActivity.this.onGetNewHeader();
                        Header mappedHeader;
                        if (header != null && header.fragment != null) {
                            mappedHeader = PreferenceActivity.this.findBestMatchingHeader(header, oldHeaders);
                            if (mappedHeader == null || PreferenceActivity.this.mCurHeader != mappedHeader) {
                                PreferenceActivity.this.switchToHeader(header);
                            }
                        } else if (PreferenceActivity.this.mCurHeader != null) {
                            mappedHeader = PreferenceActivity.this.findBestMatchingHeader(PreferenceActivity.this.mCurHeader, PreferenceActivity.this.mHeaders);
                            if (mappedHeader != null) {
                                PreferenceActivity.this.setSelectedHeader(mappedHeader);
                            }
                        }
                    default:
                        break;
                }
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypedArray sa = obtainStyledAttributes(null, R.styleable.PreferenceActivity, R.attr.op_preferenceActivityStyle, 0);
        int layoutResId = sa.getResourceId(R.styleable.PreferenceActivity_android_layout, R.layout.preference_list_content);
        this.mPreferenceHeaderItemResId = sa.getResourceId(R.styleable.PreferenceActivity_oneplusHeaderLayout, R.layout.preference_header_item);
        this.mPreferenceHeaderRemoveEmptyIcon = sa.getBoolean(R.styleable.PreferenceActivity_headerRemoveIconIfEmpty, false);
        sa.recycle();
        setContentView(layoutResId);
        this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
        this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs_frame);
        boolean z = (onIsHidingHeaders() || !onIsMultiPane()) ? MSG_BIND_PREFERENCES : null;
        this.mSinglePane = z;
        String initialFragment = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        Bundle initialArguments = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        int initialTitle = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
        int initialShortTitle = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
        if (savedInstanceState != null) {
            ArrayList<Header> headers = savedInstanceState.getParcelableArrayList(HEADERS_TAG);
            if (headers != null) {
                this.mHeaders.addAll(headers);
                int curHeader = savedInstanceState.getInt(CUR_HEADER_TAG, -1);
                if (curHeader >= 0 && curHeader < this.mHeaders.size()) {
                    setSelectedHeader((Header) this.mHeaders.get(curHeader));
                }
            }
        } else if (initialFragment == null || !this.mSinglePane) {
            onBuildHeaders(this.mHeaders);
            if (this.mHeaders.size() > 0 && !this.mSinglePane) {
                if (initialFragment == null) {
                    switchToHeader(onGetInitialHeader());
                } else {
                    switchToHeader(initialFragment, initialArguments);
                }
            }
        } else {
            switchToHeader(initialFragment, initialArguments);
            if (initialTitle != 0) {
                showBreadCrumbs(getText(initialTitle), initialShortTitle != 0 ? getText(initialShortTitle) : null);
            }
        }
        if (initialFragment != null && this.mSinglePane) {
            findViewById(R.id.headers).setVisibility(DetectedActivity.RUNNING);
            this.mPrefsContainer.setVisibility(0);
            if (initialTitle != 0) {
                showBreadCrumbs(getText(initialTitle), initialShortTitle != 0 ? getText(initialShortTitle) : null);
            }
        } else if (this.mHeaders.size() > 0) {
            setListAdapter(new HeaderAdapter(this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon));
            if (!this.mSinglePane) {
                getListView().setChoiceMode(MSG_BIND_PREFERENCES);
                if (this.mCurHeader != null) {
                    setSelectedHeader(this.mCurHeader);
                }
                this.mPrefsContainer.setVisibility(0);
            }
        } else {
            setContentView(R.layout.preference_list_content_single);
            this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
            this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs);
            this.mPreferenceManager = new PreferenceManager(this, 100);
            this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
        }
        Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            String buttonText;
            findViewById(R.id.button_bar).setVisibility(0);
            Button backButton = (Button) findViewById(R.id.back_button);
            backButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(0);
                    PreferenceActivity.this.finish();
                }
            });
            Button skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            this.mNextButton = (Button) findViewById(R.id.next_button);
            this.mNextButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            if (intent.hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                buttonText = intent.getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (TextUtils.isEmpty(buttonText)) {
                    this.mNextButton.setVisibility(DetectedActivity.RUNNING);
                } else {
                    this.mNextButton.setText(buttonText);
                }
            }
            if (intent.hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                buttonText = intent.getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (TextUtils.isEmpty(buttonText)) {
                    backButton.setVisibility(8);
                } else {
                    backButton.setText(buttonText);
                }
            }
            if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                skipButton.setVisibility(0);
            }
        }
    }

    public boolean hasHeaders() {
        return getListView().getVisibility() == 0 && this.mPreferenceManager == null;
    }

    public List<Header> getHeaders() {
        return this.mHeaders;
    }

    public boolean isMultiPane() {
        return hasHeaders() && this.mPrefsContainer.getVisibility() == 0;
    }

    public boolean onIsMultiPane() {
        return getResources().getBoolean(R.bool.preferences_prefer_dual_pane);
    }

    public boolean onIsHidingHeaders() {
        return getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public Header onGetInitialHeader() {
        for (int i = 0; i < this.mHeaders.size(); i++) {
            Header h = (Header) this.mHeaders.get(i);
            if (h.fragment != null) {
                return h;
            }
        }
        throw new IllegalStateException("Must have at least one header with a fragment");
    }

    public Header onGetNewHeader() {
        return null;
    }

    public void onBuildHeaders(List<Header> list) {
    }

    public void invalidateHeaders() {
        if (!this.mHandler.hasMessages(MSG_BUILD_HEADERS)) {
            this.mHandler.sendEmptyMessage(MSG_BUILD_HEADERS);
        }
    }

    public void loadHeadersFromResource(int resid, List<Header> target) {
        XmlResourceParser xmlResourceParser = null;
        try {
            int type;
            xmlResourceParser = getResources().getXml(resid);
            AttributeSet attrs = Xml.asAttributeSet(xmlResourceParser);
            do {
                type = xmlResourceParser.next();
                if (type == 1) {
                    break;
                }
            } while (type != 2);
            String nodeName = xmlResourceParser.getName();
            if ("preference-headers".equals(nodeName)) {
                Bundle curBundle = null;
                int outerDepth = xmlResourceParser.getDepth();
                while (true) {
                    type = xmlResourceParser.next();
                    if (type != 1) {
                        if (type == 3 && xmlResourceParser.getDepth() <= outerDepth) {
                            break;
                        } else if (type != 3 && type != 4) {
                            if ("header".equals(xmlResourceParser.getName())) {
                                Header header = new Header();
                                TypedArray sa = obtainStyledAttributes(attrs, R.styleable.PreferenceHeader);
                                header.id = (long) sa.getResourceId(R.styleable.PreferenceHeader_android_id, -1);
                                TypedValue tv = sa.peekValue(R.styleable.PreferenceHeader_android_title);
                                if (tv != null && tv.type == 3) {
                                    if (tv.resourceId != 0) {
                                        header.titleRes = tv.resourceId;
                                    } else {
                                        header.title = tv.string;
                                    }
                                }
                                tv = sa.peekValue(R.styleable.PreferenceHeader_android_summary);
                                if (tv != null && tv.type == 3) {
                                    if (tv.resourceId != 0) {
                                        header.summaryRes = tv.resourceId;
                                    } else {
                                        header.summary = tv.string;
                                    }
                                }
                                tv = sa.peekValue(R.styleable.PreferenceHeader_android_breadCrumbTitle);
                                if (tv != null && tv.type == 3) {
                                    if (tv.resourceId != 0) {
                                        header.breadCrumbTitleRes = tv.resourceId;
                                    } else {
                                        header.breadCrumbTitle = tv.string;
                                    }
                                }
                                tv = sa.peekValue(R.styleable.PreferenceHeader_android_breadCrumbShortTitle);
                                if (tv != null && tv.type == 3) {
                                    if (tv.resourceId != 0) {
                                        header.breadCrumbShortTitleRes = tv.resourceId;
                                    } else {
                                        header.breadCrumbShortTitle = tv.string;
                                    }
                                }
                                header.iconRes = sa.getResourceId(R.styleable.PreferenceHeader_android_icon, 0);
                                header.fragment = sa.getString(R.styleable.PreferenceHeader_android_fragment);
                                sa.recycle();
                                if (curBundle == null) {
                                    curBundle = new Bundle();
                                }
                                int innerDepth = xmlResourceParser.getDepth();
                                while (true) {
                                    type = xmlResourceParser.next();
                                    if (type != 1) {
                                        if (type == 3 && xmlResourceParser.getDepth() <= innerDepth) {
                                            break;
                                        } else if (type != 3 && type != 4) {
                                            String innerNodeName = xmlResourceParser.getName();
                                            if (innerNodeName.equals("extra")) {
                                                getResources().parseBundleExtra("extra", attrs, curBundle);
                                                XmlUtils.skipCurrentTag(xmlResourceParser);
                                            } else if (innerNodeName.equals("intent")) {
                                                header.intent = Intent.parseIntent(getResources(), xmlResourceParser, attrs);
                                            } else {
                                                XmlUtils.skipCurrentTag(xmlResourceParser);
                                            }
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (curBundle.size() > 0) {
                                    header.fragmentArguments = curBundle;
                                    curBundle = null;
                                }
                                target.add(header);
                            } else {
                                XmlUtils.skipCurrentTag(xmlResourceParser);
                            }
                        }
                    } else {
                        break;
                    }
                }
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                    return;
                }
                return;
            }
            throw new RuntimeException("XML document must start with <preference-headers> tag; found" + nodeName + " at " + xmlResourceParser.getPositionDescription());
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Error parsing headers", e);
        } catch (IOException e2) {
            throw new RuntimeException("Error parsing headers", e2);
        } catch (Throwable th) {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    protected boolean isValidFragment(String fragmentName) {
        if (getApplicationInfo().targetSdkVersion < 19) {
            return true;
        }
        throw new RuntimeException("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! " + getClass().getName() + " has not checked if fragment " + fragmentName + " is valid.");
    }

    public void setListFooter(View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, new LayoutParams(-1, -2));
    }

    protected void onStop() {
        super.onStop();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityStop();
        }
    }

    protected void onDestroy() {
        this.mHandler.removeMessages(MSG_BIND_PREFERENCES);
        this.mHandler.removeMessages(MSG_BUILD_HEADERS);
        super.onDestroy();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityDestroy();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mHeaders.size() > 0) {
            outState.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            if (this.mCurHeader != null) {
                int index = this.mHeaders.indexOf(this.mCurHeader);
                if (index >= 0) {
                    outState.putInt(CUR_HEADER_TAG, index);
                }
            }
        }
        if (this.mPreferenceManager != null) {
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                Bundle container = new Bundle();
                preferenceScreen.saveHierarchyState(container);
                outState.putBundle(PREFERENCES_TAG, container);
            }
        }
    }

    protected void onRestoreInstanceState(Bundle state) {
        if (this.mPreferenceManager != null) {
            Bundle container = state.getBundle(PREFERENCES_TAG);
            if (container != null) {
                PreferenceScreen preferenceScreen = getPreferenceScreen();
                if (preferenceScreen != null) {
                    preferenceScreen.restoreHierarchyState(container);
                    this.mSavedInstanceState = state;
                    return;
                }
            }
        }
        super.onRestoreInstanceState(state);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityResult(requestCode, resultCode, data);
        }
    }

    public void onContentChanged() {
        super.onContentChanged();
        if (this.mPreferenceManager != null) {
            postBindPreferences();
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (getListAdapter() != null) {
            Object item = getListAdapter().getItem(position);
            if (item instanceof Header) {
                onHeaderClick((Header) item, position);
            }
        }
    }

    public void onHeaderClick(Header header, int position) {
        if (header.fragment != null) {
            if (this.mSinglePane) {
                int titleRes = header.breadCrumbTitleRes;
                int shortTitleRes = header.breadCrumbShortTitleRes;
                if (titleRes == 0) {
                    titleRes = header.titleRes;
                    shortTitleRes = 0;
                }
                startWithFragment(header.fragment, header.fragmentArguments, null, 0, titleRes, shortTitleRes);
                return;
            }
            switchToHeader(header);
        } else if (header.intent != null) {
            startActivity(header.intent);
        }
    }

    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args, int titleRes, int shortTitleRes) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, titleRes);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, shortTitleRes);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    public void startWithFragment(String fragmentName, Bundle args, Fragment resultTo, int resultRequestCode) {
        startWithFragment(fragmentName, args, resultTo, resultRequestCode, 0, 0);
    }

    public void startWithFragment(String fragmentName, Bundle args, Fragment resultTo, int resultRequestCode, int titleRes, int shortTitleRes) {
        Intent intent = onBuildStartFragmentIntent(fragmentName, args, titleRes, shortTitleRes);
        if (resultTo == null) {
            startActivity(intent);
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode);
        }
    }

    public void showBreadCrumbs(CharSequence title, CharSequence shortTitle) {
        if (this.mFragmentBreadCrumbs == null) {
            try {
                this.mFragmentBreadCrumbs = (FragmentBreadCrumbs) findViewById(16908310);
                if (this.mFragmentBreadCrumbs != null) {
                    if (this.mSinglePane) {
                        this.mFragmentBreadCrumbs.setVisibility(DetectedActivity.RUNNING);
                        View bcSection = findViewById(R.id.breadcrumb_section);
                        if (bcSection != null) {
                            bcSection.setVisibility(DetectedActivity.RUNNING);
                        }
                        setTitle(title);
                    }
                    this.mFragmentBreadCrumbs.setMaxVisible(MSG_BUILD_HEADERS);
                    this.mFragmentBreadCrumbs.setActivity(this);
                } else if (title != null) {
                    setTitle(title);
                    return;
                } else {
                    return;
                }
            } catch (ClassCastException e) {
                setTitle(title);
            }
        }
        if (this.mFragmentBreadCrumbs.getVisibility() != 0) {
            setTitle(title);
            return;
        }
        this.mFragmentBreadCrumbs.setTitle(title, shortTitle);
        this.mFragmentBreadCrumbs.setParentTitle(null, null, null);
    }

    public void setParentTitle(CharSequence title, CharSequence shortTitle, OnClickListener listener) {
        if (this.mFragmentBreadCrumbs != null) {
            this.mFragmentBreadCrumbs.setParentTitle(title, shortTitle, listener);
        }
    }

    void setSelectedHeader(Header header) {
        this.mCurHeader = header;
        int index = this.mHeaders.indexOf(header);
        if (index >= 0) {
            getListView().setItemChecked(index, true);
        } else {
            getListView().clearChoices();
        }
        showBreadCrumbs(header);
    }

    void showBreadCrumbs(Header header) {
        if (header != null) {
            CharSequence title = header.getBreadCrumbTitle(getResources());
            if (title == null) {
                title = header.getTitle(getResources());
            }
            if (title == null) {
                title = getTitle();
            }
            showBreadCrumbs(title, header.getBreadCrumbShortTitle(getResources()));
            return;
        }
        showBreadCrumbs(getTitle(), null);
    }

    private void switchToHeaderInner(String fragmentName, Bundle args) {
        getFragmentManager().popBackStack(BACK_STACK_PREFS, MSG_BIND_PREFERENCES);
        if (isValidFragment(fragmentName)) {
            Fragment f = Fragment.instantiate(this, fragmentName, args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.prefs, f);
            transaction.commitAllowingStateLoss();
            return;
        }
        throw new IllegalArgumentException("Invalid fragment for this activity: " + fragmentName);
    }

    public void switchToHeader(String fragmentName, Bundle args) {
        Header selectedHeader = null;
        for (int i = 0; i < this.mHeaders.size(); i++) {
            if (fragmentName.equals(((Header) this.mHeaders.get(i)).fragment)) {
                selectedHeader = this.mHeaders.get(i);
                break;
            }
        }
        setSelectedHeader(selectedHeader);
        switchToHeaderInner(fragmentName, args);
    }

    public void switchToHeader(Header header) {
        if (this.mCurHeader == header) {
            getFragmentManager().popBackStack(BACK_STACK_PREFS, MSG_BIND_PREFERENCES);
        } else if (header.fragment == null) {
            throw new IllegalStateException("can't switch to header that has no fragment");
        } else {
            switchToHeaderInner(header.fragment, header.fragmentArguments);
            setSelectedHeader(header);
        }
    }

    Header findBestMatchingHeader(Header cur, ArrayList<Header> from) {
        ArrayList<Header> matches = new ArrayList();
        int j = 0;
        while (j < from.size()) {
            Header oh = (Header) from.get(j);
            if (cur != oh) {
                if (cur.id == -1 || cur.id != oh.id) {
                    if (cur.fragment != null) {
                        if (cur.fragment.equals(oh.fragment)) {
                            matches.add(oh);
                        }
                    } else if (cur.intent != null) {
                        if (cur.intent.equals(oh.intent)) {
                            matches.add(oh);
                        }
                    } else if (cur.title != null && cur.title.equals(oh.title)) {
                        matches.add(oh);
                    }
                    j++;
                }
            }
            matches.clear();
            matches.add(oh);
            break;
        }
        int NM = matches.size();
        if (NM == 1) {
            return (Header) matches.get(0);
        }
        if (NM > 1) {
            for (j = 0; j < NM; j++) {
                oh = (Header) matches.get(j);
                if (cur.fragmentArguments != null && cur.fragmentArguments.equals(oh.fragmentArguments)) {
                    return oh;
                }
                if (cur.extras != null && cur.extras.equals(oh.extras)) {
                    return oh;
                }
                if (cur.title != null && cur.title.equals(oh.title)) {
                    return oh;
                }
            }
        }
        return null;
    }

    public void startPreferenceFragment(Fragment fragment, boolean push) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.prefs, fragment);
        if (push) {
            transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        transaction.commitAllowingStateLoss();
    }

    public void startPreferencePanel(String fragmentClass, Bundle args, int titleRes, CharSequence titleText, Fragment resultTo, int resultRequestCode) {
        if (this.mSinglePane) {
            startWithFragment(fragmentClass, args, resultTo, resultRequestCode, titleRes, 0);
            return;
        }
        Fragment f = Fragment.instantiate(this, fragmentClass, args);
        if (resultTo != null) {
            f.setTargetFragment(resultTo, resultRequestCode);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.prefs, f);
        if (titleRes != 0) {
            transaction.setBreadCrumbTitle(titleRes);
        } else if (titleText != null) {
            transaction.setBreadCrumbTitle(titleText);
        }
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(BACK_STACK_PREFS);
        transaction.commitAllowingStateLoss();
    }

    public void finishPreferencePanel(Fragment caller, int resultCode, Intent resultData) {
        if (this.mSinglePane) {
            setResult(resultCode, resultData);
            finish();
            return;
        }
        onBackPressed();
        if (caller != null && caller.getTargetFragment() != null) {
            caller.getTargetFragment().onActivityResult(caller.getTargetRequestCode(), resultCode, resultData);
        }
    }

    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        startPreferencePanel(pref.getFragment(), pref.getExtras(), pref.getTitleRes(), pref.getTitle(), null, 0);
        return true;
    }

    private void postBindPreferences() {
        if (!this.mHandler.hasMessages(MSG_BIND_PREFERENCES)) {
            this.mHandler.obtainMessage(MSG_BIND_PREFERENCES).sendToTarget();
        }
    }

    private void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(getListView());
            if (this.mSavedInstanceState != null) {
                super.onRestoreInstanceState(this.mSavedInstanceState);
                this.mSavedInstanceState = null;
            }
        }
    }

    @Deprecated
    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager != null) {
            return;
        }
        if (getListAdapter() == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
        throw new RuntimeException("Modern two-pane PreferenceActivity requires use of a PreferenceFragment");
    }

    @Deprecated
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        requirePreferenceManager();
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            postBindPreferences();
            CharSequence title = getPreferenceScreen().getTitle();
            if (title != null) {
                setTitle(title);
            }
        }
    }

    @Deprecated
    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceManager != null ? this.mPreferenceManager.getPreferenceScreen() : null;
    }

    @Deprecated
    public void addPreferencesFromIntent(Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    @Deprecated
    public void addPreferencesFromResource(int preferencesResId) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this, preferencesResId, getPreferenceScreen()));
    }

    @Deprecated
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return false;
    }

    @Deprecated
    public Preference findPreference(CharSequence key) {
        return this.mPreferenceManager == null ? null : this.mPreferenceManager.findPreference(key);
    }

    protected void onNewIntent(Intent intent) {
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchNewIntent(intent);
        }
    }

    protected boolean hasNextButton() {
        return this.mNextButton != null;
    }

    protected Button getNextButton() {
        return this.mNextButton;
    }
}
