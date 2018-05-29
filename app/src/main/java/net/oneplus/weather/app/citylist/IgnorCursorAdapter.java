package net.oneplus.weather.app.citylist;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashSet;

public abstract class IgnorCursorAdapter extends CursorAdapter {
    private SparseIntArray ingioPositionMap;
    private Context mContext;
    private HashSet<Long> mDeleteItemThreadIdList;

    public IgnorCursorAdapter(Context context, Cursor c) {
        this(context, c, false);
        this.mContext = context;
    }

    public IgnorCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.mDeleteItemThreadIdList = new HashSet();
    }

    public IgnorCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItem(position) == null) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        View v;
        if (convertView == null) {
            v = newView(this.mContext, this.mCursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, this.mContext, this.mCursor);
        return v;
    }

    public void clearDelete() {
        setDeleteItemThreadIdList(null);
    }

    public void delete(long id) {
        this.mDeleteItemThreadIdList.add(Long.valueOf(id));
        setDeleteItemThreadIdList(this.mDeleteItemThreadIdList);
        notifyDataSetChanged();
    }

    public void setDeleteItemThreadIdList(HashSet<Long> deleteItemThreadIdList) {
        if (deleteItemThreadIdList == null) {
            this.mDeleteItemThreadIdList = new HashSet();
            this.ingioPositionMap = null;
            return;
        }
        this.ingioPositionMap = new SparseIntArray();
        this.mDeleteItemThreadIdList = deleteItemThreadIdList;
        this.ingioPositionMap.put(0, 0);
        for (int i = 0; i < getCount(); i++) {
            int preOffsetPositon = this.ingioPositionMap.get(i - 1) + 1;
            if (i == 0) {
                preOffsetPositon = 0;
            }
            while (preOffsetPositon < getRealCount()) {
                Cursor cursor = (Cursor) getRealPositionItem(preOffsetPositon);
                if (!this.mDeleteItemThreadIdList.contains(Long.valueOf(cursor.getLong(cursor.getColumnIndex("_id"))))) {
                    break;
                }
                preOffsetPositon++;
            }
            this.ingioPositionMap.put(i, preOffsetPositon);
        }
    }

    public int getCount() {
        return super.getCount() - this.mDeleteItemThreadIdList.size();
    }

    public Object getItem(int position) {
        int offsetPosition = position;
        if (this.ingioPositionMap != null) {
            offsetPosition = this.ingioPositionMap.get(position);
        }
        return super.getItem(offsetPosition);
    }

    public int getRealCount() {
        return super.getCount();
    }

    public Object getRealPositionItem(int position) {
        return super.getItem(position);
    }
}
