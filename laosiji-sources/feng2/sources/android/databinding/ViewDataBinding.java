package android.databinding;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.databinding.CallbackRegistry.NotifierCallback;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableList.OnListChangedCallback;
import android.databinding.ObservableMap.OnMapChangedCallback;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import com.android.databinding.library.R;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public abstract class ViewDataBinding extends BaseObservable {
    private static final int BINDING_NUMBER_START = BINDING_TAG_PREFIX.length();
    public static final String BINDING_TAG_PREFIX = "binding_";
    private static final CreateWeakListener CREATE_LIST_LISTENER = new CreateWeakListener() {
        public WeakListener create(ViewDataBinding viewDataBinding, int localFieldId) {
            return new WeakListListener(viewDataBinding, localFieldId).getListener();
        }
    };
    private static final CreateWeakListener CREATE_MAP_LISTENER = new CreateWeakListener() {
        public WeakListener create(ViewDataBinding viewDataBinding, int localFieldId) {
            return new WeakMapListener(viewDataBinding, localFieldId).getListener();
        }
    };
    private static final CreateWeakListener CREATE_PROPERTY_LISTENER = new CreateWeakListener() {
        public WeakListener create(ViewDataBinding viewDataBinding, int localFieldId) {
            return new WeakPropertyListener(viewDataBinding, localFieldId).getListener();
        }
    };
    private static final int HALTED = 2;
    private static final int REBIND = 1;
    private static final NotifierCallback<OnRebindCallback, ViewDataBinding, Void> REBIND_NOTIFIER = new NotifierCallback<OnRebindCallback, ViewDataBinding, Void>() {
        public void onNotifyCallback(OnRebindCallback callback, ViewDataBinding sender, int mode, Void arg2) {
            switch (mode) {
                case 1:
                    if (!callback.onPreBind(sender)) {
                        sender.mRebindHalted = true;
                        return;
                    }
                    return;
                case 2:
                    callback.onCanceled(sender);
                    return;
                case 3:
                    callback.onBound(sender);
                    return;
                default:
                    return;
            }
        }
    };
    private static final int REBOUND = 3;
    private static final OnAttachStateChangeListener ROOT_REATTACHED_LISTENER;
    static int SDK_INT = VERSION.SDK_INT;
    private static final boolean USE_CHOREOGRAPHER;
    private static final boolean USE_TAG_ID = (DataBinderMapper.TARGET_MIN_SDK >= 14);
    private static final ReferenceQueue<ViewDataBinding> sReferenceQueue = new ReferenceQueue();
    protected final DataBindingComponent mBindingComponent;
    private Choreographer mChoreographer;
    private ViewDataBinding mContainingBinding;
    private final FrameCallback mFrameCallback;
    private boolean mIsExecutingPendingBindings;
    private WeakListener[] mLocalFieldObservers;
    private boolean mPendingRebind = false;
    private CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> mRebindCallbacks;
    private boolean mRebindHalted = false;
    private final Runnable mRebindRunnable = new Runnable() {
        public void run() {
            synchronized (this) {
                ViewDataBinding.this.mPendingRebind = false;
            }
            ViewDataBinding.processReferenceQueue();
            if (VERSION.SDK_INT < 19 || ViewDataBinding.this.mRoot.isAttachedToWindow()) {
                ViewDataBinding.this.executePendingBindings();
                return;
            }
            ViewDataBinding.this.mRoot.removeOnAttachStateChangeListener(ViewDataBinding.ROOT_REATTACHED_LISTENER);
            ViewDataBinding.this.mRoot.addOnAttachStateChangeListener(ViewDataBinding.ROOT_REATTACHED_LISTENER);
        }
    };
    private final View mRoot;
    private Handler mUIThreadHandler;

    private interface CreateWeakListener {
        WeakListener create(ViewDataBinding viewDataBinding, int i);
    }

    protected static class IncludedLayouts {
        public final int[][] indexes;
        public final int[][] layoutIds;
        public final String[][] layouts;

        public IncludedLayouts(int bindingCount) {
            this.layouts = new String[bindingCount][];
            this.indexes = new int[bindingCount][];
            this.layoutIds = new int[bindingCount][];
        }

        public void setIncludes(int index, String[] layouts, int[] indexes, int[] layoutIds) {
            this.layouts[index] = layouts;
            this.indexes[index] = indexes;
            this.layoutIds[index] = layoutIds;
        }
    }

    private interface ObservableReference<T> {
        void addListener(T t);

        WeakListener<T> getListener();

        void removeListener(T t);
    }

    protected static abstract class PropertyChangedInverseListener extends OnPropertyChangedCallback implements InverseBindingListener {
        final int mPropertyId;

        public PropertyChangedInverseListener(int propertyId) {
            this.mPropertyId = propertyId;
        }

        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == this.mPropertyId || propertyId == 0) {
                onChange();
            }
        }
    }

    private static class WeakListListener extends OnListChangedCallback implements ObservableReference<ObservableList> {
        final WeakListener<ObservableList> mListener;

        public WeakListListener(ViewDataBinding binder, int localFieldId) {
            this.mListener = new WeakListener(binder, localFieldId, this);
        }

        public WeakListener<ObservableList> getListener() {
            return this.mListener;
        }

        public void addListener(ObservableList target) {
            target.addOnListChangedCallback(this);
        }

        public void removeListener(ObservableList target) {
            target.removeOnListChangedCallback(this);
        }

        public void onChanged(ObservableList sender) {
            ViewDataBinding binder = this.mListener.getBinder();
            if (binder != null) {
                ObservableList target = (ObservableList) this.mListener.getTarget();
                if (target == sender) {
                    binder.handleFieldChange(this.mListener.mLocalFieldId, target, 0);
                }
            }
        }

        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            onChanged(sender);
        }

        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            onChanged(sender);
        }

        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            onChanged(sender);
        }

        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            onChanged(sender);
        }
    }

    private static class WeakListener<T> extends WeakReference<ViewDataBinding> {
        protected final int mLocalFieldId;
        private final ObservableReference<T> mObservable;
        private T mTarget;

        public WeakListener(ViewDataBinding binder, int localFieldId, ObservableReference<T> observable) {
            super(binder, ViewDataBinding.sReferenceQueue);
            this.mLocalFieldId = localFieldId;
            this.mObservable = observable;
        }

        public void setTarget(T object) {
            unregister();
            this.mTarget = object;
            if (this.mTarget != null) {
                this.mObservable.addListener(this.mTarget);
            }
        }

        public boolean unregister() {
            boolean unregistered = false;
            if (this.mTarget != null) {
                this.mObservable.removeListener(this.mTarget);
                unregistered = true;
            }
            this.mTarget = null;
            return unregistered;
        }

        public T getTarget() {
            return this.mTarget;
        }

        protected ViewDataBinding getBinder() {
            ViewDataBinding binder = (ViewDataBinding) get();
            if (binder == null) {
                unregister();
            }
            return binder;
        }
    }

    private static class WeakMapListener extends OnMapChangedCallback implements ObservableReference<ObservableMap> {
        final WeakListener<ObservableMap> mListener;

        public WeakMapListener(ViewDataBinding binder, int localFieldId) {
            this.mListener = new WeakListener(binder, localFieldId, this);
        }

        public WeakListener<ObservableMap> getListener() {
            return this.mListener;
        }

        public void addListener(ObservableMap target) {
            target.addOnMapChangedCallback(this);
        }

        public void removeListener(ObservableMap target) {
            target.removeOnMapChangedCallback(this);
        }

        public void onMapChanged(ObservableMap sender, Object key) {
            ViewDataBinding binder = this.mListener.getBinder();
            if (binder != null && sender == this.mListener.getTarget()) {
                binder.handleFieldChange(this.mListener.mLocalFieldId, sender, 0);
            }
        }
    }

    private static class WeakPropertyListener extends OnPropertyChangedCallback implements ObservableReference<Observable> {
        final WeakListener<Observable> mListener;

        public WeakPropertyListener(ViewDataBinding binder, int localFieldId) {
            this.mListener = new WeakListener(binder, localFieldId, this);
        }

        public WeakListener<Observable> getListener() {
            return this.mListener;
        }

        public void addListener(Observable target) {
            target.addOnPropertyChangedCallback(this);
        }

        public void removeListener(Observable target) {
            target.removeOnPropertyChangedCallback(this);
        }

        public void onPropertyChanged(Observable sender, int propertyId) {
            ViewDataBinding binder = this.mListener.getBinder();
            if (binder != null && ((Observable) this.mListener.getTarget()) == sender) {
                binder.handleFieldChange(this.mListener.mLocalFieldId, sender, propertyId);
            }
        }
    }

    protected abstract void executeBindings();

    public abstract boolean hasPendingBindings();

    public abstract void invalidateAll();

    protected abstract boolean onFieldChange(int i, Object obj, int i2);

    public abstract boolean setVariable(int i, Object obj);

    static {
        boolean z = true;
        if (SDK_INT < 16) {
            z = false;
        }
        USE_CHOREOGRAPHER = z;
        if (VERSION.SDK_INT < 19) {
            ROOT_REATTACHED_LISTENER = null;
        } else {
            ROOT_REATTACHED_LISTENER = new OnAttachStateChangeListener() {
                @TargetApi(19)
                public void onViewAttachedToWindow(View v) {
                    ViewDataBinding.getBinding(v).mRebindRunnable.run();
                    v.removeOnAttachStateChangeListener(this);
                }

                public void onViewDetachedFromWindow(View v) {
                }
            };
        }
    }

    protected ViewDataBinding(DataBindingComponent bindingComponent, View root, int localFieldCount) {
        this.mBindingComponent = bindingComponent;
        this.mLocalFieldObservers = new WeakListener[localFieldCount];
        this.mRoot = root;
        if (Looper.myLooper() == null) {
            throw new IllegalStateException("DataBinding must be created in view's UI Thread");
        } else if (USE_CHOREOGRAPHER) {
            this.mChoreographer = Choreographer.getInstance();
            this.mFrameCallback = new FrameCallback() {
                public void doFrame(long frameTimeNanos) {
                    ViewDataBinding.this.mRebindRunnable.run();
                }
            };
        } else {
            this.mFrameCallback = null;
            this.mUIThreadHandler = new Handler(Looper.myLooper());
        }
    }

    protected void setRootTag(View view) {
        if (USE_TAG_ID) {
            view.setTag(R.id.dataBinding, this);
        } else {
            view.setTag(this);
        }
    }

    protected void setRootTag(View[] views) {
        int i = 0;
        int length;
        if (USE_TAG_ID) {
            length = views.length;
            while (i < length) {
                views[i].setTag(R.id.dataBinding, this);
                i++;
            }
            return;
        }
        length = views.length;
        while (i < length) {
            views[i].setTag(this);
            i++;
        }
    }

    public static int getBuildSdkInt() {
        return SDK_INT;
    }

    public void addOnRebindCallback(OnRebindCallback listener) {
        if (this.mRebindCallbacks == null) {
            this.mRebindCallbacks = new CallbackRegistry(REBIND_NOTIFIER);
        }
        this.mRebindCallbacks.add(listener);
    }

    public void removeOnRebindCallback(OnRebindCallback listener) {
        if (this.mRebindCallbacks != null) {
            this.mRebindCallbacks.remove(listener);
        }
    }

    public void executePendingBindings() {
        if (this.mContainingBinding == null) {
            executeBindingsInternal();
        } else {
            this.mContainingBinding.executePendingBindings();
        }
    }

    private void executeBindingsInternal() {
        if (this.mIsExecutingPendingBindings) {
            requestRebind();
        } else if (hasPendingBindings()) {
            this.mIsExecutingPendingBindings = true;
            this.mRebindHalted = false;
            if (this.mRebindCallbacks != null) {
                this.mRebindCallbacks.notifyCallbacks(this, 1, null);
                if (this.mRebindHalted) {
                    this.mRebindCallbacks.notifyCallbacks(this, 2, null);
                }
            }
            if (!this.mRebindHalted) {
                executeBindings();
                if (this.mRebindCallbacks != null) {
                    this.mRebindCallbacks.notifyCallbacks(this, 3, null);
                }
            }
            this.mIsExecutingPendingBindings = false;
        }
    }

    protected static void executeBindingsOn(ViewDataBinding other) {
        other.executeBindingsInternal();
    }

    void forceExecuteBindings() {
        executeBindings();
    }

    public void unbind() {
        for (WeakListener weakListener : this.mLocalFieldObservers) {
            if (weakListener != null) {
                weakListener.unregister();
            }
        }
    }

    static ViewDataBinding getBinding(View v) {
        if (v != null) {
            if (USE_TAG_ID) {
                return (ViewDataBinding) v.getTag(R.id.dataBinding);
            }
            Object tag = v.getTag();
            if (tag instanceof ViewDataBinding) {
                return (ViewDataBinding) tag;
            }
        }
        return null;
    }

    public View getRoot() {
        return this.mRoot;
    }

    private void handleFieldChange(int mLocalFieldId, Object object, int fieldId) {
        if (onFieldChange(mLocalFieldId, object, fieldId)) {
            requestRebind();
        }
    }

    protected boolean unregisterFrom(int localFieldId) {
        WeakListener listener = this.mLocalFieldObservers[localFieldId];
        if (listener != null) {
            return listener.unregister();
        }
        return false;
    }

    /* JADX WARNING: Missing block: B:16:0x001a, code:
            if (USE_CHOREOGRAPHER == false) goto L_0x0024;
     */
    /* JADX WARNING: Missing block: B:17:0x001c, code:
            r2.mChoreographer.postFrameCallback(r2.mFrameCallback);
     */
    /* JADX WARNING: Missing block: B:18:0x0024, code:
            r2.mUIThreadHandler.post(r2.mRebindRunnable);
     */
    /* JADX WARNING: Missing block: B:20:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:21:?, code:
            return;
     */
    protected void requestRebind() {
        /*
        r2 = this;
        r0 = r2.mContainingBinding;
        if (r0 == 0) goto L_0x000a;
    L_0x0004:
        r0 = r2.mContainingBinding;
        r0.requestRebind();
    L_0x0009:
        return;
    L_0x000a:
        monitor-enter(r2);
        r0 = r2.mPendingRebind;	 Catch:{ all -> 0x0011 }
        if (r0 == 0) goto L_0x0014;
    L_0x000f:
        monitor-exit(r2);	 Catch:{ all -> 0x0011 }
        goto L_0x0009;
    L_0x0011:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0011 }
        throw r0;
    L_0x0014:
        r0 = 1;
        r2.mPendingRebind = r0;	 Catch:{ all -> 0x0011 }
        monitor-exit(r2);	 Catch:{ all -> 0x0011 }
        r0 = USE_CHOREOGRAPHER;
        if (r0 == 0) goto L_0x0024;
    L_0x001c:
        r0 = r2.mChoreographer;
        r1 = r2.mFrameCallback;
        r0.postFrameCallback(r1);
        goto L_0x0009;
    L_0x0024:
        r0 = r2.mUIThreadHandler;
        r1 = r2.mRebindRunnable;
        r0.post(r1);
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.databinding.ViewDataBinding.requestRebind():void");
    }

    protected Object getObservedField(int localFieldId) {
        WeakListener listener = this.mLocalFieldObservers[localFieldId];
        if (listener == null) {
            return null;
        }
        return listener.getTarget();
    }

    private boolean updateRegistration(int localFieldId, Object observable, CreateWeakListener listenerCreator) {
        if (observable == null) {
            return unregisterFrom(localFieldId);
        }
        WeakListener listener = this.mLocalFieldObservers[localFieldId];
        if (listener == null) {
            registerTo(localFieldId, observable, listenerCreator);
            return true;
        } else if (listener.getTarget() == observable) {
            return false;
        } else {
            unregisterFrom(localFieldId);
            registerTo(localFieldId, observable, listenerCreator);
            return true;
        }
    }

    protected boolean updateRegistration(int localFieldId, Observable observable) {
        return updateRegistration(localFieldId, observable, CREATE_PROPERTY_LISTENER);
    }

    protected boolean updateRegistration(int localFieldId, ObservableList observable) {
        return updateRegistration(localFieldId, observable, CREATE_LIST_LISTENER);
    }

    protected boolean updateRegistration(int localFieldId, ObservableMap observable) {
        return updateRegistration(localFieldId, observable, CREATE_MAP_LISTENER);
    }

    protected void ensureBindingComponentIsNotNull(Class<?> oneExample) {
        if (this.mBindingComponent == null) {
            throw new IllegalStateException("Required DataBindingComponent is null in class " + getClass().getSimpleName() + ". A BindingAdapter in " + oneExample.getCanonicalName() + " is not static and requires an object to use, retrieved from the " + "DataBindingComponent. If you don't use an inflation method taking a " + "DataBindingComponent, use DataBindingUtil.setDefaultComponent or " + "make all BindingAdapter methods static.");
        }
    }

    protected void registerTo(int localFieldId, Object observable, CreateWeakListener listenerCreator) {
        if (observable != null) {
            WeakListener listener = this.mLocalFieldObservers[localFieldId];
            if (listener == null) {
                listener = listenerCreator.create(this, localFieldId);
                this.mLocalFieldObservers[localFieldId] = listener;
            }
            listener.setTarget(observable);
        }
    }

    protected static ViewDataBinding bind(DataBindingComponent bindingComponent, View view, int layoutId) {
        return DataBindingUtil.bind(bindingComponent, view, layoutId);
    }

    protected static Object[] mapBindings(DataBindingComponent bindingComponent, View root, int numBindings, IncludedLayouts includes, SparseIntArray viewsWithIds) {
        Object[] bindings = new Object[numBindings];
        mapBindings(bindingComponent, root, bindings, includes, viewsWithIds, true);
        return bindings;
    }

    protected static boolean parse(String str, boolean fallback) {
        return str == null ? fallback : Boolean.parseBoolean(str);
    }

    protected static byte parse(String str, byte fallback) {
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    protected static short parse(String str, short fallback) {
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    protected static int parse(String str, int fallback) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    protected static long parse(String str, long fallback) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    protected static float parse(String str, float fallback) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    protected static double parse(String str, double fallback) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    protected static char parse(String str, char fallback) {
        return (str == null || str.isEmpty()) ? fallback : str.charAt(0);
    }

    protected static int getColorFromResource(View view, int resourceId) {
        if (VERSION.SDK_INT >= 23) {
            return view.getContext().getColor(resourceId);
        }
        return view.getResources().getColor(resourceId);
    }

    protected static ColorStateList getColorStateListFromResource(View view, int resourceId) {
        if (VERSION.SDK_INT >= 23) {
            return view.getContext().getColorStateList(resourceId);
        }
        return view.getResources().getColorStateList(resourceId);
    }

    protected static Drawable getDrawableFromResource(View view, int resourceId) {
        if (VERSION.SDK_INT >= 21) {
            return view.getContext().getDrawable(resourceId);
        }
        return view.getResources().getDrawable(resourceId);
    }

    protected static <T> T getFromArray(T[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return null;
        }
        return arr[index];
    }

    protected static <T> void setTo(T[] arr, int index, T value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static boolean getFromArray(boolean[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return false;
        }
        return arr[index];
    }

    protected static void setTo(boolean[] arr, int index, boolean value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static byte getFromArray(byte[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return (byte) 0;
        }
        return arr[index];
    }

    protected static void setTo(byte[] arr, int index, byte value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static short getFromArray(short[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return (short) 0;
        }
        return arr[index];
    }

    protected static void setTo(short[] arr, int index, short value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static char getFromArray(char[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return 0;
        }
        return arr[index];
    }

    protected static void setTo(char[] arr, int index, char value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static int getFromArray(int[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return 0;
        }
        return arr[index];
    }

    protected static void setTo(int[] arr, int index, int value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static long getFromArray(long[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return 0;
        }
        return arr[index];
    }

    protected static void setTo(long[] arr, int index, long value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static float getFromArray(float[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return 0.0f;
        }
        return arr[index];
    }

    protected static void setTo(float[] arr, int index, float value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static double getFromArray(double[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return 0.0d;
        }
        return arr[index];
    }

    protected static void setTo(double[] arr, int index, double value) {
        if (arr != null && index >= 0 && index < arr.length) {
            arr[index] = value;
        }
    }

    protected static <T> T getFromList(List<T> list, int index) {
        if (list == null || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    protected static <T> void setTo(List<T> list, int index, T value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.set(index, value);
        }
    }

    protected static <T> T getFromList(SparseArray<T> list, int index) {
        if (list == null || index < 0) {
            return null;
        }
        return list.get(index);
    }

    protected static <T> void setTo(SparseArray<T> list, int index, T value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.put(index, value);
        }
    }

    @TargetApi(16)
    protected static <T> T getFromList(LongSparseArray<T> list, int index) {
        if (list == null || index < 0) {
            return null;
        }
        return list.get((long) index);
    }

    @TargetApi(16)
    protected static <T> void setTo(LongSparseArray<T> list, int index, T value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.put((long) index, value);
        }
    }

    protected static <T> T getFromList(android.support.v4.util.LongSparseArray<T> list, int index) {
        if (list == null || index < 0) {
            return null;
        }
        return list.get((long) index);
    }

    protected static <T> void setTo(android.support.v4.util.LongSparseArray<T> list, int index, T value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.put((long) index, value);
        }
    }

    protected static boolean getFromList(SparseBooleanArray list, int index) {
        if (list == null || index < 0) {
            return false;
        }
        return list.get(index);
    }

    protected static void setTo(SparseBooleanArray list, int index, boolean value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.put(index, value);
        }
    }

    protected static int getFromList(SparseIntArray list, int index) {
        if (list == null || index < 0) {
            return 0;
        }
        return list.get(index);
    }

    protected static void setTo(SparseIntArray list, int index, int value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.put(index, value);
        }
    }

    @TargetApi(18)
    protected static long getFromList(SparseLongArray list, int index) {
        if (list == null || index < 0) {
            return 0;
        }
        return list.get(index);
    }

    @TargetApi(18)
    protected static void setTo(SparseLongArray list, int index, long value) {
        if (list != null && index >= 0 && index < list.size()) {
            list.put(index, value);
        }
    }

    protected static <K, T> T getFrom(Map<K, T> map, K key) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    protected static <K, T> void setTo(Map<K, T> map, K key, T value) {
        if (map != null) {
            map.put(key, value);
        }
    }

    protected static void setBindingInverseListener(ViewDataBinding binder, InverseBindingListener oldListener, PropertyChangedInverseListener listener) {
        if (oldListener != listener) {
            if (oldListener != null) {
                binder.removeOnPropertyChangedCallback((PropertyChangedInverseListener) oldListener);
            }
            if (listener != null) {
                binder.addOnPropertyChangedCallback(listener);
            }
        }
    }

    protected static int safeUnbox(Integer boxed) {
        return boxed == null ? 0 : boxed.intValue();
    }

    protected static long safeUnbox(Long boxed) {
        return boxed == null ? 0 : boxed.longValue();
    }

    protected static short safeUnbox(Short boxed) {
        return boxed == null ? (short) 0 : boxed.shortValue();
    }

    protected static byte safeUnbox(Byte boxed) {
        return boxed == null ? (byte) 0 : boxed.byteValue();
    }

    protected static char safeUnbox(Character boxed) {
        return boxed == null ? 0 : boxed.charValue();
    }

    protected static double safeUnbox(Double boxed) {
        return boxed == null ? 0.0d : boxed.doubleValue();
    }

    protected static float safeUnbox(Float boxed) {
        return boxed == null ? 0.0f : boxed.floatValue();
    }

    protected static boolean safeUnbox(Boolean boxed) {
        return boxed == null ? false : boxed.booleanValue();
    }

    protected void setContainedBinding(ViewDataBinding included) {
        if (included != null) {
            included.mContainingBinding = this;
        }
    }

    protected static Object[] mapBindings(DataBindingComponent bindingComponent, View[] roots, int numBindings, IncludedLayouts includes, SparseIntArray viewsWithIds) {
        Object[] bindings = new Object[numBindings];
        for (View mapBindings : roots) {
            mapBindings(bindingComponent, mapBindings, bindings, includes, viewsWithIds, true);
        }
        return bindings;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:75:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:75:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:75:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006d  */
    private static void mapBindings(android.databinding.DataBindingComponent r30, android.view.View r31, java.lang.Object[] r32, android.databinding.ViewDataBinding.IncludedLayouts r33, android.util.SparseIntArray r34, boolean r35) {
        /*
        r11 = getBinding(r31);
        if (r11 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r25 = r31.getTag();
        r0 = r25;
        r3 = r0 instanceof java.lang.String;
        if (r3 == 0) goto L_0x00f1;
    L_0x0011:
        r25 = (java.lang.String) r25;
        r26 = r25;
    L_0x0015:
        r19 = 0;
        if (r35 == 0) goto L_0x00fd;
    L_0x0019:
        if (r26 == 0) goto L_0x00fd;
    L_0x001b:
        r3 = "layout";
        r0 = r26;
        r3 = r0.startsWith(r3);
        if (r3 == 0) goto L_0x00fd;
    L_0x0026:
        r3 = 95;
        r0 = r26;
        r28 = r0.lastIndexOf(r3);
        if (r28 <= 0) goto L_0x00f9;
    L_0x0030:
        r3 = r28 + 1;
        r0 = r26;
        r3 = isNumeric(r0, r3);
        if (r3 == 0) goto L_0x00f9;
    L_0x003a:
        r3 = r28 + 1;
        r0 = r26;
        r17 = parseTagInt(r0, r3);
        r3 = r32[r17];
        if (r3 != 0) goto L_0x0048;
    L_0x0046:
        r32[r17] = r31;
    L_0x0048:
        if (r33 != 0) goto L_0x00f5;
    L_0x004a:
        r18 = -1;
    L_0x004c:
        r19 = 1;
    L_0x004e:
        if (r19 != 0) goto L_0x0067;
    L_0x0050:
        r13 = r31.getId();
        if (r13 <= 0) goto L_0x0067;
    L_0x0056:
        if (r34 == 0) goto L_0x0067;
    L_0x0058:
        r3 = -1;
        r0 = r34;
        r17 = r0.get(r13, r3);
        if (r17 < 0) goto L_0x0067;
    L_0x0061:
        r3 = r32[r17];
        if (r3 != 0) goto L_0x0067;
    L_0x0065:
        r32[r17] = r31;
    L_0x0067:
        r0 = r31;
        r3 = r0 instanceof android.view.ViewGroup;
        if (r3 == 0) goto L_0x0006;
    L_0x006d:
        r29 = r31;
        r29 = (android.view.ViewGroup) r29;
        r10 = r29.getChildCount();
        r24 = 0;
        r12 = 0;
    L_0x0078:
        if (r12 >= r10) goto L_0x0006;
    L_0x007a:
        r0 = r29;
        r4 = r0.getChildAt(r12);
        r20 = 0;
        if (r18 < 0) goto L_0x00e0;
    L_0x0084:
        r3 = r4.getTag();
        r3 = r3 instanceof java.lang.String;
        if (r3 == 0) goto L_0x00e0;
    L_0x008c:
        r9 = r4.getTag();
        r9 = (java.lang.String) r9;
        r3 = "_0";
        r3 = r9.endsWith(r3);
        if (r3 == 0) goto L_0x00e0;
    L_0x009b:
        r3 = "layout";
        r3 = r9.startsWith(r3);
        if (r3 == 0) goto L_0x00e0;
    L_0x00a4:
        r3 = 47;
        r3 = r9.indexOf(r3);
        if (r3 <= 0) goto L_0x00e0;
    L_0x00ac:
        r0 = r24;
        r1 = r33;
        r2 = r18;
        r15 = findIncludeIndex(r9, r0, r1, r2);
        if (r15 < 0) goto L_0x00e0;
    L_0x00b8:
        r20 = 1;
        r24 = r15 + 1;
        r0 = r33;
        r3 = r0.indexes;
        r3 = r3[r18];
        r17 = r3[r15];
        r0 = r33;
        r3 = r0.layoutIds;
        r3 = r3[r18];
        r23 = r3[r15];
        r0 = r29;
        r22 = findLastMatching(r0, r12);
        r0 = r22;
        if (r0 != r12) goto L_0x0127;
    L_0x00d6:
        r0 = r30;
        r1 = r23;
        r3 = android.databinding.DataBindingUtil.bind(r0, r4, r1);
        r32[r17] = r3;
    L_0x00e0:
        if (r20 != 0) goto L_0x00ee;
    L_0x00e2:
        r8 = 0;
        r3 = r30;
        r5 = r32;
        r6 = r33;
        r7 = r34;
        mapBindings(r3, r4, r5, r6, r7, r8);
    L_0x00ee:
        r12 = r12 + 1;
        goto L_0x0078;
    L_0x00f1:
        r26 = 0;
        goto L_0x0015;
    L_0x00f5:
        r18 = r17;
        goto L_0x004c;
    L_0x00f9:
        r18 = -1;
        goto L_0x004e;
    L_0x00fd:
        if (r26 == 0) goto L_0x0123;
    L_0x00ff:
        r3 = "binding_";
        r0 = r26;
        r3 = r0.startsWith(r3);
        if (r3 == 0) goto L_0x0123;
    L_0x010a:
        r3 = BINDING_NUMBER_START;
        r0 = r26;
        r27 = parseTagInt(r0, r3);
        r3 = r32[r27];
        if (r3 != 0) goto L_0x0118;
    L_0x0116:
        r32[r27] = r31;
    L_0x0118:
        r19 = 1;
        if (r33 != 0) goto L_0x0120;
    L_0x011c:
        r18 = -1;
    L_0x011e:
        goto L_0x004e;
    L_0x0120:
        r18 = r27;
        goto L_0x011e;
    L_0x0123:
        r18 = -1;
        goto L_0x004e;
    L_0x0127:
        r3 = r22 - r12;
        r14 = r3 + 1;
        r0 = new android.view.View[r14];
        r16 = r0;
        r21 = 0;
    L_0x0131:
        r0 = r21;
        if (r0 >= r14) goto L_0x0142;
    L_0x0135:
        r3 = r12 + r21;
        r0 = r29;
        r3 = r0.getChildAt(r3);
        r16[r21] = r3;
        r21 = r21 + 1;
        goto L_0x0131;
    L_0x0142:
        r0 = r30;
        r1 = r16;
        r2 = r23;
        r3 = android.databinding.DataBindingUtil.bind(r0, r1, r2);
        r32[r17] = r3;
        r3 = r14 + -1;
        r12 = r12 + r3;
        goto L_0x00e0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.databinding.ViewDataBinding.mapBindings(android.databinding.DataBindingComponent, android.view.View, java.lang.Object[], android.databinding.ViewDataBinding$IncludedLayouts, android.util.SparseIntArray, boolean):void");
    }

    private static int findIncludeIndex(String tag, int minInclude, IncludedLayouts included, int includedIndex) {
        CharSequence layoutName = tag.subSequence(tag.indexOf(47) + 1, tag.length() - 2);
        String[] layouts = included.layouts[includedIndex];
        int length = layouts.length;
        for (int i = minInclude; i < length; i++) {
            if (TextUtils.equals(layoutName, layouts[i])) {
                return i;
            }
        }
        return -1;
    }

    private static int findLastMatching(ViewGroup viewGroup, int firstIncludedIndex) {
        String firstViewTag = (String) viewGroup.getChildAt(firstIncludedIndex).getTag();
        String tagBase = firstViewTag.substring(0, firstViewTag.length() - 1);
        int tagSequenceIndex = tagBase.length();
        int count = viewGroup.getChildCount();
        int max = firstIncludedIndex;
        for (int i = firstIncludedIndex + 1; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            String tag = view.getTag() instanceof String ? (String) view.getTag() : null;
            if (tag != null && tag.startsWith(tagBase)) {
                if (tag.length() == firstViewTag.length() && tag.charAt(tag.length() - 1) == '0') {
                    break;
                } else if (isNumeric(tag, tagSequenceIndex)) {
                    max = i;
                }
            }
        }
        return max;
    }

    private static boolean isNumeric(String tag, int startIndex) {
        int length = tag.length();
        if (length == startIndex) {
            return false;
        }
        for (int i = startIndex; i < length; i++) {
            if (!Character.isDigit(tag.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int parseTagInt(String str, int startIndex) {
        int val = 0;
        for (int i = startIndex; i < str.length(); i++) {
            val = (val * 10) + (str.charAt(i) - 48);
        }
        return val;
    }

    private static void processReferenceQueue() {
        while (true) {
            Reference<? extends ViewDataBinding> ref = sReferenceQueue.poll();
            if (ref == null) {
                return;
            }
            if (ref instanceof WeakListener) {
                ((WeakListener) ref).unregister();
            }
        }
    }
}
