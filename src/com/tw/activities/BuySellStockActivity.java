package com.tw.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuySellStockActivity extends Activity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_sell);

		TextView companyName = (TextView) findViewById(R.id.CompanyName);
		companyName.setText(String.valueOf(getIntent().getExtras().get("com.tw.activities.CompanyName")));
		
		String code = String.valueOf(getIntent().getExtras().get("com.tw.activities.CompanySymbol"));
		String rate = String.valueOf(getIntent().getExtras().get("com.tw.activities.Rate"));
		
		TextView rateTxt = (TextView) findViewById(R.id.RateTxt);
		rateTxt.setText(rate);
		
		
		
		
		
		
		
	}
}
