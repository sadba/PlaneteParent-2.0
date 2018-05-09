package com.lab.sadba.loginparent.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCLient2 {

    private static Retrofit retrofit2;

    public static Retrofit getClient(String baseUrl)
    {
        if (retrofit2 == null)
        {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
}
