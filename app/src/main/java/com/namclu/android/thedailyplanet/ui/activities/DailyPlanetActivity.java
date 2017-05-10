package com.namclu.android.thedailyplanet.ui.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.namclu.android.thedailyplanet.R;
import com.namclu.android.thedailyplanet.api.NewsLoader;
import com.namclu.android.thedailyplanet.api.models.News;
import com.namclu.android.thedailyplanet.ui.adapters.NewsItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DailyPlanetActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>,
        NewsItemsAdapter.OnItemClickListener {

    private static final String TAG = DailyPlanetActivity.class.getName();
    private static final String URL =
            "https://content.guardianapis.com/search?q=debates&api-key=test";

    private List<News> mNews;
    private NewsItemsAdapter mNewsItemsAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_planet);

        // Init fields
        mNews = new ArrayList<>();
        mNewsItemsAdapter = new NewsItemsAdapter(mNews, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_daily_planet);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_daily_planet);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_daily_planet);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_daily_planet);

        // Setup divider object at end of each news_list_item
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(getApplicationContext(), R.drawable.vertical_divider);
        mDividerItemDecoration.setDrawable(verticalDivider);

        // RecyclerView stuff
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setAdapter(mNewsItemsAdapter);

        // SwipeRefresh stuff
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsItemsAdapter.clear();
                /*getLoaderManager().initLoader(1, null, DailyPlanetActivity.this).forceLoad();
                Toast.makeText(DailyPlanetActivity.this, "Data refreshed", Toast.LENGTH_SHORT).show();*/
                try {
                    ConnectivityManager connectivityManager =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                        getLoaderManager().initLoader(1, null, DailyPlanetActivity.this).forceLoad();
                    } else {
                        mEmptyTextView.setText(R.string.error_message_network);
                        mProgressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error w internet connection");
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // Check for network connectivity before attempting to load data
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                getLoaderManager().initLoader(1, null, this).forceLoad();
            } else {
                mEmptyTextView.setText(R.string.error_message_network);
                mProgressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error w internet connection");
        }
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
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

    /* Method for NewsAdapter.OnItemClickListener */
    @Override
    public void onItemClicked(News newsItem) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getNewsWebUrl()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
