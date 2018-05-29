package com.loc;

import com.loc.be.a;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

// compiled from: DiskLruCache.java
public final class be implements Closeable {
    static final Pattern a;
    static ThreadPoolExecutor b;
    private static final ThreadFactory q;
    private static final OutputStream s;
    private final File c;
    private final File d;
    private final File e;
    private final File f;
    private final int g;
    private long h;
    private final int i;
    private long j;
    private Writer k;
    private int l;
    private final LinkedHashMap<String, c> m;
    private int n;
    private bf o;
    private long p;
    private final Callable<Void> r;

    // compiled from: DiskLruCache.java
    public final class a {
        private final c b;
        private final boolean[] c;
        private boolean d;
        private boolean e;

        // compiled from: DiskLruCache.java
        private class a extends FilterOutputStream {
            private a(OutputStream outputStream) {
                super(outputStream);
            }

            public final void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    com.loc.be.a.this.d = true;
                }
            }

            public final void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    com.loc.be.a.this.d = true;
                }
            }

            public final void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException e) {
                    com.loc.be.a.this.d = true;
                }
            }

            public final void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException e) {
                    com.loc.be.a.this.d = true;
                }
            }
        }

        private a(c cVar) {
            this.b = cVar;
            this.c = cVar.d ? null : new boolean[be.this.i];
        }

        public final OutputStream a() throws IOException {
            if (be.this.i <= 0) {
                throw new IllegalArgumentException(new StringBuilder("Expected index 0 to be greater than 0 and less than the maximum value count of ").append(be.this.i).toString());
            }
            OutputStream f;
            synchronized (be.this) {
                if (this.b.e != this) {
                    throw new IllegalStateException();
                }
                OutputStream fileOutputStream;
                if (!this.b.d) {
                    this.c[0] = true;
                }
                File b = this.b.b(0);
                try {
                    fileOutputStream = new FileOutputStream(b);
                } catch (FileNotFoundException e) {
                    be.this.c.mkdirs();
                    try {
                        fileOutputStream = new FileOutputStream(b);
                    } catch (FileNotFoundException e2) {
                        f = s;
                    }
                }
                f = new a(fileOutputStream, (byte) 0);
            }
            return f;
        }

        public final void b() throws IOException {
            if (this.d) {
                be.this.a(this, false);
                be.this.c(this.b.b);
            } else {
                be.this.a(this, true);
            }
            this.e = true;
        }

        public final void c() throws IOException {
            be.this.a(this, false);
        }
    }

    // compiled from: DiskLruCache.java
    public final class b implements Closeable {
        private final String b;
        private final long c;
        private final InputStream[] d;
        private final long[] e;

        private b(String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.b = str;
            this.c = j;
            this.d = inputStreamArr;
            this.e = jArr;
        }

        public final InputStream a() {
            return this.d[0];
        }

        public final void close() {
            for (Closeable closeable : this.d) {
                bh.a(closeable);
            }
        }
    }

    // compiled from: DiskLruCache.java
    private final class c {
        private final String b;
        private final long[] c;
        private boolean d;
        private a e;
        private long f;

        private c(String str) {
            this.b = str;
            this.c = new long[be.this.i];
        }

        private static IOException a(String[] strArr) throws IOException {
            throw new IOException(new StringBuilder("unexpected journal line: ").append(Arrays.toString(strArr)).toString());
        }

        static /* synthetic */ void a(c cVar, String[] strArr) throws IOException {
            if (strArr.length != be.this.i) {
                throw a(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    cVar.c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw a(strArr);
                }
            }
        }

        public final File a(int i) {
            return new File(be.this.c, this.b + "." + i);
        }

        public final String a() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long j : this.c) {
                stringBuilder.append(' ').append(j);
            }
            return stringBuilder.toString();
        }

        public final File b(int i) {
            return new File(be.this.c, this.b + "." + i + ".tmp");
        }
    }

    static {
        a = Pattern.compile("[a-z0-9_-]{1,120}");
        q = new ThreadFactory() {
            private final AtomicInteger a;

            {
                this.a = new AtomicInteger(1);
            }

            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, new StringBuilder("disklrucache#").append(this.a.getAndIncrement()).toString());
            }
        };
        b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), q);
        s = new OutputStream() {
            public final void write(int i) throws IOException {
            }
        };
    }

    private be(File file, long j) {
        this.j = 0;
        this.l = 1000;
        this.m = new LinkedHashMap(0, 0.75f, true);
        this.p = 0;
        this.r = new Callable<Void>() {
            private Void a() throws Exception {
                synchronized (be.this) {
                    if (be.this.k == null) {
                    } else {
                        be.this.m();
                        if (be.this.k()) {
                            be.this.j();
                            be.this.n = 0;
                        }
                    }
                }
                return null;
            }

            public final /* synthetic */ Object call() throws Exception {
                return a();
            }
        };
        this.c = file;
        this.g = 1;
        this.d = new File(file, "journal");
        this.e = new File(file, "journal.tmp");
        this.f = new File(file, "journal.bkp");
        this.i = 1;
        this.h = j;
    }

    public static be a(File file, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        File file2 = new File(file, "journal.bkp");
        if (file2.exists()) {
            File file3 = new File(file, "journal");
            if (file3.exists()) {
                file2.delete();
            } else {
                a(file2, file3, false);
            }
        }
        be beVar = new be(file, j);
        if (beVar.d.exists()) {
            try {
                beVar.h();
                beVar.i();
                beVar.k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beVar.d, true), bh.a));
                return beVar;
            } catch (Throwable th) {
                beVar.e();
            }
        }
        file.mkdirs();
        beVar = new be(file, j);
        beVar.j();
        return beVar;
    }

    public static void a() {
        if (b != null && !b.isShutdown()) {
            b.shutdown();
        }
    }

    private synchronized void a(a aVar, boolean z) throws IOException {
        int i = 0;
        synchronized (this) {
            c a = aVar.b;
            if (a.e != aVar) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.d) {
                    int i2 = 0;
                    while (i2 < this.i) {
                        if (!aVar.c[i2]) {
                            aVar.c();
                            throw new IllegalStateException(new StringBuilder("Newly created entry didn't create value for index ").append(i2).toString());
                        } else if (!a.b(i2).exists()) {
                            aVar.c();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.i) {
                File b = a.b(i);
                if (!z) {
                    a(b);
                } else if (b.exists()) {
                    File a2 = a.a(i);
                    b.renameTo(a2);
                    long j = a.c[i];
                    long length = a2.length();
                    a.c[i] = length;
                    this.j = (this.j - j) + length;
                }
                i++;
            }
            this.n++;
            a.e = null;
            if ((a.d | z) != 0) {
                a.d = true;
                this.k.write(new StringBuilder("CLEAN ").append(a.b).append(a.a()).append('\n').toString());
                if (z) {
                    long j2 = this.p;
                    this.p = 1 + j2;
                    a.f = j2;
                }
            } else {
                this.m.remove(a.b);
                this.k.write(new StringBuilder("REMOVE ").append(a.b).append('\n').toString());
            }
            this.k.flush();
            if (this.j > this.h || k()) {
                g().submit(this.r);
            }
        }
    }

    private static void a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    private synchronized a d(String str) throws IOException {
        a aVar;
        l();
        e(str);
        c cVar = (c) this.m.get(str);
        if (-1 == -1 || (cVar != null && cVar.f == -1)) {
            c cVar2;
            if (cVar == null) {
                cVar = new c(str, (byte) 0);
                this.m.put(str, cVar);
                cVar2 = cVar;
            } else if (cVar.e != null) {
                aVar = null;
            } else {
                cVar2 = cVar;
            }
            aVar = new a(cVar2, (byte) 0);
            cVar2.e = aVar;
            this.k.write(new StringBuilder("DIRTY ").append(str).append('\n').toString());
            this.k.flush();
        } else {
            aVar = null;
        }
        return aVar;
    }

    private static void e(String str) {
        if (!a.matcher(str).matches()) {
            throw new IllegalArgumentException(new StringBuilder("keys must match regex [a-z0-9_-]{1,120}: \"").append(str).append("\"").toString());
        }
    }

    private static ThreadPoolExecutor g() {
        try {
            if (b == null || b.isShutdown()) {
                b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), q);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return b;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() throws java.io.IOException {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.be.h():void");
        /*
        this = this;
        r9 = 5;
        r0 = 0;
        r8 = -1;
        r3 = new com.loc.bg;
        r1 = new java.io.FileInputStream;
        r2 = r10.d;
        r1.<init>(r2);
        r2 = com.loc.bh.a;
        r3.<init>(r1, r2);
        r1 = r3.a();	 Catch:{ all -> 0x008e }
        r2 = r3.a();	 Catch:{ all -> 0x008e }
        r4 = r3.a();	 Catch:{ all -> 0x008e }
        r5 = r3.a();	 Catch:{ all -> 0x008e }
        r6 = r3.a();	 Catch:{ all -> 0x008e }
        r7 = "libcore.io.DiskLruCache";
        r7 = r7.equals(r1);	 Catch:{ all -> 0x008e }
        if (r7 == 0) goto L_0x0055;
    L_0x002d:
        r7 = "1";
        r7 = r7.equals(r2);	 Catch:{ all -> 0x008e }
        if (r7 == 0) goto L_0x0055;
    L_0x0035:
        r7 = r10.g;	 Catch:{ all -> 0x008e }
        r7 = java.lang.Integer.toString(r7);	 Catch:{ all -> 0x008e }
        r4 = r7.equals(r4);	 Catch:{ all -> 0x008e }
        if (r4 == 0) goto L_0x0055;
    L_0x0041:
        r4 = r10.i;	 Catch:{ all -> 0x008e }
        r4 = java.lang.Integer.toString(r4);	 Catch:{ all -> 0x008e }
        r4 = r4.equals(r5);	 Catch:{ all -> 0x008e }
        if (r4 == 0) goto L_0x0055;
    L_0x004d:
        r4 = "";
        r4 = r4.equals(r6);	 Catch:{ all -> 0x008e }
        if (r4 != 0) goto L_0x0093;
    L_0x0055:
        r0 = new java.io.IOException;	 Catch:{ all -> 0x008e }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x008e }
        r7 = "unexpected journal header: [";
        r4.<init>(r7);	 Catch:{ all -> 0x008e }
        r1 = r4.append(r1);	 Catch:{ all -> 0x008e }
        r4 = ", ";
        r1 = r1.append(r4);	 Catch:{ all -> 0x008e }
        r1 = r1.append(r2);	 Catch:{ all -> 0x008e }
        r2 = ", ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x008e }
        r1 = r1.append(r5);	 Catch:{ all -> 0x008e }
        r2 = ", ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x008e }
        r1 = r1.append(r6);	 Catch:{ all -> 0x008e }
        r2 = "]";
        r1 = r1.append(r2);	 Catch:{ all -> 0x008e }
        r1 = r1.toString();	 Catch:{ all -> 0x008e }
        r0.<init>(r1);	 Catch:{ all -> 0x008e }
        throw r0;	 Catch:{ all -> 0x008e }
    L_0x008e:
        r0 = move-exception;
        com.loc.bh.a(r3);
        throw r0;
    L_0x0093:
        r1 = r0;
    L_0x0094:
        r4 = r3.a();	 Catch:{ EOFException -> 0x00b5 }
        r0 = 32;
        r5 = r4.indexOf(r0);	 Catch:{ EOFException -> 0x00b5 }
        if (r5 != r8) goto L_0x00c4;
    L_0x00a0:
        r0 = new java.io.IOException;	 Catch:{ EOFException -> 0x00b5 }
        r2 = new java.lang.StringBuilder;	 Catch:{ EOFException -> 0x00b5 }
        r5 = "unexpected journal line: ";
        r2.<init>(r5);	 Catch:{ EOFException -> 0x00b5 }
        r2 = r2.append(r4);	 Catch:{ EOFException -> 0x00b5 }
        r2 = r2.toString();	 Catch:{ EOFException -> 0x00b5 }
        r0.<init>(r2);	 Catch:{ EOFException -> 0x00b5 }
        throw r0;	 Catch:{ EOFException -> 0x00b5 }
    L_0x00b5:
        r0 = move-exception;
        r0 = r10.m;	 Catch:{ all -> 0x008e }
        r0 = r0.size();	 Catch:{ all -> 0x008e }
        r0 = r1 - r0;
        r10.n = r0;	 Catch:{ all -> 0x008e }
        com.loc.bh.a(r3);
        return;
    L_0x00c4:
        r0 = r5 + 1;
        r2 = 32;
        r6 = r4.indexOf(r2, r0);	 Catch:{ EOFException -> 0x00b5 }
        if (r6 != r8) goto L_0x00e6;
    L_0x00ce:
        r0 = r4.substring(r0);	 Catch:{ EOFException -> 0x00b5 }
        r2 = 6;
        if (r5 != r2) goto L_0x015b;
    L_0x00d5:
        r2 = "REMOVE";
        r2 = r4.startsWith(r2);	 Catch:{ EOFException -> 0x00b5 }
        if (r2 == 0) goto L_0x015b;
    L_0x00dd:
        r2 = r10.m;	 Catch:{ EOFException -> 0x00b5 }
        r2.remove(r0);	 Catch:{ EOFException -> 0x00b5 }
    L_0x00e2:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0094;
    L_0x00e6:
        r0 = r4.substring(r0, r6);	 Catch:{ EOFException -> 0x00b5 }
        r2 = r0;
    L_0x00eb:
        r0 = r10.m;	 Catch:{ EOFException -> 0x00b5 }
        r0 = r0.get(r2);	 Catch:{ EOFException -> 0x00b5 }
        r0 = (com.loc.be.c) r0;	 Catch:{ EOFException -> 0x00b5 }
        if (r0 != 0) goto L_0x0100;
    L_0x00f5:
        r0 = new com.loc.be$c;	 Catch:{ EOFException -> 0x00b5 }
        r7 = 0;
        r0.<init>(r2, r7);	 Catch:{ EOFException -> 0x00b5 }
        r7 = r10.m;	 Catch:{ EOFException -> 0x00b5 }
        r7.put(r2, r0);	 Catch:{ EOFException -> 0x00b5 }
    L_0x0100:
        if (r6 == r8) goto L_0x0123;
    L_0x0102:
        if (r5 != r9) goto L_0x0123;
    L_0x0104:
        r2 = "CLEAN";
        r2 = r4.startsWith(r2);	 Catch:{ EOFException -> 0x00b5 }
        if (r2 == 0) goto L_0x0123;
    L_0x010c:
        r2 = r6 + 1;
        r2 = r4.substring(r2);	 Catch:{ EOFException -> 0x00b5 }
        r4 = " ";
        r2 = r2.split(r4);	 Catch:{ EOFException -> 0x00b5 }
        r0.d = true;	 Catch:{ EOFException -> 0x00b5 }
        r4 = 0;
        r0.e = r4;	 Catch:{ EOFException -> 0x00b5 }
        com.loc.be.c.a(r0, r2);	 Catch:{ EOFException -> 0x00b5 }
        goto L_0x00e2;
    L_0x0123:
        if (r6 != r8) goto L_0x0139;
    L_0x0125:
        if (r5 != r9) goto L_0x0139;
    L_0x0127:
        r2 = "DIRTY";
        r2 = r4.startsWith(r2);	 Catch:{ EOFException -> 0x00b5 }
        if (r2 == 0) goto L_0x0139;
    L_0x012f:
        r2 = new com.loc.be$a;	 Catch:{ EOFException -> 0x00b5 }
        r4 = 0;
        r2.<init>(r0, r4);	 Catch:{ EOFException -> 0x00b5 }
        r0.e = r2;	 Catch:{ EOFException -> 0x00b5 }
        goto L_0x00e2;
    L_0x0139:
        if (r6 != r8) goto L_0x0146;
    L_0x013b:
        r0 = 4;
        if (r5 != r0) goto L_0x0146;
    L_0x013e:
        r0 = "READ";
        r0 = r4.startsWith(r0);	 Catch:{ EOFException -> 0x00b5 }
        if (r0 != 0) goto L_0x00e2;
    L_0x0146:
        r0 = new java.io.IOException;	 Catch:{ EOFException -> 0x00b5 }
        r2 = new java.lang.StringBuilder;	 Catch:{ EOFException -> 0x00b5 }
        r5 = "unexpected journal line: ";
        r2.<init>(r5);	 Catch:{ EOFException -> 0x00b5 }
        r2 = r2.append(r4);	 Catch:{ EOFException -> 0x00b5 }
        r2 = r2.toString();	 Catch:{ EOFException -> 0x00b5 }
        r0.<init>(r2);	 Catch:{ EOFException -> 0x00b5 }
        throw r0;	 Catch:{ EOFException -> 0x00b5 }
    L_0x015b:
        r2 = r0;
        goto L_0x00eb;
        */
    }

    private void i() throws IOException {
        a(this.e);
        Iterator it = this.m.values().iterator();
        while (it.hasNext()) {
            c cVar = (c) it.next();
            int i;
            if (cVar.e == null) {
                for (i = 0; i < this.i; i++) {
                    this.j += cVar.c[i];
                }
            } else {
                cVar.e = null;
                for (i = 0; i < this.i; i++) {
                    a(cVar.a(i));
                    a(cVar.b(i));
                }
                it.remove();
            }
        }
    }

    private synchronized void j() throws IOException {
        try {
            if (this.k != null) {
                this.k.close();
            }
            Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.e), bh.a));
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.i));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (c cVar : this.m.values()) {
                if (cVar.e != null) {
                    bufferedWriter.write(new StringBuilder("DIRTY ").append(cVar.b).append('\n').toString());
                } else {
                    bufferedWriter.write(new StringBuilder("CLEAN ").append(cVar.b).append(cVar.a()).append('\n').toString());
                }
            }
            bufferedWriter.close();
            if (this.d.exists()) {
                a(this.d, this.f, true);
            }
            a(this.e, this.d, false);
            this.f.delete();
            this.k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d, true), bh.a));
        } catch (Throwable th) {
        }
    }

    private boolean k() {
        return this.n >= 2000 && this.n >= this.m.size();
    }

    private void l() {
        if (this.k == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    private void m() throws IOException {
        while (true) {
            if (this.j > this.h || this.m.size() > this.l) {
                String str = (String) ((Entry) this.m.entrySet().iterator().next()).getKey();
                c(str);
                if (this.o != null) {
                    this.o.a(str);
                }
            } else {
                return;
            }
        }
    }

    public final synchronized b a(String str) throws IOException {
        b bVar = null;
        synchronized (this) {
            l();
            e(str);
            c cVar = (c) this.m.get(str);
            if (cVar != null) {
                if (cVar.d) {
                    InputStream[] inputStreamArr = new InputStream[this.i];
                    int i = 0;
                    while (i < this.i) {
                        try {
                            inputStreamArr[i] = new FileInputStream(cVar.a(i));
                            i++;
                        } catch (FileNotFoundException e) {
                            int i2 = 0;
                            while (i2 < this.i && inputStreamArr[i2] != null) {
                                bh.a(inputStreamArr[i2]);
                                i2++;
                            }
                        }
                    }
                    this.n++;
                    this.k.append(new StringBuilder("READ ").append(str).append('\n').toString());
                    if (k()) {
                        g().submit(this.r);
                    }
                    bVar = new b(str, cVar.f, inputStreamArr, cVar.c, (byte) 0);
                }
            }
        }
        return bVar;
    }

    public final void a(int i) {
        if (i < 10) {
            i = 10;
        } else if (i > 10000) {
            i = 10000;
        }
        this.l = i;
    }

    public final void a(bf bfVar) {
        this.o = bfVar;
    }

    public final a b(String str) throws IOException {
        return d(str);
    }

    public final File b() {
        return this.c;
    }

    public final synchronized boolean c() {
        return this.k == null;
    }

    public final synchronized boolean c(String str) throws IOException {
        boolean z;
        int i = 0;
        synchronized (this) {
            l();
            e(str);
            c cVar = (c) this.m.get(str);
            if (cVar == null || cVar.e != null) {
                z = false;
            } else {
                while (i < this.i) {
                    File a = cVar.a(i);
                    if (!a.exists() || a.delete()) {
                        this.j -= cVar.c[i];
                        cVar.c[i] = 0;
                        i++;
                    } else {
                        throw new IOException(new StringBuilder("failed to delete ").append(a).toString());
                    }
                }
                this.n++;
                this.k.append(new StringBuilder("REMOVE ").append(str).append('\n').toString());
                this.m.remove(str);
                if (k()) {
                    g().submit(this.r);
                }
                z = true;
            }
        }
        return z;
    }

    public final synchronized void close() throws IOException {
        if (this.k != null) {
            Iterator it = new ArrayList(this.m.values()).iterator();
            while (it.hasNext()) {
                c cVar = (c) it.next();
                if (cVar.e != null) {
                    cVar.e.c();
                }
            }
            m();
            this.k.close();
            this.k = null;
        }
    }

    public final synchronized void d() throws IOException {
        l();
        m();
        this.k.flush();
    }

    public final void e() throws IOException {
        close();
        bh.a(this.c);
    }
}
