package com.example.test5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.test5.retrofit.DownloadListener;
import com.example.test5.retrofit.ProgressRequestBody;
import com.example.test5.retrofit.Retrofit2Util;
import com.example.test5.retrofit.UploadCallbacks;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.*;


//http://ip.taobao.com/service/getIpInfo.php?ip=59.108.54.37
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login("wbadmin");

    }

    public void login(String user) {
        Call<ResponseBody> login = Retrofit2Util.getInstance().login(user, "df29319b-2561-4c37-9d09-af2d94a00cb9");
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("TAG", "response===" + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "异常===" + t);
            }
        });


//        实现文件的上传
        File file = new File("pic路径");
        //是否需要压缩
        //实现上传进度监听
        ProgressRequestBody requestFile = new ProgressRequestBody(file, "image/*", new UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                Log.e("TAG", "onProgressUpdate: " + percentage);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onFinish() {
            }
        });

//        第二个参数是文件的名字
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Retrofit2Util.getInstance().postData2(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public void downloadFile(String url, final DownloadListener downloadListener) {
        Call<ResponseBody> responseBodyCall = Retrofit2Util.getInstance().downloadFile(url);
        final File mFile = new File("mVideoPath");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //下载文件放在子线程
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //保存到本地
                        writeFile2Disk(response, mFile, downloadListener);
                    }
                };
                mThread.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                downloadListener.onFailure(); //下载失败
            }
        });

    }

    //将下载的文件写入本地存储
    private void writeFile2Disk(Response<ResponseBody> response, File file, DownloadListener downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;

        InputStream is = response.body().byteStream(); //获取下载输入流
        long totalLength = response.body().contentLength();

        try {
            os = new FileOutputStream(file); //输出流
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e("TAG", "当前进度: " + currentLength);
                //计算当前下载百分比，并经由回调传出
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
                //当百分比为100时下载结束，调用结束回调，并传出下载后的本地路径
                if ((int) (100 * currentLength / totalLength) == 100) {
                    downloadListener.onFinish("mVideoPath"); //下载完成
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close(); //关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close(); //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
