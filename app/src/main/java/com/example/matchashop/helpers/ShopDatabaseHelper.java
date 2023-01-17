package com.example.matchashop.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShopDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DISCOUNT_LIST.DB";
    private static final int DB_VERSION = 1;


    public static final String TABLE_NAME = "Discount";
    public static final String ID = "id";
    public static final String DISCOUNT_NAME = "DiscountName";
    public static final String DISCOUNT_CODE = "DiscountCode";
    public static final String DISCOUNT_DESCRIPTION = "DiscountDescription";
    public static final String DISCOUNT_VALUE = "DiscountValue";
    public static final String DISCOUNT_STARTDATE = "DiscountStartDate";
    public static final String DISCOUNT_ENDDATE = "DiscountEndDate";
    public static final String DISCOUNT_PRODUCTS = "DiscountProducts";
    public static final String DISCOUNT_REOCCURING = "DiscountReoccuring";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DISCOUNT_NAME + " TEXT NOT NULL," + DISCOUNT_CODE + " TEXT," + DISCOUNT_DESCRIPTION + " TEXT," + DISCOUNT_VALUE + " TEXT NOT NULL," + DISCOUNT_STARTDATE + "TEXT NOT NULL," + DISCOUNT_ENDDATE + "TEXT NOT NULL," + DISCOUNT_PRODUCTS + "TEXT NOT NULL," + DISCOUNT_REOCCURING + "TEXT NOT NULL" + ");";

    public ShopDatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
