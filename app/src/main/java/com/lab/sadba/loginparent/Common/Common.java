package com.lab.sadba.loginparent.Common;

import com.lab.sadba.loginparent.Remote.IMyAPI;
import com.lab.sadba.loginparent.Remote.RetrofitClient;

public class Common {

    public static final String BASE_URL = "https://api.education.sn/mobi-ien/";

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IMyAPI.class);
    }
}
