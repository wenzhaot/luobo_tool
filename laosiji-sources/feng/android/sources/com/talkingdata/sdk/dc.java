package com.talkingdata.sdk;

/* compiled from: td */
public class dc {
    public a a = null;
    public a b = a.IMMEDIATELY;

    /* compiled from: td */
    public enum a {
        IMMEDIATELY(0),
        HIGH(1);
        
        private final int index;

        private a(int i) {
            this.index = i;
        }

        public int index() {
            return this.index;
        }
    }
}
