package com.lab.sadba.loginparent.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils2 {

    private ApiUtils2() {}

    public static final String BASE_URL = "https://codeco.education.sn/api-mobi/";
    public static Retrofit retrofit = null;

    public static Retrofit getAPIService() {

        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
