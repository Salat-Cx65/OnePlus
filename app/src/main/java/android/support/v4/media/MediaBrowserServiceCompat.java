package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserServiceCompat.BrowserRoot;
import android.support.v4.media.MediaBrowserServiceCompat.Result;
import android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCompatProxy;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class MediaBrowserServiceCompat extends Service {
    static final boolean DEBUG;
    private static final float EPSILON = 1.0E-5f;
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String KEY_MEDIA_ITEM = "media_item";
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String KEY_SEARCH_RESULTS = "search_results";
    static final int RESULT_ERROR = -1;
    static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    static final int RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED = 4;
    static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    static final int RESULT_OK = 0;
    static final int RESULT_PROGRESS_UPDATE = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    static final String TAG = "MBServiceCompat";
    final ArrayMap<IBinder, ConnectionRecord> mConnections;
    ConnectionRecord mCurConnection;
    final ServiceHandler mHandler;
    private MediaBrowserServiceImpl mImpl;
    Token mSession;

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String rootId, @Nullable Bundle extras) {
            if (rootId == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = rootId;
            this.mExtras = extras;
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }

    private static class ConnectionRecord {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions;

        ConnectionRecord() {
            this.subscriptions = new HashMap();
        }
    }

    static interface MediaBrowserServiceImpl {
        Bundle getBrowserRootHints();

        void notifyChildrenChanged(String str, Bundle bundle);

        IBinder onBind(Intent intent);

        void onCreate();

        void setSessionToken(Token token);
    }

    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendProgressUpdateCalled;
        private boolean mSendResultCalled;

        Result(Object debug) {
            this.mDebug = debug;
        }

        public void sendResult(T result) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(result);
        }

        public void sendProgressUpdate(Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            checkExtraFields(extras);
            this.mSendProgressUpdateCalled = true;
            onProgressUpdateSent(extras);
        }

        public void sendError(Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendErrorCalled = true;
            onErrorSent(extras);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            } else if (this.mSendResultCalled) {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            } else if (this.mSendErrorCalled) {
                throw new IllegalStateException("detach() called when sendError() had already been called for: " + this.mDebug);
            } else {
                this.mDetachCalled = true;
            }
        }

        boolean isDone() {
            return (this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled) ? true : DEBUG;
        }

        void setFlags(int flags) {
            this.mFlags = flags;
        }

        int getFlags() {
            return this.mFlags;
        }

        void onResultSent(T t) {
        }

        void onProgressUpdateSent(Bundle extras) {
            throw new UnsupportedOperationException("It is not supported to send an interim update for " + this.mDebug);
        }

        void onErrorSent(Bundle extras) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.mDebug);
        }

        private void checkExtraFields(Bundle extras) {
            if (extras != null && extras.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float value = extras.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (value < -1.0E-5f || value > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0].");
                }
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    private static @interface ResultFlags {
    }

    private class ServiceBinderImpl {

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$pkg;
            final /* synthetic */ Bundle val$rootHints;
            final /* synthetic */ int val$uid;

            AnonymousClass_1(ServiceCallbacks serviceCallbacks, String str, Bundle bundle, int i) {
                this.val$callbacks = serviceCallbacks;
                this.val$pkg = str;
                this.val$rootHints = bundle;
                this.val$uid = i;
            }

            public void run() {
                IBinder b = this.val$callbacks.asBinder();
                MediaBrowserServiceCompat.this.mConnections.remove(b);
                ConnectionRecord connection = new ConnectionRecord();
                connection.pkg = this.val$pkg;
                connection.rootHints = this.val$rootHints;
                connection.callbacks = this.val$callbacks;
                connection.root = MediaBrowserServiceCompat.this.onGetRoot(this.val$pkg, this.val$uid, this.val$rootHints);
                if (connection.root == null) {
                    Log.i(TAG, "No root for client " + this.val$pkg + " from service " + getClass().getName());
                    try {
                        this.val$callbacks.onConnectFailed();
                        return;
                    } catch (RemoteException e) {
                        Log.w(TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + this.val$pkg);
                    }
                }
                try {
                    MediaBrowserServiceCompat.this.mConnections.put(b, connection);
                    if (MediaBrowserServiceCompat.this.mSession != null) {
                        this.val$callbacks.onConnect(connection.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connection.root.getExtras());
                    }
                } catch (RemoteException e2) {
                    Log.w(TAG, "Calling onConnect() failed. Dropping client. pkg=" + this.val$pkg);
                    MediaBrowserServiceCompat.this.mConnections.remove(b);
                }
            }
        }

        class AnonymousClass_2 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;

            AnonymousClass_2(ServiceCallbacks serviceCallbacks) {
                this.val$callbacks = serviceCallbacks;
            }

            public void run() {
                if (((ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.remove(this.val$callbacks.asBinder())) == null) {
                }
            }
        }

        class AnonymousClass_3 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$id;
            final /* synthetic */ Bundle val$options;
            final /* synthetic */ IBinder val$token;

            AnonymousClass_3(ServiceCallbacks serviceCallbacks, String str, IBinder iBinder, Bundle bundle) {
                this.val$callbacks = serviceCallbacks;
                this.val$id = str;
                this.val$token = iBinder;
                this.val$options = bundle;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(TAG, "addSubscription for callback that isn't registered id=" + this.val$id);
                } else {
                    MediaBrowserServiceCompat.this.addSubscription(this.val$id, connection, this.val$token, this.val$options);
                }
            }
        }

        class AnonymousClass_4 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$id;
            final /* synthetic */ IBinder val$token;

            AnonymousClass_4(ServiceCallbacks serviceCallbacks, String str, IBinder iBinder) {
                this.val$callbacks = serviceCallbacks;
                this.val$id = str;
                this.val$token = iBinder;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(TAG, "removeSubscription for callback that isn't registered id=" + this.val$id);
                } else if (!MediaBrowserServiceCompat.this.removeSubscription(this.val$id, connection, this.val$token)) {
                    Log.w(TAG, "removeSubscription called for " + this.val$id + " which is not subscribed");
                }
            }
        }

        class AnonymousClass_5 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ String val$mediaId;
            final /* synthetic */ ResultReceiver val$receiver;

            AnonymousClass_5(ServiceCallbacks serviceCallbacks, String str, ResultReceiver resultReceiver) {
                this.val$callbacks = serviceCallbacks;
                this.val$mediaId = str;
                this.val$receiver = resultReceiver;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(TAG, "getMediaItem for callback that isn't registered id=" + this.val$mediaId);
                } else {
                    MediaBrowserServiceCompat.this.performLoadItem(this.val$mediaId, connection, this.val$receiver);
                }
            }
        }

        class AnonymousClass_6 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ Bundle val$rootHints;

            AnonymousClass_6(ServiceCallbacks serviceCallbacks, Bundle bundle) {
                this.val$callbacks = serviceCallbacks;
                this.val$rootHints = bundle;
            }

            public void run() {
                IBinder b = this.val$callbacks.asBinder();
                MediaBrowserServiceCompat.this.mConnections.remove(b);
                ConnectionRecord connection = new ConnectionRecord();
                connection.callbacks = this.val$callbacks;
                connection.rootHints = this.val$rootHints;
                MediaBrowserServiceCompat.this.mConnections.put(b, connection);
            }
        }

        class AnonymousClass_7 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;

            AnonymousClass_7(ServiceCallbacks serviceCallbacks) {
                this.val$callbacks = serviceCallbacks;
            }

            public void run() {
                MediaBrowserServiceCompat.this.mConnections.remove(this.val$callbacks.asBinder());
            }
        }

        class AnonymousClass_8 implements Runnable {
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ Bundle val$extras;
            final /* synthetic */ String val$query;
            final /* synthetic */ ResultReceiver val$receiver;

            AnonymousClass_8(ServiceCallbacks serviceCallbacks, String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.val$callbacks = serviceCallbacks;
                this.val$query = str;
                this.val$extras = bundle;
                this.val$receiver = resultReceiver;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(TAG, "search for callback that isn't registered query=" + this.val$query);
                } else {
                    MediaBrowserServiceCompat.this.performSearch(this.val$query, this.val$extras, connection, this.val$receiver);
                }
            }
        }

        class AnonymousClass_9 implements Runnable {
            final /* synthetic */ String val$action;
            final /* synthetic */ ServiceCallbacks val$callbacks;
            final /* synthetic */ Bundle val$extras;
            final /* synthetic */ ResultReceiver val$receiver;

            AnonymousClass_9(ServiceCallbacks serviceCallbacks, String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.val$callbacks = serviceCallbacks;
                this.val$action = str;
                this.val$extras = bundle;
                this.val$receiver = resultReceiver;
            }

            public void run() {
                ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(this.val$callbacks.asBinder());
                if (connection == null) {
                    Log.w(TAG, "sendCustomAction for callback that isn't registered action=" + this.val$action + ", extras=" + this.val$extras);
                } else {
                    MediaBrowserServiceCompat.this.performCustomAction(this.val$action, this.val$extras, connection, this.val$receiver);
                }
            }
        }

        ServiceBinderImpl() {
        }

        public void connect(String pkg, int uid, Bundle rootHints, ServiceCallbacks callbacks) {
            if (MediaBrowserServiceCompat.this.isValidPackage(pkg, uid)) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_1(callbacks, pkg, rootHints, uid));
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + uid + " package=" + pkg);
        }

        public void disconnect(ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_2(callbacks));
        }

        public void addSubscription(String id, IBinder token, Bundle options, ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_3(callbacks, id, token, options));
        }

        public void removeSubscription(String id, IBinder token, ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_4(callbacks, id, token));
        }

        public void getMediaItem(String mediaId, ResultReceiver receiver, ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(mediaId) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_5(callbacks, mediaId, receiver));
            }
        }

        public void registerCallbacks(ServiceCallbacks callbacks, Bundle rootHints) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_6(callbacks, rootHints));
        }

        public void unregisterCallbacks(ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_7(callbacks));
        }

        public void search(String query, Bundle extras, ResultReceiver receiver, ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(query) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_8(callbacks, query, extras, receiver));
            }
        }

        public void sendCustomAction(String action, Bundle extras, ResultReceiver receiver, ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(action) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_9(callbacks, action, extras, receiver));
            }
        }
    }

    private static interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String str, Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String str, List<MediaItem> list, Bundle bundle) throws RemoteException;
    }

    private final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl;

        ServiceHandler() {
            this.mServiceBinderImpl = new ServiceBinderImpl();
        }

        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case RESULT_PROGRESS_UPDATE:
                    this.mServiceBinderImpl.connect(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS), new ServiceCallbacksCompat(msg.replyTo));
                case RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED:
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(msg.replyTo));
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    this.mServiceBinderImpl.addSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), data.getBundle(MediaBrowserProtocol.DATA_OPTIONS), new ServiceCallbacksCompat(msg.replyTo));
                case RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED:
                    this.mServiceBinderImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), new ServiceCallbacksCompat(msg.replyTo));
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    this.mServiceBinderImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                case ConnectionResult.RESOLUTION_REQUIRED:
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(msg.replyTo), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS));
                case DetectedActivity.WALKING:
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(msg.replyTo));
                case DetectedActivity.RUNNING:
                    this.mServiceBinderImpl.search(data.getString(MediaBrowserProtocol.DATA_SEARCH_QUERY), data.getBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                case ConnectionResult.SERVICE_INVALID:
                    this.mServiceBinderImpl.sendCustomAction(data.getString(MediaBrowserProtocol.DATA_CUSTOM_ACTION), data.getBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                default:
                    Log.w(TAG, "Unhandled message: " + msg + "\n  Service version: " + RESULT_PROGRESS_UPDATE + "\n  Client version: " + msg.arg1);
            }
        }

        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            Bundle data = msg.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        public void postOrRun(Runnable r) {
            if (Thread.currentThread() == getLooper().getThread()) {
                r.run();
            } else {
                post(r);
            }
        }
    }

    class AnonymousClass_1 extends Result<List<MediaItem>> {
        final /* synthetic */ ConnectionRecord val$connection;
        final /* synthetic */ Bundle val$options;
        final /* synthetic */ String val$parentId;

        AnonymousClass_1(Object debug, ConnectionRecord connectionRecord, String str, Bundle bundle) {
            this.val$connection = connectionRecord;
            this.val$parentId = str;
            this.val$options = bundle;
            super(debug);
        }

        void onResultSent(List<MediaItem> list) {
            if (MediaBrowserServiceCompat.this.mConnections.get(this.val$connection.callbacks.asBinder()) == this.val$connection) {
                List<MediaItem> filteredList;
                if ((getFlags() & 1) != 0) {
                    filteredList = MediaBrowserServiceCompat.this.applyOptions(list, this.val$options);
                } else {
                    filteredList = list;
                }
                try {
                    this.val$connection.callbacks.onLoadChildren(this.val$parentId, filteredList, this.val$options);
                } catch (RemoteException e) {
                    Log.w(TAG, "Calling onLoadChildren() failed for id=" + this.val$parentId + " package=" + this.val$connection.pkg);
                }
            } else if (DEBUG) {
                Log.d(TAG, "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + this.val$connection.pkg + " id=" + this.val$parentId);
            }
        }
    }

    class AnonymousClass_2 extends Result<MediaItem> {
        final /* synthetic */ ResultReceiver val$receiver;

        AnonymousClass_2(Object debug, ResultReceiver resultReceiver) {
            this.val$receiver = resultReceiver;
            super(debug);
        }

        void onResultSent(MediaItem item) {
            if ((getFlags() & 2) != 0) {
                this.val$receiver.send(RESULT_ERROR, null);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_MEDIA_ITEM, item);
            this.val$receiver.send(RESULT_OK, bundle);
        }
    }

    class AnonymousClass_3 extends Result<List<MediaItem>> {
        final /* synthetic */ ResultReceiver val$receiver;

        AnonymousClass_3(Object debug, ResultReceiver resultReceiver) {
            this.val$receiver = resultReceiver;
            super(debug);
        }

        void onResultSent(List<MediaItem> items) {
            if ((getFlags() & 4) != 0 || items == null) {
                this.val$receiver.send(RESULT_ERROR, null);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(KEY_SEARCH_RESULTS, (Parcelable[]) items.toArray(new MediaItem[0]));
            this.val$receiver.send(RESULT_OK, bundle);
        }
    }

    class AnonymousClass_4 extends Result<Bundle> {
        final /* synthetic */ ResultReceiver val$receiver;

        AnonymousClass_4(Object debug, ResultReceiver resultReceiver) {
            this.val$receiver = resultReceiver;
            super(debug);
        }

        void onResultSent(Bundle result) {
            this.val$receiver.send(RESULT_OK, result);
        }

        void onProgressUpdateSent(Bundle data) {
            this.val$receiver.send(RESULT_PROGRESS_UPDATE, data);
        }

        void onErrorSent(Bundle data) {
            this.val$receiver.send(RESULT_ERROR, data);
        }
    }

    @RequiresApi(21)
    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl, ServiceCompatProxy {
        Messenger mMessenger;
        final List<Bundle> mRootExtrasList;
        Object mServiceObj;

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ Token val$token;

            AnonymousClass_1(Token token) {
                this.val$token = token;
            }

            public void run() {
                if (!MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
                    IMediaSession extraBinder = this.val$token.getExtraBinder();
                    if (extraBinder != null) {
                        for (Bundle rootExtras : MediaBrowserServiceImplApi21.this.mRootExtrasList) {
                            BundleCompat.putBinder(rootExtras, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder.asBinder());
                        }
                    }
                    MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
                }
                MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceImplApi21.this.mServiceObj, this.val$token.getToken());
            }
        }

        class AnonymousClass_2 implements Runnable {
            final /* synthetic */ Bundle val$options;
            final /* synthetic */ String val$parentId;

            AnonymousClass_2(String str, Bundle bundle) {
                this.val$parentId = str;
                this.val$options = bundle;
            }

            public void run() {
                for (IBinder binder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                    ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(binder);
                    List<Pair<IBinder, Bundle>> callbackList = (List) connection.subscriptions.get(this.val$parentId);
                    if (callbackList != null) {
                        for (Pair<IBinder, Bundle> callback : callbackList) {
                            if (MediaBrowserCompatUtils.hasDuplicatedItems(this.val$options, (Bundle) callback.second)) {
                                MediaBrowserServiceCompat.this.performLoadChildren(this.val$parentId, connection, (Bundle) callback.second);
                            }
                        }
                    }
                }
            }
        }

        class AnonymousClass_3 extends Result<List<MediaItem>> {
            final /* synthetic */ ResultWrapper val$resultWrapper;

            AnonymousClass_3(Object debug, ResultWrapper resultWrapper) {
                this.val$resultWrapper = resultWrapper;
                super(debug);
            }

            void onResultSent(List<MediaItem> list) {
                List<Parcel> parcelList = null;
                if (list != null) {
                    parcelList = new ArrayList();
                    for (MediaItem item : list) {
                        Parcel parcel = Parcel.obtain();
                        item.writeToParcel(parcel, RESULT_OK);
                        parcelList.add(parcel);
                    }
                }
                this.val$resultWrapper.sendResult(parcelList);
            }

            public void detach() {
                this.val$resultWrapper.detach();
            }
        }

        MediaBrowserServiceImplApi21() {
            this.mRootExtrasList = new ArrayList();
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi21.createService(MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        public IBinder onBind(Intent intent) {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent);
        }

        public void setSessionToken(Token token) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new AnonymousClass_1(token));
        }

        public void notifyChildrenChanged(String parentId, Bundle options) {
            if (this.mMessenger == null) {
                MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, parentId);
            } else {
                MediaBrowserServiceCompat.this.mHandler.post(new AnonymousClass_2(parentId, options));
            }
        }

        public Bundle getBrowserRootHints() {
            if (this.mMessenger == null) {
                return null;
            }
            if (MediaBrowserServiceCompat.this.mCurConnection != null) {
                return MediaBrowserServiceCompat.this.mCurConnection.rootHints != null ? new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints) : null;
            } else {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            }
        }

        public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
            Bundle rootExtras = null;
            if (!(rootHints == null || rootHints.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, RESULT_OK) == 0)) {
                rootHints.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION);
                this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
                rootExtras = new Bundle();
                rootExtras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, RESULT_PROGRESS_UPDATE);
                BundleCompat.putBinder(rootExtras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
                if (MediaBrowserServiceCompat.this.mSession != null) {
                    IBinder iBinder;
                    IMediaSession extraBinder = MediaBrowserServiceCompat.this.mSession.getExtraBinder();
                    String str = MediaBrowserProtocol.EXTRA_SESSION_BINDER;
                    if (extraBinder == null) {
                        iBinder = null;
                    } else {
                        iBinder = extraBinder.asBinder();
                    }
                    BundleCompat.putBinder(rootExtras, str, iBinder);
                } else {
                    this.mRootExtrasList.add(rootExtras);
                }
            }
            BrowserRoot root = MediaBrowserServiceCompat.this.onGetRoot(clientPackageName, clientUid, rootHints);
            if (root == null) {
                return null;
            }
            if (rootExtras == null) {
                rootExtras = root.getExtras();
            } else if (root.getExtras() != null) {
                rootExtras.putAll(root.getExtras());
            }
            return new BrowserRoot(root.getRootId(), rootExtras);
        }

        public void onLoadChildren(String parentId, ResultWrapper<List<Parcel>> resultWrapper) {
            MediaBrowserServiceCompat.this.onLoadChildren(parentId, new AnonymousClass_3(parentId, resultWrapper));
        }
    }

    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ Token val$token;

            AnonymousClass_1(Token token) {
                this.val$token = token;
            }

            public void run() {
                Iterator<ConnectionRecord> iter = MediaBrowserServiceCompat.this.mConnections.values().iterator();
                while (iter.hasNext()) {
                    ConnectionRecord connection = (ConnectionRecord) iter.next();
                    try {
                        connection.callbacks.onConnect(connection.root.getRootId(), this.val$token, connection.root.getExtras());
                    } catch (RemoteException e) {
                        Log.w(TAG, "Connection for " + connection.pkg + " is no longer valid.");
                        iter.remove();
                    }
                }
            }
        }

        class AnonymousClass_2 implements Runnable {
            final /* synthetic */ Bundle val$options;
            final /* synthetic */ String val$parentId;

            AnonymousClass_2(String str, Bundle bundle) {
                this.val$parentId = str;
                this.val$options = bundle;
            }

            public void run() {
                for (IBinder binder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                    ConnectionRecord connection = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(binder);
                    List<Pair<IBinder, Bundle>> callbackList = (List) connection.subscriptions.get(this.val$parentId);
                    if (callbackList != null) {
                        for (Pair<IBinder, Bundle> callback : callbackList) {
                            if (MediaBrowserCompatUtils.hasDuplicatedItems(this.val$options, (Bundle) callback.second)) {
                                MediaBrowserServiceCompat.this.performLoadChildren(this.val$parentId, connection, (Bundle) callback.second);
                            }
                        }
                    }
                }
            }
        }

        MediaBrowserServiceImplBase() {
        }

        public void onCreate() {
            this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
        }

        public IBinder onBind(Intent intent) {
            return SERVICE_INTERFACE.equals(intent.getAction()) ? this.mMessenger.getBinder() : null;
        }

        public void setSessionToken(Token token) {
            MediaBrowserServiceCompat.this.mHandler.post(new AnonymousClass_1(token));
        }

        public void notifyChildrenChanged(@NonNull String parentId, Bundle options) {
            MediaBrowserServiceCompat.this.mHandler.post(new AnonymousClass_2(parentId, options));
        }

        public Bundle getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection != null) {
                return MediaBrowserServiceCompat.this.mCurConnection.rootHints == null ? null : new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
            } else {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            }
        }
    }

    private static class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger callbacks) {
            this.mCallbacks = callbacks;
        }

        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        public void onConnect(String root, Token session, Bundle extras) throws RemoteException {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, RESULT_PROGRESS_UPDATE);
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, root);
            data.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, session);
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, extras);
            sendRequest(RESULT_PROGRESS_UPDATE, data);
        }

        public void onConnectFailed() throws RemoteException {
            sendRequest(RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED, null);
        }

        public void onLoadChildren(String mediaId, List<MediaItem> list, Bundle options) throws RemoteException {
            Bundle data = new Bundle();
            data.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            data.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            if (list != null) {
                data.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, list instanceof ArrayList ? (ArrayList) list : new ArrayList(list));
            }
            sendRequest(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, data);
        }

        private void sendRequest(int what, Bundle data) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = what;
            msg.arg1 = 1;
            msg.setData(data);
            this.mCallbacks.send(msg);
        }
    }

    @RequiresApi(23)
    class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {

        class AnonymousClass_1 extends Result<MediaItem> {
            final /* synthetic */ ResultWrapper val$resultWrapper;

            AnonymousClass_1(Object debug, ResultWrapper resultWrapper) {
                this.val$resultWrapper = resultWrapper;
                super(debug);
            }

            void onResultSent(MediaItem item) {
                if (item == null) {
                    this.val$resultWrapper.sendResult(null);
                    return;
                }
                Parcel parcelItem = Parcel.obtain();
                item.writeToParcel(parcelItem, RESULT_OK);
                this.val$resultWrapper.sendResult(parcelItem);
            }

            public void detach() {
                this.val$resultWrapper.detach();
            }
        }

        MediaBrowserServiceImplApi23() {
            super();
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi23.createService(MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        public void onLoadItem(String itemId, ResultWrapper<Parcel> resultWrapper) {
            MediaBrowserServiceCompat.this.onLoadItem(itemId, new AnonymousClass_1(itemId, resultWrapper));
        }
    }

    @RequiresApi(26)
    class MediaBrowserServiceImplApi24 extends MediaBrowserServiceImplApi23 implements MediaBrowserServiceCompatApi24.ServiceCompatProxy {

        class AnonymousClass_1 extends Result<List<MediaItem>> {
            final /* synthetic */ ResultWrapper val$resultWrapper;

            AnonymousClass_1(Object debug, ResultWrapper resultWrapper) {
                this.val$resultWrapper = resultWrapper;
                super(debug);
            }

            void onResultSent(List<MediaItem> list) {
                List<Parcel> parcelList = null;
                if (list != null) {
                    parcelList = new ArrayList();
                    for (MediaItem item : list) {
                        Parcel parcel = Parcel.obtain();
                        item.writeToParcel(parcel, RESULT_OK);
                        parcelList.add(parcel);
                    }
                }
                this.val$resultWrapper.sendResult(parcelList, getFlags());
            }

            public void detach() {
                this.val$resultWrapper.detach();
            }
        }

        MediaBrowserServiceImplApi24() {
            super();
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi24.createService(MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        public void notifyChildrenChanged(String parentId, Bundle options) {
            if (options == null) {
                MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, parentId);
            } else {
                MediaBrowserServiceCompatApi24.notifyChildrenChanged(this.mServiceObj, parentId, options);
            }
        }

        public void onLoadChildren(String parentId, ResultWrapper resultWrapper, Bundle options) {
            MediaBrowserServiceCompat.this.onLoadChildren(parentId, new AnonymousClass_1(parentId, resultWrapper), options);
        }

        public Bundle getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection != null) {
                return MediaBrowserServiceCompat.this.mCurConnection.rootHints == null ? null : new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
            } else {
                return MediaBrowserServiceCompatApi24.getBrowserRootHints(this.mServiceObj);
            }
        }
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result);

    public MediaBrowserServiceCompat() {
        this.mConnections = new ArrayMap();
        this.mHandler = new ServiceHandler();
    }

    static {
        DEBUG = Log.isLoggable(TAG, RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 26) {
            this.mImpl = new MediaBrowserServiceImplApi24();
        } else if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23();
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserServiceImplApi21();
        } else {
            this.mImpl = new MediaBrowserServiceImplBase();
        }
        this.mImpl.onCreate();
    }

    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaItem>> result, @NonNull Bundle options) {
        result.setFlags(RESULT_PROGRESS_UPDATE);
        onLoadChildren(parentId, result);
    }

    public void onLoadItem(String itemId, @NonNull Result<MediaItem> result) {
        result.setFlags(RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String query, Bundle extras, @NonNull Result<List<MediaItem>> result) {
        result.setFlags(RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED);
        result.sendResult(null);
    }

    public void onCustomAction(@NonNull String action, Bundle extras, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    public void setSessionToken(Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        } else {
            this.mSession = token;
            this.mImpl.setSessionToken(token);
        }
    }

    @Nullable
    public Token getSessionToken() {
        return this.mSession;
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints();
    }

    public void notifyChildrenChanged(@NonNull String parentId) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mImpl.notifyChildrenChanged(parentId, null);
    }

    public void notifyChildrenChanged(@NonNull String parentId, @NonNull Bundle options) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (options == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        } else {
            this.mImpl.notifyChildrenChanged(parentId, options);
        }
    }

    boolean isValidPackage(String pkg, int uid) {
        if (pkg == null) {
            return DEBUG;
        }
        String[] packages = getPackageManager().getPackagesForUid(uid);
        int N = packages.length;
        for (int i = RESULT_OK; i < N; i++) {
            if (packages[i].equals(pkg)) {
                return true;
            }
        }
        return DEBUG;
    }

    void addSubscription(String id, ConnectionRecord connection, IBinder token, Bundle options) {
        List<Pair<IBinder, Bundle>> callbackList = (List) connection.subscriptions.get(id);
        if (callbackList == null) {
            callbackList = new ArrayList();
        }
        for (Pair<IBinder, Bundle> callback : callbackList) {
            if (token != callback.first || MediaBrowserCompatUtils.areSameOptions(options, (Bundle) callback.second)) {
                return;
            }
        }
        callbackList.add(new Pair(token, options));
        connection.subscriptions.put(id, callbackList);
        performLoadChildren(id, connection, options);
    }

    boolean removeSubscription(String id, ConnectionRecord connection, IBinder token) {
        if (token == null) {
            return connection.subscriptions.remove(id) != null ? true : DEBUG;
        } else {
            boolean removed = DEBUG;
            List<Pair<IBinder, Bundle>> callbackList = (List) connection.subscriptions.get(id);
            if (callbackList != null) {
                Iterator<Pair<IBinder, Bundle>> iter = callbackList.iterator();
                while (iter.hasNext()) {
                    if (token == ((Pair) iter.next()).first) {
                        removed = true;
                        iter.remove();
                    }
                }
                if (callbackList.size() == 0) {
                    connection.subscriptions.remove(id);
                }
            }
            return removed;
        }
    }

    void performLoadChildren(String parentId, ConnectionRecord connection, Bundle options) {
        Result<List<MediaItem>> result = new AnonymousClass_1(parentId, connection, parentId, options);
        this.mCurConnection = connection;
        if (options == null) {
            onLoadChildren(parentId, result);
        } else {
            onLoadChildren(parentId, result, options);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connection.pkg + " id=" + parentId);
        }
    }

    List<MediaItem> applyOptions(List<MediaItem> list, Bundle options) {
        if (list == null) {
            return null;
        }
        int page = options.getInt(MediaBrowserCompat.EXTRA_PAGE, RESULT_ERROR);
        int pageSize = options.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, RESULT_ERROR);
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

    void performLoadItem(String itemId, ConnectionRecord connection, ResultReceiver receiver) {
        Result<MediaItem> result = new AnonymousClass_2(itemId, receiver);
        this.mCurConnection = connection;
        onLoadItem(itemId, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + itemId);
        }
    }

    void performSearch(String query, Bundle extras, ConnectionRecord connection, ResultReceiver receiver) {
        Result<List<MediaItem>> result = new AnonymousClass_3(query, receiver);
        this.mCurConnection = connection;
        onSearch(query, extras, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + query);
        }
    }

    void performCustomAction(String action, Bundle extras, ConnectionRecord connection, ResultReceiver receiver) {
        Result<Bundle> result = new AnonymousClass_4(action, receiver);
        this.mCurConnection = connection;
        onCustomAction(action, extras, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + action + " extras=" + extras);
        }
    }
}
