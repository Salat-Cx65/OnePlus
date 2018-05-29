package com.google.android.gms.common.api;

import android.support.annotation.NonNull;

public class CommonStatusCodes {
    public static final int API_NOT_CONNECTED = 17;
    public static final int CANCELED = 16;
    public static final int DEAD_CLIENT = 18;
    public static final int DEVELOPER_ERROR = 10;
    public static final int ERROR = 13;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 14;
    public static final int INVALID_ACCOUNT = 5;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    @Deprecated
    public static final int SERVICE_DISABLED = 3;
    @Deprecated
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int SUCCESS_CACHE = -1;
    public static final int TIMEOUT = 15;

    protected CommonStatusCodes() {
    }

    @NonNull
    public static String getStatusCodeString(int i) {
        switch (i) {
            case SUCCESS_CACHE:
                return "SUCCESS_CACHE";
            case SUCCESS:
                return "SUCCESS";
            case SERVICE_VERSION_UPDATE_REQUIRED:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case SERVICE_DISABLED:
                return "SERVICE_DISABLED";
            case SIGN_IN_REQUIRED:
                return "SIGN_IN_REQUIRED";
            case INVALID_ACCOUNT:
                return "INVALID_ACCOUNT";
            case RESOLUTION_REQUIRED:
                return "RESOLUTION_REQUIRED";
            case NETWORK_ERROR:
                return "NETWORK_ERROR";
            case INTERNAL_ERROR:
                return "INTERNAL_ERROR";
            case DEVELOPER_ERROR:
                return "DEVELOPER_ERROR";
            case ERROR:
                return "ERROR";
            case INTERRUPTED:
                return "INTERRUPTED";
            case TIMEOUT:
                return "TIMEOUT";
            case CANCELED:
                return "CANCELED";
            case API_NOT_CONNECTED:
                return "API_NOT_CONNECTED";
            case DEAD_CLIENT:
                return "DEAD_CLIENT";
            default:
                return new StringBuilder(32).append("unknown status code: ").append(i).toString();
        }
    }
}
