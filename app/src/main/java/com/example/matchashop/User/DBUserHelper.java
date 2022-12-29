package com.example.matchashop.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DBUserHelper {
    private UserDatabase dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DBUserHelper(Context c){context = c;}

    public DBUserHelper open() throws SQLException {
        dbHelper = new UserDatabase(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        dbHelper.close();
    }

    public void insert(User input) throws SQLException{

        updateOtherLogin(input,false);
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDatabase.USERNAME,input.Username);
        contentValues.put(UserDatabase.PASSWORD,(input.savePassword ? input.Password : ""));
        contentValues.put(UserDatabase.LOGIN,input.loggedIn);
        contentValues.put(UserDatabase.SAVE_PASSWORD, String.valueOf(input.savePassword));
        database.insertOrThrow(UserDatabase.TABLE_NAME, null, contentValues);
    }

    public int update( long _id, User input){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDatabase.USERNAME, input.Username);
        contentValues.put(UserDatabase.PASSWORD, (input.savePassword ? input.Password : ""));
        contentValues.put(UserDatabase.LOGIN,input.loggedIn);
        contentValues.put(UserDatabase.SAVE_PASSWORD, String.valueOf(input.savePassword));
        int i = database.update(UserDatabase.TABLE_NAME, contentValues, UserDatabase.ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id){
        database.delete(UserDatabase.TABLE_NAME, UserDatabase.ID + "=" + _id, null);
    }

    public void deleteByName(String name){
        database.delete(UserDatabase.TABLE_NAME, UserDatabase.USERNAME + "=\'" + name + "\'", null);
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
                UserDatabase.ID,
                UserDatabase.USERNAME,
                UserDatabase.PASSWORD,
                UserDatabase.LOGIN,
                UserDatabase.SAVE_PASSWORD
        };
        Cursor cursor = database.query(UserDatabase.TABLE_NAME,columns,null,null,null,null,UserDatabase.LOGIN + " ASC, " + UserDatabase.PASSWORD  + " DESC");
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchLoggedIn(){
        String[] columns = new String[]{
                UserDatabase.ID,
                UserDatabase.USERNAME,
                UserDatabase.PASSWORD,
                UserDatabase.LOGIN,
                UserDatabase.SAVE_PASSWORD
        };
        Cursor cursor = database.query(UserDatabase.TABLE_NAME,columns, UserDatabase.LOGIN + " = 1",null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }




    public Cursor fetchByName(String name){
        String[] columns = new String[]{
                UserDatabase.ID,
                UserDatabase.USERNAME,
                UserDatabase.PASSWORD,
                UserDatabase.LOGIN,
                UserDatabase.SAVE_PASSWORD
        };
        Cursor cursor = database.query(UserDatabase.TABLE_NAME,columns,"Username = " + "\'" + name + "\'",null,null,null,"Username");
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }



    public static boolean checkIfExisted(Cursor input){
        return input.getCount()>=0;
    }



}
