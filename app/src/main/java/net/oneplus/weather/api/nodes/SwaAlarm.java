package net.oneplus.weather.api.nodes;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Date;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.impl.SwaRequest;

public class SwaAlarm extends Alarm {
    public static final Creator<SwaAlarm> CREATOR;
    private String mAlarmAreaName;
    private String mContentText;
    private Date mEndTime;
    private String mLevelName;
    private String mLevelNo;
    private Date mPublishTime;
    private Date mStartTime;
    private String mTypeName;
    private String mTypeNo;

    public SwaAlarm(String areaCode, String areaName) {
        super(areaCode, areaName, SwaRequest.DATA_SOURCE_NAME);
    }

    public String getAlarmAreaName() {
        return this.mAlarmAreaName;
    }

    public Date getPublishTime() {
        return this.mPublishTime;
    }

    public Date getStartTime() {
        return this.mStartTime;
    }

    public Date getEndTime() {
        return this.mEndTime;
    }

    public String getTypeName() {
        return this.mTypeName;
    }

    public String getLevelName() {
        return this.mLevelName;
    }

    public String getContentText() {
        return this.mContentText;
    }

    public String getLevelNumber() {
        return this.mLevelNo;
    }

    public String getTypeNumber() {
        return this.mTypeNo;
    }

    public static SwaAlarm build(String areaCode, String alarmAreaName, String typeName, String typeNo, String levelName, String levelNo, String contentText, String publishTime) {
        if (StringUtils.isBlank(areaCode) || StringUtils.isBlank(typeName)) {
            return null;
        }
        SwaAlarm swaAlarm = new SwaAlarm(areaCode, null);
        swaAlarm.mAlarmAreaName = alarmAreaName;
        swaAlarm.mContentText = contentText;
        swaAlarm.mLevelName = levelName;
        swaAlarm.mLevelNo = levelNo;
        swaAlarm.mTypeName = typeName;
        swaAlarm.mTypeNo = typeNo;
        swaAlarm.mPublishTime = DateUtils.parseSwaAlarmDate(publishTime);
        swaAlarm.mStartTime = null;
        swaAlarm.mEndTime = null;
        return swaAlarm;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAreaCode());
        dest.writeString(this.mAlarmAreaName);
        dest.writeString(this.mTypeName);
        dest.writeString(this.mTypeNo);
        dest.writeString(this.mLevelName);
        dest.writeString(this.mLevelNo);
        dest.writeString(this.mContentText);
        dest.writeString(DateUtils.formatSwaRequestDateText(this.mPublishTime));
    }

    static {
        CREATOR = new Creator<SwaAlarm>() {
            public SwaAlarm createFromParcel(Parcel source) {
                return new SwaAlarm().setParcel(source);
            }

            public SwaAlarm[] newArray(int size) {
                return new SwaAlarm[size];
            }
        };
    }

    public SwaAlarm setParcel(Parcel parcel) {
        return build(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
    }
}
