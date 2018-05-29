package android.support.v4.media;

import android.media.VolumeProvider;
import android.support.annotation.RequiresApi;
import android.support.v4.media.VolumeProviderCompatApi21.Delegate;

@RequiresApi(21)
class VolumeProviderCompatApi21 {

    static class AnonymousClass_1 extends VolumeProvider {
        final /* synthetic */ Delegate val$delegate;

        AnonymousClass_1(int x0, int x1, int x2, Delegate delegate) {
            this.val$delegate = delegate;
            super(x0, x1, x2);
        }

        public void onSetVolumeTo(int volume) {
            this.val$delegate.onSetVolumeTo(volume);
        }

        public void onAdjustVolume(int direction) {
            this.val$delegate.onAdjustVolume(direction);
        }
    }

    public static interface Delegate {
        void onAdjustVolume(int i);

        void onSetVolumeTo(int i);
    }

    VolumeProviderCompatApi21() {
    }

    public static Object createVolumeProvider(int volumeControl, int maxVolume, int currentVolume, Delegate delegate) {
        return new AnonymousClass_1(volumeControl, maxVolume, currentVolume, delegate);
    }

    public static void setCurrentVolume(Object volumeProviderObj, int currentVolume) {
        ((VolumeProvider) volumeProviderObj).setCurrentVolume(currentVolume);
    }
}
