package com.example.em9310li.dad_pushdocument;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.animation.Animation;

import activities.User_activities;
import helpers.DBHelper;
import valitation.Invalidation;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private final AppCompatActivity activity = MainActivity.this;

    private NestedScrollView nestedScrollView;
    private LinearLayoutCompat linearLayoutCompat;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditEmail;
    private TextInputEditText textInputEditPassword;

    private AppCompatButton appCompatTextBtn;
    private AppCompatTextView appCompatTextViewReg;

    private Invalidation inputValidation;
    private DBHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();

    }

    private void initObjects() {
        dbHelper = new DBHelper(activity);
        inputValidation = new Invalidation(activity);
    }

    public void initViews(){

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        linearLayoutCompat = (LinearLayoutCompat) findViewById(R.id.linearLayoutCompat);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditEmail = (TextInputEditText) findViewById(R.id.textInputEditEmail);
        textInputEditPassword = (TextInputEditText) findViewById(R.id.textInputEditPassword);

        appCompatTextBtn = (AppCompatButton) findViewById(R.id.appCompatTextBtn);
        appCompatTextViewReg = (AppCompatTextView) findViewById(R.id.appCompatTextViewReg);
    }
    public void initListeners(){
        appCompatTextBtn.setOnClickListener(this);
        appCompatTextViewReg.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case R.id.appCompatTextBtn:
                sessionManager = new SessionManager(getApplicationContext());
                if(!sessionManager.isLoggedIn()){
                    clockwise(view);
                    verifyBySQL();
                }

                break;
            case R.id.appCompatTextViewReg:
                Intent register = new Intent(getApplicationContext(), User_registration.class);
                startActivity(register);
                break;
        }
    }
    private void verifyBySQL(){
        if(!inputValidation.IsInputTextFilled(textInputEditEmail, textInputLayoutEmail, getString(R.string.error_email) )){
            return;
        }
        if(!inputValidation.isInputTextEmail(textInputEditEmail,textInputLayoutEmail, getString(R.string.error_email))){
            return;
        }
        if(!inputValidation.IsInputTextFilled(textInputEditPassword, textInputLayoutPassword, getString(R.string.error_password))){
            return;
        }
        if(dbHelper.checkUser(textInputEditEmail.getText().toString().trim(),
                textInputEditPassword.getText().toString().trim())){
            Intent intent = new Intent(activity, User_activities.class);
            intent.putExtra("EMAIL", textInputEditEmail.getText().toString().trim());
            emptyEditText();
            startActivity(intent);

        }
    }
    private void emptyEditText(){
        textInputEditEmail.setText(null);
        textInputEditPassword.setText(null);
    }
    public void clockwise(View view) {
        Button btn = (Button) findViewById(R.id.appCompatTextBtn);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation);
        btn.startAnimation(animation);
    }
}

