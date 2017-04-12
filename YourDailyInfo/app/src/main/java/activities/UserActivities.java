package activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.em9310li.yourdailyinfo.DBHelper;
import com.example.em9310li.yourdailyinfo.InputValidation;
import com.example.em9310li.yourdailyinfo.R;
import com.example.em9310li.yourdailyinfo.Register;
import com.example.em9310li.yourdailyinfo.Search;
import com.example.em9310li.yourdailyinfo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import adapters.UsersRecyclerAdapter;

/**
 * Created by EM9310LI on 2017-03-08.
 */

public class UserActivities extends AppCompatActivity implements View.OnClickListener{

    private AppCompatActivity activity = UserActivities.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUser;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DBHelper dbHelper;
    private AppCompatButton DeleteButton;

    private InputValidation inputValidation;
    private TextView textEditView;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setTitle("");



        initViews();
        initListeners();
        initObjects();

    }

    //för att initiallisera Viewsen.
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        DeleteButton = (AppCompatButton) findViewById(R.id.DeleteButton);

    }

    // För dem objekten som skall bli använda
    private void initObjects() {
        listUser = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUser);
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
    private void initListeners() {
        DeleteButton.setOnClickListener(this);
    }
    //tar emot alla users from SQLite
    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUser.clear();
                listUser.addAll(dbHelper.getAllUsers());
                return null;
            }

            protected void onPostExecute(Void avoid) {
                super.onPostExecute(avoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


      /*  SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);*/

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.action_Delete:
                /*dbHelper.deleteUser(listUser.remove);*/
                return true;
            case R.id.action_Edit:
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DeleteButton:
            if (listUser == dbHelper.getUser()) {
                dbHelper.deleteUser(listUser.remove(1));
            }
            break;
            case R.id.action_Edit:
            default:
                break;
        }

    }
}
