package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.android.volley.DefaultRetryPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private static TimeInterpolator sDefaultInterpolator;
    ArrayList<ViewHolder> mAddAnimations;
    ArrayList<ArrayList<ViewHolder>> mAdditionsList;
    ArrayList<ViewHolder> mChangeAnimations;
    ArrayList<ArrayList<ChangeInfo>> mChangesList;
    ArrayList<ViewHolder> mMoveAnimations;
    ArrayList<ArrayList<MoveInfo>> mMovesList;
    private ArrayList<ViewHolder> mPendingAdditions;
    private ArrayList<ChangeInfo> mPendingChanges;
    private ArrayList<MoveInfo> mPendingMoves;
    private ArrayList<ViewHolder> mPendingRemovals;
    ArrayList<ViewHolder> mRemoveAnimations;

    class AnonymousClass_1 implements Runnable {
        final /* synthetic */ ArrayList val$moves;

        AnonymousClass_1(ArrayList arrayList) {
            this.val$moves = arrayList;
        }

        public void run() {
            Iterator it = this.val$moves.iterator();
            while (it.hasNext()) {
                MoveInfo moveInfo = (MoveInfo) it.next();
                DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
            }
            this.val$moves.clear();
            DefaultItemAnimator.this.mMovesList.remove(this.val$moves);
        }
    }

    class AnonymousClass_2 implements Runnable {
        final /* synthetic */ ArrayList val$changes;

        AnonymousClass_2(ArrayList arrayList) {
            this.val$changes = arrayList;
        }

        public void run() {
            Iterator it = this.val$changes.iterator();
            while (it.hasNext()) {
                DefaultItemAnimator.this.animateChangeImpl((ChangeInfo) it.next());
            }
            this.val$changes.clear();
            DefaultItemAnimator.this.mChangesList.remove(this.val$changes);
        }
    }

    class AnonymousClass_3 implements Runnable {
        final /* synthetic */ ArrayList val$additions;

        AnonymousClass_3(ArrayList arrayList) {
            this.val$additions = arrayList;
        }

        public void run() {
            Iterator it = this.val$additions.iterator();
            while (it.hasNext()) {
                DefaultItemAnimator.this.animateAddImpl((ViewHolder) it.next());
            }
            this.val$additions.clear();
            DefaultItemAnimator.this.mAdditionsList.remove(this.val$additions);
        }
    }

    class AnonymousClass_4 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ ViewHolder val$holder;
        final /* synthetic */ View val$view;

        AnonymousClass_4(ViewHolder viewHolder, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.val$holder = viewHolder;
            this.val$animation = viewPropertyAnimator;
            this.val$view = view;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchRemoveStarting(this.val$holder);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$animation.setListener(null);
            this.val$view.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            DefaultItemAnimator.this.dispatchRemoveFinished(this.val$holder);
            DefaultItemAnimator.this.mRemoveAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    class AnonymousClass_5 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ ViewHolder val$holder;
        final /* synthetic */ View val$view;

        AnonymousClass_5(ViewHolder viewHolder, View view, ViewPropertyAnimator viewPropertyAnimator) {
            this.val$holder = viewHolder;
            this.val$view = view;
            this.val$animation = viewPropertyAnimator;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchAddStarting(this.val$holder);
        }

        public void onAnimationCancel(Animator animator) {
            this.val$view.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$animation.setListener(null);
            DefaultItemAnimator.this.dispatchAddFinished(this.val$holder);
            DefaultItemAnimator.this.mAddAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    class AnonymousClass_6 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ int val$deltaX;
        final /* synthetic */ int val$deltaY;
        final /* synthetic */ ViewHolder val$holder;
        final /* synthetic */ View val$view;

        AnonymousClass_6(ViewHolder viewHolder, int i, View view, int i2, ViewPropertyAnimator viewPropertyAnimator) {
            this.val$holder = viewHolder;
            this.val$deltaX = i;
            this.val$view = view;
            this.val$deltaY = i2;
            this.val$animation = viewPropertyAnimator;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchMoveStarting(this.val$holder);
        }

        public void onAnimationCancel(Animator animator) {
            if (this.val$deltaX != 0) {
                this.val$view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            }
            if (this.val$deltaY != 0) {
                this.val$view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            }
        }

        public void onAnimationEnd(Animator animator) {
            this.val$animation.setListener(null);
            DefaultItemAnimator.this.dispatchMoveFinished(this.val$holder);
            DefaultItemAnimator.this.mMoveAnimations.remove(this.val$holder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    class AnonymousClass_7 extends AnimatorListenerAdapter {
        final /* synthetic */ ChangeInfo val$changeInfo;
        final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
        final /* synthetic */ View val$view;

        AnonymousClass_7(ChangeInfo changeInfo, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.val$changeInfo = changeInfo;
            this.val$oldViewAnim = viewPropertyAnimator;
            this.val$view = view;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.oldHolder, true);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$oldViewAnim.setListener(null);
            this.val$view.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.val$view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            this.val$view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.oldHolder, true);
            DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.oldHolder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    class AnonymousClass_8 extends AnimatorListenerAdapter {
        final /* synthetic */ ChangeInfo val$changeInfo;
        final /* synthetic */ View val$newView;
        final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;

        AnonymousClass_8(ChangeInfo changeInfo, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.val$changeInfo = changeInfo;
            this.val$newViewAnimation = viewPropertyAnimator;
            this.val$newView = view;
        }

        public void onAnimationStart(Animator animator) {
            DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.newHolder, false);
        }

        public void onAnimationEnd(Animator animator) {
            this.val$newViewAnimation.setListener(null);
            this.val$newView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.val$newView.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            this.val$newView.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.newHolder, false);
            DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.newHolder);
            DefaultItemAnimator.this.dispatchFinishedWhenDone();
        }
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public ViewHolder newHolder;
        public ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    public DefaultItemAnimator() {
        this.mPendingRemovals = new ArrayList();
        this.mPendingAdditions = new ArrayList();
        this.mPendingMoves = new ArrayList();
        this.mPendingChanges = new ArrayList();
        this.mAdditionsList = new ArrayList();
        this.mMovesList = new ArrayList();
        this.mChangesList = new ArrayList();
        this.mAddAnimations = new ArrayList();
        this.mMoveAnimations = new ArrayList();
        this.mRemoveAnimations = new ArrayList();
        this.mChangeAnimations = new ArrayList();
    }

    public void runPendingAnimations() {
        boolean removalsPending = !this.mPendingRemovals.isEmpty();
        boolean movesPending = !this.mPendingMoves.isEmpty();
        boolean changesPending = !this.mPendingChanges.isEmpty();
        boolean additionsPending = !this.mPendingAdditions.isEmpty();
        if (removalsPending || movesPending || additionsPending || changesPending) {
            Iterator it = this.mPendingRemovals.iterator();
            while (it.hasNext()) {
                animateRemoveImpl((ViewHolder) it.next());
            }
            this.mPendingRemovals.clear();
            if (movesPending) {
                ArrayList<MoveInfo> moves = new ArrayList();
                moves.addAll(this.mPendingMoves);
                this.mMovesList.add(moves);
                this.mPendingMoves.clear();
                Runnable mover = new AnonymousClass_1(moves);
                if (removalsPending) {
                    ViewCompat.postOnAnimationDelayed(((MoveInfo) moves.get(0)).holder.itemView, mover, getRemoveDuration());
                } else {
                    mover.run();
                }
            }
            if (changesPending) {
                ArrayList<ChangeInfo> changes = new ArrayList();
                changes.addAll(this.mPendingChanges);
                this.mChangesList.add(changes);
                this.mPendingChanges.clear();
                Runnable changer = new AnonymousClass_2(changes);
                if (removalsPending) {
                    ViewCompat.postOnAnimationDelayed(((ChangeInfo) changes.get(0)).oldHolder.itemView, changer, getRemoveDuration());
                } else {
                    changer.run();
                }
            }
            if (additionsPending) {
                ArrayList<ViewHolder> additions = new ArrayList();
                additions.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(additions);
                this.mPendingAdditions.clear();
                Runnable adder = new AnonymousClass_3(additions);
                if (removalsPending || movesPending || changesPending) {
                    ViewCompat.postOnAnimationDelayed(((ViewHolder) additions.get(0)).itemView, adder, (removalsPending ? getRemoveDuration() : 0) + Math.max(movesPending ? getMoveDuration() : 0, changesPending ? getChangeDuration() : 0));
                } else {
                    adder.run();
                }
            }
        }
    }

    public boolean animateRemove(ViewHolder holder) {
        resetAnimation(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(ViewHolder holder) {
        View view = holder.itemView;
        ViewPropertyAnimator animation = view.animate();
        this.mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration()).alpha(AutoScrollHelper.RELATIVE_UNSPECIFIED).setListener(new AnonymousClass_4(holder, animation, view)).start();
    }

    public boolean animateAdd(ViewHolder holder) {
        resetAnimation(holder);
        holder.itemView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mPendingAdditions.add(holder);
        return true;
    }

    void animateAddImpl(ViewHolder holder) {
        View view = holder.itemView;
        ViewPropertyAnimator animation = view.animate();
        this.mAddAnimations.add(holder);
        animation.alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).setDuration(getAddDuration()).setListener(new AnonymousClass_5(holder, view, animation)).start();
    }

    public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        fromX += (int) holder.itemView.getTranslationX();
        fromY += (int) holder.itemView.getTranslationY();
        resetAnimation(holder);
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            view.setTranslationX((float) (-deltaX));
        }
        if (deltaY != 0) {
            view.setTranslationY((float) (-deltaY));
        }
        this.mPendingMoves.add(new MoveInfo(holder, fromX, fromY, toX, toY));
        return true;
    }

    void animateMoveImpl(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX != 0) {
            view.animate().translationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }
        if (deltaY != 0) {
            view.animate().translationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }
        ViewPropertyAnimator animation = view.animate();
        this.mMoveAnimations.add(holder);
        animation.setDuration(getMoveDuration()).setListener(new AnonymousClass_6(holder, deltaX, view, deltaY, animation)).start();
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if (oldHolder == newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY);
        }
        float prevTranslationX = oldHolder.itemView.getTranslationX();
        float prevTranslationY = oldHolder.itemView.getTranslationY();
        float prevAlpha = oldHolder.itemView.getAlpha();
        resetAnimation(oldHolder);
        int deltaX = (int) (((float) (toX - fromX)) - prevTranslationX);
        int deltaY = (int) (((float) (toY - fromY)) - prevTranslationY);
        oldHolder.itemView.setTranslationX(prevTranslationX);
        oldHolder.itemView.setTranslationY(prevTranslationY);
        oldHolder.itemView.setAlpha(prevAlpha);
        if (newHolder != null) {
            resetAnimation(newHolder);
            newHolder.itemView.setTranslationX((float) (-deltaX));
            newHolder.itemView.setTranslationY((float) (-deltaY));
            newHolder.itemView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }
        this.mPendingChanges.add(new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }

    void animateChangeImpl(ChangeInfo changeInfo) {
        View newView;
        ViewHolder holder = changeInfo.oldHolder;
        View view = holder == null ? null : holder.itemView;
        ViewHolder newHolder = changeInfo.newHolder;
        if (newHolder != null) {
            newView = newHolder.itemView;
        } else {
            newView = null;
        }
        if (view != null) {
            ViewPropertyAnimator oldViewAnim = view.animate().setDuration(getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            oldViewAnim.translationX((float) (changeInfo.toX - changeInfo.fromX));
            oldViewAnim.translationY((float) (changeInfo.toY - changeInfo.fromY));
            oldViewAnim.alpha(AutoScrollHelper.RELATIVE_UNSPECIFIED).setListener(new AnonymousClass_7(changeInfo, oldViewAnim, view)).start();
        }
        if (newView != null) {
            ViewPropertyAnimator newViewAnimation = newView.animate();
            this.mChangeAnimations.add(changeInfo.newHolder);
            newViewAnimation.translationX(AutoScrollHelper.RELATIVE_UNSPECIFIED).translationY(AutoScrollHelper.RELATIVE_UNSPECIFIED).setDuration(getChangeDuration()).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).setListener(new AnonymousClass_8(changeInfo, newViewAnimation, newView)).start();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> infoList, ViewHolder item) {
        for (int i = infoList.size() - 1; i >= 0; i--) {
            ChangeInfo changeInfo = (ChangeInfo) infoList.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                infoList.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, ViewHolder item) {
        boolean oldItem = false;
        if (changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != item) {
            return false;
        } else {
            changeInfo.oldHolder = null;
            oldItem = true;
        }
        item.itemView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        item.itemView.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        item.itemView.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(ViewHolder item) {
        int i;
        View view = item.itemView;
        view.animate().cancel();
        for (i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            if (((MoveInfo) this.mPendingMoves.get(i)).holder == item) {
                view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                dispatchMoveFinished(item);
                this.mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            view.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            dispatchRemoveFinished(item);
        }
        if (this.mPendingAdditions.remove(item)) {
            view.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            dispatchAddFinished(item);
        }
        for (i = this.mChangesList.size() - 1; i >= 0; i--) {
            ArrayList<ChangeInfo> changes = (ArrayList) this.mChangesList.get(i);
            endChangeAnimation(changes, item);
            if (changes.isEmpty()) {
                this.mChangesList.remove(i);
            }
        }
        for (i = this.mMovesList.size() - 1; i >= 0; i--) {
            ArrayList<MoveInfo> moves = (ArrayList) this.mMovesList.get(i);
            int j = moves.size() - 1;
            while (j >= 0) {
                if (((MoveInfo) moves.get(j)).holder == item) {
                    view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    dispatchMoveFinished(item);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(i);
                    }
                } else {
                    j--;
                }
            }
        }
        for (i = this.mAdditionsList.size() - 1; i >= 0; i--) {
            ArrayList<ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i);
            if (additions.remove(item)) {
                view.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                dispatchAddFinished(item);
                if (additions.isEmpty()) {
                    this.mAdditionsList.remove(i);
                }
            }
        }
        if (this.mRemoveAnimations.remove(item)) {
        }
        if (this.mAddAnimations.remove(item)) {
        }
        if (this.mChangeAnimations.remove(item)) {
        }
        if (this.mMoveAnimations.remove(item)) {
            dispatchFinishedWhenDone();
        } else {
            dispatchFinishedWhenDone();
        }
    }

    private void resetAnimation(ViewHolder holder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        holder.itemView.animate().setInterpolator(sDefaultInterpolator);
        endAnimation(holder);
    }

    public boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() {
        int i;
        for (i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            MoveInfo item = (MoveInfo) this.mPendingMoves.get(i);
            View view = item.holder.itemView;
            view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            dispatchMoveFinished(item.holder);
            this.mPendingMoves.remove(i);
        }
        for (i = this.mPendingRemovals.size() - 1; i >= 0; i--) {
            dispatchRemoveFinished((ViewHolder) this.mPendingRemovals.get(i));
            this.mPendingRemovals.remove(i);
        }
        for (i = this.mPendingAdditions.size() - 1; i >= 0; i--) {
            ViewHolder item2 = (ViewHolder) this.mPendingAdditions.get(i);
            item2.itemView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            dispatchAddFinished(item2);
            this.mPendingAdditions.remove(i);
        }
        for (i = this.mPendingChanges.size() - 1; i >= 0; i--) {
            endChangeAnimationIfNecessary((ChangeInfo) this.mPendingChanges.get(i));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int j;
            for (i = this.mMovesList.size() - 1; i >= 0; i--) {
                ArrayList<MoveInfo> moves = (ArrayList) this.mMovesList.get(i);
                for (j = moves.size() - 1; j >= 0; j--) {
                    MoveInfo moveInfo = (MoveInfo) moves.get(j);
                    view = moveInfo.holder.itemView;
                    view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    dispatchMoveFinished(moveInfo.holder);
                    moves.remove(j);
                    if (moves.isEmpty()) {
                        this.mMovesList.remove(moves);
                    }
                }
            }
            for (i = this.mAdditionsList.size() - 1; i >= 0; i--) {
                ArrayList<ViewHolder> additions = (ArrayList) this.mAdditionsList.get(i);
                for (j = additions.size() - 1; j >= 0; j--) {
                    item2 = (ViewHolder) additions.get(j);
                    item2.itemView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    dispatchAddFinished(item2);
                    additions.remove(j);
                    if (additions.isEmpty()) {
                        this.mAdditionsList.remove(additions);
                    }
                }
            }
            for (i = this.mChangesList.size() - 1; i >= 0; i--) {
                ArrayList<ChangeInfo> changes = (ArrayList) this.mChangesList.get(i);
                for (j = changes.size() - 1; j >= 0; j--) {
                    endChangeAnimationIfNecessary((ChangeInfo) changes.get(j));
                    if (changes.isEmpty()) {
                        this.mChangesList.remove(changes);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    void cancelAll(List<ViewHolder> viewHolders) {
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ((ViewHolder) viewHolders.get(i)).itemView.animate().cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> payloads) {
        return !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads);
    }
}
