package com.example.em9310li.yourdailyinfo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

/**
 * Created by EM9310LI on 2017-03-21.
 */

public class Search extends Activity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
            handleIntent(getIntent());
        }

        @Override
        protected void onNewIntent(Intent intent) {

            handleIntent(intent);
        }

        private void handleIntent(Intent intent) {
        DBHelper db = new DBHelper(this);
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                Cursor c = db.getWordMatches(query, null);
                //use the query to search your data somehow
            }
        }

    }

