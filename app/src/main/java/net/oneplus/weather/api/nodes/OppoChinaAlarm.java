package net.oneplus.weather.api.nodes;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Date;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.impl.OppoChinaRequest;

public class OppoChinaAlarm extends Alarm {
    public static final Creator<OppoChinaAlarm> CREATOR;
    private String mAlarmAreaName;
    private String mContentText;
    private Date mEndTime;
    private String mLevelName;
    private Date mPublishTime;
    private Date mStartTime;
    private String mTypeName;

    public OppoChinaAlarm(String areaCode, String areaName) {
        super(areaCode, areaName, OppoChinaRequest.DATA_SOURCE_NAME);
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

    public static OppoChinaAlarm build(String areaCode, String areaName, Date observationDate, String warnWeatherTitle, String warnWeatherDetail) {
        if (StringUtils.isBlank(areaCode)) {
            return null;
        }
        OppoChinaAlarm oppoChinaAlarm = new OppoChinaAlarm(areaCode, areaName);
        oppoChinaAlarm.mAlarmAreaName = areaName;
        oppoChinaAlarm.mContentText = warnWeatherDetail;
        oppoChinaAlarm.mLevelName = warnWeatherTitle;
        oppoChinaAlarm.mTypeName = warnWeatherTitle;
        oppoChinaAlarm.mPublishTime = observationDate;
        oppoChinaAlarm.mStartTime = null;
        oppoChinaAlarm.mEndTime = null;
        return oppoChinaAlarm;
    }

    public int describeContents() {
        return 0;
    }

    static {
        CREATOR = new Creator<OppoChinaAlarm>() {
            public OppoChinaAlarm createFromParcel(Parcel source) {
                return new OppoChinaAlarm().setParcel(source);
            }

            public OppoChinaAlarm[] newArray(int size) {
                return new OppoChinaAlarm[size];
            }
        };
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAreaCode());
        dest.writeString(this.mAlarmAreaName);
        dest.writeLong(DateUtils.dateToEpochDate(this.mPublishTime));
        dest.writeString(this.mTypeName);
        dest.writeString(this.mContentText);
    }

    public OppoChinaAlarm setParcel(Parcel parcel) {
        return build(parcel.readString(), parcel.readString(), DateUtils.epochDateToDate(parcel.readLong()), parcel.readString(), parcel.readString());
    }
}
