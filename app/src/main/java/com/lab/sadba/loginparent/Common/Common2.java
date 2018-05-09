package com.lab.sadba.loginparent.Common;

import com.lab.sadba.loginparent.Remote.IMyAPI;
import com.lab.sadba.loginparent.Remote.RetrofitClient;

public class Common2 {

    public static final String BASE_URL1 = "https://codeco.education.sn/api-mobi/";

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL1).create(IMyAPI.class);
    }
}
