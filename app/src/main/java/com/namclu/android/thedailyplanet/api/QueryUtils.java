package com.namclu.android.thedailyplanet.api;

import android.text.TextUtils;
import android.util.Log;

import com.namclu.android.thedailyplanet.api.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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

/**
 * Created by namlu on 28-Apr-17.
 *
 * Helper method to request and receive news info from The Guardian news API
 */

final class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    /*
     * No one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     * */
    private QueryUtils() {

    }

    /*
     * Query The Guardian API and return a {@link News} object to represent a single news article.
     * */
    public static List<News> fetchNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        List<News> news;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error obtaining JSON response.", e);
        }

        news = extractNewsFromJson(jsonResponse);
        return news;
    }

    /*
     * Returns new URL object from the given string URL.
     * */
    private static URL createUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error creating URL...");
        }
        return url;
    }

    /*
    * Make an HTTP request from the given URL and return a String as the response.
    * */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = null;

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        /*
        * Create connection to object, setup parameters and general request properties, and
        * make the actual connection to remote object
        * */
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();

            // If connection is successful (Response code = 200), read the input stream
            // and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /*
    * Convert the binary data {@link InputStream} into a String which contains the
    * whole JSON response from the server.
    * */
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return String.valueOf(stringBuilder);
    }

    /*
    * Return a List of {@link News} objects by parsing out information
    * about the news from the input jsonResponse string.
    * */
    private static List<News> extractNewsFromJson(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject newsObject = new JSONObject(jsonResponse);
            JSONObject responseObject = newsObject.getJSONObject("response");
            JSONArray newsResultsArray = responseObject.getJSONArray("results");

            if (newsResultsArray.length() > 0) {
                for (int i = 0; i < newsResultsArray.length(); i++) {

                    JSONObject currentNewsObject = newsResultsArray.getJSONObject(i);

                    // Get "webTitle" from JSONobject
                    String newsTitle;
                    if (currentNewsObject.has("webTitle")) {
                        newsTitle = currentNewsObject.getString("webTitle");
                    } else {
                        newsTitle = "No title";
                    }

                    // Get "sectionName" from JSONobject
                    String newsSection;
                    if (currentNewsObject.has("sectionName")) {
                        newsSection = currentNewsObject.getString("sectionName");
                    } else {
                        newsSection = "No section";
                    }

                    // Get "webPublicationDate" from JSONobject
                    String newsPublicationDate;
                    if (currentNewsObject.has("webPublicationDate")) {
                        newsPublicationDate = currentNewsObject.getString("webPublicationDate");
                    } else {
                        newsPublicationDate = "No date";
                    }

                    // Get "webUrl" from JSONobject
                    String newsWebUrl;
                    if (currentNewsObject.has("webUrl")) {
                        newsWebUrl = currentNewsObject.getString("webUrl");
                    } else {
                        newsWebUrl = null;
                    }

                    // Create a new News object
                    news.add(new News(newsTitle, newsSection, newsPublicationDate, newsWebUrl));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the book JSON results", e);
        }

        return news;
    }
}
