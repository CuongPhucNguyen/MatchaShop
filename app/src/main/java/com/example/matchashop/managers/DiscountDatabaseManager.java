package com.example.matchashop.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matchashop.helpers.DiscountDatabaseHelper;
import com.example.matchashop.models.DiscountModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class DiscountDatabaseManager {

    private DiscountDatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DiscountDatabaseManager(Context c){context = c;}

    public DiscountDatabaseManager open() throws SQLException {
        dbHelper = new DiscountDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        dbHelper.close();
    }

    public void insert(DiscountModel input) throws SQLException{
        ContentValues contentValues = new ContentValues();
        database.insertOrThrow(DiscountDatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public int update( long _id, DiscountModel input) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_NAME, input.DiscountName);
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_CODE, input.DiscountCode);
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_DESCRIPTION, input.DiscountDescription);
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_VALUE, input.DiscountValue);
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_ENDDATE, input.DiscountEndDate);
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_STARTDATE, input.DiscountStartDate);
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_REOCCURING, input.Reoccuring);
        String products = "";
        try {
            JSONObject arrayContainer = new JSONObject();
            arrayContainer.put("DiscountProducts", input.DiscountProducts);
            products = arrayContainer.toString();
        }
        catch (Exception e){
            Log.println(Log.ERROR, "Error", e.getMessage());
        }
        contentValues.put(DiscountDatabaseHelper.DISCOUNT_PRODUCTS, products);
        int i = database.update(DiscountDatabaseHelper.TABLE_NAME, contentValues, DiscountDatabaseHelper.ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id){
        database.delete(DiscountDatabaseHelper.TABLE_NAME, DiscountDatabaseHelper.ID + "=" + _id, null);
    }

    public void deleteByName(String name){
        database.delete(DiscountDatabaseHelper.TABLE_NAME, DiscountDatabaseHelper.DISCOUNT_NAME + "=\'" + name + "\'", null);
    }




    public Cursor fetch(){
        String[] columns = new String[]{
                DiscountDatabaseHelper.ID,
                DiscountDatabaseHelper.DISCOUNT_NAME,
                DiscountDatabaseHelper.DISCOUNT_CODE,
                DiscountDatabaseHelper.DISCOUNT_DESCRIPTION,
                DiscountDatabaseHelper.DISCOUNT_VALUE,
                DiscountDatabaseHelper.DISCOUNT_STARTDATE,
                DiscountDatabaseHelper.DISCOUNT_ENDDATE,
                DiscountDatabaseHelper.DISCOUNT_PRODUCTS,
                DiscountDatabaseHelper.DISCOUNT_REOCCURING
        };
        Cursor cursor = database.query(DiscountDatabaseHelper.TABLE_NAME,columns,null,null,null,null,DiscountDatabaseHelper.DISCOUNT_NAME + " ASC, " + DiscountDatabaseHelper.DISCOUNT_VALUE  + " DESC," + DiscountDatabaseHelper.DISCOUNT_CODE + " ASC," + DiscountDatabaseHelper.DISCOUNT_ENDDATE + " ASC");
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchByName(String name){
        String[] columns = new String[]{
                DiscountDatabaseHelper.ID,
                DiscountDatabaseHelper.DISCOUNT_NAME,
                DiscountDatabaseHelper.DISCOUNT_CODE,
                DiscountDatabaseHelper.DISCOUNT_DESCRIPTION,
                DiscountDatabaseHelper.DISCOUNT_VALUE,
                DiscountDatabaseHelper.DISCOUNT_STARTDATE,
                DiscountDatabaseHelper.DISCOUNT_ENDDATE,
                DiscountDatabaseHelper.DISCOUNT_PRODUCTS,
                DiscountDatabaseHelper.DISCOUNT_REOCCURING
        };
        Cursor cursor = database.query(DiscountDatabaseHelper.TABLE_NAME,columns, DiscountDatabaseHelper.DISCOUNT_NAME + " =\'" + name + "\'",null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor fetchByCode(String code){
        String[] columns = new String[]{
                DiscountDatabaseHelper.ID,
                DiscountDatabaseHelper.DISCOUNT_NAME,
                DiscountDatabaseHelper.DISCOUNT_CODE,
                DiscountDatabaseHelper.DISCOUNT_DESCRIPTION,
                DiscountDatabaseHelper.DISCOUNT_VALUE,
                DiscountDatabaseHelper.DISCOUNT_STARTDATE,
                DiscountDatabaseHelper.DISCOUNT_ENDDATE,
                DiscountDatabaseHelper.DISCOUNT_PRODUCTS,
                DiscountDatabaseHelper.DISCOUNT_REOCCURING
        };
        Cursor cursor = database.query(DiscountDatabaseHelper.TABLE_NAME,columns, DiscountDatabaseHelper.DISCOUNT_CODE + " =\'" + code + "\'",null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }


    public static boolean checkIfExisted(Cursor input){
        return input.getCount()>=0;
    }



}
