package android.support.v4.media;

import android.media.AudioAttributes;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.SparseIntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import net.oneplus.weather.provider.CitySearchProvider;

public class AudioAttributesCompat {
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    private static final int FLAG_ALL = 1023;
    private static final int FLAG_ALL_PUBLIC = 273;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    private static final int FLAG_BEACON = 8;
    private static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
    private static final int FLAG_BYPASS_MUTE = 128;
    private static final int FLAG_DEEP_BUFFER = 512;
    public static final int FLAG_HW_AV_SYNC = 16;
    private static final int FLAG_HW_HOTWORD = 32;
    private static final int FLAG_LOW_LATENCY = 256;
    private static final int FLAG_SCO = 4;
    private static final int FLAG_SECURE = 2;
    private static final int[] SDK_USAGES;
    private static final int SUPPRESSIBLE_CALL = 2;
    private static final int SUPPRESSIBLE_NOTIFICATION = 1;
    private static final SparseIntArray SUPPRESSIBLE_USAGES;
    private static final String TAG = "AudioAttributesCompat";
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    private static final int USAGE_VIRTUAL_SOURCE = 15;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    private static boolean sForceLegacyBehavior;
    private Wrapper mAudioAttributesWrapper;
    int mContentType;
    int mFlags;
    Integer mLegacyStream;
    int mUsage;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface AttributeContentType {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface AttributeUsage {
    }

    private static abstract class AudioManagerHidden {
        public static final int STREAM_ACCESSIBILITY = 10;
        public static final int STREAM_BLUETOOTH_SCO = 6;
        public static final int STREAM_SYSTEM_ENFORCED = 7;
        public static final int STREAM_TTS = 9;

        private AudioManagerHidden() {
        }
    }

    public static class Builder {
        private Object mAAObject;
        private int mContentType;
        private int mFlags;
        private Integer mLegacyStream;
        private int mUsage;

        public Builder() {
            this.mUsage = 0;
            this.mContentType = 0;
            this.mFlags = 0;
        }

        public Builder(AudioAttributesCompat aa) {
            this.mUsage = 0;
            this.mContentType = 0;
            this.mFlags = 0;
            this.mUsage = aa.mUsage;
            this.mContentType = aa.mContentType;
            this.mFlags = aa.mFlags;
            this.mLegacyStream = aa.mLegacyStream;
            this.mAAObject = aa.unwrap();
        }

        public AudioAttributesCompat build() {
            if (sForceLegacyBehavior || VERSION.SDK_INT < 21) {
                AudioAttributesCompat aac = new AudioAttributesCompat();
                aac.mContentType = this.mContentType;
                aac.mFlags = this.mFlags;
                aac.mUsage = this.mUsage;
                aac.mLegacyStream = this.mLegacyStream;
                aac.mAudioAttributesWrapper = null;
                return aac;
            } else if (this.mAAObject != null) {
                return AudioAttributesCompat.wrap(this.mAAObject);
            } else {
                android.media.AudioAttributes.Builder api21Builder = new android.media.AudioAttributes.Builder().setContentType(this.mContentType).setFlags(this.mFlags).setUsage(this.mUsage);
                if (this.mLegacyStream != null) {
                    api21Builder.setLegacyStreamType(this.mLegacyStream.intValue());
                }
                return AudioAttributesCompat.wrap(api21Builder.build());
            }
        }

        public android.support.v4.media.AudioAttributesCompat.Builder setUsage(int usage) {
            switch (usage) {
                case USAGE_UNKNOWN:
                case USAGE_MEDIA:
                case USAGE_VOICE_COMMUNICATION:
                case USAGE_VOICE_COMMUNICATION_SIGNALLING:
                case USAGE_ALARM:
                case USAGE_NOTIFICATION:
                case USAGE_NOTIFICATION_RINGTONE:
                case USAGE_NOTIFICATION_COMMUNICATION_REQUEST:
                case USAGE_NOTIFICATION_COMMUNICATION_INSTANT:
                case USAGE_NOTIFICATION_COMMUNICATION_DELAYED:
                case USAGE_NOTIFICATION_EVENT:
                case USAGE_ASSISTANCE_ACCESSIBILITY:
                case USAGE_ASSISTANCE_NAVIGATION_GUIDANCE:
                case USAGE_ASSISTANCE_SONIFICATION:
                case USAGE_GAME:
                case USAGE_VIRTUAL_SOURCE:
                    this.mUsage = usage;
                    break;
                case USAGE_ASSISTANT:
                    if (sForceLegacyBehavior || VERSION.SDK_INT <= 25) {
                        this.mUsage = 12;
                    } else {
                        this.mUsage = usage;
                    }
                    break;
                default:
                    this.mUsage = 0;
                    break;
            }
            return this;
        }

        public android.support.v4.media.AudioAttributesCompat.Builder setContentType(int contentType) {
            switch (contentType) {
                case USAGE_UNKNOWN:
                case USAGE_MEDIA:
                case USAGE_VOICE_COMMUNICATION:
                case USAGE_VOICE_COMMUNICATION_SIGNALLING:
                case USAGE_ALARM:
                    this.mContentType = contentType;
                    break;
                default:
                    this.mUsage = 0;
                    break;
            }
            return this;
        }

        public android.support.v4.media.AudioAttributesCompat.Builder setFlags(int flags) {
            this.mFlags |= flags & 1023;
            return this;
        }

        public android.support.v4.media.AudioAttributesCompat.Builder setLegacyStreamType(int streamType) {
            if (streamType == 10) {
                throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
            }
            this.mLegacyStream = Integer.valueOf(streamType);
            this.mUsage = AudioAttributesCompat.usageForStreamType(streamType);
            return this;
        }
    }

    static {
        SUPPRESSIBLE_USAGES = new SparseIntArray();
        SUPPRESSIBLE_USAGES.put(USAGE_NOTIFICATION, USAGE_MEDIA);
        SUPPRESSIBLE_USAGES.put(USAGE_NOTIFICATION_RINGTONE, USAGE_VOICE_COMMUNICATION);
        SUPPRESSIBLE_USAGES.put(USAGE_NOTIFICATION_COMMUNICATION_REQUEST, USAGE_VOICE_COMMUNICATION);
        SUPPRESSIBLE_USAGES.put(USAGE_NOTIFICATION_COMMUNICATION_INSTANT, USAGE_MEDIA);
        SUPPRESSIBLE_USAGES.put(USAGE_NOTIFICATION_COMMUNICATION_DELAYED, USAGE_MEDIA);
        SUPPRESSIBLE_USAGES.put(USAGE_NOTIFICATION_EVENT, USAGE_MEDIA);
        SDK_USAGES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
    }

    private AudioAttributesCompat() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mFlags = 0;
    }

