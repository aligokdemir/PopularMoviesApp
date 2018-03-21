package com.gokdemir.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gokde on 20.03.2018.
 */

public class FavoriteMoviesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteMovies.db";
    private static final int VERSION = 1;

    FavoriteMoviesDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + FavoriteMoviesContract.MovieEntry.TABLE_NAME + " (" +
                FavoriteMoviesContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_ID + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_BACKDROP + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_PLOT + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MovieEntry.COLUMN_RELEASE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.MovieEntry.TABLE_NAME );
        onCreate(sqLiteDatabase);
    }
}