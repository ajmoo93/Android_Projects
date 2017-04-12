package activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.em9310li.yourdailyinfo.DBHelper;
import com.example.em9310li.yourdailyinfo.R;
import com.example.em9310li.yourdailyinfo.User;

import java.util.ArrayList;
import java.util.List;

import adapters.UsersRecyclerAdapter;

/**
 * Created by EM9310LI on 2017-03-08.
 */

public class SingleUser extends AppCompatActivity {

    private AppCompatActivity activity = SingleUser.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> userList;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }
    //för att initiallisera Viewsen.
    private void initViews(){
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }
    // För dem objekten som skall bli använda
    private void initObjects(){
        userList = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        dbHelper = new DBHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }
    //tar emot alla users from SQLite
    private void getDataFromSQLite(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                userList.clear();
                userList.addAll(dbHelper.getUser());


                return null;
            }
            protected void onPostExecute(Void avoid){
                super.onPostExecute(avoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
