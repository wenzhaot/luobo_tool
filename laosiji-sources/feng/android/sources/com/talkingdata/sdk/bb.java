package com.talkingdata.sdk;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: td */
public class bb {
    private static HashMap a = new HashMap();

    /* compiled from: td */
    static class a {
        private FileLock fileLock;
        private RandomAccessFile randomAccessFile;
        private Lock threadLock;

        private a() {
        }

        /* synthetic */ a(bc bcVar) {
            this();
        }
    }

    /* compiled from: td */
    public enum b {
        Cloud_Control_Lock_File("Cloud_Control_Cache_Param"),
        AntiCheating_Switch_Lock_File("AntiCheating_Switch_Value"),
        App_Lock_File("__App_Synchronous_Lock__"),
        Tracking_Lock_File("__Tracking_Synchronous_Lock__"),
        Env_Lock_File("__Env_Synchronous_Lock__"),
        Game_Lock_File("__Game_Synchronous_Lock__"),
        Push_Lock_File("__Push_Synchronous_Lock__"),
        SMS_Lock_File("__SMS_Synchronous_Lock__"),
        EAuth_Lock_File("__EAuth_Synchronous_Lock__"),
        App_SQL_Lock_File("__AppSQL_Synchronous_Lock__"),
        BG_Lock_File("__BG_Synchronous_Lock__"),
        Fintech_Lock_File("__Fintech_Synchronous_Lock__"),
        AntiCheating_Data_Lock_File("AntiCheating_Data_Value"),
        Fintech_Data_Lock_File("_Fintech_Data_Lock"),
        AES_DATA_LOCK("_AES_DATA_LOCK"),
        AES_DATA_ENTRYCP_LOCK("_AES_DATA_ENTCRYPT_LOCK"),
        SMS_Model_Lock_File("__SMS_Model_Lock_File"),
        AES_SALT_LOCK("_AES_SALT_LOCK"),
        AES_IV_LOCK("_AES_IV_LOCK");
        
        private final String filePath;

        private b(String str) {
            this.filePath = new File(ab.g.getFilesDir(), ab.r + str).getAbsolutePath();
        }

        public static String getFeatureLockFileName(int i) {
            switch (i) {
                case 0:
                    return App_Lock_File.toString();
                case 1:
                    return Tracking_Lock_File.toString();
                case 2:
                    return Env_Lock_File.toString();
                case 3:
                    return Game_Lock_File.toString();
                case 4:
                    return Push_Lock_File.toString();
                case 5:
                    return SMS_Lock_File.toString();
                case 6:
                    return EAuth_Lock_File.toString();
                case 7:
                    return App_SQL_Lock_File.toString();
                case 8:
                    return BG_Lock_File.toString();
                case 9:
                    return Fintech_Lock_File.toString();
                default:
                    return null;
            }
        }

        public String toString() {
            return this.filePath;
        }
    }

    public static void getFileLock(String str) {
        try {
            if (bo.b(str)) {
                throw new RuntimeException("LockManager Error: filePath can not be null!");
            }
            a aVar;
            RandomAccessFile access$000;
            Lock access$100;
            File file = new File(str);
            if (a.containsKey(str)) {
                aVar = (a) a.get(str);
                access$000 = aVar.randomAccessFile;
                access$100 = aVar.threadLock;
            } else {
                aVar = new a();
                access$000 = new RandomAccessFile(file, "rw");
                access$100 = new ReentrantLock();
                aVar.randomAccessFile = access$000;
                aVar.threadLock = access$100;
                a.put(str, aVar);
            }
            access$100.lock();
            aVar.fileLock = access$000.getChannel().lock();
        } catch (Throwable th) {
        }
    }

    public static boolean a(String str) {
        try {
            if (bo.b(str)) {
                throw new RuntimeException("LockManager Error: filePath can not be null!");
            }
            a aVar;
            RandomAccessFile access$000;
            Lock access$100;
            File file = new File(str);
            if (a.containsKey(str)) {
                aVar = (a) a.get(str);
                access$000 = aVar.randomAccessFile;
                access$100 = aVar.threadLock;
            } else {
                aVar = new a();
                access$000 = new RandomAccessFile(file, "rw");
                access$100 = new ReentrantLock();
                aVar.randomAccessFile = access$000;
                aVar.threadLock = access$100;
                a.put(str, aVar);
            }
            access$100.lock();
            aVar.fileLock = access$000.getChannel().tryLock();
            if (aVar.fileLock != null) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static void releaseFileLock(String str) {
        try {
            if (bo.b(str)) {
                throw new RuntimeException("LockManager Error: filePath can not be null!");
            } else if (a.containsKey(str)) {
                a aVar = (a) a.get(str);
                if (aVar.fileLock != null) {
                    aVar.fileLock.release();
                }
                aVar.threadLock.unlock();
            } else {
                throw new RuntimeException("LockManager Error: there is no information about this file in the cache!");
            }
        } catch (Throwable th) {
        }
    }

    public static RandomAccessFile b(String str) {
        try {
            if (bo.b(str)) {
                throw new RuntimeException("LockManager Error: filePath can not be null!");
            }
            File file = new File(str);
            if (a.containsKey(str)) {
                return ((a) a.get(str)).randomAccessFile;
            }
            a aVar = new a();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            Lock reentrantLock = new ReentrantLock();
            aVar.randomAccessFile = randomAccessFile;
            aVar.threadLock = reentrantLock;
            a.put(str, aVar);
            return randomAccessFile;
        } catch (Throwable th) {
            return null;
        }
    }
}
