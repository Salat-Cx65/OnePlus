package android.support.v4.view;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

public class AccessibilityDelegateCompat {
    private static final AccessibilityDelegate DEFAULT_DELEGATE;
    private static final AccessibilityDelegateBaseImpl IMPL;
    final AccessibilityDelegate mBridge;

    static class AccessibilityDelegateBaseImpl {

        class AnonymousClass_1 extends AccessibilityDelegate {
            final /* synthetic */ AccessibilityDelegateCompat val$compat;

            AnonymousClass_1(AccessibilityDelegateCompat accessibilityDelegateCompat) {
                this.val$compat = accessibilityDelegateCompat;
            }

            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                return this.val$compat.dispatchPopulateAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                this.val$compat.onInitializeAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                this.val$compat.onInitializeAccessibilityNodeInfo(host, AccessibilityNodeInfoCompat.wrap(info));
            }

            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                this.val$compat.onPopulateAccessibilityEvent(host, event);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                return this.val$compat.onRequestSendAccessibilityEvent(host, child, event);
            }

            public void sendAccessibilityEvent(View host, int eventType) {
                this.val$compat.sendAccessibilityEvent(host, eventType);
            }

            public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
                this.val$compat.sendAccessibilityEventUnchecked(host, event);
            }
        }

        AccessibilityDelegateBaseImpl() {
        }

        public AccessibilityDelegate newAccessibilityDelegateBridge(AccessibilityDelegateCompat compat) {
            return new AnonymousClass_1(compat);
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(AccessibilityDelegate delegate, View host) {
            return null;
        }

        public boolean performAccessibilityAction(AccessibilityDelegate delegate, View host, int action, Bundle args) {
            return false;
        }
    }

    @RequiresApi(16)
    static class AccessibilityDelegateApi16Impl extends AccessibilityDelegateBaseImpl {

        class AnonymousClass_1 extends AccessibilityDelegate {
            final /* synthetic */ AccessibilityDelegateCompat val$compat;

            AnonymousClass_1(AccessibilityDelegateCompat accessibilityDelegateCompat) {
                this.val$compat = accessibilityDelegateCompat;
            }

            public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                return this.val$compat.dispatchPopulateAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                this.val$compat.onInitializeAccessibilityEvent(host, event);
            }

            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                this.val$compat.onInitializeAccessibilityNodeInfo(host, AccessibilityNodeInfoCompat.wrap(info));
            }

            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                this.val$compat.onPopulateAccessibilityEvent(host, event);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                return this.val$compat.onRequestSendAccessibilityEvent(host, child, event);
            }

            public void sendAccessibilityEvent(View host, int eventType) {
                this.val$compat.sendAccessibilityEvent(host, eventType);
            }

            public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
                this.val$compat.sendAccessibilityEventUnchecked(host, event);
            }

            public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
                AccessibilityNodeProviderCompat provider = this.val$compat.getAccessibilityNodeProvider(host);
                return provider != null ? (AccessibilityNodeProvider) provider.getProvider() : null;
            }

            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                return this.val$compat.performAccessibilityAction(host, action, args);
            }
        }

        AccessibilityDelegateApi16Impl() {
        }

        public AccessibilityDelegate newAccessibilityDelegateBridge(AccessibilityDelegateCompat compat) {
            return new AnonymousClass_1(compat);
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(AccessibilityDelegate delegate, View host) {
            AccessibilityNodeProvider provider = delegate.getAccessibilityNodeProvider(host);
            return provider != null ? new AccessibilityNodeProviderCompat(provider) : null;
        }

        public boolean performAccessibilityAction(AccessibilityDelegate delegate, View host, int action, Bundle args) {
            return delegate.performAccessibilityAction(host, action, args);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityDelegateApi16Impl();
        } else {
            IMPL = new AccessibilityDelegateBaseImpl();
        }
        DEFAULT_DELEGATE = new AccessibilityDelegate();
    }

    public AccessibilityDelegateCompat() {
        this.mBridge = IMPL.newAccessibilityDelegateBridge(this);
    }

    AccessibilityDelegate getBridge() {
        return this.mBridge;
    }

    public void sendAccessibilityEvent(View host, int eventType) {
        DEFAULT_DELEGATE.sendAccessibilityEvent(host, eventType);
    }

    public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
        DEFAULT_DELEGATE.sendAccessibilityEventUnchecked(host, event);
    }

    public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
        return DEFAULT_DELEGATE.dispatchPopulateAccessibilityEvent(host, event);
    }

    public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
        DEFAULT_DELEGATE.onPopulateAccessibilityEvent(host, event);
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        DEFAULT_DELEGATE.onInitializeAccessibilityEvent(host, event);
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        DEFAULT_DELEGATE.onInitializeAccessibilityNodeInfo(host, info.unwrap());
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
        return DEFAULT_DELEGATE.onRequestSendAccessibilityEvent(host, child, event);
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
        return IMPL.getAccessibilityNodeProvider(DEFAULT_DELEGATE, host);
    }

    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        return IMPL.performAccessibilityAction(DEFAULT_DELEGATE, host, action, args);
    }
}