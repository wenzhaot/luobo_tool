package com.umeng.socialize.media;

import android.os.Bundle;

public abstract class Base {
    public String transaction;

    public abstract void fromBundle(Bundle bundle);

    public abstract int getType();

    public abstract void toBundle(Bundle bundle);
}
