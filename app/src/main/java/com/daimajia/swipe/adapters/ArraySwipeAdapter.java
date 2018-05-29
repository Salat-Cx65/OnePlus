package com.daimajia.swipe.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;
import com.daimajia.swipe.implments.SwipeItemMangerImpl.Mode;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import java.util.List;

public abstract class ArraySwipeAdapter<T> extends ArrayAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {
    private SwipeItemMangerImpl mItemManger;

    public ArraySwipeAdapter(Context context, int resource) {
        super(context, resource);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
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
