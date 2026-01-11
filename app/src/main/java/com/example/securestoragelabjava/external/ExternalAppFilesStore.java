package com.example.securestoragelabjava.external;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class ExternalAppFilesStore {

    private ExternalAppFilesStore() {}

    public static String write(Context context, String fileName, String content) throws Exception {
        File dir = context.getExternalFilesDir(null);
        if (dir == null) return null;

        File file = new File(dir, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes("UTF-8"));
        }
        return file.getAbsolutePath();
    }

    public static String read(Context context, String fileName) throws Exception {
        File dir = context.getExternalFilesDir(null);
        if (dir == null) return null;

        File file = new File(dir, fileName);
        if (!file.exists()) return null;

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return new String(bytes, "UTF-8");
        }
    }

    public static boolean delete(Context context, String fileName) {
        File dir = context.getExternalFilesDir(null);
        if (dir == null) return false;

        File file = new File(dir, fileName);
        return file.delete();
    }
}
