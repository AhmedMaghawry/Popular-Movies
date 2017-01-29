package com.ahmedmaghawry.finalproject;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Ahmed Maghawry on 10/20/2016.
 */
public class DownloadForDB extends AsyncTask<String,Void,ArrayList> {
    ArrayList<Object> total = new ArrayList<>();
    String poster;
    String overview;
    String related_date;
    int id;
    String original_title;
    String title;
    Float Vote;
    
    @Override
    protected ArrayList doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/"+params[0]+"?api_key="+ "cb016c2476619f5245b6d29fc86c6457"+"&language=en-US");
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
        } finally{
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
        total.add(poster);
        total.add(overview);
        total.add(related_date);
        total.add(id);
        total.add(original_title);
        total.add(title);
        total.add(Vote);
        return total;
    }
    public void getid(String cont) throws JSONException{
        JSONObject total = new JSONObject(cont);
        String poster2 = total.getString("poster_path");
        poster = (poster2);
        //Log.i("poster", poster.get(poster.size()-1));
        String overview2 = total.getString("overview");
        overview = (overview2);
        //Log.i("Overview", overview.get(overview.size()-1));
        String release_data = total.getString("release_date");
        related_date = (release_data);
        //Log.i("Release at", related_date.get(related_date.size()-1));
        int id2 = total.getInt("id");
        id = (id2);
        //Log.i("id", id.get(id.size()-1)+"");
        String original_title2 = total.getString("original_title");
        original_title = (original_title2);
        //Log.i("org title", original_title.get(original_title.size()-1));
        String title2 = total.getString("title");
        title = (title2);
        //Log.i("title", title.get(title.size()-1));
        float Vote2 = total.getInt("vote_average");
        Vote = (Vote2);
        //Log.i("vote", Vote.get(Vote.size()-1)+"");
    }
}
