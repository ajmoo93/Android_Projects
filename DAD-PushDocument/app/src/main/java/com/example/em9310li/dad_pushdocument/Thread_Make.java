package com.example.em9310li.dad_pushdocument;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.LinearLayout;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import classes.Threads;
import classes.User;
import helpers.DBHelper;
import valitation.Invalidation;

/**
 * Created by EM9310LI on 2017-11-02.
 */

public class Thread_Make extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = Thread_Make.this;

    private LinearLayout lineatLayout;
    public TextInputEditText TitleInputView, InputTextView;
    private TextInputLayout titleEditLayout, textEditLayout;
    private AppCompatButton buttonAddThread;
    private Invalidation validation;
    private DBHelper dbHelper;
    private Threads threads;
    private User user;
    private java.util.Date date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_make);

        initViews();
        initListeners();
        initObjects();
    }



    private void initViews() {
        TitleInputView = (TextInputEditText) findViewById(R.id.titleInputView);
        InputTextView = (TextInputEditText) findViewById(R.id.inputTextView);

        titleEditLayout =(TextInputLayout) findViewById(R.id.titleEditLayout) ;
        textEditLayout = (TextInputLayout) findViewById(R.id.textEditLayout);

        buttonAddThread = (AppCompatButton) findViewById(R.id.buttonAddThread);

    }
    private void initListeners() {
        buttonAddThread.setOnClickListener(this);
    }
    private void initObjects() {
        threads = new Threads();
        user = new User();
        dbHelper = new DBHelper(activity);
    }
    public void postDataToSQL(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        threads.setDateCreated(strDate);
        threads.setTitle(TitleInputView.getText().toString().trim());
        user.setUserName(InputTextView.getText().toString().trim());


            dbHelper.addThread(threads);
            Snackbar.make(lineatLayout, getString(R.string.success_register), Snackbar.LENGTH_LONG).show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddThread:
                postDataToSQL();
                finish();
        }
    }
}
