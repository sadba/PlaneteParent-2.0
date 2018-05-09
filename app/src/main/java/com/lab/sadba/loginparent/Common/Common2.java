package com.lab.sadba.loginparent.Common;

import com.lab.sadba.loginparent.Remote.IMyAPI;
import com.lab.sadba.loginparent.Remote.RetrofitCLient2;
import com.lab.sadba.loginparent.Remote.RetrofitClient;

public class Common2 {

    public static final String BASE_URL = "https://codeco.education.sn/api-mobi/";

    public static IMyAPI getAPI()
    {
        return RetrofitCLient2.getClient(BASE_URL).create(IMyAPI.class);
    }
}
