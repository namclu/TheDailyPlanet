package com.namclu.android.thedailyplanet.api;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.namclu.android.thedailyplanet.api.models.News;

import java.util.List;

/**
 * Created by namlu on 29-Apr-17.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private final String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<News> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchNewsData(mUrl);
    }
}
