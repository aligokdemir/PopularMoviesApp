package com.gokdemir.popularmovies;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.gokdemir.popularmovies.Data.FavoriteMoviesContract;
import com.gokdemir.popularmovies.Helpers.ConnectionChecker;
import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Model.ReviewResults;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private ProgressDialog progressDialog;

    private MovieResults.Movie movie;
    private List<ReviewResults.Review> reviewList = new ArrayList<>();

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);

        movie = getIntent().getParcelableExtra(getResources().
                getString(R.string.movie_key));

        SharedPreferences sharedPreferences = getSharedPreferences(getResources()
                .getString(R.string.shared_pref_name), MODE_PRIVATE);
        isFavorite = sharedPreferences.getBoolean(String.valueOf(movie.getId()),
                Boolean.parseBoolean(null));

        configureCollapsingToolbar();

        ButterKnife.bind(this);
        mMovieTitle.setMovementMethod(new ScrollingMovementMethod());

        floatingActionButtonSymbol();

        fillUiWithMovieDetails(movie);

        mFloatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite){
                    isFavorite = false;
                    mFloatingAction.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    //first remove the movie id from shared preferences
                    SharedPreferences.Editor editor = getSharedPreferences(getResources().
                            getString(R.string.shared_pref_name), MODE_PRIVATE).edit();
                    editor.remove(String.valueOf(movie.getId()));
                    editor.apply();

                    //then delete the movie from the favorite movie database...
                    deleteMovieFromFavorites();
                    Toast.makeText(context, "The movie is removed from favorites!",
                            Toast.LENGTH_SHORT).show();
                } else{
                    isFavorite = true;
                    mFloatingAction.setImageResource(R.drawable.ic_favorite_black_24dp);

                    //first add the movie id to the favorites
                    SharedPreferences.Editor editor = getSharedPreferences(getResources().
                            getString(R.string.shared_pref_name), MODE_PRIVATE).edit();
                    editor.putBoolean(String.valueOf(movie.getId()), true);
                    editor.apply();

                    //add the movie to the favorite movie database...
                    addMovieToFavorites();

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

    public void addMovieToFavorites(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_ID, movie.getId());
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_IMAGE, movie.getPoster_path());
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_BACKDROP, movie.getBackdrop_path());
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_PLOT, movie.getOverview());
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_RATING, movie.getVote_average());
        contentValues.put(FavoriteMoviesContract.MovieEntry.COLUMN_RELEASE, movie.getRelease_date());
        getContentResolver().insert(FavoriteMoviesContract.MovieEntry.CONTENT_URI, contentValues);
    }

    public void deleteMovieFromFavorites(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = FavoriteMoviesContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendEncodedPath(String.valueOf(movie.getId())).build();
        contentResolver.delete(uri, null, null);
    }

    public void floatingActionButtonSymbol(){
        if(isFavorite){
            mFloatingAction.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            mFloatingAction.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    public void configureCollapsingToolbar(){
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        context = this;
        this.setTitle(movie.getTitle());
    }

    public void fillUiWithMovieDetails(MovieResults.Movie movie){
        voteAverage = movie.getVote_average();
        mMovieTitle.setText(movie.getTitle());
        NetworkUtils.loadImageURL(movie.getPoster_path(), mMoviePoster);
        NetworkUtils.loadBackdropURL(movie.getBackdrop_path(), mBackdropImageHeader);
        mMovieReleaseDate.setText(movie.getRelease_date());
        mMovieVoteAverage.setText(voteAverage + "/10");
        mMovieOverview.setText(movie.getOverview());
    }

    public void retrofitObtainReviews(MovieResults.Movie movie){
        retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.MOVIE_DB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


        Call<ReviewResults> call = retrofitInterface.getReviews(
                String.valueOf(movie.getId()),
                NetworkUtils.TMDB_API_KEY);

        onLoading(0);

        call.enqueue(new Callback<ReviewResults>() {
            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                ReviewResults results = response.body();
                reviewList = results.getResults();

                //set the adapter to the review list...
            }

            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void retrofitObtainVideos(){

    }

    private void onLoading(int type){
        if(type == 0) {
            if (ConnectionChecker.isOnline(this)) {
                progressDialog.setMessage(context.getResources().getString(R.string.review_movie_progress));
                progressDialog.show();
            } else {
                Toast.makeText(this, context.getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        } else if(type == 1){
            progressDialog.setMessage(getResources().getString(R.string.favorite_dialog));
            progressDialog.show();
        }
    }

}