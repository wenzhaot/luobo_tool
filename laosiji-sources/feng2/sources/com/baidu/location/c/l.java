package com.baidu.location.c;

import java.util.TimerTask;

class l extends TimerTask {
    final /* synthetic */ j a;

    l(j jVar) {
        this.a = jVar;
    }

    public void run() {
        try {
            this.a.g();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
