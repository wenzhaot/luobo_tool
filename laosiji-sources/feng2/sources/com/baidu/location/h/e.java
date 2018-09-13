package com.baidu.location.h;

import java.util.List;

public class e {
    private static double a = 6378137.0d;

    public static double a(double d, double d2) {
        return d * d2 == -1.0d ? 90.0d : Math.toDegrees(Math.atan(d2 - (d / (1.0d + (d * d2)))));
    }

    public static double a(double d, double d2, double d3, double d4) {
        double d5 = d4 - d2;
        double d6 = d3 - d;
        double toRadians = Math.toRadians(d);
        Math.toRadians(d2);
        double toRadians2 = Math.toRadians(d3);
        Math.toRadians(d4);
        d5 = Math.toRadians(d5);
        d6 = Math.toRadians(d6);
        d5 = (Math.sin(d5 / 2.0d) * ((Math.cos(toRadians) * Math.cos(toRadians2)) * Math.sin(d5 / 2.0d))) + (Math.sin(d6 / 2.0d) * Math.sin(d6 / 2.0d));
        return (Math.atan2(Math.sqrt(d5), Math.sqrt(1.0d - d5)) * 2.0d) * a;
    }

    public static double a(List<Float> list) {
        double floatValue = (double) ((Float) list.get(0)).floatValue();
        double d = floatValue;
        for (int i = 1; i < list.size(); i++) {
            double floatValue2 = ((double) ((Float) list.get(i)).floatValue()) - floatValue;
            floatValue = floatValue2 < -180.0d ? (floatValue + floatValue2) + 360.0d : floatValue2 < 180.0d ? floatValue + floatValue2 : (floatValue + floatValue2) - 360.0d;
            d += floatValue;
        }
        return d / ((double) list.size());
    }

    public static double b(double d, double d2) {
        double d3 = d2 - d;
        return d3 > 180.0d ? d3 - 360.0d : d3 < -180.0d ? d3 + 360.0d : d3;
    }

    public static double b(double d, double d2, double d3, double d4) {
        double toRadians = Math.toRadians(d2);
        double toRadians2 = Math.toRadians(d);
        double toRadians3 = Math.toRadians(d3);
        toRadians = Math.toRadians(d4) - toRadians;
        return (Math.toDegrees(Math.atan2(Math.sin(toRadians) * Math.cos(toRadians3), (Math.cos(toRadians2) * Math.sin(toRadians3)) - (Math.cos(toRadians) * (Math.sin(toRadians2) * Math.cos(toRadians3))))) + 360.0d) % 360.0d;
    }
}
