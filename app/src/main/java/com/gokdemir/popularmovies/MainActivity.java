package com.gokdemir.popularmovies;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gokdemir.popularmovies.Adapter.MoviesAdapter;
import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;

    private ProgressDialog progressDialog;

    private Context context = this;

    List<MovieResults.Movie> movieList;
    public boolean isMostPopular = true;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActivityElements();

        retrofitCall(NetworkUtils.MOVIE_REQUEST_BY_MOST_POPULAR);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sortByPopular:
                        if (!isMostPopular) {
                            retrofitCall(NetworkUtils.MOVIE_REQUEST_BY_MOST_POPULAR);
                            isMostPopular = true;
                        }
                        return true;
                    case R.id.sortByTopRated:
                        if (isMostPopular) {
                            retrofitCall(NetworkUtils.MOVIE_REQUEST_BY_TOP_RATED);
                            isMostPopular = false;
                        }
                        return true;
                    default:
                        return false;
                }
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
        if(!isOnline()){
            mRecyclerView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, context.getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
            return;
        }

        Intent movieDetails = new Intent(this, MovieDetailsActivity.class);
        movieDetails = intentPutExtra(movieDetails, position);

        startActivity(movieDetails);
    }

    public void onLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        if (isOnline()) {
            progressDialog.setMessage(context.getResources().getString(R.string.being_fecthed_info));
            progressDialog.show();
        } else {
            Toast.makeText(this, context.getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }
    }

    public void onFinishedLoading(){
        mRecyclerView.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
    }

    public void initializeActivityElements(){
        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation);
        navigationView.bringToFront();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        progressDialog = new ProgressDialog(this);
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public Intent intentPutExtra(Intent movieDetails, int position){
        movieDetails.putExtra(context.getResources().getString(R.string.movie_title_key),movieList.get(position).getTitle());
        movieDetails.putExtra(context.getResources().getString(R.string.movie_plot_key), movieList.get(position).getOverview());
        movieDetails.putExtra(context.getResources().getString(R.string.movie_release_date_key), movieList.get(position).getRelease_date());
        movieDetails.putExtra(context.getResources().getString(R.string.movie_vote_average_key), movieList.get(position).getVote_average());
        movieDetails.putExtra(context.getResources().getString(R.string.movie_poster_key), movieList.get(position).getPoster_path());
        movieDetails.putExtra(context.getResources().getString(R.string.movie_backdrop_key), movieList.get(position).getBackdrop_path());

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

        onLoading();

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
}