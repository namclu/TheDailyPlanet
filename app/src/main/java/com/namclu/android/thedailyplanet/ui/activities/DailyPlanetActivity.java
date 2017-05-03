package com.namclu.android.thedailyplanet.ui.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.namclu.android.thedailyplanet.R;
import com.namclu.android.thedailyplanet.api.NewsLoader;
import com.namclu.android.thedailyplanet.api.models.News;
import com.namclu.android.thedailyplanet.ui.adapters.NewsItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DailyPlanetActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>{

    private static final String TAG = DailyPlanetActivity.class.getName();
    private static final String URL =
            "http://content.guardianapis.com/search?q=debates&api-key=test";

    private List<News> mNews;
    private NewsItemsAdapter mNewsItemsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_planet);

        // Init fields
        mNews = new ArrayList<>();
        mNewsItemsAdapter = new NewsItemsAdapter(mNews);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_daily_planet);

        // RecyclerView stuff
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNewsItemsAdapter);

        // Check for network connectivity before attempting to load data
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                getLoaderManager().initLoader(1, null, this).forceLoad();
            } else {

            }
        } catch (Exception e) {
            Log.e(TAG, "Error w internet connection");
        }

        // Setup RecyclerView to respond to clicks
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /* Methods for LoaderManager.LoaderCallbacks */

    /*
    * Called when the system needs a new loader to be created. Your code should create a Loader
    * object and return it to the system.
    * */
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        if (news != null && !news.isEmpty()) {
            mNews.addAll(news);
            mNewsItemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
