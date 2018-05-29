package android.support.v4.widget;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.FocusStrategy.BoundsAdapter;
import com.google.android.gms.common.ConnectionResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

class FocusStrategy {

    public static interface BoundsAdapter<T> {
        void obtainBounds(T t, Rect rect);
    }

    public static interface CollectionAdapter<T, V> {
        V get(T t, int i);

        int size(T t);
    }

    private static class SequentialComparator<T> implements Comparator<T> {
        private final BoundsAdapter<T> mAdapter;
        private final boolean mIsLayoutRtl;
        private final Rect mTemp1;
        private final Rect mTemp2;

        SequentialComparator(boolean isLayoutRtl, BoundsAdapter<T> adapter) {
            this.mTemp1 = new Rect();
            this.mTemp2 = new Rect();
            this.mIsLayoutRtl = isLayoutRtl;
            this.mAdapter = adapter;
        }

        public int compare(T first, T second) {
            Object obj = 1;
            Rect firstRect = this.mTemp1;
            Rect secondRect = this.mTemp2;
            this.mAdapter.obtainBounds(first, firstRect);
            this.mAdapter.obtainBounds(second, secondRect);
            if (firstRect.top < secondRect.top) {
                return -1;
            }
            if (firstRect.top > secondRect.top) {
                return 1;
            }
            int i;
            if (firstRect.left < secondRect.left) {
                if (!this.mIsLayoutRtl) {
                    i = -1;
                }
                return i;
            } else if (firstRect.left > secondRect.left) {
                return !this.mIsLayoutRtl ? 1 : -1;
            } else {
                if (firstRect.bottom < secondRect.bottom) {
                    return -1;
                }
                if (firstRect.bottom > secondRect.bottom) {
                    return 1;
                }
                if (firstRect.right < secondRect.right) {
                    if (!this.mIsLayoutRtl) {
                        i = -1;
                    }
                    return i;
                } else if (firstRect.right > secondRect.right) {
                    return !this.mIsLayoutRtl ? 1 : -1;
                } else {
                    return 0;
                }
            }
        }
    }

    FocusStrategy() {
    }

