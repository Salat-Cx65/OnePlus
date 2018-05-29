package com.google.android.gms.internal;

import java.io.IOException;

public final class aij {
    public static final String[] EMPTY_STRING_ARRAY;
    private static int zzcvi;
    private static int zzcvj;
    private static int zzcvk;
    private static int zzcvl;
    public static final int[] zzcvm;
    public static final long[] zzcvn;
    public static final float[] zzcvo;
    private static double[] zzcvp;
    public static final boolean[] zzcvq;
    public static final byte[][] zzcvr;
    public static final byte[] zzcvs;

    static {
        zzcvi = 11;
        zzcvj = 12;
        zzcvk = 16;
        zzcvl = 26;
        zzcvm = new int[0];
        zzcvn = new long[0];
        zzcvo = new float[0];
        zzcvp = new double[0];
        zzcvq = new boolean[0];
        EMPTY_STRING_ARRAY = new String[0];
        zzcvr = new byte[0][];
        zzcvs = new byte[0];
    }

    public static final int zzb(ahw com_google_android_gms_internal_ahw, int i) throws IOException {
        int i2 = 1;
        int position = com_google_android_gms_internal_ahw.getPosition();
        com_google_android_gms_internal_ahw.zzcl(i);
        while (com_google_android_gms_internal_ahw.zzLQ() == i) {
            com_google_android_gms_internal_ahw.zzcl(i);
            i2++;
        }
        com_google_android_gms_internal_ahw.zzq(position, i);
        return i2;
    }
}
