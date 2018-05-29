package com.oneplus.lib.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.oneplus.commonctrl.R;
import com.oneplus.lib.preference.PreferenceManager.OnActivityResultListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.oneplus.weather.util.StringUtils;

public class RingtonePreference extends Preference implements OnActivityResultListener {
    private static final String EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS = "android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS";
    private static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
    private static final String TAG = "RingtonePreference";
    private static Method getDefaultRingtoneUriBySubId;
    private int mRequestCode;
    private int mRingtoneType;
    private boolean mShowDefault;
    private boolean mShowSilent;
    private int mSubscriptionID;

    public RingtonePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mSubscriptionID = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RingtonePreference, defStyleAttr, defStyleRes);
        this.mRingtoneType = a.getInt(R.styleable.RingtonePreference_android_ringtoneType, 1);
        this.mShowDefault = a.getBoolean(R.styleable.RingtonePreference_android_showDefault, true);
        this.mShowSilent = a.getBoolean(R.styleable.RingtonePreference_android_showSilent, true);
        a.recycle();
    }

    public RingtonePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RingtonePreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.op_ringtonePreferenceStyle);
    }

    public RingtonePreference(Context context) {
        this(context, null);
    }

    public int getRingtoneType() {
        return this.mRingtoneType;
    }

    public void setRingtoneType(int type) {
        this.mRingtoneType = type;
    }

    public int getSubId() {
        return this.mSubscriptionID;
    }

    public void setSubId(int subId) {
        this.mSubscriptionID = subId;
    }

    public boolean getShowDefault() {
        return this.mShowDefault;
    }

    public void setShowDefault(boolean showDefault) {
        this.mShowDefault = showDefault;
    }

    public boolean getShowSilent() {
        return this.mShowSilent;
    }

    public void setShowSilent(boolean showSilent) {
        this.mShowSilent = showSilent;
    }

    protected void onClick() {
        Intent intent;
        if (VERSION.SDK_INT >= 26) {
            intent = new Intent("oneplus.intent.action.RINGTONE_PICKER");
        } else {
            intent = new Intent("android.intent.action.oneplus.RINGTONE_PICKER");
        }
        onPrepareRingtonePickerIntent(intent);
        PreferenceFragment owningFragment = getPreferenceManager().getFragment();
        if (owningFragment != null) {
            owningFragment.startActivityForResult(intent, this.mRequestCode);
        } else {
            getPreferenceManager().getActivity().startActivityForResult(intent, this.mRequestCode);
        }
    }

    static {
        getDefaultRingtoneUriBySubId = null;
    }

    protected void onPrepareRingtonePickerIntent(Intent ringtonePickerIntent) {
        ringtonePickerIntent.putExtra("android.intent.extra.ringtone.EXISTING_URI", onRestoreRingtone());
        ringtonePickerIntent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", this.mShowDefault);
        if (this.mShowDefault) {
            if (getRingtoneType() == 1) {
                try {
                    if (getDefaultRingtoneUriBySubId == null) {
                        getDefaultRingtoneUriBySubId = RingtoneManager.class.getDeclaredMethod("getDefaultRingtoneUriBySubId", new Class[]{Integer.TYPE});
                    }
                    if (getDefaultRingtoneUriBySubId != null) {
                        ringtonePickerIntent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", (Uri) getDefaultRingtoneUriBySubId.invoke(null, new Object[]{Integer.valueOf(getSubId())}));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace();
                } catch (NoSuchMethodException e4) {
                    e4.printStackTrace();
                }
            } else {
                ringtonePickerIntent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", RingtoneManager.getDefaultUri(getRingtoneType()));
            }
        }
        ringtonePickerIntent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", this.mShowSilent);
        ringtonePickerIntent.putExtra("android.intent.extra.ringtone.TYPE", this.mRingtoneType);
        ringtonePickerIntent.putExtra("android.intent.extra.ringtone.TITLE", getTitle());
        ringtonePickerIntent.putExtra(EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS, FLAG_BYPASS_INTERRUPTION_POLICY);
    }

    protected void onSaveRingtone(Uri ringtoneUri) {
        persistString(ringtoneUri != null ? ringtoneUri.toString() : StringUtils.EMPTY_STRING);
    }

    protected Uri onRestoreRingtone() {
        String uriString = getPersistedString(null);
        return !TextUtils.isEmpty(uriString) ? Uri.parse(uriString) : null;
    }

    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValueObj) {
        String defaultValue = (String) defaultValueObj;
        if (!restorePersistedValue && !TextUtils.isEmpty(defaultValue)) {
            onSaveRingtone(Uri.parse(defaultValue));
        }
    }

    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        preferenceManager.registerOnActivityResultListener(this);
        this.mRequestCode = preferenceManager.getNextRequestCode();
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != this.mRequestCode) {
            return false;
        }
        if (data != null) {
            Uri uri = (Uri) data.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            if (callChangeListener(uri != null ? uri.toString() : StringUtils.EMPTY_STRING)) {
                onSaveRingtone(uri);
            }
        }
        return true;
    }
}
