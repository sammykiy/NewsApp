package com.example.android.newsapp;
    import android.content.AsyncTaskLoader;
    import android.content.Context;
    import java.util.List;

    // Loads a list of articles
public class ArticleLoader extends AsyncTaskLoader<List<ArticleArray>>{

    // Log messages
    private static final String LOG_TAG = ArticleLoader.class.getName();

        private String mUrl;
        /**
         * Constructs a new {@link ArticleLoader}.
         *
         * @param context of the activity
         * @param url to load data from
         */
        public ArticleLoader(Context context, String url){
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading(){
                forceLoad();
        }
        //background thread.
        @Override
        public List<ArticleArray> loadInBackground(){
            if (mUrl == null){
                    return null;
            }

            // Perform the network request, and parse the response to extract the list of articles.
            List<ArticleArray> articles = QueryUtils.fetchArticleData(mUrl);
            return articles;
        }
}
