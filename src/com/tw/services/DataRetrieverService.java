package com.tw.services;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.tw.utils.NetworkUtils;
import com.tw.utils.URLUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DataRetrieverService {
	
	Map<String,String> companyNameSymbolMap = new TreeMap<String,String>();
    NetworkUtils networkUtils = new NetworkUtils();
	
	public String getQuote(String symbol) throws IOException {
		//example : "http://finance.yahoo.com/d/quote.csv?s=SATYAMCOM.NS&f=sl1d1t1c1ohgv
        return networkUtils.sendHttpRequest(URLUtils.getGetQuoteURL(symbol));
    }
	
	public String getSymbol(String companyName) throws IOException {
        String response = networkUtils.sendHttpRequest(URLUtils.getCompanyUrl(companyName));
		if(response != null && (!response.trim().equals("")) &&response.contains("\"Result\":")) {
			JSONTokener jsonTokener = new JSONTokener(response.substring(response.indexOf("(")+1,response.lastIndexOf(")")));
			try {
				JSONArray nextValue = ((JSONObject) ((JSONObject) jsonTokener.nextValue()).get("ResultSet")).getJSONArray("Result");
				//TODO Send only BSE and NSE symbols

				return nextValue.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "N/A";
	}

	public String[] getCompanyNames(String companyName) throws IOException {
        String response = networkUtils.sendHttpRequest(URLUtils.getCompanyUrl(companyName));
		if(response != null && (!response.trim().equals("")) &&response.contains("\"Result\":")) {
			JSONTokener jsonTokener = new JSONTokener(response.substring(response.indexOf("(")+1,response.lastIndexOf(")")));
			try {
				JSONArray nextValue = ((JSONObject) ((JSONObject) jsonTokener.nextValue()).get("ResultSet")).getJSONArray("Result");
				
				
				for(int i=0; i<nextValue.length(); i++) {
					companyNameSymbolMap.put(((JSONObject)nextValue.get(i)).getString("company"),"symbol");
				}
				
				return companyNameSymbolMap.keySet().toArray(new String[0]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return new String[]{};
	}
}
