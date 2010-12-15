package com.tw.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.tw.services.DataRetrieverService;

public class Home extends Activity {
	private ArrayAdapter<String> adapter;
	private final ArrayList<String> companies = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);

		AutoCompleteTextView textBox = (AutoCompleteTextView) findViewById(R.id.stock_symbol);
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, companies);
		adapter.setNotifyOnChange(true);
		textBox.setAdapter(adapter);

		//attach listener to fetch company names as the user types
		textBox.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//TODO the service is getting called on every key press how to optimise this new
				new UpdateCompanyNamesTask().execute(((TextView) v).getText().toString());
				return false;
			}
		});

		/*//can use the above on key listener or this text changed listener
		 * 
		 * textBox.addTextChangedListener(new TextWatcher() {
		 * @Override public void afterTextChanged(Editable s) {
		 * if(s.toString().length()>2) { 
		 * //TODO the service is getting called on everykey press how to optimise this new
		 * UpdateQuoteTask().execute(s.toString()); } }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) { }
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { } });
		 */

		textBox.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String companySelected = ((TextView)arg1).getText().toString();
				new GetQuoteTask().execute(companySelected.substring(companySelected.lastIndexOf('#')+1));
			}
		});
		
		
		
		Button getQuote = (Button) findViewById(R.id.find_quote);
		getQuote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new GetQuoteTask().execute(((TextView)v).getText().toString().substring(((TextView)v).getText().toString().lastIndexOf('#')+1));
			}
		});
		
		
		Button buy = (Button)findViewById(R.id.buy_home);
        buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), BuySellStockActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
		
		
		
	}

	class GetQuoteTask extends AsyncTask<String, String, String>{
		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String result) {
			TextView quote = (TextView) findViewById(R.id.price);
			quote.setText(result);
		}
		
		@Override
		protected String doInBackground(String... params) {
			return (new DataRetrieverService().getQuote(params[0]));
		}
		
	}
	
	
	
	
	class UpdateCompanyNamesTask extends AsyncTask<String, String[], String[]> {
		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String[] result) {
			
			if (result != null && result.length > 0) {
				adapter.setNotifyOnChange(false);
				adapter.clear();
				for (int i = 0; i < result.length; i++) {
					adapter.add(result[i]);
				}
				adapter.notifyDataSetChanged();
			} else
				adapter.notifyDataSetInvalidated();
		}

		@Override
		protected String[] doInBackground(String... params) {
			// return (new DataRetrieverService().getQuote(params[0]));
			// return (new DataRetrieverService().getSymbol(params[0]));
			return (new DataRetrieverService().getCompanyNames(params[0]));
		}
	}
}
