package com.tw.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;

import com.tw.services.AutoCompleteDbAdapter;

public class SelectCompany extends Activity {

    final static int[] to = new int[] { android.R.id.text1 };
    final static String[] from = new String[] { "company" };

    private TextView nseSymbol;
    private AutoCompleteTextView companyNameView;
    private AutoCompleteDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new AutoCompleteDbAdapter(this);
        setContentView(R.layout.selectcompany);
        Button confirmButton = (Button) findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        nseSymbol = (TextView) findViewById(R.id.nse_symbol);
        companyNameView = (AutoCompleteTextView) findViewById(R.id.company_name);

        // Create a SimpleCursorAdapter for the State Name field.
        SimpleCursorAdapter adapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);
        companyNameView.setAdapter(adapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        companyNameView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String nse = 
                    cursor.getString(cursor.getColumnIndexOrThrow("nse"));

                // Update the parent class's TextView
                nseSymbol.setText(nse);
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        adapter.setCursorToStringConverter(new CursorToStringConverter() {
            public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                final int columnIndex = cursor.getColumnIndexOrThrow("company");
                final String str = cursor.getString(columnIndex);
                return str;
            }
        });

        // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                Cursor cursor = mDbHelper.getMatchingCompanies(
                        (constraint != null ? constraint.toString() : null));
                return cursor;
            }
        });
    }
}
