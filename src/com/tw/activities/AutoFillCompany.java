package com.tw.activities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.tw.services.DataRetrieverService;

public class AutoFillCompany extends Activity{
	
	List<String> companies = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autofill_company);
        
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.Auto_company);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, companies);
        textView.setAdapter(adapter);
        
        textView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				new UpdateCompanyTask().execute(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
        	
        });
        
        
    }
    
    
   class UpdateCompanyTask extends AsyncTask<String, String[], String[]> {
        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String[] result) {
        	
        	companies.addAll(Arrays.asList(result));
        	adapter.notifyDataSetChanged();
        }

		@Override
		protected String[] doInBackground(String... params) {
			//return (new DataRetrieverService().getQuote(params[0]));
			companies.clear();
			return (new DataRetrieverService()).getCompanyNames(params[0]);
		}
    }
    
 
    
    
}
