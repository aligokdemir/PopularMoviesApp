package com.gokdemir.popularmovies.Data;


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gokde on 20.03.2018.
 */

public class FavoriteMoviesContract {

    static final String AUTHORITY = "com.gokdemir.popularmovies";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();


        static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE = "release";
    }
}