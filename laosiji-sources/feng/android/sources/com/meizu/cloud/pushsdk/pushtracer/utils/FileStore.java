package com.meizu.cloud.pushsdk.pushtracer.utils;

import android.content.Context;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileStore {
    private static final String TAG = FileStore.class.getSimpleName();

    public static boolean saveMapToFile(String filename, Map objects, Context context) {
        try {
            Logger.d(TAG, "Attempting to save: %s", objects);
            ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(filename, 0));
            oos.writeObject(objects);
            oos.close();
            Logger.d(TAG, " + Successfully saved KV Pairs to: %s", filename);
            return true;
        } catch (IOException ioe) {
            Logger.e(TAG, " + Exception saving vars map: %s", ioe.getMessage());
            return false;
        }
    }

    public static Map getMapFromFile(String filename, Context context) {
        Exception ioe;
        try {
            Logger.d(TAG, "Attempting to retrieve map from: %s", filename);
            ObjectInputStream ois = new ObjectInputStream(context.openFileInput(filename));
            HashMap varsMap = (HashMap) ois.readObject();
            ois.close();
            Logger.d(TAG, " + Retrieved map from file: %s", varsMap);
            return varsMap;
        } catch (IOException e) {
            ioe = e;
        } catch (ClassNotFoundException e2) {
            ioe = e2;
        }
        Logger.e(TAG, " + Exception getting vars map: %s", ioe.getMessage());
        return null;
    }

    public static boolean deleteFile(String filename, Context context) {
        Logger.d(TAG, "Deleted %s from internal storage: %s", filename, Boolean.valueOf(context.deleteFile(filename)));
        return context.deleteFile(filename);
    }
}
