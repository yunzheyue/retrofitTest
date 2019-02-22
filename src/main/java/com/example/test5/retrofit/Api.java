package com.example.test5.retrofit;


import com.example.test5.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;


public interface Api {

//todo    下面是对于get请求

    //    可以多指定几个查询条件
    @GET("GetData/User_QueryPhonePrincipal")
    Call<ResponseBody> login(@Query("UserCode") String userCode, @Query("ApplicationID") String applicationID);


    //    动态的配置url路径，使用pathname相对应
    @GET("{pathname}/getXXX/name=xxx")
    Call<ResponseBody> getData(@Path("pathname") String path);


    //    动态的指定查询条件  会自动的添加?name=name
    @GET("getData")
    Call<ResponseBody> getName(@Query("name") String name);

    //    动态指定查询条件组
    @GET("getDataArr")
    Call<ResponseBody> getName1(@QueryMap Map<String, String> options);


//todo     下面试对于post请求

    @FormUrlEncoded   //表示的是表单上传  需要配合@Field形成键值对，然后请求数据
    @POST("SendData/xxxx")
    Call<ResponseBody> postData(@Field("name") String name);


    //    可以使用@Body将json字符串作为请求体发送到服务器.@Body会自动将对象转化为字符串
    @POST("xxxxx")
    Call<ResponseBody> postData1(@Body User user);

    //    单个文件上传@Part
    @Multipart  //必须配合@Part进行使用
    @POST("xxxx")
    Call<ResponseBody> postData2(@Part MultipartBody.Part file);


    //    多文件的上传
    @Multipart
    @POST("xxx")
    Call<ResponseBody> postData3(@PartMap Map<String, RequestBody> maps);


    //todo   文件的下载
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);


//todo    添加消息头，可以使用静态方法,动态方法，拦截器等方法

//    https://blog.csdn.net/silenceoo/article/details/77460607

}
