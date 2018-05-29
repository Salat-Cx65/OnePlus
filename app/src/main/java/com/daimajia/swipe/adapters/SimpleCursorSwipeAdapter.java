package com.daimajia.swipe.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;
import com.daimajia.swipe.implments.SwipeItemMangerImpl.Mode;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import java.util.List;

public abstract class SimpleCursorSwipeAdapter extends SimpleCursorAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {
    private SwipeItemMangerImpl mItemManger;

    @TargetApi(11)
    protected SimpleCursorSwipeAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    protected SimpleCursorSwipeAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean convertViewIsNull = convertView == null;
        View v = super.getView(position, convertView, parent);
        if (convertViewIsNull) {
            this.mItemManger.initialize(v, position);
        } else {
            this.mItemManger.updateConvertView(v, position);
        }
        return v;
    }

    public void openItem(int position) {
        this.mItemManger.openItem(position);
    }

    public void closeItem(int position) {
        this.mItemManger.closeItem(position);
    }

    public void closeAllExcept(SwipeLayout layout) {
        this.mItemManger.closeAllExcept(layout);
    }

    public List<Integer> getOpenItems() {
        return this.mItemManger.getOpenItems();
    }

    public List<SwipeLayout> getOpenLayouts() {
        return this.mItemManger.getOpenLayouts();
    }

    public void removeShownLayouts(SwipeLayout layout) {
        this.mItemManger.removeShownLayouts(layout);
    }

    public boolean isOpen(int position) {
        return this.mItemManger.isOpen(position);
    }

    public Mode getMode() {
        return this.mItemManger.getMode();
    }

    public void setMode(Mode mode) {
        this.mItemManger.setMode(mode);
    }
}
