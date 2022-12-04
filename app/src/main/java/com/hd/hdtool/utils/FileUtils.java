package com.hd.hdtool.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static String getPath(boolean isSdcard, String path) {
        String filePath = "";
        if (isSdcard) {
            filePath = Environment.getExternalStorageDirectory() + "/" + path;
        } else {
            filePath = path;
        }
        return filePath;
    }

    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static void createFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFolder(String folderPath) {
        File file = new File(folderPath);
        if (file.exists()) {
            deleteDirWihtFile(file);
        }
    }

    private static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDirWihtFile(file);
        }
        dir.delete();
    }

    public static void write(String filePath, String str) {
        File mfile = new File(filePath);
        try {
            FileOutputStream fos = fos = new FileOutputStream(mfile);
            fos.write(str.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAdd(String filePath, String str) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.append((CharSequence) "\n");
            fileWriter.append((CharSequence) str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(String filePath) {
        File mfile = new File(filePath);
        String str = "";
        if (mfile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(mfile);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                str = new String(buffer);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static void delete(String filePath) {
        File mfile = new File(filePath);
        if (mfile.exists() && mfile.isFile()) {
            mfile.delete();
        }
    }

}
