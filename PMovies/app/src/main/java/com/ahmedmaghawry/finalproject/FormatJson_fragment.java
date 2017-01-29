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
 * Created by Ahmed Maghawry on 10/17/2016.
 */
public class FormatJson_fragment extends AsyncTask<String, Void, ArrayList> {
    ArrayList<ArrayList> total = new ArrayList<>();
    ArrayList<String> poster = new ArrayList<>();
    ArrayList<String> overview= new ArrayList<>();
    ArrayList<String> related_date= new ArrayList<>();
    ArrayList<Integer> id= new ArrayList<>();
    ArrayList<String> original_title= new ArrayList<>();
    ArrayList<String> title= new ArrayList<>();
    ArrayList<Float> Vote= new ArrayList<>();
    @Override
    protected ArrayList doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            URL url = new URL(params[0]);
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
            //e.printStackTrace();
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
        JSONArray result = total.getJSONArray("results");
        for (int i = 0; i < result.length(); i++) {
            JSONObject Film = result.getJSONObject(i);
            String poster2 = Film.getString("poster_path");
            poster.add(poster2);
            Log.i("poster", poster.get(poster.size()-1));
            String overview2 = Film.getString("overview");
            overview.add(overview2);
            Log.i("Overview", overview.get(overview.size()-1));
            String release_data = Film.getString("release_date");
            related_date.add(release_data);
            Log.i("Release at", related_date.get(related_date.size()-1));
            int id2 = Film.getInt("id");
            id.add(id2);
            Log.i("id", id.get(id.size()-1)+"");
            String original_title2 = Film.getString("original_title");
            original_title.add(original_title2);
            Log.i("org title", original_title.get(original_title.size()-1));
            String title2 = Film.getString("title");
            title.add(title2);
            Log.i("title", title.get(title.size()-1));
            float Vote2 = Film.getInt("vote_average");
            Vote.add(Vote2);
            Log.i("vote", Vote.get(Vote.size()-1)+"");
        }
    }
}
