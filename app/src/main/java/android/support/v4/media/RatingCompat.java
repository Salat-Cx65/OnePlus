package android.support.v4.media;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.widget.AutoScrollHelper;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat implements Parcelable {
    public static final Creator<RatingCompat> CREATOR;
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface StarStyle {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Style {
    }

    RatingCompat(int ratingStyle, float rating) {
        this.mRatingStyle = ratingStyle;
        this.mRatingValue = rating;
    }

    public String toString() {
        String str;
        StringBuilder append = new StringBuilder().append("Rating:style=").append(this.mRatingStyle).append(" rating=");
        if (this.mRatingValue < 0.0f) {
            str = "unrated";
        } else {
            str = String.valueOf(this.mRatingValue);
        }
        return append.append(str).toString();
    }

    public int describeContents() {
        return this.mRatingStyle;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRatingStyle);
        dest.writeFloat(this.mRatingValue);
    }

    static {
        CREATOR = new Creator<RatingCompat>() {
            public RatingCompat createFromParcel(Parcel p) {
                return new RatingCompat(p.readInt(), p.readFloat());
            }

            public RatingCompat[] newArray(int size) {
                return new RatingCompat[size];
            }
        };
    }

    public static RatingCompat newUnratedRating(int ratingStyle) {
        switch (ratingStyle) {
            case RATING_HEART:
            case RATING_THUMB_UP_DOWN:
            case RATING_3_STARS:
            case RATING_4_STARS:
            case RATING_5_STARS:
            case RATING_PERCENTAGE:
                return new RatingCompat(ratingStyle, -1.0f);
            default:
                return null;
        }
    }

    public static RatingCompat newHeartRating(boolean hasHeart) {
        return new RatingCompat(1, hasHeart ? DefaultRetryPolicy.DEFAULT_BACKOFF_MULT : AutoScrollHelper.RELATIVE_UNSPECIFIED);
    }

    public static RatingCompat newThumbRating(boolean thumbIsUp) {
        return new RatingCompat(2, thumbIsUp ? DefaultRetryPolicy.DEFAULT_BACKOFF_MULT : AutoScrollHelper.RELATIVE_UNSPECIFIED);
    }

    public static RatingCompat newStarRating(int starRatingStyle, float starRating) {
        float maxRating;
        switch (starRatingStyle) {
            case RATING_3_STARS:
                maxRating = 3.0f;
                break;
            case RATING_4_STARS:
                maxRating = 4.0f;
                break;
            case RATING_5_STARS:
                maxRating = 5.0f;
                break;
            default:
                Log.e(TAG, "Invalid rating style (" + starRatingStyle + ") for a star rating");
                return null;
        }
        if (starRating >= 0.0f && starRating <= maxRating) {
            return new RatingCompat(starRatingStyle, starRating);
        }
        Log.e(TAG, "Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newPercentageRating(float percent) {
        if (percent >= 0.0f && percent <= 100.0f) {
            return new RatingCompat(6, percent);
        }
        Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public boolean hasHeart() {
        Object obj = RATING_HEART;
        if (this.mRatingStyle != 1) {
            return false;
        }
        boolean z;
        if (this.mRatingValue != 1.0f) {
            z = false;
        }
        return z;
    }

    public boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f;
    }

    public float getStarRating() {
        switch (this.mRatingStyle) {
            case RATING_3_STARS:
            case RATING_4_STARS:
            case RATING_5_STARS:
                if (isRated()) {
                    return this.mRatingValue;
                }
        }
        return RATING_NOT_RATED;
    }

    public float getPercentRating() {
        return (this.mRatingStyle == 6 && isRated()) ? this.mRatingValue : RATING_NOT_RATED;
    }

    public static RatingCompat fromRating(Object ratingObj) {
        RatingCompat ratingCompat = null;
        if (ratingObj != null && VERSION.SDK_INT >= 19) {
            int ratingStyle = RatingCompatKitkat.getRatingStyle(ratingObj);
            if (RatingCompatKitkat.isRated(ratingObj)) {
                switch (ratingStyle) {
                    case RATING_HEART:
                        ratingCompat = newHeartRating(RatingCompatKitkat.hasHeart(ratingObj));
                        break;
                    case RATING_THUMB_UP_DOWN:
                        ratingCompat = newThumbRating(RatingCompatKitkat.isThumbUp(ratingObj));
                        break;
                    case RATING_3_STARS:
                    case RATING_4_STARS:
                    case RATING_5_STARS:
                        ratingCompat = newStarRating(ratingStyle, RatingCompatKitkat.getStarRating(ratingObj));
                        break;
                    case RATING_PERCENTAGE:
                        ratingCompat = newPercentageRating(RatingCompatKitkat.getPercentRating(ratingObj));
                        break;
                }
            } else {
                ratingCompat = newUnratedRating(ratingStyle);
            }
            ratingCompat.mRatingObj = ratingObj;
        }
        return ratingCompat;
    }

    public Object getRating() {
        if (this.mRatingObj == null && VERSION.SDK_INT >= 19) {
            if (isRated()) {
                switch (this.mRatingStyle) {
                    case RATING_HEART:
                        this.mRatingObj = RatingCompatKitkat.newHeartRating(hasHeart());
                        break;
                    case RATING_THUMB_UP_DOWN:
                        this.mRatingObj = RatingCompatKitkat.newThumbRating(isThumbUp());
                        break;
                    case RATING_3_STARS:
                    case RATING_4_STARS:
                    case RATING_5_STARS:
                        this.mRatingObj = RatingCompatKitkat.newStarRating(this.mRatingStyle, getStarRating());
                        break;
                    case RATING_PERCENTAGE:
                        this.mRatingObj = RatingCompatKitkat.newPercentageRating(getPercentRating());
                        break;
                    default:
                        return null;
                }
            }
            this.mRatingObj = RatingCompatKitkat.newUnratedRating(this.mRatingStyle);
        }
        return this.mRatingObj;
    }
}
