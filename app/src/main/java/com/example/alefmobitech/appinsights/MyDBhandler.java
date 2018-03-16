package com.example.alefmobitech.appinsights;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.widget.Toast;


/**
 * Created by alefmobitech on 20/6/17.
 */

public class MyDBhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    public static final String TABLE_USER = "user";
    public static String COLUMN_KEY_VALUE = "key_value";
    public static final String COLUMN_VALUE = "value";


    public MyDBhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_USER + "("+
                COLUMN_KEY_VALUE + " TEXT " +","+
                COLUMN_VALUE + " TEXT " +
                ");";

        db.execSQL(query);

        //   db.execSQL("DROP TABLE " + TABLE_USER);
        //   System.out.println("deleted table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER );
            onCreate(db);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean addData(UserData userData){


        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_KEY_VALUE, userData.getKey_value());
        contentValues.put(COLUMN_VALUE, userData.getValue());
        SQLiteDatabase db = getWritableDatabase();

        long result = db.insert(TABLE_USER, null, contentValues);
        db.close();

        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }


    public String getAllData(String key_vale){
        SQLiteDatabase db = getWritableDatabase();

        if (isTableExists(TABLE_USER, true)){

            Cursor res = db.rawQuery("SELECT "+ COLUMN_VALUE +" FROM "+ TABLE_USER +" WHERE " + COLUMN_KEY_VALUE + "= '" + key_vale+ "';", null);

            if (res.moveToFirst()){
                return res.getString(res.getColumnIndex("value"));
            }
            else {
                return null;

            }

        }
        else{
            return null;
        }
    }





    public void update (String key_val, String val)
    {
//        SQLiteDatabase db = getWritableDatabase();
        //String qury =
        //  db.execSQL("UPDATE " + TABLE_USER + " SET " + COLUMN_VALUE + "=\"" + val + " WHERE "+ COLUMN_KEY_VALUE + "=\"" + key_val + "\";");
    }

    public String databaseToString(String key_val){
        String value = null;
        SQLiteDatabase db = getWritableDatabase();

        if (isTableExists(TABLE_USER, true)){
            String qurry = "SELECT " + COLUMN_VALUE +  " FROM " + TABLE_USER + " WHERE " + COLUMN_KEY_VALUE + " =\" " + key_val+ "\";";
            Cursor cursor = db.rawQuery(qurry, null);
            //cursor.moveToFirst();

            // while ( !cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex("key_value"))!= null){
                value = cursor.getString(cursor.getColumnIndex("value"));

            }
            //}
            db.close();
            return value;
        }
        else{
            return null;
        }



    }


    public boolean updatetodb(String key_value, String value){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        // UserData userData = new UserData();


        contentValues.put(COLUMN_KEY_VALUE, key_value);

        contentValues.put(COLUMN_VALUE, value);

        db.update(TABLE_USER, contentValues, COLUMN_KEY_VALUE +" = "+ "'"+key_value +"';" , null);

        return true;

    }


    public boolean isTableExists(String tableName, boolean openDb) {

        SQLiteDatabase mDatabase = null;

        if(openDb) {

            if(mDatabase == null || !mDatabase.isOpen()) {
                mDatabase = getReadableDatabase();
            }

            if(!mDatabase.isReadOnly()) {
                //mDatabase.close();
                mDatabase = getReadableDatabase();
            }
        }

        Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
