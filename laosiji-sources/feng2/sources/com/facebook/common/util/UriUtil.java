package com.facebook.common.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore.Images.Media;
import javax.annotation.Nullable;

public class UriUtil {
    public static final String DATA_SCHEME = "data";
    public static final String HTTPS_SCHEME = "https";
    public static final String HTTP_SCHEME = "http";
    public static final String LOCAL_ASSET_SCHEME = "asset";
    private static final String LOCAL_CONTACT_IMAGE_PREFIX = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").getPath();
    public static final String LOCAL_CONTENT_SCHEME = "content";
    public static final String LOCAL_FILE_SCHEME = "file";
    public static final String LOCAL_RESOURCE_SCHEME = "res";

    public static boolean isNetworkUri(@Nullable Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "https".equals(scheme) || "http".equals(scheme);
    }

    public static boolean isLocalFileUri(@Nullable Uri uri) {
        return LOCAL_FILE_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalContentUri(@Nullable Uri uri) {
        return LOCAL_CONTENT_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalContactUri(Uri uri) {
        return isLocalContentUri(uri) && "com.android.contacts".equals(uri.getAuthority()) && !uri.getPath().startsWith(LOCAL_CONTACT_IMAGE_PREFIX);
    }

    public static boolean isLocalCameraUri(Uri uri) {
        String uriString = uri.toString();
        return uriString.startsWith(Media.EXTERNAL_CONTENT_URI.toString()) || uriString.startsWith(Media.INTERNAL_CONTENT_URI.toString());
    }

    public static boolean isLocalAssetUri(@Nullable Uri uri) {
        return LOCAL_ASSET_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalResourceUri(@Nullable Uri uri) {
        return LOCAL_RESOURCE_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isDataUri(@Nullable Uri uri) {
        return DATA_SCHEME.equals(getSchemeOrNull(uri));
    }

    @Nullable
    public static String getSchemeOrNull(@Nullable Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    public static Uri parseUriOrNull(@Nullable String uriAsString) {
        return uriAsString != null ? Uri.parse(uriAsString) : null;
    }

    @Nullable
    public static String getRealPathFromUri(ContentResolver contentResolver, Uri srcUri) {
        String result = null;
        if (isLocalContentUri(srcUri)) {
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(srcUri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int idx = cursor.getColumnIndex("_data");
                    if (idx != -1) {
                        result = cursor.getString(idx);
                    }
                }
                if (cursor == null) {
                    return result;
                }
                cursor.close();
                return result;
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (isLocalFileUri(srcUri)) {
            return srcUri.getPath();
        } else {
            return null;
        }
    }
}
