package com.gokdemir.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView title, releaseDate, posterPath, voteAverage, plotSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.movieTitle);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        posterPath = (TextView) findViewById(R.id.posterPath);
        voteAverage = (TextView) findViewById(R.id.voteAverage);
        plotSynopsis = (TextView) findViewById(R.id.plotSynopsis);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.MOVIE_DB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<MovieResults> call = retrofitInterface.getMovies(
                NetworkUtils.MOVIE_REQUEST_BY_MOST_POPULAR,
                NetworkUtils.TMDB_API_KEY,
                NetworkUtils.LANGUAGE,
                NetworkUtils.PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.Movie> movieList = results.getResults();
                MovieResults.Movie movie = movieList.get(0);

                //change these later...
                title.setText(movie.getTitle());
                releaseDate.setText(movie.getRelease_date());
                posterPath.setText(movie.getPoster_path());
                voteAverage.setText(String.valueOf(movie.getVote_average()));
                plotSynopsis.setText(movie.getOverview());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
