package com.example.test5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.test5.retrofit.Retrofit2Util;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//http://ip.taobao.com/service/getIpInfo.php?ip=59.108.54.37
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login("wbadmin");

    }

    public void login(String user) {
        Call<ResponseBody> login = Retrofit2Util.getInstance().api.login(user, "df29319b-2561-4c37-9d09-af2d94a00cb9");
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("TAG","response==="+response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG","异常==="+t);
            }
        });
    }
}
