package com.namclu.android.thedailyplanet.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namclu.android.thedailyplanet.R;
import com.namclu.android.thedailyplanet.api.models.News;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.R.attr.start;

/**
 * Created by namlu on 20-Apr-17.
 */

public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.ViewHolder> implements
        View.OnClickListener {

    private final List<News> mNews;

    // Reference to Delegate object
    private WeakReference<Delegate> mDelegateWeakReference;

    public NewsItemsAdapter(List<News> news) {
        mNews = news;
    }

    @Override
    public NewsItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(NewsItemsAdapter.ViewHolder holder, int position) {
        final News currentNews = mNews.get(position);

        holder.newsTitle.setText(String.format("%s", currentNews.getNewsTitle()));
        holder.newsSection.setText(String.format("%s", currentNews.getNewsSectionName()));
        holder.newsDate.setText(String.format("%s", currentNews.getNewsDatePublished()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getNewsWebUrl()));
                startActivity
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    @Override
    public void onClick(View view) {
        if (getDelegateWeakReference() != null) {
            getDelegateWeakReference().OnItemClicked(NewsItemsAdapter.this);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView newsTitle;
        final TextView newsSection;
        final TextView newsDate;

        public ViewHolder(View itemView) {
            super(itemView);

            newsTitle = (TextView) itemView.findViewById(R.id.text_news_item_title);
            newsSection = (TextView) itemView.findViewById(R.id.text_news_item_section);
            newsDate = (TextView) itemView.findViewById(R.id.text_news_item_date);
        }
    }

    // Interface
    public static interface Delegate {
        public void OnItemClicked(NewsItemsAdapter itemsAdapter);
    }

    /* Getters and Setters */
    public Delegate getDelegateWeakReference() {
        if (mDelegateWeakReference == null) {
            return null;
        } else
            return mDelegateWeakReference.get();
    }

    public void setDelegateWeakReference(Delegate delegateWeakReference) {
        mDelegateWeakReference = new WeakReference<Delegate>(delegateWeakReference);
    }
}