    public static <L, T> T findNextFocusInRelativeDirection(@NonNull L focusables, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> adapter, @Nullable T focused, int direction, boolean isLayoutRtl, boolean wrap) {
        int count = collectionAdapter.size(focusables);
        ArrayList<T> sortedFocusables = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            sortedFocusables.add(collectionAdapter.get(focusables, i));
        }
        Collections.sort(sortedFocusables, new SequentialComparator(isLayoutRtl, adapter));
        switch (direction) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return getPreviousFocusable(focused, sortedFocusables, wrap);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return getNextFocusable(focused, sortedFocusables, wrap);
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
    }

    private static <T> T getNextFocusable(T focused, ArrayList<T> focusables, boolean wrap) {
        int count = focusables.size();
        int position = (focused == null ? -1 : focusables.lastIndexOf(focused)) + 1;
        if (position < count) {
            return focusables.get(position);
        }
        return (!wrap || count <= 0) ? null : focusables.get(0);
    }

    private static <T> T getPreviousFocusable(T focused, ArrayList<T> focusables, boolean wrap) {
        int count = focusables.size();
        int position = (focused == null ? count : focusables.indexOf(focused)) - 1;
        if (position >= 0) {
            return focusables.get(position);
        }
        return (!wrap || count <= 0) ? null : focusables.get(count - 1);
    }

    public static <L, T> T findNextFocusInAbsoluteDirection(@NonNull L focusables, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> adapter, @Nullable T focused, @NonNull Rect focusedRect, int direction) {
        Rect bestCandidateRect = new Rect(focusedRect);
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
                bestCandidateRect.offset(focusedRect.width() + 1, 0);
                break;
            case R.styleable.OneplusTheme_op_borderWidth:
                bestCandidateRect.offset(0, focusedRect.height() + 1);
                break;
            case R.styleable.AppCompatTheme_editTextColor:
                bestCandidateRect.offset(-(focusedRect.width() + 1), 0);
                break;
            case 130:
                bestCandidateRect.offset(0, -(focusedRect.height() + 1));
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        T closest = null;
        int count = collectionAdapter.size(focusables);
        Rect focusableRect = new Rect();
        for (int i = 0; i < count; i++) {
            T focusable = collectionAdapter.get(focusables, i);
            if (focusable != focused) {
                adapter.obtainBounds(focusable, focusableRect);
                if (isBetterCandidate(direction, focusedRect, focusableRect, bestCandidateRect)) {
                    bestCandidateRect.set(focusableRect);
                    closest = focusable;
                }
            }
        }
        return closest;
    }

    private static boolean isBetterCandidate(int direction, @NonNull Rect source, @NonNull Rect candidate, @NonNull Rect currentBest) {
        if (!isCandidate(source, candidate, direction)) {
            return false;
        }
        if (!isCandidate(source, currentBest, direction) || beamBeats(direction, source, candidate, currentBest)) {
            return true;
        }
        if (beamBeats(direction, source, currentBest, candidate)) {
            return false;
        }
        return getWeightedDistanceFor(majorAxisDistance(direction, source, candidate), minorAxisDistance(direction, source, candidate)) < getWeightedDistanceFor(majorAxisDistance(direction, source, currentBest), minorAxisDistance(direction, source, currentBest));
    }

    private static boolean beamBeats(int direction, @NonNull Rect source, @NonNull Rect rect1, @NonNull Rect rect2) {
        boolean rect1InSrcBeam = beamsOverlap(direction, source, rect1);
        if (beamsOverlap(direction, source, rect2) || !rect1InSrcBeam) {
            return false;
        }
        return !isToDirectionOf(direction, source, rect2) || direction == 17 || direction == 66 || majorAxisDistance(direction, source, rect1) < majorAxisDistanceToFarEdge(direction, source, rect2);
    }

    private static int getWeightedDistanceFor(int majorAxisDistance, int minorAxisDistance) {
        return ((majorAxisDistance * 13) * majorAxisDistance) + (minorAxisDistance * minorAxisDistance);
    }

    private static boolean isCandidate(@NonNull Rect srcRect, @NonNull Rect destRect, int direction) {
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
                return (srcRect.right > destRect.right || srcRect.left >= destRect.right) && srcRect.left > destRect.left;
            case R.styleable.OneplusTheme_op_borderWidth:
                return (srcRect.bottom > destRect.bottom || srcRect.top >= destRect.bottom) && srcRect.top > destRect.top;
            case R.styleable.AppCompatTheme_editTextColor:
                return (srcRect.left < destRect.left || srcRect.right <= destRect.left) && srcRect.right < destRect.right;
            case 130:
                return (srcRect.top < destRect.top || srcRect.bottom <= destRect.top) && srcRect.bottom < destRect.bottom;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static boolean beamsOverlap(int direction, @NonNull Rect rect1, @NonNull Rect rect2) {
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
            case R.styleable.AppCompatTheme_editTextColor:
                return rect2.bottom >= rect1.top && rect2.top <= rect1.bottom;
            case R.styleable.OneplusTheme_op_borderWidth:
            case 130:
                return rect2.right >= rect1.left && rect2.left <= rect1.right;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static boolean isToDirectionOf(int direction, @NonNull Rect src, @NonNull Rect dest) {
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
                return src.left >= dest.right;
            case R.styleable.OneplusTheme_op_borderWidth:
                return src.top >= dest.bottom;
            case R.styleable.AppCompatTheme_editTextColor:
                return src.right <= dest.left;
            case 130:
                return src.bottom <= dest.top;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static int majorAxisDistance(int direction, @NonNull Rect source, @NonNull Rect dest) {
        return Math.max(0, majorAxisDistanceRaw(direction, source, dest));
    }

    private static int majorAxisDistanceRaw(int direction, @NonNull Rect source, @NonNull Rect dest) {
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
                return source.left - dest.right;
            case R.styleable.OneplusTheme_op_borderWidth:
                return source.top - dest.bottom;
            case R.styleable.AppCompatTheme_editTextColor:
                return dest.left - source.right;
            case 130:
                return dest.top - source.bottom;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static int majorAxisDistanceToFarEdge(int direction, @NonNull Rect source, @NonNull Rect dest) {
        return Math.max(1, majorAxisDistanceToFarEdgeRaw(direction, source, dest));
    }

    private static int majorAxisDistanceToFarEdgeRaw(int direction, @NonNull Rect source, @NonNull Rect dest) {
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
                return source.left - dest.left;
            case R.styleable.OneplusTheme_op_borderWidth:
                return source.top - dest.top;
            case R.styleable.AppCompatTheme_editTextColor:
                return dest.right - source.right;
            case 130:
                return dest.bottom - source.bottom;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static int minorAxisDistance(int direction, @NonNull Rect source, @NonNull Rect dest) {
        switch (direction) {
            case ConnectionResult.SIGN_IN_FAILED:
            case R.styleable.AppCompatTheme_editTextColor:
                return Math.abs((source.top + (source.height() / 2)) - (dest.top + (dest.height() / 2)));
            case R.styleable.OneplusTheme_op_borderWidth:
            case 130:
                return Math.abs((source.left + (source.width() / 2)) - (dest.left + (dest.width() / 2)));
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }
}
