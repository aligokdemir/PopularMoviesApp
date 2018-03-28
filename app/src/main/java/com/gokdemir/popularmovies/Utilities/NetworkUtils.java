package com.gokdemir.popularmovies.Utilities;


import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.gokdemir.popularmovies.BuildConfig;
import com.gokdemir.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aligokdemir on 17.02.2018.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String YOUTUBE_BASE_URL = "www.youtube.com";
    public static final String YOUTUBE_VIDEO_PATH = "watch";
    public static final String YOUTUBE_VIDEO_QUERY_PAREMETER = "v";

    public static final String YOUTUBE_TRAILER_IMAGE_URL = "https://img.youtube.com/vi/";
    public static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String MOVIE_POSTER_SIZE_PATH = "w185";
    public static final String MOVIE_BACKDROP_SIZE_PATH = "w780";
    public static final String MOVIE_BACKDROP_URL = MOVIE_POSTER_BASE_URL + MOVIE_BACKDROP_SIZE_PATH;

    public static final String MOVIE_DB_URL = "http://api.themoviedb.org";
    public static final String MOVIE_PATH = "movie";
    public static final String MOVIE_REQUEST_BY_MOST_POPULAR = "popular";
    public static final String MOVIE_REQUEST_BY_TOP_RATED = "top_rated";
    public static final String API_REQUEST_KEY = "api_key";
    public static final String LANGUAGE = "en-US";
    public static final int PAGE = 1;

    //API key to be used...
    public static final String TMDB_API_KEY = BuildConfig.API_KEY;

    public static void loadImageURL(String imagePath, ImageView imageView){
        String imageUrl = MOVIE_POSTER_BASE_URL + MOVIE_POSTER_SIZE_PATH + imagePath;

        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .noFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static void loadBackdropURL(String imagePath, ImageView imageView){
        String imageUrl = MOVIE_BACKDROP_URL + imagePath;

        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .noFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static void loadTrailerImageURL(String videoKey, ImageView imageView){
        String imageURL = YOUTUBE_TRAILER_IMAGE_URL + videoKey + "/0.jpg";

        Picasso.with(imageView.getContext())
                .load(imageURL)
                .noFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static String generateYoutubeVideoURL(String videoKey){
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority(YOUTUBE_BASE_URL)
                .appendPath(YOUTUBE_VIDEO_PATH)
                .appendQueryParameter(YOUTUBE_VIDEO_QUERY_PAREMETER, videoKey);

        return builder.build().toString();
    }

}
