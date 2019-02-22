package com.example.test5;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetService {

    @GET("getIpInfo.php?ip=59.108.54.37")
    Call<ModelBean> getMsg();



}
