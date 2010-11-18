package com.tw.services;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DataRetrieverService {

	public String getQuote(String symbol) {
		HttpRequest request = new HttpGet("http://hk.finance.yahoo.com/d/quote.csv?s=0005.HK&f=sl1d1t1c1ohgv");
        HttpClient client = new DefaultHttpClient();  
		try {
			 HttpResponse response = client.execute((HttpUriRequest) request);
             String value = EntityUtils.toString(response.getEntity());
			 client.getConnectionManager().shutdown();
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return "NA";
	}
	
}
