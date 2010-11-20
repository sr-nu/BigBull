package com.tw.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tw.activities.R;
import com.tw.services.DataRetrieverService;

public class Home extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        
        Button getQuote = (Button)findViewById(R.id.find_quote);        
        getQuote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new UpdateQuoteTask().execute(((EditText)findViewById(R.id.stock_symbol)).getText().toString());
			}
		});    
    }
    
    
     class UpdateQuoteTask extends AsyncTask<String, String, String> {
        protected void onProgressUpdate(Integer... progress) {
        	TextView quote = (TextView)findViewById(R.id.price);
            quote.setText(progress[0]);
        }

        protected void onPostExecute(String result) {
        	TextView quote = (TextView)findViewById(R.id.price);
            quote.setText(result); 
        }

		@Override
		protected String doInBackground(String... params) {
			return (new DataRetrieverService().getQuote(params[0]));
		}
    }
        
}

