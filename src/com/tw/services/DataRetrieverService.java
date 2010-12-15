package com.tw.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import android.util.Log;

public class DataRetrieverService {
	
	Map<String,String> companyNameSymbolMap = new TreeMap<String,String>();
	
	
	public String getQuote(String symbol) {
		//example : "http://finance.yahoo.com/d/quote.csv?s=SATYAMCOM.NS&f=sl1d1t1c1ohgv
        return sendHttpRequest("http://finance.yahoo.com/d/quote.csv?s="+symbol+"&f=sl1d1t1c1ohgv");
    }
	
	public String getSymbol(String companyName) {
		String response = sendHttpRequest("http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="+companyName+"&callback=YAHOO.Finance.SymbolSuggest.ssCallback");
		if(response != null && (!response.trim().equals("")) &&response.contains("\"Result\":")) {
			JSONTokener jsonTokener = new JSONTokener(response.substring(response.indexOf("(")+1,response.lastIndexOf(")")));
			try {
				JSONArray nextValue = ((JSONObject) ((JSONObject) jsonTokener.nextValue()).get("ResultSet")).getJSONArray("Result");
				Log.d("DRS", nextValue.toString());
				//TODO Send only BSE and NSE symbols

				return nextValue.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "N/A";
	}

	public String[] getCompanyNames(String companyName) {
		
		List<String> companyList = new ArrayList<String>();
		
		String response = sendHttpRequest("http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="+companyName+"&callback=YAHOO.Finance.SymbolSuggest.ssCallback");
		if(response != null && (!response.trim().equals("")) && response.contains("\"Result\":")) {
			String jsonString = response.substring(response.indexOf("["),response.lastIndexOf("]")+1);
			Log.d("jsonString",jsonString.toString());
			try {
				JSONArray jsonArray = new JSONArray(jsonString);
				Log.d("array",jsonArray.toString());
				for(int i=0; i<jsonArray.length(); i++) {
					companyList.add(jsonArray.getJSONObject(i).getString("name"));
					Log.d("index_"+i,jsonArray.getJSONObject(i).getString("name"));
					//companyNameSymbolMap.put(((JSONObject)jsonArray.get(i)).getString("company"),"symbol");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return companyList.toArray(new String[0]);
	}
	
	
	private String sendHttpRequest(String URL) {
		HttpRequest request = new HttpGet(URL);
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
