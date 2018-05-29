package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat.ConnectionCallback;
import android.support.v4.media.MediaBrowserCompat.CustomActionCallback;
import android.support.v4.media.MediaBrowserCompat.ItemCallback;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserCompat.SearchCallback;
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class MediaBrowserCompat {
    public static final String CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD";
    public static final String CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE";
    static final boolean DEBUG;
    public static final String EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS";
    public static final String EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID";
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    static final String TAG = "MediaBrowserCompat";
    private final MediaBrowserImpl mImpl;

    private static class CallbackHandler extends Handler {
        private final WeakReference<MediaBrowserServiceCallbackImpl> mCallbackImplRef;
        private WeakReference<Messenger> mCallbacksMessengerRef;

        CallbackHandler(MediaBrowserServiceCallbackImpl callbackImpl) {
            this.mCallbackImplRef = new WeakReference(callbackImpl);
        }

        public void handleMessage(Message msg) {
            if (this.mCallbacksMessengerRef != null && this.mCallbacksMessengerRef.get() != null && this.mCallbackImplRef.get() != null) {
                Bundle data = msg.getData();
                data.setClassLoader(MediaSessionCompat.class.getClassLoader());
                MediaBrowserServiceCallbackImpl serviceCallback = (MediaBrowserServiceCallbackImpl) this.mCallbackImplRef.get();
                Messenger callbacksMessenger = (Messenger) this.mCallbacksMessengerRef.get();
                try {
                    switch (msg.what) {
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                            serviceCallback.onServiceConnected(callbacksMessenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (Token) data.getParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS));
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            serviceCallback.onConnectionFailed(callbacksMessenger);
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                            serviceCallback.onLoadChildren(callbacksMessenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), data.getParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST), data.getBundle(MediaBrowserProtocol.DATA_OPTIONS));
                        default:
                            Log.w(TAG, "Unhandled message: " + msg + "\n  Client version: " + 1 + "\n  Service version: " + msg.arg1);
                    }
                } catch (BadParcelableException e) {
                    Log.e(TAG, "Could not unparcel the data.");
                    if (msg.what == 1) {
                        serviceCallback.onConnectionFailed(callbacksMessenger);
                    }
                }
            }
        }

        void setCallbacksMessenger(Messenger callbacksMessenger) {
            this.mCallbacksMessengerRef = new WeakReference(callbacksMessenger);
        }
    }

    public static class ConnectionCallback {
        ConnectionCallbackInternal mConnectionCallbackInternal;
        final Object mConnectionCallbackObj;

        static interface ConnectionCallbackInternal {
            void onConnected();

            void onConnectionFailed();

            void onConnectionSuspended();
        }

        private class StubApi21 implements ConnectionCallback {
            StubApi21() {
            }

            public void onConnected() {
                if (android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal.onConnected();
                }
                android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.onConnected();
            }

            public void onConnectionSuspended() {
                if (android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended();
                }
                android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.onConnectionSuspended();
            }

            public void onConnectionFailed() {
                if (android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed();
                }
                android.support.v4.media.MediaBrowserCompat.ConnectionCallback.this.onConnectionFailed();
            }
        }

        public ConnectionCallback() {
            if (VERSION.SDK_INT >= 21) {
                this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21());
            } else {
                this.mConnectionCallbackObj = null;
            }
        }

        public void onConnected() {
        }

        public void onConnectionSuspended() {
        }

        public void onConnectionFailed() {
        }

        void setInternalConnectionCallback(ConnectionCallbackInternal connectionCallbackInternal) {
            this.mConnectionCallbackInternal = connectionCallbackInternal;
        }
    }

    public static abstract class CustomActionCallback {
        public void onProgressUpdate(String action, Bundle extras, Bundle data) {
        }

        public void onResult(String action, Bundle extras, Bundle resultData) {
        }

        public void onError(String action, Bundle extras, Bundle data) {
        }
    }

    public static abstract class ItemCallback {
        final Object mItemCallbackObj;

        private class StubApi23 implements ItemCallback {
            StubApi23() {
            }

            public void onItemLoaded(Parcel itemParcel) {
                if (itemParcel == null) {
                    android.support.v4.media.MediaBrowserCompat.ItemCallback.this.onItemLoaded(null);
                    return;
                }
                itemParcel.setDataPosition(0);
                MediaItem item = (MediaItem) MediaItem.CREATOR.createFromParcel(itemParcel);
                itemParcel.recycle();
                android.support.v4.media.MediaBrowserCompat.ItemCallback.this.onItemLoaded(item);
            }

            public void onError(@NonNull String itemId) {
                android.support.v4.media.MediaBrowserCompat.ItemCallback.this.onError(itemId);
            }
        }

        public ItemCallback() {
            if (VERSION.SDK_INT >= 23) {
                this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(new StubApi23());
            } else {
                this.mItemCallbackObj = null;
            }
        }

        public void onItemLoaded(MediaItem item) {
        }

        public void onError(@NonNull String itemId) {
        }
    }

    static interface MediaBrowserImpl {
        void connect();

        void disconnect();

        @Nullable
        Bundle getExtras();

        void getItem(@NonNull String str, @NonNull ItemCallback itemCallback);

        @NonNull
        String getRoot();

        ComponentName getServiceComponent();

        @NonNull
        Token getSessionToken();

        boolean isConnected();

        void search(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback);

        void sendCustomAction(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback);

        void subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback);

        void unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback);
    }

    static interface MediaBrowserServiceCallbackImpl {
        void onConnectionFailed(Messenger messenger);

        void onLoadChildren(Messenger messenger, String str, List list, Bundle bundle);

        void onServiceConnected(Messenger messenger, String str, Token token, Bundle bundle);
    }

    public static class MediaItem implements Parcelable {
        public static final Creator<android.support.v4.media.MediaBrowserCompat.MediaItem> CREATOR;
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescriptionCompat mDescription;
        private final int mFlags;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public static @interface Flags {
        }

        public static android.support.v4.media.MediaBrowserCompat.MediaItem fromMediaItem(Object itemObj) {
            if (itemObj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            return new android.support.v4.media.MediaBrowserCompat.MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaItem.getDescription(itemObj)), MediaItem.getFlags(itemObj));
        }

        public static List<android.support.v4.media.MediaBrowserCompat.MediaItem> fromMediaItemList(List<?> itemList) {
            if (itemList == null || VERSION.SDK_INT < 21) {
                return null;
            }
            List<android.support.v4.media.MediaBrowserCompat.MediaItem> items = new ArrayList(itemList.size());
            for (Object itemObj : itemList) {
                items.add(fromMediaItem(itemObj));
            }
            return items;
        }

        public MediaItem(@NonNull MediaDescriptionCompat description, int flags) {
            if (description == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (TextUtils.isEmpty(description.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            } else {
                this.mFlags = flags;
                this.mDescription = description;
            }
        }

        MediaItem(Parcel in) {
            this.mFlags = in.readInt();
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(in);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.mFlags);
            this.mDescription.writeToParcel(out, flags);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("MediaItem{");
            sb.append("mFlags=").append(this.mFlags);
            sb.append(", mDescription=").append(this.mDescription);
            sb.append('}');
            return sb.toString();
        }

        static {
            CREATOR = new Creator<android.support.v4.media.MediaBrowserCompat.MediaItem>() {
                public android.support.v4.media.MediaBrowserCompat.MediaItem createFromParcel(Parcel in) {
                    return new android.support.v4.media.MediaBrowserCompat.MediaItem(in);
                }

                public android.support.v4.media.MediaBrowserCompat.MediaItem[] newArray(int size) {
                    return new android.support.v4.media.MediaBrowserCompat.MediaItem[size];
                }
            };
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean isBrowsable() {
            return (this.mFlags & 1) != 0 ? true : DEBUG;
        }

        public boolean isPlayable() {
            return (this.mFlags & 2) != 0 ? true : DEBUG;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        @Nullable
        public String getMediaId() {
            return this.mDescription.getMediaId();
        }
    }

    public static abstract class SearchCallback {
        public void onSearchResult(@NonNull String query, Bundle extras, @NonNull List<MediaItem> list) {
        }

        public void onError(@NonNull String query, Bundle extras) {
        }
    }

    private static class ServiceBinderWrapper {
        private Messenger mMessenger;
        private Bundle mRootHints;

        public ServiceBinderWrapper(IBinder target, Bundle rootHints) {
            this.mMessenger = new Messenger(target);
            this.mRootHints = rootHints;
        }

        void connect(Context context, Messenger callbacksMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, context.getPackageName());
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.mRootHints);
            sendRequest(1, data, callbacksMessenger);
        }

        void disconnect(Messenger callbacksMessenger) throws RemoteException {
            sendRequest(RainSurfaceView.RAIN_LEVEL_SHOWER, null, callbacksMessenger);
        }

        void addSubscription(String parentId, IBinder callbackToken, Bundle options, Messenger callbacksMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, parentId);
            BundleCompat.putBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, callbackToken);
            data.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            sendRequest(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, data, callbacksMessenger);
        }

        void removeSubscription(String parentId, IBinder callbackToken, Messenger callbacksMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, parentId);
            BundleCompat.putBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, callbackToken);
            sendRequest(RainSurfaceView.RAIN_LEVEL_RAINSTORM, data, callbacksMessenger);
        }

        void getMediaItem(String mediaId, ResultReceiver receiver, Messenger callbacksMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            data.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, receiver);
            sendRequest(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, data, callbacksMessenger);
        }

        void registerCallbackMessenger(Messenger callbackMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.mRootHints);
            sendRequest(ConnectionResult.RESOLUTION_REQUIRED, data, callbackMessenger);
        }

        void unregisterCallbackMessenger(Messenger callbackMessenger) throws RemoteException {
            sendRequest(DetectedActivity.WALKING, null, callbackMessenger);
        }

        void search(String query, Bundle extras, ResultReceiver receiver, Messenger callbacksMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_SEARCH_QUERY, query);
            data.putBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS, extras);
            data.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, receiver);
            sendRequest(DetectedActivity.RUNNING, data, callbacksMessenger);
        }

        void sendCustomAction(String action, Bundle extras, ResultReceiver receiver, Messenger callbacksMessenger) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_CUSTOM_ACTION, action);
            data.putBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS, extras);
            data.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, receiver);
            sendRequest(ConnectionResult.SERVICE_INVALID, data, callbacksMessenger);
        }

        private void sendRequest(int what, Bundle data, Messenger cbMessenger) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = what;
            msg.arg1 = 1;
            msg.setData(data);
            msg.replyTo = cbMessenger;
            this.mMessenger.send(msg);
        }
    }

    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks;
        private final List<Bundle> mOptionsList;

        public Subscription() {
            this.mCallbacks = new ArrayList();
            this.mOptionsList = new ArrayList();
        }

        public boolean isEmpty() {
            return this.mCallbacks.isEmpty();
        }

        public List<Bundle> getOptionsList() {
            return this.mOptionsList;
        }

        public List<SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public SubscriptionCallback getCallback(Context context, Bundle options) {
            if (options != null) {
                options.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get(i), options)) {
                    return (SubscriptionCallback) this.mCallbacks.get(i);
                }
            }
            return null;
        }

        public void putCallback(Context context, Bundle options, SubscriptionCallback callback) {
            if (options != null) {
                options.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get(i), options)) {
                    this.mCallbacks.set(i, callback);
                    return;
                }
            }
            this.mCallbacks.add(callback);
            this.mOptionsList.add(options);
        }
    }

    public static abstract class SubscriptionCallback {
        private final Object mSubscriptionCallbackObj;
        WeakReference<Subscription> mSubscriptionRef;
        private final IBinder mToken;

        private class StubApi21 implements SubscriptionCallback {
            StubApi21() {
            }

            public void onChildrenLoaded(@NonNull String parentId, List<?> children) {
                Subscription sub = android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.mSubscriptionRef == null ? null : (Subscription) android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.mSubscriptionRef.get();
                if (sub == null) {
                    android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(parentId, MediaItem.fromMediaItemList(children));
                    return;
                }
                List<MediaItem> itemList = MediaItem.fromMediaItemList(children);
                List<android.support.v4.media.MediaBrowserCompat.SubscriptionCallback> callbacks = sub.getCallbacks();
                List<Bundle> optionsList = sub.getOptionsList();
                for (int i = 0; i < callbacks.size(); i++) {
                    Bundle options = (Bundle) optionsList.get(i);
                    if (options == null) {
                        android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(parentId, itemList);
                    } else {
                        android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(parentId, applyOptions(itemList, options), options);
                    }
                }
            }

            public void onError(@NonNull String parentId) {
                android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.onError(parentId);
            }

            List<MediaItem> applyOptions(List<MediaItem> list, Bundle options) {
                if (list == null) {
                    return null;
                }
                int page = options.getInt(EXTRA_PAGE, -1);
                int pageSize = options.getInt(EXTRA_PAGE_SIZE, -1);
                if (page == -1 && pageSize == -1) {
                    return list;
                }
                int fromIndex = pageSize * page;
                int toIndex = fromIndex + pageSize;
                if (page < 0 || pageSize < 1 || fromIndex >= list.size()) {
                    return Collections.EMPTY_LIST;
                }
                if (toIndex > list.size()) {
                    toIndex = list.size();
                }
                return list.subList(fromIndex, toIndex);
            }
        }

        private class StubApi24 extends StubApi21 implements SubscriptionCallback {
            StubApi24() {
                super();
            }

            public void onChildrenLoaded(@NonNull String parentId, List<?> children, @NonNull Bundle options) {
                android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(parentId, MediaItem.fromMediaItemList(children), options);
            }

            public void onError(@NonNull String parentId, @NonNull Bundle options) {
                android.support.v4.media.MediaBrowserCompat.SubscriptionCallback.this.onError(parentId, options);
            }
        }

        public SubscriptionCallback() {
            if (VERSION.SDK_INT >= 26) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi24.createSubscriptionCallback(new StubApi24());
                this.mToken = null;
            } else if (VERSION.SDK_INT >= 21) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21());
                this.mToken = new Binder();
            } else {
                this.mSubscriptionCallbackObj = null;
                this.mToken = new Binder();
            }
        }

        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaItem> list) {
        }

        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaItem> list, @NonNull Bundle options) {
        }

        public void onError(@NonNull String parentId) {
        }

        public void onError(@NonNull String parentId, @NonNull Bundle options) {
        }

        private void setSubscription(Subscription subscription) {
            this.mSubscriptionRef = new WeakReference(subscription);
        }
    }

    private static class CustomActionResultReceiver extends ResultReceiver {
        private final String mAction;
        private final CustomActionCallback mCallback;
        private final Bundle mExtras;

        CustomActionResultReceiver(String action, Bundle extras, CustomActionCallback callback, Handler handler) {
            super(handler);
            this.mAction = action;
            this.mExtras = extras;
            this.mCallback = callback;
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (this.mCallback != null) {
                switch (resultCode) {
                    case RainSurfaceView.RAIN_LEVEL_DEFAULT:
                        this.mCallback.onError(this.mAction, this.mExtras, resultData);
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        this.mCallback.onResult(this.mAction, this.mExtras, resultData);
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        this.mCallback.onProgressUpdate(this.mAction, this.mExtras, resultData);
                    default:
                        Log.w(TAG, "Unknown result code: " + resultCode + " (extras=" + this.mExtras + ", resultData=" + resultData + ")");
                }
            }
        }
    }

    private static class ItemReceiver extends ResultReceiver {
        private final ItemCallback mCallback;
        private final String mMediaId;

        ItemReceiver(String mediaId, ItemCallback callback, Handler handler) {
            super(handler);
            this.mMediaId = mediaId;
            this.mCallback = callback;
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData != null) {
                resultData.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (resultCode == 0 && resultData != null && resultData.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                Parcelable item = resultData.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
                if (item == null || (item instanceof MediaItem)) {
                    this.mCallback.onItemLoaded((MediaItem) item);
                    return;
                } else {
                    this.mCallback.onError(this.mMediaId);
                    return;
                }
            }
            this.mCallback.onError(this.mMediaId);
        }
    }

    @RequiresApi(21)
    static class MediaBrowserImplApi21 implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl, ConnectionCallbackInternal {
        protected final Object mBrowserObj;
        protected Messenger mCallbacksMessenger;
        final Context mContext;
        protected final CallbackHandler mHandler;
        private Token mMediaSessionToken;
        protected final Bundle mRootHints;
        protected ServiceBinderWrapper mServiceBinderWrapper;
        private final ArrayMap<String, Subscription> mSubscriptions;

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ ItemCallback val$cb;
            final /* synthetic */ String val$mediaId;

            AnonymousClass_1(ItemCallback itemCallback, String str) {
                this.val$cb = itemCallback;
                this.val$mediaId = str;
            }

            public void run() {
                this.val$cb.onError(this.val$mediaId);
            }
        }

        class AnonymousClass_2 implements Runnable {
            final /* synthetic */ ItemCallback val$cb;
            final /* synthetic */ String val$mediaId;

            AnonymousClass_2(ItemCallback itemCallback, String str) {
                this.val$cb = itemCallback;
                this.val$mediaId = str;
            }

            public void run() {
                this.val$cb.onError(this.val$mediaId);
            }
        }

        class AnonymousClass_3 implements Runnable {
            final /* synthetic */ ItemCallback val$cb;
            final /* synthetic */ String val$mediaId;

            AnonymousClass_3(ItemCallback itemCallback, String str) {
                this.val$cb = itemCallback;
                this.val$mediaId = str;
            }

            public void run() {
                this.val$cb.onError(this.val$mediaId);
            }
        }

        class AnonymousClass_4 implements Runnable {
            final /* synthetic */ SearchCallback val$callback;
            final /* synthetic */ Bundle val$extras;
            final /* synthetic */ String val$query;

            AnonymousClass_4(SearchCallback searchCallback, String str, Bundle bundle) {
                this.val$callback = searchCallback;
                this.val$query = str;
                this.val$extras = bundle;
            }

            public void run() {
                this.val$callback.onError(this.val$query, this.val$extras);
            }
        }

        class AnonymousClass_5 implements Runnable {
            final /* synthetic */ SearchCallback val$callback;
            final /* synthetic */ Bundle val$extras;
            final /* synthetic */ String val$query;

            AnonymousClass_5(SearchCallback searchCallback, String str, Bundle bundle) {
                this.val$callback = searchCallback;
                this.val$query = str;
                this.val$extras = bundle;
            }

            public void run() {
                this.val$callback.onError(this.val$query, this.val$extras);
            }
        }

        class AnonymousClass_6 implements Runnable {
            final /* synthetic */ String val$action;
            final /* synthetic */ CustomActionCallback val$callback;
            final /* synthetic */ Bundle val$extras;

            AnonymousClass_6(CustomActionCallback customActionCallback, String str, Bundle bundle) {
                this.val$callback = customActionCallback;
                this.val$action = str;
                this.val$extras = bundle;
            }

            public void run() {
                this.val$callback.onError(this.val$action, this.val$extras, null);
            }
        }

        class AnonymousClass_7 implements Runnable {
            final /* synthetic */ String val$action;
            final /* synthetic */ CustomActionCallback val$callback;
            final /* synthetic */ Bundle val$extras;

            AnonymousClass_7(CustomActionCallback customActionCallback, String str, Bundle bundle) {
                this.val$callback = customActionCallback;
                this.val$action = str;
                this.val$extras = bundle;
            }

            public void run() {
                this.val$callback.onError(this.val$action, this.val$extras, null);
            }
        }

        public MediaBrowserImplApi21(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
            this.mHandler = new CallbackHandler(this);
            this.mSubscriptions = new ArrayMap();
            this.mContext = context;
            if (rootHints == null) {
                rootHints = new Bundle();
            }
            rootHints.putInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 1);
            this.mRootHints = new Bundle(rootHints);
            callback.setInternalConnectionCallback(this);
            this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(context, serviceComponent, callback.mConnectionCallbackObj, this.mRootHints);
        }

        public void connect() {
            MediaBrowserCompatApi21.connect(this.mBrowserObj);
        }

        public void disconnect() {
            if (!(this.mServiceBinderWrapper == null || this.mCallbacksMessenger == null)) {
                try {
                    this.mServiceBinderWrapper.unregisterCallbackMessenger(this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error unregistering client messenger.");
                }
            }
            MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
        }

        public boolean isConnected() {
            return MediaBrowserCompatApi21.isConnected(this.mBrowserObj);
        }

        public ComponentName getServiceComponent() {
            return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj);
        }

        @NonNull
        public String getRoot() {
            return MediaBrowserCompatApi21.getRoot(this.mBrowserObj);
        }

        @Nullable
        public Bundle getExtras() {
            return MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
        }

        @NonNull
        public Token getSessionToken() {
            if (this.mMediaSessionToken == null) {
                this.mMediaSessionToken = Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
            }
            return this.mMediaSessionToken;
        }

        public void subscribe(@NonNull String parentId, Bundle options, @NonNull SubscriptionCallback callback) {
            Subscription sub = (Subscription) this.mSubscriptions.get(parentId);
            if (sub == null) {
                sub = new Subscription();
                this.mSubscriptions.put(parentId, sub);
            }
            callback.setSubscription(sub);
            Bundle copiedOptions = options == null ? null : new Bundle(options);
            sub.putCallback(this.mContext, copiedOptions, callback);
            if (this.mServiceBinderWrapper == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, parentId, callback.mSubscriptionCallbackObj);
                return;
            }
            try {
                this.mServiceBinderWrapper.addSubscription(parentId, callback.mToken, copiedOptions, this.mCallbacksMessenger);
            } catch (RemoteException e) {
                Log.i(TAG, "Remote error subscribing media item: " + parentId);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void unsubscribe(@android.support.annotation.NonNull java.lang.String r9_parentId, android.support.v4.media.MediaBrowserCompat.SubscriptionCallback r10_callback) {
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.unsubscribe(java.lang.String, android.support.v4.media.MediaBrowserCompat$SubscriptionCallback):void");
            /*
            this = this;
            r5 = r8.mSubscriptions;
            r4 = r5.get(r9);
            r4 = (android.support.v4.media.MediaBrowserCompat.Subscription) r4;
            if (r4 != 0) goto L_0x000b;
        L_0x000a:
            return;
        L_0x000b:
            r5 = r8.mServiceBinderWrapper;
            if (r5 != 0) goto L_0x004f;
        L_0x000f:
            if (r10 != 0) goto L_0x0024;
        L_0x0011:
            r5 = r8.mBrowserObj;
            android.support.v4.media.MediaBrowserCompatApi21.unsubscribe(r5, r9);
        L_0x0016:
            r5 = r4.isEmpty();
            if (r5 != 0) goto L_0x001e;
        L_0x001c:
            if (r10 != 0) goto L_0x000a;
        L_0x001e:
            r5 = r8.mSubscriptions;
            r5.remove(r9);
            goto L_0x000a;
        L_0x0024:
            r0 = r4.getCallbacks();
            r3 = r4.getOptionsList();
            r5 = r0.size();
            r2 = r5 + -1;
        L_0x0032:
            if (r2 < 0) goto L_0x0043;
        L_0x0034:
            r5 = r0.get(r2);
            if (r5 != r10) goto L_0x0040;
        L_0x003a:
            r0.remove(r2);
            r3.remove(r2);
        L_0x0040:
            r2 = r2 + -1;
            goto L_0x0032;
        L_0x0043:
            r5 = r0.size();
            if (r5 != 0) goto L_0x0016;
        L_0x0049:
            r5 = r8.mBrowserObj;
            android.support.v4.media.MediaBrowserCompatApi21.unsubscribe(r5, r9);
            goto L_0x0016;
        L_0x004f:
            if (r10 != 0) goto L_0x0074;
        L_0x0051:
            r5 = r8.mServiceBinderWrapper;	 Catch:{ RemoteException -> 0x005a }
            r6 = 0;
            r7 = r8.mCallbacksMessenger;	 Catch:{ RemoteException -> 0x005a }
            r5.removeSubscription(r9, r6, r7);	 Catch:{ RemoteException -> 0x005a }
            goto L_0x0016;
        L_0x005a:
            r1 = move-exception;
            r5 = "MediaBrowserCompat";
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "removeSubscription failed with RemoteException parentId=";
            r6 = r6.append(r7);
            r6 = r6.append(r9);
            r6 = r6.toString();
            android.util.Log.d(r5, r6);
            goto L_0x0016;
        L_0x0074:
            r0 = r4.getCallbacks();	 Catch:{ RemoteException -> 0x005a }
            r3 = r4.getOptionsList();	 Catch:{ RemoteException -> 0x005a }
            r5 = r0.size();	 Catch:{ RemoteException -> 0x005a }
            r2 = r5 + -1;
        L_0x0082:
            if (r2 < 0) goto L_0x0016;
        L_0x0084:
            r5 = r0.get(r2);	 Catch:{ RemoteException -> 0x005a }
            if (r5 != r10) goto L_0x009b;
        L_0x008a:
            r5 = r8.mServiceBinderWrapper;	 Catch:{ RemoteException -> 0x005a }
            r6 = r10.mToken;	 Catch:{ RemoteException -> 0x005a }
            r7 = r8.mCallbacksMessenger;	 Catch:{ RemoteException -> 0x005a }
            r5.removeSubscription(r9, r6, r7);	 Catch:{ RemoteException -> 0x005a }
            r0.remove(r2);	 Catch:{ RemoteException -> 0x005a }
            r3.remove(r2);	 Catch:{ RemoteException -> 0x005a }
        L_0x009b:
            r2 = r2 + -1;
            goto L_0x0082;
            */
        }

        public void getItem(@NonNull String mediaId, @NonNull ItemCallback cb) {
            if (TextUtils.isEmpty(mediaId)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (cb == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
                Log.i(TAG, "Not connected, unable to retrieve the MediaItem.");
                this.mHandler.post(new AnonymousClass_1(cb, mediaId));
            } else if (this.mServiceBinderWrapper == null) {
                this.mHandler.post(new AnonymousClass_2(cb, mediaId));
            } else {
                try {
                    this.mServiceBinderWrapper.getMediaItem(mediaId, new ItemReceiver(mediaId, cb, this.mHandler), this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error getting media item: " + mediaId);
                    this.mHandler.post(new AnonymousClass_3(cb, mediaId));
                }
            }
        }

        public void search(@NonNull String query, Bundle extras, @NonNull SearchCallback callback) {
            if (!isConnected()) {
                throw new IllegalStateException("search() called while not connected");
            } else if (this.mServiceBinderWrapper == null) {
                Log.i(TAG, "The connected service doesn't support search.");
                this.mHandler.post(new AnonymousClass_4(callback, query, extras));
            } else {
                try {
                    this.mServiceBinderWrapper.search(query, extras, new SearchResultReceiver(query, extras, callback, this.mHandler), this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error searching items with query: " + query, e);
                    this.mHandler.post(new AnonymousClass_5(callback, query, extras));
                }
            }
        }

        public void sendCustomAction(@NonNull String action, Bundle extras, @Nullable CustomActionCallback callback) {
            if (isConnected()) {
                if (this.mServiceBinderWrapper == null) {
                    Log.i(TAG, "The connected service doesn't support sendCustomAction.");
                    if (callback != null) {
                        this.mHandler.post(new AnonymousClass_6(callback, action, extras));
                    }
                }
                try {
                    this.mServiceBinderWrapper.sendCustomAction(action, extras, new CustomActionResultReceiver(action, extras, callback, this.mHandler), this.mCallbacksMessenger);
                    return;
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error sending a custom action: action=" + action + ", extras=" + extras, e);
                    if (callback != null) {
                        this.mHandler.post(new AnonymousClass_7(callback, action, extras));
                    }
                }
            }
            throw new IllegalStateException("Cannot send a custom action (" + action + ") with " + "extras " + extras + " because the browser is not connected to the " + "service.");
        }

        public void onConnected() {
            Bundle extras = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
            if (extras != null) {
                IBinder serviceBinder = BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER);
                if (serviceBinder != null) {
                    this.mServiceBinderWrapper = new ServiceBinderWrapper(serviceBinder, this.mRootHints);
                    this.mCallbacksMessenger = new Messenger(this.mHandler);
                    this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger);
                    try {
                        this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger);
                    } catch (RemoteException e) {
                        Log.i(TAG, "Remote error registering client messenger.");
                    }
                }
                IMediaSession sessionToken = Stub.asInterface(BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_SESSION_BINDER));
                if (sessionToken != null) {
                    this.mMediaSessionToken = Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj), sessionToken);
                }
            }
        }

        public void onConnectionSuspended() {
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mMediaSessionToken = null;
            this.mHandler.setCallbacksMessenger(null);
        }

        public void onConnectionFailed() {
        }

        public void onServiceConnected(Messenger callback, String root, Token session, Bundle extra) {
        }

        public void onConnectionFailed(Messenger callback) {
        }

        public void onLoadChildren(Messenger callback, String parentId, List list, Bundle options) {
            if (this.mCallbacksMessenger == callback) {
                Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
                if (subscription != null) {
                    SubscriptionCallback subscriptionCallback = subscription.getCallback(this.mContext, options);
                    if (subscriptionCallback == null) {
                        return;
                    }
                    if (options == null) {
                        if (list == null) {
                            subscriptionCallback.onError(parentId);
                        } else {
                            subscriptionCallback.onChildrenLoaded(parentId, list);
                        }
                    } else if (list == null) {
                        subscriptionCallback.onError(parentId, options);
                    } else {
                        subscriptionCallback.onChildrenLoaded(parentId, list, options);
                    }
                } else if (DEBUG) {
                    Log.d(TAG, "onLoadChildren for id that isn't subscribed id=" + parentId);
                }
            }
        }
    }

    static class MediaBrowserImplBase implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        static final int CONNECT_STATE_CONNECTED = 3;
        static final int CONNECT_STATE_CONNECTING = 2;
        static final int CONNECT_STATE_DISCONNECTED = 1;
        static final int CONNECT_STATE_DISCONNECTING = 0;
        static final int CONNECT_STATE_SUSPENDED = 4;
        final ConnectionCallback mCallback;
        Messenger mCallbacksMessenger;
        final Context mContext;
        private Bundle mExtras;
        final CallbackHandler mHandler;
        private Token mMediaSessionToken;
        final Bundle mRootHints;
        private String mRootId;
        ServiceBinderWrapper mServiceBinderWrapper;
        final ComponentName mServiceComponent;
        MediaServiceConnection mServiceConnection;
        int mState;
        private final ArrayMap<String, Subscription> mSubscriptions;

        class AnonymousClass_3 implements Runnable {
            final /* synthetic */ ItemCallback val$cb;
            final /* synthetic */ String val$mediaId;

            AnonymousClass_3(ItemCallback itemCallback, String str) {
                this.val$cb = itemCallback;
                this.val$mediaId = str;
            }

            public void run() {
                this.val$cb.onError(this.val$mediaId);
            }
        }

        class AnonymousClass_4 implements Runnable {
            final /* synthetic */ ItemCallback val$cb;
            final /* synthetic */ String val$mediaId;

            AnonymousClass_4(ItemCallback itemCallback, String str) {
                this.val$cb = itemCallback;
                this.val$mediaId = str;
            }

            public void run() {
                this.val$cb.onError(this.val$mediaId);
            }
        }

        class AnonymousClass_5 implements Runnable {
            final /* synthetic */ SearchCallback val$callback;
            final /* synthetic */ Bundle val$extras;
            final /* synthetic */ String val$query;

            AnonymousClass_5(SearchCallback searchCallback, String str, Bundle bundle) {
                this.val$callback = searchCallback;
                this.val$query = str;
                this.val$extras = bundle;
            }

            public void run() {
                this.val$callback.onError(this.val$query, this.val$extras);
            }
        }

        class AnonymousClass_6 implements Runnable {
            final /* synthetic */ String val$action;
            final /* synthetic */ CustomActionCallback val$callback;
            final /* synthetic */ Bundle val$extras;

            AnonymousClass_6(CustomActionCallback customActionCallback, String str, Bundle bundle) {
                this.val$callback = customActionCallback;
                this.val$action = str;
                this.val$extras = bundle;
            }

            public void run() {
                this.val$callback.onError(this.val$action, this.val$extras, null);
            }
        }

        private class MediaServiceConnection implements ServiceConnection {

            class AnonymousClass_1 implements Runnable {
                final /* synthetic */ IBinder val$binder;
                final /* synthetic */ ComponentName val$name;

                AnonymousClass_1(ComponentName componentName, IBinder iBinder) {
                    this.val$name = componentName;
                    this.val$binder = iBinder;
                }

                public void run() {
                    if (DEBUG) {
                        Log.d(TAG, "MediaServiceConnection.onServiceConnected name=" + this.val$name + " binder=" + this.val$binder);
                        MediaBrowserImplBase.this.dump();
                    }
                    if (MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                        MediaBrowserImplBase.this.mServiceBinderWrapper = new ServiceBinderWrapper(this.val$binder, MediaBrowserImplBase.this.mRootHints);
                        MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger(MediaBrowserImplBase.this.mHandler);
                        MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger);
                        MediaBrowserImplBase.this.mState = 2;
                        try {
                            if (DEBUG) {
                                Log.d(TAG, "ServiceCallbacks.onConnect...");
                                MediaBrowserImplBase.this.dump();
                            }
                            MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserImplBase.this.mContext, MediaBrowserImplBase.this.mCallbacksMessenger);
                        } catch (RemoteException e) {
                            Log.w(TAG, "RemoteException during connect for " + MediaBrowserImplBase.this.mServiceComponent);
                            if (DEBUG) {
                                Log.d(TAG, "ServiceCallbacks.onConnect...");
                                MediaBrowserImplBase.this.dump();
                            }
                        }
                    }
                }
            }

            class AnonymousClass_2 implements Runnable {
                final /* synthetic */ ComponentName val$name;

                AnonymousClass_2(ComponentName componentName) {
                    this.val$name = componentName;
                }

                public void run() {
                    if (DEBUG) {
                        Log.d(TAG, "MediaServiceConnection.onServiceDisconnected name=" + this.val$name + " this=" + this + " mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection);
                        MediaBrowserImplBase.this.dump();
                    }
                    if (MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                        MediaBrowserImplBase.this.mServiceBinderWrapper = null;
                        MediaBrowserImplBase.this.mCallbacksMessenger = null;
                        MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
                        MediaBrowserImplBase.this.mState = 4;
                        MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
                    }
                }
            }

            MediaServiceConnection() {
            }

            public void onServiceConnected(ComponentName name, IBinder binder) {
                postOrRun(new AnonymousClass_1(name, binder));
            }

            public void onServiceDisconnected(ComponentName name) {
                postOrRun(new AnonymousClass_2(name));
            }

            private void postOrRun(Runnable r) {
                if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
                    r.run();
                } else {
                    MediaBrowserImplBase.this.mHandler.post(r);
                }
            }

            boolean isCurrent(String funcName) {
                if (MediaBrowserImplBase.this.mServiceConnection == this && MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
                    return true;
                }
                if (!(MediaBrowserImplBase.this.mState == 0 || MediaBrowserImplBase.this.mState == 1)) {
                    Log.i(TAG, funcName + " for " + MediaBrowserImplBase.this.mServiceComponent + " with mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection + " this=" + this);
                }
                return DEBUG;
            }
        }

        public MediaBrowserImplBase(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
            this.mHandler = new CallbackHandler(this);
            this.mSubscriptions = new ArrayMap();
            this.mState = 1;
            if (context == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if (serviceComponent == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if (callback == null) {
                throw new IllegalArgumentException("connection callback must not be null");
            } else {
                this.mContext = context;
                this.mServiceComponent = serviceComponent;
                this.mCallback = callback;
                this.mRootHints = rootHints == null ? null : new Bundle(rootHints);
            }
        }

        public void connect() {
            if (this.mState == 0 || this.mState == 1) {
                this.mState = 2;
                this.mHandler.post(new Runnable() {
                    public void run() {
                        if (MediaBrowserImplBase.this.mState != 0) {
                            MediaBrowserImplBase.this.mState = 2;
                            if (DEBUG && MediaBrowserImplBase.this.mServiceConnection != null) {
                                throw new RuntimeException("mServiceConnection should be null. Instead it is " + MediaBrowserImplBase.this.mServiceConnection);
                            } else if (MediaBrowserImplBase.this.mServiceBinderWrapper != null) {
                                throw new RuntimeException("mServiceBinderWrapper should be null. Instead it is " + MediaBrowserImplBase.this.mServiceBinderWrapper);
                            } else if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                                throw new RuntimeException("mCallbacksMessenger should be null. Instead it is " + MediaBrowserImplBase.this.mCallbacksMessenger);
                            } else {
                                Intent intent = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
                                intent.setComponent(MediaBrowserImplBase.this.mServiceComponent);
                                MediaBrowserImplBase.this.mServiceConnection = new MediaServiceConnection();
                                boolean bound = DEBUG;
                                try {
                                    bound = MediaBrowserImplBase.this.mContext.bindService(intent, MediaBrowserImplBase.this.mServiceConnection, CONNECT_STATE_DISCONNECTED);
                                } catch (Exception e) {
                                    Log.e(TAG, "Failed binding to service " + MediaBrowserImplBase.this.mServiceComponent);
                                }
                                if (!bound) {
                                    MediaBrowserImplBase.this.forceCloseConnection();
                                    MediaBrowserImplBase.this.mCallback.onConnectionFailed();
                                }
                                if (DEBUG) {
                                    Log.d(TAG, "connect...");
                                    MediaBrowserImplBase.this.dump();
                                }
                            }
                        }
                    }
                });
                return;
            }
            throw new IllegalStateException("connect() called while neigther disconnecting nor disconnected (state=" + getStateLabel(this.mState) + ")");
        }

        public void disconnect() {
            this.mState = 0;
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        try {
                            MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserImplBase.this.mCallbacksMessenger);
                        } catch (RemoteException e) {
                            Log.w(TAG, "RemoteException during connect for " + MediaBrowserImplBase.this.mServiceComponent);
                        }
                    }
                    int state = MediaBrowserImplBase.this.mState;
                    MediaBrowserImplBase.this.forceCloseConnection();
                    if (state != 0) {
                        MediaBrowserImplBase.this.mState = state;
                    }
                    if (DEBUG) {
                        Log.d(TAG, "disconnect...");
                        MediaBrowserImplBase.this.dump();
                    }
                }
            });
        }

        void forceCloseConnection() {
            if (this.mServiceConnection != null) {
                this.mContext.unbindService(this.mServiceConnection);
            }
            this.mState = 1;
            this.mServiceConnection = null;
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
            this.mRootId = null;
            this.mMediaSessionToken = null;
        }

        public boolean isConnected() {
            return this.mState == 3 ? true : DEBUG;
        }

        @NonNull
        public ComponentName getServiceComponent() {
            if (isConnected()) {
                return this.mServiceComponent;
            }
            throw new IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + ")");
        }

        @NonNull
        public String getRoot() {
            if (isConnected()) {
                return this.mRootId;
            }
            throw new IllegalStateException("getRoot() called while not connected(state=" + getStateLabel(this.mState) + ")");
        }

        @Nullable
        public Bundle getExtras() {
            if (isConnected()) {
                return this.mExtras;
            }
            throw new IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + ")");
        }

        @NonNull
        public Token getSessionToken() {
            if (isConnected()) {
                return this.mMediaSessionToken;
            }
            throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.mState + ")");
        }

        public void subscribe(@NonNull String parentId, Bundle options, @NonNull SubscriptionCallback callback) {
            Subscription sub = (Subscription) this.mSubscriptions.get(parentId);
            if (sub == null) {
                sub = new Subscription();
                this.mSubscriptions.put(parentId, sub);
            }
            Bundle copiedOptions = options == null ? null : new Bundle(options);
            sub.putCallback(this.mContext, copiedOptions, callback);
            if (isConnected()) {
                try {
                    this.mServiceBinderWrapper.addSubscription(parentId, callback.mToken, copiedOptions, this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.d(TAG, "addSubscription failed with RemoteException parentId=" + parentId);
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void unsubscribe(@android.support.annotation.NonNull java.lang.String r9_parentId, android.support.v4.media.MediaBrowserCompat.SubscriptionCallback r10_callback) {
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.unsubscribe(java.lang.String, android.support.v4.media.MediaBrowserCompat$SubscriptionCallback):void");
            /*
            this = this;
            r5 = r8.mSubscriptions;
            r4 = r5.get(r9);
            r4 = (android.support.v4.media.MediaBrowserCompat.Subscription) r4;
            if (r4 != 0) goto L_0x000b;
        L_0x000a:
            return;
        L_0x000b:
            if (r10 != 0) goto L_0x0029;
        L_0x000d:
            r5 = r8.isConnected();	 Catch:{ RemoteException -> 0x0059 }
            if (r5 == 0) goto L_0x001b;
        L_0x0013:
            r5 = r8.mServiceBinderWrapper;	 Catch:{ RemoteException -> 0x0059 }
            r6 = 0;
            r7 = r8.mCallbacksMessenger;	 Catch:{ RemoteException -> 0x0059 }
            r5.removeSubscription(r9, r6, r7);	 Catch:{ RemoteException -> 0x0059 }
        L_0x001b:
            r5 = r4.isEmpty();
            if (r5 != 0) goto L_0x0023;
        L_0x0021:
            if (r10 != 0) goto L_0x000a;
        L_0x0023:
            r5 = r8.mSubscriptions;
            r5.remove(r9);
            goto L_0x000a;
        L_0x0029:
            r0 = r4.getCallbacks();	 Catch:{ RemoteException -> 0x0059 }
            r3 = r4.getOptionsList();	 Catch:{ RemoteException -> 0x0059 }
            r5 = r0.size();	 Catch:{ RemoteException -> 0x0059 }
            r2 = r5 + -1;
        L_0x0037:
            if (r2 < 0) goto L_0x001b;
        L_0x0039:
            r5 = r0.get(r2);	 Catch:{ RemoteException -> 0x0059 }
            if (r5 != r10) goto L_0x0056;
        L_0x003f:
            r5 = r8.isConnected();	 Catch:{ RemoteException -> 0x0059 }
            if (r5 == 0) goto L_0x0050;
        L_0x0045:
            r5 = r8.mServiceBinderWrapper;	 Catch:{ RemoteException -> 0x0059 }
            r6 = r10.mToken;	 Catch:{ RemoteException -> 0x0059 }
            r7 = r8.mCallbacksMessenger;	 Catch:{ RemoteException -> 0x0059 }
            r5.removeSubscription(r9, r6, r7);	 Catch:{ RemoteException -> 0x0059 }
        L_0x0050:
            r0.remove(r2);	 Catch:{ RemoteException -> 0x0059 }
            r3.remove(r2);	 Catch:{ RemoteException -> 0x0059 }
        L_0x0056:
            r2 = r2 + -1;
            goto L_0x0037;
        L_0x0059:
            r1 = move-exception;
            r5 = "MediaBrowserCompat";
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "removeSubscription failed with RemoteException parentId=";
            r6 = r6.append(r7);
            r6 = r6.append(r9);
            r6 = r6.toString();
            android.util.Log.d(r5, r6);
            goto L_0x001b;
            */
        }

        public void getItem(@NonNull String mediaId, @NonNull ItemCallback cb) {
            if (TextUtils.isEmpty(mediaId)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (cb == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (isConnected()) {
                try {
                    this.mServiceBinderWrapper.getMediaItem(mediaId, new ItemReceiver(mediaId, cb, this.mHandler), this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error getting media item: " + mediaId);
                    this.mHandler.post(new AnonymousClass_4(cb, mediaId));
                }
            } else {
                Log.i(TAG, "Not connected, unable to retrieve the MediaItem.");
                this.mHandler.post(new AnonymousClass_3(cb, mediaId));
            }
        }

        public void search(@NonNull String query, Bundle extras, @NonNull SearchCallback callback) {
            if (isConnected()) {
                try {
                    this.mServiceBinderWrapper.search(query, extras, new SearchResultReceiver(query, extras, callback, this.mHandler), this.mCallbacksMessenger);
                    return;
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error searching items with query: " + query, e);
                    this.mHandler.post(new AnonymousClass_5(callback, query, extras));
                }
            }
            throw new IllegalStateException("search() called while not connected (state=" + getStateLabel(this.mState) + ")");
        }

        public void sendCustomAction(@NonNull String action, Bundle extras, @Nullable CustomActionCallback callback) {
            if (isConnected()) {
                try {
                    this.mServiceBinderWrapper.sendCustomAction(action, extras, new CustomActionResultReceiver(action, extras, callback, this.mHandler), this.mCallbacksMessenger);
                    return;
                } catch (RemoteException e) {
                    Log.i(TAG, "Remote error sending a custom action: action=" + action + ", extras=" + extras, e);
                    if (callback != null) {
                        this.mHandler.post(new AnonymousClass_6(callback, action, extras));
                    }
                }
            }
            throw new IllegalStateException("Cannot send a custom action (" + action + ") with " + "extras " + extras + " because the browser is not connected to the " + "service.");
        }

        public void onServiceConnected(Messenger callback, String root, Token session, Bundle extra) {
            if (!isCurrent(callback, "onConnect")) {
                return;
            }
            if (this.mState != 2) {
                Log.w(TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring");
                return;
            }
            this.mRootId = root;
            this.mMediaSessionToken = session;
            this.mExtras = extra;
            this.mState = 3;
            if (DEBUG) {
                Log.d(TAG, "ServiceCallbacks.onConnect...");
                dump();
            }
            this.mCallback.onConnected();
            try {
                for (Entry<String, Subscription> subscriptionEntry : this.mSubscriptions.entrySet()) {
                    String id = (String) subscriptionEntry.getKey();
                    Subscription sub = (Subscription) subscriptionEntry.getValue();
                    List<SubscriptionCallback> callbackList = sub.getCallbacks();
                    List<Bundle> optionsList = sub.getOptionsList();
                    for (int i = CONNECT_STATE_DISCONNECTING; i < callbackList.size(); i++) {
                        this.mServiceBinderWrapper.addSubscription(id, ((SubscriptionCallback) callbackList.get(i)).mToken, (Bundle) optionsList.get(i), this.mCallbacksMessenger);
                    }
                }
            } catch (RemoteException e) {
                Log.d(TAG, "addSubscription failed with RemoteException.");
            }
        }

        public void onConnectionFailed(Messenger callback) {
            Log.e(TAG, "onConnectFailed for " + this.mServiceComponent);
            if (!isCurrent(callback, "onConnectFailed")) {
                return;
            }
            if (this.mState != 2) {
                Log.w(TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring");
                return;
            }
            forceCloseConnection();
            this.mCallback.onConnectionFailed();
        }

        public void onLoadChildren(Messenger callback, String parentId, List list, Bundle options) {
            if (isCurrent(callback, "onLoadChildren")) {
                if (DEBUG) {
                    Log.d(TAG, "onLoadChildren for " + this.mServiceComponent + " id=" + parentId);
                }
                Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
                if (subscription != null) {
                    SubscriptionCallback subscriptionCallback = subscription.getCallback(this.mContext, options);
                    if (subscriptionCallback == null) {
                        return;
                    }
                    if (options == null) {
                        if (list == null) {
                            subscriptionCallback.onError(parentId);
                        } else {
                            subscriptionCallback.onChildrenLoaded(parentId, list);
                        }
                    } else if (list == null) {
                        subscriptionCallback.onError(parentId, options);
                    } else {
                        subscriptionCallback.onChildrenLoaded(parentId, list, options);
                    }
                } else if (DEBUG) {
                    Log.d(TAG, "onLoadChildren for id that isn't subscribed id=" + parentId);
                }
            }
        }

        private static String getStateLabel(int state) {
            switch (state) {
                case CONNECT_STATE_DISCONNECTING:
                    return "CONNECT_STATE_DISCONNECTING";
                case CONNECT_STATE_DISCONNECTED:
                    return "CONNECT_STATE_DISCONNECTED";
                case CONNECT_STATE_CONNECTING:
                    return "CONNECT_STATE_CONNECTING";
                case CONNECT_STATE_CONNECTED:
                    return "CONNECT_STATE_CONNECTED";
                case CONNECT_STATE_SUSPENDED:
                    return "CONNECT_STATE_SUSPENDED";
                default:
                    return "UNKNOWN/" + state;
            }
        }

        private boolean isCurrent(Messenger callback, String funcName) {
            if (this.mCallbacksMessenger == callback && this.mState != 0 && this.mState != 1) {
                return true;
            }
            if (!(this.mState == 0 || this.mState == 1)) {
                Log.i(TAG, funcName + " for " + this.mServiceComponent + " with mCallbacksMessenger=" + this.mCallbacksMessenger + " this=" + this);
            }
            return DEBUG;
        }

        void dump() {
            Log.d(TAG, "MediaBrowserCompat...");
            Log.d(TAG, "  mServiceComponent=" + this.mServiceComponent);
            Log.d(TAG, "  mCallback=" + this.mCallback);
            Log.d(TAG, "  mRootHints=" + this.mRootHints);
            Log.d(TAG, "  mState=" + getStateLabel(this.mState));
            Log.d(TAG, "  mServiceConnection=" + this.mServiceConnection);
            Log.d(TAG, "  mServiceBinderWrapper=" + this.mServiceBinderWrapper);
            Log.d(TAG, "  mCallbacksMessenger=" + this.mCallbacksMessenger);
            Log.d(TAG, "  mRootId=" + this.mRootId);
            Log.d(TAG, "  mMediaSessionToken=" + this.mMediaSessionToken);
        }
    }

    private static class SearchResultReceiver extends ResultReceiver {
        private final SearchCallback mCallback;
        private final Bundle mExtras;
        private final String mQuery;

        SearchResultReceiver(String query, Bundle extras, SearchCallback callback, Handler handler) {
            super(handler);
            this.mQuery = query;
            this.mExtras = extras;
            this.mCallback = callback;
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData != null) {
                resultData.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (resultCode == 0 && resultData != null && resultData.containsKey(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)) {
                Parcelable[] items = resultData.getParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS);
                List<MediaItem> results = null;
                if (items != null) {
                    results = new ArrayList();
                    for (Parcelable item : items) {
                        results.add((MediaItem) item);
                    }
                }
                this.mCallback.onSearchResult(this.mQuery, this.mExtras, results);
                return;
            }
            this.mCallback.onError(this.mQuery, this.mExtras);
        }
    }

    @RequiresApi(23)
    static class MediaBrowserImplApi23 extends MediaBrowserImplApi21 {
        public MediaBrowserImplApi23(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
            super(context, serviceComponent, callback, rootHints);
        }

        public void getItem(@NonNull String mediaId, @NonNull ItemCallback cb) {
            if (this.mServiceBinderWrapper == null) {
                MediaBrowserCompatApi23.getItem(this.mBrowserObj, mediaId, cb.mItemCallbackObj);
            } else {
                super.getItem(mediaId, cb);
            }
        }
    }

    @RequiresApi(26)
    static class MediaBrowserImplApi24 extends MediaBrowserImplApi23 {
        public MediaBrowserImplApi24(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
            super(context, serviceComponent, callback, rootHints);
        }

        public void subscribe(@NonNull String parentId, @NonNull Bundle options, @NonNull SubscriptionCallback callback) {
            if (options == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, parentId, callback.mSubscriptionCallbackObj);
            } else {
                MediaBrowserCompatApi24.subscribe(this.mBrowserObj, parentId, options, callback.mSubscriptionCallbackObj);
            }
        }

        public void unsubscribe(@NonNull String parentId, SubscriptionCallback callback) {
            if (callback == null) {
                MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, parentId);
            } else {
                MediaBrowserCompatApi24.unsubscribe(this.mBrowserObj, parentId, callback.mSubscriptionCallbackObj);
            }
        }
    }

    static {
        DEBUG = Log.isLoggable(TAG, RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
    }

    public MediaBrowserCompat(Context context, ComponentName serviceComponent, ConnectionCallback callback, Bundle rootHints) {
        if (VERSION.SDK_INT >= 26) {
            this.mImpl = new MediaBrowserImplApi24(context, serviceComponent, callback, rootHints);
        } else if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserImplApi23(context, serviceComponent, callback, rootHints);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserImplApi21(context, serviceComponent, callback, rootHints);
        } else {
            this.mImpl = new MediaBrowserImplBase(context, serviceComponent, callback, rootHints);
        }
    }

    public void connect() {
        this.mImpl.connect();
    }

    public void disconnect() {
        this.mImpl.disconnect();
    }

    public boolean isConnected() {
        return this.mImpl.isConnected();
    }

    @NonNull
    public ComponentName getServiceComponent() {
        return this.mImpl.getServiceComponent();
    }

    @NonNull
    public String getRoot() {
        return this.mImpl.getRoot();
    }

    @Nullable
    public Bundle getExtras() {
        return this.mImpl.getExtras();
    }

    @NonNull
    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public void subscribe(@NonNull String parentId, @NonNull SubscriptionCallback callback) {
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback is null");
        } else {
            this.mImpl.subscribe(parentId, null, callback);
        }
    }

    public void subscribe(@NonNull String parentId, @NonNull Bundle options, @NonNull SubscriptionCallback callback) {
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback is null");
        } else if (options == null) {
            throw new IllegalArgumentException("options are null");
        } else {
            this.mImpl.subscribe(parentId, options, callback);
        }
    }

    public void unsubscribe(@NonNull String parentId) {
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        }
        this.mImpl.unsubscribe(parentId, null);
    }

    public void unsubscribe(@NonNull String parentId, @NonNull SubscriptionCallback callback) {
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback is null");
        } else {
            this.mImpl.unsubscribe(parentId, callback);
        }
    }

    public void getItem(@NonNull String mediaId, @NonNull ItemCallback cb) {
        this.mImpl.getItem(mediaId, cb);
    }

    public void search(@NonNull String query, Bundle extras, @NonNull SearchCallback callback) {
        if (TextUtils.isEmpty(query)) {
            throw new IllegalArgumentException("query cannot be empty");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        } else {
            this.mImpl.search(query, extras, callback);
        }
    }

    public void sendCustomAction(@NonNull String action, Bundle extras, @Nullable CustomActionCallback callback) {
        if (TextUtils.isEmpty(action)) {
            throw new IllegalArgumentException("action cannot be empty");
        }
        this.mImpl.sendCustomAction(action, extras, callback);
    }
}
