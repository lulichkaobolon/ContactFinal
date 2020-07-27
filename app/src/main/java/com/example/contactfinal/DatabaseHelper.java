package com.example.contactfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ContactsInfo.db";
    public static final String TABLE_NAME = "contacts_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "GENDER";
    public static final String COL_5 = "CITY";
    //Integer index = MainActivity.selectedID;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+
                "(ID INTEGER PRIMARY KEY, EMAIL TEXT, DATE TEXT, GENDER TEXT, CITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData (Integer index, String email, String date, String gender, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, index + 1);
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, gender);
        contentValues.put(COL_5, city);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public boolean updateData (String email, String date, String gender, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, MainActivity.selectedID + 1);
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, gender);
        contentValues.put(COL_5, city);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {(MainActivity.selectedID+1)+""} );
        return true;
    }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
