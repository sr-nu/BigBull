package com.tw.services;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple database access helper class.
 * 
 * @author Dan Breslau
 */
public class AutoCompleteDbAdapter {
    /**
     * List of states and capitals.
     */
    private static final String[][] symbol_data = {
            { "INFOSYS Technologies", "INFY.NSE", "INFOSYS.BSE" },
            { "Reliance", "RIL.NSE", "RIL.BSE" },
            { "HCL INFOSYSTEMS", "HCL.NSE", "HCL.BSE" }
    };

    private static final String DATABASE_NAME = "stocks";
    private static final String TABLE_NAME = "symbol_map";
    private static final int DATABASE_VERSION = 1;

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String DATABASE_CREATE_STATES =
                    "create table " + TABLE_NAME
                            + "(_id integer primary key autoincrement"
                            + ", company text not null"
                            + ", nse text"
                            + ", bse text)";

            db.execSQL(DATABASE_CREATE_STATES);
            populateWithData(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Activity mActivity;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param activity
     *            the Activity that is using the database
     */
    public AutoCompleteDbAdapter(Activity activity) {
        this.mActivity = activity;
        mDbHelper = this.new DatabaseHelper(activity);
        mDb = mDbHelper.getWritableDatabase();
    }

    /**
     * Closes the database.
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Return a Cursor that returns all companies (and their symbols) where
     * the company name begins with the given constraint string.
     * 
     * @param constraint
     *            Specifies the first letters of the companies to be listed. If
     *            null, all rows are returned.
     * @return Cursor managed and positioned to the first company, if found
     * @throws SQLException
     *             if query fails
     */
    public Cursor getMatchingCompanies(String constraint) throws SQLException {

        String queryString =
                "SELECT _id, company, nse, bse FROM " + TABLE_NAME;

        if (constraint != null) {
            // Query for any rows where the company name begins with the
            // string specified in constraint.
            //
            // NOTE:
            // If wildcards are to be used in a rawQuery, they must appear
            // in the query parameters, and not in the query string proper.
            // See http://code.google.com/p/android/issues/detail?id=3153
            constraint = constraint.trim() + "%";
            queryString += " WHERE company LIKE ?";
        }
        String params[] = { constraint };

        if (constraint == null) {
            // If no parameters are used in the query,
            // the params arg must be null.
            params = null;
        }
        try {
            Cursor cursor = mDb.rawQuery(queryString, params);
            if (cursor != null) {
                this.mActivity.startManagingCursor(cursor);
                cursor.moveToFirst();
                return cursor;
            }
        }
        catch (SQLException e) {
            Log.e("AutoCompleteDbAdapter", e.toString());
            throw e;
        }

        return null;
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

                db.insert(TABLE_NAME, "symbol_data", values);
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

}
