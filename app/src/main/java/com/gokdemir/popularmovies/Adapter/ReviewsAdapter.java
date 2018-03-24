package com.gokdemir.popularmovies.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gokdemir.popularmovies.Model.ReviewResults;
import com.gokdemir.popularmovies.R;

import java.util.List;

/**
 * Created by gokde on 24.03.2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<ReviewResults.Review> reviewList;
    private final ReviewClickListener mReviewClickListener;

    public ReviewsAdapter(List<ReviewResults.Review> reviewList, ReviewClickListener mReviewClickListener){
        this.reviewList = reviewList;
        this.mReviewClickListener = mReviewClickListener;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_reviews, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {
        holder.mContent.setText(reviewList.get(position).getContent());
        holder.mAuthor.setText(reviewList.get(position).getAuthor() + " says:");
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void setmReviewList(List<ReviewResults.Review> reviewList){
        this.reviewList.clear();
        this.reviewList.addAll(reviewList);

        this.notifyDataSetChanged();
    }

    public interface ReviewClickListener{
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mContent;
        public TextView mAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.content);
            mAuthor = itemView.findViewById(R.id.author);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            int clickPosition = getAdapterPosition();
            mReviewClickListener.onClick(clickPosition);
        }
    }
}
