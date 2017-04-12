package com.example.em9310li.yourdailyinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import activities.UserActivities;

/**
 * Created by EM9310LI on 2017-03-07.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Register.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail1;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditName;
    private TextInputEditText textInputEditTextEmail1;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DBHelper dbHelper;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();

    }
    private void initViews(){
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);

        textInputLayoutEmail1 = (TextInputLayout)findViewById(R.id.textInputLayoutEmail1);
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutPassword = (TextInputLayout)findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextEmail1 = (TextInputEditText)findViewById(R.id.textInputEditEmail1);
        textInputEditName = (TextInputEditText) findViewById(R.id.textInputEditName);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = (AppCompatTextView)findViewById(R.id.appCompatTextViewLoginLink);
    }
    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }
    private void initObjects(){
        inputValidation = new InputValidation(activity);
        dbHelper = new DBHelper(activity);
        user = new User();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;

        }
    }
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditName, textInputLayoutName, getString(R.string.error_message_UserName))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail1, textInputLayoutEmail1, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail1, textInputLayoutEmail1, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        //kolla in på denna imorgon, loopar på något vis? hmm
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }
        if (!dbHelper.checkUser(textInputEditTextEmail1.getText().toString().trim())) {
            user.setName(textInputEditName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail1.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            dbHelper.addUser(user);

            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();


        }
    }
    private void emptyInputEditText(){
        textInputEditName.setText(null);
        textInputEditTextEmail1.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}
