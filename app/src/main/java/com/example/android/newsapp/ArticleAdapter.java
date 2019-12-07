package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends ArrayAdapter<ArticleArray> {

    private static final String AUTHOR_SEPARATOR = " | ";

    /**
     * Create a new {@link ArticleAdapter} object.
     *
     * @param context  is the current context (i.e. Activity) that the adapter is being created in.
     * @param articles is the list of {@link ArticleArray}s to be displayed.
     */


    public ArticleAdapter(Context context, List<ArticleArray> articles) {
        super(context, 0, articles);
    }

    /**
     * Returns a list item view that displays information about the article at the given position
     * in the list of articles.
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        // Get the {@link Place} object located at this position in the list
        ArticleArray currentArticle = getItem(position);

        // Get the original section string from the Article object,
        String originalTitle = currentArticle.getTitle();

        Log.v("Title", "Title: " + originalTitle);
        // If the original tile string contains a title and an author
        // then store the title separately from the author offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
        String newTitle;
        String titleAuthor;
        if (originalTitle.contains(AUTHOR_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " | " text. We expect an array of 2 Strings, where
            // the first String will be "the title and the second String will be "the author".
            String[] parts = originalTitle.split(" \\| ");
            // Location offset should be "Title " + "|" "Author"
            newTitle = parts[0];
            titleAuthor = parts[1];
            Log.v("Seperator", "Seperator: " + AUTHOR_SEPARATOR);
            Log.v("Title", "Title: " + newTitle);
            Log.v("Author from Split", "Author: " + titleAuthor);
        } else {
            // Otherwise, there is no " | " text in the originalLocation string.
            // Hence, set the default location offset to say "NA".
            titleAuthor = getContext().getString(R.string.noAuthor);

            newTitle = originalTitle;
        }
        // Find the TextView with view ID location
        TextView articleTitleView = (TextView) listItemView.findViewById(R.id.article_title);
        // Display the location of the current article in that TextView
        articleTitleView.setText(newTitle);

        // Find the TextView with view ID Author
        TextView titleAuthorView = (TextView) listItemView.findViewById(R.id.article_author);
        // Display the location offset of the current article in that TextView
        titleAuthorView.setText(titleAuthor);

        // Find the TextView with view article
        TextView titleSectionView = (TextView) listItemView.findViewById(R.id.article_section);
        String articleSection = currentArticle.getSection();
        // Display the section topic of the current article in that TextView
        titleSectionView.setText(articleSection);

        // Find the TextView in the list_item.xml layout with the date text
        //Date dateObject = new Date(currentArticle.getDateTime());
        String dateTime = currentArticle.getDateTime();
        String input = dateTime;     //input string
        String dateStamp = dateTime;
        String timeStamp = dateTime;
        if (input.length() > 0)
        {
            input = dateTime;
            dateStamp = input.substring(0, 10);
            timeStamp = input.substring(11, 19);
        }
        else
        {
           dateStamp = input;
           timeStamp = "";
        }

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date inDate = inputFormat.parse(dateStamp);
            DateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy ", Locale.ENGLISH);
            String finalDate = outputFormat.format(inDate);
            TextView dateView = (TextView) listItemView.findViewById(R.id.article_date);
            dateView.setText(finalDate);
        }catch (Exception ex) {
            TextView dateView = (TextView) listItemView.findViewById(R.id.article_date);
            dateView.setText(dateStamp);
        }
        try {
            DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            Date inTime = inputFormat.parse(timeStamp);
            DateFormat outputFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            String finalTime = outputFormat.format(inTime);
            TextView timeView = (TextView) listItemView.findViewById(R.id.article_time);
            timeView.setText(finalTime);
        }catch (Exception ex) {
            TextView timeView = (TextView) listItemView.findViewById(R.id.article_time);
            timeView.setText(timeStamp);
        }


        return listItemView;
    }

}
