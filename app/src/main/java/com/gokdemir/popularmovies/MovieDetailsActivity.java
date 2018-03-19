package com.gokdemir.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.prefs.PreferenceChangeEvent;

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
    @BindView(R.id.backdropImageHeader)
    public ImageView mBackdropImageHeader;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.favMovie)
    public FloatingActionButton mFloatingAction;

    private Context context;

    double voteAverage;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setSupportActionBar(toolbar);

        final MovieResults.Movie movie = getIntent().getParcelableExtra(getResources().
                getString(R.string.movie_key));

        SharedPreferences sharedPreferences = getSharedPreferences(getResources()
                .getString(R.string.shared_pref_name), MODE_PRIVATE);
        isFavorite = sharedPreferences.getBoolean(String.valueOf(movie.getId()),
                Boolean.parseBoolean(null));

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        context = this;
        this.setTitle(movie.getTitle());

        ButterKnife.bind(this);
        mMovieTitle.setMovementMethod(new ScrollingMovementMethod());

        if(isFavorite){
            mFloatingAction.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            mFloatingAction.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        voteAverage = movie.getVote_average();
        mMovieTitle.setText(movie.getTitle());
        NetworkUtils.loadImageURL(movie.getPoster_path(), mMoviePoster);
        NetworkUtils.loadBackdropURL(movie.getBackdrop_path(), mBackdropImageHeader);
        mMovieReleaseDate.setText(movie.getRelease_date());
        mMovieVoteAverage.setText(voteAverage + "/10");
        mMovieOverview.setText(movie.getOverview());

        mFloatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite){
                    mFloatingAction.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    SharedPreferences.Editor editor = getSharedPreferences(getResources().
                            getString(R.string.shared_pref_name), MODE_PRIVATE).edit();
                    editor.remove(String.valueOf(movie.getId()));
                    editor.apply();
                    Toast.makeText(context, "The movie is removed from favorites!",
                            Toast.LENGTH_SHORT).show();
                    //remove the movie from database...

                } else{
                    mFloatingAction.setImageResource(R.drawable.ic_favorite_black_24dp);
                    SharedPreferences.Editor editor = getSharedPreferences(getResources().
                            getString(R.string.shared_pref_name), MODE_PRIVATE).edit();
                    editor.putBoolean(String.valueOf(movie.getId()), true);
                    editor.apply();
                    Toast.makeText(context, "The movie is added to favorites!",
                            Toast.LENGTH_SHORT).show();
                    //add the movie to the database...
                }
            }
        });
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

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}