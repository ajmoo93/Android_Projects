package com.example.em9310li.dad_pushdocument;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by EM9310LI on 2017-09-27.
 */

public class User_activities extends AppCompatActivity implements View.OnClickListener {
    private final User_activities activity = User_activities.this;

    private AppCompatTextView appCompatEmailView, appCompatNameView;
    private TextView textViewNewThread, textViewContact, textViewInfo;
    public ImageView imageView;
    private User users;
    private DBHelper dbHelper;
    private ScrollView scrollView;
    private RelativeLayout relativeLayout;
    private User_activities user_activities;
    private AppCompatTextView InputEditName;
    private SessionManager sessionManager;
    public static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_profile);


        initViews();
        initObjects();
        initListeners();


    }

    private void initListeners() {

        imageView.setOnClickListener(this);
        textViewNewThread.setOnClickListener(this);
    }

    private void initViews() {
        scrollView = (ScrollView) findViewById(R.id.scrollViewProfile);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutProfile);


        appCompatEmailView = (AppCompatTextView) findViewById(R.id.appCompatEmailView);
        appCompatNameView = (AppCompatTextView) findViewById(R.id.appCompatNameView);

        textViewNewThread =(TextView)findViewById(R.id.textViewNewThread);
        imageView = (ImageView) findViewById(R.id.picture_change);
        /*textViewContact =(TextView)findViewById(R.id.textViewContact);
        textViewInfo =(TextView)findViewById(R.id.textViewInfo);*/
    }

    private void initObjects() {

        users = new User();

        dbHelper = new DBHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        appCompatEmailView.setText(emailFromIntent);
        appCompatNameView.setText(users.getUserName());


        getDataFromSQLite();
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.picture_change:
             selectImage();
                break;
            case R.id.textViewNewThread:
            Intent threadIntent = new Intent(User_activities.this, Thread_activities.class);
                startActivity(threadIntent);
                break;
        }
    }

    private void selectImage(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageButton imageButton = (ImageButton) findViewById(R.id.user_profile_pic);
            imageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));

    }
    }
    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                User user = dbHelper.getUser("", "");
                users.getEmail();
                users.getUserName();
                return null;
            }

            protected void onPostExecute(Void avoid) {
                super.onPostExecute(avoid);
                //dbHelper.notifyAll();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {

            case R.id.action_settings:
                Intent intent = new Intent(User_activities.this, User_edit.class);
                startActivity(intent);
                return true;
            case R.id.action_loguut:
                sessionManager = new SessionManager(getApplicationContext());

                if (!sessionManager.isLoggedIn()) {
                    logoutUser();
                }
            default:
                return super.onOptionsItemSelected(menu);
        }
    }
    public void logoutUser(){
        sessionManager.setLogin(false);
        finish();
    }
}


