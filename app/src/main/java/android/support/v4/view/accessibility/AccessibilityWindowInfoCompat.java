package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityWindowInfo;

public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object object) {
        return object != null ? new AccessibilityWindowInfoCompat(object) : null;
    }

    private AccessibilityWindowInfoCompat(Object info) {
        this.mInfo = info;
    }

    public int getType() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getType() : UNDEFINED;
    }

    public int getLayer() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getLayer() : UNDEFINED;
    }

    public AccessibilityNodeInfoCompat getRoot() {
        return VERSION.SDK_INT >= 21 ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getRoot()) : null;
    }

    public AccessibilityWindowInfoCompat getParent() {
        return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getParent()) : null;
    }

    public int getId() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getId() : UNDEFINED;
    }

    public void getBoundsInScreen(Rect outBounds) {
        if (VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.mInfo).getBoundsInScreen(outBounds);
        }
    }

    public boolean isActive() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).isActive() : true;
    }

    public boolean isFocused() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).isFocused() : true;
    }

    public boolean isAccessibilityFocused() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).isAccessibilityFocused() : true;
    }

    public int getChildCount() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getChildCount() : 0;
    }

    public AccessibilityWindowInfoCompat getChild(int index) {
        return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getChild(index)) : null;
    }

    public CharSequence getTitle() {
        return VERSION.SDK_INT >= 24 ? ((AccessibilityWindowInfo) this.mInfo).getTitle() : null;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        return VERSION.SDK_INT >= 24 ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getAnchor()) : null;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(AccessibilityWindowInfo.obtain()) : null;
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat info) {
        return (VERSION.SDK_INT < 21 || info == null) ? null : wrapNonNullInstance(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) info.mInfo));
    }

    public void recycle() {
        if (VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.mInfo).recycle();
        }
    }

    public int hashCode() {
        return this.mInfo == null ? 0 : this.mInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat other = (AccessibilityWindowInfoCompat) obj;
        return this.mInfo == null ? other.mInfo == null : this.mInfo.equals(other.mInfo);
    }

    public String toString() {
        boolean z;
        boolean z2 = true;
        StringBuilder builder = new StringBuilder();
        Rect bounds = new Rect();
        getBoundsInScreen(bounds);
        builder.append("AccessibilityWindowInfo[");
        builder.append("id=").append(getId());
        builder.append(", type=").append(typeToString(getType()));
        builder.append(", layer=").append(getLayer());
        builder.append(", bounds=").append(bounds);
        builder.append(", focused=").append(isFocused());
        builder.append(", active=").append(isActive());
        StringBuilder append = builder.append(", hasParent=");
        if (getParent() != null) {
            z = true;
        } else {
            z = false;
        }
        append.append(z);
        StringBuilder append2 = builder.append(", hasChildren=");
        if (getChildCount() <= 0) {
            z2 = false;
        }
        append2.append(z2);
        builder.append(']');
        return builder.toString();
    }

    private static String typeToString(int type) {
        switch (type) {
            case TYPE_APPLICATION:
                return "TYPE_APPLICATION";
            case TYPE_INPUT_METHOD:
                return "TYPE_INPUT_METHOD";
            case TYPE_SYSTEM:
                return "TYPE_SYSTEM";
            case TYPE_ACCESSIBILITY_OVERLAY:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }
}
