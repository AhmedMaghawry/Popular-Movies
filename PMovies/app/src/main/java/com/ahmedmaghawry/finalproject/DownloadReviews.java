package com.ahmedmaghawry.finalproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ahmed Maghawry on 10/21/2016.
 */
public class DownloadReviews extends AsyncTask<String, Void, ArrayList> {
    ArrayList<ArrayList<String>> total = new ArrayList<>();
    ArrayList<String> author = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();

    @Override
    protected ArrayList doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/"+params[0]+"/reviews?api_key="+ "cb016c2476619f5245b6d29fc86c6457"+"&language=en-US");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                Log.i("Test", line);
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.i("Json", forecastJsonStr);
            getid(forecastJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        total.add(author);
        total.add(content);
        return total;
    }

    public void getid(String cont) throws JSONException {
        JSONObject total = new JSONObject(cont);
        JSONArray result = total.getJSONArray("results");
        for (int i = 0; i < result.length(); i++) {
            JSONObject Film = result.getJSONObject(i);
            String poster2 = Film.getString("author");
            Log.i("Review",poster2);
            author.add(poster2);
            String overview2 = Film.getString("content");
            Log.i("Review",overview2);
            content.add(overview2);
        }
    }
}