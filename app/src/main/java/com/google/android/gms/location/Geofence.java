package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.zzcfd;

public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public static final class Builder {
        private String zzQz;
        private int zzbhJ;
        private long zzbhK;
        private short zzbhL;
        private double zzbhM;
        private double zzbhN;
        private float zzbhO;
        private int zzbhP;
        private int zzbhQ;

        public Builder() {
            this.zzQz = null;
            this.zzbhJ = 0;
            this.zzbhK = Long.MIN_VALUE;
            this.zzbhL = (short) -1;
            this.zzbhP = 0;
            this.zzbhQ = -1;
        }

        public final Geofence build() {
            if (this.zzQz == null) {
                throw new IllegalArgumentException("Request ID not set.");
            } else if (this.zzbhJ == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            } else if ((this.zzbhJ & 4) != 0 && this.zzbhQ < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            } else if (this.zzbhK == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            } else if (this.zzbhL == (short) -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            } else if (this.zzbhP >= 0) {
                return new zzcfd(this.zzQz, this.zzbhJ, (short) 1, this.zzbhM, this.zzbhN, this.zzbhO, this.zzbhK, this.zzbhP, this.zzbhQ);
            } else {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            }
        }

        public final com.google.android.gms.location.Geofence.Builder setCircularRegion(double d, double d2, float f) {
            this.zzbhL = (short) 1;
            this.zzbhM = d;
            this.zzbhN = d2;
            this.zzbhO = f;
            return this;
        }

        public final com.google.android.gms.location.Geofence.Builder setExpirationDuration(long j) {
            if (j < 0) {
                this.zzbhK = -1;
            } else {
                this.zzbhK = SystemClock.elapsedRealtime() + j;
            }
            return this;
        }

        public final com.google.android.gms.location.Geofence.Builder setLoiteringDelay(int i) {
            this.zzbhQ = i;
            return this;
        }

        public final com.google.android.gms.location.Geofence.Builder setNotificationResponsiveness(int i) {
            this.zzbhP = i;
            return this;
        }

        public final com.google.android.gms.location.Geofence.Builder setRequestId(String str) {
            this.zzQz = str;
            return this;
        }

        public final com.google.android.gms.location.Geofence.Builder setTransitionTypes(int i) {
            this.zzbhJ = i;
            return this;
        }
    }

    String getRequestId();
}
