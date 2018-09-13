package com.umeng.weixin.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class r {
    public static String a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine + "/n");
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    public static String a(String str) {
        String str2 = "";
        try {
            URLConnection openConnection = new URL(str).openConnection();
            if (openConnection == null) {
                return str2;
            }
            openConnection.connect();
            InputStream inputStream = openConnection.getInputStream();
            return inputStream != null ? a(inputStream) : str2;
        } catch (Exception e) {
            return "##" + e.getMessage();
        }
    }
}
