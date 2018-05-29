package net.oneplus.weather.api.nodes;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Date;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.impl.AccuRequest;

public class AccuAlarm extends Alarm {
    public static final Creator<AccuAlarm> CREATOR;
    private String mAlarmAreaName;
    private String mContentText;
    private Date mEndTime;
    private String mLevelName;
    private Date mPublishTime;
    private Date mStartTime;
    private String mTypeName;

    public AccuAlarm(String areaCode, String areaName) {
        super(areaCode, areaName, AccuRequest.DATA_SOURCE_NAME);
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

    public static AccuAlarm build(String areaCode, String alarmAreaName, String typeName, String levelName, String contentText, long startTime, long endTime) {
        if (StringUtils.isBlank(areaCode) || StringUtils.isBlank(typeName)) {
            return null;
        }
        AccuAlarm accuAlarm = new AccuAlarm(areaCode, null);
        accuAlarm.mAlarmAreaName = alarmAreaName;
        accuAlarm.mContentText = contentText;
        accuAlarm.mLevelName = levelName;
        accuAlarm.mTypeName = typeName;
        accuAlarm.mStartTime = DateUtils.epochDateToDate(startTime);
        accuAlarm.mPublishTime = DateUtils.epochDateToDate(startTime);
        accuAlarm.mEndTime = DateUtils.epochDateToDate(endTime);
        return accuAlarm;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAreaCode());
        dest.writeString(this.mAlarmAreaName);
        dest.writeString(this.mTypeName);
        dest.writeString(this.mLevelName);
        dest.writeString(this.mContentText);
        dest.writeLong(DateUtils.dateToEpochDate(this.mStartTime));
        dest.writeLong(DateUtils.dateToEpochDate(this.mEndTime));
    }

    static {
        CREATOR = new Creator<AccuAlarm>() {
            public AccuAlarm createFromParcel(Parcel source) {
                return new AccuAlarm().setParcel(source);
            }

            public AccuAlarm[] newArray(int size) {
                return new AccuAlarm[size];
            }
        };
    }

    public AccuAlarm setParcel(Parcel parcel) {
        return build(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readLong());
    }
}
