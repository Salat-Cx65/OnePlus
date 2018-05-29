package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

public final class ListViewCompat {
    public static void scrollListBy(@NonNull ListView listView, int y) {
        if (VERSION.SDK_INT >= 19) {
            listView.scrollListBy(y);
            return;
        }
        int firstPosition = listView.getFirstVisiblePosition();
        if (firstPosition != -1) {
            View firstView = listView.getChildAt(0);
            if (firstView != null) {
                listView.setSelectionFromTop(firstPosition, firstView.getTop() - y);
            }
        }
    }

    public static boolean canScrollList(@NonNull ListView listView, int direction) {
        if (VERSION.SDK_INT >= 19) {
            return listView.canScrollList(direction);
        }
        int childCount = listView.getChildCount();
        if (childCount == 0) {
            return false;
        }
        int firstPosition = listView.getFirstVisiblePosition();
        if (direction > 0) {
            return firstPosition + childCount < listView.getCount() || listView.getChildAt(childCount - 1).getBottom() > listView.getHeight() - listView.getListPaddingBottom();
        } else {
            return firstPosition > 0 || listView.getChildAt(0).getTop() < listView.getListPaddingTop();
        }
    }

    private ListViewCompat() {
    }
}
