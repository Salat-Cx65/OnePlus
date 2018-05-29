package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.oneplus.lib.preference.Preference;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MediaBrowserCompatUtils {
    public static boolean areSameOptions(Bundle options1, Bundle options2) {
        if (options1 == options2) {
            return true;
        }
        return options1 == null ? options2.getInt(EXTRA_PAGE, -1) == -1 && options2.getInt(EXTRA_PAGE_SIZE, -1) == -1 : options2 == null ? options1.getInt(EXTRA_PAGE, -1) == -1 && options1.getInt(EXTRA_PAGE_SIZE, -1) == -1 : options1.getInt(EXTRA_PAGE, -1) == options2.getInt(EXTRA_PAGE, -1) && options1.getInt(EXTRA_PAGE_SIZE, -1) == options2.getInt(EXTRA_PAGE_SIZE, -1);
    }

    public static boolean hasDuplicatedItems(Bundle options1, Bundle options2) {
        int pageSize1;
        int pageSize2;
        int startIndex1;
        int endIndex1;
        int startIndex2;
        int endIndex2;
        int page1 = options1 == null ? -1 : options1.getInt(EXTRA_PAGE, -1);
        int page2 = options2 == null ? -1 : options2.getInt(EXTRA_PAGE, -1);
        if (options1 == null) {
            pageSize1 = -1;
        } else {
            pageSize1 = options1.getInt(EXTRA_PAGE_SIZE, -1);
        }
        if (options2 == null) {
            pageSize2 = -1;
        } else {
            pageSize2 = options2.getInt(EXTRA_PAGE_SIZE, -1);
        }
        if (page1 == -1 || pageSize1 == -1) {
            startIndex1 = 0;
            endIndex1 = Preference.DEFAULT_ORDER;
        } else {
            startIndex1 = pageSize1 * page1;
            endIndex1 = (startIndex1 + pageSize1) - 1;
        }
        if (page2 == -1 || pageSize2 == -1) {
            startIndex2 = 0;
            endIndex2 = Preference.DEFAULT_ORDER;
        } else {
            startIndex2 = pageSize2 * page2;
            endIndex2 = (startIndex2 + pageSize2) - 1;
        }
        if (startIndex1 > startIndex2 || startIndex2 > endIndex1) {
            return startIndex1 <= endIndex2 && endIndex2 <= endIndex1;
        } else {
            return true;
        }
    }
}
