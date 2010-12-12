package com.tw.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String databaseName = "stocks";
	private static int version = 1;
	public static final String PORTFOLIO_TABLE = "portfolio";
    public static final String SYMBOL_TABLE = "symbol_map";
    
    
    //TODO: need to find a way to populate the DB with all symbols
    private static final String[][] symbol_data = {
            { "INFOSYS Technologies", "INFY.NSE", "INFOSYS.BSE" },
            { "Reliance", "RIL.NSE", "RIL.BSE" },
            { "HCL INFOSYSTEMS", "HCL.NSE", "HCL.BSE" }
    };
    
	
	DatabaseHelper(Context context) {
        super(context, databaseName, null, version);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		 final String DATABASE_CREATE_PORTFOLIO =
             "create table " + PORTFOLIO_TABLE
                     + "(_id integer primary key autoincrement"
                     + ", qty integer not null"
                     + ", unit_cost real not null"
                     + ", profit real not null)";

		 final String DATABASE_CREATE_SYMBOL_TABLE =
             "create table " + SYMBOL_TABLE
                     + "(_id integer primary key autoincrement"
                     + ", company text not null"
                     + ", nse text"
                     + ", bse text)";

     db.execSQL(DATABASE_CREATE_SYMBOL_TABLE);
     db.execSQL(DATABASE_CREATE_PORTFOLIO);
     
     
     
	}

	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PORTFOLIO_TABLE);
        onCreate(db);
    }
	
	
    /**
     * Populates the database with data on company name, bse, nse symbols
     * 
     * @param db
     *            The database to be populated; must have the appropriate table
     *            ("symbol_map") and columns (company , nse, bse) already set up.
     */
    private void populateWithData(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            // Populate the database with the data
            for (String[] s : symbol_data) {
            	values.put("company", s[0]);
                values.put("nse", s[1]);
                values.put("bse", s[2]);

                db.insert(SYMBOL_TABLE, "symbol_data", values);
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
}
