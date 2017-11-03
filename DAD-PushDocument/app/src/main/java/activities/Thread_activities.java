package activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.em9310li.dad_pushdocument.R;
import com.example.em9310li.dad_pushdocument.Thread_Make;
import classes.Threads;
import classes.User;

import java.util.ArrayList;
import java.util.List;

import helpers.DBHelper;
import recycler.Thread_Recycler;
import valitation.Invalidation;

/**
 * Created by EM9310LI on 2017-11-01.
 */

public class Thread_activities extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Thread_activities.this;

    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewThread;
    private List<Threads> threadsList;
    private Thread_Recycler usersRecyclerAdapter;
    private DBHelper dbHelper;
    private User user;
    private AppCompatButton DeleteButton;
    private FloatingActionButton fab;
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
        // hide() är en metod som kommer med fab för att kunna gömma eller visa med en
        //bra animation
        //dy är ett värde som ändras när du scrollar vertikalt
        //när användaren scrollar ner så är värdet positivt och upp så är det negativt
        //så här kollar vi om fab är synlig och positiv.
        recyclerViewThread.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

    }

    //för att initiallisera Viewsen.
    private void initViews() {
        recyclerViewThread = (RecyclerView) findViewById(R.id.recyclerViewThread);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    // För dem objekten som skall bli använda
    private void initObjects() {
        threadsList = new ArrayList<>();

        usersRecyclerAdapter = new Thread_Recycler(threadsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewThread.setLayoutManager(layoutManager);
        recyclerViewThread.setItemAnimator(new DefaultItemAnimator());
        recyclerViewThread.setHasFixedSize(true);
        recyclerViewThread.setAdapter(usersRecyclerAdapter);
        dbHelper = new DBHelper(activity);
        getDataFromSQLite();
    }

    private void initListeners() {
        fab.setOnClickListener(this);
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
            case R.id.fab:
                Intent intent = new Intent(getApplicationContext(), Thread_Make.class);
                startActivity(intent);
        }
    }
}


