package com.gokdemir.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.textViewTitle)
    public TextView mMovieTitle;
    @BindView(R.id.textViewReleaseDate)
    public TextView mMovieReleaseDate;
    @BindView(R.id.textViewUserRating)
    public TextView mMovieVoteAverage;
    @BindView(R.id.textViewPlot)
    public TextView mMovieOverview;
    @BindView(R.id.imageViewMoviePoster)
    public ImageView mMoviePoster;

    private Context context;

    double voteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovieResults.Movie movie = getIntent().getParcelableExtra(getResources().getString(R.string.movie_key));

        context = this;
        this.setTitle(movie.getTitle());

        ButterKnife.bind(this);
        mMovieTitle.setMovementMethod(new ScrollingMovementMethod());

        voteAverage = movie.getVote_average();

        mMovieTitle.setText(movie.getTitle());
        NetworkUtils.loadImageURL(movie.getPoster_path(), mMoviePoster);
        mMovieReleaseDate.setText(movie.getRelease_date());
        mMovieVoteAverage.setText(voteAverage + "/10");
        mMovieOverview.setText(movie.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
