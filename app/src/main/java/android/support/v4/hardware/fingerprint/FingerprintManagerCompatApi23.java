package android.support.v4.hardware.fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationCallback;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationResultInternal;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@RequiresApi(23)
@RestrictTo({Scope.LIBRARY_GROUP})
public final class FingerprintManagerCompatApi23 {

    static class AnonymousClass_1 extends android.hardware.fingerprint.FingerprintManager.AuthenticationCallback {
        final /* synthetic */ AuthenticationCallback val$callback;

        AnonymousClass_1(AuthenticationCallback authenticationCallback) {
            this.val$callback = authenticationCallback;
        }

        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            this.val$callback.onAuthenticationError(errMsgId, errString);
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            this.val$callback.onAuthenticationHelp(helpMsgId, helpString);
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
            this.val$callback.onAuthenticationSucceeded(new AuthenticationResultInternal(FingerprintManagerCompatApi23.unwrapCryptoObject(result.getCryptoObject())));
        }

        public void onAuthenticationFailed() {
            this.val$callback.onAuthenticationFailed();
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResultInternal result) {
        }

        public void onAuthenticationFailed() {
        }
    }

    public static final class AuthenticationResultInternal {
        private CryptoObject mCryptoObject;

        public AuthenticationResultInternal(CryptoObject crypto) {
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    private static FingerprintManager getFingerprintManagerOrNull(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.fingerprint") ? (FingerprintManager) context.getSystemService(FingerprintManager.class) : null;
    }

    @SuppressLint({"MissingPermission"})
    static boolean hasEnrolledFingerprints(Context context) {
        FingerprintManager fp = getFingerprintManagerOrNull(context);
        return fp != null && fp.hasEnrolledFingerprints();
    }

    @SuppressLint({"MissingPermission"})
    static boolean isHardwareDetected(Context context) {
        FingerprintManager fp = getFingerprintManagerOrNull(context);
        return fp != null && fp.isHardwareDetected();
    }

    @SuppressLint({"MissingPermission"})
    static void authenticate(Context context, CryptoObject crypto, int flags, Object cancel, AuthenticationCallback callback, Handler handler) {
        FingerprintManager fp = getFingerprintManagerOrNull(context);
        if (fp != null) {
            fp.authenticate(wrapCryptoObject(crypto), (CancellationSignal) cancel, flags, wrapCallback(callback), handler);
        }
    }

    private static android.hardware.fingerprint.FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject.getSignature());
        }
        return cryptoObject.getMac() != null ? new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject.getMac()) : null;
    }

    private static CryptoObject unwrapCryptoObject(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new CryptoObject(cryptoObject.getSignature());
        }
        return cryptoObject.getMac() != null ? new CryptoObject(cryptoObject.getMac()) : null;
    }

    private static android.hardware.fingerprint.FingerprintManager.AuthenticationCallback wrapCallback(AuthenticationCallback callback) {
        return new AnonymousClass_1(callback);
    }
}
