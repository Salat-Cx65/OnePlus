package android.support.v4.content;

public final class ContentResolverCompat {
    private ContentResolverCompat() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.database.Cursor query(android.content.ContentResolver r9_resolver, android.net.Uri r10_uri, java.lang.String[] r11_projection, java.lang.String r12_selection, java.lang.String[] r13_selectionArgs, java.lang.String r14_sortOrder, android.support.v4.os.CancellationSignal r15_cancellationSignal) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.ContentResolverCompat.query(android.content.ContentResolver, android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, android.support.v4.os.CancellationSignal):android.database.Cursor");
        /*
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 16;
        if (r1 < r2) goto L_0x002b;
    L_0x0006:
        if (r15 == 0) goto L_0x001d;
    L_0x0008:
        r1 = r15.getCancellationSignalObject();	 Catch:{ Exception -> 0x001f }
    L_0x000c:
        r1 = (android.os.CancellationSignal) r1;	 Catch:{ Exception -> 0x001f }
        r0 = r1;
        r0 = (android.os.CancellationSignal) r0;	 Catch:{ Exception -> 0x001f }
        r7 = r0;
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r4 = r12;
        r5 = r13;
        r6 = r14;
        r1 = r1.query(r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x001f }
    L_0x001c:
        return r1;
    L_0x001d:
        r1 = 0;
        goto L_0x000c;
    L_0x001f:
        r8 = move-exception;
        r1 = r8 instanceof android.os.OperationCanceledException;
        if (r1 == 0) goto L_0x002a;
    L_0x0024:
        r1 = new android.support.v4.os.OperationCanceledException;
        r1.<init>();
        throw r1;
    L_0x002a:
        throw r8;
    L_0x002b:
        if (r15 == 0) goto L_0x0030;
    L_0x002d:
        r15.throwIfCanceled();
    L_0x0030:
        r1 = r9.query(r10, r11, r12, r13, r14);
        goto L_0x001c;
        */
    }
}
