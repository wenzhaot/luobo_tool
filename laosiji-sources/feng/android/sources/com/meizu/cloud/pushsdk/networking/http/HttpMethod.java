package com.meizu.cloud.pushsdk.networking.http;

import com.feng.library.okhttp.utils.OkHttpUtils.METHOD;
import com.umeng.message.util.HttpRequest;

public class HttpMethod {
    public static boolean invalidatesCache(String method) {
        return method.equals(HttpRequest.METHOD_POST) || method.equals(METHOD.PATCH) || method.equals("PUT") || method.equals("DELETE") || method.equals("MOVE");
    }

    public static boolean requiresRequestBody(String method) {
        return method.equals(HttpRequest.METHOD_POST) || method.equals("PUT") || method.equals(METHOD.PATCH) || method.equals("PROPPATCH") || method.equals("REPORT");
    }

    public static boolean permitsRequestBody(String method) {
        if (requiresRequestBody(method) || method.equals(HttpRequest.METHOD_OPTIONS) || method.equals("DELETE") || method.equals("PROPFIND") || method.equals("MKCOL") || method.equals("LOCK")) {
            return true;
        }
        return false;
    }

    public static boolean redirectsToGet(String method) {
        return !method.equals("PROPFIND");
    }

    private HttpMethod() {
    }
}
