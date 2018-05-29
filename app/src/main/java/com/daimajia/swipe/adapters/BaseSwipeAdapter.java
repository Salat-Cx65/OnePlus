package com.daimajia.swipe.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;
import com.daimajia.swipe.implments.SwipeItemMangerImpl.Mode;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import java.util.List;

public abstract class BaseSwipeAdapter extends BaseAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {
    protected SwipeItemMangerImpl mItemManger;

    public abstract void fillValues(int i, View view);

    public abstract View generateView(int i, ViewGroup viewGroup);

    public abstract int getSwipeLayoutResourceId(int i);

    public BaseSwipeAdapter() {
        this.mItemManger = new SwipeItemMangerImpl(this);
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = generateView(position, parent);
            this.mItemManger.initialize(v, position);
        } else {
            this.mItemManger.updateConvertView(v, position);
        }
        fillValues(position, v);
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
