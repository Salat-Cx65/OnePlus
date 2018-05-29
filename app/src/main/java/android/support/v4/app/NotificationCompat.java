package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.compat.R;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.Extender;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.app.NotificationCompatBase.UnreadConversation;
import android.support.v4.app.NotificationCompatBase.UnreadConversation.Factory;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.v4.text.BidiFormatter;
import android.support.v4.view.ViewCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import com.android.volley.DefaultRetryPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.oneplus.weather.util.StringUtils;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    static final NotificationCompatImpl IMPL;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface BadgeIconType {
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public ArrayList<Action> mActions;
        int mBadgeIcon;
        RemoteViews mBigContentView;
        String mCategory;
        String mChannelId;
        int mColor;
        boolean mColorized;
        boolean mColorizedSet;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentInfo;
        PendingIntent mContentIntent;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentText;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentTitle;
        RemoteViews mContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        private int mGroupAlertBehavior;
        String mGroupKey;
        boolean mGroupSummary;
        RemoteViews mHeadsUpContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap mLargeIcon;
        boolean mLocalOnly;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Notification mNotification;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public int mNumber;
        public ArrayList<String> mPeople;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence[] mRemoteInputHistory;
        String mShortcutId;
        boolean mShowWhen;
        String mSortKey;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Style mStyle;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mSubText;
        RemoteViews mTickerView;
        long mTimeout;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public boolean mUseChronometer;
        int mVisibility;

        public Builder(@NonNull Context context, @NonNull String channelId) {
            this.mShowWhen = true;
            this.mActions = new ArrayList();
            this.mLocalOnly = false;
            this.mColor = 0;
            this.mVisibility = 0;
            this.mBadgeIcon = 0;
            this.mGroupAlertBehavior = 0;
            this.mNotification = new Notification();
            this.mContext = context;
            this.mChannelId = channelId;
            this.mNotification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList();
        }

        @Deprecated
        public Builder(Context context) {
            this(context, null);
        }

        public android.support.v4.app.NotificationCompat.Builder setWhen(long when) {
            this.mNotification.when = when;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setShowWhen(boolean show) {
            this.mShowWhen = show;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setUsesChronometer(boolean b) {
            this.mUseChronometer = b;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setSmallIcon(int icon) {
            this.mNotification.icon = icon;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setSmallIcon(int icon, int level) {
            this.mNotification.icon = icon;
            this.mNotification.iconLevel = level;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setContentTitle(CharSequence title) {
            this.mContentTitle = limitCharSequenceLength(title);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setContentText(CharSequence text) {
            this.mContentText = limitCharSequenceLength(text);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setSubText(CharSequence text) {
            this.mSubText = limitCharSequenceLength(text);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setRemoteInputHistory(CharSequence[] text) {
            this.mRemoteInputHistory = text;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setNumber(int number) {
            this.mNumber = number;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setContentInfo(CharSequence info) {
            this.mContentInfo = limitCharSequenceLength(info);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setProgress(int max, int progress, boolean indeterminate) {
            this.mProgressMax = max;
            this.mProgress = progress;
            this.mProgressIndeterminate = indeterminate;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setContent(RemoteViews views) {
            this.mNotification.contentView = views;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setContentIntent(PendingIntent intent) {
            this.mContentIntent = intent;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setDeleteIntent(PendingIntent intent) {
            this.mNotification.deleteIntent = intent;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setFullScreenIntent(PendingIntent intent, boolean highPriority) {
            this.mFullScreenIntent = intent;
            setFlag(FLAG_HIGH_PRIORITY, highPriority);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setTicker(CharSequence tickerText) {
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setTicker(CharSequence tickerText, RemoteViews views) {
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            this.mTickerView = views;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setLargeIcon(Bitmap icon) {
            this.mLargeIcon = icon;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setSound(Uri sound) {
            this.mNotification.sound = sound;
            this.mNotification.audioStreamType = -1;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setSound(Uri sound, int streamType) {
            this.mNotification.sound = sound;
            this.mNotification.audioStreamType = streamType;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setVibrate(long[] pattern) {
            this.mNotification.vibrate = pattern;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setLights(@ColorInt int argb, int onMs, int offMs) {
            boolean showLights;
            int i = VISIBILITY_PUBLIC;
            this.mNotification.ledARGB = argb;
            this.mNotification.ledOnMS = onMs;
            this.mNotification.ledOffMS = offMs;
            if (this.mNotification.ledOnMS == 0 || this.mNotification.ledOffMS == 0) {
                showLights = false;
            } else {
                showLights = true;
            }
            Notification notification = this.mNotification;
            int i2 = this.mNotification.flags & -2;
            if (!showLights) {
                i = 0;
            }
            notification.flags = i | i2;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setOngoing(boolean ongoing) {
            setFlag(PRIORITY_MAX, ongoing);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setColorized(boolean colorize) {
            this.mColorized = colorize;
            this.mColorizedSet = true;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
            setFlag(FLAG_ONLY_ALERT_ONCE, onlyAlertOnce);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setAutoCancel(boolean autoCancel) {
            setFlag(FLAG_AUTO_CANCEL, autoCancel);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setLocalOnly(boolean b) {
            this.mLocalOnly = b;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setCategory(String category) {
            this.mCategory = category;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setDefaults(int defaults) {
            this.mNotification.defaults = defaults;
            if ((defaults & 4) != 0) {
                Notification notification = this.mNotification;
                notification.flags |= 1;
            }
            return this;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                Notification notification = this.mNotification;
                notification.flags |= mask;
                return;
            }
            notification = this.mNotification;
            notification.flags &= mask ^ -1;
        }

        public android.support.v4.app.NotificationCompat.Builder setPriority(int pri) {
            this.mPriority = pri;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder addPerson(String uri) {
            this.mPeople.add(uri);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setGroup(String groupKey) {
            this.mGroupKey = groupKey;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setGroupSummary(boolean isGroupSummary) {
            this.mGroupSummary = isGroupSummary;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setSortKey(String sortKey) {
            this.mSortKey = sortKey;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder addExtras(Bundle extras) {
            if (extras != null) {
                if (this.mExtras == null) {
                    this.mExtras = new Bundle(extras);
                } else {
                    this.mExtras.putAll(extras);
                }
            }
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Bundle getExtras() {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        public android.support.v4.app.NotificationCompat.Builder addAction(int icon, CharSequence title, PendingIntent intent) {
            this.mActions.add(new Action(icon, title, intent));
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                }
            }
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setColor(@ColorInt int argb) {
            this.mColor = argb;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setVisibility(int visibility) {
            this.mVisibility = visibility;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setPublicVersion(Notification n) {
            this.mPublicVersion = n;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setCustomContentView(RemoteViews contentView) {
            this.mContentView = contentView;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setCustomBigContentView(RemoteViews contentView) {
            this.mBigContentView = contentView;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setCustomHeadsUpContentView(RemoteViews contentView) {
            this.mHeadsUpContentView = contentView;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setChannelId(@NonNull String channelId) {
            this.mChannelId = channelId;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setTimeoutAfter(long durationMs) {
            this.mTimeout = durationMs;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setShortcutId(String shortcutId) {
            this.mShortcutId = shortcutId;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setBadgeIconType(int icon) {
            this.mBadgeIcon = icon;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder setGroupAlertBehavior(int groupAlertBehavior) {
            this.mGroupAlertBehavior = groupAlertBehavior;
            return this;
        }

        public android.support.v4.app.NotificationCompat.Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            return IMPL.build(this, getExtender());
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        protected BuilderExtender getExtender() {
            return new BuilderExtender();
        }

        protected static CharSequence limitCharSequenceLength(CharSequence cs) {
            return (cs != null && cs.length() > 5120) ? cs.subSequence(VISIBILITY_PRIVATE, MAX_CHARSEQUENCE_LENGTH) : cs;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getContentView() {
            return this.mContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getBigContentView() {
            return this.mBigContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getHeadsUpContentView() {
            return this.mHeadsUpContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public long getWhenIfShowing() {
            return this.mShowWhen ? this.mNotification.when : 0;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getPriority() {
            return this.mPriority;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getColor() {
            return this.mColor;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    protected static class BuilderExtender {
        protected BuilderExtender() {
        }

        public Notification build(Builder b, NotificationBuilderWithBuilderAccessor builder) {
            RemoteViews styleContentView = b.mStyle != null ? b.mStyle.makeContentView(builder) : null;
            Notification n = builder.build();
            if (styleContentView != null) {
                n.contentView = styleContentView;
            } else if (b.mContentView != null) {
                n.contentView = b.mContentView;
            }
            if (VERSION.SDK_INT >= 16 && b.mStyle != null) {
                RemoteViews styleBigContentView = b.mStyle.makeBigContentView(builder);
                if (styleBigContentView != null) {
                    n.bigContentView = styleBigContentView;
                }
            }
            if (VERSION.SDK_INT >= 21 && b.mStyle != null) {
                RemoteViews styleHeadsUpContentView = b.mStyle.makeHeadsUpContentView(builder);
                if (styleHeadsUpContentView != null) {
                    n.headsUpContentView = styleHeadsUpContentView;
                }
            }
            return n;
        }
    }

    public static interface Extender {
        Builder extend(Builder builder);
    }

    static interface NotificationCompatImpl {
        Notification build(Builder builder, BuilderExtender builderExtender);

        Action getAction(Notification notification, int i);

        Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList);

        Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation);

        ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr);

        UnreadConversation getUnreadConversationFromBundle(Bundle bundle, Factory factory, RemoteInput.Factory factory2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface NotificationVisibility {
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        @RestrictTo({Scope.LIBRARY_GROUP})
        protected Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet;

        public Style() {
            this.mSummaryTextSet = false;
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }

        public Notification build() {
            return this.mBuilder != null ? this.mBuilder.build() : null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void addCompatExtras(Bundle extras) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        protected void restoreFromCompatExtras(Bundle extras) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews applyStandardTemplate(boolean showSmallIcon, int resId, boolean fitIn1U) {
            Resources res = this.mBuilder.mContext.getResources();
            RemoteViews contentView = new RemoteViews(this.mBuilder.mContext.getPackageName(), resId);
            boolean z = false;
            boolean showLine2 = false;
            boolean minPriority = this.mBuilder.getPriority() < -1;
            if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 21) {
                if (minPriority) {
                    contentView.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg_low);
                    contentView.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_low_bg);
                } else {
                    contentView.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg);
                    contentView.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_bg);
                }
            }
            if (this.mBuilder.mLargeIcon != null) {
                if (VERSION.SDK_INT >= 16) {
                    contentView.setViewVisibility(R.id.icon, VISIBILITY_PRIVATE);
                    contentView.setImageViewBitmap(R.id.icon, this.mBuilder.mLargeIcon);
                } else {
                    contentView.setViewVisibility(R.id.icon, FLAG_ONLY_ALERT_ONCE);
                }
                if (showSmallIcon && this.mBuilder.mNotification.icon != 0) {
                    int backgroundSize = res.getDimensionPixelSize(R.dimen.notification_right_icon_size);
                    int iconSize = backgroundSize - (res.getDimensionPixelSize(R.dimen.notification_small_icon_background_padding) * 2);
                    if (VERSION.SDK_INT >= 21) {
                        contentView.setImageViewBitmap(R.id.right_icon, createIconWithBackground(this.mBuilder.mNotification.icon, backgroundSize, iconSize, this.mBuilder.getColor()));
                    } else {
                        contentView.setImageViewBitmap(R.id.right_icon, createColoredBitmap(this.mBuilder.mNotification.icon, VISIBILITY_SECRET));
                    }
                    contentView.setViewVisibility(R.id.right_icon, VISIBILITY_PRIVATE);
                }
            } else if (showSmallIcon && this.mBuilder.mNotification.icon != 0) {
                contentView.setViewVisibility(R.id.icon, VISIBILITY_PRIVATE);
                if (VERSION.SDK_INT >= 21) {
                    contentView.setImageViewBitmap(R.id.icon, createIconWithBackground(this.mBuilder.mNotification.icon, res.getDimensionPixelSize(R.dimen.notification_large_icon_width) - res.getDimensionPixelSize(R.dimen.notification_big_circle_margin), res.getDimensionPixelSize(R.dimen.notification_small_icon_size_as_large), this.mBuilder.getColor()));
                } else {
                    contentView.setImageViewBitmap(R.id.icon, createColoredBitmap(this.mBuilder.mNotification.icon, VISIBILITY_SECRET));
                }
            }
            if (this.mBuilder.mContentTitle != null) {
                contentView.setTextViewText(R.id.title, this.mBuilder.mContentTitle);
            }
            if (this.mBuilder.mContentText != null) {
                contentView.setTextViewText(R.id.text, this.mBuilder.mContentText);
                z = true;
            }
            boolean hasRightSide = VERSION.SDK_INT < 21 && this.mBuilder.mLargeIcon != null;
            if (this.mBuilder.mContentInfo != null) {
                contentView.setTextViewText(R.id.info, this.mBuilder.mContentInfo);
                contentView.setViewVisibility(R.id.info, VISIBILITY_PRIVATE);
                z = true;
                hasRightSide = true;
            } else if (this.mBuilder.mNumber > 0) {
                if (this.mBuilder.mNumber > res.getInteger(R.integer.status_bar_notification_info_maxnum)) {
                    contentView.setTextViewText(R.id.info, res.getString(R.string.status_bar_notification_info_overflow));
                } else {
                    contentView.setTextViewText(R.id.info, NumberFormat.getIntegerInstance().format((long) this.mBuilder.mNumber));
                }
                contentView.setViewVisibility(R.id.info, VISIBILITY_PRIVATE);
                z = true;
                hasRightSide = true;
            } else {
                contentView.setViewVisibility(R.id.info, FLAG_ONLY_ALERT_ONCE);
            }
            if (this.mBuilder.mSubText != null && VERSION.SDK_INT >= 16) {
                contentView.setTextViewText(R.id.text, this.mBuilder.mSubText);
                if (this.mBuilder.mContentText != null) {
                    contentView.setTextViewText(R.id.text2, this.mBuilder.mContentText);
                    contentView.setViewVisibility(R.id.text2, VISIBILITY_PRIVATE);
                    showLine2 = true;
                } else {
                    contentView.setViewVisibility(R.id.text2, FLAG_ONLY_ALERT_ONCE);
                }
            }
            if (showLine2 && VERSION.SDK_INT >= 16) {
                if (fitIn1U) {
                    contentView.setTextViewTextSize(R.id.text, VISIBILITY_PRIVATE, (float) res.getDimensionPixelSize(R.dimen.notification_subtext_size));
                }
                contentView.setViewPadding(R.id.line1, VISIBILITY_PRIVATE, VISIBILITY_PRIVATE, VISIBILITY_PRIVATE, VISIBILITY_PRIVATE);
            }
            if (this.mBuilder.getWhenIfShowing() != 0) {
                if (!this.mBuilder.mUseChronometer || VERSION.SDK_INT < 16) {
                    contentView.setViewVisibility(R.id.time, VISIBILITY_PRIVATE);
                    contentView.setLong(R.id.time, "setTime", this.mBuilder.getWhenIfShowing());
                } else {
                    contentView.setViewVisibility(R.id.chronometer, VISIBILITY_PRIVATE);
                    contentView.setLong(R.id.chronometer, "setBase", this.mBuilder.getWhenIfShowing() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                    contentView.setBoolean(R.id.chronometer, "setStarted", true);
                }
                hasRightSide = true;
            }
            contentView.setViewVisibility(R.id.right_side, hasRightSide ? VISIBILITY_PRIVATE : FLAG_ONLY_ALERT_ONCE);
            contentView.setViewVisibility(R.id.line3, z ? VISIBILITY_PRIVATE : FLAG_ONLY_ALERT_ONCE);
            return contentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap createColoredBitmap(int iconId, int color) {
            return createColoredBitmap(iconId, color, VISIBILITY_PRIVATE);
        }

        private Bitmap createColoredBitmap(int iconId, int color, int size) {
            int width;
            int height;
            Drawable drawable = this.mBuilder.mContext.getResources().getDrawable(iconId);
            if (size == 0) {
                width = drawable.getIntrinsicWidth();
            } else {
                width = size;
            }
            if (size == 0) {
                height = drawable.getIntrinsicHeight();
            } else {
                height = size;
            }
            Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            drawable.setBounds(VISIBILITY_PRIVATE, VISIBILITY_PRIVATE, width, height);
            if (color != 0) {
                drawable.mutate().setColorFilter(new PorterDuffColorFilter(color, Mode.SRC_IN));
            }
            drawable.draw(new Canvas(resultBitmap));
            return resultBitmap;
        }

        private Bitmap createIconWithBackground(int iconId, int size, int iconSize, int color) {
            int i = R.drawable.notification_icon_background;
            if (color == 0) {
                color = VISIBILITY_PRIVATE;
            }
            Bitmap coloredBitmap = createColoredBitmap(i, color, size);
            Canvas canvas = new Canvas(coloredBitmap);
            Drawable icon = this.mBuilder.mContext.getResources().getDrawable(iconId).mutate();
            icon.setFilterBitmap(true);
            int inset = (size - iconSize) / 2;
            icon.setBounds(inset, inset, iconSize + inset, iconSize + inset);
            icon.setColorFilter(new PorterDuffColorFilter(-1, Mode.SRC_ATOP));
            icon.draw(canvas);
            return coloredBitmap;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void buildIntoRemoteViews(RemoteViews outerView, RemoteViews innerView) {
            hideNormalContent(outerView);
            outerView.removeAllViews(R.id.notification_main_column);
            outerView.addView(R.id.notification_main_column, innerView.clone());
            outerView.setViewVisibility(R.id.notification_main_column, VISIBILITY_PRIVATE);
            if (VERSION.SDK_INT >= 21) {
                outerView.setViewPadding(R.id.notification_main_column_container, VISIBILITY_PRIVATE, calculateTopPadding(), 0, 0);
            }
        }

        private void hideNormalContent(RemoteViews outerView) {
            outerView.setViewVisibility(R.id.title, FLAG_ONLY_ALERT_ONCE);
            outerView.setViewVisibility(R.id.text2, FLAG_ONLY_ALERT_ONCE);
            outerView.setViewVisibility(R.id.text, FLAG_ONLY_ALERT_ONCE);
        }

        private int calculateTopPadding() {
            Resources resources = this.mBuilder.mContext.getResources();
            float largeFactor = (constrain(resources.getConfiguration().fontScale, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - largeFactor) * ((float) resources.getDimensionPixelSize(R.dimen.notification_top_pad))) + (((float) resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text)) * largeFactor));
        }

        private static float constrain(float amount, float low, float high) {
            if (amount < low) {
                return low;
            }
            return amount > high ? high : amount;
        }
    }

    public static class Action extends android.support.v4.app.NotificationCompatBase.Action {
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final android.support.v4.app.NotificationCompatBase.Action.Factory FACTORY;
        public PendingIntent actionIntent;
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final RemoteInput[] mDataOnlyRemoteInputs;
        final Bundle mExtras;
        private final RemoteInput[] mRemoteInputs;
        public CharSequence title;

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private final Bundle mExtras;
            private final int mIcon;
            private final PendingIntent mIntent;
            private ArrayList<RemoteInput> mRemoteInputs;
            private final CharSequence mTitle;

            public Builder(int icon, CharSequence title, PendingIntent intent) {
                this(icon, title, intent, new Bundle(), null, true);
            }

            public Builder(android.support.v4.app.NotificationCompat.Action action) {
                this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
            }

            private Builder(int icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, boolean allowGeneratedReplies) {
                ArrayList arrayList;
                this.mAllowGeneratedReplies = true;
                this.mIcon = icon;
                this.mTitle = android.support.v4.app.NotificationCompat.Builder.limitCharSequenceLength(title);
                this.mIntent = intent;
                this.mExtras = extras;
                if (remoteInputs == null) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList(Arrays.asList(remoteInputs));
                }
                this.mRemoteInputs = arrayList;
                this.mAllowGeneratedReplies = allowGeneratedReplies;
            }

            public android.support.v4.app.NotificationCompat.Action.Builder addExtras(Bundle extras) {
                if (extras != null) {
                    this.mExtras.putAll(extras);
                }
                return this;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public android.support.v4.app.NotificationCompat.Action.Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            public android.support.v4.app.NotificationCompat.Action.Builder setAllowGeneratedReplies(boolean allowGeneratedReplies) {
                this.mAllowGeneratedReplies = allowGeneratedReplies;
                return this;
            }

            public android.support.v4.app.NotificationCompat.Action.Builder extend(android.support.v4.app.NotificationCompat.Action.Extender extender) {
                extender.extend(this);
                return this;
            }

            public android.support.v4.app.NotificationCompat.Action build() {
                RemoteInput[] dataOnlyInputsArr;
                RemoteInput[] textInputsArr;
                List<RemoteInput> dataOnlyInputs = new ArrayList();
                List<RemoteInput> textInputs = new ArrayList();
                if (this.mRemoteInputs != null) {
                    Iterator it = this.mRemoteInputs.iterator();
                    while (it.hasNext()) {
                        RemoteInput input = (RemoteInput) it.next();
                        if (input.isDataOnly()) {
                            dataOnlyInputs.add(input);
                        } else {
                            textInputs.add(input);
                        }
                    }
                }
                if (dataOnlyInputs.isEmpty()) {
                    dataOnlyInputsArr = null;
                } else {
                    dataOnlyInputsArr = (RemoteInput[]) dataOnlyInputs.toArray(new RemoteInput[dataOnlyInputs.size()]);
                }
                if (textInputs.isEmpty()) {
                    textInputsArr = null;
                } else {
                    textInputsArr = (RemoteInput[]) textInputs.toArray(new RemoteInput[textInputs.size()]);
                }
                return new android.support.v4.app.NotificationCompat.Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, textInputsArr, dataOnlyInputsArr, this.mAllowGeneratedReplies);
            }
        }

        public static interface Extender {
            android.support.v4.app.NotificationCompat.Action.Builder extend(android.support.v4.app.NotificationCompat.Action.Builder builder);
        }

        public static final class WearableExtender implements android.support.v4.app.NotificationCompat.Action.Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
                this.mFlags = 1;
            }

            public WearableExtender(android.support.v4.app.NotificationCompat.Action action) {
                this.mFlags = 1;
                Bundle wearableBundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (wearableBundle != null) {
                    this.mFlags = wearableBundle.getInt(KEY_FLAGS, FLAG_AVAILABLE_OFFLINE);
                    this.mInProgressLabel = wearableBundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = wearableBundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = wearableBundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            public android.support.v4.app.NotificationCompat.Action.Builder extend(android.support.v4.app.NotificationCompat.Action.Builder builder) {
                Bundle wearableBundle = new Bundle();
                if (this.mFlags != 1) {
                    wearableBundle.putInt(KEY_FLAGS, this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    wearableBundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    wearableBundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    wearableBundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
                return builder;
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender clone() {
                android.support.v4.app.NotificationCompat.Action.WearableExtender that = new android.support.v4.app.NotificationCompat.Action.WearableExtender();
                that.mFlags = this.mFlags;
                that.mInProgressLabel = this.mInProgressLabel;
                that.mConfirmLabel = this.mConfirmLabel;
                that.mCancelLabel = this.mCancelLabel;
                return that;
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender setAvailableOffline(boolean availableOffline) {
                setFlag(FLAG_AVAILABLE_OFFLINE, availableOffline);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int mask, boolean value) {
                if (value) {
                    this.mFlags |= mask;
                } else {
                    this.mFlags &= mask ^ -1;
                }
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender setInProgressLabel(CharSequence label) {
                this.mInProgressLabel = label;
                return this;
            }

            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender setConfirmLabel(CharSequence label) {
                this.mConfirmLabel = label;
                return this;
            }

            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender setCancelLabel(CharSequence label) {
                this.mCancelLabel = label;
                return this;
            }

            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender setHintLaunchesActivity(boolean hintLaunchesActivity) {
                setFlag(FLAG_HINT_LAUNCHES_ACTIVITY, hintLaunchesActivity);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public android.support.v4.app.NotificationCompat.Action.WearableExtender setHintDisplayActionInline(boolean hintDisplayInline) {
                setFlag(FLAG_HINT_DISPLAY_INLINE, hintDisplayInline);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }

        public Action(int icon, CharSequence title, PendingIntent intent) {
            this(icon, title, intent, new Bundle(), null, null, true);
        }

        Action(int icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, RemoteInput[] dataOnlyRemoteInputs, boolean allowGeneratedReplies) {
            this.icon = icon;
            this.title = android.support.v4.app.NotificationCompat.Builder.limitCharSequenceLength(title);
            this.actionIntent = intent;
            if (extras == null) {
                extras = new Bundle();
            }
            this.mExtras = extras;
            this.mRemoteInputs = remoteInputs;
            this.mDataOnlyRemoteInputs = dataOnlyRemoteInputs;
            this.mAllowGeneratedReplies = allowGeneratedReplies;
        }

        public int getIcon() {
            return this.icon;
        }

        public CharSequence getTitle() {
            return this.title;
        }

        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }

        static {
            FACTORY = new android.support.v4.app.NotificationCompatBase.Action.Factory() {
                public android.support.v4.app.NotificationCompatBase.Action build(int icon, CharSequence title, PendingIntent actionIntent, Bundle extras, RemoteInput[] remoteInputs, RemoteInput[] dataOnlyRemoteInputs, boolean allowGeneratedReplies) {
                    return new android.support.v4.app.NotificationCompat.Action(icon, title, actionIntent, extras, (RemoteInput[]) remoteInputs, (RemoteInput[]) dataOnlyRemoteInputs, allowGeneratedReplies);
                }

                public android.support.v4.app.NotificationCompat.Action[] newArray(int length) {
                    return new android.support.v4.app.NotificationCompat.Action[length];
                }
            };
        }
    }

    public static class BigPictureStyle extends Style {
        private Bitmap mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private Bitmap mPicture;

        public BigPictureStyle(Builder builder) {
            setBuilder(builder);
        }

        public android.support.v4.app.NotificationCompat.BigPictureStyle setBigContentTitle(CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public android.support.v4.app.NotificationCompat.BigPictureStyle setSummaryText(CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public android.support.v4.app.NotificationCompat.BigPictureStyle bigPicture(Bitmap b) {
            this.mPicture = b;
            return this;
        }

        public android.support.v4.app.NotificationCompat.BigPictureStyle bigLargeIcon(Bitmap b) {
            this.mBigLargeIcon = b;
            this.mBigLargeIconSet = true;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.addBigPictureStyle(builder, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mPicture, this.mBigLargeIcon, this.mBigLargeIconSet);
            }
        }
    }

    public static class BigTextStyle extends Style {
        private CharSequence mBigText;

        public BigTextStyle(Builder builder) {
            setBuilder(builder);
        }

        public android.support.v4.app.NotificationCompat.BigTextStyle setBigContentTitle(CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public android.support.v4.app.NotificationCompat.BigTextStyle setSummaryText(CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public android.support.v4.app.NotificationCompat.BigTextStyle bigText(CharSequence cs) {
            this.mBigText = Builder.limitCharSequenceLength(cs);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.addBigTextStyle(builder, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mBigText);
            }
        }
    }

    public static final class CarExtender implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public static class UnreadConversation extends android.support.v4.app.NotificationCompatBase.UnreadConversation {
            static final Factory FACTORY;
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages;
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(String name) {
                    this.mMessages = new ArrayList();
                    this.mParticipant = name;
                }

                public android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation.Builder addMessage(String message) {
                    this.mMessages.add(message);
                    return this;
                }

                public android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation.Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput;
                    this.mReplyPendingIntent = pendingIntent;
                    return this;
                }

                public android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation.Builder setReadPendingIntent(PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent;
                    return this;
                }

                public android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation.Builder setLatestTimestamp(long timestamp) {
                    this.mLatestTimestamp = timestamp;
                    return this;
                }

                public android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation build() {
                    return new android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation((String[]) this.mMessages.toArray(new String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new String[]{this.mParticipant}, this.mLatestTimestamp);
                }
            }

            UnreadConversation(String[] messages, RemoteInput remoteInput, PendingIntent replyPendingIntent, PendingIntent readPendingIntent, String[] participants, long latestTimestamp) {
                this.mMessages = messages;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = readPendingIntent;
                this.mReplyPendingIntent = replyPendingIntent;
                this.mParticipants = participants;
                this.mLatestTimestamp = latestTimestamp;
            }

            public String[] getMessages() {
                return this.mMessages;
            }

            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public String[] getParticipants() {
                return this.mParticipants;
            }

            public String getParticipant() {
                return this.mParticipants.length > 0 ? this.mParticipants[0] : null;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            static {
                FACTORY = new Factory() {
                    public android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation build(String[] messages, RemoteInput remoteInput, PendingIntent replyPendingIntent, PendingIntent readPendingIntent, String[] participants, long latestTimestamp) {
                        return new android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation(messages, (RemoteInput) remoteInput, replyPendingIntent, readPendingIntent, participants, latestTimestamp);
                    }
                };
            }
        }

        public CarExtender() {
            this.mColor = 0;
        }

        public CarExtender(Notification notification) {
            this.mColor = 0;
            if (VERSION.SDK_INT >= 21) {
                Bundle carBundle;
                if (NotificationCompat.getExtras(notification) == null) {
                    carBundle = null;
                } else {
                    carBundle = NotificationCompat.getExtras(notification).getBundle(EXTRA_CAR_EXTENDER);
                }
                if (carBundle != null) {
                    this.mLargeIcon = (Bitmap) carBundle.getParcelable(EXTRA_LARGE_ICON);
                    this.mColor = carBundle.getInt(EXTRA_COLOR, VISIBILITY_PRIVATE);
                    this.mUnreadConversation = (UnreadConversation) IMPL.getUnreadConversationFromBundle(carBundle.getBundle(EXTRA_CONVERSATION), UnreadConversation.FACTORY, RemoteInput.FACTORY);
                }
            }
        }

        public Builder extend(Builder builder) {
            if (VERSION.SDK_INT >= 21) {
                Bundle carExtensions = new Bundle();
                if (this.mLargeIcon != null) {
                    carExtensions.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon);
                }
                if (this.mColor != 0) {
                    carExtensions.putInt(EXTRA_COLOR, this.mColor);
                }
                if (this.mUnreadConversation != null) {
                    carExtensions.putBundle(EXTRA_CONVERSATION, IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
                }
                builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, carExtensions);
            }
            return builder;
        }

        public android.support.v4.app.NotificationCompat.CarExtender setColor(@ColorInt int color) {
            this.mColor = color;
            return this;
        }

        @ColorInt
        public int getColor() {
            return this.mColor;
        }

        public android.support.v4.app.NotificationCompat.CarExtender setLargeIcon(Bitmap largeIcon) {
            this.mLargeIcon = largeIcon;
            return this;
        }

        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public android.support.v4.app.NotificationCompat.CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        private static final int MAX_ACTION_BUTTONS = 3;

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                builder.getBuilder().setStyle(new android.app.Notification.DecoratedCustomViewStyle());
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            return (VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) ? createRemoteViews(this.mBuilder.getContentView(), false) : null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews innerView;
            RemoteViews bigContentView = this.mBuilder.getBigContentView();
            if (bigContentView != null) {
                innerView = bigContentView;
            } else {
                innerView = this.mBuilder.getContentView();
            }
            return innerView != null ? createRemoteViews(innerView, true) : null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUp = this.mBuilder.getHeadsUpContentView();
            return headsUp != null ? createRemoteViews(headsUp != null ? headsUp : this.mBuilder.getContentView(), true) : null;
        }

        private RemoteViews createRemoteViews(RemoteViews innerView, boolean showActions) {
            int actionVisibility = VISIBILITY_PRIVATE;
            RemoteViews remoteViews = applyStandardTemplate(true, R.layout.notification_template_custom_big, false);
            remoteViews.removeAllViews(R.id.actions);
            boolean actionsVisible = false;
            if (showActions && this.mBuilder.mActions != null) {
                int numActions = Math.min(this.mBuilder.mActions.size(), MAX_ACTION_BUTTONS);
                if (numActions > 0) {
                    actionsVisible = true;
                    for (int i = VISIBILITY_PRIVATE; i < numActions; i++) {
                        remoteViews.addView(R.id.actions, generateActionButton((Action) this.mBuilder.mActions.get(i)));
                    }
                }
            }
            if (!actionsVisible) {
                actionVisibility = FLAG_ONLY_ALERT_ONCE;
            }
            remoteViews.setViewVisibility(R.id.actions, actionVisibility);
            remoteViews.setViewVisibility(R.id.action_divider, actionVisibility);
            buildIntoRemoteViews(remoteViews, innerView);
            return remoteViews;
        }

        private RemoteViews generateActionButton(Action action) {
            boolean tombstone = action.actionIntent == null;
            RemoteViews button = new RemoteViews(this.mBuilder.mContext.getPackageName(), tombstone ? R.layout.notification_action_tombstone : R.layout.notification_action);
            button.setImageViewBitmap(R.id.action_image, createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
            button.setTextViewText(R.id.action_text, action.title);
            if (!tombstone) {
                button.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            if (VERSION.SDK_INT >= 15) {
                button.setContentDescription(R.id.action_container, action.title);
            }
            return button;
        }
    }

    public static class InboxStyle extends Style {
        private ArrayList<CharSequence> mTexts;

        public InboxStyle() {
            this.mTexts = new ArrayList();
        }

        public InboxStyle(Builder builder) {
            this.mTexts = new ArrayList();
            setBuilder(builder);
        }

        public android.support.v4.app.NotificationCompat.InboxStyle setBigContentTitle(CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public android.support.v4.app.NotificationCompat.InboxStyle setSummaryText(CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public android.support.v4.app.NotificationCompat.InboxStyle addLine(CharSequence cs) {
            this.mTexts.add(Builder.limitCharSequenceLength(cs));
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.addInboxStyle(builder, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mTexts);
            }
        }
    }

    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<Message> mMessages;
        CharSequence mUserDisplayName;

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_SENDER = "sender";
            static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private Bundle mExtras;
            private final CharSequence mSender;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence text, long timestamp, CharSequence sender) {
                this.mExtras = new Bundle();
                this.mText = text;
                this.mTimestamp = timestamp;
                this.mSender = sender;
            }

            public android.support.v4.app.NotificationCompat.MessagingStyle.Message setData(String dataMimeType, Uri dataUri) {
                this.mDataMimeType = dataMimeType;
                this.mDataUri = dataUri;
                return this;
            }

            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public CharSequence getSender() {
                return this.mSender;
            }

            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            public Uri getDataUri() {
                return this.mDataUri;
            }

            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                if (this.mText != null) {
                    bundle.putCharSequence(KEY_TEXT, this.mText);
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
                if (this.mSender != null) {
                    bundle.putCharSequence(KEY_SENDER, this.mSender);
                }
                if (this.mDataMimeType != null) {
                    bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType);
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable(KEY_DATA_URI, this.mDataUri);
                }
                if (this.mExtras != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras);
                }
                return bundle;
            }

            static Bundle[] getBundleArrayForMessages(List<android.support.v4.app.NotificationCompat.MessagingStyle.Message> messages) {
                Bundle[] bundles = new Bundle[messages.size()];
                int N = messages.size();
                for (int i = VISIBILITY_PRIVATE; i < N; i++) {
                    bundles[i] = ((android.support.v4.app.NotificationCompat.MessagingStyle.Message) messages.get(i)).toBundle();
                }
                return bundles;
            }

            static List<android.support.v4.app.NotificationCompat.MessagingStyle.Message> getMessagesFromBundleArray(Parcelable[] bundles) {
                List<android.support.v4.app.NotificationCompat.MessagingStyle.Message> messages = new ArrayList(bundles.length);
                for (int i = VISIBILITY_PRIVATE; i < bundles.length; i++) {
                    if (bundles[i] instanceof Bundle) {
                        android.support.v4.app.NotificationCompat.MessagingStyle.Message message = getMessageFromBundle((Bundle) bundles[i]);
                        if (message != null) {
                            messages.add(message);
                        }
                    }
                }
                return messages;
            }

            static android.support.v4.app.NotificationCompat.MessagingStyle.Message getMessageFromBundle(Bundle bundle) {
                try {
                    if (!bundle.containsKey(KEY_TEXT) || !bundle.containsKey(KEY_TIMESTAMP)) {
                        return null;
                    }
                    android.support.v4.app.NotificationCompat.MessagingStyle.Message message = new android.support.v4.app.NotificationCompat.MessagingStyle.Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
                    if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                        message.setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri) bundle.getParcelable(KEY_DATA_URI));
                    }
                    if (!bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                        return message;
                    }
                    message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                    return message;
                } catch (ClassCastException e) {
                    return null;
                }
            }
        }

        MessagingStyle() {
            this.mMessages = new ArrayList();
        }

        public MessagingStyle(@NonNull CharSequence userDisplayName) {
            this.mMessages = new ArrayList();
            this.mUserDisplayName = userDisplayName;
        }

        public CharSequence getUserDisplayName() {
            return this.mUserDisplayName;
        }

        public android.support.v4.app.NotificationCompat.MessagingStyle setConversationTitle(CharSequence conversationTitle) {
            this.mConversationTitle = conversationTitle;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public android.support.v4.app.NotificationCompat.MessagingStyle addMessage(CharSequence text, long timestamp, CharSequence sender) {
            this.mMessages.add(new Message(text, timestamp, sender));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(VISIBILITY_PRIVATE);
            }
            return this;
        }

        public android.support.v4.app.NotificationCompat.MessagingStyle addMessage(Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(VISIBILITY_PRIVATE);
            }
            return this;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public static android.support.v4.app.NotificationCompat.MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras != null && !extras.containsKey(EXTRA_SELF_DISPLAY_NAME)) {
                return null;
            }
            try {
                android.support.v4.app.NotificationCompat.MessagingStyle style = new android.support.v4.app.NotificationCompat.MessagingStyle();
                style.restoreFromCompatExtras(extras);
                return style;
            } catch (ClassCastException e) {
                return null;
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                List<CharSequence> texts = new ArrayList();
                List<Long> timestamps = new ArrayList();
                List<CharSequence> senders = new ArrayList();
                List<String> dataMimeTypes = new ArrayList();
                List<Uri> dataUris = new ArrayList();
                for (Message message : this.mMessages) {
                    Message message2;
                    texts.add(message2.getText());
                    timestamps.add(Long.valueOf(message2.getTimestamp()));
                    senders.add(message2.getSender());
                    dataMimeTypes.add(message2.getDataMimeType());
                    dataUris.add(message2.getDataUri());
                }
                NotificationCompatApi24.addMessagingStyle(builder, this.mUserDisplayName, this.mConversationTitle, texts, timestamps, senders, dataMimeTypes, dataUris);
                return;
            }
            Message latestIncomingMessage = findLatestIncomingMessage();
            if (this.mConversationTitle != null) {
                builder.getBuilder().setContentTitle(this.mConversationTitle);
            } else if (latestIncomingMessage != null) {
                builder.getBuilder().setContentTitle(latestIncomingMessage.getSender());
            }
            if (latestIncomingMessage != null) {
                CharSequence makeMessageLine;
                android.app.Notification.Builder builder2 = builder.getBuilder();
                if (this.mConversationTitle != null) {
                    makeMessageLine = makeMessageLine(latestIncomingMessage);
                } else {
                    makeMessageLine = latestIncomingMessage.getText();
                }
                builder2.setContentText(makeMessageLine);
            }
            if (VERSION.SDK_INT >= 16) {
                SpannableStringBuilder completeMessage = new SpannableStringBuilder();
                boolean showNames = this.mConversationTitle != null || hasMessagesWithoutSender();
                for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                    message2 = (Message) this.mMessages.get(i);
                    CharSequence line = showNames ? makeMessageLine(message2) : message2.getText();
                    if (i != this.mMessages.size() - 1) {
                        completeMessage.insert(VISIBILITY_PRIVATE, "\n");
                    }
                    completeMessage.insert(VISIBILITY_PRIVATE, line);
                }
                NotificationCompatJellybean.addBigTextStyle(builder, null, false, null, completeMessage);
            }
        }

        @Nullable
        private Message findLatestIncomingMessage() {
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                Message message = (Message) this.mMessages.get(i);
                if (!TextUtils.isEmpty(message.getSender())) {
                    return message;
                }
            }
            return !this.mMessages.isEmpty() ? (Message) this.mMessages.get(this.mMessages.size() - 1) : null;
        }

        private boolean hasMessagesWithoutSender() {
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                if (((Message) this.mMessages.get(i)).getSender() == null) {
                    return true;
                }
            }
            return false;
        }

        private CharSequence makeMessageLine(Message message) {
            BidiFormatter bidi = BidiFormatter.getInstance();
            SpannableStringBuilder sb = new SpannableStringBuilder();
            boolean afterLollipop = VERSION.SDK_INT >= 21;
            int color = afterLollipop ? ViewCompat.MEASURED_STATE_MASK : VISIBILITY_SECRET;
            CharSequence replyName = message.getSender();
            if (TextUtils.isEmpty(message.getSender())) {
                replyName = this.mUserDisplayName == null ? StringUtils.EMPTY_STRING : this.mUserDisplayName;
                if (afterLollipop && this.mBuilder.getColor() != 0) {
                    color = this.mBuilder.getColor();
                }
            }
            CharSequence senderText = bidi.unicodeWrap(replyName);
            sb.append(senderText);
            sb.setSpan(makeFontColorSpan(color), sb.length() - senderText.length(), sb.length(), net.oneplus.weather.R.styleable.OneplusTheme_op_borderWidth);
            sb.append("  ").append(bidi.unicodeWrap(message.getText() == null ? StringUtils.EMPTY_STRING : message.getText()));
            return sb;
        }

        @NonNull
        private TextAppearanceSpan makeFontColorSpan(int color) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(color), null);
        }

        public void addCompatExtras(Bundle extras) {
            super.addCompatExtras(extras);
            if (this.mUserDisplayName != null) {
                extras.putCharSequence(EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
            }
            if (this.mConversationTitle != null) {
                extras.putCharSequence(EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                extras.putParcelableArray(EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages));
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        protected void restoreFromCompatExtras(Bundle extras) {
            this.mMessages.clear();
            this.mUserDisplayName = extras.getString(EXTRA_SELF_DISPLAY_NAME);
            this.mConversationTitle = extras.getString(EXTRA_CONVERSATION_TITLE);
            Parcelable[] parcelables = extras.getParcelableArray(EXTRA_MESSAGES);
            if (parcelables != null) {
                this.mMessages = Message.getMessagesFromBundleArray(parcelables);
            }
        }
    }

    static class NotificationCompatBaseImpl implements NotificationCompatImpl {

        public static class BuilderBase implements NotificationBuilderWithBuilderAccessor {
            private android.app.Notification.Builder mBuilder;

            BuilderBase(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate) {
                this.mBuilder = new android.app.Notification.Builder(context).setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setProgress(progressMax, progress, progressIndeterminate);
            }

            public android.app.Notification.Builder getBuilder() {
                return this.mBuilder;
            }

            public Notification build() {
                return this.mBuilder.getNotification();
            }
        }

        NotificationCompatBaseImpl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            return extender.build(b, new BuilderBase(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate));
        }

        public Action getAction(Notification n, int actionIndex) {
            return null;
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return null;
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actions) {
            return null;
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation uc) {
            return null;
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle b, Factory factory, RemoteInput.Factory remoteInputFactory) {
            return null;
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions;
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex;
        private int mContentIcon;
        private int mContentIconGravity;
        private int mCustomContentHeight;
        private int mCustomSizePreset;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags;
        private int mGravity;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages;

        public WearableExtender() {
            this.mActions = new ArrayList();
            this.mFlags = 1;
            this.mPages = new ArrayList();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
        }

        public WearableExtender(Notification notification) {
            this.mActions = new ArrayList();
            this.mFlags = 1;
            this.mPages = new ArrayList();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
            Bundle extras = NotificationCompat.getExtras(notification);
            Bundle wearableBundle = extras != null ? extras.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
            if (wearableBundle != null) {
                Action[] actions = IMPL.getActionsFromParcelableArrayList(wearableBundle.getParcelableArrayList(KEY_ACTIONS));
                if (actions != null) {
                    Collections.addAll(this.mActions, actions);
                }
                this.mFlags = wearableBundle.getInt(KEY_FLAGS, SIZE_XSMALL);
                this.mDisplayIntent = (PendingIntent) wearableBundle.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] pages = NotificationCompat.getNotificationArrayFromBundle(wearableBundle, KEY_PAGES);
                if (pages != null) {
                    Collections.addAll(this.mPages, pages);
                }
                this.mBackground = (Bitmap) wearableBundle.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = wearableBundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = wearableBundle.getInt(KEY_CONTENT_ICON_GRAVITY, DEFAULT_CONTENT_ICON_GRAVITY);
                this.mContentActionIndex = wearableBundle.getInt(KEY_CONTENT_ACTION_INDEX, UNSET_ACTION_INDEX);
                this.mCustomSizePreset = wearableBundle.getInt(KEY_CUSTOM_SIZE_PRESET, SIZE_DEFAULT);
                this.mCustomContentHeight = wearableBundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = wearableBundle.getInt(KEY_GRAVITY, DEFAULT_GRAVITY);
                this.mHintScreenTimeout = wearableBundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = wearableBundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = wearableBundle.getString(KEY_BRIDGE_TAG);
            }
        }

        public Builder extend(Builder builder) {
            Bundle wearableBundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                wearableBundle.putParcelableArrayList(KEY_ACTIONS, IMPL.getParcelableArrayListForActions((Action[]) this.mActions.toArray(new Action[this.mActions.size()])));
            }
            if (this.mFlags != 1) {
                wearableBundle.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                wearableBundle.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                wearableBundle.putParcelableArray(KEY_PAGES, (Parcelable[]) this.mPages.toArray(new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                wearableBundle.putParcelable(KEY_BACKGROUND, this.mBackground);
            }
            if (this.mContentIcon != 0) {
                wearableBundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                wearableBundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                wearableBundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                wearableBundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                wearableBundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                wearableBundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                wearableBundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                wearableBundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                wearableBundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
            return builder;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender clone() {
            android.support.v4.app.NotificationCompat.WearableExtender that = new android.support.v4.app.NotificationCompat.WearableExtender();
            that.mActions = new ArrayList(this.mActions);
            that.mFlags = this.mFlags;
            that.mDisplayIntent = this.mDisplayIntent;
            that.mPages = new ArrayList(this.mPages);
            that.mBackground = this.mBackground;
            that.mContentIcon = this.mContentIcon;
            that.mContentIconGravity = this.mContentIconGravity;
            that.mContentActionIndex = this.mContentActionIndex;
            that.mCustomSizePreset = this.mCustomSizePreset;
            that.mCustomContentHeight = this.mCustomContentHeight;
            that.mGravity = this.mGravity;
            that.mHintScreenTimeout = this.mHintScreenTimeout;
            that.mDismissalId = this.mDismissalId;
            that.mBridgeTag = this.mBridgeTag;
            return that;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender addActions(List<Action> actions) {
            this.mActions.addAll(actions);
            return this;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setDisplayIntent(PendingIntent intent) {
            this.mDisplayIntent = intent;
            return this;
        }

        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender addPage(Notification page) {
            this.mPages.add(page);
            return this;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender addPages(List<Notification> pages) {
            this.mPages.addAll(pages);
            return this;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public List<Notification> getPages() {
            return this.mPages;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setBackground(Bitmap background) {
            this.mBackground = background;
            return this;
        }

        public Bitmap getBackground() {
            return this.mBackground;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setContentIcon(int icon) {
            this.mContentIcon = icon;
            return this;
        }

        public int getContentIcon() {
            return this.mContentIcon;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setContentIconGravity(int contentIconGravity) {
            this.mContentIconGravity = contentIconGravity;
            return this;
        }

        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setContentAction(int actionIndex) {
            this.mContentActionIndex = actionIndex;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        public int getGravity() {
            return this.mGravity;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setCustomSizePreset(int sizePreset) {
            this.mCustomSizePreset = sizePreset;
            return this;
        }

        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setCustomContentHeight(int height) {
            this.mCustomContentHeight = height;
            return this;
        }

        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setStartScrollBottom(boolean startScrollBottom) {
            setFlag(FLAG_START_SCROLL_BOTTOM, startScrollBottom);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setContentIntentAvailableOffline(boolean contentIntentAvailableOffline) {
            setFlag(SIZE_XSMALL, contentIntentAvailableOffline);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setHintHideIcon(boolean hintHideIcon) {
            setFlag(SIZE_SMALL, hintHideIcon);
            return this;
        }

        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setHintShowBackgroundOnly(boolean hintShowBackgroundOnly) {
            setFlag(SIZE_LARGE, hintShowBackgroundOnly);
            return this;
        }

        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setHintAvoidBackgroundClipping(boolean hintAvoidBackgroundClipping) {
            setFlag(FLAG_HINT_AVOID_BACKGROUND_CLIPPING, hintAvoidBackgroundClipping);
            return this;
        }

        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setHintScreenTimeout(int timeout) {
            this.mHintScreenTimeout = timeout;
            return this;
        }

        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setHintAmbientBigPicture(boolean hintAmbientBigPicture) {
            setFlag(FLAG_BIG_PICTURE_AMBIENT, hintAmbientBigPicture);
            return this;
        }

        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setHintContentIntentLaunchesActivity(boolean hintContentIntentLaunchesActivity) {
            setFlag(FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY, hintContentIntentLaunchesActivity);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setDismissalId(String dismissalId) {
            this.mDismissalId = dismissalId;
            return this;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public android.support.v4.app.NotificationCompat.WearableExtender setBridgeTag(String bridgeTag) {
            this.mBridgeTag = bridgeTag;
            return this;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                this.mFlags |= mask;
            } else {
                this.mFlags &= mask ^ -1;
            }
        }
    }

    @RequiresApi(16)
    static class NotificationCompatApi16Impl extends NotificationCompatBaseImpl {
        NotificationCompatApi16Impl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            android.support.v4.app.NotificationCompatJellybean.Builder builder = new android.support.v4.app.NotificationCompatJellybean.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mExtras, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView);
            NotificationCompat.addActionsToBuilder(builder, b.mActions);
            if (b.mStyle != null) {
                b.mStyle.apply(builder);
            }
            Notification notification = extender.build(b, builder);
            if (b.mStyle != null) {
                Bundle extras = NotificationCompat.getExtras(notification);
                if (extras != null) {
                    b.mStyle.addCompatExtras(extras);
                }
            }
            return notification;
        }

        public Action getAction(Notification n, int actionIndex) {
            return (Action) NotificationCompatJellybean.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> parcelables) {
            return (Action[]) NotificationCompatJellybean.getActionsFromParcelableArrayList(parcelables, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actions) {
            return NotificationCompatJellybean.getParcelableArrayListForActions(actions);
        }
    }

    @RequiresApi(19)
    static class NotificationCompatApi19Impl extends NotificationCompatApi16Impl {
        NotificationCompatApi19Impl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            android.support.v4.app.NotificationCompatKitKat.Builder builder = new android.support.v4.app.NotificationCompatKitKat.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mPeople, b.mExtras, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView);
            NotificationCompat.addActionsToBuilder(builder, b.mActions);
            if (b.mStyle != null) {
                b.mStyle.apply(builder);
            }
            return extender.build(b, builder);
        }

        public Action getAction(Notification n, int actionIndex) {
            return (Action) NotificationCompatKitKat.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }
    }

    @RequiresApi(20)
    static class NotificationCompatApi20Impl extends NotificationCompatApi19Impl {
        NotificationCompatApi20Impl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            android.support.v4.app.NotificationCompatApi20.Builder builder = new android.support.v4.app.NotificationCompatApi20.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mPeople, b.mExtras, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView, b.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder, b.mActions);
            if (b.mStyle != null) {
                b.mStyle.apply(builder);
            }
            Notification notification = extender.build(b, builder);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(NotificationCompat.getExtras(notification));
            }
            return notification;
        }

        public Action getAction(Notification n, int actionIndex) {
            return (Action) NotificationCompatApi20.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> parcelables) {
            return (Action[]) NotificationCompatApi20.getActionsFromParcelableArrayList(parcelables, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actions) {
            return NotificationCompatApi20.getParcelableArrayListForActions(actions);
        }
    }

    @RequiresApi(21)
    static class NotificationCompatApi21Impl extends NotificationCompatApi20Impl {
        NotificationCompatApi21Impl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            android.support.v4.app.NotificationCompatApi21.Builder builder = new android.support.v4.app.NotificationCompatApi21.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mCategory, b.mPeople, b.mExtras, b.mColor, b.mVisibility, b.mPublicVersion, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView, b.mHeadsUpContentView, b.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder, b.mActions);
            if (b.mStyle != null) {
                b.mStyle.apply(builder);
            }
            Notification notification = extender.build(b, builder);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(NotificationCompat.getExtras(notification));
            }
            return notification;
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation uc) {
            return NotificationCompatApi21.getBundleForUnreadConversation(uc);
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle b, Factory factory, RemoteInput.Factory remoteInputFactory) {
            return NotificationCompatApi21.getUnreadConversationFromBundle(b, factory, remoteInputFactory);
        }
    }

    @RequiresApi(24)
    static class NotificationCompatApi24Impl extends NotificationCompatApi21Impl {
        NotificationCompatApi24Impl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            android.support.v4.app.NotificationCompatApi24.Builder builder = new android.support.v4.app.NotificationCompatApi24.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mCategory, b.mPeople, b.mExtras, b.mColor, b.mVisibility, b.mPublicVersion, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mRemoteInputHistory, b.mContentView, b.mBigContentView, b.mHeadsUpContentView, b.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder, b.mActions);
            if (b.mStyle != null) {
                b.mStyle.apply(builder);
            }
            Notification notification = extender.build(b, builder);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(NotificationCompat.getExtras(notification));
            }
            return notification;
        }

        public Action getAction(Notification n, int actionIndex) {
            return (Action) NotificationCompatApi24.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> parcelables) {
            return (Action[]) NotificationCompatApi24.getActionsFromParcelableArrayList(parcelables, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actions) {
            return NotificationCompatApi24.getParcelableArrayListForActions(actions);
        }
    }

    @RequiresApi(26)
    static class NotificationCompatApi26Impl extends NotificationCompatApi24Impl {
        NotificationCompatApi26Impl() {
        }

        public Notification build(Builder b, BuilderExtender extender) {
            android.support.v4.app.NotificationCompatApi26.Builder builder = new android.support.v4.app.NotificationCompatApi26.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mCategory, b.mPeople, b.mExtras, b.mColor, b.mVisibility, b.mPublicVersion, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mRemoteInputHistory, b.mContentView, b.mBigContentView, b.mHeadsUpContentView, b.mChannelId, b.mBadgeIcon, b.mShortcutId, b.mTimeout, b.mColorized, b.mColorizedSet, b.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder, b.mActions);
            if (b.mStyle != null) {
                b.mStyle.apply(builder);
            }
            Notification notification = extender.build(b, builder);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(NotificationCompat.getExtras(notification));
            }
            return notification;
        }
    }

    static void addActionsToBuilder(NotificationBuilderWithActions builder, ArrayList<Action> actions) {
        Iterator it = actions.iterator();
        while (it.hasNext()) {
            builder.addAction((Action) it.next());
        }
    }

    static {
        if (VERSION.SDK_INT >= 26) {
            IMPL = new NotificationCompatApi26Impl();
        } else if (VERSION.SDK_INT >= 24) {
            IMPL = new NotificationCompatApi24Impl();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new NotificationCompatApi21Impl();
        } else if (VERSION.SDK_INT >= 20) {
            IMPL = new NotificationCompatApi20Impl();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new NotificationCompatApi19Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new NotificationCompatApi16Impl();
        } else {
            IMPL = new NotificationCompatBaseImpl();
        }
    }

    static Notification[] getNotificationArrayFromBundle(Bundle bundle, String key) {
        Parcelable[] array = bundle.getParcelableArray(key);
        if ((array instanceof Notification[]) || array == null) {
            return (Notification[]) array;
        }
        Notification[] typedArray = new Notification[array.length];
        for (int i = VISIBILITY_PRIVATE; i < array.length; i++) {
            typedArray[i] = (Notification) array[i];
        }
        bundle.putParcelableArray(key, typedArray);
        return typedArray;
    }

    public static Bundle getExtras(Notification notification) {
        if (VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        return VERSION.SDK_INT >= 16 ? NotificationCompatJellybean.getExtras(notification) : null;
    }

    public static int getActionCount(Notification notification) {
        return VERSION.SDK_INT >= 19 ? notification.actions != null ? notification.actions.length : VISIBILITY_PRIVATE : VERSION.SDK_INT >= 16 ? NotificationCompatJellybean.getActionCount(notification) : VISIBILITY_PRIVATE;
    }

    public static Action getAction(Notification notification, int actionIndex) {
        return IMPL.getAction(notification, actionIndex);
    }

    public static String getCategory(Notification notification) {
        return VERSION.SDK_INT >= 21 ? notification.category : null;
    }

    public static boolean getLocalOnly(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return (notification.flags & 256) != 0;
        } else {
            if (VERSION.SDK_INT >= 19) {
                return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            }
            return VERSION.SDK_INT >= 16 ? NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY) : false;
        }
    }

    public static String getGroup(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        return VERSION.SDK_INT >= 16 ? NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY) : null;
    }

    public static boolean isGroupSummary(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return (notification.flags & 512) != 0;
        } else {
            if (VERSION.SDK_INT >= 19) {
                return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            }
            return VERSION.SDK_INT >= 16 ? NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY) : false;
        }
    }

    public static String getSortKey(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        return VERSION.SDK_INT >= 16 ? NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY) : null;
    }

    public static String getChannelId(Notification notification) {
        return VERSION.SDK_INT >= 26 ? notification.getChannelId() : null;
    }

    public static long getTimeoutAfter(Notification notification) {
        return VERSION.SDK_INT >= 26 ? notification.getTimeoutAfter() : 0;
    }

    public static int getBadgeIconType(Notification notification) {
        return VERSION.SDK_INT >= 26 ? notification.getBadgeIconType() : VISIBILITY_PRIVATE;
    }

    public static String getShortcutId(Notification notification) {
        return VERSION.SDK_INT >= 26 ? notification.getShortcutId() : null;
    }

    public static int getGroupAlertBehavior(Notification notification) {
        return VERSION.SDK_INT >= 26 ? notification.getGroupAlertBehavior() : VISIBILITY_PRIVATE;
    }
}
