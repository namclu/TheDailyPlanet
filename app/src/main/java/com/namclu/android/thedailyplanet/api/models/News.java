package com.namclu.android.thedailyplanet.api.models;

/**
 * Created by namlu on 20-Apr-17.
 *
 * A News object represents a single news story article that can include
 * a title, publication date, news URL, and new type
 */

public class News {

    /*
    * @param mNewsTitle         title of the news article
    * @param mNewsSectionName   section name of the news article
    * @param mNewsDatePublished date news article was published
    * @param mNewsWebUrl        news article web URL
    * */
    private String mNewsTitle;
    private String mNewsSectionName;
    private String mNewsDatePublished;
    private String mNewsWebUrl;

    /* Create a new News object */
    public News(String newsTitle, String newsSectionName, String newsDatePublished, String newsWebUrl) {
        setNewsTitle(newsTitle);
        setNewsSectionName(newsSectionName);
        setNewsDatePublished(newsDatePublished);
        setnNewsWebUrl(newsWebUrl);
    }
    /* Setter and getter methods */

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        mNewsTitle = newsTitle;
    }

    public String getNewsSectionName() {
        return mNewsSectionName;
    }

    public void setNewsSectionName(String newsSectionName) {
        mNewsSectionName = newsSectionName;
    }

    public String getNewsDatePublished() {
        return mNewsDatePublished;
    }

    public void setNewsDatePublished(String newsDatePublished) {
        mNewsDatePublished = newsDatePublished;
    }

    public String getNewsWebUrl() {
        return mNewsWebUrl;
    }

    public void setnNewsWebUrl(String newsWebUrl) {
        mNewsWebUrl = newsWebUrl;
    }
}
