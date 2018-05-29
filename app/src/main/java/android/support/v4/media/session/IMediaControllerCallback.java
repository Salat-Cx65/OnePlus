package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.text.TextUtils;
import java.util.List;

public interface IMediaControllerCallback extends IInterface {

    public static abstract class Stub extends Binder implements IMediaControllerCallback {
        private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback";
        static final int TRANSACTION_onCaptioningEnabledChanged = 11;
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onRepeatModeChanged = 9;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onShuffleModeChanged = 12;
        static final int TRANSACTION_onShuffleModeChangedDeprecated = 10;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        private static class Proxy implements IMediaControllerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            public void onEvent(String event, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeString(event);
                if (extras != null) {
                    _data.writeInt(TRANSACTION_onEvent);
                    extras.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                this.mRemote.transact(TRANSACTION_onEvent, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onSessionDestroyed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                this.mRemote.transact(TRANSACTION_onSessionDestroyed, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onPlaybackStateChanged(PlaybackStateCompat state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (state != null) {
                    _data.writeInt(TRANSACTION_onEvent);
                    state.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                this.mRemote.transact(TRANSACTION_onPlaybackStateChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onMetadataChanged(MediaMetadataCompat metadata) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (metadata != null) {
                    _data.writeInt(TRANSACTION_onEvent);
                    metadata.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                this.mRemote.transact(TRANSACTION_onMetadataChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onQueueChanged(List<QueueItem> queue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeTypedList(queue);
                this.mRemote.transact(TRANSACTION_onQueueChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onQueueTitleChanged(CharSequence title) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (title != null) {
                    _data.writeInt(TRANSACTION_onEvent);
                    TextUtils.writeToParcel(title, _data, 0);
                } else {
                    _data.writeInt(0);
                }
                this.mRemote.transact(TRANSACTION_onQueueTitleChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onExtrasChanged(Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (extras != null) {
                    _data.writeInt(TRANSACTION_onEvent);
                    extras.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                this.mRemote.transact(TRANSACTION_onExtrasChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (info != null) {
                    _data.writeInt(TRANSACTION_onEvent);
                    info.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                this.mRemote.transact(TRANSACTION_onVolumeInfoChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onRepeatModeChanged(int repeatMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(repeatMode);
                this.mRemote.transact(TRANSACTION_onRepeatModeChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onShuffleModeChangedDeprecated(boolean enabled) throws RemoteException {
                int i = TRANSACTION_onEvent;
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (!enabled) {
                    i = 0;
                }
                _data.writeInt(i);
                this.mRemote.transact(TRANSACTION_onShuffleModeChangedDeprecated, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onCaptioningEnabledChanged(boolean enabled) throws RemoteException {
                int i = TRANSACTION_onEvent;
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (!enabled) {
                    i = 0;
                }
                _data.writeInt(i);
                this.mRemote.transact(TRANSACTION_onCaptioningEnabledChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }

            public void onShuffleModeChanged(int shuffleMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(shuffleMode);
                this.mRemote.transact(TRANSACTION_onShuffleModeChanged, _data, null, TRANSACTION_onEvent);
                _data.recycle();
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaControllerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            return (iin == null || !(iin instanceof IMediaControllerCallback)) ? new Proxy(obj) : (IMediaControllerCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg0 = false;
            switch (code) {
                case TRANSACTION_onEvent:
                    Bundle _arg1;
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    onEvent(_arg02, _arg1);
                    return true;
                case TRANSACTION_onSessionDestroyed:
                    data.enforceInterface(DESCRIPTOR);
                    onSessionDestroyed();
                    return true;
                case TRANSACTION_onPlaybackStateChanged:
                    PlaybackStateCompat _arg03;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    onPlaybackStateChanged(_arg03);
                    return true;
                case TRANSACTION_onMetadataChanged:
                    MediaMetadataCompat _arg04;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    onMetadataChanged(_arg04);
                    return true;
                case TRANSACTION_onQueueChanged:
                    data.enforceInterface(DESCRIPTOR);
                    onQueueChanged(data.createTypedArrayList(QueueItem.CREATOR));
                    return true;
                case TRANSACTION_onQueueTitleChanged:
                    CharSequence _arg05;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg05 = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
                    } else {
                        _arg05 = null;
                    }
                    onQueueTitleChanged(_arg05);
                    return true;
                case TRANSACTION_onExtrasChanged:
                    Bundle _arg06;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg06 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg06 = null;
                    }
                    onExtrasChanged(_arg06);
                    return true;
                case TRANSACTION_onVolumeInfoChanged:
                    ParcelableVolumeInfo _arg07;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg07 = (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg07 = null;
                    }
                    onVolumeInfoChanged(_arg07);
                    return true;
                case TRANSACTION_onRepeatModeChanged:
                    data.enforceInterface(DESCRIPTOR);
                    onRepeatModeChanged(data.readInt());
                    return true;
                case TRANSACTION_onShuffleModeChangedDeprecated:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    onShuffleModeChangedDeprecated(_arg0);
                    return true;
                case TRANSACTION_onCaptioningEnabledChanged:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    onCaptioningEnabledChanged(_arg0);
                    return true;
                case TRANSACTION_onShuffleModeChanged:
                    data.enforceInterface(DESCRIPTOR);
                    onShuffleModeChanged(data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onCaptioningEnabledChanged(boolean z) throws RemoteException;

    void onEvent(String str, Bundle bundle) throws RemoteException;

    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException;

    void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException;

    void onQueueChanged(List<QueueItem> list) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onRepeatModeChanged(int i) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onShuffleModeChanged(int i) throws RemoteException;

    void onShuffleModeChangedDeprecated(boolean z) throws RemoteException;

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException;
}
