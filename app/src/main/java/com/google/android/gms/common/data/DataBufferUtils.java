package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils {
    private DataBufferUtils() {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
        ArrayList<T> arrayList = new ArrayList(dataBuffer.getCount());
        Iterator it = dataBuffer.iterator();
        while (it.hasNext()) {
            arrayList.add(r0.freeze());
        }
        dataBuffer.close();
        return arrayList;
    }

    public static boolean hasData(DataBuffer<?> dataBuffer) {
        return dataBuffer != null && dataBuffer.getCount() > 0;
    }

    public static boolean hasNextPage(DataBuffer<?> dataBuffer) {
        Bundle zzqL = dataBuffer.zzqL();
        return (zzqL == null || zzqL.getString("next_page_token") == null) ? false : true;
    }

    public static boolean hasPrevPage(DataBuffer<?> dataBuffer) {
        Bundle zzqL = dataBuffer.zzqL();
        return (zzqL == null || zzqL.getString("prev_page_token") == null) ? false : true;
    }
}