    public int getVolumeControlStream() {
        if (this != null) {
            return (VERSION.SDK_INT < 26 || sForceLegacyBehavior || unwrap() == null) ? toVolumeStreamType(true, this) : ((AudioAttributes) unwrap()).getVolumeControlStream();
        } else {
            throw new IllegalArgumentException("Invalid null audio attributes");
        }
    }

    @Nullable
    public Object unwrap() {
        return this.mAudioAttributesWrapper != null ? this.mAudioAttributesWrapper.unwrap() : null;
    }

    public int getLegacyStreamType() {
        if (this.mLegacyStream != null) {
            return this.mLegacyStream.intValue();
        }
        return (VERSION.SDK_INT < 21 || sForceLegacyBehavior) ? toVolumeStreamType(false, this.mFlags, this.mUsage) : AudioAttributesCompatApi21.toLegacyStreamType(this.mAudioAttributesWrapper);
    }

    @Nullable
    public static AudioAttributesCompat wrap(@NonNull Object aa) {
        if (VERSION.SDK_INT < 21 || sForceLegacyBehavior) {
            return null;
        }
        AudioAttributesCompat aac = new AudioAttributesCompat();
        aac.mAudioAttributesWrapper = Wrapper.wrap((AudioAttributes) aa);
        return aac;
    }

