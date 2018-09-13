package android.support.v7.widget.helper;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;

public class CompatItemTouchHelper extends ItemTouchHelper {
    public CompatItemTouchHelper(Callback callback) {
        super(callback);
    }

    public Callback getCallback() {
        return this.mCallback;
    }
}
