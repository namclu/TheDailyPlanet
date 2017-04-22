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

import com.namclu.android.thedailyplanet.R;
import com.namclu.android.thedailyplanet.api.models.News;
import com.namclu.android.thedailyplanet.ui.adapters.NewsItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DailyPlanetActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>{

    private static final String TAG = DailyPlanetActivity.class.getName();

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

        // Add dummy data
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));
        mNews.add(new News("Title", "Section", "DatePublished", "Web URL"));

        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            } else {

            }
        } catch (Exception e) {

        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
