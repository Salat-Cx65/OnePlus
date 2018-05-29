package com.loc;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

// compiled from: Util.java
public final class bh {
    public static final Charset a;
    static final Charset b;

    static {
        a = Charset.forName("US-ASCII");
        b = Charset.forName("UTF-8");
    }

    static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    static void a(File file) throws IOException {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new IOException(new StringBuilder("not a readable directory: ").append(file).toString());
        }
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            File file2 = listFiles[i];
            if (file2.isDirectory()) {
                a(file2);
            }
            if (file2.delete()) {
                i++;
            } else {
                throw new IOException(new StringBuilder("failed to delete file: ").append(file2).toString());
            }
        }
    }
}
