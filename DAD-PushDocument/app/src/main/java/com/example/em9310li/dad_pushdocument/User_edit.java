package com.example.em9310li.dad_pushdocument;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import classes.User;
import helpers.DBHelper;
import valitation.Invalidation;

/**
 * Created by EM9310LI on 2017-10-27.
 */
public class User_edit extends AppCompatActivity implements View.OnClickListener {
    private final User_edit activity = User_edit.this;

    private NestedScrollView nestedScrollView;
    private LinearLayoutCompat linearLayoutCompat;
    private TextInputLayout textInputLayout1, textInputLayout2;
    private TextInputEditText TextViewEditPassword, TextViewEditEmail;
    private AppCompatTextView appCompatTextEditUser;
    private Invalidation invalidation;
    private AppCompatButton appCompatTextBtn;
    private DBHelper dbHelper;
    private User_registration registration;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit);
        initObjects();
        initViews();
        initListeners();
    appCompatTextBtn.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationZ(10f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) { }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                })
                .start();
    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewEdit);
        linearLayoutCompat = (LinearLayoutCompat) findViewById(R.id.linearEditLayoutCompat);

        textInputLayout1 = (TextInputLayout) findViewById(R.id.textEditLayoutEmail);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.textLayoutPassword);

        TextViewEditEmail = (TextInputEditText) findViewById(R.id.textEditEmail);
        TextViewEditPassword = (TextInputEditText) findViewById(R.id.textEditPassword);

        appCompatTextBtn = (AppCompatButton) findViewById(R.id.appCompatTextBtn);
        appCompatTextEditUser = (AppCompatTextView) findViewById(R.id.appCompatTextEditUser);
    }

    private void initListeners() {
        appCompatTextBtn.setOnClickListener(this);
        appCompatTextEditUser.setOnClickListener(this);
    }


    private void initObjects() {
        user = new User();
        dbHelper = new DBHelper(activity);
        invalidation = new Invalidation(activity);
    }

    @Override
    public void onClick(View veiw) {
        switch (veiw.getId()) {
            case R.id.appCompatTextBtn:
                verifyBySQL();
                break;
            case R.id.appCompatTextEditUser:
                finish();
                break;

        }
    }

    public void verifyBySQL() {
        if (!invalidation.IsInputTextFilled(TextViewEditEmail, textInputLayout1, getString(R.string.error_email))) {
            return;
        }
        if (!invalidation.isInputTextEmail(TextViewEditEmail, textInputLayout1, getString(R.string.error_email))) {
            return;
        }
        if (!invalidation.IsInputTextFilled(TextViewEditPassword, textInputLayout2, getString(R.string.error_password))) {
            return;
        }
        if (!dbHelper.checkUser(TextViewEditEmail.getText().toString().trim())) {
            user.setEmail(TextViewEditEmail.getText().toString().trim());
            user.setPassword(TextViewEditPassword.getText().toString().trim());
            dbHelper.updateUser(user);
            Snackbar.make(nestedScrollView, getString(R.string.success_updated), Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(nestedScrollView, getString(R.string.error_edit_user), Snackbar.LENGTH_LONG).show();

        }

    }
}