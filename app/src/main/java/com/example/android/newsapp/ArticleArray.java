package com.example.android.newsapp;

import android.location.Location;

public class ArticleArray {
    //** Section Name
    private String mSection;
    //** Date
    private String mDateTime;
    //** Title
    private String mTitle;
    //** Url
    private String mUrl;
    //** Author
    private String mAuthor;

    /**
     * Create a new Place object.
     *
     * @param Section is the name of the articles section
     * @param dateTime time in which the article was published
     * @param Title is a short description of the article
     *
     **@param url is the website URL to find more details about the article
     **@param author is the website URL to find more details about the article
     **/
    /**
     * Create a new Article object.
     **/
    public ArticleArray(String Section, String DateTime, String Title, String url, String Author) {
        mSection = Section;
        mDateTime = DateTime;
        mTitle = Title;
        mUrl = url;
        mAuthor = Author;
    }

    //** Get Section */
    public String getSection() { return mSection; }
    //** Get date */
    public String getDateTime() { return mDateTime; }
    //** Get Titlen */
    public String getTitle() { return mTitle; }

    public String getUrl() { return mUrl; }

    public String getAuthor() { return mAuthor; }
}

