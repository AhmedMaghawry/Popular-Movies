package com.ahmedmaghawry.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ahmed Maghawry on 10/17/2016.
 */
public class DownloadImage extends AsyncTask<ArrayList<String>, Void,ArrayList<Bitmap>> {
    @Override
    protected ArrayList<Bitmap> doInBackground(ArrayList<String>... params) {
        try {
            ArrayList<Bitmap> bitmap = new ArrayList<>();
            for(int i = 0; i < params[0].size(); i++) {
                URL url = new URL("https://image.tmdb.org/t/p/w500"+params[0].get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap.add(BitmapFactory.decodeStream(inputStream));
            }
            return bitmap;
        } catch (Exception e) {
            throw new RuntimeException("There is no Pic");
        }
    }
}

