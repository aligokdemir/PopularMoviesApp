package com.gokdemir.popularmovies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gokdemir.popularmovies.MainActivity;
import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.R;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokde on 20.02.2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MainActivity.MovieViewHolder> {
    private List<MovieResults.Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MoviesAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
    }

    @Override
    public MainActivity.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_movie, parent, false);
        MainActivity.MovieViewHolder viewHolder = new MainActivity.MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivity.MovieViewHolder holder, int position) {
        MovieResults.Movie movie = mMovieList.get(position);

        NetworkUtils.loadImageURL(movie.getPoster_path(), holder.imageView);

    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setmMovieList(List<MovieResults.Movie> movieList){
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);

        notifyDataSetChanged();
    }
}