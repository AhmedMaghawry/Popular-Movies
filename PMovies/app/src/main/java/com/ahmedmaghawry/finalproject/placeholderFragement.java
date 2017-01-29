package com.ahmedmaghawry.finalproject;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//import android.graphics.Bitmap;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class placeholderFragement extends Fragment {

    //public static ArrayList<Bitmap> Imegas = new ArrayList<>();
    ArrayList<ArrayList> total = new ArrayList<>();
    ArrayList<Object> total_db = new ArrayList<>();
    ArrayList<String> poster = new ArrayList<>();
    ArrayList<String> overview= new ArrayList<>();
    ArrayList<String> related_date= new ArrayList<>();
    ArrayList<Integer> id_film = new ArrayList<>();
    ArrayList<String> original_title= new ArrayList<>();
    ArrayList<String> title= new ArrayList<>();
    ArrayList<Float> Vote= new ArrayList<>();
    ArrayList<Integer> id_db = new ArrayList<>();
    GridView grid;
    public interface CallBack{
        public void Selee(ArrayList<Object> xx);
    }
    public placeholderFragement(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FormatJson_fragment movie = new FormatJson_fragment();
        String res = Sorted();
        try {
            if(res.equals("1")) {
                total = movie.execute("https://api.themoviedb.org/3/movie/popular?api_key="+ "cb016c2476619f5245b6d29fc86c6457"+"&language=en-US").get();
            } else if (res.equals("2")) {
                total = movie.execute("https://api.themoviedb.org/3/movie/top_rated?api_key="+"cb016c2476619f5245b6d29fc86c6457"+"&language=en-US").get();
            } else {
                SQLiteDatabase DB;
                //if (getActivity().openOrCreateDatabase("Movies", Context.MODE_PRIVATE, null) != null) {
                    DB = getActivity().openOrCreateDatabase("Movies", Context.MODE_PRIVATE, null);
                /*} else {
                    // To access your database, instantiate your subclass of SQLiteOpenHelper:
                    FavoriteDB mDbHelper = new FavoriteDB(getContext());
                    // Gets the data repository in write mode
                    DB = mDbHelper.getWritableDatabase();
                }*/
                Cursor c = DB.rawQuery("SELECT * FROM " + MovieContract.TABLE_NAME, null);
                if (c.moveToFirst()) {
                    int index = c.getColumnIndex(MovieContract.MOVIE_ID);
                    Log.i("DB", c.getInt(index) + "");
                    id_db.add(c.getInt(index));
                    while (c.moveToNext()) {
                        id_db.add(c.getInt(index));
                    }
                    for (int i = 0; i < id_db.size(); i++) {
                        DownloadForDB dDB = new DownloadForDB();
                        total_db = dDB.execute(String.valueOf(id_db.get(i))).get();
                        poster.add((String) total_db.get(0));
                        overview.add((String) total_db.get(1));
                        related_date.add(total_db.get(2) + "");
                        id_film.add(Integer.parseInt(total_db.get(3) + ""));
                        original_title.add((String) total_db.get(4));
                        title.add((String) total_db.get(5));
                        Vote.add((Float) total_db.get(6));
                        total_db.clear();
                    }
                }
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
        } catch (ExecutionException e) {
            //e.printStackTrace();
        }
        if(res.equals("1") || res.equals("2")){
        poster = total.get(0);
        overview = total.get(1);
        related_date = total.get(2);
        id_film
                = total.get(3);
        original_title = total.get(4);
        title = total.get(5);
        Vote = total.get(6);
            ArrayList<Object> sa = new ArrayList<>();
            sa.add(id_film.get(0));
            sa.add(poster.get(0));
            sa.add(overview.get(0));
            sa.add(related_date.get(0).substring(0, 4));
            sa.add(original_title.get(0));
            sa.add(Vote.get(0));
                    ((CallBack) getActivity()).Selee(sa);
        }
        //mForecastAdapter = new ArrayAdapter<String>(getActivity(),R.layout.grid_item,R.id.title);
        //mForecastAdapter.clear();
        /*for(int i = 0; i < title.size(); i++) {
            mForecastAdapter.add(title.get(i));
        }*/
        /*
        DownloadImage img = new DownloadImage();
        try {
            Imegas = img.execute(poster).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final View rootView2 = inflater.inflate(R.layout.activity_main, container, false);
        grid = (GridView) rootView.findViewById(R.id.grid);
        grid.setAdapter(new AdapterGrid(this, title, poster));
        /*for(int i = 0; i < poster.size(); i++) {
            ImageView imm = (ImageView) rootView.findViewById(R.id.image);
            imm.setImageBitmap(Imegas.get(i));
        }
        grid.setAdapter(mForecastAdapter);*/
        Log.i("Ahmed","tes");
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), Ditails.class);
                    intent.putExtra("id", id_film.get(position));
                    intent.putExtra("poster", poster.get(position));
                    intent.putExtra("overview", overview.get(position));
                    intent.putExtra("date", related_date.get(position).substring(0, 4));
                    intent.putExtra("title", original_title.get(position));
                    intent.putExtra("Vote", Vote.get(position));
                    ArrayList<Object> sa = new ArrayList<>();
                    sa.add(id_film.get(position));
                    sa.add(poster.get(position));
                    sa.add(overview.get(position));
                    sa.add(related_date.get(position).substring(0, 4));
                    sa.add(original_title.get(position));
                    sa.add(Vote.get(position));
                    if (rootView2.findViewById(R.id.container2) != null) {
                        ((CallBack) getActivity()).Selee(sa);
                    } else {
                        startActivity(intent);
                    }
                }
            });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.set_main){
            startActivity(new Intent(getActivity(),SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String Sorted(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String type = sharedPreferences.getString("Sort", "1");
        if(type.equals("1")){
            Log.i("Setting","1");
            return "1";
        } else if (type.equals("2")) {
            Log.i("Setting","2");
            return "2";
        } else {
            Log.i("Setting","3");
            return "3";
        }
    }
}
