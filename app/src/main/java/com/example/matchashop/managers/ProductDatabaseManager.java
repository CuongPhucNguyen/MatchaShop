package com.example.matchashop.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.matchashop.helpers.ProductDatabaseHelper;

public class ProductDatabaseManager {

    private ProductDatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase  database;

    public ProductDatabaseManager(Context c){
        context = c;
    }

    public ProductDatabaseManager open() throws SQLException {
        dbHelper = new ProductDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void insert(String productName, String productPhotoTitle, double productPrice, String productDes){
        ContentValues contentValue = new ContentValues();
        contentValue.put(ProductDatabaseHelper.PRODUCT_NAME, productName);
        contentValue.put(ProductDatabaseHelper.PRODUCT_PHOTO_TITLE, productPhotoTitle);
        contentValue.put(ProductDatabaseHelper.PRODUCT_PRICE, productPrice);
        contentValue.put(ProductDatabaseHelper.PRODUCT_DESCRIPTION, productDes);
        database.insert(ProductDatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public int update(long _id, String productName, String productPhotoTitle, double productPrice, String productDes){
        ContentValues contentValue = new ContentValues();
        contentValue.put(ProductDatabaseHelper.PRODUCT_NAME, productName);
        contentValue.put(ProductDatabaseHelper.PRODUCT_PHOTO_TITLE, productPhotoTitle);
        contentValue.put(ProductDatabaseHelper.PRODUCT_PRICE, productPrice);
        contentValue.put(ProductDatabaseHelper.PRODUCT_DESCRIPTION, productDes);

        int i = database.update(ProductDatabaseHelper.TABLE_NAME,
                contentValue,
                ProductDatabaseHelper.ID + " =" + _id, null);
        return i;
    }

    public void delete(long _id){
        database.delete(ProductDatabaseHelper.TABLE_NAME,
                ProductDatabaseHelper.ID + " =" + _id, null);
        // When deleting or adding rows with AUTOINCREMENT, use this to
        // reserve the biggest primary key in the table
        database.delete("SQLITE_SEQUENCE", "NAME = ?", new String[]{
                ProductDatabaseHelper.TABLE_NAME});
    }

    public Cursor selectAll(){
        String [] columns = new String[] {
                ProductDatabaseHelper.ID,
                ProductDatabaseHelper.PRODUCT_NAME,
                ProductDatabaseHelper.PRODUCT_PHOTO_TITLE,
                ProductDatabaseHelper.PRODUCT_PRICE,
                ProductDatabaseHelper.PRODUCT_DESCRIPTION
        };
        Cursor cursor = database.query(ProductDatabaseHelper.TABLE_NAME, columns,
                null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
