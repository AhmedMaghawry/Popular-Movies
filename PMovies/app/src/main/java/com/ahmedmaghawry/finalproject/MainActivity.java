package com.ahmedmaghawry.finalproject;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

;


public class MainActivity extends ActionBarActivity implements placeholderFragement.CallBack {
    int id;
    float rate;
    String Over;
    String titl;
    String date;
    String pica;
    Bundle sav;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sav = savedInstanceState;
        setContentView(R.layout.activity_main);
        if(isOnline()) {
            if (savedInstanceState == null) {
                placeholderFragement fr = new placeholderFragement();
                fr.setArguments(savedInstanceState);
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
                if (findViewById(R.id.container2) != null) {
                    /*Fragment fr2 = new Ditails().Createfrag(id, rate, Over, pica, date, titl);
                    fr2.setArguments(savedInstanceState);
                    FragmentManager fm2 = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction2 = fm2.beginTransaction();
                    fragmentTransaction2.replace(R.id.container2, fr2);
                    fragmentTransaction2.commit();*/
                }
            }
        }  else{
            TextView mes = (TextView) findViewById(R.id.messa);
            mes.setText("No Internet Connection");
            Button retry = (Button) findViewById(R.id.retry);
            retry.setVisibility(View.VISIBLE);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplication(),MainActivity.class));
                }
            });
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
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

    @Override
    public void Selee(ArrayList<Object> xx) {
        id = (Integer)xx.get(0);
        pica = (String)xx.get(1);
        Over = (String)xx.get(2);
        date = (String)xx.get(3);
        titl = (String)xx.get(4);
        rate = (Float)xx.get(5);
        if (findViewById(R.id.container2) != null) {
            Go();
        }
    }

    public void Go() {
        Fragment fr2 = new Ditails().Createfrag(id, pica, Over, date, titl, rate);
        fr2.setArguments(sav);
        FragmentManager fm2 = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction2 = fm2.beginTransaction();
        fragmentTransaction2.replace(R.id.container2, fr2);
        fragmentTransaction2.commit();
    }
}