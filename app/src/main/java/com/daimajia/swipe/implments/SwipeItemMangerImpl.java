package com.daimajia.swipe.implments;

import android.view.View;
import android.widget.BaseAdapter;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.SwipeLayout.OnLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl.Mode;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SwipeItemMangerImpl implements SwipeItemMangerInterface {
    public final int INVALID_POSITION;
    protected BaseAdapter mAdapter;
    protected int mOpenPosition;
    protected Set<Integer> mOpenPositions;
    protected Set<SwipeLayout> mShownLayouts;
    private Mode mode;

    public enum Mode {
        Single,
        Multiple
    }

    class ValueBox {
        OnLayoutListener onLayoutListener;
        int position;
        SwipeMemory swipeMemory;

        ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    class OnLayoutListener implements OnLayout {
        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void onLayout(SwipeLayout v) {
            if (SwipeItemMangerImpl.this.isOpen(this.position)) {
                v.open(false, false);
            } else {
                v.close(false, false);
            }
        }
    }

    class SwipeMemory extends SimpleSwipeListener {
        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        public void onClose(SwipeLayout layout) {
            if (SwipeItemMangerImpl.this.mode == Mode.Multiple) {
                SwipeItemMangerImpl.this.mOpenPositions.remove(Integer.valueOf(this.position));
            } else {
                SwipeItemMangerImpl.this.mOpenPosition = -1;
            }
        }

        public void onStartOpen(SwipeLayout layout) {
            if (SwipeItemMangerImpl.this.mode == Mode.Single) {
                SwipeItemMangerImpl.this.closeAllExcept(layout);
            }
        }

        public void onOpen(SwipeLayout layout) {
            if (SwipeItemMangerImpl.this.mode == Mode.Multiple) {
                SwipeItemMangerImpl.this.mOpenPositions.add(Integer.valueOf(this.position));
                return;
            }
            SwipeItemMangerImpl.this.closeAllExcept(layout);
            SwipeItemMangerImpl.this.mOpenPosition = this.position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public SwipeItemMangerImpl(BaseAdapter adapter) {
        this.mode = Mode.Single;
        this.INVALID_POSITION = -1;
        this.mOpenPosition = -1;
        this.mOpenPositions = new HashSet();
        this.mShownLayouts = new HashSet();
        if (adapter == null) {
            throw new IllegalArgumentException("Adapter can not be null");
        } else if (adapter instanceof SwipeItemMangerInterface) {
            this.mAdapter = adapter;
        } else {
            throw new IllegalArgumentException("adapter should implement the SwipeAdapterInterface");
        }
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        this.mOpenPositions.clear();
        this.mShownLayouts.clear();
        this.mOpenPosition = -1;
    }

    public void initialize(View target, int position) {
        int resId = getSwipeLayoutId(position);
        OnLayoutListener onLayoutListener = new OnLayoutListener(position);
        SwipeLayout swipeLayout = (SwipeLayout) target.findViewById(resId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        SwipeMemory swipeMemory = new SwipeMemory(position);
        swipeLayout.addSwipeListener(swipeMemory);
        swipeLayout.addOnLayoutListener(onLayoutListener);
        swipeLayout.setTag(resId, new ValueBox(position, swipeMemory, onLayoutListener));
        this.mShownLayouts.add(swipeLayout);
    }

    public void updateConvertView(View target, int position) {
        int resId = getSwipeLayoutId(position);
        SwipeLayout swipeLayout = (SwipeLayout) target.findViewById(resId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
        valueBox.swipeMemory.setPosition(position);
        valueBox.onLayoutListener.setPosition(position);
        valueBox.position = position;
    }

    private int getSwipeLayoutId(int position) {
        return ((SwipeAdapterInterface) this.mAdapter).getSwipeLayoutResourceId(position);
    }

    public void openItem(int position) {
        if (this.mode != Mode.Multiple) {
            this.mOpenPosition = position;
        } else if (!this.mOpenPositions.contains(Integer.valueOf(position))) {
            this.mOpenPositions.add(Integer.valueOf(position));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void closeItem(int position) {
        if (this.mode == Mode.Multiple) {
            this.mOpenPositions.remove(Integer.valueOf(position));
        } else if (this.mOpenPosition == position) {
            this.mOpenPosition = -1;
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void closeAllExcept(SwipeLayout layout) {
        for (SwipeLayout s : this.mShownLayouts) {
            if (s != layout) {
                s.close();
            }
        }
    }

    public void removeShownLayouts(SwipeLayout layout) {
        this.mShownLayouts.remove(layout);
    }

    public List<Integer> getOpenItems() {
        if (this.mode == Mode.Multiple) {
            return new ArrayList(this.mOpenPositions);
        }
        return Arrays.asList(new Integer[]{Integer.valueOf(this.mOpenPosition)});
    }

    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList(this.mShownLayouts);
    }

    public boolean isOpen(int position) {
        if (this.mode == Mode.Multiple) {
            return this.mOpenPositions.contains(Integer.valueOf(position));
        }
        return this.mOpenPosition == position;
    }
}
