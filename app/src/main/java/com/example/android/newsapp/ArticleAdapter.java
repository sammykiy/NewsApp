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

    private static final String AUTHOR_SEPARATOR = "|";

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
        // Get the author string from the Article object,
        String articleAuthor = currentArticle.getAuthor();

        Log.v("Adapter", "Title (origin): " + originalTitle);
        Log.v("Adapter", "Author (origin): " + articleAuthor);

        // If the articleAuthis is null and original tile string contains a title and an author
        // then store the title separately from the author offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
        String newTitle;
        String titleAuthor;
        if (articleAuthor != null && !articleAuthor.isEmpty()) {

            titleAuthor = articleAuthor;

            // Split the string into different parts (as an array of Strings)
            // based on the " | " text. We expect an array of 2 Strings, where
            // the first String will be "the title and the second String will be "the author".
            if (originalTitle.contains(AUTHOR_SEPARATOR)) {

                String[] parts = originalTitle.split(" \\| ");
                newTitle = parts[0];

            } else {
                newTitle = originalTitle;
            }
            Log.v("Adapter", "Author (if): " + titleAuthor);
            Log.v("Adapter", "Title (if): " + newTitle);

        } else if ((articleAuthor.isEmpty()) && (originalTitle.contains(AUTHOR_SEPARATOR))) {

            String[] parts = originalTitle.split(" \\| ");
            // Location offset should be "Title " + "|" "Author"
            newTitle = parts[0];
            titleAuthor = parts[1];
            Log.v("Adapter", "Title (else if): " + newTitle);
            Log.v("Adapter", "Author(else if): " + titleAuthor);
        } else {
            // If there is no " | " text in the originalLocation string.
            // set the titleAuthor to read "..."
            titleAuthor = getContext().getString(R.string.noAuthor);
            newTitle = originalTitle;

            Log.v("Adapter", "Title (else): " + newTitle);
            Log.v("Adapter", "Author(else): " + titleAuthor);
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
