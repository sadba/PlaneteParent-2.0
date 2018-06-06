package com.lab.sadba.loginparent.Remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCLient4 {

    private static final String BASE_URL = "https://codeco.education.sn/api-mobi/";
    //private static final String BASE_URL = "http://simen_api.education.sn/";
    //private static final String BASE_URLP = "http://api.simendev.com/";

    private static Retrofit retrofit = null;
    //private static Retrofit retrofit1 = null;

    public static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
