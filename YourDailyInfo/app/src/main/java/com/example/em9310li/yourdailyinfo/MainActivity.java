package com.example.em9310li.yourdailyinfo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import activities.UserActivities;
import adapters.UsersRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = MainActivity.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout  textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar();

        initViews();
        initListeners();
        initObjects();
    }
    private void initViews(){
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);
        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout)findViewById(R.id.textInputLayoutPassword);
        textInputEditTextEmail = (TextInputEditText)findViewById(R.id.textInputEditEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditPassword);
        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = (AppCompatTextView)findViewById(R.id.textViewLinkRegister);
    }
    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }
    private void initObjects(){
    dbHelper = new DBHelper(activity);
        inputValidation = new InputValidation(activity);
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                    break;
        }
    }
    private void verifyFromSQLite(){
        if(!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail,getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))){
            return;
        }
        if(dbHelper.checkUser(textInputEditTextEmail.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim())){
            Intent accountIntent = new Intent(activity, UserActivities.class);
            accountIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyEditText();
            startActivity(accountIntent);

        }else{
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();

        }
    }
    //Tar bort alla inputs som gjorts.
    private void emptyEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


}
