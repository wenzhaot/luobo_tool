package com.github.johnpersano.supertoasts;

import java.util.Iterator;
import java.util.LinkedList;

class ManagerSuperCardToast {
    private static final String TAG = "Manager SuperCardToast";
    private static ManagerSuperCardToast mManagerSuperCardToast;
    private final LinkedList<SuperCardToast> mList = new LinkedList();

    private ManagerSuperCardToast() {
    }

    protected static synchronized ManagerSuperCardToast getInstance() {
        ManagerSuperCardToast managerSuperCardToast;
        synchronized (ManagerSuperCardToast.class) {
            if (mManagerSuperCardToast != null) {
                managerSuperCardToast = mManagerSuperCardToast;
            } else {
                mManagerSuperCardToast = new ManagerSuperCardToast();
                managerSuperCardToast = mManagerSuperCardToast;
            }
        }
        return managerSuperCardToast;
    }

    void add(SuperCardToast superCardToast) {
        this.mList.add(superCardToast);
    }

    void remove(SuperCardToast superCardToast) {
        this.mList.remove(superCardToast);
    }

    void cancelAllSuperActivityToasts() {
        Iterator i$ = this.mList.iterator();
        while (i$.hasNext()) {
            SuperCardToast superCardToast = (SuperCardToast) i$.next();
            if (superCardToast.isShowing()) {
                superCardToast.getViewGroup().removeView(superCardToast.getView());
                superCardToast.getViewGroup().invalidate();
            }
        }
        this.mList.clear();
    }

    LinkedList<SuperCardToast> getList() {
        return this.mList;
    }
}
