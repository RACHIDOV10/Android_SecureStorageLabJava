package com.example.securestoragelabjava.cache;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class CacheStore {

    private CacheStore() {}

    public static void write(Context context, String fileName, String content) throws Exception {
        File file = new File(context.getCacheDir(), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes("UTF-8"));
        }
    }

    public static String read(Context context, String fileName) throws Exception {
        File file = new File(context.getCacheDir(), fileName);
        if (!file.exists()) return null;

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return new String(bytes, "UTF-8");
        }
    }

    public static int purge(Context context) {
        File[] files = context.getCacheDir().listFiles();
        if (files == null) return 0;
        int deleted = 0;
        for (File f : files) {
            if (f.delete()) deleted++;
        }
        return deleted;
    }
}
