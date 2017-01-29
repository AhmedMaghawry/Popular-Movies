package com.ahmedmaghawry.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ahmed Maghawry on 10/20/2016.
 */
public class FavoriteDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "movie.db";

    public FavoriteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieContract.TABLE_NAME + " (" +
                MovieContract.LOCATION+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.MOVIE_DAUR + " INTEGER NOT NULL, " +
                MovieContract.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.MOVIE_RATE + " REAL NOT NULL," +
                MovieContract.MOVIE_DESC + " TEXT NOT NULL, " +
                MovieContract.MOVIE_YEAR + " TEXT NOT NULL, " +
                MovieContract.MOVIE_PIC + " TEXT NOT NULL, " +
                MovieContract.MOVIE_TRAIL1 + " TEXT NOT NULL, " +
                MovieContract.MOVIE_TRAIL2 + " TEXT NOT NULL" +");";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +MovieContract.TABLE_NAME);
        onCreate(db);
    }
}
