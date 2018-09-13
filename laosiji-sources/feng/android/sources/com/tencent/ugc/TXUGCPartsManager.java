package com.tencent.ugc;

import android.os.AsyncTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TXUGCPartsManager {
    private ArrayList<IPartsManagerListener> iPartsManagerObservers = new ArrayList();
    private int mDuration;
    private CopyOnWriteArrayList<PartInfo> mPartsList = new CopyOnWriteArrayList();

    public interface IPartsManagerListener {
        void onDeleteAllParts();

        void onDeleteLastPart();
    }

    public synchronized void setPartsManagerObserver(IPartsManagerListener iPartsManagerListener) {
        if (iPartsManagerListener != null) {
            if (!this.iPartsManagerObservers.contains(iPartsManagerListener)) {
                this.iPartsManagerObservers.add(iPartsManagerListener);
            }
        }
    }

    public synchronized void removePartsManagerObserver(IPartsManagerListener iPartsManagerListener) {
        if (iPartsManagerListener != null) {
            this.iPartsManagerObservers.remove(iPartsManagerListener);
        }
    }

    public void addClipInfo(PartInfo partInfo) {
        this.mPartsList.add(partInfo);
        this.mDuration = (int) (((long) this.mDuration) + partInfo.getDuration());
    }

    public int getDuration() {
        return this.mDuration;
    }

    public List<String> getPartsPathList() {
        List arrayList = new ArrayList();
        Iterator it = this.mPartsList.iterator();
        while (it.hasNext()) {
            arrayList.add(((PartInfo) it.next()).getPath());
        }
        return arrayList;
    }

    public void deleteLastPart() {
        if (this.mPartsList.size() != 0) {
            PartInfo partInfo = (PartInfo) this.mPartsList.remove(this.mPartsList.size() - 1);
            this.mDuration = (int) (((long) this.mDuration) - partInfo.getDuration());
            deleteFile(partInfo.getPath());
            callbackDeleteLastPart();
        }
    }

    public void deletePart(int i) {
        if (i > 0 && this.mPartsList.size() != 0) {
            PartInfo partInfo = (PartInfo) this.mPartsList.remove(i - 1);
            this.mDuration = (int) (((long) this.mDuration) - partInfo.getDuration());
            deleteFile(partInfo.getPath());
        }
    }

    public void deleteAllParts() {
        Iterator it = this.mPartsList.iterator();
        while (it.hasNext()) {
            deleteFile(((PartInfo) it.next()).getPath());
        }
        this.mPartsList.clear();
        this.mDuration = 0;
        callbackDeleteAllParts();
    }

    private void callbackDeleteLastPart() {
        synchronized (this) {
            Iterator it = this.iPartsManagerObservers.iterator();
            while (it.hasNext()) {
                ((IPartsManagerListener) it.next()).onDeleteLastPart();
            }
        }
    }

    private void callbackDeleteAllParts() {
        synchronized (this) {
            Iterator it = this.iPartsManagerObservers.iterator();
            while (it.hasNext()) {
                ((IPartsManagerListener) it.next()).onDeleteAllParts();
            }
        }
    }

    private void deleteFile(final String str) {
        new AsyncTask() {
            protected Object doInBackground(Object[] objArr) {
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Object[0]);
    }
}
