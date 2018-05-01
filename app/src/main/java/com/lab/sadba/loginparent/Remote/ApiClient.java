package com.lab.sadba.loginparent.Remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lab.sadba.loginparent.Common.AppConst;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient instance;
    private Retrofit retrofit;

    private ApiClient(){
        buildRetrofit();
    }

    public synchronized static ApiClient getInstance(){
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }

    private  Retrofit buildRetrofit(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //log url body
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpBuilder.build())
                .build();

        return  retrofit;
    }

    public IMyAPI getIMyAPI(){
        return retrofit.create(IMyAPI.class);
    }
}
