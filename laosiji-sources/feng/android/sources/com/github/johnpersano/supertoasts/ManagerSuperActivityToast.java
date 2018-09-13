package com.github.johnpersano.supertoasts;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import com.github.johnpersano.supertoasts.SuperToast.Animations;
import java.util.Iterator;
import java.util.LinkedList;

class ManagerSuperActivityToast extends Handler {
    private static final String TAG = "ManagerSuperActivityToast";
    private static ManagerSuperActivityToast mManagerSuperActivityToast;
    private final LinkedList<SuperActivityToast> mList = new LinkedList();

    private static final class Messages {
        private static final int DISPLAY = 1146306900;
        private static final int REMOVE = 1381187924;

        private Messages() {
        }
    }

    private ManagerSuperActivityToast() {
    }

    protected static synchronized ManagerSuperActivityToast getInstance() {
        ManagerSuperActivityToast managerSuperActivityToast;
        synchronized (ManagerSuperActivityToast.class) {
            if (mManagerSuperActivityToast != null) {
                managerSuperActivityToast = mManagerSuperActivityToast;
            } else {
                mManagerSuperActivityToast = new ManagerSuperActivityToast();
                managerSuperActivityToast = mManagerSuperActivityToast;
            }
        }
        return managerSuperActivityToast;
    }

    void add(SuperActivityToast superActivityToast) {
        this.mList.add(superActivityToast);
        showNextSuperToast();
    }

    private void showNextSuperToast() {
        SuperActivityToast superActivityToast = (SuperActivityToast) this.mList.peek();
        if (!this.mList.isEmpty() && superActivityToast.getActivity() != null && !superActivityToast.isShowing()) {
            Message message = obtainMessage(1146306900);
            message.obj = superActivityToast;
            sendMessage(message);
        }
    }

    public void handleMessage(Message message) {
        SuperActivityToast superActivityToast = message.obj;
        switch (message.what) {
            case 1146306900:
                displaySuperToast(superActivityToast);
                return;
            case 1381187924:
                removeSuperToast(superActivityToast);
                return;
            default:
                super.handleMessage(message);
                return;
        }
    }

    private void displaySuperToast(SuperActivityToast superActivityToast) {
        if (!superActivityToast.isShowing()) {
            ViewGroup viewGroup = superActivityToast.getViewGroup();
            View toastView = superActivityToast.getView();
            if (viewGroup != null) {
                try {
                    viewGroup.addView(toastView);
                    if (!superActivityToast.getShowImmediate()) {
                        toastView.startAnimation(getShowAnimation(superActivityToast));
                    }
                } catch (IllegalStateException e) {
                    cancelAllSuperActivityToastsForActivity(superActivityToast.getActivity());
                }
            }
            if (!superActivityToast.isIndeterminate()) {
                Message message = obtainMessage(1381187924);
                message.obj = superActivityToast;
                sendMessageDelayed(message, ((long) superActivityToast.getDuration()) + getShowAnimation(superActivityToast).getDuration());
            }
        }
    }

    void removeSuperToast(final SuperActivityToast superActivityToast) {
        if (superActivityToast.isShowing()) {
            removeMessages(1381187924, superActivityToast);
            ViewGroup viewGroup = superActivityToast.getViewGroup();
            View toastView = superActivityToast.getView();
            if (viewGroup != null) {
                Animation animation = getDismissAnimation(superActivityToast);
                animation.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        if (superActivityToast.getOnDismissWrapper() != null) {
                            superActivityToast.getOnDismissWrapper().onDismiss(superActivityToast.getView());
                        }
                        ManagerSuperActivityToast.this.showNextSuperToast();
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                toastView.startAnimation(animation);
                viewGroup.removeView(toastView);
                this.mList.poll();
                return;
            }
            return;
        }
        this.mList.remove(superActivityToast);
    }

    void cancelAllSuperActivityToasts() {
        removeMessages(1146306900);
        removeMessages(1381187924);
        Iterator i$ = this.mList.iterator();
        while (i$.hasNext()) {
            SuperActivityToast superActivityToast = (SuperActivityToast) i$.next();
            if (superActivityToast.isShowing()) {
                superActivityToast.getViewGroup().removeView(superActivityToast.getView());
                superActivityToast.getViewGroup().invalidate();
            }
        }
        this.mList.clear();
    }

    void cancelAllSuperActivityToastsForActivity(Activity activity) {
        Iterator<SuperActivityToast> superActivityToastIterator = this.mList.iterator();
        while (superActivityToastIterator.hasNext()) {
            SuperActivityToast superActivityToast = (SuperActivityToast) superActivityToastIterator.next();
            if (superActivityToast.getActivity() != null && superActivityToast.getActivity().equals(activity)) {
                if (superActivityToast.isShowing()) {
                    superActivityToast.getViewGroup().removeView(superActivityToast.getView());
                }
                removeMessages(1146306900, superActivityToast);
                removeMessages(1381187924, superActivityToast);
                superActivityToastIterator.remove();
            }
        }
    }

    LinkedList<SuperActivityToast> getList() {
        return this.mList;
    }

    private Animation getShowAnimation(SuperActivityToast superActivityToast) {
        AlphaAnimation alphaAnimation;
        AnimationSet animationSet;
        if (superActivityToast.getAnimations() == Animations.FLYIN) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.75f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (superActivityToast.getAnimations() == Animations.SCALE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, 1, 0.5f, 1, 0.5f);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (superActivityToast.getAnimations() == Animations.POPUP) {
            TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.1f, 1, 0.0f);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation2);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else {
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setInterpolator(new DecelerateInterpolator());
            return animation;
        }
    }

    private Animation getDismissAnimation(SuperActivityToast superActivityToast) {
        AlphaAnimation alphaAnimation;
        AnimationSet animationSet;
        if (superActivityToast.getAnimations() == Animations.FLYIN) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.75f, 1, 0.0f, 1, 0.0f);
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new AccelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (superActivityToast.getAnimations() == Animations.SCALE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, 1, 0.5f, 1, 0.5f);
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else if (superActivityToast.getAnimations() == Animations.POPUP) {
            TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 0.1f);
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation2);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(250);
            return animationSet;
        } else {
            Animation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation2.setDuration(500);
            alphaAnimation2.setInterpolator(new AccelerateInterpolator());
            return alphaAnimation2;
        }
    }
}
