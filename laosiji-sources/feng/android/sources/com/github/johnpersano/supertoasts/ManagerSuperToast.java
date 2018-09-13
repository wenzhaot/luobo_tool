package com.github.johnpersano.supertoasts;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ManagerSuperToast extends Handler {
    private static final String TAG = "ManagerSuperToast";
    private static ManagerSuperToast mManagerSuperToast;
    private final Queue<SuperToast> mQueue = new LinkedBlockingQueue();

    private static final class Messages {
        private static final int ADD_SUPERTOAST = 4281172;
        private static final int DISPLAY_SUPERTOAST = 4477780;
        private static final int REMOVE_SUPERTOAST = 5395284;

        private Messages() {
        }
    }

    private ManagerSuperToast() {
    }

    protected static synchronized ManagerSuperToast getInstance() {
        ManagerSuperToast managerSuperToast;
        synchronized (ManagerSuperToast.class) {
            if (mManagerSuperToast != null) {
                managerSuperToast = mManagerSuperToast;
            } else {
                mManagerSuperToast = new ManagerSuperToast();
                managerSuperToast = mManagerSuperToast;
            }
        }
        return managerSuperToast;
    }

    protected void add(SuperToast superToast) {
        this.mQueue.add(superToast);
        showNextSuperToast();
    }

    private void showNextSuperToast() {
        if (!this.mQueue.isEmpty()) {
            SuperToast superToast = (SuperToast) this.mQueue.peek();
            if (superToast.isShowing()) {
                sendMessageDelayed(superToast, 4477780, getDuration(superToast));
                return;
            }
            Message message = obtainMessage(4281172);
            message.obj = superToast;
            sendMessage(message);
        }
    }

    private void sendMessageDelayed(SuperToast superToast, int messageId, long delay) {
        Message message = obtainMessage(messageId);
        message.obj = superToast;
        sendMessageDelayed(message, delay);
    }

    private long getDuration(SuperToast superToast) {
        return ((long) superToast.getDuration()) + 1000;
    }

    public void handleMessage(Message message) {
        SuperToast superToast = message.obj;
        switch (message.what) {
            case 4281172:
                displaySuperToast(superToast);
                return;
            case 4477780:
                showNextSuperToast();
                return;
            case 5395284:
                removeSuperToast(superToast);
                return;
            default:
                super.handleMessage(message);
                return;
        }
    }

    private void displaySuperToast(SuperToast superToast) {
        if (!superToast.isShowing()) {
            WindowManager windowManager = superToast.getWindowManager();
            View toastView = superToast.getView();
            LayoutParams params = superToast.getWindowManagerParams();
            if (windowManager != null) {
                windowManager.addView(toastView, params);
            }
            sendMessageDelayed(superToast, 5395284, (long) (superToast.getDuration() + 500));
        }
    }

    protected void removeSuperToast(SuperToast superToast) {
        WindowManager windowManager = superToast.getWindowManager();
        View toastView = superToast.getView();
        if (windowManager != null) {
            this.mQueue.poll();
            windowManager.removeView(toastView);
            sendMessageDelayed(superToast, 4477780, 500);
            if (superToast.getOnDismissListener() != null) {
                superToast.getOnDismissListener().onDismiss(superToast.getView());
            }
        }
    }

    protected void cancelAllSuperToasts() {
        removeMessages(4281172);
        removeMessages(4477780);
        removeMessages(5395284);
        for (SuperToast superToast : this.mQueue) {
            if (superToast.isShowing()) {
                superToast.getWindowManager().removeView(superToast.getView());
            }
        }
        this.mQueue.clear();
    }
}
