package net.oneplus.weather.app;

import android.content.Context;
import java.util.Arrays;

public class EggDetector {
    private static final long ABANDON_SCAP_TIME = 1300;
    private static final long MAX_SCAP_TIME = 700;
    private static final long MIN_SCAP_TIME = 200;
    private final Context mContext;
    private int mCurrent;
    private OnEggListerner mOnEggListerner;
    private long[] mTimes;

    public static interface OnEggListerner {
        void onSuccess(String str);
    }

    public EggDetector(Context context, int count) {
        this.mContext = context;
        this.mTimes = new long[count];
        this.mCurrent = -1;
    }

    public void tip() {
        try {
            this.mCurrent++;
            this.mCurrent %= this.mTimes.length;
            this.mTimes[this.mCurrent] = System.currentTimeMillis();
            judge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void judge() {
        long[] tmp = toScap();
        if (absSub(tmp) >= 100) {
            long avg = getAvg(tmp);
            if (avg >= 200) {
                StringBuilder builder = new StringBuilder();
                int i = 0;
                while (i < tmp.length) {
                    if (tmp[i] <= 1300 && tmp[i] > 0) {
                        if (tmp[i] > avg || tmp[i] > 700) {
                            builder.append("1");
                        } else {
                            builder.append("0");
                        }
                        i++;
                    } else {
                        return;
                    }
                }
                if (this.mOnEggListerner != null) {
                    this.mOnEggListerner.onSuccess(builder.toString());
                }
                reset();
            }
        }
    }

    private void reset() {
        Arrays.fill(this.mTimes, 0);
        this.mCurrent = -1;
    }

    private long getAvg(long[] data) {
        long sum = 0;
        for (long j : data) {
            sum += j;
        }
        return sum / ((long) data.length);
    }

    private long[] toScap() {
        long[] result = new long[(this.mTimes.length - 1)];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.mTimes[((this.mCurrent + i) + 2) % this.mTimes.length] - this.mTimes[((this.mCurrent + i) + 1) % this.mTimes.length];
        }
        return result;
    }

    public long absSub(long[] values) {
        long avg = getAvg(values);
        long sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += (values[i] - avg) * (values[i] - avg);
        }
        return (long) Math.sqrt((double) (sum / ((long) values.length)));
    }

    public void setOnEggListerner(OnEggListerner listerner) {
        this.mOnEggListerner = listerner;
    }
}
