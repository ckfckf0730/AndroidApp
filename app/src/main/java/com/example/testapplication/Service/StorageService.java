package com.example.testapplication.Service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StorageService
{

    public static boolean saveFileToMediaStore(Context context, InputStream inputStream, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {  // Android 10及以上
            return saveFileToMediaStoreAndroidQ(context, inputStream, fileName);
        } else {
            // 对于Android 10之前的版本，可以使用传统的外部存储方法
            return saveFileToExternalStorage(context, inputStream, fileName);
        }
    }

    // Android 10及以上版本（使用MediaStore）
    private static boolean saveFileToMediaStoreAndroidQ(Context context, InputStream inputStream, String fileName) {
        ContentResolver resolver = context.getContentResolver();

        // 创建文件的ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);  // 文件名
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");  // 设置文件的MIME类型
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp/");  // 设置文件保存的目录路径

        // 插入数据到MediaStore，获取文件的Uri
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        if (uri == null) {
            Log.e("FileUtils", "Failed to create new file in MediaStore.");
            return false;
        }

        try (OutputStream outputStream = resolver.openOutputStream(uri)) {
            if (outputStream == null) {
                Log.e("FileUtils", "Failed to open output stream for URI: " + uri);
                return false;
            }

            // 将InputStream的数据写入到outputStream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return true;  // 文件保存成功
        } catch (IOException e) {
            e.printStackTrace();
            return false;  // 文件保存失败
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 传统方式保存文件到外部存储（适用于Android 10之前）
    private static boolean saveFileToExternalStorage(Context context, InputStream inputStream, String fileName)
    {
        return false;
    }
}