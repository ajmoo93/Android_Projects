package com.example.em9310li.dad_pushdocument;

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
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

/**
 * Created by EM9310LI on 2017-09-26.
 */

public class User_registration extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = User_registration.this;

    private NestedScrollView nestedScrollViewReg;
    private LinearLayoutCompat linearLayoutCompatReg;

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutConPassword;

    private TextInputEditText inputEditTextName;
    private TextInputEditText inputEditTextEmail;
    private TextInputEditText inputEditTextPassword;
    private TextInputEditText inputEditTextConPassword;

    private AppCompatButton appCompatRegisterButton;
    private AppCompatTextView appCompatTextViewLogin;
    private DBHelper dbHelper;
    private Invalidation inputValidation;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }



    private void initViews() {
        nestedScrollViewReg = (NestedScrollView) findViewById(R.id.nestedScrollviewReg);
        linearLayoutCompatReg = (LinearLayoutCompat) findViewById(R.id.linnearLayoutCompatReg);

        inputLayoutName = (TextInputLayout) findViewById(R.id.InputLayoutName);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.InputLayoutEmail);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.InputLayoutPassword);
        inputLayoutConPassword = (TextInputLayout)findViewById(R.id.InputLayoutConPassword);

        inputEditTextName = (TextInputEditText) findViewById(R.id.InputEditName);
        inputEditTextEmail = (TextInputEditText)findViewById(R.id.InputEditEmail);
        inputEditTextPassword = (TextInputEditText)findViewById(R.id.InputEditPassword);

        inputEditTextConPassword = (TextInputEditText)findViewById(R.id.textInputEditConPassword);

        appCompatRegisterButton = (AppCompatButton) findViewById(R.id.appCompatRegisterBtn);
        appCompatTextViewLogin = (AppCompatTextView) findViewById(R.id.appCompatTextLogin);
    }

    private void initListeners() {
        appCompatTextViewLogin.setOnClickListener(this);
        appCompatRegisterButton.setOnClickListener(this);
    }
    private void initObjects() {
        inputValidation = new Invalidation(activity);
        dbHelper = new DBHelper(activity);
        user = new User();
    }
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.appCompatTextLogin:
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            break;
        case R.id.appCompatRegisterBtn:
            postDataToSQL();
            break;
    }
    }
    public void postDataToSQL(){
        if(!inputValidation.IsInputTextFilled(inputEditTextName, inputLayoutName, getString(R.string.error_userName))){
            return;
        }
        if(!inputValidation.IsInputTextFilled(inputEditTextEmail, inputLayoutEmail, getString(R.string.error_email))){
            return;
        }
        if(!inputValidation.isInputTextEmail(inputEditTextEmail, inputLayoutEmail, getString(R.string.error_email))){
            return;
        }
        if(!inputValidation.IsInputTextFilled(inputEditTextPassword, inputLayoutPassword, getString(R.string.error_password))){
            return;
        }
        if(!inputValidation.isInputTextMatching(inputEditTextPassword, inputEditTextConPassword,
                inputLayoutConPassword, getString(R.string.error_password))){
            return;
        }
        if(!inputValidation.isPasswordLongerThenEight(inputEditTextPassword, inputLayoutPassword
                , getString(R.string.error_password_eight))){
            return;
        }
        if(!dbHelper.checkUser(inputEditTextEmail.getText().toString().trim())) {
            user.setUserName(inputEditTextName.getText().toString().trim());
            user.setEmail(inputEditTextEmail.getText().toString().trim());
            user.setPassword(inputEditTextPassword.getText().toString().trim());
            dbHelper.addUser(user);

            Snackbar.make(nestedScrollViewReg, getString(R.string.success_register), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }
            else{
            Snackbar.make(nestedScrollViewReg, getString(R.string.error_email_inUse), Snackbar.LENGTH_LONG).show();

        }
    }
    private void emptyInputEditText(){
        inputEditTextName.setText(null);
        inputEditTextEmail.setText(null);
        inputEditTextPassword.setText(null);
        inputEditTextConPassword.setText(null);
    }
}
