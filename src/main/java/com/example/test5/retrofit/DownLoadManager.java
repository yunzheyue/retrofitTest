package com.example.test5.retrofit;

import android.util.Log;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownLoadManager {

    //Log标记
    private static final String TAG = "eeeee";
    //APK文件类型
    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    //PNG文件类型
    private static String PNG_CONTENTTYPE = "image/png";
    //JPG文件类型
    private static String JPG_CONTENTTYPE = "image/jpeg";
    //文件后缀名
    private static String fileSuffix = "";

    /**
     * 写入文件到本地
     */
    public static boolean writeResponseBodyToDisk(File file, ResponseBody body) {

        Log.d(TAG, "contentType:>>>>" + body.contentType().toString());
        //下载文件类型判断，并对fileSuffix赋值
        String type = body.contentType().toString();
        Log.e("TAG", "type==" + type);
        if (type.equals(APK_CONTENTTYPE)) {
            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENTTYPE)) {
            fileSuffix = ".png";
        } else if (type.equals(JPG_CONTENTTYPE)) {
            fileSuffix = ".jpg";
        }
        Log.e("TAG", "total="+body.contentLength());
        InputStream is = body.byteStream();
        FileOutputStream fos = null;
        int len = 0;
        int sum=0;
        try {
            // 打开一个已存在文件的输出流
            fos = new FileOutputStream(file);
            // 将输入流is写入文件输出流fos中
            while ((len = is.read()) != -1) {
                fos.write(len);
//                Log.e("TAG", "len==="+len);
                sum=sum+len;
//                Log.e("TAG", "sum="+(sum/100));
            }
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
