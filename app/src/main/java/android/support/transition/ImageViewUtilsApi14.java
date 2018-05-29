package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@RequiresApi(14)
class ImageViewUtilsApi14 implements ImageViewUtilsImpl {

    class AnonymousClass_1 extends AnimatorListenerAdapter {
        final /* synthetic */ ImageView val$view;

        AnonymousClass_1(ImageView imageView) {
            this.val$view = imageView;
        }

        public void onAnimationEnd(Animator animation) {
            ScaleType scaleType = (ScaleType) this.val$view.getTag(R.id.save_scale_type);
            this.val$view.setScaleType(scaleType);
            this.val$view.setTag(R.id.save_scale_type, null);
            if (scaleType == ScaleType.MATRIX) {
                this.val$view.setImageMatrix((Matrix) this.val$view.getTag(R.id.save_image_matrix));
                this.val$view.setTag(R.id.save_image_matrix, null);
            }
            animation.removeListener(this);
        }
    }

    ImageViewUtilsApi14() {
    }

    public void startAnimateTransform(ImageView view) {
        ScaleType scaleType = view.getScaleType();
        view.setTag(R.id.save_scale_type, scaleType);
        if (scaleType == ScaleType.MATRIX) {
            view.setTag(R.id.save_image_matrix, view.getImageMatrix());
        } else {
            view.setScaleType(ScaleType.MATRIX);
        }
        view.setImageMatrix(MatrixUtils.IDENTITY_MATRIX);
    }

    public void animateTransform(ImageView view, Matrix matrix) {
        view.setImageMatrix(matrix);
    }

    public void reserveEndAnimateTransform(ImageView view, Animator animator) {
        animator.addListener(new AnonymousClass_1(view));
    }
}
