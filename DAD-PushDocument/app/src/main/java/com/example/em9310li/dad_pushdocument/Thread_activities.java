package com.example.em9310li.dad_pushdocument;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EM9310LI on 2017-11-01.
 */

public class Thread_activities extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = Thread_activities.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<Threads> threadsList;
    private Thread_Recycler usersRecyclerAdapter;
    private DBHelper dbHelper;
    private AppCompatButton DeleteButton;

    private Invalidation inputValidation;
    private TextView textEditView;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.thread_activity);
        getSupportActionBar().setTitle("");


        initViews();
        initListeners();
        initObjects();

    }

    //för att initiallisera Viewsen.
    private void initViews() {

    }

    // För dem objekten som skall bli använda
    private void initObjects() {
        threadsList = new ArrayList<>();
        usersRecyclerAdapter = new Thread_Recycler(threadsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        dbHelper = new DBHelper(activity);
        getDataFromSQLite();
    }

    private void initListeners() {
    }

    //tar emot alla users from SQLite
    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                threadsList.clear();
                threadsList.addAll(dbHelper.getAllThreads());
                return null;
            }

            protected void onPostExecute(Void avoid) {
                super.onPostExecute(avoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}


