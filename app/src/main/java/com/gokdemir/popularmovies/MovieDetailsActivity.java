package com.gokdemir.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokdemir.popularmovies.Utilities.NetworkUtils;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView mMovieTitle;
    private TextView mMovieReleaseDate;
    private TextView mMovieVoteAverage;
    private TextView mMovieOverview;

    private ImageView mMovieBackdrop;
    private ImageView mMoviePoster;

    private Context context;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        context = this;
        intent = getIntent();

        mMovieTitle = findViewById(R.id.textViewTitle);
        mMovieReleaseDate = findViewById(R.id.textViewReleaseDate);
        mMovieVoteAverage = findViewById(R.id.textViewVoteAverage);
        mMovieOverview = findViewById(R.id.textViewOverview);

        mMovieBackdrop = findViewById(R.id.imageViewBackDrop);
        mMoviePoster = findViewById(R.id.imageViewPoster);

        mMovieTitle.setText(intent.getStringExtra(context.getResources().getString(R.string.movie_title_key)));
        NetworkUtils.loadBackdropURL(context.getResources().getString(R.string.movie_backdrop_key), mMovieBackdrop);
        NetworkUtils.loadImageURL(context.getResources().getString(R.string.movie_poster_key), mMoviePoster);
        mMovieReleaseDate.setText(context.getResources().getString(R.string.movie_release_date_key));
        mMovieVoteAverage.setText(context.getResources().getString(R.string.movie_vote_average_key));
        mMovieOverview.setText(context.getResources().getString(R.string.movie_plot_key));
    }
}
