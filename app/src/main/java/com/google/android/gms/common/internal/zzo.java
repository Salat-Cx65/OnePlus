package com.google.android.gms.common.internal;

import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

public final class zzo extends zze {
    private /* synthetic */ zzd zzaHg;

    @BinderThread
    public zzo(zzd com_google_android_gms_common_internal_zzd, @Nullable int i, 
    /* Method generation error in method: com.google.android.gms.common.internal.zzo.<init>(com.google.android.gms.common.internal.zzd, int, android.os.Bundle):void
java.lang.IndexOutOfBoundsException: Index: 2, Size: 2
	at java.util.ArrayList.rangeCheck(Unknown Source)
	at java.util.ArrayList.get(Unknown Source)
	at jadx.core.codegen.AnnotationGen.addForParameter(AnnotationGen.java:45)
	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:126)
	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:110)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:310)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:259)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:222)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:114)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:79)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:11)
	at jadx.core.ProcessClass.process(ProcessClass.java:22)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:209)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:133)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
 */

    protected final void zzj(ConnectionResult connectionResult) {
        this.zzaHg.zzaGS.zzf(connectionResult);
        this.zzaHg.onConnectionFailed(connectionResult);
    }

    protected final boolean zzrh() {
        this.zzaHg.zzaGS.zzf(ConnectionResult.zzazZ);
        return true;
    }
}
