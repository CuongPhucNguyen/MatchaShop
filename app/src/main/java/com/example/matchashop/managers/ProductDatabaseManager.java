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

    public void generateData()
    {
        //Electronic
        this.insert("Iphone 14 Pro Max", "https://imgs.viettelstore.vn/Images/Product/ProductImage/dien-thoai/Apple/iPhone%2014%20Pro%20Max%20128/iPhone-14-Pro-Max-3.jpg", 1200, "256 GB Model");
        this.insert("Airpods Pro", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/2022201914.jpeg", 250, "Bluetooth earphone");
        this.insert("S22 Ultra", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/3106108.jpeg", 1300, "Good phone");
        this.insert("A73 5G", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/2055015872.jpeg", 450, "Cheap phone");
        this.insert("Galaxy Buds 2 Pro", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/1551107182.jpeg",  150, "Super good earphone");
        this.insert("Macbook Pro M2", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/141348973.jpeg", 1800, "Powerful Mac");
        this.insert("Apple Watch SE", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/474951337.jpeg", 400, "Apple Smart Watch");
        this.insert("Galaxy Watch5", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/711970888.jpeg", 300, "Samsung Smart Watch");
        this.insert("Iphone 14 Plus", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/2056439452.jpeg", 900, "Cheaper Iphone");
        this.insert("Macbook Air M2", "https://cdn1.viettelstore.vn/Images/Product/ProductImage/869870060.jpeg",1300, "Light and thin Mac");
        //General Goods
        this.insert("Male Grey Hoody", "https://cf.shopee.vn/file/53a99ab959a37a737daf8150aaf96a5b", 6, "Grey hoody for man");
        this.insert("Stainless Steel Pan", "https://cf.shopee.vn/file/sg-11134201-22100-cq7dljnl84iv71", 20, "Stainless steel cooking pot");
        this.insert("Purse", "https://cf.shopee.vn/file/2bc2a52826e0eb179bd727f180cf14eb", 25, "A long wallet");
        this.insert("Bitis Sandal", "https://cf.shopee.vn/file/ddd24e906688b21756ee3161209cd0b7", 22, "Sandal for men");
        this.insert("Flower Pillow", "https://cf.shopee.vn/file/841b55cbd78846e35853d544cb2dbc07", 3, "A flower pillow for your couch");
        this.insert("Baseus Compact Charger", "https://cf.shopee.vn/file/d9e84dc1e03e9d25f877deb3bae4290e", 6, "A compact charger for your phone");
        this.insert("Dog rain poncho", "https://cf.shopee.vn/file/631a5f24aedd1eeb1c7c83ba6de28074", 2, "A poncho for your dog");
        this.insert("Dust cleaning robot", "https://cf.shopee.vn/file/cbe600c23e88c24bfb43fdc0efe3c175", 4, "A dust cleaning robot for when you are lazy to clean your home");
        this.insert("Bluetooth Speaker", "https://cf.shopee.vn/file/94d2a0fa052169b885cfdbdaee6c1849", 8, "Bluetooth speaker");
        this.insert("USB microphone", "https://cf.shopee.vn/file/659afe857af1a9b53e16d21b8f17aaaf",30, "USB Microphone");
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
