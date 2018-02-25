package com.gokdemir.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.gokdemir.popularmovies.Utilities.NetworkUtils;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView mMovieTitle;
    private TextView mMovieReleaseDate;
    private TextView mMovieVoteAverage;
    private TextView mMovieOverview;

    private ImageView mMovieBackdrop;
    private ImageView mMoviePoster;

    private Context context;

    double voteAverage;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        context = this;
        intent = getIntent();

        this.setTitle(intent.getExtras().getString(context.getResources().getString(R.string.movie_title_key)));

        mMovieTitle = findViewById(R.id.textViewTitle);
        mMovieTitle.setMovementMethod(new ScrollingMovementMethod());
        mMovieReleaseDate = findViewById(R.id.textViewReleaseDate);
        mMovieVoteAverage = findViewById(R.id.textViewUserRating);
        mMovieOverview = findViewById(R.id.textViewPlot);

        mMoviePoster = findViewById(R.id.imageViewMoviePoster);

        voteAverage = intent.getDoubleExtra(context.getResources().getString(R.string.movie_vote_average_key), voteAverage);

        mMovieTitle.setText(intent.getStringExtra(context.getResources().getString(R.string.movie_title_key)));
        NetworkUtils.loadImageURL(intent.getStringExtra(context.getResources().getString(R.string.movie_poster_key)), mMoviePoster);
        mMovieReleaseDate.setText(intent.getStringExtra(context.getResources().getString(R.string.movie_release_date_key)));
        mMovieVoteAverage.setText(String.valueOf(voteAverage) + "/10");
        mMovieOverview.setText(intent.getStringExtra(context.getResources().getString(R.string.movie_plot_key)));
    }
}
