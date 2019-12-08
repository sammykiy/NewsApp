package com.example.android.newsapp;
// The QueryUtils.ajva contains material from the earthquake app project.
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {


    public static final String LOG_TAG = "Err_Log";

    /**
     * Create a private constructor {@link QueryUtils} object.
     */
    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "URL Build Error", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResult = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResult;
        }

        HttpURLConnection urlConnect = null;
        InputStream inputData = null;
        try {
            urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setReadTimeout(10000 /* milliseconds */);
            urlConnect.setConnectTimeout(15000 /* milliseconds */);
            urlConnect.setRequestMethod("GET");
            urlConnect.connect();

            // If the request was successful (response code 200)then read the input stream and parse the response.
            if (urlConnect.getResponseCode() == 200) {
                inputData = urlConnect.getInputStream();
                jsonResult = readFromStream(inputData);
            } else {
                Log.e(LOG_TAG, "Error Code: " + urlConnect.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with The Gaurdian JSON results.", e);
        } finally {
            if (urlConnect != null) {
                urlConnect.disconnect();
            }
            if (inputData != null) {
                // Close the inputstream
                inputData.close();
            }
        }
        return jsonResult;
    }

    /**
     * Change the {@link InputStream} into a String
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.v("QueryUtils", "readFromStream" + output.toString());
        return output.toString();
    }

    /**
     * Return a list of {@link ArticleArray} objects that has been built up from
     */
    private static List<ArticleArray> extractFeatureFromJson(String articleJSON) {



        // if the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            // Set empty state text to display "No articles are found."
            return null;
        }

        // Create an empty Arraylist that we can start adding articles to
        List<ArticleArray> articles = new ArrayList<>();

        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            // Extract the JSONArray with the key called "results",which represents a list of articles.
            JSONObject responseObj = baseJsonResponse.getJSONObject("response");
           // Then extract the JSONObject associated with the key called "results"
            JSONArray dataArray = responseObj.getJSONArray("results");

            // Create an {@link Article} object
            for (int i = 0; i < dataArray.length(); i++) {

                // Get a single article at position i within the list of articles
                JSONObject currentArticle = dataArray.getJSONObject(i);

                // Create an Array from the result
                // Extract the "sectionName
                String sectionName = currentArticle.getString("sectionName");
                // Extract the date and time
               String dateTime = currentArticle.getString("webPublicationDate");
                // Extract the Title
                String articleTitle = currentArticle.getString("webTitle");
                // Extract the url
                String url = currentArticle.getString("webUrl");

                // Create a new Object from the results to create an array of tags
                JSONArray tagsArray = currentArticle.getJSONArray("tags");
                String author = "";
                for (int j = 0; j < tagsArray.length(); j++) {
                    JSONObject authorName = tagsArray.getJSONObject(j);
                    author = authorName.getString("webTitle");
                    Log.v("QueryUtils", "show me THE author: " + author);

                }

                // Create a new object with the sectionName, dateTime, articleTitle, url, and authot
                ArticleArray article = new ArticleArray(sectionName, dateTime, articleTitle, url, author);
                articles.add(article);

            }

        } catch (JSONException e) {
            // Log the QueryUtils error.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        // Return the list of articles
        return articles;
    }

    public static List<ArticleArray> fetchArticleData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "TEST: fetchArticleData() called...");
        //Create URL objext
        URL url = createUrl(requestUrl);

        Log.i(LOG_TAG, url.toString());
        //Perform HTTP request to the URL and receive a JSON responcs back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot complete HTTP request.", e);
        }

        List<ArticleArray> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return articles;
    }
}