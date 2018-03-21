package com.gokdemir.popularmovies.Adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gokdemir.popularmovies.Model.MovieResults;
import com.gokdemir.popularmovies.R;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokde on 20.02.2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{
    private List<MovieResults.Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;
    private final MovieClickListener mMovieClickListener;

    public MoviesAdapter(Context context, MovieClickListener mMovieClickListener){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieClickListener = mMovieClickListener;
        this.mMovieList = new ArrayList<>();

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_movie, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieResults.Movie movie = mMovieList.get(position);

        NetworkUtils.loadImageURL(movie.getPoster_path(), holder.imageView);
    }

    public interface MovieClickListener{
        void onClick(int position);
    }

    public interface MovieOnLongClickListener{
        void onLongClick(int position);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setmMovieList(List<MovieResults.Movie> movieList){
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);

        this.notifyDataSetChanged();
    }

    public void removeAt(int position){
        mMovieList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMovieList.size());
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView imageView;

        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            int clickPosition = getAdapterPosition();
            mMovieClickListener.onClick(clickPosition);
        }
    }
}