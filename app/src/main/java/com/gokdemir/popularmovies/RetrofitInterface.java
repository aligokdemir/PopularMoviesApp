package com.gokdemir.popularmovies;

import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Model.ReviewResults;
import com.gokdemir.popularmovies.Model.VideoResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gokde on 19.02.2018.
 */

public interface RetrofitInterface {

    @GET("/3/movie/{category}")
    Call<MovieResults> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/movie/{id}/reviews")
    Call<ReviewResults> getReviews(
            @Path("id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/videos")
    Call<VideoResults> getVideos(
            @Path("id") String movieId,
            @Query("api_key") String apiKey
    );


}