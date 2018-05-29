package android.support.v4.provider;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.GuardedBy;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.v4.provider.SelfDestructiveThread.ReplyCallback;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SelfDestructiveThread {
    private static final int MSG_DESTRUCTION = 0;
    private static final int MSG_INVOKE_RUNNABLE = 1;
    private Callback mCallback;
    private final int mDestructAfterMillisec;
    @GuardedBy("mLock")
    private int mGeneration;
    @GuardedBy("mLock")
    private Handler mHandler;
    private final Object mLock;
    private final int mPriority;
    @GuardedBy("mLock")
    private HandlerThread mThread;
    private final String mThreadName;

    class AnonymousClass_2 implements Runnable {
        final /* synthetic */ Callable val$callable;
        final /* synthetic */ Handler val$callingHandler;
        final /* synthetic */ ReplyCallback val$reply;

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ Object val$result;

            AnonymousClass_1(Object obj) {
                this.val$result = obj;
            }

            public void run() {
                AnonymousClass_2.this.val$reply.onReply(this.val$result);
            }
        }

        AnonymousClass_2(Callable callable, Handler handler, ReplyCallback replyCallback) {
            this.val$callable = callable;
            this.val$callingHandler = handler;
            this.val$reply = replyCallback;
        }

        public void run() {
            T t;
            try {
                t = this.val$callable.call();
            } catch (Exception e) {
                t = null;
            }
            this.val$callingHandler.post(new AnonymousClass_1(t));
        }
    }

    class AnonymousClass_3 implements Runnable {
        final /* synthetic */ Callable val$callable;
        final /* synthetic */ Condition val$cond;
        final /* synthetic */ AtomicReference val$holder;
        final /* synthetic */ ReentrantLock val$lock;
        final /* synthetic */ AtomicBoolean val$running;

        AnonymousClass_3(AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
            this.val$holder = atomicReference;
            this.val$callable = callable;
            this.val$lock = reentrantLock;
            this.val$running = atomicBoolean;
            this.val$cond = condition;
        }

        public void run() {
            try {
                this.val$holder.set(this.val$callable.call());
            } catch (Exception e) {
            }
            this.val$lock.lock();
            this.val$running.set(false);
            this.val$cond.signal();
            this.val$lock.unlock();
        }
    }

    public static interface ReplyCallback<T> {
        void onReply(T t);
    }

    public SelfDestructiveThread(String threadName, int priority, int destructAfterMillisec) {
        this.mLock = new Object();
        this.mCallback = new Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_DESTRUCTION:
                        SelfDestructiveThread.this.onDestruction();
                        break;
                    case MSG_INVOKE_RUNNABLE:
                        SelfDestructiveThread.this.onInvokeRunnable((Runnable) msg.obj);
                        break;
                }
                return true;
            }
        };
        this.mThreadName = threadName;
        this.mPriority = priority;
        this.mDestructAfterMillisec = destructAfterMillisec;
        this.mGeneration = 0;
    }

    @VisibleForTesting
    public boolean isRunning() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mThread != null;
        }
        return z;
    }

    @VisibleForTesting
    public int getGeneration() {
        int i;
        synchronized (this.mLock) {
            i = this.mGeneration;
        }
        return i;
    }

    private void post(Runnable runnable) {
        synchronized (this.mLock) {
            if (this.mThread == null) {
                this.mThread = new HandlerThread(this.mThreadName, this.mPriority);
                this.mThread.start();
                this.mHandler = new Handler(this.mThread.getLooper(), this.mCallback);
                this.mGeneration++;
            }
            this.mHandler.removeMessages(MSG_DESTRUCTION);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(MSG_INVOKE_RUNNABLE, runnable));
        }
    }

    public <T> void postAndReply(Callable<T> callable, ReplyCallback<T> reply) {
        post(new AnonymousClass_2(callable, new Handler(), reply));
    }

    public <T> T postAndWait(Callable<T> callable, int timeoutMillis) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        AtomicReference<T> holder = new AtomicReference();
        AtomicBoolean running = new AtomicBoolean(true);
        post(new AnonymousClass_3(holder, callable, lock, running, cond));
        lock.lock();
        T t;
        if (running.get()) {
            long remaining = TimeUnit.MILLISECONDS.toNanos((long) timeoutMillis);
            do {
                try {
                    remaining = cond.awaitNanos(remaining);
                } catch (InterruptedException e) {
                }
                if (!running.get()) {
                    t = holder.get();
                    lock.unlock();
                    return t;
                }
            } while (remaining > 0);
            throw new InterruptedException("timeout");
        } else {
            t = holder.get();
            lock.unlock();
            return t;
        }
    }

    private void onInvokeRunnable(Runnable runnable) {
        runnable.run();
        synchronized (this.mLock) {
            this.mHandler.removeMessages(MSG_DESTRUCTION);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(MSG_DESTRUCTION), (long) this.mDestructAfterMillisec);
        }
    }

    private void onDestruction() {
        synchronized (this.mLock) {
            if (this.mHandler.hasMessages(MSG_INVOKE_RUNNABLE)) {
                return;
            }
            this.mThread.quit();
            this.mThread = null;
            this.mHandler = null;
        }
    }
}
