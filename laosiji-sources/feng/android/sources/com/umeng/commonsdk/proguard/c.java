package com.umeng.commonsdk.proguard;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/* compiled from: UMCrashUtils */
public class c {
    public static String a(Throwable th) {
        if (th == null) {
            return null;
        }
        try {
            Writer stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
            }
            String obj = stringWriter.toString();
            printWriter.close();
            stringWriter.close();
            return obj;
        } catch (Exception e) {
            return null;
        }
    }
}
