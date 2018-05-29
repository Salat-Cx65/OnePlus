package com.oneplus.lib.widget.recyclerview;

import com.oneplus.lib.widget.recyclerview.Pools.Pool;
import com.oneplus.lib.widget.recyclerview.Pools.SimplePool;
import com.oneplus.lib.widget.recyclerview.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

class AdapterHelper implements Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates;
    final ArrayList<UpdateOp> mPostponedList;
    private Pool<UpdateOp> mUpdateOpPool;

    static interface Callback {
        ViewHolder findViewHolder(int i);

        void markViewHoldersUpdated(int i, int i2, Object obj);

        void offsetPositionsForAdd(int i, int i2);

        void offsetPositionsForMove(int i, int i2);

        void offsetPositionsForRemovingInvisible(int i, int i2);

        void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2);

        void onDispatchFirstPass(UpdateOp updateOp);

        void onDispatchSecondPass(UpdateOp updateOp);
    }

    static class UpdateOp {
        static final int ADD = 0;
        static final int MOVE = 3;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 1;
        static final int UPDATE = 2;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int cmd, int positionStart, int itemCount, Object payload) {
            this.cmd = cmd;
            this.positionStart = positionStart;
            this.itemCount = itemCount;
            this.payload = payload;
        }

        String cmdToString() {
            switch (this.cmd) {
                case ADD:
                    return "add";
                case REMOVE:
                    return "rm";
                case UPDATE:
                    return "up";
                case MOVE:
                    return "mv";
                default:
                    return "??";
            }
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UpdateOp op = (UpdateOp) o;
            if (this.cmd != op.cmd) {
                return false;
            }
            if (this.cmd == 3 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == op.positionStart && this.positionStart == op.itemCount) {
                return true;
            }
            if (this.itemCount != op.itemCount) {
                return false;
            }
            if (this.positionStart != op.positionStart) {
                return false;
            }
            return this.payload != null ? this.payload.equals(op.payload) : op.payload == null;
        }

        public int hashCode() {
            return (((this.cmd * 31) + this.positionStart) * 31) + this.itemCount;
        }
    }

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean disableRecycler) {
        this.mUpdateOpPool = new SimplePool(30);
        this.mPendingUpdates = new ArrayList();
        this.mPostponedList = new ArrayList();
        this.mCallback = callback;
        this.mDisableRecycler = disableRecycler;
        this.mOpReorderer = new OpReorderer(this);
    }

    AdapterHelper addUpdateOp(UpdateOp... ops) {
        Collections.addAll(this.mPendingUpdates, ops);
        return this;
    }

    void reset() {
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        recycleUpdateOpsAndClearList(this.mPostponedList);
    }

    void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int count = this.mPendingUpdates.size();
        for (int i = POSITION_TYPE_INVISIBLE; i < count; i++) {
            UpdateOp op = (UpdateOp) this.mPendingUpdates.get(i);
            switch (op.cmd) {
                case POSITION_TYPE_INVISIBLE:
                    applyAdd(op);
                    break;
                case POSITION_TYPE_NEW_OR_LAID_OUT:
                    applyRemove(op);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    applyUpdate(op);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    applyMove(op);
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        this.mPendingUpdates.clear();
    }

    void consumePostponedUpdates() {
        int count = this.mPostponedList.size();
        for (int i = POSITION_TYPE_INVISIBLE; i < count; i++) {
            this.mCallback.onDispatchSecondPass((UpdateOp) this.mPostponedList.get(i));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
    }

    private void applyMove(UpdateOp op) {
        postponeAndUpdateViewHolders(op);
    }

    private void applyRemove(UpdateOp op) {
        int tmpStart = op.positionStart;
        int tmpCount = POSITION_TYPE_INVISIBLE;
        int tmpEnd = op.positionStart + op.itemCount;
        int type = -1;
        int position = op.positionStart;
        while (position < tmpEnd) {
            boolean typeChanged = DEBUG;
            if (this.mCallback.findViewHolder(position) != null || canFindInPreLayout(position)) {
                if (type == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(POSITION_TYPE_NEW_OR_LAID_OUT, tmpStart, tmpCount, null));
                    typeChanged = true;
                }
                type = POSITION_TYPE_NEW_OR_LAID_OUT;
            } else {
                if (type == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(POSITION_TYPE_NEW_OR_LAID_OUT, tmpStart, tmpCount, null));
                    typeChanged = true;
                }
                type = POSITION_TYPE_INVISIBLE;
            }
            if (typeChanged) {
                position -= tmpCount;
                tmpEnd -= tmpCount;
                tmpCount = POSITION_TYPE_NEW_OR_LAID_OUT;
            } else {
                tmpCount++;
            }
            position++;
        }
        if (tmpCount != op.itemCount) {
            recycleUpdateOp(op);
            op = obtainUpdateOp(POSITION_TYPE_NEW_OR_LAID_OUT, tmpStart, tmpCount, null);
        }
        if (type == 0) {
            dispatchAndUpdateViewHolders(op);
        } else {
            postponeAndUpdateViewHolders(op);
        }
    }

    private void applyUpdate(UpdateOp op) {
        int tmpStart = op.positionStart;
        int tmpCount = POSITION_TYPE_INVISIBLE;
        int tmpEnd = op.positionStart + op.itemCount;
        int type = -1;
        int position = op.positionStart;
        while (position < tmpEnd) {
            if (this.mCallback.findViewHolder(position) != null || canFindInPreLayout(position)) {
                if (type == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(RainSurfaceView.RAIN_LEVEL_SHOWER, tmpStart, tmpCount, op.payload));
                    tmpCount = POSITION_TYPE_INVISIBLE;
                    tmpStart = position;
                }
                type = POSITION_TYPE_NEW_OR_LAID_OUT;
            } else {
                if (type == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(RainSurfaceView.RAIN_LEVEL_SHOWER, tmpStart, tmpCount, op.payload));
                    tmpCount = POSITION_TYPE_INVISIBLE;
                    tmpStart = position;
                }
                type = POSITION_TYPE_INVISIBLE;
            }
            tmpCount++;
            position++;
        }
        if (tmpCount != op.itemCount) {
            Object payload = op.payload;
            recycleUpdateOp(op);
            op = obtainUpdateOp(RainSurfaceView.RAIN_LEVEL_SHOWER, tmpStart, tmpCount, payload);
        }
        if (type == 0) {
            dispatchAndUpdateViewHolders(op);
        } else {
            postponeAndUpdateViewHolders(op);
        }
    }

    private void dispatchAndUpdateViewHolders(UpdateOp op) {
        if (op.cmd == 0 || op.cmd == 3) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int positionMultiplier;
        int tmpStart = updatePositionWithPostponed(op.positionStart, op.cmd);
        int tmpCnt = POSITION_TYPE_NEW_OR_LAID_OUT;
        int offsetPositionForPartial = op.positionStart;
        switch (op.cmd) {
            case POSITION_TYPE_NEW_OR_LAID_OUT:
                positionMultiplier = POSITION_TYPE_INVISIBLE;
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                positionMultiplier = POSITION_TYPE_NEW_OR_LAID_OUT;
                break;
            default:
                throw new IllegalArgumentException("op should be remove or update." + op);
        }
        for (int p = POSITION_TYPE_NEW_OR_LAID_OUT; p < op.itemCount; p++) {
            int updatedPos = updatePositionWithPostponed(op.positionStart + (positionMultiplier * p), op.cmd);
            boolean continuous = DEBUG;
            switch (op.cmd) {
                case POSITION_TYPE_NEW_OR_LAID_OUT:
                    continuous = updatedPos == tmpStart;
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    if (updatedPos == tmpStart + 1) {
                        continuous = true;
                    } else {
                        continuous = false;
                    }
                    break;
            }
            if (continuous) {
                tmpCnt++;
            } else {
                UpdateOp tmp = obtainUpdateOp(op.cmd, tmpStart, tmpCnt, op.payload);
                dispatchFirstPassAndUpdateViewHolders(tmp, offsetPositionForPartial);
                recycleUpdateOp(tmp);
                if (op.cmd == 2) {
                    offsetPositionForPartial += tmpCnt;
                }
                tmpStart = updatedPos;
                tmpCnt = POSITION_TYPE_NEW_OR_LAID_OUT;
            }
        }
        Object payload = op.payload;
        recycleUpdateOp(op);
        if (tmpCnt > 0) {
            tmp = obtainUpdateOp(op.cmd, tmpStart, tmpCnt, payload);
            dispatchFirstPassAndUpdateViewHolders(tmp, offsetPositionForPartial);
            recycleUpdateOp(tmp);
        }
    }

    void dispatchFirstPassAndUpdateViewHolders(UpdateOp op, int offsetStart) {
        this.mCallback.onDispatchFirstPass(op);
        switch (op.cmd) {
            case POSITION_TYPE_NEW_OR_LAID_OUT:
                this.mCallback.offsetPositionsForRemovingInvisible(offsetStart, op.itemCount);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                this.mCallback.markViewHoldersUpdated(offsetStart, op.itemCount, op.payload);
            default:
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    private int updatePositionWithPostponed(int pos, int cmd) {
        int i;
        for (i = this.mPostponedList.size() - 1; i >= 0; i--) {
            UpdateOp postponed = (UpdateOp) this.mPostponedList.get(i);
            if (postponed.cmd == 3) {
                int start;
                int end;
                if (postponed.positionStart < postponed.itemCount) {
                    start = postponed.positionStart;
                    end = postponed.itemCount;
                } else {
                    start = postponed.itemCount;
                    end = postponed.positionStart;
                }
                if (pos < start || pos > end) {
                    if (pos < postponed.positionStart) {
                        if (cmd == 0) {
                            postponed.positionStart++;
                            postponed.itemCount++;
                        } else if (cmd == 1) {
                            postponed.positionStart--;
                            postponed.itemCount--;
                        }
                    }
                } else if (start == postponed.positionStart) {
                    if (cmd == 0) {
                        postponed.itemCount++;
                    } else if (cmd == 1) {
                        postponed.itemCount--;
                    }
                    pos++;
                } else {
                    if (cmd == 0) {
                        postponed.positionStart++;
                    } else if (cmd == 1) {
                        postponed.positionStart--;
                    }
                    pos--;
                }
            } else if (postponed.positionStart <= pos) {
                if (postponed.cmd == 0) {
                    pos -= postponed.itemCount;
                } else if (postponed.cmd == 1) {
                    pos += postponed.itemCount;
                }
            } else if (cmd == 0) {
                postponed.positionStart++;
            } else if (cmd == 1) {
                postponed.positionStart--;
            }
        }
        for (i = this.mPostponedList.size() - 1; i >= 0; i--) {
            UpdateOp op = (UpdateOp) this.mPostponedList.get(i);
            if (op.cmd == 3) {
                if (op.itemCount == op.positionStart || op.itemCount < 0) {
                    this.mPostponedList.remove(i);
                    recycleUpdateOp(op);
                }
            } else if (op.itemCount <= 0) {
                this.mPostponedList.remove(i);
                recycleUpdateOp(op);
            }
        }
        return pos;
    }

    private boolean canFindInPreLayout(int position) {
        int count = this.mPostponedList.size();
        for (int i = POSITION_TYPE_INVISIBLE; i < count; i++) {
            UpdateOp op = (UpdateOp) this.mPostponedList.get(i);
            if (op.cmd == 3) {
                if (findPositionOffset(op.itemCount, i + 1) == position) {
                    return true;
                }
            } else if (op.cmd == 0) {
                int end = op.positionStart + op.itemCount;
                for (int pos = op.positionStart; pos < end; pos++) {
                    if (findPositionOffset(pos, i + 1) == position) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return DEBUG;
    }

    private void applyAdd(UpdateOp op) {
        postponeAndUpdateViewHolders(op);
    }

    private void postponeAndUpdateViewHolders(UpdateOp op) {
        this.mPostponedList.add(op);
        switch (op.cmd) {
            case POSITION_TYPE_INVISIBLE:
                this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
            case POSITION_TYPE_NEW_OR_LAID_OUT:
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(op.positionStart, op.itemCount);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
            default:
                throw new IllegalArgumentException("Unknown update op type for " + op);
        }
    }

    boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0 ? true : DEBUG;
    }

    int findPositionOffset(int position) {
        return findPositionOffset(position, POSITION_TYPE_INVISIBLE);
    }

    int findPositionOffset(int position, int firstPostponedItem) {
        int count = this.mPostponedList.size();
        for (int i = firstPostponedItem; i < count; i++) {
            UpdateOp op = (UpdateOp) this.mPostponedList.get(i);
            if (op.cmd == 3) {
                if (op.positionStart == position) {
                    position = op.itemCount;
                } else {
                    if (op.positionStart < position) {
                        position--;
                    }
                    if (op.itemCount <= position) {
                        position++;
                    }
                }
            } else if (op.positionStart > position) {
                continue;
            } else if (op.cmd == 1) {
                if (position < op.positionStart + op.itemCount) {
                    return -1;
                }
                position -= op.itemCount;
            } else if (op.cmd == 0) {
                position += op.itemCount;
            }
        }
        return position;
    }

    boolean onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        this.mPendingUpdates.add(obtainUpdateOp(RainSurfaceView.RAIN_LEVEL_SHOWER, positionStart, itemCount, payload));
        return this.mPendingUpdates.size() == 1 ? true : DEBUG;
    }

    boolean onItemRangeInserted(int positionStart, int itemCount) {
        this.mPendingUpdates.add(obtainUpdateOp(POSITION_TYPE_INVISIBLE, positionStart, itemCount, null));
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeRemoved(int positionStart, int itemCount) {
        this.mPendingUpdates.add(obtainUpdateOp(POSITION_TYPE_NEW_OR_LAID_OUT, positionStart, itemCount, null));
        return this.mPendingUpdates.size() == 1 ? true : DEBUG;
    }

    boolean onItemRangeMoved(int from, int to, int itemCount) {
        Object obj = POSITION_TYPE_NEW_OR_LAID_OUT;
        if (from == to) {
            return DEBUG;
        }
        if (itemCount != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        boolean z;
        this.mPendingUpdates.add(obtainUpdateOp(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, from, to, null));
        if (this.mPendingUpdates.size() != 1) {
            z = false;
        }
        return z;
    }

    void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        int count = this.mPendingUpdates.size();
        for (int i = POSITION_TYPE_INVISIBLE; i < count; i++) {
            UpdateOp op = (UpdateOp) this.mPendingUpdates.get(i);
            switch (op.cmd) {
                case POSITION_TYPE_INVISIBLE:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForAdd(op.positionStart, op.itemCount);
                    break;
                case POSITION_TYPE_NEW_OR_LAID_OUT:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForRemovingInvisible(op.positionStart, op.itemCount);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.markViewHoldersUpdated(op.positionStart, op.itemCount, op.payload);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    this.mCallback.onDispatchSecondPass(op);
                    this.mCallback.offsetPositionsForMove(op.positionStart, op.itemCount);
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
    }

    public int applyPendingUpdatesToPosition(int position) {
        int size = this.mPendingUpdates.size();
        for (int i = POSITION_TYPE_INVISIBLE; i < size; i++) {
            UpdateOp op = (UpdateOp) this.mPendingUpdates.get(i);
            switch (op.cmd) {
                case POSITION_TYPE_INVISIBLE:
                    if (op.positionStart <= position) {
                        position += op.itemCount;
                    }
                case POSITION_TYPE_NEW_OR_LAID_OUT:
                    if (op.positionStart > position) {
                        continue;
                    } else if (op.positionStart + op.itemCount > position) {
                        return -1;
                    } else {
                        position -= op.itemCount;
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    if (op.positionStart == position) {
                        position = op.itemCount;
                    } else {
                        if (op.positionStart < position) {
                            position--;
                        }
                        if (op.itemCount <= position) {
                            position++;
                        }
                    }
                default:
                    break;
            }
        }
        return position;
    }

    public UpdateOp obtainUpdateOp(int cmd, int positionStart, int itemCount, Object payload) {
        UpdateOp op = (UpdateOp) this.mUpdateOpPool.acquire();
        if (op == null) {
            return new UpdateOp(cmd, positionStart, itemCount, payload);
        }
        op.cmd = cmd;
        op.positionStart = positionStart;
        op.itemCount = itemCount;
        op.payload = payload;
        return op;
    }

    public void recycleUpdateOp(UpdateOp op) {
        if (!this.mDisableRecycler) {
            op.payload = null;
            this.mUpdateOpPool.release(op);
        }
    }

    void recycleUpdateOpsAndClearList(List<UpdateOp> ops) {
        int count = ops.size();
        for (int i = POSITION_TYPE_INVISIBLE; i < count; i++) {
            recycleUpdateOp((UpdateOp) ops.get(i));
        }
        ops.clear();
    }
}
