package com.ahmedmaghawry.finalproject;

import android.provider.BaseColumns;

/**
 * Created by Ahmed Maghawry on 10/20/2016.
 */
public class MovieContract implements BaseColumns {

    public static final String TABLE_NAME = "Movies";

    public static final String LOCATION = "position";

    public static final String MOVIE_ID = "id";

    public static final String MOVIE_TITLE = "title";

    public static final String MOVIE_YEAR = "date";

    public static final String MOVIE_DESC = "overview";

    public static final String MOVIE_TRAIL1 = "trail1";

    public static final String MOVIE_TRAIL2 = "trail2";

    public static final String MOVIE_DAUR = "time";

    public static final String MOVIE_RATE = "rate";

    public static final String MOVIE_PIC = "image";
}
