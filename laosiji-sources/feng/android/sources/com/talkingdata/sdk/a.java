package com.talkingdata.sdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: td */
public abstract class a {
    public static final a ENV = new c("ENV", 2);
    private static volatile List FeaturesList = new ArrayList();
    private static final String MF_JSON = "JSON";
    private static final String MF_MP = "MP";
    private static final String MF_PB = "PB";
    public static final a TRACKING = new b("TRACKING", 1);
    private static final a[] service = new a[]{TRACKING, ENV};
    private int index;
    private String name;

    public abstract String getCert();

    public abstract String getHost();

    public abstract String getIP();

    public abstract String getMessageFormat();

    public abstract String getUrl();

    protected a(String str, int i) {
        this.name = str;
        this.index = i;
        addFeatures2List(str);
    }

    public static List getFeaturesNameList() {
        return FeaturesList;
    }

    protected a(String str, int i, boolean z) {
        this.name = str;
        this.index = i;
    }

    private void addFeatures2List(String str) {
        try {
            if (!bo.b(str) && !FeaturesList.contains(str)) {
                FeaturesList.add(str);
            }
        } catch (Throwable th) {
        }
    }

    public static a valueOf(String str) {
        if (str.equals(TRACKING.name())) {
            return TRACKING;
        }
        if (str.equals(ENV.name())) {
            return ENV;
        }
        return null;
    }

    public static a[] values() {
        return (a[]) Arrays.copyOf(service, service.length);
    }

    public String name() {
        return this.name;
    }

    public int index() {
        return this.index;
    }

    public static ArrayList getFeaturesList() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            try {
                int i2 = i;
                if (i2 >= FeaturesList.size()) {
                    break;
                }
                arrayList.add(valueOf((String) FeaturesList.get(i2)));
                i = i2 + 1;
            } catch (Throwable th) {
            }
        }
        return arrayList;
    }
}
