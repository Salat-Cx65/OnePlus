package com.google.android.gms.common.api;

import com.google.android.gms.internal.zzbcq;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends zzbcq<BatchResult> {
    private final Object mLock;
    private int zzaAE;
    private boolean zzaAF;
    private boolean zzaAG;
    private final PendingResult<?>[] zzaAH;

    public static final class Builder {
        private List<PendingResult<?>> zzaAJ;
        private GoogleApiClient zzapw;

        public Builder(GoogleApiClient googleApiClient) {
            this.zzaAJ = new ArrayList();
            this.zzapw = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.zzaAJ.size());
            this.zzaAJ.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zzapw, null);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zzaAE = list.size();
        this.zzaAH = new PendingResult[this.zzaAE];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.zzaBo, this.zzaAH));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult pendingResult = (PendingResult) list.get(i);
            this.zzaAH[i] = pendingResult;
            pendingResult.zza(new zzb(this));
        }
    }

    static /* synthetic */ int zzb(Batch batch) {
        int i = batch.zzaAE;
        batch.zzaAE = i - 1;
        return i;
    }

    public final void cancel() {
        super.cancel();
        for (PendingResult pendingResult : this.zzaAH) {
            pendingResult.cancel();
        }
    }

    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zzaAH);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return createFailedResult(status);
    }
}
