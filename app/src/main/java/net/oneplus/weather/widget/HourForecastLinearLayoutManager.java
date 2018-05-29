package net.oneplus.weather.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import net.oneplus.weather.provider.CitySearchProvider;

public class HourForecastLinearLayoutManager extends LinearLayoutManager {
    private int[] mMeasuredDimension;

    public HourForecastLinearLayoutManager(Context context) {
        super(context);
        this.mMeasuredDimension = new int[2];
    }

    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
        int widthMode = MeasureSpec.getMode(widthSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);
        int widthSize = MeasureSpec.getSize(widthSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);
        int width = 0;
        int height = 0;
        int stateCount = state.getItemCount();
        int itemCout = getItemCount();
        for (int i = 0; i < itemCout; i++) {
            if (stateCount > i) {
                if (getOrientation() == 0) {
                    measureScrapChild(recycler, i, MeasureSpec.makeMeasureSpec(i, 0), heightSpec, this.mMeasuredDimension);
                    width += this.mMeasuredDimension[0];
                    if (i == 0) {
                        height = this.mMeasuredDimension[1];
                    }
                } else {
                    measureScrapChild(recycler, i, widthSpec, MeasureSpec.makeMeasureSpec(i, 0), this.mMeasuredDimension);
                    height += this.mMeasuredDimension[1];
                    if (i == 0) {
                        width = this.mMeasuredDimension[0];
                    }
                }
            }
        }
        switch (widthMode) {
            case CitySearchProvider.GET_SEARCH_RESULT_SUCC:
                width = widthSize;
                break;
        }
        switch (heightMode) {
            case CitySearchProvider.GET_SEARCH_RESULT_SUCC:
                height = heightSize;
                break;
        }
        setMeasuredDimension(width, height);
    }

    private void measureScrapChild(Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        View view = recycler.getViewForPosition(position);
        recycler.bindViewToPosition(view, position);
        if (view != null) {
            LayoutParams p = (LayoutParams) view.getLayoutParams();
            view.measure(ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), p.width), ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height));
            measuredDimension[0] = (view.getMeasuredWidth() + p.leftMargin) + p.rightMargin;
            measuredDimension[1] = (view.getMeasuredHeight() + p.bottomMargin) + p.topMargin;
            recycler.recycleView(view);
        }
    }
}
