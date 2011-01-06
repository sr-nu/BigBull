package com.tw.utils;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class NetworkUtils {

    public String sendHttpRequest(String URL) throws IOException {
        HttpRequest request = new HttpGet(URL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute((HttpUriRequest) request);
        String value = EntityUtils.toString(response.getEntity());
        client.getConnectionManager().shutdown();
        return value;
    }

}
