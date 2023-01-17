package com.example.matchashop.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matchashop.models.User;
import com.example.matchashop.helpers.UserDatabaseHelper;

import java.sql.SQLException;

public class UserDatabaseManager {
    private UserDatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public UserDatabaseManager(Context c){context = c;}

    public UserDatabaseManager open() throws SQLException {
        dbHelper = new UserDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        dbHelper.close();
    }

    public void insert(User input) throws SQLException{
        updateOtherLogin(input,false);
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDatabaseHelper.USERNAME,input.Username);
        contentValues.put(UserDatabaseHelper.PASSWORD,(input.savePassword ? input.Password : ""));
        contentValues.put(UserDatabaseHelper.LOGIN,input.loggedIn);
        contentValues.put(UserDatabaseHelper.SAVE_PASSWORD, String.valueOf(input.savePassword));
        database.insertOrThrow(UserDatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public int update( long _id, User input){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDatabaseHelper.USERNAME, input.Username);
        contentValues.put(UserDatabaseHelper.PASSWORD, (input.savePassword ? input.Password : ""));
        contentValues.put(UserDatabaseHelper.LOGIN,input.loggedIn);
        contentValues.put(UserDatabaseHelper.SAVE_PASSWORD, String.valueOf(input.savePassword));
        int i = database.update(UserDatabaseHelper.TABLE_NAME, contentValues, UserDatabaseHelper.ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id){
        database.delete(UserDatabaseHelper.TABLE_NAME, UserDatabaseHelper.ID + "=" + _id, null);
    }

    public void deleteByName(String name){
        database.delete(UserDatabaseHelper.TABLE_NAME, UserDatabaseHelper.USERNAME + "=\'" + name + "\'", null);
    }



    public void updateOtherLogin(User input, boolean status){
        if (input.loggedIn){
            Cursor temp = fetchLoggedIn();
            while (temp.getCount() > 0) {
                User user = new User(temp.getString(1), temp.getString(2), Boolean.parseBoolean(temp.getString(4)), status);
                update(Long.parseLong(temp.getString(0)), user);
                if(!temp.moveToNext()){
                    break;
                }
            }
        }
    }


    public Cursor fetch(){
        String[] columns = new String[]{
                UserDatabaseHelper.ID,
                UserDatabaseHelper.USERNAME,
                UserDatabaseHelper.PASSWORD,
                UserDatabaseHelper.LOGIN,
                UserDatabaseHelper.SAVE_PASSWORD
        };
        Cursor cursor = database.query(UserDatabaseHelper.TABLE_NAME,columns,null,null,null,null,UserDatabaseHelper.LOGIN + " ASC, " + UserDatabaseHelper.PASSWORD  + " DESC");
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchLoggedIn(){
        String[] columns = new String[]{
                UserDatabaseHelper.ID,
                UserDatabaseHelper.USERNAME,
                UserDatabaseHelper.PASSWORD,
                UserDatabaseHelper.LOGIN,
                UserDatabaseHelper.SAVE_PASSWORD
        };
        Cursor cursor = database.query(UserDatabaseHelper.TABLE_NAME,columns, UserDatabaseHelper.LOGIN + " = 1",null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }




    public Cursor fetchByName(String name){
        String[] columns = new String[]{
                UserDatabaseHelper.ID,
                UserDatabaseHelper.USERNAME,
                UserDatabaseHelper.PASSWORD,
                UserDatabaseHelper.LOGIN,
                UserDatabaseHelper.SAVE_PASSWORD
        };
        Cursor cursor = database.query(UserDatabaseHelper.TABLE_NAME,columns,"Username = " + "\'" + name + "\'",null,null,null,"Username");
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }



    public static boolean checkIfExisted(Cursor input){
        return input.getCount()>=0;
    }



}
