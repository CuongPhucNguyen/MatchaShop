package com.example.matchashop.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ProductDB";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Product";
    public static final String ID = "id";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_PHOTO_TITLE = "productPhotoTitle";
    public static final String PRODUCT_PRICE = "productPrice";
    public static final String PRODUCT_DESCRIPTION = "productDescription";
    private static final String CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PRODUCT_NAME + " TEXT NOT NULL," +
                    PRODUCT_PHOTO_TITLE + " TEXT NOT NULL," +
                    PRODUCT_PRICE + " DOUBLE NOT NULL," +
                    PRODUCT_PHOTO_TITLE + " TEXT NOT NULL" +
                    ");";
    public ProductDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
