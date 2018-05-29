package net.oneplus.weather.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class SpacesItemDecoration extends ItemDecoration {
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    private int mOrientation;
    private final int mPadding;
    private final int mSpace;

    public SpacesItemDecoration(int orientation, int padding, int space) {
        this.mPadding = padding;
        this.mSpace = space;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation == 0 || orientation == 1) {
            this.mOrientation = orientation;
            return;
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (this.mOrientation == 1) {
            if (parent.getChildPosition(view) == 0) {
                outRect.set(HORIZONTAL_LIST, this.mPadding, HORIZONTAL_LIST, HORIZONTAL_LIST);
            } else if (parent.getChildPosition(view) == state.getItemCount() - 1) {
                outRect.set(HORIZONTAL_LIST, this.mSpace, HORIZONTAL_LIST, this.mPadding);
            } else {
                outRect.set(HORIZONTAL_LIST, this.mSpace, HORIZONTAL_LIST, HORIZONTAL_LIST);
            }
        } else if (parent.getChildPosition(view) == 0) {
            outRect.set(this.mPadding, HORIZONTAL_LIST, HORIZONTAL_LIST, HORIZONTAL_LIST);
        } else if (parent.getChildPosition(view) == state.getItemCount() - 1) {
            outRect.set(this.mSpace, HORIZONTAL_LIST, this.mPadding, HORIZONTAL_LIST);
        } else {
            outRect.set(this.mSpace, HORIZONTAL_LIST, HORIZONTAL_LIST, HORIZONTAL_LIST);
        }
    }
}