    public int getContentType() {
        return (VERSION.SDK_INT < 21 || sForceLegacyBehavior || this.mAudioAttributesWrapper == null) ? this.mContentType : this.mAudioAttributesWrapper.unwrap().getContentType();
    }

    public int getUsage() {
        return (VERSION.SDK_INT < 21 || sForceLegacyBehavior || this.mAudioAttributesWrapper == null) ? this.mUsage : this.mAudioAttributesWrapper.unwrap().getUsage();
    }

    public int getFlags() {
        if (VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && this.mAudioAttributesWrapper != null) {
            return this.mAudioAttributesWrapper.unwrap().getFlags();
        }
        int flags = this.mFlags;
        int legacyStream = getLegacyStreamType();
        if (legacyStream == 6) {
            flags |= 4;
        } else if (legacyStream == 7) {
            flags |= 1;
        }
        return flags & 273;
    }

    public int hashCode() {
        if (VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && this.mAudioAttributesWrapper != null) {
            return this.mAudioAttributesWrapper.unwrap().hashCode();
        }
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), this.mLegacyStream});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (unwrap() != null) {
            sb.append(" audioattributes=").append(unwrap());
        } else {
            if (this.mLegacyStream != null) {
                sb.append(" stream=").append(this.mLegacyStream);
                sb.append(" derived");
            }
            sb.append(" usage=").append(usageToString()).append(" content=").append(this.mContentType).append(" flags=0x").append(Integer.toHexString(this.mFlags).toUpperCase());
        }
        return sb.toString();
    }

    String usageToString() {
        return usageToString(this.mUsage);
    }

    static String usageToString(int usage) {
        switch (usage) {
            case USAGE_UNKNOWN:
                return new String("USAGE_UNKNOWN");
            case USAGE_MEDIA:
                return new String("USAGE_MEDIA");
            case USAGE_VOICE_COMMUNICATION:
                return new String("USAGE_VOICE_COMMUNICATION");
            case USAGE_VOICE_COMMUNICATION_SIGNALLING:
                return new String("USAGE_VOICE_COMMUNICATION_SIGNALLING");
            case USAGE_ALARM:
                return new String("USAGE_ALARM");
            case USAGE_NOTIFICATION:
                return new String("USAGE_NOTIFICATION");
            case USAGE_NOTIFICATION_RINGTONE:
                return new String("USAGE_NOTIFICATION_RINGTONE");
            case USAGE_NOTIFICATION_COMMUNICATION_REQUEST:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST");
            case USAGE_NOTIFICATION_COMMUNICATION_INSTANT:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT");
            case USAGE_NOTIFICATION_COMMUNICATION_DELAYED:
                return new String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED");
            case USAGE_NOTIFICATION_EVENT:
                return new String("USAGE_NOTIFICATION_EVENT");
            case USAGE_ASSISTANCE_ACCESSIBILITY:
                return new String("USAGE_ASSISTANCE_ACCESSIBILITY");
            case USAGE_ASSISTANCE_NAVIGATION_GUIDANCE:
                return new String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE");
            case USAGE_ASSISTANCE_SONIFICATION:
                return new String("USAGE_ASSISTANCE_SONIFICATION");
            case USAGE_GAME:
                return new String("USAGE_GAME");
            case USAGE_ASSISTANT:
                return new String("USAGE_ASSISTANT");
            default:
                return new String("unknown usage " + usage);
        }
    }

    private static int usageForStreamType(int streamType) {
        switch (streamType) {
            case USAGE_UNKNOWN:
            case USAGE_NOTIFICATION_RINGTONE:
                return USAGE_VOICE_COMMUNICATION;
            case USAGE_MEDIA:
            case USAGE_NOTIFICATION_COMMUNICATION_REQUEST:
                return USAGE_ASSISTANCE_SONIFICATION;
            case USAGE_VOICE_COMMUNICATION:
                return USAGE_NOTIFICATION_RINGTONE;
            case USAGE_VOICE_COMMUNICATION_SIGNALLING:
                return USAGE_MEDIA;
            case USAGE_ALARM:
                return USAGE_ALARM;
            case USAGE_NOTIFICATION:
                return USAGE_NOTIFICATION;
            case USAGE_NOTIFICATION_COMMUNICATION_INSTANT:
                return USAGE_VOICE_COMMUNICATION_SIGNALLING;
            case USAGE_NOTIFICATION_EVENT:
                return USAGE_ASSISTANCE_ACCESSIBILITY;
            default:
                return USAGE_UNKNOWN;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void setForceLegacyBehavior(boolean force) {
        sForceLegacyBehavior = force;
    }

    static int toVolumeStreamType(boolean fromGetVolumeControlStream, AudioAttributesCompat aa) {
        return toVolumeStreamType(fromGetVolumeControlStream, aa.getFlags(), aa.getUsage());
    }

    static int toVolumeStreamType(boolean fromGetVolumeControlStream, int flags, int usage) {
        Object obj = null;
        if ((flags & 1) == 1) {
            return fromGetVolumeControlStream ? USAGE_MEDIA : USAGE_NOTIFICATION_COMMUNICATION_REQUEST;
        } else {
            if ((flags & 4) == 4) {
                return fromGetVolumeControlStream ? 0 : USAGE_NOTIFICATION_RINGTONE;
            } else {
                switch (usage) {
                    case USAGE_UNKNOWN:
                        return fromGetVolumeControlStream ? CitySearchProvider.GET_SEARCH_RESULT_FAIL : 3;
                    case USAGE_MEDIA:
                    case USAGE_ASSISTANCE_NAVIGATION_GUIDANCE:
                    case USAGE_GAME:
                    case USAGE_ASSISTANT:
                        return 3;
                    case USAGE_VOICE_COMMUNICATION:
                        return 0;
                    case USAGE_VOICE_COMMUNICATION_SIGNALLING:
                        if (!fromGetVolumeControlStream) {
                            obj = USAGE_NOTIFICATION_COMMUNICATION_INSTANT;
                        }
                        return r1;
                    case USAGE_ALARM:
                        return 4;
                    case USAGE_NOTIFICATION:
                    case USAGE_NOTIFICATION_COMMUNICATION_REQUEST:
                    case USAGE_NOTIFICATION_COMMUNICATION_INSTANT:
                    case USAGE_NOTIFICATION_COMMUNICATION_DELAYED:
                    case USAGE_NOTIFICATION_EVENT:
                        return USAGE_NOTIFICATION;
                    case USAGE_NOTIFICATION_RINGTONE:
                        return USAGE_VOICE_COMMUNICATION;
                    case USAGE_ASSISTANCE_ACCESSIBILITY:
                        return USAGE_NOTIFICATION_EVENT;
                    case USAGE_ASSISTANCE_SONIFICATION:
                        return USAGE_MEDIA;
                    default:
                        if (!fromGetVolumeControlStream) {
                            return 3;
                        }
                        throw new IllegalArgumentException("Unknown usage value " + usage + " in audio attributes");
                }
            }
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioAttributesCompat that = (AudioAttributesCompat) o;
        if (VERSION.SDK_INT >= 21 && !sForceLegacyBehavior && this.mAudioAttributesWrapper != null) {
            return this.mAudioAttributesWrapper.unwrap().equals(that.unwrap());
        }
        if (this.mContentType == that.getContentType() && this.mFlags == that.getFlags() && this.mUsage == that.getUsage()) {
            if (this.mLegacyStream != null) {
                if (this.mLegacyStream.equals(that.mLegacyStream)) {
                    return true;
                }
            } else if (that.mLegacyStream == null) {
                return true;
            }
        }
        return false;
    }
}
