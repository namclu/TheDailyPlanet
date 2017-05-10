package com.namclu.android.thedailyplanet.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namclu.android.thedailyplanet.R;
import com.namclu.android.thedailyplanet.api.models.News;

import java.util.List;

/**
 * Created by namlu on 20-Apr-17.
 */

public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.ViewHolder> {

    private static final String TAG = NewsItemsAdapter.class.getName();

    private final List<News> mNews;
    private final OnItemClickListener mClickListener;

    /* NewsItemAdapter constructor */
    public NewsItemsAdapter(List<News> news, OnItemClickListener clickListener) {
        mNews = news;
        mClickListener = clickListener;
    }

    @Override
    public NewsItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(NewsItemsAdapter.ViewHolder holder, final int position) {
        final News currentNews = mNews.get(position);

        holder.newsTitle.setText(String.format("%s", currentNews.getNewsTitle()));
        holder.newsSection.setText(String.format("%s", currentNews.getNewsSectionName()));
        holder.newsDate.setText(String.format("%s", currentNews.getNewsDatePublished()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onItemClicked(currentNews);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
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

    // Interface to respond when a News item is clicked
    public interface OnItemClickListener {
        void onItemClicked(News newsItem);
    }

    // Clear all elements of RecyclerView
    public void clear() {
        mNews.clear();
        notifyDataSetChanged();
    }
}
