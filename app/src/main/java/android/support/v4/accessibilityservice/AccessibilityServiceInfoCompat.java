package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;

public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    @Deprecated
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    private static final AccessibilityServiceInfoBaseImpl IMPL;

    static class AccessibilityServiceInfoBaseImpl {
        AccessibilityServiceInfoBaseImpl() {
        }

        public int getCapabilities(AccessibilityServiceInfo info) {
            return AccessibilityServiceInfoCompat.getCanRetrieveWindowContent(info) ? DEFAULT : 0;
        }

        public String loadDescription(AccessibilityServiceInfo info, PackageManager pm) {
            return null;
        }
    }

    @RequiresApi(16)
    static class AccessibilityServiceInfoApi16Impl extends AccessibilityServiceInfoBaseImpl {
        AccessibilityServiceInfoApi16Impl() {
        }

        public String loadDescription(AccessibilityServiceInfo info, PackageManager pm) {
            return info.loadDescription(pm);
        }
    }

    @RequiresApi(18)
    static class AccessibilityServiceInfoApi18Impl extends AccessibilityServiceInfoApi16Impl {
        AccessibilityServiceInfoApi18Impl() {
        }

        public int getCapabilities(AccessibilityServiceInfo info) {
            return info.getCapabilities();
        }
    }

    static {
        if (VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityServiceInfoApi18Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityServiceInfoApi16Impl();
        } else {
            IMPL = new AccessibilityServiceInfoBaseImpl();
        }
    }

    private AccessibilityServiceInfoCompat() {
    }

    @Deprecated
    public static String getId(AccessibilityServiceInfo info) {
        return info.getId();
    }

    @Deprecated
    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
        return info.getResolveInfo();
    }

    @Deprecated
    public static String getSettingsActivityName(AccessibilityServiceInfo info) {
        return info.getSettingsActivityName();
    }

    @Deprecated
    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
        return info.getCanRetrieveWindowContent();
    }

    @Deprecated
    public static String getDescription(AccessibilityServiceInfo info) {
        return info.getDescription();
    }

    public static String loadDescription(AccessibilityServiceInfo info, PackageManager packageManager) {
        return IMPL.loadDescription(info, packageManager);
    }

    public static String feedbackTypeToString(int feedbackType) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        while (feedbackType > 0) {
            int feedbackTypeFlag = 1 << Integer.numberOfTrailingZeros(feedbackType);
            feedbackType &= feedbackTypeFlag ^ -1;
            if (builder.length() > 1) {
                builder.append(", ");
            }
            switch (feedbackTypeFlag) {
                case DEFAULT:
                    builder.append("FEEDBACK_SPOKEN");
                    break;
                case FLAG_INCLUDE_NOT_IMPORTANT_VIEWS:
                    builder.append("FEEDBACK_HAPTIC");
                    break;
                case FLAG_REQUEST_TOUCH_EXPLORATION_MODE:
                    builder.append("FEEDBACK_AUDIBLE");
                    break;
                case FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY:
                    builder.append("FEEDBACK_VISUAL");
                    break;
                case FLAG_REPORT_VIEW_IDS:
                    builder.append("FEEDBACK_GENERIC");
                    break;
                default:
                    break;
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public static String flagToString(int flag) {
        switch (flag) {
            case DEFAULT:
                return "DEFAULT";
            case FLAG_INCLUDE_NOT_IMPORTANT_VIEWS:
                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
            case FLAG_REQUEST_TOUCH_EXPLORATION_MODE:
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
            case FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY:
                return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case FLAG_REPORT_VIEW_IDS:
                return "FLAG_REPORT_VIEW_IDS";
            case FLAG_REQUEST_FILTER_KEY_EVENTS:
                return "FLAG_REQUEST_FILTER_KEY_EVENTS";
            default:
                return null;
        }
    }

    public static int getCapabilities(AccessibilityServiceInfo info) {
        return IMPL.getCapabilities(info);
    }

    public static String capabilityToString(int capability) {
        switch (capability) {
            case DEFAULT:
                return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
            case FLAG_INCLUDE_NOT_IMPORTANT_VIEWS:
                return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
            case FLAG_REQUEST_TOUCH_EXPLORATION_MODE:
                return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY:
                return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
            default:
                return "UNKNOWN";
        }
    }
}
