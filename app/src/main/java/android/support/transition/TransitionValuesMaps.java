package android.support.transition;

import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

@RequiresApi(14)
class TransitionValuesMaps {
    final SparseArray<View> mIdValues;
    final LongSparseArray<View> mItemIdValues;
    final ArrayMap<String, View> mNameValues;
    final ArrayMap<View, TransitionValues> mViewValues;

    TransitionValuesMaps() {
        this.mViewValues = new ArrayMap();
        this.mIdValues = new SparseArray();
        this.mItemIdValues = new LongSparseArray();
        this.mNameValues = new ArrayMap();
    }
}
