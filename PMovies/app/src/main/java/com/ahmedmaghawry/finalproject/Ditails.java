package com.ahmedmaghawry.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Ditails extends ActionBarActivity {
    int id;
    String overview;
    String date;
    String title;
    String pic;
    float rate;
    ArrayList<String> author = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ditails);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragmentee())
                    .commit();
        }
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        pic = intent.getStringExtra("poster");
        overview = intent.getStringExtra("overview");
        date = intent.getStringExtra("date");
        title = intent.getStringExtra("title");
        rate = intent.getFloatExtra("Vote", 0f);
        /*ArrayList<ArrayList<String>> tot = new ArrayList<>();
        DownloadReviews rev = new DownloadReviews();
        try {
            tot = rev.execute(id+"").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        author = tot.get(0);
        content = tot.get(1);*/
        Log.i("Date",id+"");
        Log.i("Date",overview);
        Log.i("Date",date);
        Log.i("Date",title);
        Log.i("Date",rate+"");
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragmentee extends Fragment {

        public PlaceholderFragmentee() {
        }
        public PlaceholderFragmentee(int idq, String post, String over, String dat, String tit, float rateq) {
            id = idq;
            rate = rateq;
            overview = over;
            pic = post;
            date = dat;
            title = tit;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            ArrayList<String> urls = new ArrayList();
            ArrayList<ArrayList<String>> tot = new ArrayList<>();
            DownloadReviews rev = new DownloadReviews();
            try {
                tot = rev.execute(id+"").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            author = tot.get(0);
            content = tot.get(1);
            DownloadRestDetails Urls = new DownloadRestDetails();
            try {
                urls = Urls.execute("https://api.themoviedb.org/3/movie/"+id+"/videos?api_key="+"cb016c2476619f5245b6d29fc86c6457"+"&language=en-US").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            DownloadDuar dur = new DownloadDuar();
            String durr = null;
            try {
                durr = dur.execute("https://api.themoviedb.org/3/movie/"+id+"?api_key="+ "cb016c2476619f5245b6d29fc86c6457"+"&language=en-US").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            View rootView = inflater.inflate(R.layout.fragments_detalis, container, false);
            TextView titlee = (TextView) rootView.findViewById(R.id.Title);
            titlee.setText(title);
            ImageView imaa = (ImageView) rootView.findViewById(R.id.img);
            Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500" + pic).placeholder(R.drawable.loading).error(R.drawable.imagenotfound).resize(400,600).centerCrop().into(imaa);
            //imaa.setImageBitmap(pic);
            TextView datee = (TextView) rootView.findViewById(R.id.Date);
            datee.setText(date);
            TextView ratee = (TextView) rootView.findViewById(R.id.Rate);
            ratee.setText(rate+"/10");
            TextView over = (TextView) rootView.findViewById(R.id.OverView);
            over.setText(overview);
            TextView duree = (TextView) rootView.findViewById(R.id.Time);
            duree.setText(durr + " min");
            LinearLayout firstclk = (LinearLayout) rootView.findViewById(R.id.url1);
            final ArrayList<String> finalUrls = urls;
            firstclk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + finalUrls.get(0))));

                }
            });
            LinearLayout secondclk = (LinearLayout) rootView.findViewById(R.id.url2);
            secondclk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+ finalUrls.get(1))));

                }
            });
            TextView review = (TextView) rootView.findViewById(R.id.reviews);
            String revi = "";
            for(int i = 0; i < author.size();i++) {
                revi += author.get(i)+" :"+System.lineSeparator();
                revi += content.get(i)+System.lineSeparator()+System.lineSeparator();
                revi += "**************************************************************"+System.lineSeparator();
            }
            review.setText(revi);
            final Button favo = (Button) rootView.findViewById(R.id.favo);
            if(isMarked(id)) {
                favo.setText("Unmark from favorite");
                favo.setBackgroundColor(Color.parseColor("#FF4081"));
            }
            favo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isMarked(id)) {
                        SQLiteDatabase DB = getActivity().openOrCreateDatabase("Movies",MODE_PRIVATE,null);
                        favo.setText("mark as favorite");
                        favo.setBackgroundColor(Color.parseColor("#69c62f"));
                        // remove from db
                        DB.execSQL("DELETE FROM "+MovieContract.TABLE_NAME+" WHERE "+MovieContract.MOVIE_ID+" = "+id);
                    } else {
                        SQLiteDatabase DB = getActivity().openOrCreateDatabase("Movies",MODE_PRIVATE,null);
                        favo.setText("Unmark from favorite");
                        favo.setBackgroundColor(Color.parseColor("#FF4081"));
                        // Add to DB
                        DB.execSQL("CREATE TABLE IF NOT EXISTS " + MovieContract.TABLE_NAME + " (" +
                                MovieContract.LOCATION+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                MovieContract.MOVIE_ID + " INTEGER NOT NULL)");
                        DB.execSQL("INSERT INTO "+MovieContract.TABLE_NAME+" (" +
                                MovieContract.MOVIE_ID +") VALUES ("+id+")");
                    }
                }
            });
            return rootView;
        }
        private Boolean isMarked(int id) {
            SQLiteDatabase DB;
            DB = getActivity().openOrCreateDatabase("Movies", Context.MODE_PRIVATE, null);
            DB.execSQL("CREATE TABLE IF NOT EXISTS " + MovieContract.TABLE_NAME + " (" +
                    MovieContract.LOCATION+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MovieContract.MOVIE_ID + " INTEGER NOT NULL)");
            /*if (getActivity().openOrCreateDatabase("Movies", Context.MODE_PRIVATE, null) != null) {
                DB = getActivity().openOrCreateDatabase("Movies", Context.MODE_PRIVATE, null);
                DB.execSQL("CREATE TABLE IF NOT EXISTS " + MovieContract.TABLE_NAME + " (" +
                                MovieContract.LOCATION+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                MovieContract.MOVIE_ID + " INTEGER NOT NULL)");
            } else {
                // To access your database, instantiate your subclass of SQLiteOpenHelper:
                FavoriteDB mDbHelper = new FavoriteDB(getContext());
                // Gets the data repository in write mode
                DB = mDbHelper.getWritableDatabase();
            //}*/
            Cursor c = DB.rawQuery("SELECT * FROM " + MovieContract.TABLE_NAME, null);
            if (c.moveToFirst()) {
                int index = c.getColumnIndex(MovieContract.MOVIE_ID);
                if (c.getInt(index) == id) {
                    return true;
                }
                while (c.moveToNext()) {
                    if (c.getInt(index) == id)
                        return true;
                }
            }
            return false;
        }
    }


    public Fragment Createfrag(int idq, String post, String over, String dat, String tit, float rateq) {
        Fragment x = new PlaceholderFragmentee(idq, post, over, dat, tit, rateq);
        return x;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
