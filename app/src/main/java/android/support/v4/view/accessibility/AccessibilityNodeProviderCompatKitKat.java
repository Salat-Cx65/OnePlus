package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@RequiresApi(19)
class AccessibilityNodeProviderCompatKitKat {

    static class AnonymousClass_1 extends AccessibilityNodeProvider {
        final /* synthetic */ AccessibilityNodeInfoBridge val$bridge;

        AnonymousClass_1(AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
            this.val$bridge = accessibilityNodeInfoBridge;
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            return (AccessibilityNodeInfo) this.val$bridge.createAccessibilityNodeInfo(virtualViewId);
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text, int virtualViewId) {
            return this.val$bridge.findAccessibilityNodeInfosByText(text, virtualViewId);
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            return this.val$bridge.performAction(virtualViewId, action, arguments);
        }

        public AccessibilityNodeInfo findFocus(int focus) {
            return (AccessibilityNodeInfo) this.val$bridge.findFocus(focus);
        }
    }

    static interface AccessibilityNodeInfoBridge {
        Object createAccessibilityNodeInfo(int i);

        List<Object> findAccessibilityNodeInfosByText(String str, int i);

        Object findFocus(int i);

        boolean performAction(int i, int i2, Bundle bundle);
    }

    AccessibilityNodeProviderCompatKitKat() {
    }

    public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge bridge) {
        return new AnonymousClass_1(bridge);
    }
}
