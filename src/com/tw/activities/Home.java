package com.tw.activities;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        textBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length()>2) {
					new UpdateQuoteTask().execute(s.toString());
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
        });
        
        Button getQuote = (Button)findViewById(R.id.find_quote);        
        getQuote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new UpdateQuoteTask().execute(((EditText)findViewById(R.id.stock_symbol)).getText().toString());
			}
		});    
    }
    
    
     class UpdateQuoteTask extends AsyncTask<String, String[], String[]> {
        protected void onProgressUpdate(Integer... progress) {
        	TextView quote = (TextView)findViewById(R.id.price);
            quote.setText(progress[0]);
        }

        protected void onPostExecute(String[] result) {
        	TextView quote = (TextView)findViewById(R.id.price);
        	if(result!=null && result.length > 0) {
        	    adapter.setNotifyOnChange(false);
        	    for (int i = 0; i < result.length; i++) {
					adapter.add(result[i]);
				}
        		adapter.notifyDataSetChanged();
        	}
        	else
        		quote.setText("NA");
        }

		@Override
		protected String[] doInBackground(String... params) {
			//return (new DataRetrieverService().getQuote(params[0]));
			//return (new DataRetrieverService().getSymbol(params[0]));
			return (new DataRetrieverService().getCompanyNames(params[0]));
		}
    }
}

