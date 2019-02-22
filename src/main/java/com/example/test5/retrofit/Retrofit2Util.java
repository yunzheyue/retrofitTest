package com.example.test5.retrofit;

import android.util.Log;
import com.example.test5.BuildConfig;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


public class Retrofit2Util {

    public static Api api = null;

    public static Api getInstance() {
        if (api == null) {
            synchronized (Retrofit2Util.class) {
                if (api == null) {
                    api = getApi();
                }
            }
        }
        return api;
    }

    // 添加默认参数拦截器
    private static class CommonInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request oldRequest = chain.request();
            // 添加默认参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("dataKey", "00-00-00");
            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();

            return chain.proceed(newRequest);
        }
    }

    //    添加日志拦截器
    //        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(); //默认日志
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.e("TAG", "retrofit2请求===" + message);
        }
    });

    private static Api getApi() {
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder = builder.addInterceptor(new CommonInterceptor()).addInterceptor(logging);
        }
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //这个是为了rxjava
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置网络请求的Url地址
                .baseUrl("http://dev.fpc119.com:8001/WebApi/DataExchange/")
                .client(client)
                .build();
        // 创建网络请求接口的实例
        Api mApi = retrofit.create(Api.class);
        return mApi;
    }


}
