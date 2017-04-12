package com.example.em9310li.yourdailyinfo;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EM9310LI on 2017-03-06.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";

    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    //Create table
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    //Drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    //Constructor
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL(DROP_USER_TABLE);
        onCreate(database);
    }
    //Metod för att skapa en User record
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, user.getName());
        contentValues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, contentValues);
        db.close();
    }
    // denna metod är för att hämta alla users och lista dem.
    public List<User> getAllUsers(){
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_NAME,
                COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD
        };
        String sortOrder = COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //denna query function hämtar alla users table och denna fukction fungerar som en sql query
        //
        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        //går igenom listan och lägger till
        if(cursor.moveToFirst()){
            do{
                User user = new User();
                    user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                    user.setName((cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))));
                    user.setEmail((cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))));
                    user.setPassword((cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))));
                //Här läggs user till i listan

                userList.add(user);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // returnerar listan
        return userList;
    }

    public List<User> getUser(){
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_NAME,
                COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD};
        List<User> listUser = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query( TABLE_USER,
                 columns,
                 null,
                 null,
                 null,
                 null,
                 null);
        if(cursor.getCount() > 0 ){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName((cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))));
                user.setEmail((cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))));
                user.setPassword((cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))));
                listUser.add(user);
            }while ( cursor.getCount() > 0);

        }
        cursor.close();
        db.close();
        return listUser;
    }

    //Metod som uppdaterar usern

    public void updateUser(User user){
    SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, user.getName());
        contentValues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        database.update(TABLE_USER, contentValues, COLUMN_USER_ID + " = ? ", new String[] {String.
        valueOf(user.getId())});
        database.close();
    }
    //Delete method
    public void deleteUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_USER, COLUMN_USER_ID + " = ? ", new String[]{String.valueOf(user.getId())});
        database.close();
    }
    public int userDelete(String username){
        SQLiteDatabase database = this.getWritableDatabase();
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = database.delete("LOGIN", where,
                new String[] { username });
        return numberOFEntriesDeleted;
    }

    //Metod för att se om usern existerar eller inte
    //email är ett pra sett att kolla, eftersom det är unikt per user.
    public boolean checkUser(String email){
        //Array av columner att hämta
        String[] columns ={
                COLUMN_USER_ID
        };
        SQLiteDatabase database = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ? ";

        String[] selectionArgs = {email};

        Cursor cursor = database.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        database.close();
        if(cursorCount > 0 ){
            return true;
        }
        return false;
    }
    public boolean checkUser(String email, String password){
        String[] columns={
                COLUMN_USER_ID
        };

        SQLiteDatabase database = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ? " + " AND " + COLUMN_USER_PASSWORD + " = ? ";

        String[] selectionArgs = {email, password};

        Cursor cursor = database.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        database.close();
        if(cursorCount > 0 ){
            return true;
        }
        return false;
    }
    public Cursor getWordMatches(String query, String[] columns) {
        /*String selection = COL_WORD + " MATCH ?";*/
        String selection =  COLUMN_USER_EMAIL + " = ? ";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_USER,
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

}
