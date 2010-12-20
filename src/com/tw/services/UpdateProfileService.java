package com.tw.services;

import com.tw.utils.NetworkUtils;
import com.tw.utils.URLUtils;

import java.io.IOException;

public class UpdateProfileService {
    public void updateProfile(NetworkUtils networkUtils, String userName, Integer money) throws IOException {
        String serverUrl = URLUtils.getAppEngineUrl(userName,money.toString());
        networkUtils.sendHttpRequest(serverUrl);
    }
}
