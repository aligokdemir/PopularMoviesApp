package com.gokdemir.popularmovies;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gokdemir.popularmovies.Adapter.MoviesAdapter;
import com.gokdemir.popularmovies.Data.FavoriteMoviesContract;
import com.gokdemir.popularmovies.Helpers.BottomNavigationViewHelper;
import com.gokdemir.popularmovies.Helpers.ConnectionChecker;
import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerView)
    public  RecyclerView mRecyclerView;
    @BindView(R.id.navigation)
    public BottomNavigationView bottomNavigationView;

    private ProgressDialog progressDialog;
    private MoviesAdapter mAdapter;
    private Context context = this;
    List<MovieResults.Movie> movieList;

    public boolean isFavorite = false;
    public boolean isMostPopular = true;

    private ActionBarDrawerToggle mToggle;
    Retrofit retrofit;
    private Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActivityElements();

        retrofitCall(NetworkUtils.MOVIE_REQUEST_BY_MOST_POPULAR);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_favorites:
                        isFavorite = true;
                        isMostPopular = false;
                        onLoading(1);
                        loadFavoriteMovies();
                        onFinishedLoading();
                        break;
                    case R.id.action_popular:
                        if(!isMostPopular){
                            retrofitCall(NetworkUtils.MOVIE_REQUEST_BY_MOST_POPULAR);
                            isMostPopular = true;
                            mainActivity.setTitle(R.string.title_popular_movies_activity);
                        }
                        return true;
                    case R.id.action_top_rated:
                        if(isMostPopular || isFavorite){
                            retrofitCall(NetworkUtils.MOVIE_REQUEST_BY_TOP_RATED);
                            isMostPopular = false;
                            isFavorite = false;
                            mainActivity.setTitle(R.string.title_top_rated_activity);
                        }
                        return true;

                }
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        if(!ConnectionChecker.isOnline(this)){
            mRecyclerView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, context.getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            return;
        }

        Intent movieDetails = new Intent(this, MovieDetailsActivity.class);
        movieDetails = intentPutExtra(movieDetails, position);

        startActivity(movieDetails);
    }


    public void onLoading(int type) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        if(type == 0) {
            if (ConnectionChecker.isOnline(this)) {
                progressDialog.setMessage(context.getResources().getString(R.string.being_fecthed_info));
                progressDialog.show();
            } else {
                Toast.makeText(this, context.getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            }
        } else if(type == 1){
            progressDialog.setMessage(getResources().getString(R.string.favorite_dialog));
            progressDialog.show();
        }
    }

    public void onFinishedLoading(){
        mRecyclerView.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
    }

    public void initializeActivityElements(){
        mainActivity = this;

        ButterKnife.bind(this);

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        progressDialog = new ProgressDialog(this);
    }


    public Intent intentPutExtra(Intent movieDetails, int position){
        MovieResults.Movie movie = new MovieResults.Movie(position, movieList);

        movieDetails.putExtra(getResources().getString(R.string.movie_key), movie);

        return movieDetails;
    }

    public void retrofitCall(String queryType) {
        retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.MOVIE_DB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<MovieResults> call = retrofitInterface.getMovies(
                queryType,
                NetworkUtils.TMDB_API_KEY,
                NetworkUtils.LANGUAGE,
                NetworkUtils.PAGE);

        onLoading(0);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                movieList = results.getResults();

                mAdapter.setmMovieList(movieList);
                onFinishedLoading();
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loadFavoriteMovies(){
        movieList.clear();
        Cursor cursor = getContentResolver().query(FavoriteMoviesContract.MovieEntry.CONTENT_URI,
                null, null, null, FavoriteMoviesContract.MovieEntry._ID);


        if(cursor != null){
            while(cursor.moveToNext()) {

                MovieResults.Movie movie = new MovieResults.Movie(
                        cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_BACKDROP)),
                        cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_RELEASE)),
                        Double.valueOf(cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_RATING))),
                        cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_PLOT)),
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.MovieEntry.COLUMN_ID)))
                        );

                movieList.add(movie);
            }
            cursor.close();
        }


        Log.v("Size of Favorites:", String.valueOf(movieList.size()));
        mAdapter.setmMovieList(movieList);
    }

}