package com.example.em9310li.dad_pushdocument;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.em9310li.dad_pushdocument.Post;
import com.example.em9310li.dad_pushdocument.Threads;
import com.example.em9310li.dad_pushdocument.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by EM9310LI on 2017-09-25.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userManagement.db";

    private static final String TABLE_USER = "user";
    private static final String TABLE_THREAD = "threads";
    private static final String TABLE_POST = "posts";
    private static final String TABLE_THREAD_POST = "thread_posts";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String COLUMN_THREADID = "thread_id";
    private static final String COLUMN_THREADTITLE = "thread_title";
    private static final String COLUMN_THREADDATE = "thread_date";

    private static final String COLUMN_POSTID = "post_id";
    private static final String COLUMN_POSTTEXT = "post_text";
    private static final String COLUMN_POSTDATE = "post_date";
    private static final String COLUMN_POSTLIKES = "post_likes";

    private static final String COLUMN_THREADFKID = "threadid";
    private static final String COLUMN_POSTFKID = "postid";
    private static final String COLUMN_USER_THREADID = "userthreadid";




    public String CREATE_TABLE = " CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    public String CREATE_TABLE_THREAD = " CREATE TABLE " + TABLE_THREAD + "("
            + COLUMN_THREADID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_THREADTITLE + " TEXT," + COLUMN_THREADDATE + " DATETIME,"
            + COLUMN_USER_ID + "INTEGER" + ")";

    public String CREATE_TABLE_POST = " CREATE TABLE " + TABLE_POST + "("
            + COLUMN_POSTID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_POSTTEXT + " TEXT," + COLUMN_POSTDATE + " DATETIME,"
            + COLUMN_USER_ID + "INTEGER," + COLUMN_POSTLIKES + " INTEGER" + ")";

    public String CREATE_TABLE_THREADPOST = " CREATE TABLE " + TABLE_THREAD_POST + "("
            + COLUMN_THREADID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_THREADFKID + " INTEGER," + COLUMN_POSTFKID + " INTEGER"
            + ")";

    public String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    public String DROP_TABLE_THREAD = "DROP TABLE IF EXISTS " + TABLE_THREAD;
    public String DROP_TABLE_POST = "DROP TABLE IF EXISTS " + TABLE_POST;
    public String DROP_TABLE_THREADPOST = "DROP TABLE IF EXISTS " + TABLE_THREAD_POST;

    public DBHelper(Context context){super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_THREAD);
        db.execSQL(CREATE_TABLE_POST);
        db.execSQL(CREATE_TABLE_THREADPOST);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_THREAD);
        db.execSQL(DROP_TABLE_POST);
        db.execSQL(DROP_TABLE_THREADPOST);

        onCreate(db);
    }
    public void addUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLUMN_USER_NAME, user.getUserName());
        contentvalues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentvalues.put(COLUMN_USER_PASSWORD, user.getPassword());
        database.insert(TABLE_USER, null , contentvalues);
        database.close();
    }

    public void updateUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        database.update(TABLE_USER, contentValues, COLUMN_USER_ID + " = ? ", new String[] {String.
                valueOf(user.getUserID())});
        database.close();
    }
    public void deleteUser(User user){

    }

    //------------------------THREAD-----------------------------
    public void addThread(Threads thread){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLUMN_THREADTITLE, thread.getTitle());
        contentvalues.put(COLUMN_THREADDATE, thread.getDateCreated());
        database.insert(TABLE_THREAD, null , contentvalues);
        database.close();
    }


    //------------------------POST-------------------------------
    public void addPost(Post post){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLUMN_POSTTEXT, post.getPostText());
        contentvalues.put(COLUMN_POSTDATE, post.getDatePosted());
        contentvalues.put(COLUMN_POSTLIKES, post.getLikes());
        database.insert(TABLE_POST, null , contentvalues);
        database.close();
    }
    public User getUser(String email, String userName) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
// selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_NAME + " = ?";

// selection arguments
        String[] selectionArgs = {email,userName };

// query user table with conditions


        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns, //columns to return
                selection, //columns for the WHERE clause
                selectionArgs, //The values for the WHERE clause
                null, //group the rows
                null, //filter by row groups
                null); //The sort order

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setUserID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
            user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));

            cursor.close();
            db.close();
            return user;
        }

        cursor.close();
        db.close();

        return null;
    }


    public boolean checkUser(String email){
        String[] columns = {COLUMN_USER_ID};

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

        if(cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String email,String password){
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase database = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + "= ?" + " AND " + COLUMN_USER_PASSWORD + "= ?";

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
    public List<Threads> getAllThreads(){
        String[] columns = {COLUMN_THREADID, COLUMN_THREADTITLE,
                COLUMN_THREADDATE,
        };
        String sortOrder = COLUMN_THREADTITLE + " ASC";
        List<Threads> threadsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //denna query function hämtar alla users table och denna fukction fungerar som en sql query
        //
        Cursor cursor = db.query(TABLE_THREAD,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        //går igenom listan och lägger till
        if(cursor.moveToFirst()){
            do{
                Threads thread = new Threads();
                thread.setThreadId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_THREADID))));
                thread.setTitle((cursor.getString(cursor.getColumnIndex(COLUMN_THREADTITLE))));
                thread.setDateCreated((cursor.getString(cursor.getColumnIndex(COLUMN_THREADDATE))));
                //Här läggs user till i listan

                threadsList.add(thread);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // returnerar listan
        return threadsList;
    }
}
