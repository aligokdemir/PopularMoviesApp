package com.gokdemir.popularmovies.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokdemir.popularmovies.Model.VideoResults;
import com.gokdemir.popularmovies.R;
import com.gokdemir.popularmovies.Utilities.NetworkUtils;

import java.util.List;

/**
 * Created by gokde on 25.03.2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private List<VideoResults.Video> videoList;
    private final TrailerClickListener mTrailerClickListener;

    public TrailersAdapter(List<VideoResults.Video> videoList, TrailerClickListener mTrailerClickListener){
        this.videoList = videoList;
        this.mTrailerClickListener = mTrailerClickListener;
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_video, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.ViewHolder holder, int position) {
        NetworkUtils.loadTrailerImageURL(videoList.get(position).getKey(), holder.mVideoImage);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void setmVideoList(List<VideoResults.Video> videoList){
        this.videoList.clear();
        this.videoList.addAll(videoList);

        this.notifyDataSetChanged();
    }

    public interface TrailerClickListener{
        void onVideoClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mVideoImage;

        public ViewHolder(View itemView){
            super(itemView);
            mVideoImage = itemView.findViewById(R.id.videoContent);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            int clickPosition = getAdapterPosition();
            mTrailerClickListener.onVideoClick(clickPosition);
        }
    }
}