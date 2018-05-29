package net.oneplus.weather.api.nodes;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.api.R;
import net.oneplus.weather.api.impl.SwaRequest;
import net.oneplus.weather.api.nodes.LifeIndexWeather.LifeIndex;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class SwaLifeIndexWeather extends LifeIndexWeather {
    private static String DEFAULT_INDEX_TEXT;
    private List<LifeIndex> mIndexList;

    static {
        DEFAULT_INDEX_TEXT = StringUtils.EMPTY_STRING;
    }

    public SwaLifeIndexWeather(String areaCode) {
        super(areaCode, null, SwaRequest.DATA_SOURCE_NAME);
        this.mIndexList = null;
    }

    public List<LifeIndex> getLifeIndexList() {
        return this.mIndexList;
    }

    public void add(LifeIndex item) {
        if (item != null) {
            if (this.mIndexList == null) {
                this.mIndexList = new ArrayList();
            }
            this.mIndexList.add(item);
        }
    }

    public String getUVIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("fs".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }

    public String getUVIndexInternationalText(Context context, String defaultValue) {
        String shortText = getUVIndexText(DEFAULT_INDEX_TEXT);
        String result = shortText;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(shortText)) {
            return result;
        }
        Object obj = -1;
        switch (shortText.hashCode()) {
            case 24369:
                if (shortText.equals("\u5f31")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 24378:
                if (shortText.equals("\u5f3a")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 651964:
                if (shortText.equals("\u4e2d\u7b49")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 782505:
                if (shortText.equals("\u5f88\u5f31")) {
                    obj = null;
                }
                break;
            case 782514:
                if (shortText.equals("\u5f88\u5f3a")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case 841777:
                if (shortText.equals("\u6700\u5f31")) {
                    obj = 1;
                }
                break;
            case 841786:
                if (shortText.equals("\u6700\u5f3a")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case 1163278:
                if (shortText.equals("\u8f83\u5f31")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 1163287:
                if (shortText.equals("\u8f83\u5f3a")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.ultraviolet_index_level_one);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.ultraviolet_index_level_two);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(R.string.ultraviolet_index_level_three);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
            case ConnectionResult.RESOLUTION_REQUIRED:
                return context.getString(R.string.ultraviolet_index_level_four);
            case DetectedActivity.WALKING:
            case DetectedActivity.RUNNING:
                return context.getString(R.string.ultraviolet_index_level_five);
            default:
                return result;
        }
    }

    public String getSportsIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("yd".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }

    public String getSportsIndexInternationalText(Context context, String defaultValue) {
        String shortText = getSportsIndexText(DEFAULT_INDEX_TEXT);
        String result = shortText;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(shortText)) {
            return result;
        }
        Object obj = -1;
        switch (shortText.hashCode()) {
            case 642863:
                if (shortText.equals("\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 1166298:
                if (shortText.equals("\u9002\u5b9c")) {
                    obj = null;
                }
                break;
            case 35949042:
                if (shortText.equals("\u8f83\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 36472477:
                if (shortText.equals("\u8f83\u9002\u5b9c")) {
                    obj = 1;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.motion_index_level_one);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.motion_index_level_two);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.motion_index_level_three);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.motion_index_level_four);
            default:
                return result;
        }
    }

    public String getCarwashIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("xc".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }

    public String getCarwashIndexInternationalText(Context context, String defaultValue) {
        String shortText = getCarwashIndexText(DEFAULT_INDEX_TEXT);
        String result = shortText;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(shortText)) {
            return result;
        }
        Object obj = -1;
        switch (shortText.hashCode()) {
            case 642863:
                if (shortText.equals("\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 1166298:
                if (shortText.equals("\u9002\u5b9c")) {
                    obj = null;
                }
                break;
            case 35949042:
                if (shortText.equals("\u8f83\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 36472477:
                if (shortText.equals("\u8f83\u9002\u5b9c")) {
                    obj = 1;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.carwash_index_level_one);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.carwash_index_level_two);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.carwash_index_level_three);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.carwash_index_level_four);
            default:
                return result;
        }
    }

    public String getClothingIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("ct".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }

    public String getClothingIndexInternationalText(Context context, String defaultValue) {
        String shortText = getClothingIndexText(DEFAULT_INDEX_TEXT);
        String result = shortText;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(shortText)) {
            return result;
        }
        Object obj = -1;
        switch (shortText.hashCode()) {
            case 20919:
                if (shortText.equals("\u51b7")) {
                    obj = 1;
                }
                break;
            case 28909:
                if (shortText.equals("\u70ed")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 749605:
                if (shortText.equals("\u5bd2\u51b7")) {
                    obj = null;
                }
                break;
            case 922143:
                if (shortText.equals("\u708e\u70ed")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case 1069104:
                if (shortText.equals("\u8212\u9002")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 1159828:
                if (shortText.equals("\u8f83\u51b7")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 36375283:
                if (shortText.equals("\u8f83\u8212\u9002")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.dress_index_earmuff);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.dress_index_scarf);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.dress_index_sweater);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.dress_index_jacket);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(R.string.dress_index_fleece);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return context.getString(R.string.dress_index_short_sleeve);
            case ConnectionResult.RESOLUTION_REQUIRED:
                return context.getString(R.string.dress_index_waistcoat);
            default:
                return result;
        }
    }
}
