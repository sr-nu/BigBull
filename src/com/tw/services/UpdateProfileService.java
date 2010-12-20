package com.tw.services;

import com.tw.utils.NetworkUtils;

import java.io.IOException;
import java.util.Properties;

public class UpdateProfileService {
    Properties properties = new Properties();
    public void updateProfile(NetworkUtils networkUtils, String userName, int money) throws IOException {
//        properties.load(new FileInputStream("/home/praveeg/projects/BigBull/application.properties"));
        String serverUrl;
//        serverUrl = properties.getProperty("app.engine.url");
        serverUrl = "http://1.latest.tamethebullandroid.appspot.com/register";
        StringBuilder builder = new StringBuilder(serverUrl);
        builder.append("?").
                append("key=").
                append(userName).
                append("&value=").
                append(money);
        networkUtils.sendHttpRequest(builder.toString());
    }
}
