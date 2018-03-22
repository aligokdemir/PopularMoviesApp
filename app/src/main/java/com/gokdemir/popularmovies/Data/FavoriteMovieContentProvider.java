package com.gokdemir.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gokdemir.popularmovies.R;

import com.gokdemir.popularmovies.Data.FavoriteMoviesContract.MovieEntry;

/**
 * Created by gokde on 20.03.2018.
 */

public class FavoriteMovieContentProvider extends ContentProvider {

    public static final int MOVIES = 101;
    public static final int MOVIES_WITH_ID = 102;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private FavoriteMoviesDBHelper mFavoriteMoviesDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoriteMoviesDBHelper = new FavoriteMoviesDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase database = mFavoriteMoviesDBHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (match){
            case MOVIES:
                returnCursor = database.query(MovieEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.unknown_uri_exception) + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = mFavoriteMoviesDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case MOVIES:
                long id = database.insert(FavoriteMoviesContract.MovieEntry.TABLE_NAME, null, contentValues);

                if(id > 0){
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException(getContext().getString(R.string.row_insertion_failed) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.unknown_uri_exception) + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase database = mFavoriteMoviesDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int movieToBeDeleted;

        switch (match){
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                movieToBeDeleted = database.delete(MovieEntry.TABLE_NAME,
                        MovieEntry.COLUMN_ID + "=?",
                        new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.unknown_uri_exception)+ uri);
        }

        if(movieToBeDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return movieToBeDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY, FavoriteMoviesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY, FavoriteMoviesContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);

        return uriMatcher;
    }
}