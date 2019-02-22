package com.example.test5.retrofit;

public interface UploadCallbacks {
    void onProgressUpdate(int percentage);

    void onError();

    void onFinish();
}
