package android.support.v4.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.FocusStrategy.BoundsAdapter;
import android.support.v4.widget.FocusStrategy.CollectionAdapter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.R;
import net.oneplus.weather.api.WeatherRequest.Type;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    private static final String DEFAULT_CLASS_NAME = "android.view.View";
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS;
    private static final BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER;
    private static final CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER;
    private int mAccessibilityFocusedVirtualViewId;
    private final View mHost;
    private int mHoveredVirtualViewId;
    private int mKeyboardFocusedVirtualViewId;
    private final AccessibilityManager mManager;
    private MyNodeProvider mNodeProvider;
    private final int[] mTempGlobalRect;
    private final Rect mTempParentRect;
    private final Rect mTempScreenRect;
    private final Rect mTempVisibleRect;

    private class MyNodeProvider extends AccessibilityNodeProviderCompat {
        MyNodeProvider() {
        }

        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(virtualViewId));
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            return ExploreByTouchHelper.this.performAction(virtualViewId, action, arguments);
        }

        public AccessibilityNodeInfoCompat findFocus(int focusType) {
            int focusedId = focusType == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId;
            return focusedId == Integer.MIN_VALUE ? null : createAccessibilityNodeInfo(focusedId);
        }
    }

    protected abstract int getVirtualViewAt(float f, float f2);

    protected abstract void getVisibleVirtualViews(List<Integer> list);

    protected abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

    protected abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    static {
        INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        NODE_ADAPTER = new BoundsAdapter<AccessibilityNodeInfoCompat>() {
            public void obtainBounds(AccessibilityNodeInfoCompat node, Rect outBounds) {
                node.getBoundsInParent(outBounds);
            }
        };
        SPARSE_VALUES_ADAPTER = new CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>() {
            public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> collection, int index) {
                return (AccessibilityNodeInfoCompat) collection.valueAt(index);
            }

            public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> collection) {
                return collection.size();
            }
        };
    }

    public ExploreByTouchHelper(View host) {
        this.mTempScreenRect = new Rect();
        this.mTempParentRect = new Rect();
        this.mTempVisibleRect = new Rect();
        this.mTempGlobalRect = new int[2];
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mHoveredVirtualViewId = Integer.MIN_VALUE;
        if (host == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.mHost = host;
        this.mManager = (AccessibilityManager) host.getContext().getSystemService("accessibility");
        host.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(host) == 0) {
            ViewCompat.setImportantForAccessibility(host, 1);
        }
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider();
        }
        return this.mNodeProvider;
    }

    public final boolean dispatchHoverEvent(@NonNull MotionEvent event) {
        Object obj = 1;
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case DetectedActivity.WALKING:
            case ConnectionResult.SERVICE_INVALID:
                boolean z;
                int virtualViewId = getVirtualViewAt(event.getX(), event.getY());
                updateHoveredVirtualView(virtualViewId);
                if (virtualViewId == Integer.MIN_VALUE) {
                    z = false;
                }
                return z;
            case ConnectionResult.DEVELOPER_ERROR:
                if (this.mAccessibilityFocusedVirtualViewId == Integer.MIN_VALUE) {
                    return false;
                }
                updateHoveredVirtualView(INVALID_ID);
                return true;
            default:
                return false;
        }
    }

    public final boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == 1) {
            return false;
        }
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
            case ConnectionResult.RESTRICTED_PROFILE:
            case R.styleable.Toolbar_titleMargin:
            case R.styleable.Toolbar_titleMarginBottom:
                if (!event.hasNoModifiers()) {
                    return false;
                }
                int direction = keyToDirection(keyCode);
                int count = event.getRepeatCount() + 1;
                for (int i = 0; i < count && moveFocus(direction, null); i++) {
                    handled = true;
                }
                return handled;
            case R.styleable.Toolbar_titleMarginEnd:
            case R.styleable.AppCompatTheme_editTextColor:
                if (!event.hasNoModifiers() || event.getRepeatCount() != 0) {
                    return false;
                }
                clickKeyboardFocusedVirtualView();
                return true;
            case R.styleable.AppCompatTheme_dividerHorizontal:
                if (event.hasNoModifiers()) {
                    return moveFocus(RainSurfaceView.RAIN_LEVEL_SHOWER, null);
                }
                return event.hasModifiers(1) ? moveFocus(1, null) : false;
            default:
                return false;
        }
    }

    public final void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId);
        }
        if (gainFocus) {
            moveFocus(direction, previouslyFocusedRect);
        }
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.mAccessibilityFocusedVirtualViewId;
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.mKeyboardFocusedVirtualViewId;
    }

    private static int keyToDirection(int keyCode) {
        switch (keyCode) {
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
                return R.styleable.OneplusTheme_op_borderWidth;
            case R.styleable.Toolbar_titleMargin:
                return ConnectionResult.SIGN_IN_FAILED;
            case R.styleable.Toolbar_titleMarginBottom:
                return R.styleable.AppCompatTheme_editTextColor;
            default:
                return 130;
        }
    }

    private void getBoundsInParent(int virtualViewId, Rect outBounds) {
        obtainAccessibilityNodeInfo(virtualViewId).getBoundsInParent(outBounds);
    }

    private boolean moveFocus(int direction, @Nullable Rect previouslyFocusedRect) {
        AccessibilityNodeInfoCompat focusedNode;
        AccessibilityNodeInfoCompat nextFocusedNode;
        SparseArrayCompat<AccessibilityNodeInfoCompat> allNodes = getAllNodes();
        int focusedNodeId = this.mKeyboardFocusedVirtualViewId;
        if (focusedNodeId == Integer.MIN_VALUE) {
            focusedNode = null;
        } else {
            focusedNode = (AccessibilityNodeInfoCompat) allNodes.get(focusedNodeId);
        }
        switch (direction) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                nextFocusedNode = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInRelativeDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, focusedNode, direction, ViewCompat.getLayoutDirection(this.mHost) == 1, false);
                break;
            case ConnectionResult.SIGN_IN_FAILED:
            case R.styleable.OneplusTheme_op_borderWidth:
            case R.styleable.AppCompatTheme_editTextColor:
            case 130:
                Rect selectedRect = new Rect();
                if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
                    getBoundsInParent(this.mKeyboardFocusedVirtualViewId, selectedRect);
                } else if (previouslyFocusedRect != null) {
                    selectedRect.set(previouslyFocusedRect);
                } else {
                    guessPreviouslyFocusedRect(this.mHost, direction, selectedRect);
                }
                nextFocusedNode = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInAbsoluteDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, focusedNode, selectedRect, direction);
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return requestKeyboardFocusForVirtualView(nextFocusedNode == null ? INVALID_ID : allNodes.keyAt(allNodes.indexOfValue(nextFocusedNode)));
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> getAllNodes() {
        List<Integer> virtualViewIds = new ArrayList();
        getVisibleVirtualViews(virtualViewIds);
        SparseArrayCompat<AccessibilityNodeInfoCompat> allNodes = new SparseArrayCompat();
        for (int virtualViewId = 0; virtualViewId < virtualViewIds.size(); virtualViewId++) {
            allNodes.put(virtualViewId, createNodeForChild(virtualViewId));
        }
        return allNodes;
    }

    private static Rect guessPreviouslyFocusedRect(@NonNull View host, int direction, @NonNull Rect outBounds) {
        int w = host.getWidth();
        int h = host.getHeight();
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
                outBounds.set(w, 0, w, h);
                break;
            case R.styleable.OneplusTheme_op_borderWidth:
                outBounds.set(0, h, w, h);
                break;
            case R.styleable.AppCompatTheme_editTextColor:
                outBounds.set(HOST_ID, 0, HOST_ID, h);
                break;
            case 130:
                outBounds.set(0, HOST_ID, w, HOST_ID);
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return outBounds;
    }

    private boolean clickKeyboardFocusedVirtualView() {
        return this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE && onPerformActionForVirtualView(this.mKeyboardFocusedVirtualViewId, ConnectionResult.API_UNAVAILABLE, null);
    }

    public final boolean sendEventForVirtualView(int virtualViewId, int eventType) {
        if (virtualViewId == Integer.MIN_VALUE || !this.mManager.isEnabled()) {
            return false;
        }
        ViewParent parent = this.mHost.getParent();
        if (parent == null) {
            return false;
        }
        return ViewParentCompat.requestSendAccessibilityEvent(parent, this.mHost, createEvent(virtualViewId, eventType));
    }

    public final void invalidateRoot() {
        invalidateVirtualView(HOST_ID, 1);
    }

    public final void invalidateVirtualView(int virtualViewId) {
        invalidateVirtualView(virtualViewId, 0);
    }

    public final void invalidateVirtualView(int virtualViewId, int changeTypes) {
        if (virtualViewId != Integer.MIN_VALUE && this.mManager.isEnabled()) {
            ViewParent parent = this.mHost.getParent();
            if (parent != null) {
                AccessibilityEvent event = createEvent(virtualViewId, CitySearchProvider.PROVIDER_ACCU_WEATHER);
                AccessibilityEventCompat.setContentChangeTypes(event, changeTypes);
                ViewParentCompat.requestSendAccessibilityEvent(parent, this.mHost, event);
            }
        }
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    protected void onVirtualViewKeyboardFocusChanged(int virtualViewId, boolean hasFocus) {
    }

    private void updateHoveredVirtualView(int virtualViewId) {
        if (this.mHoveredVirtualViewId != virtualViewId) {
            int previousVirtualViewId = this.mHoveredVirtualViewId;
            this.mHoveredVirtualViewId = virtualViewId;
            sendEventForVirtualView(virtualViewId, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            sendEventForVirtualView(previousVirtualViewId, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        }
    }

    private AccessibilityEvent createEvent(int virtualViewId, int eventType) {
        switch (virtualViewId) {
            case HOST_ID:
                return createEventForHost(eventType);
            default:
                return createEventForChild(virtualViewId, eventType);
        }
    }

    private AccessibilityEvent createEventForHost(int eventType) {
        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        this.mHost.onInitializeAccessibilityEvent(event);
        return event;
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        onPopulateEventForHost(event);
    }

    private AccessibilityEvent createEventForChild(int virtualViewId, int eventType) {
        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        AccessibilityNodeInfoCompat node = obtainAccessibilityNodeInfo(virtualViewId);
        event.getText().add(node.getText());
        event.setContentDescription(node.getContentDescription());
        event.setScrollable(node.isScrollable());
        event.setPassword(node.isPassword());
        event.setEnabled(node.isEnabled());
        event.setChecked(node.isChecked());
        onPopulateEventForVirtualView(virtualViewId, event);
        if (event.getText().isEmpty() && event.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        event.setClassName(node.getClassName());
        AccessibilityRecordCompat.setSource(event, this.mHost, virtualViewId);
        event.setPackageName(this.mHost.getContext().getPackageName());
        return event;
    }

    @NonNull
    AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int virtualViewId) {
        return virtualViewId == -1 ? createNodeForHost() : createNodeForChild(virtualViewId);
    }

    @NonNull
    private AccessibilityNodeInfoCompat createNodeForHost() {
        AccessibilityNodeInfoCompat info = AccessibilityNodeInfoCompat.obtain(this.mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, info);
        ArrayList<Integer> virtualViewIds = new ArrayList();
        getVisibleVirtualViews(virtualViewIds);
        if (info.getChildCount() <= 0 || virtualViewIds.size() <= 0) {
            int count = virtualViewIds.size();
            for (int i = 0; i < count; i++) {
                info.addChild(this.mHost, ((Integer) virtualViewIds.get(i)).intValue());
            }
            return info;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        onPopulateNodeForHost(info);
    }

    @NonNull
    private AccessibilityNodeInfoCompat createNodeForChild(int virtualViewId) {
        AccessibilityNodeInfoCompat node = AccessibilityNodeInfoCompat.obtain();
        node.setEnabled(true);
        node.setFocusable(true);
        node.setClassName(DEFAULT_CLASS_NAME);
        node.setBoundsInParent(INVALID_PARENT_BOUNDS);
        node.setBoundsInScreen(INVALID_PARENT_BOUNDS);
        node.setParent(this.mHost);
        onPopulateNodeForVirtualView(virtualViewId, node);
        if (node.getText() == null && node.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        node.getBoundsInParent(this.mTempParentRect);
        if (this.mTempParentRect.equals(INVALID_PARENT_BOUNDS)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = node.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        } else {
            boolean isFocused;
            node.setPackageName(this.mHost.getContext().getPackageName());
            node.setSource(this.mHost, virtualViewId);
            if (this.mAccessibilityFocusedVirtualViewId == virtualViewId) {
                node.setAccessibilityFocused(true);
                node.addAction((int) AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            } else {
                node.setAccessibilityFocused(false);
                node.addAction((int) Type.SUCCESS);
            }
            if (this.mKeyboardFocusedVirtualViewId == virtualViewId) {
                isFocused = true;
            } else {
                isFocused = false;
            }
            if (isFocused) {
                node.addAction((int) RainSurfaceView.RAIN_LEVEL_SHOWER);
            } else if (node.isFocusable()) {
                node.addAction(1);
            }
            node.setFocused(isFocused);
            this.mHost.getLocationOnScreen(this.mTempGlobalRect);
            node.getBoundsInScreen(this.mTempScreenRect);
            if (this.mTempScreenRect.equals(INVALID_PARENT_BOUNDS)) {
                node.getBoundsInParent(this.mTempScreenRect);
                if (node.mParentVirtualDescendantId != -1) {
                    AccessibilityNodeInfoCompat parentNode = AccessibilityNodeInfoCompat.obtain();
                    for (int virtualDescendantId = node.mParentVirtualDescendantId; virtualDescendantId != -1; virtualDescendantId = parentNode.mParentVirtualDescendantId) {
                        parentNode.setParent(this.mHost, HOST_ID);
                        parentNode.setBoundsInParent(INVALID_PARENT_BOUNDS);
                        onPopulateNodeForVirtualView(virtualDescendantId, parentNode);
                        parentNode.getBoundsInParent(this.mTempParentRect);
                        this.mTempScreenRect.offset(this.mTempParentRect.left, this.mTempParentRect.top);
                    }
                    parentNode.recycle();
                }
                this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
            }
            if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
                this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                    node.setBoundsInScreen(this.mTempScreenRect);
                    if (isVisibleToUser(this.mTempScreenRect)) {
                        node.setVisibleToUser(true);
                    }
                }
            }
            return node;
        }
    }

    boolean performAction(int virtualViewId, int action, Bundle arguments) {
        switch (virtualViewId) {
            case HOST_ID:
                return performActionForHost(action, arguments);
            default:
                return performActionForChild(virtualViewId, action, arguments);
        }
    }

    private boolean performActionForHost(int action, Bundle arguments) {
        return ViewCompat.performAccessibilityAction(this.mHost, action, arguments);
    }

    private boolean performActionForChild(int virtualViewId, int action, Bundle arguments) {
        switch (action) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return requestKeyboardFocusForVirtualView(virtualViewId);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return clearKeyboardFocusForVirtualView(virtualViewId);
            case Type.SUCCESS:
                return requestAccessibilityFocus(virtualViewId);
            case AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS:
                return clearAccessibilityFocus(virtualViewId);
            default:
                return onPerformActionForVirtualView(virtualViewId, action, arguments);
        }
    }

    private boolean isVisibleToUser(Rect localRect) {
        if (localRect == null || localRect.isEmpty() || this.mHost.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent viewParent = this.mHost.getParent();
        while (viewParent instanceof View) {
            View view = (View) viewParent;
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            viewParent = view.getParent();
        }
        return viewParent != null;
    }

    private boolean requestAccessibilityFocus(int virtualViewId) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled() || this.mAccessibilityFocusedVirtualViewId == virtualViewId) {
            return false;
        }
        if (this.mAccessibilityFocusedVirtualViewId != Integer.MIN_VALUE) {
            clearAccessibilityFocus(this.mAccessibilityFocusedVirtualViewId);
        }
        this.mAccessibilityFocusedVirtualViewId = virtualViewId;
        this.mHost.invalidate();
        sendEventForVirtualView(virtualViewId, AccessibilityNodeInfoCompat.ACTION_PASTE);
        return true;
    }

    private boolean clearAccessibilityFocus(int virtualViewId) {
        if (this.mAccessibilityFocusedVirtualViewId != virtualViewId) {
            return false;
        }
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mHost.invalidate();
        sendEventForVirtualView(virtualViewId, AccessibilityNodeInfoCompat.ACTION_CUT);
        return true;
    }

    public final boolean requestKeyboardFocusForVirtualView(int virtualViewId) {
        if ((!this.mHost.isFocused() && !this.mHost.requestFocus()) || this.mKeyboardFocusedVirtualViewId == virtualViewId) {
            return false;
        }
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId);
        }
        this.mKeyboardFocusedVirtualViewId = virtualViewId;
        onVirtualViewKeyboardFocusChanged(virtualViewId, true);
        sendEventForVirtualView(virtualViewId, DetectedActivity.RUNNING);
        return true;
    }

    public final boolean clearKeyboardFocusForVirtualView(int virtualViewId) {
        if (this.mKeyboardFocusedVirtualViewId != virtualViewId) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(virtualViewId, false);
        sendEventForVirtualView(virtualViewId, DetectedActivity.RUNNING);
        return true;
    }

    protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
    }

    protected void onPopulateEventForHost(AccessibilityEvent event) {
    }

    protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat node) {
    }
}
