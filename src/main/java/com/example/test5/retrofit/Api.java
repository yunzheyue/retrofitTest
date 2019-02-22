package com.example.test5.retrofit;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.HashMap;


public interface Api {

    @GET("GetData/User_QueryPhonePrincipal")
    Call<ResponseBody> login(@Query("UserCode") String userCode, @Query("ApplicationID") String applicationID);

    @POST("SendData/CMDS_MiniTask_AddOne")
    Call<ResponseBody> postNews5(@QueryMap HashMap<String, String> deviceInfo);
}
