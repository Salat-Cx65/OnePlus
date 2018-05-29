package com.oneplus.lib.widget.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;
import com.oneplus.lib.widget.recyclerview.GridLayoutManager.SpanSizeLookup;
import com.oneplus.lib.widget.recyclerview.RecyclerView.Recycler;
import com.oneplus.lib.widget.recyclerview.RecyclerView.State;
import java.util.Arrays;
import net.oneplus.weather.provider.CitySearchProvider;

public class GridLayoutManager extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    static final int MAIN_DIR_SPEC;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets;
    boolean mPendingSpanCountChange;
    final SparseIntArray mPreLayoutSpanIndexCache;
    final SparseIntArray mPreLayoutSpanSizeCache;
    View[] mSet;
    int mSpanCount;
    SpanSizeLookup mSpanSizeLookup;

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices;
        final SparseIntArray mSpanIndexCache;

        public abstract int getSpanSize(int i);

        public SpanSizeLookup() {
            this.mSpanIndexCache = new SparseIntArray();
            this.mCacheSpanIndices = false;
        }

        public void setSpanIndexCacheEnabled(boolean cacheSpanIndices) {
            this.mCacheSpanIndices = cacheSpanIndices;
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        int getCachedSpanIndex(int position, int spanCount) {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(position, spanCount);
            }
            int existing = this.mSpanIndexCache.get(position, DEFAULT_SPAN_COUNT);
            if (existing != -1) {
                return existing;
            }
            int value = getSpanIndex(position, spanCount);
            this.mSpanIndexCache.put(position, value);
            return value;
        }

        public int getSpanIndex(int position, int spanCount) {
            int positionSpanSize = getSpanSize(position);
            if (positionSpanSize == spanCount) {
                return 0;
            }
            int i = MAIN_DIR_SPEC;
            int startPos = MAIN_DIR_SPEC;
            if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0) {
                int prevKey = findReferenceIndexFromCache(position);
                if (prevKey >= 0) {
                    i = this.mSpanIndexCache.get(prevKey) + getSpanSize(prevKey);
                    startPos = prevKey + 1;
                }
            }
            for (int i2 = startPos; i2 < position; i2++) {
                int size = getSpanSize(i2);
                i += size;
                if (i == spanCount) {
                    i = MAIN_DIR_SPEC;
                } else if (i > spanCount) {
                    i = size;
                }
            }
            return i + positionSpanSize > spanCount ? 0 : i;
        }

        int findReferenceIndexFromCache(int position) {
            int lo = MAIN_DIR_SPEC;
            int hi = this.mSpanIndexCache.size() - 1;
            while (lo <= hi) {
                int mid = (lo + hi) >>> 1;
                if (this.mSpanIndexCache.keyAt(mid) < position) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            int index = lo - 1;
            return (index < 0 || index >= this.mSpanIndexCache.size()) ? DEFAULT_SPAN_COUNT : this.mSpanIndexCache.keyAt(index);
        }

        public int getSpanGroupIndex(int adapterPosition, int spanCount) {
            int span = MAIN_DIR_SPEC;
            int group = MAIN_DIR_SPEC;
            int positionSpanSize = getSpanSize(adapterPosition);
            for (int i = MAIN_DIR_SPEC; i < adapterPosition; i++) {
                int size = getSpanSize(i);
                span += size;
                if (span == spanCount) {
                    span = MAIN_DIR_SPEC;
                    group++;
                } else if (span > spanCount) {
                    span = size;
                    group++;
                }
            }
            return span + positionSpanSize > spanCount ? group + 1 : group;
        }
    }

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public int getSpanSize(int position) {
            return 1;
        }

        public int getSpanIndex(int position, int spanCount) {
            return position % spanCount;
        }
    }

    public static class LayoutParams extends com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        private int mSpanIndex;
        private int mSpanSize;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams source) {
            super(source);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    static {
        MAIN_DIR_SPEC = MeasureSpec.makeMeasureSpec(MAIN_DIR_SPEC, MAIN_DIR_SPEC);
    }

    public GridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(getProperties(context, attrs, defStyleAttr, defStyleRes).spanCount);
    }

    public GridLayoutManager(Context context, int spanCount) {
        super(context);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(spanCount);
    }

    public GridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(spanCount);
    }

    public void setStackFromEnd(boolean stackFromEnd) {
        if (stackFromEnd) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(DEBUG);
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        return state.getItemCount() < 1 ? MAIN_DIR_SPEC : getSpanGroupIndex(recycler, state, state.getItemCount() - 1);
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        return state.getItemCount() < 1 ? MAIN_DIR_SPEC : getSpanGroupIndex(recycler, state, state.getItemCount() - 1);
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View host, AccessibilityNodeInfo info) {
        android.view.ViewGroup.LayoutParams lp = host.getLayoutParams();
        if (lp instanceof LayoutParams) {
            LayoutParams glp = (LayoutParams) lp;
            int spanGroupIndex = getSpanGroupIndex(recycler, state, glp.getViewLayoutPosition());
            if (this.mOrientation == 0) {
                int spanIndex = glp.getSpanIndex();
                int spanSize = glp.getSpanSize();
                boolean z = (this.mSpanCount <= 1 || glp.getSpanSize() != this.mSpanCount) ? DEBUG : true;
                info.setCollectionItemInfo(CollectionItemInfo.obtain(spanIndex, spanSize, spanGroupIndex, 1, z, DEBUG));
                return;
            }
            int spanIndex2 = glp.getSpanIndex();
            int spanSize2 = glp.getSpanSize();
            boolean z2 = (this.mSpanCount <= 1 || glp.getSpanSize() != this.mSpanCount) ? DEBUG : true;
            info.setCollectionItemInfo(CollectionItemInfo.obtain(spanGroupIndex, 1, spanIndex2, spanSize2, z2, DEBUG));
            return;
        }
        super.onInitializeAccessibilityNodeInfoForItem(host, info);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        clearPreLayoutSpanMappingCache();
        if (!state.isPreLayout()) {
            this.mPendingSpanCountChange = false;
        }
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i = MAIN_DIR_SPEC; i < childCount; i++) {
            LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
            int viewPosition = lp.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(viewPosition, lp.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(viewPosition, lp.getSpanIndex());
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount, Object payload) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return new LayoutParams(c, attrs);
    }

    public com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        return lp instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) lp) : new LayoutParams(lp);
    }

    public boolean checkLayoutParams(com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    private void updateMeasurements() {
        int totalSpace;
        if (getOrientation() == 1) {
            totalSpace = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            totalSpace = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        calculateItemBorders(totalSpace);
    }

    private void calculateItemBorders(int totalSpace) {
        if (!(this.mCachedBorders != null && this.mCachedBorders.length == this.mSpanCount + 1 && this.mCachedBorders[this.mCachedBorders.length - 1] == totalSpace)) {
            this.mCachedBorders = new int[(this.mSpanCount + 1)];
        }
        this.mCachedBorders[0] = 0;
        int sizePerSpan = totalSpace / this.mSpanCount;
        int sizePerSpanRemainder = totalSpace % this.mSpanCount;
        int consumedPixels = MAIN_DIR_SPEC;
        int additionalSize = MAIN_DIR_SPEC;
        for (int i = 1; i <= this.mSpanCount; i++) {
            int itemSize = sizePerSpan;
            additionalSize += sizePerSpanRemainder;
            if (additionalSize > 0 && this.mSpanCount - additionalSize < sizePerSpanRemainder) {
                itemSize++;
                additionalSize -= this.mSpanCount;
            }
            consumedPixels += itemSize;
            this.mCachedBorders[i] = consumedPixels;
        }
    }

    void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo) {
        super.onAnchorReady(recycler, state, anchorInfo);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInFirstSpan(recycler, state, anchorInfo);
        }
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    private void ensureAnchorIsInFirstSpan(Recycler recycler, State state, AnchorInfo anchorInfo) {
        int span = getSpanIndex(recycler, state, anchorInfo.mPosition);
        while (span > 0 && anchorInfo.mPosition > 0) {
            anchorInfo.mPosition--;
            span = getSpanIndex(recycler, state, anchorInfo.mPosition);
        }
    }

    View findReferenceChild(Recycler recycler, State state, int start, int end, int itemCount) {
        ensureLayoutState();
        View invalidMatch = null;
        View outOfBoundsMatch = null;
        int boundsStart = this.mOrientationHelper.getStartAfterPadding();
        int boundsEnd = this.mOrientationHelper.getEndAfterPadding();
        int diff = end > start ? 1 : DEFAULT_SPAN_COUNT;
        for (int i = start; i != end; i += diff) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < itemCount && getSpanIndex(recycler, state, position) == 0) {
                if (((com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (invalidMatch == null) {
                        invalidMatch = childAt;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart(childAt) < boundsEnd && this.mOrientationHelper.getDecoratedEnd(childAt) >= boundsStart) {
                    return childAt;
                } else {
                    if (outOfBoundsMatch == null) {
                        outOfBoundsMatch = childAt;
                    }
                }
            }
        }
        if (outOfBoundsMatch == null) {
            outOfBoundsMatch = invalidMatch;
        }
        return outOfBoundsMatch;
    }

    private int getSpanGroupIndex(Recycler recycler, State state, int viewPosition) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(viewPosition, this.mSpanCount);
        }
        int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(viewPosition);
        if (adapterPosition != -1) {
            return this.mSpanSizeLookup.getSpanGroupIndex(adapterPosition, this.mSpanCount);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. " + viewPosition);
        return MAIN_DIR_SPEC;
    }

    private int getSpanIndex(Recycler recycler, State state, int pos) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(pos, this.mSpanCount);
        }
        int cached = this.mPreLayoutSpanIndexCache.get(pos, DEFAULT_SPAN_COUNT);
        if (cached != -1) {
            return cached;
        }
        int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(pos);
        if (adapterPosition != -1) {
            return this.mSpanSizeLookup.getCachedSpanIndex(adapterPosition, this.mSpanCount);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + pos);
        return MAIN_DIR_SPEC;
    }

    private int getSpanSize(Recycler recycler, State state, int pos) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(pos);
        }
        int cached = this.mPreLayoutSpanSizeCache.get(pos, DEFAULT_SPAN_COUNT);
        if (cached != -1) {
            return cached;
        }
        int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(pos);
        if (adapterPosition != -1) {
            return this.mSpanSizeLookup.getSpanSize(adapterPosition);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + pos);
        return 1;
    }

    void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult result) {
        View view;
        boolean layingOutInPrimaryDirection = layoutState.mItemDirection == 1 ? true : DEBUG;
        int count = MAIN_DIR_SPEC;
        int consumedSpanCount = MAIN_DIR_SPEC;
        int remainingSpan = this.mSpanCount;
        if (!layingOutInPrimaryDirection) {
            remainingSpan = getSpanIndex(recycler, state, layoutState.mCurrentPosition) + getSpanSize(recycler, state, layoutState.mCurrentPosition);
        }
        while (count < this.mSpanCount && layoutState.hasMore(state) && remainingSpan > 0) {
            int pos = layoutState.mCurrentPosition;
            int spanSize = getSpanSize(recycler, state, pos);
            if (spanSize <= this.mSpanCount) {
                remainingSpan -= spanSize;
                if (remainingSpan >= 0) {
                    view = layoutState.next(recycler);
                    if (view == null) {
                        break;
                    }
                    consumedSpanCount += spanSize;
                    this.mSet[count] = view;
                    count++;
                } else {
                    break;
                }
            }
            throw new IllegalArgumentException("Item at position " + pos + " requires " + spanSize + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
        }
        if (count == 0) {
            result.mFinished = true;
            return;
        }
        int i;
        int maxSize = MAIN_DIR_SPEC;
        assignSpans(recycler, state, count, consumedSpanCount, layingOutInPrimaryDirection);
        for (i = MAIN_DIR_SPEC; i < count; i++) {
            view = this.mSet[i];
            if (layoutState.mScrapList == null) {
                if (layingOutInPrimaryDirection) {
                    addView(view);
                } else {
                    addView(view, MAIN_DIR_SPEC);
                }
            } else if (layingOutInPrimaryDirection) {
                addDisappearingView(view);
            } else {
                addDisappearingView(view, MAIN_DIR_SPEC);
            }
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            int spec = MeasureSpec.makeMeasureSpec(this.mCachedBorders[lp.mSpanIndex + lp.mSpanSize] - this.mCachedBorders[lp.mSpanIndex], CitySearchProvider.GET_SEARCH_RESULT_SUCC);
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin(view, spec, getMainDirSpec(lp.height), DEBUG);
            } else {
                measureChildWithDecorationsAndMargin(view, getMainDirSpec(lp.width), spec, DEBUG);
            }
            int size = this.mOrientationHelper.getDecoratedMeasurement(view);
            if (size > maxSize) {
                maxSize = size;
            }
        }
        int maxMeasureSpec = getMainDirSpec(maxSize);
        for (i = MAIN_DIR_SPEC; i < count; i++) {
            view = this.mSet[i];
            if (this.mOrientationHelper.getDecoratedMeasurement(view) != maxSize) {
                lp = (LayoutParams) view.getLayoutParams();
                spec = MeasureSpec.makeMeasureSpec(this.mCachedBorders[lp.mSpanIndex + lp.mSpanSize] - this.mCachedBorders[lp.mSpanIndex], CitySearchProvider.GET_SEARCH_RESULT_SUCC);
                if (this.mOrientation == 1) {
                    measureChildWithDecorationsAndMargin(view, spec, maxMeasureSpec, true);
                } else {
                    measureChildWithDecorationsAndMargin(view, maxMeasureSpec, spec, true);
                }
            }
        }
        result.mConsumed = maxSize;
        int left = MAIN_DIR_SPEC;
        int right = MAIN_DIR_SPEC;
        int i2 = MAIN_DIR_SPEC;
        int i3 = MAIN_DIR_SPEC;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                i3 = layoutState.mOffset;
                i2 = i3 - maxSize;
            } else {
                i2 = layoutState.mOffset;
                i3 = i2 + maxSize;
            }
        } else if (layoutState.mLayoutDirection == -1) {
            right = layoutState.mOffset;
            left = right - maxSize;
        } else {
            left = layoutState.mOffset;
            right = left + maxSize;
        }
        for (i = MAIN_DIR_SPEC; i < count; i++) {
            view = this.mSet[i];
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            if (this.mOrientation == 1) {
                left = getPaddingLeft() + this.mCachedBorders[params.mSpanIndex];
                right = left + this.mOrientationHelper.getDecoratedMeasurementInOther(view);
            } else {
                i2 = getPaddingTop() + this.mCachedBorders[params.mSpanIndex];
                i3 = i2 + this.mOrientationHelper.getDecoratedMeasurementInOther(view);
            }
            layoutDecorated(view, left + params.leftMargin, i2 + params.topMargin, right - params.rightMargin, i3 - params.bottomMargin);
            if (params.isItemRemoved() || params.isItemChanged()) {
                result.mIgnoreConsumed = true;
            }
            result.mFocusable |= view.isFocusable();
        }
        Arrays.fill(this.mSet, null);
    }

    private int getMainDirSpec(int dim) {
        return dim < 0 ? MAIN_DIR_SPEC : MeasureSpec.makeMeasureSpec(dim, CitySearchProvider.GET_SEARCH_RESULT_SUCC);
    }

    private void measureChildWithDecorationsAndMargin(View child, int widthSpec, int heightSpec, boolean capBothSpecs) {
        calculateItemDecorationsForChild(child, this.mDecorInsets);
        com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams lp = (com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutParams) child.getLayoutParams();
        if (capBothSpecs || this.mOrientation == 1) {
            widthSpec = updateSpecWithExtra(widthSpec, lp.leftMargin + this.mDecorInsets.left, lp.rightMargin + this.mDecorInsets.right);
        }
        if (capBothSpecs || this.mOrientation == 0) {
            heightSpec = updateSpecWithExtra(heightSpec, lp.topMargin + this.mDecorInsets.top, lp.bottomMargin + this.mDecorInsets.bottom);
        }
        child.measure(widthSpec, heightSpec);
    }

    private int updateSpecWithExtra(int spec, int startInset, int endInset) {
        if (startInset == 0 && endInset == 0) {
            return spec;
        }
        int mode = MeasureSpec.getMode(spec);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(spec) - startInset) - endInset, mode) : spec;
    }

    private void assignSpans(Recycler recycler, State state, int count, int consumedSpanCount, boolean layingOutInPrimaryDirection) {
        int start;
        int end;
        int diff;
        int span;
        int spanDiff;
        if (layingOutInPrimaryDirection) {
            start = MAIN_DIR_SPEC;
            end = count;
            diff = 1;
        } else {
            start = count - 1;
            end = DEFAULT_SPAN_COUNT;
            diff = DEFAULT_SPAN_COUNT;
        }
        if (this.mOrientation == 1 && isLayoutRTL()) {
            span = this.mSpanCount - 1;
            spanDiff = DEFAULT_SPAN_COUNT;
        } else {
            span = MAIN_DIR_SPEC;
            spanDiff = 1;
        }
        for (int i = start; i != end; i += diff) {
            View view = this.mSet[i];
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.mSpanSize = getSpanSize(recycler, state, getPosition(view));
            if (spanDiff != -1 || params.mSpanSize <= 1) {
                params.mSpanIndex = span;
            } else {
                params.mSpanIndex = span - (params.mSpanSize - 1);
            }
            span += params.mSpanSize * spanDiff;
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void setSpanCount(int spanCount) {
        if (spanCount != this.mSpanCount) {
            this.mPendingSpanCountChange = true;
            if (spanCount < 1) {
                throw new IllegalArgumentException("Span count should be at least 1. Provided " + spanCount);
            }
            this.mSpanCount = spanCount;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
        }
    }

    public boolean supportsPredictiveItemAnimations() {
        return (this.mPendingSavedState != null || this.mPendingSpanCountChange) ? DEBUG : true;
    }
}
